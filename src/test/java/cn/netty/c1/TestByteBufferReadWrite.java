package cn.netty.c1;

import java.nio.ByteBuffer;

import static cn.netty.c1.ByteBufferUtil.debugAll;

public class TestByteBufferReadWrite {

    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put((byte) 0x61);  //写入字符a
        debugAll(buffer);
        buffer.put(new byte[]{0x62,0x63,0x64});
        debugAll(buffer);  //写入字符b c d
//        System.out.println(buffer.get());
        buffer.flip();
        System.out.println(buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);
        buffer.put(new byte[]{0x65,0x66});
        debugAll(buffer);
    }
}
