package chapter18;


import org.junit.jupiter.api.Test;

import java.util.Random;

/**
 *
 * 3. 当使用类、接口的静态字段时(final修饰特殊考虑)，比如，使用getstatic或者putstatic指令。（对应访问变量、赋值变量操作）
 *
 */
public class ActiveUse2 {
    @Test
    public void test1(){
//        System.out.println(User.num);//打印了“User类的初始化过程”，因为User.num是显示赋值了，在clinit中覆盖操作，所以类初始化了
//        System.out.println(User.num1);//没打印“User类的初始化过程”，因为User.num1是常量，在准备阶段就赋值了，不会初始化
        System.out.println(User.num2);//打印了“User类的初始化过程”，因为User.num调用了方法赋值，在clinit中赋值操作，所以类初始化了
    }

    //接口初始化测试
    @Test
    public void test2(){
//        System.out.println(CompareA.NUM1);//没打印“CompareA的初始化”，因为CompareA.NUM1，在准备阶段就赋值了，不会初始化
        System.out.println(CompareA.NUM2);//打印了“CompareA的初始化”，因为CompareA.NUM2调用了方法赋值，在clinit中赋值操作，所以类初始化了
    }
}

class User{
    static{
        System.out.println("User类的初始化过程");
    }

    public static int num = 1;
    public static final int num1 = 1;
    public static final int num2 = new Random().nextInt(10);

}

interface CompareA{
    public static final Thread t = new Thread(){
        {
            System.out.println("CompareA的初始化");
        }
    };

    public static final int NUM1 = 1;
    public static final int NUM2 = new Random().nextInt(10);

}