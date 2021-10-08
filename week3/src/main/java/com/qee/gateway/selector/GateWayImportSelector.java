package com.qee.gateway.selector;

import com.qee.gateway.annotions.EnableGateWay;
import com.qee.gateway.config.GateWayConfiguration;
import org.springframework.context.annotation.ImportSelector;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

public class GateWayImportSelector implements ImportSelector {


    private static final String ENABLE = "enable";


    @Override
    public String[] selectImports(AnnotationMetadata importingClassMetadata) {
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(
                        EnableGateWay.class.getName(), false));
        String value = attributes.getString("value");
        if (ENABLE.equals(value)) {
            return new String[]{GateWayConfiguration.class.getName()};
        }
        return new String[]{};
    }

}
