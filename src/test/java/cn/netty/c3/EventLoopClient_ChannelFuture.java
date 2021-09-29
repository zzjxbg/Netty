package cn.netty.c3;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringEncoder;
import lombok.extern.slf4j.Slf4j;

import java.net.InetSocketAddress;

@Slf4j
public class EventLoopClient_ChannelFuture {
    public static void main(String[] args) throws InterruptedException {
        // 2.带有Future、Promise的类型都是和异步方法配套使用,用来处理结果
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
        // 2.1 使用sync方法同步处理结果
//        channelFuture.sync(); //阻塞住当前线程(主线程),直到nio线程连接建立完毕
        // 无阻塞向下执行获取channel
        // connect 方法是异步的,意味着不等连接建立,方法执行就返回了
        // 因此channelFuture对象中不能立刻获得到正确的Channel对象
//        Channel channel = channelFuture.channel();
//        log.debug("{}",channel);
//        channel.writeAndFlush("success");

        // 2.2 使用addListener(回调对象) 方法异步处理结果(主线程交由其他线程完成连接后调用)
        channelFuture.addListener(new ChannelFutureListener() {
            @Override
            // 在nio线程连接建立好之后,会调用operationComplete
            public void operationComplete(ChannelFuture channelFuture) throws Exception {
                Channel channel = channelFuture.channel();
                log.debug("{}",channel);
                channel.writeAndFlush("success");
            }
        });

    }
}
