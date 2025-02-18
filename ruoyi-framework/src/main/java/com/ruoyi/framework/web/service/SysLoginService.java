package com.ruoyi.framework.web.service;

import javax.annotation.Resource;

import com.alibaba.fastjson2.JSONObject;
import com.ruoyi.common.constant.UserConstants;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.RegisterBody;
import com.ruoyi.common.utils.*;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.framework.smsConfig.SmsCodeAuthenticationToken;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import com.ruoyi.common.constant.CacheConstants;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.exception.user.CaptchaException;
import com.ruoyi.common.exception.user.CaptchaExpireException;
import com.ruoyi.common.exception.user.UserPasswordNotMatchException;
import com.ruoyi.common.utils.ip.IpUtils;
import com.ruoyi.framework.manager.AsyncManager;
import com.ruoyi.framework.manager.factory.AsyncFactory;
import com.ruoyi.system.service.ISysConfigService;
import com.ruoyi.system.service.ISysUserService;

import java.util.List;
import java.util.Map;

/**
 * 登录校验方法
 *
 * @author ruoyi
 */
@Component
public class SysLoginService {

    // 是否允许账户多终端同时登录（true允许 false不允许）
    @Value("${token.soloLogin}")
    private boolean soloLogin;

    @Autowired
    private TokenService tokenService;

    @Resource
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysConfigService configService;

    @Autowired
    private SysRegisterService sysRegisterService;

    @Autowired
    private ISysRoleService sysRoleService;

    @Autowired
    private AesUtils aesUtils;

