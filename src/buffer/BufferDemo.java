package buffer;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.SocketTimeoutException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo {
    /**
     * channel结合bytebuffer读取文件操作
     * 详细分析limit capacity position的值
     * @throws IOException
     */
    @Test
    public void buffer01() throws IOException {
        String filePath = System.getProperty("user.dir") + "/resource/test1.txt";
        //File channel
        RandomAccessFile file = new RandomAccessFile(filePath,"rw");
        FileChannel channel = file.getChannel();
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        System.out.println("buffer.position() = " + buffer.position());
        System.out.println("buffer.limit() = " + buffer.limit());
        System.out.println("=============================");
        int bytesRead  = channel.read(buffer);
        System.out.println("buffer.position() = " + buffer.position());
        System.out.println("buffer.limit() = " + buffer.limit());
        System.out.println("=============================");
        while(bytesRead!=-1){
             //read 模式
            buffer.flip();
            System.out.println("flip之后");
            System.out.println("buffer.position() = " + buffer.position());
            System.out.println("buffer.limit() = " + buffer.limit());
            System.out.println("=============================");
            while(buffer.hasRemaining()){
                System.out.println((char)buffer.get());
            }
            System.out.println("buffer get之后");
            System.out.println("buffer.position() = " + buffer.position());
            System.out.println("buffer.limit() = " + buffer.limit());
            System.out.println("=============================");
            buffer.clear();
            System.out.println("clear之后");
            System.out.println("buffer.position() = " + buffer.position());
            System.out.println("buffer.limit() = " + buffer.limit());
            System.out.println("=============================");
            bytesRead=channel.read(buffer);
            System.out.println("循环read之后");
            System.out.println("buffer.position() = " + buffer.position());
            System.out.println("buffer.limit() = " + buffer.limit());

        }
        channel.close();
        file.close();

    }

    /**
     * 测试put写入，get取出
     */
    @Test
    public void buffer02(){
        // create
        IntBuffer buffer=IntBuffer.allocate(8);
        // buffer put
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put(i+1);
        }
        // buffer get (change to read mode)
        buffer.flip();
        while(buffer.hasRemaining()){
            int value = buffer.get();
            System.out.println(value+"  ");
        }


    }

    /**
     * 子缓冲区的操作
     */
    @Test
    public void buffer03(){
        ByteBuffer buffer=ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        System.out.println("put之后");
        System.out.println("buffer.position() = " + buffer.position());
        System.out.println("buffer.limit() = " + buffer.limit());
        System.out.println("=============================");
        // 创建子缓冲区
        buffer.position(3);
        buffer.limit(7);
        System.out.println("设置position和limit之后");
        System.out.println("buffer.position() = " + buffer.position());
        System.out.println("buffer.limit() = " + buffer.limit());
        System.out.println("=============================");
        ByteBuffer slice = buffer.slice();
        System.out.println("slice之后");
        System.out.println("buffer.position() = " + buffer.position());
        System.out.println("buffer.limit() = " + buffer.limit());
        System.out.println("slice.position() = " + slice.position());
        System.out.println("slice.limit() = " + slice.limit());
        System.out.println("=============================");
        // 改变子缓冲区
        for (int i = 0; i < slice.capacity(); i++) {
            byte b = slice.get(i);
            b*=11;
            slice.put(i,b);
        }
        buffer.position(0);
        buffer.limit(10);
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }

    /**
     * 只读缓冲区只是一个副本，共享数据
     */
    @Test
    public void readOnlyBuffer(){
        ByteBuffer buffer=ByteBuffer.allocate(10);
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte)i);
        }
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        System.out.println("readOnlyBuffer.position() = " + readOnlyBuffer.position());
        System.out.println("readOnlyBuffer.limit() = " + readOnlyBuffer.limit());
        for (int i = 0; i < buffer.capacity(); i++) {
            byte b = buffer.get(i);
            b*=10;
            buffer.put(i,b);
        }
//        readOnlyBuffer.position(0);
//        readOnlyBuffer.limit(buffer.capacity());
        readOnlyBuffer.flip();
        System.out.println("readOnlyBuffer.position() = " + readOnlyBuffer.position());
        System.out.println("readOnlyBuffer.limit() = " + readOnlyBuffer.limit());
        while(readOnlyBuffer.hasRemaining()){
            System.out.println(readOnlyBuffer.get());
        }
    }


}
