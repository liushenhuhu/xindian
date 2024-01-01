package com.ruoyi.xindian.appUpdate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.appUpdate.domain.AppVersion;
import com.ruoyi.xindian.appUpdate.service.AppVersionService;
import com.ruoyi.xindian.appUpdate.mapper.AppVersionMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 13401
* @description 针对表【app_version】的数据库操作Service实现
* @createDate 2023-12-31 21:59:11
*/
@Service
public class AppVersionServiceImpl extends ServiceImpl<AppVersionMapper, AppVersion>
    implements AppVersionService{

    @Resource
    private AppVersionMapper appVersionMapper;

    @Override
    public AppVersion getAppVersionCompare(AppVersion appVersion) {
        AppVersion versionsId = appVersionMapper.selectOne(new QueryWrapper<AppVersion>().eq("type", appVersion.getType()).orderByDesc("version_id").last("limit 1"));
        if (versionsId!=null && !appVersion.getVersionName().equals(versionsId.getVersionName())){
            return versionsId;
        }
        return null;
    }
}




