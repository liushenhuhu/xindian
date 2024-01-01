package com.ruoyi.xindian.appUpdate.service;

import com.ruoyi.xindian.appUpdate.domain.AppVersion;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【app_version】的数据库操作Service
* @createDate 2023-12-31 21:59:11
*/
public interface AppVersionService extends IService<AppVersion> {

    AppVersion getAppVersionCompare(AppVersion appVersion);
}
