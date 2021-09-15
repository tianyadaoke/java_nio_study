package chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

// 服务器端
public class ChatServer {
    public static void main(String[] args) {
        // 启动服务端
        try {
            new ChatServer().startServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 服务端启动的方法
    public void startServer() throws IOException {
        // 创建Selector
        Selector selector = Selector.open();
        // 创建ServerSocketChannel
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        // 为channel绑定端口
        serverSocketChannel.bind(new InetSocketAddress(8000));
        // 设置非阻塞模式
        serverSocketChannel.configureBlocking(false);
        // 注册到选择器
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println("服务器已经启动成功");
        // 循环等待是否有新的连接
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
                if (selectionKey.isAcceptable()) {
                    acceptOperator(serverSocketChannel, selector);
                } else if (selectionKey.isReadable()) {
                    readOperator(selector, selectionKey);
                }


            }

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
        // 把客户端消息广播到其他客户端
        if (message.length() > 0) {
            // 广播
            System.out.println(message);
            castOtherClient(message, selector, socketChannel);
        }
    }

    // 广播给其他客户端
    private void castOtherClient(String message, Selector selector, SocketChannel socketChannel) throws IOException {
        // 获取到所有已经接入的客户端
        Set<SelectionKey> keySet = selector.keys();
        // 循环向所有的channel广播消息
        for (SelectionKey key : keySet) {
            // 获取每个channel
            Channel targetChannel = key.channel();
            if (targetChannel instanceof SocketChannel && targetChannel != socketChannel) {
                ((SocketChannel) targetChannel).write(Charset.forName("UTF-8").encode(message));
            }
        }
    }

    // 处理接入状态
    private void acceptOperator(ServerSocketChannel serverSocketChannel, Selector selector) throws IOException {
        // 接入状态，创建socketchannel
        SocketChannel socketChannel = serverSocketChannel.accept();
        // 设置非阻塞
        socketChannel.configureBlocking(false);
        // channel注册到选择器，监听可读状态
        socketChannel.register(selector, SelectionKey.OP_READ);
        // 客户端回复信息
        socketChannel.write(Charset.forName("UTF-8").encode("欢迎进入聊天室，请注意隐私安全"));
    }
}
