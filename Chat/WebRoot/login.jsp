<%@ page contentType="text/html;charset=GBK" language="java" %>
<%@ page import="java.util.*"%>
<%@ page import="com.wgh.UserInfo"%>
<%@ page import="com.wgh.UserListener"%>
<%
request.setCharacterEncoding("GBK");
String username=request.getParameter("username");	//��õ�¼�û���
UserInfo user=UserInfo.getInstance();		//���UserInfo��Ķ���
session.setMaxInactiveInterval(600);		//����Session�Ĺ���ʱ��Ϊ10����
Vector vector=user.getList();
boolean flag=true;		//����Ƿ��¼�ı���
//�ж��û��Ƿ��¼
if(vector!=null&&vector.size()>0){
	for(int i=0;i<vector.size();i++){
		if(user.equals(vector.elementAt(i))){
			out.println("<script language='javascript'>alert('���û��Ѿ���¼');window.location.href='index.jsp';</script>");
			flag=false;
			break;
		}
	}
}
//�����û���Ϣ
if(flag){
	UserListener ul=new UserListener();
	ul.setUser(username);
	session.setAttribute("user",ul);
	session.setAttribute("username",username);
	user.addUser(ul.getUser());	
	//���浱ǰ��¼���û���
	session.setAttribute("loginTime",new Date().toLocaleString());		//�����¼ʱ��
	response.sendRedirect("MessagesAction?action=loginRoom");
}
%>