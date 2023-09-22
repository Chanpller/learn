# 12章 MySQL数据类型
## 12.1 MySQL中的数据类型
|类型|类型举例|
|--|--|
|整数类型|TINYINT、SMALLINT、MEDIUMINT、INT(或INTEGER)、BIGINT|
|浮点类型|FLOAT、DOUBLE|
|定点数类型|DECIMAL|
|位类型|BIT|
|日期时间类型|YEAR、TIME、DATE、DATETIME、TIMESTAMP|
|文本字符串类型|CHAR、VARCHAR、TINYTEXT、TEXT、MEDIUMTEXT、LONGTEXT|
|枚举类型|ENUM|
|集合类型|SET|
|二进制字符串类型|BINARY、VARBINARY、TINYBLOB、BLOB、MEDIUMBLOB、LONGBLOB|
|JSON类型|JSON对象、JSON数组|
|空间数据类型|单值类型：GEOMETRY、POINT、LINESTRING、POLYGON；集合类型：MULTIPOINT、MULTILINESTRING、MULTIPOLYGON、GEOMETRYCOLLECTION|
常见数据类型的属性：

|MySQL关键字|含义|
|--|--|
|NULL|数据列可包含NULL值|
|NOT NULL|数据列不允许包含NULL值|
|DEFAULT|默认值|
|PRIMARY KEY|主键|
|AUTO_INCREMENT|自动递增，适用于整数类型|
|UNSIGNED|无符号|
|CHARACTER SET name|指定一个字符集|
## 12.2 整数类型
### 12.2.1 类型分类
整数类型一共有5种，包括 TINYINT、SMALLINT、MEDIUMINT、INT（INTEGER）和 BIGINT。 它们的区别如下表所示：

|整数类型|字节|有符号数取值范围|无符号数取值范围| 备注           |
|--|--|--|--|--------------|
|TINYINT|1|-128~127|0~255| 类似java的byte  |
|SMALLINT|2|-32768~32767|0~65535| 类似java的short |
|MEDIUMINT|3|-8388608~8388607|0~16777215| 这个比较特殊       |
|INT、INTEGER|4|-2147483648~2147483647|0~4294967295| 类似java的int   |
|BIGINT|8|-9223372036854775808~9223372036854775807|0~18446744073709551615| 类似java的long  |
### 12.2.2 参数属性（可配置）
* M：表示显示宽度(不是数据类型占用bit长度)，M的取值范围是(0, 255)
  * int(5)：当数据宽度小于5位的时候在数字前面需要用字符填满宽度。该项功能需要配合“ ZEROFILL ”使用，表示用“0”填满宽度，否则指定显示宽度无效。 
  * 插入的数据宽度超过显示宽度限制且不超过最大显示宽度，不会截断和失败，该配置只针对小于宽度的。如果超过最大显示宽度，则报错。
  * 不指定宽度，则系统为每一种类型指定默认的宽度值。
    * TINYINT 默认且最多显示4个长度，比如-128
    * SMALLINT 默认且最多显示6个长度。比如-32768
    * MEDIUMINT 默认且最多显示9个长度。比如-8388608
    * INT 默认且最多显示11个长度。比如-2147483648
    * BIGINT 默认且最多显示11个长度。比如-9223372036854775808
  * mysql8已经不推荐使用显示宽度了。
```sql 
CREATE TABLE test_int2(
f1 INT,
f2 INT(5),
f3 INT(5) ZEROFILL
)
```
* UNSIGNED：无符号类型（非负数）
  * int类型默认显示宽度为int(11)，无符号int类型默认显示宽度为int(10)。
