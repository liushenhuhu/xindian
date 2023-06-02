package com.ruoyi.xindian.mark_info.mapper;

import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.mark_info.domain.MarkInfo;
import com.ruoyi.xindian.mark_info.domain.User;

import java.util.List;

public interface MarkInfoMapper {

    public List<MarkInfo> selectMarkInfoByLogId();

    public List<User> getUsers();

    public void groupConcat();
}
