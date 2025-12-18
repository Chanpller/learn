# 第2章-SpringBoot3-Web开发

> SpringBoot的Web开发能力，由SpringMVC提供。
>

## 2.0 WebMvcAutoConfiguration原理

### 2.0.1 生效条件

```java
@AutoConfiguration(after = { DispatcherServletAutoConfiguration.class, TaskExecutionAutoConfiguration.class,ValidationAutoConfiguration.class }) //在这些自动配置之后
@ConditionalOnWebApplication(type = Type.SERVLET) //如果是web应用就生效，类型SERVLET、REACTIVE 响应式web
@ConditionalOnClass({ Servlet.class, DispatcherServlet.class,WebMvcConfigurer.class })
@ConditionalOnMissingBean(WebMvcConfigurationSupport.class) //容器中没有这个Bean，才生效。默认就是没有
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE + 10)//优先级
@ImportRuntimeHints(WebResourcesRuntimeHints.class)
public class WebMvcAutoConfiguration {
}
```

### 2.0.2 效果

a）放了两个Filter：

1. HiddenHttpMethodFilter；页面表单提交Rest请求（GET、POST、PUT、DELETE）
2. FormContentFilter： 表单内容Filter，GET（数据放URL后面）、POST（数据放请求体）请求可以携带数据，PUT、DELETE 的请求体数据会被忽略

b）给容器中放了WebMvcConfigurer组件；给SpringMVC添加各种定制功能

1. 所有的功能最终会和配置文件进行绑定
2. WebMvcProperties： spring.mvc配置文件
3. WebProperties： spring.web配置文件

### 2.0.3 WebMvcConfigurer接口

提供了配置SpringMVC底层的所有组件入口。WebMvcAutoConfiguration

![img](../image/deed4a847b42f3419685747a17504325.png)

#### 2.0.4 静态资源规则源码

* WebMvcAutoConfiguration配置文件中

  ```java
   public void addResourceHandlers(ResourceHandlerRegistry registry) {
       if (!this.resourceProperties.isAddMappings()) {
           logger.debug("Default resource handling disabled");
       } else {
           this.addResourceHandler(registry, this.mvcProperties.getWebjarsPathPattern(), "classpath:/META-INF/resources/webjars/");
           this.addResourceHandler(registry, this.mvcProperties.getStaticPathPattern(), (Consumer)((registration) -> {
               registration.addResourceLocations(this.resourceProperties.getStaticLocations());
               if (this.servletContext != null) {
                   ServletContextResource resource = new ServletContextResource(this.servletContext, "/");
                   registration.addResourceLocations(new Resource[]{resource});
               }
  
           }));
       }
   }
  ```

* WebMvcProperties配置文件中

  ```java
  private String webjarsPathPattern = "/webjars/**";
  
  public String getWebjarsPathPattern() {
      return this.webjarsPathPattern;
  }
  ```

a）规则一：访问： /webjars/路径就去 classpath:/META-INF/resources/webjars/下找资源.

1. maven 导入依赖

b）规则二：访问： /路径就去 静态资源默认的四个位置找资源

1. classpath:/META-INF/resources/
2. classpath:/resources/
3. classpath:/static/
4. classpath:/public/

c）规则三：静态资源默认都有缓存规则的设置

