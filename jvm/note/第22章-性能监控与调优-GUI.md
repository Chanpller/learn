# 第22章-性能监控与调优-GUI

## 22.1 工具概述

​	使用命令行工具能获取到Java应用的基础信息，但存在一下局限性

* 无法获取方法级别的数据，如方法间的调用关系、各方法的调用次数和调用时间等（这对定位应用性能瓶颈至关重要）

* 要求用户登录到目标Java应用所在的宿主上，使用起来不是很方便。

* 分析数据通过终端输出，结果展示不够直观。

  为此，JDK提供了一些内存泄漏的分析工具，如jconsole，jvisualvm等，用于辅助开发人员定位问题，但是这些工具很多时候并不足以满足快速定位的需求。所以这里我们介绍的工具相对多一些、丰富一些。

  **图形化综合诊断工具**

* JDK自带的工具
  * jconsole：JDK自带的可视化监控工具。查看Java应用程序的运行概况、监控堆信息、永久区(或元空间)使用情况、类加载情况等。位置：jdk\bin\jsconsole.exe
  * Visual VM:Visual VM是一个工具，它提供了一个可视化界面，用于查看Java虚拟机上运行的基于Java技术的应用程序的详细信息。位置：jdk\bin\jvisualvm.exe
  * JMC：Java Mission Control，内置Java Flight Recorder。能够以极低的性能开销收集Java虚拟机的性能数据。JMC是Jrocket VM的，因为被oracle收购了，所以它的分析工具JMC也被集成到Hotspot虚拟机中了，一部分转化了了jcmd。
* 第三方工具
  * MAT：MAT（Memory Analyzer Tool）是基于Eclipse的内存分析工具，是一个快速、功能丰富的Java Heap分析工具，它可以帮助我们查找内存泄漏和减少内存消耗。eclipse的插件形式，也可以自行下载后打开，不需要eclipse程序
  * JProfiler：商业软件，需要付费，功能强大。与Visual VM类似
  * Arthas：Alibaba开源的Java诊断工具。深受开发者喜爱。
  * Btrace：Java运行时追踪工具。可以在不停机的情况下，跟踪指定的方法调用、构造函数调用和系统内存等信息

## 22.2 jConsole

### 22.2.1 基本概述

* 从Java5开始，在JDK中自带的java监控和管理控制台。
* 用于对JVM中内存、线程和类等的监控，是一个继续JMX（java management extensions）的GUI性能监控工具
* 官方教程：https://doc.oracle.com/javase/7/docs/technotes/guides/management/jsonsole.html

### 22.2.2 启动

* jdk/bin目录下，启动jconsole.exe命令
* 不需要使用jps命令来查询

### 22.2.3 三种链接方式

* Local：使用Jconsole链接一个正在本地系统运行的JVM，并且执行程序的和运行JConsole的需要是同一各用户。JConsole使用文件系统 的授权通过RMI连接器连接到平台的MBean服务器上。这种从本地连接的监控能力只有Sun的JDK具有
* Remote：使用下面的URL通过RMI连接器连接到一个JMX代理，service:jmx:rmi:///jndi/rmi://hostName:portNum/jmxrmi。JConsole 为建立连接，需要在环境变量中设置mx.remote.credentials来指定用户名和密码，从而进行授权。
* Advance：使用一个特殊的URL连接JMX代理。一般情况使用自己定制的连接器而不是RMI提供的连接器来连接JMX代理，或者是一个使用JDK1.4的实现了JMX和JMX Rmote的应用。

### 22.2.4 主要作用

![image-20240317094942324](../image/chapter22/image-20240317094942324.png)

## 22.3 Visual VM

### 22.3.1 基本概述

* Visual VM 是一个功能强大多合一故障诊断和性能监控的可视化工具。
* 它集成了多个JDK命令行工具，使用Visual VM可用于显示虚拟机进程和进程的配置和环境信息（jps,jinfo），监视应用程序的CPU、GC、堆、方法区及线程信息（jstat、jstack）等，甚至代替JConsole。
* 在JDK 6 update 7以后，Visual VM便作为JDK的一部分发布（Visual VM 在JDK/bin目录下），即它完全免费
* 此外，Visual VM也可以作为独立的软件进行安装：首页https://visualvm.github.io/index.html

![image-20240317095853757](../image/chapter22/image-20240317095853757.png)

### 22.3.2 插件的安装

* Visual VM 的一大特点是支持插件扩展，并且插件安装非常方便，我们即可以通过离线下载插件文件*.nbm，然后再Plugin对话框的已下载页面下，添加已下载的插件。也可以再可用插件页面下，在线安装插件。（建议安装上：VisualGC）
* 插件地址：https://visualvm.github.io/pluginscenters.html