```sql 
CREATE TABLE test_int3(
f1 INT UNSIGNED
);
```
* ZEROFILL:不够M位时，用0在左边填充，如果超过M位，只要不超过数据存储范围没有影响。如果某列是ZEROFILL，那么MySQL会自动为当前列添加UNSIGNED属性
## 12.3 浮点类型
### 12.3.1 浮点类型分类
| 类型          | 字节 |备注|
|-------------|----|--|
| FLOAT       | 4  |单精度浮点数|
| DOUBLE      | 8  |双精度浮点数|
* REAL默认就是 DOUBLE。如果你把 SQL 模式设定为启用“ REAL_AS_FLOAT ”，那 么，MySQL 就认为 REAL 是 FLOAT。如果要启用“REAL_AS_FLOAT”，可以通过以下 SQL 语句实现：
```roomsql
SET sql_mode = “REAL_AS_FLOAT”;
```
* FLOAT 占用字节数少，取值范围小；DOUBLE 占用字节数多，取值范围也大。
* 无符号数取值范围是浮点型去掉负数那部分正数，与整数类型不同，主要体现在存储上，浮点型存储不管有没有符号，第一位都是符号位，后面的才是存储的值。
### 12.3.2 数据精度
* FLOAT(M,D) 或DOUBLE(M,D) 。这里，M称为精度，D称为标度。(M,D)中 M=整数位+小数位，D=小数位。 D<=M<=255，0<=D<=30。其他数据库不一定支持，所以最好不要这样写。
* 设置精度后，如果写入数据整数超过 M-D位，则会报错，与整数的显示长度有区别。
* FLOAT和DOUBLE类型在不指定(M,D)时，默认会按照实际的精度（由实际的硬件和操作系统决定）来显示。
* mysql存储时会四舍五入，不管有没有设置精度，五入时超过最大返回就会报错。
* 从MySQL 8.0.17开始，FLOAT(M,D) 和DOUBLE(M,D)用法在官方文档中已经明确不推荐使用，将来可能被移除。另外，关于浮点型FLOAT和DOUBLE的UNSIGNED也不推荐使用了，将来也可能被移除。
* 浮点类精度有误差（比如在做SUM计算式，结果是1.1，浮点型就是1.09999），不能用来比较相等，如果要用浮点比较，使用DECIMAL。
```roomsql
CREATE TABLE test_double1(
f1 FLOAT primary key,
f2 FLOAT(5,2),
f3 DOUBLE,
f4 DOUBLE(5,2)
);
#f2不能写超过3位的整数，如1234.123，写入报错
INSERT INTO test_double1(f1,f2,f3,f4)
VALUES(1.1,12.123456,123,1.2);

select * from test_double1
```
## 12.4 定点数类型
### 12.4.1 数据分类

|数据类型|字节数|含义|
|--|--|--|
|DECIMAL(M,D),DEC,NUMERIC|M+2字节|有效范围由M和D决定|
### 12.4.2 DECIMAL
* 使用 DECIMAL(M,D) 的方式表示高精度小数。其中，M被称为精度，D被称为标度。0<=M<=65，0<=D<=30，D<M。例如，定义DECIMAL（5,2）的类型，表示该列取值范围是-999.99~999.99。
* DECIMAL(M,D)的最大取值范围与DOUBLE类型一样，但是有效的数据范围是由M和D决定的。
* 定点数在MySQL内部是以字符串的形式进行存储，这就决定了它一定是精准的。
* 当DECIMAL类型不指定精度和标度时，其默认为DECIMAL(10,0)。当数据的精度超出了定点数类型的精度范围时，则MySQL同样会进行四舍五入处理。
```roomsql
CREATE TABLE test_decimal1(
f1 DECIMAL,
f2 DECIMAL(5,2)
);
DESC test_decimal1;
INSERT INTO test_decimal1(f1,f2)
VALUES(123.123,123.456);
#无法写入，超出范围，Out of range value for column 'f2' at row 1
INSERT INTO test_decimal1(f2)
VALUES(1234.34);
```
## 9.5 位类型：BIT
* BIT类型中存储的是二进制值，类似010110。
* 在向BIT类型的字段中插入数据时，一定要确保插入的数据在BIT类型支持的范围内。
### 9.5.1 位类型分类
|二进制字符串类型|长度|长度范围|占用空间|
|--|--|--|--|
|BIT(M)| M |1 <= M <= 64 |约为(M + 7)/8个字节|
### 9.5.2 BIT
* 使用SELECT命令查询位字段时，可以用BIN() 或HEX() 函数进行读取。
* 使用b+0查询数据时，可以直接查询出存储的十进制数据的值。
```roomsql

#默认BIT 只有1位，只能表示0和1.
CREATE TABLE test_bit1(
f1 BIT primary key,
f2 BIT(5),
f3 BIT(64)
);
INSERT INTO test_bit1(f1)
VALUES(1);

#这个报错，#Data too long for column 'f1' at row 1，因为只有1位bit，值只能为0和1
INSERT INTO test_bit1(f1)
VALUES(2);
INSERT INTO test_bit1(f1,f2)
VALUES(0,23);

select * from test_bit1
#结果集
f1  f2      f3
0	10111	0000000000000000000000000000000000000000000000000000000000000000
1	00000	0000000000000000000000000000000000000000000000000000000000000000

select f2+0 from test_bit1
#结果集
f2+0
23
null
```
## 9.6 日期与时间类型
### 9.6.1 日期与时间类型分类
* MySQL8.0支持的类型

