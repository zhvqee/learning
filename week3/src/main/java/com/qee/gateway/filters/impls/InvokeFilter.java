package com.qee.gateway.filters.impls;

import com.qee.gateway.config.GateWayConfig;
import com.qee.gateway.filters.AbstractFilter;
import com.qee.gateway.filters.Request;
import com.qee.gateway.filters.Response;
import com.qee.gateway.httpclient.HttpOkClient;
import com.qee.gateway.nettyhttpclient.NettyHttpClient;
import org.springframework.util.StringUtils;

public class InvokeFilter extends AbstractFilter {
    @Override
    public boolean doFilter(Request request, Response response) {
        GateWayConfig requestConfig = request.getConfig();
        String url = response.getRequestUrl();
        if (!StringUtils.isEmpty(response.getRequestParams())) {
            url = url + "?" + response.getRequestParams();
        }
        if (requestConfig.getUseOKHttpClient()) {
            String result = HttpOkClient.get("http://" + url);
            response.setResult(result);
        } else {
            //netty 实现http 调用
            String result = new NettyHttpClient(requestConfig).get(url);
            response.setResult(result);
        }
        return true;
    }
}
