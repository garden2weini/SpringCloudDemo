# Spring Cloud(Hoxton) Demo

## TODO List
1. Netflix Hystrix: 服务熔断与降级、线程隔离
1.  Netflix Zuul服务网关：（Web／Other Client -> 微服务系统）微服务网关（为每个服务提供统一的权限控制／用户控制／路由）
    1. 
spring cloud + dubbo协议／rest混合
1. zipkin、turbin、sonar
1. Spring Cloud Config: 集中管理应用的配置文件；动态刷新配置文件 
1. Alibaba开源的同类组件尝试 for Spring Cloud
1. Swagger：接口文档及实时更新

## Demo说明
- Eureka Server(http://eureka7001.com:7001/): 
    1. 默认用户名密码：http://localhost:7001/login. 弹出登录页面，用户名默认为user，密码会在IDEA控制台随机生成
    2. 自定义用户名密码：http://merlin:helloworld@localhost:7001/eureka/
- Provider: 提供Rest服务（可直接访问），注册到Eureka供集群访问 
    1. http://localhost:8001/dept/get/1
    1. http://localhost:8001/dept/list
- Consumer: 通过RestTemplate和Feign两种方式访问集群中的服务(Client Ribbon负载均衡）
    1. http://localhost:8099/consumer/dept/get/3, RestTemplate(未配置服务降级)
    1. http://localhost:8099/consumer/dept/list, RestTemplate(未配置服务降级)
    1. http://localhost:8099/consumer/dept/getx/3, Feign+Hystrix
    1. http://localhost:8099/consumer/dept/listx, Feign+Hystrix
    1. 注：RestTemplate 内部使用 Ribbon做负载均衡, Feign内部也是使用的Ribbon做负载均衡

## 数据准备
~~~
CREATE TABLE `Dept` (
  `deptno` int(16) NOT NULL AUTO_INCREMENT,
  `deptname` varchar(32) DEFAULT NULL,
  `dbsource` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`deptno`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8

INSERT INTO Dept VALUES(1, 'dept1', 'dbsource1');
INSERT INTO Dept VALUES(2, 'dept2', 'dbsource2');
INSERT INTO Dept VALUES(3, 'dept3', 'dbsource3');
INSERT INTO Dept VALUES(4, 'dept4', 'dbsource4');
INSERT INTO Dept VALUES(5, 'dept5', 'dbsource5');
INSERT INTO Dept VALUES(6, 'dept6', 'dbsource6');
~~~

## Eureka
Eureka server和client之间每隔30秒会进行一次心跳通信，告诉server，client还活着。由此引出两个名词： 
Renews threshold：server期望在每分钟中收到的心跳次数 
Renews (last min)：上一分钟内收到的心跳次数。
client个数为n，阈值为1+2*n（此为一个server且禁止自注册的情况） 
如果是多个server，且开启了自注册，那么就和client一样，是对于其他的server来说就是client，是要*2的

## FAQ
### Spring Cloud下配置eureka.instance.instance-id使得服务实例在eureka界面增加显示版本号
SpringCloud体系里的，服务实体向eureka注册时，注册名默认是“IP名:应用名:应用端口名”，即${spring.cloud.client.ipAddress}:${spring.application.name}:${spring.application.instance_id:${server.port}}。
Eureka界面注册服务名加上版本号：
~~~
eureka.instance.instance-id=${spring.cloud.client.ip-address}:${spring.application.name}:${server.port}:@project.version@
注1: project.version是引用maven里面的属性，因为springboot的parent包将maven中默认的${*}修改成了@*@，所以引用maven属性要用@@，而其他的比如server.port本来就是springboot的属性，直接${}
注2：spring.cloud.client.ip-address在spring-cloud-commons包中
<dependency>
    <groupId>org.springframework.cloud</groupId>
    <artifactId>spring-cloud-commons</artifactId>
</dependency>
~~~

### Feign报错'xx.FeignClientSpecification', defined in null, could not be registered.
Ref: https://blog.csdn.net/u012211603/article/details/84312709
1. 在application.yml中配置：allow-bean-definition-overriding
~~~
   spring:
     main:
       allow-bean-definition-overriding: true
~~~
2. 在注解中添加 contextId 来区分解决该问题
~~~
@FeignClient(name="common-service", contextId = "example")
~~~

### Fetching config from server at : http://localhost:8888
从spring cloud config 配置中心读取数据。启动的时候是会先加载bootstrap.properties或bootstrap.yml配置文件，如果没有的话，则会远程从http://localhost:8888获取配置，然后才会加载到application.yaml文件。

### 客户端注册报错：Cannot execute request on any known server
在对eureka注册中心服务端添加安全验证后，新版本springcloud出现一个问题就是，在客户端注册到服务中心时报了一个错：Cannot execute request on any known server，重新仔细看了一下官方文档Securing The Eureka Server部分得以解决。
参考：[添加安全验证](https://www.cnblogs.com/zrk3/p/springcloud_securing_eurekaserver.html)

- EurekaServer配置

~~~
# 添加依赖
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>
# 修改配置文件:设置安全认证的用户名跟密码：
#验证的用户名和密码
spring.security.user.name=...
spring.security.user.password=...
#修改eureka访问url
eureka.client.service-url.defaultZone=http://${spring.security.user.name}:${spring.security.user.password}@${eureka.instance.hostname}:${server.port}/eureka/
~~~
- EurekaServer添加类 
~~~
@EnableWebSecurity
class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }
}
~~~
- EurekaClint配置
~~~
#修改eureka访问url
eureka.client.service-url.defaultZone=http://${spring.security.user.name}:${spring.security.user.password}@${eureka.instance.hostname}:${server.port}/eureka/
~~~

## Ref
- [Spring Cloud Samples](https://spring.io/projects/spring-cloud#samples)
- [Spring Cloud 分布式](https://docs.qq.com/doc/BRf85Y33IMg43FMZBB3GJqp93QkUjt1e0mHr2IQmKC2Cjyb92ATX7q2MRDz90SXiBi1Vci1K1)
- [SpringCloud：eureka注册中心设置访问密码](https://blog.csdn.net/Qizhi_Hu/article/details/105188111)
- [SpringCloud 项目搭建以及简单使用](https://blog.csdn.net/qq_40717036/article/details/88733853)

