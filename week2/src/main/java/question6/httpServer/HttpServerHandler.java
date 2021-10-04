package question6.httpServer;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
import io.netty.handler.codec.http.HttpVersion;
import io.netty.util.ReferenceCountUtil;

public class HttpServerHandler extends ChannelInboundHandlerAdapter {
    private RequestHanlderService requestHanlderService;

    public HttpServerHandler(RequestHanlderService requestHanlderService) {
        this.requestHanlderService = requestHanlderService;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (!(msg instanceof FullHttpRequest)) {
            throw new IllegalStateException("状态异常");
        }
        FullHttpRequest fullHttpRequest = null;
        FullHttpResponse fullHttpResponse = null;
        try {
            fullHttpRequest = (FullHttpRequest) msg;
            ByteBuf content = fullHttpRequest.content();
            String dealWith = requestHanlderService.simpleDealWith(fullHttpRequest.uri(),content);

            if (dealWith != null && dealWith.length() > 0) {
                fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.OK, Unpooled.wrappedBuffer(dealWith.getBytes()));
            } else {
                fullHttpResponse = new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.NOT_FOUND, Unpooled.wrappedBuffer("not found".getBytes()));
            }

            fullHttpResponse.headers().set("Content-Type", "application/json");
            fullHttpResponse.headers().set("Content-Length", fullHttpResponse.content().readableBytes());
            fullHttpResponse.headers().set("Connection", "keep-alive");
        } finally {
            ctx.writeAndFlush(fullHttpResponse);
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IllegalStateException) {
            System.out.println("状态异常:" + cause.getMessage());
            return;
        }
        super.exceptionCaught(ctx, cause);
    }

}
