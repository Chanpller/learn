# Maven使用

# 1、 Maven概述

## 1.1 为什么要用maven

* 随着我们使用越来越多的框架，或者框架封装程度越来越高，项目中使用的jar包也越来越多。项目中，一个模块里面用到上百个jar包是非常正常的。

* 使用Maven ，依赖对应的 jar 包能够自动下载，方便、快捷又规范。
* 管理规模庞大的 jar 包，需要专门工具。
* 脱离 IDE 环境执行构建操作，需要专门工具。

## 1.2 什么是 Maven

* Maven 是 Apache 软件基金会组织维护的一款专门为 Java 项目提供**构建**和**依赖**管理支持的工具。但事实上这只是 Maven 的一部分功能。Maven 本身的产品定位是一款『**项目**管理工具』。
* 构建：Java 项目开发过程中，构建指的是使用**『原材料生产产品』**的过程。
* 构建过程包含的主要的环节：
  - 清理：删除上一次构建的结果，为下一次构建做好准备
  - 编译：Java 源程序编译成 *.class 字节码文件
  - 测试：运行提前准备好的测试程序
  - 报告：针对刚才测试的结果生成一个全面的信息
  - 打包
    - Java工程：jar包
    - Web工程：war包
  - 安装：把一个 Maven 工程经过打包操作生成的 jar 包或 war 包存入 Maven 仓库
  - 部署
    - 部署 jar 包：把一个 jar 包部署到 Nexus 私服服务器上
    - 部署 war 包：借助相关 Maven 插件（例如 cargo），将 war 包部署到 Tomcat 服务器上
* 依赖：如果 A 工程里面用到了 B 工程的类、接口、配置文件等等这样的资源，那么我们就可以说 A 依赖 B

# 2、Maven 核心程序解压和配置

2.1 Maven 官网地址

* 首页：https://maven.apache.org/index.html
* 下载地址：https://maven.apache.org/download.cgi

## 2.2 解压Maven核心程序

* 核心程序压缩包：apache-maven-3.8.4-bin.zip，解压到**非中文、没有空格**的目录。

## 2.3 指定本地仓库

* 本地仓库默认值：用户家目录/.m2/repository。由于本地仓库的默认位置是在用户的家目录下，而家目录往往是在 C 盘，也就是系统盘。将来 Maven 仓库中 jar 包越来越多，仓库体积越来越大，可能会拖慢 C 盘运行速度，影响系统性能。所以建议将 Maven 的本地仓库放在其他盘符下。配置方式如下：
* 解压目录中，找到 Maven 的核心配置文件：**conf/settings.xml**

```
<localRepository>D:\maven-repository</localRepository>
```

本地仓库这个目录，我们手动创建一个空的目录即可。

**记住**：一定要把 localRepository 标签**从注释中拿出来**。

**注意**：本地仓库本身也需要使用一个**非中文、没有空格**的目录。

## 2.4 配置阿里云提供的镜像仓库

* 将原有的例子配置注释掉，添加下面配置

```xml
<!-- <mirror>
  <id>maven-default-http-blocker</id>
  <mirrorOf>external:http:*</mirrorOf>
  <name>Pseudo repository to mirror external repositories initially using HTTP.</name>
  <url>http://0.0.0.0/</url>
  <blocked>true</blocked>
</mirror> -->
	<mirror>
		<id>nexus-aliyun</id>
		<mirrorOf>central</mirrorOf>
		<name>Nexus aliyun</name>
		<url>http://maven.aliyun.com/nexus/content/groups/public</url>
	</mirror>
```

## 2.5 配置 Maven 工程的基础 JDK 版本

* 默认配置运行，Java 工程使用的默认 JDK 版本是 1.5，而我们熟悉和常用的是 JDK 1.8 版本。修改配置的方式是：将 profile 标签整个复制到 settings.xml 文件的 profiles 标签内。

```xml
<profile>
	  <id>jdk-1.8</id>
	  <activation>
		<activeByDefault>true</activeByDefault>
		<jdk>1.8</jdk>
	  </activation>
	  <properties>
		<maven.compiler.source>1.8</maven.compiler.source>
		<maven.compiler.target>1.8</maven.compiler.target>
		<maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
	  </properties>
	</profile>
```

## 2.7 配置环境变量

* Maven 是一个用 Java 语言开发的程序，它必须基于 JDK 来运行，需要通过 JAVA_HOME 来找到 JDK 的安装位置，需要配置JAVA_HOME
* 配置 MAVEN_HOME 

```
XXX_HOME 通常指向的是 bin 目录的上一级

PATH 指向的是 bin 目录
```

* 配置PATH

```
%MAVEN_HOME%\bin
```

# 3、使用 Maven：命令行环境

## 3.1 Maven 核心概念：坐标

- **groupId**：公司或组织的 id
- **artifactId**：一个项目或者是项目中的一个模块的 id
- **version**：版本号
- 坐标和仓库中 jar 包的存储路径之间的对应关系

```xml
 <groupId>javax.servlet</groupId>
  <artifactId>servlet-api</artifactId>
  <version>2.5</version>

Maven本地仓库根目录\javax\servlet\servlet-api\2.5\servlet-api-2.5.jar
```

## 3.2 创建maven工程

* 创建目录作为后面操作的工作空间，例如：D:\maven-workspace\space201026
* 运行 **mvn archetype:generate** 命令（创建java工程）
  * mvn maven的执行命令
  * archetype 插件
  * generate 目标

```
Choose a number or apply filter (format: [groupId:]artifactId, case sensitive contains): 7:【直接回车，使用默认值】

Define value for property 'groupId': com.xx.maven

Define value for property 'artifactId': pro01-maven-java

Define value for property 'version' 1.0-SNAPSHOT: :【直接回车，使用默认值】

Define value for property 'package' com.xx.maven: :【直接回车，使用默认值】

Confirm properties configuration: groupId: com.xx.maven artifactId: pro01-maven-java version: 1.0-SNAPSHOT package: com.xx.maven Y: :【直接回车，表示确认。如果前面有输入错误，想要重新输入，则输入 N 再回车。】
```

* Maven 默认生成的工程，对 junit 依赖的是较低的 3.8.1 版本，我们可以改成较适合的 4.12 版本。自动生成的 App.java 和 AppTest.java 可以删除

* 自动生成的 pom.xml 解读

```xml
<!-- 当前Maven工程的坐标 -->
  <groupId>com.ee.maven</groupId>
  <artifactId>pro01-maven-java</artifactId>
  <version>1.0-SNAPSHOT</version>
  
  <!-- 当前Maven工程的打包方式，可选值有下面三种： -->
  <!-- jar：表示这个工程是一个Java工程  -->
  <!-- war：表示这个工程是一个Web工程 -->
  <!-- pom：表示这个工程是“管理其他工程”的工程 -->
  <packaging>jar</packaging>

  <name>pro01-maven-java</name>
  <url>http://maven.apache.org</url>

  <properties>
	<!-- 工程构建过程中读取源码时使用的字符集 -->
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
  </properties>

  <!-- 当前工程所依赖的jar包 -->
  <dependencies>
	<!-- 使用dependency配置一个具体的依赖 -->
    <dependency>
	
	  <!-- 在dependency标签内使用具体的坐标依赖我们需要的一个jar包 -->
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>4.12</version>
	  
	  <!-- scope标签配置依赖的范围 -->
      <scope>test</scope>
    </dependency>
  </dependencies>
```

## 3.3 Maven核心概念：POM

①含义

POM：**P**roject **O**bject **M**odel，项目对象模型。和 POM 类似的是：DOM（Document Object Model），文档对象模型。它们都是模型化思想的具体体现。

②模型化思想

POM 表示将工程抽象为一个模型，再用程序中的对象来描述这个模型。这样我们就可以用程序来管理项目了。我们在开发过程中，最基本的做法就是将现实生活中的事物抽象为模型，然后封装模型相关的数据作为一个对象，这样就可以在程序中计算与现实事物相关的数据。

③对应的配置文件

POM 理念集中体现在 Maven 工程根目录下 **pom.xml** 这个配置文件中。所以这个 pom.xml 配置文件就是 Maven 工程的核心配置文件。其实学习 Maven 就是学这个文件怎么配置，各个配置有什么用。

## 3.4 Maven核心概念：约定的目录结构

①各个目录的作用

* src 源码目录
  * main 主程序目录
    * java  是java源代码目录
  * resources 配置文件目录
* test 测试程序目录

②约定目录结构的意义

Maven 为了让构建过程能够尽可能自动化完成，所以必须约定目录结构的作用。例如：Maven 执行编译操作，必须先去 Java 源程序目录读取 Java 源代码，然后执行编译，最后把编译结果存放在 target 目录。

③约定大于配置

Maven 对于目录结构这个问题，没有采用配置的方式，而是基于约定。这样会让我们在开发过程中非常方便。如果每次创建 Maven 工程后，还需要针对各个目录的位置进行详细的配置，那肯定非常麻烦。

目前开发领域的技术发展趋势就是：约定大于配置，配置大于编码。

