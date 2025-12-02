# 第10章-Redis的缓存过期淘汰策略
## 10.1 总结

* 生产上你们redis内存设置多少
* 如何配置、修改redis的内存大小
* 如果内存满了怎么办
* redis清理内存的方式？定期删除和惰性删除了解过吗？
* redis缓存淘汰策略有哪些？分别是什么？用过那个？
* redis的LRU了解过吗？请手写LRU(尚硅谷大厂面试题第3季 65个视频)
* LRU和LFU算法的区别是什么？
  * LRU means Least Recently Used 最近最少使用
  * LFU means Least Frequently Used 最不常用

## 10.2 如果内存满了怎么办

redis默认内存是多少？在哪查看？如何设置修改？

- **查看Redis最大占用内存**

  ![](../image2/1.Redis最大内存占用配置.png)

  打开redis配置文件，设置maxmemory参数，maxmemory是bytes字节类型，注意类型转换。

  通过命令查看默认的内存大小

  ![](../image2/2.默认内存大小.png)

  默认内存大小为0吗？那我们之前的数据是怎么保存进去的？

- **redis默认内存多少可用？**

  如果不设置最大内存或者设置最大内存大小为0，<font color = 'red'>在64位操作系统下不限制内存大小</font>，在32位操作系统下最多使用3GB内存

  注意：在64bit系统下，maxmemory设置为0表示不限制redis内存使用

- **一般生产上你如何配置**

  一般推荐Redis设置内存为最大物理内存的3/4

- **如何修改redis内存设置**

  通过修改文件配置

  ![](../image2/3.通过修改配置文件.png)

  通过命令修改，但是redis重启后会失效

  ![](../image2/4.通过命令修改.png)
  ![image-20251016204316353](../image2/image-20251016204316353.png)

- 什么命令查看redis内存使用情况

  info memory

  config get maxmemory

内存打满了，超出了设置的最大值会怎么样？

![](../image2/5.超出内存.png)

结论：

设置了maxmemory的选项，假如redis内存使用达到了上限，没有加上过期时间就会导致数据写满maxmemory，为了避免类似情况，需要使用内存淘汰策略

## 10.3 王redis里写的数据时怎么没了的？它如何删除的？


### 10.3.1 redis过期键的删除策略

* 如果一个键是过期的，那它到了过期时间之后是不是马上就从内存中被删除了呢？肯定不是

* 如果不是，那过期后到底什么时候被删除呢？是什么操作？

### 10.3.2 redis三种删除策略

1. #### 立即删除/定时删除

   立即删除能保证内存中数据的最大新鲜度，因为它保证过期键值会在过期后马上被删除，其所占用的内存也会随之释放。但是立即删除对cpu是最不友好的。因为删除操作会占用cpu的时间，如果刚好碰上了cpu很忙的时候，比如正在做交集或排序等计算的时候，就会给cpu造成额外的压力，让CPU心累，时时需要删除，忙死。

   <font color = 'red'>这会产生大量的性能消耗，同时也会影响数据的读取操作</font>

   **总结：**对CPU不友好，用处理器性能换取存储空间（拿时间换空间）

2. #### 惰性删除

   数据到达过期时间，不做处理。等下次访问该数据时，如果未过期，返回数据 ;发现已过期，删除，返回不存在。

   <font color = 'red'>惰性删除策略的缺点是，它对内存是最不友好的。</font>

   如果一个键已经过期，而这个键又仍然保留在redis中，那么只要这个过期键不被访问，它所占用的内存就不会释放。在使用惰性删除策略时，如果数据库中有非常多的过期键，而这些过期键又恰好没有被访问到的话，那么它们也许永远也不会被删除(除非用户手动执行FLUSHDB)，我们甚至可以将这种情况看作是一种内存泄漏  - 无用的垃圾数据占用了大量的内存。而服务器却不会自己去释放它们，这对于运行状态非常依赖于内存的Redis服务器来说,肯定不是一个好消息

   **总结：**对内存不友好，用存储空间换取处理器性能（拿空间换时间），开启惰性删除淘汰，lazyfree-lazy-eviction=yes

   ```
   lazyfree-lazy-eviction：表示当Redis运行内存超过maxmemory时，释放开启lazy free机制删除。
   lazyfree-lazy-expire：表示设置了过期时间的key/value，当过期之后释放开启lazy free机制删除
   lazyfree-lazy-server-del：有些指令在处理已存在的key时，会
   带有一个隐式的del键的操作，比如rename命令，当目标key已存储，redis会先删除目标key, 如果这些目标key是一个big key，就会造成阻塞删除的问题，此配置表示在这种场景中是否开启lazy free机制删除。
   slave-lazy-flush：针对slave从节点进行全量数据同步，slave在加载master的RDB文件之前，会运行flushall来清理自己的数据，他表示此时是否开启lazy free机制删除。
   
   建议开其中的lazyfree-lazy-eviction、lazyfree-lazy-expire、lazyfree-lazy-server-del 等配置，这样就可以有效提高主线程的执行效率。
   ```

   

