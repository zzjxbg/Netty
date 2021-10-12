package org.example.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.embedded.EmbeddedChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.logging.LoggingHandler;
import org.example.message.LoginRequestMessage;

public class TestMessageCodec {
    public static void main(String[] args) throws Exception {
        EmbeddedChannel channel = new EmbeddedChannel(
                new LengthFieldBasedFrameDecoder(1024,12,4,0,0),
                new LoggingHandler(),
                new MessageCodec());

        // encode
        LoginRequestMessage message = new LoginRequestMessage("zhangsan","123");
        channel.writeOutbound(message);

        // decode
        ByteBuf buf = ByteBufAllocator.DEFAULT.buffer();
        new MessageCodec().encode(null,message,buf);

        // 入站解码(读取客户端来的数据)
        channel.writeInbound(buf);
    }
}
