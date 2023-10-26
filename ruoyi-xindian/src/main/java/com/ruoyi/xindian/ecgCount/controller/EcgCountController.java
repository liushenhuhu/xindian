package com.ruoyi.xindian.ecgCount.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.xindian.ecgCount.domain.EcgCount;
import com.ruoyi.xindian.ecgCount.domain.EcgCountType;
import com.ruoyi.xindian.ecgCount.service.EcgCountService;
import com.ruoyi.xindian.ecgCount.service.EcgCountTypeService;
import com.ruoyi.xindian.ecgCount.vo.TypeListVo;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import org.aspectj.weaver.loadtime.Aj;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 数据库统计
 */
@RestController
@RequestMapping("/ecgCount/ecgCount")
public class EcgCountController extends BaseController {


    @Resource
    private EcgCountService ecgCountService;

    @Resource
    private EcgCountTypeService ecgCountTypeService;

    @Resource
    private RedisTemplate<String , Map<String,Object>> redisTemplate;

    /**
     * 数据库导入
     * @param path
     * @throws Exception
     */
    @GetMapping("/importEcg")
    public void importEcg(String path) throws Exception {
        ecgCountService.importEcgCount(path);
    }

    /**
     * 查询数据库统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:count:list')")
    @GetMapping("/list")
    public TableDataInfo list(EcgCount ecgCount)
    {
        startPage();
        List<EcgCount> list = ecgCountService.selectEcgCountList(ecgCount);
        return getDataTable(list);
    }

    /**
     * 导出数据库统计列表
     */
    @PreAuthorize("@ss.hasPermi('system:count:export')")
    @Log(title = "数据库统计", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EcgCount ecgCount)
    {
        List<EcgCount> list = ecgCountService.selectEcgCountList(ecgCount);
        ExcelUtil<EcgCount> util = new ExcelUtil<EcgCount>(EcgCount.class);
        util.exportExcel(response, list, "数据库统计数据");
    }

    /**
     * 获取数据库统计详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:count:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(ecgCountService.selectEcgCountById(id));
    }

    /**
     * 新增数据库统计
     */
    @PreAuthorize("@ss.hasPermi('system:count:add')")
    @Log(title = "数据库统计", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EcgCount ecgCount)
    {
        return toAjax(ecgCountService.insertEcgCount(ecgCount));
    }

    /**
     * 修改数据库统计
     */
    @PreAuthorize("@ss.hasPermi('system:count:edit')")
    @Log(title = "数据库统计", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EcgCount ecgCount)
    {
        return toAjax(ecgCountService.updateEcgCount(ecgCount));
    }

    /**
     * 删除数据库统计
     */
    @PreAuthorize("@ss.hasPermi('system:count:remove')")
    @Log(title = "数据库统计", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(ecgCountService.deleteEcgCountByIds(ids));
    }


    /**
     * 查找数据库统计中的所有类型
     * @return
     */
    @GetMapping("/getLogType")
    public AjaxResult getLogType(){
        List<ListValueAndLabelVO> logType = ecgCountService.getLogType();
        return AjaxResult.success(logType);
    }


    /**
     * 数据库统计表进行男女人数分布
     */
    @GetMapping ("/ageList")
    public Map<Object,Object> ageList(@RequestParam("type") List<String> type){
        if(type!=null&&type.size()>0){

            List<String> collect = getListType(type);
            StringBuilder sb = new StringBuilder();
            Map<String, Object> typeMap = new HashMap<>();
            for (String s : collect) {
                typeMap.put(s, s);
                sb.append(s).append(",");
            }
            // 去掉最后一个逗号
            if (sb.length() > 0) {
                sb.delete(sb.length() - 2, sb.length());
            }
            if (Boolean.TRUE.equals(redisTemplate.hasKey("ecgCountTypeAgeJump:"+sb))){
                return redisTemplate.opsForHash().entries("ecgCountTypeAgeJump:"+sb);
            }else {
                /* 查询性别男 */
                List<AgeStatistics> manList = ecgCountService.ageListByMan(typeMap);
                /* 查询性别女 */
                List<AgeStatistics> womanList = ecgCountService.ageListByWoman(typeMap);

                Map<Object,Object> map = new HashMap<Object,Object>();
                map.put("men",manList);
                map.put("women",womanList);
                //存入redis
                redisTemplate.opsForHash().putAll("ecgCountTypeAgeJump:"+sb, map);
                //给redis设置毫秒值
                //第一个参数是key
                //第二个参数是值
                //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
//                redisTemplate.expire("ecgCountTypeAgeJump:"+sb,43, TimeUnit.HOURS);

                return map;
            }

        }
        if(Boolean.TRUE.equals(redisTemplate.hasKey("ecgCountTypeAge"))) {
            //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
            //返回这个集合
            Map<Object, Object> ecgCountTypeAge = redisTemplate.opsForHash().entries("ecgCountTypeAge");
            return ecgCountTypeAge;
        }else {
            /* 查询性别男 */
            List<AgeStatistics> manList = ecgCountService.ageListByMan(null);
            /* 查询性别女 */
            List<AgeStatistics> womanList = ecgCountService.ageListByWoman(null);
            Map<Object,Object> map = new HashMap<Object,Object>();
            map.put("men",manList);
            map.put("women",womanList);
            //存入redis
            redisTemplate.opsForHash().putAll("ecgCountTypeAge", map);
            //给redis设置毫秒值
            //第一个参数是key
            //第二个参数是值
            //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
            redisTemplate.expire("ecgCountTypeAge",43, TimeUnit.MINUTES);

            return map;
        }
    }


    @GetMapping("/getTypeList")
    public AjaxResult getTypeList() throws InstantiationException, IllegalAccessException {
        if(Boolean.TRUE.equals(redisTemplate.hasKey("ecgCountType"))) {
            //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
            //返回这个集合
            Map<Object, Object> ecgCountTypeAge = redisTemplate.opsForHash().entries("ecgCountType");
            return AjaxResult.success(ecgCountTypeAge);
        }else {
            Map<String , List<TypeListVo>> typeList = ecgCountService.getTypeList();
            //存入redis
            redisTemplate.opsForHash().putAll("ecgCountType", typeList);
            //给redis设置毫秒值
            //第一个参数是key
            //第二个参数是值
            //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
            redisTemplate.expire("ecgCountType",43, TimeUnit.MINUTES);

            return AjaxResult.success(typeList);
        }

    }


    @GetMapping("/getZCXDTCount")
    public AjaxResult getZCXDTCount(){
        return AjaxResult.success(ecgCountService.getZCXDTCount());
    }

    @GetMapping("/getAgeYoung")
    public AjaxResult getAgeYoung(@RequestParam("type") List<String> type){
        List<String> collect = getListType(type);
        StringBuilder sb = new StringBuilder();
        Map<String, Object> typeMap = new HashMap<>();
        for (String s : collect) {
            typeMap.put(s, s);
            sb.append(s).append(",");
        }
        // 去掉最后一个逗号
        if (sb.length() > 0) {
            sb.delete(sb.length() - 2, sb.length());
        }

        return AjaxResult.success(ecgCountService.getAgeYoung(typeMap,sb.toString()));
    }


    private List<String> getListType(List<String> type){
        List<String> strings = new ArrayList<>();
        for (String c : type){
            EcgCountType ecgValueButLabel = ecgCountTypeService.getEcgValueButLabel(c);
            if (ecgValueButLabel==null){
                strings.add("ycxdt");
                continue;
            }
            strings.add(ecgValueButLabel.getValue());
        }

        return strings.stream().map(i -> i + "_ecg").collect(Collectors.toList());
    }

}
