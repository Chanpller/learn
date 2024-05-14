package chapter17;

public class Demo {
    private int num = 1;
    public int add(){
        num = num +2;
        return num;
    }
    public void foo(long l, float f) {
        {
            int i = 0;
        }
        {
            String s = "Hello, World";
        }
    }
}
