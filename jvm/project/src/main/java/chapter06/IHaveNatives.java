package chapter06;

public class IHaveNatives {

//    public abstract   native void Native0(int x);
    public  native void Native1(int x);

    public native static long Native2();

    private native synchronized float Native3(Object o);

    native void Native4(int[] ary) throws Exception;

}
