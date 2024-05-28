# 第2章-CompletableFuture(Completable可完成的)

## 2.1 Future接口理论知识复习

* Future接口(FutureTask实现类)定义了操作***异步任务执行一些方法***，比如获取异步任务的执行结果、取消任务的执行、判断任务是否被取消、判断任务执行是否完毕等。
* 比如主线程让一个子线程去执行热为奴，子线程可能比较耗时，启动子线程开始执行任务后，主线程就去做其他事情了，忙其他事情或者先执行完，过了一会采取获取子任务的执行结果或变更的任务状态。
* 定义了5个方法

![image-20240528213218899](D:\ideaprojects\learn\juc\image\image-20240528213218899.png)

* 一句话Future接口可以为主线程开一个分支任务，专门为主线程处理耗时和费力的复杂业务。

## 2.2 Future接口常用实现类FutureTask异步任务

### 2.2.1 Future接口能干什么

* Future是Java5新加的一个接口，它提供了一种异步并行计算的功能。

* 如果主线程需要执行一个很耗时的计算任务，我们就可以通过Future把这个任务放到异步线程中执行。

* 主线程继续处理其他任务或者先行结束，再通过Future获取计算结果。

* 异步线程任务执行有返回有结果，特点：多线程/有返回/异步任务

* 实现异步线程的接口

  * Runnable接口
    * 需要重写run方法
  * Callable接口
    * 与实现Runnable不同，需要重写call()方法，有返回值，有异常
  * Future接口和FutureTask实现类

  ```java
  class MyThread2 implements Runnable
  {
  
      @Override
      public void run() {
          
      }
  class MyThread implements Callable<String>
  {
      @Override
      public String call() throws Exception
      {
          System.out.println("-----come in call() " );
          return "hello Callable";
      }
  }
  
  ```

### 2.2.2 本源的Future接口相关框架

* 需要同时满足：多线程/有返回/异步任务三个特点，
* 

![image-20240528214754313](D:\ideaprojects\learn\juc\image\image-20240528214754313.png)

![image-20240528215103326](D:\ideaprojects\learn\juc\image\image-20240528215103326.png)



### 2.2.3 Future编码实战和优缺点分析

### 2.2.4 想完成一些复杂的任务

## 2.3 CompletableFuture对Future的改进

## 2.4 案例分析

## 2.5 CompletableFuture常用方法