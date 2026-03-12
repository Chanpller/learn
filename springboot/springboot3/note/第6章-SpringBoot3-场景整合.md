# 第6章-SpringBoot3-场景整合NoSQL

## 6.1环境准备

### 6.1.1 云服务器

* 阿里云、腾讯云、华为云 服务器开通； 按量付费，省钱省心
* 安装以下组件
  * docker
  * redis
  * kafka
  * prometheus
  * grafana
* https://github.com/kingToolbox/WindTerm/releases/download/2.5.0/WindTerm_2.5.0_Windows_Portable_x86_64.zip 下载windterm
* 重要：开通云服务器以后，请一定在安全组设置规则，放行端口

### 6.1.2 Docker安装

> 还不会docker的同学，参考【云原生实战（10~25集）】快速入门
>
> https://www.bilibili.com/video/BV13Q4y1C7hS?p=10

```
sudo yum install -y yum-utils
sudo yum-config-manager \
--add-repo \
https://download.docker.com/linux/centos/docker-ce.repo
sudo yum install docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin
sudo systemctl enable docker --now
#测试工作
docker ps
# 批量安装所有软件
docker compose
```

1. 创建 /prod﻿文件夹，准备以下文件

2. prometheus.yml

```yaml
global:
  scrape_interval: 15s
  evaluation_interval: 15s

scrape_configs:
  - job_name: 'prometheus'
    static_configs:
      - targets: ['localhost:9090']

  - job_name: 'redis'
    static_configs:
      - targets: ['redis:6379']

  - job_name: 'kafka'
    static_configs:
      - targets: ['kafka:9092']
```

3. docker-compose.yml
   * http://poe.com/ChatGPT

```yaml
version: '3.9'

services:
  redis:
    image: redis:latest
    container_name: redis
    restart: always
    ports:
      - "6379:6379"
    networks:
      - backend

  zookeeper:
    image: bitnami/zookeeper:latest
    container_name: zookeeper
    restart: always
    environment:
      ZOOKEEPER_CLIENT_PORT: 2181
      ZOOKEEPER_TICK_TIME: 2000
    networks:
      - backend

  kafka:
    image: bitnami/kafka:3.4.0
    container_name: kafka
    restart: always
    depends_on:
      - zookeeper
    ports:
      - "9092:9092"
    environment:
      ALLOW_PLAINTEXT_LISTENER: yes
      KAFKA_CFG_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR: 1
    networks:
      - backend

  kafka-ui:
    image: provectuslabs/kafka-ui:latest
    container_name: kafka-ui
    restart: always
    depends_on:
      - kafka
    ports:
      - "8080:8080"
    environment:
      KAFKA_CLUSTERS_0_NAME: dev
      KAFKA_CLUSTERS_0_BOOTSTRAPSERVERS: kafka:9092
    networks:
      - backend

  prometheus:
    image: prom/prometheus:latest
    container_name: prometheus
    restart: always
    volumes:
      - ./prometheus.yml:/etc/prometheus/prometheus.yml
    ports:
      - "9090:9090"
    networks:
      - backend

  grafana:
    image: grafana/grafana:latest
    container_name: grafana
    restart: always
    depends_on:
      - prometheus
    ports:
      - "3000:3000"
    networks:
      - backend

networks:
  backend:
    name: backend
```

4. 启动环境

   * 国内连docker可能连不过去，使用国内镜像。

   * 配置国内镜像源在/etc/docker/daemon.json 文件中添加或修改镜像源，如果没有该文件可以新建一个，将如下内容复制进去：

     ```json
     {
     	"registry-mirrors": [
     	    "https://docker.m.daocloud.io/",
     	    "https://huecker.io/",
     	    "https://dockerhub.timeweb.cloud",
     	    "https://noohub.ru/",
     	    "https://dockerproxy.com",
     	    "https://docker.mirrors.ustc.edu.cn",
     	    "https://docker.nju.edu.cn",
     	    "https://xx4bwyg2.mirror.aliyuncs.com",
     	    "http://f1361db2.m.daocloud.io",
     	    "https://registry.docker-cn.com",
     	    "http://hub-mirror.c.163.com"
     	  ]
       }
     ```

     ```json
     {
         "registry-mirrors": [
         	"https://docker-0.unsee.tech",
             "https://docker-cf.registry.cyou",
             "https://docker.1panel.live"
         ]
     }
     ```

   * 重启docker使配置生效：

     ```
     systemctl daemon-reload
     systemctl restart docker
     ```

     

```
docker compose -f docker-compose.yml up -d
```

5. 验证
   * Redis：你的ip:6379
     * 填写表单，下载官方可视化工具：
     * https://redis.com/redis-enterprise/redis-insight/#insight-form
   * Kafka：你的ip:9092
     * idea安装大数据插件
   * Prometheus：你的ip:9090
     * 直接浏览器访问
   * Grafana：你的ip:3000
     * 直接浏览器访问

## 6.2 NoSQL

Redis整合

