1.1从迭代到流的操作

处理集合，比如需要统计集合中的元素，会list结果饭后返回内容。JAVA SE 8提供了stream操作。Collection集合提供了stream()方法，返回一个java.util.stream.Stream类，Stream类可以进行过滤，统计等操作。使用parallelStream可以并行执行过滤和计数。

		List<String> list = new ArrayList<>();
		list.add("123545785545");
		list.add("123545785545ewrqew");
		list.add("123545785545qewreqwrqewrewqr");
		long count = list.stream().filter(str->str.length()>12).count();
		System.out.println(count);
		long parallelStreamCount = list.parallelStream().filter(str->str.length()>12).count();
		System.out.println(parallelStreamCount);
1.2 stream的创建

Stream.of方法可以对数组进行转化为流。Array.stream也可以。

		Stream<String> stream = Stream.empty();//产生一个空的流
		Stream<String> streamGenerate = Stream.generate(()->"123");//产生一个常量流，会无限循环。
		Stream<Double> streamDouble = Stream.generate(Math::random);//产生一个随机数流，会无限循环。
		Iterator<Double>  iterator= streamDouble.iterator();
		while(iterator.hasNext()){
				System.out.println(iterator.next());
			}
API

	java.util.stream.Stream
	static <T> Stream<T> of(T... values)产生一个元素给定值的流
	static <T> Stream<T> empty() 产生一个不包含任何元素的流
	static <T> Stream<T> generate(Supplier<T> s)产生一个无限流，它的值是通过反复调用函数S构建的。
	static<T> Stream<T> iterate(final T seed, final UnaryOperator<T> f) 产生一个无限流，它的元素包含种子、在种子上调用f产生的值、在前一个元素上调用f产生的值。
	
	java.util.Arrays
	static <T> Stream<T> stream(T[] array)产生一个流
	static <T> Stream<T> stream(T[] array, int startInclusive, int endExclusive) 产生一个指定返回的流
	
	java.util.regex.Pattern
	Stream<String> splitAsStream(final CharSequence input) 产生一个流，它的元素时输入中由该模式界定的部分
	
	java.nio.file.Files
	static Stream<String> lines(Path path) 产生一个流，它的元素是指定文件中的行
	static Stream<String> lines(Path path, Charset cs) 产生一个流，它的元素是指定文件中的行，以及编码
	
	java.util.function.Supplier
	T get() 提供一个值



1.3 filter、map和flatMap方法

流的转换会产生一个新的流。

filter进行过滤

map应用到每个元素上，返回的流是包含流的流，类似于数组套数组（2维数组）

flatMap摊平的map，返回的就是摊平的流，类似于把2维数组展开为一维数组、



1.4抽取子流和链接流

stream.limit(n)会返回一个新的流，它在n个元素之后结束。比如随机流，取前一百个。

stream.skip(n)它会丢弃前n个元素。比如读取文件，去掉标题。调用时，stream不应该是无限流。



1.5其他的流交换

stream.distinct()去重

stream.sorted()排序

stream.peek(Consumer<? super T> action)与原来流元素相同，但是每次获取一个元素，都会调用一个函数action。



1.6简单约简

API

	java.util.stream.Stream
	Optional<T> max(Consumer<? super T> action)查找最大值，如果流为空，返回一个空的Optional对象
	Optional<T> max(Consumer<? super T> action)查找最小值，如果流为空，返回一个空的Optional对象
	Optional<T> findFirst()查找第一个元素，如果流为空，返回一个空的Optional对象
	Optional<T> findAny()查找任意一个元素，，如果流为空，返回一个空的Optional对象
	Optional<T> anyMatch(Predicate<? super T> predicate任意一元素)匹配，返回true
	Optional<T> allMatch(Predicate<? super T> predicate)所有元素匹配，返回true
	Optional<T> noneMatch(Predicate<? super T> predicate)没有元素匹配，返回true
1.7 Optional类型

Optional包装器对象，Optional比直接使用T引用要安全，要么引用某个对象，要么为null。它只有在正确使用的情况下才会更安全。

1.7.1如何使用Optional值

使用API才能体现比直接使用泛型更安全。

API

```
java.util.Optional
void ifPresent(Consumer<? super T> consumer)如果Optional不为空，那么它的值传给consumer
T orElse(T other)产生Optional，如果Optional为空，值为other
T orElseGet(Supplier<? extends T> other)产生Optional，如果Optional为空，获取值时为other
<X extends Throwable> T orElseThrow(Supplier<? extends X> exceptionSupplier)产生Optional，Optional为空时，调用抛出exceptionSupplier结果
<U> Optional<U> map(Function<? super T, ? extends U> mapper) 产生将该Optional的值传递给mapper后的结果，只要这个Optional不为空或者结果不为null，否则产生一个空Optional。

```

