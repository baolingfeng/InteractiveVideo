<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/questionnaire.css" />
<script src="js/jquery-3.0.0.min.js"></script>

<title>问卷完成</title>
<%
//session.invalidate();
int recordId = -1;
try{
	recordId = Integer.parseInt(request.getParameter("record"));
}catch(Exception e){}

if (session != null) {
    session.invalidate();
}
%>
</head>
<body>
<div style="margin:auto; width:70%;">
<div class="w3-padding-jumbo w3-light-grey">
	<h1>感谢你的参与！</h1>
	<label class="w3-large italic-text">
	参与这个实验我们将赠送价值50元左右的U盘，非常感谢你的参与。
	<br/>
	<span id="info">
	<input class="underline-input w3-light-grey " name="useremail" size="100" placeholder="请在此输入"></input>
	<br/><br/>
	<input id="resetBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 提交 " type="button" onclick="submitEmail()">
	</span>
	</label>
</div>
</div>
<script type="text/javascript">
var recordId = <%=recordId %>;

function submitEmail()
{
	var email = $('input[name=useremail]').val();
	if(email.trim() == "") {
		alert("邮件不能为空");
		return;
	}
	
	$.ajax({
		type: 'post',
		url: '/InteractiveVideo/SubmitEmail',
		data: {'recordId': recordId, 'email': email},
		success: function(d){
			console.log(d);
			
			var r = confirm("提交成功, 谢谢你的参与！");
			
			$("#info").html("<label class='w3-pale-red'>问卷完成</label>");
		},
		error: function(xhr, status, error) {
			var err = eval("(" + xhr.responseText + ")");
			alert(err.Message);
		}
	});
}
</script>
</body>
</html>