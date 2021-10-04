package com.qee.gateway.filters;

public abstract class AbstractFilter implements Filter {

    @Override
    public void filter(Request request, Response response, FilterChain filterChain) {
        Long startTime = System.currentTimeMillis();
        boolean doFilter = doFilter(request, response);
        Long endTime = System.currentTimeMillis();
        System.out.println("当前filter->" + this.getClass().getSimpleName() + "花费时间:" + (endTime - startTime));
        if (doFilter) {
            filterChain.filter(request, response);
        }
    }

    public abstract boolean doFilter(Request request, Response response);

}
