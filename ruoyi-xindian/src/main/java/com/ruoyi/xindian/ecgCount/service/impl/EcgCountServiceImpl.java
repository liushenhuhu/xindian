package com.ruoyi.xindian.ecgCount.service.impl;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.ecgCount.domain.EcgCount;
import com.ruoyi.xindian.ecgCount.service.EcgCountService;
import com.ruoyi.xindian.ecgCount.mapper.EcgCountMapper;
import com.ruoyi.xindian.ecgCount.vo.TypeListVo;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.patient_management.vo.DocVO;
import com.ruoyi.xindian.patient_management.vo.ListValueAndLabelVO;
import com.ruoyi.xindian.statistics.domain.AgeStatistics;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;
import java.util.logging.SimpleFormatter;

/**
* @author 13401
* @description 针对表【ecg_count】的数据库操作Service实现
* @createDate 2023-10-07 16:23:51
*/
@Service
public class EcgCountServiceImpl extends ServiceImpl<EcgCountMapper, EcgCount>
    implements EcgCountService{




    @Resource
    private EcgCountMapper ecgCountMapper;


    @Resource
    private RedisTemplate<String , TypeListVo> redisTemplate;

    /**
     * 查询数据库统计
     *
     * @param id 数据库统计主键
     * @return 数据库统计
     */
    @Override
    public EcgCount selectEcgCountById(Long id)
    {
        return ecgCountMapper.selectEcgCountById(id);
    }

    /**
     * 查询数据库统计列表
     *
     * @param ecgCount 数据库统计
     * @return 数据库统计
     */
    @Override
    public List<EcgCount> selectEcgCountList(EcgCount ecgCount)
    {
        return ecgCountMapper.selectEcgCountList(ecgCount);
    }

    /**
     * 新增数据库统计
     *
     * @param ecgCount 数据库统计
     * @return 结果
     */
    @Override
    public int insertEcgCount(EcgCount ecgCount)
    {
        return ecgCountMapper.insertEcgCount(ecgCount);
    }

    /**
     * 修改数据库统计
     *
     * @param ecgCount 数据库统计
     * @return 结果
     */
    @Override
    public int updateEcgCount(EcgCount ecgCount)
    {
        return ecgCountMapper.updateEcgCount(ecgCount);
    }

    /**
     * 批量删除数据库统计
     *
     * @param ids 需要删除的数据库统计主键
     * @return 结果
     */
    @Override
    public int deleteEcgCountByIds(Long[] ids)
    {
        return ecgCountMapper.deleteEcgCountByIds(ids);
    }

    /**
     * 删除数据库统计信息
     *
     * @param id 数据库统计主键
     * @return 结果
     */
    @Override
    public int deleteEcgCountById(Long id)
    {
        return ecgCountMapper.deleteEcgCountById(id);
    }

    @Override
    public List<ListValueAndLabelVO> getLogType() {
        List<ListValueAndLabelVO> logType = ecgCountMapper.getLogType();
        DocVO docVO2 = new DocVO();
        docVO2.setValue("1");
        docVO2.setLabel("是");
        DocVO docVO3 = new DocVO();
        docVO3.setValue("0");
        docVO3.setLabel("否");
        for (ListValueAndLabelVO valueAndLabelVO : logType){
            valueAndLabelVO.getChildren().add(docVO2);
            valueAndLabelVO.getChildren().add(docVO3);
        }
        return logType;
    }


    @Transactional
    @Override
    public void importEcgCount(String filePath) throws Exception {

        FileInputStream inputStream = new FileInputStream(filePath);
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
        reader.readLine();//第一行信息是标题信息，跳过，如果需要第一行信息，请删除这行代码
        String line ;

        // 定义list，方便后面批次新增 TlVacuumFurnaceLog是表实体类
        List<EcgCount> result = new ArrayList<>();
        // 循环每行数据
        while ((line = reader.readLine()) != null) {
            // 分割字符串
            String[] item = line.split(",");
            if (item.length >0) {
                // 调用下面的方法，将每列数据赋值到对应的属性中，返回的结果新增到list内
                result.add(propertiesAssignment(item));
            }
        }
//        ecgCountMapper.insertEcgCounts(result);

//        System.out.println(result.size());

        for (int i = 0; i < result.size(); i++) {
            ecgCountMapper.insert(result.get(i));
        }
    }

    public EcgCount propertiesAssignment(String[] item) throws Exception{
//        List<String> strings = new ArrayList<>();
//
//        for (int i = 0; i < item1.length; i++) {
//            if (i!=0&&i!=3&&i!=4){
//                strings.add(item1[i]);
//            }
//        }
//        String[] item = strings.toArray(new String[strings.size()]);
        // 获取目标类class
        Class<EcgCount> clazz = EcgCount.class;
        // 实例化
        EcgCount tlVacuumFurnaceLog = clazz.newInstance();

        // 获取对象全部属性
        Field[] fields = clazz.getDeclaredFields();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/M/d HH:mm");
        // 循环遍历 当前行数据的列数
        for (int i = 0; i < item.length; i++) {
            // 获取方法名
            String methodName = "set" + fields[i].getName().substring(0,1).toUpperCase() + fields[i].getName().substring(1);
            // 获取Method参数
            Method method = clazz.getMethod(methodName, fields[i].getType());
            // 判断当前属性是什么类型的，将数据进行类型转换
            if ("Integer".equals(fields[i].getType().getSimpleName())){
                method.invoke(tlVacuumFurnaceLog,Integer.parseInt(item[i]));
            } else if ("BigDecimal".equals(fields[i].getType().getSimpleName())){
                method.invoke(tlVacuumFurnaceLog, new BigDecimal(item[i]));
            } else if ("Short".equals(fields[i].getType().getSimpleName())){
                method.invoke(tlVacuumFurnaceLog,Short.valueOf(item[i]));
            } else if ("Double".equals(fields[i].getType().getSimpleName())){
                method.invoke(tlVacuumFurnaceLog,Double.valueOf(item[i]));
            } else if ("Byte".equals(fields[i].getType().getSimpleName())){
                method.invoke(tlVacuumFurnaceLog,Byte.valueOf(item[i]));
            } else if ("String".equals(fields[i].getType().getSimpleName())){
                method.invoke(tlVacuumFurnaceLog,item[i]);
            }else if ("Date".equals(fields[i].getType().getSimpleName())){
                method.invoke(tlVacuumFurnaceLog,simpleDateFormat.parse(item[i]));
            }

        }

        return tlVacuumFurnaceLog;
    }


    public EcgCount getTypeSum() throws Exception{
        // 获取目标类class
        Class<EcgCount> clazz = EcgCount.class;
        // 实例化
        EcgCount tlVacuumFurnaceLog = clazz.newInstance();

        // 获取对象全部属性
        Field[] fields = clazz.getDeclaredFields();
        // 循环遍历 当前行数据的列数
        for (Field field : fields) {
            System.out.println(field.getName());
            if (field.getName().contains("Ecg")){
                // 获取方法名
                String methodName = "set" + field.getName().substring(0, 1).toUpperCase() + field.getName().substring(1);
            }



        }

        return tlVacuumFurnaceLog;
    }

    @Override
    public List<AgeStatistics> ageListByMan(AgeStatistics str) {

        return ecgCountMapper.ageListByMan(str);
    }

    @Override
    public List<AgeStatistics> ageListByWoman(AgeStatistics str) {

        return ecgCountMapper.ageListByWoman(str);
    }

    @Override
    public Map<String , List<TypeListVo>> getTypeList() throws InstantiationException, IllegalAccessException {


//        if(Boolean.TRUE.equals(redisTemplate.hasKey("ecgCountType"))) {
//            //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
//            //返回这个集合
//            List<TypeListVo> ecgCountType = redisTemplate.opsForList().range("ecgCountType", 0, -1);
//            return ecgCountType;
//        }else {
            //存放心动过速类型
            List<TypeListVo> typeListVoList = new ArrayList<>();
            //存放颤动/扑动类型
            List<TypeListVo> typeListVoList1 = new ArrayList<>();
            //存放其他类型
            List<TypeListVo> typeListVoList2 = new ArrayList<>();
            //存放心肌梗死类型
            List<TypeListVo> typeListVoList3 = new ArrayList<>();
            //存放传导阻滞类型
            List<TypeListVo> typeListVoList4 = new ArrayList<>();
            //存放心律类型
            List<TypeListVo> typeListVoList6 = new ArrayList<>();
            //存放房室肥大类型
            List<TypeListVo> typeListVoList7 = new ArrayList<>();
            //存放颤动/扑动类型
            List<TypeListVo> typeListVoList8 = new ArrayList<>();
            //存放预测猝死类型
            List<TypeListVo> typeListVoList9 = new ArrayList<>();

            EcgCount ecgTypeList = ecgCountMapper.getEcgTypeList();
            List<TypeListVo> logType = ecgCountMapper.getLogTypeAndValue();

            Class<? extends EcgCount> aClass = ecgTypeList.getClass();

            Field[] fields = aClass.getDeclaredFields();
            Map<String , List<TypeListVo>> stringListMap = new HashMap<>();
            // 遍历字段并获取值
            for (Field field : fields) {
                field.setAccessible(true); // 设置字段可访问，即使是私有字段
                Object fieldValue = field.get(ecgTypeList);
                if (field.getName().contains("Ecg")&&fieldValue!=null){
                    String text = field.getName().substring(0,field.getName().length()-3);
                    for (TypeListVo typeListVo : logType){

                        if (text.equals(typeListVo.getValue())){
                            if (typeListVo.getType().equals("心动过速")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("早搏/逸搏")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList1.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("其他")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList2.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("预测猝死")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList9.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("心肌梗死")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList3.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("传导阻滞")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList4.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("心律")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList6.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("房室肥大")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList7.add(typeListVo);
                                break;
                            }
                            if (typeListVo.getType().equals("颤动/扑动")){
                                typeListVo.setValue(fieldValue.toString());
                                typeListVo.setName(typeListVo.getLabel());
                                typeListVoList8.add(typeListVo);
                                break;
                            }

                        }
                    }
                }
            }


        stringListMap.put("XDGS",typeListVoList);
        stringListMap.put("ZBTB",typeListVoList1);
        stringListMap.put("QT",typeListVoList2);
        stringListMap.put("XJGS",typeListVoList3);
        stringListMap.put("CDZD",typeListVoList4);
        stringListMap.put("XL",typeListVoList6);
        stringListMap.put("FSFD",typeListVoList7);
        stringListMap.put("CDPD",typeListVoList8);
        stringListMap.put("YCCS",typeListVoList9);

//            if (typeListVoList.size()>0){
//                if (Boolean.TRUE.equals(redisTemplate.hasKey("ecgCountType"))){
//                    redisTemplate.opsForList().remove("ecgCountType", 0, -1);
//                }
//                //存入redis
//                redisTemplate.opsForList().leftPushAll("ecgCountType", typeListVoList);
//                //给redis设置毫秒值
//                //第一个参数是key
//                //第二个参数是值
//                //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
//                redisTemplate.expire("ecgCountType",37, TimeUnit.MINUTES);
//            }
            return stringListMap;

//        }



    }

    @Override
    public List<TypeListVo> getZCXDTCount() {
        if(Boolean.TRUE.equals(redisTemplate.hasKey("ecgZCXDTCountType"))) {
            //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
            //返回这个集合
            List<TypeListVo> ecgCountType = redisTemplate.opsForList().range("ecgZCXDTCountType", 0, -1);
            return ecgCountType;
        }else {

            List<TypeListVo> zcxdtCount = ecgCountMapper.getZCXDTCount();
            for (TypeListVo typeListVo : zcxdtCount){
                if (typeListVo.getName().equals("1")){
                    typeListVo.setName("正常心电图");
                }
                if (typeListVo.getName().equals("0")){
                    typeListVo.setName("异常心电图");
                }
            }
            if (zcxdtCount.size()>0){
                if (Boolean.TRUE.equals(redisTemplate.hasKey("ecgZCXDTCountType"))){
                    redisTemplate.opsForList().remove("ecgZCXDTCountType", 0, -1);
                }
                //存入redis
                redisTemplate.opsForList().leftPushAll("ecgZCXDTCountType", zcxdtCount);
                //给redis设置毫秒值
                //第一个参数是key
                //第二个参数是值
                //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
                redisTemplate.expire("ecgZCXDTCountType",35, TimeUnit.MINUTES);
            }
            return zcxdtCount;

        }
    }

    @Override
    public List<TypeListVo> getAgeYoung() {
        if(Boolean.TRUE.equals(redisTemplate.hasKey("ecgAgeYoung"))) {
            //如果有就查询redis里这个list集合（第一个参数是key,0,-1是查询所有）
            //返回这个集合
            return redisTemplate.opsForList().range("ecgAgeYoung", 0, -1);
        }else {

            List<TypeListVo> typeListVoList = ecgCountMapper.getAgeYoung();

            if (typeListVoList!=null&&typeListVoList.size()>0){
                if (Boolean.TRUE.equals(redisTemplate.hasKey("ecgAgeYoung"))){
                    redisTemplate.opsForList().remove("ecgAgeYoung", 0, -1);
                }
                //存入redis
                redisTemplate.opsForList().leftPushAll("ecgAgeYoung", typeListVoList);
                //给redis设置毫秒值
                //第一个参数是key
                //第二个参数是值
                //第三个参数是时间颗粒度转换,MILLISECONDS是毫秒,所以这个redis的TTl是一小时
                redisTemplate.expire("ecgAgeYoung",38, TimeUnit.MINUTES);
            }
            return typeListVoList;

        }

    }


}




