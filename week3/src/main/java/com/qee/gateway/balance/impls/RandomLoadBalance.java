package com.qee.gateway.balance.impls;

import com.qee.gateway.balance.LoadBalance;
import com.qee.gateway.constants.GateWayConstant;

import java.util.List;
import java.util.Random;

public class RandomLoadBalance implements LoadBalance {
    @Override
    public String select(List<String> serviceProviderList) {
        if (serviceProviderList.size() == 1) {
            return serviceProviderList.get(0);
        }
        Random seedRandom1 = new Random(System.currentTimeMillis());
        return serviceProviderList.get(seedRandom1.nextInt(serviceProviderList.size()));
    }

    @Override
    public String name() {
        return GateWayConstant.DEFAULT_ROUTE_SELECT_STRATEGY;
    }
}
