# 第11章-Redis经典五大类型源码及底层实现
## 11.1 粉丝反馈回来的题目

* redis的跳跃列表了解吗？这个数据机构有什么特点
* redis项目里面怎么用？redis的数据结构都了解哪些？布隆过滤怎么用？
* redis的多路io服用如何理解？为什么单线程还可以抗那么高的qps
* redis的zset底层实现，说了压缩列表和跳表，问这样设计的优缺点？
* redis的跳表说一下，解决了哪些问题，时间复杂度和空间复杂度如何？
* redis的zset用的什么数据结构

### 11.1.1 Redis数据类型的底层数据结构

- SDS动态字符串
- 双向链表
- 压缩列表ziplist
- 哈希表Hashtable
- 跳表skiplist
- 整数集合intset
- 快速列表quicklist
- 紧凑列表listpack

### 11.1.2 本章节意义

90%是没有太大意义的，更多的是为了面试

但是如果是大厂，会内部重构redis，比如阿里云redis，美团tair，滴滴kedis等

## 11.2 redis源码在哪里

* redis-7.0.5\src

* https://github.com/redis/redis

![image-20251016215645893](../image2/image-20251016215645893.png)

## 11.3 源码分析参考书

* 《Redis设计与实现》
* 《Redis5设计与源码分析》

## 11.4 Redis源代码的核心部分

* src源码包下该如何看

  * 源码分析思路

    * 外面考什么就看什么
    * 分类

  * Redis基本的数据结构（骨架）

    * Github官网说明

      ![image-20251016220115027](../image2/image-20251016220115027.png)

      * Redis对象object.c
      * 字符串t_string.c
      * 列表t_list.c
      * 字典t_hash.c
      * 集合及有序集合t_set.c和t_zset.c
      * 数据流t_stream.c：Streams的底层实现结构listpack.c和rax.c；了解即可

    * 简单动态字符串sds.c

    * 整数集合intset.c

    * 压缩列表ziplist.c

    * 快速链表quicklist.c

    * listpack

    * 字段dict.c

  * Redis数据库的实现

    * 数据库的底层实现db.c
    * 持久化rdb.c和aof.c

  * Redis服务端和客户端实现

    * 事件驱动ae.c和ae_epoll.c
    * 网络链接anet.c和networking.c
    * 服务端程序server.c
    * 客户端程序redis-cli.c

  * 其他

    * 主从复制replication.c
    * 哨兵sentinel.c
    * 集群cluster.c
    * 其他数据机构，如hyperloglog.c、geo.c等
    * 其他功能，如pub/sub、Lua脚本

## 11.5 我们平时说的redis时字典数据库KV键值对到底时什么

### 11.5.1 怎样实现键值对（key-value）数据库的

* redis时key-value存储系统

  * key一般都是String类型的字符串
  * value类型则为redis对象
    * value可以时字符串对象，也可以时集合数据类型的对象，比如List对象、Hash对象、Set对象

* 图说

  ![image-20251016221016770](../image2/image-20251016221016770.png)

### 11.5.2 10类型说明（粗分）

* 传统的5大类型
  * String
  * List
  * Hash
  * Set
  * ZSet
* 新介绍的5大类型
  * bitmap--实质String
  * hyperLogLog--实质String
  * GEO--实质Zset
  * Stream--实质Stream
  * BITFIFLD--看具体key

### 11.5.3 上帝视角

![image-20251016221516465](../image2/5.上帝视角.png)

### 11.5.4 Redis定义了redisObject结构体来表示string、hash、list、set、zset等数据类型

Redis中一切皆是KV,键值对俗称dict字段，用户api对到redis中时RedisObject对象的高度抽象，底层C语言时KV，dict字典

* C语言struct结构体语法简介

  ![](../image2/6.C语言struct结构体简介.jpg)

  ![](../image2/7.typedef关键字.jpg)

* Redis中每个对象都是一个redisObject结构

* 字典、KV是什么（重点）

  <font color = 'red'>每个键值对都会有一个dictEntry：</font>

  <font color = 'red'>源码位置：dict.h</font>

  ![](../image2/8.dict.h.jpg)

  <font color = 'red'>重点：从dictEntry到redisObject</font>
  ![](../image2/9.server.h.jpg)

* 这些键值对如何保存进Redis并进行读取操作，O(1)复杂度

  ![image-20251016223712899](../image2/image-20251016223712899.png)

* redisObject +Redis数据类型+Redis 所有编码方式(底层实现)三者之间的关系

  ![](../image2/10.server.h.jpg)

  ![](../image2/11.底层实现.jpg)





## 11.6 5大结构底层C语言源码分析

### 11.6.1 重点：redis数据类型与数据结构总纲图

1. 源码分析总体数据结构大纲

   - SDS动态字符串
   - 双向链表
   - 压缩列表ziplist
   - 哈希表Hashtable
   - 跳表skiplist
   - 整数集合intset
   - 快速列表quicklist
   - 紧凑列表listpack

