package com.qee.common;

/**
 * advice 和 pointcut 聚合
 */
public interface Advisor extends Ordered {

    Advice getAdvice();


    Pointcut getPointcut();
}
