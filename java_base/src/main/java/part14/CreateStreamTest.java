package part14;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

public class CreateStreamTest {
    @Test
    public void testCollectionGetStream(){
        List<String> stringList = new ArrayList<>();
        stringList.add("123");
        stringList.add("234");
        Stream<String> stream = stringList.stream();
        System.out.println(stream);
    }
    @Test
    public void testArrayGetStream(){
        String[] strings = {"123","234"};
        Stream<String> stringStream = Arrays.stream(strings);
        System.out.println(stringStream);
    }
    @Test
    public void testStreamOfGetStream(){
        Stream<String> stringStream = Stream.of("123","234");
        System.out.println(stringStream);
    }
    @Test
    public void testStreamIterateAndStreamGenerateGetStream(){
        //创建随机数的无限流
        //类::实例方法，是适用于 传入的形参， 引用方法需要调用的。如果没有形参则无法调用。
        Stream<Integer> generate = Stream.generate(()->new Random().nextInt());
        //类::静态方法。形参、返回值与引用方法一致。
        Stream<Double> generate2 = Stream.generate(Math::random);
        System.out.println(generate);
        System.out.println(generate2);

        //创建偶数的无限流
        Stream<Integer> iterate = Stream.iterate(0, t -> t + 2);
        System.out.println(iterate);

    }
}
