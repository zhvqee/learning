package com.qee.gateway.serviceloader;

import com.qee.gateway.context.NameAware;

import java.util.Iterator;

public class ServiceLoader {

    public  static  <T extends NameAware> T getService(String strategy, Class<T> clzz) {
        java.util.ServiceLoader<T> loadBalances = java.util.ServiceLoader.load(clzz);
        Iterator<T> iterator = loadBalances.iterator();
        T t = null;
        while (iterator.hasNext()) {
            t = iterator.next();
            if (t.name().equals(strategy)) {
                break;
            }
            t = null;
        }
        return t;
    }
}
