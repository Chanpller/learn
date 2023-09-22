# 1章 mysql简介、安装、登陆

## 1.1 数据库与数据库管理系统

- DB：数据库（Database）：即存储数据的“仓库”，其本质是一个文件系统。它保存了一系列有组织的数据。
- DBMS：数据库管理系统（Database Management System）：是一种操纵和管理数据库的大型软件，用于建立、使用和维护数据库，对数据库进行统一管理和控制。用户通过数据库管理系统访问数据库中表内的数据。
- SQL：结构化查询语言（Structured Query Language）：专门用来与数据库通信的语言。

## 1.2 常见数据库

目前互联网上常见的数据库管理软件有Oracle、MySQL、MS SQL Server、DB2、PostgreSQL、Access、Sybase、Informix这几种

## 1.3 MySQL介绍

- MySQL是一个开放源代码的关系型数据库管理系统，由瑞典MySQL AB（创始人Michael Widenius）公司1995年开发，迅速成为开源数据库的 No.1。
- MySQL6.x 版本之后分为社区版和商业版。
- MySQL支持大型数据库，支持5000万条记录的数据仓库，32位系统表文件最大可支持4GB ，64位系统支持最大的表文件为8TB 。

1.4 关于MySQL 8.0
MySQL从5.7版本直接跳跃发布了8.0版本，开发者对MySQL的源代码进行了重构，最突出的一点是多MySQL Optimizer优化器进行了改进。

## 1.4 数据库分类

- 关系型数据库(RDBMS)
- 非关系型数据库(非RDBMS)
  - 键值型数据库
  - 文档型数据库
  - 搜索引擎数据库
  - 列式数据库
  - 图形数据库

## 1.5 表的关联关系

- 一对一关联（one-to-one）
- 一对多关系（one-to-many）
- 多对多（many-to-many）
- 自我引用(Self reference)

## 1.6 mysql版本

- MySQL Community Server 社区版本，开源免费，自由下载，但不提供官方技术支持，适用于大多数普通用户。
- MySQL Enterprise Edition 企业版本，需付费，不能在线下载，可以试用30天。提供了更多的功能和更完备的技术支持，更适合于对数据库的功能和可靠性要求较高的企业客户。
- MySQL Cluster 集群版，开源免费。用于架设集群服务器，可将几个MySQL Server封装成一个Server。需要在社区版或企业版的基础上使用。
- MySQL Cluster CGE 高级集群版，需付费。

## 1.7 软件的下载

https://www.mysql.com

## 1.8 MySQL8.0 版本的安装

MySQL下载完成后，找到下载文件，双击进行安装，具体操作步骤如下。

步骤1：双击下载的mysql-installer-community-8.0.26.0.msi文件，打开安装向导。

步骤2：打开“Choosing a Setup Type”（选择安装类型）窗口，在其中列出了5种安装类型，分别是Developer Default（默认安装类型）、Server only（仅作为服务器）、Client only（仅作为客户端）、Full（完全安装）、Custom（自定义安装）。这里选择“Custom（自定义安装）”类型按钮，单击“Next(下一步)”按钮。

步骤3：打开“Select Products” （选择产品）窗口，可以定制需要安装的产品清单。例如，选择“MySQL Server 8.0.26-X64”后，单击“→”添加按钮，即可选择安装MySQL服务器，如图所示。采用通用的方法，可以添加其他你需要安装的产品。选择右侧产品可以自定义安装目录和文件存放目录。

步骤4：在上一步选择好要安装的产品之后，单击“Next”（下一步）进入确认窗口，如图所示。单击
“Execute”（执行）按钮开始安装。

步骤5：安装完成后在“Status”（状态）列表下将显示“Complete”（安装完成）

## 1.9 配置MySQL8.0

MySQL安装之后，需要对服务器进行配置。具体的配置步骤如下。

步骤1：在上一个小节的最后一步，单击“Next”（下一步）按钮，就可以进入产品配置窗口。其中，“Config Type”选项用于设置服务器的类型

- Server Machine（服务器） ：该选项代表服务器，MySQL服务器可以同其他服务器应用程序一起运行，例如Web服务器等。MySQL服务器配置成适当比例的系统资源。
- Dedicated Machine（专用服务器） ：该选项代表只运行MySQL服务的服务器。MySQL服务器配置成使用所有可用系统资源。

步骤3：单击“Next”（下一步）按钮，打开设置授权方式窗口。其中，上面的选项是MySQL8.0提供的新的授权方式，采用SHA256基础的密码加密方法；下面的选项是传统授权方法（保留5.x版本兼容性）。

步骤4：单击“Next”（下一步）按钮，打开设置服务器root超级管理员的密码窗口，如图所示，需要输入两次同样的登录密码。也可以通过“Add User”添加其他用户，添加其他用户时，需要指定用户名、允许该用户名在哪台/哪些主机上登录，还可以指定用户角色等。