2. redis6.0.5

   ![](../image2/12.redis6.0.5.jpg)

   string = SDS

   Set = intset + hashtabLe

   ZSet = skiplist + ziplist

   List = quicklist + ziplist

   Hash = hashtable + ziplist

3. 2021.11.29之后，Redis7
   ![](../image2/13.Redis7.jpg)

   string = SDS

   Set = intset + hashtabLe

   ZSet = skiplist + listpack紧凑列表

   List = quicklist

   Hash = hashtable + listpack

### 11.6.2 源码分析总体数据结构大纲

程序员写代码时脑子底层思维

![image-20251021222331117](../image2/image-20251021222331117.png)

上帝视角最右边编码如何来的

![image-20251016221516465](../image2/5.上帝视角.png)

redisObject操作底层定义来自哪里？来自源码server.h

![](../image2/11.底层实现.jpg)

![image-20251021222700132](../image2/image-20251021222700132.png)

### 11.6.3 从set hellow world说起

* 每个键值对都会有一个dictEntry
  * set hello word为例，因为Redis是KV键值对的数据库，每个键值对都会有一个dictEntry(源码位置:dict.h)，里面指向了key和value的指针,next 指向下一个dictEntry。
  * key是字符串，但是 Redis没有直接使用C的字符数组，而是存储在redis自定义的SDS中。
  * value 既不是直接作为字符串存储，也不是直接存储在 SDs中，而是存储在redisobject中。
  * 实际上五种常用的数据类型的任何一种，都是通过 redisobject来存储的。

![](../image2/15.redisObject.jpg)

* 看看类型：type 键

* 看看编码：object encoding hello

![](../image2/14.类型.jpg)

### 11.6.4 redisObject结构的作用

为了便于操作，Redis采用redisObjec结构来统一五种不同的数据类型，这样所有的数据类型就都可以以相同的形式在函数间传递而不用使用特定的类型结构。同时，为了识别不同的数据类型，redisObjec中定义了type和encoding字段对不同的数据类型加以区别。简单地说，redisObjec就是string、hash、list、set、zset的父类，可以在函数间传递时隐藏具体的类型信息，所以作者抽象了redisObjec结构来到达同样的目的。

![](../image2/16.redisObject解析.jpg)

- redisObject各字段的含义

  ![](../image2/17.redisObjec各字段含义.jpg)

  1、4位的type表示具体的数据类型
  2、4位的encoding表示该类型的物理编码方式见下表，同一种数据类型可能有不同的编码方式。(比如String就提供了3种:int embstr raw)

  ![](../image2/18.encoding编码.jpg)

  3、lru字段表示当内存超限时采用LRU算法清除内存中的对象。

  4、refcount表示对象的引用计数。
  <font color='red'>5、ptr指针指向真正的底层数据结构的指针。</font>

- 案例

  set age 17

  ![](../image2/19.模拟同一数据类型不同编码.jpg)

  ![](../image2/20.案例解析.jpg)

| type     | 类型                          |
| -------- | ----------------------------- |
| encoding | 编码，本案例是数值类型        |
| lru      | 最近被访问的时间              |
| refcount | 等于1，表示当前对象引用的次数 |
| ptr      | value值是多少，当前就是17     |



### 11.6.5 经典5大数据结构解析

#### 11.6.5.1 各类型的数据结构的编码映射和定义

![](../image2/21.数据类型定义.jpg)

#### 11.6.5.2 Debug Object key 

- 命令：debug object key

  ![](../image2/22.Debug命令.jpg)

  开启前：

  ![](../image2/23.Debug命令默认关闭.jpg)

  开启后：

  ![](../image2/24.开启debug命令.jpg)

  ![](../image2/25.开启后.jpg)

  Value at：内存地址
  refcount：引用次数
  encoding：物理编码类型
  serializedlength：序列化后的长度（注意这里的长度是序列化后的长度，保存为rdb文件时使用了该算法，不是真正存储在内存的大小)，会对字串做一些可能的压缩以便底层优化
  lru：记录最近使用时间戳ge
  lru_seconds_idle：空闲时间（每get一次，最近使用时间戳和空闲时间都会刷新）



#### 11.6.5.3  String数据结构介绍

##### 11.6.5.3.1 3大物理编码格式

**RedisObject内部对应三大物理编码**

![img](../image2/26.redisObject.jpg?lastModify=1761057805)

1. 整数 int

   - 保存long 型（长整型）的64位（8个字节）有符号整数

     ![img](../image2/27.long型.jpg?lastModify=1761057805)

   - 上面数字最多19位

   - 如果是数字的最大值或最小值，使用INCR key 递增会报 ERR increment or decrement would overflow

   - 只有整数才会使用int，如果是浮点数，Redis内部其实先将浮点数转化为字符串值，然后再保存。