|类型|名称|字节|日期格式|最小值|最大值|
|--|--|--|--|--|--|
|YEAR|年|1|YYYY或YY|1901|2155|
|TIME|时间|3|HH:MM:SS|-838:59:59|838:59:59|
|DATE|日期|3|YYYY-MM-DD|1000-01-01|9999-12-03|
|DATETIME|日期时间|8|YYYY-MM-DD HH:MM:SS|1000-01-01 00:00:00|9999-12-31 23:59:59|
|TIMESTAMP|日期时间|4|YYYY-MM-DD HH:MM:SS|1970-01-01 00:00:00 UTC|2038-01-19 03:14:07UTC|
* 不同数据类型表示的时间内容不同、取值范围不同，而且占用的字节数也不一样，要根据实际需要灵活选取。
* TIME的取值范围不是-23:59:59～23:59:59，因为MySQL设计的TIME 类型，不光表示一天之内的时间，而且可以用来表示一个时间间隔，这个时间间隔可以超过24小时。
### 9.6.2 YEAR类型
* 以4位字符串或数字格式表示YEAR类型，其格式为YYYY，最小值为1901，最大值为2155。
* 以2位字符串格式表示YEAR类型，最小值为00，最大值为99。
  * 当取值为01到69时，表示2001到2069；
  * 当取值为70到99时，表示1970到1999；
  * 当取值整数的0或00添加的话，那么是0000年；
  * 当取值是日期/字符串的'0'添加的话，是2000年。
