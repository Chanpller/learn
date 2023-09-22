# 17章 MySQL8其它新特性

## 17.1 MySQL8新特性概述

### 17.1.1 现在特性

* 更简便的NoSQL支持 ，NoSQL泛指非关系型数据库和数据存储。
* 更好的索引：MySQL 8中新增了隐藏索引和降序索引
* 更完善的JSON支持：增加了聚合函数JSON_ARRAYAGG() 和JSON_OBJECTAGG() ，将参数聚合为JSON数组或对象，新增了行内操作符 ->>，是列路径运算符 ->的增强，对JSON排序做了提升，并优化了JSON的更新操作。
* 安全和账户管理：新增了caching_sha2_password 授权插件、角色、密码历史记录和FIPS模式支持，这些特性提高了数据库的安全性和性能，使数据库管理员能够更灵活地进行账户管理工作。
* InnoDB的变化：默认的存储引擎，InnoDB在自增、索引、加密、死锁、共享锁等方面做了大量的改进和优化，并且支持原子数据定义语言（DDL），提高了数据安全性，对事务提供更好的支持。
* 数据字典：新增了事务数据字典，在这个字典里存储着数据库对象信息，这些数据字典存储在内部事务表中。在之前的MySQL版本中，字典数据都存储在元数据文件和非事务表中。
* 原子数据定义语句：MySQL 8开始支持原子数据定义语句（Automic DDL），即原子DDL 。
* 资源管理：支持创建和管理资源组，允许将服务器内运行的线程分配给特定的分组，以便线程根据组内可用资源执行。组属性能够控制组内资源，启用或限制组内资源消耗。数据库管理员能够根据不同的工作负载适当地更改这些属性。
* 字符集支持：MySQL 8中默认的字符集由latin1 更改为utf8mb4 ，并首次增加了日语所特定使用的集合，utf8mb4_ja_0900_as_cs。
* 优化器增强：优化器开始支持隐藏索引和降序索引。隐藏索引不会被优化器使用，验证索引的必要性时不需要删除索引，先将索引隐藏，如果优化器性能无影响就可以真正地删除索引。降序索引允许优化器对多个列进行排序，并且允许排序顺序不一致。
* 公用表表达式：公用表表达式（Common Table Expressions）简称为CTE，MySQL现在支持递归和非递归两种形式的CTE。CTE通过在SELECT语句或其他特定语句前使用WITH语句对临时结果集进行命名。
* 窗口函数
* 正则表达式支持：增加了REGEXP_LIKE()、EGEXP_INSTR()、REGEXP_REPLACE()和 REGEXP_SUBSTR()等函数来提升性能。另外，regexp_stack_limit和regexp_time_limit 系统变量能够通过匹配引擎来控制资源消耗。
* 内部临时表：TempTable存储引擎取代MEMORY存储引擎成为内部临时表的默认存储引擎
* 日志记录：在MySQL 8中错误日志子系统由一系列MySQL组件构成。这些组件的构成由系统变量log_error_services来配置，能够实现日志事件的过滤和写入
* 备份锁：新的备份锁允许在线备份期间执行数据操作语句，同时阻止可能造成快照不一致的操作。新备份锁由 LOCK INSTANCE FOR BACKUP 和 UNLOCK INSTANCE 语法提供支持，执行这些操作需要备份管理员特权。
* 增强的MySQL复制：MySQL 8复制支持对JSON文档进行部分更新的二进制日志记录，该记录使用紧凑的二进制格式，从而节省记录完整JSON文档的空间。当使用基于语句的日志记录时，这种紧凑的日志记录会自动完成，并且可以通过将新的binlog_row_value_options系统变量值设置为PARTIAL_JSON来启用。

### 17.1.2 MySQL8.0移除的旧特性

