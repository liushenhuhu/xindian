//package com.ruoyi.framework.aspectj;
//
//import com.ruoyi.common.annotation.Aes;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.After;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.stereotype.Component;
//import org.springframework.web.context.request.RequestAttributes;
//import org.springframework.web.context.request.RequestContextHolder;
//import org.springframework.web.context.request.ServletRequestAttributes;
//
//import javax.servlet.http.HttpServletRequest;
//import java.util.Arrays;
//import java.util.Map;
//
///**
// * @Description //请求参数aop
// **/
//@Component
//@Aspect
//@Slf4j
//public class RequestParameterAop {
//
//    /**
//     * @Description: 定义需要拦截的切面
//     * @Pointcut("execution(* com.*.controller.*Controller.*(..))")
//     * @Return: void
//     **/
//    @Pointcut("@annotation(aes)")
//    public void controllerAspect(Aes aes) {
//    }
//
//    @After("controllerAspect(aes)")
//    public void After(JoinPoint pjp,Aes aes)  {
//        RequestAttributes ra = RequestContextHolder.getRequestAttributes();
//        ServletRequestAttributes sra = (ServletRequestAttributes) ra;
//        HttpServletRequest request = sra.getRequest();
//        String name = request.getParameter("patientPhone");//获取指定key参数
//        Map<String, String[]> parameterMap = request.getParameterMap();//获取全部Parameter参数
//        Object[] args = pjp.getArgs();//获取所有参数包括body和Parameter
//        System.out.println(name);
//        System.out.println(Arrays.toString(args));
//    }
//
//
//    /**
//     * 获取目标主机的ip
//     * @param request
//     * @return
//     */
//    private String getRemoteHost(HttpServletRequest request) {
//        String ip = request.getHeader("x-forwarded-for");
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getHeader("WL-Proxy-Client-IP");
//        }
//        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
//            ip = request.getRemoteAddr();
//        }
//        return ip.contains("0:0:0:0:0:0:0:1") ? "127.0.0.1" : ip;
//    }
//
//}