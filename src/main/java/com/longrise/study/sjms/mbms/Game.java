package com.longrise.study.sjms.mbms;

public abstract class Game {
    abstract void initialize();
    abstract void startplay();
    abstract void endplay();

    // 模板 (final关键词目的: 防止恶意操作)
    public final void play(){
        //初始化游戏
        initialize();
        
        // 开始游戏
        startplay();
        
        // 结束游戏
        endplay();
    }
}