    /**
     * 微信登录方法
     */
    public String wxLogin(String decryptResult,String openId,String unionId) throws Exception {
        JSONObject jsonObject = JSONObject.parseObject(decryptResult);
        String nickName = "微信用户";
        String numberPhone = jsonObject.getString("phoneNumber");
        String encrypt = aesUtils.encrypt(numberPhone);
        SysUser wxUser = userService.selectUserByPhone(encrypt);
        //如果没有新建
        SysUser user = new SysUser();
        if(wxUser==null){
            user.setUserName(encrypt);
            user.setNickName(nickName);
            user.setPhonenumber(encrypt);
            user.setOpenId(openId);
            user.setCreateTime(DateUtils.getNowDate());
            user.setUnionId(unionId);
//            user.setPassword(SecurityUtils.encryptPassword("123456"));
            userService.insertUser(user);
            Long userId = userService.selectUserByUserName(encrypt).getUserId();
            userService.setUserRole(userId, 100L);
//            //绑定用户
//            userService.insertAppData(user);
//            //创建用户
//            userService.insertPatient(user);
//            //新增病历
//            userService.insertMedical(user);
        }else{
            user=wxUser;
//            Long userId = userService.selectUserByUserName(numberPhone).getUserId();
//            userService.setUserRole(userId, 100L);
            user.setOpenId(openId);
            user.setUnionId(unionId);
            user.setUpdateTime(DateUtils.getNowDate());
            userService.updateUserProfile(user);
        }
//        组装token
//        LoginUser loginUser = new LoginUser();
//        loginUser.setOpenId(openId);
//
//        loginUser.setUser(user);
//        loginUser.setUserId(user.getUserId());

        // 用户验证
        Authentication authentication = null;
        try {

            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new SmsCodeAuthenticationToken(encrypt));
        } catch (Exception e) {

            AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_FAIL, e.getMessage()));
            throw new ServiceException(e.getMessage());

        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());

        return tokenService.createToken(loginUser);
    }

    /**
     * 微信登录方法
     */
    public String wxLoginSkip(String numberPhone,String openId,String unionId) throws Exception {

        String nickName = "微信用户";
        String encrypt = aesUtils.encrypt(numberPhone);
        SysUser wxUser = userService.selectUserByPhone(encrypt);
        //如果没有新建
        SysUser user = new SysUser();
        if(wxUser==null){
            user.setUserName(encrypt);
            user.setNickName(nickName);
            user.setPhonenumber(encrypt);
            user.setOpenId(openId);
            user.setCreateTime(DateUtils.getNowDate());
            user.setUnionId(unionId);
//            user.setPassword(SecurityUtils.encryptPassword("123456"));
            userService.insertUser(user);
            Long userId = userService.selectUserByUserName(encrypt).getUserId();
            userService.setUserRole(userId, 100L);
//            //绑定用户
//            userService.insertAppData(user);
//            //创建用户
//            userService.insertPatient(user);
//            //新增病历
//            userService.insertMedical(user);
        }else{
            user=wxUser;
//            Long userId = userService.selectUserByUserName(numberPhone).getUserId();
//            userService.setUserRole(userId, 100L);
            user.setOpenId(openId);
            user.setUnionId(unionId);
            user.setUpdateTime(DateUtils.getNowDate());
            userService.updateUserProfile(user);
        }
//        组装token
//        LoginUser loginUser = new LoginUser();
//        loginUser.setOpenId(openId);
//
//        loginUser.setUser(user);
//        loginUser.setUserId(user.getUserId());

        // 用户验证
        Authentication authentication = null;
        try {

            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new SmsCodeAuthenticationToken(encrypt));
        } catch (Exception e) {

            AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_FAIL, e.getMessage()));
            throw new ServiceException(e.getMessage());

        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());

        return tokenService.createToken(loginUser);
    }

    public String appLoginSkip(String mobile, String code, String uuid) throws Exception {

        String nickName = "微信用户";
        checkSmsCode(mobile, code, uuid);
        String encrypt = aesUtils.encrypt(mobile);
        SysUser wxUser = userService.selectUserByPhone(encrypt);
        //如果没有新建
        SysUser user = new SysUser();
        if(wxUser==null){
            user.setUserName(encrypt);
            user.setNickName(nickName);
            user.setPhonenumber(encrypt);

            user.setCreateTime(DateUtils.getNowDate());

//            user.setPassword(SecurityUtils.encryptPassword("123456"));
            userService.insertUser(user);
            Long userId = userService.selectUserByUserName(encrypt).getUserId();
            userService.setUserRole(userId, 100L);
//            //绑定用户
//            userService.insertAppData(user);
//            //创建用户
//            userService.insertPatient(user);
//            //新增病历
//            userService.insertMedical(user);
        }else{
            user=wxUser;
//            Long userId = userService.selectUserByUserName(numberPhone).getUserId();
//            userService.setUserRole(userId, 100L);
            user.setUpdateTime(DateUtils.getNowDate());
            userService.updateUserProfile(user);
        }
//        组装token
//        LoginUser loginUser = new LoginUser();
//        loginUser.setOpenId(openId);
//
//        loginUser.setUser(user);
//        loginUser.setUserId(user.getUserId());

        // 用户验证
        Authentication authentication = null;
        try {

            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new SmsCodeAuthenticationToken(encrypt));
        } catch (Exception e) {

            AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_FAIL, e.getMessage()));
            throw new ServiceException(e.getMessage());

        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        recordLoginInfo(loginUser.getUserId());

        return tokenService.createToken(loginUser);
    }
    /**
     * 登录验证
     *
     * @param username 用户名
     * @param password 密码
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public String login(String username, String password, String code, String uuid) {
        boolean captchaOnOff = configService.selectCaptchaOnOff();
        // 验证码开关
//        if (captchaOnOff) {
//            validateCaptcha(username, code, uuid);
//        }
        // 用户验证
        Authentication authentication = null;
        try {
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username, password);
            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager.authenticate(authenticationToken);
        } catch (Exception e) {
            if (e instanceof BadCredentialsException) {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.password.not.match")));
                throw new UserPasswordNotMatchException();
            } else {
                AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, e.getMessage()));
                throw new ServiceException(e.getMessage());
            }
        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

//        if (!soloLogin) {
//            // 如果用户不允许多终端同时登录，清除缓存信息
//            String userIdKey = Constants.LOGIN_USERID_KEY + loginUser.getUser().getUserId();
//            String userKey = redisCache.getCacheObject(userIdKey);
//            if (StringUtils.isNotEmpty(userKey)) {
//                redisCache.deleteObject(userIdKey);
//                redisCache.deleteObject(userKey);
//            }
//        }

        recordLoginInfo(loginUser.getUserId());
        // 生成token
        return tokenService.createToken(loginUser);
    }

    /**
     * 校验验证码
     *
     * @param username 用户名
     * @param code     验证码
     * @param uuid     唯一标识
     * @return 结果
     */
    public void validateCaptcha(String username, String code, String uuid) {
        String verifyKey = CacheConstants.CAPTCHA_CODE_KEY + StringUtils.nvl(uuid, "");
        String captcha = redisCache.getCacheObject(verifyKey);
        redisCache.deleteObject(verifyKey);
        if (captcha == null) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.expire")));
            throw new CaptchaExpireException();
        }
        if (!code.equalsIgnoreCase(captcha)) {
            AsyncManager.me().execute(AsyncFactory.recordLogininfor(username, Constants.LOGIN_FAIL, MessageUtils.message("user.jcaptcha.error")));
            throw new CaptchaException();
        }
    }

    /**
     * 记录登录信息
     *
     * @param userId 用户ID
     */
    public void recordLoginInfo(Long userId) {
        SysUser sysUser = new SysUser();
        sysUser.setUserId(userId);
        sysUser.setLoginIp(IpUtils.getIpAddr(ServletUtils.getRequest()));
        sysUser.setLoginDate(DateUtils.getNowDate());
        userService.updateUserProfile(sysUser);
    }

    /**
     * 手机号登录验证
     *
     * @param mobile 手机号
     * @param code   验证码
     * @param uuid   唯一标识
     * @return 结果
     */

    public AjaxResult smsLogin(String mobile, String code, String uuid) throws Exception {

        String encrypt = aesUtils.encrypt(mobile);
        // 用户验证
        Authentication authentication = null;
        try {
            checkSmsCode(mobile, code, uuid);

            // 该方法会去调用UserDetailsServiceImpl.loadUserByUsername
            authentication = authenticationManager
                    .authenticate(new SmsCodeAuthenticationToken(encrypt));
        } catch (Exception e) {

            AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_FAIL, e.getMessage()));
            throw new ServiceException(e.getMessage());

        }
        AsyncManager.me().execute(AsyncFactory.recordLogininfor(encrypt, Constants.LOGIN_SUCCESS, MessageUtils.message("user.login.success")));
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();

