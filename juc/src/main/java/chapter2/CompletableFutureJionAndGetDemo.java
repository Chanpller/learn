package chapter2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class CompletableFutureJionAndGetDemo {
//    public static void main(String[] args) {
//        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
//            return "Hello Word";
//        });
//        System.out.println(completableFuture.join());
//    }
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(()->{
            return "Hello Word";
        });
        System.out.println(completableFuture.get());
    }
}
