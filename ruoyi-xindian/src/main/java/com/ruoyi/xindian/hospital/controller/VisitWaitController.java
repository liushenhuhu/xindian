package com.ruoyi.xindian.hospital.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.domain.VisitWait;
import com.ruoyi.xindian.hospital.service.IHospitalService;
import com.ruoyi.xindian.hospital.service.VisitWaitService;
import com.ruoyi.xindian.util.DateUtil;
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
 * 门诊候诊Controller
 * 
 * @author ruoyi
 * @date 2023-10-27
 */
@RestController
@RequestMapping("/hospital/wait")
public class VisitWaitController extends BaseController
{

    @Autowired
    private VisitWaitService visitWaitService;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private IHospitalService hospitalService;

    @Resource
    private TokenService tokenService;

    /**
     * 查询门诊候诊列表
     */
    @GetMapping("/list")
    public TableDataInfo list(VisitWait visitWait, HttpServletRequest request) throws Exception {
        LoginUser loginUser = tokenService.getLoginUser(request);
        VisitWaitEncrypt(visitWait);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        if (StringUtils.isNotEmpty(visitWait.getVisitDate())){
           try {
               visitWait.setPeriodDay(simpleDateFormat.parse(visitWait.getVisitDate()));
           }catch (Exception e){
               visitWait.setPeriodDay(simpleDateFormat.parse(simpleDateFormat.format(new Date())));
           }
        }
        List<VisitWait> waitList = new ArrayList<>();
        if (SysUser.isAdmin(loginUser.getUser().getUserId())){
            startPage();
            waitList = visitWaitService.selectVisitWaitList(visitWait);
        }else {

            if (loginUser.getUser().getHospitalCode()!=null){

                if (loginUser.getUser().getDeptId()!=null&&loginUser.getUser().getDeptId()==200&&loginUser.getUser().getRoleId()==109){
                    Hospital hospital = hospitalService.selectHospitalByHospitalCode(loginUser.getUser().getHospitalCode());
                    visitWait.setHospitalId(hospital.getHospitalId());
                    startPage();
                    waitList = visitWaitService.selectVisitWaitList(visitWait);
                }else {
                    visitWait.setDoctorPhone(loginUser.getUser().getUserName());
                    startPage();
                    waitList = visitWaitService.selectVisitWaitList(visitWait);
                }

            }
        }

        for (VisitWait visitWait1: waitList){
            if (visitWait1.getBirthBay()!=null){
                visitWait1.setPatientAge(DateUtil.getAge(visitWait1.getBirthBay()));
            }
            VisitWaitDecrypt(visitWait1);
        }
        return getDataTable(waitList);
    }


    @GetMapping("/isWait/{waitId}")
    public AjaxResult isWait(@PathVariable("waitId") String id ){

        VisitWait visitWait = new VisitWait();
        visitWait.setAppointmentId(id);
        List<VisitWait> waitList = visitWaitService.selectVisitWaitList(visitWait);
        if (waitList!=null&&waitList.size()>0){
            return AjaxResult.success(true);
        }
        return AjaxResult.success(false);
    }


    private void VisitWaitEncrypt(VisitWait visitWait) throws Exception {
        if (visitWait!=null){
            if (StringUtils.isNotEmpty(visitWait.getDoctorPhone())){
                visitWait.setDoctorPhone(aesUtils.encrypt(visitWait.getDoctorPhone()));
            }
            if (StringUtils.isNotEmpty(visitWait.getDoctorName())){
                visitWait.setDoctorName(aesUtils.encrypt(visitWait.getDoctorName()));
            }
            if (StringUtils.isNotEmpty(visitWait.getPatientName())){
                visitWait.setPatientName(aesUtils.encrypt(visitWait.getPatientName()));
            }
            if (StringUtils.isNotEmpty(visitWait.getPatientPhone())){
                visitWait.setPatientName(aesUtils.encrypt(visitWait.getPatientPhone()));
            }

        }
    }

    private void VisitWaitDecrypt(VisitWait visitWait) throws Exception {
        if (visitWait!=null){
            if (StringUtils.isNotEmpty(visitWait.getDoctorPhone())){
                visitWait.setDoctorPhone(aesUtils.decrypt(visitWait.getDoctorPhone()));
            }
            if (StringUtils.isNotEmpty(visitWait.getDoctorName())){
                visitWait.setDoctorName(aesUtils.decrypt(visitWait.getDoctorName()));
            }
            if (StringUtils.isNotEmpty(visitWait.getPatientName())){
                visitWait.setPatientName(aesUtils.decrypt(visitWait.getPatientName()));
            }
            if (StringUtils.isNotEmpty(visitWait.getPatientPhone())){
                visitWait.setPatientPhone(aesUtils.decrypt(visitWait.getPatientPhone()));
            }

        }
    }
    /**
     * 导出门诊候诊列表
     */
    @Log(title = "门诊候诊", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, VisitWait visitWait)
    {
        List<VisitWait> list = visitWaitService.selectVisitWaitList(visitWait);
        ExcelUtil<VisitWait> util = new ExcelUtil<VisitWait>(VisitWait.class);
        util.exportExcel(response, list, "门诊候诊数据");
    }

    /**
     * 获取门诊候诊详细信息
     */
    @GetMapping(value = "/{waitId}")
    public AjaxResult getInfo(@PathVariable("waitId") Long waitId)
    {
        return AjaxResult.success(visitWaitService.selectVisitWaitByWaitId(waitId));
    }

    /**
     * 新增门诊候诊
     */
    @PreAuthorize("@ss.hasPermi('hospital:wait:add')")
    @Log(title = "门诊候诊", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody VisitWait visitWait)
    {
        return toAjax(visitWaitService.insertVisitWait(visitWait));
    }

    /**
     * 修改门诊候诊
     */
    @PreAuthorize("@ss.hasPermi('hospital:wait:edit')")
    @Log(title = "门诊候诊", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody VisitWait visitWait)
    {
        return toAjax(visitWaitService.updateVisitWait(visitWait));
    }

    /**
     * 删除门诊候诊
     */
    @PreAuthorize("@ss.hasPermi('hospital:wait:remove')")
    @Log(title = "门诊候诊", businessType = BusinessType.DELETE)
	@DeleteMapping("/{waitIds}")
    public AjaxResult remove(@PathVariable Long[] waitIds)
    {
        return toAjax(visitWaitService.deleteVisitWaitByWaitIds(waitIds));
    }


    @GetMapping("/accomplishStatus")
    public AjaxResult accomplishStatus(VisitWait visitWait){

        int accomplishStatus = visitWaitService.getAccomplishStatus(visitWait);
        return AjaxResult.success(accomplishStatus>0);
    }
}