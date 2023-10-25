package com.ruoyi.xindian.appUpdate.service;

import com.ruoyi.xindian.appUpdate.domain.AppVersions;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【app_versions】的数据库操作Service
* @createDate 2023-10-20 17:09:31
*/
public interface AppVersionsService extends IService<AppVersions> {



    AppVersions getAppVersionsCompare(AppVersions appVersions);


}