1. 所有缓存的设置，直接通过配置文件： spring.web
2. cachePeriod： 缓存周期； 多久不用找服务器要新的。 默认没有，以s为单位
3. cacheControl： HTTP缓存控制；[https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching](https://developer.mozilla.org/zh-CN/docs/Web/HTTP/Caching#概览)
4. useLastModified：是否使用最后一次修改。配合HTTP Cache规则

> 如果浏览器访问了一个静态资源 index.js，如果服务这个资源没有发生变化，下次访问的时候就可以直接让浏览器用自己缓存中的东西，而不用给服务器发请求。

```java
 private void addResourceHandler(ResourceHandlerRegistry registry, String pattern, Consumer<ResourceHandlerRegistration> customizer) {
     if (!registry.hasMappingForPattern(pattern)) {
         ResourceHandlerRegistration registration = registry.addResourceHandler(new String[]{pattern});
         customizer.accept(registration);
         registration.setCachePeriod(this.getSeconds(this.resourceProperties.getCache().getPeriod()));
         registration.setCacheControl(this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl());
         registration.setUseLastModified(this.resourceProperties.getCache().isUseLastModified());
         this.customizeResourceHandlerRegistration(registration);
     }
 }
```

#### 2.0.5 EnableWebMvcConfiguration 源码

```java
//SpringBoot 给容器中放WebMvcConfigurationSupport组件。
//我们如果自己放了给容器中放WebMvcConfigurationSupport组件，Boot的WebMvcAutoConfiguration都会失效
@Configuration(proxyBeanMethods = false)
@EnableConfigurationProperties({WebProperties.class})
public static class EnableWebMvcConfiguration extends DelegatingWebMvcConfiguration implements ResourceLoaderAware {
}
```

1. HandlerMapping： 根据请求路径 /a 找哪个handler能处理请求
   - WelcomePageHandlerMapping：
     - 访问 /路径下的所有请求，都在以前四个静态资源路径下找，欢迎页也一样
     - 找index.html：只要静态资源的位置有一个 index.html页面，项目启动默认访问

#### 2.0.6 为什么容器中放一个WebMvcConfigurer就能配置底层行为

1. WebMvcAutoConfiguration 是一个自动配置类，它里面有一个EnableWebMvcConfiguration

2. EnableWebMvcConfiguration继承于 DelegatingWebMvcConfiguration，这两个都生效

3. DelegatingWebMvcConfiguration利用 DI 把容器中 所有 WebMvcConfigurer 注入进来，保存在configures属性中

![img](../image/a8dd08fbf6ce451ea04d3396532f59d2.png)

4. 别人调用 DelegatingWebMvcConfiguration 的方法配置底层规则，而它调用所有 WebMvcConfigurer的配置底层方法。

#### 2.0.7 WebMvcConfigurationSupport

提供了很多的默认设置。

判断系统中是否有相应的类：如果有，就加入相应的HttpMessageConverter

```java
static {
        ClassLoader classLoader = WebMvcConfigurationSupport.class.getClassLoader();
        romePresent = ClassUtils.isPresent("com.rometools.rome.feed.WireFeed", classLoader);
        jaxb2Present = ClassUtils.isPresent("jakarta.xml.bind.Binder", classLoader);
        jackson2Present = ClassUtils.isPresent("com.fasterxml.jackson.databind.ObjectMapper", classLoader) && ClassUtils.isPresent("com.fasterxml.jackson.core.JsonGenerator", classLoader);
        jackson2XmlPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.xml.XmlMapper", classLoader);
        jackson2SmilePresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.smile.SmileFactory", classLoader);
        jackson2CborPresent = ClassUtils.isPresent("com.fasterxml.jackson.dataformat.cbor.CBORFactory", classLoader);
        gsonPresent = ClassUtils.isPresent("com.google.gson.Gson", classLoader);
        jsonbPresent = ClassUtils.isPresent("jakarta.json.bind.Jsonb", classLoader);
        kotlinSerializationCborPresent = ClassUtils.isPresent("kotlinx.serialization.cbor.Cbor", classLoader);
        kotlinSerializationJsonPresent = ClassUtils.isPresent("kotlinx.serialization.json.Json", classLoader);
        kotlinSerializationProtobufPresent = ClassUtils.isPresent("kotlinx.serialization.protobuf.ProtoBuf", classLoader);
    }
```

## 2.1 Web场景

### 2.1.1 自动配置

1、整合web场景

```xml
 <dependency>
     <groupId>org.springframework.boot</groupId>
     <artifactId>spring-boot-starter-web</artifactId>
 </dependency>
```

2、引入了 autoconfigure功能

3、@EnableAutoConfiguration注解使用@Import(AutoConfigurationImportSelector.class)批量导入组件

4、加载 META-INF/spring/org.springframework.boot.autoconfigure.AutoConfiguration.imports 文件中配置的所有组件

5、所有web自动配置类如下

```shell
org.springframework.boot.autoconfigure.web.client.RestTemplateAutoConfiguration
org.springframework.boot.autoconfigure.web.embedded.EmbeddedWebServerFactoryCustomizerAutoConfiguration
====以下是响应式web场景和现在的没关系======
org.springframework.boot.autoconfigure.web.reactive.HttpHandlerAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.ReactiveMultipartAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.ReactiveWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.WebFluxAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.WebSessionIdResolverAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.error.ErrorWebFluxAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.function.client.ClientHttpConnectorAutoConfiguration
org.springframework.boot.autoconfigure.web.reactive.function.client.WebClientAutoConfiguration
================以上没关系=================
org.springframework.boot.autoconfigure.web.servlet.DispatcherServletAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.ServletWebServerFactoryAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.HttpEncodingAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.MultipartAutoConfiguration
org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration
```

6、绑定了配置文件的一堆配置项

​    a）SpringMVC的所有配置以 spring.mvc 开头

​    b）Web场景通用配置以 spring.web 开头

​    c）文件上传配置以 spring.servlet.multipart 开头

​    d）服务器的配置以 server: 开头，比如：编码方式

### 2.1.2 默认效果

SpringBoot整合SpringMVC

默认配置：

1. 包含了 ContentNegotiatingViewResolver 和 BeanNameViewResolver 组件，方便视图解析

2. 默认的静态资源处理机制： 静态资源放在 static 文件夹下即可直接访问

3. 自动注册了 Converter,GenericConverter,Formatter组件，适配常见数据类型转换和格式化需求

4. 支持 HttpMessageConverters，可以方便返回json等数据类型

5. 注册 MessageCodesResolver，方便国际化及错误消息处理

6. 支持 静态 index.html

7. 自动使用ConfigurableWebBindingInitializer，实现消息处理、数据绑定、类型转化、数据校验等功能

> 重要：
>
> - 如果想保持 boot mvc 的默认配置，并且自定义更多的 mvc 配置，如：interceptors, formatters, view controllers 等。可以使用@Configuration注解添加一个 WebMvcConfigurer 类型的配置类，并不要标注 @EnableWebMvc
> - 如果想保持 boot mvc 的默认配置，但要自定义核心组件实例，比如：RequestMappingHandlerMapping, RequestMappingHandlerAdapter, 或ExceptionHandlerExceptionResolver，给容器中放一个 WebMvcRegistrations 组件即可
> - 如果想全面接管 Spring MVC，@Configuration 标注一个配置类，并加上 @EnableWebMvc注解，实现 WebMvcConfigurer 接口

## 2.2 静态资源

### 2.2.1 默认规则

#### 2.2.1.1 静态资源映射

静态资源映射规则在 WebMvcAutoConfiguration 中进行了定义：

a） /webjars/** 的所有路径 资源都在 classpath:/META-INF/resources/webjars/

b）/** 的所有路径 资源都在 classpath:/META-INF/resources/、classpath:/resources/、classpath:/static/、classpath:/public/

c）所有静态资源都定义了缓存规则。【浏览器访问过一次，就会缓存一段时间】，但此功能参数无默认值

1. period： 缓存间隔。 默认 0S；
2. cacheControl：缓存控制。 默认无；
3. useLastModified：是否使用lastModified头。 默认 false；

#### 2.2.1.2 静态资源缓存

如前面所述

所有静态资源都定义了缓存规则。【浏览器访问过一次，就会缓存一段时间】，但此功能参数无默认值

1. period： 缓存间隔。 默认 0S；
2. cacheControl：缓存控制。 默认无；
3. useLastModified：是否使用lastModified头。 默认 false；

#### 2.2.1.3 欢迎页

欢迎页规则在 WebMvcAutoConfiguration 中进行了定义：

1. 在静态资源目录下找 index.html
2. 没有就在 templates下找index模板页

#### 2.2.1.4 Favicon

1. 在静态资源目录下找 favicon.ico

#### 2.2.1.5 缓存实验

```properties
server.port=9000
#1、spring.web：
# 1.配置国际化的区域信息
# 2.静态资源策略(开启、处理链、缓存)
#开启静态资源映射规则
spring.web.resources.add-mappings=true
#设置缓存
#spring.web.resources.cache.period=3600
##缓存详细合并项控制，覆盖period配置：
## 浏览器第一次请求服务器，服务器告诉浏览器此资源缓存7200秒，7200秒以内的所有此资源访问不用
发给服务器请求，7200秒以后发请求给服务器
spring.web.resources.cache.cachecontrol.max-age=7200
#使用资源 last-modified 时间，来对比服务器和浏览器的资源是否相同没有变化。相同返回 304
spring.web.resources.cache.use-last-modified=true
```

### 2.2.2 自定义静态资源规则

> 自定义静态资源路径、自定义缓存规则
>

#### 2.2.2.1 配置方式

spring.mvc： 静态资源访问前缀路径

spring.web：

- 静态资源目录
- 静态资源缓存策略

```properties
#1、spring.web：
# 1.配置国际化的区域信息
# 2.静态资源策略(开启、处理链、缓存)
#开启静态资源映射规则
spring.web.resources.add-mappings=true
#设置缓存
spring.web.resources.cache.period=3600
##缓存详细合并项控制，覆盖period配置：
## 浏览器第一次请求服务器，服务器告诉浏览器此资源缓存7200秒，7200秒以内的所有此资源访问不用
发给服务器请求，7200秒以后发请求给服务器
spring.web.resources.cache.cachecontrol.max-age=7200
## 共享缓存
spring.web.resources.cache.cachecontrol.cache-public=true
#使用资源 last-modified 时间，来对比服务器和浏览器的资源是否相同没有变化。相同返回 304
spring.web.resources.cache.use-last-modified=true
#自定义静态资源文件夹位置
spring.web.resources.static-locations=classpath:/a/,classpath:/b/,classpath:/static/
#2、 spring.mvc
## 2.1. 自定义webjars路径前缀
spring.mvc.webjars-path-pattern=/wj/**
## 2.2. 静态资源访问路径前缀
spring.mvc.static-path-pattern=/static/**
```

#### 2.2.2.2 代码方式

> - 容器中只要有一个 WebMvcConfigurer 组件。配置的底层行为都会生效
> - @EnableWebMvc //禁用boot的默认配置，全手动

```java
@Configuration //这是一个配置类
public class MyConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //保留以前规则
        //自己写新的规则。
        registry.addResourceHandler("/static/**")
        .addResourceLocations("classpath:/a/","classpath:/b/")
        .setCacheControl(CacheControl.maxAge(1180, TimeUnit.SECONDS));
    }
}
```

```java
@Configuration //这是一个配置类,给容器中放一个 WebMvcConfigurer 组件，就能自定义底层
public class MyConfig /*implements WebMvcConfigurer*/ {
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {
            @Override
            public void addResourceHandlers(ResourceHandlerRegistry registry)
            {
                registry.addResourceHandler("/static/**")
                    .addResourceLocations("classpath:/a/", "classpath:/b/")
                    .setCacheControl(CacheControl.maxAge(1180, TimeUnit.SECONDS));
            }
        };
    }
}
```



## 2.3 路径匹配

> Spring5.3 之后加入了更多的请求路径匹配的实现策略；
>
> 以前只支持 AntPathMatcher 策略, 现在提供了 PathPatternParser 策略。并且可以让我们指定到底使用那种策略。

### 2.3.1 Ant风格路径用法

Ant 风格的路径模式语法具有以下规则：

- ：表示任意数量的字符。
- ?：表示任意一个字符。
- **：表示任意数量的目录。
- {}：表示一个命名的模式占位符。
- []：表示字符集合，例如[a-z]表示小写字母。

例如：

- *.html 匹配任意名称，扩展名为.html的文件。
- `/folder1/*/*.java` 匹配在folder1目录下的任意两级目录下的.java文件。
- `/folder2/*/*.jsp` 匹配在folder2目录下任意目录深度的.jsp文件。
- /{type}/{id}.html 匹配任意文件名为{id}.html，在任意命名的{type}目录下的文件。

注意：Ant 风格的路径模式语法中的特殊字符需要转义，如：

- 要匹配文件路径中的星号，则需要转义为`\\*`。
- 要匹配文件路径中的问号，则需要转义为`\\?`。

### 2.3.2 模式切换

> AntPathMatcher 与 PathPatternParser
>
> - PathPatternParser 在 jmh 基准测试下，有 6~8 倍吞吐量提升，降低 30%~40%空间分配率
> - PathPatternParser 兼容 AntPathMatcher语法，并支持更多类型的路径模式
> - PathPatternParser "**" 多段匹配的支持仅允许在模式末尾使用，不能匹配  在中间的情况，剩下的和 antPathMatcher语法兼容
>

```java
@GetMapping("/a*/b?/{p1:[a-f]+}")
public String hello(HttpServletRequest request,@PathVariable("p1") String path){
    log.info("路径变量p1： {}", path);
    //获取请求路径
    String uri = request.getRequestURI();
    return uri;
}
```

总结：

- 使用默认的路径匹配规则，是由 PathPatternParser 提供的
- 如果路径中间需要有**，替换成ant风格路径

```properties
# 改变路径匹配策略：
# ant_path_matcher 老版策略；
# path_pattern_parser 新版策略；
spring.mvc.pathmatch.matching-strategy=ant_path_matcher
```

## 2.4 内容协商

一套系统适配多端数据返回



![img](../image/cb738dc39482f9b54ccf8e961ede58ae.png)

### 2.4.1 多端内容适配

#### 2.4.1.1 默认规则

SpringBoot 多端内容适配:

a）基于请求头内容协商：（默认开启）

客户端向服务端发送请求，携带HTTP标准的Accept请求头。

1. Accept: application/json、text/xml、text/yaml
2. 服务端根据客户端请求头期望的数据类型进行动态返回

b）基于请求参数内容协商：（需要手动开启）

1. 发送请求 GET /projects/spring-boot?format=json
2. 匹配到 @GetMapping("/projects/spring-boot")
3. 根据参数协商，优先返回 json 类型数据【需要开启参数匹配设置】
4. 发送请求 GET /projects/spring-boot?format=xml,优先返回 xml 类型数据

#### 2.4.1.2 效果演示

> 请求同一个接口，可以返回json和xml不同格式数据
>

1、引入支持写出xml内容依赖

```xml
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-xml</artifactId>
</dependency>
```

2、标注注解

```java
@JacksonXmlRootElement// 可以写出为xml文档
@Data
public class Person{
    private Long id;
    private String userName;
    private String email;
    private Integer age;
}
```

3、开启基于请求参数的内容协商

```properties
# 开启基于请求参数的内容协商功能。 默认参数名：format。 默认此功能不开启
spring.mvc.contentnegotiation.favor-parameter=true
# 指定内容协商时使用的参数名。默认是 format
spring.mvc.contentnegotiation.parameter-name=type
```

4、效果

![img](../image/b013f41b744a8712b9621c6ec651390c.png)



![img](../image/beb4fa6a7db806d7f45af31fc5e0c658.png)

#### 2.4.1.3 配置协商规则与支持类型

修改内容协商方式

```

