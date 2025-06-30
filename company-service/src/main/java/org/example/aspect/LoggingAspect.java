package org.example.aspect;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;
import java.util.Collection;


@Component
@Aspect
@Slf4j
public class LoggingAspect {

    @Pointcut("execution(public * org.example.controller.*.*(..))")
    public void controllerLog(){}

    @Pointcut("execution(public * org.example.service.serviceImpl.*.*(..))")
    public void serviceLog(){}

    @Before("controllerLog()")
    public void doBeforeControllerLog(JoinPoint joinPoint){
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = null;
        if(attributes != null){
            request = attributes.getRequest();
        }
        if (request != null) {
            log.info("NEW REQUEST: IP: {}, URL: {}, HTTP_METHOD: {}, CONTROLLER_METHOD: {}.{}",
                    request.getRemoteAddr(),
                    request.getRequestURL().toString(),
                    request.getMethod(),
                    joinPoint.getSignature().getDeclaringType(),
                    joinPoint.getSignature().getName());
        }
    }

    @Before("serviceLog()")
    public void doBeforeServiceLog(JoinPoint joinPoint){
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        Object[] args = joinPoint.getArgs();
        String argsString = args.length > 0 ? Arrays.toString(args) : "METHOD HAS NO ARGUMENTS";

        log.info("RUN SERVICE: SERVICE_METHOD: {}.{} METHOD ARGUMENTS: [{}]",
                className, methodName,  argsString);
    }

    @AfterReturning(returning = "returnObject", pointcut = "controllerLog()")
    public void doAfterReturning(Object returnObject){
        if (returnObject instanceof Collection<?> collection && collection.size() > 10) {
            log.info("Return collection ({} items): [truncated]", collection.size());
        } else {
            log.info("Return value: {}", returnObject);
        }
    }

    @After("controllerLog() || serviceLog()")
    public void logAfter(JoinPoint joinPoint){
        String layer = getLayer(joinPoint);
        log.info("{} Method executed successfully: {}.{}",
                layer,
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName());

    }


    @Around("controllerLog() || serviceLog()")
    private Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long time = System.currentTimeMillis() - start;
        log.info("Execution method: {}.{}. Execution time: {} ms",
                joinPoint.getSignature().getDeclaringTypeName(),
                joinPoint.getSignature().getName(),
                time);
        return result;
    }

    @AfterThrowing(throwing = "ex", pointcut = "controllerLog() || serviceLog()")
    public void throwsException(JoinPoint joinPoint, Exception ex){
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getSimpleName();

        log.error("Exception in {}.{} with arguments: {}. Exception message: {}",
                className, methodName, Arrays.toString(joinPoint.getArgs()), ex.getMessage());

    }


    private String getLayer(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        if (className.contains(".controller.")) return "Controller";
        if (className.contains(".service.")) return "Service";
        return "Unknown";
    }
}
