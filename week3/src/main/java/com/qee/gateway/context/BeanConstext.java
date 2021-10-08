package com.qee.gateway.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BeanConstext implements ApplicationContextAware {

    private ApplicationContext applicationContext;

    private static BeanConstext _self;

    @PostConstruct
    public void init() {
        _self = this;
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    public static <T> T getBean(Class<T> clzz) {
        return _self.applicationContext.getBean(clzz);
    }
}
