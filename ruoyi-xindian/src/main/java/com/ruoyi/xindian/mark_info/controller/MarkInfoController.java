package com.ruoyi.xindian.mark_info.controller;


import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.mark_info.domain.MarkInfo;
import com.ruoyi.xindian.mark_info.domain.User;
import com.ruoyi.xindian.mark_info.service.IMarkInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/mark_info/mark_info")
public class MarkInfoController extends BaseController {
    @Autowired
    private IMarkInfoService iMarkInfoService;

    @GetMapping("/list")
    public TableDataInfo list() {
        startPage();
        List<MarkInfo> list = iMarkInfoService.selectMarkInfoByLogId();
        Map<String,String> map = null;
        List<Map<String,String>> listMap=null;
        for (MarkInfo markInfo : list) {
            listMap=new ArrayList<>();
            List<String> info = Arrays.asList(markInfo.getAllLabel().split(",", -1));
            List<String> users = Arrays.asList(markInfo.getAllUsers().split(",", -1));
            for(int i=0;i<info.size();i++){
                map=new HashMap<>();
                map.put(users.get(i),info.get(i));
                listMap.add(map);
            }
            markInfo.setLabelList(listMap);
        }
        return getDataTable(list);
    }
    @GetMapping("/users")
    public List<User> users() {
        return iMarkInfoService.getUsers();
    }

}
