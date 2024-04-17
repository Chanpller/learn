# 第23章-JVM运行时参数

## 23.1 jvm参数选项类型

### 23.1.1 标准参数选项

* 特点：比较稳定，后续版本基本不会变化，以-开头
* 各种选项：运行java或者java -help可以看到所有的标准选项
* 补充内容：-server与-client
  * Hotspot JVM有两种模式，分别为server和client，分别通过-server与-client模式设置
    * 在32位Windows系统上，默认使用Client类型的JVM，要想使用Server模式，则机器配置至少有2个以上的CPU和2G以上的物理内存。client模式适用于对内存要求较小的桌面应用程序，默认使用Serial串行垃圾回收器
    * 64位机器上只支持server模式的JVM，适用于需要大内存的应用程序，默认使用并行垃圾回收期。
    * 官网介绍：https://docs.oracle.com/javase/8/docs/technotes/guides/vm/server-class.html

```shell
C:\Users\cp>java -help
用法: java [-options] class [args...]
           (执行类)
   或  java [-options] -jar jarfile [args...]
           (执行 jar 文件)
其中选项包括:
    -d32          使用 32 位数据模型 (如果可用)
    -d64          使用 64 位数据模型 (如果可用)
    -server       选择 "server" VM
                  默认 VM 是 server.

    -cp <目录和 zip/jar 文件的类搜索路径>
    -classpath <目录和 zip/jar 文件的类搜索路径>
                  用 ; 分隔的目录, JAR 档案
                  和 ZIP 档案列表, 用于搜索类文件。
    -D<名称>=<值>
                  设置系统属性
    -verbose:[class|gc|jni]
                  启用详细输出
    -version      输出产品版本并退出
    -version:<值>
                  需要指定的版本才能运行
    -showversion  输出产品版本并继续
    -jre-restrict-search | -no-jre-restrict-search
                  在版本搜索中包括/排除用户专用 JRE
    -? -help      输出此帮助消息
    -X            输出非标准选项的帮助
    -ea[:<packagename>...|:<classname>]
    -enableassertions[:<packagename>...|:<classname>]
                  按指定的粒度启用断言
    -da[:<packagename>...|:<classname>]
    -disableassertions[:<packagename>...|:<classname>]
                  禁用具有指定粒度的断言
    -esa | -enablesystemassertions
                  启用系统断言
    -dsa | -disablesystemassertions
                  禁用系统断言
    -agentlib:<libname>[=<选项>]
                  加载本机代理库 <libname>, 例如 -agentlib:hprof
                  另请参阅 -agentlib:jdwp=help 和 -agentlib:hprof=help
    -agentpath:<pathname>[=<选项>]
                  按完整路径名加载本机代理库
    -javaagent:<jarpath>[=<选项>]
                  加载 Java 编程语言代理, 请参阅 java.lang.instrument
    -splash:<imagepath>
                  使用指定的图像显示启动屏幕
有关详细信息, 请参阅 http://www.oracle.com/technetwork/java/javase/documentation/index.html。
```

### 23.1.2 -X参数选项

* 特点：非标准化参数，功能还是比较稳定的，但官方说后续版本可能会变更，以-X开头。
* 各种选项：通过运行java -X命令可以看到所有的X选项
* JVM的JIT编译模式相关的选项
  * -Xmixed 混合模式,默认模式，让JIT根据程序运行的情况，有选择的将某些代码
  * -Xint 禁用JIT，所有字节码都被解释执行，这个模式的速度最慢的。
  * -Xcomp 所有字节码第一次使用就被编译成本地代码，然后再执行。
* 特别地
  * -Xms<size>        设置初始 Java 堆大小，等价于-XX:InitalHeapSize
  * -Xmx<size>        设置最大 Java 堆大小，等价于-XX:MaxHeapSize
  * -Xss<size>        设置 Java 线程堆栈大小，等价于-XX:ThreadStackSize

