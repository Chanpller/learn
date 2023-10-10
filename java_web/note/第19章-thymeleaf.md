# 第19章-thymeleaf

## 19.1 什么是thymeleaf

- JSP、Freemarker、Velocity等等，它们有一个共同的名字：**服务器端模板技术**。
- thymeleaf 是一个跟 Velocity、FreeMarker 类似的模板引擎，它可以完全替代 JSP 。相较与其他的模板引擎，它有如下三个极吸引人的特点：
  - Thymeleaf 在有网络和无网络的环境下皆可运行，即它可以让美工在浏览器查看页面的静态效果，也可以让程序员在服务器查看带数据的动态页面效果。这是由于它支持 html 原型，然后在 html 标签里增加额外的属性来达到模板+数据的展示方式。浏览器解释 html 时会忽略未定义的标签属性，所以 thymeleaf 的模板可以静态地运行；当有数据返回到页面时，Thymeleaf 标签会动态地替换掉静态内容，使页面动态显示。
  - Thymeleaf 开箱即用的特性。它提供标准和spring标准两种方言，可以直接套用模板实现JSTL、 OGNL表达式效果，避免每天套模板、该jstl、改标签的困扰。同时开发人员也可以扩展和创建自定义的方言。
  - Thymeleaf 提供spring标准方言和一个与 SpringMVC 完美集成的可选模块，可以快速的实现表单绑定、属性编辑器、国际化等功能。

* 官网：http://www.thymeleaf.org/

## 19.2 Thymeleaf优势

- SpringBoot官方推荐使用的视图模板技术，和SpringBoot完美整合。
- 不经过服务器运算仍然可以直接查看原始值，对前端工程师更友好。

```html
<!DOCTYPE html>
<html lang="en" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
</head>
<body>
    <p th:text="${hello}">Original Value</p>
</body>
</html>
```

## 19.3物理视图和逻辑视图

### 物理视图

* 在Servlet中，将请求转发到一个HTML页面文件时，使用的完整的转发路径就是**物理视图**。

### 逻辑视图

* 物理视图=视图前缀+逻辑视图+视图后缀

## 19.4 在服务器端引入Thymeleaf环境

pom.xml

```xml
 <!--thymeleaf start-->
        <dependency>
            <groupId>org.attoparser</groupId>
            <artifactId>attoparser</artifactId>
            <version>2.0.5.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.javassist</groupId>
            <artifactId>javassist</artifactId>
            <version>3.20.0-GA</version>
        </dependency>
        <dependency>
            <groupId>apache-log4j</groupId>
            <artifactId>log4j</artifactId>
            <version>1.2.15</version>
        </dependency>
        <dependency>
            <groupId>ognl</groupId>
            <artifactId>ognl</artifactId>
            <version>3.1.26</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-api</artifactId>
            <version>1.7.25</version>
        </dependency>
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>slf4j-log4j12</artifactId>
            <version>1.7.25</version>
        </dependency>
        <dependency>
            <groupId>org.thymeleaf</groupId>
            <artifactId>thymeleaf</artifactId>
            <version>3.0.12.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>org.unbescape</groupId>
            <artifactId>unbescape</artifactId>
            <version>1.1.6.RELEASE</version>
        </dependency>
        <!--thymeleaf end-->
```

创建ViewBaseServlet

```java
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.WebContext;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ViewBaseServlet extends HttpServlet {

    private TemplateEngine templateEngine;

    @Override
    public void init() throws ServletException {

        // 1.获取ServletContext对象
        ServletContext servletContext = this.getServletContext();

        // 2.创建Thymeleaf解析器对象
        ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(servletContext);

        // 3.给解析器对象设置参数
        // ①HTML是默认模式，明确设置是为了代码更容易理解
        templateResolver.setTemplateMode(TemplateMode.HTML);

        // ②设置前缀
        String viewPrefix = servletContext.getInitParameter("view-prefix");

        templateResolver.setPrefix(viewPrefix);

        // ③设置后缀
        String viewSuffix = servletContext.getInitParameter("view-suffix");

        templateResolver.setSuffix(viewSuffix);

        // ④设置缓存过期时间（毫秒）
        templateResolver.setCacheTTLMs(60000L);

        // ⑤设置是否缓存
        templateResolver.setCacheable(true);

        // ⑥设置服务器端编码方式
        templateResolver.setCharacterEncoding("utf-8");

        // 4.创建模板引擎对象
        templateEngine = new TemplateEngine();

        // 5.给模板引擎对象设置模板解析器
        templateEngine.setTemplateResolver(templateResolver);

    }

    protected void processTemplate(String templateName, HttpServletRequest req, HttpServletResponse resp) throws IOException {
        // 1.设置响应体内容类型和字符集
        resp.setContentType("text/html;charset=UTF-8");

        // 2.创建WebContext对象
        WebContext webContext = new WebContext(req, resp, getServletContext());

        // 3.处理模板数据
        templateEngine.process(templateName, webContext, resp.getWriter());
    }
}
```

创建QueryFruitServlet

