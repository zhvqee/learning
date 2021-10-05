package com.qee.gateway.filters;

import lombok.Data;

import java.util.List;

@Data
public class Response {

    private List<String> serviceProviderList;

    private String requestUrl;

    private String requestParams;

    private Object result;


}