```shell
C:\Users\cp>java -X
    -Xmixed           混合模式执行 (默认)
    -Xint             仅解释模式执行
    -Xbootclasspath:<用 ; 分隔的目录和 zip/jar 文件>
                      设置搜索路径以引导类和资源
    -Xbootclasspath/a:<用 ; 分隔的目录和 zip/jar 文件>
                      附加在引导类路径末尾
    -Xbootclasspath/p:<用 ; 分隔的目录和 zip/jar 文件>
                      置于引导类路径之前
    -Xdiag            显示附加诊断消息
    -Xnoclassgc       禁用类垃圾收集
    -Xincgc           启用增量垃圾收集
    -Xloggc:<file>    将 GC 状态记录在文件中 (带时间戳)
    -Xbatch           禁用后台编译
    -Xms<size>        设置初始 Java 堆大小
    -Xmx<size>        设置最大 Java 堆大小
    -Xss<size>        设置 Java 线程堆栈大小
    -Xprof            输出 cpu 配置文件数据
    -Xfuture          启用最严格的检查, 预期将来的默认值
    -Xrs              减少 Java/VM 对操作系统信号的使用 (请参阅文档)
    -Xcheck:jni       对 JNI 函数执行其他检查
    -Xshare:off       不尝试使用共享类数据
    -Xshare:auto      在可能的情况下使用共享类数据 (默认)
    -Xshare:on        要求使用共享类数据, 否则将失败。
    -XshowSettings    显示所有设置并继续
    -XshowSettings:all
                      显示所有设置并继续
    -XshowSettings:vm 显示所有与 vm 相关的设置并继续
    -XshowSettings:properties
                      显示所有属性设置并继续
    -XshowSettings:locale
                      显示所有与区域设置相关的设置并继续

-X 选项是非标准选项, 如有更改, 恕不另行通知。
```

### 23.1.3 -XX参数选项

* 特点：非标准化参数，使用的最多的参数类型，这类选项属于实验性，不稳定，以-XX开头
* 作用：用于开发和调试JVM
* 分类：
  * Boolean类型格式
    * -XX:+<option> 表示启用option属性
    * -XX:-<option> 表示禁用option属性
    * 说明：因为有的指令默认是开启的，所以可以使用-关闭
    * 举例
      * -XX:+UseParalleGC选择垃圾收集器为并行收集器
      * -XX:+UseG1GC 表示启用G1收集器
      * -XX:+UseAdaptiveSizePolicy 自动选择年轻代区大小和相应的Servivor区比例
  * 非Boolean类型格式（key-value类型）
    * 子类型1：数值格式-XX:<option>=<number>
      * number表示数值，number可以带上单位，比如：m、M表示兆；k、K表示KB；g、G表示G
      * 例如：
        * -XX:NewSize=1024m 表示设置新生代初始大小为1024兆
        * -XX:MaxGCPauseMillis=500 表示设置GC停顿时间：500毫秒
        * -XX:GCTimeRatio=19 表示设置吞吐量
        * -XX:NewRatio=2 表示新生代与老年代的比例
    * 子类型2：非数值格式-XX:<option>=<string>
      * 例如：-XX:HeapDumpPath=/usr/local/heapdump.hprof用来指定heap转存文件的存储路径。
* 特别地
  * -XX:+PrintFlagsFinal
    * 输出所有参数的名称和默认值
    * 默认不包括Diagnostic和Experimental的参数
    * 可以配合-XX:+UnlockDiagnosticVMOptions和-XX:UnlockExperimentalVMOptions使用

## 23.2 添加JVM参数选项

### 23.2.1 Eclipse

### 23.2.2 IDEA

![image-20240417122848855](D:\IdeaProjects\learn\jvm\image\chapter23\image-20240417122848855.png)

### 23.2.3 运行jar包

* java -Xms50m -Xmx50m -XX:+PrintGCDatails -XX:+PrintGCTimeStamps -jar demo.jar

### 23.2.4 通过Tomcat运行war包

* Linux系统下可以在tomcat/bin/catalina.sh中添加类似如下配置
  * JAVA_OPTS="-Xms512M -Xmx1024M"
* Windows系统下在tomcat/bin/catalina.bat中添加类似如下配置
  * set "JAVA_OPTS=-Xms512M -Xmx1024M"

### 23.2.5 程序运行过程中

​	jinfo 能设置的参数有限，可以使用一个命令查看哪些参数可以在运行中设置。

* 使用jinfo -flag <name>=<value> <pid> 设置非Boolean类型参数
* 使用jinfo -flag [+|-]<name> <pid> 设置Boolean类型参数

