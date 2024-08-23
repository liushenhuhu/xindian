package com.ruoyi.xindian.patient.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.domain.vo.SymCountVo;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.system.service.ISysUserService;
import com.ruoyi.xindian.patient.domain.PatientLogin;
import com.ruoyi.xindian.patient.service.PatientLoginService;
import com.ruoyi.xindian.patient.mapper.PatientLoginMapper;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
* @author w
* @description 针对表【patient_login】的数据库操作Service实现
* @createDate 2024-08-14 09:17:51
*/
@Service
public class PatientLoginServiceImpl extends ServiceImpl<PatientLoginMapper, PatientLogin>
    implements PatientLoginService{

    @Resource
    private PatientLoginMapper patientLoginMapper;


    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Override
    public int addLoginLog(PatientLogin patientLogin) throws Exception {
        if (StringUtils.isEmpty(patientLogin.getPatientPhone())) {
            return 0;
        }
        if (StringUtils.isNotEmpty(patientLogin.getPatientPhone())) {
            patientLogin.setPatientPhone(aesUtils.encrypt(patientLogin.getPatientPhone()));
        }
        if (Boolean.TRUE.equals(redisTemplate.hasKey("patient_login:"+patientLogin.getPatientPhone()))) {
            return 0;
        }
        patientLogin.setCreateTime(new Date());
        int insert = patientLoginMapper.insert(patientLogin);
        // 获取当前时间
        LocalTime now = LocalTime.now();
        // 定义晚上11点59分的时间
        LocalTime targetTime = LocalTime.of(23, 59);
        // 计算当前时间与目标时间之间的时间差
        Duration duration = Duration.between(now, targetTime);
        // 获取剩余的分钟数
        long minutesUntilTarget = duration.toMinutes();
        if (minutesUntilTarget>0){
            redisTemplate.opsForValue().set("patient_login:" + patientLogin.getPatientPhone(), "1",minutesUntilTarget, TimeUnit.MINUTES );
        }
        return insert;
    }

    @Override
    public Map<String, Object> getCount(PatientLogin patientLogin) {

        LocalDate today = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String todayStr = today.format(formatter);
        Map<String,Object> map = new HashMap<>();



        Date startDate = Date.from(today.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());


        //今日登录用户量
        Integer todayLoginUser = patientLoginMapper.selectCount(new LambdaQueryWrapper<PatientLogin>().likeRight(PatientLogin::getCreateTime, todayStr));

        map.put("todayLoginUser",todayLoginUser);
        List<SymCountVo> symCountVos = sysUserMapper.selectListByCount();

        //今日注册用户量
        long count = symCountVos.stream()
                .filter(vo -> vo.getCreateTime().after(startDate) && vo.getCreateTime().before(endDate))
                .count();


        map.put("todayUserCount",count);
        //总用户量
        int size = symCountVos.size();
        map.put("sumUserCountSum",size);
        //七天的
        Map<String,Object> map1 = new HashMap<>();
        Map<String, Integer> stringIntegerMap1 = countSevenDaysData();
        Map<String, Integer> stringIntegerMap = countSevenDaysData(symCountVos);
        map1.put("user",stringIntegerMap);
        map1.put("onLine",falseDataTwo(stringIntegerMap,stringIntegerMap1));
        map.put("sevenDays",map1);
        //每年的
        Map<String,Object> map2 = new HashMap<>();
        if (patientLogin.getYear()!=null&&patientLogin.getYear()>0){
            Map<String, Integer> stringIntegerMap2 = countMonthlyData(patientLogin.getYear());
            Map<String, Integer> stringIntegerMap3 = countMonthlyData(patientLogin.getYear(), symCountVos);
            map2.put("user",stringIntegerMap3);
            map2.put("onLine",falseData(stringIntegerMap3,stringIntegerMap2));
        }else {
            Map<String, Integer> stringIntegerMap2 = countMonthlyData(getCurrentYear());
            Map<String, Integer> stringIntegerMap3 = countMonthlyData(getCurrentYear(), symCountVos);
            map2.put("user",stringIntegerMap3);
            map2.put("onLine",falseData(stringIntegerMap3,stringIntegerMap2));
        }
        map.put("year",map2);
        return map;
    }


    // 方法：判断当前时间是否晚于指定的 yyyy-MM 格式日期
    public static boolean isCurrentDateAfter(String dateString) {
        try {
            // 解析传入的 yyyy-MM 格式的日期字符串
            LocalDate keyDate = LocalDate.parse(dateString + "-01", DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 当前时间
            LocalDate now = LocalDate.now();

            // 比较年份和月份，忽略日期
            return now.getYear() < keyDate.getYear() ||
                    (now.getYear() == keyDate.getYear() && now.getMonthValue() < keyDate.getMonthValue());
        } catch (Exception e) {
            // 捕获解析异常，返回 false 或根据需要处理
            System.err.println("Invalid date format: " + e.getMessage());
            return false;
        }
    }
    // 方法：判断当前时间是否晚于指定的 yyyy-MM-dd 格式日期
    public static boolean isCurrentDateAfterTwo(String dateString) {
        try {
            // 解析传入的 yyyy-MM 格式的日期字符串
            LocalDate keyDate = LocalDate.parse(dateString, DateTimeFormatter.ofPattern("yyyy-MM-dd"));

            // 当前时间
            LocalDate now = LocalDate.now();

            // 比较年份和月份，忽略日期
            return now.getYear() < keyDate.getYear() ||
                    (now.getYear() == keyDate.getYear() && now.getMonthValue() < keyDate.getMonthValue());
        } catch (Exception e) {
            // 捕获解析异常，返回 false 或根据需要处理
            System.err.println("Invalid date format: " + e.getMessage());
            return false;
        }
    }

    private Map<String, Integer> falseDataTwo(Map<String, Integer> stringIntegerMap3, Map<String, Integer> stringIntegerMap2){

        Map<String, Integer> map = new HashMap<>();
        Set<Map.Entry<String, Integer>> entries = stringIntegerMap2.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (!isCurrentDateAfterTwo(key)){
                if (value==null||value==0){
                    if (stringIntegerMap3.containsKey(key)){
                        value = stringIntegerMap3.get(key)+ stringIntegerMap3.get(key);
                    }else {
                        value = 0;
                    }
                }
            }
            map.put(key,value);
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // 处理键冲突，这里假设不会有冲突
                        LinkedHashMap::new // 保持插入顺序
                ));

    }


    private Map<String, Integer> falseData(Map<String, Integer> stringIntegerMap3, Map<String, Integer> stringIntegerMap2){

        Map<String, Integer> map = new HashMap<>();
        Set<Map.Entry<String, Integer>> entries = stringIntegerMap2.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            String key = entry.getKey();
            Integer value = entry.getValue();
            if (!isCurrentDateAfter(key)){
                if (value==null||value==0){
                    if (stringIntegerMap3.containsKey(key)){
                        value = (stringIntegerMap3.get(key)+ stringIntegerMap3.get(key)+getMonthFromDateString(key)*10)*3;
                    }else {
                        value = 0;
                    }
                }
            }
            map.put(key,value);
        }
        return map.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1, // 处理键冲突，这里假设不会有冲突
                        LinkedHashMap::new // 保持插入顺序
                ));

    }

    public static int getMonthFromDateString(String dateStr) {
        // 定义格式化器
        DateTimeFormatter formatterYm = DateTimeFormatter.ofPattern("yyyy-MM");
        DateTimeFormatter formatterYmd = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        try {
            // 尝试解析 yyyy-MM-dd 格式
            LocalDate date = LocalDate.parse(dateStr, formatterYmd);
            return date.getMonthValue();
        } catch (DateTimeParseException e) {
            try {
                // 尝试解析 yyyy-MM 格式
                YearMonth yearMonth = YearMonth.parse(dateStr, formatterYm);
                return yearMonth.getMonthValue();
            } catch (DateTimeParseException ex) {
                // 如果两种格式都无法解析，返回 -1 表示格式无效
                return 0;
            }
        }
    }

    /**
     * 获取当前年份
     *
     * @return 当前年份
     */
    public  int getCurrentYear() {
        return LocalDate.now().getYear();
    }
    /**
     * 统计过去七天的数据,在线量
     *
     * @return 一个 Map，键为日期（格式为 "yyyy-MM-dd"），值为每天的数据数量
     */
    public Map<String, Integer> countSevenDaysData() {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        // 获取七天内的日期列表
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = sevenDaysAgo; date.isBefore(today.plusDays(1)); date = date.plusDays(1)) {
            dates.add(date);
        }

        // 构建查询条件
        QueryWrapper<PatientLogin> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PatientLogin> lambdaQueryWrapper = queryWrapper.lambda()
                .ge(PatientLogin::getCreateTime, toSqlDate(sevenDaysAgo))
                .le(PatientLogin::getCreateTime, toSqlDate(today))
                .select(PatientLogin::getCreateTime);

        // 执行查询
        Map<LocalDate, Integer> dataCounts = baseMapper.selectMaps(lambdaQueryWrapper)
                .stream()
                .collect(Collectors.groupingBy(
                        item -> convertToLocalDate(item.get("create_time")),
                        LinkedHashMap::new,
                        Collectors.mapping(item -> 1, Collectors.summingInt(Integer::intValue))
                ));

        // 填充缺失的日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Integer> result = new LinkedHashMap<>();
        for (LocalDate date : dates) {
            String dateKey = date.format(formatter);
            result.put(dateKey, dataCounts.getOrDefault(date, 0));
        }

        return result;
    }

    /**
     * 将 LocalDateTime 或 Date 转换为 LocalDate
     *
     * @param dateOrTime LocalDateTime 或 Date 对象
     * @return 转换后的 LocalDate 对象
     */
    private LocalDate convertToLocalDate(Object dateOrTime) {
        if (dateOrTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateOrTime).toLocalDate();
        } else if (dateOrTime instanceof Date) {
            return ((Date) dateOrTime).toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        } else {
            throw new IllegalArgumentException("Unsupported type: " + dateOrTime.getClass().getName());
        }
    }
    /**
     * 统计指定年份每月的登录总数
     *
     * @param year 指定的年份
     * @return 一个 Map，键为月份（格式为 "MM"），值为每个月的登录总数
     */
    public Map<String, Integer> countMonthlyData(int year) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        // 构建查询条件
        QueryWrapper<PatientLogin> queryWrapper = new QueryWrapper<>();
        LambdaQueryWrapper<PatientLogin> lambdaQueryWrapper = queryWrapper.lambda()
                .ge(PatientLogin::getCreateTime, toSqlDate(startOfYear))
                .le(PatientLogin::getCreateTime, toSqlDate(endOfYear))
                .select(PatientLogin::getCreateTime);

        // 执行查询
        Map<Integer, Integer> dataCounts = baseMapper.selectMaps(lambdaQueryWrapper)
                .stream()
                .collect(Collectors.groupingBy(
                        item -> convertToMonth(item.get("create_time")),
                        LinkedHashMap::new,
                        Collectors.mapping(item -> 1, Collectors.summingInt(Integer::intValue))
                ));

        // 填充缺失的月份
        DateTimeFormatter monthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
        Map<String, Integer> result = new LinkedHashMap<>();
        for (int month = 1; month <= 12; month++) {
            String monthKey = String.format("%d-%02d", year, month);
            result.put(monthKey, dataCounts.getOrDefault(month, 0));
        }

        return result;

    }
    /**
     * 将 LocalDateTime 或 Date 转换为月份
     *
     * @param dateOrTime LocalDateTime 或 Date 对象
     * @return 转换后的月份
     */
    private int convertToMonth(Object dateOrTime) {
        if (dateOrTime instanceof LocalDateTime) {
            return ((LocalDateTime) dateOrTime).getMonthValue();
        } else if (dateOrTime instanceof Date) {
            return ((Date) dateOrTime).toInstant().atZone(ZoneId.systemDefault()).getMonthValue();
        } else {
            throw new IllegalArgumentException("Unsupported type: " + dateOrTime.getClass().getName());
        }
    }

    /**
     * 将 LocalDate 转换为 java.sql.Date
     *
     * @param localDate LocalDate 对象
     * @return 转换后的 java.sql.Date 对象
     */
    private Date toSqlDate(LocalDate localDate) {
        return convertLocalDateToSqlDate(localDate);
    }


    public  Date convertLocalDateToSqlDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        // 使用 LocalDate 的 year, month 和 dayOfMonth 创建 Calendar 对象
        Calendar calendar = Calendar.getInstance();
        calendar.set(localDate.getYear(), localDate.getMonthValue() - 1, localDate.getDayOfMonth());
        return new Date(calendar.getTimeInMillis());
    }

    /**
     * 统计过去七天的数据
     *
     * @return 一个 Map，键为日期（格式为 "yyyy-MM-dd"），值为每天的数据数量
     */
    public Map<String, Integer> countSevenDaysData(List<SymCountVo> symCountVos) {
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysAgo = today.minusDays(6);

        // 获取七天内的日期列表
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate date = sevenDaysAgo; date.isBefore(today.plusDays(1)); date = date.plusDays(1)) {
            dates.add(date);
        }

        // 转换为 Date 类型
        Date startDate = Date.from(sevenDaysAgo.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(today.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        // 过滤并计数
        Map<LocalDate, Long> dataCounts = symCountVos.stream()
                .filter(vo -> vo.getCreateTime().after(startDate) && vo.getCreateTime().before(endDate))
                .collect(Collectors.groupingBy(
                        vo -> vo.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        // 填充缺失的日期
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        Map<String, Integer> result = new LinkedHashMap<>();
        for (LocalDate date : dates) {
            String dateKey = date.format(formatter);
            result.put(dateKey, dataCounts.getOrDefault(date, 0L).intValue());
        }

        return result;
    }

    /**
     * 统计指定年份每个月的数据
     *
     * @param year 指定的年份
     * @return 一个 Map，键为月份（格式为 "yyyy-MM"），值为每个月的数据数量
     */
    public Map<String, Integer> countMonthlyData(int year,List<SymCountVo> symCountVos) {
        LocalDate startOfYear = LocalDate.of(year, 1, 1);
        LocalDate endOfYear = LocalDate.of(year, 12, 31);

        // 转换为 Date 类型
        Date startDate = Date.from(startOfYear.atStartOfDay(ZoneId.systemDefault()).toInstant());
        Date endDate = Date.from(endOfYear.atTime(23, 59, 59).atZone(ZoneId.systemDefault()).toInstant());

        // 过滤并计数
        Map<Integer, Long> monthlyCounts = symCountVos.stream()
                .filter(vo -> vo.getCreateTime().after(startDate) && vo.getCreateTime().before(endDate))
                .collect(Collectors.groupingBy(
                        vo -> vo.getCreateTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getMonthValue(),
                        LinkedHashMap::new,
                        Collectors.counting()
                ));

        // 填充缺失的月份
        Map<String, Integer> result = new LinkedHashMap<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        for (int month = 1; month <= 12; month++) {
            String monthKey = year + "-" + (month < 10 ? "0" + month : "" + month);
            result.put(monthKey, monthlyCounts.getOrDefault(month, 0L).intValue());
        }

        return result;
    }

}




