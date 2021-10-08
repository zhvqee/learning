package com.qee.gateway.constants;

public final class GateWayConstant {

    public static final int IO_WORKER_THREAD_NUM = 8;

    public static final int TCP_BACK_LOG = 1024;

    public static final Boolean TCP_NO_DELAY = true;

    public static final Boolean TCP_KEEP_ALIVE = true;

    public static final Boolean TCP_REUSE_ADDR = true;

    public static final Integer TCP_RCVBUF_SIZE = 1024 * 1024 * 2;

    public static final Integer TCP_SNDBUF_SIZE = 1024 * 1024 * 2;

    public static final Integer HTTP_MAX_CONTENT_LENGTH = 1024 * 1024;

    public static final Integer PORT = 8888;

    public static final Boolean OPEN_DEBUG_LOG = false;

    public static final Boolean OPEN_IP_FILTER = false;

    public static final Integer CIDR_PREFIX = 8;

    public static final Integer BUSINESS_THREAD_NUM = 200;

    public static final int BUSINESS_THREAD_QUEUE_SIZE = 500;

    public static final Boolean USE_OKHTTP_CLIENT = true;

    public static final String DEFAULT_ROUTE_SELECT_STRATEGY = "random";

    public static final String DEFAULT_REGISTRY_ADDR = "127.0.0.1:2181";

    public static final String DEFAULT_REGITRY_NAME = "zookeeper";

}
