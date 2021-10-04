package com.qee.gateway.filters.impls;

import com.qee.gateway.balance.LoadBalance;
import com.qee.gateway.context.BeanConstext;
import com.qee.gateway.filters.AbstractFilter;
import com.qee.gateway.filters.Request;
import com.qee.gateway.filters.Response;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 集群模式，负载均衡选择一个
 */
public class RouteFilter extends AbstractFilter {

    @Override
    public boolean doFilter(Request request, Response response) {
        List<String> providerList = response.getServiceProviderList();
        if (CollectionUtils.isEmpty(providerList)) {
            return false;
        }
        LoadBalance loadBalance = BeanConstext.getBean(LoadBalance.class);
        String serviceUrl = loadBalance.select(providerList);
        response.setRequestUrl(serviceUrl);
        return true;
    }

}
