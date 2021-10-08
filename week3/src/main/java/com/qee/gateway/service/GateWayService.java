package com.qee.gateway.service;

import com.qee.gateway.config.GateWayConfig;
import io.netty.handler.codec.http.FullHttpRequest;

public interface GateWayService {

    Object invoke(FullHttpRequest fullHttpRequest, GateWayConfig config);
}
