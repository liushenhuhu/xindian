package com.ruoyi.xindian.pdfPath.service;

import java.util.List;
import com.ruoyi.xindian.pdfPath.domain.PdfPath;

/**
 * pdf保存路径Service接口
 * 
 * @author hanhan
 * @date 2022-10-29
 */
public interface IPdfPathService 
{
    /**
     * 查询pdf保存路径
     * 
     * @param pathId pdf保存路径主键
     * @return pdf保存路径
     */
    public PdfPath selectPdfPathByPathId(Long pathId);

    /**
     * 查询pdf保存路径
     *
     * @return pdf保存路径
     */
    public PdfPath selectPdfPathByPId(String pId);

    /**
     * 查询pdf保存路径列表
     * 
     * @param pdfPath pdf保存路径
     * @return pdf保存路径集合
     */
    public List<PdfPath> selectPdfPathList(PdfPath pdfPath);

    /**
     * 新增pdf保存路径
     * 
     * @param pdfPath pdf保存路径
     * @return 结果
     */
    public int insertPdfPath(PdfPath pdfPath);

    /**
     * 修改pdf保存路径
     * 
     * @param pdfPath pdf保存路径
     * @return 结果
     */
    public int updatePdfPath(PdfPath pdfPath);

    /**
     * 批量删除pdf保存路径
     * 
     * @param pathIds 需要删除的pdf保存路径主键集合
     * @return 结果
     */
    public int deletePdfPathByPathIds(Long[] pathIds);

    /**
     * 删除pdf保存路径信息
     * 
     * @param pathId pdf保存路径主键
     * @return 结果
     */
    public int deletePdfPathByPathId(Long pathId);
}
