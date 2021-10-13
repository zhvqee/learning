package question2.com.qee.autwireds.xml;

import org.springframework.context.support.ClassPathXmlApplicationContext;
import question2.com.qee.autwireds.xml.model.KClass;

public class XmlAutowiredTest {

    public static void main(String[] args) {

        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext("app.xml");
        KClass bean = applicationContext.getBean(KClass.class);
        System.out.println(bean);
    }
}
