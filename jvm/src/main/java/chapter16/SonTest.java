package chapter16;

/**
 * 非静态成员变量的初始化过程：
 * 1. 默认初始化
 * 2. 显示初始化 / 代码块中初始化
 * 3. 构造器初始化
 * 4. 有了对象之后，可以 对象.setter方法 或者 对象.属性的方式赋值
 *
 */
class Father {
    int x = 10;

    public Father() {
        this.print();
        x = 20;
    }

    public void print() {
        System.out.println("Father.x = " + x);
    }
}

class Son extends Father {
    int x = 30;

    public Son() {
        System.out.println("====="+x);
//        this.print();
        x = 40;
    }

    public void print() {
        System.out.println("Son.x = " + x);
    }
}

public class SonTest {
    public static void main(String[] args) {
        Father f = new Son();
        System.out.println(f.x);
    }
}

/**
 * Son.x = 0
 * Son.x = 30
 * 20
 */
