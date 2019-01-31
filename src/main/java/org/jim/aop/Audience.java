package org.jim.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

import java.util.Arrays;

/**
 * @Component 对象交给Spring容器托管
 * @Aspect 切面
 *
 * @author Dev
 */
@Component
@Aspect
public class Audience {

    /**
     * 切入点，切入点表达式
     */
    @Pointcut("execution(public * org.jim.aop.Performance.perform(..))")
    public void performance(){}

    /**
     * 通知，前置通知
     */
    @Before("performance()")
    public void silenceCellPhones(JoinPoint joinPoint) throws Exception {
        System.out.println("Sillencing cell phones");

//        System.out.println(joinPoint.toLongString());
//        System.out.println(Arrays.toString(joinPoint.getArgs()));
//        System.out.println(joinPoint.getKind());
//        System.out.println(joinPoint.getThis().toString());
//        System.out.println(joinPoint.getTarget().toString());
//        System.out.println(joinPoint.getStaticPart().toLongString());
//        System.out.println(joinPoint.getSourceLocation().toString());
//        System.out.println(joinPoint.getSignature().toLongString());

        Thread.sleep(200L);
    }

    /**
     * 后置通知
     */
    @After("performance()")
    public void stop() throws Exception {
        System.out.println("STOP");
        Thread.sleep(200L);
    }

    /**
     * 通知，后置通知
     * 无返回值
     */
    @AfterReturning("performance()")
    public void applause() throws Exception {
        System.out.println("CLAP CLAP CLAP");
        Thread.sleep(200L);
    }

    /**
     * 通知，后置通知
     * 有返回值
     */
    @AfterReturning(pointcut = "performance()", returning = "result")
    public void applause(Object result) throws Exception {
        System.out.println("WOW WOW WOW " + result.toString());
        Thread.sleep(200L);
    }

    /**
     * 后置异常通知
     */
    @AfterThrowing("performance()")
    public void demandRefund() throws Exception {
        System.out.println("Demand a Refund");
        Thread.sleep(200L);
    }

    /**
     * 环绕通知
     */
    @Around("performance()")
    public Object takeCare(ProceedingJoinPoint proceedingJoinPoint) throws Exception {
        System.out.println("Around entrance");
        try {
            Object obj = proceedingJoinPoint.proceed(proceedingJoinPoint.getArgs());
            System.out.println(obj.toString());
            return obj;
        } catch (Throwable e) {
            System.err.println(e.getMessage());
            return null;
        } finally {
            System.out.println("Around exit");
        }
    }

}
