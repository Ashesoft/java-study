package com.longrise.study.nio.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

public class NIOServer {
    private static final int BUF_SIZE = 120;
    private static final int PORT = 9527;
    private static final int TIMEOUT = 3000;

    public static void main(String[] args) {
        selector();
    }

    public static void selector() {
        // 创建selector
        // 为了将Channel和Selector配合使用, 必须将Channel注册到Selector上, 通过SelectorChannel.register()方法类实现
        try (Selector selector = Selector.open(); ServerSocketChannel sSocketChannel = ServerSocketChannel.open()) {
            sSocketChannel.socket().bind(new InetSocketAddress(PORT));
            sSocketChannel.configureBlocking(false);

            // Channel与Selector一起使用时, Channel必须处于非阻塞模式下, 这意味着不能将FileChannel与Selector一起使用, 因为FileChannel不能切换到非阻塞模式, 而套接字(即socket)通道都可以
            // register()方法的第二个参数, 是一个"interest集合", 意思是在通过Selector监听Channnel时对什么事件感兴趣, 可以监听四种不同类型的事件"Connect, Accept, Read, Write";
            sSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            while (true) {
                /**
                 * select() 阻塞到至少有一个通道在注册的事件上就绪了;
                 * select(long timeout) 和 select()一样, 除了最长会阻塞timeout毫秒(参数);
                 * selectNow()不会阻塞, 不管什么通道就绪都会立刻返回(此方法执行非阻塞的选择操作, 如果自从前一次选择操作后, 没有通道变成可选择的, 则此方法直接返回零)
                 */
                if (selector.select(TIMEOUT) == 0) { // 返回的int值表示有多少通道已经就绪
                    System.out.println("==");
                    continue;
                }
                Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                while (iterator.hasNext()) {
                    SelectionKey key = iterator.next();

                    // 检测channel中什么事件或操作已经就绪
                    if (key.isAcceptable()) {
                        handleAccept(key);
                    }
                    if (key.isReadable()) {
                        handleRead(key);
                    }
                    if (key.isWritable() && key.isValid()) {
                        handleWrite(key);
                    }
                    if (key.isConnectable()) {
                        System.out.println("isConnectable = true");
                    }
                    iterator.remove();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void handleWrite(SelectionKey key) throws IOException {
        SocketChannel sChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        buffer.flip();
        while (buffer.hasRemaining()) {
            sChannel.write(buffer);
        }
        // buffer.compact();
    }

    private static void handleRead(SelectionKey key) throws IOException {
        SocketChannel sChannel = (SocketChannel) key.channel();
        ByteBuffer buffer = (ByteBuffer) key.attachment();
        var bytes = new byte[120];
        while (sChannel.read(buffer) != -1) {
            buffer.flip();
            while(buffer.hasRemaining()){
                buffer.get(bytes, 0, buffer.limit());
                System.out.println(new String(bytes));
            }
            buffer.clear();
        }
    }



    private static void handleAccept(SelectionKey key) throws IOException {
        ServerSocketChannel ssChannel = (ServerSocketChannel) key.channel();
        SocketChannel sChannel = ssChannel.accept(); // 监听新进来的连接
        sChannel.configureBlocking(false); // 设置成非阻塞模式, 在非阻塞模式下, accept()方法会立刻返回, 如果还没新连接进来, 返回的将是null, 因此需要检查返回的SocketChannel是否为null
        sChannel.register(key.selector(), SelectionKey.OP_READ, ByteBuffer.allocateDirect(BUF_SIZE));
        /**
         * ByteBuffer.allocateDirect(size); // 创建直接缓冲区(缓冲区建立在JVM中, 实际读写数据时, 需要在OS和JVM之间进行数据拷贝)
         * ByteBuffer.allocate(size); // 创建非直接缓冲区(缓冲区建立在受系统管理的物理内存中, OS和JVM直接通过这块物理内存进行交互, 没有了中间的拷贝环节)
         * 
         * 直接缓冲区:
         * 优点: 速度更快, 效率更高;
         * 缺点: 创建直接缓冲区将会有更多消耗, 数据进入直接缓冲区后, 后续写入磁盘等操作就完全由操作系统决定了, 不在由我们编写的程序控制了;
         * 什么时候用: 缓冲区要长时间使用(数据本身需要长时间在内存/缓冲区复用率很高), 或者大数据量的操作(大文件才能体现出速度优势);
         * 
         * 如何使用:
         * 通过ByteBuffer.allocateDirect(), 创建直接缓冲区
         * 通过内存映射文件的方式
         * 使用通道直接传输
         */
    }
}