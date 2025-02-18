package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ObjectUtil;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.domain.server.Sys;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.service.ISysPostService;
import com.ruoyi.system.service.ISysRoleService;
import com.ruoyi.system.service.ISysUserService;

/**
 * 用户信息
 *
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController
{
    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private ISysPostService postService;

    @Autowired
    private AesUtils aesUtils;


    @Resource
    private RedisTemplate<String, SysUser> redisTemplate;

    /**
     * 获取用户列表
     */
    @PreAuthorize("@ss.hasPermi('system:user:list')")
    @GetMapping("/list")
    public TableDataInfo list(SysUser user,Integer pageSize ,Integer pageNum) throws Exception {


        if (StringUtils.isNotEmpty(user.getIsEncrypt())&&"1".equals(user.getIsEncrypt())){
            if (StringUtils.isNotEmpty(user.getUserName())){
                List<SysUser> listKey = redisTemplate.opsForList().range("userList", 0, -1);
                List<SysUser> sysUsers = new ArrayList<>();
                if (listKey != null) {
                    for (SysUser c : listKey){
                        if (c.getUserName().contains(user.getUserName())){

                            sysUsers.add(c);
                        }
                    }
                }
                if (listKey != null && listKey.size() == sysUsers.size()) {
                    List<SysUser> listKeys = redisTemplate.opsForList().range("userList", (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);
                    long total = 0;
                    if (listKeys != null) {
                        total = new PageInfo(listKeys).getTotal();
                    }
                    return getTable(listKeys, total);
                }
                else {
                    if (Boolean.TRUE.equals(redisTemplate.hasKey("userListByTest:" + user.getUserName()))){
                        List<SysUser> listKeys = redisTemplate.opsForList().range("userListByTest:"+ user.getUserName(), (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);

                        return getTable(listKeys, redisTemplate.opsForList().size("userListByTest:"+ user.getUserName()));
                    }else {
                        redisTemplate.delete("userListByTest:*");
                        for (SysUser s : sysUsers){
                            redisTemplate.opsForList().rightPush("userListByTest:"+user.getUserName(),s);
                        }
                        List<SysUser> listKeys = redisTemplate.opsForList().range("userListByTest:"+ user.getUserName(), (long) (pageNum - 1) * pageSize, ((long) pageNum * pageSize) - 1);
                        return getTable(listKeys, redisTemplate.opsForList().size("userListByTest:"+ user.getUserName()));
                    }


                }

            }
        }



        if(user.getPhonenumber()!=null&&!"".equals(user.getPhonenumber())){
            user.setPhonenumber(aesUtils.encrypt(user.getPhonenumber()));
        }
        if(user.getUserName()!=null&&!"".equals(user.getUserName())){
            user.setUserName(aesUtils.encrypt(user.getUserName()));
        }
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        for (SysUser c :list){
            if (c.getUserName()!=null){
                c.setUserName(aesUtils.decrypt(c.getUserName()));
            }
            if (c.getPhonenumber()!=null){
                c.setPhonenumber(aesUtils.decrypt(c.getPhonenumber()));
            }
        }



        return getDataTable(list);
    }

    @Log(title = "用户管理", businessType = BusinessType.EXPORT)
    @PreAuthorize("@ss.hasPermi('system:user:export')")
    @PostMapping("/export")
    public void export(HttpServletResponse response, SysUser user)
    {
        List<SysUser> list = userService.selectUserList(user);
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.exportExcel(response, list, "用户数据");
    }

    @Log(title = "用户管理", businessType = BusinessType.IMPORT)
    @PreAuthorize("@ss.hasPermi('system:user:import')")
    @PostMapping("/importData")
    public AjaxResult importData(MultipartFile file, boolean updateSupport) throws Exception
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        List<SysUser> userList = util.importExcel(file.getInputStream());
        String operName = getUsername();
        String message = userService.importUser(userList, updateSupport, operName);
        return AjaxResult.success(message);
    }

    @PostMapping("/importTemplate")
    public void importTemplate(HttpServletResponse response)
    {
        ExcelUtil<SysUser> util = new ExcelUtil<SysUser>(SysUser.class);
        util.importTemplateExcel(response, "用户数据");
    }

    /**
     * 根据用户编号获取详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping(value = { "/", "/{userId}" })
    public AjaxResult getInfo(@PathVariable(value = "userId", required = false) Long userId) throws Exception {
        userService.checkUserDataScope(userId);
        AjaxResult ajax = AjaxResult.success();
        List<SysRole> roles = roleService.selectRoleAll();
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        ajax.put("posts", postService.selectPostAll());
        if (StringUtils.isNotNull(userId))
        {
            SysUser sysUser = userService.selectUserById(userId);
            if (sysUser.getUserName()!=null){
                sysUser.setUserName(aesUtils.decrypt(sysUser.getUserName()));
            }
            if (sysUser.getPhonenumber()!=null){
                sysUser.setPhonenumber(aesUtils.decrypt(sysUser.getPhonenumber()));
            }
            ajax.put(AjaxResult.DATA_TAG, sysUser);
            ajax.put("postIds", postService.selectPostListByUserId(userId));
            ajax.put("roleIds", sysUser.getRoles().stream().map(SysRole::getRoleId).collect(Collectors.toList()));
        }
        return ajax;
    }

    /**
     * 新增用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:add')")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@Validated @RequestBody SysUser user) throws Exception {
        if (UserConstants.NOT_UNIQUE.equals(userService.checkUserNameUnique(user.getUserName())))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，登录账号已存在");
        }
        else if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("新增用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setCreateBy(getUsername());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        if (user.getUserName()!=null){
            user.setUserName(aesUtils.encrypt(user.getUserName()));
        }
        if (user.getPhonenumber()!=null){
            user.setPhonenumber(aesUtils.encrypt(user.getPhonenumber()));
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@Validated @RequestBody SysUser user) throws Exception {
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        if (StringUtils.isNotEmpty(user.getPhonenumber())
                && UserConstants.NOT_UNIQUE.equals(userService.checkPhoneUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，手机号码已存在");
        }
        else if (StringUtils.isNotEmpty(user.getEmail())
                && UserConstants.NOT_UNIQUE.equals(userService.checkEmailUnique(user)))
        {
            return AjaxResult.error("修改用户'" + user.getUserName() + "'失败，邮箱账号已存在");
        }
        user.setUpdateBy(getUsername());
        if (user.getUserName()!=null){
            user.setUserName(aesUtils.encrypt(user.getUserName()));
        }
        if (user.getPhonenumber()!=null){
            user.setPhonenumber(aesUtils.encrypt(user.getPhonenumber()));
        }
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户
     */
    @PreAuthorize("@ss.hasPermi('system:user:remove')")
    @Log(title = "用户管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        if (ArrayUtils.contains(userIds, getUserId()))
        {
            return error("当前用户不能删除");
        }
        return toAjax(userService.deleteUserByIds(userIds));
    }

    /**
     * 重置密码
     */
    @PreAuthorize("@ss.hasPermi('system:user:resetPwd')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/resetPwd")
    public AjaxResult resetPwd(@RequestBody SysUser user) throws Exception {
        if (user.getUserName()!=null){
            user.setUserName(aesUtils.encrypt(user.getUserName()));
        }
        if (user.getPhonenumber()!=null){
            user.setPhonenumber(aesUtils.encrypt(user.getPhonenumber()));
        }
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setPassword(SecurityUtils.encryptPassword(user.getPassword()));
        user.setUpdateBy(getUsername());
        return toAjax(userService.resetPwd(user));
    }

    /**
     * 状态修改
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.UPDATE)
    @PutMapping("/changeStatus")
    public AjaxResult changeStatus(@RequestBody SysUser user) throws Exception {
        if (user.getUserName()!=null){
            user.setUserName(aesUtils.encrypt(user.getUserName()));
        }
        if (user.getPhonenumber()!=null){
            user.setPhonenumber(aesUtils.encrypt(user.getPhonenumber()));
        }
        userService.checkUserAllowed(user);
        userService.checkUserDataScope(user.getUserId());
        user.setUpdateBy(getUsername());
        return toAjax(userService.updateUserStatus(user));
    }

    /**
     * 根据用户编号获取授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:query')")
    @GetMapping("/authRole/{userId}")
    public AjaxResult authRole(@PathVariable("userId") Long userId)
    {
        AjaxResult ajax = AjaxResult.success();
        SysUser user = userService.selectUserById(userId);
        List<SysRole> roles = roleService.selectRolesByUserId(userId);
        ajax.put("user", user);
        ajax.put("roles", SysUser.isAdmin(userId) ? roles : roles.stream().filter(r -> !r.isAdmin()).collect(Collectors.toList()));
        return ajax;
    }

    /**
     * 用户授权角色
     */
    @PreAuthorize("@ss.hasPermi('system:user:edit')")
    @Log(title = "用户管理", businessType = BusinessType.GRANT)
    @PutMapping("/authRole")
    public AjaxResult insertAuthRole(Long userId, Long[] roleIds)
    {
        userService.checkUserDataScope(userId);
        userService.insertUserAuth(userId, roleIds);
        return success();
    }


    /**
     * 导出/医生就诊等功能需要验证当前操作人的密码
     * @param params
     * @return
     */
    @PostMapping("/VerifyPassword")
    public AjaxResult insertAuthRole(@RequestBody Map<String,Object> params)
    {
        String passwordDb = SecurityUtils.getLoginUser().getPassword();
        if(ObjectUtil.isEmpty(params.get("password"))){
            return AjaxResult.error("密码不能为空!");
        }
        if (!SecurityUtils.matchesPassword((String) params.get("password"),passwordDb)) {
            return AjaxResult.error("密码输入错误!");
        }
        return AjaxResult.success();
    }
}
