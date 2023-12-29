package com.ruoyi.xindian.appUpdate.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.xindian.appUpdate.domain.AppVersions;
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



    @GetMapping("/getAppVersions")
    public AjaxResult getAppVersions(AppVersions  appVersions){
        if (StringUtils.isEmpty(appVersions.getVersionsNumber())){
            return AjaxResult.error("请携带版本号");
        }
        if(appVersions.getType()==null){
            return AjaxResult.error("请携带类型");
        }
        AppVersions appVersionsCompare = appVersionsService.getAppVersionsCompare(appVersions);
        if (appVersionsCompare==null){
            return AjaxResult.success("您的版本为最新版");
        }
        return new AjaxResult(201, appVersionsCompare.getUrl());
    }






}