* 查询缓存已被移除
  * 删除项
    * （1）语句：FLUSH QUERY CACHE和RESET QUERY CACHE。 
    * （2）系统变量：query_cache_limit、query_cache_min_res_unit、query_cache_size、query_cache_type、query_cache_wlock_invalidate。 
    * （3）状态变量：Qcache_free_blocks、Qcache_free_memory、Qcache_hits、Qcache_inserts、Qcache_lowmem_prunes、Qcache_not_cached、Qcache_queries_in_cache、Qcache_total_blocks。 
    * （4）线程状态：checking privileges on cachedquery、checking query cache for query、invalidating query cache entries、sending cached result to client、storing result in query cache、waiting for query cache lock。
* 删除加密相关：ENCODE()、DECODE()、ENCRYPT()、DES_ENCRYPT()和DES_DECRYPT()函数，配置项des-key-file，系统变量have_crypt，FLUSH语句的DES_KEY_FILE选项，HAVE_CRYPT CMake选项。 对于移除的ENCRYPT()函数，考虑使用SHA2()替代，对于其他移除的函数，使用AES_ENCRYPT()和AES_DECRYPT()替代。
* 空间函数相关，多个空间函数已被标记为过时。这些过时函数在MySQL 8中都已被移除，只保留了对应的ST_和MBR函数。
* \N和NULL 在SQL语句中，解析器不再将\N视为NULL，所以在SQL语句中应使用NULL代替\N。这项变化不会影响使用LOAD DATA INFILE或者SELECT...INTO OUTFILE操作文件的导入和导出。在这类操作中，NULL仍等同于\N。
* mysql_install_db 在MySQL分布中，已移除了mysql_install_db程序，数据字典初始化需要调用带着--initialize或者--initialize-insecure选项的mysqld来代替实现。另外，--bootstrap和INSTALL_SCRIPTDIR CMake也已被删除。
* 通用分区处理程序 通用分区处理程序已从MySQL服务中被移除。为了实现给定表分区，表所使用的存储引擎需要自有的分区处理程序。 提供本地分区支持的MySQL存储引擎有两个，即InnoDB和NDB，而在MySQL 8中只支持InnoDB。
* 系统和状态变量信息  在INFORMATION_SCHEMA数据库中，对系统和状态变量信息不再进行维护。GLOBAL_VARIABLES、SESSION_VARIABLES、GLOBAL_STATUS、SESSION_STATUS表都已被删除。另外，系统变量show_compatibility_56也已被删除。被删除的状态变量有Slave_heartbeat_period、Slave_last_heartbeat,Slave_received_heartbeats、Slave_retried_transactions、Slave_running。以上被删除的内容都可使用性能模式中对应的内容进行替代。
* mysql_plugin工具 mysql_plugin工具用来配置MySQL服务器插件，现已被删除，可使用--plugin-load或--plugin-load-add选项在服务器启动时加载插件或者在运行时使用INSTALL PLUGIN语句加载插件来替代该工具。

## 17.2 窗口函数

### 17.2.1 窗口函数使用

需求：现在计算这个网站在每个城市的销售总额、在全国的销售总额、每个区的销售额占所在城市销售额中的比率，以及占总销售额中的比率。

```sql
CREATE TABLE sales(
id INT PRIMARY KEY AUTO_INCREMENT,
city VARCHAR(15),
county VARCHAR(15),
sales_value DECIMAL
);


INSERT INTO sales(city,county,sales_value)
VALUES
('北京','海淀',10.00),
('北京','朝阳',20.00),
('上海','黄埔',30.00),
('上海','长宁',10.00);

select * from sales

#原始方法
select a.*,ss.city_sum ,dd.all_sum ,a.sales_value/ss.city_sum,a.sales_value/dd.all_sum  from sales a  join (select s.city,sum(s.sales_value) city_sum from sales s group by s.city) ss on ss.city  = a.city 
 join (select sum(d.sales_value) all_sum from sales d) dd
 
 
#使用窗口函数方法 
 SELECT city AS 城市,county AS 区,sales_value AS 区销售额,
SUM(sales_value) OVER(PARTITION BY city) AS 市销售额, -- 计算市销售额
sales_value/SUM(sales_value) OVER(PARTITION BY city) AS 市比率,
SUM(sales_value) OVER() AS 总销售额, -- 计算总销售额
sales_value/SUM(sales_value) OVER() AS 总比率
FROM sales
ORDER BY city,county;
```

