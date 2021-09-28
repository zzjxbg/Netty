package cn.netty.c5;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

import static cn.netty.c1.ByteBufferUtil.debugAll;

@Slf4j
public class AioFileChannel {
    public static void main(String[] args) throws IOException {
        try {
            AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("data.txt"), StandardOpenOption.READ);
            //参数1 ByteBuffer
            //参数2 读取的起始位置
            //参数3 附件
            //参数4 回调对象
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.debug("read begin...");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override // read成功
                public void completed(Integer result, ByteBuffer attachment) {
                    log.debug("read completed...");
                    attachment.flip();
                    debugAll(attachment);
                }

                @Override // read失败
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            log.debug("read end...");
        } catch (IOException e) {
            e.printStackTrace();
        }
        //默认文件AIO使用的线程都是守护线程,所以最后要执行System.in.read()以避免守护线程意外结束
        System.in.read();
    }
}
