# RPC Demo
## Required
- web.xml
- application.properties

## v1:RPC基本实现 base Java Socked
1. 通过Socket进行通讯
1. 序列化采用Java Serialize + ObjectStream
1. 通过JDK动态代理调用服务端方法

## v2dubbo:RPC基本实现 base Netty+ZK(Provider/Consumer)
1. 生产者服务端启动Netty服务端，并向ZK注册Interface服务。
2. 消费者客户端通过JDK动态代理启动Netty客户端，通过注册中心ZK获得生产者服务端地址(IP+Port)。同时将接口调用信息（接口、方法、参数等）先序列化再发送给生产者服务端。
3. 生产者服务端接收消息并通过反射调用相应方法，然后返回调用结果给消费者。
4. 消费者接收生产者传来的调用结果。
注：序列化使用Netty API

## v3:RPC高级实现
- 自定义注解使用
- 支持多个接口
- 服务注册与发现

## Zookeeper(Docker image)
~~~
#docker run --name ml-zookeeper --restart always -d zookeeper
# 启动容器并添加映射
docker run --privileged=true -d --name zookeeper --publish 2181:2181 -d zookeeper
# 查看容器是否启动
docker ps
# Start ZK Client
java -jar zookeeper-dev-ZooInspector.jar //执行成功后，会弹出java ui client
~~~