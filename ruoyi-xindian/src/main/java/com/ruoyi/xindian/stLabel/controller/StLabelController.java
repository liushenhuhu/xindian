package com.ruoyi.xindian.stLabel.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import cn.hutool.core.util.ArrayUtil;
import com.mchange.lang.FloatUtils;
import com.mysql.cj.util.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.stLabel.domain.StLabel;
import com.ruoyi.xindian.stLabel.service.IStLabelService;
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
 * ST标注Controller
 * 
 * @author liuyi
 * @date 2024-01-26
 */
@RestController
@RequestMapping("/stLabel/stLabel")
public class StLabelController extends BaseController
{
    @Autowired
    private IStLabelService stLabelService;

    /**
     * 查询ST标注列表
     */
    @PreAuthorize("@ss.hasPermi('stLabel:stLabel:list')")
    @GetMapping("/list")
    public TableDataInfo list(StLabel stLabel)
    {
        startPage();
        List<StLabel> list = stLabelService.selectStLabelList(stLabel);
//        list.forEach(item->{
//            byte[] ecgData = item.getEcgData();
//            float[][] floats = bytesToHalfFloats(ecgData);
//            List<List<Float>> ecgDataJson = new ArrayList<>();
//            for (float[] row : floats) {
//                List<Float> rowList = new ArrayList<>();
//                for (float value : row) {
//                    rowList.add(value);
//                }
//                ecgDataJson.add(rowList);
//            }
//            item.setEcgDataJson(ecgDataJson);
//        });
        return getDataTable(list);
    }
    public static float[][] bytesToHalfFloats(byte[] inputBytes) {
        if (inputBytes.length != 48000) {
            throw new IllegalArgumentException("Invalid input length, expected 48000 for 12 * 2000 float16 numbers");
        }

        float[][] halfFloats = new float[12][2000];
        int rowIndex = 0;
        int columnIndex = 0;

        for (int i = 0; i < inputBytes.length; i += 2) {
            // 将两个连续的字节转换为一个short以表示float16
            short halfFloat = (short) ((inputBytes[i] & 0xFF) | ((inputBytes[i + 1] & 0xFF) << 8));
            // 这里假设已经有了正确的float16编码逻辑，实际应用中请使用上文提到的floatToHalf方法或其他可靠的转换方式
            float v = decodeFloat16(halfFloat);
            halfFloats[rowIndex][columnIndex++] = v;

            if (columnIndex == 2000) {
                rowIndex++;
                columnIndex = 0;
            }
        }

        return halfFloats;
    }
    private static float decodeFloat16(short half) {
        int sign = (half >> 15) & 0x01;
        int exponent = (half >> 10) & 0x1F;
        int mantissa = half & 0x3FF;

        int bits = (sign << 31) | ((exponent + 112) << 23) | (mantissa << 13);
        return Float.intBitsToFloat(bits);
    }
    /**
     * 导出ST标注列表
     */
    @PreAuthorize("@ss.hasPermi('stLabel:stLabel:export')")
    @Log(title = "ST标注", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, StLabel stLabel)
    {
        List<StLabel> list = stLabelService.selectStLabelList(stLabel);
        ExcelUtil<StLabel> util = new ExcelUtil<StLabel>(StLabel.class);
        util.exportExcel(response, list, "ST标注数据");
    }

    /**
     * 获取ST标注详细信息
     */
    @PreAuthorize("@ss.hasPermi('stLabel:stLabel:query')")
    @GetMapping(value = "/{logId}")
    public AjaxResult getInfo(@PathVariable("logId") String logId)
    {
        StLabel stLabel = stLabelService.selectStLabelByLogId(logId);
        byte[] ecgData = stLabel.getEcgData();
        float[][] floats = bytesToHalfFloats(ecgData);
        List<List<Float>> ecgDataJson = new ArrayList<>();
        for (float[] row : floats) {
            List<Float> rowList = new ArrayList<>();
            for (float value : row) {
                rowList.add(value);
            }
            ecgDataJson.add(rowList);
        }
        stLabel.setEcgDataJson(ecgDataJson);
        return AjaxResult.success(stLabel);
    }

    /**
     * 新增ST标注
     */
    @PreAuthorize("@ss.hasPermi('stLabel:stLabel:add')")
    @Log(title = "ST标注", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody StLabel stLabel)
    {
        return toAjax(stLabelService.insertStLabel(stLabel));
    }

    /**
     * 修改ST标注
     */
    @PreAuthorize("@ss.hasPermi('stLabel:stLabel:edit')")
    @Log(title = "ST标注", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody StLabel stLabel)
    {
        stLabel.setLabelTime(new Date());
        return toAjax(stLabelService.updateStLabel(stLabel));
    }

    /**
     * 删除ST标注
     */
    @PreAuthorize("@ss.hasPermi('stLabel:stLabel:remove')")
    @Log(title = "ST标注", businessType = BusinessType.DELETE)
	@DeleteMapping("/{logIds}")
    public AjaxResult remove(@PathVariable String[] logIds)
    {
        return toAjax(stLabelService.deleteStLabelByLogIds(logIds));
    }
}
