import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * @ProjectName: learning
 * @Package: PACKAGE_NAME
 * @ClassName: Test
 * @Description:
 * @Date: 2021/10/13 1:34 下午
 * @Version: 1.0
 */
public class Test {

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        UserService userService = new UserServiceImpl();

        Test test = new Test();
        UserService proxy = test.getProxy(UserServiceImpl.class, Arrays.asList("say"));
        String say = proxy.say("1233");
        System.out.println(proxy.getClass());
        System.out.println(say);
    }


    public <T> T getProxy(Class<T> sourceClass, List<String> methods) throws IllegalAccessException, InstantiationException {
        ByteBuddy byteBuddy = new ByteBuddy(ClassFileVersion.JAVA_V8);
        return byteBuddy.subclass(sourceClass, ConstructorStrategy.Default.IMITATE_SUPER_CLASS)
                .implement(sourceClass.getInterfaces())
                .method(getMatcherMethod(methods))
                .intercept(MethodDelegation.to(TestAdvice.class))
                .make().load(ClassLoader.getSystemClassLoader()).getLoaded().newInstance();

    }

    private ElementMatcher<? super MethodDescription> getMatcherMethod(List<String> methods) {
        if (methods == null || methods.size() == 0) {
            return ElementMatchers.any();
        }
        ElementMatcher.Junction<MethodDescription> elementMatcher = ElementMatchers.none();
        for (String methodName : methods) {
            elementMatcher = elementMatcher.or(ElementMatchers.named(methodName));
        }
        return elementMatcher;
    }

    public static class TestAdvice {

        @RuntimeType
        public static Object onMethodExecution(@SuperCall Callable<Object> callable, @Origin Method method, @AllArguments Object[] args) throws Exception {
            System.out.println("调用了该方法" + method.getName());
            return callable.call();
        }

    }
}
