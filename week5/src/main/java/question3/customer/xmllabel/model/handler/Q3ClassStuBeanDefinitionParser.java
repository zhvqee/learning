package question3.customer.xmllabel.model.handler;

import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import question3.customer.xmllabel.model.Q3Klass;
import question3.customer.xmllabel.model.Q3Student;

import java.util.ArrayList;
import java.util.List;

public class Q3ClassStuBeanDefinitionParser implements BeanDefinitionParser {

    private Class<?> clzz;

    public Q3ClassStuBeanDefinitionParser(Class<?> clzz) {
        this.clzz = clzz;
    }

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        RootBeanDefinition beanDefinition = new RootBeanDefinition();
        beanDefinition.setBeanClass(clzz);
        beanDefinition.setLazyInit(false);

        String id = "";
        if (Q3Student.class.equals(clzz)) {
            id = element.getAttribute("id");
            String name = element.getAttribute("name");
            beanDefinition.getPropertyValues().addPropertyValue("name", name);
            beanDefinition.getPropertyValues().addPropertyValue("id", Long.valueOf(id));
        }
        if (Q3Klass.class.equals(clzz)) {
            String clzzId = element.getAttribute("clzzId");
            id = clzzId;
            beanDefinition.getPropertyValues().addPropertyValue("clzzId", clzzId);


            NodeList childNodes = element.getFirstChild().getNextSibling().getChildNodes();
            List<Object> refList = new ArrayList<>();
            for (int i = 0; i < childNodes.getLength(); i++) {
                Node item = childNodes.item(0).getNextSibling().getAttributes().item(0);
                String nodeValue = item.getNodeValue();


                Object reference = new RuntimeBeanReference("q3Student" + nodeValue);

                // refList.add(reference);

                beanDefinition.getPropertyValues().addPropertyValue("studentList", reference);
            }
        }
        String name = clzz.getSimpleName();
        name = name.substring(0, 1).toLowerCase() + name.substring(1);
        parserContext.getRegistry().registerBeanDefinition(name + id, beanDefinition);
        return beanDefinition;
    }
}
