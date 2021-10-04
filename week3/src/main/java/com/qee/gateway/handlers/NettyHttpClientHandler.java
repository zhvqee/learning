package com.qee.gateway.handlers;

import com.qee.gateway.nettyhttpclient.ResponseFuture;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpResponse;

public class NettyHttpClientHandler extends ChannelInboundHandlerAdapter {

    private ResponseFuture responseFuture;

    public void setResponseFuture(ResponseFuture responseFuture) {
        this.responseFuture = responseFuture;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;
            System.out.println("CONTENT_TYPE:" + response.headers().get(HttpHeaders.Names.CONTENT_TYPE));
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String strContent = toString(buf);
            responseFuture.setSuccess(strContent);
            System.out.println(buf.toString(io.netty.util.CharsetUtil.UTF_8));
            buf.release();
        }
    }

    private String toString(ByteBuf byteBuf) {
        String result = null;
        if (byteBuf.hasArray()) {
            result = new String(byteBuf.array(), byteBuf.readerIndex(), byteBuf.readableBytes());
        } else {
            byte[] by = new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(by, byteBuf.readerIndex(), byteBuf.readableBytes());
            result = new String(by, 0, by.length);

        }
        return result;
    }
}
