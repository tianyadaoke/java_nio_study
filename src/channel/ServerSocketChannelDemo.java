package channel;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

public class ServerSocketChannelDemo {
    public static void main(String[] args) {
        int port = 8080;

        //buffer
        ByteBuffer buffer = ByteBuffer.wrap("hello你好".getBytes(StandardCharsets.UTF_8));
        // ServerSocketChannel
        try (ServerSocketChannel ssc = ServerSocketChannel.open()) {
            // bind
            ssc.socket().bind(new InetSocketAddress(port));
            // config non blocking
            ssc.configureBlocking(false);
            while(true){
                System.out.println("waiting for connections");
                // listen
                SocketChannel sc = ssc.accept();
                if(sc==null){
                    System.out.println("null");
                    TimeUnit.SECONDS.sleep(3);
                } else{
                    System.out.println("incoming connection from "+ sc.socket().getRemoteSocketAddress());
                    buffer.rewind();
                    sc.write(buffer);
                    sc.close();
                }
            }
        } catch(IOException | InterruptedException e){
            e.printStackTrace();
        }

    }
}