```shell
#查看java进程
C:\Users\cp>jps
3520
31396 Launcher
9412 RemoteMavenServer36
27528 OOMTest
33740 Jps

#使用G1垃圾回收器报错
C:\Users\cp>jinfo -flag +UseG1GC 27528
Exception in thread "main" com.sun.tools.attach.AttachOperationFailedException: flag 'UseG1GC' cannot be changed

        at sun.tools.attach.WindowsVirtualMachine.execute(WindowsVirtualMachine.java:117)
        at sun.tools.attach.HotSpotVirtualMachine.executeCommand(HotSpotVirtualMachine.java:261)
        at sun.tools.attach.HotSpotVirtualMachine.setFlag(HotSpotVirtualMachine.java:234)
        at sun.tools.jinfo.JInfo.flag(JInfo.java:140)
        at sun.tools.jinfo.JInfo.main(JInfo.java:81)
#查看G1垃圾回收器是否被使用
C:\Users\cp>jinfo -flag UseG1GC 27528
-XX:-UseG1GC
#查看HeapDumpAfterFullGC是否被使用
C:\Users\cp>jinfo -flag HeapDumpAfterFullGC 27528
-XX:-HeapDumpAfterFullGC
#设置使用HeapDumpAfterFullG
C:\Users\cp>jinfo -flag +HeapDumpAfterFullGC 27528
#查看HeapDumpAfterFullGC是否被使用
C:\Users\cp>jinfo -flag HeapDumpAfterFullGC 27528
-XX:+HeapDumpAfterFullGC
```

## 23.3 常用的JVM参数选项

### 23.3.1 打印设置的XX选项及值

* -XX:+PrintCommandLineFlags 可以让在程序运行前打印出用户手动设置或者JVM自动设置的XX选项
* -XX:+PrintFlagsInitial 表示打印出所有XX选项的默认值
* -XX:+PrintFlagsFianl 表示打印出XX选项在运行程序时生效的值
* -XX:+PrintVMOptions 打印JVM的参数

### 23.3.2 堆、栈、方法区等内存大小设置

* 栈
  * -Xss128k 设置每个线程的栈大小为128k，等价于：-XX:ThreadStackSize=128k
* 堆
  * -Xms50m 等价于-XX:InitialHeapSize，设置JVM初始堆内存为50M
  * -Xmx50m 等价于-XX:MaxHeapSize，设置JVM最大堆内存为50M
  * -Xmn2g 设置年轻代大小为2G，官方推荐配置为整个堆大小的3/8
  * -XX:NewSize=1024m 设置年轻代初始值为1024M
  * -XX:MaxNewSize=1024m 设置年轻代最大值为1024M
  * -XX:SurvivorRatio=8 设置年轻代中Eden区与一个Survivor区的比值，默认为8
  * -XX:+UseAdaptiveSizePolicy 自动选择各区大小比例，默认是开启的
  * -XX:NewRatio=4 设置老年代与年轻代（包括1个Eden区和2个Survivor区）的比值
  * -XX:PretenureSizeThreadshold=1024 设置让大于此阈值的对象直接分配在老年代，单位为字节，只对Serial、ParNew收集器有效
  * -XX:MaxTenuringThreshold=15 默认为15，新生代每次MinorGC后，还存活的对象年龄+1，当对象的年龄大于设置的这个值时就进入老年代。
  * -XX:+PrintTenuringDistribution 让JVM在每次MinorGC后打印出当前使用的Survivor中对象的年龄分布
  * -XX:TargetSurvivorRatio 表示MinorGC结束后Survivor区域中占用空间的期望比例
* 方法区
  * 永久代：-XX:PermSize=256m 设置永久代初始值为256M
  * 永久代：-XX:MaxPermSize=256m 设置永久代最大值为256M
  * 元空间：-XX:MetaspaceSize=256m 初始空间大小
  * 元空间：-XX:MaxMetaspaceSize=256m 最大空间，默认没限制
  * 元空间：-XX:+UseCompressedOops 压缩对象指针
  * 元空间：-XX:+UseCompressedClassPointers 压缩类指针 
  * 元空间：-XX:CompressedClassSpaceSize=1g 设置Class Metaspace的大小，默认是1G
* 直接内存
  * -XX:MaxDirectMemorySize=1G 指定DirectMemory容量，若未指定，则默认与Java堆最大值一样。

```
 * -XX:+PrintFlagsFinal -Xms600m -Xmx600m
 *  -XX:SurvivorRatio=8，默认是自动配置，600M的是6:1,使用-XX:-UseAdaptiveSizePolicy 去掉后，可以控制各个区域的大小
 * 默认情况下，新生代占 1/3 ： 200m，老年代占2/3 : 400m
 *   其中，Eden默认占新生代的8/10 : 160m ,Survivor0，Survivor1各占新生代的1/10 ： 20m
```

![image-20240417153958676](D:\IdeaProjects\learn\jvm\image\chapter23\image-20240417153958676.png)

### 23.3.3 OutofMemory相关的选项



### 23.3.4 垃圾回收器相关选项

### 23.3.5 GC日志相关选项

### 23.3.6 其他参数

## 23.4 通过Java代码获取JVM参数