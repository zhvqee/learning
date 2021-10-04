package com.qee.gateway.registry.impl;

import com.google.common.collect.Lists;
import com.qee.gateway.config.GateWayConfig;
import com.qee.gateway.constants.GateWayConstant;
import com.qee.gateway.registry.Registry;
import com.qee.gateway.utils.JsonUtil;
import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ZoopeekerRegistry implements Registry {

    private CuratorFramework client;
    private Map<String, List<String>> urlToServiceListMap;

    private ScheduledExecutorService service;

    private static final String ROOT = "/registry/providers";
    private static final String SEPARATE_LINE = "/";

    public ZoopeekerRegistry() {

    }

    @Override
    public void init(GateWayConfig gateWayConfig) {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
        client = CuratorFrameworkFactory.builder()
                .connectString(gateWayConfig.getRegistryAddr())
                .sessionTimeoutMs(50000)
                .connectionTimeoutMs(50000)
                .retryPolicy(retryPolicy)
                .namespace("GATEWAY")
                .build();
        client.start();
        urlToServiceListMap = new HashMap<>();
        service = new ScheduledThreadPoolExecutor(1);
        service.scheduleAtFixedRate(() -> {
            getAllServiceProvider();
        }, 0, 15, TimeUnit.MINUTES);

    }

    /**
     * 在工程启动时就加载，不会多线程获取
     *
     * @return
     */
    @Override
    public Map<String, List<String>> getAllServiceProvider() {
        try {
            if (CollectionUtils.isEmpty(urlToServiceListMap)) {
                Map<String, List<String>> urlToServiceListMapTmp = new HashMap<>();
                List<String> stringList = client.getChildren().forPath(ROOT);
                for (String host : stringList) {//host:port

                    List<String> strings = client.getChildren().forPath(ROOT + SEPARATE_LINE + host);//host:port/project-name/
                    for (String projectName : strings) {
                        byte[] data = client.getData().forPath(ROOT + SEPARATE_LINE + host + SEPARATE_LINE + projectName);
                        String url = new String(data);
                        List<String> urls = JsonUtil.toList(url, String.class);
                        for (String s : urls) {
                            List<String> serviceList = urlToServiceListMapTmp.computeIfAbsent(SEPARATE_LINE + projectName + s, k -> Lists.newArrayList());
                            serviceList.add(host + SEPARATE_LINE + projectName + s);
                        }
                    }
                }
                urlToServiceListMap = urlToServiceListMapTmp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return urlToServiceListMap;
    }


    @Override
    public String name() {
        return GateWayConstant.DEFAULT_REGITRY_NAME;
    }
}