2. 嵌入式 embstr

   代表embstr格式的SDS(Simple Dynamic String简单动态字符串)，保存长度小于44字节的字符串

   EMBSTR顾名思义即：embedded string，表示嵌入式的String

3. 未加工数据 raw

   保存长度大于44字节的字符串

##### 11.6.5.3.2 **3**大物理编码案例

- 案例演示

  ![img](../image2/28.string三大物理编码演示.jpg?lastModify=1761057805)

- C语言中字符串的展现

  ![img](../image2/29.C语言中字符串的展现.jpg?lastModify=1761057805)

  Redis没有直接复用C语言的字符串，而是新建了属于自己的结构-----SDS 在Redis数据库里，包含字符串值的键值对都是由SDS实现的(Redis中所有的键都是由字符串对象实现的即底层是由SDS实现，Redis中所有的值对象中包含的字符串对象底层也是由SDS实现)。

  ![img](../image2/30.jpg?lastModify=1761057805)

- SDS简单动态字符串

  - sds.h源码分析

    ![img](../image2/31.sds.h.jpg?lastModify=1761057805)

  - 说明

    ![img](../image2/32说明.jpg?lastModify=1761057805)

    * Redis中字符串的实现,SDS有多种结构( sds.h) : sdshdr5、(2^5=32byte)，但是不会使用，是redis团队内部测试使用 sdshdr8、(2^8=256byte) sdshdr16、(2^16=65536byte=64KB) sdshdr32、(2 ^32byte=4GB) sdshdr64，2的64次方byte=17179869184G用于存储不同的长度的字符串。

    * len表示SDS的长度，使我们在获取字符串长度的时候可以在o(1)情况下拿到，而不是像C那样需要遍历一遍字符串。

    * alloc可以用来计算 free就是字符串已经分配的未使用的空间，有了这个值就可以引入预分配空间的算法了，而不用去考虑内存分配的问题。

    * buf表示字符串数组，真实存数据的。

  - 官网

- Redis为什么要重新设计一个SDS数据结构？

  ![img](../image2/33.redis字符串展示.jpg?lastModify=1761057805)

  |                | C语言                                                        | SDS                                                          |
  | -------------- | ------------------------------------------------------------ | ------------------------------------------------------------ |
  | 字符串长度处理 | 需要从头开始遍历,直到遇到'\O'为止，时间复杂度O(N)            | 记录当前字符串的长度，直接读取即可，时间复杂度O(1)           |
  | 内存重新分配   | 分配内存空间超过后，会导致数组下标越级或者内存分配溢出       | 空间预分配 SDS修改后，len长度小于1M，那么将会额外分配与 len相同长度的未使用空间。如果修改后长度大于1M，那么将分配1M的使用空间。 惰性空间释放 有空间分配对应的就有空间释放。SDS缩短时并不会回收多余的内存空间,而是使用free字段将多出来的空间记录下来。如果后续有变更操作，直接使用free中记录的空间，减少了内存的分配。 |
  | 二进制安全     | 二进制数据并不是规则的字符串格式，可能会包含一些特殊的字符，比如 '\0'等。前面提到过，C中字符串遇到'\0'会结束,那'\0'之后的数据就读取不上了 | 根据len长度来判断字符串结束的，二进制安全的问题就解决了      |

- 源码分析

  用户API

  set k1 v1 底层发生了什么？调用关系？

  ![img](../image2/34.底层调用关系.png?lastModify=1761057805)

- 3大物理编码方式

  ![img](../image2/35.3大物理编码方式.png?lastModify=1761057805)

  **INT编码格式**

  命令示例: set k1 123

  当字符串键值的内容可以用一个64位有符号整形来表示时，Redis会将键值转化为long型来进行存储，此时即对应 OB_ENCODING_INT 编码类型。内部的内存结构表示如下:

  ![img](../image2/36.INT类型.png?lastModify=1761057805)

  * Redis启动时会预先建立10000个分别存储0~9999的redisObject变量作为共享对象，这意味着如果set字符串的键值在0~10000之间的话，则可以直接指向共享对象而不需要再建立新对象，此时键值不占用空间(享元模式)。比如 set k1 123    set key2 123

    ![image-20251021231203124](../image2/image-20251021231203124.png)

  * 源码

    ![image-20251021231350042](../image2/image-20251021231350042.png)

  **EMBSTR编码格式**

  ![img](../image2/37.EMBSTR编码格式.png?lastModify=1761057805)

  对于长度小于 44的字符串，Redis 对键值采用OBJ_ENCODING_EMBSTR 方式，EMBSTR 顾名思义即: embedded string，表示嵌入式的String。从内存结构上来讲 即字符串 sds结构体与其对应的 redisObject 对象分配在同一块连续的内存空间，字符串sds嵌入在redisObiect对象之中一样。

**RAW编码格式**