3. #### 上面两种方案都走极端

   * 定期抽样key，判断是否过期
   * 有漏网之鱼

   <font color = 'red'>**定期删除**策略是前两种策略的折中：</font>
   定期删除策略<font color = 'red'>每隔一段时间执行一次删除过期键操作</font>并通过限制删除操作执行时长和频率来减少删除操作对CPU时间的影响。

   周期性轮询redis库中的时效性数据，采用随机抽取的策略，利用过期数据占比的方式控制删除频度

   特点1：CPU性能占用设置有峰值，检测频度可自定义设置

   特点2：内存压力不是很大，长期占用内存的冷数据会被持续清理

   总结：周期性抽查存储空间 (随机抽查，重点抽查)
   **举例:**
   redis默认每隔100ms检查是否有过期的key，有过期key则删除。注意: redis不是每隔100ms将所有的key检查一次而是随机抽取进行检查<font color = 'red'>(如果每隔100ms.全部key进行检查，redis直接进去ICU)</font>。因此，如果只采用定期删除策略，会导致很多key到时间没有删除。

   定期删除策略的难点是确定删除操作执行的时长和频率：如果删除操作执行得太频繁或者执行的时间太长，定期删除策略就会退化成立即删除策略，以至于将CPU时间过多地消耗在删除过期键上面。如果删除操作执行得太少，或者执行的时间太短，定期删除策略又会和惰性删除束略一样，出现浪费内存的情况。因此，如果采用定期删除策略的话，服务器必须根据情况，合理地设置删除操作的执行时长和执行频率。

### 10.3.3 上述步骤都过堂了，还有漏洞吗？

1 定期删除时，从来没有被抽查到

2 惰性删除时，也从来没有被点中使用过

上述两个步骤 ======> 大量过期的key堆积在内存中，导致redis内存空间紧张或者很快耗尽
<font color = 'blue'>必须要有一个更好的兜底方案......</font>

## 10.4 redis缓存淘汰策略

### 10.4.1 redis配置文件

在MEMORY MANAGEMENT中配置maxmemory-policy配置项

![](../image2/6.缓存淘汰策略配置.png)

### 10.4.2 LRU和LFU算法的区别是什么

LRU：最近<font color = 'red'>最少使用</font>页面置换算法，淘汰最长时间未被使用的页面，看页面最后一次被使用到发生调度的时间长短，首先淘汰最长时间未被使用的页面。（LRU基于数据最近被访问的时间（时间维度），优先淘汰最久未使用的数据）

```java
public class LRUCache<K, V> extends LinkedHashMap<K, V> {

    // 缓存最大容量
    private final int maxCapacity;

    // 构造函数：accessOrder=true 表示“按访问顺序排序”（核心）
    public LRUCache(int maxCapacity) {

        super(maxCapacity, 0.75f, true);
        this.maxCapacity = maxCapacity;
    }

    // 核心：当缓存大小超过maxCapacity时，自动删除“最老的 entry”（最近最少使用的）
    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {

        return size() > maxCapacity;
    }

    // 测试
    public static void main(String[] args) {

        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println(cache); // 输出：{1=A, 2=B, 3=C}（插入顺序）

        cache.get(1); // 访问1，1变成最近使用
        System.out.println(cache); // 输出：{2=B, 3=C, 1=A}（按访问顺序排序）

        cache.put(4, "D"); // 缓存满了，淘汰最老的2
        System.out.println(cache); // 输出：{3=C, 1=A, 4=D}
    }
}
```

LFU：最近<font color = 'red'>最不常用</font>页面置换算法，淘汰一定时期内被访问次数最少的页面，看一定时间段内页面被使用的频率，淘汰一定时期内被访问次数最少的页。（LFU基于数据被访问的频率（频率维度），优先淘汰访问次数最少的数据。‌‌）

<font color = 'blue'>举个栗子</font>

某次时期Time为10分钟，如果每分钟进行一次调页，主存块为3（即只能保存3个页面），若所需页面走向为2 1 2 1 2 3 4，假设到页面4时会发生缺页中断（装不下导致的缓存淘汰）

若按LRU算法，应换页面1(1页面最久未被使用)，但按LFU算法应换页面3(十分钟内,页面3只使用了一次)

可见LRU关键是看页面最后一次被使用到发生调度的时间长短，而LFU关键是看一定时间段内页面被使用的频率