![image-20240317100220500](../image/chapter22/image-20240317100220500.png)

设置插件地址，然后选择可用插件，即可安装需要的插件了

### ![image-20240317100455023](../image/chapter22/image-20240317100455023.png)

* IDEA 中也可以安装插件Visual VM插件

![image-20240317100751398](../image/chapter22/image-20240317100751398.png)

![image-20240317184555652](../image/chapter22/image-20240317184555652.png)

![image-20240317184722618](../image/chapter22/image-20240317184722618.png)

### 22.3.3 链接方式

* 本地连接：监控本地Java进程的CPU、类、线程等
* 远程连接
  * 确定远程服务器的ip地址
  * 添加JMX（通过JMX技术具体监控远程服务器哪个Java进程）
  * 修改bin/catalina.sh文件，连接远程的tomcat
  * 在../conf中添加jmxremote.access和jmxremote.password文件
  * 将服务器地址改为公网ip地址
  * 设置阿里云安全策略和防火墙策略
  * 启动tomcat，查看tomcat启动日志和端口监听
  * JMX中输入端口号、用户名、密码登录

### 22.3.4 主要功能

* 生成/读取堆内存快照
* 查看JVM参数和系统属性
* 查看运行中的虚拟机进程
* 生成/读取线程快照
* 程序资源的实时监控
* 其他功能：JMX代理连接、远程环境监控、CPU分析和内存分析

![image-20240317185624214](../image/chapter22/image-20240317185624214.png)

![image-20240317185759965](../image/chapter22/image-20240317185759965.png)

![image-20240317190132255](../image/chapter22/image-20240317190132255.png)

![image-20240317190240761](../image/chapter22/image-20240317190240761.png)

![image-20240317190413477](../image/chapter22/image-20240317190413477.png)

## 22.4 eclipse MAT

### 22.4.1 基本概述

​	MAT（Memory Analyzer Tool）工具是一款功能强大的Java堆内存分析器，可以用于查找内存泄漏以及查看内存消耗情况。

​	MAT是基于Eclipse开发的，不仅可以单独使用，还可以作为插件的形式嵌入在Eclipse中使用。是一款免费的性能分析工具，使用起来非常方便。

​	只要确定机器上装有JDK并配置好相关的环境变量，MAT可正常启动，还可以在Eclipse中以插件的形式安装。

​	下载地址：https://eclipse.dev/mat/downloads.php

![image-20240317191624266](../image/chapter22/image-20240317191624266.png)

### 22.4.2 获取dump文件

​	MAT看可以分析heap dump文件，在进行内存分析时，只要获得了反映当前设备内存映像的hprof文件，通过MAT打开就可以直观的看到当前的内存信息。

​	一般来说，这些内存信息包含：

* 所有的对象信息，包括对象实例、成员变量、存储于栈中的基本类型值和存储于堆中的其他对象的引用值。

* 所有的类信息，包括classloader、类名称、父类、静态变量等

* GCRoot到所有的这些对象的引用路径

* 线程信息，包括线程的调用栈及此线程的线程局部变量(TLS)

  说明1：缺点

* MAT不是一个万能工具，它并不能处理所有类型的堆存储文件。但是比较主流的厂家和格式，例如Sun、HP、SAP所采用的HPROF二进程堆存储文件，以及IBM的PHD堆存储文件等都能被很好的解析。

  说明2

* 最吸引人的还是能够快速为开发人员生成内存泄漏报表，方便定位问题和分析问题。虽然MAT有如此强大的功能，但是内存分析也没有简单到一键完成的程序，很多内存问题还是需要我们从MAT展现给我们的信息当中通过经验和直觉来判断才能发现。

  获取dump文件

* 方法一：通过jmap命令工具生成
* 方法二：通过JVM参数生成
  * -XX:+HeapDumpOnOutOfMemoryError或-XX:+HeapDumpBeforeFullGC
  * -XX:+HeapDumpOnPath
* 方式三：通过Visual VM可以到i出堆dump文件
* 方式四：MAT可以从活动的Java程序中导出堆快照（借助jps列出Java进程获取的堆快照）。

### 22.4.3 分析堆dump文件

![image-20240317225908458](../image/chapter22/image-20240317225908458.png)

![image-20240317230154741](../image/chapter22/image-20240317230154741.png)

![image-20240317230531501](../image/chapter22/image-20240317230531501.png)

#### 22.4.3.1 histogram（直方图）

​	展示各个类的实例数目以及这些实例Shallowheap或Retainedheap的总和

![image-20240317231635792](../image/chapter22/image-20240317231635792.png)

#### 22.4.3.2 thread overview

