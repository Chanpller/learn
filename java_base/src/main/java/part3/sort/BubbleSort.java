package part3.sort;


import part3.array.sort.stabilization.DataWrap;

import java.util.Arrays;

public class BubbleSort {
    //冒泡排序
    public static void main(String[] args) {
        DataWrap[] data = {
                new DataWrap(21, "*"),
                new DataWrap(23, ""),
                new DataWrap(30, "*"),
                new DataWrap(21, ""),
                new DataWrap(-49, ""),
                new DataWrap(30, "") ,
                new DataWrap(40, "")};
        //[21*, 23, 30*, 21, -49, 30, 40]
        System.out.println(Arrays.toString(data));
        System.out.println("========排序前======");
        //相邻两个数比较，交换位置。会将最大的移到最后。最后一个元素不用循环，最后一个元素也不用比较
        for(int i=1;i<data.length-1;i++){
            boolean isChange = false;
            for(int j=0;j<data.length-1-i;j++){
                if(data[j].compareTo(data[j+1])>0){
                    DataWrap temp = data[j];
                    data[j] = data[j+1];
                    data[j+1] = temp;
                    isChange = true;
                }
            }
            if(!isChange)
                break;
        }
        System.out.println(Arrays.toString(data));
        System.out.println("========排序后======");
        //[-49, 21*, 21, 23, 30*, 30, 40]
    }
}