* 从MySQL5.5.27开始，2位格式的YEAR已经不推荐使用。YEAR默认格式就是“YYYY”，没必要写成YEAR(4)，
* 从MySQL 8.0.19开始，不推荐使用指定显示宽度的YEAR(4)数据类型。
```roomsql
CREATE TABLE test_year(
f1 year primary key,
f2 YEAR(4)
);

DESC test_year;

INSERT INTO test_year(f1)
VALUES('2021'),(2022);



INSERT INTO test_year(f1)
VALUES ('2155');

#Out of range value for column 'f1' at row 1
INSERT INTO test_year(f1)
VALUES ('2156');

#当取值为01到69时，表示2001到2069；当取值为70到99时，表示1970到1999；
INSERT INTO test_year(f1)
VALUES ('69'),('70');

#当取值整数的0或00添加的话，那么是0000年,当取值是日期/字符串的'0'添加的话，是2000年。
INSERT INTO test_year(f1)
VALUES (0),('00');

SELECT * FROM test_year;
#结果集
f1
0	
1970	
2000	
2021	
2022	
2069	
2155	
```
### 9.6.3 DATE类型
* DATE类型表示日期，没有时间部分，格式为YYYY-MM-DD ，其中，YYYY表示年份，MM表示月份，DD表示日期。需要3个字节的存储空间
* 以YYYY-MM-DD 格式或者YYYYMMDD 格式表示的字符串日期，其最小取值为1000-01-01，最大取值为9999-12-03。YYYYMMDD格式会被转化为YYYY-MM-DD格式。
* 以YY-MM-DD 格式或者YYMMDD 格式表示的字符串日期，此格式中，年份为两位数值或字符串满足YEAR类型的格式条件为：当年份取值为00到69时，会被转化为2000到2069；当年份取值为70到99 时，会被转化为1970到1999。
* 使用CURRENT_DATE() 或者NOW() 函数，会插入当前系统的日期。
```roomsql
CREATE TABLE test_date1(
id int primary key auto_increment,
f1 DATE 
);

DESC test_date1;

INSERT INTO test_date1(f1)
VALUES ('2020-10-01'), ('20201001'),(20201001);

#存在隐式转换
INSERT INTO test_date1(f1)
VALUES ('00-01-01'), ('000101'), ('69-10-01'), ('691001'), ('70-01-01'), ('700101'), ('99-01-01'), ('990101');

INSERT INTO test_date1(f1)
VALUES (000301), (690301), (700301), (990301); #存在隐式转换

INSERT INTO test_date1(f1)
VALUES (CURDATE()),(CURRENT_DATE()),(NOW());

SELECT *
FROM test_date1;
#结果集
id  f1
1	2020-10-01
2	2020-10-01
3	2020-10-01
4	2000-01-01
5	2000-01-01
6	2069-10-01
7	2069-10-01
8	1970-01-01
9	1970-01-01
10	1999-01-01
11	1999-01-01
12	2000-03-01
13	2069-03-01
14	1970-03-01
15	1999-03-01
16	2023-04-17
17	2023-04-17
18	2023-04-17
```
### 9.6.4 TIME类型
* TIME类型用来表示时间，不包含日期部分。在MySQL中，需要3个字节的存储空间来存储TIME类型的数据，可以使用“HH:MM:SS”格式来表示TIME类型，其中，HH表示小时，MM表示分钟，SS表示秒。
* 可以使用带有冒号的字符串，比如' D HH:MM:SS' 、' HH:MM:SS '、' HH:MM '、' D HH:MM '、' D HH '或' SS '格式，都能被正确地插入TIME类型的字段中。其中D表示天，其最小值为0，最大值为34。如果使用带有D格式的字符串插入TIME类型的字段时，D会被转化为小时，计算格式为D*24+HH。当使用带有冒号并且不带D的字符串表示时间时，表示当天的时间，比如12:10表示12:10:00，而不是00:12:10。
* 可以使用不带有冒号的字符串或者数字，格式为' HHMMSS '或者HHMMSS 。如果插入一个不合法的字符串或者数字，MySQL在存储数据时，会将其自动转化为00:00:00进行存储。比如1210，MySQL会将最右边的两位解析成秒，表示00:12:10，而不是12:10:00。
* 使用CURRENT_TIME() 或者NOW() ，会插入当前系统的时间。
```roomsql
CREATE TABLE test_time1(
f1 int primary key auto_increment,
f2 TIME
);

#                                 D HH:MM:SS      HH:MM:SS      HH:MM       D HH:MM     D HH      SS
INSERT INTO test_time1(f2) VALUES('2 12:30:29'), ('12:35:29'), ('12:40'), ('2 12:40'),('1 05'), ('45');
#                                   HHMMSS     HHMMSS   MMSS
INSERT INTO test_time1(f2) VALUES ('123520'), (124011),(1210);

INSERT INTO test_time1(f2) VALUES (NOW()), (CURRENT_TIME());

SELECT * FROM test_time1;
#结果集
f1	f2
1	60:30:29
2	12:35:29
3	12:40:00
4	60:40:00
5	29:00:00
6	00:00:45
7	12:35:20
8	12:40:11
9	00:12:10
10	15:56:26
11	15:56:26
```
### 9.6.5 DATETIME类型
* DATETIME类型在所有的日期时间类型中占用的存储空间最大，总共需要8 个字节的存储空间。
* 在格式上 为DATE类型和TIME类型的组合，可以表示为YYYY-MM-DD HH:MM:SS ，其中YYYY表示年份，MM表示月份，DD表示日期，HH表示小时，MM表示分钟，SS表示秒。
* 以YYYY-MM-DD HH:MM:SS 格式或者YYYYMMDDHHMMSS 格式的字符串插入DATETIME类型的字段时，最小值为1000-01-01 00:00:00，最大值为9999-12-03 23:59:59
* 格式和规则参考 DATE和TIME
* 语句也参考DATE和TIME
* 不建议使用DATETIME存储，而是使用时间戳，因为DATETIME虽然直观，但不便于计算。
### 9.6.6 TIMESTAMP类型
* TIMESTAMP类型也可以表示日期时间，其显示格式与DATETIME类型相同，都是YYYY-MM-DD HH:MM:SS ，需要4个字节的存储空间。
* TIMESTAMP类型存储了时区，使用TIMESTAMP存储的同一个时间值，在不同的时区查询时会显示不同的时间。
* TIMESTAMP存储的时间范围比DATETIME要小很多，只能存储“1970-01-01 00:00:01 UTC”到“2038-01-19 03:14:07 UTC”之间的时间
* TIMESTAMP底层存储的是毫秒值，距离1970-1-1 0:0:0 0毫秒的毫秒值。
* 格式和规则参考DATE和TIME
```roomsql
INSERT INTO test_timestamp1
VALUES ('1999-01-01 03:04:50'), ('19990101030405'), ('99-01-01 03:04:05'),
('990101030405');
INSERT INTO test_timestamp1
VALUES ('2020@01@01@00@00@00'), ('20@01@01@00@00@00');
INSERT INTO test_timestamp1
VALUES (CURRENT_TIMESTAMP()), (NOW());

#报错 Incorrect datetime value
INSERT INTO test_timestamp1
VALUES ('2038-01-20 03:14:07');
```
## 9.7 文本字符串类型
### 9.7.1 文本类型分类
MySQL中，文本字符串总体上分为CHAR 、VARCHAR 、TINYTEXT 、TEXT 、MEDIUMTEXT 、LONGTEXT 、ENUM 、SET 等类型。

