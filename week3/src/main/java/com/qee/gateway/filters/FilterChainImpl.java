package com.qee.gateway.filters;

import java.util.Arrays;

public class FilterChainImpl implements FilterChain {

    private Filter[] filters;

    private int pos = 0;


    @Override
    public void filter(Request request, Response response) {

        if (pos < filters.length) {
            Filter filter = filters[pos++];
            filter.filter(request, response, this);
        }
    }

    @Override
    public void addFilter(Filter filter) {
        if (filters == null || filters.length == 0) {
            filters = new Filter[1];
            filters[0] = filter;
        } else {
            if (isExist(filter)) {
                return;
            }
            Filter[] tmp = new Filter[filters.length + 1];
            System.arraycopy(filters, 0, tmp, 0, filters.length);
            tmp[filters.length] = filter;
            filters = tmp;
        }
    }

    boolean isExist(Filter filter) {
        return Arrays.stream(filters).anyMatch(p -> p == filter);
    }

}