当字符串的键值为长度大于44的超长字符串时，Redis 则会将键值的内部编码方式改为OBJ_ENCODING_RAW格式，这与OBJ_ENCODING_EMBSTR编码方式的不同之处在于，此时动态字符sds的内存与其依赖的redisobiect的内存不再连续了

![image-20251021231551251](../image2/image-20251021231551251.png)

- 明明没有超过阈值，为什么变成raw了

  ![img](../image2/38.raw.png)

##### 11.6.5.3.3 案例结论

只有整数才会使用int,如果是浮点数, Redis内部其实先将浮点数转化为字符串值,然后再保存

embstr与raw类型底层的数据结构其实都是SDS(简单动态字符串，Redis内部定义sdshdr一种结构)

| int    | Long类型整数时，RedisObiect中的ptr指针直接赋值为整数数据，不再额外的指针再指向整数了，节省了指针的空间开销。 |
| ------ | ------------------------------------------------------------ |
| embstr | 当保存的是字符串数据且字符串小于等于44字节时，emstr类型将会调用内存分配函数，只分配一块连续的内存空间，空间中依次包含 redisObject 与 sdshdr 两个数据结构，让元数据、指针和SDS是一块连续的内存区域，这样就可以避免内存碎片 |
| raw    | 当字符串大于44字节时，SDS的数据量变多变大了，SDS和RedisObject布局分家各自过，会给SDS分配多的空间并用指针指SDS结构，raw 类型将会调用两次内存分配函数，分配两块内存空间，一块用于包含 redisObject结构，而另一块用于包含sdshdr 结构 |

![img](../image2/39.string总结.png?lastModify=1761057805)

总结：Redis内部会根据用户给的不同键值而使用不同的编码格式，自适应地选择较优化的内部编码格式，而这一切对用户完全透明。

#### 11.6.5.4 Hash数据结构介绍

##### 11.6.5.4.1 Hash的两种编码格式

* Redis6以前：ziplist、Hashtable

* Redis7：listpack、Hashtable

##### 11.6.5.4.2 Redis6

```shell
#使用命令查看
config get hash*

#设置对象
hset user01 name z3

#查看对象编码
object encoding user01

#设置hash对象使用ziplist最大的属性个数
config set hash-max-ziplist-entries 3

#设置hash对象使用ziplist最大的值长度
config set hash-max-ziplist-value 8

#设置的hash任意超过上面两个值，就会变成hashtable
```

hash-max-ziplist-entries：使用压缩列表保存时哈希集合中的最大元素个数。

hash-max-ziplist-value：使用压缩列表保存时哈希集合中单个元素的最人长度。

Hash类型键的字段个数 <font color = 'red'>小于 </font>hashh-max-ziplist-entries 并且每个字段名和字段值的长度 <font color = 'red'>小于 </font>hash-max-ziplist-value 时，Redis才会使用 OBJ_ENCODING_ZIPLIST来存该键，前述条件任意一个不满足则会转换为 OBJ_ENCODING_HT的编码方式

![](../image2/40.Hashtable演示一(redis6).png)

![](../image2/41.Hashtable演示二(redis6).png)

**结论**

1. 哈希对象保存的键值对数量小于 512个；
2. 所有的键值对的健和值的字符串长度都小于等于 64byte (一个英文字母一个字节)时用ziplist，反之用hashtable；
3. ziplist升级到hashtable可以，反过来降级不可以；即一旦从压缩列表转为了哈希表，Hash类型就会一直用哈希表进行保存而不会再转回压缩列表了。在节省内存空间方面哈希表就没有压缩列表高效了。

**源码分析**

* t_hash.c

  * 在redis中，hashtable被称为字典（dictionary），它时一个数组+链表结构

  * OBJ_ENCODING_HT编码分析。

    ![image-20251021233415110](../image2/image-20251021233415110.png)

    ![image-20251021233541219](../image2/image-20251021233541219.png)

    ![image-20251021233615514](../image2/image-20251021233615514.png)

    * 每个键值对都会有一个dictEntry

  * haset命令解读

    ![image-20251021233749515](../image2/image-20251021233749515.png)

    * 类型

      ![image-20251021233830425](../image2/image-20251021233830425.png)

