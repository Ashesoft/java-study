package com.longrise.study.sjms.clms;

/**
 * 普通信息处理类
 */
public class InfoLogger extends AbstractLogger {

    public InfoLogger(){
        super.level = Level.INFO.getLevel();
        super.setNextLogger(null);
    }

    
    @Override
    protected void write(String message) {
        System.out.println("info = " + message);
    }
    
}