> Redis不会的同学：参照 阳哥-《Redis7》 https://www.bilibili.com/video/BV13R4y1v7sP?p=1
> HashMap： key：value

### 6.2.1 场景整合

1. 依赖导入

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

2. 配置
   * 如果redis则需要配置cluster.nodes，不需要配置HOST

```properties
spring.data.redis.host=192.168.200.100
#如果redis则需要配置cluster.nodes，不需要配置HOST
#spring.data.redis.cluster.nodes=8.130.74.183:6379
spring.data.redis.password=Lfy123!@!
```

3. 测试

```java
@Autowired
StringRedisTemplate redisTemplate;
@Test
void redisTest(){
    redisTemplate.opsForValue().set("a","1234");
    Assertions.assertEquals("1234",redisTemplate.opsForValue().get("a"));
}
```

### 6.2.2 自动配置原理

1. META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports
   中导入了RedisAutoConfiguration﻿、RedisReactiveAutoConfiguration和RedisRepositoriesAutoConfiguration。所有属性绑定在﻿RedisProperties﻿中

2. RedisReactiveAutoConfiguration属于响应式编程，不用管。RedisRepositoriesAutoConfiguration属于 JPA 操作，也不用管

3. RedisAutoConfiguration配置了以下组件

   1.1. LettuceConnectionConfiguration： 给容器中注入了连接工厂LettuceConnectionFactory，和操作 redis 的客户端DefaultClientResources。
   1.2. RedisTemplate<Object, Object>﻿： 可给 redis 中存储任意对象，会使用 jdk 默认序列化方式。
   1.3. ﻿StringRedisTemplate﻿： 给 redis 中存储字符串，如果要存对象，需要开发人员自己进行序列化。key-value都是字符串进行操作··

6.2.3 定制化

1. 序列化机制，自定义序列化机制

   ```java
   @Configuration
   public class AppRedisConfiguration {
       /**
       * 允许Object类型的key-value，都可以被转为json进行存储。
       * @param redisConnectionFactory 自动配置好了连接工厂
       * @return
       */
       @Bean
       public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
           RedisTemplate<Object, Object> template = new RedisTemplate<>();
           template.setConnectionFactory(redisConnectionFactory);
           //把对象转为json字符串的序列化工具
           template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
           return template;
       }
   }
   ```

2. redis客户端

> RedisTemplate、StringRedisTemplate： 操作redis的工具类
>
> * 要从redis的连接工厂获取链接才能操作redis
> *  Redis客户端
>   *  Lettuce： 默认
>   *  Jedis：可以使用以下切换

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-data-redis</artifactId>
    <exclusions>
        <exclusion>
            <groupId>io.lettuce</groupId>
            <artifactId>lettuce-core</artifactId>
        </exclusion>
    </exclusions>
</dependency>
<!-- 切换 jedis 作为操作redis的底层客户端-->
<dependency>
    <groupId>redis.clients</groupId>
    <artifactId>jedis</artifactId>
</dependency>
```

3. 配置参考

   ```properties
   spring.data.redis.host=8.130.74.183
   #如果redis则需要配置cluster.nodes，不需要配置HOST
   #spring.data.redis.cluster.nodes=8.130.74.183:6379
   spring.data.redis.port=6379
   #spring.data.redis.client-type=lettuce
   #设置lettuce的底层参数
   #spring.data.redis.lettuce.pool.enabled=true
   #spring.data.redis.lettuce.pool.max-active=8
   spring.data.redis.client-type=jedis
   spring.data.redis.jedis.pool.enabled=true
   spring.data.redis.jedis.pool.max-active=8
   ```

   

## 6.3 接口文档

### 6.3.1 OpenAPI 3 与 Swagger

> Swagger 可以快速生成实时接口文档，方便前后开发人员进行协调沟通。遵循 OpenAPI 规范。
>
> 文档：https://springdoc.org/v2/

#### 6.3.1.1 OpenAPI 3 架构

![image-20260312220605467](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\image-20260312220605467.png)

#### 6.3.1.2 整合

导入场景

```
<dependency>
    <groupId>org.springdoc</groupId>
    <artifactId>springdoc-openapi-starter-webmvc-ui</artifactId>
    <version>2.1.0</version>
