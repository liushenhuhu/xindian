package com.ruoyi.xindian.detection.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.detection.mapper.DetectionMapper;
import com.ruoyi.xindian.detection.domain.Detection;
import com.ruoyi.xindian.detection.service.IDetectionService;

/**
 * detectionService业务层处理
 * 
 * @author chenpeng
 * @date 2023-05-25
 */
@Service
public class DetectionServiceImpl implements IDetectionService 
{
    @Autowired
    private DetectionMapper detectionMapper;

    /**
     * 查询detection
     * 
     * @param detectionId detection主键
     * @return detection
     */
    @Override
    public Detection selectDetectionByDetectionId(Long detectionId)
    {
        return detectionMapper.selectDetectionByDetectionId(detectionId);
    }

    /**
     * 查询detection列表
     * 
     * @param detection detection
     * @return detection
     */
    @Override
    public List<Detection> selectDetectionList(Detection detection)
    {
        return detectionMapper.selectDetectionList(detection);
    }

    /**
     * 新增detection
     * 
     * @param detection detection
     * @return 结果
     */
    @Override
    public int insertDetection(Detection detection)
    {
        return detectionMapper.insertDetection(detection);
    }

    /**
     * 修改detection
     * 
     * @param detection detection
     * @return 结果
     */
    @Override
    public int updateDetection(Detection detection)
    {
        return detectionMapper.updateDetection(detection);
    }

    /**
     * 批量删除detection
     * 
     * @param detectionIds 需要删除的detection主键
     * @return 结果
     */
    @Override
    public int deleteDetectionByDetectionIds(Long[] detectionIds)
    {
        return detectionMapper.deleteDetectionByDetectionIds(detectionIds);
    }

    /**
     * 删除detection信息
     * 
     * @param detectionId detection主键
     * @return 结果
     */
    @Override
    public int deleteDetectionByDetectionId(Long detectionId)
    {
        return detectionMapper.deleteDetectionByDetectionId(detectionId);
    }
}
