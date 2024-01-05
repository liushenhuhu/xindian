package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.itextpdf.text.pdf.codec.Base64;
import com.ruoyi.common.config.WxAppConfig;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysMenu;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginBody;
import com.ruoyi.common.core.domain.model.WxLoginBody;
import com.ruoyi.common.core.domain.model.WxOpenId;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.SysLoginService;
import com.ruoyi.framework.web.service.SysPermissionService;
import com.ruoyi.system.service.ISysMenuService;
import com.ruoyi.xindian.appData.domain.AppData;
import com.ruoyi.xindian.appData.service.IAppDataService;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * 登录验证
 *
 * @author ruoyi
 */
@RestController
public class SysLoginController {

    private static final Logger logger = LoggerFactory.getLogger(SysLoginController.class);
    @Autowired
    private SysLoginService loginService;

    @Autowired
    private ISysMenuService menuService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    private IAppDataService appDataService;

    @Autowired
    private IDoctorService doctorService;
    @Autowired
    private WxAppConfig wxAppConfig;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @Autowired
    private AesUtils aesUtils;

    /**
     * 微信登录方法
     *
     * @param wxloginBody 登录信息
     * @return 结果
     */
    @PostMapping("/wxLogin")
    public AjaxResult wxLogin(@RequestBody WxLoginBody wxloginBody) throws Exception {

        logger.info("登录参数："+ JSON.toJSONString(wxloginBody));
        String code=wxloginBody.getCode();
        String encryptedIv = wxloginBody.getEncryptedIv();
        String getEncryptedData = wxloginBody.getEncryptedData();

        //向微信服务器发送请求获取用户信息
        String url="https://api.weixin.qq.com/sns/jscode2session?appid="+wxAppConfig.getAppId()+"&secret="+wxAppConfig.getAppSecret()+"&js_code="+code+"&grant_type=authorization_code";
        String res=restTemplate.getForObject(url,String.class);
//        String tok=loginService.wxLogin(res,"124");
        JSONObject jsonObject = JSONObject.parseObject(res);
        logger.info("微信返回参数："+ jsonObject);

        //获取session_key和openid
        String session_key = jsonObject.getString("session_key");
        String openid = jsonObject.getString("openid");
        String unionid = jsonObject.getString("unionid");

        //解密
        String decryptResult="";
        try {
            decryptResult=decrypt(session_key,encryptedIv,getEncryptedData);
        } catch (Exception e){
            e.printStackTrace();
            return AjaxResult.error("数据解析失败");
        }
        if(StringUtils.hasText(decryptResult)){
            String token=loginService.wxLogin(decryptResult,openid,unionid);
            JSONObject json = JSONObject.parseObject(decryptResult);
            String numberPhone = json.getString("phoneNumber");
            AjaxResult result = AjaxResult.success();
            result.put(Constants.TOKEN,token);
            result.put("phone",numberPhone);
            String encrypt = aesUtils.encrypt(numberPhone);
            AppData appData = appDataService.selectAppDataByPatientPhone(encrypt);
            if (null == appData) {
                result.put("BindingState", false);
            } else {
                result.put("BindingState", true);
            }
            Doctor doctor = doctorService.selectDoctorByDoctorPhone(encrypt);
            if(null == doctor){
                result.put("IsDoctor",false);
            } else {
                result.put("IsDoctor",true);
            }
            return result;
        }else{
            return AjaxResult.error("微信登录失败");
        }
    }

    /**
     * AES解密
     */
    private String decrypt(String sessionKey,String encryptedIv,String encryptData) throws
            NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException,
            InvalidKeyException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException{
        logger.info("start-------Base64.decode");
        byte[] key= Base64.decode(sessionKey);
        byte[] iv= Base64.decode(encryptedIv);
        byte[] encData= Base64.decode(encryptData);
        logger.info("end-------Base64.decode");
        //如果秘钥不够16位，就补充
        int base=16;
        if(key.length%base!=0){
            int groups= key.length/base+ 1;
            byte[] bytes = new byte[groups * base];
            Arrays.fill(bytes,(byte) 0);
            System.arraycopy(key,0,bytes,0,key.length);
            key=bytes;
        }
        if(iv.length%base!=0){
            int groups= iv.length/base+ 1;
            byte[] bytes = new byte[groups * base];
            Arrays.fill(bytes,(byte) 0);
            System.arraycopy(iv,0,bytes,0,iv.length);
            iv=bytes;
        }
        IvParameterSpec ivSpec = new IvParameterSpec(iv);
        String resultStr=null;
        try{
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            cipher.init(Cipher.DECRYPT_MODE,keySpec,ivSpec);
            resultStr = new String(cipher.doFinal(encData), "UTF-8");
        }catch (Exception e){
            logger.info("解析错误！！！");
            e.printStackTrace();
        }
        return resultStr;
    }



