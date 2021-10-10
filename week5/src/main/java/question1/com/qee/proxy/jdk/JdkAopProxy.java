package question1.com.qee.proxy.jdk;

import com.qee.common.Advice;
import com.qee.common.Advised;
import com.qee.common.Advisor;
import com.qee.common.AopProxy;
import com.qee.common.AroundAdvice;
import com.qee.common.Pointcut;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

public class JdkAopProxy implements AopProxy, InvocationHandler {

    private JdkAdvised jdkAdvised;

    private List<Advisor> realAdvisorList;

    public JdkAopProxy(Advised advised) {
        if (!(advised instanceof JdkAdvised)) {
            throw new IllegalArgumentException("param illegal");
        }
        this.jdkAdvised = (JdkAdvised) advised;
        realAdvisorList = new ArrayList<>();
        for (Advisor advisor : advised.getAdvisor()) {
            Pointcut pointcut = advisor.getPointcut();
            if (pointcut.getClassMatcher().matches(advised.getTarget().getClass())) {
                realAdvisorList.add(advisor);
            }
        }
    }

    @Override
    public Object getProxy() {
        Class<?>[] interfaces = getProxyInterfaces();
        return Proxy.newProxyInstance(ClassLoader.getSystemClassLoader(), interfaces, this);
    }

    private Class<?>[] getProxyInterfaces() {
        Object target = jdkAdvised.getTarget();
        return target.getClass().getInterfaces();
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("equals") && method.getParameterTypes().length == 1 && method.getParameterTypes()[0] == Object.class) {
            return this.jdkAdvised.getTarget().equals(args[0]);
        }

        if (method.getName().equals("hashCode") && method.getParameterTypes().length == 0) {
            return this.jdkAdvised.getTarget().hashCode();
        }

        if (method.getName().equals("toString") && method.getParameterTypes().length == 0) {
            return this.jdkAdvised.getTarget().toString();
        }

        if (this.realAdvisorList == null || this.realAdvisorList.size() == 0) {
            return method.invoke(this.jdkAdvised.getTarget(), args);
        }

        // before
        for (Advisor advisor : this.realAdvisorList) {
            Advice advice = advisor.getAdvice();
            if (advice instanceof AroundAdvice) {
                ((AroundAdvice) advice).before(method, args, this.jdkAdvised.getTarget());
            }
        }

        Object retVal = method.invoke(this.jdkAdvised.getTarget(), args);
        // after
        for (Advisor advisor : this.realAdvisorList) {
            Advice advice = advisor.getAdvice();
            if (advice instanceof AroundAdvice) {
                ((AroundAdvice) advice).after(retVal, method, args, this.jdkAdvised.getTarget());
            }
        }

        //如果方法的返回值是其target对象，则也返回其代理的对象proxy
        Class<?> returnType = method.getReturnType();
        if (retVal == this.jdkAdvised.getTarget() && returnType.isInstance(proxy)) {
            retVal = proxy;
        }
        return retVal;
    }
}