* ziplist.c

  ![image-20251021233926108](../image2/image-20251021233926108.png)

  * ziplist，什么样

    ![image-20251021234222923](../image2/image-20251021234222923.png)

    ![image-20251021234311433](../image2/image-20251021234311433.png)

    * ziplist各个组成单元什么意思

      ![image-20251021234502289](../image2/image-20251021234502289.png)

  * zlentry，压缩列表节点构成

    * 官网源码

      ![image-20251021234741706](../image2/image-20251021234741706.png)

    * zlentry实体结构解析

      ![image-20251021234823770](../image2/image-20251021234823770.png)
  
      ![image-20251022204457752](../image2/image-20251022204457752.png)
  
      * prevlen 记录了前一个节点的长度
    * encoding 记录了当前节点实际数据的类型以及长度
      
      * data 记录了当前节点的实际数据
  
  * ziplist存取情况
  
    * zlentry解析
  
      * 压缩列表zlentry节点结构：每个zlentry由前一个节点的长度、encoding和entry-data三部分组成
  
        ![image-20251022204909554](../image2/image-20251022204909554.png)
  
      * 当前节点：（前节点占用的内存字节数）表示前1个zlentry的长度，privious_entry_length有两种取值情况：1字节或5字节。取1字节时，表示上一个entry的长度小于254字节。虽然1字节的值能表示的数字范围时0到255，但是压缩列表中的zlend的取值默认时255，因此，就默认用255表示整个压缩列表的结束，其他表示长度的地方就不能再用255这个值了。所以，当上一个entry长度小于254字节时，prev_len取值为1字节，否则就取值为5字节。记录长度的好处：占用内存小，1或者5字节。
  
      * encoding：记录节点的content保留数据的类型和长度
  
      * content：保存实际数据内容
  
        ![image-20251022213006215](../image2/image-20251022213006215.png)
  
    * 为什么zlentry这样设计？数组和链表数据结构对比
  
      privious_entry_length，encoding长度都可以根据编码方式推算，真正变化的时content，而content长度记录在encoding里，因此entry的长度就知道了。entry总长度=privious_entry_length字节数+encoding字节数+content字节数。
  
      ![image-20251022213520111](../image2/image-20251022213520111.png)
  
      为什么entry这么设计？记录前一个节点的长度？
  
      链表在内存中，一般时不连续的，遍历相对比较满，而ziplist可以很好的解决这个问题。如果知道了当前的其实地址，因为entry是连续的，entry后一定是另一个entry，想知道下一个entry的地址，☞要将当前的其实地址加上当前entry总长度。如果还想便利下一个entry，只要继续同样的操作。
  
  * 明明有链表了，为什么出来一个压缩链表？
  
    * 普通的双向链表会有两个指针，在存储数据很小的情况下，我们存储的实际数据的大小可能还没有指针占用的内存大，得不偿失。ziplist是一个特殊的双向链表没有维护双向指针：previous next；而是存储上一个entry的长度和当前entry的长度，通过长度推算下一个元素在什么地方。牺牲读取的性能，获取高校的存储空间，因为（简短字符串的情况）存储值指针比存储entry长度更费内存。这是典型的”时间换空间“。
    * 链表在内存中一般是不连续的，遍历相对比较慢，而ziplist可以很好的解决这个问题，普通数组的遍历时根据数组里存储的数据类型找到下一个元素的（例如int类型的数组访问下一个元素时每次只需要移动一个sizeof(int)就行），但是ziplist的每个节点的长度时可以不一样的，而我们面对不同长度的节点有不可能直接sizeof(entry)，所以ziplist只好将一些必要的偏移量信息记录在每一个节点里，使之能跳到上一个节点或下一个几点。备注：sizeof实际上是获取了数据在内存中所占用的存储空间，以字节为单位来计数。
    * 头节点里有头节点同时还有一个参数len，和String类型提到的SDS类型，这里是用来记录链表长度的。因此获取链表长度时不用再便利整个链表，直接拿len值就可以了，这个时间复杂度时O(1)。
  
  * ziplist总结
  
    * ziplist为了节省内存，采用了紧凑的连续存储
    * ziplist是一个双向链表，可以在时间复杂度为O(1)下从头部、尾部进行pop和push。
    * 新增或更新元素可能会出现连锁更新现象（致命缺点导致被listpack替换）。
    * 不能保存过多的元素，否则查询效率会降低，数量小和内容小的情况下可以使用。

##### 11.6.5.4.3 Redis7

**案例**

* hash-max-listpack-entries：使用压缩列表保存时哈希集合中的最大元素个数。
* hash-max-listpack-value：使用压缩列表保存时哈希集合中单个元素的最人长度。
* Hash类型键的字段个数 <font color = 'red'>小于 </font> hash-max-listpack-entries且每个字段名和字段值的长度 <font color = 'red'>小于 </font> hash-max-listpack-value 时，Redis才会使用OBJ_ENCODING_LISTPACK来存储该键，前述条件任意一个不满足则会转换为 OBI_ENCODING_HT（HT酒店hashtable简写）的编码方式

![](../image2/42.Hashtable演示一(redis7).png)

![](../image2/43.Hashtable演示二(redis7).png)

![](../image2/44.Hashtable演示三(redis7).png)

**结构**

hash-max-listpack-entries：使用紧凑列表保存时哈希集合中的最大元素个数。

hash-max-listpack-value：使用紧凑列表保存时哈希集合中单个元素的最人长度。

**结论**