|文本字符串类型| 值的长度 | 长度范围             | 占用的存储空间      |
|--|------|------------------|--------------|
|CHAR(M)| M    | 0<=M<=255        | M个字节         |
|VARCHAR(M)| M    | 0<=M<=65535      | M个字节         |
|TINYTEXT| L    | 0<=L<=255        | L+2个字节       |
|MEDIUMTEXT| L    | 0<=L<=65535      | L+2个字节       |
|LONGTEXT| L    | 0<=L<=4294967295 | L+4个字节       |
|ENUM| L    | 0<=L<=65535      | 1或2个字节       |
|SET| L    | 0<=L<=64         | 1,2,3,4或8个字节 |
### 9.7.2 CHAR与VARCHAR类型
* CHAR和VARCHAR类型都可以存储比较短的字符串。
* CHAR
  * CHAR(M) 类型一般需要预先定义字符串长度。如果不指定(M)，则表示长度默认是1个字符。
  * 如果保存时，数据的实际长度比CHAR类型声明的长度小，则会在右侧填充空格以达到指定的长 度。
  * 当MySQL检索CHAR类型的数据时，CHAR类型的字段会去除尾部的空格。
  * 定义CHAR类型字段时，声明的字段长度即为CHAR类型字段所占的存储空间的字节数。
* VARCHAR
  * VARCHAR(M) 定义时， 必须指定长度M，否则报错。
  * MySQL4.0版本以下，varchar(20)：指的是20字节，如果存放UTF8汉字时，只能存6个（每个汉字3字节） ；MySQL5.0版本以上，varchar(20)：指的是20字符。
  * 检索VARCHAR类型的字段数据时，会保留数据尾部的空格。VARCHAR类型的字段所占用的存储空间为字符串实际长度加1个字节。(可以变字符串类型)
```roomsql
CREATE TABLE test_varchar1(
NAME VARCHAR #错误
);


#Column length too big for column 'NAME' (max = 21845);   最大长度为21845，65535是字节数，一个汉字占用3个字节。
CREATE TABLE test_varchar2(
NAME VARCHAR(65535) #错误
);
```
* 情况1：存储很短的信息。比如门牌号码101，201……这样很短的信息应该用char，因为varchar还要占个byte用于存储信息长度，本来打算节约存储的，结果得不偿失。
* 情况2：固定长度的。比如使用uuid作为主键，那用char应该更合适。因为他固定长度，varchar动态根据长度的特性就消失了，而且还要占个长度信息。
* 情况3：十分频繁改变的column。因为varchar每次存储都要有额外的计算，得到长度等工作，如果一个非常频繁改变的，那就要有很多的精力用于计算，而这些对于char来说是不需要的。
* 情况4：具体存储引擎中的情况： 
  * MyISAM 数据存储引擎和数据列：MyISAM数据表，最好使用固定长度(CHAR)的数据列代替可变长度(VARCHAR)的数据列。这样使得整个表静态化，从而使数据检索更快，用空间换时间。 
  * MEMORY 存储引擎和数据列：MEMORY数据表目前都使用固定长度的数据行存储，因此无论使用CHAR或VARCHAR列都没有关系，两者都是作为CHAR类型处理的。 
  * InnoDB 存储引擎，建议使用VARCHAR类型。因为对于InnoDB数据表，内部的行存储格式并没有区分固定长度和可变长度列（所有数据行都使用指向数据列值的头指针），而且主要影响性能的因素是数据行使用的存储总量，由于char平均占用的空间多于varchar，所以除了简短并且固定长度的，其他考虑varchar。这样节省空间，对磁盘I/O和数据存储总量比较好。
