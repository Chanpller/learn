package chapter16;

public class StringTest {
    public static void main(String[] args) {
        String str = new String("hello") +
                new String("world");
        String str2 = "helloword";
        System.out.println(str2 == str);

    }
}
