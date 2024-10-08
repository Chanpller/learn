# 第7章-CAS

## 7.1 原子类

Java.util.concurrent.atomic

![image.png](../image/1681279535601-bdff9fb4-717b-4ae5-9ff0-bc2264473a2a.png)

## 7.2 没有CAS之前

多线程环境中不使用原子类保证线程安全i++（基本数据类型）

```java
class Test {
        private volatile int count = 0;
        //若要线程安全执行执行count++，需要加锁
        public synchronized void increment() {
                  count++;
        }

        public int getCount() {
                  return count;
        }
}
```

## 7.3 使用CAS之后

多线程环境中使用原子类保证线程安全i++（基本数据类型）---------->类似于乐观锁

```java
class Test2 {
        private AtomicInteger count = new AtomicInteger();

        public void increment() {
                  count.incrementAndGet();
        }
      //使用AtomicInteger之后，不需要加锁，也可以实现线程安全。
       public int getCount() {
                return count.get();
        }
}
```

## 7.4 CAS是什么？

### 7.4.1 说明

CAS(compare and swap)，中文翻译为比较并交换，实现并发算法时常用到的一种技术，用于保证共享变量的原子性更新，它包含三个操作数---内存位置、预期原值与更新值。

执行CAS操作的时候，将内存位置的值与预期原值进行比较：

- 如果相匹配，那么处理器会自动将该位置更新为新值
- 如果不匹配，处理器不做任何操作，多个线程同时执行CAS操作只有一个会成功。

![image.png](../image/1681279948065-f7d04f1d-7cd7-43b3-8786-693a1d016f84.png)

### 7.4.2 CASDemo代码

```java
public class CasDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger  = new AtomicInteger(5);
        boolean setOk = atomicInteger.compareAndSet(5, 200);
        System.out.println("更新："+setOk+",新值："+atomicInteger.get());
        setOk = atomicInteger.compareAndSet(5, 300);
        System.out.println("更新："+setOk+",新值："+atomicInteger.get());
    }
}
更新：true,新值：200
更新：false,新值：200
```

### 7.4.3硬件级别保证

* CAS是JDK提供的非阻塞原子性操作，它通过硬件保证了比较-更新的原子性。
* 它是非阻塞的且自身具有原子性，也就是说这玩意效率更高且通过硬件保证，说明这玩意更可靠。
* CAS是一条CPU的原子指令（cmpxchg指令），不会造成所谓的数据不一致问题，Unsafe提供的CAS方法（如compareAndSwapXXX）底层实现即为CPU指令cmpxchg。
* 执行cmpxchg指令的时候，会判断当前系统是否为多核系统，如果是就给总线加锁，只有一个线程会对总线加锁成功，加锁成功之后会执行CAS操作，也就是说CAS的原子性实际上是CPU实现独占的，比起用synchronized重量级锁，这里的排他时间要短很多，所以再多线程情况下性能会比较好。

### 7.4.4 源码分析CompareAndSet(int expect,int update)

java.util.concurrent.atomic.AtomicInteger的compareAndSet代码追踪

```java
public final boolean compareAndSet(int expect, int update) {
    return unsafe.compareAndSwapInt(this, valueOffset, expect, update);
}
```

sun.misc.Unsafe最后调用的Unsafe的本地方法

```java
public final native boolean compareAndSwapInt(Object var1, long var2, int var4, int var5);
```

compareAndSet 源码

![image.png](../image/1681280524693-7e39b744-3963-48ae-bed3-d9b2c2cce3f6.png)

## 7.5 CAS底层原理？谈谈对Unsafe类的理解？

### 7.5.1 Unsafe

* Unsafe类是CAS的核心类，由于Java方法无法直接访问底层系统，需要通过本地（native）方法来访问，Unsafe相当于一个后门，基于该类可以直接操作特定内存的数据。Unsafe类存在于sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，因此Java中CAS操作的执行依赖于Unsafe类的方法。

* 注意：Unsafe类中的所有方法都是native修饰的，也就是说Unsafe类中的所有方法都直接调用操作系统底层资源执行相应任务。

* 变量valueOffset，表示该变量值在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的。

  ```java
  //AtomicInteger内中
  public final int getAndIncrement() {
      return unsafe.getAndAddInt(this, valueOffset, 1);
  }
  ```

* 变量value使用volatile修饰，保证多线程之间的内存可见性。

  ```java
   //AtomicInteger内中
   private volatile int value;
  ```

### 7.5.2 atomicInteger.getAndIncrement()如何保证原子性？

