package com.ruoyi.xindian.alert_log.service;

import com.ruoyi.xindian.alert_log.domain.JecgSingle;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 13401
* @description 针对表【jecg_single(静态心电数据)】的数据库操作Service
* @createDate 2023-10-16 13:03:15
*/
public interface JecgSingleService extends IService<JecgSingle> {


    /**
     * 再原有的数据上替换关键点数据
     */
    int updateBeatLabel(JecgSingle jecgSingle);


    /**
     * 通过pID查询所有的关键点数据
     */
    JecgSingle selectById(String pId);


    /**
     * 修改标签数据，并重新覆盖
     */
    int updateWaveLabel(JecgSingle jecgSingle);

}
