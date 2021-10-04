package com.qee.gateway.proxy;

import com.qee.gateway.filters.FilterChainImpl;
import com.qee.gateway.filters.Request;
import com.qee.gateway.filters.Response;
import com.qee.gateway.filters.impls.AcquireServiceFilter;
import com.qee.gateway.filters.impls.GateWayInitFilter;
import com.qee.gateway.filters.impls.InvokeFilter;
import com.qee.gateway.filters.impls.MockFilter;
import com.qee.gateway.filters.impls.RouteFilter;

import javax.naming.OperationNotSupportedException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class GatewayInvocationHandler implements InvocationHandler {
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (method.getName().equals("hashCode")) {
            return hashCode();
        }
        if (method.getName().equals("equals")) {
            return equals(args);
        }
        if (method.getName().equals("toString")) {
            return toString();
        }
        if (method.getName().equals("invoke")) {
            FilterChainImpl filterChain = new FilterChainImpl();
            filterChain.addFilter(new GateWayInitFilter());
            filterChain.addFilter(new MockFilter());
            filterChain.addFilter(new AcquireServiceFilter());
            filterChain.addFilter(new RouteFilter());
            filterChain.addFilter(new InvokeFilter());
            Request request = new Request();
            request.setArguments(args);
            Response response = new Response();
            filterChain.filter(request, response);
            return response.getResult();
        }
        throw new OperationNotSupportedException();
    }
}
