package com.merlin.patterns.aop.spring;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 定义一个切面的名称和属性
 * NOTE: 定义了一个方法级别的注解，在运行时有效。
 *
 * @Target 说明了Annotation所修饰的对象范围：
 *   Annotation可被用于 packages、types（类、接口、枚举、Annotation类型）、类型成员（方法、构造方法、成员变量、枚举值）、
 *   方法参数和本地变量（如循环变量、catch参数）。在Annotation类型的声明中使用了target可更加明晰其修饰的目标。
 * @Retention 定义了该Annotation被保留的时间长短：
 *   某些Annotation仅出现在源代码中，而被编译器丢弃；而另一些却被编译在class文件中；编译在class文件中的Annotation可能会被虚拟机忽略，
 *   而另一些在class被装载时将被读取（这里并不影响class的执行，因为Annotataion与class使用上是被分离的）。
 *   使用这个meta-Annotation可以对Annotation的“生命周期”进行限制。例如我们常见的@Override，它的@Retention就在source（源代码）阶段，
 *   因为这只是针对方法覆盖进行语法检查，在runtime运行阶段）就不需要了。
 * @Document 描述其他类型的annotation应该被作为被标注的程序成员的公告API，因此可以被例如javadoc这类的工具文档化。
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
//@Document
public @interface LogPrint{
    //可附加属性
    public String desc(); //注意属性后面有括号
}