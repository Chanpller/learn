package chapter2;

import java.util.concurrent.*;

public class CompletableFutureBuildDemo
{
    public static void main(String[] args) throws ExecutionException, InterruptedException
    {
//        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        //没有返回值，没有传入线程池，默认线程池时ForkJoinPool.commonPool，通过线程名字可以看出
        CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            //暂停几秒钟线程
            try { TimeUnit.SECONDS.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
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
