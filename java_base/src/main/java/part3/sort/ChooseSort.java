package part3.sort;

import part3.array.sort.stabilization.DataWrap;

import java.util.Arrays;

public class ChooseSort {
    //选择排序
    public static void main(String[] args) {
        DataWrap[] data = {
                new DataWrap(21, "*"),
                new DataWrap(23, ""),
                new DataWrap(30, "*"),
                new DataWrap(21, ""),
                new DataWrap(-49, ""),
                new DataWrap(30, "") };
        System.out.println(Arrays.toString(data));
        System.out.println("========排序前======");
        //每次取一个，与后面的所有数比较，进行交换位置。arr[arr.length-1]的时候只有1个数了，不需要进第二个循环体，所以直接到倒数第二个。
        for(int i=0;i<data.length-1;i++){
            int minIndex = i;
            for(int j=i+1;j<data.length;j++){
                if(data[minIndex].compareTo(data[j])>0){
                    minIndex = j;
                }
            }
            if(minIndex != i){
                DataWrap temp = data[i];
                data[i] = data[minIndex];
                data[minIndex] = temp;
            }
            System.out.println(Arrays.toString(data));
        }
        System.out.println("========排序后======");
        System.out.println(Arrays.toString(data));
    }
}