### 9.7.3 TEXT类型
* 在MySQL中，TEXT用来保存文本类型的字符串，总共包含4种类型，分别为TINYTEXT、TEXT、MEDIUMTEXT 和 LONGTEXT 类型。
* 由于实际存储的长度不确定，MySQL 不允许 TEXT 类型的字段做主键。遇到这种情况，你只能采用 CHAR(M)，或者 VARCHAR(M)。
* 在保存和查询数据时，并没有删除TEXT类型的数据尾部的空格。
## 9.8 ENUM类型
* ENUM类型也叫作枚举类型，ENUM类型的取值范围需要在定义字段时进行指定。设置字段值时，ENUM类型只允许从成员中选取单个值，不能一次选取多个值。
* 其所需要的存储空间由定义ENUM类型时指定的成员个数决定
* 当ENUM类型包含1～255个成员时，需要1个字节的存储空间
* 当ENUM类型包含256～65535个成员时，需要2个字节的存储空间
* ENUM类型的成员个数的上限为65535个
```roomsql
CREATE TABLE test_enum(
season ENUM('春','夏','秋','冬','unknow')
);

INSERT INTO test_enum
VALUES('春'),('秋');
# 忽略大小写
INSERT INTO test_enum
VALUES('UNKNOW');
# 允许按照角标的方式获取指定索引位置的枚举值
INSERT INTO test_enum
VALUES('1'),(3);
# Data truncated for column 'season' at row 1
INSERT INTO test_enum
VALUES('ab');
# 当ENUM类型的字段没有声明为NOT NULL时，插入NULL也是有效的
INSERT INTO test_enum
VALUES(NULL);
```
## 11.9 SET类型
* SET表示一个字符串对象，可以包含0个或多个成员，但成员个数的上限为64
* 设置字段值时，可以取取值范围内的 0 个或多个值。
* 当SET类型包含的成员个数不同时，其所占用的存储空间也是不同的，具体如下：
  * 1 <= L <= 8 1个,1个字节
  * 9 <= L <= 16 1个,2个字节
  * 17 <= L <= 24 1个,3个字节
  * 25 <= L <= 32 1个,4个字节
  * 33 <= L <= 64 1个,8个字节
* SET类型在存储数据时成员个数越多，其占用的存储空间越大。
* SET类型在选取成员时，可以一次选择多个成员，这一点与ENUM类型不同。
```roomsql
CREATE TABLE test_set(
s SET ('A', 'B', 'C')
);

INSERT INTO test_set (s) VALUES ('A'), ('A,B');
#插入重复的SET类型成员时，MySQL会自动删除重复的成员
INSERT INTO test_set (s) VALUES ('A,B,C,A');
#向SET类型的字段插入SET成员中不存在的值时，MySQL会抛出错误。
INSERT INTO test_set (s) VALUES ('A,B,C,D');
SELECT *
FROM test_set;
```
## 11.10 二进制字符串类型
* MySQL中的二进制字符串类型主要存储一些二进制数据，比如可以存储图片、音频和视频等二进制数据。
* MySQL中支持的二进制字符串类型主要包括BINARY、VARBINARY、TINYBLOB、BLOB、MEDIUMBLOB 和 LONGBLOB类型。

### 11.10.1 BINARY和VARBINARY
* BINARY和VARBINARY类似于CHAR和VARCHAR，只是它们存储的是二进制字符串。

