<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*"%>
<%if (null==session.getAttribute("username") || "".equals(session.getAttribute("username"))){
	out.println("<script language='javascript'>alert('���뿪��̫�ã��ѱ��߳�!');window.location.href='index.jsp';</script>");
	return;
}%>
