# 设计模式
## 行为模式
### Mediator 调节者
https://en.wikipedia.org/wiki/Mediator_pattern#Java

## AOP(切面编程)
- JDK 动态代理 AOP
- CGLIB 动态代理 AOP
- Spring AOP

Java原生动态代理类和Cglib的区别
1. JDK动态代理是实现了被代理对象的接口，Cglib是继承了被代理对象。
2. JDK和Cglib都是在运行期生成字节码，JDK是直接写Class字节码，Cglib使用ASM框架写Class字节码，Cglib代理实现更复杂，生成代理类比JDK效率低。
3. JDK调用代理方法，是通过反射机制调用，Cglib是通过FastClass机制直接调用方法，Cglib执行效率更高。Cglib底层将方法全部存入一个数组中，通过数组索引直接进行方法调用
4. 代理类将被代理类作为自己的父类并为其中的非final委托方法创建两个方法，一个是与委托方法签名相同的方法（重写机制），它在方法中会通过super调用委托方法；另一个是代理类独有的方法。在代理方法中，它会判断是否存在实现了MethodInterceptor接口的对象，若存在则将调用intercept方法对委托方法进行代理
JDK 动态代理，JDK 动态代理的类必须实现一个接口，而且生成的代理类是其接口的实现类，也就是被代理的类的兄弟类，由JDK内部实现，
cglib代理的类，无需强制实现接口，其生成的代理类 是 被代理类的子类，并且重写的被代理类的方法，而且需要额外的引入Jar