### 17.2.2窗口函数分类

* 窗口函数的作用类似于在查询中对数据进行分组，不同的是，分组操作会把分组的结果聚合成一条记录，而窗口函数是将结果置于每一条数据记录中。
* 窗口函数的特点是可以分组，而且可以在分组内排序。另外，窗口函数不会因为分组而减少原表中的行数，这对我们在原表数据的基础上进行统计和排序非常有用。
* 窗口函数可以分为静态窗口函数和动态窗口函数。
  * 静态窗口函数的窗口大小是固定的，不会因为记录的不同而不同；
  * 动态窗口函数的窗口大小会随着记录的不同而变化。

* 窗口函数总体上可以分为序号函数、分布函数、前后函数、首尾函数和其他函数
  * 序号函数
    * ROW_NUMBER() 顺序排序
    * RANK() 并列排序，有重复的跳过。比如排序为1、1、3，相同的都是1，跳过2
    * DENSE_RANK() 并列排序，有重复的不跳过。比如排序为1、1、2，相同的都是1，不跳过2
  * 分布函数
    * PERCENT_RANK() 等级值百分比
    * CUME_DIST() 累计分布值
  * 前后函数
    * LAG(expr,n) 返回当前行的前n行的expr的值
    * LEAD(expr,n)返回当前行的后n行的expr的值
  * 首尾函数
    * FIRST_VALUE(expr) 返回第一个expr值
    * LAST_VALUE(expr)返回最后一个expr的值
  * 其他函数
    * NTH_VALUE(expr,n)返回第n个expr的值
    * NTILE(n)将分区中的有序数据分为n个桶。记录桶编号

### 17.2.3 语法结构

窗口函数的语法结构是：

```
#解释说明： 函数通过 语句（通过字段分区获取） 获取对应的结果。
函数 OVER（[PARTITION BY 字段名 ORDER BY 字段名 ASC|DESC]）
或
函数 OVER 窗口名 … WINDOW 窗口名 AS （[PARTITION BY 字段名 ORDER BY 字段名 ASC|DESC]）
```

* OVER 关键字指定函数窗口的范围。
  * 如果省略后面括号中的内容，则窗口会包含满足WHERE条件的所有记录，窗口函数会基于所有满足WHERE条件的记录进行计算。
  * 如果OVER关键字后面的括号不为空，则可以使用如下语法设置窗口。
* 窗口名：为窗口设置一个别名，用来标识窗口。
* PARTITION BY子句：指定窗口函数按照哪些字段进行分组。分组后，窗口函数可以在每个分组中分别执行。
* ORDER BY子句：指定窗口函数按照哪些字段进行排序。执行排序操作使窗口函数按照排序后的数据记录的顺序进行编号。
* FRAME子句：为分区中的某个子集定义规则，可以用来作为滑动窗口使用。

### 17.2.4分类详解

初始化数据

```sql
CREATE TABLE goods(
id INT PRIMARY KEY AUTO_INCREMENT,
category_id INT,
category VARCHAR(15),
NAME VARCHAR(30),
price DECIMAL(10,2),
stock INT,
upper_time DATETIME
);

INSERT INTO goods(category_id,category,NAME,price,stock,upper_time)
VALUES
(1, '女装/女士精品', 'T恤', 39.90, 1000, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '连衣裙', 79.90, 2500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '卫衣', 89.90, 1500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '牛仔裤', 89.90, 3500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '百褶裙', 29.90, 500, '2020-11-10 00:00:00'),
(1, '女装/女士精品', '呢绒外套', 399.90, 1200, '2020-11-10 00:00:00'),
(2, '户外运动', '自行车', 399.90, 1000, '2020-11-10 00:00:00'),
(2, '户外运动', '山地自行车', 1399.90, 2500, '2020-11-10 00:00:00'),
(2, '户外运动', '登山杖', 59.90, 1500, '2020-11-10 00:00:00'),
(2, '户外运动', '骑行装备', 399.90, 3500, '2020-11-10 00:00:00'),
(2, '户外运动', '运动外套', 799.90, 500, '2020-11-10 00:00:00'),
(2, '户外运动', '滑板', 499.90, 1200, '2020-11-10 00:00:00');
```



