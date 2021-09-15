package filelock;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class FileLockDemo1 {
    public static void main(String[] args) throws IOException {
        String filePath = System.getProperty("user.dir") + "/resource/test4.txt";
        writeFile(filePath);
        readFile(filePath);
    }

    private static void writeFile(String filePath) throws IOException {
        ByteBuffer buffer = ByteBuffer.wrap("哈哈哈你是谁啊".getBytes(StandardCharsets.UTF_8));
        FileChannel fileChannel = FileChannel.open(Path.of(filePath),StandardOpenOption.READ,StandardOpenOption.WRITE);
        FileLock lock = fileChannel.lock(0,Long.MAX_VALUE,false);
        System.out.println(lock.isShared());
        fileChannel.write(buffer);
        fileChannel.close();
    }

    private static void readFile(String filePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(filePath));
            lines.forEach(l -> System.out.println(l));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
