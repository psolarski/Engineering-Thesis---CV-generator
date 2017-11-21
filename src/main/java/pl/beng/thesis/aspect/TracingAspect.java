package pl.beng.thesis.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
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

    @AfterReturning("pl.beng.thesis.aspect.GeneratorPointcuts.anyControllerBean()")
    public void afterReturningControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        logger.info(" ---> Method " + className + "." + methodName + " performed as expected");
    }

    @AfterThrowing("pl.beng.thesis.aspect.GeneratorPointcuts.anyControllerBean()")
    public void afterThrowingControllerMethod(JoinPoint joinPoint) {
        String className = joinPoint.getSignature().getDeclaringTypeName();
        String methodName = joinPoint.getSignature().getName();

        logger.info(" ---> Method " + className + "." + methodName + " thrown exception!");
    }
}