1.7.2不适合使用Optional值的方式

get方法会在Optional值存在的情况下获得其中包装器的元素，或者不存在的情况下抛出NoSuchElementException。

isPresent方法可以检测Optional对象是否有一个值。

1.7.3创建Optional值

API

```
java.util.Optional
static <T> Optional<T> of(T value)产生Optional,如果value是null，抛出NullPointerException
static <T> Optional<T> ofNullable(T value)产生Optional,如果value是null,产生一个空的Optional
static <T> Optional<T> empty()产生一个空的Optional
```



1.7.4使用flatMap构建Optional值函数

```
<U> Optional<U> map(Function<? super T, ? extends U> mapper) 产生将该Optional的值传递给mapper后的结果，或者Optional为空返回一个空Optional。如果结果为空，会抛出空指针异常。
```

1.8收集结果

API

```
java.util.stream.BaseStream
Iterator<T> iterator()产生一个获取当前流中各元素的迭代器。

java.util.stream.Stream
void forEache(Consumer<? super T> action)在流的每个元素上调用action
Object[] toArray()
<A> A[] toArray(IntFunction<A[]> generator) 产生一个对象数组，或者将A[]::new传递给构造器时，返回一个A类型的数组。
<R,A> R collect(Collector<? super T,A,R> collector)使用给定的收集器来手机当前流中的元素。Collectors类有用于多种收集器的工厂方法。

java.util.stream.Collectors
static <T> Collector<T, ?, List<T>> toList() 产生一个将元素手机到列表或集的收集器
static <T> Collector<T, ?, Set<T>> toSet()

```

1.9收集到映射表中

```
Stream<Person>  people = Stream.of(people1,people2,people3);

Map<Integer,String> idToName = people.collect(Collectors.toMap(Person::getId,Person::getName))

//people.collect(Collectors.toMap(Person::getId,Function.identity())) Function.identity()表示当前元素
```

如果有相同的键，会存在冲突，上面代码收集器会抛出IllegalStateException对象。可以通过第三个参数来指定。

```
people.collect(Collectors.toMap(Person::getId,Person::getName,(oldValue,newValue)->oldValue));//不进行覆盖
```

1.10群组和分区

stream需要分区使用收集器groupby

demo

	List<String> list = new ArrayList<>();
		list.add("123545785545");
		list.add("123545785545");
		list.add("123545785545");
		list.add("123545785545");
		list.add("123545785545ewrqew");
		list.add("123545785545qewreqwrqewrewqr");
		Stream<String> stream = list.stream();
		Map<String,List<String>> map =  stream.collect(Collectors.groupingBy(String::toString));
		System.out.println(map);
结果

{123545785545qewreqwrqewrewqr=[123545785545qewreqwrqewrewqr], 123545785545ewrqew=[123545785545ewrqew], 123545785545=[123545785545, 123545785545, 123545785545, 123545785545]}

当结果集是boolean值，使用partitioningBy效率比groupingBy高一些

groupingByConcurrent就是在使用并行流时获得一个并行组装的并行映射表

API

```
java.util.stream.Collectors

static <T, K> Collector<T, ?, Map<K, List<T>>> groupingBy(Function<? super T, ? extends K> classifier)

static <T, K> Collector<T, ?, ConcurrentMap<K, List<T>>>  groupingByConcurrent(Function<? super T, ? extends K> classifier)    产生一个收集器，按照classifier结果相同的组合成一个列表。

static <T> Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)产生一个收集器，它会产生一个映射表，键是true/false，满足条件的和不满足条件的分开组成列表。
```



1.11下游收集器

如果groupby之后想要SET而不是List，可以使用下游收集器

demo

		List<String> list = new ArrayList<>();
		list.add("123545785545");
		list.add("123545785545");
		list.add("123545785545");
		list.add("123545785545");
		list.add("123545785545ewrqew");
		list.add("123545785545qewreqwrqewrewqr");
		Stream<String> stream = list.stream();
	//		Map<String,List<String>> map =  stream.collect(Collectors.groupingBy(String::toString));
	//		System.out.println(map);
	//		
	//		stream = list.stream();
			Map<String,Set<String>> mapSet= stream.collect(Collectors.groupingBy(String::toString,Collectors.toSet()));
			System.out.println(mapSet);