## 3.5 maven工程中编码写代码

* 主程序创建在src/main/java 下面
* 测试程序创建在test/main/java下面

## 3.6 maven构建命令

- 运行 Maven 中和构建操作相关的命令时，必须进入到 pom.xml 所在的目录。如果没有在 pom.xml 所在的目录运行 Maven 的构建命令，就会报错。

### 3.6.1 清理操作

mvn clean

效果：删除 target 目录

### 3.6.2 编译操作

主程序编译：mvn compile

测试程序编译：mvn test-compile

主体程序编译结果存放的目录：target/classes

测试程序编译结果存放的目录：target/test-classes

### 3.6.3测试操作

mvn test

测试的报告存放的目录：target/surefire-reports

### 3.6.4打包操作

mvn package

打包的结果——jar 包，存放的目录：target

### 3.6.5安装操作

mvn install

- 安装的效果是将本地构建过程中生成的 jar 包存入 Maven 本地仓库。这个 jar 包在 Maven 仓库中的路径是根据它的坐标生成的。
- 另外，安装操作还会将 pom.xml 文件转换为 XXX.pom 文件一起存入本地仓库。所以我们在 Maven 的本地仓库中想看一个 jar 包原始的 pom.xml 文件时，查看对应 XXX.pom 文件即可，它们是名字发生了改变，本质上是同一个文件。

## 3.7 创建 Maven 版的 Web 工程

```log
mvn archetype:generate -DarchetypeGroupId=org.apache.maven.archetypes -DarchetypeArtifactId=maven-archetype-webapp -DarchetypeVersion=1.4
#一样的输入groupid这些信息创建工程
```

* 参数 archetypeGroupId、archetypeArtifactId、archetypeVersion 用来指定现在使用的 maven-archetype-webapp 的坐标。
* 生成的pom.xml文件中 packaging是war包
* 生成的web工程目录，有一个webapp目录，放index.jsp，webapp/WEB-INF下面有web.xml
* 引入servlet-api包

```xml
<dependency>
    <groupId>javax.servlet</groupId>
    <artifactId>javax.servlet-api</artifactId>
    <version>3.1.0</version>
    <scope>provided</scope>
</dependency>
```

* mvn package 打包，然后将war包部署到tomcat

## 3.8 web工程依赖java工程

* 在web工程的pom.xml文件引入jar工程的本地maven库坐标。

```xml
<!-- 配置对Java工程pro01-maven-java的依赖 -->
<!-- 具体的配置方式：在dependency标签内使用坐标实现依赖 -->
<dependency>
	<groupId>com.xx.maven</groupId>
	<artifactId>pro01-maven-java</artifactId>
	<version>1.0-SNAPSHOT</version>
</dependency>
```

* mvn package 编译后可以看到WEN-INFO的lib目录下有引入的jar工程
* mvn dependency:list 查看依赖jar包
* mvn dependency:tree 查看树形依赖jar包

## 3.9 包的依赖范围

### 3.9.1依赖范围

