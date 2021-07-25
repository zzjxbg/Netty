package cn.netty.c1;


import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;

import static cn.netty.c1.ByteBufferUtil.debugAll;

public class TestByteBufferString {
    public static void main(String[] args) {
        //1.字符串转为ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);
        buffer.put("Hello".getBytes());
        debugAll(buffer);

        //2.Charset方法(转换完自动切换成读模式)
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("Hello");
        debugAll(buffer2);

        //3.wrap(转换完自动切换成读模式)
        ByteBuffer buffer3 = ByteBuffer.wrap("Hello".getBytes());
        debugAll(buffer3);

        //4.ByteBuffer转为字符串
        String str1 = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(str1);

        buffer.flip();  //先切换成读模式再转成字符串
        String str2 = StandardCharsets.UTF_8.decode(buffer).toString();
        System.out.println(str2);

    }
}