</dependency>
```

配置

```
# /api-docs endpoint custom path 默认 /v3/api-docs
springdoc.api-docs.path=/api-docs
# swagger 相关配置在 springdoc.swagger-ui
# swagger-ui custom path
springdoc.swagger-ui.path=/swagger-ui.html
springdoc.show-actuator=true
```

#### 6.3.1.3 使用

| 注解         | 标注位置            | 作用                   |
| ------------ | ------------------- | ---------------------- |
| @Tag         | controller类标识    | controller 作用        |
| @Parameter   | 参数标识            | 参数作用               |
| @Parameters  | 参数                | 参数多重说明           |
| @Schema      | model 层的 JavaBean | 描述模型作用及每个属性 |
| @Operation   | 方法                | 描述方法作用           |
| @ApiResponse | 方法                | 描述响应状态码等       |

### 6.3.2 Docket配置

> 如果有多个Docket，配置如下。
>
> Docket用于文档分组

```java
@Bean
public GroupedOpenApi publicApi() {
	return GroupedOpenApi.builder()
        .group("springshop-public")
        .pathsToMatch("/public/**")
        .build();
}
@Bean
public GroupedOpenApi adminApi() {
	return GroupedOpenApi.builder()
        .group("springshop-admin")
        .pathsToMatch("/admin/**")
        .addMethodFilter(method -> method.isAnnotationPresent(Admin.class))
        .build();
}
```

如果只有一个Docket，可以配置如下

```properties
springdoc.packagesToScan=package1, package2
springdoc.pathsToMatch=/v1, /api/balance/**
```

上面代码用于控制下面区域

![image-20260312222559586](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\image-20260312222559586.png)

### 6.3.3 OpenAPI配置

```java
@Bean
public OpenAPI springShopOpenAPI() {
	return new OpenAPI()
        .info(new Info()
              .title("SpringShop API")
              .description("Spring shop sample application")
              .version("v0.0.1")
              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
                      .description("SpringShop Wiki Documentation")
                      .url("https://springshop.wiki.github.org/docs"));
}
```

上面代码用于控制下面区域

![image-20260312222452352](D:\workspace\IdeaProjects\learn\springboot\springboot3\image\image-20260312222452352.png)

### 6.3.4 Springfox 迁移

#### 6.3.4.1 注解变化

| 原注解 | 现注解 | 作用 |
| ------ | ------ | ---- |
|@Api |@Tag |描述Controller|
|@ApiIgnore |@Parameter(hidden = true)<br/>@Operation(hidden = true)<br/>@Hidden|描述忽略操作|
|@ApiImplicitParam |@Parameter |描述参数|
|@ApiImplicitParams |@Parameters|描述参数|
|@ApiModel |@Schema|描述对象|
|@ApiModelProperty(hidden= true)|@Schema(accessMode =READ_ONLY)|描述对象属性|
|@ApiModelProperty |@Schema |描述对象属性|
|@ApiOperation(value ="foo", notes = "bar")|@Operation(summary ="foo", description = "bar")|描述方法|
|@ApiParam |@Parameter |描述参数|
|@ApiResponse(code = 404,message = "foo") |@ApiResponse(responseCode = "404", description ="foo") |描述响应|

#### 6.3.4.2 Docket配置变化

以前写法

```java
@Bean
public Docket publicApi() {
	return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.github.springshop.web.public"))
        .paths(PathSelectors.regex("/public.*"))
        .build()
        .groupName("springshop-public")
        .apiInfo(apiInfo());
}
@Bean
public Docket adminApi() {
	return new Docket(DocumentationType.SWAGGER_2)
        .select()
        .apis(RequestHandlerSelectors.basePackage("org.github.springshop.web.admin"))
        .paths(PathSelectors.regex("/admin.*"))
        .apis(RequestHandlerSelectors.withMethodAnnotation(Admin.class))
        .build()
        .groupName("springshop-admin")
        .apiInfo(apiInfo());
}
```

新写法

```java
@Bean
public GroupedOpenApi publicApi() {
	return GroupedOpenApi
        .builder()
        .group("springshop-public")
        .pathsToMatch("/public/**")
        .build();
}
@Bean
public GroupedOpenApi adminApi() {
	return GroupedOpenApi
        .builder()
        .group("springshop-admin")
        .pathsToMatch("/admin/**")
        .addOpenApiMethodFilter(method -> method.isAnnotationPresent(Admin.class))
        .build();
}
```

添加OpenAPI组件

```java
@Bean
public OpenAPI springShopOpenAPI() {
	return new OpenAPI()
        .info(new Info()
              .title("SpringShop API")
              .description("Spring shop sample application")
              .version("v0.0.1")
              .license(new License().name("Apache 2.0").url("http://springdoc.org")))
        .externalDocs(new ExternalDocumentation()
                      .description("SpringShop Wiki Documentation")
                      .url("https://springshop.wiki.github.org/docs"));
}
```

### 6.3.5 Knife4j

Knife4j是基于SpringBoot构建的一个文档生成工具，它可以让开发者为我们的应用生成在线API文档； 目的是可以更加方便的基于API文档进行测试。 生成的文档还可以导出，然后给到前端开发团队，前端开发团队可以基于API接口写具体的调用。 是基于Swagger框架实现的。

 Knife4j的优点 Knife4j 功能强大，易于操作。 Knife4j 的UI界面非常美观，使用流畅。 Knife4j 可以高度定制化，让其符合你的项目需求

```xml
<!--添加Knife4j依赖-->
<dependency>
    <groupId>com.github.xiaoymin</groupId>
    <artifactId>knife4j-spring-boot-starter</artifactId>
    <version>2.0.9</version>
    <!--<version>4.3.0</version>-->
</dependency>
```

