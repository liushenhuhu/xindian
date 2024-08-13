package com.ruoyi.xindian.wSuryvey.controller;

import java.util.LinkedList;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import avro.shaded.com.google.common.base.Preconditions;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.wSuryvey.convert.WSurveyDTOConverter;
import com.ruoyi.xindian.wSuryvey.domain.WSurveyDTO;
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
import com.ruoyi.xindian.wSuryvey.domain.WSurvey;
import com.ruoyi.xindian.wSuryvey.service.IWSurveyService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * wSuryveyController
 *
 * @author chenpeng
 * @date 2024-07-18
 */
@RestController
@RequestMapping("/survey")
public class WSurveyController extends BaseController {
    @Autowired
    private IWSurveyService wSurveyService;

    @Autowired
    private AesUtils aesUtils;

    /**
     * 查询wSuryvey列表
     */
//    @PreAuthorize("@ss.hasPermi('system:survey:list')")
    @GetMapping("/list")
    public TableDataInfo list(WSurvey wSurvey) throws Exception {
        if (wSurvey.getPatientPhone() != null)
            wSurvey.setPatientPhone(aesUtils.encrypt(wSurvey.getPatientPhone()));

        startPage();
        List<WSurvey> list = wSurveyService.selectWSurveyList(wSurvey);
        for (WSurvey survey : list) {
            survey.setPatientPhone(aesUtils.decrypt(survey.getPatientPhone()));
        }
        return getDataTable(list);
    }

    /**
     * 导出wSuryvey列表
     */
//    @PreAuthorize("@ss.hasPermi('system:survey:export')")
    @Log(title = "wSuryvey", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, WSurvey wSurvey) {
        List<WSurvey> list = wSurveyService.selectWSurveyList(wSurvey);
        ExcelUtil<WSurvey> util = new ExcelUtil<WSurvey>(WSurvey.class);
        util.exportExcel(response, list, "wSuryvey数据");
    }

    /**
     * 获取wSuryvey详细信息
     */
//    @PreAuthorize("@ss.hasPermi('system:survey:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) throws Exception {
        WSurvey wSurvey = wSurveyService.selectWSurveyById(id);
        if (wSurvey!=null){
            wSurvey.setPatientPhone(aesUtils.decrypt(wSurvey.getPatientPhone()));
        }
        return AjaxResult.success(wSurvey);
    }

    /**
     * 新增wSuryvey
     */
//    @PreAuthorize("@ss.hasPermi('system:survey:add')")
    @Log(title = "wSuryvey", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody WSurvey wSurvey) throws Exception {
        wSurvey.setPatientPhone(aesUtils.encrypt(wSurvey.getPatientPhone()));
        return toAjax(wSurveyService.insertWSurvey(wSurvey));
    }

    /**
     * 修改wSuryvey
     */
//    @PreAuthorize("@ss.hasPermi('system:survey:edit')")
    @Log(title = "wSuryvey", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody WSurvey wSurvey) throws Exception {
        return getAjaxResult(wSurvey);
    }

    /**
     * 删除wSuryvey
     */
    @PreAuthorize("@ss.hasPermi('system:survey:remove')")
    @Log(title = "wSuryvey", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(wSurveyService.deleteWSurveyByIds(ids));
    }




    @PostMapping("/addWSurveyHistory")
    public AjaxResult addWSurveyHistory(@RequestBody WSurvey wSurvey) throws Exception {
        return getAjaxResult(wSurvey);
    }

    private AjaxResult getAjaxResult(WSurvey wSurvey) throws Exception {
        Preconditions.checkNotNull(wSurvey.getPatientPhone(), "用户电话不能为空！");
        wSurvey.setPatientPhone(aesUtils.encrypt(wSurvey.getPatientPhone()));
        WSurvey wSurvey1 = new WSurvey();
        wSurvey1.setPatientPhone(wSurvey.getPatientPhone());
        List<WSurvey> wSurveyList = wSurveyService.selectWSurveyList(wSurvey1);
        if (!wSurveyList.isEmpty()){
            wSurvey.setId(wSurveyList.get(0).getId());
            return toAjax(wSurveyService.updateWSurvey(wSurvey));
        }else {
            return toAjax(wSurveyService.insertWSurvey(wSurvey));
        }
    }

    /**
     * 判断是否存在
     * @param patientPhone
     * @return
     * @throws Exception
     */
    @GetMapping("/getIsScreening")
    public AjaxResult getScreening(String patientPhone) throws Exception {
        Preconditions.checkNotNull(patientPhone, "用户电话不能为空！");
        patientPhone = aesUtils.encrypt(patientPhone);
        WSurvey wSurvey = new WSurvey();
        wSurvey.setPatientPhone(patientPhone);
        List<WSurvey> wSurveyList = wSurveyService.selectWSurveyList(wSurvey);
        return AjaxResult.success(!wSurveyList.isEmpty());
    }

    @GetMapping("/getScreeningByPhone")
    public AjaxResult getScreeningByPhone(String patientPhone) throws Exception {
        if (patientPhone == null) {
            return AjaxResult.error("用户电话不能为空！");
        }
        patientPhone = aesUtils.encrypt(patientPhone);
        WSurvey screening = wSurveyService.getScreening(patientPhone);
        if (screening!=null) {
            screening.setPatientPhone(aesUtils.decrypt(screening.getPatientPhone()));
        }
        return AjaxResult.success(screening);
    }

}