//        if (!soloLogin) {
//            // 如果用户不允许多终端同时登录，清除缓存信息
//            String userIdKey = Constants.LOGIN_USERID_KEY + loginUser.getUser().getUserId();
//            String userKey = redisCache.getCacheObject(userIdKey);
//            if (StringUtils.isNotEmpty(userKey)) {
//                redisCache.deleteObject(userIdKey);
//                redisCache.deleteObject(userKey);
//            }
//        }

        AjaxResult ajax = AjaxResult.success();

        // 生成token
        String token = tokenService.createToken(loginUser);
        ajax.put(Constants.TOKEN, token);
        return ajax;
    }

    /**
     * 检查手机号登录
     *
     * @param
     */
    public void checkSmsCode(String mobile, String inputCode, String uuid) {

        String verifyKey = Constants.SMS_CAPTCHA_CODE_KEY + uuid;

        Map<String, Object> smsCode = redisCache.getCacheObject(verifyKey);
//        redisCache.deleteObject(verifyKey);
        if (StringUtils.isEmpty(inputCode)) {
            throw new BadCredentialsException("验证码不能为空");
        }

        if (smsCode == null) {
            throw new BadCredentialsException("验证码失效");
        }

        String applyMobile = (String) smsCode.get("mobile");
        int code = (int) smsCode.get("code");

        if (!applyMobile.equals(mobile)) {
            throw new BadCredentialsException("手机号码不一致");
        }
        if (code != Integer.parseInt(inputCode)) {
            throw new BadCredentialsException("验证码错误");
        }
    }
}