|二进制字符串类型|特点|值的长度|占用空间|
|--|--|--|--|
|BINARY(M)|固定长度|M（0 <= M <= 255）| M个字节|
|VARBINARY(M)|可变长度|M（0 <= M <= 65535）|M+1个字节|
```roomsql
CREATE TABLE test_binary1(
f1 BINARY,
f2 BINARY(3),
# f3 VARBINARY,
f4 VARBINARY(10)
);
INSERT INTO test_binary1(f1,f2)
VALUES('a','a');
INSERT INTO test_binary1(f1,f2)
VALUES('尚','尚');#失败
```
### 11.10.2 BLOB类型
* MySQL中的BLOB类型包括TINYBLOB、BLOB、MEDIUMBLOB和LONGBLOB 4种类型，它们可容纳值的最大长度不同。可以存储一个二进制的大对象，比如图片、音频和视频等。

|二进制字符串类型|值的长度|长度范围|占用空间|
|--|--|--|--|
|TINYBLOB|L|0 <= L <= 255 |L + 1 个字节|
|BLOB |L| 0 <= L <= 65535（相当于64KB） |L + 2 个字节|
|MEDIUMBLOB |L| 0 <= L <= 16777215 （相当于16MB）| L + 3 个字节|
|LONGBLOB |L |0 <= L <= 4294967295（相当于4GB）| L + 4 个字节|
* TEXT和BLOB的使用注意事项：
  * BLOB和TEXT值也会引起自己的一些问题，特别是执行了大量的删除或更新操作的时候。删除这种值会在数据表中留下很大的" 空洞"，以后填入这些"空洞"的记录可能长度不同。为了提高性能，建议定期使用 OPTIMIZE TABLE 功能对这类表进行碎片整理。
  * 如果需要对大文本字段进行模糊查询，MySQL 提供了前缀索引。但是仍然要在不必要的时候避免检索大型的BLOB或TEXT值。例如，SELECT * 查询就不是很好的想法，除非你能够确定作为约束条件的WHERE子句只会找到所需要的数据行。否则，你可能毫无目的地在网络上传输大量的值。
  * 把BLOB或TEXT列分离到单独的表中。在某些环境中，如果把这些数据列移动到第二张数据表中，可以让你把原数据表中的数据列转换为固定长度的数据行格式，那么它就是有意义的。这会减少主表中的碎片，使你得到固定长度数据行的性能优势。它还使你在主数据表上运行 SELECT * 查询的时候不会通过网络传输大量的BLOB或TEXT值。
## 11.11 JSON类型
* 在MySQL 5.7中，就已经支持JSON数据类型。在MySQL 8.x版本中，JSON类型提供了可以进行自动验证的JSON文档和优化的存储结构，使得在MySQL中存储和读取JSON类型的数据更加方便和高效。 创建数据表，表中包含一个JSON类型的字段 js 。
* 通过“->”和“->>”符号，从JSON字段中正确查询出了指定的JSON数据的值。
```roomsql
CREATE TABLE test_json(
js json
);

INSERT INTO test_json (js)
VALUES ('{"name":"dds", "age":18, "address":{"province":"beijing",
"city":"beijing"}}');


mysql> SELECT js -> '$.name' AS NAME,js -> '$.age' AS age ,js -> '$.address.province'
AS province, js -> '$.address.city' AS city
-> FROM test_json;
+----------+------+-----------+-----------+
| NAME | age | province | city |
+----------+------+-----------+-----------+
| "dds" | 18 | "beijing" | "beijing" |
+----------+------+-----------+-----------+
```
## 11.12 空间类型
* MySQL 空间类型扩展支持地理特征的生成、存储和分析。这里的地理特征表示世界上具有位置的任何东 西，可以是一个实体，例如一座山；可以是空间，例如一座办公楼；也可以是一个可定义的位置，例如一个十字路口等等。MySQL中使用Geometry（几何） 来表示所有地理特征。Geometry指一个点或点的集合，代表世界上任何具有位置的事物。
* MySQL的空间数据类型（Spatial Data Type）对应于OpenGIS类，包括单值类型：GEOMETRY、POINT、LINESTRING、POLYGON以及集合类型：MULTIPOINT、MULTILINESTRING、MULTIPOLYGON、GEOMETRYCOLLECTION 。
