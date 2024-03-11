package com.ruoyi.xindian.equipment.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
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
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.relationship.domain.PatientRelationship;
import com.ruoyi.xindian.relationship.mapper.PatientRelationshipMapper;
import com.ruoyi.xindian.vipPatient.service.SxReportUnscrambleService;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
* @author 13401
* @description 针对表【equipment_heading_code】的数据库操作Service实现
* @createDate 2023-09-07 17:47:23
*/
@Service
public class EquipmentHeadingCodeServiceImpl extends ServiceImpl<EquipmentHeadingCodeMapper, EquipmentHeadingCode>
    implements EquipmentHeadingCodeService{


    private final Lock lock = new ReentrantLock();

    @Resource
    private TokenService tokenService;

    @Resource
    private SxReportUnscrambleService sxReportUnscrambleService;
    @Resource
    private IPatientManagementService patientManagementService;

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





    /**
     * 查询善行设备管理
     *
     * @param codeId 善行设备管理主键
     * @return 善行设备管理
     */
    @Override
    public EquipmentHeadingCode selectEquipmentHeadingCodeByCodeId(Long codeId)
    {
        return equipmentHeadingCodeMapper.selectEquipmentHeadingCodeByCodeId(codeId);
    }

    /**
     * 查询善行设备管理列表
     *
     * @param equipmentHeadingCode 善行设备管理
     * @return 善行设备管理
     */
    @Override
    public List<EquipmentHeadingCode> selectEquipmentHeadingCodeList(EquipmentHeadingCode equipmentHeadingCode)
    {
        return equipmentHeadingCodeMapper.selectEquipmentHeadingCodeList(equipmentHeadingCode);
    }

    /**
     * 新增善行设备管理
     *
     * @param equipmentHeadingCode 善行设备管理
     * @return 结果
     */
    @Override
    public int insertEquipmentHeadingCode(EquipmentHeadingCode equipmentHeadingCode)
    {
        String equipmentCode = equipmentHeadingCode.getHeadingCode();
        if (equipmentCode!=null){
           if (equipmentCode.length()>17){
               equipmentCode=equipmentCode.substring(0,17);
           }
        }
        Equipment equipment = new Equipment();
        equipment.setEquipmentCode(equipmentCode);
        equipment.setEquipmentType("DECG12SX");
        equipment.setHospitalCode("29");
        equipment.setEquipmentVersion("v1.1");
        equipment.setEquipmentStatus("False");
        equipmentService.insertEquipment(equipment);
        return equipmentHeadingCodeMapper.insertEquipmentHeadingCode(equipmentHeadingCode);
    }

    /**
     * 修改善行设备管理
     *
     * @param equipmentHeadingCode 善行设备管理
     * @return 结果
     */
    @Override
    public int updateEquipmentHeadingCode(EquipmentHeadingCode equipmentHeadingCode)
    {
        return equipmentHeadingCodeMapper.updateEquipmentHeadingCode(equipmentHeadingCode);
    }

    /**
     * 批量删除善行设备管理
     *
     * @param codeIds 需要删除的善行设备管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentHeadingCodeByCodeIds(Long[] codeIds)
    {
        return equipmentHeadingCodeMapper.deleteEquipmentHeadingCodeByCodeIds(codeIds);
    }

    /**
     * 删除善行设备管理信息
     *
     * @param codeId 善行设备管理主键
     * @return 结果
     */
    @Override
    public int deleteEquipmentHeadingCodeByCodeId(Long codeId)
    {
        return equipmentHeadingCodeMapper.deleteEquipmentHeadingCodeByCodeId(codeId);
    }

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

                if (equipment.getEquipmentStatus().equals("True")&&equipment.getPatientPhone().equals(aesUtils.encrypt(split[1]))){
                    SysUser sysUser = new SysUser();
                    redisTemplate.delete("getEquipmentCodeT15!"+split[0]+"="+split[1]);
//                    redisTemplate.delete("getEquipmentCodeAgainTwo!"+split[0]+"="+split[1]);
//                    redisTemplate.delete("getEquipmentCodeAgainT15!"+split[0]+"="+split[1]);
                    sysUser = sysUserMapper.selectUserByPhone(aesUtils.encrypt(split[1]));
                    EquipmentHeadingCode headingCode = equipmentHeadingCodeMapper.selectOne(new QueryWrapper<EquipmentHeadingCode>().eq("heading_code", equipment.getEquipmentCode()));
                    if (sysUser!=null){

                        wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),headingCode.getEquipmentCode(),"设备已绑定成功，请登迈雅云小程序查看","绑定成功");
                    }else {
                        PatientRelationship patientRelationship = new PatientRelationship();
                        patientRelationship.setSonPhone(aesUtils.encrypt(split[1]));
                        List<PatientRelationship> patientRelationships = patientRelationshipMapper.selectPatientRelationshipList(patientRelationship);
                        if (patientRelationships!=null&&patientRelationships.size()>0){
                            sysUser = sysUserMapper.selectUserByPhone(patientRelationships.get(0).getFatherPhone());
                            wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),headingCode.getEquipmentCode(),"设备已绑定成功，请登迈雅云小程序查看","绑定成功");
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
                EquipmentHeadingCode headingCode = equipmentHeadingCodeMapper.selectOne(new QueryWrapper<EquipmentHeadingCode>().eq("heading_code", equipment.getEquipmentCode()));
                if (sysUser!=null){
                    wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),headingCode.getEquipmentCode(),"设备绑定失败，请重新绑定或者联系客服","绑定失败");
                }else {
                    PatientRelationship patientRelationship = new PatientRelationship();
                    patientRelationship.setSonPhone(aesUtils.encrypt(split[1]));
                    List<PatientRelationship> patientRelationships = patientRelationshipMapper.selectPatientRelationshipList(patientRelationship);
                    if (patientRelationships!=null&&patientRelationships.size()>0){
                        sysUser = sysUserMapper.selectUserByPhone(patientRelationships.get(0).getFatherPhone());
                        wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),headingCode.getEquipmentCode(),"设备绑定失败，请重新绑定或者联系客服","绑定失败");
                    }
                }

                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
                Patient patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(split[1]));
                String decrypt = aesUtils.decrypt(patient.getPatientName());
                if (decrypt.length()>4){
                    decrypt = decrypt.substring(0,3);
                }
                for (AccountsMsg c : accountsMsgs){
                    wxPublicRequest.sendEquipmentMsgFail(c.getOpenId(),decrypt,split[1],headingCode.getEquipmentCode());
                }
