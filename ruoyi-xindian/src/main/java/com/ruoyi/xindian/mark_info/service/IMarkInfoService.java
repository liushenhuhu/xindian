package com.ruoyi.xindian.mark_info.service;


import com.ruoyi.xindian.mark_info.domain.MarkInfo;
import com.ruoyi.xindian.mark_info.domain.User;

import java.util.List;

public interface IMarkInfoService {

    //获取用户标注信息
    public List<MarkInfo> selectMarkInfoByLogId();

    //获取用户
    public List<User> getUsers();

    public void groupConcat();
}
