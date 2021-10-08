package com.qee.gateway.balance;

import com.qee.gateway.context.NameAware;

import java.util.List;

public interface LoadBalance extends NameAware {

    String select(List<String> serviceProviderList);
}
