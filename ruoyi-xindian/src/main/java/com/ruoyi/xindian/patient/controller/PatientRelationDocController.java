package com.ruoyi.xindian.patient.controller;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.xindian.patient.domain.PatientRelationDoc;
import com.ruoyi.xindian.patient.service.PatientRelationDocService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * 医患关系绑定
 */
@RestController
@RequestMapping("/relation_doc")
public class PatientRelationDocController {




    @Resource
    private PatientRelationDocService patientRelationDocService;


    /**
     * 查询记录
     * @param patientRelationDoc
     * @return
     * @throws Exception
     */
    @GetMapping("/getRelationDocList")
    public AjaxResult getRelationDocList(PatientRelationDoc patientRelationDoc) throws Exception {
        return AjaxResult.success(patientRelationDocService.selectAllList(patientRelationDoc));
    }


    /**
     * 删除关系
     * @param patientRelationDoc
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delRelationDoc")
    public AjaxResult delRelationDoc(PatientRelationDoc patientRelationDoc) throws Exception {
        return AjaxResult.success(patientRelationDocService.delPatientRelationDoc(patientRelationDoc));
    }


    /**
     * 新增关系
     * @param patientRelationDoc
     * @return
     * @throws Exception
     */
    @PostMapping("/addRelationDoc")
    public AjaxResult addRelationDoc(@RequestBody PatientRelationDoc patientRelationDoc) throws Exception {
        return AjaxResult.success(patientRelationDocService.insertPatientRelationDoc(patientRelationDoc));
    }

    /**
     * 修改关系
     * @param patientRelationDoc
     * @return
     */
    @PostMapping("/updateRelationDoc")
    public AjaxResult updateRelationDoc(@RequestBody PatientRelationDoc patientRelationDoc){
        return AjaxResult.success(patientRelationDocService.updatePatientRelation(patientRelationDoc));
    }

}
