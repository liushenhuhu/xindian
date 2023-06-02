package com.ruoyi.xindian.mark_info.controller;


import com.alibaba.fastjson2.JSONObject;
import com.github.pagehelper.PageInfo;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.PageDomain;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.core.page.TableSupport;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.mark_info.domain.MarkInfo;
import com.ruoyi.xindian.mark_info.domain.User;
import com.ruoyi.xindian.mark_info.service.IMarkInfoService;
import com.ruoyi.xindian.util.StrUtil;
import org.json.JSONException;
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
        Map<String,String> map;
        List<Map<String,String>> listMap;
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
    //根据ID查询
    @GetMapping("/{id}")
    public TableDataInfo List_id(@PathVariable("id") String id){
//        startPage();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        int index=(pageNum-1)*pageSize;
        List<MarkInfo> list = iMarkInfoService.selectMarkInfoByLogId();
        List<MarkInfo> resList = new ArrayList<>();
        List<MarkInfo> reList = new ArrayList<>();
        Map<String,String> map = null;
        List<Map<String,String>> listMap=null;

        for (MarkInfo markInfo : list) {
            if(",".equals(markInfo.getAllLabel())){
                continue;
            }
            listMap=new ArrayList<>();
            List<String> info = Arrays.asList(markInfo.getAllLabel().split(",", -1));
            List<String> users = Arrays.asList(markInfo.getAllUsers().split(",", -1));

            for(int i=0;i<info.size();i++) {
                if(id.equals(users.get(i)) && !info.get(i).equals("")) {
                    for(int j=0;j<info.size();j++) {
                        map=new HashMap<>();
                        map.put(users.get(j),info.get(j));
                        listMap.add(map);
                    }
                    markInfo.setLabelList(listMap);
                    resList.add(markInfo);
                    break;
                }
            }
        }
        for(int i=index;i<index+pageSize&&i<resList.size();i++){
            reList.add(resList.get(i));
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(reList);
        rspData.setTotal(resList.size());
        return rspData;
    }
//    获取不同标注
    @GetMapping("/NotSame")
    public TableDataInfo NotSame(User user) throws JSONException {
//        startPage();
        PageDomain pageDomain = TableSupport.buildPageRequest();
        Integer pageNum = pageDomain.getPageNum();
        Integer pageSize = pageDomain.getPageSize();
        int index=(pageNum-1)*pageSize;

//        String flag = user.getFlag();
//        double threshold = user.getThreshold();
        String flag = "noise";
        double threshold = 0.8;
        List<MarkInfo> list = iMarkInfoService.selectMarkInfoByLogId();
        List<MarkInfo> resList = new ArrayList<>();
        List<MarkInfo> reList = new ArrayList<>();
        Map<String,String> map;
        List<Map<String,String>> listMap;
        for (MarkInfo markInfo : list) {
//            if(!markInfo.getLogId().equals("10e3be44-5015-5cbb-b7e9-1e3ca1c59117"))
//                continue;
            listMap=new ArrayList<>();
            List<String> info = Arrays.asList(markInfo.getAllLabel().split(",", -1));
            List<String> users = Arrays.asList(markInfo.getAllUsers().split(",", -1));
            if(markInfo.getAllDataLabel()==null){
                continue;
            }
            List<String> dataLabel = Arrays.asList(markInfo.getAllDataLabel().split(";", -1));
            if (isSame(info) && isSameLocation(dataLabel,flag,threshold)){
                continue;
            }
            for(int i=0;i<info.size();i++){
                map=new HashMap<>();
                map.put(users.get(i),info.get(i));
                listMap.add(map);
            }
            markInfo.setLabelList(listMap);
            resList.add(markInfo);
        }
        for(int i=index;i<index+pageSize&&i<resList.size();i++){
            reList.add(resList.get(i));
        }
        TableDataInfo rspData = new TableDataInfo();
        rspData.setCode(HttpStatus.SUCCESS);
        rspData.setMsg("查询成功");
        rspData.setRows(reList);
        rspData.setTotal(resList.size());
        return rspData;


//        return getDataTable(resList);
    }

    /**
     * 判断噪声等级是否相同
     * @param info
     * @return
     */
    public boolean isSame(List<String> info){
        if(info.size()==1){
            return "".equals(info.get(0));
        }
        long count=info.stream().distinct().count();
        int flag=0;
        for (String s : info) {
            if("".equals(s)){
                flag=1;
                break;
            }
        }
        if(flag==1){
            return count == 2 || count==1;
        }
        else{
            return count == 1;
        }
    }
    /**
     * 判断噪声标签位置是否相同
     */
    public boolean isSameLocation(List<String> info,String flag, double threshold) throws JSONException {
        if (info.size() == 1) {
            return "".equals(info.get(0));
        }
        if("".equals(info.get(0))&&"".equals(info.get(1))){
            return true;
        } else if ("".equals(info.get(0)) || "".equals(info.get(1))) {
            return false;
        }
        return StrUtil.isSameDir(info.get(0), info.get(1),flag,threshold);
    }
}
