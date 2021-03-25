package com.chanpller.learn.javacore.chaper14_thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ReetrantLockTest {
    public  int count = 0;
    public static void main(String[] args) {
        ReetrantLockTest reetrantLockTest = new ReetrantLockTest();
        Thread threadOne = new Thread(()->{
            while(true) {
                reetrantLockTest.add();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        Thread threadTwo = new Thread(()->{
            while(true) {
                reetrantLockTest.add();
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadOne.start();
        threadTwo.start();
    }
    private ReentrantLock lock = new ReentrantLock();
    public void add(){
        lock.lock();//锁定代码
        this.intrinisicLock.lock();
        try {
            count++;
            System.out.println("getAddHoldCount="+lock.getHoldCount());;
            System.out.println(Thread.currentThread() + ",count=" + count);
            delete();
        }finally {
            lock.unlock();//解锁
        }
    }
    public  void delete(){
        lock.lock();//锁重入
        try {
            System.out.println("deleteGetHoldCount=" + lock.getHoldCount());
        }finally {
            lock.unlock();
        }
    }
}