#### 17.2.4.1 ROW_NUMBER()

* 顺序排序

需求：按照商品分组按照价格进行顺序排序

```sql
SELECT ROW_NUMBER() OVER(PARTITION BY category_id ORDER BY price DESC) AS
row_num,id, category_id, category, NAME, price, stock
FROM goods;

#或

SELECT ROW_NUMBER() over group_category_id_winows AS
row_num,id, category_id, category, NAME, price, stock
FROM goods window group_category_id_winows as (PARTITION BY category_id ORDER BY price DESC);
#结果集，每组商品分类的都是从1开始
row_num,id, category_id, category, NAME, price, stock
1	6	1	女装/女士精品	呢绒外套	399.90	1200
2	3	1	女装/女士精品	卫衣	89.90	1500
3	4	1	女装/女士精品	牛仔裤	89.90	3500
4	2	1	女装/女士精品	连衣裙	79.90	2500
5	1	1	女装/女士精品	T恤	39.90	1000
6	5	1	女装/女士精品	百褶裙	29.90	500
1	8	2	户外运动	山地自行车	1399.90	2500
2	11	2	户外运动	运动外套	799.90	500
3	12	2	户外运动	滑板	499.90	1200
4	7	2	户外运动	自行车	399.90	1000
5	10	2	户外运动	骑行装备	399.90	3500
6	9	2	户外运动	登山杖	59.90	1500
```

#### 17.2.4.2 RANK()

* 并列排序，有重复的跳过。比如排序为1、1、3，相同的都是1，跳过2

```sql
SELECT RANK() OVER(PARTITION BY category_id ORDER BY price DESC) AS
row_num,id, category_id, category, NAME, price, stock
FROM goods;

#或
SELECT RANK() over group_category_id_winows AS
row_num,id, category_id, category, NAME, price, stock
FROM goods window group_category_id_winows as (PARTITION BY category_id ORDER BY price DESC);
#结果集，每组商品分类的都是从1开始，当价格相同时，排序时一样的，重复的会跳过排序数。
row_num,id, category_id, category, NAME, price, stock
1	6	1	女装/女士精品	呢绒外套	399.90	1200
2	3	1	女装/女士精品	卫衣	89.90	1500
2	4	1	女装/女士精品	牛仔裤	89.90	3500
4	2	1	女装/女士精品	连衣裙	79.90	2500
5	1	1	女装/女士精品	T恤	39.90	1000
6	5	1	女装/女士精品	百褶裙	29.90	500
1	8	2	户外运动	山地自行车	1399.90	2500
2	11	2	户外运动	运动外套	799.90	500
3	12	2	户外运动	滑板	499.90	1200
4	7	2	户外运动	自行车	399.90	1000
4	10	2	户外运动	骑行装备	399.90	3500
6	9	2	户外运动	登山杖	59.90	1500
```

#### 17.2.4.3 DENSE_RANK()

* 并列排序，有重复的跳过。比如排序为1、1、3，相同的都是1，跳过2

```sql
SELECT DENSE_RANK() OVER(PARTITION BY category_id ORDER BY price DESC) AS
row_num,id, category_id, category, NAME, price, stock
FROM goods;

#或

SELECT DENSE_RANK() over group_category_id_winows AS
row_num,id, category_id, category, NAME, price, stock
FROM goods window group_category_id_winows as (PARTITION BY category_id ORDER BY price DESC);
#结果集，每组商品分类的都是从1开始，当价格相同时，排序时一样的，重复的不会跳过排序数。
row_num,id, category_id, category, NAME, price, stock
1	6	1	女装/女士精品	呢绒外套	399.90	1200
2	3	1	女装/女士精品	卫衣	89.90	1500
2	4	1	女装/女士精品	牛仔裤	89.90	3500
3	2	1	女装/女士精品	连衣裙	79.90	2500
4	1	1	女装/女士精品	T恤	39.90	1000
5	5	1	女装/女士精品	百褶裙	29.90	500
1	8	2	户外运动	山地自行车	1399.90	2500
2	11	2	户外运动	运动外套	799.90	500
3	12	2	户外运动	滑板	499.90	1200
4	7	2	户外运动	自行车	399.90	1000
4	10	2	户外运动	骑行装备	399.90	3500
5	9	2	户外运动	登山杖	59.90	1500
```