```

![img](../image/3c229443f83f41e092bd7f765147e30e.png)

大多数 MediaType 都是开箱即用的。也可以自定义内容类型，如：

```properties
spring.mvc.contentnegotiation.media-types.yaml=text/yaml
```

### 2.4.2 自定义内容返回

#### 2.4.2.1 增加yaml返回支持

导入依赖

```XML
<dependency>
    <groupId>com.fasterxml.jackson.dataformat</groupId>
    <artifactId>jackson-dataformat-yaml</artifactId>
</dependency>
```

把对象写出成YAML

```java
public static void main(String[] args) throws JsonProcessingException {
    Person person = new Person(); 
    person.setId(1L); 
    person.setUserName("张三"); 
    person.setEmail("aaa@qq.com"); 
    person.setAge(18); 
    YAMLFactory factory = new YAMLFactory().disable(YAMLGenerator.Feature .WRITE_DOC_START_MARKER); 
    ObjectMapper mapper = new ObjectMapper(factory); 
    String s = mapper.writeValueAsString(person); 
    System.out.println(s) ; 
}
```

![img](../image/fcb8c8e84e1d4a5d9dbcfe010e87b62b.png)

编写配置

```properties
#新增一种媒体类型
spring.mvc.contentnegotiation.media-types.yaml=text/yaml
```

增加HttpMessageConverter组件，专门负责把对象写出为yaml格式

```java
@Bean
public WebMvcConfigurer webMvcConfigurer(){
    return new WebMvcConfigure(){
        @Override//配置一个能把对象转为yaml的messageConverter
        public void configureMessageConverters (List<HttpMessageConverter<?>> converters){
            converters.add(new MyYamlHttpMessageConverter());
        }
    }; 
}
```

#### 2.4.2.2 思考：如何增加其他

配置媒体类型支持:

- spring.mvc.contentnegotiation.media-types.yaml=text/yaml

编写对应的HttpMessageConverter，要告诉Boot这个支持的媒体类型

- 按照3的示例

把MessageConverter组件加入到底层

- 容器中放一个WebMvcConfigurer 组件，并配置底层的MessageConverter

#### 2.4.2.3 HttpMessageConverter的示例写法

```java
public class MyYamlHttpMessageConverter extends AbstractHttpMessageConverter<Object> {

