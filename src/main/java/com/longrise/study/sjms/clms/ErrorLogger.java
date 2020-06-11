package com.longrise.study.sjms.clms;

/**
 * 错误信息处理类
 */
public class ErrorLogger extends AbstractLogger {

    public ErrorLogger(){
        this.level = Level.ERROR.getLevel();
        super.setNextLogger(new DebugLogger());
    }

    @Override
    protected void write(String message) {
        System.out.println("error = " + message);
    }
    
}