#### 17.2.4.4PERCENT_RANK()函数

PERCENT_RANK()函数是等级值百分比函数。按照如下方式进行计算。

```
(rank - 1) / (rows - 1)
rank的值为使用RANK()函数产生的序号，rows的值为当前表达式窗口的总记录数。
求的时累计百分比，相同的则累计百分比也相同
```

```sql
SELECT RANK() OVER (PARTITION BY category_id ORDER BY price DESC) AS r,
PERCENT_RANK() OVER (PARTITION BY category_id ORDER BY price DESC) AS pr,
id, category_id, category, NAME, price, stock
FROM goods
WHERE category_id = 1;

#或

SELECT RANK() OVER w AS r,
PERCENT_RANK() OVER w AS pr,
id, category_id, category, NAME, price, stock
FROM goods
WHERE category_id = 1 window w as (PARTITION BY category_id ORDER BY price DESC);

#
r	p	id	category_id	category	NAME	price	stock
1	0.0	6	1	女装/女士精品	呢绒外套	399.90	1200
2	0.2	3	1	女装/女士精品	卫衣	89.90	1500
2	0.2	4	1	女装/女士精品	牛仔裤	89.90	3500
4	0.6	2	1	女装/女士精品	连衣裙	79.90	2500
5	0.8	1	1	女装/女士精品	T恤	39.90	1000
6	1.0	5	1	女装/女士精品	百褶裙	29.90	500

SELECT 
PERCENT_RANK() OVER w AS pr,
id, category_id, category, NAME, price, stock
FROM goods
 window w as (PARTITION BY category_id ORDER BY price DESC);
pr	id	category_id	category	NAME	price	stock
0.0	6	1	女装/女士精品	呢绒外套	399.90	1200
0.2	3	1	女装/女士精品	卫衣	89.90	1500
0.2	4	1	女装/女士精品	牛仔裤	89.90	3500
0.6	2	1	女装/女士精品	连衣裙	79.90	2500
0.8	1	1	女装/女士精品	T恤	39.90	1000
1.0	5	1	女装/女士精品	百褶裙	29.90	500
0.0	8	2	户外运动	山地自行车	1399.90	2500
0.2	11	2	户外运动	运动外套	799.90	500
0.4	12	2	户外运动	滑板	499.90	1200
0.6	7	2	户外运动	自行车	399.90	1000
0.6	10	2	户外运动	骑行装备	399.90	3500
1.0	9	2	户外运动	登山杖	59.90	1500
```

#### 17.2.4.5 CUME_DIST()函数

* CUME_DIST()函数主要用于查询小于或等于某个值的比例。

举例：查询goods数据表中小于或等于当前价格的比例。

```sql
SELECT CUME_DIST() OVER(PARTITION BY category_id ORDER BY price ASC) AS cd,
 id, category, NAME, price
FROM goods;

#或

SELECT CUME_DIST() over w AS cd,
 id, category, NAME, price
FROM goods  window w as (PARTITION BY category_id ORDER BY price ASC);
	cd	id	category	NAME	price
0.16666666666666666	5	女装/女士精品	百褶裙	29.90
0.3333333333333333	1	女装/女士精品	T恤	39.90
0.5	2	女装/女士精品	连衣裙	79.90
0.8333333333333334	3	女装/女士精品	卫衣	89.90
0.8333333333333334	4	女装/女士精品	牛仔裤	89.90
1.0	6	女装/女士精品	呢绒外套	399.90
0.16666666666666666	9	户外运动	登山杖	59.90
0.5	7	户外运动	自行车	399.90
0.5	10	户外运动	骑行装备	399.90
0.6666666666666666	12	户外运动	滑板	499.90
0.8333333333333334	11	户外运动	运动外套	799.90
1.0	8	户外运动	山地自行车	1399.90
```

