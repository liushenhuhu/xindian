package com.ruoyi.xindian.hospital.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.xindian.hospital.domain.UserMedicalCard;
import com.ruoyi.xindian.hospital.service.IUserMedicalCardService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户就诊卡信息表Controller
 * 
 * @author chenpeng
 * @date 2023-06-12
 */
@RestController
@RequestMapping("/hospital/hospitalMedicalCard")
public class UserMedicalCardController extends BaseController
{
    @Autowired
    private IUserMedicalCardService userMedicalCardService;

    /**
     * 查询用户就诊卡信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalMedicalCard:list')")
    @GetMapping("/list")
    public TableDataInfo list(UserMedicalCard userMedicalCard)
    {
        startPage();
        List<UserMedicalCard> list = userMedicalCardService.selectUserMedicalCardList(userMedicalCard);
        return getDataTable(list);
    }

    /**
     * 导出用户就诊卡信息表列表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalMedicalCard:export')")
    @Log(title = "用户就诊卡信息表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, UserMedicalCard userMedicalCard)
    {
        List<UserMedicalCard> list = userMedicalCardService.selectUserMedicalCardList(userMedicalCard);
        ExcelUtil<UserMedicalCard> util = new ExcelUtil<UserMedicalCard>(UserMedicalCard.class);
        util.exportExcel(response, list, "用户就诊卡信息表数据");
    }

    /**
     * 获取用户就诊卡信息表详细信息
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalMedicalCard:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(userMedicalCardService.selectUserMedicalCardById(id));
    }

    /**
     * 新增用户就诊卡信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalMedicalCard:add')")
    @Log(title = "用户就诊卡信息表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody UserMedicalCard userMedicalCard)
    {
        return toAjax(userMedicalCardService.insertUserMedicalCard(userMedicalCard));
    }

    /**
     * 修改用户就诊卡信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalMedicalCard:edit')")
    @Log(title = "用户就诊卡信息表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody UserMedicalCard userMedicalCard)
    {
        return toAjax(userMedicalCardService.updateUserMedicalCard(userMedicalCard));
    }

    /**
     * 删除用户就诊卡信息表
     */
    @PreAuthorize("@ss.hasPermi('hospital:hospitalMedicalCard:remove')")
    @Log(title = "用户就诊卡信息表", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(userMedicalCardService.deleteUserMedicalCardByIds(ids));
    }
}
