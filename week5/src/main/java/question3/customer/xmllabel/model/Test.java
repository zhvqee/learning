package question3.customer.xmllabel.model;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

public class Test {

    @Configuration
    @ImportResource(locations = "classpath:class-stu.xml")
    static class XmlConfiguration {

    }

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        applicationContext.register(XmlConfiguration.class);
        applicationContext.refresh();

        Q3Student q3Student = (Q3Student) applicationContext.getBean("q3Student9527");
        System.out.println(q3Student);
        Q3Student q3Student2 = (Q3Student) applicationContext.getBean("q3Student10086");
        System.out.println(q3Student2);

        Q3Klass q3Klass = applicationContext.getBean(Q3Klass.class);
        System.out.println(q3Klass);
    }
}