标签的位置：dependencies/dependency/**scope**

标签的可选值：**compile**/**test**/**provided**/system/runtime/**import**

### 3.9.2 compile 和 test 对比

|         | main目录（空间） | test目录（空间） | 开发过程（时间） | 部署到服务器（时间） |
| ------- | ---------------- | ---------------- | ---------------- | -------------------- |
| compile | 有效             | 有效             | 有效             | 有效                 |
| test    | 无效             | 有效             | 有效             | 无效                 |

### 3.9.3 compile 和 provided 对比

|          | main目录（空间） | test目录（空间） | 开发过程（时间） | 部署到服务器（时间） |
| -------- | ---------------- | ---------------- | ---------------- | -------------------- |
| compile  | 有效             | 有效             | 有效             | 有效                 |
| provided | 有效             | 有效             | 有效             | 无效                 |

### 3.9.4 结论

- compile：通常使用的第三方框架的 jar 包这样在项目实际运行时真正要用到的 jar 包都是以 compile 范围进行依赖的。比如 SSM 框架所需jar包。
- test：测试过程中使用的 jar 包，以 test 范围依赖进来。比如 junit。
- provided(已提供，表示只需要编译就行，打包部署到服务器时去掉)：在开发过程中需要用到的“服务器上的 jar 包”通常以 provided 范围依赖进来。比如 servlet-api、jsp-api。而这个范围的 jar 包之所以不参与部署、不放进 war 包，就是避免和服务器上已有的同类 jar 包产生冲突，同时减轻服务器的负担。说白了就是：“**服务器上已经有了，你就别带啦！**

## 3.10 jar包依赖的传递性

jar包传递性原则:

在 A 依赖 B，B 依赖 C 的前提下，C 是否能够传递到 A，取决于 B 依赖 C 时使用的依赖范围。

- B 依赖 C 时使用 compile 范围：可以传递
- B 依赖 C 时使用 test 或 provided 范围：不能传递，所以需要这样的 jar 包时，就必须在需要的地方明确配置依赖才可以。

## 3.11 jar包依赖的排除

- 当 A 依赖 B，B 依赖 C 而且 C 可以传递到 A 的时候，A 不想要 C，需要在 A 里面把 C 排除掉。而往往这种情况都是为了避免 jar 包之间的冲突。
- 排除只是排除Bjar包会引入到A的引用，不排除B项目(不是jar包)的引用，但是Bjar包如果有引用，排除了就无法运行了。
- 所以配置依赖的排除其实就是阻止某些 jar 包的传递。因为这样的 jar 包传递过来会和其他 jar 包冲突。
- 配置方式

```xml
<dependency>
	<groupId>com.xx.maven</groupId>
	<artifactId>pro01-maven-java</artifactId>
	<version>1.0-SNAPSHOT</version>
	<scope>compile</scope>
	<!-- 使用excludes标签配置依赖的排除	-->
	<exclusions>
		<!-- 在exclude标签中配置一个具体的排除 -->
		<exclusion>
			<!-- 指定要排除的依赖的坐标（不需要写version） -->
			<groupId>commons-logging</groupId>
			<artifactId>commons-logging</artifactId>
		</exclusion>
	</exclusions>
</dependency>
```

## 3.12 继承

### 3.12.1概念

Maven工程之间，A 工程继承 B 工程

- B 工程：父工程
- A 工程：子工程

本质上是 A 工程的 pom.xml 中的配置继承了 B 工程中 pom.xml 的配置。

### 3.12.2 作用

* 在父工程中统一管理项目中的依赖信息，具体来说是管理依赖信息的版本。

* 通过在父工程中为整个项目维护依赖信息的组合既**保证了整个项目使用规范、准确的 jar 包**；又能够将**以往的经验沉淀**下来，节约时间和精力。

### 3.12.3 举例

在一个工程中依赖多个 Spring 的 jar 包

```
TIP

[INFO] +- org.springframework:spring-core:jar:4.0.0.RELEASE:compile
[INFO] | \- commons-logging:commons-logging:jar:1.1.1:compile
[INFO] +- org.springframework:spring-beans:jar:4.0.0.RELEASE:compile
[INFO] +- org.springframework:spring-context:jar:4.0.0.RELEASE:compile
[INFO] +- org.springframework:spring-expression:jar:4.0.0.RELEASE:compile
[INFO] +- org.springframework:spring-aop:jar:4.0.0.RELEASE:compile
[INFO] | \- aopalliance:aopalliance:jar:1.0:compile
```

使用 Spring 时要求所有 Spring 自己的 jar 包版本必须一致。为了能够对这些 jar 包的版本进行统一管理，我们使用继承这个机制，将所有版本信息统一在父工程中进行管理。

### 3.12.4 具体操作

* 第一步：创建父工程，修改打包方式为pom
  * 只有打包方式为 pom 的 Maven 工程能够管理其他 Maven 工程。打包方式为 pom 的 Maven 工程中不写业务代码，它是专门管理其他 Maven 工程的工程。
  * properties标签可以自定义标签，然后使用${标签名}进行使用value值。
  * 有子工程后会多一个modules标签，表示子模块，表示聚合，也可以不加表示不是聚合项目。
  * 父工程管理依赖使用dependencyManagement标签，里面写dependencies

```xml
  <groupId>com.xx.maven</groupId>
  <artifactId>pro03-maven-parent</artifactId>
  <version>1.0-SNAPSHOT</version>

  <!-- 当前工程作为父工程，它要去管理子工程，所以打包方式必须是 pom -->
  <packaging>pom</packaging>

<!-- 通过自定义属性，统一指定Spring的版本 -->
<properties>
	<project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
	
	<!-- 自定义标签，维护Spring版本数据 -->
	<xx.spring.version>4.3.6.RELEASE</xx.spring.version>
</properties>

<modules>  
	<module>pro04-maven-module</module>
	<module>pro05-maven-module</module>
	<module>pro06-maven-module</module>
</modules>

<!-- 使用dependencyManagement标签配置对依赖的管理 -->
<!-- 被管理的依赖并没有真正被引入到工程 -->
<dependencyManagement>
	<dependencies>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-core</artifactId>
			<version>${xx.spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-beans</artifactId>
			<version>${xx.spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-context</artifactId>
			<version>${xx.spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-expression</artifactId>
			<version>${xx.spring.version}</version>
		</dependency>
		<dependency>
			<groupId>org.springframework</groupId>
			<artifactId>spring-aop</artifactId>
			<version>${xx.spring.version}</version>
		</dependency>
	</dependencies>
</dependencyManagement>
```

* 第二步：创建模块工程
  * 需要**进入 pro03-maven-parent 工程的根目录**，然后运行 mvn archetype:generate 命令来创建模块工程。
  * 创建出的子工程会有parent标签确认父工程的坐标。如果groupId和version与父工程一致，子工程可以省略不写。
  * 子工程需要使用父工程的由来，还是需要手动写入，唯一区别是不用写版本号。如果写了版本号，就是子工程覆盖了父工程的，以子工程的为准。

```xml
<!-- 使用parent标签指定当前工程的父工程 -->
<parent>
	<!-- 父工程的坐标 -->
	<groupId>com.atguigu.maven</groupId>
	<artifactId>pro03-maven-parent</artifactId>
	<version>1.0-SNAPSHOT</version>
</parent>

<!-- 子工程的坐标 -->
<!-- 如果子工程坐标中的groupId和version与父工程一致，那么可以省略 -->
<!-- <groupId>com.atguigu.maven</groupId> -->
<artifactId>pro04-maven-module</artifactId>
<!-- <version>1.0-SNAPSHOT</version> -->

<!-- 子工程引用父工程中的依赖信息时，可以把版本号去掉。	-->
<!-- 把版本号去掉就表示子工程中这个依赖的版本由父工程决定。 -->
<!-- 具体来说是由父工程的dependencyManagement来决定。 -->
<dependencies>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-core</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-beans</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-context</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-expression</artifactId>
	</dependency>
	<dependency>
		<groupId>org.springframework</groupId>
		<artifactId>spring-aop</artifactId>
	</dependency>
</dependencies>
```

## 3.13 聚合

### 3.13.1 Maven 中的聚合

使用一个“总工程”将各个“模块工程”汇集起来，作为一个整体对应完整的项目。

### 3.13.2 好处

- 一键执行 Maven 命令：很多构建命令都可以在“总工程”中一键执行。
- 配置聚合之后，各个模块工程会在总工程中展示一个列表，让项目中的各个模块一目了然

### 3.13.3 聚合的配置

在总工程中配置 modules 即可：

```xml
	<modules>  
		<module>pro04-maven-module</module>
		<module>pro05-maven-module</module>
		<module>pro06-maven-module</module>
	</modules>
```

### 3.13.4 依赖循环问题

如果 A 工程依赖 B 工程，B 工程依赖 C 工程，C 工程又反过来依赖 A 工程，那么在执行构建操作时会报下面的错误：

```
DANGER

[ERROR] [ERROR] The projects in the reactor contain a cyclic reference:
```

# 4、IDEA使用Maven

## 4.1 创建父工程

* File->new->project->maven  选择默认的即可。
* 创建 Project 后，IDEA 会自动弹出下面提示，我们选择**『Enable Auto-Import』**，意思是启用自动导入。

## 4.2 创建子工程

* 右键有项目或者File->new->module->选择默认的就创建了一个java子工程
* 右键有项目或者File->new->module->选择maven-archetype-webapp创建一个web子工程

## 4.3 默认子工程转换为web工程

* File->Project Structure->Modules(Facets)选择模块->+号添加需要加入的内容，选择Web
* 修改添加web页面的Path路径，把web路径改为src/main/webapp下面
* 再设置webapp的根路径为src/main/webapp

## 4.4 IDEA中执行Maven命令

* 右侧maven界面可以直接选择对应命令执行
* 或者右侧界面中的m图标点开，可以输入命令执行
* 或者在项目中打开Terminal 输入命令执行

```sh
# -D 表示后面要附加命令的参数，字母 D 和后面的参数是紧挨着的，中间没有任何其它字符
# maven.test.skip=true 表示在执行命令的过程中跳过测试
mvn clean install -Dmaven.test.skip=true
```

## 4.5 工程导入

### 4.5.1 来自工程目录

* 假设别人发给我们一个 Maven 工程的 zip 压缩包：maven-rest-demo.zip。从码云或GitHub上也可以以 ZIP 压缩格式对项目代码打包下载。
* 如果你的所有 IDEA 工程有一个专门的目录来存放，而不是散落各处，那么首先我们就把 ZIP 包解压到这个指定目录中。
* 只要我们确认在解压目录下可以直接看到 pom.xml，那就能证明这个解压目录就是我们的工程目录。那么接下来让 IDEA 打开这个目录就可以了。

### 4.5.2 模块导入

* 复制我们想要导入的模块目录
* 粘贴到我们自己工程目录下，IDEA就可以看到这个目录了
* 在 IDEA 中执行导入：File->Project Structure->Modules->+号->Import Module   选择需要导入的模块目录->选择maven项目方式导入
* 修改 pom.xml，刚刚导入的 module 的父工程坐标还是以前的，需要改成我们自己的 project。

# 5、其他核心概念

## 5.1 生命周期

### 5.1.1 作用

为了让构建过程自动化完成，Maven 设定了三个生命周期，生命周期中的每一个环节对应构建过程中的一个操作

### 5.1.2 三个生命周期

- 前面三个生命周期彼此是独立的。
- 在任何一个生命周期内部，执行任何一个具体环节的操作，都是从本周期最初的位置开始执行，直到指定的地方。（本节记住这句话就行了，其他的都不需要记）

Maven 之所以这么设计其实就是为了提高构建过程的自动化程度：让使用者只关心最终要干的即可，过程中的各个环节是自动执行的

| 生命周期名称 | 作用         | 各个环节                                                     |
| ------------ | ------------ | ------------------------------------------------------------ |
| Clean        | 清理操作相关 | pre-clean clean post-clean                                   |
| Site         | 生成站点相关 | pre-site site post-site deploy-site                          |
| Default      | 主要构建过程 | validate generate-sources process-sources generate-resources process-resources 复制并处理资源文件，至目标目录，准备打包。 compile 编译项目 main 目录下的源代码。 process-classes generate-test-sources process-test-sources generate-test-resources process-test-resources 复制并处理资源文件，至目标测试目录。 test-compile 编译测试源代码。 process-test-classes test 使用合适的单元测试框架运行测试。这些测试代码不会被打包或部署。 prepare-package package 接受编译好的代码，打包成可发布的格式，如JAR。 pre-integration-test integration-test post-integration-test verify install将包安装至本地仓库，以让其它项目依赖。 deploy将最终的包复制到远程的仓库，以让其它开发人员共享；或者部署到服务器上运行（需借助插件，例如：cargo）。 |

## 5.2 插件和目标

* Maven 的核心程序仅仅负责宏观调度，不做具体工作。具体工作都是由 Maven 插件完成的。例如：编译就是由 maven-compiler-plugin-3.1.jar 插件来执行的。

* 一个插件可以对应多个目标，而每一个目标都和生命周期中的某一个环节对应。

  Default 生命周期中有 compile 和 test-compile 两个和编译相关的环节，这两个环节对应 compile 和 test-compile 两个目标，而这两个目标都是由 maven-compiler-plugin-3.1.jar 插件来执行的。

## 5.3 仓库

- 本地仓库：在当前电脑上，为电脑上所有 Maven 工程服务
- 远程仓库：需要联网
  - 局域网：我们自己搭建的 Maven 私服，例如使用 Nexus 技术。
  - Internet
    - 中央仓库
    - 镜像仓库：内容和中央仓库保持一致，但是能够分担中央仓库的负载，同时让用户能够就近访问提高下载速度，例如：Nexus aliyun

建议：不要中央仓库和阿里云镜像混用，否则 jar 包来源不纯，彼此冲突。

专门搜索 Maven 依赖信息的网站：https://mvnrepository.com/

# 6、Maven进阶

## 6.1 重新认识maven

### 6.1.1 Maven 的完整功能

在入门的时候我们介绍说 Maven 是一款『**构建**管理』和『**依赖**管理』的工具。但事实上这只是 Maven 的一部分功能。Maven 本身的产品定位是一款『**项目**管理工具』。

### 6.1.2 项目管理功能的具体体现

下面是 spring-boot-starter 的 POM 文件，可以看到：除了我们熟悉的坐标标签、dependencies 标签，还有 description、url、organization、licenses、developers、scm、issueManagement 等这些描述项目信息的标签。

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd" xmlns="http://maven.apache.org/POM/4.0.0"
    xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
  <!-- This module was also published with a richer model, Gradle metadata,  -->
  <!-- which should be used instead. Do not delete the following line which  -->
  <!-- is to indicate to Gradle or any Gradle module metadata file consumer  -->
  <!-- that they should prefer consuming it instead. -->
  <!-- do_not_remove: published-with-gradle-metadata -->
  <modelVersion>4.0.0</modelVersion>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter</artifactId>
  <version>2.5.6</version>
  <name>spring-boot-starter</name>
  <description>Core starter, including auto-configuration support, logging and YAML</description>
  <url>https://spring.io/projects/spring-boot</url>
  <organization>
    <name>Pivotal Software, Inc.</name>
    <url>https://spring.io</url>
  </organization>
  <licenses>
    <license>
      <name>Apache License, Version 2.0</name>
      <url>https://www.apache.org/licenses/LICENSE-2.0</url>
    </license>
  </licenses>
  <developers>
    <developer>
      <name>Pivotal</name>
      <email>info@pivotal.io</email>
      <organization>Pivotal Software, Inc.</organization>
      <organizationUrl>https://www.spring.io</organizationUrl>
    </developer>
  </developers>
  <scm>
    <connection>scm:git:git://github.com/spring-projects/spring-boot.git</connection>
    <developerConnection>scm:git:ssh://git@github.com/spring-projects/spring-boot.git</developerConnection>
    <url>https://github.com/spring-projects/spring-boot</url>
  </scm>
  <issueManagement>
    <system>GitHub</system>
    <url>https://github.com/spring-projects/spring-boot/issues</url>
  </issueManagement>

  <dependencies>
    <dependency>
      ……
    </dependency>
  </dependencies>
</project>
```

所以从『项目管理』的角度来看，Maven 提供了如下这些功能：

- 项目对象模型（POM）：将整个项目本身抽象、封装为应用程序中的一个对象，以便于管理和操作。
- 全局性构建逻辑重用：Maven 对整个构建过程进行封装之后，程序员只需要指定配置信息即可完成构建。让构建过程从 Ant 的『编程式』升级到了 Maven 的『声明式』。
- 构件的标准集合：在 Maven 提供的标准框架体系内，所有的构件都可以按照统一的规范生成和使用。
- 构件关系定义：Maven 定义了构件之间的三种基本关系，让大型应用系统可以使用 Maven 来进行管理
  - 继承关系：通过从上到下的继承关系，将各个子构件中的重复信息提取到父构件中统一管理
  - 聚合关系：将多个构件聚合为一个整体，便于统一操作
  - 依赖关系：Maven 定义了依赖的范围、依赖的传递、依赖的排除、版本仲裁机制等一系列规范和标准，让大型项目可以有序容纳数百甚至更多依赖
- 插件目标系统：Maven 核心程序定义抽象的生命周期，然后将插件的目标绑定到生命周期中的特定阶段，实现了标准和具体实现解耦合，让 Maven 程序极具扩展性
- 项目描述信息的维护：我们不仅可以在 POM 中声明项目描述信息，更可以将整个项目相关信息收集起来生成 HTML 页面组成的一个可以直接访问的站点。这些项目描述信息包括：
  - 公司或组织信息
  - 项目许可证
  - 开发成员信息
  - issue 管理信息
  - SCM 信息

## 6.2 POM 的四个层次

### 6.2.1 超级 POM

经过我们前面的学习，我们看到 Maven 在构建过程中有很多默认的设定。例如：源文件存放的目录、测试源文件存放的目录、构建输出的目录……等等。但是其实这些要素也都是被 Maven 定义过的。定义的位置就是：**超级 POM**。

关于超级 POM，Maven 官网是这样介绍的：

The Super POM is Maven's default POM. All POMs extend the Super POM unless explicitly set, meaning the configuration specified in the Super POM is inherited by the POMs you created for your projects.

译文：Super POM 是 Maven 的默认 POM。除非明确设置，否则所有 POM 都扩展 Super POM，这意味着 Super POM 中指定的配置由您为项目创建的 POM 继承。

所以我们自己的 POM 即使没有明确指定一个父工程（父 POM），其实也默认继承了超级 POM。就好比一个 Java 类默认继承了 Object 类。

超级 POM 中定义的内容如下：

```xml
<project>
  <!-- 模型版本号 -->
  <model模型版本号Version>4.0.0</modelVersion>
 
  <!-- 仓库地址 -->
  <repositories>
    <repository>
      <id>central</id>
      <name>Central Repository</name>
      <url>https://repo.maven.apache.org/maven2</url>
      <layout>default</layout>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
    </repository>
  </repositories>
 
  <!-- 插件仓库地址-->
  <pluginRepositories>
    <pluginRepository>
      <id>central</id>
      <name>Central Repository</name>
      <url>https://repo.maven.apache.org/maven2</url>
      <layout>default</layout>
      <snapshots>
        <enabled>false</enabled>
      </snapshots>
      <releases>
        <updatePolicy>never</updatePolicy>
      </releases>
    </pluginRepository>
  </pluginRepositories>
    
  <!-- 构建 -->
  <build>
    <!-- 构建根目录 -->
    <directory>${project.basedir}/target</directory>
    <!-- 构建class输出目录 -->
    <outputDirectory>${project.build.directory}/classes</outputDirectory>
    <!-- 生成的文件名 -->
    <finalName>${project.artifactId}-${project.version}</finalName>
    <!--测试class输出目录 -->
    <testOutputDirectory>${project.build.directory}/test-classes</testOutputDirectory>
    <!-- 源码目录 -->
    <sourceDirectory>${project.basedir}/src/main/java</sourceDirectory>
    <!-- 可运行脚本源码目录 -->
    <scriptSourceDirectory>${project.basedir}/src/main/scripts</scriptSourceDirectory>
    <!-- 测试源码目录 -->
    <testSourceDirectory>${project.basedir}/src/test/java</testSourceDirectory>
    <!-- 资源目录 -->
    <resources>
      <resource>
        <directory>${project.basedir}/src/main/resources</directory>
      </resource>
    </resources>
    <!-- 测试资源目录 -->
    <testResources>
      <testResource>
        <directory>${project.basedir}/src/test/resources</directory>
      </testResource>
    </testResources>
      
    <!-- 插件管理 -->
    <pluginManagement>
      <!-- NOTE: These plugins will be removed from future versions of the super POM -->
      <!-- They are kept for the moment as they are very unlikely to conflict with lifecycle mappings (MNG-4453) -->
      <plugins>
        <!-- 插件 -->
        <plugin>
          <artifactId>maven-antrun-plugin</artifactId>
          <version>1.3</version>
        </plugin>
        <plugin>
          <artifactId>maven-assembly-plugin</artifactId>
          <version>2.2-beta-5</version>
        </plugin>
        <plugin>
          <artifactId>maven-dependency-plugin</artifactId>
          <version>2.8</version>
        </plugin>
        <plugin>
          <artifactId>maven-release-plugin</artifactId>
          <version>2.5.3</version>
        </plugin>
      </plugins>
    </pluginManagement>
  </build>
 
  <!-- 站点目录 -->
  <reporting>
    <outputDirectory>${project.build.directory}/site</outputDirectory>
  </reporting>
 
  
  <profiles>
    <!-- NOTE: The release profile will be removed from future versions of the super POM -->
    <!-- 项目管理配置，环境配置 -->
    <profile>
      <id>release-profile</id>
 
      <activation>
        <property>
          <name>performRelease</name>
          <value>true</value>
        </property>
      </activation>
 
      <build>
        <plugins>
          <plugin>
            <inherited>true</inherited>
            <artifactId>maven-source-plugin</artifactId>
            <executions>
              <execution>
                <id>attach-sources</id>
                <goals>
                  <goal>jar-no-fork</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
          <plugin>
            <inherited>true</inherited>
            <artifactId>maven-javadoc-plugin</artifactId>
            <executions>
              <execution>
                <id>attach-javadocs</id>
                <goals>
                  <goal>jar</goal>
                </goals>
              </execution>
            </executions>
          </plugin>
          <plugin>
            <inherited>true</inherited>
            <artifactId>maven-deploy-plugin</artifactId>
            <configuration>
              <updateReleaseInfo>true</updateReleaseInfo>
            </configuration>
          </plugin>
        </plugins>
      </build>
    </profile>
  </profiles>
 
</project>
```

### 6.2.2 父 POM

和 Java 类一样，POM 之间其实也是**单继承**的。如果我们给一个 POM 指定了父 POM，那么继承关系如下图所示：

当前POM-->继承     父POM -->继承    超级POM

### 6.2.3 当前POM

当前项目中写的POM

### 6.2.4 有效 POM

有效 POM 英文翻译为 effective POM，它的概念是这样的——在 POM 的继承关系中，子 POM 可以覆盖父 POM 中的配置；如果子 POM 没有覆盖，那么父 POM 中的配置将会被继承。按照这个规则，继承关系中的所有 POM 叠加到一起，就得到了一个最终生效的 POM。显然 Maven 实际运行过程中，执行构建操作就是按照这个最终生效的 POM 来运行的。这个最终生效的 POM 就是**有效 POM**，英文叫**effective POM**。

查看有效pom的命令

```
mvn help:effective-pom
```

综上所述，平时我们使用和配置的 POM 其实大致是由四个层次组成的：

- 超级 POM：所有 POM 默认继承，只是有直接和间接之分。
- 父 POM：这一层可能没有，可能有一层，也可能有很多层。
- 当前 pom.xml 配置的 POM：我们最多关注和最多使用的一层。
- 有效 POM：隐含的一层，但是实际上真正生效的一层。

## 6.3 属性的声明与引用

### 6.3.1 help 插件的各个目标(查看属性需要用到这个插件)

官网说明地址：https://maven.apache.org/plugins/maven-help-plugin

| 目标                    | 说明                                              |
| ----------------------- | ------------------------------------------------- |
| help:active-profiles    | 列出当前已激活的 profile                          |
| help:all-profiles       | 列出当前工程所有可用 profile                      |
| help:describe           | 描述一个插件和/或 Mojo 的属性                     |
| help:effective-pom      | 以 XML 格式展示有效 POM                           |
| help:effective-settings | 为当前工程以 XML 格式展示计算得到的 settings 配置 |
| **help:evaluate**       | 计算用户在交互模式下给出的 Maven 表达式           |
| help:system             | 显示平台详细信息列表，如系统属性和环境变量        |

### 6.3.2 使用 help:evaluate 查看属性值

定义属性：在properties标签中自定义标签。标签中间是value值。

```xml
<properties>
    <com.xx.hello>good morning maven</com.xx.hello>
</properties>
```

运行命令：mvn help:evaluate，然后输入表达式${com.xx.hello}，即可得到自定义属性的值。

### 6.3.3 通过 Maven 访问java系统属性

java代码获取系统默认属性值

```java
Properties properties = System.getProperties();
Set<Object> propNameSet = properties.keySet();
for (Object propName : propNameSet) {
    String propValue = properties.getProperty((String) propName);
    System.out.println(propName + " = " + propValue);
}
```

```
sun.cpu.isalist====>amd64
sun.desktop====>windows
sun.io.unicode.encoding====>UnicodeLittle
sun.cpu.endian====>little
java.vendor.url.bug====>http://bugreport.sun.com/bugreport/
file.separator====>\
java.vendor====>Oracle Corporation
sun.boot.class.path====>C:\Program Files\Java\jdk1.8.0_102\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\rt.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\sunrsasign.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_102\jre\classes
java.ext.dirs====>C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext;C:\WINDOWS\Sun\Java\lib\ext
java.version====>1.8.0_102
java.vm.info====>mixed mode
awt.toolkit====>sun.awt.windows.WToolkit
user.language====>zh
java.specification.vendor====>Oracle Corporation
sun.java.command====>GetSystemProperties
java.home====>C:\Program Files\Java\jdk1.8.0_102\jre
sun.arch.data.model====>64
java.vm.specification.version====>1.8
java.class.path====>C:\Program Files\Java\jdk1.8.0_102\jre\lib\charsets.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\deploy.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\access-bridge-64.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\cldrdata.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\dnsns.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\jaccess.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\jfxrt.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\localedata.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\nashorn.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\sunec.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\sunjce_provider.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\sunmscapi.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\sunpkcs11.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\ext\zipfs.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\javaws.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\jce.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\jfr.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\jfxswt.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\jsse.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\management-agent.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\plugin.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\resources.jar;C:\Program Files\Java\jdk1.8.0_102\jre\lib\rt.jar;E:\ideaprojects\maven-parent-01\project-maven-java-01\target\classes;E:\Program Files\JetBrains\IntelliJ IDEA 2021.2.1\lib\idea_rt.jar
user.name====>Administrator
file.encoding====>UTF-8
java.specification.version====>1.8
java.awt.printerjob====>sun.awt.windows.WPrinterJob
user.timezone====>
user.home====>C:\Users\Administrator
os.version====>10.0
sun.management.compiler====>HotSpot 64-Bit Tiered Compilers
java.specification.name====>Java Platform API Specification
java.class.version====>52.0
java.library.path====>C:\Program Files\Java\jdk1.8.0_102\bin;C:\WINDOWS\Sun\Java\bin;C:\WINDOWS\system32;C:\WINDOWS;E:\Program Files (x86)\VMware\VMware Workstation\bin\;C:\ProgramData\Oracle\Java\javapath;C:\WINDOWS\system32;C:\WINDOWS;C:\WINDOWS\System32\Wbem;C:\WINDOWS\System32\WindowsPowerShell\v1.0\;C:\WINDOWS\System32\OpenSSH\;E:\Program Files\Git\cmd;E:\Program Files\nodejs\;E:\apache-maven-3.6.3\bin;C:\Program Files\Java\jdk1.8.0_102\bin;C:\Program Files\MySQL\MySQL Shell 8.0\bin\;C:\Users\Administrator\AppData\Local\Microsoft\WindowsApps;C:\Users\Administrator\AppData\Roaming\npm;C:\Users\Administrator\AppData\Local\GitHubDesktop\bin;.
sun.jnu.encoding====>GBK
os.name====>Windows 10
user.variant====>
java.vm.specification.vendor====>Oracle Corporation
java.io.tmpdir====>C:\Users\ADMINI~1\AppData\Local\Temp\
line.separator====>

java.endorsed.dirs====>C:\Program Files\Java\jdk1.8.0_102\jre\lib\endorsed
os.arch====>amd64
java.awt.graphicsenv====>sun.awt.Win32GraphicsEnvironment
java.runtime.version====>1.8.0_102-b14
java.vm.specification.name====>Java Virtual Machine Specification
user.dir====>E:\ideaprojects\maven-parent-01
user.country====>CN
user.script====>
sun.java.launcher====>SUN_STANDARD
sun.os.patch.level====>
java.vm.name====>Java HotSpot(TM) 64-Bit Server VM
file.encoding.pkg====>sun.io
path.separator====>;
java.vm.vendor====>Oracle Corporation
java.vendor.url====>http://java.oracle.com/
sun.boot.library.path====>C:\Program Files\Java\jdk1.8.0_102\jre\bin
java.vm.version====>25.102-b14
java.runtime.name====>Java(TM) SE Runtime Environment
```

可以通过系统变量名获取对应值：如${sun.cpu.isalist}

```shell
[INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
${sun.cpu.isalist}
[INFO]
amd64
[INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
```

### 6.3.4 访问系统环境变量

使用：${env.系统环境变量名} 获取对应值

```shell
[INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
${env.JAVA_HOME}
[INFO]
C:\Program Files\Java\jdk1.8.0_102
[INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:

```

### 6.3.5 访问 project 属性

- 使用表达式 ${project.xxx} 可以访问当前 POM 中的元素值。
- ${project.标签名} 访问标签
- ${project.标签名.子标签名} 访问子标签
- ${project.标签名[下标]} 访问标签列表

```
[INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:
${project.modelVersion}
[INFO]
4.0.0
[INFO] Enter the Maven expression i.e. ${project.groupId} or 0 to exit?:

#可以获取到project的所有值
${project}

```

### 6.3.6 访问 settings 全局配置

${settings.标签名} 可以访问 settings.xml 中配置的元素值。

### 6.3.7 用途

- 在当前 pom.xml 文件中引用属性
- 资源过滤功能：在非 Maven 配置文件中引用属性，由 Maven 在处理资源时将引用属性的表达式替换为属性值

## 6.4 build 标签详解

### 6.4.1 build 概述

在实际使用 Maven 的过程中，我们会发现 build 标签有时候有，有时候没，这是怎么回事呢？其实通过有效 POM 我们能够看到，build 标签的相关配置其实一直都在，只是在我们需要定制构建过程的时候才会通过配置 build 标签覆盖默认值或补充配置。这一点我们可以通过打印有效 POM 来看到。

所以**本质**上来说：我们配置的 build 标签都是对**超级 POM 配置**的**叠加**。那我们又为什么要在默认配置的基础上叠加呢？很简单，在默认配置无法满足需求的时候**定制构建过程**。

### 6.4.2 build 标签组成

从完整示例中我们能够看到，build 标签的子标签大致包含三个主体部分：

#### 6.4.2.1 定义约定的目录结构

| 目录名                | 作用                       |
| --------------------- | -------------------------- |
| sourceDirectory       | 主体源程序存放目录         |
| scriptSourceDirectory | 脚本源程序存放目录         |
| testSourceDirectory   | 测试源程序存放目录         |
| outputDirectory       | 主体源程序编译结果输出目录 |
| testOutputDirectory   | 测试源程序编译结果输出目录 |
| resources             | 主体资源文件存放目录       |
| testResources         | 测试资源文件存放目录       |
| directory             | 构建结果输出目录           |

```xml
<build>
      <sourceDirectory>E:\ideaprojects\maven-parent-01\project-maven-web-01\src\main\java</sourceDirectory>
      <scriptSourceDirectory>E:\ideaprojects\maven-parent-01\project-maven-web-01\src\main\scripts</scriptSourceDirectory>
      <testSourceDirectory>E:\ideaprojects\maven-parent-01\project-maven-web-01\src\test\java</testSourceDirectory>
      <outputDirectory>E:\ideaprojects\maven-parent-01\project-maven-web-01\target\classes</outputDirectory>
      <testOutputDirectory>E:\ideaprojects\maven-parent-01\project-maven-web-01\target\test-classes</testOutputDirectory>
      <resources>
        <resource>
          <directory>E:\ideaprojects\maven-parent-01\project-maven-web-01\src\main\resources</directory>
        </resource>
      </resources>
      <testResources>
        <testResource>
          <directory>E:\ideaprojects\maven-parent-01\project-maven-web-01\src\test\resources</directory>
        </testResource>
      </testResources>
      <directory>E:\ideaprojects\maven-parent-01\project-maven-web-01\target</directory>
    </build>

```

#### 6.4.2.2 备用插件管理

pluginManagement 标签存放着几个极少用到的插件：

- maven-antrun-plugin
- maven-assembly-plugin
- maven-dependency-plugin
- maven-release-plugin

通过 pluginManagement 标签管理起来的插件就像 dependencyManagement 一样，子工程使用时可以省略版本号，起到在父工程中统一管理版本的效果。情看下面例子：

- 被 spring-boot-dependencies 管理的插件信息：

```xml
<plugin>
	<groupId>org.springframework.boot</groupId>
	<artifactId>spring-boot-maven-plugin</artifactId>
	<version>2.6.2</version>
</plugin>
```

- 子工程使用的插件信息：

```xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
```

#### 6.4.2.3 生命周期插件

plugins 标签存放的是默认生命周期中实际会用到的插件，这些插件想必大家都不陌生，所以抛开插件本身不谈，我们来看看 plugin 标签的结构：

```xml
<plugin>
    <artifactId>maven-compiler-plugin</artifactId>
    <version>3.1</version>
    <executions>
        <execution>
            <id>default-compile</id>
            <phase>compile</phase>
            <goals>
                <goal>compile</goal>
            </goals>
        </execution>
        <execution>
            <id>default-testCompile</id>
            <phase>test-compile</phase>
            <goals>
                <goal>testCompile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```

* 坐标部分

  artifactId （制品id）和 version 标签定义了插件的坐标，作为 Maven 的自带插件这里省略了 groupId。

* 执行部分

  executions（执行） 标签内可以配置多个 execution 标签，execution 标签内：

  * id：指定唯一标识
  * phase（阶段）：关联的生命周期阶段
  * goals/goal(目标，目的)：关联指定生命周期的目标
    * goals 标签中可以配置多个 goal 标签，表示一个生命周期环节可以对应当前插件的多个目标。
  * configuration 标签内进行配置时使用的标签是插件本身定义的
    * 就以 maven-site-plugin 插件为例，它的核心类是 org.apache.maven.plugins.site.render.SiteMojo，在这个类中我们看到了 outputDirectory 属性：
    * 每个插件能够做哪些设置都是各个插件自己规定的，无法一概而论

另外，插件目标的执行过程可以进行配置，例如 maven-site-plugin 插件的 site 目标：

```xml
<execution>
    <id>default-site</id>
    <phase>site</phase>
    <goals>
        <goal>site</goal>
    </goals>
    <configuration>
        <outputDirectory>D:\idea2019workspace\atguigu-maven-test-prepare\target\site</outputDirectory>
        <reportPlugins>
            <reportPlugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-project-info-reports-plugin</artifactId>
            </reportPlugin>
        </reportPlugins>
    </configuration>
</execution>
```

### 6.4.3 典型应用：编译源码版本

* 在maven程序根目录下的settings.xml文件中定义了编译版本，但是脱离本地后，放到服务器上maven可能没配置这个，就无法运行了

```
<!-- 配置Maven工程的默认JDK版本 -->
<!-- <profile>
  <id>jdk-1.8</id>
  <activation>
	<activeByDefault>true</activeByDefault>
	<jdk>1.8</jdk>
  </activation>
  <properties>
	<maven.compiler.source>1.8</maven.compiler.source>
	<maven.compiler.target>1.8</maven.compiler.target>
	<maven.compiler.compilerVersion>1.8</maven.compiler.compilerVersion>
  </properties>
</profile> -->
```

* 我们在pom文件中定义这个插件

```xml
<!-- build 标签：意思是告诉 Maven，你的构建行为，我要开始定制了！ -->
<build>
    <!-- plugins 标签：Maven 你给我听好了，你给我构建的时候要用到这些插件！ -->
    <plugins>
        <!-- plugin 标签：这是我要指定的一个具体的插件 -->
        <plugin>
            <!-- 插件的坐标。此处引用的 maven-compiler-plugin 插件不是第三方的，是一个 Maven 自带的插件。 -->
            <groupId>org.apache.maven.plugins</groupId>
            <artifactId>maven-compiler-plugin</artifactId>
            <version>3.1</version>
            
            <!-- configuration 标签：配置 maven-compiler-plugin 插件 -->
            <configuration>
                <!-- 具体配置信息会因为插件不同、需求不同而有所差异 -->
                <source>1.8</source>
                <target>1.8</target>
                <encoding>UTF-8</encoding>
            </configuration>
        </plugin>
    </plugins>
</build>
```

* 这个功能还可以通过在 properties 标签中配置 maven.compiler.source 属性来实现。所以我们也经常会看到类似这样的配置：
  * 该方法利用，Java 编译器命令时传入的 -target 参数和调用 Java 编译器命令时传入的 -source 参数实现。
  * javac -source 发行的源码版本
  * javac -target 发行生成的class文件版本

```xml
<properties>
    <maven.compiler.source>1.8</maven.compiler.source>
    <maven.compiler.target>1.8</maven.compiler.target>
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
</properties>
```

### 6.4.4 典型应用：SpringBoot 定制化打包

默认情况下 Maven 调用 maven-jar-plugin 插件的 jar 目标，生成普通的 jar 包。无法生成spring-boot的可运行包。

引入springboot插件

```xml
<build>
	<plugins>
		<plugin>
			<groupId>org.springframework.boot</groupId>
			<artifactId>spring-boot-maven-plugin</artifactId>
			<version>2.5.5</version>
		</plugin>
	</plugins>
</build>
```

插件的7个目标：

| 目标名称                | 作用                                                         |
| ----------------------- | ------------------------------------------------------------ |
| spring-boot:build-image | Package an application into a OCI image using a buildpack.   |
| spring-boot:build-info  | Generate a build-info.properties file based on the content of the current MavenProject. |
| spring-boot:help        | Display help information on spring-boot-maven-plugin. Call mvn spring-boot:help -Ddetail=true -Dgoal=<goal-name> to display parameter details. |
| spring-boot:repackage   | Repackage existing JAR and WAR archives so that they can be executed from the command line using java -jar. With layout=NONE can also be used simply to package a JAR with nested dependencies (and no main class, so not executable). |
| spring-boot:run         | Run an application in place.                                 |
| spring-boot:start       | Start a spring application. Contrary to the run goal, this does not block and allows other goals to operate on the application. This goal is typically used in integration test scenario where the application is started before a test suite and stopped after. |
| spring-boot:stop        | Stop an application that has been started by the 'start' goal. Typically invoked once a test suite has completed |

### 6.4.5 典型应用：Mybatis 逆向工程

```xml
<!-- 控制 Maven 在构建过程中相关配置 -->
<build>
		
	<!-- 构建过程中用到的插件 -->
	<plugins>
		
		<!-- 具体插件，逆向工程的操作是以构建过程中插件形式出现的 -->
		<plugin>
			<groupId>org.mybatis.generator</groupId>
			<artifactId>mybatis-generator-maven-plugin</artifactId>
			<version>1.3.0</version>
	
			<!-- 插件的依赖 -->
			<dependencies>
				
				<!-- 逆向工程的核心依赖 -->
				<dependency>
					<groupId>org.mybatis.generator</groupId>
					<artifactId>mybatis-generator-core</artifactId>
					<version>1.3.2</version>
				</dependency>
					
				<!-- 数据库连接池 -->
				<dependency>
					<groupId>com.mchange</groupId>
					<artifactId>c3p0</artifactId>
					<version>0.9.2</version>
				</dependency>
					
				<!-- MySQL驱动 -->
				<dependency>
					<groupId>mysql</groupId>
					<artifactId>mysql-connector-java</artifactId>
					<version>5.1.8</version>
				</dependency>
			</dependencies>
		</plugin>
	</plugins>
</build>
```

## 6.5 依赖配置补充

### 6.5.1 import

* scope标签

* 管理依赖最基本的办法是继承父工程，但是和 Java 类一样，Maven 也是单继承的。如果不同体系的依赖信息封装在不同 POM 中了，没办法继承多个父工程怎么办？这时就可以使用 import 依赖范围。

  典型案例当然是在项目中引入 SpringBoot、SpringCloud 依赖：

* import 依赖范围使用要求：

  - 打包类型必须是 pom
  - 必须放在 dependencyManagement 中

```xml
<dependencyManagement>
    <dependencies>

        <!-- SpringCloud 依赖导入 -->
        <dependency>
            <groupId>org.springframework.cloud</groupId>
            <artifactId>spring-cloud-dependencies</artifactId>
            <version>Hoxton.SR9</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <!-- SpringCloud Alibaba 依赖导入 -->
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-dependencies</artifactId>
            <version>2.2.6.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>

        <!-- SpringBoot 依赖导入 -->
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-dependencies</artifactId>
            <version>2.3.6.RELEASE</version>
            <type>pom</type>
            <scope>import</scope>
        </dependency>
    </dependencies>
    
</dependencyManagement>
```

### 6.5.2 system

- scope标签
- 以 Windows 系统环境下开发为例，假设现在 D:\tempare\xx-maven-test-aaa-1.0-SNAPSHOT.jar 想要引入到我们的项目中，此时我们就可以将依赖配置为 system 范围：

```xml
<dependency>
    <groupId>com.atguigu.maven</groupId>
    <artifactId>atguigu-maven-test-aaa</artifactId>
    <version>1.0-SNAPSHOT</version>
    <systemPath>D:\tempare\atguigu-maven-test-aaa-1.0-SNAPSHOT.jar</systemPath>
    <scope>system</scope>
</dependency>
```

### 6.5.3 runtime

- scope标签
- 专门用于编译时不需要，但是运行时需要的 jar 包。比如：编译时我们根据接口调用方法，但是实际运行时需要的是接口的实现类。典型案例是：

```xml
<!--热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### 6.5.4 可选依赖

- optional标签
- 可选其实就是『可有可无』。官网的解释是：Project X 依赖 Project A，A 中一部分 X 用不到的代码依赖了 B，那么对 X 来说 B 就是『可有可无』的。

```xml
<!--热部署 -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
    <scope>runtime</scope>
    <optional>true</optional>
</dependency>
```

### 6.5.5 版本仲裁（版本冲突选择）

- 最短路径优先。引用的路径最短就引用哪一个
- 路径相同时先声明者优先。路径相同就看哪个先声明
- 因为maven没版本确认哪个版本式最新的。

## 6.7 Maven 自定义插件

### 6.7.1 创建工程

自行创建

### 6.7.2 设定打包方式

```xml
<packaging>maven-plugin</packaging>
```

### 6.7.3 引入依赖

下面两种方式二选一：

**[1]将来在文档注释中使用注解**

```xml
<dependency>
    <groupId>org.apache.maven</groupId>
    <artifactId>maven-plugin-api</artifactId>
    <version>3.5.2</version>
</dependency>
```

**[2]将来直接使用注解**

```xml
<dependency>
    <groupId>org.apache.maven.plugin-tools</groupId>
    <artifactId>maven-plugin-annotations</artifactId>
    <version>3.5.2</version>
</dependency>
```

### 6.7.4 创建 Mojo 类

Mojo 类是一个 Maven 插件的核心类。

Mojo 这个单词的意思是：Maven Old Java Object，其实 mojo 这个单词本身包含魔力;符咒(袋);护身符;(人的)魅力的含义，Maven 用 Mojo 是因为它是对 POJO 开的一个小玩笑。

两种方式：1、实现Mojo接口（比较困难）    2、继承AbstractMojo 是需要实现 execute() 一个方法即可。

```java
/**
 * @goal sayHello   在文档中使用注解，表示目标
 */
