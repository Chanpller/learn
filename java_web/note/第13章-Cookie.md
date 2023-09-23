# 第13章-Cookie

## 13.1 什么是Cookie

1、Cookie 翻译过来是饼干的意思。

2、Cookie 是服务器通知客户端保存键值对的一种技术。

3、客户端有了Cookie 后，每次请求都发送给服务器。

4、每个Cookie 的大小不能超过4kb

## 13.2 如何创建Cookie

* 通过new Cookie创建一个Cookie
* 再通过response.addCookie(cookie)设置回客户端

```java
package com.example.java_web;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "CookieServlet", value = "/CookieServlet")
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //创建cookie
        Cookie cookie = new Cookie("key1","value1");
        //设置cookie
        response.addCookie(cookie);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

```

## 13.3 服务器获取Cookie

* 通过request.getCookies()获取全部的cookie，没有方法根据name过去cookie

```java
@WebServlet(name = "CookieServlet", value = "/CookieServlet")
public class CookieServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cookie[] cookies = request.getCookies();
        if (cookies !=null){
            for (Cookie cookie: cookies) {
                System.out.println("客户端cookie,key="+cookie.getName()+",value="+cookie.getValue());
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}

```

## 13.4 Cookie 值的修改

* 方案一：

  1、先创建一个要修改的同名（指的就是key）的Cookie 对象

  2、在构造器，同时赋于新的Cookie 值。

  3、调用response.addCookie( Cookie );

* 方案二：

  1、通过Request.getCookies先查找到需要修改的Cookie 对象

  2、调用Cookie.setValue()方法赋于新的Cookie 值。

  3、调用response.addCookie()通知客户端保存修改

## 13.5 Cookie 生命控制

* Cookie 的生命控制指的是如何管理Cookie 什么时候被销毁（删除）
* Cookie .setMaxAge()
  * 正数，表示在指定的秒数后过期
  * 负数，表示浏览器一关，Cookie 就会被删除（默认值是-1）
  * 零，表示马上删除Cookie

## 13.6 Cookie 有效路径Path 的设置

* Cookie 的path 属性可以有效的过滤哪些Cookie 可以发送给服务器。哪些不发。path 属性是通过请求的地址来进行有效的过滤。

```
CookieA path=/工程路径
CookieB path=/工程路径/abc
请求地址如下：
http://ip:port/工程路径/a.html
CookieA 发送
CookieB 不发送

http://ip:port/工程路径/abc/a.html
CookieA 发送
CookieB 发送
```

## 13.7 Cookie应用场景

* 免输入用户名登录：用户登录一次后，将用户名保持在cookie中，设置超时时间，跳转到登陆框时，先去找cookie中是否有用户，有就将值设置进去。JSP可以直接通过EL表达式的内置对象获取到Cookie是否带有用户名。