1. 哈希对象保存的键值对数量小于 512个；
2. 所有的键值对的健和值的字符串长度都小于等于 64byte (一个英文字母一个字节时用listpack，反之用hashtable
3. listpack升级到Hashtable可以，反过来降级不可以

**源码分析**

* 源码说明

  * 实现：object.c

    ![image-20251022220229691](../image2/image-20251022220229691.png)

  * 实现：listpack.c

    ![image-20251022220302861](../image2/image-20251022220302861.png)

    ![image-20251022220336617](../image2/image-20251022220336617.png)

  * 实现2：object.c

    ![image-20251022220423700](../image2/image-20251022220423700.png)

* 明明有ziplist了，为什么出来一个listpack紧凑链表

  * ziplist的连锁更新问题

    ![image-20251022220620784](../image2/image-20251022220620784.png)

    ![image-20251022220759976](../image2/image-20251022220759976.png)

    ![image-20251022220821612](../image2/image-20251022220821612.png)

    ![image-20251022220839800](../image2/image-20251022220839800.png)

  * 结论

    * listpack是Redis设计用来取代掉ziplist的数据结构，它通过每个节点记录自己的长度且放在节点的尾部，来彻底解决掉ziplist存在的连锁更新的问题。

* listpack结构

  ![image-20251022221213520](../image2/image-20251022221213520.png)

  * 官网

    * https://github.com/antirez/listpack/blob/master/listpack.md

  * listpack由4部分组成：total Bytes、Num Elen、Entry以及End

    ![image-20251022221444378](../image2/image-20251022221444378.png)

    ![image-20251022221510955](../image2/image-20251022221510955.png)

  * entry结构

    ![image-20251022221854665](../image2/image-20251022221854665.png)

    * 当前元素的编码类型(entry-encoding)

    * 元素数据(entry-data)

    * 以及编码类型和元素数据这两部分的长度(entry-len)

    * listpackEntry结构定义：listpack.h

      ![image-20251022221938935](../image2/image-20251022221938935.png)

* ziplist内存布局 VS listpack内存布局

  ![image-20251022222041658](../image2/image-20251022222041658.png)

  ![image-20251022222121224](../image2/image-20251022222121224.png)

#### 11.6.5.5 List数据结构介绍

严格意义来讲Redis7中List是quicklist+listpack。粗分可以说Redis7中List是quicklist。

##### 11.6.5.5.1 reids6

**案例**

* ![image-20251023215837339](../image2/image-20251023215837339.png)
* ziplist压缩配置：list-compress-depth 0；表示一个quicklist两端不被压缩的节点数。这里的节点是指quicklist双向链表的节点，而不是指ziplist里面的数据项个数。参数list-compress-depth的取值含义如下：
  * 0：是个特殊值，表示都不压缩。这是Redis的默认值。
  * 1：表示quicklist两端各有1个节点不压缩，中间的节点压缩。
  * 2：表示quicklist两端各有2个节点不压缩，中间的节点压缩。
  * 3：表示quicklist两端各有3个节点不压缩，中间的节点压缩。
  * 依此类推
* ziplist中entry配置：list-max-ziplist-size 2；
  * 当取正值的时候，表示按照数据项个数来限定每个quicklist节点上的ziplist长度。比如，当这个参数配置成5的时候，表示每个quicklist节点的ziplist最多包含5个数据项。当取值为负值的时候，表示按照占用字节数来限定每个quicklist节点上的ziplist长度。这时，它只能取-1到-5这5个值，每个值含义如下：
  * -5：每个quicklist节点上的ziplist大小不能超过64kb。（注：1kb=1024bytes）
  * -4：每个quicklist节点上的ziplist大小不能超过32kb。
  * -3：每个quicklist节点上的ziplist大小不能超过16kb。
  * -2：每个quicklist节点上的ziplist大小不能超过8kb。（注：-2是Redis给出的默认值）
  * -1：每个quicklist节点上的ziplist大小不能超过4kb。

**Redis6版本前的List的一种编码格式**

* list用quicklist来存储，quicklist存储了一个双向链表，每个节点都是一个ziplist

  ![image-20251023221107784](../image2/image-20251023221107784.png)

  ![image-20251023221126681](../image2/image-20251023221126681.png)

**quicklist总纲**

* quicklist实际上是ziplist和linedlist的混合体，它将linkedlist按段切分，每一段使用ziplist来紧凑存储，多个ziplist之间使用双向指针串接起来

  ![image-20251023221824509](../image2/image-20251023221824509.png)

* 是ziplist和linkendlist的结合体

**源码分析**

* quicklist.h，head和tail只想双向列表的表头和表尾

  * quicklist结构

    ![image-20251023222113838](../image2/image-20251023222113838.png)

  * quicklistNode结构

    ![image-20251023222201929](../image2/image-20251023222201929.png)

* quicklistNode中的*zl指向一个ziplist，一个ziplist可以存放多个元素

  ![image-20251023222232704](../image2/image-20251023222232704.png)

##### 11.6.5.5.2 reids7

**案例**

![image-20251023222452683](../image2/image-20251023222452683.png)

**源码说明**

* 实现：t_list.c

  ![image-20251023222614781](../image2/image-20251023222614781.png)

* 实现：object.c

  ![image-20251023222637555](../image2/image-20251023222637555.png)

**Reids7的List的一种编码格式**

* list用quicklist来存储，quicklist存储了一个双向链表，每个节点都是一个listpack
* quicklist
  * 是listpack和linkedlist的结合体

#### 11.6.5.6 Set数据结构介绍

**案例**

* set-proc-title 修改进程标题以及显示一些运行时信息，不是set相关内容的配置

  ![](../image2/48.set类型.png)

**Set的两种编码格式**

* intset

* hashtable

Redis用intset或hashtable存储set。如果元素都是整数类型，就用intset存储。

如果不是整数类型，就用hashtable(数组+链表的存来储结构)。key就是元素的值，value为null。

**源码分析**

* t_set.c

![image-20251023223503102](../image2/image-20251023223503102.png)

![image-20251023223541523](../image2/image-20251023223541523.png)

#### 11.6.5.7 Zset数据结构介绍

**案例**

* redis6

  当有序集合中包含的元素数量超过服务器属性 server.zset_max_ziplist_entries 的值(默认值为 128 )，或者有序集合中新添加元素的 member 的长度大于服务器属性 server.zset_max_ziplist_value 的值(默认值为 64 )时，redis会使用跳跃表作为有序集合的底层实现。
  否则会使用ziplist作为有序集合的底层实现

  ![](../image2/49.zset(redis6)1.png)

  ![](../image2/50.zset(redis6)2.png)

* redis7

  ![](../image2/51.zset(redis7)1.png)

**ZSet的两种编码格式**

* redis6
  * ziplist
  * skiplist
* redis7
  * listpack
  * skiplist

**redis6源码分析**

* t_zset.c

  ![image-20251023224538682](../image2/image-20251023224538682.png)

  ![image-20251023224605160](../image2/image-20251023224605160.png)

**redis7源码分析**

* t_zset.c

  ![image-20251023224623137](../image2/image-20251023224623137.png)

### 11.6.6 小总结

#### 11.6.6.1 redis6类型-物理编码-对应表

![image-20251023224856122](../image2/image-20251023224856122.png)

![image-20251023224937231](../image2/image-20251023224937231.png)

#### 11.6.6.1 redis6数据类型对应的底层数据结构

1.字符串

int：8个字节的长整型。
embstr：小于等于44个字节的字符串。
raw：大于44个字节的字符串。
Redis会根据当前值的类型和长度决定使用哪种内部编码实现。

2.哈希
ziplist(压缩列表)：当哈希类型元素个数小于hash-max-ziplist-entries 配置(默认512)、同时所有值都小于hash-max-ziplist-value配置(默认64 字节)时，Redis会使用ziplist作为哈希的内部实现，ziplist使用更加紧凑的 结构实现多个元素的连续存储，所以在节省内存方面比hashtable更加优秀。

hashtable(哈希表):当哈希类型无法满足 ziplist的条件时，Redis会 用hashtable为哈希的内部实现。因为此时ziplist的读写效率会下降，而hashtahe的读写时间复度为O(1)。

3.列表
ziplist(压列表)：当列表的元素个数小于list-max-ziplist-entries配置 默认512)，同时列表中每元素的值都小于list-max-ziplist-value配置时(默认64字节)Redis会选用ziplist来作为列表的内部实现来减少内存的使用。
inkedlist(链表):当列表类型无法满足ziplist的条件时，Redis会使用 linkedlist作为列表的内部实现。quicklist ziplist和linkedlist的结合以ziplist为节点的链表。

