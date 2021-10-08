package com.qee.gateway.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "gateway")
public class GateWayProperties {

    private Integer workerIOThreadNum;

    private Integer tcpBackLog;

    private Boolean tcpNoDelay;

    private Boolean tcpKeepAlive;

    private Boolean reuseAddr;

    private Integer rcvBufSize;

    private Integer sndBufSize;

    private Integer httpMaxContentLength;

    private Integer port;

    private Boolean openDebugLog;

    private Boolean openIpFilter;

    private Integer cidrPrefix;

    private Integer businessThreadNum;

    private Boolean useOKHttpClient;

    private String routeSelectStrategy;

    private String registryAddr;

    private String registryName;


    public GateWayProperties() {
        System.out.println("==>GateWayProperties");
    }
}
