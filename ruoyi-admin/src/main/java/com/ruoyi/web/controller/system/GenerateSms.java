package com.ruoyi.web.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.util.PhoneCheckUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api("生成验证码接口")
@Controller
public class GenerateSms {
    @Autowired
    private RedisCache redisCache;

    @Autowired
    private SysLoginService loginService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Resource
    private TokenService tokenService;

    @Resource
    private RedisTemplate<String, String> redisTemplate;

    @ApiOperation("生成验证码")
    @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "query")
    @PostMapping("/sms/code")
    @ResponseBody
    public AjaxResult sms(@RequestBody LoginBody loginBody) {
        return phoneCodeUserGet(loginBody);
    }


    @ApiOperation("生成验证码")
    @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "query")
    @PostMapping("/phoneCode/code")
    @ResponseBody
    public AjaxResult phoneCode(@RequestBody LoginBody loginBody, HttpServletRequest request) {
        LoginUser loginUser = tokenService.getLoginUser(request);
        loginBody.setUserId(loginUser.getUser().getUserId());
        if (Boolean.TRUE.equals(redisTemplate.hasKey("userPhoneTimeCode:" + loginUser.getUser().getUserId()))){
            return AjaxResult.error("请勿重复请求验证码");
        }
        return phoneCodeUserGet(loginBody);
    }


    private AjaxResult phoneCodeUserGet(LoginBody loginBody){
        String mobile = loginBody.getMobile();
        if (!PhoneCheckUtils.isPhoneLegal(mobile)) {
            return AjaxResult.error("手机号格式不对，请重新输入");
        }
        if (loginBody.getUserId()!=null){
            redisTemplate.opsForValue().set("userPhoneTimeOne:" + loginBody.getUserId(),mobile,50, TimeUnit.SECONDS);
        }


        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.SMS_CAPTCHA_CODE_KEY + uuid;

        int code = (int) Math.ceil(Math.random() * 9000 + 1000);
//        String code = Integer.toString((int) (Math.random() * 9000) + 1000);
        Map<String, Object> map = new HashMap<>(16);
        map.put("mobile", mobile);
        map.put("code", code);

        AjaxResult codeResult = getCode(mobile, code);
        System.out.println(codeResult);
        redisCache.setCacheObject(verifyKey, map, Constants.SMS_EXPIRATION, TimeUnit.MINUTES);
//        session.setAttribute("smsCode", map);

        logger.info(" 为 {} 设置短信验证码：{}", mobile, code);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
//        ajax.put("smsCode", code);
//        ajax.put("codeResult", codeResult);
        return ajax;
    }


    @PostMapping("/sms/check")
    @ResponseBody
    public AjaxResult checkCode(@RequestBody LoginBody loginBody) {
        return sure(loginBody);
    }

    private AjaxResult sure(LoginBody loginBody){
        String mobile = loginBody.getMobile();
        String inputCode = loginBody.getSmsCode();
        String uuid = loginBody.getUuid();
        String verifyKey = Constants.SMS_CAPTCHA_CODE_KEY + uuid;

        Map<String, Object> smsCode = redisCache.getCacheObject(verifyKey);
//        redisCache.deleteObject(verifyKey);
        if (StringUtils.isEmpty(inputCode)) {
            return AjaxResult.error("验证码不能为空");
        }

        if (smsCode == null) {
            return AjaxResult.error("验证码失效");
        }

        String applyMobile = (String) smsCode.get("mobile");
        int code = (int) smsCode.get("code");

        if (!applyMobile.equals(mobile)) {
            return AjaxResult.error("手机号码不一致");
        }
        if (code != Integer.parseInt(inputCode)) {
            return AjaxResult.error("验证码错误");
        } else {
            return AjaxResult.success("验证码正确");
        }
    }

    @PostMapping("/phoneCode/check")
    @ResponseBody
    public AjaxResult phoneCodeCheck(@RequestBody LoginBody loginBody) {
        return sure(loginBody);
    }


    public AjaxResult getCode(String telephone, int code) {
////        String host = "http://smsyun.market.alicloudapi.com";
//        String host = "http://dfsmsv2.market.alicloudapi.com";
////        String path = "/sms/sms01";
//        String path = "/data/send_sms_v2";
//        String method = "POST";
////        String appcode = "37a5b008bed84153ad0691d0c33fe42a";
//        String appcode = "37a5b008bed84153ad0691d0c33fe42a";
//        Map<String, String> headers = new HashMap<String, String>();
//        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
//        headers.put("Authorization", "APPCODE " + appcode);
//        Map<String, String> querys = new HashMap<String, String>();
////        querys.put("content", "【迈雅】您正在登录验证，验证码" + code + "，切勿将验证码泄露于他人，本条验证码有效期15分钟。");
//        querys.put("content", "【迈雅】您正在登录验证，验证码" + code + "，切勿将验证码泄露于他人，本条验证码有效期15分钟。");
//        querys.put("mobile", telephone);
//        String bodys = "";
//        redisCache.setCacheObject(telephone, code, 15, TimeUnit.MINUTES);
//        try {
//            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
//            System.out.println(response.toString()); //获取response的body //
//            System.out.println(EntityUtils.toString(response.getEntity()));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        String host = "https://dfsmsv2.market.alicloudapi.com";
        String path = "/data/send_sms_v2";
        String method = "POST";
        String appcode = "37a5b008bed84153ad0691d0c33fe42a";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        bodys.put("content", "code:"+code);
        bodys.put("phone_number", telephone);
        bodys.put("template_id", "CST_vrgpu10213");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success(code);
    }

    /*
    * 向用户推送预警短信
    * */
    @PostMapping("/sms/warn")
    @ResponseBody
    public AjaxResult warnCode(@RequestBody LoginBody loginBody) {
        String telephone = loginBody.getMobile();
        String code = loginBody.getSmsCode();
        String host = "http://smsyun.market.alicloudapi.com";
        String path = "/sms/sms01";
        String method = "POST";
        String appcode = "37a5b008bed84153ad0691d0c33fe42a";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("content", "【迈雅】您正在登录验证，验证码" + code + "，切勿将验证码泄露于他人，本条验证码有效期15分钟。");
        querys.put("mobile", telephone);
        String bodys = "";
        redisCache.setCacheObject(telephone, code, 15, TimeUnit.MINUTES);
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString()); //获取response的body //
            System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return AjaxResult.success(code);
    }

}
