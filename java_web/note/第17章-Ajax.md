# 第17章-Ajax

## 17.1 什么是 AJAX 请

- AJAX 即“Asynchronous Javascript And XML”（异步 JavaScript 和 XML），是指一种创建交互式网页应用的网页开发 技术。 
- ajax 是一种浏览器通过 js 异步发起请求，局部更新页面的技术。 
- Ajax 请求的局部更新，浏览器地址栏不会发生变化 局部更新不会舍弃原来页面的内容

## 17.2 原生 AJAX 请求

* 通过var xmlhttprequest = new XMLHttpRequest();创建一个XMLHttpRequest对象

```javascript
function createXMLHttpRequest(){
    if(window.XMLHttpRequest){
        //符合DOM2标准的浏览器 ，xmlHttpRequest的创建方式
        xmlHttpRequest = new XMLHttpRequest() ;
    }else if(window.ActiveXObject){//IE浏览器
        try{
            xmlHttpRequest = new ActiveXObject("Microsoft.XMLHTTP");
        }catch (e) {
            xmlHttpRequest = new ActiveXObject("Msxml2.XMLHTTP")
        }
    }
}
```



* xmlhttprequest.open("GET","http://localhost:8080/java_web/ajaxServlet",true);调用 open 方法设置请求参数。

  * *async*：true（异步）或 false（同步） 同步表示请求需要一个一个排队发送，页面也不会发生跳转。

* 在 send 方法前绑定 onreadystatechange 状态改变事件，处理请求完成后的操作。

  | 属性               | 描述                                                         |
  | ------------------ | ------------------------------------------------------------ |
  | onreadystatechange | 存储函数（或函数名），每当 readyState 属性改变时，就会调用该函数。 |
  | readyState         | 存有 XMLHttpRequest 的状态。从 0 到 4 发生变化。 0: 请求未初始化  1: 服务器连接已建立  2: 请求已接收  3: 请求处理中  4: 请求已完成，且响应已就绪 |
  | status             | 200: "OK" 404: 未找到页面                                    |

```
 xmlhttprequest.onreadystatechange = function(){
                if (xmlhttprequest.readyState == 4 && xmlhttprequest.status == 200) {
// 把响应的数据显示在页面上
                    document.getElementById("div01").innerHTML = xmlhttprequest.responseText;
                }
```

* xmlhttprequest.send(); 调用 send 方法发送请求

servlet

```java
package com.example.java_web.ajax;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public class AjaxServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("请求了ajax");
        response.setContentType("txt/html;charset=UTF-8");
        response.getWriter().println("请求了ajax");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }
}

```

web.xml

```xml
    <servlet>
        <servlet-name>AjaxServlet</servlet-name>
        <servlet-class>com.example.java_web.ajax.AjaxServlet</servlet-class>
    </servlet>
    <servlet-mapping>
        <servlet-name>AjaxServlet</servlet-name>
        <url-pattern>/ajaxServlet</url-pattern>
    </servlet-mapping>
```

html

```html
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="pragma" content="no-cache" />
    <meta http-equiv="cache-control" content="no-cache" />
    <meta http-equiv="Expires" content="0" />
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Insert title here</title>
    <script type="text/javascript">
        // 在这里使用 javaScript 语言发起 Ajax 请求，访问服务器 AjaxServlet 中 javaScriptAjax
        function ajaxRequest() {
// 1、我们首先要创建 XMLHttpRequest
            var xmlhttprequest = new XMLHttpRequest();
// 2、调用 open 方法设置请求参数
            xmlhttprequest.open("GET","http://localhost:8080/java_web/ajaxServlet",true);
// 4、在 send 方法前绑定 onreadystatechange 事件，处理请求完成后的操作。
            xmlhttprequest.onreadystatechange = function(){
                if (xmlhttprequest.readyState == 4 && xmlhttprequest.status == 200) {
// 把响应的数据显示在页面上
                    document.getElementById("div01").innerHTML = xmlhttprequest.responseText;
                }
            }
// 3、调用 send 方法发送请求
            xmlhttprequest.send();
        }
    </script>
</head>
<body>
<button onclick="ajaxRequest()">ajax request</button>
<div id="div01">
</div>
</body>
</html>
```

## 17.3 jQuery 中的 AJAX

### 17.3.1 $.ajax方法

```
$.ajax 方法
    url 表示请求的地址
    type 表示请求的类型 GET 或 POST 请求
    data 表示发送给服务器的数据
        格式有两种：
        一：name=value&name=value
        二：{key:value}
    success 请求成功，响应的回调函数
    dataType 响应的数据类型
            常用的数据类型有：
            text 表示纯文本
            xml 表示 xml 数据
            json 表示 json 对
```

示例：

```javascript
$("#ajaxBtn").click(function(){
    $.ajax({
        url:"http://localhost:8080/16_json_ajax_i18n/ajaxServlet",
        // data:"action=jQueryAjax",
        data:{action:"jQueryAjax"},
        type:"GET",
        success:function (data) {
            // alert("服务器返回的数据是：" + data);
            // var jsonObj = JSON.parse(data);
            $("#msg").html("编号：" + data.id + " , 姓名：" + data.name);
        },
        dataType : "json"
    });
});
```

### 17.3.2 $.get 方法和$.post 方法

格式：

```
url 请求的 url 地址
data 发送的数据
callback 成功的回调函数
type 返回的数据类型
```

示例：

```javascript
// ajax--get 请求
$("#getBtn").click(function(){
    $.get(
        "http://localhost:8080/16_json_ajax_i18n/ajaxServlet",
        "action=jQueryGet",
        function (data) {
            $("#msg").html(" get 编号：" + data.id + " , 姓名：" + data.name);
        },
        "json"
    );
});
// ajax--post 请求
$("#postBtn").click(function(){
    $.post(
        "http://localhost:8080/16_json_ajax_i18n/ajaxServlet",
        "action=jQueryPost",
        function (data){
    		$("#msg").html(" post 编号：" + data.id + " , 姓名：" + data.name);
    	},
        "json"
    );
})
```

### 17.3.3 $.getJSON 方法

格式：

```
url 请求的 url 地址
data 发送给服务器的数据
callback 成功的回调函数
```

```javascript
// ajax--getJson 请求
$("#getJSONBtn").click(function(){
    $.getJSON(
        "http://localhost:8080/16_json_ajax_i18n/ajaxServlet",
        "action=jQueryGetJSON",
        function(data) {
    		$("#msg").html(" getJSON 编号：" + data.id + " , 姓名：" + data.name);
        }
    );
});
```

## 17.4 表单序列化 serialize()

* serialize()可以把表单中所有表单项的内容都获取到，并以name=value&name=value 的形式进行拼接

示例:

```javascript
// ajax 请求
$("#submit").click(function(){
    // 把参数序列化
    $.getJSON(
        "http://localhost:8080/16_json_ajax_i18n/ajaxServlet",
        "action=jQuerySerialize&" +$("#form01").serialize(),
        function (data) {
    		$("#msg").html(" Serialize 编号：" + data.id + " , 姓名：" + data.name);
        }
    );
});
```

