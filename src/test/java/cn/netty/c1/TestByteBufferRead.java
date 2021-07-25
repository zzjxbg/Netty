package cn.netty.c1;

import java.nio.ByteBuffer;

import static cn.netty.c1.ByteBufferUtil.debugAll;

public class TestByteBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a','b','c','d'});
        buffer.flip();

        //rewind 从头开始读
        buffer.get(new byte[4]);
        debugAll(buffer);
        buffer.rewind();
        System.out.println((char)buffer.get());

        //mark & reset
        //mark 做一个标记,记录position位置,reset是将position重置到mark的位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.mark();  //加标记,索引2的位置
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        buffer.reset();  //将position重置到索引2
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());

        //get(i) 不会改变position的位置
        System.out.println((char)buffer.get(3));
        debugAll(buffer);

    }
}
