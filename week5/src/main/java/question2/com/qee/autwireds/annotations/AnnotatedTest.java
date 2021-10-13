package question2.com.qee.autwireds.annotations;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import question2.com.qee.autwireds.annotations.model.KClass;

public class AnnotatedTest {


    public static void main(String[] args) {
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext("question2.com.qee.autwireds.annotations");
        KClass bean = applicationContext.getBean(KClass.class);
        System.out.println(bean);
    }
}
