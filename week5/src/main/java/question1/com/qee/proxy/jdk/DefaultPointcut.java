package question1.com.qee.proxy.jdk;

import com.qee.common.ClassMatcher;
import com.qee.common.Pointcut;

public class DefaultPointcut implements Pointcut {

    public static final Pointcut INSTANCE = new DefaultPointcut(JdkAopAnnotationClassMathcer.INSTANCE);

    public DefaultPointcut(ClassMatcher classMatcher) {
        this.classMatcher = classMatcher;
    }

    public DefaultPointcut() {
    }

    private ClassMatcher classMatcher;

    @Override
    public ClassMatcher getClassMatcher() {
        return this.classMatcher;
    }

    public void setClassMatcher(ClassMatcher classMatcher) {
        this.classMatcher = classMatcher;
    }
}
