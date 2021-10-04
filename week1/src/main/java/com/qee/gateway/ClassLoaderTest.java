package com.qee.gateway;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * @ProjectName: learning
 * @Package: com.qee
 * @ClassName: ClassLoaderTest
 * @Description:
 * @Date: 2021/9/14 8:27 下午
 * @Version: 1.0
 */
public class ClassLoaderTest {
    public static void main(String[] args) throws ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        MyXClassLoader myXClassLoader = new MyXClassLoader(new URL[]{ClassLoaderTest.class.getResource("/")});
        Class<?> HelloClass = myXClassLoader.loadClass("com.qee.Hello");
        Method method = HelloClass.getMethod("hello", String.class);
        Object newInstance = HelloClass.newInstance();
        method.setAccessible(true);
        method.invoke(newInstance, "class loader hello world");

        Class<?> helloClazz = myXClassLoader.loadClass("Hello");
        Object hello = helloClazz.getConstructor().newInstance();
        Method helloClazzMethod = helloClazz.getMethod("hello");
        helloClazzMethod.setAccessible(true);
        helloClazzMethod.invoke(hello);
    }
}
