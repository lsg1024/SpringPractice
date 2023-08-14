package com.kan.gemstone.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

@Aspect
@Component
@Slf4j
public class PerformanceAspect {

    // 특정 어노테이션 대상 지정
    @Pointcut("@annotation(com.kan.gemstone.annotation.RunningTime)")
    private void enableRunningTime() {};

    @Pointcut("execution(* com.kan.gemstone..*.*(..))")
    private void cut() {};

    @Around("cut() && enableRunningTime()") // 실행 시점 설정: 두조건을 만족할 때 전후 부가 기능을 삽입
    public void loggingRunningTime(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        // 메소드 수행 전, 측정 시작
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        // 메소드명
        String methodName = proceedingJoinPoint.getSignature().getName();

        // 메소드를 수행
        Object returningOBJ = proceedingJoinPoint.proceed();

        // 메소드 숭행 후, 측정 종료 및 로깅
        stopWatch.stop();
        log.info("{}의 총 수행 시간 => {} sec", methodName, stopWatch.getTotalTimeSeconds());

    }
}
