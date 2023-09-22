# 第2章-MySQL的数据目录

## 2.1 MySQL8的主要目录结构

```
[root@atguigu01 ~]# find / -name mysql


mysql57
[root@192 ~]# find / -name mysql
/etc/logrotate.d/mysql
/var/lib/mysql
/var/lib/mysql/mysql
/usr/bin/mysql
/usr/lib64/mysql
/usr/share/mysql

mysql80
[root@192 ~]# find / -name mysql
/etc/logrotate.d/mysql
/etc/selinux/targeted/active/modules/100/mysql
/var/lib/mysql
/var/lib/mysql/mysql
/usr/bin/mysql
/usr/lib64/mysql

```

### 2.1.1 数据库文件的存放路径

MySQL数据库文件的存放路径：/var/lib/mysql/

```sql
mysql> show variables like 'datadir';
+---------------+-----------------+
| Variable_name | Value |
+---------------+-----------------+
| datadir | /var/lib/mysql/ |
+---------------+-----------------+
1 row in set (0.04 sec)
```

### 2.1.2 mysql命令目录

相关命令目录：/usr/bin（mysqladmin、mysqlbinlog、mysqldump等命令）和/usr/sbin。

```shell

[root@192 bin]# find . -name "mysql*"
./mysql
./mysql_config_editor
./mysql_migrate_keyring
./mysqladmin
./mysqlbinlog
./mysqlcheck
./mysqldump
./mysqlimport
./mysqlpump
./mysqlshow
./mysqlslap
./mysql_secure_installation
./mysql_ssl_rsa_setup
./mysql_tzinfo_to_sql
./mysql_upgrade
./mysqld_pre_systemd
./mysqldumpslow

[root@192 sbin]# find . -name "mysql*"
./mysqld-debug
./mysqld
```

### 2.1.3 配置文件目录

配置文件目录：/usr/share/mysql-8.0（命令及配置文件），/etc/mysql（如my.cnf） my57的目录/usr/share/mysql

## 2.2 数据库和文件系统的关系

### 2.2.1 查看默认数据库

```sql
mysql> SHOW DATABASES;
```

* 4个数据库是属于MySQL自带的系统数据库
  * mysql:MySQL 系统自带的核心数据库，它存储了MySQL的用户账户和权限信息，一些存储过程、事件的定义信息，一些运行过程中产生的日志信息，一些帮助信息以及时区信息等。
  * information_schema:MySQL 系统自带的数据库，这个数据库保存着MySQL服务器维护的所有其他数据库的信息，比如有哪些表、哪些视图、哪些触发器、哪些列、哪些索引。这些信息并不是真实的用户数据，而是一些描述性信息，有时候也称之为元数据。在系统数据库information_schema 中提供了一些以innodb_sys 开头的表，用于表示内部系统表。information（信息）
  * performance_schema:MySQL 系统自带的数据库，这个数据库里主要保存MySQL服务器运行过程中的一些状态信息，可以用来监控 MySQL 服务的各类性能指标。包括统计最近执行了哪些语句，在执行过程的每个阶段都花费了多长时间，内存的使用情况等信息。performance(性能)
  * sys:MySQL 系统自带的数据库，这个数据库主要是通过视图的形式把information_schema 和performance_schema 结合起来，帮助系统管理员和开发人员监控 MySQL 的技术性能。

### 2.2.2 数据库在文件系统中的表示

```shell

[root@192 mysql-8.0]# cd /var/lib/mysql
[root@192 mysql]# ll
total 188892
-rw-r-----. 1 mysql mysql       56 Apr 27 09:53 auto.cnf
-rw-r-----. 1 mysql mysql      498 Apr 27 10:42 binlog.000001
-rw-r-----. 1 mysql mysql      499 Apr 27 10:46 binlog.000002
-rw-r-----. 1 mysql mysql     1400 May  7 09:56 binlog.000003
-rw-r-----. 1 mysql mysql      156 May  7 10:21 binlog.000004
-rw-r-----. 1 mysql mysql      156 May 15 08:13 binlog.000005
-rw-r-----. 1 mysql mysql      156 May 15 09:50 binlog.000006
-rw-r-----. 1 mysql mysql      156 May 18 10:07 binlog.000007
-rw-r-----. 1 mysql mysql      156 May 18 10:07 binlog.000008
-rw-r-----. 1 mysql mysql      128 May 18 10:07 binlog.index
-rw-------. 1 mysql mysql     1680 Apr 27 09:53 ca-key.pem
-rw-r--r--. 1 mysql mysql     1112 Apr 27 09:53 ca.pem
-rw-r--r--. 1 mysql mysql     1112 Apr 27 09:53 client-cert.pem
-rw-------. 1 mysql mysql     1676 Apr 27 09:53 client-key.pem
-rw-r-----. 1 mysql mysql   196608 May 18 10:09 #ib_16384_0.dblwr
-rw-r-----. 1 mysql mysql  8585216 Apr 27 09:53 #ib_16384_1.dblwr
-rw-r-----. 1 mysql mysql     3418 Apr 27 10:46 ib_buffer_pool
-rw-r-----. 1 mysql mysql 12582912 May 18 10:07 ibdata1
-rw-r-----. 1 mysql mysql 50331648 May 18 10:09 ib_logfile0
-rw-r-----. 1 mysql mysql 50331648 Apr 27 09:53 ib_logfile1
-rw-r-----. 1 mysql mysql 12582912 May 18 10:07 ibtmp1
drwxr-x---. 2 mysql mysql      187 May 18 10:07 #innodb_temp
drwxr-x---. 2 mysql mysql      143 Apr 27 09:53 mysql
-rw-r-----. 1 mysql mysql 25165824 May 18 10:07 mysql.ibd
srwxrwxrwx. 1 mysql mysql        0 May 18 10:07 mysql.sock
-rw-------. 1 mysql mysql        5 May 18 10:07 mysql.sock.lock
drwxr-x---. 2 mysql mysql     8192 Apr 27 09:53 performance_schema
-rw-------. 1 mysql mysql     1676 Apr 27 09:53 private_key.pem
-rw-r--r--. 1 mysql mysql      452 Apr 27 09:53 public_key.pem
-rw-r--r--. 1 mysql mysql     1112 Apr 27 09:53 server-cert.pem
-rw-------. 1 mysql mysql     1680 Apr 27 09:53 server-key.pem
drwxr-x---. 2 mysql mysql       28 Apr 27 09:53 sys
-rw-r-----. 1 mysql mysql 16777216 May 18 10:09 undo_001
-rw-r-----. 1 mysql mysql 16777216 May 18 10:09 undo_002
```

