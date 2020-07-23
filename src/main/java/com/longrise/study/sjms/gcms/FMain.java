package com.longrise.study.sjms.gcms;

public class FMain {
    public static void main(String[] args) {
        System.out.println(NetMessage.class.getName());
        IFactory.getInstances(NetMessage.class).sendMessage();
        IFactory.getInstances(NetService.class).sendService();
    }
}