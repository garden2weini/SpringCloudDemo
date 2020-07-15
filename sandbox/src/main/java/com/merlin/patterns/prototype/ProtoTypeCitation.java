package com.merlin.patterns.prototype;

/**
 * 原型（Prototype）模式的定义如下：用一个已经创建的实例作为原型，通过复制该原型对象来创建一个和原型相同或相似的新对象。
 * 在这里，原型实例指定了要创建的对象的种类。用这种方式创建对象非常高效，根本无须知道对象创建的细节。s
 */
public class ProtoTypeCitation {
    public static void main(String[] args) throws CloneNotSupportedException
    {
        Citation obj1=new Citation("张三","同学：在2016学年第一学期中表现优秀，被评为三好学生。","韶关学院");
        obj1.display();
        Citation obj2=(Citation) obj1.clone();
        obj2.setName("李四");
        obj2.display();
    }
}

//奖状类
class Citation implements Cloneable
{
    String name;
    String info;
    String college;
    Citation(String name, String info, String college)
    {
        this.name=name;
        this.info=info;
        this.college=college;
        System.out.println("奖状创建成功！");
    }
    void setName(String name)
    {
        this.name=name;
    }
    String getName()
    {
        return(this.name);
    }
    void display()
    {
        System.out.println(name+info+college);
    }
    public Object clone() throws CloneNotSupportedException
    {
        System.out.println("奖状拷贝成功！");
        return (Citation)super.clone();
    }
}
