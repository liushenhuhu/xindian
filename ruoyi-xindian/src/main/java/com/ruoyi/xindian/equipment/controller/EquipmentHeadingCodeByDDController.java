package com.ruoyi.xindian.equipment.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.model.LoginUser;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.framework.web.service.TokenService;
import com.ruoyi.xindian.equipment.domain.AccountsMsg;
import com.ruoyi.xindian.equipment.domain.Equipment;
import com.ruoyi.xindian.equipment.domain.EquipmentHeadingCode;
import com.ruoyi.xindian.equipment.service.AccountsMsgService;
import com.ruoyi.xindian.equipment.service.EquipmentHeadingCodeService;
import com.ruoyi.xindian.equipment.service.IEquipmentService;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.hospital.service.IDoctorService;
import com.ruoyi.xindian.lease.domain.Lease;
import com.ruoyi.xindian.lease.domain.LeaseDetails;
import com.ruoyi.xindian.lease.domain.LeaseLog;
import com.ruoyi.xindian.lease.mapper.LeaseDetailsMapper;
import com.ruoyi.xindian.lease.service.LeaseDetailsService;
import com.ruoyi.xindian.lease.service.LeaseLogService;
import com.ruoyi.xindian.lease.service.LeaseService;
import com.ruoyi.xindian.medical.domain.MedicalHistory;
import com.ruoyi.xindian.medical.service.IMedicalHistoryService;
import com.ruoyi.xindian.patient.domain.Patient;
import com.ruoyi.xindian.patient.service.IPatientService;
import com.ruoyi.xindian.patient_management.controller.OnlineController;
import com.ruoyi.xindian.patient_management.domain.OnlineParam;
import com.ruoyi.xindian.patient_management.domain.PatientManagement;
import com.ruoyi.xindian.patient_management.service.IPatientManagementService;
import com.ruoyi.xindian.util.WxUtil;
import com.ruoyi.xindian.vipPatient.domain.SxReportUnscramble;
import com.ruoyi.xindian.vipPatient.service.SxReportUnscrambleService;
import com.ruoyi.xindian.wx_pay.util.WXPublicRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/headingCode/dd")
public class EquipmentHeadingCodeByDDController extends BaseController {



    @Value("${sx.url}")
    private String sxUrl;


    @Autowired
    private LeaseDetailsService leaseDetailsService;


    @Resource
    private EquipmentHeadingCodeService equipmentHeadingCodeService;


    @Resource
    private IEquipmentService equipmentService;

    @Resource
    private IPatientService patientService;

    @Resource
    private WXPublicRequest wxPublicRequest;


    @Resource
    private IDoctorService doctorService;

    @Resource
    private SxReportUnscrambleService sxReportUnscrambleService;

    @Resource
    private AccountsMsgService accountsMsgService;

    @Resource
    private AesUtils aesUtils;


    private final Lock lock = new ReentrantLock();
    @Resource
    private IMedicalHistoryService medicalHistoryService;

    @Resource
    private OnlineController onlineController;

    @Resource
    private RestTemplate restTemplate;

    @Resource
    private RedisTemplate<String,String> redisTemplate;


    @Resource
    private TokenService tokenService;

    @Resource
    private IPatientManagementService patientManagementService;

    @Autowired
    private LeaseService leaseService;


    @Resource
    private LeaseLogService leaseLogService;


    @Resource
    private LeaseDetailsMapper leaseDetailsMapper;

    /**
     * 查询善行设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('headingCode:headingCode:list')")
    @GetMapping("/list")
    public TableDataInfo list(EquipmentHeadingCode equipmentHeadingCode)
    {
        startPage();
        List<EquipmentHeadingCode> list = equipmentHeadingCodeService.selectEquipmentHeadingCodeList(equipmentHeadingCode);
        return getDataTable(list);
    }

    /**
     * 导出善行设备管理列表
     */
    @PreAuthorize("@ss.hasPermi('headingCode:headingCode:export')")
    @Log(title = "善行设备管理", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, EquipmentHeadingCode equipmentHeadingCode)
    {
        List<EquipmentHeadingCode> list = equipmentHeadingCodeService.selectEquipmentHeadingCodeList(equipmentHeadingCode);
        ExcelUtil<EquipmentHeadingCode> util = new ExcelUtil<EquipmentHeadingCode>(EquipmentHeadingCode.class);
        util.exportExcel(response, list, "善行设备管理数据");
    }

