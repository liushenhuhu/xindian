package com.ruoyi.xindian.appUpdate.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xindian.appUpdate.domain.AppVersion;
import com.ruoyi.xindian.appUpdate.domain.AppVersions;
import com.ruoyi.xindian.appUpdate.service.AppVersionService;
import com.ruoyi.xindian.appUpdate.service.AppVersionsService;
import org.apache.poi.util.StringUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 医生app更新
 */
@RestController
@RequestMapping("/app_update")
public class AppUpdateController {



    @Resource
    private AppVersionsService appVersionsService;


    @Resource
    private AppVersionService appVersionService;


    @GetMapping("/getAppVersions")
    public AjaxResult getAppVersions(AppVersion appVersion){
        if (StringUtils.isEmpty(appVersion.getVersionName())){
            return AjaxResult.error("请携带版本号");
        }
        if(appVersion.getType()==null){
            return AjaxResult.error("请携带类型");
        }
        AppVersion appVersionCompare = appVersionService.getAppVersionCompare(appVersion);
        if (appVersionCompare==null){
            return AjaxResult.success("您的版本为最新版");
        }
        return new  AjaxResult(201,"版本更新",appVersionCompare);
    }






}
