package com.qee.gateway.nettyhttpclient;

import com.qee.gateway.config.GateWayConfig;
import com.qee.gateway.handlers.NettyHttpClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaderNames;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NettyHttpClient {

    private Bootstrap bootstrap;

    private NioEventLoopGroup executors;

    private GateWayConfig config;


    public NettyHttpClient(GateWayConfig config) {
        this.config = config;
        executors = new NioEventLoopGroup(5, new DefaultThreadFactory("request-io¬"));
        bootstrap = new Bootstrap();
        bootstrap.group(executors)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, config.getTcpNoDelay())
                .option(ChannelOption.SO_KEEPALIVE, config.getTcpKeepAlive())
                .option(ChannelOption.SO_REUSEADDR, config.getReuseAddr())
                .option(ChannelOption.SO_RCVBUF, config.getRcvBufSize())
                .option(ChannelOption.SO_SNDBUF, config.getSndBufSize())
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel channel) throws Exception {
                        ChannelPipeline pipeline = channel.pipeline();
                        if (config.getOpenDebugLog()) {
                            pipeline.addLast("client-logging", new LoggingHandler(LogLevel.INFO));
                        }
                        pipeline.addLast(new HttpResponseDecoder());
                        pipeline.addLast(new HttpRequestEncoder());
                        pipeline.addLast("nettyHttpClientHandler", new NettyHttpClientHandler());

                    }
                });
    }


    public String get(String url) {
        try {

            String host = url.substring(0, url.indexOf("/"));
            String[] strings = host.split(":");
            String hostIp = strings[0];
            String port = "80";
            if (strings.length == 2) {
                port = strings[1];
            }

            ChannelFuture connect = bootstrap.connect(hostIp, Integer.parseInt(port)).sync();

            Channel channel = connect.channel();

          //  String msg = url.substring(url.indexOf("?") + 1);

            //配置HttpRequest的请求数据和一些配置信息
           // FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, url, Unpooled.wrappedBuffer(msg.getBytes()));
           FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, url);


            ResponseFuture responseFuture = new ResponseFuture();
            channel.pipeline().get(NettyHttpClientHandler.class).setResponseFuture(responseFuture);
            request.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, "application/json;charset=UTF-8")
                    //开启长连接
                    //   .set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE)
                    //设置传递请求内容的长度
                    .set(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
            channel.writeAndFlush(request).sync();
            String resp = responseFuture.get(1000, TimeUnit.MILLISECONDS);
            channel.close();
            return resp;

        } catch (InterruptedException  | ExecutionException | TimeoutException e) {
            e.printStackTrace();
        } finally {
            executors.shutdownGracefully();
        }
        return null;
    }
}