    /**
     * 获取善行设备管理详细信息
     */
    @PreAuthorize("@ss.hasPermi('headingCode:headingCode:query')")
    @GetMapping(value = "/{codeId}")
    public AjaxResult getInfo(@PathVariable("codeId") Long codeId)
    {
        return AjaxResult.success(equipmentHeadingCodeService.selectEquipmentHeadingCodeByCodeId(codeId));
    }

    /**
     * 新增善行设备管理
     */
    @PreAuthorize("@ss.hasPermi('headingCode:headingCode:add')")
    @Log(title = "善行设备管理", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody EquipmentHeadingCode equipmentHeadingCode)
    {
        return toAjax(equipmentHeadingCodeService.insertEquipmentHeadingCode(equipmentHeadingCode));
    }

    /**
     * 修改善行设备管理
     */
    @PreAuthorize("@ss.hasPermi('headingCode:headingCode:edit')")
    @Log(title = "善行设备管理", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody EquipmentHeadingCode equipmentHeadingCode)
    {
        return toAjax(equipmentHeadingCodeService.updateEquipmentHeadingCode(equipmentHeadingCode));
    }

    /**
     * 删除善行设备管理
     */
    @PreAuthorize("@ss.hasPermi('headingCode:headingCode:remove')")
    @Log(title = "善行设备管理", businessType = BusinessType.DELETE)
    @DeleteMapping("/{codeIds}")
    public AjaxResult remove(@PathVariable Long[] codeIds)
    {
        return toAjax(equipmentHeadingCodeService.deleteEquipmentHeadingCodeByCodeIds(codeIds));
    }






//
//    /**
//     * 查询设备编号以及给管理员发送消息
//     * @param code
//     * @param phone
//     * @return
//     * @throws Exception
//     */
//    @GetMapping("/getEquipmentCode")
//    public AjaxResult getEquipmentCode(String code, String phone, HttpServletRequest request) throws Exception {
//
//        if (code.length()>17){
//            code=code.substring(0,17);
//        }
//        String encrypt = aesUtils.encrypt(phone);
//        EquipmentHeadingCode equipmentHeadingCode = new EquipmentHeadingCode();
//
//        equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);
//        if (equipmentHeadingCode==null){
//           equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
//           if (equipmentHeadingCode==null){
//               return AjaxResult.error("识别码不存在");
//           }
//        }
//        getCodeStatus(equipmentHeadingCode.getHeadingCode());
//        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
//        if (equipment==null){
//            return AjaxResult.error("SN码不存在");
//        }
//        Patient patient = patientService.selectPatientByPatientPhone(encrypt);
//        if (patient==null){
//            return AjaxResult.error("患者信息不存在");
//        }
//        if (equipment.getEquipmentStatus().equals("True")){
//            return AjaxResult.error(207,"当前设备已被使用，请更换设备后重试");
//        }
//
//
//        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(encrypt);
//        EquipmentHeadingCode finalEquipmentHeadingCode = equipmentHeadingCode;
//        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
//            redisTemplate.delete("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone);
//        }
//        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
//            redisTemplate.delete("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone);
//        }
//        ExecutorService executorService = Executors.newSingleThreadExecutor();
//        CompletableFuture.runAsync(() ->{
//            System.out.println("异步线程 =====> 开始推送公众号消息 =====> " + new Date());
//            try{
//                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
//                for (AccountsMsg c : accountsMsgs){
//                    wxPublicRequest.sendEquipmentMsgNew(finalEquipmentHeadingCode.getEquipmentCode(),c.getOpenId(),
//                            aesUtils.decrypt(patient.getPatientName())+"/"+patient.getPatientSex(),aesUtils.decrypt(patient.getPatientPhone()),medicalHistory.getHeight()+"/"+medicalHistory.getWeight(),patient.getBirthDay());
//                }
//            }catch (Exception e){
//                System.out.println(e);
//            }
//            System.out.println("异步线程 =====> 结束推送公众号消息 =====> " + new Date());
//        },executorService);
//        executorService.shutdown(); // 回收线程池
//
//        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),5, TimeUnit.SECONDS);
//        redisTemplate.opsForValue().set("getEquipmentCodeT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),30,TimeUnit.MINUTES);
//        redisTemplate.opsForValue().set("getEquipmentCodeSF!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),12,TimeUnit.SECONDS);
//        return AjaxResult.success();
//
//    }
//



