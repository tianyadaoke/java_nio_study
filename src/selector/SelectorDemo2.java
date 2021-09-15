package selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class SelectorDemo2 {
    public static void main(String[] args) throws IOException {
        //获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        //非阻塞模式
        socketChannel.configureBlocking(false);
        //创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String str = scanner.next();
            //写入buffer数据
            byteBuffer.put((new Date().toString()+"---->"+str).getBytes(StandardCharsets.UTF_8));
            //模式切换
            byteBuffer.flip();
            //写入通道
            socketChannel.write(byteBuffer);
            //清空关闭
            byteBuffer.clear();

        }
        socketChannel.close();
    }
    // 客户端代码
    @Test
    public void clientDemo() throws IOException {
        //获取通道，绑定主机和端口号
        SocketChannel socketChannel = SocketChannel.open(new InetSocketAddress("127.0.0.1", 8080));
        //非阻塞模式
        socketChannel.configureBlocking(false);
        //创建buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        //写入buffer数据
        byteBuffer.put(new Date().toString().getBytes(StandardCharsets.UTF_8));
        //模式切换
        byteBuffer.flip();
        //写入通道
        socketChannel.write(byteBuffer);
        //清空关闭
        byteBuffer.clear();
        socketChannel.close();
    }

    // 服务代码
    @Test
    public void ServerDemo() throws IOException {
        //获取服务通道
        ServerSocketChannel serverSocketChannel =  ServerSocketChannel.open();
        //切换非阻塞
        serverSocketChannel.configureBlocking(false);
        //创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //绑定端口号
        serverSocketChannel.bind(new InetSocketAddress(8080));
        //获取selector选择器
        Selector selector = Selector.open();
        //通道注册到选择器进行监听
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        //选择器进行轮询进行后续操作
        while(selector.select()>0){
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            //遍历
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()){
                SelectionKey key = iterator.next();
                if(key.isAcceptable()){
                    //获取连接
                    SocketChannel socketChannel=serverSocketChannel.accept();
                    //切换非阻塞模式
                    socketChannel.configureBlocking(false);
                    //注册
                    socketChannel.register(selector, SelectionKey.OP_READ);
                }else if(key.isReadable()){
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    //读取数据
                    int length = channel.read(byteBuffer);
                    while(length>0){
                        byteBuffer.flip();
                        System.out.println(new String(byteBuffer.array(),0,length));
                        byteBuffer.clear();
                        length = channel.read(byteBuffer);
                    }
                }
            }
            iterator.remove();
        }

    }
}
