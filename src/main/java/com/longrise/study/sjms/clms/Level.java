package com.longrise.study.sjms.clms;

/**
 * 信息输出等级枚举类
 */
public enum Level {
    INFO(1),
    DEBUG(2),
    ERROR(3);

    private int level;

    public int getLevel(){
        return this.level;
    }
    Level(int level){
        this.level = level;
    }

}