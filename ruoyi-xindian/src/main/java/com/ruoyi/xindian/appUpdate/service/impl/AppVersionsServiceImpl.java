package com.ruoyi.xindian.appUpdate.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.appUpdate.domain.AppVersions;
import com.ruoyi.xindian.appUpdate.service.AppVersionsService;
import com.ruoyi.xindian.appUpdate.mapper.AppVersionsMapper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
* @author 13401
* @description 针对表【app_versions】的数据库操作Service实现
* @createDate 2023-10-20 17:09:31
*/
@Service
public class AppVersionsServiceImpl extends ServiceImpl<AppVersionsMapper, AppVersions>
    implements AppVersionsService{



    @Resource
    private AppVersionsMapper appVersionsMapper;

    @Override
    public AppVersions getAppVersionsCompare(AppVersions appVersions) {
        AppVersions versionsId = appVersionsMapper.selectOne(new QueryWrapper<AppVersions>().eq("type", appVersions.getType()).orderByDesc("versions_id").last("limit 1"));
        if (versionsId!=null && !appVersions.getVersionsNumber().equals(versionsId.getVersionsNumber())){
            return versionsId;
        }
        return null;
    }
}




