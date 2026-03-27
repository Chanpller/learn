# 第7章-Reactor核心

## 7.1 前置知识

### 7.1.1 Lambda

* Lambda是Java8引入的特性

* Lambda表达式可以被视为匿名函数，允许在需要函数的地方以更简洁的方式定义功能。

* 格式

  ```
  (parameters)->expression
  (parameters)->{statments;}
  ```

Java8语法糖：

* 函数式接口；只要是函数式接口就可以用Lambda表达式简化
* 函数式接口： 接口中有且只有一个未实现的方法，这个接口就叫函数式接口。可以用default方法和静态方法。
* @FunctionalInterface //检查注解，帮我们快速检查我们写的接口是否函数式接口

```java
package com.chanpller.chapter7reactor.function;

@FunctionalInterface //检查注解，帮我们快速检查我们写的接口是否函数式接口
interface MyInterface {
     void test();
     default void test2(){

     }
     static void test3(){

     }
}
class MyInterfaceImpl implements MyInterface {
    @Override
    public void test() {
        System.out.println("test");
    }
}

public class TestFunction {
    public static void main(String[] args) {
        MyInterface myInterface = new MyInterfaceImpl();
        myInterface.test();

        MyInterface myInterface2 = () -> System.out.println("test2");
        myInterface2.test();

    }
}

```

### 7.1.2 Function

* 在Java中，函数式接口是只包含一个抽象方法的接口。他们是支持Lambda表达式的基础，因为Lambda表达式需要一个目标类型，这个目标类型必须是一个函数式接口。
* java.util.function包下提供了很多java自定义的函数接口
* 分类几类
  * 有入参，有返回值（比如Function，就是函数）
  * 有入参，没有返回值（比如Consumer，消费者）
  * 没有入参，有返回值（比如Supplier，生产者）
  * 没有入参，没有返回值（比如Runnable，线程方法）
* java.util.function包下的所有function定义：
  * Consumer： 消费者
  * Supplier： 提供者
  * Predicate： 断言
  * get/test/apply/accept调用的函数方法；

```java
package com.chanpller.chapter7reactor.function;

import java.util.UUID;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Lambda {
    public static void main(String[] args) {
//        Function<String,Integer> function = (s)-> Integer.parseInt(s);
        //一个入，一个出
        //String,Integer，前面String是入参，Inter是出餐一个参数，一个返回值，函数式接口
        Function<String,Integer> function = Integer::parseInt;
        System.out.println(function.apply("123"));

        //消费者，binary 双份，两个参数的，String,String都是入参
        //都是入，没有出
        BiConsumer<String,String> biConsumer = (s1,s2)-> System.out.println(s1+s2);

        biConsumer.accept("123","456");

        //没有入，只有出
        //生产者
        Supplier<String> stringSupplier = ()-> UUID.randomUUID().toString();
        System.out.println(stringSupplier.get());

        //没入，没出
        Runnable runnable = ()-> System.out.println("runnable");
        runnable.run();

        //Predicate断言
        Predicate<Integer> predicate = (i)-> i%2==0;
        System.out.println(predicate.test(2));
        System.out.println(predicate.negate().test(2));
    }
}

```

![Screenshot_2026-03-27-07-55-01-577_tv.danmaku.bili](../image/Screenshot_2026-03-27-07-55-01-577_tv.danmaku.bili.jpg)

### 7.1.3 StreamAPI

![Screenshot_2026-03-27-08-21-48-253_tv.danmaku.bili](../image/Screenshot_2026-03-27-08-21-48-253_tv.danmaku.bili.jpg)

* 概念
  * Stream Pipeline：流管道、流水线
  * Intermediate Operation：中间操作
  * Terminal Operation：终止操作
* 最佳实战：以后凡是你写for循环处理数据的统一全部用StreamAPI进行替换；
* Stream所有数据和操作被组合成流管道

* Stream所有数据和操作被组合成流管道流管道组成：

  一个数据源（可以是一个数组、集合、生成器函数、I/O管道）---->零或多个中间操作（将一个流变形成另一个流）---->一个终止操作（产生最终结果）

* Stream分三步，像流水一样：准备数据源==》0个或多个处理（中间操作，不会真正执行）==>1个终止操作(产生结果)

* 流是惰性的，只有终止操作时才会对数据进行计算，而且只有在需要时才会消耗源元素。这个很好理解，中间过程都是在定义流程和操作，就像定义一个流水线一样，会返回(创建)Stream类，当执行Terminal Operation最后的操作，才是执行方法。

中间操作：Intermediate Operations包括

* filter：过滤； 挑出我们用的元素
* map：映射： 一一映射，a 变成 b
  * mapToInt、mapToLong、mapToDouble
* flatMap：打散、散列、展开、扩维：一对多映射

```
filter、map、mapToInt、mapToLong、mapToDouble
flatMap、flatMapToInt、flatMapToLong、flatMapToDouble
mapMulti、mapMultiToInt、mapMultiToLong、mapMultiToDouble、
parallel、unordered、onClose、sequential
distinct、sorted、peek、limit、skip、takeWhile、dropWhile、
```

终止操作：Terminal Operation

```
forEach、forEachOrdered、toArray、reduce、collect、toList、min、
max、count、anyMatch、allMatch、noneMatch、findFirst、findAny、iterator
```

可以通过源代码看到方法是不是结束操作

![image-20260327155850147](../image/12.jpg)

```java
public static void main(String[] args) {
        List<Integer> list = List.of(1,2,3,4,5,6,7,8,9,10);
        list.stream().forEach(System.out::println);
        list.stream().filter(i -> i % 2 == 0).forEach(System.out::println);

        Stream<Integer> integerStream = list.stream().filter(i -> {
                    System.out.println("中间过程执行"+i);
                    return i % 2 == 0;
                }
        );
        integerStream.count();
    }
```



### 7.1.4 Reactive-Stream