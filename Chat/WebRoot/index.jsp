<%@ page contentType="text/html;charset=GBK" language="java" %>
<html>
<title>
	������
</title>
<link href="CSS/style.css" rel="stylesheet">
<script language="javascript">
function check(){
	if(form1.username.value==""){
		alert("�������ǳƣ�");form1.username.focus();return false;	
	}else if(form1.username.value=="\'"){
		alert("�벻Ҫ����Ƿ��ַ���");form1.username.focus();return false;
	}
}
</script>
<body style="background:url('images/bg-a.jpg') no-repeat;">
<br>
<p>&nbsp;</p>
<p>&nbsp;</p>
<p>&nbsp;</p>

<form name="form1" method="post" action="MessagesAction?action=loginRoom" onSubmit="return check()">
    <table width="459" height="339"  border="0" align="center" cellpadding="0" cellspacing="0" background="images/login.gif">
      <tr>
        <td height="121" colspan="3" class="word_dark">&nbsp;</td>
      </tr>
      <tr>
        <td width="53" align="center" valign="top" class="word_dark">&nbsp;</td>
        <td width="216" align="center" valign="top" class="word_dark">�ǳƣ�
<input type="text" name="username"></td>
        <td width="94" valign="top" class="word_dark"><input type="image" name="imageField" src="images/go.jpg"></td>
      </tr>
</table>		
</form>
</body>
</html>