    /**
     * 登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) throws Exception {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌
        String encrypt = aesUtils.encrypt(loginBody.getUsername());
        String token = loginService.login(encrypt, loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);

        AppData appData = new AppData();
        appData.setUserName(encrypt);
        List<AppData> appDataList = appDataService.selectAppDataList(appData);
        if (null == appDataList || appDataList.size() == 0) {
            ajax.put("BindingState", false);
        } else {
            ajax.put("BindingState", true);
        }
        return ajax;
    }

    /**
     * 医生登录登录方法
     *
     * @param loginBody 登录信息
     * @return 结果
     */
    @PostMapping("/loginDoc")
    public AjaxResult loginDoc(@RequestBody LoginBody loginBody) throws Exception {
        AjaxResult ajax = AjaxResult.success();
        // 生成令牌

        if (!StringUtils.hasText(loginBody.getUsername())){
            return AjaxResult.error("用户名为空");
        }
        String encrypt = aesUtils.encrypt(loginBody.getUsername());
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(encrypt);
        if (doctor==null){
            return AjaxResult.error("非医生账号，不能登录");
        }
        if (doctor.getAccountStatus().equals("1")){
            return AjaxResult.error("医生账号已被停用，请联系管理员解封");
        }
        String token = loginService.login(encrypt, loginBody.getPassword(), loginBody.getCode(),
                loginBody.getUuid());
        ajax.put(Constants.TOKEN, token);
        ajax.put("doctor", doctor);
        AppData appData = new AppData();
        appData.setUserName(encrypt);
        List<AppData> appDataList = appDataService.selectAppDataList(appData);
        if (null == appDataList || appDataList.size() == 0) {
            ajax.put("BindingState", false);
        } else {
            ajax.put("BindingState", true);
        }
        return ajax;
    }

    /**
     * 手机号登录方法
     *
     * @param
     * @return 结果
     */
    @ApiOperation("手机号登录")
    @ApiImplicitParam(name = "loginBody", value = "登录信息", dataType = "LoginBody")
    @PostMapping("/sms/login")
    public AjaxResult smsLogin(@RequestBody LoginBody loginBody) throws Exception {
        String mobile = loginBody.getMobile();
        String smsCode = loginBody.getSmsCode();
        String uuid = loginBody.getUuid();
        AjaxResult ajax = loginService.smsLogin(mobile, smsCode, uuid);

        AppData appData = new AppData();
        appData.setUserName(aesUtils.encrypt(loginBody.getMobile()));
        List<AppData> appDataList = appDataService.selectAppDataList(appData);
        if (null == appDataList || appDataList.size() == 0) {
            ajax.put("BindingState", false);
        } else {
            ajax.put("BindingState", true);
        }
        return ajax;
    }


    /**
     * 获取用户信息
     *
     * @return 用户信息
     */
    @GetMapping("getInfo")
    public AjaxResult getInfo() throws Exception {
        SysUser user = SecurityUtils.getLoginUser().getUser();
        if (user.getUserName()!=null&&!"".equals(user.getUserName())){
            user.setUserName(aesUtils.decrypt(user.getUserName()));
        }
        if (user.getPhonenumber()!=null&&!"".equals(user.getPhonenumber())){
            user.setPhonenumber(aesUtils.decrypt(user.getPhonenumber()));
        }
        // 角色集合
        Set<String> roles = permissionService.getRolePermission(user);
        // 权限集合
        Set<String> permissions = permissionService.getMenuPermission(user);
        AjaxResult ajax = AjaxResult.success();
        ajax.put("user", user);
        ajax.put("roles", roles);
        ajax.put("permissions", permissions);
        return ajax;
    }

    /**
     * 获取路由信息
     *
     * @return 路由信息
     */
    @GetMapping("getRouters")
    public AjaxResult getRouters() {
        Long userId = SecurityUtils.getUserId();
        List<SysMenu> menus = menuService.selectMenuTreeByUserId(userId);
        return AjaxResult.success(menuService.buildMenus(menus));
    }

    @PostMapping("/wxOpenId")
    public AjaxResult wxOpenId(@RequestBody WxOpenId wxOpenId) {
        String openId = wxOpenId.getOpenId();
        String doctorPhone = wxOpenId.getDoctorPhone();
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(doctorPhone);
        if(doctor==null) return AjaxResult.error("该账户医生不存在！请联系管理员添加权限！");
        doctor.setOpenId(openId);
        return AjaxResult.success(doctorService.updateDoctor(doctor));
    }

    @PostMapping("/wxGZHOpenId")
    public AjaxResult wxGZHOpenId(@RequestBody WxOpenId wxOpenId) {
        String openId = wxOpenId.getOpenId();
        String doctorPhone = wxOpenId.getDoctorPhone();
        Doctor doctor = doctorService.selectDoctorByDoctorPhone(doctorPhone);
        if(doctor==null) return AjaxResult.success();
        doctor.setOpenId(openId);
        return AjaxResult.success(doctorService.updateDoctor(doctor));
    }
}