#### 17.2.4.6 LAG(expr,n)函数

* LAG(expr,n)函数返回当前行的前n行的expr的值。

举例：查询goods数据表中前一个商品价格与当前商品价格的差值。

```sql
SELECT id, category, NAME, price, pre_price, price - pre_price AS diff_price
FROM (
SELECT id, category, NAME, price,LAG(price,1) OVER w AS pre_price
FROM goods
WINDOW w AS (PARTITION BY category_id ORDER BY price)) t;

id, category, NAME, price, pre_price, diff_price
5	女装/女士精品	百褶裙	29.90	null	null
1	女装/女士精品	T恤	39.90	29.90	10.00
2	女装/女士精品	连衣裙	79.90	39.90	40.00
3	女装/女士精品	卫衣	89.90	79.90	10.00
4	女装/女士精品	牛仔裤	89.90	89.90	0.00
6	女装/女士精品	呢绒外套	399.90	89.90	310.00
9	户外运动	登山杖	59.90	null	null	
7	户外运动	自行车	399.90	59.90	340.00
10	户外运动	骑行装备	399.90	399.90	0.00
12	户外运动	滑板	499.90	399.90	100.00
11	户外运动	运动外套	799.90	499.90	300.00
8	户外运动	山地自行车	1399.90	799.90	600.00
```

#### 17.2.4.7 LEAD(expr,n)函数

* LEAD(expr,n)函数返回当前行的后n行的expr的值。

举例：查询goods数据表中后一个商品价格与当前商品价格的差值。

```sql
SELECT id, category, NAME, behind_price, price,behind_price - price AS
diff_price
FROM(
SELECT id, category, NAME, price,LEAD(price, 1) OVER w AS behind_price
FROM goods WINDOW w AS (PARTITION BY category_id ORDER BY price)) t;

id, category, NAME, price, pre_price, diff_price
5	女装/女士精品	百褶裙	39.90	29.90	10.00
1	女装/女士精品	T恤	79.90	39.90	40.00
2	女装/女士精品	连衣裙	89.90	79.90	10.00
3	女装/女士精品	卫衣	89.90	89.90	0.00
4	女装/女士精品	牛仔裤	399.90	89.90	310.00
6	女装/女士精品	呢绒外套	NULL	399.90	NULL
9	户外运动	登山杖	399.90	59.90	340.00
7	户外运动	自行车	399.90	399.90	0.00
10	户外运动	骑行装备	499.90	399.90	100.00
12	户外运动	滑板	799.90	499.90	300.00
11	户外运动	运动外套	1399.90	799.90	600.00
8	户外运动	山地自行车	NULL	1399.90	NULL
```

#### 17.2.4.8 FIRST_VALUE(expr)函数

* FIRST_VALUE(expr)函数返回第一个expr的值

语法：

```sql
LAST_VALUE (expression) OVER (
    [partition_clause]
    [order_clause]
    [frame_clause]
) 
```

举例：按照价格排序，查询第1个商品的价格信息。

```sql
SELECT id, category, NAME, price, stock,FIRST_VALUE(price) OVER w AS
first_price
FROM goods WINDOW w AS (PARTITION BY category_id ORDER BY price);
id, category, NAME, price, stock,first_price
5	女装/女士精品	百褶裙	29.90	500	29.90
1	女装/女士精品	T恤	39.90	1000	29.90
2	女装/女士精品	连衣裙	79.90	2500	29.90
3	女装/女士精品	卫衣	89.90	1500	29.90
4	女装/女士精品	牛仔裤	89.90	3500	29.90
6	女装/女士精品	呢绒外套	399.90	1200	29.90
9	户外运动	登山杖	59.90	1500	59.90
7	户外运动	自行车	399.90	1000	59.90
10	户外运动	骑行装备	399.90	3500	59.90
12	户外运动	滑板	499.90	1200	59.90
11	户外运动	运动外套	799.90	500	59.90
8	户外运动	山地自行车	1399.90	2500	59.90
```

