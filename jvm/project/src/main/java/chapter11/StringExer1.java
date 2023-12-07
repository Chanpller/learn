package chapter11;

import org.junit.jupiter.api.Test;

public class StringExer1 {
    public static void main(String[] args) {
//        String x = "ab";
        String s = new String("a") + new String("b");//new String("ab")
        //在上一行代码执行完以后，字符串常量池中并没有"ab"

        String s2 = s.intern();//jdk6中：在串池中创建一个字符串"ab"
                               //jdk8中：串池中没有创建字符串"ab",而是创建一个引用，指向new String("ab")，将此引用返回

        System.out.println(s2 == "ab");//jdk6:true  jdk8:true
        System.out.println(s == "ab");//jdk6:false  jdk8:true
    }

    @Test
    public  void bianxing() {
        String x = "ab";
        //在上一行代码执行完以后，字符串常量池中有"ab"
        String s = new String("a") + new String("b");//new String("ab")


        String s2 = s.intern();
        //s还是指向原来的对象，因为常量池中已经有了ab，所以在JDK 8 中常量池中的对象不会指向s。所以s != "ab"

        System.out.println(s2 == "ab");//jdk6:true  jdk8:true
        System.out.println(s == "ab");//jdk6:false  jdk8:false
    }
}