* 可以查看系统中的Java线程

* 可以查看局部变量的信息

![image-20240317232315940](../image/chapter22/image-20240317232315940.png)

#### 22.4.3.3 获得对象相互引用的关系

* with outgoing reference（引用了哪些对象，外部引用）

* with incoming reference（哪些引用了这个，引用进来的）

![image-20240317232849033](../image/chapter22/image-20240317232849033.png)

![image-20240317233021082](../image/chapter22/image-20240317233021082.png)

![image-20240317233119705](../image/chapter22/image-20240317233119705.png)

### 22.4.3.4 浅堆与深堆

#### 22.4.3.4.1 shallow heap

​	浅堆是指一个对象所消耗的内存。在32位系统中，一个对象引用会占据4个字节，一个int类型会占据4个字节，long类型会占据8个字节，每个对象头需要占据8个字节。根据堆快照格式不同，对象的大小可能会向8个字节对齐.

​	以String为例：2个int值共占8个字节，对象引用占4字节，对象头8字节，合计20字节，向8字节对齐，故占24字节。（JDK7中)

| int  | hash32 | 0                      |
| ---- | ------ | ---------------------- |
| int  | hash   | 0                      |
| ref  | value  | C:/Users/Administrator |

​	这24字节为String对象的浅堆大小。它与String的Value实际取值无关，无论字符串长度如何，浅堆大小始终是24字节。	

#### 22.4.3.4.2 retained heap

* 保留集（Retained Set）
  * 对象A的保留集指当对象A被垃圾回收后，可以被释放的所有的对象集合（包括对象A本身），即对象A的保留集可以被认为是只能通过对象A被直接或间接访问到的所有对象的集合。通俗的说，就是指仅被对象A所持有的对象的集合。
* 深堆（Retained  Heap）
  * 深堆是指对象的保留集中所有的对象的浅堆大小之和。
  * 注意：浅堆指对象本身占用的内存，不包括其内部引用对象的大小。一个对象的深堆指只能通过该对象访问到（直接或间接）所有对象的浅堆之和，即对象被回收后，可以释放的真是空间。
* 举例
  * S1-->hash32，S1-->hash，S1-->hash32，S1-->value->hello三个引用
  * S2-->hash32，S2-->hash，S2-->hash32，S2-->value->hello三个引用
  * 如果s1和s2都引用了hello字符串，则它们的深堆不包含hello字符串的大小。

#### 22.4.3.4.3 补充：对象实际大小

​	另外一个常用的概念是对象的实际大小。这里，对象的实际大小定义为一个对象所能触及的所有对象的浅堆大小之和，也就是通常意义上我们说的对象大小。与深堆相比，似乎这个在日常开发中更为直观和被人接受，但实际上，这个概念和垃圾回收无关。

​	下图显示了一个简单的对象引用关系图，对象A引用了C和D，对象B引用了C和E。那么对象A的浅堆大小只是A本身，不包含C和D，而A的实际大小为ACD三者之和。而A的深堆大小为A与D之和，由于对象C还可以通过对象B访问到，因此不再对象A的深堆范围内。

![image-20240318081245149](../image/chapter22/image-20240318081245149.png)

#### 22.4.3.4.4 练习

![image-20240318081409506](../image/chapter22/image-20240318081409506.png)

​	上图中，GC Roots直接引用了A和B两个对象。

​	A对象的Retained  Heap=A对象的Shallow Size

​	B对象的Retained  Heap=B对象的Shallow Size + C对象的Shallow Size

​	这里不包括D对象，因为D对象被GC Roots直接引用。

#### 22.4.3.4.5 案例分析：StudentTrace

​	浅堆的Student对象都占24个字节，深堆大小不一样

![image-20240318082349234](../image/chapter22/image-20240318082349234.png)

![image-20240318082843923](../image/chapter22/image-20240318082843923.png)

### 22.4.3.5 支配树

### 22.4.4 案例：Tomcat堆溢出分析

## 22.5 JProfiler

22.2.1 基本概述

22.2.2 启动

22.2.3 三种链接方式

22.2.4 主要作用

## 22.6 Arthas

22.2.1 基本概述

22.2.2 启动

22.2.3 三种链接方式

22.2.4 主要作用

## 22.7 Java Mission Control

22.2.1 基本概述

22.2.2 启动

22.2.3 三种链接方式

22.2.4 主要作用

## 22.8 trace

22.2.1 基本概述

22.2.2 启动

22.2.3 三种链接方式

22.2.4 主要作用

## 22.9 Flame Graphs(火焰图)

22.2.1 基本概述

22.2.2 启动

22.2.3 三种链接方式

22.2.4 主要作用