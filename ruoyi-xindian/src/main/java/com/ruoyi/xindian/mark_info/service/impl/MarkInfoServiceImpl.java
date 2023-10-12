package com.ruoyi.xindian.mark_info.service.impl;

import com.ruoyi.xindian.mark_info.domain.MarkInfo;
import com.ruoyi.xindian.mark_info.domain.User;
import com.ruoyi.xindian.mark_info.mapper.MarkInfoMapper;
import com.ruoyi.xindian.mark_info.service.IMarkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MarkInfoServiceImpl implements IMarkInfoService {

    @Autowired
    private MarkInfoMapper markInfoMapper;

    @Override
    public List<MarkInfo> selectMarkInfoByLogId(MarkInfo markInfo) {
        return markInfoMapper.selectMarkInfoByLogId(markInfo);
    }

    @Override
    public List<User> getUsers() {
        return markInfoMapper.getUsers();
    }
    @Autowired
    public void groupConcat(){
        markInfoMapper.groupConcat();
    }
}
