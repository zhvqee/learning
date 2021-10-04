package com.qee.gateway.handlers;

import com.qee.gateway.config.GateWayConfig;
import com.qee.gateway.constants.GateWayConstant;
import com.qee.gateway.proxy.GatewayInvocationHandler;
import com.qee.gateway.proxy.JdkProxyFactory;
import com.qee.gateway.service.GateWayService;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.UnsupportedEncodingException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class GateWayHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final ExecutorService executorService;

    private GateWayService gateWayService;

    private GateWayConfig gateWayConfig;


    public GateWayHandler(GateWayConfig gateWayConfig) {
        executorService = new ThreadPoolExecutor(gateWayConfig.getBusinessThreadNum(), gateWayConfig.getBusinessThreadNum(), 0, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(GateWayConstant.BUSINESS_THREAD_QUEUE_SIZE), new ThreadFactory() {
            private final AtomicInteger atomicInteger = new AtomicInteger();

            @Override
            public Thread newThread(Runnable r) {
                Thread thread = new Thread(r);
                thread.setName("business-thread-" + atomicInteger.getAndIncrement());
                return thread;
            }
        });

        gateWayService = JdkProxyFactory.getProxy(GateWayService.class, new GatewayInvocationHandler());
        this.gateWayConfig = gateWayConfig;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) throws Exception {
        addTask(channelHandlerContext, fullHttpRequest);
    }

    private void addTask(ChannelHandlerContext channelHandlerContext, FullHttpRequest fullHttpRequest) {
        executorService.submit(() -> {
            try {
                Object result = gateWayService.invoke(fullHttpRequest, gateWayConfig);
                if (channelHandlerContext.channel().isActive() && channelHandlerContext.channel().isWritable()) {
                    channelHandlerContext.writeAndFlush(buildResponse(result));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        });
    }

    private FullHttpResponse buildResponse(Object result) throws UnsupportedEncodingException {
        FullHttpResponse response = null;
        if (result == null) {
            response = new DefaultFullHttpResponse(HTTP_1_1, HttpResponseStatus.NOT_FOUND);
        } else {
            String value = (String) result;
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(value.getBytes("UTF-8")));
            response.headers().set("Content-Type", "application/json");
            response.headers().setInt("Content-Length", response.content().readableBytes());
        }
        return response;
    }
}
