package chapter16;

public class TestInterfaceClass implements A,B,C{
    private int num = 1;

    public int add() {
        num = num + 2;
        return num;
    }
}
interface A{

}
interface B{

}
interface C{

}