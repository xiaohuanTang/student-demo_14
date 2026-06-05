package com.example.studentdemo.aspect;

import com.example.studentdemo.annotation.Log;
import com.example.studentdemo.entity.OperationLog;
import com.example.studentdemo.service.OperationLogService;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.Arrays;

@Aspect
@Component
public class LogAspect {

    @Autowired
    private OperationLogService operationLogService;

    @Around("@annotation(com.example.studentdemo.annotation.Log)")
    public Object around(ProceedingJoinPoint point) throws Throwable {
        long beginTime = System.currentTimeMillis();
        Object result = null;
        String errorMsg = null;
        String status = "SUCCESS";

        try {
            result = point.proceed();
            return result;
        } catch (Throwable e) {
            errorMsg = e.getMessage();
            status = "FAIL";
            throw e;
        } finally {
            long duration = System.currentTimeMillis() - beginTime;
            saveLog(point, duration, status, errorMsg);
        }
    }

    private void saveLog(ProceedingJoinPoint point, long duration, String status, String errorMsg) {
        OperationLog log = new OperationLog();

        // 获取请求信息
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            log.setIp(getIpAddress(request));
            log.setMethod(request.getMethod() + " " + request.getRequestURI());
        }

        // 获取注解上的描述
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        Log logAnnotation = method.getAnnotation(Log.class);
        if (logAnnotation != null) {
            log.setOperation(logAnnotation.value());
        }

        // 获取参数
        Object[] args = point.getArgs();
        if (args != null && args.length > 0) {
            try {
                String params = Arrays.toString(args);
                if (params.length() > 2000) {
                    params = params.substring(0, 2000);
                }
                log.setParams(params);
            } catch (Exception e) {
                log.setParams("参数解析失败");
            }
        }

        // 获取当前用户（写死，后续可以从session获取）
        log.setUserId("20240001");
        log.setUserName("张三");
        log.setDuration(duration);
        log.setStatus(status);
        log.setErrorMsg(errorMsg);
        log.setCreateTime(LocalDateTime.now());

        operationLogService.save(log);
    }

    private String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty()) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}