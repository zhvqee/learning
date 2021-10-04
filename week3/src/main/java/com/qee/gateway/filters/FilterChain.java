package com.qee.gateway.filters;

public interface FilterChain {

    void filter(Request request, Response response);

    void addFilter(Filter filter);

}
