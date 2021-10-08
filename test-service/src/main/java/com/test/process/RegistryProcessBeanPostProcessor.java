package com.test.process;

import com.fasterxml.jackson.core.type.TypeReference;
import com.test.utils.JsonUtil;
import com.test.utils.NetUtils;
import com.test.annotations.RegistryService;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class RegistryProcessBeanPostProcessor implements BeanPostProcessor, ApplicationContextAware {


    private CuratorFramework client;

    public static final String ROOT = "/registry/providers";

    public static final String projectName = "/test-service";


    private ApplicationContext applicationContext;

    public RegistryProcessBeanPostProcessor() {
        init();
    }


    public void init() {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString("127.0.0.1:2181")
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .namespace("GATEWAY")
                .build();
        client.start();
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        RegistryService annotation = AnnotationUtils.findAnnotation(bean.getClass(), RegistryService.class);
        if (annotation == null) {
            return bean;
        }

        RequestMapping requestMapping = AnnotationUtils.findAnnotation(bean.getClass(), RequestMapping.class);
        String[] pre = null;
        if (requestMapping != null) {
            pre = requestMapping.path();
        }

        Method[] declaredMethods = bean.getClass().getDeclaredMethods();
        List<String> registryServiceList = new ArrayList<>();
        for (Method method : declaredMethods) {
            RequestMapping mapping = AnnotationUtils.findAnnotation(method, RequestMapping.class);
            if (mapping == null) {
                continue;
            }
            String[] path = mapping.path();
            if (pre != null) {
                for (String pr : pre) {
                    if (pr.startsWith(projectName)) {
                        pr = pr.substring(projectName.length());
                        if (!pr.startsWith("/")) {
                            pr = "/" + pr;
                        }
                    }
                    for (String p : path) {
                        registryServiceList.add(pr + p);
                    }
                }
            } else {
                for (String p : path) {
                    registryServiceList.add(p);
                }
            }
        }

        ///gateways/providers/127.0.0.1:90" + i + "/test-service", "get".getBytes()
        Environment environment = applicationContext.getBean(Environment.class);

        try {
            String ip = NetUtils.getLocalInetAddress().getHostName();
            String port = environment.getProperty("server.port");
            String host = "/" + ip + ":" + port;
            Stat stat = client.checkExists().forPath(ROOT + host);
            if (stat == null) {
                client.create().withMode(CreateMode.PERSISTENT).forPath(ROOT + host);
            }

            stat = client.checkExists().forPath(ROOT + host + projectName);
            if (stat == null) {
                client.create().withMode(CreateMode.EPHEMERAL).forPath(ROOT + host + projectName);
            }

            byte[] bytes = client.getData().forPath(ROOT + host + projectName);
            if (bytes != null) {
                String content = new String(bytes);
                List<String> serviceList = JsonUtil.string2Obj(content, new TypeReference<List<String>>() {
                });
                if (!CollectionUtils.isEmpty(serviceList)) {
                    registryServiceList.addAll(serviceList);
                }
            }

            client.setData().forPath(ROOT + host + projectName, JsonUtil.obj2String(registryServiceList).getBytes());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return bean;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
