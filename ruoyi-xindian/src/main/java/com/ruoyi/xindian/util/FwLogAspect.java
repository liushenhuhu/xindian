package com.ruoyi.xindian.util;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.ServletUtils;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.xindian.fw_log.domain.FwLog;
import com.ruoyi.xindian.fw_log.service.FwLogService;
import com.ruoyi.xindian.report.domain.Report;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


import java.util.Date;


/**
 * @author lixinlong
 * 2023/8/16 15:49
 */
@Aspect
@Component
public class FwLogAspect {

    private Logger logger = LoggerFactory.getLogger(FwLogAspect.class);
    @Autowired
    private FwLogService fwLogService;
    @Pointcut("@annotation(com.ruoyi.xindian.util.FwLogAnnotation)")
    public void pointcut(){

    }

    @Around("pointcut()")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();
        Report reports= (Report) args[0];
        //获取返回值
        AjaxResult proceed = (AjaxResult) joinPoint.proceed();
        if(reports.getDiagnosisStatus()!=2||!proceed.get("code").equals("200")){
            return joinPoint.proceed();
        }
        //获取用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //获取IP
        String ip = IpUtils.getIpAddr(ServletUtils.getRequest());
        //获取ip地址
        FwLog fwLog = new FwLog();
        fwLog.setUserName(loginUser.getUser().getPhonenumberAes());
        fwLog.setIpaddr(ip);
        try {
            String address = getAddress(ip);
            fwLog.setLoginLocation(address);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if(reports.getDiagnosisStatus()==2&&proceed.get("code").equals("200")){
            fwLog.setStatus("成功");
        }
        fwLog.setLogTime(new Date());
        fwLog.setFwStatus("2");
        fwLog.setFwNum(1);
        //获取代理方法的参数
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;
        FwLogAnnotation annotation = methodSignature.getMethod().getAnnotation(FwLogAnnotation.class);
        String value="";
        if(annotation!=null){
            //获取注解中的值
            value = annotation.value();
            fwLog.setMsg(value);
        }
        boolean save = fwLogService.save(fwLog);
        String name = joinPoint.getSignature().getName();
        if(save){
            logger.info("{日志}:用户"+loginUser.getUser().getUserId()+"，调用"+name+"方法，请求参数为"+args[0]+",返回值为"+proceed);
        }
        return joinPoint.proceed();
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



}
