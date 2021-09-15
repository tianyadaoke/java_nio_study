package selector;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class SelectorDemo {
    @Test
    public void demo1() throws IOException {
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));
//        System.out.println(serverSocketChannel.validOps());
        // socketChannel不支持accept
//        SocketChannel socketChannel = SocketChannel.open();
//        System.out.println(socketChannel.validOps());
        // 注册到通道
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        // 查询已经就绪的通道
        Set<SelectionKey> selectionKeys = selector.selectedKeys();
        // 遍历
        Iterator<SelectionKey> iterator = selectionKeys.iterator();
        while(iterator.hasNext()){
            SelectionKey key = iterator.next();
            if(key.isAcceptable()) {

            } else if (key.isConnectable()){

            }else if (key.isReadable()){

            }else if (key.isWritable()){

            }
            iterator.remove();
        }
    }
}
