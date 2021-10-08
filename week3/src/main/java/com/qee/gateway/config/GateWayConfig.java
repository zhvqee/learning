package com.qee.gateway.config;

import com.qee.gateway.constants.GateWayConstant;
import lombok.Data;

import java.util.Optional;

@Data
public class GateWayConfig {

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

    public GateWayConfig(GateWayProperties gateWayProperties) {
        parse(gateWayProperties);
    }

    private void parse(GateWayProperties gateWayProperties) {
        setWorkerIOThreadNum(Optional.ofNullable(gateWayProperties.getWorkerIOThreadNum()).orElse(GateWayConstant.IO_WORKER_THREAD_NUM));
        setTcpBackLog(Optional.ofNullable(gateWayProperties.getTcpBackLog()).orElse(GateWayConstant.TCP_BACK_LOG));
        setTcpNoDelay(Optional.ofNullable(gateWayProperties.getTcpNoDelay()).orElse(GateWayConstant.TCP_NO_DELAY));
        setTcpKeepAlive(Optional.ofNullable(gateWayProperties.getTcpKeepAlive()).orElse(GateWayConstant.TCP_KEEP_ALIVE));
        setReuseAddr(Optional.ofNullable(gateWayProperties.getReuseAddr()).orElse(GateWayConstant.TCP_REUSE_ADDR));
        setRcvBufSize(Optional.ofNullable(gateWayProperties.getRcvBufSize()).orElse(GateWayConstant.TCP_RCVBUF_SIZE));
        setSndBufSize(Optional.ofNullable(gateWayProperties.getSndBufSize()).orElse(GateWayConstant.TCP_SNDBUF_SIZE));
        setHttpMaxContentLength(Optional.ofNullable(gateWayProperties.getHttpMaxContentLength()).orElse(GateWayConstant.HTTP_MAX_CONTENT_LENGTH));
        setPort(Optional.ofNullable(gateWayProperties.getPort()).orElse(GateWayConstant.PORT));
        setOpenDebugLog(Optional.ofNullable(gateWayProperties.getOpenDebugLog()).orElse(GateWayConstant.OPEN_DEBUG_LOG));
        setOpenIpFilter(Optional.ofNullable(gateWayProperties.getOpenIpFilter()).orElse(GateWayConstant.OPEN_IP_FILTER));
        setCidrPrefix(Optional.ofNullable(gateWayProperties.getCidrPrefix()).orElse(GateWayConstant.CIDR_PREFIX));
        setBusinessThreadNum(Optional.ofNullable(gateWayProperties.getBusinessThreadNum()).orElse(GateWayConstant.BUSINESS_THREAD_NUM));
        setUseOKHttpClient(Optional.ofNullable(gateWayProperties.getUseOKHttpClient()).orElse(GateWayConstant.USE_OKHTTP_CLIENT));
        setRouteSelectStrategy(Optional.ofNullable(gateWayProperties.getRouteSelectStrategy()).orElse(GateWayConstant.DEFAULT_ROUTE_SELECT_STRATEGY));
        setRegistryAddr(Optional.ofNullable(gateWayProperties.getRegistryAddr()).orElse(GateWayConstant.DEFAULT_REGISTRY_ADDR));
        setRegistryName(Optional.ofNullable(gateWayProperties.getRegistryName()).orElse(GateWayConstant.DEFAULT_REGITRY_NAME));

    }


}
