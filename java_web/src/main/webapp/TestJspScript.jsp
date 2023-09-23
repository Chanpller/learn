<%@ page import="java.util.HashMap" %>
<%@ page import="java.util.Map" %>
<%@ page import="java.util.Date" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html>
<head>
    <title>Title</title>
    <%--=============================声明脚本=======================--%>
    <%--1、声明类属性--%>
    <%!
        private Integer id;
        private String name;
        private static Map<String,Object> map;
    %>
    <%--2、声明static静态代码块--%>
    <%!
        static {
            map = new HashMap<String,Object>();
            map.put("key1", "value1");
            map.put("key2", "value2");
            map.put("key3", "value3");
        }
    %>
    <%--3、声明类方法--%>
    <%!
        public int abc(){
            return 12;
        }
    %>
    <%--4、声明内部类--%>
    <%!
        public static class A {
            private Integer id = 12;
            private String abc = "abc";
        }
    %>


    <%--=============================表达式脚本=======================--%>
    <%=new Date() %>

    <%--=============================代码脚本=======================--%>
    <%--2.代码脚本----for 循环语句--%>
    <table>
        <%
            for (int j = 0; j < 10; j++) {
        %>
        <tr>
            <td>第 <%=j + 1%>行</td>
        </tr>
        <%
            }
        %>
    </table>
    <%--3.翻译后java文件中_jspService方法内的代码都可以写--%>
    <%
        String username = request.getParameter("username");
        System.out.println("用户名的请求参数值是：" + username);
    %>
</head>
<body>
    这是一个测试的jsp页面
</body>
</html>
