package com.longrise.study.sjms.mbms;

public class Footbal extends Game {

    @Override
    void initialize() {
        System.out.println("Football Game Initailized! Start playing");
    }

    @Override
    void startplay() {
        System.out.println("Football Game Started. Enjoy the game!");
    }

    @Override
    void endplay() {
        System.out.println("Football Game Finished!");
    }
    
}