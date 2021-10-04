package com.qee.gateway.filters.impls;

import com.qee.gateway.config.GateWayConfig;
import com.qee.gateway.filters.AbstractFilter;
import com.qee.gateway.filters.Request;
import com.qee.gateway.filters.Response;
import io.netty.handler.codec.http.FullHttpRequest;

public class GateWayInitFilter extends AbstractFilter {

    @Override
    public boolean doFilter(Request request, Response response) {
        request.setFullHttpRequest((FullHttpRequest) request.getArguments()[0]);
        request.setConfig((GateWayConfig) request.getArguments()[1]);
        return true;
    }

}
