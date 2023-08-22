package com.ruoyi.xindian.callPhone;

import com.ruoyi.common.core.domain.AjaxResult;
import com.tencentcloudapi.ccc.v20200210.CccClient;
import com.tencentcloudapi.ccc.v20200210.models.CreateSDKLoginTokenRequest;
import com.tencentcloudapi.ccc.v20200210.models.CreateSDKLoginTokenResponse;
import com.tencentcloudapi.common.Credential;
import com.tencentcloudapi.common.exception.TencentCloudSDKException;
import com.tencentcloudapi.common.profile.ClientProfile;
import com.tencentcloudapi.common.profile.HttpProfile;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/callPhone")
public class CallPhoneController {

    @PostMapping("/web/getsdkURL")
    public AjaxResult test() throws TencentCloudSDKException {
        String SecretId="AKIDkPxNdqLo571viMq9GEFFAtjSyGNI5f6D";
        String SecretKey="41ulkfOGC3iCg18AA6waChz2sY7TVZXl";
        Credential cred = new Credential(SecretId, SecretKey);
        // 实例化一个http选项，可选的，没有特殊需求可以跳过
        HttpProfile httpProfile = new HttpProfile();
        httpProfile.setEndpoint("ccc.tencentcloudapi.com");
        // 实例化一个client选项，可选的，没有特殊需求可以跳过
        ClientProfile clientProfile = new ClientProfile();
        clientProfile.setHttpProfile(httpProfile);
        // 实例化要请求产品的client对象,clientProfile是可选的
        CccClient client = new CccClient(cred, "", clientProfile);
        // 实例化一个请求对象,每个接口都会对应一个request对象
        CreateSDKLoginTokenRequest req = new CreateSDKLoginTokenRequest();
        req.setSdkAppId(1400826444L);
        req.setSeatUserId("974023397@qq.com");
        // 返回的resp是一个CreateSDKLoginTokenResponse的实例，与请求对象对应
        CreateSDKLoginTokenResponse resp = client.CreateSDKLoginToken(req);
        // 输出json格式的字符串回包
        String sdkURL = resp.getSdkURL();

       return AjaxResult.success(sdkURL);
    }

}
