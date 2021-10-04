package com.qee.gateway.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

public class JdkProxyFactory {

    public static  <T> T getProxy(Class<T> clazz, InvocationHandler invocationHandler) {
        return (T) Proxy.newProxyInstance(JdkProxyFactory.class.getClassLoader(), new Class[]{clazz}, invocationHandler);
    }

}
