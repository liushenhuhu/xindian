package com.ruoyi.xindian.detection.controller;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.xml.crypto.Data;

import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.util.WxUtil;
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
import com.ruoyi.xindian.detection.domain.Detection;
import com.ruoyi.xindian.detection.service.IDetectionService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * detectionController
 * 
 * @author chenpeng
 * @date 2023-05-25
 */
@RestController
@RequestMapping("/detection/detection")
public class DetectionController extends BaseController
{
    @Autowired
    private IDetectionService detectionService;

    @Autowired
    private IPatientService patientService;

    @Resource
    private ISysUserService sysUserService;


    @Resource
    private AesUtils aesUtils;

    /**
     * 查询detection列表
     */
    @PreAuthorize("@ss.hasPermi('detection:detection:list')")
    @GetMapping("/list")
    public TableDataInfo list(Detection detection) throws Exception {
        if (detection.getPatientPhone()!=null&&!"".equals(detection.getPatientPhone())){
            detection.setPatientPhone(aesUtils.encrypt(detection.getPatientPhone()));
        }
        startPage();
        List<Detection> list = detectionService.selectDetectionList(detection);
        for (Detection c:list){
            if (c.getPatientPhone()!=null&&!"".equals(c.getPatientPhone())){
                c.setPatientPhone(aesUtils.decrypt(c.getPatientPhone()));
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出detection列表
     */
    @PreAuthorize("@ss.hasPermi('detection:detection:export')")
    @Log(title = "detection", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, Detection detection)
    {
        List<Detection> list = detectionService.selectDetectionList(detection);
        ExcelUtil<Detection> util = new ExcelUtil<Detection>(Detection.class);
        util.exportExcel(response, list, "detection数据");
    }

    /**
     * 获取detection详细信息
     */
    @PreAuthorize("@ss.hasPermi('detection:detection:query')")
    @GetMapping(value = "/{detectionId}")
    public AjaxResult getInfo(@PathVariable("detectionId") Long detectionId) throws Exception {
        Detection detection = detectionService.selectDetectionByDetectionId(detectionId);
        if (detection.getPatientPhone()!=null&&!"".equals(detection.getPatientPhone())){
            detection.setPatientPhone(aesUtils.decrypt(detection.getPatientPhone()));
        }
        return AjaxResult.success();
    }

    /**
     * 新增detection
     */
    @PreAuthorize("@ss.hasPermi('detection:detection:add')")
    @Log(title = "detection", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody Detection detection) throws Exception {
        if (detection.getPatientPhone()!=null&&!"".equals(detection.getPatientPhone())){
            detection.setPatientPhone(aesUtils.encrypt(detection.getPatientPhone()));
        }
        return toAjax(detectionService.insertDetection(detection));
    }

    /**
     * 修改detection
     */
    @PreAuthorize("@ss.hasPermi('detection:detection:edit')")
    @Log(title = "detection", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody Detection detection) throws Exception {
        if (detection.getPatientPhone()!=null&&!"".equals(detection.getPatientPhone())){
            detection.setPatientPhone(aesUtils.encrypt(detection.getPatientPhone()));
        }
        return toAjax(detectionService.updateDetection(detection));
    }

    /**
     * 删除detection
     */
    @PreAuthorize("@ss.hasPermi('detection:detection:remove')")
    @Log(title = "detection", businessType = BusinessType.DELETE)
	@DeleteMapping("/{detectionIds}")
    public AjaxResult remove(@PathVariable Long[] detectionIds)
    {
        return toAjax(detectionService.deleteDetectionByDetectionIds(detectionIds));
    }
    /**
     * 获取当前天次数
     */
    @GetMapping("/getDetectionNumByPhone/{patientPhone}")
    public AjaxResult getDetectionNumByPhone(@PathVariable String patientPhone) throws Exception {
        String encrypt = aesUtils.encrypt(patientPhone);
        HashMap<String, Object> params = new HashMap<>();
        Detection detection = new Detection();
        LocalDate now = LocalDate.now();
        LocalDateTime startOfDay = now.atStartOfDay();
        LocalDateTime endofDay = now.atTime(LocalTime.MAX);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String start = startOfDay.format(formatter);
        String end = endofDay.format(formatter);
        params.put("beginDetectionTime",start);
        params.put("endDetectionTime",end);
        detection.setPatientPhone(encrypt);
        detection.setParams(params);
        List<Detection> detections = detectionService.selectDetectionList(detection);
        SysUser sysUser = sysUserService.selectUserByPhone(encrypt);
        if(detections.size()<= sysUser.getDetectionNum()){
            Long d=sysUser.getDetectionNum()-detections.size();
            return AjaxResult.success(d);
        }
        else{
            return AjaxResult.error("今日次数已用用完");
        }



    }



}
