package com.longrise.study.sjms.gcms;

public class NetService implements IService{

    @Override
    public void sendService() {
        System.out.println("[服务]您接受了服务请求.");
    }
    
}