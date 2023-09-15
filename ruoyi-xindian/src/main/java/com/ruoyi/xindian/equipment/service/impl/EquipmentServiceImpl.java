package com.ruoyi.xindian.equipment.service.impl;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.xindian.hospital.domain.AssociatedHospital;
import com.ruoyi.xindian.hospital.domain.Hospital;
import com.ruoyi.xindian.hospital.mapper.AssociatedHospitalMapper;
import com.ruoyi.xindian.hospital.mapper.HospitalMapper;
import com.ruoyi.xindian.patient.domain.Patient;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.xindian.equipment.mapper.EquipmentMapper;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 设备Service业务层处理
 *
 * @author hanhan
 * @date 2022-08-20
 */
@Service
public class EquipmentServiceImpl implements IEquipmentService {
    @Autowired
    private EquipmentMapper equipmentMapper;

    @Resource
    private AssociatedHospitalMapper associatedHospitalMapper;

    @Resource
    private HospitalMapper hospitalMapper;

    /**
     * 查询设备
     *
     * @param equipmentId 设备主键
     * @return 设备
     */
    @Override
    public Equipment selectEquipmentByEquipmentId(Long equipmentId) {
        return equipmentMapper.selectEquipmentByEquipmentId(equipmentId);
    }

    /**
     * 查询设备列表
     *
     * @param equipment 设备
     * @return 设备
     */
    @Override
    public List<Equipment> selectEquipmentList(Equipment equipment) {

        return equipmentMapper.selectEquipmentList(equipment);
    }

    /**
     * 新增设备
     *
     * @param equipment 设备
     * @return 结果
     */
    @Override
    public int insertEquipment(Equipment equipment) {
        return equipmentMapper.insertEquipment(equipment);
    }

    /**
     * 修改设备
     *
     * @param equipment 设备
     * @return 结果
     */
    @Override
    public int updateEquipment(Equipment equipment) {
        return equipmentMapper.updateEquipment(equipment);
    }

    /**
     * 批量删除设备
     *
     * @param equipmentIds 需要删除的设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByEquipmentIds(Long[] equipmentIds) {
        return equipmentMapper.deleteEquipmentByEquipmentIds(equipmentIds);
    }

    /**
     * 删除设备信息
     *
     * @param equipmentId 设备主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentByEquipmentId(Long equipmentId) {
        return equipmentMapper.deleteEquipmentByEquipmentId(equipmentId);
    }

    @Override
    public void updateEquipmentStatusAll() {
        equipmentMapper.updateEquipmentStatusAll();
    }

    @Override
    public void updateEquipmentStatus(String[] statusList) {
        equipmentMapper.updateEquipmentStatus(statusList);
    }

    @Override
    public Equipment selectEquipmentByEquipmentCode(String equipmentCode){
        return equipmentMapper.selectEquipmentByEquipmentCode(equipmentCode);
    }



    @Transactional
    @Override
    public void batchInsert(String filePath) throws Exception {

            FileInputStream inputStream = new FileInputStream(filePath);
            XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
            XSSFSheet sheet = workbook.getSheetAt(0);//5表示excel表的第几页，从下表0开始
            List<Equipment> equipmentList = new ArrayList<>();
            for (int i = 1; i <= sheet.getLastRowNum(); i++) {
                XSSFRow row = sheet.getRow(i);
                String name = row.getCell(1).getStringCellValue();

                //因为id自增，所以不加id，后期可以自行修改，其他参数自行修改
                Equipment equipment = new Equipment( name, "V2023-06-19", "False", "29", "JECGsingleWL");
                equipmentList.add(equipment);
            }
            for (int i = 0; i < equipmentList.size(); i ++) {
                System.out.println(equipmentList.get(i));
                equipmentMapper.insertEquipment(equipmentList.get(i));
            }

    }


    @Transactional
    @Override
    public List<String> batchSelect(String filePath,Integer sum) throws Exception {

        FileInputStream inputStream = new FileInputStream(filePath);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(sum);//5表示excel表的第几页，从下表0开始
        List<Equipment> equipmentList = new ArrayList<>();
        List<String> list = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            XSSFRow row = sheet.getRow(i);
            String name = row.getCell(1).getStringCellValue();

            Equipment equipment1 = equipmentMapper.selectEquipmentByEquipmentCode(name);

            if (equipment1==null){

                list.add(name);

            }

            //因为id自增，所以不加id，后期可以自行修改，其他参数自行修改
//                Equipment equipment = new Equipment( name, "V2023-06-19", "False", "29", "JECGsingleWL");
//                equipmentList.add(equipment);
        }
       return list;

    }

    @Override
    public int selectEquipmentOnlineNum() {
        return equipmentMapper.selectEquipmentOnlineNum();
    }


}
