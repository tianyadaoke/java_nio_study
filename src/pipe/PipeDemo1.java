package pipe;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Pipe;
import java.nio.charset.StandardCharsets;

public class PipeDemo1 {
    public static void main(String[] args) throws IOException {
        // 创建管道
        Pipe pipe = Pipe.open();
        // 获取sink通道
        Pipe.SinkChannel sink = pipe.sink();
        // 创建缓冲区
        ByteBuffer writeBuffer=ByteBuffer.allocate(1024);
        writeBuffer.put("哈哈哈".getBytes(StandardCharsets.UTF_8));
        writeBuffer.flip();
        //写入数据
        sink.write(writeBuffer);
        //获取source通道
        Pipe.SourceChannel source = pipe.source();
        //创建缓冲区
        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
        //读取数据
        int length = source.read(readBuffer);
        System.out.println(new String(readBuffer.array(),0,length));
        //关闭通道
        source.close();
        sink.close();
    }
}
