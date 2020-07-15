package com.merlin.rpc.v2dubbo.zk;

import com.merlin.rpc.Const;
import com.merlin.rpc.v2dubbo.util.LoadBalance;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import java.util.ArrayList;
import java.util.List;

/**
 * 消费者端使用，用以监控特定的服务提供者。
 * NOTE：监控zk中特定服务(Server Name)的集群(host:port)数量变化, 并将变化赋给LoadBalance.SERVICE_LIST
 */
public class WatchDog {
    private String BASE_SERVICE = Const.DUBBO_ZK_ROOT;
    private String SERVICE_NAME;
    private ZooKeeper zooKeeper;

    public WatchDog(String serviceName) {
        this.SERVICE_NAME = "/" + serviceName;
        try {
            //连接zk,获得列表信息
            //watcher机制：监控获取到的服务列表的变化
            zooKeeper = new ZooKeeper(Const.ZK_SERVER, 5000, (watchedEvent) -> {
                if (watchedEvent.getType() == Watcher.Event.EventType.NodeChildrenChanged
                        && watchedEvent.getPath().equals(BASE_SERVICE + SERVICE_NAME)) {
                    System.out.println("***注册到zk的服务信息发生变化***");
                    updateServerList();
                }
            });
            //第一次连接的时候要返回的列表
            updateServerList();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateServerList() {
        List<String> list = new ArrayList<>();
        try {
            List<String> children = zooKeeper.getChildren(BASE_SERVICE + SERVICE_NAME, true);
            for (String subNode : children) {
                byte[] data = zooKeeper.getData(BASE_SERVICE + SERVICE_NAME + "/" + subNode, false, null);
                String host = new String(data, "utf-8");
                list.add(host);
            }
            //将获取的服务端口和IP保存List中
            LoadBalance.SERVICE_LIST = list;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
