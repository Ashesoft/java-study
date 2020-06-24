package com.longrise.study.nio.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.util.concurrent.TimeUnit;

public class Client {
    public static void main(String[] args) {
        client();
    }

    public static void client(){
        ByteBuffer buffer = ByteBuffer.allocate(120);
        try (SocketChannel socketChannel = SocketChannel.open();) {
            socketChannel.configureBlocking(false);
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 9527));
            if(socketChannel.finishConnect()){
                var i = 1;
                while(true){
                    TimeUnit.SECONDS.sleep(5);
                    String info = "第" + i++ + "天学习java";
                    buffer.put(info.getBytes());
                    buffer.flip();
                    while(buffer.hasRemaining()){
                        socketChannel.write(buffer);
                    }
                    buffer.clear();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}