package cn.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopClient_ChannelFuture {
    public static void main(String[] args) throws InterruptedException {
        ChannelFuture channelFuture = new Bootstrap()
                .group(new NioEventLoopGroup())
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override // 在连接建立后被调用
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringEncoder());
                    }
                })
                // 1. 连接到服务器
                // connect方法异步非阻塞,main方法发起调用,真正执行connect是nio线程
                .connect(new InetSocketAddress("localhost",8080));  // 1s后连接上
        channelFuture.sync();
        // 无阻塞向下执行获取channel
        // connect 方法是异步的,意味着不等连接建立,方法执行就返回了
        // 因此channelFuture对象中不能立刻获得到正确的Channel对象
        Channel channel = channelFuture.channel();
        log.debug("{}",channel);
        // 2.向服务器发送数据
        channel.writeAndFlush("success");
    }
}
