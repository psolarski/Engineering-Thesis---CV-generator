package pl.beng.thesis.aspect;

import org.aspectj.lang.annotation.Pointcut;

public class GeneratorPointcuts {

    @Pointcut(value = "bean(*Controller)")
    public void anyControllerBean() {}
}
