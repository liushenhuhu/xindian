//package com.ruoyi.framework.aspectj;
//
//        import com.alibaba.fastjson2.JSON;
//        import org.aspectj.lang.JoinPoint;
//        import org.aspectj.lang.ProceedingJoinPoint;
//        import org.aspectj.lang.annotation.*;
//        import org.aspectj.lang.reflect.MethodSignature;
//        import org.slf4j.Logger;
//        import org.slf4j.LoggerFactory;
//        import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
//        import org.springframework.core.annotation.Order;
//        import org.springframework.stereotype.Component;
//        import org.springframework.web.context.request.RequestContextHolder;
//        import org.springframework.web.context.request.ServletRequestAttributes;
//        import org.springframework.web.multipart.MultipartFile;
//
//        import javax.servlet.http.HttpServletRequest;
//        import javax.servlet.http.HttpServletResponse;
//        import java.lang.reflect.Method;
//        import java.util.Arrays;
//
//@Aspect
//@Component
//public class AopLogAspect {
//
//    private Logger logger = LoggerFactory.getLogger(getClass());
//
//
//    /**
//     * 环绕通知，调用目标方法
//     */
//    @Around("@annotation(com.ruoyi.framework.interfaces.Aes)")
//    public Object doBefore(ProceedingJoinPoint joinPoint) throws Throwable {
//        logger.info("请求开始 : ====================================================================================");
//        // 接收到请求，记录请求内容
//        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
//        HttpServletRequest request = attributes.getRequest();
//        //
//        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
//        Method method = signature.getMethod();
//
//        // 记录下请求内容
//        logger.info("请求类型 :" + request.getMethod() + "  " + "请求URL : " + request.getRequestURL());
//        logger.info("请求IP  : " + request.getRemoteAddr());
//        logger.info("请求方法 : " + joinPoint.getSignature().getDeclaringTypeName() + "." + joinPoint.getSignature().getName());
//
//        // 只记录post方法 传json格式的数据
//        if ("POST".equals(request.getMethod())) {
//            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
//            String[] paramNames = u.getParameterNames(method);
//            //方法 1 请求的方法参数值 JSON 格式 null不显示
//            if (joinPoint.getArgs().length > 0) {
//                Object[] args = joinPoint.getArgs();
//                for (int i = 0; i < args.length; i++) {
//                    //请求参数类型判断过滤，防止JSON转换报错
//                    if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile) {
//                        continue;
//                    }
//                    logger.info("请求参数名称 :" + paramNames[i] + ", 内容 :" + JSON.toJSONString(args[i]));
//                }
//            }
//        } else {
//            //请求的方法参数值 兼容fromDate格式和JSON格式
//            Object[] args = joinPoint.getArgs();
//            // 请求的方法参数名称 显示所有字段 值为null也显示该字段
//            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
//            String[] paramNames = u.getParameterNames(method);
//            if (args != null && paramNames != null) {
//                String params = "";
//                for (int i = 0; i < args.length; i++) {
//                    //请求参数类型判断过滤，
//                    if (args[i] instanceof HttpServletRequest || args[i] instanceof HttpServletResponse || args[i] instanceof MultipartFile) {
//                        continue;
//                    }
//                    params += " " + paramNames[i] + ": " + args[i] + ",";
//                }
//                logger.info("请求参数 :" + params);
//            }
//        }
//        Object[] args = joinPoint.getArgs();
//
//        System.out.println(Arrays.toString(args));
//        return joinPoint.proceed(args);
//
//    }
//
//
//
//}
//
//
//