* 我们知道i++是线程不安全的，那么atomicInteger.getAndIncrement()如何保证原子性？
* CAS的全程为Compare-And-Swap，它是一条CPU并发原语。
* 它的功能是判断内存某个位置的值是否为预期，如果是则更改为新的值，这个过程是原子的。

* AtomicInteger类主要利用CAS+volatile和native方法来保证原子操作，从而避免synchronized的高开销，执行效率大为提升：

![image.png](../image/1681281445135-332ae9b5-ae46-4021-a7ea-237b1f37b927.png)

* CAS并发原语体现在Java语言中就是sun.misc.Unsafe类中的各个方法。调用Unsafe类中的CAS方法，JVM会帮我们实现出CAS汇编指令。这是一种完全依赖于硬件的功能，通过它实现了原子操作。再次强调，由于CAS是一种系统原语，原语属于操作系统用语范畴，是由若干条指令组成的，用于完成某个功能的一个过程，并且原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是一条CPU的原子指令，不会造成所谓的数据不一致问题。

### 7.5.2 源码分析

* new AtomicInteger().getAndIncrement()

![image-20240913231650805](../image/image-20240913231650805.png)

![image-20240913231941790](../image/image-20240913231941790.png)

### 7.5.3 底层汇编

![image-20240913232038910](../image/image-20240913232038910.png)

![image-20240913232201853](../image/image-20240913232201853.png)

![image-20240913232225709](../image/image-20240913232225709.png)

JDK提供的CAS机制，在汇编层级会禁止变量两侧的指令优化，然后使用compxchg指令比较并更新变量值（原子性）

总结：

- CAS是靠硬件实现的从而在硬件层面提升效率，最底层还是交给硬件来保证原子性和可见性
- 实现方式是基于硬件平台的汇编指令，在intel的CPU中(x86机器上)，使用的是汇编指令compxchg指令
- 核心思想就是比较要更新变量V的值和预期值E（compare），相等才会将V的值设为新值N（swap），如果不相等自旋再来

## 7.6 原子引用

### 7.6.1 AtomicInteger原子整型，可否有其他原子类型？

* 比如AtomicBook或AtomicOrder，可以通过AtomicReference引用类型实现

![image.png](../image/1681282437108-0d23bc7e-e378-4a02-b0fd-d2be94961cff.png)

![image-20240913232813427](../image/image-20240913232813427.png)

### 7.6.2 AtomicReferenceDemo

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
class User {
    String userName;
    int age;
}

public class AtomicReferenceDemo {
    public static void main(String[] args) {
        AtomicReference<User> atomicReference = new AtomicReference<>();
        User z3 = new User("z3", 22);
        User li4 = new User("li4", 25);

        atomicReference.set(z3);
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());//true	User(userName=li4, age=25)
        System.out.println(atomicReference.compareAndSet(z3, li4) + "\t" + atomicReference.get().toString());//false	User(userName=li4, age=25)

    }
}
```

## 7.7 CAS与自旋锁，借鉴CAS思想

### 7.7.1 是什么？

自旋锁（sinlock），CAS是实现自旋锁的基础，CAS利用CPU指令保证了操作的原子性，以达到锁的效果，至于自旋锁---字面意思自己旋转。是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，当线程发现锁被占用时，会不断循环判断锁的状态，直到获取。这样的好处是减少线程上下文切换的消耗，缺点是循环会消耗CPU。

![image-20240913233402619](../image/image-20240913233402619.png)

### 7.7.2 自己实现一个自旋锁spinLockDemo

题目：实现一个自旋锁，借鉴CAS思想

通过CAS完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现当前有线程持有锁，所以只能通过自旋等待，直到A释放锁后B随后抢到。

```java
package chapter6;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;

public class SpinLockDemo {
    private static AtomicReference<Thread> threadAtomicReference = new AtomicReference<>();
    public void  lock(){
        Thread thread = Thread.currentThread();
        System.out.println(Thread.currentThread().getName() + "\t --------come in");
        while (!threadAtomicReference.compareAndSet(null,thread)){

        }
    }
    public void  unlock(){
        Thread thread = Thread.currentThread();
        threadAtomicReference.compareAndSet(thread,null);
        System.out.println(Thread.currentThread().getName() + "\t --------task over,unLock.........");
    }

