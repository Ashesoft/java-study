package com.longrise.study.sjms.clms;

/**
 * 调试信息处理类
 */
public class DebugLogger extends AbstractLogger {

    public DebugLogger(){
        this.level = Level.DEBUG.getLevel();
        super.setNextLogger(new InfoLogger());
    }

    @Override
    protected void write(String message) {
        System.out.println("debug = " + message);
    }
    
}