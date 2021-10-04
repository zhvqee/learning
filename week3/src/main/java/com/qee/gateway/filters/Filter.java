package com.qee.gateway.filters;

public interface Filter {

    void filter(Request request, Response response, FilterChain filterChain);
}