4.集合
intset(需数集合):当集合中的元素都是整数且元素个数小于set-max-ntset-entries置(默认512)时，Redis会用intset来为集合的内部实现，从而减少内存的使用hashtable(哈希表):当集合类型无法满足intset的条件时，Redis会使用hashtable作为集合的内部实现。

5.有序集合
ziplist(压缩列表):当有序集合的元素个数小于zset-max-ziplist- entres配置(默认128)，同时每元素的值都小于et-max-ziplist-value配 置(默认64字节)时Redis会用ziplist来作为有序集合的内部实现，ziplist 可以有效减少内存的使用。skiplist(跳跃表)：当ziplist条件不满足时，有序集合会使用skiplist作 为内部实现，因为此时ziplist的读写效率会下降。

#### 11.6.6.2 redis6数据类型以及数据结构的关系

![](../image2/52.Redis6数据类型和数据结构关系.png)

#### 11.6.6.3 redis7数据类型以及数据结构的关系

![](../image2/53.Redis7数据类型和数据结构关系.png)

#### 11.6.6.4 redis数据类型以及数据结构的时间复杂度

![](../image2/54.时间复杂度.png)

## 11.7 skiplist调表面试题

java中有ConcurrentSkipListMap<K,V>

### 11.7.1 为什么引出跳表

