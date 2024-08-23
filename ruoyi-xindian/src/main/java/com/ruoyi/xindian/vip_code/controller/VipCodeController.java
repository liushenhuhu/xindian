package com.ruoyi.xindian.vip_code.controller;


import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.fw_log.mapper.FwLogMapper;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.vipPatient.domain.VipPatient;
import com.ruoyi.xindian.vipPatient.service.IVipPatientService;
import com.ruoyi.xindian.vip_code.domain.VipCode;
import com.ruoyi.xindian.vip_code.service.VipCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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


    @Resource
    private FwLogMapper fwLogMapper;

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
        if(one.getNum()<25){
            a=2;
            data=2;
        }else if(one.getNum()<100){
            a=2;
            data=6;
        }else {
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
            SysUser sysUser2 = new SysUser();
            sysUser2.setUserId(sysUser.getUserId());
            sysUser2.setDetectionTime(vipPatient.getEndDate());
            sysUserMapper.updateUser(sysUser2);
        }else {
            if(one.getIsVip()==1){//激活码是vip
                Date date1 = new Date();
                //升级为vip
                VipPatient vip = new VipPatient();
                vip.setPatientPhone(sysUser.getPhonenumber());
                vip.setVipNum(Long.valueOf(one.getNum())+sysUser.getDetectionNum());
                //加时间
                calendar.setTime(date1);
                calendar.add(a, data);
                vip.setEndDate(calendar.getTime());
                vip.setCreateTime(date1);
                iVipPatientService.insertVipPatient(vip);
                sysUserMapper.updateDeteTime(sysUser.getPhonenumber());
                SysUser sysUser2 = new SysUser();
                sysUser2.setUserId(sysUser.getUserId());
                sysUser2.setDetectionTime(vip.getEndDate());
                sysUserMapper.updateUser(sysUser2);
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

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.runAsync(() ->{
            System.out.println("异步线程 =====> 开始添加购买服务日志 =====> " + new Date());
            try{
                FwLog fwLog = new FwLog();
                fwLog.setUserName(sysUser.getPhonenumber());
                fwLog.setMsg("激活激活码获得心电解读服务");
                fwLog.setStatus("1");
                fwLog.setLogTime(new Date());
                fwLog.setFwStatus("1");
                fwLog.setFwNum(one.getNum());
                String ipAddr = IpUtils.getIpAddr(request);
                fwLog.setIpaddr(ipAddr);
                try {
                    String address = getAddress(ipAddr);
                    fwLog.setLoginLocation(address);
                } catch (Exception e) {
                    System.out.println(e);
                }
                fwLogMapper.insert(fwLog);
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束添加购买服务日志 =====> " + new Date());
        },executorService);
        executorService.shutdown(); // 回收线程池
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

    public String getAddress(String ip){
        String json_result = null;
        String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?resource_id=6006&format=json&query=" + ip;
        HttpResponse res = HttpRequest.get(url).execute();
        JSONObject resJson = JSONObject.parseObject(res.body());
        JSONArray resArr = JSONArray.parseArray(resJson.getString("data"));
        resJson = JSONObject.parseObject("" + resArr.get(0));

        return resJson.getString("location");
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
