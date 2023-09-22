# 14章-java8新特性

## 14.1概述

* Java8 2014年3月发布，重大更新版本。

* 新特性

  * 函数接口
  * Lambda表达式
  * 方法引用、构造器引用
  * Stream API
    * 并行流
    * 串行流
  * Optional类
  * 新的时间和日期API
  * 增强接口
    * 静态方法
    * 默认方法

  ---------------------------------

  * 注解增加
    * 重复注解
    * 类型注解
    * 通用目标类型注解
  * 集合
    * 集合的stream操作
  * 并发
  * Arryas
  * Number和Math
  * IO/NIO的改进
  * Reflection获取形参名
  * String:jion()
  * Files
  * 新编译工具
    * jjs   可以执行js文件（Nashorn引擎允许JVM上允许JS应用）
    * jdeps
  * JVM中的Metaspace取代PermGen空间

## 14.2 Lambda表达式

* Lambda是一个匿名函数，可以理解为：一段可以传递的代码。

* "->"称为Lambda操作符或箭头操作符。左侧：需要的形参列表。右侧：抽象方法的实现逻辑。

* Lambda表达式的本质：作为函数式接口的实例

* 一个接口中，只声明了一个抽象方法，则此接口就称为函数式接口。我们可以在一个接口上使用 @FunctionalInterface 注解， 这样做可以检查它是否是一个函数式接口。

* 所以以前用匿名实现类表示的现在都可以用Lambda表达式来写。

* 语法

  * 格式一：无参，无返回值。括号不能省，大括号可以省。

    ```
    Runnable r2 = () -> System.out.println("我爱北京故宫");
    或
    Runnable r2 = () -> {System.out.println("我爱北京故宫")};
    ```

  * 格式二：一个参数，但无返回值

    ```
    Consumer<String> con1 = (String s) -> {
        System.out.println(s);
    };
    或
    Consumer<String> con2 = (s) -> {
        System.out.println(s);
    };
    或
    Consumer<String> con1 = s -> System.out.println(s);
    ```

  * 格式三：数据类型可以被确定推断的（泛型）

    ```
    Consumer<String> con2 = (s) -> {
        System.out.println(s);
    };
    或
    Consumer<String> con1 = s -> System.out.println(s);
    ```

  * 格式四：一个参数，小括号可以省略

    ```
    Consumer<String> con2 = s -> {
        System.out.println(s);
    };
    ```

  * 格式五：只有一条执行语句，有没有返回值的都可以省略大括号

    ```
    Comparator<Integer> com2 = (o1,o2) -> o1.compareTo(o2);
    Consumer<String> con2 = s -> System.out.println(s);
    ```

  * 格式六：两个或以上的参数，小括号不能省。多条执行语句，并且有返回值，大括号不能省

    ```
    Comparator<Integer> com2 = (o1,o2) -> {
        System.out.println(o1);
        System.out.println(o2);
        return o1.compareTo(o2);
    };
    ```

## 14.3 函数式接口

* 只包含一个抽象方法的接口，称为 函数式接口 。

* 我们可以在一个接口上使用@FunctionalInterface 注解，这样做可以检查它是否是一个函数式接口。

* Lambda 表达式就是一个函数式接口的实例。

* java内置的4大核心函数式接口

  * 消费型接口 Consumer<T>     void accept(T t)
  * 供给型接口 Supplier<T>     T get()
  * 函数型接口 Function<T,R>   R apply(T t)
  * 断定型接口 Predicate<T>    boolean test(T t)

