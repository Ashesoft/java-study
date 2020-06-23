package com.longrise.study.http.network;

import java.net.InetAddress;
import java.util.Arrays;

// 获取指定主机的ip地址
public class GetIP {
    public static void main(String[] args) {
        try {
            InetAddress addr = InetAddress.getByName("cn.bing.com");
            String hostName = addr.getHostName();
            String hostAddress = addr.getHostAddress();
            addr = InetAddress.getLocalHost();
            System.out.println("主机名为:" + hostName);
            System.out.println("ip地址为:" + hostAddress);
            System.out.println("本地主机名为:" + addr);

            InetAddress[] allByName = InetAddress.getAllByName("localhost");
            System.out.println(Arrays.toString(allByName));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}