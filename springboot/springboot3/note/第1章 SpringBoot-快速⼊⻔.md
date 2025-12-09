---

---

# SpringBoot全套

## SpringBoot核⼼特性

1. SpringBoot-快速⼊⻔
2. SpringBoot-Web开发
3. SpringBoot-数据访问
4. SpringBoot-基础特性
5. SpringBoot-核⼼原理
6. 附录：SpringBoot改变&新特性

## SpringBoot_场景整合

1. NoSQL
2. 接⼝⽂档远
3. 程调⽤
4. 消息服务Web安全
5. 可观测性
6. AOT

## SpringBoot响应式编程

1. Reactor核⼼
2. SpringWebflux
3. RDBC
4. SpringSecurityReactive

## SpringBoot分流

### Reactive Stack（响应式技术栈）

* Netty,Servlet3.1+ Containers
* Reactive Streams Adapters
* Spring Securitry Reactive
* Spring WebFlux
* Spring Data Reactive Repositories(Mongo,Cassandra,Redis,Couchbase,R2DBC)

### Servlet Stack（Servlet 技术栈）

* Servlet Containers
* Servlet API
* Spring Securitry 
* Spring MVC
* Spring Data Repositories(JDBC,JPA,NoSQL)

# 第1章 SpringBoot-快速⼊⻔

## 1.1 简介

项⽬源码： https://gitee.com/leifengyang/spring-boot-3

### 1.1.1 前置知识 

- Java17（最低要求）
- Spring、SpringMVC、MyBatis（相关框架）
- Maven、IDEA （工具）

## 1.2 环境要求

| 环境&⼯具          | 版本（or later |
| ------------------ | -------------- |
| SpringBoot         | 3.0.5+         |
| IDEA               | 2021.2.1+      |
| Java               | 17+            |
| Maven              | 3.5+           |
| Tomcat             | 10.0+          |
| Servlet            | 5.0+           |
| GraalVM Community  | 22.3+          |
| Native Build Tools | 0.9.19         |

## 1.3 SpringBoot是什么 

SpringBoot 帮我们简单、快速地创建⼀个独立的、生产级别的 Spring 应用（说明：SpringBoot 底层是Spring）

大多数 SpringBoot 应用只需要编写少量配置即可快速整合 Spring 平台以及第三方技术 **特性：**

* 快速创建独立 Spring 应用 
  *  SSM：导包-->写配置-->启动运行
* 直接嵌⼊Tomcat、Jetty or Undertow（无需部署 war 包）【Servlet容器】
  * linux  java tomcat mysql： war 放到 tomcat 的 webapps下
  *  jar: java环境；  java -jar 
* 重点：提供可选的 starter，简化应用整合 
  * 场景启动器（starter）：web、json、邮件、oss（对象存储）、异步、定时任务、缓存... 
  * 导包一堆，控制好版本。 
  * 为每⼀种场景准备了一个依赖； web-starter。mybatis-starter 
* 重点： 按需自动配置 Spring 以及 第三方库
  *  如果这些场景我要使用（生效）。这个场景的所有配置都会自动配置好。
  *  约定⼤于配置：每个场景都有很多默认配置。 
  * 自定义：配置⽂件中修改几项就可以 
* 提供生产级特性：如 监控指标、健康检查、外部化配置等 
  * 监控指标、健康检查（k8s）、外部化配置 
* 无代码生成、 无xml 

总结：简化开发，简化配置，简化整合，简化部署，简化监控，简化运维

## 1.4 快速体验

场景：浏览器发送/hello请求，返回"Hello,Spring Boot 3!

### 1.4.1 开发流程

#### 1.4.1.1 创建项目

```xml
<!--    所有springboot项⽬都必须继承⾃ spring-boot-starter-parent -->
 <parent>
 	<groupId>org.springframework.boot</groupId>
 	<artifactId>spring-boot-starter-parent</artifactId>
 	<version>3.0.5</version>
 </parent>
```

#### 1.4.1.2 导入场景

```xml
<dependencies>
 <!--   web开发的场景启动器 -->
 	<dependency>
 		<groupId>org.springframework.boot</groupId>
 		<artifactId>spring-boot-starter-web</artifactId>
 	</dependency>
</dependencies>
```

#### 1.4.1.3 写主程序

```java
@SpringBootApplication //这是⼀个SpringBoot应⽤
public class MainApplication {
 	public static void main(String[] args) {
 		SpringApplication.run(MainApplication.class,args);
 	}
}
```

#### 1.4.1.4 写业务

```java
@RestController
public class HelloController {
	@GetMapping("/hello")
	public String hello(){
		return "Hello,Spring Boot 3!";
	}
}
```

#### 1.4.1.5 测试

默认启动访问：localhost:8080

#### 1.4.1.6 打包

```xml
<!--    SpringBoot应⽤打包插件-->
<build>
	<plugins>
		<plugin>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-maven-plugin</artifactId>
	</plugin>
	</plugins>
</build>
```

mvn clean package 把项目打成可执行的jar包

java -jar demo.jar 启动项目



### 1.4.2 特性小结

**1. 简化整合**

导入相关的场景，拥有相关的功能——场景启动器

默认支持的所有场景：https://docs.spring.io/spring-boot/docs/current/reference/html/using.html#using.build-systems.starters

- 官方提供的场景：命名为：`spring-boot-starter-*`
- 第三方提供场景：命名为：`*-spring-boot-starter`

场景一导入，万物皆就绪

**2. 简化开发**

无需编写任何配置，直接开发业务

**3. 简化配置**

`application.properties`：

- 集中式管理配置。只需要修改这个文件就行 。
- 配置基本都有默认值
- 能写的所有配置都在： https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties

**4. 简化部署**

打包为可执行的jar包。

只需linux服务器上有java环境。

**5. 简化运维**

修改配置（外部放一个application.properties文件，外部有，项目内部也有，以外部优先）、监控、健康检查。

.....

### 1.4.3 Spring Initializr 创建向导

一键创建好整个项目结构

![56fbf4f1ed01b369e8372d0e5b724050](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\56fbf4f1ed01b369e8372d0e5b724050.png)

## 1.5 应用分析

### 1.5.1 依赖管理机制

思考：

1、为什么导入`starter-web`所有相关依赖都导入进来？

- 开发什么场景，就导入什么场景启动器。
- **maven依赖传递原则。A-B-C： A就拥有B和C**
- 导入 场景启动器。 场景启动器 自动把这个场景的所有核心依赖全部导入进来

2、为什么版本号都不用写？

- 每个boot项目都有一个父项目`spring-boot-starter-parent`
- parent的父项目是`spring-boot-dependencies`
- 父项目 **版本仲裁中心**，把所有常见的jar的依赖版本都声明好了。
- 比如：`mysql-connector-j`

3、自定义版本号

* 利用maven的就近原则
  * 第一种：直接在当前项目`properties`标签中声明父项目用的版本属性的key
  * 第二种：直接在**导入依赖的时候声明版本**

4、第三方的jar包

- boot父项目没有管理的需要自行声明好

![image.png](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\2566191a5ae036d9eab0e2ace7ae935a.png)

### 1.5.2 自动配置机制

#### 1.5.2.1 初步理解

- **自动配置**的 Tomcat、SpringMVC 等
  - 导入场景，容器中就会自动配置好这个场景的核心组件。
  - 以前：DispatcherServlet、ViewResolver、CharacterEncodingFilter....
  - 现在：自动配置好的这些组件
  - 验证：容器中有了什么组件，就具有什么功能

- 默认的包扫描规则
  - @SpringBootApplication 标注的类就是主程序类
  - SpringBoot只会扫描主程序所在的包
  - 及其下面的子包，自动的component-scan功能
  - 自定义扫描路径（两种方式）
    - @SpringBootApplication(scanBasePackages = "com.atguigu")
    - @ComponentScan("com.atguigu") 直接指定扫描的路径

- 配置默认值
  - 配置文件的所有配置项是和某个类的对象值进行一一绑定的。
  - 绑定了配置文件中每一项值的类： 属性类。
  - 比如：
    - ServerProperties 绑定了所有Tomcat服务器有关的配置
    - MultipartProperties 绑定了所有文件上传相关的配置
    - ....参照[官方文档](https://docs.spring.io/spring-boot/docs/current/reference/html/application-properties.html#appendix.application-properties.server)：或者参照 绑定的 属性类。

- **按需加载自动配置★★★**
  - 导入场景spring-boot-starter-web
  - 场景启动器除了会导入相关功能依赖，还会导入一个spring-boot-starter（也是一个依赖），是所有starter的starter，基础核心starter
  - spring-boot-starter导入了一个包 spring-boot-autoconfigure。包里面都是各种场景的AutoConfiguration **自动配置类**
  - 虽然全场景的自动配置都在 spring-boot-autoconfigure这个包，但是不是全都开启的。
    - 导入哪个场景就开启哪个自动配置

总结： 导入场景启动器、触发 spring-boot-autoconfigure这个包的自动配置生效、容器中就会具有相关场景的功能

#### 1.5.2.2 完整流程

思考：

1、SpringBoot怎么实现导一个`starter`、写一些简单配置，应用就能跑起来，我们无需关心整合

2、为什么Tomcat的端口号可以配置在`application.properties`中，并且`Tomcat`能启动成功？

3、导入场景后哪些自动配置能生效？

**以下原理图要完全理解★★★★★**

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\77ce0a8b9134caa7904bc6cd9569d2cc.png)

**自动配置流程细节梳理：**

**1、**导入场景启动器spring-boot-starter-web：导入了web开发场景

- 1、场景启动器导入了相关场景的所有依赖：spring-boot-starter-json、spring-boot-starter-tomcat、springmvc
- 2、每个场景启动器都引入了一个spring-boot-starter，核心场景启动器。
- 3、核心场景启动器引入了spring-boot-autoconfigure包。
- 4、spring-boot-autoconfigure里面囊括了所有场景的所有配置。
- 5、只要这个包下的所有类都能生效，那么相当于SpringBoot官方写好的整合功能就生效了。
- 6、SpringBoot默认却扫描不到 spring-boot-autoconfigure下写好的所有**配置类**。（这些**配置类**给我们做了整合操作），**默认只扫描主程序所在的包**。（却利用主程序的**@EnableAutoConfiguration**注解，批量地将142个配置类全部导入进来）

**2、主程序**：@SpringBootApplication

- 1、@SpringBootApplication由三个注解组成@SpringBootConfiguration、@EnableAutoConfiguration、@ComponentScan

- 2、SpringBoot默认只能扫描自己主程序所在的包及其下面的子包，扫描不到 spring-boot-autoconfigure包中官方写好的**配置类**

