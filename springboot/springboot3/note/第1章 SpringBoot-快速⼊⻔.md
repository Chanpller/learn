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