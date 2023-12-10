package com.ruoyi.xindian.verify.service;

import com.ruoyi.xindian.verify.domain.VerifyAuthority;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

/**
* @author 13401
* @description 针对表【verify_authority】的数据库操作Service
* @createDate 2023-12-05 09:26:59
*/
public interface VerifyAuthorityService extends IService<VerifyAuthority> {




    Boolean verifyAuthority(VerifyAuthority verifyAuthority, HttpServletRequest request) throws Exception;




}