```java
import java.util.*;

public class LFUCache<K, V> {
   
    // 缓存节点：存储key、value、使用频率、最后使用时间（处理频率相同时的淘汰）
    static class Node<K, V> {
   
        K key;
        V value;
        int freq; // 使用频率
        long lastUseTime; // 最后使用时间（毫秒）

        public Node(K key, V value) {
   
            this.key = key;
            this.value = value;
            this.freq = 1; // 初始频率1
            this.lastUseTime = System.currentTimeMillis();
        }
    }

    private final int maxCapacity;
    private final Map<K, Node<K, V>> cache; // key→Node
    private final Map<Integer, LinkedHashSet<K>> freqMap; // 频率→key集合（LinkedHashSet保证顺序）
    private int minFreq; // 当前最小频率（快速定位要淘汰的key）

    public LFUCache(int maxCapacity) {
   
        this.maxCapacity = maxCapacity;
        this.cache = new HashMap<>();
        this.freqMap = new HashMap<>();
        this.minFreq = 1;
    }

    // 1. 获取缓存：命中则更新频率和最后使用时间
    public V get(K key) {
   
        Node<K, V> node = cache.get(key);
        if (node == null) {
   
            return null;
        }
        // 更新节点频率
        updateNodeFreq(node);
        return node.value;
    }

    // 2. 存入缓存：不存在则新增，存在则更新；满了则淘汰最小频率的key
    public void put(K key, V value) {
   
        if (maxCapacity <= 0) {
   
            return;
        }

        Node<K, V> node = cache.get(key);
        if (node != null) {
   
            // 存在：更新value、频率、最后使用时间
            node.value = value;
            node.lastUseTime = System.currentTimeMillis();
            updateNodeFreq(node);
        } else {
   
            // 不存在：检查缓存是否满
            if (cache.size() >= maxCapacity) {
   
                // 淘汰最小频率的key（频率相同则淘汰最早使用的）
                evictMinFreqKey();
            }
            // 新增节点
            Node<K, V> newNode = new Node<>(key, value);
            cache.put(key, newNode);
            // 加入freqMap：频率1的集合
            freqMap.computeIfAbsent(1, k -> new LinkedHashSet<>()).add(key);
            // 新节点频率是1，minFreq重置为1
            minFreq = 1;
        }
    }

    // 辅助：更新节点频率
    private void updateNodeFreq(Node<K, V> node) {
   
        K key = node.key;
        int oldFreq = node.freq;
        int newFreq = oldFreq + 1;

        // 1. 从旧频率的集合中移除key
        LinkedHashSet<K> oldFreqSet = freqMap.get(oldFreq);
        oldFreqSet.remove(key);
        // 如果旧频率是minFreq，且集合为空，minFreq+1
        if (oldFreq == minFreq && oldFreqSet.isEmpty()) {
   
            minFreq = newFreq;
        }

        // 2. 加入新频率的集合
        freqMap.computeIfAbsent(newFreq, k -> new LinkedHashSet<>()).add(key);

        // 3. 更新节点的频率和最后使用时间
        node.freq = newFreq;
        node.lastUseTime = System.currentTimeMillis();
    }

    // 辅助：淘汰最小频率的key（频率相同则淘汰最早使用的）
    private void evictMinFreqKey() {
   
        // 1. 获取最小频率的key集合
        LinkedHashSet<K> minFreqSet = freqMap.get(minFreq);
        // 2. 淘汰集合中第一个key（LinkedHashSet按插入顺序，即最早使用的）
        K evictKey = minFreqSet.iterator().next();
        minFreqSet.remove(evictKey);

        // 3. 同步删除cache和freqMap（如果集合为空）
        cache.remove(evictKey);
        if (minFreqSet.isEmpty()) {
   
            freqMap.remove(minFreq);
        }

        System.out.println("淘汰key：" + evictKey);
    }

    // 测试
    public static void main(String[] args) {
   
        LFUCache<Integer, String> cache = new LFUCache<>(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        System.out.println(cache.get(1)); // 输出A，频率变成2，minFreq还是1
        cache.put(4, "D"); // 缓存满，淘汰minFreq=1的2（B）
        System.out.println(cache.get(2)); // 输出null（已淘汰）
        cache.get(3); // 3频率变成2，minFreq变成2
        cache.get(4); // 4频率变成2
        cache.put(5, "E"); // 缓存满，淘汰minFreq=2的3（C，最早使用）
    }
```



### 10.4.3 淘汰策略有哪些(Redis7版本)

1. noeviction：不会驱逐任何key，表示即使内存达到上限也不进行置换，所有能引起内存增加的命令都会返回error
2. allkeys-lru：对所有key使用LRU算法进行删除，优先删除掉最近最不经常使用的key，用以保存新数据
3. volatile-lru：对所有设置了过期时间的key使用LRU算法进行删除
4. allkeys-random：对所有key随机删除
5. volatile-random：对所有设置了过期时间的key随机删除
6. volatile-ttl：删除马山要过期的key
7. allkeys-lfu：对所有key使用LFU算法进行删除
8. volatile-lfu：对所有设置了过期时间的key使用LFU算法进行删除

### 10.4.4 <font color = 'red'>对上面淘汰策略的总结</font>

2个维度：过期键中筛选；所有键中筛选

4个方面：LRU	LFU	random	ttl

8个选项

### 10.4.5 淘汰策略怎么选

![](../image2/7.淘汰策略的选择.png)

### 10.4.6 如何配置？如何修改？

- 直接使用config命令
- 直接redis.conf配置文件

## 10.5 redis缓存淘汰策略配置性能建议

- 避免存储BigKey
- 开启惰性删除，lazyfree-lazy-eviction=yes





