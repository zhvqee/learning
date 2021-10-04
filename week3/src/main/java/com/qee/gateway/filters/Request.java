package com.qee.gateway.filters;

import com.qee.gateway.config.GateWayConfig;
import io.netty.handler.codec.http.FullHttpRequest;
import lombok.Data;

@Data
public class Request {

    private GateWayConfig config;

    private FullHttpRequest fullHttpRequest;

    private Object[] arguments;
}
