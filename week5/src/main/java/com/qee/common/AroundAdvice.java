package com.qee.common;

import java.lang.reflect.Method;

public interface AroundAdvice extends Advice {

    void before(Method method, Object[] args, Object target) throws Throwable;

    void after(Object returnValue, Method method, Object[] args, Object target) throws Throwable;

}
