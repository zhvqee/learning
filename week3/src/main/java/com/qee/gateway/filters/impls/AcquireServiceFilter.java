package com.qee.gateway.filters.impls;

import com.qee.gateway.context.BeanConstext;
import com.qee.gateway.filters.AbstractFilter;
import com.qee.gateway.filters.Request;
import com.qee.gateway.filters.Response;
import com.qee.gateway.registry.Registry;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;

/**
 * 根据 注册中心获取服务地址，
 * 服务一般为
 * 127.0.0.1:8012/#{projectName}/#{uri}
 */
public class AcquireServiceFilter extends AbstractFilter {
    @Override
    public boolean doFilter(Request request, Response response) {
        Registry registry = BeanConstext.getBean(Registry.class);
        Map<String, List<String>> allServiceProvider = registry.getAllServiceProvider();
        String uri = request.getFullHttpRequest().uri();
        String suri = uri.split("\\?")[0];
        List<String> serviceProviderList = allServiceProvider.get(suri);
        if (CollectionUtils.isEmpty(serviceProviderList)) {
            return false;
        }

        response.setRequestParams(uri.contains("?") ? uri.split("\\?")[1] : null);
        response.setServiceProviderList(serviceProviderList);
        return true;
    }
}