- 先从一个单链表来说
  对于一个单链表来讲，<font color = 'blue'>即便链表中存储的数据是有序的</font>，如果我们要想在其中查找某个数据，也只能从头到尾遍历链表。这样查找效率就会很低，时间复杂度会很高O(N)

  ![](../image2/55.单链表.png)

- 痛点

  ![](../image2/56.单链表.png)

  解决方法：升维，也即空间换时间

- 优化

  ![](../image2/57.索引优化.png)

- 案例：画一个包含64个节点的链表，按前面思路，建立五级索引

  ![](../image2/58.索引案例.png)

###  11.7.2 是什么

* 跳表是可以实现二分查找的有序链表
  * <font color = 'red'>skiplist是一种以空间换取时间的结构</font>。
  * 由于链表，无法进行二分查找，因此借鉴数据库索引的思想，提取出链表中关键节点(索引)，先在关键节点上查找，再进入下层链表查找，提取多层关键节点，就形成了跳跃表。
  * 但是，<font color = 'red'>由于索引也要占据一定空间的，所以，索引添加的越多，空间占用的越多</font>

* 总结来说，跳表=链表+多级索引

### 11.7.3 <font color = 'red'>跳表时间和空间复杂度介绍</font>

1. 跳表的时间复杂度，O(logN)

   <font color = 'blue'>跳表查询的时间复杂度分析，如果链表里有N个结点，会有多少级索引呢？</font>

   按照我们前面笔记，两两取首。每两个结点会抽出一个结点作为上一级索引的结点，以此估算:

   第一级索引的结点个数大约就是n/2

   第二级索引的结点个数大约就是n/4.

   第三级索引的结点个数大约就是n/8，依次类推......

   也就是说，第k级索引的结点个数是第k-1级索引的结点个数的1/2，那第k级索引结点的个数就是n/(2^k)

   假设索引有h级，最高级的索引有2个结点。通过上面的公式，

   我们可以得到n/(2^h)=2，从而求得h=log2n-1(log以2为底，n的对数)

   如果包含原始链表这一层，整个跳表的高度就是log2n(log以2为底，n的对数)

   ![](../image2/59.跳表时间复杂度.png)

2. 跳表空间复杂度O(n)

   <font color = 'blue'>跳表查询的空间复杂度分析</font>

   比起单纯的单链表，跳表需要存储多级索引，肯定要消耗更多的存储空间。那到底需要消耗多少额外的存储空间呢?

   我们来分析一下跳表的空间复杂度。

   <font color = 'red'>第一步：</font>首先原始链表长度为n，

   <font color = 'red'>第二步：</font>两两取首，每层索引的结点数: n/2,n/4,n/8 ...,8,4,2 每上升一级就减少一半，直到剩下2个结点,以此类推，如果我们把每层索引的结点数写出来，就是一个等比数列。

   ![](../image2/60.跳表.png)

   这几级索引的结点总和就是n/2+n/4+n/8...+8+4+2=n-2。所以，跳表的空间复杂度是O(n)。也就是说，如果将包含n个结点的单链表构造成跳表，我们需要额外再用接近n个结点的存储空间。
   
   
   
   <font color = 'red'>第三步：</font>三三取首，每层索引的结点数: n/3,n/9,n/27 ...,9,3,1 每上升一级，索引节点个数都除以3。为了方便计算，我们假设最高一级的索引节点个数是1。如果我们把每层索引的结点数写出来，也是一个等比数列。
   
   ![image-20251023230028134](../image2/image-20251023230028134.png)
   
   通过等比数列求和公式，总的索引节点大约是n/3+n/9+n/27...+9+3+1=n/2。尽管空间复杂度还是O(n)，但比上面的每两个节点抽一个几点的索引构建方法，要减少了一般的索引节点存储空间。
   
   所以空间复杂度是O(n);

### 11.7.4 优缺点

<font color = 'blue'>优点：</font>

跳表是一个最典型的空间换时间解决方案，而且只有在<font color = 'red'>数据量较大的情况下</font>才能体现出来优势。而且应该是<font color = 'red'>读多写少的情况下</font>才能使用，所以它的适用范围应该还是比较有限的

<font color = 'blue'>缺点：</font>

维护成本相对要高，
在单链表中，一旦定位好要插入的位置，插入结点的时间复杂度是很低的，就是O(1)

but

新增或者删除时需要把所有索引都更新一遍，为了保证原始链表中数据的有序性，我们需要先找到要动作的位置，这个查找操作就会比较耗时最后在新增和删除的过程中的更新，时间复杂度也是o(log n)









