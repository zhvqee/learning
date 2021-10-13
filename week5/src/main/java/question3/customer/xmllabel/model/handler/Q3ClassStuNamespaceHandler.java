package question3.customer.xmllabel.model.handler;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;
import question3.customer.xmllabel.model.Q3Klass;
import question3.customer.xmllabel.model.Q3Student;

public class Q3ClassStuNamespaceHandler extends NamespaceHandlerSupport {
    @Override
    public void init() {
        registerBeanDefinitionParser("class", new Q3ClassStuBeanDefinitionParser(Q3Klass.class));
        registerBeanDefinitionParser("student", new Q3ClassStuBeanDefinitionParser(Q3Student.class));

    }
}