```java
public class QueryFruitServlet extends ViewBaseServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        FruitDAO fruitDAO = new FruitDAOImpl();
        List<Fruit> fruitList = fruitDAO.getFruitList();
        HttpSession session = req.getSession();
        session.setAttribute("fruitList",fruitList);
        //逻辑视图index，实际访问的地址为=视图前缀+逻辑视图+视图后缀，/java_web_2022/thymeleaf/index.html
        processTemplate("index",req,resp);
    }
}
```

web.xml加入配置

```xml
   <!-- thymeleaf start-->
    <context-param>
        <param-name>view-prefix</param-name>
        <!--视图前缀-->
        <param-value>/java_web_2022/thymeleaf/</param-value>
    </context-param>
    <context-param>
        <param-name>view-suffix</param-name>
        <!--视图后缀-->
        <param-value>.html</param-value>
    </context-param>

    <servlet>
        <servlet-name>QueryFruitServlet</servlet-name>
        <servlet-class>chapter19.thymeleaf.fruit.servlets.QueryFruitServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>QueryFruitServlet</servlet-name>
        <url-pattern>/queryFruitServlet</url-pattern>
    </servlet-mapping>
    <!-- thymeleaf end-->
```

index.html页面

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org" >
<head>
    <base href="http://localhost:8080/java_web/java_web_2022/thymeleaf/">
    <meta charset="utf-8">
    <link rel="stylesheet" href="testCss.css">
    <script type="text/javascript" src="testjs.js"></script>
</head>
<body>
<div id="div_container">
    <div id="div_fruit_list">
        <table id="tbl_fruit">
            <tr>
                <th class="w20">名称</th>
                <th class="w20">单价</th>
                <th class="w20">数量</th>
                <th class="w20">小计</th>
                <th>操作</th>
            </tr>
            <tr th:if="${#lists.isEmpty(session.fruitList)}">
                <td colspan="5">对不起，库存为空！</td>
            </tr>
            <tr th:unless="${#lists.isEmpty(session.fruitList)}" th:each="fruit : ${session.fruitList}">
                <td th:text="${fruit.fname}"></td>
                <td th:text="${fruit.price}"></td>
                <td th:text="${fruit.fcount}"></td>
                <td th:text="${fruit.fcount*fruit.price}"></td>
                <td><img src="del.jpg" class="delImg"/></td>
            </tr>
            <tr>
                <td>总计</td>
                <td colspan="4">999</td>
            </tr>
        </table>
        <hr/>
    </div>
</div>
</body>
</html>
```

直接访问queryFruitServlet会跳转到index页面，从而解析。

## 19.5 Thymeleaf页面th名称空间

* 在html标签上添加xmlns:th，表示th命名空间，后面使用的th标签就可以解析了。

```html
<html lang="en" xmlns:th="http://www.thymeleaf.org" >
```

## 19.6 Thymeleaf语法

* th:xxx

| 属性名          | 作用                               |
| --------------- | ---------------------------------- |
| th:text         | 使用表达式设置文本标签体的数据     |
| th:属性名       | 使用表达式运算得到的值设置HTML属性 |
| th:if/th:unless | 分支判断                           |
| th:each         | 迭代                               |

```html
 			<tr th:if="${#lists.isEmpty(session.fruitList)}">
                <td colspan="5">对不起，库存为空！</td>
            </tr>
            <tr th:unless="${#lists.isEmpty(session.fruitList)}" th:each="fruit : ${session.fruitList}">
                <td th:text="${fruit.fname}"></td>
                <td th:text="${fruit.price}"></td>
                <td th:text="${fruit.fcount}"></td>
                <td th:text="${fruit.fcount*fruit.price}"></td>
                <td><img src="del.jpg" class="delImg"/></td>
            </tr>
```

* 直接写表达式
  * 有转义效果：[[${表达式}]]
  * 无转义效果：[(${表达式})]

* 表达式的类型
  - @{}：给传入的字符串前面附加**『上下文路径』**
  - ${}：解析OGNL表达式

* OGNL表达式

OGNL：Objects-Graph Navigation Language对象图导航语言

**[1]起点**

| 表达式起点描述  | 作用                       | 参考                             |
| --------------- | -------------------------- | -------------------------------- |
| 请求域属性名    | 根据属性名从请求域取出数据 |                                  |
| session         | 访问session域              |                                  |
| application     | 访问application域          |                                  |
| param           | 读取请求参数               |                                  |
| #strings        | 执行字符串的相关处理       | org.thymeleaf.expression.Strings |
| #lists          | 执行集合相关的处理         | org.thymeleaf.expression.Lists   |
| #request        | 原生的request对象          |                                  |
| #response       | 原生的response对象         |                                  |
| #session        | 原生的session对象          |                                  |
| #servletContext | 原生的ServletContext对象   |                                  |

**[2]访问具体属性语法**

| 对象类型                                     | 访问方式    |
| -------------------------------------------- | ----------- |
| 普通对象包含使用getXxx()、setXxx()定义的属性 | 对象.属性名 |
| List集合                                     | [index]     |
| 数组                                         | [下标]      |
| Map集合                                      | map.key     |
| 有方法的对象                                 | 对象.方法() |