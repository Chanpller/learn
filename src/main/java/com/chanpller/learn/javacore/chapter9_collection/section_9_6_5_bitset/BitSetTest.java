package com.chanpller.learn.javacore.chapter9_collection.section_9_6_5_bitset;

import java.util.BitSet;

public class BitSetTest {
    public static void main(String[] args) {
        BitSet bitset = new BitSet();
        bitset.set(10);
        bitset.set(8);
        bitset.set(100);
        bitset.set(3);
        boolean isbitset = bitset.get(8);

        //bitset.length(),最大位+1，也就是100+1
        System.out.println( bitset.length());
        System.out.println(getPrimeNumberCount(26));
    }

    /**
     *获取素数的总数
     * @param maxNumber 素数范围
     * @return 返回素数总数
     * @author chanpller
     */
    public static long getPrimeNumberCount(int maxNumber){
        BitSet bitset = new BitSet(maxNumber+1);
        int i;
        for(i=2;i<=maxNumber;i++)
            bitset.set(i);
        i=2;
        int count = 0;
        //分析算法
        //当i=2 去掉2的倍数的值。
        //当i=3 去掉3的倍数的值
        //当i=4 不会进来，在i=2时过滤掉了
        //当i=n 去掉N的倍数的值。
        //
        while(i*i<=maxNumber){//i*i表示过滤前面i的前面素数
            if(bitset.get(i)){
                count++;
                int k = 2*i;
                while(k<=maxNumber){
                    bitset.clear(k);
                    System.out.println("k="+k);
                    k +=i;
                }
            }
            i++;
        }
// i只到前面单倍的素数，后面是所有的素数
        while(i<=maxNumber){
            if(bitset.get(i))
                count++;
            i++;
        }
        return count;

    }
}
