package chat;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

public class ClientThread implements Runnable {
    private Selector selector;

    public ClientThread(Selector selector) {
        this.selector = selector;
    }

    @Override
    public void run() {
        try {
            for (; ; ) {
                //获取channel数量
                int readChannels = selector.select();
                if (readChannels == 0) {
                    continue;
                }
                //获取可用channel.
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                //遍历集合
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    // 移除set集合中当前的selectionKey
                    iterator.remove();
                    // 根据就绪状态调用方法
                    if (selectionKey.isReadable()) {
                        readOperator(selector, selectionKey);
                    }


                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    // 处理read状态
    private void readOperator(Selector selector, SelectionKey selectionKey) throws IOException {
        // 从selectionkey获取到已经就绪的通道
        SocketChannel socketChannel = (SocketChannel) selectionKey.channel();
        // 创建buffer
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        // 循环读取客户信息
        int readLength = socketChannel.read(buffer);
        String message = "";
        if (readLength > 0) {
            // 切换读模式
            buffer.flip();
            // 读取内容
            message += Charset.forName("UTF-8").decode(buffer);
        }
        // 将channel再次注册到选择器上，监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        if (message.length() > 0) {
            System.out.println(message);
        }
    }

}
