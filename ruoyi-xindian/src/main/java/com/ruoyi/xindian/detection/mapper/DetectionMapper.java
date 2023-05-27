package com.ruoyi.xindian.detection.mapper;

import java.util.List;
import com.ruoyi.xindian.detection.domain.Detection;

/**
 * detectionMapper接口
 * 
 * @author chenpeng
 * @date 2023-05-25
 */
public interface DetectionMapper 
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
     * 删除detection
     * 
     * @param detectionId detection主键
     * @return 结果
     */
    public int deleteDetectionByDetectionId(Long detectionId);

    /**
     * 批量删除detection
     * 
     * @param detectionIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteDetectionByDetectionIds(Long[] detectionIds);
}
