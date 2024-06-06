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

![image-20240529080139786](D:\IdeaProjects\learn\juc\image\image-20240529080139786.png)

* 创建一个线程通过new Thread，但是Thread的构造器，只能传Runnable，如何传Callable具有返回值的呢。
* RunnableFuture<v>，同时继承了Runnable, Future<V>。
* FutureTask则是RunnableFuture<V>的实现。
* FutureTask通过构造器传入Callable，实现具有返回值的线程。

```java
 public FutureTask(Callable<V> callable) {
        if (callable == null)
            throw new NullPointerException();
        this.callable = callable;
        this.state = NEW;       // ensure visibility of callable
    }

    /**
     * Creates a {@code FutureTask} that will, upon running, execute the
     * given {@code Runnable}, and arrange that {@code get} will return the
     * given result on successful completion.
     *
     * @param runnable the runnable task
     * @param result the result to return on successful completion. If
     * you don't need a particular result, consider using
     * constructions of the form:
     * {@code Future<?> f = new FutureTask<Void>(runnable, null)}
     * @throws NullPointerException if the runnable is null
     */
    public FutureTask(Runnable runnable, V result) {
        this.callable = Executors.callable(runnable, result);
        this.state = NEW;       // ensure visibility of callable
    }
```

### 2.2.3 Future编码实战和优缺点分析

* 优点

  * future+线程池一部多线程任务配合，能显著提高程序的执行效率

    ```java
    public class FutureAPIDemo
    {
        public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException
        {
            FutureTask<String> futureTask = new FutureTask<String>( () -> {
                System.out.println(Thread.currentThread().getName()+"\t -----come in");
                try { TimeUnit.SECONDS.sleep(5); } catch (InterruptedException e) { e.printStackTrace(); }
                return "task over";
            });
            Thread t1 = new Thread(futureTask, "t1");
            t1.start();
    
            System.out.println(Thread.currentThread().getName()+"\t ----忙其它任务了");
    
           //get()阻塞 //System.out.println(futureTask.get());
            //System.out.println(futureTask.get(3,TimeUnit.SECONDS));
    
            while(true)
            {
                //isDone需要一直轮询
                if(futureTask.isDone())
                {
                    System.out.println(futureTask.get());
                    break;
                }else{
                    //暂停毫秒
                    try { TimeUnit.MILLISECONDS.sleep(500); } catch (InterruptedException e) { e.printStackTrace(); }
                    System.out.println("正在处理中，不要再催了，越催越慢 ，再催熄火");
                }
            }
        }
    }
    ```

* 缺点
  * get()阻塞：一旦调用get()方法求结果，如果计算没有完成容易导致程序阻塞。
  * isDone()轮询：轮询的方式会耗费无谓的CPU资源，而且不见得能及时地得到计算结果。如果想要一部获取结果，通常都会以轮询的方式去获取结果，尽量不要阻塞。
* 结论
  
  * Future对于结果的获取不是很友好，只能通过阻塞或轮询的方式得到任务的结果。

### 2.2.4 想完成一些复杂的任务

对于简单的业务场景使用Future完全OK，但如果要处理下面的复杂任务就力不从心了。

* 回调通知
  * Future完成后告诉我，这就是回调通知。
  * 通过轮询的方式获取结果CPU占用高，代码也不优雅
* 创建异步任务
  * Future+线程池配合
* 多个任务前后以来可以组合处理
  * 想要将多个一部任务的计算结果组合起来，后一个任务的计算结果依赖前一个异步任务的值的场景
  * 两个或多个一部计算合成一个一部计算，这几个一部计算相互独立，同时后面这个又以来前一个处理的结果场景。
* 对计算速度选最快
  * Future集合中某个任务最快结束时，返回结果，返回第一名处理结果。
* 其他
  * Future之前提供的API不足以支持其他复杂场景，处理起来不够优雅，这时就诞生了CompletableFuture
  * Future能做的，CompletableFuture也能做。

## 2.3 CompletableFuture对Future的改进

### 2.3.1 CompletableFuture为什么出现

* get()方法在Future计算完成之前会一直处于阻塞状态
* isDone()方法容易耗费CPU资源
* 希望异步任务通过传入回调函数，在Future结束时自动调用该回调函数，不用等待结果。
* 阻塞和异步编程的设计理念违背，轮询方式耗费无谓的CPU资源，因此JDK8设计出CompletableFuture
* CompletableFuture提供了一种观察者模式类似的机制，可以让任务执行完成后通知监听的一方。

### 2.3.2 CompletableFuture和CompletionStage源码分别介绍

* 类框架说明

![image-20240531121511098](D:\IdeaProjects\learn\juc\image\image-20240531121511098.png)

```java
public class CompletableFuture<T> implements Future<T>, CompletionStage<T> {}
```

