package java.lang;

public class StringTest {
    //自定义一个java.lang.String，然后实例化，会报错Prohibited package name: java.lang
//核心的String类是由引导类加载器加载的，不能被篡改。
    public static void main(String[] args) {
        String str = new String();
    }
}
