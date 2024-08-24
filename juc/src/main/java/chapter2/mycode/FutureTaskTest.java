package chapter2.mycode;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class FutureTaskTest {
    public static void main(String[] args)  {
        Callable<Integer> callable = new Callable<Integer>() {
            @Override
            public Integer call() throws Exception {
                TimeUnit.SECONDS.sleep(4);
//                for(;;){
//                    System.out.println(System.currentTimeMillis());
//                    if(4==5){
//                        break;
//                    }
//                }
                System.out.println("我线程还是执行完了");
                return 1;
            }
        };
        FutureTask<Integer> futureTask = new FutureTask(callable);
        Thread thread = new Thread(futureTask);
        thread.start();
//        try {
//            //线程阻塞，需要等待执行完成后才返回
//            Integer result = futureTask.get();
//            System.out.println(result);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        } catch (ExecutionException e) {
//            e.printStackTrace();
//        }

        while (true){
            //循环使用isDone判断是否结束
            if(futureTask.isDone()){
                try {
                    System.out.println("执行结束"+futureTask.get());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (ExecutionException e) {
                    e.printStackTrace();
                }
                break;
            }else{
                System.out.println("还未完成");
                try {
                    TimeUnit.SECONDS.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }

//        while (true){
//            //循环使用isDone判断是否结束
//            if(futureTask.isDone()){
//                System.out.println("执行结束,是否已经取消"+futureTask.isCancelled());
//                //如果任务取消了，使用get方法则抛异常,CancellationException,任务完成了则不会抛异常
//                try {
//                    System.out.println("执行结束"+futureTask.get());
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//                break;
//            }else{
//                try {
//                    TimeUnit.SECONDS.sleep(1);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                //取消任务,不会报错
//                futureTask.cancel(true);
//                System.out.println("还未完成"+futureTask.isCancelled());
//
//            }
//        }
//
//        try {
//            TimeUnit.SECONDS.sleep(4);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println("程序结束");
    }
}
