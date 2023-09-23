<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2023/9/16
  Time: 11:58
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
  <%
    // out 输出
    out.write("这是 out 的第一次输出<br/>");
  // out flush 之后。会把输出的内容写入 writer 的缓冲区中
    out.flush();
  // 最后一次的输出，由于没有手动 flush，会在整个页面输出到客户端的时候，自动写入到 writer
//    缓冲区
    out.write("这是 out 的第二次输出<br/>");
  // writer 的输出
    response.getWriter().write("这是 writer 的第一次输出<br/>");
    response.getWriter().write("这是 writer 的第二次输出<br/>");
  %>
</body>
</html>
