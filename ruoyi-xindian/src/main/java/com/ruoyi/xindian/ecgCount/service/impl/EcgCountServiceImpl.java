package com.ruoyi.xindian.ecgCount.service.impl;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
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
        DocVO docVO = new DocVO();
        docVO.setValue("1");
        docVO.setLabel("有");
        DocVO docVO1 = new DocVO();
        docVO1.setValue("0");
        docVO1.setLabel("无");
        for (ListValueAndLabelVO valueAndLabelVO : logType){
            if (valueAndLabelVO.getLabel().equals("正常心电图")){
                DocVO docVO2 = new DocVO();
                docVO2.setValue("1");
                docVO2.setLabel("是");
                DocVO docVO3 = new DocVO();
                docVO3.setValue("0");
                docVO3.setLabel("否");
                valueAndLabelVO.getChildren().add(docVO2);
                valueAndLabelVO.getChildren().add(docVO3);
                continue;
            }
            valueAndLabelVO.getChildren().add(docVO1);
            valueAndLabelVO.getChildren().add(docVO);
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
//        ecgCountMapper.insertEcgCounts(result);insertEcgCounts


//        for (int i = 1048577; i < result.size(); i++) {
//            ecgCountMapper.insert(result.get(i));
//        }
    }

    public EcgCount propertiesAssignment(String[] item1) throws Exception{
        List<String> strings = new ArrayList<>();

        for (int i = 0; i < item1.length; i++) {
            if (i!=0&&i!=3&&i!=4){
                strings.add(item1[i]);
            }
        }
        String[] item = strings.toArray(new String[strings.size()]);
        // 获取目标类class
        Class<EcgCount> clazz = EcgCount.class;
        // 实例化
        EcgCount tlVacuumFurnaceLog = clazz.newInstance();

        // 获取对象全部属性
        Field[] fields = clazz.getDeclaredFields();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
    public List<TypeListVo> getTypeList() throws InstantiationException, IllegalAccessException {

        List<TypeListVo> typeListVoList = new ArrayList<>();

        EcgCount ecgTypeList = ecgCountMapper.getEcgTypeList();
        List<ListValueAndLabelVO> logType = ecgCountMapper.getLogType();

        Class<? extends EcgCount> aClass = ecgTypeList.getClass();

        Field[] fields = aClass.getDeclaredFields();
        // 遍历字段并获取值
        for (Field field : fields) {
            field.setAccessible(true); // 设置字段可访问，即使是私有字段
            Object fieldValue = field.get(ecgTypeList);
            System.out.println("属性名称: " + field.getName() + ", 属性值: " + fieldValue);
            if (field.getName().contains("Ecg")&&fieldValue!=null){

                String text = field.getName().substring(0,field.getName().length()-3);
                for (ListValueAndLabelVO listValueAndLabelVO : logType){

                    if (text.equals(listValueAndLabelVO.getValue())){
                        TypeListVo typeListVo = new TypeListVo();
                        typeListVo.setValue(fieldValue.toString());
                        typeListVo.setName(listValueAndLabelVO.getLabel());
                        typeListVoList.add(typeListVo);
                        break;
                    }
                }
            }
        }

        return typeListVoList;
    }


}




