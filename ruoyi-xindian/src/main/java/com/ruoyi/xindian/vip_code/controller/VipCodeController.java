package com.ruoyi.xindian.vip_code.controller;


import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.product.domain.TProduct;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.vip_code.domain.VipCode;
import com.ruoyi.xindian.vip_code.service.VipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/list")
    public TableDataInfo list(VipCode vipCode){
        startPage();
        LambdaQueryWrapper<VipCode> wrapper = new LambdaQueryWrapper<VipCode>();
        wrapper.like(vipCode.getCode()!=null,VipCode::getCode,vipCode.getCode())
                .like(vipCode.getIsVip()!=null,VipCode::getIsVip,vipCode.getIsVip());
        List<VipCode> list = vipCodeService.list(wrapper);
        return getTable(list,new PageInfo(list).getTotal());
    }
    //生成num个vip激活码
    @PostMapping("/insertCode/{n}")
    public AjaxResult insertCode(VipCode vipCode,@PathVariable("n") Integer n){
        List<VipCode> vipCodes = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            String code = randomGen(16);
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
    public AjaxResult useCode(@RequestParam("code") String code, @RequestParam("phone")String phone){
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
        one.setUpdateTime(new Date());
        vipCodeService.updateById(one);

        Calendar calendar = Calendar.getInstance();
        int a=0;// 1是对年份加时间 2是对月份加时间
        int data=0;//加多少
        //服务次数判断  加上相应时间
        if(one.getNum()<=10){
            a=2;
            data=1;
        }
        if(one.getNum()>10&&one.getNum()<=30){
            a=2;
            data=6;
        }
        if(one.getNum()>30){
            a=1;
            data=1;
        }
        if(one.getIsVip()==1){
            //激活码为vip的
            VipPatient vipPatient=iVipPatientService.findVipPhone(phone);
            if(vipPatient==null){
                return AjaxResult.error("未找到vip用户");
            }
            //设置加上去的时间
            calendar.setTime(vipPatient.getEndDate());
            calendar.add(a, data);
            vipPatient.setEndDate(calendar.getTime());

            vipPatient.setVipNum(vipPatient.getVipNum()+one.getNum());
            iVipPatientService.updateVipPatient(vipPatient);
        }else {
            //激活码是普通的
            Patient patient = iPatientService.selectPatientByPatientPhone(phone);
            if(patient==null){
                return AjaxResult.error("未找到用户");
            }
            //设置加上去的时间
            calendar.setTime(patient.getDetectionTime());
            calendar.add(a, data);
            patient.setDetectionTime(calendar.getTime());

            patient.setDetectionNum(patient.getDetectionNum()+one.getNum());
            iPatientService.updatePatient(patient);
        }

        return AjaxResult.success("激活成功");
    }

    /**
     * 随机数
     * @param place 定义随机数的位数
     */
    public String randomGen(int place) {
        String base = "wqetryuioplkjhgfdsazxcvbmnABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuffer sb = new StringBuffer();
        Random rd = new Random();
        for(int i=0;i<place;i++) {
            sb.append(base.charAt(rd.nextInt(base.length())));
        }
        return sb.toString();
    }
}
