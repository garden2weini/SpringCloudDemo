package com.merlin.rpc.v2dubbo.zk;

import com.merlin.rpc.Const;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;


/**
 *
 */

public class ServiceRegister {

    public static void register(String serviceName, String address, int port) {
        String BASE_SERVICE = Const.DUBBO_ZK_ROOT;
        /**
         * 在zk创建根节点path,在根节点下创建临时子节点用于存放服务ip和端口
         */
        try {
            String path = BASE_SERVICE + serviceName;
            ZooKeeper zooKeeper = new ZooKeeper(Const.ZK_SERVER, 5000, (watchedEvent) -> {
            });
            System.out.println(zooKeeper);
            Thread.sleep(2000);
            Stat exists = zooKeeper.exists(path, false);
            //先判断服务根路径是否存在
            System.out.println("ZooKeeper's App Root: " + exists);
            if (exists == null) {
                zooKeeper.create(BASE_SERVICE, "root".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
                zooKeeper.create(path, "serviceName".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            //将服务的ip和端口作为临时带序号的子节点
            String serverPath = address + ":" + port;

            zooKeeper.create(path + "/child", serverPath.getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL_SEQUENTIAL);

            System.out.println(serviceName + "(Interface)服务注册成功");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
