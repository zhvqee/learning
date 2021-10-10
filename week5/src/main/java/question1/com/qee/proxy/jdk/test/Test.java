package question1.com.qee.proxy.jdk.test;

import com.qee.common.AroundAdvice;
import com.qee.common.DefaultAdvisor;
import question1.com.qee.proxy.jdk.DefaultPointcut;
import question1.com.qee.proxy.jdk.JdkAdvised;
import question1.com.qee.proxy.jdk.JdkAopProxy;

import java.lang.reflect.Method;

public class Test {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();

        JdkAdvised jdkAdvised = new JdkAdvised(userService);
        jdkAdvised.addAdvisor(new DefaultAdvisor(new AroundAdvice() {
            @Override
            public void before(Method method, Object[] args, Object target) throws Throwable {
                System.out.println(" before advisor =>" + method.getName());
            }

            @Override
            public void after(Object returnValue, Method method, Object[] args, Object target) throws Throwable {
                System.out.println(" after advisor =>" + method.getName());
            }
        }, DefaultPointcut.INSTANCE));


        JdkAopProxy jdkAopProxy = new JdkAopProxy(jdkAdvised);
        UserService proxyUserService = (UserService) jdkAopProxy.getProxy();


        System.out.println(proxyUserService.getThis().getClass());
        System.out.println(proxyUserService.say("jdk proxy"));

    }
}
