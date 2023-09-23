<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/9/15
  Time: 22:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="ISO-8859-1"
         import="java.lang.String"
         import="java.util.Date"
         autoFlush="false"
         buffer="1kb"
         errorPage="error500.jsp"
         isErrorPage="true"
         session="false"
%>
<%--<%@ page extends="javax.servlet.HttpServlet" %>--%>

<html>
<head>
    <title>Title</title>
    <%=new Date() %>
    <% int i = 1/0 ;%>
</head>
<body>
    这是一个测试的jsp页面
</body>
</html>
