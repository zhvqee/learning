package question1.com.qee.proxy.jdk;

import com.qee.common.ClassMatcher;
import com.qee.common.JdkAop;

public class JdkAopAnnotationClassMathcer implements ClassMatcher {

    public static final JdkAopAnnotationClassMathcer INSTANCE = new JdkAopAnnotationClassMathcer();

    @Override
    public boolean matches(Class<?> clzzz) {
        return clzzz.getAnnotation(JdkAop.class) != null;
    }
}
