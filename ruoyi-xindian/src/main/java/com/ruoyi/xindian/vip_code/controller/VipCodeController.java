package com.ruoyi.xindian.vip_code.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.vip_code.domain.VipCode;
import com.ruoyi.xindian.vip_code.service.VipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@RestController
@RequestMapping("/vipcode/vipcode")
public class VipCodeController extends BaseController {
    @Autowired
    private VipCodeService vipCodeService;
    @Autowired
    private IVipPatientService iVipPatientService;
    @Autowired
    private IPatientService iPatientService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private ISysUserService iSysUserService;
    @Autowired
    private SysUserMapper sysUserMapper;
    //web分页查询激活码
    @GetMapping("/web/list")
    public TableDataInfo list(VipCode vipCode){
        startPage();
        LambdaQueryWrapper<VipCode> wrapper = new LambdaQueryWrapper<VipCode>();
        wrapper.like(vipCode.getCode()!=null,VipCode::getCode,vipCode.getCode())
                .like(vipCode.getIsVip()!=null,VipCode::getIsVip,vipCode.getIsVip())
                .like(vipCode.getNum()!=null,VipCode::getNum,vipCode.getNum())
                .like(vipCode.getState()!=null,VipCode::getState,vipCode.getState())
                .orderByDesc(VipCode::getCreateTime);
        List<VipCode> list = vipCodeService.list(wrapper);
        return getTable(list,new PageInfo(list).getTotal());
    }
    //新增  生成num个vip激活码
    @PostMapping("/web/insertCode/{n}")
    public AjaxResult insertCode(VipCode vipCode,@PathVariable("n") Integer n){
        List<VipCode> vipCodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String code = randomGen(12);
            VipCode item = new VipCode();
            item.setNum(vipCode.getNum());
            item.setIsVip(vipCode.getIsVip());
            item.setCode(code);
            item.setState(0);
            item.setCreateTime(new Date());
            item.setUpdateTime(new Date());
            vipCodes.add(item);
        }
        for (VipCode code : vipCodes) {
            System.out.println(code);
        }
        boolean b = vipCodeService.saveBatch(vipCodes);

        if(!b){
            return AjaxResult.error("生成激活码失败");
        }
        return AjaxResult.success("生成"+n+"个激活码");
    }

    //小程序端使用vip激活码
    @GetMapping("/useCode")
    @Transactional
    public AjaxResult useCode(@RequestParam("code") String code, HttpServletRequest request){
        LoginUser loginUser = tokenService.getLoginUser(request);
        SysUser sysUser = iSysUserService.selectUserById(loginUser.getUser().getUserId());
        if(sysUser==null){
            return AjaxResult.error("用户信息不存在");
        }
        LambdaQueryWrapper<VipCode> wrapper = new LambdaQueryWrapper<VipCode>();
        wrapper.eq(VipCode::getCode,code);
        VipCode one = vipCodeService.getOne(wrapper);

        if(one==null){
            return AjaxResult.error("激活码无效");
        }
        if(one.getState()==1){
            return AjaxResult.error("激活码已被使用");
        }
        //修改激活码状态  修改成已使用
        one.setState(1);
        one.setUseTime(new Date());
        vipCodeService.updateById(one);

        Calendar calendar = Calendar.getInstance();
        int a=0;// 1是对年份加时间 2是对月份加时间
        int data=0;//加多少
        //服务次数判断  加上相应时间
        if(one.getNum()==25){
            a=2;
            data=3;
        }
        if(one.getNum()==100){
            a=1;
            data=1;
        }
        VipPatient vipPatient=iVipPatientService.findVipPhone(sysUser.getPhonenumber());
        if(vipPatient!=null){
            //用户原本就是vip
            //设置加上去的时间
            calendar.setTime(vipPatient.getEndDate());
            calendar.add(a, data);
            vipPatient.setEndDate(calendar.getTime());

            vipPatient.setVipNum(vipPatient.getVipNum()+one.getNum());
            iVipPatientService.updateVipPatient(vipPatient);
        }else {
            if(one.getIsVip()==1){//激活码是vip
                //升级为vip
                VipPatient vip = new VipPatient();
                vip.setPatientPhone(sysUser.getPhonenumber());
                vip.setVipNum(Long.valueOf(one.getNum())+sysUser.getDetectionNum());
                //加时间
                calendar.setTime(new Date());
                calendar.add(a, data);
                vip.setEndDate(calendar.getTime());
                iVipPatientService.insertVipPatient(vip);
                sysUserMapper.updateDeteTime(sysUser.getPhonenumber());
            }else {//普通激活码
                //设置加上去的时间
                calendar.setTime(sysUser.getDetectionTime());
                calendar.add(a, data);
                //加时间
                sysUser.setDetectionTime(calendar.getTime());
                sysUser.setDetectionNum(sysUser.getDetectionNum()+one.getNum());
                iSysUserService.updateUser(sysUser);
            }

        }

        return AjaxResult.success("激活成功");
    }

    /**
     * 随机数
     * @param place 定义随机数的位数
     */
    public String randomGen(int place) {
        String base = "0123456789";
        StringBuilder sb = new StringBuilder();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }
    //根据id获取详细信息
    @GetMapping("/web/detail/{id}")
    public AjaxResult detail(@PathVariable("id") Long id)
    {

        VipCode vipCode = vipCodeService.getById(id);
        return AjaxResult.success(vipCode);
    }
    //修改
    @PutMapping
    public AjaxResult update(VipCode vipCode)
    {

        vipCode.setUpdateTime(new Date());
        return toAjax(vipCodeService.updateById(vipCode));
    }

    //删除
    @DeleteMapping("/web/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {

        return toAjax(vipCodeService.removeByIds(Arrays.asList(ids)));
    }
    //导出
    @PostMapping("/export")
    public void export(HttpServletResponse response,VipCode vipCode)
    {

        LambdaQueryWrapper<VipCode> wrapper = new LambdaQueryWrapper<VipCode>();
        wrapper.like(vipCode.getCode()!=null,VipCode::getCode,vipCode.getCode())
                .like(vipCode.getIsVip()!=null,VipCode::getIsVip,vipCode.getIsVip())
                .like(vipCode.getNum()!=null,VipCode::getNum,vipCode.getNum())
                .like(vipCode.getState()!=null,VipCode::getState,vipCode.getState());
        List<VipCode> list = vipCodeService.list(wrapper);
        ExcelUtil<VipCode> util = new ExcelUtil<VipCode>(VipCode.class);
        util.exportExcel(response, list, "激活码列表");
    }
}
