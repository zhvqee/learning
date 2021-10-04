package com.qee.gateway.gateway;

import com.qee.gateway.config.GateWayConfig;
import com.qee.gateway.handlers.GateWayHandler;
import com.qee.gateway.utils.NetUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ipfilter.IpFilterRuleType;
import io.netty.handler.ipfilter.IpSubnetFilterRule;
import io.netty.handler.ipfilter.RuleBasedIpFilter;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class GateWay {

    private final GateWayConfig config;

    private NioEventLoopGroup bossGroup;

    private NioEventLoopGroup workerGroup;

    private RuleBasedIpFilter ruleBasedIpFilter;

    public GateWay(GateWayConfig config) {
        this.config = config;
        new Thread(this::start, "gateway-main").start();
    }

    public void start() {

        bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss-thread"));
        workerGroup = new NioEventLoopGroup(config.getWorkerIOThreadNum(), new DefaultThreadFactory("worker-thread"));

        if (config.getOpenIpFilter()) {
            ruleBasedIpFilter = new RuleBasedIpFilter(new IpSubnetFilterRule(NetUtils.getLocalInetAddress(), config.getCidrPrefix(), IpFilterRuleType.ACCEPT));
        }

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, config.getTcpBackLog())
                .childOption(ChannelOption.TCP_NODELAY, config.getTcpNoDelay())
                .childOption(ChannelOption.SO_KEEPALIVE, config.getTcpKeepAlive())
                .childOption(ChannelOption.SO_REUSEADDR, config.getReuseAddr())
                .childOption(ChannelOption.SO_RCVBUF, config.getRcvBufSize())
                .childOption(ChannelOption.SO_SNDBUF, config.getSndBufSize())
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();
                        if (config.getOpenDebugLog()) {
                            pipeline.addLast("logging", new LoggingHandler(LogLevel.INFO));
                        }
                        if (ruleBasedIpFilter != null) {
                            pipeline.addLast("ipWhit", ruleBasedIpFilter);
                        }
                        pipeline.addLast("httpServerCodec", new HttpServerCodec());
                        pipeline.addLast("httpObjectAggregator", new HttpObjectAggregator(config.getHttpMaxContentLength()));
                        pipeline.addLast("gateWayHandler", new GateWayHandler(config));

                    }
                });

        try {

            if (config.getOpenDebugLog()) {
                serverBootstrap.handler(new LoggingHandler(LogLevel.INFO));
            }

            Channel ch = serverBootstrap.bind(config.getPort()).sync().channel();
            System.out.println("开启netty 网关服务");
            ch.closeFuture().sync();
            System.out.println("关闭netty 网关服务");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws UnknownHostException {
        System.out.println(InetAddress.getLocalHost());
        System.out.println(NetUtils.getLocalInetAddress());
    }

}
