package question6.httpServer;

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
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.ResourceLeakDetector;

public class HttpServer {

    private ServerBootstrap serverBootstrap;

    private NioEventLoopGroup boss;

    private NioEventLoopGroup worker;

    private int listenPort;

    private Channel listenChanel;


    public void initServer(int listenPort) throws InterruptedException {
        if (listenPort <= 0) {
            throw new IllegalArgumentException("参数异常");
        }
        try {
            this.serverBootstrap = new ServerBootstrap();
            this.boss = new NioEventLoopGroup(1);
            this.worker = new NioEventLoopGroup(Runtime.getRuntime().availableProcessors() * 2);
            this.listenPort = listenPort;

            RequestHanlderService requestHanlderService = new RequestHanlderService();

            listenChanel = this.serverBootstrap.group(boss, worker)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 150)
                    .option(ChannelOption.SO_REUSEADDR, true)
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    .childOption(ChannelOption.SO_RCVBUF, 5 * 1024 * 1024)
                    .childOption(ChannelOption.SO_SNDBUF, 5 * 1024 * 1024)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<NioSocketChannel>() {
                        @Override
                        protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                            ChannelPipeline pipeline = nioSocketChannel.pipeline();
                            pipeline.addLast(new HttpServerCodec());
                            pipeline.addLast(new HttpObjectAggregator(1024 * 1024));
                            pipeline.addLast(new HttpServerHandler(requestHanlderService));

                        }
                    }).bind(listenPort).sync().channel();
            System.out.println("开启了服务端8888");
            ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);
            listenChanel.closeFuture().sync();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }

    }

    public static void main(String[] args) throws InterruptedException {
        HttpServer httpServer = new HttpServer();
        httpServer.initServer(8888);
    }
}
