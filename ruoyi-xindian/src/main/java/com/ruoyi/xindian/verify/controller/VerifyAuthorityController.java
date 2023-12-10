package com.ruoyi.xindian.verify.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.verify.domain.VerifyAuthority;
import com.ruoyi.xindian.verify.service.VerifyAuthorityService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/verify/authority")
public class VerifyAuthorityController {



    @Resource
    private VerifyAuthorityService verifyAuthorityService;



    @PostMapping("/verify")
    public AjaxResult verify(@RequestBody VerifyAuthority verifyAuthority, HttpServletRequest request) throws Exception {
        return AjaxResult.success(verifyAuthorityService.verifyAuthority(verifyAuthority, request));
    }


}
