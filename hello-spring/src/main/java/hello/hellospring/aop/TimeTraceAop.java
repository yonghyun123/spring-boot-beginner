package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Aspect;

@Aspect
public class TimeTraceAop {

    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable{
        long start = System.currentTimeMillis();
        System.out.println("start:"+joinPoint.toString());
        try{
            Object proceed = joinPoint.proceed();
            return proceed;
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish-start;
            System.out.println("end:"+ joinPoint.toString() + " "+timeMs + "ms");
        }

    }
}