    //把对象转成yaml
    private final ObjectMapper objectMapper;

    public MyYamlHttpMessageConverter() {
        //告诉SpringBoot这个MessageConverter支持哪种媒体类型 //媒体类型
        super(new MediaType("text", "yaml", Charset.forName("UTF-8")));
        YAMLFactory factory = new YAMLFactory()
                .disable(YAMLGenerator.Feature.WRITE_DOC_START_MARKER);
        this.objectMapper = new ObjectMapper(factory);
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        // 只要是对象类型，不是基本类型，就支持
        return true;
    }

    @Override //@RequestBody
    protected Object readInternal(Class<?> clazz, HttpInputMessage inputMessage)
            throws IOException, HttpMessageNotReadableException {
        return null;
    }

    @Override //@ResponseBody 把对象怎么写出去
    protected void writeInternal(Object methodReturnValue, HttpOutputMessage outputMessage)
            throws IOException, HttpMessageNotWritableException {
        //try-with写法，自动关流
        try (OutputStream os = outputMessage.getBody()) {
            objectMapper.writeValue(os, methodReturnValue);
        }
    }
}
```

### 2.4.3 内容协商原理-HttpMessageConverter

> - HttpMessageConverter 怎么工作？合适工作？
> - 定制 HttpMessageConverter 来实现多端内容协商
> - 编写WebMvcConfigurer提供的configureMessageConverters底层，修改底层的MessageConverter

#### 2.4.3.1 @ResponseBody由HttpMessageConverter处理

> 标注了@ResponseBody的返回值 将会由支持它的 HttpMessageConverter写给浏览器

1. 如果controller方法的返回值标注了 @ResponseBody 注解

   a）请求进来先来到 DispatcherServlet的doDispatch() 进行处理

   b）找到一个 HandlerAdapter 适配器。利用适配器执行目标方法

   c）RequestMappingHandlerAdapter来执行，调用invokeHandlerMethod（）来执行目标方法

   d）目标方法执行之前，准备好两个东西

   1. HandlerMethodArgumentResolver：参数解析器，确定目标方法每个参数值
   2. HandlerMethodReturnValueHandler：返回值处理器，确定目标方法的返回值改怎么处理

   e）RequestMappingHandlerAdapter 里面的invokeAndHandle()真正执行目标方法

   f）目标方法执行完成，会返回返回值对象

   g）找到一个合适的返回值处理器 HandlerMethodReturnValueHandler

   h）最终找到 RequestResponseBodyMethodProcessor能处理 标注了 @ResponseBody注解的方法

   i）RequestResponseBodyMethodProcessor 调用writeWithMessageConverters ,利用MessageConverter把返回值写出去

> 上面步骤解释：@ResponseBody由HttpMessageConverter处理

2. HttpMessageConverter 会先进行内容协商

   a）遍历所有的MessageConverter看谁支持这种内容类型的数据

   b）默认MessageConverter有以下

   ![img](../image/5f5c4e1ba583324d30866fcc2b521775.png)

   c）最终因为要json所以MappingJackson2HttpMessageConverter支持写出json

   d）jackson用ObjectMapper把对象写出去

#### 2.4.3.2 WebMvcAutoConfiguration提供几种默认HttpMessageConverters

EnableWebMvcConfiguration通过 addDefaultHttpMessageConverters添加了默认的MessageConverter；如下：

- ByteArrayHttpMessageConverter： 支持字节数据读写
- StringHttpMessageConverter： 支持字符串读写
- ResourceHttpMessageConverter：支持资源读写
- ResourceRegionHttpMessageConverter: 支持分区资源写出
- AllEncompassingFormHttpMessageConverter：支持表单xml/json读写
- MappingJackson2HttpMessageConverter： 支持请求响应体Json读写

默认8个：

![img](../image/13567250152cf0baf592d920a803adfa.png)

> 系统提供默认的MessageConverter 功能有限，仅用于json或者普通返回数据。额外增加新的内容协商功能，必须增加新的HttpMessageConverter

## 2.5 模板引擎

> 由于 SpringBoot 使用了嵌入式 Servlet 容器。所以 JSP 默认是不能使用的。
>
> 如果需要服务端页面渲染，优先考虑使用 模板引擎。
>

![image.png](../image/fc42e54f5a54578b96964d410fb46548.png)

模板引擎页面默认放在 src/main/resources/templates

SpringBoot 包含以下模板引擎的自动配置

- FreeMarker
- Groovy
- Thymeleaf
- Mustache

Thymeleaf官网：[Thymeleaf](https://www.thymeleaf.org/)

```html
<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org">
<head>
    <title>Good Thymes Virtual Grocery</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
    <link rel="stylesheet" type="text/css" media="all" th:href="@{/css/gtvg.css}" />
</head>
<body>
    <p th:text="#{home.welcome}">
        Welcome to our grocery store!
    </p>
</body>
</html>
```

### 2.5.1 Thymeleaf整合

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-thymeleaf</artifactId>
</dependency>
```

自动配置原理：

1. 开启了org.springframework.boot.autoconfigure.thymeleaf.ThymeleafAutoConfiguration 自动配置

2. 属性绑定在 ThymeleafProperties 中，对应配置文件 spring.thymeleaf 内容

3. 所有的模板页面默认在 classpath:/templates文件夹下

4. 默认效果
   1. 所有的模板页面在 classpath:/templates/下面找
   2. 找后缀名为.html的页面

------

Thymeleaf初体验 

### 2.5.2 基础语法

#### 2.5.2.1 核心用法

th:xxx：动态渲染指定的 html 标签属性值、或者th指令（遍历、判断等）

- th:text：标签体内文本值渲染

  - th:utext：不会转义，显示为html原本的样子。
- th:属性：标签指定属性渲染
- th:attr：标签任意属性渲染
- th:if th:each ...：其他th指令
- 例如：

```html
<p th:text="${content}">
    原内容
</p>
<a th:href="${url}">
    登录
</a>
<img src="../../images/gtvglogo.png"
     th:attr="src=@{/images/gtvglogo.png}, title=#{logo}, alt=#{logo}" />
```

表达式：用来动态取值

- ${}：变量取值；使用model共享给页面的值都直接用${}
- @{}：url路径；
- \#{}：国际化消息
- ~{}：片段引用
- *{}：变量选择：需要配合th:object绑定对象