* 这个数据目录下的文件和子目录比较多，除了information_schema 这个系统数据库外，其他的数据库在数据目录下都有对应的子目录

* 每个数据库都在这个目录下创建了数据库目录，用于存放数据信息

### 2.2.3 表在文件系统中的表示

表文件在 /var/lib/mysql/数据库名/表名的目录中

#### 2.2.3.1 InnoDB存储引擎模式

* 表结构存储：
  * 在数据库目录中，描述表结构的文件格式：表名.frm。表结构永远保存在/var/lib/mysql/数据库名/表名的目录中
* 表数据存储：
  * 可以存储在系统表空间存储，InnoDB会在数据目录下创建一个名为ibdata1 、大小为12M 的文件，这个文件就是对应的系统表空间在文件系统上的表示。
  * 独立表空间存储，在MySQL5.6.6以及之后的版本中，InnoDB并不会默认的把各个表的数据存储到系统表空间中。在数据库目录中，存储数据和索引的文件格式：表名.ibd （InnoDB），数据和索引放在一起，称为聚簇索引。

```
#如果你想让系统表空间对应文件系统上多个实际文件，或者仅仅觉得原来的ibdata1 这个文件名难听，那可以在MySQL启动时配置对应的文件路径以及它们的大小，比如我们这样修改一下my.cnf 配置文件：
[server]
innodb_data_file_path=data1:512M;data2:512M:autoextend
```

* 系统表空间与独立表空间的设置

```
#启动参数innodb_file_per_table 控制，想将表数据都存储到系统表空间时，可以在启动MySQL服务器的时候这样配置：
[server]
innodb_file_per_table=0 # 0：代表使用系统表空间； 1：代表使用独立表空间

#默认都是独立表空间
mysql> show variables like 'innodb_file_per_table';
+-----------------------+-------+
| Variable_name         | Value |
+-----------------------+-------+
| innodb_file_per_table | ON    |
+-----------------------+-------+
```

* 其他类型的表空间：随着MySQL的发展，除了上述两种老牌表空间之外，现在还新提出了一些不同类型的表空间，比如通用
  表空间（general tablespace）、临时表空间（temporary tablespace）等。

#### 2.2.3.2 MyISAM存储引擎模式

* 表结构存储：
  * 与InnoDB一样，在数据库目录中，描述表结构的文件格式：表名.frm。表结构永远保存在/var/lib/mysql/数据库名/表名的目录中
* 表数据存储和索引存储：
  * 在MyISAM中的索引全部都是二级索引，该存储引擎的数据和索引是分开存放的。
  * 索引存储，在数据库目录中，索引的文件格式：表名.MYI   (MyISAM Index)
  * 数据存储，在数据库目录中，数据的文件格式：表名.MYD   (MyISAM Data)

### 2.2.4 视图存储

* mysql57中创建了视图，在数据库下面只会多一个文件：视图名.frm
* mysql80中创建了视图，没有新增文件。

### 2.2.5 其他文件说明

举例： 数据库a ， 表b 。

* MySQL5.7 中会在data/a的目录下生成db.opt 文件用于保存数据库的相关配置。比如：字符集、比较规则。
* MySQL8.0不再提供db.opt文件。
* MySQL8.0中不再单独提供b.frm，而是合并在b.ibd文件中。

```
#证明表机构存储到了b.ibd，可以用下面命令。
#进到database存放的表空间路径下。执行ibd2sdi --dump-file=存放文件 表名.ibd
ibd2sdi --dump-file=table.txt test_innodeb.ibd
#然后查看存放文件
less table.txt

["ibd2sdi"
,
{
        "type": 1,
        "id": 360,
        "object":
                {
    "mysqld_version_id": 80025,
    "dd_version": 80023,
    "sdi_version": 80019,
    "dd_object_type": "Table",
    "dd_object": {
        "name": "test_innodeb",
        "mysql_version_id": 80025,
        "created": 20230518150106,
        "last_altered": 20230518150106,
        "hidden": 1,
        "options": "avg_row_length=0;encrypt_type=N;key_block_size=0;keys_disabled=0;pack_record=0;stats_auto_recalc=0;stats_sample_pages=0;",
        "columns": [
            {
                "name": "id",
                "type": 4,

```



* 如果表b采用MyISAM ，data\a中会产生3个文件：
  * MySQL5.7 中： b.frm ：描述表结构文件，字段长度等。
  * MySQL8.0 中 b.xxx.sdi ：描述表结构文件，字段长度等
  * b.MYD (MYData)：数据信息文件，存储数据信息(如果采用独立表存储模式)
  * b.MYI (MYIndex)：存放索引信息文件
