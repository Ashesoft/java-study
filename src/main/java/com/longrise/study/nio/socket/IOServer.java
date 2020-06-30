package com.longrise.study.nio.socket;

import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;

public class IOServer {
    public static void main(String[] args) {
        server();
    }

    public static void server() {
        try (ServerSocket serverSocket = new ServerSocket(9527);) {
            var recvBuffer = new byte[120];
            Socket clnSocket = null;
            InputStream in = null;
            try {
                while (true) {
                    clnSocket = serverSocket.accept(); // 监听要连接到此套接字的socket连接, 并阻塞直到建立连接
                    in = clnSocket.getInputStream(); // 获取输入流
                    SocketAddress clientAddress = clnSocket.getRemoteSocketAddress();
                    System.out.println("Handling client at " + clientAddress);

                    while ((in.read(recvBuffer)) != -1) { // 循环读取输入流中的数据, 并再控制台输出
                        System.out.println(new String(recvBuffer));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                try {
                    if (in != null) {
                        in.close();
                    }
                    if (clnSocket != null) {
                        clnSocket.close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    
}