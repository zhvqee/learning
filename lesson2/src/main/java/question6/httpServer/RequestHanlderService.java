package question6.httpServer;

import io.netty.buffer.ByteBuf;

public class RequestHanlderService {


    public String simpleDealWith(String uri, ByteBuf byteBuf) {
        return "uri:" + uri + ",helloWorld" + ",params:" + toString(byteBuf);
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
