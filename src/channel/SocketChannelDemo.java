package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class SocketChannelDemo {
    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel=SocketChannel.open(new InetSocketAddress("www.baidu.com",80));
// 第二种打开方法
//        SocketChannel socketChannel2=SocketChannel.open();
//        socketChannel2.connect(new InetSocketAddress("www.baidu.com",80))
        socketChannel.configureBlocking(false);

        ByteBuffer byteBuffer= ByteBuffer.allocate(1024);
        socketChannel.read(byteBuffer);
        socketChannel.close();
        System.out.println("read over");
    }
}
