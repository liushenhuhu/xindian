package com.ruoyi.web.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginTeleBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.utils.Tool;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
@Component("login")
public class SysLoginController {
    private static final Logger LOG = LoggerFactory.getLogger(SysLoginController.class);
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private TokenService tokenService;

    /**
     * 登录失败限制次数
     */
    final Integer LOGIN_LIMIT_NUMBER = 5;

    /**
     * 登录超出限制锁定时间设置
     */
    final Integer OVERTIME_SETTING_TIME = 10 * 60;

    /**
     * 手机验证码登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/telephoneCodeLogin")
    public AjaxResult telephoneCodeLogin(@RequestBody LoginBody loginBody) {
        AjaxResult ajax = AjaxResult.success();

        String telephone = loginBody.getUsername();
        // 判断是否存在改手机号
        SysUser sysUser = userService.selectUserByPhoneNumber(telephone);
        if (sysUser == null) {
            ajax.put("state", "notExists");
            return ajax;
        }

        LoginUser loginUser = new LoginUser(sysUser.getUserId(), sysUser.getDeptId(), sysUser, permissionService.getMenuPermission(sysUser));
        loginService.recordLoginInfo(loginUser.getUserId());
        String token = tokenService.createToken(loginUser);

        ajax.put("status", "login");
        ajax.put(Constants.TOKEN, token);

        return ajax;
    }

    /**
     * 登录方法(用户名)
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        System.out.println(Tool.getMac());
        AjaxResult ajax = AjaxResult.success();

        // 判断用户是否为系统用户
        SysUser sysUser = userService.selectUserByUserName(loginBody.getUsername());
        if (sysUser != null) {
            ajax.put("dept", sysUser.getDept());
        }

        Object cacheObject = redisCache.getCacheObject(loginBody.getUsername());
        Object cacheObjectTime = redisCache.getCacheObject(loginBody.getUsername() + "overtime");


        // 验证是否被锁定
        if (cacheObjectTime != null) {
            ajax.put("status", "lock");
            String token = tokenService.createToken();
            ajax.put(Constants.TOKEN, token);
            return ajax;
        }

        // 验证登录次数是否超时
        if (cacheObject != null && Integer.parseInt(cacheObject.toString()) >= LOGIN_LIMIT_NUMBER) {
            redisCache.setCacheObject(loginBody.getUsername() + "overtime", loginBody.getUsername());
            redisCache.expire(loginBody.getUsername() + "overtime", OVERTIME_SETTING_TIME);
            redisCache.deleteObject(loginBody.getUsername());
            String token = tokenService.createToken();
            ajax.put("status", "over");
            ajax.put(Constants.TOKEN, token);
            return ajax;
        }

        // 生成令牌
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put("status", "login");
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 登录方法(手机号)
     *
     * @param loginTeleBody 登录信息
     * @return 结果
     */
    @PostMapping("/loginTele")
    public AjaxResult loginTele(@RequestBody LoginTeleBody loginTeleBody) {
        System.out.println(Tool.getMac());
        AjaxResult ajax = AjaxResult.success();

        // 判断用户是否为系统用户
        SysUser sysUser = userService.selectUserByPhoneNumber(loginTeleBody.getPhonenumber());
        if (sysUser != null) {
            ajax.put("dept", sysUser.getDept());
        }
        String UserName=userService.selectUserByPhoneNumber(loginTeleBody.getPhonenumber()).getUserName();

        Object cacheObject = redisCache.getCacheObject(UserName);
        Object cacheObjectTime = redisCache.getCacheObject(UserName + "overtime");

        LOG.info("运行到这里了");
        // 验证是否被锁定
        if (cacheObjectTime != null) {
            ajax.put("status", "lock");
            String token = tokenService.createToken();
            ajax.put(Constants.TOKEN, token);
            return ajax;
        }

        // 验证登录次数是否超时
        if (cacheObject != null && Integer.parseInt(cacheObject.toString()) >= LOGIN_LIMIT_NUMBER) {
            redisCache.setCacheObject(UserName + "overtime", UserName);
            redisCache.expire(UserName + "overtime", OVERTIME_SETTING_TIME);
            redisCache.deleteObject(UserName);
            String token = tokenService.createToken();
            ajax.put("status", "over");
            ajax.put(Constants.TOKEN, token);
            return ajax;
        }

        // 生成令牌
        String token = loginService.login(UserName, loginTeleBody.getPassword(), loginTeleBody.getCode(),
                loginTeleBody.getUuid());
        ajax.put("status", "login");
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

}