- 3、@EnableAutoConfiguration：SpringBoot开启自动配置的核心
  - 1. 是由@Import(AutoConfigurationImportSelector.class)提供功能：批量给容器中导入组件。
  - 2. SpringBoot启动会默认加载 142个配置类。
  - 3. 这**142个配置类**来自于spring-boot-autoconfigure下 META-INF/spring/**org.springframework.boot.autoconfigure.AutoConfiguration**.imports文件指定的
  - 项目启动的时候利用 @Import 批量导入组件机制把 spring-boot-autoconfigure 包下的142 xxxxAutoConfiguration类导入进来（**自动配置类**）
  - 虽然导入了142个自动配置类
- 4、但是按需生效：
  - 并不是这142个自动配置类都能生效
  - 每一个自动配置类，都有条件注解@ConditionalOnxxx，只有条件成立，才能生效

**3、xxxxAutoConfiguration自动配置类**

- **1、给容器中使用@Bean 放一堆组件。**
- 2、每个自动配置类都可能有这个注解@EnableConfigurationProperties(ServerProperties.class)，用来把配置文件中配的指定前缀的属性值封装（绑定）到 xxxProperties中，并且将xxxProperties属性类注入容器
- 3、以Tomcat为例：把服务器的所有配置都是以server开头的。配置都封装到了属性类中。给容器中放Tomcat自定义工厂组件的时候，要求传入ServerProperties。（ServerProperties在上一步中已经完成属性绑定和注入容器；@Bean标注的方法上有参数，而参数在容器中有，则自动从容器中拿）![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\56ef287d1c084094919db51cd69f3fd1.png)![img](https://i-blog.csdnimg.cn/direct/89c29c3d489e4e1d9950eb63ae875ccc.png)
- 4、给容器中放的所有组件的一些核心参数（如端口号port），都来自于xxxProperties。xxxProperties都是和配置文件绑定。
- 最终实现一个效果：只需要改配置文件的值，核心组件的底层参数都能修改

4、写业务，全程无需关心各种整合（底层这些整合写好了，而且也生效了）



**★★★★★核心流程总结★★★★★：**

1、导入starter，就会导入autoconfigure包。

2、autoconfigure 包里面 有一个文件 META-INF/spring/**org.springframework.boot.autoconfigure.AutoConfiguration**.imports,里面指定了所有启动要加载的自动配置类，共142个，都是以**xxxAutoConfiguration**命名的

![img](https://i-blog.csdnimg.cn/direct/ca1e06accbf945c8bde446c51ab0dab6.png)

3、@EnableAutoConfiguration 会自动的把上面文件里面写的所有自动配置类都导入进来。xxxAutoConfiguration 是有条件注解进行按需加载

4、xxxAutoConfiguration给容器中导入一堆组件，组件都是从 xxxProperties中提取属性值

5、xxxProperties又是和配置文件进行了绑定

效果：导入starter、修改配置文件，就能修改底层行为。

#### 1.5.2.3 如何学好SpringBoot

框架的框架、底层基于Spring。能调整每一个场景的底层行为。100%项目一定会用到底层自定义

摄影：

- 傻瓜：自动配置好。
- 单反：焦距、光圈、快门、感光度....
- 傻瓜+单反：SpringBoot就类似这种

1. 理解自动配置原理

   导入starter --> 生效xxxxAutoConfiguration --> 组件\ --> xxxProperties --> 配置文件

2. 理解其他框架底层

   拦截器

3. 可以随时定制化任何组件

   a.配置文件

   b.自定义组件

普通开发：导入starter，Controller、Service、Mapper、偶尔修改配置文件

高级开发：自定义组件、自定义配置、自定义starter

核心：

- 这个场景自动配置导入了哪些组件，我们能不能Autowired进来使用
- 能不能通过修改配置改变组件的一些默认参数
- 需不需要自己完全定义这个组件
- 场景定制化

**最佳实战**：

- **选场景，导入到项目**
  - 官方：starter
  - 第三方：去仓库搜

- **写配置，改配置文件关键项**
  - 数据库参数（连接地址、账号密码...）

- 分析这个场景给我们导入了哪些能用的组件
  - 自动装配这些组件进行后续使用
  - 不满意boot提供的自动配好的默认组件
    - **定制化**
      - 改配置
      - 自定义组件

整合redis：

- 选场景

  ：spring-boot-starter-data-redis

  - 场景名+AutoConfiguration 拼一下，一般就是这个场景的自动配置类![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\972b44f34aa04af88be4cdf4ef5cccfb.png)

- 写配置：

  - 分析到这个场景的自动配置类开启了哪些属性绑定关系
  - @EnableConfigurationProperties(RedisProperties.class)
  - 修改redis相关的配置![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\32c9bb6938f2435eb3b6011fc94153c0.png)![img](https://i-blog.csdnimg.cn/direct/1a1f0c4e3a224fd7b9daf08fc3f67e05.png)

- 分析组件：

  - 分析到 RedisAutoConfiguration 给容器中放了 StringRedisTemplate
  - 给业务代码中自动装配 StringRedisTemplate![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\5494905878a74be0a4a34d076aa2f88e.png)

- 定制化

  - 修改配置文件
  - 自定义组件，自己给容器中放一个 StringRedisTemplate（因为StringRedisTemplate被@ConditionalOnMissingBean注解标注，表示当容器中没有这个组件时，SpringBoot才给我们配置；如果容器中有这个组件，SpringBoot就不配了）

## 1.6 核心技能

### 1.6.1 常用注解

SpringBoot摒弃XML配置方式，改为全注解驱动

#### 1.6.1.1 组件注册

@Configuration、@SpringBootConfiguration

@Bean、@Scope

@Controller、 @Service、@Repository、@Component

@Import

@ComponentScan

步骤：

1、@Configuration 编写一个配置类

2、在配置类中，自定义方法给容器中注册组件。配合@Bean

3、或使用@Import 导入第三方的组件

```java
//@Import(FastsqlException.class) //给容器中放指定类型的组件，组件的名字默认是全类名

/**
 * 1、开启Sheep组件的属性绑定
 * 2、默认会把这个组件自己放到容器中
 */
