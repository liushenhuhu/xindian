package com.ruoyi.xindian.detection.service;

import java.util.List;
import com.ruoyi.xindian.detection.domain.Detection;

/**
 * detectionService接口
 * 
 * @author chenpeng
 * @date 2023-05-25
 */
public interface IDetectionService 
{
    /**
     * 查询detection
     * 
     * @param detectionId detection主键
     * @return detection
     */
    public Detection selectDetectionByDetectionId(Long detectionId);

    /**
     * 查询detection列表
     * 
     * @param detection detection
     * @return detection集合
     */
    public List<Detection> selectDetectionList(Detection detection);

    /**
     * 新增detection
     * 
     * @param detection detection
     * @return 结果
     */
    public int insertDetection(Detection detection);

    /**
     * 修改detection
     * 
     * @param detection detection
     * @return 结果
     */
    public int updateDetection(Detection detection);

    /**
     * 批量删除detection
     * 
     * @param detectionIds 需要删除的detection主键集合
     * @return 结果
     */
    public int deleteDetectionByDetectionIds(Long[] detectionIds);

    /**
     * 删除detection信息
     * 
     * @param detectionId detection主键
     * @return 结果
     */
    public int deleteDetectionByDetectionId(Long detectionId);
}
