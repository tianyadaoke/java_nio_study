package channel;


import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class DatagramChannelDemo {
    //发送的实现
    @Test
    public void sendDatagram() throws IOException, InterruptedException {
        DatagramChannel sendChannel = DatagramChannel.open();
        InetSocketAddress sendAddress = new InetSocketAddress("127.0.0.1", 15672);
        while (true) {
            ByteBuffer buffer = ByteBuffer.wrap("data---------data".getBytes(StandardCharsets.UTF_8));
            sendChannel.send(buffer, sendAddress);
            System.out.println("已经完成发送");
            TimeUnit.SECONDS.sleep(1);
        }
    }

    //接收的实现
    @Test
    public void receiveDatagram() throws IOException {
        DatagramChannel receiveChannel = DatagramChannel.open();
        InetSocketAddress receiveAddress = new InetSocketAddress("127.0.0.1", 15672);
        receiveChannel.bind(receiveAddress);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        while (true) {
            buffer.clear();
            SocketAddress socketAddress = receiveChannel.receive(buffer);
            buffer.flip();
            System.out.println(socketAddress.toString());
            System.out.println(Charset.forName("utf-8").decode(buffer));

        }
    }

    //连接
    @Test
    public void testConnect() throws IOException {
        DatagramChannel conChannel = DatagramChannel.open();
        conChannel.bind(new InetSocketAddress(15672));
        conChannel.connect(new InetSocketAddress("127.0.0.1",15672));
        conChannel.write(ByteBuffer.wrap("data---data".getBytes(StandardCharsets.UTF_8)));
        ByteBuffer buffer=ByteBuffer.allocate(1024);
        while(true){
            buffer.clear();
            conChannel.read(buffer);
            buffer.flip();
            System.out.println(Charset.forName("utf-8").decode(buffer));
        }
    }
}
