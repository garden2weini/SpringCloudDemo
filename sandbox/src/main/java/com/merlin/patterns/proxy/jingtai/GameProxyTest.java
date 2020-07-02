package com.merlin.patterns.proxy.jingtai;

public class GameProxyTest {
    public static void main(String[] args) {
        IGamePlayer player = new GamePlayer("草原狼");

        IGamePlayer proxy = new GamePlayerProxy(player);
        proxy.login("merlin", "123456");
        proxy.killBoss();
        proxy.upgrade();
    }

}
