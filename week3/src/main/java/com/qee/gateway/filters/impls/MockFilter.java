package com.qee.gateway.filters.impls;

import com.qee.gateway.filters.AbstractFilter;
import com.qee.gateway.filters.Request;
import com.qee.gateway.filters.Response;


public class MockFilter extends AbstractFilter {

    @Override
    public boolean doFilter(Request request, Response response) {
       // response.setResult("helloword->" + request.getFullHttpRequest().uri());
        return true;
    }

}
