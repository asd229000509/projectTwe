<%--
  Created by IntelliJ IDEA.
  User: 22900
  Date: 2018-03-13
  Time: 19:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" isELIgnored="true" %>
<%

    String callback = request.getParameter("callback");

    out.print(callback + "({\"test\":\"heima\"});");

%>
