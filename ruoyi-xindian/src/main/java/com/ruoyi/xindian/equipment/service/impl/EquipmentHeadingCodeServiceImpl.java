package com.ruoyi.xindian.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.system.mapper.SysUserMapper;
import com.ruoyi.xindian.equipment.domain.AccountsMsg;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.domain.EquipmentHeadingCode;
import com.ruoyi.xindian.equipment.mapper.EquipmentHeadingCodeMapper;
import com.ruoyi.xindian.equipment.service.AccountsMsgService;
import com.ruoyi.xindian.equipment.service.EquipmentHeadingCodeService;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.domain.OnlineParam;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.mapper.PatientRelationshipMapper;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
* @author 13401
* @description 针对表【equipment_heading_code】的数据库操作Service实现
* @createDate 2023-09-07 17:47:23
*/
@Service
public class EquipmentHeadingCodeServiceImpl extends ServiceImpl<EquipmentHeadingCodeMapper, EquipmentHeadingCode>
    implements EquipmentHeadingCodeService{


    @Resource
    private IEquipmentService equipmentService;

    @Resource
    private IPatientService patientService;

    @Resource
    private WXPublicRequest wxPublicRequest;


    @Resource
    private AccountsMsgService accountsMsgService;

    @Resource
    private AesUtils aesUtils;

    @Resource
    private PatientRelationshipMapper patientRelationshipMapper;


    @Resource
    private IMedicalHistoryService medicalHistoryService;

    @Resource
    private RedisTemplate<String,String> redisTemplate;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private EquipmentHeadingCodeMapper equipmentHeadingCodeMapper;

    @Override
    public EquipmentHeadingCode selectByCode(String code) {
        return equipmentHeadingCodeMapper.selectOne(new QueryWrapper<EquipmentHeadingCode>().eq("heading_code",code));
    }

    @Override
    public EquipmentHeadingCode selectEquipmentCode(String code) {
        return equipmentHeadingCodeMapper.selectOne(new QueryWrapper<EquipmentHeadingCode>().eq("equipment_code",code));
    }


    @Override
    public void selectCodeState(String code) throws Exception {

        String[] split = code.split("=");
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(split[0]);
        if (equipment!=null){

                if (equipment.getEquipmentStatus().equals("True")){
                    SysUser sysUser = new SysUser();

                    sysUser = sysUserMapper.selectUserByPhone(aesUtils.encrypt(split[1]));

                    if (sysUser!=null){

                        wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),equipment.getEquipmentCode(),"设备已绑定成功，请登迈雅云小程序查看","绑定成功");
                    }else {
                        PatientRelationship patientRelationship = new PatientRelationship();
                        patientRelationship.setSonPhone(aesUtils.encrypt(split[1]));
                        List<PatientRelationship> patientRelationships = patientRelationshipMapper.selectPatientRelationshipList(patientRelationship);
                        if (patientRelationships!=null&&patientRelationships.size()>0){
                            sysUser = sysUserMapper.selectUserByPhone(patientRelationships.get(0).getFatherPhone());
                            wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),equipment.getEquipmentCode(),"设备已绑定成功，请登迈雅云小程序查看","绑定成功");
                        }
                    }
                    //给管理员发消息
                    List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
                    Patient patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(split[1]));
                    for (AccountsMsg c : accountsMsgs){
                        wxPublicRequest.sendEquipmentMsgFailTrue(c.getOpenId(),aesUtils.decrypt(patient.getPatientName()));
                    }
                }else {
                    if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeT15!"+split[0]+"="+split[1]))){
                        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+split[0]+"="+split[1],split[1],5, TimeUnit.SECONDS);
                    }
                }
        }
    }

    @Override
    public void selectCodeState15(String code) throws Exception {

        String[] split = code.split("=");

        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(split[0]);
        if (equipment!=null){
            SysUser sysUser = new SysUser();
            if (equipment.getEquipmentStatus().equals("True")){

            }else {
                sysUser = sysUserMapper.selectUserByPhone(aesUtils.encrypt(split[1]));
                if (sysUser!=null){
                    wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),equipment.getEquipmentCode(),"设备绑定失败，请重新绑定或者联系客服","绑定失败");
                }else {
                    PatientRelationship patientRelationship = new PatientRelationship();
                    patientRelationship.setSonPhone(aesUtils.encrypt(split[1]));
                    List<PatientRelationship> patientRelationships = patientRelationshipMapper.selectPatientRelationshipList(patientRelationship);
                    if (patientRelationships!=null&&patientRelationships.size()>0){
                        sysUser = sysUserMapper.selectUserByPhone(patientRelationships.get(0).getFatherPhone());
                        wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),equipment.getEquipmentCode(),"设备绑定失败，请重新绑定或者联系客服","绑定失败");
                    }
                }

                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
                Patient patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(split[1]));
                String decrypt = aesUtils.decrypt(patient.getPatientName());
                if (decrypt.length()>4){
                    decrypt = decrypt.substring(0,3);
                }
                for (AccountsMsg c : accountsMsgs){
                    wxPublicRequest.sendEquipmentMsgFail(c.getOpenId(),decrypt,split[1],split[0]);
                }
                redisTemplate.opsForValue().set("getEquipmentCodeAgainTwo!"+equipment.getEquipmentCode()+"="+split[1],equipment.getEquipmentCode(),5, TimeUnit.SECONDS);
                redisTemplate.opsForValue().set("getEquipmentCodeAgainT15!"+equipment.getEquipmentCode()+"="+split[1],equipment.getEquipmentCode(),30,TimeUnit.MINUTES);
            }
        }

    }

    @Override
    public void selectCodeStateAgain(String code) throws Exception {


        String[] split = code.split("=");
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(split[0]);
        if (equipment!=null){

            if (equipment.getEquipmentStatus().equals("True")){
                SysUser sysUser = new SysUser();

                sysUser = sysUserMapper.selectUserByPhone(aesUtils.encrypt(split[1]));

                if (sysUser!=null){

                    wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),equipment.getEquipmentCode(),"设备已绑定成功，无需再重复绑定","绑定成功");
                }else {
                    PatientRelationship patientRelationship = new PatientRelationship();
                    patientRelationship.setSonPhone(aesUtils.encrypt(split[1]));
                    List<PatientRelationship> patientRelationships = patientRelationshipMapper.selectPatientRelationshipList(patientRelationship);
                    if (patientRelationships!=null&&patientRelationships.size()>0){
                        sysUser = sysUserMapper.selectUserByPhone(patientRelationships.get(0).getFatherPhone());
                        wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),equipment.getEquipmentCode(),"设备已绑定成功，无需再重复绑定","绑定成功");
                    }
                }

            }else {
                if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainT15!"+split[0]+"="+split[1]))){
                    redisTemplate.opsForValue().set("getEquipmentCodeAgainTwo!"+split[0]+"="+split[1],split[1],5, TimeUnit.SECONDS);
                }
            }
        }


    }



    public void getCodeStatus(String code){
        String[] split = code.split("=");
        OnlineParam onlineParam = new OnlineParam("所有");
        String url = "https://server.mindyard.cn:84/get_device";
//        String url = "http://202.102.249.124:84/get_device";
        //请求
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("user", "zzu");
        headers.set("password", "zzu123");
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<OnlineParam> request = new HttpEntity<OnlineParam>(onlineParam, headers);
        ResponseEntity<String> responseEntity = null;
        try {
            responseEntity = restTemplate.postForEntity(url, request, String.class);
        } catch (RestClientException e) {
            System.out.println(e);
        }
        String responseEntityBody = responseEntity.getBody();
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        String splitData = splitData(responseEntityBody, "[", "]");
        String s = removeDoubleQuotes(splitData);
        String[] devList = s.split(",");
        String Sn = "";

        for (String c :devList){
            if (c.equals(split[0])){
                Sn = c;
            }
        }
        if (StringUtils.isNotEmpty(Sn)){
            String[] strings = new String[1];
            strings[0] = Sn;
            equipmentService.updateEquipmentStatus(strings);
        }else {
            if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainT15!"+split[0]+"="+split[1]))||Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeT15!"+split[0]+"="+split[1]))){
                redisTemplate.opsForValue().set("getEquipmentCodeSF!"+split[0]+"="+split[1],code,12,TimeUnit.SECONDS);
            }

        }

    }

    public String splitData(String str, String strStart, String strEnd) {
        String tempStr;
        tempStr = str.substring(str.indexOf(strStart) + 1, str.lastIndexOf(strEnd));
        return tempStr;
    }

    public static String removeDoubleQuotes(String result) {
        //去掉" "号
        return result.replace("\"", "");
    }
}




