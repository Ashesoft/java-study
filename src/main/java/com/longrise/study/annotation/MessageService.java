package com.longrise.study.annotation;

/**
 * 定义一个额消息服务类, 专门负责消息的处理
 */
@Iannotation(LocalMessage.class)
public class MessageService {
    private IMessage iMessage;

    public MessageService(){
        Iannotation annotation = this.getClass().getAnnotation(Iannotation.class);
        this.iMessage = (IMessage) Factory.getProxyInstance(annotation.value());

    }

    public void send(String msg){
        this.iMessage.send(msg);
    }
}