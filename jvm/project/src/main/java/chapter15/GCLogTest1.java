package chapter15;

/**
 * 在jdk7 和 jdk8中分别执行
 * -verbose:gc -Xms20M -Xmx20M -Xmn10M -XX:+PrintGCDetails -XX:SurvivorRatio=8 -XX:+UseSerialGC
 *  *
 *  *   -XX:+PrintGC ：输出GC日志。类似：-verbose:gc
 *  *  -XX:+PrintGCDetails ：输出GC的详细日志
 *  *  -XX:+PrintGCTimestamps ：输出GC的时间戳（以基准时间的形式）
 *  *  -XX:+PrintGCDatestamps ：输出GC的时间戳（以日期的形式，如2013-05-04T21: 53: 59.234 +0800）
 *  *  -XX:+PrintHeapAtGC ：在进行GC的前后打印出堆的信息
 *  *  -Xloggc:…/logs/gc.log ：日志文件的输出路径
 */
public class GCLogTest1 {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[4 * _1MB];
    }

    public static void main(String[] agrs) {
        testAllocation();
    }
}
