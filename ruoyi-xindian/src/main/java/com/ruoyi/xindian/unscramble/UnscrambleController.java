package com.ruoyi.xindian.unscramble;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.vipPatient.domain.SxReportUnscramble;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.vipPatient.service.SxReportUnscrambleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/unscramble")
public class UnscrambleController extends BaseController {


    @Resource
    private ISysUserService sysUserService;



    @Resource
    private IVipPatientService vipPatientService;


    @Resource
    private SxReportUnscrambleService sxReportUnscrambleService;

    @Resource
    private AesUtils aesUtils;



    @GetMapping("/getUserFrequency")
    public AjaxResult getUserFrequency() throws Exception {
        Long userId = getUserId();
        SysUser sysUser1 = sysUserService.selectUserById(userId);
        if (sysUser1==null){
            throw new ServiceException("用户不存在");
        }


        Map<String, Object> map = new HashMap<>();
        String encrypt = sysUser1.getPhonenumber();
        VipPatient vipPhone = vipPatientService.findVipPhone(encrypt);
        if (vipPhone != null) {
            map.put("xindian",vipPhone.getVipNum()==null?0:vipPhone.getVipNum());
            map.put("isVip",true);
        }else {
            map.put("xindian",sysUser1.getDetectionNum()==null?0:sysUser1.getDetectionNum());
            map.put("isVip",false);
        }

        map.put("zhoubao",sysUser1.getWeeklyNewspaperNum()==null?0:sysUser1.getWeeklyNewspaperNum());

        SxReportUnscramble sxReportUnscramble = sxReportUnscrambleService.selectSxReportUnscrambleById(aesUtils.decrypt(sysUser1.getPhonenumber()));
        if (sxReportUnscramble!=null){
            map.put("ysbg",sxReportUnscramble.getVipNum()==null?0:sxReportUnscramble.getVipNum());
        }else {
            map.put("ysbg",0);
        }


        return AjaxResult.success(map);
    }




}