public class MyHelloPlugin extends AbstractMojo {
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("---> This is my first maven plugin. <---");
    }
}

```

直接在类上标记注解

对应 pom.xml 中的依赖：maven-plugin-annotations

```java
// name 属性：指定目标名称
@Mojo(name = "firstBlood")
public class MyPluginOfFistBlood extends AbstractMojo {
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        getLog().info("---> first blood <---");
    }
}
```

### 6.7.5插件配置

要在后续使用插件，就必须至少将插件安装到本地仓库。

将插件坐标中的 groupId 部分注册到 **settings.xml** 中。

```xml
<pluginGroups>
	<!-- pluginGroup
	 | Specifies a further group identifier to use for plugin lookup.
	<pluginGroup>com.your.plugins</pluginGroup>
	-->
	<pluginGroup>com.xx.maven</pluginGroup>
</pluginGroups>
```

### 6.7.6 使用插件

Maven 根据插件的 artifactId 来识别插件前缀。artifactId 必须规范，也就是下面两种情况，否则无法识别。

[1]前置匹配

- 匹配规则：${prefix}-maven-plugin
- artifactId：hello-maven-plugin
- 前缀：hello

[2]中间匹配

- 匹配规则：maven-${prefix}-plugin
- artifactId：maven-good-plugin
- 前缀：good

在命令行直接用

```sh
mvn hello:sayHello
```

### 6.7.7 配置到build里

```xml
<build>
	<plugins>
		<plugin>
			<groupId>com.xx.maven</groupId>
			<artifactId>hello-maven-plugin</artifactId>
			<version>1.0-SNAPSHOT</version>
			<executions>
				<execution>
                    <id>hello</id>
                    <!-- 指定和目标关联的生命周期阶段 -->
					<phase>clean</phase>
					<goals>
						<goal>sayHello</goal>
					</goals>
				</execution>
                <execution>
                    <id>blood</id>
                    <phase>validate</phase>
                    <goals>
                        <goal>firstBlood</goal>
                    </goals>
                </execution>
			</executions>
		</plugin>
	</plugins>
