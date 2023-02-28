package com.ruoyi.xindian.interfaceList;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;


/**
 * 患者管理Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping()
public class InterfaceListController extends BaseController {

    @Autowired
    private ISysUserService sysUserService;

    @GetMapping("/getOpenId/{js_code}")
    public String getOpenId(@PathVariable("js_code") String js_code) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> forEntity = restTemplate.getForEntity("https://api.weixin.qq.com/sns/jscode2session?appid=wx331beedb5dbfe460&secret=3c031e8a5c6eef5bfdd2be1d4820fcc2&grant_type=authorization_code&js_code=" + js_code, String.class);
        String entityBody = forEntity.getBody();
        JSONObject jsonObject = JSONObject.parseObject(entityBody);
        String openid = jsonObject.getString("openid");
        return openid;
    }

    @GetMapping("/selUser/{phone}")
    public boolean selUser(@PathVariable("phone") String phone) {
        boolean flag;
        SysUser sysUser = sysUserService.selectUserByPhone(phone);
        flag = null != sysUser.getOpenId();
        return flag;
    }

    @GetMapping("/setOpenId/{openId}/{phone}")
    public AjaxResult selUser(@PathVariable("openId") String openId, @PathVariable("phone") String phone) {
        int i = sysUserService.updateUserOpenId(openId, phone);
        return toAjax(i);
    }


    public String splitData(String str, String strStart, String strEnd) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
        return tempStr;
    }

    public static String removeDoubleQuotes(String result) {
        //去掉" "号
        return result.replace("\"", "");
    }

}
