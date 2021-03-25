package com.chanpller.learn.javacore.chaper14_thread;

import static java.lang.Thread.sleep;

public class ThreadStatTest {
    public static void main(String[] args) {
        Thread thread = new Thread(()->{
            try {
                Thread.currentThread().sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        System.out.println(thread.getState());
        thread.start();
        System.out.println(thread.getState());
        while (true) {
            System.out.println(thread.getState());
            if(Thread.State.TERMINATED==thread.getState()){
                break;
            }
        }

    }
}
