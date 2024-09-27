package com.ruoyi.xindian.equipment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.equipment.domain.EquipmentActivate;
import com.ruoyi.xindian.equipment.service.EquipmentActivateService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设备归属激活Controller
 * 
 * @author ruoyi
 * @date 2024-09-26
 */
@RestController
@RequestMapping("/equipment/activate")
public class EquipmentActivateController extends BaseController
{
    @Autowired
    private EquipmentActivateService equipmentActivateService;


    @Autowired
    private IVipPatientService vipPatientService;

    @Resource
    private SysUserMapper sysUserMapper;


    @Resource
    private AesUtils aesUtils;

    @Autowired
    private RedisCache redisCache;

    /**
     * 查询设备归属激活列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:activate:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipmentActivate equipmentActivate)
    {
        startPage();
        List<EquipmentActivate> list = equipmentActivateService.selectEquipmentActivateList(equipmentActivate);
        return getDataTable(list);
    }

    /**
     * 导出设备归属激活列表
     */
    @PreAuthorize("@ss.hasPermi('equipment:activate:export')")
    @Log(title = "设备归属激活", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentActivate equipmentActivate)
    {
        List<EquipmentActivate> list = equipmentActivateService.selectEquipmentActivateList(equipmentActivate);
        ExcelUtil<EquipmentActivate> util = new ExcelUtil<EquipmentActivate>(EquipmentActivate.class);
        util.exportExcel(response, list, "设备归属激活数据");
    }

    /**
     * 获取设备归属激活详细信息
     */
    @PreAuthorize("@ss.hasPermi('equipment:activate:query')")
    @GetMapping(value = "/{equipmentId}")
    public AjaxResult getInfo(@PathVariable("equipmentId") Long equipmentId)
    {
        return AjaxResult.success(equipmentActivateService.selectEquipmentActivateByEquipmentId(equipmentId));
    }

    /**
     * 新增设备归属激活
     */
    @PreAuthorize("@ss.hasPermi('equipment:activate:add')")
    @Log(title = "设备归属激活", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentActivate equipmentActivate)
    {
        return toAjax(equipmentActivateService.insertEquipmentActivate(equipmentActivate));
    }

    /**
     * 修改设备归属激活
     */
    @PreAuthorize("@ss.hasPermi('equipment:activate:edit')")
    @Log(title = "设备归属激活", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentActivate equipmentActivate)
    {
        return toAjax(equipmentActivateService.updateEquipmentActivate(equipmentActivate));
    }

    /**
     * 删除设备归属激活
     */
    @PreAuthorize("@ss.hasPermi('equipment:activate:remove')")
    @Log(title = "设备归属激活", businessType = BusinessType.DELETE)
	@DeleteMapping("/{equipmentIds}")
    public AjaxResult remove(@PathVariable Long[] equipmentIds)
    {
        return toAjax(equipmentActivateService.deleteEquipmentActivateByEquipmentIds(equipmentIds));
    }