</build>
```

## 6.8 profile 详解

profile 轮廓; 简介; 面部的侧影; 概述; 传略; 印象; 形象; 外形;

这里我们可以对接 profile 这个单词中『侧面』这个含义：项目的每一个运行环境，相当于是项目整体的一个侧面。

### 6.8.1 作用

在 Maven 中，使用 profile 机制来管理不同环境下的配置信息。其他框架中也有，在构建工具中配置也违反了『高内聚，低耦合』的原则。所以 Maven 的 profile 我们了解一下即可，不必深究。

默认 profile：其实即使我们在 pom.xml 中不配置 profile 标签，也已经用到 profile了。因为根标签 project 下所有标签相当于都是在设定默认的 profile。所以project 标签下除了 modelVersion 和坐标标签之外，其它标签都可以配置到 profile 中。

### 6.8.2 profile 配置

从外部视角来看，profile 可以在下面两种配置文件中配置：

- settings.xml：全局生效。其中我们最熟悉的就是配置 JDK 1.8。
- pom.xml：当前 POM 生效

从内部视角来看，配置 profile 有如下语法要求：

[1] profiles/profile 标签

- 由于 profile 天然代表众多可选配置中的一个所以由复数形式的 profiles 标签统一管理。
- 由于 profile 标签覆盖了 pom.xml 中的默认配置，所以 profiles 标签通常是 pom.xml 中的最后一个标签。

[2]id 标签

每个 profile 都必须有一个 id 标签，指定该 profile 的唯一标识。这个 id 标签的值会在命令行调用 profile 时被用到。这个命令格式是：-D<profile id>。

[3]其它允许出现的标签

一个 profile 可以覆盖项目的最终名称、项目依赖、插件配置等各个方面以影响构建行为。

- build
  - defaultGoal
  - finalName
  - resources
  - testResources
  - plugins
- reporting
- modules
- dependencies
- dependencyManagement
- repositories
- pluginRepositories
- properties

### 6.8.3 激活 profile

* POM 中没有在 profile 标签里的就是默认的 profile，当然默认被激活。
* 基于环境信息激活

环境信息包含：JDK 版本、操作系统参数、文件、属性等各个方面。一个 profile 一旦被激活，那么它定义的所有配置都会覆盖原来 POM 中对应层次的元素。大家可以参考下面的标签结构：

```xml
<profile>
	<id>dev</id>
    <activation>
        <!-- 配置是否默认激活 -->
    	<activeByDefault>false</activeByDefault>
        <jdk>1.5</jdk>
        <os>
        	<name>Windows XP</name>
            <family>Windows</family>
            <arch>x86</arch>
            <version>5.1.2600</version>
        </os>
        <property>
        	<name>mavenVersion</name>
            <value>2.0.5</value>
        </property>
        <file>
        	<exists>file2.properties</exists>
            <missing>file1.properties</missing>
        </file>
    </activation>
