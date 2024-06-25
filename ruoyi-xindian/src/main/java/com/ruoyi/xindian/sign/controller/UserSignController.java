package com.ruoyi.xindian.sign.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;
import com.ruoyi.common.utils.sign.AesUtils;
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
import com.ruoyi.xindian.sign.domain.UserSign;
import com.ruoyi.xindian.sign.service.IUserSignService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * signController
 *
 * @author chenpeng
 * @date 2024-06-25
 */
@RestController
@RequestMapping("/sign/sign")
public class UserSignController extends BaseController {
    @Autowired
    private IUserSignService userSignService;

    @Autowired
    private AesUtils aesUtils;

    /**
     * 查询sign列表
     */
    @GetMapping("/list")
    public TableDataInfo list(UserSign userSign) {
        startPage();
        List<UserSign> list = userSignService.selectUserSignList(userSign);
        return getDataTable(list);
    }

    /**
     * 导出sign列表
     */
    @Log(title = "sign", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserSign userSign) {
        List<UserSign> list = userSignService.selectUserSignList(userSign);
        ExcelUtil<UserSign> util = new ExcelUtil<UserSign>(UserSign.class);
        util.exportExcel(response, list, "sign数据");
    }

    /**
     * 获取sign详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id) {
        return AjaxResult.success(userSignService.selectUserSignById(id));
    }

    /**
     * 新增sign
     */
    @Log(title = "sign", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserSign userSign) {
        return toAjax(userSignService.insertUserSign(userSign));
    }

    /**
     * 修改sign
     */
    @Log(title = "sign", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserSign userSign) throws Exception {
        String phone="";
        if (userSign != null && !userSign.getPatientPhone().isEmpty()) {
            phone = aesUtils.encrypt(userSign.getPatientPhone());
        }else{
            return AjaxResult.error("手机号不完整，请稍后在试");
        }
        UserSign sign = userSignService.selectUserSignByPhone(phone);
        if (sign == null) {
            sign = new UserSign();
            sign.setLastSign(new Date());
            sign.setSeriesDays(1L);
            sign.setContinuityDays(1L);
            userSignService.insertUserSign(sign);
        } else{
            sign.setLastSign(new Date());
            sign.setSeriesDays(sign.getSeriesDays() + 1);
            sign.setContinuityDays(sign.getContinuityDays() + 1);
            userSignService.updateUserSign(sign);
        }
        return toAjax(1);
    }
    /**
     * 删除sign
     */
    @Log(title = "sign", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        return toAjax(userSignService.deleteUserSignByIds(ids));
    }

    @GetMapping()
    public AjaxResult getSignByPhone(String patientPhone) throws Exception {
        if (patientPhone != null && !patientPhone.isEmpty()) {
            patientPhone = aesUtils.encrypt(patientPhone);
        }else{
            return AjaxResult.error("手机号不完整，请稍后在试");
        }
        UserSign userSign = userSignService.selectUserSignByPhone(patientPhone);
        if(userSign!=null)
            userSign.setPatientPhone("");
        else{
            userSign = new UserSign();
            userSign.setSeriesDays(0L);
            userSign.setContinuityDays(0L);
        }
        return AjaxResult.success(userSign);
    }

}
