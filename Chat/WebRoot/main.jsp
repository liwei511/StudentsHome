<%@ page contentType="text/html; charset=GBK" language="java" import="java.sql.*" %>
<%@ include file="safe.jsp"%>
<% request.setCharacterEncoding("GBK"); 
int maxTime=50*60*1000;		//����5���Ӳ�˵���������߳�������
%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>������</title>
<link href="CSS/style.css" rel="stylesheet">
<script language="javascript" src="JS/AjaxRequest.js"></script>
<script language="javascript">
window.setInterval("showContent();",1000); 
window.setInterval("showOnline();",10000); 
var sysBBS="<span style='font-size:14px; line-height:30px;'>��ӭ����ѧ����԰�����ң������������ҹ��򣬲�Ҫʹ�ò��������</span><br><span style='line-height:22px;'>";
//�˴���Ҫ��?nocache="+new Date().getTime()�����򽫳���������Ա�б����µ����
function showOnline(){
	var loader=new net.AjaxRequest("online.jsp?nocache="+new Date().getTime(),deal_online,onerror,"GET");
}
function showContent(){
	var loader1=new net.AjaxRequest("MessagesAction?action=getMessages&nocache="+new Date().getTime(),deal_content,onerror,"GET");
}
function onerror(){
	alert("�ܱ�Ǹ�����������ִ��󣬵�ǰ���ڽ��رգ�");
	window.opener=null;
	window.close();
}
function deal_online(){
	online.innerHTML=this.req.responseText;
}
function deal_content(){
	var returnValue=this.req.responseText;		//��ȡAjax����ҳ�ķ���ֵ
	var h=returnValue.replace(/\s/g,"");	//ȥ���ַ����е�Unicode�հ׷�
	if(h=="error"){
		//alert("�����˻��Ѿ����ڣ������µ�¼��");
		Exit();
	}else{
		content.innerHTML=sysBBS+returnValue+"</span>";
	}
}
//�����Ƿ����
function checkScrollScreen(){
	if(!form1.scrollScreen.checked){
		document.getElementById("content").style.overflow='scroll';
		//document.getElementById('content').scrollTop = document.getElementById('content').scrollHeight*2;
		//setTimeout('checkScrollScreen()',500);
	}else{
		document.getElementById("content").style.overflow='hidden';
		document.getElementById('content').scrollTop = document.getElementById('content').scrollHeight*2;	//��������Ϣ����һ��ʱ���������ȷ��͵�������Ϣ����ʾ
	}
	setTimeout('checkScrollScreen()',500);
}
window.onload=function(){
	checkScrollScreen();				//��ҳ�����������Ƿ����
	showContent();						//��ҳ���������ʾ��������
	showOnline();						//��ҳ���������ʾ������Ա�б�
}
</script>
<script language="javascript">
<!--
	function send(){	//��֤������Ϣ������
		if(form1.to.value==""){
			alert("��ѡ���������");return false;
		}
		if(form1.content1.value==""){
			alert("������Ϣ������Ϊ�գ�");form1.content1.focus();return false;
		}
		if(form1.isPrivate.checked){
			isPrivate="true";
		}else{
			isPrivate="false";
		}
		var param="from="+form1.from.value+"&face="+form1.face.value+"&color="+form1.color.value+"&to="+form1.to.value+"&content="+	form1.content1.value+"&isPrivate="+isPrivate; 
		var loader=new net.AjaxRequest("MessagesAction?action=sendMessage",deal_send,onerror,"POST",param);

	}
function deal_send(){
content.innerHTML=sysBBS+this.req.responseText+"</span>";
	if(form1.scrollScreen.checked){
		document.getElementById('content').scrollTop = document.getElementById('content').scrollHeight*2;	//��������Ϣ����һ��ʱ���������ȷ��͵�������Ϣ����ʾ
	}
	//���¼�ʱ
	clearTimeout(timer);
	timer = window.setTimeout("Exit()",<%=maxTime%>); 
	form1.content1.value="";
}	
	function Exit(){
		window.location.href="MessagesAction?action=exitRoom";
		alert("��ӭ���´ι��٣�");
	}
-->
</script>
<script language="javascript">
function set(selectPerson){	//�Զ�����������
	if(selectPerson!="<%=session.getAttribute("username")%>"){
		if(form1.isPrivate.checked && selectPerson=="������"){
			alert("��ѡ��˽�Ķ���");
		}else{
			form1.to.value=selectPerson;
		}
	}else{
		alert("������ѡ���������");
	}
}
function checkIsPrivate(){
	if(form1.isPrivate.checked){
		if(form1.to.value=="������"){	
			alert("��ѡ��˽�Ķ���");
			form1.to.value="";
		}
		
	}	
}
</script>
<script language="jscript"> 
timer = window.setTimeout("Exit()",<%=maxTime%>); 		//���ڵ��û���ʱ�䲻˵��ʱ�������߳�������
window.onbeforeunload=function(){   //���û�����������еġ��رա���ťʱ��ִ���˳�����
	ver = navigator.appVersion; //������İ汾
	bType =navigator.appName;//�����������
	vNumber=parseFloat(ver.substring(ver.indexOf("MSIE")+5,ver.lastIndexOf("Windows")));
	if(bType=="Microsoft Internet Explorer"){
		if(vNumber>6){//IE6���������
			vOffset=document.body.offsetWidth-document.body.scrollWidth;
			if(event.clientY<0 && event.clientX>document.body.scrollWidth-vOffset-20){  
				 	Exit();		//ִ���˳�����
			}else{//IE6�����������
				if(event.clientY<0 && event.clientX>document.body.scrollWidth){  
				 	Exit();		//ִ���˳�����
				}   
			}
		}
	}
}     
</script> 
</head>
<body style="background:url('images/bg-a.jpg') no-repeat;">
<p>&nbsp;</p>