* 接口CompletionStage
  * CompletionStage代表异步计算过程中的某一个阶段，一个阶段完成后可能会出发另一个阶段。
  * 一个阶段的计算执行可能是一个Function，Consumer或者Ruanable。比如：stage.thenApply(x->square(x)).thenAccept(X->System.out.print(x)).thenRun(()->System.out.printlin())
  * 一个阶段的执行可能是被单个阶段的完出发，也可能是由多个阶段一起触发。
  * 代表异步计算过程中的某一个阶段，一个阶段完成后可能会触发另外一个阶段，有些类似Linux系统的管道分隔符传参数。
  * 通过CompletionStage接口定义了更多的方法供CompletableFuture操作实现。

* 类CompletableFuture
  * 在Java8中，CompletableFuture提供了非常强大的Future的扩展功能，看可以帮助我们简化异步编程的复杂性，并且提供了函数式编程的能力，可以通过回调的方式处理计算结果，也提供了转换和组合CompletableFuture的方法。
  * 它可能代表一个明确完成的Future，也有可能代表一个完成阶段（CompletionStage），它支持在计算完成后触发一些函数或执行某些动作。
  * 它实现了Future和CompletionStage接口。

### 2.3.3 核心的四个静态方法，来创建一个异步任务

#### 2.3.3.1 四个静态方法

* runAsync无返回值
  * public static CompletableFuture<Void> runAsync(Runnable runnable)
  * public static CompletableFuture<Void> runAsync(Runnable runnable,Executor executor)
* supplyAsync有返回值
  * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier)
  * public static <U> CompletableFuture<U> supplyAsync(Supplier<U> supplier,Executor executor)

2.3.3.2 静态方法参数说明

* 上述Executor executor参数说明

  * 没有指定Executor的方法，直接使用默认的ForkJoinPool.commonPool()作为它的线程池执行异步代码
  * 如果指定线程池，则使用我们自定义的或者特别指定的线程池执行异步代码

* Code（代码）

  ```java
  public class CompletableFutureBuildDemo
  {
      public static void main(String[] args) throws ExecutionException, InterruptedException
      {
  //        ExecutorService threadPool = Executors.newFixedThreadPool(3);
  
          //没有返回值,没有传入线程池，默认线程池时ForkJoinPool.commonPool，通过线程名字可以看出
          CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
              System.out.println(Thread.currentThread().getName());
              //暂停几秒钟线程
              try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
          });
  
  //        传入自定义线程池
  //        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
  //            System.out.println(Thread.currentThread().getName());
  //            //暂停几秒钟线程
  //            try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
  //        },threadPool);
  
          System.out.println(completableFuture.get());
  //
          //有返回值
          CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
              System.out.println(Thread.currentThread().getName());
              //暂停几秒钟线程
              try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
              return "hello supplyAsync";
          });
  
          //传入自定义线程池
  /*        CompletableFuture<String> completableFuture2 = CompletableFuture.supplyAsync(() -> {
              System.out.println(Thread.currentThread().getName());
              //暂停几秒钟线程
              try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
              return "hello supplyAsync";
          },threadPool);*/
  
          System.out.println(completableFuture2.get());
  
  //        threadPool.shutdown();
      }
  }
  ```

* Code(代码)之通用演示，减少阻塞和轮询

  * 从Java8开始引入了CompletableFuture，它时Future的功能增强版，减少阻塞和轮询。可以传入回调对象，当异步任务完成或者发生异常时，自动调用回调对象的回调方法。
  * 为什么默认线程池关闭，自定义线程池需要自己关闭。
    * CompletableFuture默认的线程池是守护线程，一但主程序退出，它就结束
    * 自定义线程是用户线程，不会主动结束，需要我们手动结束。

  ```java
   public static void main(String[] args) throws ExecutionException, InterruptedException
      {
  
          ExecutorService threadPool = Executors.newFixedThreadPool(3);
  
          try
          {
              CompletableFuture.supplyAsync(() -> {
                  System.out.println(Thread.currentThread().getName() + "----come in");
                  int result = ThreadLocalRandom.current().nextInt(10);
                  try { TimeUnit.SECONDS.sleep(1); } catch (InterruptedException e) { e.printStackTrace(); }
                  System.out.println("-----1秒钟后出结果：" + result);
                  if(result > 2)
                  {
                      int i=10/0;
                  }
                  return result;
              }).whenComplete((v,e) -> {
                  if (e == null) {
                      System.out.println("-----计算完成，更新系统UpdateValue："+v);
                  }
              }).exceptionally(e -> {
                  e.printStackTrace();
                  System.out.println("异常情况："+e.getCause()+"\t"+e.getMessage());
                  return null;
              });
  
              System.out.println(Thread.currentThread().getName()+"线程先去忙其它任务");
          }catch (Exception e){
              e.printStackTrace();
          }finally {
  //            threadPool.shutdown();
          }
  
  
          //主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭:暂停3秒钟线程
          //try { TimeUnit.SECONDS.sleep(3); } catch (InterruptedException e) { e.printStackTrace(); }
  
      }
  ```

* CompletableFuture特点

  * 异步任务结束时，会自动回调某个对象的方法
  * 主线程设置好回调后，不再关心异步任务的执行，异步任务之间可以顺序执行
  * 异步任务出错时，会自动回调某个对象的方法

## 2.4 案例分析

## 2.5 CompletableFuture常用方法