结果

{123545785545qewreqwrqewrewqr=[123545785545qewreqwrqewrewqr], 123545785545ewrqew=[123545785545ewrqew], 123545785545=[123545785545]}

API

```
java.util.stream.Collectors

static <T> Collector<T,?,Long> counting() 产生一个元素进行计数的收集器。
static <T> Collector<T,?,Long> summingInt(ToIntFunction<? super T> mapper) 
static <T> Collector<T,?,Long> summingLong(ToLongFunction<? super T> mapper) 
static <T> Collector<T,?,Long> summingDouble(ToDoubleFunction<? super T> mapper) 
产生一个收集器，使用mapper计算，请求总和。
static <T> Collector<T, ?, Optional<T>> maxBy(Comparator<? super T> comparator)
static <T> Collector<T, ?, Optional<T>> minBy(Comparator<? super T> comparator)
产生一个收集器，使用comparator指定排序，获取到最大或最小值
static <T, U, A, R> Collector<T, ?, R> mapping(Function<? super T, ? extends U> mapper, Collector<? super U, A, R> downstream)
产生一个收集器，它会生成一个map,key是mapper操作产生，value是downstream收集到相同键的元素。
```

1.12 reduce（简约，累计）操作

demo

		List<Integer> list = new ArrayList<>();
		Stream<Integer> stream = list.stream();
		Optional<Integer> optional= stream.reduce((oldVue,newValue)->oldVue+newValue);
		System.out.println(optional);
		
		list.add(1);
		list.add(2);
		list.add(3);
		list.add(4);
		list.add(5);
		stream = list.stream();
		optional= stream.reduce((oldVue,newValue)->{return oldVue+newValue;});
		System.out.println(optional.get());
		
		stream = list.stream();
			Integer integer= stream.reduce(1,(oldVue,newValue)->{return oldVue+newValue;});
			System.out.println(integer);
结果

Optional.empty
15

16



API

```
java.util.stream.Stream

Optional<T> reduce(BinaryOperator<T> accumulator);
根据accumulator进行累计计算，如果没有元素，Optional返回是empty

T reduce(T identity, BinaryOperator<T> accumulator)；
根据accumulator进行累计计算，初始值为identity会累计在计算之中

<U> U reduce(U identity, BiFunction<U, ? super T, U> accumulator,BinaryOperator<U> combiner)；
根据accumulator进行累计计算，初始值为identity会累计在计算之中，如果提供了combiner，那么它可以用来将分别累计的各部分合成总和。

<R> R collect(Supplier<R> supplier,BiConsumer<R, ? super T> accumulator,BiConsumer<R, R> combiner)；
将元素收集到类型R的结果中。每个部分上，都会调用supplier来提供初始化结果，再调用accumulator交替的添加元素到结果中，并调用combiner来整合两个结果。 这个需要使用并行流才能调用到combiner
```

demo

		List<List<Integer>> listIntegers = new ArrayList<>();
		
		List<Integer> listInteger1 = new ArrayList<>();
		listInteger1.add(1);
		List<Integer> listInteger2 = new ArrayList<>();
		listInteger2.add(2);
		List<Integer> listInteger3 = new ArrayList<>();
		listInteger3.add(3);
		List<Integer> listInteger4 = new ArrayList<>();
		listInteger4.add(4);
		listIntegers.add(listInteger1);
		listIntegers.add(listInteger2);
		listIntegers.add(listInteger3);
		listIntegers.add(listInteger4);
		List<Integer> listinteger = listIntegers.parallelStream().collect(
				()->new ArrayList<Integer>(), 
				(identity,elment)->{
					identity.addAll(elment);
					System.out.println(identity);
				},
				(first,second)->{
					first.addAll(second);
					System.out.println("======"+second);
					}
				);
		
		System.out.println(listinteger.size());
1.13基本类型流

整数如果使用Stream<Integer>，这样会有装箱和拆箱的效果，效率低。基本类型double、float、long、short、char、byte和boolean，有专门的基础类型流IntStream、LongStream、DoubleStream。short、char、byte和boolean可以用IntStream，float可以用DoubleStream。

API

