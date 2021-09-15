package async;

import org.junit.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.Future;

public class AsyncFileChannelDemo {
    /**
     * 通过future读取
     * @throws IOException
     */
    @Test
    public void readAsyncFuture() throws IOException {
        String filePath = System.getProperty("user.dir") + "/resource/test4.txt";
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Path.of(filePath), StandardOpenOption.READ);
        ByteBuffer buffer  = ByteBuffer.allocate(1024);
        Future<Integer> future = channel.read(buffer, 0);
        while(!future.isDone()){
            System.out.println("reading.......");
        }
        buffer.flip();
        // 输出字节
//        while(buffer.hasRemaining()){
//            System.out.println(buffer.get());
//        }
        byte[] data =new byte[buffer.limit()];
        buffer.get(data);
        System.out.println(new String(data));
        buffer.clear();
    }

    /**
     * 通过completion读取
     * @throws IOException
     * @throws InterruptedException
     */
    @Test
    public void readAsyncComplete() throws IOException, InterruptedException {
        String filePath = System.getProperty("user.dir") + "/resource/test4.txt";
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Path.of(filePath), StandardOpenOption.READ);
        ByteBuffer buffer  = ByteBuffer.allocate(1024);
        System.out.println("开始");
        channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("result:"+result);
                attachment.flip();
                byte[] data =new byte[attachment.limit()];
                attachment.get(data);
                System.out.println(new String(data));
                attachment.clear();
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("读取失败");
            }
        });

        Thread.sleep(2000);
    }

    /**
     * 通过Future写数据
     * @throws IOException
     */
    @Test
    public void writeAsyncFuture() throws IOException {
        String filePath = System.getProperty("user.dir") + "/resource/test4.txt";
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Path.of(filePath), StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("我是谁我在哪我干什么".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        Future<Integer> future = channel.write(buffer, 0);
        while(!future.isDone()){
            System.out.println("正在写入。。。。");
        }
        System.out.println("写入完成");
        buffer.clear();
    }

    /**
     * 通过complete写数据
     * @throws IOException
     */
    @Test
    public void writeAsyncComplete() throws IOException, InterruptedException {
        String filePath = System.getProperty("user.dir") + "/resource/test4.txt";
        AsynchronousFileChannel channel = AsynchronousFileChannel.open(Path.of(filePath), StandardOpenOption.WRITE);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put("我是谁我在哪我干什么".getBytes(StandardCharsets.UTF_8));
        buffer.flip();
        channel.write(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
            @Override
            public void completed(Integer result, ByteBuffer attachment) {
                System.out.println("bytes write:"+result);
            }

            @Override
            public void failed(Throwable exc, ByteBuffer attachment) {
                System.out.println("写入失败");
            }
        });
        Thread.sleep(1000);
    }
}
