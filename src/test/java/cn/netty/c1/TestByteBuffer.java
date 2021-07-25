package cn.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

@Slf4j
public class TestByteBuffer {

    public static void main(String[] agrs) {
        //打开FileChannel(两种方式)
        //1.输入输出流  2.RandomAccessFile
        try (FileChannel channel = new FileInputStream("data.txt").getChannel()) {
            //准备缓冲区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            while(true) {
                //从channel读取数据,向buffer写入
                int len = channel.read(buffer);
                log.debug("读取到的字节数 {}",len);
                if (len == -1) {  //没有内容了
                    break;
                }
                //打印buffer的内容
                buffer.flip();  //切换至读模式
                while(buffer.hasRemaining()) {  //是否还有剩余未读数据
                    byte b = buffer.get();  //每次读取一个字节
                    log.debug("实际字节 {}",(char)b);
                }
                buffer.clear();  //buffer切换成写模式
            }
        } catch(IOException e) {
        }
    }
}