    /**
     * 判断设备的归属
     * @param equipmentActivate
     * @return
     */
    @GetMapping("/getEquipStatus")
    public AjaxResult getEquipStatus(EquipmentActivate equipmentActivate) throws Exception {
        if (StringUtils.isEmpty(equipmentActivate.getEquipmentCode())){
            return AjaxResult.error("设备编号不能为空");
        }
        String username = aesUtils.decrypt(getUsername());

        EquipmentActivate equipmentActivate1 = equipmentActivateService.selectEquipmentActivateByEquipmentCode(equipmentActivate.getEquipmentCode());
        if (equipmentActivate1==null) {
            return AjaxResult.success();
        }
        Map<String,Boolean> map = new HashMap<>();
        String encrypt = aesUtils.encrypt(username);
        //判断是否激活
        if (StringUtils.isEmpty(equipmentActivate1.getStatus())|| "0".equals(equipmentActivate1.getStatus())){
            map.put("isActivate",true);
            //修改设备激活状态
            equipmentActivate1.setPatientPhone(username);
            equipmentActivate1.setActivatePhone(username);
            equipmentActivate1.setFirstTime(new Date());
            equipmentActivate1.setStatus("1");
            equipmentActivate1.setMac(equipmentActivate.getMac());
            equipmentActivateService.updateEquipmentActivate(equipmentActivate1);
            //添加两次服务

            VipPatient vipPhone = vipPatientService.findVipPhone(encrypt);
            if (vipPhone==null){
                sysUserMapper.updateNum(encrypt,2);
            }else {
                vipPhone.setVipNum(vipPhone.getVipNum()+2);
                vipPatientService.updateVipPatient(vipPhone);
            }
            return AjaxResult.success(map);
        }
        map.put("isActivate",false);
        //判断数据中手机号是否一致，当手机号为空时，添加当前人手机号进行绑定


        if (StringUtils.isEmpty(equipmentActivate1.getPatientPhone())) {
            //修改当前设备归属
            equipmentActivate1.setPatientPhone(username);
            equipmentActivateService.updateEquipmentActivate(equipmentActivate1);
            map.put("isSame",true);
            return AjaxResult.success(map);
        }

        if (equipmentActivate1.getPatientPhone().equals(username)){
            map.put("isSame",true);
            return AjaxResult.success(map);
        }else {
            map.put("isSame",false);
            return AjaxResult.success(map);
        }

    }

    /**
     * 解除绑定
     * @param loginBody
     * @return
     * @throws Exception
     */
    @PutMapping("/updateEquipPhone")
    public AjaxResult updateEquipPhone(@RequestBody LoginBody loginBody) throws Exception {

        String username = aesUtils.decrypt(getUsername());
        checkSmsCode(username, loginBody.getSmsCode(), loginBody.getUuid());

        EquipmentActivate equipmentActivate1 = equipmentActivateService.selectEquipmentActivateByEquipmentCode(loginBody.getEquipmentCode());

        if (equipmentActivate1==null){
            return AjaxResult.error("设备不存在");
        }

        if (!equipmentActivate1.getPatientPhone().equals(username)) {
            return AjaxResult.error("账号下未绑定该设备");
        }

        EquipmentActivate equipmentActivate = new EquipmentActivate();
        equipmentActivate.setEquipmentCode(loginBody.getEquipmentCode());
        equipmentActivate.setPatientPhone(username);
        equipmentActivateService.updateEquipmentActivateByPhoneIsNull(equipmentActivate);

        return AjaxResult.success();
    }

    /**
     * 获取用户绑定设备列表
     * @return
     * @throws Exception
     */
    @GetMapping("/getActivateCode")
    public AjaxResult getActivateCode() throws Exception {
        String username = aesUtils.decrypt(getUsername());
        EquipmentActivate equipmentActivate = new EquipmentActivate();
        equipmentActivate.setPatientPhone(username);
        List<EquipmentActivate> list = equipmentActivateService.selectEquipmentActivateList(equipmentActivate);
        return AjaxResult.success(list);
    }


    /**
     * 检查手机号登录
     *
     * @param
     */
    public void checkSmsCode(String mobile, String inputCode, String uuid) {

        String verifyKey = Constants.SMS_CAPTCHA_CODE_KEY + uuid;

        Map<String, Object> smsCode = redisCache.getCacheObject(verifyKey);
//        redisCache.deleteObject(verifyKey);
        if (StringUtils.isEmpty(inputCode)) {
            throw new BadCredentialsException("验证码不能为空");
        }

        if (smsCode == null) {
            throw new BadCredentialsException("验证码失效");
        }

        String applyMobile = (String) smsCode.get("mobile");
        int code = (int) smsCode.get("code");

        if (!applyMobile.equals(mobile)) {
            throw new BadCredentialsException("手机号码不一致");
        }
        if (code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }
    }
}
