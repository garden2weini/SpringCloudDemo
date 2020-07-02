package com.merlin.patterns.proxy.jingtai;

public class GamePlayerProxy implements  IGamePlayer {
    IGamePlayer player = null;
    public GamePlayerProxy(IGamePlayer player) {
        this.player = player;
    }

    @Override
    public void login(String user, String passwd) {
        this.player.login(user, passwd);
    }

    @Override
    public void killBoss() {
        this.player.killBoss();
    }

    @Override
    public void upgrade() {
        this.player.upgrade();
    }
}
