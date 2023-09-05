package com.ruoyi.xindian.util;

import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.sign.AesUtils;
import com.ruoyi.xindian.alert_log.domain.AlertLog;
import com.ruoyi.xindian.hospital.domain.Doctor;
import com.ruoyi.xindian.patient.domain.Patient;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;


/**
 * @author lixinlong
 * 2023/8/16 15:49
 */
@Aspect
@Component
public class RequestAspect {

    private Logger logger = LoggerFactory.getLogger(RequestAspect.class);

    @Resource
    private AesUtils aesUtils;

    //@Pointcut("execution(public * com.*.*.*.controller.*.*(..)))")
    @Pointcut("@annotation(com.ruoyi.common.annotation.Aes)")
    public void pointcut(){

    }

    /*@Before("BrokerAspect()")
    public void doBefore(JoinPoint joinPoint){
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        logger.info("URL参数={}",requestAttributes.getRequest().getQueryString());
    }*/
    @Around("pointcut()")
    public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
        //获取传入目标方法的参数
        Object[] args = joinPoint.getArgs();

        //获取代理方法的参数
        Signature signature = joinPoint.getSignature();
        MethodSignature methodSignature = (MethodSignature) signature;

        //获取参数名
        String[] argsName = methodSignature.getParameterNames();

        if (null != argsName) {
            //单导预警
            if ("alertLog".equalsIgnoreCase(argsName[0])) {
                AlertLog dto = (AlertLog) args[0];
                if(!StringUtils.isEmpty(dto.getPatientName())){
                    //需要加密的数据
                    dto.setPatientName(aesUtils.encrypt(dto.getPatientName()));
                }
                if(!StringUtils.isEmpty(dto.getPatientPhone())){
                    //需要加密的数据
                    dto.setPatientPhone(aesUtils.encrypt(dto.getPatientPhone()));
                }
                args[0] = dto;
                return joinPoint.proceed(args);
            }
            //医生管理
            if ("doctor".equalsIgnoreCase(argsName[0])) {
                Doctor dto = (Doctor) args[0];
                if(!StringUtils.isEmpty(dto.getDoctorPhone())){
                    //需要加密的数据
                    dto.setDoctorPhone(aesUtils.encrypt(dto.getDoctorPhone()));
                }
                if(!StringUtils.isEmpty(dto.getDoctorName())){
                    //需要加密的数据
                    dto.setDoctorName(aesUtils.encrypt(dto.getDoctorName()));
                }
                args[0] = dto;
                return joinPoint.proceed(args);
            }
            //患者管理
            if ("patient".equalsIgnoreCase(argsName[0])) {
                Patient dto = (Patient) args[0];
                if(!StringUtils.isEmpty(dto.getPatientName())){
                    //需要加密的数据
                    dto.setPatientName(aesUtils.encrypt(dto.getPatientName()));

                }
                if(!StringUtils.isEmpty(dto.getPatientPhone())){
                    //需要加密的数据
                    dto.setPatientPhone(aesUtils.encrypt(dto.getPatientPhone()));
                }
                args[0] = dto;
                return joinPoint.proceed(args);
            }

        }
        return joinPoint.proceed();
    }



}