@EnableConfigurationProperties(Sheep.class) //导入第三方写好的组件进行属性绑定
//SpringBoot默认只扫描自己主程序所在的包。如果导入第三方包，即使组件上标注了 @Component、@ConfigurationProperties 注解，也没用。因为组件都扫描不进来
@SpringBootConfiguration //这是一个配置类，替代以前的配置文件。配置类本身也是容器中的组件
//@Configuration
public class AppConfig {
    @Bean
    @ConfigurationProperties(prefix = "pig")
    public Pig pig(){
        return new Pig(); //我们自己new新pig
    }
    /**
     * 1、组件默认是单实例的
     * @return
     */
    @Scope("prototype")
    @Bean("userHaha") //替代以前的Bean标签。 组件在容器中的名字默认是方法名，可以直接修改注解的值
    public User user01(){
        var user = new User();
        user.setId(1L);
        user.setName("张三");
        return user;
    }
//    @Bean
//    public FastsqlException fastsqlException(){
//
//        return new FastsqlException();
//    }
}
```

#### 1.6.**1.2 条件注解**

如果注解指定的条件成立，则触发指定行为

*@ConditionalOnXxx*

@ConditionalOnClass：如果类路径中存在这个类，则触发指定行为

@ConditionalOnMissingClass：如果类路径中不存在这个类，则触发指定行为

@ConditionalOnBean：如果容器中存在这个Bean（组件），则触发指定行为

@ConditionalOnMissingBean：如果容器中不存在这个Bean（组件），则触发指定行为

> 场景：
>
> - 如果存在 `FastsqlException `这个类，给容器中放一个 `Cat `组件，名cat01，
> - 否则，就给容器中放一个 `Dog`组件，名dog01
> - 如果系统中有 `dog01`这个组件，就给容器中放一个 User组件，名zhangsan
> - 否则，就放一个User，名叫lisi

**@ConditionalOnBean（value=组件类型，name=组件名字）：判断容器中是否有这个类型的组件，并且名字是指定的值**

@ConditionalOnRepositoryType (org.springframework.boot.autoconfigure.data)

@ConditionalOnDefaultWebSecurity(org.springframework.boot.autoconfigure.security)
@ConditionalOnSingleCandidate(org.springframework.boot.autoconfigure.conditiwon)

@ConditionalOnWebApplication(org.springframework.boot.autoconfigure.condition)

@ConditionalOnWarDeployment(org.springframework.boot.autoconfigure.condition)

@ConditionalOnJndi (org.springframework.boot.autoconfigure.condition)

@ConditionalOnResource (org.springframework.boot.autoconfigure.condition)

@ConditionalOnExpression (org.springframework.boot.autoconfigure.condition)

**@ConditionalOnClass** (org.springframework.boot.autoconfigure.condition)

@ConditionalOnEnabledResourceChain(org.springframework.boot.autoconfigure.web)

**@ConditionalOnMissingClass**(org.springframework.boot.autoconfigure.condition)

@ConditionalOnNotWebApplication(org.springframework.boot.autoconfigure.condition)

@ConditionalOnProperty(org.springframework.boot.autoconfigure.condition)

@ConditionalOnCloudPlatform (org.springframework.boot.autoconfigure.condition)

**@ConditionalOnBean** (org.springframework.boot.autoconfigure.condition)

**@ConditionalOnMissingBean**(org.springframework.boot.autoconfigure.condition)

@ConditionalOnMissingFilterBean(org.springframework.boot.autoconfigure.web.servlet)

@Profile(org.springframework.context.annotation)

@ConditionalOnInitializedRestarter(org.springframework.boot.devtools.restart)

@ConditionalOnGraphQlSchema (org.springframework.boot.autoconfigure.graphql)

@ConditionalOnJava (org.springframework.boot.autoconfigure.condition)

```java
@ConditionalOnMissingClass(value="com.alibaba.druid.FastsqlException") //放在类级别，如果注解判断生效，则整个配置类才生效
@SpringBootConfiguration
public class AppConfig2 {
    @ConditionalOnClass(name="com.alibaba.druid.FastsqlException") //放在方法级别，单独对这个方法进行注解判断。
    @Bean
    public Cat cat01(){
        return new Cat();
    }
    @Bean
    public Dog dog01(){
        return new Dog();
    }
    @ConditionalOnBean(value = Dog.class)
    @Bean
    public User zhangsan(){
        return new User();
    }
    @ConditionalOnMissingBean(value = Dog.class)
    @Bean
    public User lisi(){
        return new User();
    }
}
```



#### 1.6.1.3 属性绑定

**@ConfigurationProperties： 声明组件的属性和配置文件哪些前缀开始项进行绑定**

**@EnableConfigurationProperties：快速注册注解：**

- **场景：**SpringBoot默认只扫描自己主程序所在的包。如果导入第三方包，即使组件上标注了 @Component、@ConfigurationProperties 注解，也没用。因为组件都扫描不进来，此时使用这个注解就可以快速进行属性绑定并把组件注册进容器

@EnableConfigurationProperties(Sheep.class)有两个功能：1、把配置文件绑定到属性类中；2、把类添加到容器中

> 将容器中任意**组件（Bean）的属性值**和**配置文件**的配置项的值**进行绑定**
>
> - **1、给容器中注册组件（@Component、@Bean）**
> - 2、使用@ConfigurationProperties 声明组件和配置文件的哪些配置项进行绑定

**属性配置文件解决中文乱码问题**

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\f829e36119e04d4b86f7fdc2851bb606.png)

第二种属性绑定方式：@EnableConfigurationProperties

更多注解参照：spring注解驱动开发【1-26集】

#### 1.4.2 YAML配置文件

> **痛点**：SpringBoot 集中化管理配置，`application.properties`
>
> **问题**：配置多以后难阅读和修改，**层级结构辨识度不高**



> YAML 是 "YAML Ain't a Markup Language"（YAML 不是一种标记语言）。在开发的这种语言时，YAML 的意思其实是："Yet Another Markup Language"（是另一种标记语言）。
>
> - 设计目标，就是**方便人类读写**
> - **层次分明**，更适合做配置文件
> - 使用`.yaml`或 `.yml`作为文件后缀

##### **1.4.2.1 基本语法**

- **大小写敏感**
- 使用**缩进表示层级关系，k: v，使用空格分割k,v**
- 缩进时不允许使用Tab键，只允许**使用空格**。换行
- 缩进的空格数目不重要，只要**相同层级**的元素**左侧对齐**即可
- **# 表示注释**，从这个字符一直到行尾，都会被解析器忽略。

支持的写法：

- **对象**：**键值对**的集合，如：映射（map）/ 哈希（hash） / 字典（dictionary）
- **数组**：一组按次序排列的值，如：序列（sequence） / 列表（list）
- **纯量**：单个的、不可再分的值，如：字符串、数字、bool、日期

##### **1.4.2.2 示例**

properties表示法 

yaml表示法 

##### **1.4.2.3 细节**

- birthDay 推荐写为 birth-day

- 文本

  ：

  - **单引号**不会转义【\n 则为普通字符串显示】
  - **双引号**会转义【\n会显示为**换行符**】

- 大文本

  - |开头，大文本写在下层，**保留文本格式**，**换行符正确显示**
  - \>开头，大文本写在下层，折叠换行符

- 多文档合并

  - 使用---可以把多个yaml文档合并在一个文档中，每个文档区依然认为内容独立

##### **1.4.2.4 小技巧：lombok**

简化JavaBean 开发。自动生成构造器、getter/setter、自动生成Builder模式等

 使用`@Data`等注解

#### 1.4.3 日志配置

规范：项目开发不要编写`System.out.println()`，应该用**日志**记录信息

![img](https://i-blog.csdnimg.cn/blog_migrate/3a240977eb4fcfd81fad213d687b4258.png)

日志门面是日志接口，相当于数据库中JDBC接口的概念

日志实现是日志接口的具体实现类，相当于数据库中导入mysql、oracle等驱动，真正实现CRUD操作数据库

SpringBoot默认采用 SLF4j + Logback（可以选择切换成其他组合）

**感兴趣日志框架关系与起源可参考**：[尚硅谷SpringBoot顶尖教程(springboot之idea版spring boot)_哔哩哔哩_bilibili](https://www.bilibili.com/video/BV1gW411W76m) 视频 21~27集

##### **1.4.3.1 简介**

\1. Spring使用commons-logging作为内部日志，但底层日志实现是开放的。可对接其他日志框架。

​    spring5及以后 commons-logging被spring直接自己写了。

\2. 支持 jul，log4j2,logback。SpringBoot 提供了默认的控制台输出配置，也可以配置输出为文件。

\3. logback是默认使用的。

\4. 虽然**日志框架很多**，但是我们不用担心，使用 SpringBoot 的**默认配置就能工作的很好**。



**SpringBoot怎么把日志默认配置好的**

1、每个starter场景，都会导入一个核心场景spring-boot-starter

2、核心场景引入了日志的所用功能spring-boot-starter-logging

3、默认使用了logback + slf4j 组合作为默认底层日志

4、日志是系统一启动就要用，xxxAutoConfiguration是系统启动好了以后放好的组件，后来用的，时机不同。日志的时机更早，系统一启动，系统的一些核心行为都要被日志记录

5、日志是利用**监听器机制**配置好的。ApplicationListener。

6、日志所有的配置都可以通过修改配置文件实现。以logging开始的所有配置。

##### **1.4.3.2 日志格式**

默认输出格式：

- 时间和日期：毫秒级精度
- 日志级别：ERROR, WARN, INFO, DEBUG, or TRACE.
- 进程 ID
- ---： 消息分割符
- 线程名： 使用[]包含
- Logger 名： 通常是产生日志的**类名**
- 消息： 日志记录的内容

注意： logback 没有FATAL级别，对应的是ERROR

默认值：参照：spring-boot包additional-spring-configuration-metadata.json文件

默认输出格式值：

%clr(%d{${LOG_DATEFORMAT_PATTERN:-yyyy-MM-dd'T'HH:mm:ss.SSSXXX}}){faint} %clr(${LOG_LEVEL_PATTERN:-%5p}) %clr(${PID:- }){magenta} %clr(---){faint} %clr([%15.15t]){faint} %clr(%-40.40logger{39}){cyan} %clr(:){faint} %m%n${LOG_EXCEPTION_CONVERSION_WORD:-%wEx}

可修改为：'%d{yyyy-MM-dd HH:mm:ss.SSS} %-5level [%thread] %logger{15} ===> %msg%n'

##### **1.4.3.3 记录日志**

##### **1.4.3.4 日志级别**

- 由低到高：ALL,TRACE, DEBUG, INFO, WARN, ERROR,FATAL,OFF；
  - **只会打印指定级别及以上级别的日志**
  - ALL：打印所有日志
  - TRACE：追踪框架详细流程日志，一般不使用
  - DEBUG：开发调试细节日志
  - INFO：关键、感兴趣信息日志
  - WARN：警告但不是错误的信息日志，比如：版本过时
  - ERROR：业务错误日志，比如出现各种异常
  - FATAL：致命错误日志，比如jvm系统崩溃
  - OFF：关闭所有日志记录

- 不指定级别的所有类，都使用root指定的级别作为默认级别
- SpringBoot日志**默认级别是 \**INFO\****

1. 在application.properties/yaml中配置logging.level.<logger-name>=<level>指定日志级别
2. level可取值范围：TRACE, DEBUG, INFO, WARN, ERROR, FATAL, or OFF，定义在 LogLevel类中
3. root 的logger-name叫root，可以配置logging.level.root=warn，代表所有未指定日志级别都使用 root 的 warn 级别

##### **1.4.3.5 日志分组**

比较有用的技巧是：

将相关的logger分组在一起，统一配置。SpringBoot 也支持。比如：Tomcat 相关的日志统一设置

SpringBoot 预定义两个组

| Name | Loggers                                                      |
| ---- | ------------------------------------------------------------ |
| web  | org.springframework.core.codec, org.springframework.http, org.springframework.web, org.springframework.boot.actuate.endpoint.web, org.springframework.boot.web.servlet.ServletContextInitializerBeans |
| sql  | org.springframework.jdbc.core, org.hibernate.SQL, org.jooq.tools.LoggerListener |

##### **1.4.3.6 文件输出**

SpringBoot 默认只把日志写在控制台，如果想额外记录到文件，可以在application.properties中添加logging.file.name or logging.file.path配置项。

| logging.file.name | logging.file.path | 示例     | 效果                             |
| ----------------- | ----------------- | -------- | -------------------------------- |
| 未指定            | 未指定            |          | 仅控制台输出                     |
| ***\*指定\****    | 未指定            | my.log   | 写入指定文件。可以加路径         |
| 未指定            | ***\*指定\****    | /var/log | 写入指定目录，文件名为spring.log |
| ***\*指定\****    | ***\*指定\****    |          | 以logging.file.name为准          |

**1.4.3.7 文件归档与滚动切割**

归档：每天的日志单独存到一个文档中。

切割：每个文件10MB，超过大小切割成另外一个文件。

1 每天的日志应该独立分割出来存档。如果使用logback（SpringBoot 默认整合），可以通过application.properties/yaml文件指定日志滚动规则。

2 如果是其他日志系统，需要自行配置（添加log4j2.xml或log4j2-spring.xml）

3 支持的滚动规则设置如下

| 配置项                                               | 描述                                                         |
| ---------------------------------------------------- | ------------------------------------------------------------ |
| logging.logback.rollingpolicy.file-name-pattern      | 日志存档的文件名格式（默认值：${LOG_FILE}.%d{yyyy-MM-dd}.%i.gz） |
| logging.logback.rollingpolicy.clean-history-on-start | 应用启动时是否清除以前存档（默认值：false）                  |
| logging.logback.rollingpolicy.max-file-size          | 存档前，每个日志文件的最大大小（默认值：10MB）               |
| logging.logback.rollingpolicy.total-size-cap         | 日志文件被删除之前，可以容纳的最大大小（默认值：0B）。设置1GB则磁盘存储超过 1GB 日志后就会删除旧日志文件 |
| logging.logback.rollingpolicy.max-history            | 日志文件保存的最大天数(默认值：7).                           |

**1.4.3.8 自定义配置**

通常我们配置 application.properties 就够了。当然也可以自定义。比如：

| 日志系统                | 自定义                                                       |
| ----------------------- | ------------------------------------------------------------ |
| Logback                 | logback-spring.xml, logback-spring.groovy,logback.xml, or logback.groovy |
| Log4j2                  | log4j2-spring.xml or log4j2.xml                              |
| JDK (Java Util Logging) | logging.properties                                           |

如果可能，我们建议您在日志配置中使用-spring 变量（例如，logback-spring.xml 而不是 logback.xml）。如果您使用标准配置文件，spring 无法完全控制日志初始化。

最佳实战：自己要写配置，配置文件名加上 xx-spring.xml

##### **1.4.3.****9 切换日志组合**

log4j2支持yaml和json格式的配置文件

| 格式 | 依赖                                                         | 文件名                   |
| ---- | ------------------------------------------------------------ | ------------------------ |
| YAML | com.fasterxml.jackson.core:jackson-databind + com.fasterxml.jackson.dataformat:jackson-dataformat-yaml | log4j2.yaml + log4j2.yml |
| JSON | com.fasterxml.jackson.core:jackson-databind                  | log4j2.json + log4j2.jsn |

##### **1.4.3.*****\*10 最佳实战\****

1 导入任何第三方框架，先排除它的日志包，因为Boot底层控制好了日志

2 修改 application.properties 配置文件，就可以调整日志的所有行为。如果不够，可以编写日志框架自己的配置文件放在类路径下就行，比如logback-spring.xml，log4j2-spring.xml

3 如需对接**专业日志系统**，也只需要把 logback 记录的**日志**灌倒 ***\*kafka\****之类的中间件，这和SpringBoot没关系，都是日志框架自己的配置，**修改配置文件即可**

***\*4\**** ***\*业务中使用\*******\*slf4j-api\*******\*记录日志。不要再\**** ***\*sout\**** ***\*了\****

## ***\*2 SpringBoot3-Web\*******\*开发\****

SpringBoot的Web开发能力，由***\*SpringMVC\****提供。

### ***\*2.0 WebMvcAutoConfiguration\*******\*原理\****

#### ***\*2.0.1 生效条件\****

#### ***\*2.0.2 效果\****

a）放了两个Filter：

1. HiddenHttpMethodFilter；页面表单提交Rest请求（GET、POST、PUT、DELETE）
2. FormContentFilter： 表单内容Filter，GET（数据放URL后面）、POST（数据放请求体）请求可以携带数据，PUT、DELETE 的请求体数据会被忽略

b）给容器中放了WebMvcConfigurer组件；给SpringMVC添加各种定制功能

1. 所有的功能最终会和配置文件进行绑定
2. WebMvcProperties： spring.mvc配置文件
3. WebProperties： spring.web配置文件

#### ***\*2.0.3 WebMvcConfigurer接口\****

提供了配置SpringMVC底层的所有组件入口

![img](https://i-blog.csdnimg.cn/blog_migrate/deed4a847b42f3419685747a17504325.png)

#### ***\*2.0.4 静态资源规则源码\****

![img](https://i-blog.csdnimg.cn/direct/fc783378ba5440c18a65eec3fb1022cb.png)

![img](https://i-blog.csdnimg.cn/direct/c355a57752824f1db636c017b050f55e.png)

a）规则一：访问： /webjars/**路径就去 classpath:/META-INF/resources/webjars/下找资源.

1. maven 导入依赖

b）规则二：访问： /**路径就去 静态资源默认的四个位置找资源

1. classpath:/META-INF/resources/
2. classpath:/resources/
3. classpath:/static/
4. classpath:/public/

c）规则三：**静态资源默认都有缓存规则的设置**

1. 所有缓存的设置，直接通过**配置文件**： spring.web
2. cachePeriod： 缓存周期； 多久不用找服务器要新的。 默认没有，以s为单位
3. cacheControl： **HTTP****缓存**控制；[https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching#概览)
4. **useLastModified**：是否使用最后一次修改。配合HTTP Cache规则

> 如果浏览器访问了一个静态资源 index.js，如果服务这个资源没有发生变化，下次访问的时候就可以直接让浏览器用自己缓存中的东西，而不用给服务器发请求。

#### ***\*2.0.5 EnableWebMvcConfiguration 源码\****

HandlerMapping： 根据请求路径 /a 找哪个handler能处理请求

​    WelcomePageHandlerMapping：

​        1.访问 /**路径下的所有请求，都在以前四个静态资源路径下找，欢迎页也一样

​        2.找index.html：只要静态资源的位置有一个 index.html页面，项目启动默认访问

#### ***\*2.0.\****6 为什么容器中放一个WebMvcConfigurer就能配置底层行为

\1. WebMvcAutoConfiguration 是一个自动配置类，它里面有一个 EnableWebMvcConfiguration

\2. EnableWebMvcConfiguration继承于 DelegatingWebMvcConfiguration，这两个都生效

\3. DelegatingWebMvcConfiguration利用 DI 把容器中 所有 WebMvcConfigurer 注入进来，保存在configures属性中

![img](https://i-blog.csdnimg.cn/direct/a8dd08fbf6ce451ea04d3396532f59d2.png)

\4. 别人调用 DelegatingWebMvcConfiguration 的方法配置底层规则，而它调用所有 WebMvcConfigurer的配置底层方法。

#### ***\*2.0.7 WebMvcConfigurationSupport\****

提供了很多的默认设置。

判断系统中是否有相应的类：如果有，就加入相应的HttpMessageConverter

### 2.1 Web场景

#### 2.1.1 自动配置

1、整合web场景

2、引入了 autoconfigure功能

3、@EnableAutoConfiguration注解使用@Import(AutoConfigurationImportSelector.class)批量导入组件

4、加载 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 文件中配置的所有组件

5、所有web自动配置类如下

6、绑定了配置文件的一堆配置项

​    a）SpringMVC的所有配置以 spring.mvc 开头

​    b）Web场景通用配置以 spring.web 开头

​    c）文件上传配置以 spring.servlet.multipart 开头

​    d）服务器的配置以 server: 开头，比如：编码方式

#### 2.1.2 默认效果

SpringBoot整合SpringMVC

默认配置：

1 包含了 ContentNegotiatingViewResolver 和 BeanNameViewResolver 组件，**方便视图解析**

**2 默认的静态资源处理机制**： 静态资源放在 static 文件夹下即可直接访问

**3 自动注册**了 **Converter**,GenericConverter,**Formatter**组件，适配常见**数据类型转换**和**格式化需求**

**4 支持** **HttpMessageConverters**，可以**方便返回**json等**数据类型**

**5 注册** MessageCodesResolver，方便**国际化**及错误消息处理

**6 支持** **静态** index.html

**7 自动使用**ConfigurableWebBindingInitializer，实现消息处理、数据绑定、类型转化、数据校验等功能

> ***\*重要：\****
>
> - 如果想保持 **boot mvc 的默认配置**，并且自定义更多的 mvc 配置，如：**interceptors**, **formatters**, **view controllers** 等。可以使用@Configuration注解添加一个 WebMvcConfigurer 类型的配置类，并不要标注 @EnableWebMvc
> - 如果想保持 boot mvc 的默认配置，但要自定义核心组件实例，比如：RequestMappingHandlerMapping, RequestMappingHandlerAdapter, 或ExceptionHandlerExceptionResolver，给容器中放一个 WebMvcRegistrations 组件即可
> - 如果想全面接管 Spring MVC，@Configuration 标注一个配置类，并加上 @EnableWebMvc注解，实现 WebMvcConfigurer 接口

### 2.2 静态资源

#### 2.2.1 默认规则

##### 2.2.1.1 静态资源映射

静态资源映射规则在 WebMvcAutoConfiguration 中进行了定义：

a） /webjars/** 的所有路径 资源都在 classpath:/META-INF/resources/webjars/

b）/** 的所有路径 资源都在 classpath:/META-INF/resources/、classpath:/resources/、classpath:/static/、classpath:/public/

c）所有静态资源都定义了缓存规则。【浏览器访问过一次，就会缓存一段时间】，但此功能参数无默认值

1. period： 缓存间隔。 默认 0S；
2. cacheControl：缓存控制。 默认无；
3. useLastModified：是否使用lastModified头。 默认 false；

##### 2.2.1.2 静态资源缓存

如前面所述

所有静态资源都定义了缓存规则。【浏览器访问过一次，就会缓存一段时间】，但此功能参数无默认值

1. period： 缓存间隔。 默认 0S；
2. cacheControl：缓存控制。 默认无；
3. useLastModified：是否使用lastModified头。 默认 false；

##### 2.2.1.3 欢迎页

欢迎页规则在 WebMvcAutoConfiguration 中进行了定义：

1. 在**静态资源**目录下找 index.html
2. 没有就在 templates下找index模板页

##### 2.2.1.4 Favicon

1. 在静态资源目录下找 favicon.ico

##### 2.2.1.5 缓存实验

#### 2.2.2 自定义静态资源规则

自定义静态资源路径、自定义缓存规则

##### 2.2.2.1 配置方式

spring.mvc： 静态资源访问前缀路径

spring.web：

- 静态资源目录
- 静态资源缓存策略

##### 2.2.2.2 代码方式

> - 容器中只要有一个 WebMvcConfigurer 组件。配置的底层行为都会生效
> - @EnableWebMvc //禁用boot的默认配置，全手动

### 2.3 路径匹配

***\*Spring5.3\**** 之后加入了更多的请求路径匹配的实现策略；

以前只支持 AntPathMatcher 策略, 现在提供了 ***\**\*PathPatternParser\*\**\*** 策略。并且可以让我们指定到底使用那种策略。

#### 2.3.1 Ant风格路径用法

Ant 风格的路径模式语法具有以下规则：

- *：表示**任意数量**的字符。
- ?：表示任意**一个字符**。
- **：表示**任意数量的目录**。
- {}：表示一个命名的模式**占位符**。
- []：表示**字符集合**，例如[a-z]表示小写字母。

例如：

- *.html 匹配任意名称，扩展名为.html的文件。
- /folder1/*/*.java 匹配在folder1目录下的任意两级目录下的.java文件。
- /folder2/**/*.jsp 匹配在folder2目录下任意目录深度的.jsp文件。
- /{type}/{id}.html 匹配任意文件名为{id}.html，在任意命名的{type}目录下的文件。

注意：Ant 风格的路径模式语法中的特殊字符需要转义，如：

- 要匹配文件路径中的星号，则需要转义为\\*。
- 要匹配文件路径中的问号，则需要转义为\\?。

#### 2.3.2 模式切换

***\*AntPathMatcher 与 PathPatternParser\****

- PathPatternParser 在 jmh 基准测试下，有 6~8 倍吞吐量提升，降低 30%~40%空间分配率
- PathPatternParser 兼容 AntPathMatcher语法，并支持更多类型的路径模式
- PathPatternParser "***\***" ***\*多段匹配\****的支持**仅允许在模式末尾使用，**不能匹配 ** 在中间的情况，剩下的和 antPathMatcher语法兼容

总结：

- 使用默认的路径匹配规则，是由 PathPatternParser 提供的
- 如果路径中间需要有 **，替换成ant风格路径

### 2.4 内容协商

一套系统适配多端数据返回



![img](https://i-blog.csdnimg.cn/blog_migrate/cb738dc39482f9b54ccf8e961ede58ae.png)

#### 2.4.1 多端内容适配

##### 2.4.1.1 默认规则

***\*SpringBoot\**** ***\*多端内容适配:\****

***\*a）基于\*******\*请求头\*******\*内容协商\****：（默认开启）

客户端向服务端发送请求，携带HTTP标准的**Accept请求头**。

1. ***\*Accept\****: application/json、text/xml、text/yaml
2. 服务端根据客户端**请求头期望的数据类型**进行**动态返回**

***\*b）基于\*******\*请求参数\*******\*内容协商：（需要手动开启）\****

1. 发送请求 GET /projects/spring-boot?format=json
2. 匹配到 @GetMapping("/projects/spring-boot")
3. 根据**参数协商**，优先返回 json 类型数据【**需要开启参数匹配设置**】
4. 发送请求 GET /projects/spring-boot?format=xml,优先返回 xml 类型数据

##### 2.4.1.2 效果演示

请求同一个接口，可以返回json和xml不同格式数据

1、引入支持写出xml内容依赖

2、标注注解

3、开启基于请求参数的内容协商

4、效果

![img](https://i-blog.csdnimg.cn/blog_migrate/b013f41b744a8712b9621c6ec651390c.png)



![img](https://i-blog.csdnimg.cn/blog_migrate/beb4fa6a7db806d7f45af31fc5e0c658.png)

##### 2.4.1.3 配置协商规则与支持类型

修改***\*内容协商方式\****

![img](https://i-blog.csdnimg.cn/direct/3c229443f83f41e092bd7f765147e30e.png)

大多数 MediaType 都是开箱即用的。也可以***\*自定义内容类型，如：\****

#### 2.4.2 自定义内容返回

##### 2.4.2.1 增加yaml返回支持

导入依赖

把对象写出成YAML

![img](https://i-blog.csdnimg.cn/direct/fcb8c8e84e1d4a5d9dbcfe010e87b62b.png)

编写配置

增加HttpMessageConverter组件，专门负责把对象写出为yaml格式

##### 2.4.2.2 思考：如何增加其他

配置媒体类型支持:

- spring.mvc.contentnegotiation.media-types.yaml=text/yaml

编写对应的HttpMessageConverter，要告诉Boot这个支持的媒体类型

- 按照3的示例

把MessageConverter组件加入到底层

- 容器中放一个WebMvcConfigurer 组件，并配置底层的MessageConverter

##### 2.4.2.3 HttpMessageConverter的示例写法

#### 2.4.3 内容协商原理-HttpMessageConverter

> - HttpMessageConverter 怎么工作？合适工作？
> - 定制 HttpMessageConverter 来实现多端内容协商
> - 编写WebMvcConfigurer提供的configureMessageConverters底层，修改底层的MessageConverter

##### 2.4.3.1 @ResponseBody由HttpMessageConverter处理

> 标注了@ResponseBody的返回值 将会由支持它的 HttpMessageConverter写给浏览器

**1 如果controller方法的返回值标注了 @ResponseBody 注解**

a）请求进来先来到 DispatcherServlet的doDispatch() 进行处理

b）找到一个 HandlerAdapter 适配器。利用适配器执行目标方法

c）RequestMappingHandlerAdapter来执行，调用invokeHandlerMethod（）来执行目标方法

d）目标方法执行之前，准备好两个东西

1. HandlerMethodArgumentResolver：参数解析器，确定目标方法每个参数值
2. HandlerMethodReturnValueHandler：返回值处理器，确定目标方法的返回值改怎么处理

e）RequestMappingHandlerAdapter 里面的invokeAndHandle()真正执行目标方法

f）目标方法执行完成，会返回**返回值对象**

g）**找到一个合适的返回值处理器** HandlerMethodReturnValueHandler

h）最终找到 RequestResponseBodyMethodProcessor能处理 标注了 @ResponseBody注解的方法

i）RequestResponseBodyMethodProcessor 调用writeWithMessageConverters ,利用MessageConverter把返回值写出去

> 上面步骤解释：@ResponseBody由HttpMessageConverter处理

**2 HttpMessageConverter 会先进行内容协商**

a）遍历所有的MessageConverter看谁支持这种**内容类型的数据**

b）默认MessageConverter有以下

![img](https://i-blog.csdnimg.cn/blog_migrate/5f5c4e1ba583324d30866fcc2b521775.png)

c）最终因为要json所以MappingJackson2HttpMessageConverter支持写出json

d）jackson用ObjectMapper把对象写出去

##### 2.4.3.2 WebMvcAutoConfiguration提供几种默认HttpMessageConverters

EnableWebMvcConfiguration通过 addDefaultHttpMessageConverters添加了默认的MessageConverter；如下：

- ByteArrayHttpMessageConverter： 支持字节数据读写
- StringHttpMessageConverter： 支持字符串读写
- ResourceHttpMessageConverter：支持资源读写
- ResourceRegionHttpMessageConverter: 支持分区资源写出
- AllEncompassingFormHttpMessageConverter：支持表单xml/json读写
- MappingJackson2HttpMessageConverter： 支持请求响应体Json读写

默认8个：

![img](https://i-blog.csdnimg.cn/blog_migrate/13567250152cf0baf592d920a803adfa.png)

> 系统提供默认的MessageConverter 功能有限，仅用于json或者普通返回数据。额外增加新的内容协商功能，必须增加新的HttpMessageConverter

### 2.5 模板引擎

由于 **SpringBoot** 使用了**嵌入式 Servlet 容器**。所以 **JSP** 默认是**不能使用**的。

如果需要**服务端页面渲染**，优先考虑使用 模板引擎。

![image.png](https://i-blog.csdnimg.cn/blog_migrate/fc42e54f5a54578b96964d410fb46548.png)

模板引擎页面默认放在 src/main/resources/templates

***\*SpringBoot\**** 包含以下模板引擎的自动配置

- FreeMarker
- Groovy
- ***\*Thymeleaf\****
- Mustache

***\*Thymeleaf\*******\*官网\****：[Thymeleaf](https://www.thymeleaf.org/)

#### 2.5.1 Thymeleaf整合

自动配置原理：

开启了 org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration 自动配置

属性绑定在 ThymeleafProperties 中，对应配置文件 spring.thymeleaf 内容

所有的模板页面默认在 classpath:/templates文件夹下

默认效果

1. 所有的模板页面在 classpath:/templates/下面找
2. 找后缀名为.html的页面

------

Thymeleaf初体验 

#### 2.5.2 基础语法

##### 2.5.2.1 核心用法

***\**\*th:xxx\*\**\******\*：动态渲染指定的 html 标签属性值、或者th指令（遍历、判断等）\****

- th:text

  ：标签体内文本值渲染

  - th:utext：不会转义，显示为html原本的样子。

- th:属性：标签指定属性渲染

- th:attr：标签任意属性渲染

- th:if th:each ...：其他th指令

- 例如：

------

------

***\**\*表达式\*\**\******\*：用来动态取值\****

- **${}****：变量取值；使用model共享给页面的值都直接用${}**
- **@{}****：url路径；**
- \#{}：国际化消息
- ~{}：片段引用
- *{}：变量选择：需要配合th:object绑定对象

***\*系统工具&内置对象：\****[详细文档](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#appendix-a-expression-basic-objects)

- param：请求参数对象
- session：session对象
- application：application对象
- \#execInfo：模板执行信息
- \#messages：国际化消息
- \#uris：uri/url工具
- \#conversions：类型转换工具
- \#dates：日期工具，是java.util.Date对象的工具类
- \#calendars：类似#dates，只不过是java.util.Calendar对象的工具类
- \#temporals： JDK8+ ***\*java.time\**** API 工具类
- \#numbers：数字操作工具
- \#strings：字符串操作
- \#objects：对象操作
- \#bools：bool操作
- \#arrays：array工具
- \#lists：list工具
- \#sets：set工具
- \#maps：map工具
- \#aggregates：集合聚合工具（sum、avg）
- \#ids：id生成工具

##### 2.5.2.2 语法示例

***\*表达式：\****

- 变量取值：${...}
- url 取值：@{...}
- 国际化消息：#{...}
- 变量选择：*{...}
- 片段引用: ~{...}

***\*常见：\****

- 文本： 'one text'，'another one!',...
- 数字： 0,34,3.0,12.3,...
- 布尔：true、false
- null: null
- 变量名： one,sometext,main...

***\*文本操作：\****

- 拼串： +
- 文本替换：| The name is ${name} |

***\*布尔操作：\****

- 二进制运算： and,or
- 取反：!,not

***\*比较运算：\****

- 比较：>，<，<=，>=（gt，lt，ge,le）
- 等值运算：==,!=（eq，ne）

***\*条件运算：\****

- if-then： (if)?(then)
- if-then-else: (if)?(then):(else)
- default: (value)?:(defaultValue)

***\*特殊语法：\****

- 无操作：_

***\*所有以上都可以嵌套组合\****

#### 2.5.3 属性设置

\1. th:href="@{/product/list}"

\2. th:attr="class=${active}"

\3. th:attr="src=@{/images/gtvglogo.png},title=${logo},alt=#{logo}"

\4. th:checked="${user.active}"

#### 2.5.4 遍历

> 语法： th:each="元素名,迭代状态 : ${集合}"

iterStat 有以下属性：

- index：当前遍历元素的索引，从0开始
- count：当前遍历元素的索引，从1开始
- size：需要遍历元素的总数量
- current：当前正在遍历的元素对象
- even/odd：是否偶数/奇数行
- first：是否第一个元素
- last：是否最后一个元素

------

#### 2.5.5 判断

##### 2.5.5.1 th:if

##### 2.5.5.2 th:switch

#### 2.5.6 属性优先级

- 片段
- 遍历
- 判断

| Order | Feature          | Attributes                           |
| ----- | ---------------- | ------------------------------------ |
| 1     | 片段包含         | th:insert th:replace                 |
| 2     | 遍历             | th:each                              |
| 3     | 判断             | th:if th:unless th:switch th:case    |
| 4     | 定义本地变量     | th:object th:with                    |
| 5     | 通用方式属性修改 | th:attr th:attrprepend th:attrappend |
| 6     | 指定属性修改     | th:value th:href th:src ...          |
| 7     | 文本值           | th:text th:utext                     |
| 8     | 片段指定         | th:fragment                          |
| 9     | 片段移除         | th:remove                            |

#### 2.5.7 行内写法

[[...]] or [(...)]

#### 2.5.8 变量选择

等同于 

#### 2.5.9 模板布局

- 定义模板： th:fragment
- 引用模板：~{templatename::selector}
- 插入模板：th:insert、th:replace

------

common.html

index.html

#### 2.5.10 devtools

热启动依赖

修改页面后；ctrl+F9刷新效果；

java代码的修改，如果devtools热启动了，可能会引起一些bug，难以排查

建议：页面修改直接ctrl+F9；java代码的修改，重启项目

------

常用配置项

### 2.6 国际化

国际化的自动配置参照MessageSourceAutoConfiguration

**实现步骤**：

1 Spring Boot 在类路径根下查找messages资源绑定文件。文件名为：messages.properties

2 多语言可以定义多个消息文件，命名为messages_区域代码.properties。如：

​    a）messages.properties：默认

​    b）messages_zh_CN.properties：中文环境

​    c）messages_en_US.properties：英语环境

3 在**程序中**可以自动注入 MessageSource组件，获取国际化的配置项值

4 在**页面中**可以使用表达式 #{}获取国际化的配置项值

------

common.html

messages.properties

### 2.7 错误处理

#### 2.7.1 默认机制

**错误处理的自动配置**都在ErrorMvcAutoConfiguration中，两大核心机制：

- \1. SpringBoot 会***\*自适应\**处理错误**，***\*响应页面\****或***\*JSON\**\**数据\****
- \2. ***\*SpringMVC\*******\*的错误处理机制\****依然保留，***\*MVC\**\**处理不了\****，才会***\*交给boot进行处理\****

![img](https://i-blog.csdnimg.cn/direct/e1802d79b46947c695f2c27413cff8da.png)

- 发生错误以后，转发给/error路径，SpringBoot在底层写好一个 BasicErrorController的组件，专门处理这个请求

- 错误页面是这么解析到的

容器中专门有一个错误视图解析器 

SpringBoot解析自定义错误页的默认规则

容器中有一个默认的名为 error 的 view； 提供了默认白页功能 

封装了JSON格式的错误信息

规则：

**解析一个错误页**

a）如果发生了500、404、503、403 这些错误

1. 如果有**模板引擎**，默认在 classpath:/templates/error/**精确码****.html**
2. 如果没有模板引擎，在静态资源文件夹下找 **精确码****.html**

b）如果匹配不到精确码.html这些精确的错误页，就去找5xx.html，4xx.html**模糊匹配**

1. 如果有模板引擎，默认在 classpath:/templates/error/5xx.html
2. 如果没有模板引擎，在静态资源文件夹下找 5xx.html

**如果模板引擎路径 templates下有 error.html页面，就直接渲染**

#### 2.7.2 自定义错误响应

##### 2.7.2.1 自定义json响应

使用@ControllerAdvice + @ExceptionHandler 进行统一异常处理

##### 2.7.2.2 自定义页面响应

根据boot的错误页面规则，自定义页面模板

#### 2.7.3 最佳实战

**前后分离**

后台发生的所有错误，@ControllerAdvice + @ExceptionHandler进行统一异常处理。

**服务端页面渲染**

**不可预知的一些，HTTP码表示的服务器或客户端错误**

给classpath:/templates/error/下面，放常用精确的错误码页面。500.html，404.html

给classpath:/templates/error/下面，放通用模糊匹配的错误码页面。 5xx.html，4xx.html

**发生业务错误**

**核心业务**，每一种错误，都应该代码控制，**跳转到自己定制的错误页**。

**通用业务**，classpath:/templates/error.html页面，**显示错误信息**。



页面，JSON，可用的Model数据如下



![img](https://i-blog.csdnimg.cn/blog_migrate/e4ca8f75c680045628328204f66dd70e.png)

### 2.8 嵌入式容器

**Servlet容器**：管理、运行**Servlet组件**（Servlet、Filter、Listener）的环境，一般指**服务器（Tomcat）**

#### 2.8.1 自动配置原理

SpringBoot 默认嵌入Tomcat作为Servlet容器。

**自动配置类**是 `ServletWebServerFactoryAutoConfiguration`，`EmbeddedWebServerFactoryCustomizerAutoConfiguration`

自动配置类开始分析功能。`xxxxAutoConfiguration`

```
1 ServletWebServerFactoryAutoConfiguration ``自动配置了嵌入式容器场景
2 ``绑定了ServerProperties配置类，所有和服务器有关的配置 server
3 ServletWebServerFactoryAutoConfiguration ``导入了 嵌入式的三大服务器 Tomcat、Jetty、Undertow
  a）导入 Tomcat、Jetty、Undertow 都有条件注解。系统中有这个类才行（也就是导了包）
  b``）默认 Tomcat配置生效。给容器中放 TomcatServletWebServerFactory
 c）都给容器中 放了一个ServletWebServerFactory（接口） **web服务器工厂（造web服务器的）——**xxxServletWebServerFactory（具体实现类）
  **d**``**）web服务器工厂 都有一个功能**，getWebServer获取web服务器
  e``）TomcatServletWebServerFactory 创建了 tomcat。
