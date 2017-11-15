package pl.beng.thesis.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class TracingAspect {

    Logger logger = LoggerFactory.getLogger(this.getClass());

    @Before("pl.beng.thesis.aspect.GeneratorPointcuts.anyControllerBean()")
    public void beforeAnyControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        logger.info(" ---> Method " + className + "." + methodName + " is about to be called");
    }

    @After("pl.beng.thesis.aspect.GeneratorPointcuts.anyControllerBean()")
    public void afterAnyControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        logger.info(" ---> Method " + className + "." + methodName + " performed as expected");
    }
}
