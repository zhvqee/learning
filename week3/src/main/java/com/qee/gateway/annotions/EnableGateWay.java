package com.qee.gateway.annotions;

import com.qee.gateway.selector.GateWayImportSelector;
import org.springframework.context.annotation.Import;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(GateWayImportSelector.class)
public @interface EnableGateWay {
    String value() default "enable";

}
