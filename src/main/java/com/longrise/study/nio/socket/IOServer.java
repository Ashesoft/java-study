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
            int recvMsgSize = 0;
            byte[] recvBuffer = new byte[120];
            Socket clnSocket = null;
            InputStream in = null;
            try {
                while (true) {
                    clnSocket = serverSocket.accept();
                    in = clnSocket.getInputStream();
                    SocketAddress clientAddress = clnSocket.getRemoteSocketAddress();
                    System.out.println("Handling client at " + clientAddress);

                    while ((recvMsgSize = in.read(recvBuffer)) != -1) {
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