```
java.util.stream.IntStream

static IntStream range(int startInclusive, int endExclusive) 
产生一个startInclusive到endExclusive的整数IntStream，起始为startInclusive，endExclusive-1

static IntStream rangeClosed(int startInclusive, int endExclusive) 
产生一个startInclusive到endExclusive的整数IntStream，起始为startInclusive，endExclusive

static IntStream of(int... values)
产生一个values为值的IntStream

int[] toArray()
产生数组

int sum()
求和
OptionalDouble average()
求平均值
OptionalInt max()
求最大值
OptionalInt min()
求最小值
IntSummaryStatistics summaryStatistics()
请总和
Stream<Interger> boxed()
产生包装器对象流
```

```
java.util.stream.LongStream

static LongStream range(long startInclusive, int endExclusive) 
产生一个startInclusive到endExclusive的整数LongStream，起始为startInclusive（包含），结束值endExclusive（不包含）

static LongStream rangeClosed(long startInclusive, long endExclusive) 
产生一个startInclusive到endExclusive的整数LongStream，起始为startInclusive（包含），结束值endExclusive（包含）

static LongStream of(long... values)
产生一个values为值的LongStream

long[] toArray()
产生数组
long sum()
求和
OptionalDouble average()
求平均值
OptionalLong max()
求最大值
OptionalLong min()
求最小值
LongSummaryStatistics summaryStatistics()
请总和
Stream<Long> boxed()
产生包装器对象流
```

```
java.util.stream.DoubleStream
static DoubleStream of(double... values)
产生一个values为值的DoubleStream

double[] toArray()
产生数组
double sum()
求和
OptionalDouble average()
求平均值
OptionalDouble max()
求最大值
OptionalDoubleg min()
求最小值
DoubleSummaryStatistics summaryStatistics()
请总和
Stream<Double> boxed()
产生包装器对象流
```

```
java.lang.CharSequence

IntStream codePoints()

产生当前字符串的所有Unicode码点构成的流
```

```
java.util.Random
IntStream ints()
IntStream ints(long streamSize)
IntStream ints(int randomNumberOrigin, int randomNumberBound) 
IntStream ints(long streamSize, int randomNumberOrigin,int randomNumberBound)

LongStream longs()
LongStream longs(long streamSize)
LongStream longs(long randomNumberOrigin, long randomNumberBound) 
LongStream longs(long streamSize, int randomNumberOrigin,int randomNumberBound)

DoubleStream doubles()
DoubleStream doubles(long streamSize)
DoubleStream doubles(double randomNumberOrigin, double randomNumberBound) 
DoubleStream doubles(long streamSize, int randomNumberOrigin,int randomNumberBound)
产生一个随机数流。如果提供了streamSize,表示有界。元素值在randomNumberOrigin（包含）到randomNumberBound（不包含）之间
```

```
java.util.Optional(Int/Long/Double)
static Optional(Int/Long/Double) of(int/long/double value)
根据value产生一个可选对象

int/long/double getAs(Int/Long/Double)()
产生当前可选对象值，为空抛出NoSuchElementException异常

int/long/double orElse(int/long/double other)
int/long/double orElseGet((Int/Long/Double)Supplier other)
产生当前可选对象值，为空返回other值

void ifPresent((Int/Long/Double)Consumer consumer)
如果当前可选对象不为空，则将其值传递给consumer
```

```
java.util.(Int/Long/Double)SummaryStatistics
long getCount()
获取元素个数
(int/long/double)  getSum()
总和
double getAverage()
平均数
(int/long/double)  getMax()
最大值
(int/long/double)  getMin()
最小值
```

1.14 并行流

流使得并行处理变的容易。

Collection.parallelStream()获取并行流，可以并发处理。

Stream.of(stream).parallel()；可以将任意的顺序流转换为并行流。

默认情况下，从有序集和（数组和列表）、范围、生成器和迭代产生的流，或者通过调用Steam.sorted产生的流都是有序的。

排序并不排斥高效的并行处理。如，stream.map(fun)时，流可以被划分为n的部分，会并行处理，然后按照顺序组装起来。

通过调用unordered方法，表示可以不用排序，更高效的并行化处理。Stream.distinct则是使用的unordered这种方法。

通过放弃排序，可以提高limit方法的速度。

并行流正常工作，需要满足大量的条件：

- 数据应该在内存中。必须等到数据到达是非常低下的。
- 流应该可以被高效的分成若干个子部分。由数组或者平衡二叉数支持的流都可以工作的很好，但是Steam.iterate返回的结果不行。
- 流操作的工作量应该具有较大的规模。如果量小，并行计算没有意义。
- 流操作不应该被阻塞。

API

java.util.stream.BaseStream<T, S extends BaseStream<T, S>>

```
S parallel();
```
