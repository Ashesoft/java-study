package com.longrise.study.annotation;

public class LocalMessage implements IMessage{
    @Override
    public void send(String msg) {
        System.out.println("[本地消息发送]" + msg);
    }
    
}