系统工具&内置对象：[详细文档](https://www.thymeleaf.org/doc/tutorials/3.1/usingthymeleaf.html#appendix-a-expression-basic-objects)

- param：请求参数对象
- session：session对象
- application：application对象
- \#execInfo：模板执行信息
- \#messages：国际化消息
- \#uris：uri/url工具
- \#conversions：类型转换工具
- \#dates：日期工具，是java.util.Date对象的工具类
- \#calendars：类似#dates，只不过是java.util.Calendar对象的工具类
- \#temporals： JDK8+ java.time API 工具类
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

#### 2.5.2.2 语法示例

表达式：

- 变量取值：${...}
- url 取值：@{...}
- 国际化消息：#{...}
- 变量选择：*{...}
- 片段引用: ~{...}

常见：

- 文本： 'one text'，'another one!',...
- 数字： 0,34,3.0,12.3,...
- 布尔：true、false
- null: null
- 变量名： one,sometext,main...

文本操作：

- 拼串： +
- 文本替换：| The name is ${name} |

布尔操作：

- 二进制运算： and,or
- 取反：!,not

比较运算：

- 比较：>，<，<=，>=（gt，lt，ge,le）
- 等值运算：==,!=（eq，ne）

条件运算：

- if-then： (if)?(then)
- if-then-else: (if)?(then):(else)
- default: (value)?:(defaultValue)

特殊语法：

- 无操作：_

所有以上都可以嵌套组合

```
'User is of type ' + (${user.isAdmin()} ? 'Administrator' : (${user.type} ?:'Unknown'))
```



### 2.5.3 属性设置

1. th:href="@{/product/list}"

2. th:attr="class=${active}"

3. th:attr="src=@{/images/gtvglogo.png},title=${logo},alt=#{logo}"

4. th:checked="${user.active}"

```html
<p th:text="${content}">原内容</p>
<a th:href="${url}">登录</a>
<img src="../../images/gtvglogo.png"
th:attr="src=@{/images/gtvglogo.png},title=#{logo},alt=#{logo}" />
```

### 2.5.4 遍历

> 语法： th:each="元素名,迭代状态 : ${集合}"

```html
<tr th:each="prod : ${prods}">
    <td th:text="${prod.name}">Onions</td>
    <td th:text="${prod.price}">2.41</td>
    <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
</tr>

<tr th:each="prod, iterStat : ${prods}" th:class="${iterStat.odd}? 'odd'">
    <td th:text="${prod.name}">Onions</td>
    <td th:text="${prod.price}">2.41</td>
    <td th:text="${prod.inStock}? #{true} : #{false}">yes</td>
</tr>
```

iterStat 有以下属性：

- index：当前遍历元素的索引，从0开始
- count：当前遍历元素的索引，从1开始
- size：需要遍历元素的总数量
- current：当前正在遍历的元素对象
- even/odd：是否偶数/奇数行
- first：是否第一个元素
- last：是否最后一个元素

------

### 2.5.5 判断

#### 2.5.5.1 th:if

```html
<a href="comments.html"
   th:href="@{/product/comments(prodId=${prod.id})}"
   th:if="${not #lists.isEmpty(prod.comments)}">
    view
</a>
```

#### 2.5.5.2 th:switch

```html
<div th:switch="${user.role}">
    <p th:case="'admin'">User is an administrator</p>
    <p th:case="${roles.manager}">User is a manager</p>
    <p th:case="*">User is some other thing</p>
</div>
```

### 2.5.6 属性优先级

- 片段
- 遍历
- 判断

```html
<ul>
    <li th:each="item : ${items}" th:text="${item.description}">
        Item description here...
    </li>
</ul>
```

| Order | Feature          | Attributes                           |
| --------- | -------------------- | ---------------------------------------- |
| 1     | 片段包含         | th:insert th:replace                 |
| 2     | 遍历             | th:each                              |
| 3     | 判断             | th:if th:unless th:switch th:case    |
| 4     | 定义本地变量     | th:object th:with                    |
| 5     | 通用方式属性修改 | th:attr th:attrprepend th:attrappend |
| 6     | 指定属性修改     | th:value th:href th:src ...          |
| 7     | 文本值           | th:text th:utext                     |
| 8     | 片段指定         | th:fragment                          |
| 9     | 片段移除         | th:remove                            |

### 2.5.7 行内写法

[[...]] or [(...)]

```html
<p>Hello, [[${session.user.name}]]!</p>
```

### 2.5.8 变量选择

```html
<div th:object="${session.user}">
    <p>
        Name: <span th:text="*{firstName}">Sebastian</span>.
    </p>
    <p>
        Surname: <span th:text="*{lastName}">Pepper</span>.
    </p>
    <p>
        Nationality: <span th:text="*{nationality}">Saturn</span>.
    </p>
</div>
```

等同于 

```html
<div th:object="${session.user}">
    <p>
        Name: <span th:text="*{firstName}">Sebastian</span>.
    </p>
    <p>
        Surname: <span th:text="*{lastName}">Pepper</span>.
    </p>
    <p>
        Nationality: <span th:text="*{nationality}">Saturn</span>.
    </p>
</div>
```

### 2.5.9 模板布局

- 定义模板： th:fragment
- 引用模板：~{templatename::selector}
- 插入模板：th:insert、th:replace

```html
<footer th:fragment="copy">
    &copy; 2011 The Good Thymes Virtual Grocery
</footer>

<body>
    <div th:insert="~{footer :: copy}"></div>
    <div th:replace="~{footer :: copy}"></div>
</body>

<body>
   结果:
   <body>
    <div>
        <footer>
            &copy; 2011 The Good Thymes Virtual Grocery
        </footer>
    </div>
    <footer>
        &copy; 2011 The Good Thymes Virtual Grocery
    </footer>
   </body>
</body>
```

common.html

index.html

### 2.5.10 devtools

热启动依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-devtools</artifactId>
</dependency>
```

修改页面后；ctrl+F9刷新效果；

java代码的修改，如果devtools热启动了，可能会引起一些bug，难以排查

建议：页面修改直接ctrl+F9；java代码的修改，重启项目

------

常用配置项

## 2.6 国际化

国际化的自动配置参照MessageSourceAutoConfiguration

实现步骤：

1. Spring Boot 在类路径根下查找messages资源绑定文件。文件名为：messages.properties

2. 多语言可以定义多个消息文件，命名为messages_区域代码.properties。如：

   ​    a）messages.properties：默认

   ​    b）messages_zh_CN.properties：中文环境

   ​    c）messages_en_US.properties：英语环境

3. 在程序中可以自动注入 MessageSource组件，获取国际化的配置项值

4. 在页面中可以使用表达式 #{}获取国际化的配置项值

```java
@Autowired //国际化取消息用的组件
MessageSource messageSource;
@GetMapping("/haha")
public String haha(HttpServletRequest request){
    Locale locale = request.getLocale();
//利用代码的方式获取国际化配置文件中指定的配置项的值
    String login = messageSource.getMessage("login", null, locale);
    return login;
}
```

common.html

messages.properties

## 2.7 错误处理

### 2.7.1 默认机制

> 错误处理的自动配置都在ErrorMvcAutoConfiguration中，两大核心机制：
>
> - 1. SpringBoot 会自适应处理错误，响应页面或JSON数据
> - 2. SpringMVC的错误处理机制依然保留，MVC处理不了，才会交给boot进行处理
>

![img](../image/e1802d79b46947c695f2c27413cff8da.png)

- 发生错误以后，转发给/error路径，SpringBoot在底层写好一个 BasicErrorController的组件，专门处理这个请求

  ```java
  @RequestMapping(produces = MediaType.TEXT_HTML_VALUE)
  // 返回 HTML
  public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
      HttpStatus status = getStatus(request);
      Map<String, Object> model = Collections.unmodifiableMap(
          getErrorAttributes(request, getErrorAttributeOptions(request, MediaType.TEXT_HTML))
      );
      response.setStatus(status.value());
      ModelAndView modelAndView = resolveErrorView(request, response, status, model);
      return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
  }
  
  @ RequestMapping
  // 返回 ResponseEntity, JSON
  public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
      HttpStatus status = getStatus(request);
      if (status == HttpStatus.NO_CONTENT) {
          return new ResponseEntity<>(status);
      }
      Map<String, Object> body = getErrorAttributes(
          request,
          getErrorAttributeOptions(request, MediaType.ALL)
      );
      return new ResponseEntity<>(body, status);
  }
  ```

- 错误页面是这么解析到的

  ```java
  // 1. 解析错误的自定义视图地址
  ModelAndView modelAndView = resolveErrorView(request, response, status, model);
  
  // 2. 如果解析不到错误页面地址，默认的错误页就是 "error"
  return (modelAndView != null) ? modelAndView : new ModelAndView("error", model);
  ```

容器中专门有一个错误视图解析器 

```java
@Bean
@ConditionalOnBean(DispatcherServlet.class)
@ConditionalOnMissingBean(ErrorViewResolver.class)
public DefaultErrorViewResolver conventionErrorViewResolver() {
    return new DefaultErrorViewResolver(this.applicationContext, this.resources);
}
```

SpringBoot解析自定义错误页的默认规则

```java
@Override
public ModelAndView resolveErrorView(HttpServletRequest request, HttpStatus status, Map<String, Object> model) {
    ModelAndView modelAndView = resolve(String.valueOf(status.value()), model);
    if (modelAndView == null && SERIES_VIEWS.containsKey(status.series())) {
        modelAndView = resolve(SERIES_VIEWS.get(status.series()), model);
    }
    return modelAndView;
}

