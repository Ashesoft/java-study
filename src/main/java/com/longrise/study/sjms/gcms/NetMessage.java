package com.longrise.study.sjms.gcms;

public class NetMessage implements IMessage{

    @Override
    public void sendMessage() {
        System.out.println("[消息]您有一条消息请查阅.");
    }

}