* 其他Java函数式接口

  * BiFunction<T, U, R>       R apply(T t, U u);对类型为T, U 参数应用操作返回R类型的结果
  * UnaryOperator<T> ((Function子接口) R apply(T t);对类型为T 的对象进行一元运算并返回 T 类型的结果 。
  * BinaryOperator<T>(BiFunction 子接口）对类型为T 的对象进行二元运算并返回T类型的结果. 包含方法 为 T apply(T t1 T t2)
  * BiConsumer<T,U>  void apply(T t, U u); 对类型为T, U 参数应用操作 。
  * BiPredicate<T,U>  boolean test(T t,U u)
  * ToIntFunction<T>
    ToLongFunction<T>
    ToDoubleFunction<T>    分别计算int 、 long 、 double 值的函数
  * IntFunction<R>
    LongFunction<R>
    DoubleFunction<R>   参数分别为int 、 long 、 double 类型的函数

* java自定义的函数接口，我们可以根据实际情况，参考对应接口，做成函数式方法。

  ```
  //断定型接口，可以使用Predicate进行设计。
  @Test
  public void test2(){
      List<String> list = Arrays.asList("北京","南京","天津","东京","西京","普京");
  
      List<String> filterStrs1 = filterString(list,s -> s.contains("京"));
      System.out.println(filterStrs1);
  }
  //根据给定的规则，过滤集合中的字符串。此规则由Predicate的方法决定
  public List<String> filterString(List<String> list, Predicate<String> pre){
      ArrayList<String> filterList = new ArrayList<>();
      for(String s : list){
          if(pre.test(s)){
              filterList.add(s);
          }
      }
      return filterList;
  }
  ```

* 自定义函数式接口

  ```
  /**
   * 自定义函数式接口
   * @author shkstart
   * @create 2019 下午 2:20
   */
  @FunctionalInterface
  public interface MyInterface {
  
      void method1();//只能有一个抽象方法，可以有默认方法
  
  //    void method2();不能加，加了就不是函数式接口了
  }
  ```

## 14.4 方法引用

- 使用情境：当要传递给Lambda体的操作，已经有实现的方法了，可以使用方法引用！
- 方法引用，本质上就是Lambda表达式，而Lambda表达式作为函数式接口的实例。所以方法引用，也是函数式接口的实例。
- 使用格式：  类(或对象) :: 方法名

### 14.4.1 对象::实例方法名

接口中的抽象方法的形参列表和返回值类型与方法引用的方法的形参列表和返回值类型相同

```java
// 情况一：对象 :: 实例方法
//Consumer中的void accept(T t)
//PrintStream中的void println(T t)
@Test
public void test1() {
   Consumer<String> con1 = str -> System.out.println(str);
   con1.accept("北京");

   System.out.println("*******************");
   PrintStream ps = System.out;
   Consumer<String> con2 = ps::println;
   con2.accept("beijing");
}

//Supplier中的T get()
	//Employee中的String getName()
	@Test
	public void test2() {
		Employee emp = new Employee(1001,"Tom",23,5600);

		Supplier<String> sup1 = () -> emp.getName();
		System.out.println(sup1.get());

		System.out.println("*******************");
		Supplier<String> sup2 = emp::getName;
		System.out.println(sup2.get());

	}
```

### 14.4.2 类::静态方法名

接口中的抽象方法的形参列表和返回值类型与方法引用的方法的形参列表和返回值类型相同

```java
// 情况二：类 :: 静态方法
//Comparator中的int compare(T t1,T t2)
//Integer中的int compare(T t1,T t2)
@Test
public void test3() {
   Comparator<Integer> com1 = (t1,t2) -> Integer.compare(t1,t2);
   System.out.println(com1.compare(12,21));

   System.out.println("*******************");
   Comparator<Integer> com2 = Integer::compare;
   System.out.println(com2.compare(12,3));

}

//Function中的R apply(T t)
//Math中的Long round(Double d)
@Test
public void test4() {
   Function<Double,Long> func = new Function<Double, Long>() {
      @Override
      public Long apply(Double d) {
         return Math.round(d);
      }
   };
    
   System.out.println("*******************");
   Function<Double,Long> func1 = d -> Math.round(d);
   System.out.println(func1.apply(12.3));

   System.out.println("*******************");
   Function<Double,Long> func2 = Math::round;
   System.out.println(func2.apply(12.6));
}
```

### 14.4.3 类::实例方法名

接口中的抽象方法的形参列表在方法引用时是对应使用的，返回值类型与引用的返回值类型相同。

多个或一个参数形参在方法体中都有使用，才可以。

```java
// 情况三：类 :: 实例方法  (有难度)
// Comparator中的int comapre(T t1,T t2)
// String中的int t1.compareTo(t2)
@Test
public void test5() {
   Comparator<String> com1 = (s1,s2) -> s1.compareTo(s2);
   System.out.println(com1.compare("abc","abd"));
    
   System.out.println("*******************");
   Comparator<String> com2 = String :: compareTo;
   System.out.println(com2.compare("abd","abm"));
}

//BiPredicate中的boolean test(T t1, T t2);
//String中的boolean t1.equals(t2)
@Test
public void test6() {
   BiPredicate<String,String> pre1 = (s1,s2) -> s1.equals(s2);
   System.out.println(pre1.test("abc","abc"));

   System.out.println("*******************");
   BiPredicate<String,String> pre2 = String :: equals;
   System.out.println(pre2.test("abc","abd"));
}

// Function中的R apply(T t)
// Employee中的String getName();
@Test
public void test7() {
   Employee employee = new Employee(1001, "Jerry", 23, 6000);
   Function<Employee,String> func1 = e -> e.getName();
   System.out.println(func1.apply(employee));

   System.out.println("*******************");
   Function<Employee,String> func2 = Employee::getName;
   System.out.println(func2.apply(employee));
}
```

## 14.5 构造器引用

- 格式：ClassName::new

- 与方法引用类似，函数式接口的抽象方法的形参列表和构造器的形参列表一致。

- 抽象方法的返回值类型即为构造器所属的类的类型

  ```java
  //构造器引用
     //Supplier中的T get()
     //Employee的空参构造器：Employee()
     @Test
     public void test1(){
  		//原始写法
         Supplier<Employee> sup = new Supplier<Employee>() {
             @Override
             public Employee get() {
                 return new Employee();
             }
         };
         System.out.println("*******************");
  		//Lamdba写法
         Supplier<Employee>  sup1 = () -> new Employee();
         System.out.println(sup1.get());
  
         System.out.println("*******************");
  		//直接用用写法
         Supplier<Employee>  sup2 = Employee :: new;
         System.out.println(sup2.get());
     }
  
  //Function中的R apply(T t)
  //带参数构造器引用
      @Test
      public void test2(){
          //Lamdba写法
          Function<Integer,Employee> func1 = id -> new Employee(id);
          Employee employee = func1.apply(1001);
          System.out.println(employee);
  
          System.out.println("*******************");
  		
          //直接引用
          Function<Integer,Employee> func2 = Employee :: new;
          Employee employee1 = func2.apply(1002);
          System.out.println(employee1);
      }
  ```

## 14.6 数组引用

- 数组看做是一个特殊的类，则写法与构造器引用一致。
- 格式：type[]::new

```
//数组引用
   //Function中的R apply(T t)
   @Test
   public void test4(){
       //Lamdba写法
       Function<Integer,String[]> func1 = length -> new String[length];
       String[] arr1 = func1.apply(5);
       System.out.println(Arrays.toString(arr1));

       System.out.println("*******************");
		//直接引用写法
       Function<Integer,String[]> func2 = String[] :: new;
       String[] arr2 = func2.apply(10);
       System.out.println(Arrays.toString(arr2));
       
       //二维数组
       BiFunction<Integer,Integer,String[][]> biFunction = (len1,len2) -> new String[len1][len2];;
       System.out.println(biFunction.apply(100,200));

   }
```



## 14.7 stream API

* Stream关注的是对数据的运算，与CPU打交道。集合关注的是数据的存储，与内存打交道。提供了类似于数据库的操作。
* Stream是数据渠道，用于操作数据源（集合、数组等）所生成的元素序列。
* Stream自己不会存储元素、不会改变源对象、操作是延迟执行的。
* Stream的操作三个步骤
  * 创建 Stream：一个数据源（如：集合、数组），获取一个流
  * 中间操作：一个中间操作链，对数据源的数据进行处理
  * 终止操作（终端操作）：一旦执行终止操作，就执行中间操作链 ，并产生结果。之后不能再使用。

### 14.7.1 获取Stream

* 方式一：通过集合。Collection接口
  * default Stream<E> stream()  获取一个顺序流
  * default Stream<E> parallelStream()获取一个并行流，可以并发操作，效率更高。
* 方式二：通过数组。Arrays静态方法
  * static <T> Stream<T> stream(T[] array)返回一个流
* 方式三：Stream 的 of()
  * public static<T> Stream<T> of(T... values) : 返回一个流
* 方式四：Stream.iterate()和Stream.generate()创建无限流
  * public static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) 迭代方式创建
  * public static<T> Stream<T> generate(Supplier<T> s) 生成方式创建

```java
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
```

### 14.7.2 Stream中间操作

* 中间操作可以连接起来形成一个流水线
* 中间操作也不会执行任何处理，需要终止操作才会执行。

#### 14.7.2.1 筛选与切片

* filter(Predicate p)  过滤

* distinct()  通过hashCode() 和 equals()去重

* limit(long maxSize）截断流，只取maxSize个
* skip(long n）跳过n个

```java
        List<Employee> list = EmployeeData.getEmployees();
//        filter(Predicate p)——接收 Lambda ， 从流中排除某些元素。
        Stream<Employee> stream = list.stream();
        //练习：查询员工表中薪资大于7000的员工信息
        stream.filter(e -> e.getSalary() > 7000).forEach(System.out::println);

        System.out.println();
//        limit(n)——截断流，使其元素不超过给定数量。
        list.stream().limit(3).forEach(System.out::println);
        System.out.println();

//        skip(n) —— 跳过元素，返回一个扔掉了前 n 个元素的流。若流中元素不足 n 个，则返回一个空流。与 limit(n) 互补
        list.stream().skip(3).forEach(System.out::println);

        System.out.println();
//        distinct()——筛选，通过流所生成元素的 hashCode() 和 equals() 去除重复元素

        list.add(new Employee(1010,"刘强东",40,8000));
        list.add(new Employee(1010,"刘强东",41,8000));
        list.add(new Employee(1010,"刘强东",40,8000));
        list.add(new Employee(1010,"刘强东",40,8000));
        list.add(new Employee(1010,"刘强东",40,8000));

//        System.out.println(list);

        list.stream().distinct().forEach(System.out::println);
```

#### 14.7.2.2 映射

映射：给一个元素返回另一个元素。比如给员工，可以返回员工工资，通过员工的Stream映射出一个工资的Stream。

* map(Function f）接收一个函数作为参数，该函数会被应用到每个元素上，并将其映射成一个新的元素。
* mapToDouble(ToDoubleFunction f) 映射为Double类型的Stream
* mapToInt(ToIntFunction f) 映射为Int类型的Stream
* mapToLong(ToLongFunction f)映射为Long类型的Stream
* flatMap(Function f)扁平化的映射，可以将多个Stream的元素合成一个Stream。而map不行，map如果是多个Stream，只会将Stream映射为一个元素，不会取出Stream中的元素进行映射。
* 还有flatMapToInt、flatMapToLong、flatMapToDouble方法相识。

```java
    //映射
    @Test
    public void test2(){
//        map(Function f)——接收一个函数作为参数，将元素转换成其他形式或提取信息，该函数会被应用到每个元素上，并将其映射成一个新的元素。
        List<String> list = Arrays.asList("aa", "bb", "cc", "dd");
        list.stream().map(str -> str.toUpperCase()).forEach(System.out::println);

//        练习1：获取员工姓名长度大于3的员工的姓名。
        List<Employee> employees = EmployeeData.getEmployees();
        Stream<String> namesStream = employees.stream().map(Employee::getName);
        namesStream.filter(name -> name.length() > 3).forEach(System.out::println);
        System.out.println();
        //练习2：用普通的map处理Stream的类型元素，需要多次操作
        Stream<Stream<Character>> streamStream = list.stream().map(StreamAPITest1::fromStringToStream);
        streamStream.forEach(s ->{
            //s是stream对象，如果需要获取里面的元素，还需要循环一次。
            s.forEach(System.out::println);
        });
        System.out.println();
//        flatMap(Function f)——接收一个函数作为参数，将流中的每个值都换成另一个流，然后把所有流连接成一个流。
        //用flatMap不用像map处理Stream元素一样多次循环，可以直接串联起来。
        Stream<Character> characterStream = list.stream().flatMap(StreamAPITest1::fromStringToStream);
        characterStream.forEach(System.out::println);

    }
      //将字符串中的多个字符构成的集合转换为对应的Stream的实例
    public static Stream<Character> fromStringToStream(String str){//aa
        ArrayList<Character> list = new ArrayList<>();
        for(Character c : str.toCharArray()){
            list.add(c);
        }
       return list.stream();

    }
```

#### 14.7.2.3 排序

* sorted() 自然排序，需要元素实现Comparable接口
* sorted(Comparator com)比较器排序

```java
@Test
    public void test4(){
//        sorted()——自然排序
        List<Integer> list = Arrays.asList(12, 43, 65, 34, 87, 0, -98, 7);
        list.stream().sorted().forEach(System.out::println);
        //抛异常，原因:Employee没有实现Comparable接口
//        List<Employee> employees = EmployeeData.getEmployees();
//        employees.stream().sorted().forEach(System.out::println);
//        sorted(Comparator com)——定制排序
        List<Employee> employees = EmployeeData.getEmployees();
        employees.stream().sorted( (e1,e2) -> {
           int ageValue = Integer.compare(e1.getAge(),e2.getAge());
           if(ageValue != 0){
               return ageValue;
           }else{
               return -Double.compare(e1.getSalary(),e2.getSalary());
           }
        }).forEach(System.out::println);
    }
```

### 14.7.3 Stream终止操作

#### 14.7.3.1 匹配与查找

- allMatch(Predicate p) 检查是否匹配所有元素
- anyMatch(Predicate p) 检查是否至少匹配一个元素
- noneMatch(Predicate p) 检查是否没有匹配所有元素
- findFirst() 返回第一个元素，比如排序后取第一个。
- findAny() 返回当前流中的任意元素
- count() 返回流中元素总数
- max(Comparator c) 返回流中最大值
- min(Comparator c) 返回流中最小值
- forEach(Consumer c）内部迭代

```java
//1-匹配与查找
    @Test
    public void test1(){
        List<Employee> employees = EmployeeData.getEmployees();

//        allMatch(Predicate p)——检查是否匹配所有元素。
//          练习：是否所有的员工的年龄都大于18
        boolean allMatch = employees.stream().allMatch(e -> e.getAge() > 18);
        System.out.println(allMatch);

//        anyMatch(Predicate p)——检查是否至少匹配一个元素。
//         练习：是否存在员工的工资大于 10000
        boolean anyMatch = employees.stream().anyMatch(e -> e.getSalary() > 10000);
        System.out.println(anyMatch);

//        noneMatch(Predicate p)——检查是否没有匹配的元素。
//          练习：是否存在员工姓“雷”
        boolean noneMatch = employees.stream().noneMatch(e -> e.getName().startsWith("雷"));
        System.out.println(noneMatch);
//        findFirst——返回第一个元素
        Optional<Employee> employee = employees.stream().findFirst();
        System.out.println(employee);
//        findAny——返回当前流中的任意元素
        Optional<Employee> employee1 = employees.parallelStream().findAny();
        System.out.println(employee1);

    }
@Test
    public void test2(){
        List<Employee> employees = EmployeeData.getEmployees();
        // count——返回流中元素的总个数
        long count = employees.stream().filter(e -> e.getSalary() > 5000).count();
        System.out.println(count);
//        max(Comparator c)——返回流中最大值
//        练习：返回最高的工资：
        Stream<Double> salaryStream = employees.stream().map(e -> e.getSalary());
        Optional<Double> maxSalary = salaryStream.max(Double::compare);
        System.out.println(maxSalary);
//        min(Comparator c)——返回流中最小值
//        练习：返回最低工资的员工
        Optional<Employee> employee = employees.stream().min((e1, e2) -> Double.compare(e1.getSalary(), e2.getSalary()));
        System.out.println(employee);
        System.out.println();
//        forEach(Consumer c)——内部迭代
        employees.stream().forEach(System.out::println);

        //使用集合的遍历操作
        employees.forEach(System.out::println);
    }
```

#### 14.7.3.2 归约

* reduce(T iden, BinaryOperator b)  iden是初始值，可以将流中元素反复结合起来，得到一个值。返回 T
* reduce(BinaryOperator b) 可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
* map 和 reduce 的连接通常称为 map-reduce 模式，因 Google用它来进行网络搜索而出名。

```
//2-归约
    @Test
    public void test3(){
//        reduce(T identity, BinaryOperator)——可以将流中元素反复结合起来，得到一个值。返回 T
//        练习1：计算1-10的自然数的和
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
        Integer sum = list.stream().reduce(0, Integer::sum);
        System.out.println(sum);


//        reduce(BinaryOperator) ——可以将流中元素反复结合起来，得到一个值。返回 Optional<T>
//        练习2：计算公司所有员工工资的总和
        List<Employee> employees = EmployeeData.getEmployees();
        Stream<Double> salaryStream = employees.stream().map(Employee::getSalary);
//        Optional<Double> sumMoney = salaryStream.reduce(Double::sum);
        Optional<Double> sumMoney = salaryStream.reduce((d1,d2) -> d1 + d2);
        System.out.println(sumMoney.get());

    }
```

#### 14.7.3.3 收集

* collect(Collector c) 将流转换为其他形式。Collector接口用Collectors提供的方法就行。
* Collectors
  * toList 把流中元素收集到List
  * toSet 把流中元素收集到Set
  * toCollection 把流中元素收集到创建的集合中
  * counting 计算流中元素的个数
  * summingInt 对流中元素的整数属性求和
  * averagingInt 计算流中元素Integer 属性的平均值
  * summarizingInt 收集流中Integer 属性的统计值 
  * joining 连接流中每个字符串
  * maxBy 根据比较器选择最大值
  * minBy 根据比较器选择最小值
  * reducing 从一个作为累加器的初始值开始，利用 BinaryOperator 与流中元素逐个结合，从而归约成单个值
  * collectingAndThen 包裹另一个收集器，对其结果转换函数
  * groupingBy 根据某属性值对流分组，属性为K结果为 V
  * partitioningBy 根据true 或 false 进行分区

```
    //3-收集
    @Test
    public void test4(){
//        collect(Collector c)——将流转换为其他形式。接收一个 Collector接口的实现，用于给Stream中元素做汇总的方法
//        练习1：查找工资大于6000的员工，结果返回为一个List或Set

        List<Employee> employees = EmployeeData.getEmployees();
        List<Employee> employeeList = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toList());

        employeeList.forEach(System.out::println);
        System.out.println();
        Set<Employee> employeeSet = employees.stream().filter(e -> e.getSalary() > 6000).collect(Collectors.toSet());

        employeeSet.forEach(System.out::println);
    }
```

## 14.8 Optional 类

* Google 公司著名的 Guava 项目引入了 Optional 类，Guava 通过使用检查空值的方式来防止代码污染，它鼓励程序员写更干净的代。

* Optional<T> 类 (java.util.Optional) 是一个容器类 它可以保存类型 T 的值，代表这个值存在 。或者仅仅保存null ，表示这个值 不存在 。原来用null 表示一个值不存在，现在 Optional 可以更好的表达这个概念。并且可以避免空指针异常 。
* 创建 Optional 类对象的方法：
  * Optional.of(T t) : 创建一个 Optional 实例， t必须非空
  * Optional.empty() : 创建一个空的Optional 实例
  * Optional.ofNullable(T t) ：创建一个 Optional 实例，t 可以为null
* 判断 Optional 容器中是否包含对象：
  * boolean isPresent() : 判断是否包含对象
  * void ifPresent(Consumer<? super T> consumer) 如果有值，就执行 Consumer接口的实现代码，并且该值会作为参数传给它。
* 获取 Optional 容器的对象：
  * T get(): 如果调用对象包含值，返回该值，否则抛异常
  * T orElse(T other) 如果有值则将其返回，否则返回指定的 other 对象。
  * T orElseGet(Supplier<? extends T> other) 如果有值则将其返回，否则返回由Supplier 接口实现提供的对象。
  * T orElseThrow(Supplier<? extends X> exceptionSupplier) 如果有值则将其返回，否则抛出由 Supplier 接口实现提供的异常 。
* 常用方法：Optional.ofNullable(T t) 搭配T orElse(T other) 使用。

```java
  @Test
    public void test2(){
        Girl girl = new Girl();
//        girl = null;
        //ofNullable(T t)：t可以为null
        Optional<Girl> optionalGirl = Optional.ofNullable(girl);
        System.out.println(optionalGirl);
        //orElse(T t1):如果单前的Optional内部封装的t是非空的，则返回内部的t.
        //如果内部的t是空的，则返回orElse()方法中的参数t1.
        Girl girl1 = optionalGirl.orElse(new Girl("赵丽颖"));
        System.out.println(girl1);

    }
```