步骤5：单击“Next”（下一步）按钮，打开设置服务器名称窗口，如图所示。该服务名会出现在Windows服务列表中，也可以在命令行窗口中使用该服务名进行启动和停止服务。本书将服务名设置为“MySQL80”。如果希望开机自启动服务，也可以勾选“Start the MySQL Server at System Startup”选项（推荐）。

步骤6：完成配置，如图所示。单击“Finish”（完成）按钮，即可完成服务器的配置。

步骤7：如果还有其他产品需要配置，可以选择其他产品，然后继续配置。如果没有，直接选择“Next”（下一步），直接完成整个安装和配置过程。

步骤8：结束安装和配置。

## 1.10配置环境变量

如果不配置MySQL环境变量，就不能在命令行直接输入MySQL登录命令。下面说如何配置MySQL的环境变量：
步骤1：在桌面上右击【此电脑】图标，在弹出的快捷菜单中选择【属性】菜单命令。 

步骤2：打开【系统】窗口，单击【高级系统设置】链接。 

步骤3：打开【系统属性】对话框，选择【高级】选项卡，然后单击【环境变量】按钮。

步骤4：打开【环境变量】对话框，在系统变量列表中选择path变量。

步骤5：单击【编辑】按钮，在【编辑环境变量】对话框中，将MySQL应用程序的bin目录（C:\Program Files\MySQL\MySQL Server 8.0\bin）添加到变量值中，用分号将其与其他路径分隔开。

步骤6：添加完成之后，单击【确定】按钮，这样就完成了配置path变量的操作，然后就可以直接输入MySQL命令来登录数据库了。

## 1.11 mysql启停和登陆

```
# 启动 MySQL 服务命令：
net start MySQL服务名

# 停止 MySQL 服务命令：
net stop MySQL服务名
```

登录方式1：MySQL自带客户端

开始菜单 → 所有程序 → MySQL → MySQL 8.0 Command Line Client     仅限于root用户

```
mysql -h 主机名 -P 端口号 -u 用户名 -p密码
如：
mysql -h localhost -P 3306 -u root -pabc123 
-p与密码之间不能有空格，其他参数名与参数值之间可以有空格也可以没有空格
```

密码建议在下一行输入，保证安全

```
mysql -h localhost -P 3306 -u root -p
Enter password:****
```

客户端和服务器在同一台机器上，所以输入localhost或者IP地址127.0.0.1。同时，因为是连接本机： -hlocalhost就可以省略，如果端口号没有修改：-P3306也可以省略

```
mysql -u root -p
Enter password:****
```

查看版本信息

```
命令行 mysql -V 或mysql --version
登录后使用：select version();

退出：
exit 
或
quit
```

## 1.12 mysql的编码设置

* Mysql5.7

  ```
  #查看编码
  show variables like 'character_%';
  show variables like 'collation_%';
  
  
  #修改mysql的数据目录下的my.ini配置文件
  default-character-set=utf8 #默认字符集
  [mysqld] # 大概在76行左右，在其下添加
  ...
  character-set-server=utf8
  collation-server=utf8_general_ci
  
  #修改后重启服务
  
  ```

* MySQL8.0

  在MySQL 8.0版本之前，默认字符集为latin1，utf8字符集指向的是utf8mb3。MySQL8.0默认编码是utf8mb4。



## 1.13 MySQL目录结构与源码

| MySQL的目录结构                             | 说明                                 |
| ------------------------------------------- | ------------------------------------ |
| bin目录                                     | 所有MySQL的可执行文件。如：mysql.exe |
| MySQLInstanceConfig.exe                     | 数据库的配置向导，在安装时出现的内容 |
| data目录                                    | 系统数据库所在的目录                 |
| my.ini文件                                  | MySQL的主要配置文件                  |
| c:\ProgramData\MySQL\MySQL Server 8.0\data\ | 用户创建的数据库所在的目录           |

## 1.14 root用户密码忘记，重置的操作

* 通过任务管理器或者服务管理，关掉mysqld(服务进程) 
* 通过命令行+特殊参数开启mysqld mysqld --defaults-file="D:\ProgramFiles\mysql\MySQLServer5.7Data\my.ini" --skip-grant-tables
* 此时，mysqld服务进程已经打开。并且不需要权限检查
* mysql -uroot 无密码登陆服务器。另启动一个客户端进行 
* 修改权限表 （1） use mysql; （2）update user set authentication_string=password('新密码') where user='root' and Host='localhost'; （3）flush privileges;
* 通过任务管理器，关掉mysqld服务进程。 
* 再次通过服务管理，打开mysql服务。 
* 即可用修改后的新密码登陆。
