<%@ page contentType="text/html; charset=GBK" language="java" import="java.util.*" errorPage="" %>
<% request.setCharacterEncoding("GBK"); %>
<%out.println(request.getAttribute("messages").toString());
%>