//                redisTemplate.opsForValue().set("getEquipmentCodeAgainTwo!"+equipment.getEquipmentCode()+"="+split[1],equipment.getEquipmentCode(),5, TimeUnit.SECONDS);
//                redisTemplate.opsForValue().set("getEquipmentCodeAgainT15!"+equipment.getEquipmentCode()+"="+split[1],equipment.getEquipmentCode(),30,TimeUnit.MINUTES);
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
                EquipmentHeadingCode headingCode = equipmentHeadingCodeMapper.selectOne(new QueryWrapper<EquipmentHeadingCode>().eq("heading_code", equipment.getEquipmentCode()));
                if (sysUser!=null){

                    wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),headingCode.getEquipmentCode(),"设备已绑定成功，无需再重复绑定","绑定成功");
                }else {
                    PatientRelationship patientRelationship = new PatientRelationship();
                    patientRelationship.setSonPhone(aesUtils.encrypt(split[1]));
                    List<PatientRelationship> patientRelationships = patientRelationshipMapper.selectPatientRelationshipList(patientRelationship);
                    if (patientRelationships!=null&&patientRelationships.size()>0){
                        sysUser = sysUserMapper.selectUserByPhone(patientRelationships.get(0).getFatherPhone());
                        wxPublicRequest.boundEquipmentMsg(sysUser.getOpenId(),headingCode.getEquipmentCode(),"设备已绑定成功，无需再重复绑定","绑定成功");
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

    @Override
    public Boolean addSXReport(String phone, String pId) throws Exception {
        return null;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean submitDetectionReport(String pId, HttpServletRequest request) throws Exception {

        lock.lock();
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser==null){
               throw new ServiceException("请先登录");
            }
            Long userId = loginUser.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("submitDetectionReport"+userId))){
                throw new ServiceException("请勿重复点击");
            }
            redisTemplate.opsForValue().set("submitDetectionReport"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            if (StringUtil.isEmpty(pId)){
                throw new ServiceException("参数错误");
            }

            PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);
            if (patientManagement==null){
                throw new ServiceException("报告不存在");
            }
            if (patientManagement.getSxReportStatus()!=null&&patientManagement.getSxReportStatus()==1){
                throw new ServiceException("报告已提交");
            }
            if (StringUtils.isEmpty(patientManagement.getPatientPhone())){
                throw new ServiceException("报告不存在");
            }
            if (StringUtils.isEmpty(patientManagement.getEcgType())){
                throw new ServiceException("报告不存在");
            }
            if (!patientManagement.getEcgType().contains("DECG")){
                throw new ServiceException("请选择动态报告进行提交");
            }

            Patient patient = patientService.selectPatientByPatientPhone(patientManagement.getPatientPhone());

            if (patient==null){
                throw new ServiceException("患者信息不存在");
            }
            int i = sxReportUnscrambleService.updateSxReportUnscrambleByNumReduce(aesUtils.decrypt(loginUser.getUser().getPhonenumber()));
            if (i>0){
                Date connectionTime = patientManagement.getConnectionTime();
                // 获取当前时间
                Date currentDate = new Date();
                // 计算固定时间加上 24 小时后的时间，并获取其与当前时间的时间差
                long diffInMinutes = calculateMinutesRemaining(currentDate, addHoursToDate(connectionTime, 24));

                if (diffInMinutes > 0) {
                    System.out.println("当前时间在给定固定时间的前面");
                    System.out.println("距离固定时间加上 24 小时还有 " + diffInMinutes + " 分钟");
                    redisTemplate.opsForValue().set("addSXReportSubmitOrder:"+pId,pId,diffInMinutes, TimeUnit.MINUTES);
                } else {
                    throw new ServiceException("报告已结束监测模式");
                }
                PatientManagement patientManagement1 = new PatientManagement();
                patientManagement1.setpId(pId);
                patientManagement1.setSxReportStatus(1);
                patientManagementService.updatePatientManagement(patientManagement1);
                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
                for (AccountsMsg c : accountsMsgs){
                    wxPublicRequest.sendSXDetectionMsg(c.getOpenId(),aesUtils.decrypt(patientManagement.getPatientName()),aesUtils.decrypt(patientManagement.getPatientPhone()),pId,"心电动态实时检测");
                }

                return true;
            }
            throw new ServiceException("网络开小差~~，请稍后再试一次");

        } finally {
            lock.unlock();
        }
    }

    private static Date addHoursToDate(Date date, int hours) {
        long time = date.getTime();
        return new Date(time + TimeUnit.HOURS.toMillis(hours));
    }

    // 计算两个时间之间的时间差，返回分钟数
    private static long calculateMinutesRemaining(Date current, Date target) {
        long diffInMillies = target.getTime() - current.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
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




