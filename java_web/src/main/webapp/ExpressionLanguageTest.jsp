<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="com.example.java_web.model.Person" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
======================================el表达式格式=========================<br/>
<%
    request.setAttribute("key","值");
%>
<%--el表达式格式--%>
表达式脚本输出key 的值是：
<%=request.getAttribute("key")==null?"":request.getAttribute("key")%><br/>
EL 表达式输出key 的值是：${key}
<br/>
表达式脚本输出key1 的值是：
<%=request.getAttribute("key1") %><br/>
EL 表达式输出key1 的值是：${key1}<br/>

======================================EL表达式搜索域数据的顺序=========================<br/>
<%--EL表达式搜索域数据的顺序--%>
<%
    //往四个域中都保存了相同的key 的数据。
    request.setAttribute("key", "request");
    session.setAttribute("key", "session");
    application.setAttribute("key", "application");
    pageContext.setAttribute("key", "pageContext");
%>
${ key }<br/>


======================================EL表达式输出属性=========================<br/>
<%--EL表达式输出属性--%>
<%
    Person person = new Person();
    person.setName("国哥好帅！");
    person.setPhones(new String[]{"18610541354","18688886666","18699998888"});
    List<String> cities = new ArrayList<String>();
    cities.add("北京");
    cities.add("上海");
    cities.add("深圳");
    person.setCities(cities);
    Map<String,Object> map = new HashMap<>();
    map.put("key1","value1");
    map.put("key2","value2");
    map.put("key3","value3");
    person.setMap(map);
    pageContext.setAttribute("p", person);
%>
输出Person：${ p }<br/>
输出Person 的name 属性：${p.name} <br>
输出Person 的pnones 数组属性值：${p.phones[2]} <br>
输出Person 的cities 集合中的元素值：${p.cities} <br>
输出Person 的List 集合中个别元素值：${p.cities[2]} <br>
输出Person 的Map 集合: ${p.map} <br>
输出Person 的Map 集合中某个key 的值: ${p.map.key3} <br>
输出Person 的Map 集合中某个key 的值: ${p.map['key3']} <br>
输出Person 的age 属性：${p.age} <br>

======================================EL运算符=========================<br/>
${ 5 == 5 }<br/>
${  12 == 12 && 12 < 11 }<br/>
${ 12 + 18 }<br/>
${ empty null }<br/>

======================================EL11个隐含对象=========================<br/>
<body>
<%
    pageContext.setAttribute("key1", "pageContext1");
    pageContext.setAttribute("key2", "pageContext2");
    request.setAttribute("key2", "request");
    session.setAttribute("key2", "session");
    application.setAttribute("key2", "application");
%>
${ pageScope.key2 }<br/>
${ requestScope.key2 }<br/>
${ sessionScope.key2 }<br/>
${ applicationScope.key2 }<br/>
</body>

<body>
<%--
request.getScheme() 它可以获取请求的协议
request.getServerName() 获取请求的服务器ip 或域名
request.getServerPort() 获取请求的服务器端口号
getContextPath() 获取当前工程路径
request.getMethod() 获取请求的方式（GET 或POST）
request.getRemoteHost() 获取客户端的ip 地址
session.getId() 获取会话的唯一标识
--%>
<%
    pageContext.setAttribute("req", request);
%>
<%=request.getScheme() %> <br>
1.协议： ${ req.scheme }<br>
2.服务器ip：${ pageContext.request.serverName }<br>
3.服务器端口：${ pageContext.request.serverPort }<br>
4.获取工程路径：${ pageContext.request.contextPath }<br>
5.获取请求方法：${ pageContext.request.method }<br>
6.获取客户端ip 地址：${ pageContext.request.remoteHost }<br>
7.获取会话的id 编号：${ pageContext.session.id }<br>
</body>


输出请求参数username 的值：${ param.username } <br>
输出请求参数password 的值：${ param.password } <br>
输出请求参数username 的值：${ paramValues.username[0] } <br>
输出请求参数hobby 的值：${ paramValues.hobby[0] } <br>
输出请求参数hobby 的值：${ paramValues.hobby[1] } <br>

输出请求头【User-Agent】的值：${ header['User-Agent'] } <br>
输出请求头【Connection】的值：${ header.Connection } <br>
输出请求头【User-Agent】的值：${ headerValues['User-Agent'][0] } <br>

获取Cookie 的名称：${ cookie.JSESSIONID.name } <br>
获取Cookie 的值：${ cookie.JSESSIONID.value } <br>

输出&lt;Context-param&gt;contextUserName 的值：${ initParam.contextUserName } <br>
输出&lt;Context-param&gt;contextUrl 的值：${ initParam.contextUrl } <br>
</body>
</html>
