package channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileChannelReadDemo {
    public static void main(String[] args) throws IOException {
        CharsetDecoder decoder= StandardCharsets.UTF_8.newDecoder();
        String filePath = System.getProperty("user.dir") + "/resource/test1.txt";
        if (!Files.exists(Path.of(filePath))) {
            Files.createFile(Path.of(filePath));
        }
        // 创建file channel
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        FileChannel channel = file.getChannel();
        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 数据从channel读取到buffer中
        int bytesRead =channel.read(buffer);
        StringBuilder sb = new StringBuilder();
        while (bytesRead!=-1){
            System.out.println("读取了："+bytesRead);
            buffer.flip();
            while(buffer.hasRemaining()){
                sb.append((char)buffer.get());
            }
            buffer.clear();
            bytesRead=channel.read(buffer);
        }
        channel.close();
        file.close();
        System.out.println(sb.toString());
        System.out.println("操作结束");
    }
}