private ModelAndView resolve(String viewName, Map<String, Object> model) {
    String errorViewName = "error/" + viewName;
    TemplateAvailabilityProvider provider = this.templateAvailabilityProviders.getProvider(
        errorViewName,
        this.applicationContext
    );
    if (provider != null) {
        return new ModelAndView(errorViewName, model);
    }
    return resolveResource(errorViewName, model);
}

private ModelAndView resolveResource(String viewName, Map<String, Object> model) {
    for (String location : this.resources.getStaticLocations()) {
        try {
            Resource resource = this.applicationContext.getResource(location);
            resource = resource.createRelative(viewName + ".html");
            if (resource.exists()) {
                return new ModelAndView(new HtmlResourceView(resource), model);
            }
        } catch (Exception ex) {
            // Ignore
        }
    }
    return null;
}
```

容器中有一个默认的名为 error 的 view； 提供了默认白页功能

```java
@Bean(name = "error")
@ConditionalOnMissingBean(name = "error")
public View defaultErrorView() {
    return this.defaultErrorView;
}
```

封装了JSON格式的错误信息

```java
@Bean
@ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchSt
rategy.CURRENT)
public DefaultErrorAttributes errorAttributes() {
	return new DefaultErrorAttributes();
}
```

规则：

解析一个错误页

a）如果发生了500、404、503、403 这些错误

1. 如果有模板引擎，默认在 classpath:/templates/error/精确码.html
2. 如果没有模板引擎，在静态资源文件夹下找 精确码.html

b）如果匹配不到精确码.html这些精确的错误页，就去找5xx.html，4xx.html模糊匹配

1. 如果有模板引擎，默认在 classpath:/templates/error/5xx.html
2. 如果没有模板引擎，在静态资源文件夹下找 5xx.html

如果模板引擎路径 templates下有 error.html页面，就直接渲染

### 2.7.2 自定义错误响应

#### 2.7.2.1 自定义json响应

> 使用@ControllerAdvice + @ExceptionHandler 进行统一异常处理
>

#### 2.7.2.2 自定义页面响应

> 根据boot的错误页面规则，自定义页面模板
>

### 2.7.3 最佳实战

* 前后分离
  * 后台发生的所有错误，@ControllerAdvice + @ExceptionHandler进行统一异常处理。

* 服务端页面渲染
  * 不可预知的一些，HTTP码表示的服务器或客户端错误
    * 给classpath:/templates/error/下面，放常用精确的错误码页面。500.html，404.html
    * 给classpath:/templates/error/下面，放通用模糊匹配的错误码页面。 5xx.html，4xx.html
  * 发生业务错误
    * 核心业务，每一种错误，都应该代码控制，跳转到自己定制的错误页。
    * 通用业务，classpath:/templates/error.html页面，显示错误信息。



页面，JSON，可用的Model数据如下

![img](../image/e4ca8f75c680045628328204f66dd70e.png)

## 2.8 嵌入式容器

> Servlet容器：管理、运行Servlet组件（Servlet、Filter、Listener）的环境，一般指服务器（Tomcat）
>

### 2.8.1 自动配置原理

> - SpringBoot 默认嵌入Tomcat作为Servlet容器。
>
> - 自动配置类是 `ServletWebServerFactoryAutoConfiguration`，`EmbeddedWebServerFactoryCustomizerAutoConfiguration`
>
> - 自动配置类开始分析功能。`xxxxAutoConfiguration`
>

```java
@AutoConfiguration
@AutoConfigureOrder(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(ServletRequest.class)
@ConditionalOnWebApplication(type = Type.SERVLET)
@EnableConfigurationProperties(ServerProperties.class)
@Import({ ServletWebServerFactoryAutoConfiguration.BeanPostProcessorsRegistra
        r.class,
        ServletWebServerFactoryConfiguration.EmbeddedTomcat.class,
        ServletWebServerFactoryConfiguration.EmbeddedJetty.class,
        ServletWebServerFactoryConfiguration.EmbeddedUndertow.class })
public class ServletWebServerFactoryAutoConfiguration {
}
```

1. ServletWebServerFactoryAutoConfiguration 自动配置了嵌入式容器场景

2.  绑定了ServerProperties配置类，所有和服务器有关的配置 server

3. ServletWebServerFactoryAutoConfiguration 导入了 嵌入式的三大服务器 Tomcat、Jetty、Undertow

   ​	a）导入 Tomcat、Jetty、Undertow 都有条件注解。系统中有这个类才行（也就是导了包）

   ​	 b）默认 Tomcat配置生效。给容器中放 TomcatServletWebServerFactory

   ​	c）都给容器中 放了一个ServletWebServerFactory（接口） web服务器工厂（造web服务器的）——xxxServletWebServerFactory（具体实现类）

   ​	d）web服务器工厂 都有一个功能，getWebServer获取web服务器

   ​	e）TomcatServletWebServerFactory 创建了 tomcat。

4. ServletWebServerFactory 什么时候会创建 webServer出来。

5. ServletWebServerApplicationContext ioc容器，启动的时候会调用创建web服务器

6. Spring容器刷新（启动）的时候，会预留一个时机，刷新子容器。onRefresh()

7. refresh()容器刷新 十二大步的刷新子容器会调用 onRefresh()；

```java
@Override
protected void onRefresh() {
    super.onRefresh();
    try {
    	createWebServer();
    }catch (Throwable ex) {
    	throw new ApplicationContextException("Unable to start web server",ex);
    }
}
```



> Web场景的Spring容器启动，在onRefresh的时候，会调用创建web服务器的方法。
>
> Web服务器的创建是通过WebServerFactory搞定的。容器中又会根据导了什么包条件注解，启动相关的 服务器配置，默认`EmbeddedTomcat`会给容器中放一个 `TomcatServletWebServerFactory`，导致项目启动，自动创建出Tomcat。

### 2.8.2 自定义



![img](../image/530761049dbb78beff1970c49edffdfa.png)

> 切换服务器；

```xml
<properties>
    <servlet-api.version>3.1.0</servlet-api.version>
</properties>

<dependencies>
    <!-- Spring Boot Web Starter (excluding Tomcat) -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-tomcat</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <!-- Use Jetty as the embedded server -->
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jetty</artifactId>
    </dependency>
</dependencies>
```

### 2.8.3 最佳实战

用法：

- 修改server下的相关配置就可以修改服务器参数
- 通过给容器中放一个ServletWebServerFactory（接口，放入的是xxxServletWebServerFactory实现类），来禁用掉SpringBoot默认放的服务器工厂，实现自定义嵌入任意服务器。

![img](../image/f4dd032dfb5343208f98486e85674897.png)

以Tomcat服务器为例，当容器中没有ServletWebServerFactory时，系统创建new TomcatServletWebServerFactory()；所以当我们自己配置了ServletWebServerFactory放入容器，则系统就不创建服务器工厂了

## 2.9 全面接管SpringMVC

> - SpringBoot 默认配置好了 SpringMVC 的所有常用特性。
> - 如果我们需要全面接管SpringMVC的所有配置并禁用默认配置，仅需要编写一个WebMvcConfigurer配置类，并标注 @EnableWebMvc 即可
> - 全手动模式
>   - @EnableWebMvc : 禁用默认配置
>   - WebMvcConfigurer组件：定义MVC的底层行为
>

### 2.9.1 WebMvcAutoConfiguration 到底自动配置了哪些规则

> SpringMVC自动配置场景给我们配置了如下所有默认行为

1. WebMvcAutoConfigurationweb场景的自动配置类

   ​    1.1 支持RESTful的filter：HiddenHttpMethodFilter

   ​    1.2 支持非POST请求，请求体携带数据：FormContentFilter

   ​    1.3 导入EnableWebMvcConfiguration：

   ​        1.3.1 RequestMappingHandlerAdapter

   ​        1.3.2 WelcomePageHandlerMapping： 欢迎页功能支持（模板引擎目录、静态资源目录放index.html），项目访问/ 就默认展示这个页面.

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

### 2.9.2 @EnableWebMvc 禁用默认行为

1. @EnableWebMvc给容器中导入 DelegatingWebMvcConfiguration组件，他是 WebMvcConfigurationSupport
2. WebMvcAutoConfiguration有一个核心的条件注解, @ConditionalOnMissingBean(WebMvcConfigurationSupport.class)，容器中没有WebMvcConfigurationSupport，WebMvcAutoConfiguration才生效.
3. @EnableWebMvc 导入 WebMvcConfigurationSupport 导致 WebMvcAutoConfiguration 失效。导致禁用了默认行为

> - @EnableWebMVC 禁用了 Mvc的自动配置
> - WebMvcConfigurer 定义SpringMVC底层组件的功能类

### 2.9.3 WebMvcConfigurer 功能

> 定义扩展SpringMVC底层功能

![img](../image/07d52c8e1b514f8c8971f99ccbb99bcd.png)

## 2.10 最佳实践

SpringBoot 已经默认配置好了Web开发场景常用功能。我们直接使用即可。

### 2.10.1 三种方式

| 方式         | 用法                                                     | 效果                               |                                                              |
| ---------------- | ------------------------------------------------------------ | -------------------------------------- | ------------------------------------------------------------ |
| 全自动 | 直接编写控制器逻辑                                       |                                        | 全部使用自动配置默认效果                           |
| 手自一体     | @Configuration + 配置WebMvcConfigurer + 配置 WebMvcRegistrations | 不要标注 @EnableWebMvc | 保留自动配置效果 手动设置部分功能 定义MVC底层组件 |
| 全手动 | @Configuration + 配置WebMvcConfigurer | 标注 @EnableWebMvc     | 禁用自动配置效果 全手动设置                  |

总结：

给容器中写一个配置类 加上注解@Configuration， 实现 WebMvcConfigurer， 但是不要标注 @EnableWebMvc注解，实现手自一体的效果。

### 2.10.2 两种模式

1、前后分离模式： @RestController 响应JSON数据

2、前后不分离模式：@Controller + Thymeleaf模板引擎

## 2.11 Web新特性

### 2.11.1 Problemdetails

> RFC 7807: https://www.rfc-editor.org/rfc/rfc7807
>
> 错误信息返回新格式

原理 

```java
@Configuration(proxyBeanMethods = false)
//配置过一个属性 spring.mvc.problemdetails.enabled=true
@ConditionalOnProperty(prefix = "spring.mvc.problemdetails", name = "enabled", havingValue = "true")
static class ProblemDetailsErrorHandlingConfiguration {
    @Bean
    @ConditionalOnMissingBean(ResponseEntityExceptionHandler.class)
    ProblemDetailsExceptionHandler problemDetailsExceptionHandler() {
        return new ProblemDetailsExceptionHandler();
    }
}
```

1. ProblemDetailsExceptionHandler 是一个 @ControllerAdvice集中处理系统异常

2. 处理以下异常。如果系统出现以下异常，会被SpringBoot支持以 `RFC 7807`规范方式返回错误数据

```java
@ExceptionHandler({
    HttpRequestMethodNotSupportedException.class, //请求方式不支持
    HttpMediaTypeNotSupportedException.class,
    HttpMediaTypeNotAcceptableException.class,
    MissingPathVariableException.class,
    MissingServletRequestParameterException.class,
    MissingServletRequestPartException.class,
    ServletRequestBindingException.class,
    MethodArgumentNotValidException.class,
    NoHandlerFoundException.class,
    AsyncRequestTimeoutException.class,
    ErrorResponseException.class,
    ConversionNotSupportedException.class,
    TypeMismatchException.class,
    HttpMessageNotReadableException.class,
    HttpMessageNotWritableException.class,
    BindException.class
})
```

>  效果：

默认响应错误的json。状态码 405 

```json
{
    "timestamp": "2023-04-18T11:13:05.515+00:00",
    "status": 405,
    "error": "Method Not Allowed",
    "trace": "org.springframework.web.HttpRequestMethodNotSupportedException: Request method 'POST' is not supported\r\n\tat org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.handleNoMatch(RequestMappingInfoHandlerMapping.java:265)\r\n\tat org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.lookupHandlerMethod(AbstractHandlerMethodMapping.java:441)\r\n\tat org.springframework.web.servlet.handler.AbstractHandlerMethodMapping.getHandlerInternal(AbstractHandlerMethodMapping.java:382)\r\n\tat org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.getHandlerInternal(RequestMappingInfoHandlerMapping.java:126)\r\n\tat org.springframework.web.servlet.mvc.method.RequestMappingInfoHandlerMapping.getHandlerInternal(RequestMappingInfoHandlerMapping.java:68)\r\n\tat org.springframework.web.servlet.handler.AbstractHandlerMapping.getHandler(AbstractHandlerMapping.java:505)\r\n\tat org.springframework.web.servlet.DispatcherServlet.getHandler(DispatcherServlet.java:1275)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doDispatch(DispatcherServlet.java:1057)\r\n\tat org.springframework.web.servlet.DispatcherServlet.doService(DispatcherServlet.java:974)\r\n\tat org.springframework.web.servlet.FrameworkServlet.processRequest(FrameworkServlet.java:1011)\r\n\tat org.springframework.web.servlet.FrameworkServlet.doPost(FrameworkServlet.java:914)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:563)\r\n\tat org.springframework.web.servlet.FrameworkServlet.service(FrameworkServlet.java:885)\r\n\tat jakarta.servlet.http.HttpServlet.service(HttpServlet.java:631)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:205)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.apache.tomcat.websocket.server.WsFilter.doFilter(WsFilter.java:53)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.springframework.web.filter.RequestContextFilter.doFilterInternal(RequestContextFilter.java:100)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.springframework.web.filter.FormContentFilter.doFilterInternal(FormContentFilter.java:93)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.springframework.web.filter.CharacterEncodingFilter.doFilterInternal(CharacterEncodingFilter.java:201)\r\n\tat org.springframework.web.filter.OncePerRequestFilter.doFilter(OncePerRequestFilter.java:116)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.internalDoFilter(ApplicationFilterChain.java:174)\r\n\tat org.apache.catalina.core.ApplicationFilterChain.doFilter(ApplicationFilterChain.java:149)\r\n\tat org.apache.catalina.core.StandardWrapperValve.invoke(StandardWrapperValve.java:166)\r\n\tat org.apache.catalina.core.StandardContextValve.invoke(StandardContextValve.java:90)\r\n\tat org.apache.catalina.authenticator.AuthenticatorBase.invoke(AuthenticatorBase.java:493)\r\n\tat org.apache.catalina.core.StandardHostValve.invoke(StandardHostValve.java:115)\r\n\tat org.apache.catalina.valves.ErrorReportValve.invoke(ErrorReportValve.java:93)\r\n\tat org.apache.catalina.core.StandardEngineValve.invoke(StandardEngineValve.java:74)\r\n\tat org.apache.catalina.connector.CoyoteAdapter.service(CoyoteAdapter.java:341)\r\n\tat org.apache.coyote.http11.Http11Processor.service(Http11Processor.java:390)\r\n\tat org.apache.coyote.AbstractProcessorLight.process(AbstractProcessorLight.java:63)\r\n\tat org.apache.coyote.AbstractProtocol$ConnectionHandler.process(AbstractProtocol.java:894)\r\n\tat org.apache.tomcat.util.net.NioEndpoint$SocketProcessor.doRun(NioEndpoint.java:1741)\r\n\tat org.apache.tomcat.util.net.SocketProcessorBase.run(SocketProcessorBase.java:52)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1191)\r\n\tat org.apache.tomcat.util.threads.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:659)\r\n\tat org.apache.tomcat.util.threads.TaskThread$WrappingRunnable.run(TaskThread.java:61)\r\n\tat java.base/java.lang.Thread.run(Thread.java:833)",
    "message": "Method 'POST' is not supported.",
    "path": "/list"
}
```

开启ProblemDetails返回, 使用新的MediaType

```
{
    "type": "about:blank",
    "title": "Method Not Allowed",
    "status": 405,
    "detail": "Method 'POST' is not supported.",
    "instance": "/list"
}
```

Content-Type: application/problem+json+ 额外扩展返回

![img](../image/750ca69ecbaa9c1c9c938216ae8cacfb.png)

### 2.11.2 函数式Web

> SpringMVC 5.2 以后 允许我们使用函数式的方式，定义Web的请求处理流程。
>
> 函数式接口
>
> Web请求处理的方式：
>
> 1. @Controller + @RequestMapping：耦合式 （路由、业务耦合）
> 2. 函数式Web：分离式（路由、业务分离）

#### 2.11.2.1 场景

场景：User RESTful - CRUD

- GET /user/1 获取1号用户
- GET /users 获取所有用户
- POST /user 请求体携带JSON，新增一个用户
- PUT /user/1 请求体携带JSON，修改1号用户
- DELETE /user/1 删除1号用户

#### 2.11.2.2 核心类

- RouterFunction
- RequestPredicate
- ServerRequest
- ServerResponse

#### 2.11.2.3 示例

```java
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.function.RequestPredicate;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;
import static org.springframework.web.servlet.function.RequestPredicates.accept;
import static org.springframework.web.servlet.function.RouterFunctions.route;

@Configuration(proxyBeanMethods = false)
public class MyRoutingConfiguration {

    private static final RequestPredicate ACCEPT_JSON = accept(MediaType.APPLICATION_JSON);

    @Bean
    public RouterFunction<ServerResponse> routerFunction(MyUserHandler userHandler) {
        return route()
                .GET("/{user}", ACCEPT_JSON, userHandler::getUser)
                .GET("/{user}/customers", ACCEPT_JSON, userHandler::getUserCustomers)
                .DELETE("/{user}", ACCEPT_JSON, userHandler::deleteUser)
                .build();
    }
}
```

```java
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.function.ServerRequest;
import org.springframework.web.servlet.function.ServerResponse;

@Component
public class MyUserHandler {

    public ServerResponse getUser(ServerRequest request) {
        // TODO: 实现获取用户逻辑
        return ServerResponse.ok().build();
    }

    public ServerResponse getUserCustomers(ServerRequest request) {
        // TODO: 实现获取用户客户列表逻辑
        return ServerResponse.ok().build();
    }

    public ServerResponse deleteUser(ServerRequest request) {
        // TODO: 实现删除用户逻辑
        return ServerResponse.ok().build();
    }
}
```

