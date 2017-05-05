<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*"%>
<%if (null==session.getAttribute("username") || "".equals(session.getAttribute("username"))){
	out.println("<script language='javascript'>alert('您离开的太久，已被踢出!');window.location.href='index.jsp';</script>");
	return;
}%>
