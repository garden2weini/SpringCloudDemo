package com.merlin.patterns.proxy.jingtai;

public class GamePlayer implements IGamePlayer {
    String userName = null;
    public GamePlayer(String name) {
        this.userName = name;
    }

    @Override
    public void login(String user, String passwd) {
        System.out.println(userName + " login.");
    }

    @Override
    public void killBoss() {
        System.out.println(userName + " kill boss.");
    }

    @Override
    public void upgrade() {
        System.out.println(userName + " level updated.");
    }
}