<table width="778" height="152" border="0" align="center" cellpadding="0" cellspacing="0" background="images/top.jpg">
  <tr>
    <td>&nbsp;</td>
  </tr>
</table>
<table width="778" height="276" border="0" align="center" cellpadding="0" cellspacing="0">
  <tr>
    <td width="165" valign="top" bgcolor="#F7FDED" id="online" style="padding:5px">������Ա�б�</td>
    <td width="613"  height="200px" valign="top" bgcolor="#FFFFFF" style="padding:5px; ">
	<div style="height:290px; overflow:hidden" id="content">��������</div>
	</td>

  </tr>
</table>
<table width="778" border="0" align="center" cellpadding="0" cellspacing="0" bordercolor="#D6D3CE" bgcolor="#D1E9AA">
<form action="" name="form1" method="post" >
  <tr>
    <td height="30" align="left">&nbsp;</td>
    <td height="37" align="left"><input name="from" type="hidden" value="<%=session.getAttribute("username")%>">[<%=session.getAttribute("username")%> ]��
      <input name="to" type="text" value="" size="35" readonly="readonly">
����
<select name="face" class="wenbenkuang">
  <option  value="�ޱ����">�ޱ����</option>
  <option value="΢Ц��" >΢Ц��</option>
  <option value="Ц�Ǻǵ�">Ц�Ǻǵ�</option>
  <option value="�����">�����</option>
  <option value="���ߵ�">���ߵ�</option>
  <option value="�����" selected>�����</option>
  <option value="������">������</option>
  <option value="�Ҹ���">�Ҹ���</option>
  <option value="�����">�����</option>
  <option value="����ӯ����">����ӯ����</option>
  <option value="���������">���������</option>
  <option value="�����">�����</option>
  <option value="���������">���������</option>
  <option value="��ݺݵ�">��ݺݵ�</option>
  <option value="������">������</option>
  <option value="������">������</option>
  <option value="�����ֻ���">�����ֻ���</option>
  <option value="ͬ���">ͬ���</option>
  <option value="�ź���">�ź���</option>
  <option value="������Ȼ��">������Ȼ��</option>
  <option value="�����">�����</option>
  <option value="����˹���">����˹���</option>
  <option value="�޾���ɵ�">�޾���ɵ�</option>
</select>
˵�� ���Ļ�
<input name="isPrivate" type="checkbox" class="noborder" id="isPrivate" value="true" onClick="checkIsPrivate()">
����
<input name="scrollScreen" type="checkbox" class="noborder" id="scrollScreen" onClick="checkScrollScreen()" value="1" checked></td>
    <td width="163" align="left">
      <select name="color" size="1" class="wenbenkuang" id="select">
        <option selected>Ĭ����ɫ</option>
        <option style="color:#FF0000" value="FF0000">��ɫ����</option>
        <option style="color:#0000FF" value="0000ff">��ɫ����</option>
        <option style="color:#ff00ff" value="ff00ff">��ɫ����</option>
        <option style="color:#009900" value="009900">��ɫ�ഺ</option>
        <option style="color:#009999" value="009999">��ɫ��ˬ</option>
        <option style="color:#990099" value="990099">��ɫ�н�</option>
        <option style="color:#990000" value="990000">��ҹ�˷�</option>
        <option style="color:#000099" value="000099">��������</option>
        <option style="color:#999900" value="999900">�����Ʒ�</option>
        <option style="color:#ff9900" value="ff9900">�ֽ�����</option>
        <option style="color:#0099ff" value="0099ff">��������</option>
        <option style="color:#9900ff" value="9900ff">��������</option>
        <option style="color:#ff0099" value="ff0099">���İ�ʾ</option>
        <option style="color:#006600" value="006600">ī�����</option>
        <option style="color:#999999" value="999999">��������</option>
      </select></td>
    <td width="19" align="left">&nbsp;</td>
  </tr>
  <tr>
    <td width="21" height="30" align="left">&nbsp;</td>
    <td width="905" align="left"><input name="content1" type="text" size="104" onKeyDown="if(event.keyCode==13 && event.ctrlKey){send();}">
      <input name="Submit2" type="button" class="btn_blank" value="����" onClick="send()"></td>
    <td align="right"><input name="button_exit" type="button" class="btn_blank" value="�ر�" onClick="Exit()"></td>
    <td align="center">&nbsp;</td>
  </tr>
  <tr>
    <td height="30" align="left">&nbsp;</td>
    <td colspan="2" align="center" class="word_dark">&nbsp;ѧ����԰������  &nbsp;2015 &nbsp;�ӱ����̴�ѧ</td>
    <td align="center">&nbsp;</td>
  </tr>
</form>
</table>
</body>
</html>
