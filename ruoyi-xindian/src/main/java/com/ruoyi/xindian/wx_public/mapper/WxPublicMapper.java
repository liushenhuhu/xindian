package com.ruoyi.xindian.wx_public.mapper;

import com.ruoyi.xindian.patient_management.vo.Limit;
import com.ruoyi.xindian.report.domain.Report;
import com.ruoyi.xindian.wx_public.domain.WxPublic;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface WxPublicMapper
{
    List<String> selectAllOpenids();

    int insert(@Param("openid")String openid, @Param("unionid")String unionid);
}