</profile>
```

这里有个问题是：多个激活条件之间是什么关系呢？

- Maven **3.2.2 之前**：遇到第一个满足的条件即可激活——**或**的关系。
- Maven **3.2.2 开始**：各条件均需满足——**且**的关系。

### 6.8.4 命令行激活

列出活动的 profile

```sh
# 列出所有激活的 profile，以及它们在哪里定义
mvn help:active-profiles
```

指定某个具体 profile

```xml
mvn compile -P<profile id>
```

### 6.8.5 具体举例

```xml
<profiles>
    <profile>
        <id>myJDKProfile</id>
        <!-- build 标签：意思是告诉 Maven，你的构建行为，我要开始定制了！ -->
        <build>
            <!-- plugins 标签：Maven 你给我听好了，你给我构建的时候要用到这些插件！ -->
            <plugins>
                <!-- plugin 标签：这是我要指定的一个具体的插件 -->
                <plugin>
                    <!-- 插件的坐标。此处引用的 maven-compiler-plugin 插件不是第三方的，是一个 Maven 自带的插件。 -->
                    <groupId>org.apache.maven.plugins</groupId>
                    <artifactId>maven-compiler-plugin</artifactId>
                    <version>3.1</version>

                    <!-- configuration 标签：配置 maven-compiler-plugin 插件 -->
                    <configuration>
                        <!-- 具体配置信息会因为插件不同、需求不同而有所差异 -->
                        <source>1.6</source>
                        <target>1.6</target>
                        <encoding>UTF-8</encoding>
                    </configuration>
                </plugin>
            </plugins>
        </build>
    </profile>
