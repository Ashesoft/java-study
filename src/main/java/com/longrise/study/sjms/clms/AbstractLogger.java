package com.longrise.study.sjms.clms; // Java设计模式

/**
 * 信息统一处理抽象类
 */
public abstract class AbstractLogger {
    protected int level;
    
    // 责任链中的下一个元素
    private AbstractLogger nextLogger;

    public void setNextLogger(AbstractLogger nextLogger){
        this.nextLogger = nextLogger;
    }

    public void logMessage(Level level, String message){
        if(this.level <= level.getLevel()){
            write(message);
        }
        if(nextLogger != null){
            nextLogger.logMessage(level, message);
        }
    }

    abstract protected void write(String message);
}