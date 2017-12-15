<%--
  Created by IntelliJ IDEA.
  User: HC
  Date: 2017/12/11
  Time: 11:41
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Map" %>
<html>
<head>
    <title>查询结果</title>
</head>
<body>
<h1>
    <%
        out.print("文件名包含 " + (String) request.getAttribute("name") + " 的文件有：<br>");
    %>
</h1>
<p>
    <%

        List<Map<String, String>> info = (List) request.getAttribute("result");
        for (Map<String, String> map : info) {
            for (Map.Entry<String, String> entry : map.entrySet()) {
                out.println(entry.getKey() + ":" + entry.getValue()+"<br>");
            }
            out.print("<br>");
        }
    %>
</p>
</body>
</html>