4 ServletWebServerFactory ``什么时候会创建 webServer出来。
5 ServletWebServerApplicationContext ioc``容器，启动的时候会调用创建web服务器
6 Spring``容器刷新（启动）的时候，会预留一个时机，刷新子容器。onRefresh()
7 refresh() ``容器刷新 十二大步的刷新子容器会调用 onRefresh()；
```

> Web场景的Spring容器启动，在onRefresh的时候，会调用创建web服务器的方法。
>
> Web服务器的创建是通过WebServerFactory搞定的。容器中又会根据导了什么包条件注解，启动相关的 服务器配置，默认`EmbeddedTomcat`会给容器中放一个 `TomcatServletWebServerFactory`，导致项目启动，自动创建出Tomcat。

#### 2.8.2 自定义



![img](https://i-blog.csdnimg.cn/blog_migrate/530761049dbb78beff1970c49edffdfa.png)

切换服务器；

#### 2.8.3 最佳实战

**用法：**

- 修改`server`下的相关配置就可以修改**服务器参数**
- 通过给容器中放一个`**ServletWebServerFactory（接口，放入的是xxxServletWebServerFactory实现类）**`，来禁用掉SpringBoot默认放的服务器工厂，实现自定义嵌入**任意服务器**。

![img](https://i-blog.csdnimg.cn/direct/f4dd032dfb5343208f98486e85674897.png)

以Tomcat服务器为例，当容器中没有ServletWebServerFactory时，系统创建new TomcatServletWebServerFactory()；所以当我们自己配置了ServletWebServerFactory放入容器，则系统就不创建服务器工厂了

### 2.9 全面接管SpringMVC

- SpringBoot 默认配置好了 SpringMVC 的所有常用特性。
- 如果我们需要全面接管SpringMVC的所有配置并**禁用默认配置**，仅需要编写一个WebMvcConfigurer配置类，并标注 @EnableWebMvc 即可
- 全手动模式
  - @EnableWebMvc : 禁用默认配置
  - **WebMvcConfigurer**组件：定义MVC的底层行为

#### 2.9.1 WebMvcAutoConfiguration 到底自动配置了哪些规则

> SpringMVC自动配置场景给我们配置了如下所有**默认行为**

1 WebMvcAutoConfigurationweb场景的自动配置类

​    1.1 支持RESTful的filter：HiddenHttpMethodFilter

​    1.2 支持非POST请求，请求体携带数据：FormContentFilter

​    1.3 导入EnableWebMvcConfiguration：

​        1.3.1 RequestMappingHandlerAdapter

​        1.3.2 WelcomePageHandlerMapping： **欢迎页功能**支持（模板引擎目录、静态资源目录放index.html），项目访问/ 就默认展示这个页面.

​        1.3.3 RequestMappingHandlerMapping：找每个请求由谁处理的映射关系

​        1.3.4 ExceptionHandlerExceptionResolver：默认的异常解析器

​        1.3.5 LocaleResolver：国际化解析器

​        1.3.6 ThemeResolver：主题解析器

​        1.3.7 FlashMapManager：临时数据共享

​        1.3.8 FormattingConversionService： 数据格式化 、类型转化

​        1.3.9 Validator： 数据校验JSR303提供的数据校验功能

​        1.3.10 WebBindingInitializer：请求参数的封装与绑定

​        1.3.11 ContentNegotiationManager：内容协商管理器

​    1.4 WebMvcAutoConfigurationAdapter配置生效，它是一个WebMvcConfigurer，定义mvc底层组件

​        1.4.1 定义好 WebMvcConfigurer 底层组件默认功能；所有功能详见列表

​        1.4.2 视图解析器：InternalResourceViewResolver

​        1.4.3 视图解析器：BeanNameViewResolver,视图名（controller方法的返回值字符串）就是组件名

​        1.4.4 内容协商解析器：ContentNegotiatingViewResolver

​        1.4.5 请求上下文过滤器：RequestContextFilter: 任意位置直接获取当前请求

​        1.4.6 静态资源链规则

​        1.4.7 ProblemDetailsExceptionHandler：错误详情

​        1.4.7.1 SpringMVC内部场景异常被它捕获：

​    1.5 定义了MVC默认的底层行为: WebMvcConfigurer

#### 2.9.2 @EnableWebMvc 禁用默认行为

1. @EnableWebMvc给容器中导入 DelegatingWebMvcConfiguration组件，他是 WebMvcConfigurationSupport
2. WebMvcAutoConfiguration有一个核心的条件注解, @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)，容器中没有WebMvcConfigurationSupport，WebMvcAutoConfiguration才生效.
3. @EnableWebMvc 导入 WebMvcConfigurationSupport 导致 WebMvcAutoConfiguration 失效。导致禁用了默认行为

> - @EnableWebMVC 禁用了 Mvc的自动配置
> - WebMvcConfigurer 定义SpringMVC底层组件的功能类

#### 2.9.3 WebMvcConfigurer 功能

> 定义扩展SpringMVC底层功能

![img](https://i-blog.csdnimg.cn/direct/07d52c8e1b514f8c8971f99ccbb99bcd.png)

### 2.10 最佳实践

SpringBoot 已经默认配置好了Web开发场景常用功能。我们直接使用即可。

#### 2.10.1 三种方式

| 方式             | 用法                                                         | 效果                                       |                                                              |
| ---------------- | ------------------------------------------------------------ | ------------------------------------------ | ------------------------------------------------------------ |
| ***\*全自动\**** | 直接编写控制器逻辑                                           |                                            | 全部使用***\*自动配置默认效果\****                           |
| **手自一体**     | @Configuration + ***\*配置\*******\*WebMvcConfigurer\**** + ***配置\*** ***\**WebMvcRegistrations\**\*** | ***\*不要标注\**** ***\*@EnableWebMvc\**** | ***\*保留自动配置效果\**** ***\*手动设置部分功能\**** 定义MVC底层组件 |
| ***\*全手动\**** | @Configuration ***\**\*+\*\**\*** ***\*配置\*******\*WebMvcConfigurer\**** | ***\*标注\**** ***\*@EnableWebMvc\****     | ***\*禁用自动配置效果\**** ***\*全手动设置\****              |

总结：

给容器中写一个配置类 加上注解@Configuration， 实现 WebMvcConfigurer， 但是不要标注 @EnableWebMvc注解，实现手自一体的效果。

#### 2.10.2 两种模式

1、前后分离模式： @RestController 响应JSON数据

2、前后不分离模式：@Controller + Thymeleaf模板引擎

### 2.11 Web新特性

#### 2.11.1 Problemdetails

> RFC 7807: https://www.rfc-editor.org/rfc/rfc7807
>
> **错误信息**返回新格式

原理 

1 ProblemDetailsExceptionHandler 是一个 @ControllerAdvice集中处理系统异常

2 处理以下异常。如果系统出现以下异常，会被SpringBoot支持以 `RFC 7807`规范方式返回错误数据

>  效果：

默认响应错误的json。状态码 405 

开启ProblemDetails返回, 使用新的MediaType

Content-Type: application/problem+json+ 额外扩展返回

![img](https://i-blog.csdnimg.cn/blog_migrate/750ca69ecbaa9c1c9c938216ae8cacfb.png)

#### 2.11.2 函数式Web

> SpringMVC 5.2 以后 允许我们使用**函数式**的方式，**定义Web的请求处理流程**。
>
> 函数式接口
>
> Web请求处理的方式：
>
> 1. @Controller + @RequestMapping：**耦合式** （**路由**、**业务**耦合）
> 2. **函数式Web**：分离式（路由、业务分离）

##### 2.11.2.1 场景

场景：User RESTful - CRUD

- GET /user/1 获取1号用户
- GET /users 获取所有用户
- POST /user **请求体**携带JSON，新增一个用户
- PUT /user/1 **请求体**携带JSON，修改1号用户
- DELETE /user/1 **删除**1号用户

##### 2.11.2.2 核心类

- **RouterFunction**
- **RequestPredicate**
- **ServerRequest**
- **ServerResponse**

##### 2.11.2.3 示例

------

## 3 SpringBoot3-数据访问

**整合SSM场景**

SpringBoot 整合 Spring、SpringMVC、MyBatis 进行**数据访问场景**开发

### 3.1 创建SSM整合项目

### 3.2 配置数据源

安装MyBatisX 插件，帮我们生成Mapper接口的xml文件即可 

**UserMapper接口**

**UserMapper.xml**

**告诉SpringBoot，扫描Mapper接口的位置** 

### 3.3 配置MyBatis

### 3.4 CRUD编写

- 编写Bean
- 编写Mapper
- 使用mybatisx插件，快速生成MapperXML
- 测试CRUD

------

UserController

### 3.5 自动配置原理

**SSM整合总结：**

> 1. **导入** mybatis-spring-boot-starter
> 2. 配置**数据源**信息
> 3. 配置mybatis的**mapper****接口扫描**与**xml****映射文件扫描**
> 4. 编写bean，mapper，生成xml，编写sql 进行crud。***\*事务等操作依然和Spring中用法一样\****
> 5. 效果：
>
> ​    a）所有sql写在xml中
>
> ​    b）所有mybatis配置写在application.properties下面

![img](https://i-blog.csdnimg.cn/direct/e42dd9ff7fe44dda9b7a3fabead3d82d.png)

![img](https://i-blog.csdnimg.cn/direct/313600fd68814e7880d6c44b4b448839.png)

> **如何分析哪个场景导入以后，开启了哪些自动配置类。**
>
> 找：classpath:/META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports文件中配置的所有值，就是要开启的自动配置类，但是每个类可能有条件注解，基于条件注解判断哪个自动配置类生效了。

### 3.6 快速定位生效的配置

### 3.7 扩展：整合其他数据源

#### 3.7.1 Druid 数据源

> 暂不支持 `SpringBoot3`
>
> - 导入`druid-starter`
> - 写配置
> - 分析自动配置了哪些东西，怎么用

Druid官网：

[GitHub - alibaba/druid: 阿里云计算平台DataWorks(https://help.aliyun.com/document_detail/137663.html) 团队出品，为监控而生的数据库连接池](https://github.com/alibaba/druid)

### 3.8 附录：示例数据库

## 4 SpringBoot3-基础特性

### 4.1 SpringApplication

#### 4.1.1 自定义 banner

类路径添加banner.txt或设置spring.banner.location就可以定制 banner

推荐网站：[Spring Boot banner 在线生成工具，制作下载英文 banner.txt，修改替换 banner.txt 文字实现自定义，个性化启动 banner-bootschool.net](https://www.bootschool.net/ascii)

#### 4.1.2 自定义 SpringApplication

#### 4.1.3 FluentBuilder API

流式写法

### 4.2 Profiles

> 环境隔离能力；快速切换开发、测试、生产环境
>
> 步骤：
>
> 1. **标识环境**：指定哪些组件、配置在哪个环境生效
> 2. **切换环境**：这个环境对应的所有组件和配置就应该生效

------

Boot306FeaturesApplication 

application.properties 

#### 4.2.1 使用

##### 4.2.1.1 指定环境

- Spring Profiles 提供一种**隔离配置**的方式，使其仅在**特定环境**生效；
- 任何@Component, @Configuration 或 @ConfigurationProperties 可以使用 @Profile 标记，来指定何时被加载。【**容器中的组件**都可以被 @Profile标记】

##### 4.2.1.2 环境激活

1 配置激活指定环境； 配置文件

2 也可以使用命令行激活。--spring.profiles.active=dev,hsqldb

3 还可以配置**默认环境**； 不标注@Profile 的组件永远都存在。

​    a）以前默认环境叫default

​    b）spring.profiles.default=test

4 推荐使用激活方式激活指定环境

##### 4.2.1.3 环境包含

注意：

1. spring.profiles.active 和spring.profiles.default 只能用到 **无 profile 的文件**中，如果在application-dev.yaml中编写就是**无效的**
2. 也可以额外添加生效文件，而不是激活替换。比如：

最佳实战：

- **生效的环境** = **激活的环境/默认环境** + **包含的环境**
- 项目里面这么用
  - 基础的配置mybatis、log、xxx：写到**包含环境中**
  - 需要动态切换变化的 db、redis：写到**激活的环境中**

#### 4.2.2 Profile 分组

创建prod组，指定包含db和mq配置

使用--spring.profiles.active=prod ，就会激活prod，db，mq配置文件

#### 4.2.3 Profile 配置文件

- application-{profile}.properties可以作为**指定环境的配置文件**。

- 激活这个环境，

  配置

  就会生效。最终生效的所有

  配置

  是

  - application.properties：主配置文件，任意时候都生效
  - application-{profile}.properties：指定环境配置文件，激活指定环境生效

profile优先级 > application

### 4.3 外部化配置

> **场景**：线上应用如何**快速修改配置**，并应**用最新配置**？
>
> - SpringBoot 使用 **配置优先级** + **外部配置** 简化配置更新、简化运维。
> - 只需要给`jar`应用所在的文件夹放一个`application.properties`最新配置文件，重启项目就能自动应用最新配置

#### 4.3.1 配置优先级

Spring Boot 允许将**配置外部化**，以便可以在不同的环境中使用相同的应用程序代码。

我们可以使用各种**外部配置源**，包括Java Properties文件、YAML文件、环境变量和命令行参数。

@Value可以获取值，也可以用@ConfigurationProperties将所有属性绑定到java object中

***\*以下是 SpringBoot 属性源加载顺序。\*******\**\*后面的会覆盖前面的值\*\**\***。由低到高，高优先级配置覆盖低优先级

1. ***\*默认属性\****（通过SpringApplication.setDefaultProperties指定的）
2. @PropertySource指定加载的配置（需要写在@Configuration类上才可生效）
3. ***\*配置文件（\*\*application.properties/yml\*\*等）\****
4. RandomValuePropertySource支持的random.*配置（如：@Value("${random.int}")）
5. OS 环境变量
6. Java 系统属性（System.getProperties()）
7. JNDI 属性（来自java:comp/env）
8. ServletContext 初始化参数
9. ServletConfig 初始化参数
10. SPRING_APPLICATION_JSON属性（内置在环境变量或系统属性中的 JSON）
11. ***\*命令行参数\****
12. 测试属性。(@SpringBootTest进行测试时指定的属性)
13. 测试类@TestPropertySource注解
14. Devtools 设置的全局属性。($HOME/.config/spring-boot)

> 结论：配置可以写到很多位置，常见的优先级顺序：
>
> - 命令行> 配置文件> springapplication配置

***\*配置文件优先级\****如下：(**后面覆盖前面**)

1. ***\*jar\**** ***\*包内\****的application.properties/yml
2. ***\*jar\**** ***\*包内\****的application-{profile}.properties/yml
3. ***\*jar\**** ***\*包外\****的application.properties/yml
4. ***\*jar\**** ***\*包外\****的application-{profile}.properties/yml

***\*建议\****：**用一种格式的配置文件**。***\*如果\*******\**\*.properties\*\**\******\*和\*******\**\*.yml\*\**\******\*同时存在\*******\*,\*******\*则\*******\**\*.properties\*\**\******\*优先\****

> 结论：包外 > 包内； 同级情况：profile配置 > application默认配置

***\*所有参数均可由命令行传入，使用\*******\**\*--\*\**\******\**\*参数项\*\**\******\**\*=\*\**\******\**\*参数值\*\**\******\*，将会被添加到环境变量中，并优先于\*******\**\*配置文件\*\**\******\*。\****

***\*比如\*******\**\*java -jar app.jar --name="Spring"\*\**\******\*,\*******\*可以使用\*******\**\*@Value("${name}")\*\**\******\*获取\****



演示场景：

- 包内： application.properties server.port=8000
- 包内： application-dev.properties server.port=9000
- 包外： application.properties server.port=8001
- 包外： application-dev.properties server.port=9001

启动端口？：命令行 > 9001 > 8001 > 9000 > 8000

#### 4.3.2 外部配置

SpringBoot 应用启动时会自动寻找application.properties和application.yaml位置，进行加载。顺序如下：（**后面覆盖前面**）

1 类路径: 内部

​    a）类根路径

​    b）类下/config包

2 当前路径（项目所在的位置）

​    a）当前路径

​    b）当前下/config子目录

​    c）/config目录的直接子目录



最终效果：优先级由高到低，前面覆盖后面

- 命令行 > 包外config直接子目录 > 包外config目录 > 包外根目录 > 包内目录
- 同级比较：
  - profile配置 > 默认配置
  - properties配置 > yaml配置

![img](https://i-blog.csdnimg.cn/direct/e806efb2eb0e4d8196a28b4e39ac5476.png)

规律：最外层的最优先。

- 命令行 > 所有
- 包外 > 包内
- config目录 > 根目录
- profile > application

配置不同就都生效（互补），配置相同高优先级覆盖低优先级

#### 4.3.3 导入配置

使用spring.config.import可以导入额外配置

无论以上写法的先后顺序，my.properties的值总是优先于直接在文件中编写的my.property。

#### 4.3.4 属性占位符

配置文件中可以使用 ${name:default}形式取出之前配置过的值。

------

 ${name:default}，取name的时候如果配置文件中没有配置过，则可以使用默认值

### 4.4 单元测试-JUnit5

#### 4.4.1 整合

SpringBoot 提供一系列测试工具集及注解方便我们进行测试。

spring-boot-test提供核心测试能力，spring-boot-test-autoconfigure 提供测试的一些自动配置。

我们只需要导入spring-boot-starter-test 即可整合测试

spring-boot-starter-test 默认提供了以下库供我们测试使用

- [JUnit 5](https://junit.org/junit5/)
- [Spring Test](https://docs.spring.io/spring-framework/docs/6.0.4/reference/html/testing.html#integration-testing)
- [AssertJ](https://assertj.github.io/doc/)
- [Hamcrest](https://github.com/hamcrest/JavaHamcrest)
- [Mockito](https://site.mockito.org/)
- [JSONassert](https://github.com/skyscreamer/JSONassert)
- [JsonPath](https://github.com/jayway/JsonPath)

#### 4.4.2 测试

##### 4.4.2.0 组件测试

直接@Autowired容器中的组件进行测试

##### 4.4.2.1 注解

JUnit5的注解与JUnit4的注解有所变化

[JUnit 5 User Guide](https://junit.org/junit5/docs/current/user-guide/#writing-tests-annotations)

- **@Test :**表示方法是测试方法。但是与JUnit4的@Test不同，他的职责非常单一不能声明任何属性，拓展的测试将会由Jupiter提供额外测试
- **@ParameterizedTest :**表示方法是参数化测试，下方会有详细介绍
- **@RepeatedTest :**表示方法可重复执行，下方会有详细介绍
- **@DisplayName :**为测试类或者测试方法设置展示名称
- **@BeforeEach :**表示在每个单元测试之前执行
- **@AfterEach :**表示在每个单元测试之后执行
- **@BeforeAll :**表示在所有单元测试之前执行
- **@AfterAll :**表示在所有单元测试之后执行
- **@Tag :**表示单元测试类别，类似于JUnit4中的@Categories
- **@Disabled :**表示测试类或测试方法不执行，类似于JUnit4中的@Ignore
- **@Timeout :**表示测试方法运行如果超过了指定时间将会返回错误
- **@ExtendWith :**为测试类或测试方法提供扩展类引用

------

#####  4.4.2.2 断言

| 方法              | 说明                                 |
| ----------------- | ------------------------------------ |
| assertEquals      | 判断两个对象或两个原始类型是否相等   |
| assertNotEquals   | 判断两个对象或两个原始类型是否不相等 |
| assertSame        | 判断两个对象引用是否指向同一个对象   |
| assertNotSame     | 判断两个对象引用是否指向不同的对象   |
| assertTrue        | 判断给定的布尔值是否为 true          |
| assertFalse       | 判断给定的布尔值是否为 false         |
| assertNull        | 判断给定的对象引用是否为 null        |
| assertNotNull     | 判断给定的对象引用是否不为 null      |
| assertArrayEquals | 数组断言                             |
| assertAll         | 组合断言                             |
| assertThrows      | 异常断言                             |
| assertTimeout     | 超时断言                             |
| fail              | 快速失败                             |

##### 4.4.2.3 嵌套测试

> JUnit 5 可以通过 Java 中的内部类和@Nested 注解实现嵌套测试，从而可以更好的把相关的测试方法组织在一起。在内部类中可以使用@BeforeEach 和@AfterEach 注解，而且嵌套的层次没有限制。

##### 4.4.2.4 参数化测试（了解）

参数化测试是JUnit5很重要的一个新特性，它使得用不同的参数多次运行测试成为了可能，也为我们的单元测试带来许多便利。

利用**@ValueSource**等注解，指定入参，我们将可以使用不同的参数进行多次单元测试，而不需要每新增一个参数就新增一个单元测试，省去了很多冗余代码。

**@ValueSource**: 为参数化测试指定入参来源，支持八大基础类以及String类型,Class类型

**@NullSource**: 表示为参数化测试提供一个null的入参

**@EnumSource**: 表示为参数化测试提供一个枚举入参

**@CsvFileSource**：表示读取指定CSV文件内容作为参数化测试入参

**@MethodSource**：表示读取指定方法的返回值作为参数化测试入参(注意方法返回需要是一个流)

## 5 SpringBoot3-核心原理

### 5.1 事件和监听器

#### 5.1.1 生命周期监听

场景：监听**应用**的***\*生命周期\****

##### 5.1.1.1 监听器-SpringApplicationRunListener

自定义SpringApplicationRunListener来**监听事件：**

1 编写SpringApplicationRunListener ***\*实现类\****

2 在 META-INF/spring.factories 中配置 org.springframework.boot.SpringApplicationRunListener=自己的Listener，还可以指定一个**有参构造器**，接受两个参数(SpringApplication application, String[] args)

3 springboot 在spring-boot.jar中配置了默认的 Listener，如下

![img](https://i-blog.csdnimg.cn/blog_migrate/7b9063c1fc7fa45652123fdf074d9cac.png)

##### 5.1.1.2 生命周期全流程

![img](https://i-blog.csdnimg.cn/blog_migrate/7bb0fd1f8180848f996ff63299b5f307.png)

#### 5.1.2 事件触发时机

##### 5.1.2.1 各种回调监听器

- BootstrapRegistryInitializer：

   

  **感知特定阶段：**

  感知

  引导初始化

  - META-INF/spring.factories
  - 创建引导上下文bootstrapContext的时候触发。
  - application.addBootstrapRegistryInitializer();
  - 场景：进行密钥校对授权。

- ApplicationContextInitializer：

   

  **感知特定阶段：**

   

  感知ioc容器初始化

  - META-INF/spring.factories
  - application.addInitializers();

- **ApplicationListener**

  **： 感知全阶段：基于事件机制，感知事件。 一旦到了哪个阶段可以做别的事**

  - @Bean或@EventListener： 事件驱动
  - SpringApplication.addListeners(…)或 SpringApplicationBuilder.listeners(…)
  - META-INF/spring.factories

- **SpringApplicationRunListener**

  **： 感知全阶段生命周期 + 各种阶段都能自定义操作； 功能更完善。**

  - META-INF/spring.factories

- **ApplicationRunner:** 

  **感知特定阶段：感知应用就绪Ready。卡死应用，就不会就绪**

  - @Bean

- **CommandLineRunner**

  **： 感知特定阶段：感知应用就绪Ready。卡死应用，就不会就绪**

  - @Bean

最佳实战：

- 如果项目启动前做事： BootstrapRegistryInitializer 和 ApplicationContextInitializer
- 如果想要在项目启动完成后做事：***\*ApplicationRunner\*******\*和\**** ***\*CommandLineRunner\****
- ***\*如果要干涉生命周期做事：\*******\*SpringApplicationRunListener\****
- ***\*如果想要用事件机制：\*******\*ApplicationListener\****

##### 5.1.2.2 完整触发流程

***\*9\*******\*大事件\****触发顺序&时机

1. ApplicationStartingEvent：应用启动但未做任何事情, 除过注册listeners and initializers.
2. ApplicationEnvironmentPreparedEvent： Environment 准备好，但context 未创建.
3. ApplicationContextInitializedEvent: ApplicationContext 准备好，ApplicationContextInitializers 调用，但是任何bean未加载
4. ApplicationPreparedEvent： 容器刷新之前，bean定义信息加载
5. ApplicationStartedEvent： 容器刷新完成， runner未调用

=========以下就开始插入了**探针机制**============

1. AvailabilityChangeEvent： LivenessState.CORRECT应用存活； **存活探针**
2. ApplicationReadyEvent: 任何runner被调用
3. AvailabilityChangeEvent：ReadinessState.ACCEPTING_TRAFFIC**就绪探针**，可以接请求
4. ApplicationFailedEvent ：启动出错

![img](https://i-blog.csdnimg.cn/blog_migrate/9bed1300ab64af8d6bc5305a43ab1a33.png)

应用事件发送顺序如下：

![img](https://i-blog.csdnimg.cn/blog_migrate/799a53a73ac0668f780435baf11debdd.png)

感知应用是否**存活**了：可能植物状态，虽然活着但是不能处理请求。

应用是否**就绪**了：能响应请求，说明确实活的比较好。

##### 5.1.2.3 SpringBoot 事件驱动开发

> 应用启动过程生命周期事件感知（9大事件）、应用运行中事件感知（无数种）。

- **事件发布**：`ApplicationEventPublisherAware`或`注入：ApplicationEventMulticaster`
- **事件监听**：`组件 + @EventListener`

![img](https://i-blog.csdnimg.cn/blog_migrate/3fd4bd0ba4470249d2e3a7ced609676c.png)

![img](https://i-blog.csdnimg.cn/blog_migrate/b5840f48ddbc645cff9d116c0378017f.png)

------

举个例子

LoginController

------

事件发布者

事件订阅者 

### 5.2 自动配置原理★★★★★

#### 5.2.1 入门理解

> 应用关注的**三大核心**：**场景**、**配置**、**组件**

------

总结：场景导入自动配置类，自动配置类注入组件；组件绑定属性类，属性类绑定配置文件。

所以：修改配置文件，就能修改底层参数

------

##### 5.2.1.1 自动配置流程

![img](https://i-blog.csdnimg.cn/blog_migrate/00ee3f832b7bb32737bde3343a4af2b4.png)

1 导入starter

2 依赖导入autoconfigure包

3 寻找类路径下 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports文件

4 启动，加载所有 自动配置类 xxxAutoConfiguration

​    a）给容器中配置**功能**组件

​    b）组件参数绑定到 属性类中。xxxProperties

​    c）属性类和配置文件前缀项绑定

​    d）@Contional派生的条件注解进行判断**是否组件生效**

5 效果：

​    a）修改配置文件，修改底层参数

​    b）所有场景自动配置好直接使用

​    c）可以注入SpringBoot配置好的组件随时使用

##### 5.2.1.2 SPI机制

> - ***\*Java\*******\*中的SPI（Service Provider Interface）是一种软件设计模式，用于\*******\*在应用程序中动态地发现和加载组件\*******\*。\******SPI****的思想**是，定义一个接口或抽象类，然后通过在classpath中定义实现该接口的类来实现对组件的动态发现和加载。
> - SPI的主要目的是解决在应用程序中使用可插拔组件的问题。例如，一个应用程序可能需要使用不同的日志框架或数据库连接池，但是这些组件的选择可能取决于运行时的条件。通过使用SPI，应用程序可以在运行时发现并加载适当的组件，而无需在代码中硬编码这些组件的实现类。
> - 在Java中，**SPI**的实现方式是通过在META-INF/services目录下创建一个以服务接口全限定名为名字的文件，文件中包含实现该服务接口的类的全限定名。当应用程序启动时，Java的SPI机制会自动扫描classpath中的这些文件，并根据文件中指定的类名来加载实现类。
> - 通过使用SPI，应用程序可以实现更灵活、可扩展的架构，同时也可以避免硬编码依赖关系和增加代码的可维护性。
>
> 以上回答来自ChatGPT-3.5

在SpringBoot中，

META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 

SPI的例子，在《2025版DDD领域驱动设计实战天花板教程，7天学完DDD电商服务开放平台设计落地！》视频中，[使用DDD重新设计服务开放平台]章节中有涉及，可以对比查看

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\71969f1a0441492aba15890046c53c8a.png)

##### 5.2.1.3 功能开关

- 自动配置：全部都配置好，什么都不用管。

   

  **自动批量导入**

  - 项目一启动，spi文件中指定的所有都加载。

- @EnableXxxx：手动控制哪些功能的开启；

   

  **手动导入**

  - 开启xxx功能
  - 都是利用 @Import 把此功能要用的组件导入进去

#### 5.2.2 进阶理解

##### 5.2.2.1 @SpringBootApplication

**@SpringBootConfiguration**

就是： @Configuration ，容器中的组件，配置类。spring ioc启动就会加载创建这个类对象

**@EnableAutoConfiguration：开启自动配置**

开启自动配置

**@AutoConfigurationPackage：扫描主程序包：加载自己的组件**

- 利用 `@Import(AutoConfigurationPackages.Registrar.class)` 想要给容器中导入组件。
- 把主程序所在的**包**的所有组件导入进来。
- **为什么SpringBoot默认只扫描主程序所在的包及其子包**

**@Import(AutoConfigurationImportSelector.class)：加载所有自动配置类：加载starter导入的组件**

> 扫描SPI文件：
>
> META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 

**@ComponentScan**

> 组件扫描：排除一些组件（哪些不要）
>
> 排除前面已经扫描进来的配置类、和自动配置类。

**补充说明：三个注解间的关系（修正前面）**

##### ![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\7bea0fc1ceb04caaa13f5b8db789245f.png)

##### 5.2.2.2 完整启动加载流程

生命周期启动加载流程

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\d72b22031fc24de9b34843c6d6f56345.png)

### 5.3 自定义starter★★★★★

> 场景：**抽取聊天机器人场景，它可以打招呼**。
>
> 效果：任何项目导入此`starter`都具有打招呼功能，并且**问候语**中的**人名**需要可以在**配置文件**中修改

\1. 创建自定义starter项目，引入spring-boot-starter基础依赖

\2. 编写模块功能，引入模块所有需要的依赖。

\3. 编写xxxAutoConfiguration自动配置类，帮其他项目导入这个模块需要的所有组件

\4. 编写配置文件META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports指定启动需要加载的自动配置

\5. 其他项目引入即可使用

#### 5.3.1 业务代码

> 自定义配置有提示。导入以下依赖重启项目，再写配置文件就有提示

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\e3b1863f242a4c319a9fc051a396eea1.png)

#### 5.3.2 基本抽取

- 创建starter项目，把公共代码需要的所有依赖导入
- 把公共代码复制进来
- 自己写一个 RobotAutoConfiguration，给容器中导入这个场景需要的所有组件
  - 为什么这些组件默认不会扫描进去？
  - **starter所在的包和 引入它的项目的主程序所在的包不是父子层级★★★★★**

- 别人引用这个starter，直接导入这个 RobotAutoConfiguration,就能把这个场景的组件导入进来
- 功能生效。
- 测试编写配置文件

------

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\3d84348dd74a45bb9474f176773dce9a.png)

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\193f987837a641e89f645fd6263dc723.png)

RobotAutoConfiguration

#### 5.3.3 使用@EnableXxx机制

别人引入starter需要使用 @EnableRobot开启功能（使用@EnableRobot，相当于使用了@Import(RobotAutoConfiguration.class)）

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\fef7f5f62384458e8337c8cfc51f5a72.png)

#### 5.3.4 完全自动配置

依赖SpringBoot的SPI机制

META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 文件中编写好我们自动配置类的全类名即可（这样@EnableXxx都可以不用标了，导入依赖就可以使用）

项目启动，自动加载我们的自动配置类

![img](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\f8779518336c4dd29618a9dfff1a9746.png)

## 附录：SpringBoot3改变&新特性快速总结

1、**自动配置包位置变化**【参照视频：07、11】

META-INF/spring/**org.springframework.boot.autoconfigure.AutoConfiguration**.imports

2、**jakata api迁移**

- **druid****有问题**

3、**新特性** - **函数式Web**、**ProblemDetails**【参照视频：50、51】

**4****、GraalVM 与 AOT**【参照视频：86~93】

**5、响应式编程全套**