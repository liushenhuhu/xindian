package com.ruoyi.xindian.hospital.controller;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.domain.HospitalSubscribe;
import com.ruoyi.xindian.hospital.service.HospitalSubscribeService;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;

import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 门诊预约医院Controller
 * 
 * @author ruoyi
 * @date 2023-10-20
 */
@RestController
@RequestMapping("/hospital/subscribe")
public class HospitalSubscribeController extends BaseController
{
    @Autowired
    private HospitalSubscribeService hospitalSubscribeService;


    @Resource
    private IHospitalService hospitalService;

    @Resource
    private TokenService tokenService;


    /**
     * 查询门诊预约医院列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:subscribe:list')")
    @GetMapping("/list")
    public TableDataInfo list(HospitalSubscribe hospitalSubscribe)
    {
        startPage();
        List<HospitalSubscribe> list = hospitalSubscribeService.selectHospitalSubscribeList(hospitalSubscribe);
        return getDataTable(list);
    }

    /**
     * 导出门诊预约医院列表
     */
//    @PreAuthorize("@ss.hasPermi('hospital:subscribe:export')")
    @Log(title = "门诊预约医院", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, HospitalSubscribe hospitalSubscribe)
    {
        List<HospitalSubscribe> list = hospitalSubscribeService.selectHospitalSubscribeList(hospitalSubscribe);
        ExcelUtil<HospitalSubscribe> util = new ExcelUtil<HospitalSubscribe>(HospitalSubscribe.class);
        util.exportExcel(response, list, "门诊预约医院数据");
    }

    /**
     * 获取门诊预约医院详细信息
     */
//    @PreAuthorize("@ss.hasPermi('hospital:subscribe:query')")
    @GetMapping(value = "/{subscribeId}")
    public AjaxResult getInfo(@PathVariable("subscribeId") Long subscribeId)
    {
        return AjaxResult.success(hospitalSubscribeService.selectHospitalSubscribeBySubscribeId(subscribeId));
    }

    /**
     * 新增门诊预约医院
     */
//    @PreAuthorize("@ss.hasPermi('hospital:subscribe:add')")
    @Log(title = "门诊预约医院", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody HospitalSubscribe hospitalSubscribe)
    {
        return toAjax(hospitalSubscribeService.insertHospitalSubscribe(hospitalSubscribe));
    }

    /**
     * 修改门诊预约医院
     */
//    @PreAuthorize("@ss.hasPermi('hospital:subscribe:edit')")
    @Log(title = "门诊预约医院", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody HospitalSubscribe hospitalSubscribe)
    {
        return toAjax(hospitalSubscribeService.updateHospitalSubscribe(hospitalSubscribe));
    }

    /**
     * 删除门诊预约医院
     */
//    @PreAuthorize("@ss.hasPermi('hospital:subscribe:remove')")
    @Log(title = "门诊预约医院", businessType = BusinessType.DELETE)
	@DeleteMapping("/{subscribeIds}")
    public AjaxResult remove(@PathVariable Long[] subscribeIds)
    {
        return toAjax(hospitalSubscribeService.deleteHospitalSubscribeBySubscribeIds(subscribeIds));
    }

    @GetMapping("/getHospitalIdFindList")
    public AjaxResult getHospitalIdFindList(String hospitalId){
        Long hospital = null;
        try {
            hospital =   Long.valueOf(hospitalId);
        }catch (Exception e){
            return AjaxResult.error("参数有问题，请稍后再试");
        }
       return AjaxResult.success( hospitalSubscribeService.getHospitalIdFindList(hospital));
    }

    @GetMapping("/getVisitHospitalList")
    public AjaxResult getVisitHospitalList(HttpServletRequest request){

        LoginUser loginUser = tokenService.getLoginUser(request);

        List<HospitalSubscribe> list = new ArrayList<>();
        HospitalSubscribe hospitalSubscribe = new HospitalSubscribe();
        if (SysUser.isAdmin(loginUser.getUserId())){
            list = hospitalSubscribeService.selectHospitalSubscribeList(hospitalSubscribe);
        }else {
            if (loginUser.getUser().getHospitalCode()!=null){
                Hospital hospital = hospitalService.selectHospitalByHospitalCode(loginUser.getUser().getHospitalCode());
                hospitalSubscribe.setHospitalId(hospital.getHospitalId());
                list = hospitalSubscribeService.selectHospitalSubscribeList(hospitalSubscribe);
            }
        }
         return AjaxResult.success(list);
    }
}
