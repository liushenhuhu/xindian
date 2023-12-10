package com.ruoyi.xindian.verify.service.impl;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.verify.domain.VerifyAuthority;
import com.ruoyi.xindian.verify.service.VerifyAuthorityService;
import com.ruoyi.xindian.verify.mapper.VerifyAuthorityMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
* @author 13401
* @description 针对表【verify_authority】的数据库操作Service实现
* @createDate 2023-12-05 09:26:59
*/
@Service
public class VerifyAuthorityServiceImpl extends ServiceImpl<VerifyAuthorityMapper, VerifyAuthority>
    implements VerifyAuthorityService{



    @Resource
    private VerifyAuthorityMapper verifyAuthorityMapper;


    @Resource
    private AesUtils aesUtils;

    @Override
    public Boolean verifyAuthority(VerifyAuthority verifyAuthority, HttpServletRequest request) throws Exception {
        if (StringUtils.isEmpty(verifyAuthority.getAccountPwd())){
            throw new ServiceException("密码不能为空");
        }
        String s = SecureUtil.md5(verifyAuthority.getAccountPwd());

        VerifyAuthority verifyAuthority1 = verifyAuthorityMapper.selectOne(new QueryWrapper<VerifyAuthority>().eq("login_name", aesUtils.encrypt("admin")));

        if (verifyAuthority1==null){
            throw  new ServiceException("密码错误");
        }
        if (!verifyAuthority1.getAccountPwd().equals(s)){
            throw  new ServiceException("密码错误");
        }else {
            return true;
        }

    }
}




