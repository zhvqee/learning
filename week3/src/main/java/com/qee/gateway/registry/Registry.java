package com.qee.gateway.registry;

import com.qee.gateway.config.GateWayConfig;
import com.qee.gateway.context.NameAware;

import java.util.List;
import java.util.Map;

public interface Registry extends NameAware {

    Map<String, List<String>> getAllServiceProvider();

    void init(GateWayConfig gateWayConfig);
}
