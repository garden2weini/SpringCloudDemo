package com.merlin.patterns.proxy.jingtai;

public class GameTest {
    public static void main(String[] args) {
        IGamePlayer player = new GamePlayer("草原狼");

        player.login("merlin", "123456");
        player.killBoss();
        player.upgrade();
    }
}
