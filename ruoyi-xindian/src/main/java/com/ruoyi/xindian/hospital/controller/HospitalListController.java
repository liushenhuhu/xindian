package com.ruoyi.xindian.hospital.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.system.service.ISysDictDataService;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 医院Controller
 *
 * @author hanhan
 * @date 2022-08-20
 */
@RestController
@RequestMapping("/hospital/hospitalList")
public class HospitalListController extends BaseController
{
    @Autowired
    private IHospitalService hospitalService;


    @Autowired
    private TokenService tokenService;

    /**
     * 查询医院列表
     */
    @GetMapping("/list")
    public TableDataInfo list(Hospital hospital, HttpServletRequest request)
    {
        LoginUser loginUser = tokenService.getLoginUser(request);
        List<Hospital> list = null;
        //判断是否为管理员
        if (SecurityUtils.isAdmin(loginUser.getUser().getUserId()))
        {
            startPage();
           list = hospitalService.selectHospitalList(hospital);
        }
        else
        {
            startPage();
            list = hospitalService.selectUserId(loginUser.getUser().getUserId());
        }

        return getDataTable(list);
    }


    /**
     * 获取医院详细信息
     */
    @GetMapping(value = "/{hospitalId}")
    public AjaxResult getInfo(@PathVariable("hospitalId") Long hospitalId)
    {
        return AjaxResult.success(hospitalService.selectHospitalByHospitalId(hospitalId));
    }


}