#### 17.2.4.9 LAST_VALUE(expr)函数

* LAST_VALUE(expr)函数返回最后一个expr的值。

举例：按照价格排序，查询最后一个商品的价格信息。

```sql

SELECT id, category, NAME, price, stock,last_value(price) OVER w AS
last_price
FROM goods WINDOW w AS (PARTITION BY category_id ORDER BY price RANGE BETWEEN
     UNBOUNDED PRECEDING AND
            UNBOUNDED FOLLOWING);
#RANGE BETWEEN UNBOUNDED PRECEDING AND UNBOUNDED FOLLOWING  表示从第一行到最后一行结束，不加默认就一行结束。查询出来的结果时每行的结果。
id, category, NAME, price, stock,last_price
5	女装/女士精品	百褶裙	29.90	500	399.90
1	女装/女士精品	T恤	39.90	1000	399.90
2	女装/女士精品	连衣裙	79.90	2500	399.90
3	女装/女士精品	卫衣	89.90	1500	399.90
4	女装/女士精品	牛仔裤	89.90	3500	399.90
6	女装/女士精品	呢绒外套	399.90	1200	399.90
9	户外运动	登山杖	59.90	1500	1399.90
7	户外运动	自行车	399.90	1000	1399.90
10	户外运动	骑行装备	399.90	3500	1399.90
12	户外运动	滑板	499.90	1200	1399.90
11	户外运动	运动外套	799.90	500	1399.90
8	户外运动	山地自行车	1399.90	2500	1399.90
```

#### 17.2.4.10 NTH_VALUE(expr,n)函数

* NTH_VALUE(expr,n)函数返回第n个expr的值。

举例：查询goods数据表中排名第2和第3的价格信息。

```sql
SELECT id, category, NAME, price,NTH_VALUE(price,2) OVER w AS second_price,
NTH_VALUE(price,3) OVER w AS third_price
FROM goods WINDOW w AS (PARTITION BY category_id ORDER BY price);

 id, category, NAME, price,second_price,third_price
 5	女装/女士精品	百褶裙	29.90		
1	女装/女士精品	T恤	39.90	39.90	
2	女装/女士精品	连衣裙	79.90	39.90	79.90
3	女装/女士精品	卫衣	89.90	39.90	79.90
4	女装/女士精品	牛仔裤	89.90	39.90	79.90
6	女装/女士精品	呢绒外套	399.90	39.90	79.90
9	户外运动	登山杖	59.90		
7	户外运动	自行车	399.90	399.90	399.90
10	户外运动	骑行装备	399.90	399.90	399.90
12	户外运动	滑板	499.90	399.90	399.90
11	户外运动	运动外套	799.90	399.90	399.90
8	户外运动	山地自行车	1399.90	399.90	399.90

```

#### 17.2.4.11 NTILE(n)函数

* NTILE(n)函数将分区中的有序数据分为n个桶，记录桶编号。

举例：将goods表中的商品按照价格分为3组。

```sql
SELECT NTILE(3) OVER w AS nt,id, category, NAME, price
FROM goods WINDOW w AS (PARTITION BY category_id ORDER BY price)

nt,id, category, NAME, price
1	5	女装/女士精品	百褶裙	29.90
1	1	女装/女士精品	T恤	39.90
2	2	女装/女士精品	连衣裙	79.90
2	3	女装/女士精品	卫衣	89.90
3	4	女装/女士精品	牛仔裤	89.90
3	6	女装/女士精品	呢绒外套	399.90
1	9	户外运动	登山杖	59.90
1	7	户外运动	自行车	399.90
2	10	户外运动	骑行装备	399.90
2	12	户外运动	滑板	499.90
3	11	户外运动	运动外套	799.90
3	8	户外运动	山地自行车	1399.90
```

## 17.3公用表表达式