    public static void main(String[] args) {
        SpinLockDemo spinLockDemo = new SpinLockDemo();
//        ReentrantLock spinLockDemo = new ReentrantLock();
        new Thread(() -> {
            //如果换ReentrantLock，报错
//            spinLockDemo.unlock();
            spinLockDemo.lock();
            try {
                TimeUnit.SECONDS.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            spinLockDemo.unlock();
        }, "A").start();


        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        new Thread(() -> {
            spinLockDemo.lock();
            spinLockDemo.unlock();
        }, "B").start();
    }
}
/**
 * A	 --------come in
 * B	 --------come in
 * A	 --------task over,unLock.........
 * B	 --------task over,unLock.........
 */
```

## 7.8 CAS缺点

### 7.8.1 循环时间长开销很大

![image.png](../image/1681283435669-2927eebd-5d97-431f-9e7f-a5b970d54f76.png)

- getAndAddInt方法有一个do while
- 如果CAS失败，会一直进行尝试，如果CAS长时间一直不成功，可能会给CPU带来很大开销

### 7.8.2 引出来ABA问题？

- ABA问题怎么产生的？

- - CAS算法实现一个重要前提需要提取出内存中某时刻的数据并在当下时刻比较并替换，那么在这个时间差类会导致数据的变化。
  - 比如说一个线程1从内存位置V中取出A，这时候另一个线程2也从内存中取出A，并且线程2进行了一些操作将值变成了B，然后线程2又将V位置的数据变成A，这时候线程1进行CAS操作发现内存中仍然是A，预期ok，然后线程1操作成功--------尽管线程1的CAS操作成功，但是不代表这个过程就是没有问题的。（即数据没变，但是中途可能有修改，无法避免中途修改的情况）

- 版本号时间戳原子引用，解决中途修改了的情况

![image.png](../image/1681284076874-d8cab49f-baef-49cb-8b6a-5aaa051aa4d2.png)

```java
@Data
@AllArgsConstructor
@NoArgsConstructor
class Book {
    private int id;
    private String bookName;
}

public class AtomicStampedReferenceDemo {
    public static void main(String[] args) {
        Book javaBook = new Book(1, "javaBook");
        AtomicStampedReference<Book> atomicStampedReference = new AtomicStampedReference<>(javaBook, 1);
        System.out.println(atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        Book mysqlBook = new Book(2, "mysqlBook");
        boolean b;
        b = atomicStampedReference.compareAndSet(javaBook, mysqlBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());

        b = atomicStampedReference.compareAndSet(mysqlBook, javaBook, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
        System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());
    }
}
/**
 * Book(id=1, bookName=javaBook)	1
 * true	Book(id=2, bookName=mysqlBook)	2
 * true	Book(id=1, bookName=javaBook)	3
 */
```

多线程情况下演示AtomicStampedReference解决ABA问题

```
public class ABADemo {
    static AtomicInteger atomicInteger = new AtomicInteger(100);
    static AtomicStampedReference<Integer> atomicStampedReference = new AtomicStampedReference<>(100, 1);

    public static void main(String[] args) {
//        abaHappen();//true	2023
        /**
         * t3	首次版本号: 1
         * t4	首次版本号: 1
         * t3	2次版本号: 2
         * t3	3次版本号: 3
         * false	100	3
         */
        abaNoHappen();

    }

    private static void abaNoHappen() {
        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t" + "首次版本号: " + stamp);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicStampedReference.compareAndSet(100, 101, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t" + "2次版本号: " + atomicStampedReference.getStamp());
            atomicStampedReference.compareAndSet(101, 100, atomicStampedReference.getStamp(), atomicStampedReference.getStamp() + 1);
            System.out.println(Thread.currentThread().getName() + "\t" + "3次版本号: " + atomicStampedReference.getStamp());
        }, "t3").start();


        new Thread(() -> {
            int stamp = atomicStampedReference.getStamp();
            System.out.println(Thread.currentThread().getName() + "\t" + "首次版本号: " + stamp);
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            boolean b = atomicStampedReference.compareAndSet(100, 200, stamp, stamp + 1);
            System.out.println(b + "\t" + atomicStampedReference.getReference() + "\t" + atomicStampedReference.getStamp());
        }, "t4").start();
    }

    private static void abaHappen() {
        new Thread(() -> {
            atomicInteger.compareAndSet(100, 101);
            try {
                TimeUnit.MILLISECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            atomicInteger.compareAndSet(101, 100);
        }, "t1").start();


        new Thread(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(atomicInteger.compareAndSet(100, 2023) + "\t" + atomicInteger.get());//true	2023
        }, "t2").start();
    }
}
```

- 一句话：比较加版本号一起上