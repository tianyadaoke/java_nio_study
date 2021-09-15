package buffer;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BufferDemo2 {
    String filePath = System.getProperty("user.dir") + "/resource/test1.txt";
    String fileTo = System.getProperty("user.dir") + "/resource/test3.txt";

    /**
     * 直接内存bytebuffer适合文件读写
     * @throws IOException
     */
    @Test
    public void bufferDirect() throws IOException {
        FileInputStream fileInputStream = new FileInputStream(filePath);
        FileChannel fileInputStreamChannel = fileInputStream.getChannel();
        FileOutputStream fileOutputStream = new FileOutputStream(fileTo);
        FileChannel fileOutputStreamChannel = fileOutputStream.getChannel();
        // 创建直接缓冲区
        ByteBuffer buffer = ByteBuffer.allocateDirect(1024);
        while (true) {
            buffer.clear();
            int r = fileInputStreamChannel.read(buffer);
            if (r == -1) {
                break;
            }
            buffer.flip();
            fileOutputStreamChannel.write(buffer);
        }
    }
}