</profiles>
```

执行构建命令，就是激活的myJDKProfile的profile

```sh
mvn clean test -PmyJDKProfile
```

### 6.8.6 资源过滤

* Maven 为了能够通过 profile 实现各不同运行环境切换，提供了一种『资源属性过滤』的机制。通过属性替换实现不同环境使用不同的参数。

* 操作演示
  * 配置profile

```xml
<profiles>
    <profile>
        <id>devJDBCProfile</id>
        <properties>
            <dev.jdbc.user>root</dev.jdbc.user>
            <dev.jdbc.password>atguigu</dev.jdbc.password>
            <dev.jdbc.url>http://localhost:3306/db_good</dev.jdbc.url>
            <dev.jdbc.driver>com.mysql.jdbc.Driver</dev.jdbc.driver>
        </properties>
        <build>
            <resources>
                <resource>
                    <!-- 表示为这里指定的目录开启资源过滤功能 -->
                    <directory>src/main/resources</directory>

                    <!-- 将资源过滤功能打开 -->
                    <filtering>true</filtering>
                </resource>
            </resources>
        </build>
    </profile>
</profiles>
```

* 创建待处理的资源文件

```properties
dev.user=${dev.jdbc.user}
dev.password=${dev.jdbc.password}
dev.url=${dev.jdbc.url}
dev.driver=${dev.jdbc.driver}
```

* 执行处理资源命令

```shell
mvn clean resources:resources -PdevJDBCProfile
```

执行后打包的目录中的配置会替换为profile中的值。

## 6.9 resource目录下的排除和包含

在 resource 标签下看到 includes 和 excludes 标签。它们的作用是：

- includes：指定执行 resource 阶段时要包含到目标位置的资源
- excludes：指定执行 resource 阶段时要排除的资源

```xml
build>
    <resources>
        <resource>
            <!-- 表示为这里指定的目录开启资源过滤功能 -->
            <directory>src/main/resources</directory>

            <!-- 将资源过滤功能打开 -->
            <filtering>true</filtering>

            <includes>
                <include>*.properties</include>
            </includes>

            <excludes>
                <exclude>happy.properties</exclude>
            </excludes>
        </resource>
    </resources>
