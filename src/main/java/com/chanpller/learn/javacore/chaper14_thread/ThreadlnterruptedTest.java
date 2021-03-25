package com.chanpller.learn.javacore.chaper14_thread;

public class ThreadlnterruptedTest {
    public static void main(String[] args) {
        while(true){
            try {
                Thread.currentThread().sleep(10000);
                Thread.currentThread().interrupt();
                System.out.println("currentThread is sleep");
            } catch (InterruptedException e) {
                e.printStackTrace();
                Thread.currentThread().interrupt();
            }

        }
    }
}
