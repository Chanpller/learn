package chapter2;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
public class CompletableFutureCombineDemo
{
    public static void main(String[] args)
    {
//        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "\t ---启动");
//            //暂停几秒钟线程
//            try {
//                TimeUnit.SECONDS.sleep(1);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 10;
//        });
//        CompletableFuture<Integer> completableFuture2 = CompletableFuture.supplyAsync(() -> {
//            System.out.println(Thread.currentThread().getName() + "\t ---启动");
//            //暂停几秒钟线程
//            try {
//                TimeUnit.SECONDS.sleep(2);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            return 20;
//        });
//        CompletableFuture<Integer> result = completableFuture1.thenCombine(completableFuture2, (x, y) -> {
//            System.out.println("-----开始两个结果合并");
//            System.out.println("x="+x);
//            System.out.println("y="+y);
//            return x + y;
//        });
//        System.out.println(result.join());

        biaodashiban();
    }

    public static void biaodashiban()
    {
        CompletableFuture<Integer> completableFuture1 = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 10;
        }).thenCombine( CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ---启动");
            //暂停几秒钟线程
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 20;
        }), (x, y) -> {
            System.out.println("-----开始两个结果合并");
            System.out.println("x="+x);
            System.out.println("y="+y);
            return x + y;
        });

        System.out.println(completableFuture1.join());
    }
}
