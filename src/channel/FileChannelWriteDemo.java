package channel;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileChannelWriteDemo {
    public static void main(String[] args) throws IOException {
        String filePath = System.getProperty("user.dir") + "/resource/test1.txt";
        if (!Files.exists(Path.of(filePath))) {
            Files.createFile(Path.of(filePath));
        }
        // 创建file channel
        RandomAccessFile file = new RandomAccessFile(filePath, "rw");
        FileChannel channel = file.getChannel();
        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        String newData="new data new data \n new data new data \n 测试中文可以吗";
        buffer.clear();
        buffer.put(newData.getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        // buffer数据写入channel
        while(buffer.hasRemaining()){
            channel.write(buffer);
        }
        // 关闭channel
        channel.close();
        file.close();
    }
}
