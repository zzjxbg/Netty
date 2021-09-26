package cn.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static cn.netty.c1.ByteBufferUtil.debugRead;

@Slf4j
public class Server_Selector {
    public static void main(String[] args) throws IOException {

        // 1.创建selector,管理多个channel
        Selector selector = Selector.open();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2.建立selector和channel的联系(注册)
        //SelectionKey就是将来事件发生后,通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector,0,null);
        //key只关注accept事件
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("registry key:{}",sscKey);

        ssc.bind(new InetSocketAddress(8080));
        while(true) {
           //3.select方法,没有事件发生,线程阻塞,有事件发生,线程才会恢复运行
            //select在事件未处理时,它不会阻塞,事件发生后要么处理,要么取消,不能置之不理
            selector.select();
           //4.处理事件,selectedKeys内部包含了所有发生的事件
            Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
            while(iter.hasNext()) {
                SelectionKey key = iter.next();
                log.debug("key:{}",key);
                //5.区分事件类型
                if (key.isAcceptable()) { // 如果是accept
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey sckey = sc.register(selector,0,null);
                    sckey.interestOps(SelectionKey.OP_READ);
                    log.debug("{}",sc);
                } else if (key.isReadable()) { // 如果是read
                    SocketChannel channel = (SocketChannel) key.channel();
                    channel.read(buffer);
                    buffer.flip();
                    debugRead(buffer);
                }
//                key.cancel();
            }
        }
    }
}