    /**
     * 查询设备编号是否存在数据库中
     * @param code
     * @return
     * @throws Exception
     */
    @GetMapping("/getEquipmentExist")
    public AjaxResult getEquipmentExist(String code) throws Exception {
        if (code == null || code.isEmpty()) {
            return AjaxResult.success(false);
        }
        if (code.length() > 17) {
            code = code.substring(0, 17);
        }
        EquipmentHeadingCode equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);

        if (equipmentHeadingCode == null) {
            equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
        }
        if (equipmentHeadingCode == null) {
            Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(code);
            return AjaxResult.success(equipment != null);
        } else {
            Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
            return AjaxResult.success(equipment != null);
        }
    }






    /**
     * 查询设备编号以及给管理员发送消息
     * @param code
     * @param phone
     * @return
     * @throws Exception
     */
    @GetMapping("/getEquipmentCode")
    public AjaxResult getSXUserAdd(String code, String phone, HttpServletRequest request) throws Exception {

        if (code == null || code.isEmpty()) {
            return AjaxResult.error("识别码不存在");
        }
        if (phone == null || phone.isEmpty()) {
            return AjaxResult.error("手机号码不存在");
        }
        if (code.length()>17){
            code=code.substring(0,17);
        }
        String encrypt = aesUtils.encrypt(phone);
        EquipmentHeadingCode equipmentHeadingCode = new EquipmentHeadingCode();

        equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);
        if (equipmentHeadingCode==null){
            equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
            if (equipmentHeadingCode==null){
                return AjaxResult.error("识别码不存在");
            }
        }
        getCodeStatus(equipmentHeadingCode.getHeadingCode());
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
        if (equipment==null){
            return AjaxResult.error("SN码不存在");
        }
        Patient patient = patientService.selectPatientByPatientPhone(encrypt);
        if (patient==null){
            return AjaxResult.error("患者信息不存在");
        }
        if (equipment.getEquipmentStatus().equals("True")){
            return AjaxResult.error(207,"当前设备已被使用，请更换设备后重试");
        }
        String sxUserId = getSXUserId(patient);
        if (sxUserId==null){
            sxUserId = getSXUserId(patient);
        }
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("userId",sxUserId);
        paramsMap.put("mac", equipment.getEquipmentCode());

        HttpEntity<Map<String, Object>> request1 = new HttpEntity<>(paramsMap,headers);

        String url = sxUrl+"/bmecg/third/report/zzdx/bindDevice";
        HashMap<String,String> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request1, HashMap.class);
        }catch (Exception e){
            System.out.println(e);
        }


        LeaseDetails leaseDetails1 = leaseDetailsService.selectLeaseDetailsByEquipmentCode(equipment.getEquipmentCode());
        if (leaseDetails1!=null){
            if (!StringUtils.isNotEmpty(leaseDetails1.getStatus())&&!leaseDetails1.getStatus().equals("1")){
                LeaseDetails leaseDetails = new LeaseDetails();
                leaseDetails.setUsername(aesUtils.decrypt(patient.getPatientName()));
                leaseDetails.setCreateTime(new Date());
                leaseDetails.setPhone(phone);
                leaseDetails.setStatus("1");
                leaseDetails.setEquipmentCode(equipment.getEquipmentCode());
                int update = leaseDetailsMapper.update(leaseDetails, new LambdaQueryWrapper<LeaseDetails>().eq(LeaseDetails::getEquipmentCode, leaseDetails.getEquipmentCode()));

                LeaseLog leaseLog = new LeaseLog();
                leaseLog.setUsername(aesUtils.decrypt(patient.getPatientName()));
                leaseLog.setPhone(phone);
                leaseLog.setStatus("1");
                leaseLog.setEquipmentCode(equipment.getEquipmentCode());
                leaseLog.setEquipmentType("心电衣");
                leaseLog.setCreateTime(new Date());
                leaseLog.setUpdateTime(new Date());
                leaseLogService.save(leaseLog);
            }

        }

        EquipmentHeadingCode finalEquipmentHeadingCode = equipmentHeadingCode;
        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),5, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("getEquipmentCodeT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),30,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("getEquipmentCodeSF!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),12,TimeUnit.SECONDS);
        if (sendMessageVo!=null){
            if (sendMessageVo.get("resultCode").equals("200")){
                return AjaxResult.success("绑定成功,请等待2-3分钟后重新打开");
            }else {
                return AjaxResult.error(sendMessageVo.get("resultMsg"));
            }
        }
        return AjaxResult.success("申请绑定成功");
    }




    /**
     * 获取患者的userId
     * @param patient
     * @return
     * @throws Exception
     */
    public String getSXUserId(Patient patient) throws Exception {
        if (patient==null){
            return null;
        }
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(patient.getPatientPhone());

        SimpleDateFormat simpleDateFormat  = new SimpleDateFormat("yyyy-MM-dd");

        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("name",aesUtils.decrypt(patient.getPatientName()) );
        paramsMap.put("sex",patient.getPatientSex() );
        try {
            paramsMap.put("birthday",simpleDateFormat.format(patient.getBirthDay()) );
        }catch (Exception e){
            paramsMap.put("birthday","2000-12-12");
        }
        paramsMap.put("phone",aesUtils.decrypt(patient.getPatientPhone()) );
        paramsMap.put("height",medicalHistory.getHeight() );
        paramsMap.put("weight",medicalHistory.getWeight() );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);

        String url = sxUrl+"/bmecg/third/ecg/zzdx/user";
        HashMap<String,Map<String,Object>> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        }catch (Exception e){
            System.out.println(e);
        }
        Map<String,Object> resultData = sendMessageVo.get("resultData");
        if (resultData!=null){
            return resultData.get("uid").toString();
        }
        redisTemplate.delete("EquipmentCodeAccess_token");

        return null;
    }


    /**
     * 下载PDF
     * @param orderId
     * @return
     * @throws Exception
     */
    @GetMapping("/downloadPFD")
    public AjaxResult downloadPFD(String orderId) throws Exception {
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
        String url = sxUrl+"/bmecg/third/report/download?orderId="+orderId;
        ResponseEntity<byte[]> sendMessageVo=null;
        try {
             sendMessageVo = restTemplate.exchange(url, HttpMethod.GET,formEntity, byte[].class);
        }catch (Exception e){
            redisTemplate.delete("EquipmentCodeAccess_token");
            System.out.println(e);
        }
        fileToBytes(sendMessageVo.getBody(),"D:\\Users\\Downloads\\","test.pdf");
        return null;
    }





    @GetMapping("/submitSXReport")
    public AjaxResult submitSXReport(String pId,HttpServletRequest request) throws Exception {

        lock.lock();
        try {
            LoginUser loginUser = tokenService.getLoginUser(request);
            if (loginUser==null){
                return AjaxResult.error("请先登录");
            }
            Long userId = loginUser.getUser().getUserId();
            if (Boolean.TRUE.equals(redisTemplate.hasKey("submitSXReport"+userId))){
                return AjaxResult.error("请勿重复点击");
            }
            redisTemplate.opsForValue().set("submitSXReport"+userId, String.valueOf(userId),5, TimeUnit.SECONDS);

            if (StringUtil.isEmpty(pId)){
                return AjaxResult.error("参数错误");
            }

            PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);
            if (patientManagement==null){
                return AjaxResult.error("报告不存在");
            }
            if (StringUtils.isEmpty(patientManagement.getPatientPhone())){
                return AjaxResult.error("报告不存在");
            }

            String patientPhone = patientManagement.getPatientPhone();
            Patient patient = patientService.selectPatientByPatientPhone(patientPhone);
            String sxUserId = getSXUserId(patient);
            if (sxUserId==null){
                sxUserId =getSXUserId(patient);
            }

            LinkedHashMap<String, Object> sxDateList = getSXDateList(sxUserId, pId);
            if (sxDateList==null){
                return AjaxResult.error("数据采集不够24小时,请注意数据是否采集完成");
            }
            Integer notifyStatus = (Integer)sxDateList.get("notifyStatus");
            if (notifyStatus!=null&&notifyStatus==1){
                return AjaxResult.error("该报告已提交诊断");
            }
            Integer fuwaiSendStatus = (Integer)sxDateList.get("fuwaiSendStatus");

            if (fuwaiSendStatus!=null&&fuwaiSendStatus==2){
                return AjaxResult.error("该报告已提交诊断");
            }

            Lease lease = new Lease();
            lease.setGiveBack("0");
            List<String> leases = leaseService.selectLeaseList(lease).stream().map(Lease::getPhone).collect(Collectors.toList());
            if (!leases.contains(aesUtils.decrypt(patientPhone))){
                SxReportUnscramble sxReportUnscramble = sxReportUnscrambleService.selectSxReportUnscrambleById(aesUtils.decrypt(loginUser.getUser().getPhonenumber()));
                if (sxReportUnscramble==null){
                    return AjaxResult.error(302,"服务次数不够，请先购买");
                }
                if (sxReportUnscramble.getVipNum()==null||sxReportUnscramble.getVipNum()<=0){
                    return AjaxResult.error(302,"服务次数不够，请先购买");
                }
                int i = sxReportUnscrambleService.updateSxReportUnscrambleByNumReduce(aesUtils.decrypt(loginUser.getUser().getPhonenumber()));
            }




            ifSubmitOrder(pId);
            PatientManagement patientManagement1 = new PatientManagement();
            patientManagement1.setpId(pId);
            patientManagement1.setSxReportStatus(1);
            patientManagementService.updatePatientManagement(patientManagement1);
            return AjaxResult.success("提交成功");


        }catch (Exception e){
            System.out.println(e);
            return AjaxResult.error("网络开小差~~，请稍后再试一次");
        }finally {
            lock.unlock();
        }
    }


    /**
     * 判断提交的时间是否在规定时间内
     * @param pId
     */
    @GetMapping("/addSXReport")
    public void ifSubmitOrder(String pId) throws Exception {

        PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);if (patientManagement==null){
            return;
        }
        if (patientManagement.getConnectionTime()==null){
            return;
        }

        if (StringUtils.isEmpty(patientManagement.getPatientPhone())){
            return;
        }

        String phone = aesUtils.decrypt(patientManagement.getPatientPhone());

        Date connectionTime = patientManagement.getConnectionTime();

        // 获取当前时间
        Date currentDate = new Date();
        // 计算固定时间加上 24 小时后的时间，并获取其与当前时间的时间差
        long diffInMinutes = calculateMinutesRemaining(currentDate, addHoursToDate(connectionTime, 23));

        if (diffInMinutes > 0) {
            System.out.println("当前时间在给定固定时间的前面");
            System.out.println("距离固定时间加上 24 小时还有 " + diffInMinutes + " 分钟");
            redisTemplate.opsForValue().set("addSXReportSubmitOrder:"+pId,pId,diffInMinutes, TimeUnit.MINUTES);
        } else {
            Boolean b = addSXReport(phone, pId);
            if (!b){
                redisTemplate.opsForValue().set("addSXReportSubmitOrder:"+pId,pId,5, TimeUnit.MINUTES);
            }
        }

    }

    // 将指定的小时数加到给定的时间上，并返回新的 Date 对象
    private static Date addHoursToDate(Date date, int hours) {
        long time = date.getTime();
        return new Date(time + TimeUnit.HOURS.toMillis(hours));
    }

    // 计算两个时间之间的时间差，返回分钟数
    private static long calculateMinutesRemaining(Date current, Date target) {
        long diffInMillies = target.getTime() - current.getTime();
        return TimeUnit.MILLISECONDS.toMinutes(diffInMillies);
    }



    /**
     * 提交报告到诊断平台
     * @param phone
     * @param pId
     * @return
     * @throws Exception
     */
    public Boolean addSXReport(String phone,String pId) throws Exception {

        Patient patient = patientService.selectPatientByPatientPhone(aesUtils.encrypt(phone));

        String sxUserId = getSXUserId(patient);
        if (sxUserId==null){
            sxUserId = getSXUserId(patient);
        }

        LinkedHashMap<String, Object> sxDateList = getSXDateList(sxUserId, pId);

        if (sxDateList==null){
            return false;
        }
        try {
            String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
            HttpHeaders headers = new HttpHeaders(); //构建请求头
            headers.setContentType(MediaType.APPLICATION_JSON);
            headers.set("authorization","Bearer "+equipmentCodeAccessToken);
            Map<String, Object> paramsMap = new HashMap<>();
            paramsMap.put("userId",sxDateList.get("userId"));
            paramsMap.put("fileName",sxDateList.get("fileName"));
            HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);
            String url = sxUrl+"/bmecg/third/report/zzdx/sendNotify";
            HashMap<String,Object> sendMessageVo=null;
            try {
                sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
            }catch (Exception e){
                System.out.println(e);
                return false;
            }
            if (sendMessageVo!=null){
                if (sendMessageVo.get("resultCode").toString().equals("200")){
                    ExecutorService executorService = Executors.newSingleThreadExecutor();
                    CompletableFuture.runAsync(() ->{
                        System.out.println("异步线程 =====> 开始添加购买服务日志 =====> " + new Date());
                        try{
                            Doctor doctor = new Doctor();
                            doctor.setIsDoc("4");
                            doctor.setAccountStatus("0");
                            List<Doctor> doctors = doctorService.selectUserDoc(doctor);
                            for (Doctor d : doctors){
                                WxUtil.sendSubmitAdviceSX(aesUtils.decrypt(d.getDoctorPhone()));
                            }
                        }catch (Exception e){
                            System.out.println(e);
                        }
                        System.out.println("异步线程 =====> 结束添加购买服务日志 =====> " + new Date());
                    },executorService);
                    executorService.shutdown(); // 回收线程池
                    return true;
                }else {
                    return false;
                }
            }


            return true;
        }catch (Exception e){
            System.out.println(e);
            return false;
        }
    }


    /**
     * 通过userId获取用户检测列表
     * @param userId
     * @return
     * @throws Exception
     */
    public LinkedHashMap<String,Object> getSXDateList(String userId,String pId) throws Exception {
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        String equipmentCodeAccessToken = getEquipmentCodeAccess_token();
        headers.set("authorization","Bearer "+equipmentCodeAccessToken);
        //封装请求头
        HttpEntity<MultiValueMap<String, Object>> formEntity = new HttpEntity<MultiValueMap<String, Object>>(headers);
        String url = sxUrl+"/bmecg/third/report/zzdx/dataList?userId="+userId+"&page="+1+"&size="+100;
        ResponseEntity<Object> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.exchange(url, HttpMethod.GET,formEntity, Object.class);
        }catch (Exception e){
            System.out.println(e);
            return null;
        }
        try {
            PatientManagement patientManagement = patientManagementService.selectPatientManagementByPId(pId);

            if (patientManagement==null){
                return null;
            }
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

            LinkedHashMap<String,Object> body =(LinkedHashMap<String, Object>) sendMessageVo.getBody();

            LinkedHashMap<String,Object> resultData =(LinkedHashMap<String, Object>) body.get("resultData");

            List<LinkedHashMap<String,Object>> datas =(List<LinkedHashMap<String, Object>>) resultData.get("datas");

            String format = simpleDateFormat.format(patientManagement.getConnectionTime());

            for(LinkedHashMap<String,Object> data:datas){
                String startTime =data.get("startTime").toString();
                if (simpleDateFormat.format(simpleDateFormat.parse(startTime)).equals(format)){
                    return data;
                }
            }

            return null;

        }catch (Exception e){
            System.out.println(e);
            return null;
        }

    }








    /**
     * 将Byte数组转换成文件
     * @param bytes byte数组
     * @param filePath 文件路径  如 D:\\Users\\Downloads\\
     * @param fileName  文件名
     */
    public  void fileToBytes(byte[] bytes, String filePath, String fileName) {
        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {

            file = new File(filePath + fileName);
            if (!file.getParentFile().exists()){
                //文件夹不存在 生成
                file.getParentFile().mkdirs();
            }
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /**
     * 获取善行的验证token
     * @return
     * @throws Exception
     */
    private String getEquipmentCodeAccess_token() throws Exception {
        if (Boolean.TRUE.equals(redisTemplate.hasKey("EquipmentCodeAccess_token"))){
            return redisTemplate.opsForValue().get("EquipmentCodeAccess_token");
        }
        HttpHeaders headers = new HttpHeaders(); //构建请求头
        headers.setContentType(MediaType.APPLICATION_JSON);
        Map<String, Object> paramsMap = new HashMap<>();
        paramsMap.put("password","PSEIF363T" );
        paramsMap.put("appKey","s_zwecg" );
        paramsMap.put("uid","80000261" );
        HttpEntity<Map<String, Object>> request = new HttpEntity<>(paramsMap,headers);

        String url = sxUrl+"/p/thirdLogin/"+"ZZDX";
        HashMap<String,Map<String,Object>> sendMessageVo=null;
        try {
            sendMessageVo = restTemplate.postForObject(url, request, HashMap.class);
        }catch (Exception e){
            System.out.println(e);
        }
        Map<String,Object> resultData = sendMessageVo.get("resultData");
        if (resultData!=null){
            String accessToken = resultData.get("access_token").toString();
            redisTemplate.opsForValue().set("EquipmentCodeAccess_token",accessToken,1, TimeUnit.HOURS);
            return accessToken;
        }
        return null;
    }


    /**
     * 通过pId去通知监测人员有人需要实时监测
     * @param pId
     * @return
     * @throws Exception
     */
    @GetMapping("/submitDetectionReport")
    public AjaxResult submitDetectionReport(String pId, HttpServletRequest request) throws Exception {
        return AjaxResult.success(equipmentHeadingCodeService.submitDetectionReport(pId,request));

    }




    /**
     * 患者解除绑定
     * @param code
     * @param phone
     * @return
     * @throws Exception
     */
    @GetMapping("/getGiveBackCode")
    public AjaxResult getGiveBackCode(String code,String phone) throws Exception {
        if (code.length()>17){
            code=code.substring(0,17);
        }
        String encrypt = aesUtils.encrypt(phone);
        EquipmentHeadingCode equipmentHeadingCode = new EquipmentHeadingCode();

        equipmentHeadingCode = equipmentHeadingCodeService.selectByCode(code);
        if (equipmentHeadingCode==null){
            equipmentHeadingCode = equipmentHeadingCodeService.selectEquipmentCode(code);
            if (equipmentHeadingCode==null){
                return AjaxResult.error("识别码不存在");
            }
        }
        getCodeStatus(equipmentHeadingCode.getHeadingCode());
        Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(equipmentHeadingCode.getHeadingCode());
        if (equipment==null){
            return AjaxResult.error("SN码不存在");
        }
        Patient patient = patientService.selectPatientByPatientPhone(encrypt);
        if (patient==null){
            return AjaxResult.error("患者信息不存在");
        }

        MedicalHistory medicalHistory = medicalHistoryService.selectMedicalHistoryByPatientPhone(encrypt);
        EquipmentHeadingCode finalEquipmentHeadingCode = equipmentHeadingCode;
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
            redisTemplate.delete("getEquipmentCodeAgainTwo");
        }
        if (Boolean.TRUE.equals(redisTemplate.hasKey("getEquipmentCodeAgainT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone))){
            redisTemplate.delete("getEquipmentCodeAgainT15");
        }
        ExecutorService executorService = Executors.newSingleThreadExecutor();
        CompletableFuture.runAsync(() ->{
            System.out.println("异步线程 =====> 开始推送公众号消息 =====> " + new Date());
            try{
                List<AccountsMsg> accountsMsgs = accountsMsgService.selectByList();
                for (AccountsMsg c : accountsMsgs){
                    wxPublicRequest.sendEquipmentMsg(finalEquipmentHeadingCode.getEquipmentCode(), c.getOpenId(),"换设备："+
                            aesUtils.decrypt(patient.getPatientName())+"/"+patient.getPatientSex(),aesUtils.decrypt(patient.getPatientPhone()),medicalHistory.getHeight()+"/"+medicalHistory.getWeight(),patient.getBirthDay());
                }
            }catch (Exception e){
                System.out.println(e);
            }
            System.out.println("异步线程 =====> 结束推送公众号消息 =====> " + new Date());
        },executorService);
        executorService.shutdown(); // 回收线程池

        redisTemplate.opsForValue().set("getEquipmentCodeTwo!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),5, TimeUnit.SECONDS);
        redisTemplate.opsForValue().set("getEquipmentCodeT15!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),30,TimeUnit.MINUTES);
        redisTemplate.opsForValue().set("getEquipmentCodeSF!"+finalEquipmentHeadingCode.getHeadingCode()+"="+phone,finalEquipmentHeadingCode.getEquipmentCode(),12,TimeUnit.SECONDS);
        return AjaxResult.success();
    }



    public void getCodeStatus(String code){
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
            if (c.equals(code)){
                Sn = c;
            }
        }
        if (StringUtils.isEmpty(Sn)){
            Equipment equipment = equipmentService.selectEquipmentByEquipmentCode(code);
            if (equipment!=null&&equipment.getEquipmentStatus().equals("True")){
                equipment.setEquipmentStatus("False");
                equipmentService.updateStatusAndPatientPhoneNull(equipment);
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
