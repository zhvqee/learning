package question2.com.qee.autwireds.manual;

import org.springframework.beans.PropertyValue;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.RootBeanDefinition;
import question2.com.qee.autwireds.manual.model.Person;

public class ManualTest {

    public static void main(String[] args) {

        DefaultListableBeanFactory defaultListableBeanFactory = new DefaultListableBeanFactory();
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(Person.class);
        rootBeanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("id", 10086L));
        rootBeanDefinition.getPropertyValues().addPropertyValue(new PropertyValue("name", "移动"));

        defaultListableBeanFactory.registerBeanDefinition("person", rootBeanDefinition);
        Person bean = defaultListableBeanFactory.getBean(Person.class);
        System.out.println(bean);
    }
}
