package com.ruoyi.web.controller.system;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.uuid.IdUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Api("生成验证码接口")
@Controller
public class GenerateSms {
    @Autowired
    private RedisCache redisCache;

    private Logger logger = LoggerFactory.getLogger(getClass());


    @ApiOperation("生成验证码")
    @ApiImplicitParam(name = "mobile", value = "手机号码", required = true, dataType = "String", paramType = "query")
    @PostMapping("/sms/code")
    @ResponseBody
    public AjaxResult sms(@RequestBody LoginBody loginBody) {

        String mobile = loginBody.getMobile();
        // 保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.SMS_CAPTCHA_CODE_KEY + uuid;

        String code = Integer.toString((int) (Math.random() * 9000) + 1000);
        Map<String, Object> map = new HashMap<>(16);
        map.put("mobile", mobile);
        map.put("code", code);

        AjaxResult codeResult = getCode(mobile, code);
        System.out.println(codeResult);
//        redisCache.setCacheObject(verifyKey, map, Constants.SMS_EXPIRATION, TimeUnit.MINUTES);
//        session.setAttribute("smsCode", map);

        logger.info(" 为 {} 设置短信验证码：{}", mobile, code);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("smsCode", code);
        return ajax;
    }

    @PostMapping("/sms/check")
    @ResponseBody
    public AjaxResult checkCode(@RequestBody LoginBody loginBody) {
        String mobile = loginBody.getMobile();
        String code = loginBody.getCode();
        String cacheObject = redisCache.getCacheObject(mobile);
        if (code != null && !"".equals(code) && code.equals(cacheObject)) {
            return AjaxResult.success("操作成功");
        } else {
            return AjaxResult.error("验证码信息错误");
        }
    }





    public AjaxResult getCode(String telephone, String code) {
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
