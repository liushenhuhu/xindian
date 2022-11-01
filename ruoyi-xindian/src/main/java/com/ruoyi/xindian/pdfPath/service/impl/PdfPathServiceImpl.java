package com.ruoyi.xindian.pdfPath.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.pdfPath.mapper.PdfPathMapper;
import com.ruoyi.xindian.pdfPath.domain.PdfPath;
import com.ruoyi.xindian.pdfPath.service.IPdfPathService;

/**
 * pdf保存路径Service业务层处理
 * 
 * @author hanhan
 * @date 2022-10-29
 */
@Service
public class PdfPathServiceImpl implements IPdfPathService 
{
    @Autowired
    private PdfPathMapper pdfPathMapper;

    /**
     * 查询pdf保存路径
     * 
     * @param pathId pdf保存路径主键
     * @return pdf保存路径
     */
    @Override
    public PdfPath selectPdfPathByPathId(Long pathId)
    {
        return pdfPathMapper.selectPdfPathByPathId(pathId);
    }

    /**
     * 查询pdf保存路径列表
     * 
     * @param pdfPath pdf保存路径
     * @return pdf保存路径
     */
    @Override
    public List<PdfPath> selectPdfPathList(PdfPath pdfPath)
    {
        return pdfPathMapper.selectPdfPathList(pdfPath);
    }

    /**
     * 新增pdf保存路径
     * 
     * @param pdfPath pdf保存路径
     * @return 结果
     */
    @Override
    public int insertPdfPath(PdfPath pdfPath)
    {
        return pdfPathMapper.insertPdfPath(pdfPath);
    }

    /**
     * 修改pdf保存路径
     * 
     * @param pdfPath pdf保存路径
     * @return 结果
     */
    @Override
    public int updatePdfPath(PdfPath pdfPath)
    {
        return pdfPathMapper.updatePdfPath(pdfPath);
    }

    /**
     * 批量删除pdf保存路径
     * 
     * @param pathIds 需要删除的pdf保存路径主键
     * @return 结果
     */
    @Override
    public int deletePdfPathByPathIds(Long[] pathIds)
    {
        return pdfPathMapper.deletePdfPathByPathIds(pathIds);
    }

    /**
     * 删除pdf保存路径信息
     * 
     * @param pathId pdf保存路径主键
     * @return 结果
     */
    @Override
    public int deletePdfPathByPathId(Long pathId)
    {
        return pdfPathMapper.deletePdfPathByPathId(pathId);
    }
}
