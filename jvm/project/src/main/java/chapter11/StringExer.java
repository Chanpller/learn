package chapter11;

public class StringExer {
    String str = new String("good");
    char[] ch = {'t', 'e', 's', 't'};

    public void change(String str, char ch[]) {
        str = "test ok";
        ch[0] = 'b';
        ch = new char[]{'e','b','c'};
    }

    public static void main(String[] args) {
        StringExer ex = new StringExer();
        ex.change(ex.str, ex.ch);
//方法的引用都是值的引用。这里是将str对象地址值赋值给方法变量,
//方法内的变量str的变量值 重新只想了test ok，而属性str并没有改变。
        System.out.println(ex.str);//good，
//方法的引用都是值的引用。这里是将ch对象地址值赋值给方法变量,
//ch中的元素被重新赋值，会修改数组中值的引用，所以变成了b。如果是ch = new char[10]
//,则属性中的ch不会改变。
        System.out.println(ex.ch);//best
    }
}
