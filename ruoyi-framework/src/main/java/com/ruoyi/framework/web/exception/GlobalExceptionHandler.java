package com.ruoyi.framework.web.exception;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolationException;

import com.mysql.cj.jdbc.exceptions.CommunicationsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import com.ruoyi.common.constant.HttpStatus;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.DemoModeException;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.StringUtils;

import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;

/**
 * 全局异常处理器
 *
 * @author ruoyi
 */
@RestControllerAdvice
public class GlobalExceptionHandler
{
    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 权限校验异常
     */
    @ExceptionHandler(AccessDeniedException.class)
    public AjaxResult handleAccessDeniedException(AccessDeniedException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',权限校验失败'{}'", requestURI, e.getMessage());
        return AjaxResult.error(HttpStatus.FORBIDDEN, "没有权限，请联系管理员授权");
    }

    /**
     * 请求方式不支持
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public AjaxResult handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException e,
            HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',不支持'{}'请求", requestURI, e.getMethod());
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e, HttpServletRequest request)
    {
        log.error(e.getMessage(), e);
        Integer code = e.getCode();
        return StringUtils.isNotNull(code) ? AjaxResult.error(code, e.getMessage()) : AjaxResult.error(e.getMessage());
    }

    /**
     * 拦截未知的运行时异常
     */
    @ExceptionHandler(RuntimeException.class)
    public AjaxResult handleRuntimeException(RuntimeException e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生未知异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e, HttpServletRequest request)
    {
        String requestURI = request.getRequestURI();
        log.error("请求地址'{}',发生系统异常.", requestURI, e);
        return AjaxResult.error(e.getMessage());
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(BindException.class)
    public AjaxResult handleBindException(BindException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getAllErrors().get(0).getDefaultMessage();
        return AjaxResult.error(message);
    }

    /**
     * 自定义验证异常
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object handleMethodArgumentNotValidException(MethodArgumentNotValidException e)
    {
        log.error(e.getMessage(), e);
        String message = e.getBindingResult().getFieldError().getDefaultMessage();
        return AjaxResult.error(message);
    }


    @ExceptionHandler({ConstraintViolationException.class})
    public AjaxResult handleConstraintViolationException(ConstraintViolationException e) {
        log.error("参数校验异常", e);

        String message = e.getMessage();
        StringBuffer returnError=new StringBuffer();
        if (message.contains(",")){
            String[] split = message.split(",");
            for (int i = 0; i < split.length; i++) {
                String s1 = split[i].substring(0, split[i].indexOf(":"));
                String s2 = split[i].substring(s1.length() + 1, split[i].length());
                if (i!=split.length-1){
                    returnError.append(s2+",");
                }else{
                    returnError.append(s2);
                }
            }
            return AjaxResult.error(returnError.toString());
        }else if (message.contains("，")){
            String[] split = message.split("，");
            for (int i = 0; i < split.length; i++) {
                String s1 = split[i].substring(0, split[i].indexOf(":"));
                String s2 = split[i].substring(s1.length() + 1, split[i].length());
                if (i!=split.length-1){
                    returnError.append(s2+",");
                }else{
                    returnError.append(s2);
                }
            }
            return AjaxResult.error(returnError.toString());
        }
        String substring = message.substring(0, message.indexOf(":"));
        String substring1 = message.substring(substring.length() + 1, message.length());
        return AjaxResult.error(substring1.trim());
    }

    /**
     * SQL异常
     * @param e
     * @return
     */
    @ExceptionHandler(BadSqlGrammarException.class)
    public Object handleBadSqlGrammarException(BadSqlGrammarException e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error("服务器内部异常，请联系管理员！");
    }


    @ExceptionHandler(GeneralSecurityException.class)
    public Object handleGeneralSecurityException(GeneralSecurityException e)
    {
        log.error(e.getMessage(), e);
        return AjaxResult.error("服务器内部异常，请联系管理员！");
    }

    @ExceptionHandler(CommunicationsException.class)
    public Object handleCommunicationsException(CommunicationsException e){
        return AjaxResult.error("网络异常，请检测网络");
    }
    /**
     * 演示模式异常
     */
    @ExceptionHandler(DemoModeException.class)
    public AjaxResult handleDemoModeException(DemoModeException e)
    {
        return AjaxResult.error("演示模式，不允许操作");
    }



}
