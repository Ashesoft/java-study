package com.longrise.study.annotation;

public class NetMessage implements IMessage {

    @Override
    public void send(String msg) {
        System.out.println("[网络消息发送]" + msg);
    }
    
}