</build>
```

# 7、搭建Nexus及部署

## 7.1 nexus搭建

https://www.sonatype.com/products/repository-oss-download

https://sonatype-download.global.ssl.fastly.net/repository/downloads-prod-group/3/nexus-3.50.0-01-unix.tar.gz

下载好后，上传到 Linux 系统，解压后即可使用，不需要安装。但是需要**注意**：必须提前安装 JDK。

```
yum search jdk
找到版本
yum install 具体版本号
```

解压缩：

```
tar -zxvf **.tar.gz
```

压缩：

```
tar -zcvf 压缩名.tar.gz 被压缩文件名
```

启动：

```
/root/nexus-3.31.1-01/bin/nexus start
```

查看状态：

```
/root/nexus-3.31.1-01/bin/nexus status
```

安装netstat命令

```
yum install net-tools -y
```

查看端口号占用：

```
netstat -anp | grep java
#访问地址是8081

tcp        0      0 0.0.0.0:8081            0.0.0.0:*               LISTEN      8536/java
tcp        0      0 127.0.0.1:40765         0.0.0.0:*               LISTEN      8536/java
unix  3      [ ]         STREAM     CONNECTED     27529    8536/java
unix  3      [ ]         STREAM     CONNECTED     30824    8536/java
unix  2      [ ]         STREAM     CONNECTED     29998    8536/java
unix  2      [ ]         STREAM     CONNECTED     27526    8536/java
unix  3      [ ]         STREAM     CONNECTED     30825    8536/java
unix  3      [ ]         STREAM     CONNECTED     27528    8536/java

```

关闭防火墙

```
#停止防火墙
systemctl stop firewalld.service
#永久关闭防火墙防火墙
systemctl disable firewalld.service
```

首次登陆：

用户名：admin

密码会在页面提示你在哪个文件里

登陆进去后，会修改密码



| 仓库类型 | 说明                                           |
| -------- | ---------------------------------------------- |
| proxy    | 某个远程仓库的代理                             |
| group    | 存放：通过 Nexus 获取的第三方 jar 包           |
| hosted   | 存放：本团队其他开发人员部署到 Nexus 的 jar 包 |

| 仓库名称        | 说明                                                         |
| --------------- | ------------------------------------------------------------ |
| maven-central   | Nexus 对 Maven 中央仓库的代理                                |
| maven-public    | Nexus 默认创建，供开发人员下载使用的组仓库                   |
| maven-releasse  | Nexus 默认创建，供开发人员部署自己 jar 包的宿主仓库 要求 releasse 版本 |
| maven-snapshots | Nexus 默认创建，供开发人员部署自己 jar 包的宿主仓库 要求 snapshots 版本 |

## 7.2 配置本地nexus地址

在settings中配置

```xml
<mirror>
	<id>nexus-mine</id>
	<mirrorOf>central</mirrorOf>
	<name>Nexus mine</name>
	<url>http://192.168.15.132:8081/repository/maven-public/</url>
</mirror>
```

还需要配置登陆密码

```
<server>
  <!--id与mirror的对应-->
  <id>nexus-mine</id>
  <username>admin</username>
  <password>自己的密码</password>
</server>
```

IDEA配置了无法访问

查看Settings->找到maven->把work offline勾选去掉

## 7.3 将 jar 包部署到 Nexus

POM文件配置

snapshotRepository 的 id 标签也必须和 settings.xml 中指定的 mirror 标签的 id 属性一致。

```xml
<distributionManagement>
    <snapshotRepository>
        <id>nexus-mine</id>
        <name>Nexus Snapshot</name>
        <url>http://192.168.15.132:8081/repository/cmaven-snapshots/</url>
    </snapshotRepository>
</distributionManagement>
```

执行部署命令：

```sh
mvn deploy
```

## 7.4 引用别人部署的 jar 包

- 默认访问的 Nexus 仓库：maven-public
- 存放别人部署 jar 包的仓库：maven-snapshots

配置pom

```xml
<repositories>
    <repository>
        <id>nexus-mine</id>
        <name>nexus-mine</name>
        <url>http://192.168.15.132:8081/repository/maven-snapshots/</url>
        <snapshots>
            <enabled>true</enabled>
        </snapshots>
        <releases>
            <enabled>true</enabled>
        </releases>
    </repository>
</repositories>
```

## 7.5 修改nexus仓库代理

设置（齿轮图标）->Repositories->maven-central->Proxy->Remote storage地址，修改后保存

阿里云：http://maven.aliyun.com/nexus/content/groups/public

# 8、解决jar包冲突

* 引入了两个jar包有相同的类，导致。

* 解决思路

  * 第一步：把彼此冲突的 jar 包找到
  * 第二步：在冲突的 jar 包中选定一个。具体做法无非是通过 exclusions 排除依赖，或是明确声明依赖。

* IDEA 的 Maven Helper 插件

  这个插件是 IDEA 中安装的插件，不是 Maven 插件。它能够给我们罗列出来同一个 jar 包的不同版本，以及它们的来源。但是对不同 jar 包中同名的类没有办法。

* Maven 的 enforcer 插件

配置插件

```xml
<build>
    <pluginManagement>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-enforcer-plugin</artifactId>
                <version>1.4.1</version>
                <executions>
                    <execution>
                        <id>enforce-dependencies</id>
                        <phase>validate</phase>
                        <goals>
                            <goal>display-info</goal>
                            <goal>enforce</goal>
                        </goals>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>org.codehaus.mojo</groupId>
                        <artifactId>extra-enforcer-rules</artifactId>
                        <version>1.0-beta-4</version>
                    </dependency>
                </dependencies>
                <configuration>
                    <rules>
                        <banDuplicateClasses>
                            <findAllDuplicates>true</findAllDuplicates>
                        </banDuplicateClasses>
                    </rules>
                </configuration>
            </plugin>
        </plugins>
    </pluginManagement>
</build>
```

执行命令：可以检查到冲突的类

```sh
mvn clean package enforcer:enforce
```

# 9、体系外jar包引入

将该 jar 包安装到 Maven 仓库，然后就可以引用了。

例如（Windows 系统下使用 ^ 符号换行；Linux 系统用 \）

```sh
mvn install:install-file -Dfile=[体系外 jar 包路径] \
-DgroupId=[给体系外 jar 包强行设定坐标] \
-DartifactId=[给体系外 jar 包强行设定坐标] \
-Dversion=1 \
-Dpackage=jar
```