公用表表达式（或通用表表达式）简称为CTE（Common Table Expressions）。CTE是一个命名的临时结果集，作用范围是当前语句。CTE可以理解成一个可以复用的子查询，当然跟子查询还是有点区别的，CTE可以引用其他CTE，但子查询不能引用其他子查询。所以，可以考虑代替子查询。依据语法结构和执行方式的不同，公用表表达式分为普通公用表表达式和递归公用表表达式 2 种。

### 17.3.1普通公用表表达式

语法结构：

```sql
WITH CTE名称
AS （子查询）
SELECT|DELETE|UPDATE 语句;
```

举例

```sql
#使用前定义，定义号之后可以重读再后面的查询语句中使用。
WITH emp_dept_id
AS (SELECT DISTINCT department_id FROM employees)
 SELECT *
FROM departments d JOIN emp_dept_id e
ON d.department_id = e.department_id;


#使用前定义，可以认为就是一张表
WITH emp_dept_id
AS (SELECT DISTINCT department_id FROM employees)
 SELECT * from emp_dept_id;
```

### 17.3.1递归公用表表达式

* 递归公用表表达式也是一种公用表表达式，只不过，除了普通公用表表达式的特点以外，它还有自己的特点，就是可以调用自己。

* 递归公用表表达式需要包含UNION
* 递归*CTE*由三个主要部分组成：
  - 形成*CTE*结构的基本结果集的初始查询(*initial_query*)，初始查询部分被称为锚成员。
  - 递归查询部分是引用*CTE*名称的查询，因此称为递归成员。递归成员由一个UNION ALL或UNION DISTINCT运算符与锚成员相连。
  - 终止条件是当递归成员没有返回任何行时，确保递归停止
* 递归成员不能包含以下结构：
  * 聚合函数，如MAX，MIN，SUM，AVG，COUNT等
  * GROUP BY子句
  * ORDER BY子句
  * LIMIT子句
  * DISTINCT
* 递归公用表表达式由 2 部分组成，分别是种子查询和递归查询，中间通过关键字 UNION [ALL]进行连接。这里的种子查询，意思就是获得递归的初始值。这个查询只会运行一次，以创建初始数据集，之后递归查询会一直执行，直到没有任何新的查询数据产生，递归返回。
  语法结构：

```sql
WITH RECURSIVE cte_name AS (
    initial_query  -- anchor member  初始化执行
    UNION ALL
    recursive_query -- recursive member that references to the CTE name  每次都是执行这句话，没有结果集了就退出
)
SELECT|DELETE|UPDATE 语句;
```

举例

```sql
#简单举例
WITH RECURSIVE cte_count (n) 
AS (
      SELECT 1
      UNION ALL   #初始化时n是1，初始化执行
      SELECT n + 1  #执行到这时，n是2，下面才是递归本地，每次都是执行这句话。
      FROM cte_count #回去调用cte_count,n变成3，
      WHERE n < 3 #退出条件，没有结果集了就退出。
    )
SELECT n 
FROM cte_count;





WITH RECURSIVE cte
AS
(
SELECT employee_id,last_name,manager_id,1 AS n FROM employees WHERE employee_id = 100
-- 种子查询，找到第一代领导，初始化执行
UNION ALL
SELECT a.employee_id,a.last_name,a.manager_id,n+1 FROM employees AS a JOIN cte
ON (a.manager_id = cte.employee_id) -- 递归查询，找出以递归公用表表达式的人为领导的人
)
SELECT employee_id,last_name FROM cte WHERE n >= 3;

#解析
1、用递归公用表表达式中的种子查询，找出初代管理者。字段 n 表示代次，初始值为 1，表示是第一代管理者。
2、用递归公用表表达式中的递归查询，查出以这个递归公用表表达式中的人为管理者的人，并且代次的值加 1。直到没有人以这个递归公用表表达式中的人为管理者了，递归返回。
3、在最后的查询中，选出所有代次大于等于 3 的人，他们肯定是第三代及以上代次的下属了，也就是下下属了。这样就得到了我们需要的结果集。
```

