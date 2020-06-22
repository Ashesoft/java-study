package com.longrise.study.sjms.mbms;

public class Cricket extends Game {

    @Override
    void initialize() {
        System.out.println("Cricket Game Initialized! Start playing.");
    }
    
    @Override
    void startplay() {
        System.out.println("Cricket Game Started. Enjoy the game!");
    }
    
    @Override
    void endplay() {
        System.out.println("Cricket Game Finished!");
    }
    
}