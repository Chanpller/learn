package part3.sort;

import part3.array.sort.stabilization.DataWrap;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        DataWrap[] data = {
                new DataWrap(21, "*"),
                new DataWrap(23, ""),
                new DataWrap(30, "*"),
                new DataWrap(21, ""),
                new DataWrap(-49, ""),
                new DataWrap(30, "") ,
                new DataWrap(45, "*"),
                new DataWrap(20, ""),
                new DataWrap(-36, ""),
                new DataWrap(90, "") ,
                new DataWrap(40, "")};
        System.out.println("========排序前======");
        System.out.println(Arrays.toString(data));
        for(int index=0;index<data.length-1;index++){
            //最有一个元素索引
            int lastIndex = data.length-1-index;

            //查找每一个父节点
            for(int prarentNode = 0;prarentNode<=(lastIndex-1)/2;prarentNode++){
                //查找每个子节点的最大值
                int currentNodeIndex = prarentNode;

                //查看当前节点有没有子节点
                while((currentNodeIndex*2+1)<=lastIndex){
                    //先将左节点设置为节点最大值索引
                    int nodeMaxValueIndex = currentNodeIndex*2+1;

                    //判断有没有右节点，有右节点判断大小，取出最大节点索引
                    if (nodeMaxValueIndex < lastIndex) {
                        // 比较两个值
                        if (data[nodeMaxValueIndex].compareTo(data[nodeMaxValueIndex + 1]) < 0) {
                            //记录最大值
                            nodeMaxValueIndex++;
                        }
                    }

                    // 如果currentNodeIndex节点的值小于节点的值，交换数据。再查询子节点的最大值。
                    if (data[currentNodeIndex].compareTo(data[nodeMaxValueIndex]) < 0) {
                        DataWrap temp = data[currentNodeIndex];
                        data[currentNodeIndex] = data[nodeMaxValueIndex];
                        data[nodeMaxValueIndex] = temp;
                        // 将maxValueIndex赋给currentNodeIndex，开始while循环的下一次循环。把最大值提到最顶端。
                        // 重新保证currentNodeIndex节点的值大于其左、右节点的值
                        currentNodeIndex = nodeMaxValueIndex;
                    } else {
                        break;
                    }
                }
            }
            //交换顶端位置和最后位置
            DataWrap temp = data[0];
            data[0] = data[data.length-1-index];
            data[data.length-1-index] = temp;
        }
        System.out.println("========排序后======");
        System.out.println(Arrays.toString(data));
    }
}
