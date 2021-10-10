package com.qee.common;

public class DefaultAdvisor implements Advisor {

    private Advice advice;

    private Pointcut pointcut;

    public DefaultAdvisor(Advice advice, Pointcut pointcut) {
        this.advice = advice;
        this.pointcut = pointcut;
    }

    @Override
    public Advice getAdvice() {
        return this.advice;
    }

    @Override
    public Pointcut getPointcut() {
        return this.pointcut;
    }
}
