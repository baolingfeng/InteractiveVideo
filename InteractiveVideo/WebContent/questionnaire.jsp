<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="cn.zju.blf.video.VideoMetadataManager" %>

<%
	String group = request.getParameter("group");
	String videoName = request.getParameter("name");
	
	String groupName = "1".equals(group) ? "实验组" : "对照组";
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>问卷调查 - <%=videoName %></title>
</head>
<link rel="stylesheet" href="css/questionnaire.css" />
<script src="js/jquery-3.0.0.min.js"></script>
<script src="js/questionnaire.js"></script>


<script type="text/javascript">
var group = <%=group%>;
var videoName = "<%=videoName%>";

var questionNumber = -1;
if(videoName == "email")
{
	questionNumber = 9;
}
else if(videoName == "mysql")
{
	questionNumber = 9;
}
else if(videoName == "plugin")
{
	questionNumber = 11;
}

var lastQestion = -1;

$(document).ready(function(){
	$("input[name=group][value=" + group + "]").attr('checked', 'checked');
	
	for(i=1; i<=questionNumber; i++)
	{
		$("input[name=q"+i+"]").prop('disabled', true);
		
		var type = $("input[name=q"+i+"]").prop('type');
		if(type == "checkbox" || type == "radio")
		{
			$("input[name=q"+i+"]").change(function() {
				var t = createDateAsUTC(new Date()).toISOString().slice(0, 19); 
				
				if(lastQestion != this.name)
				{
					var qt = $("input[name="+this.name+"_time]").val();
					if(qt != "") qt = qt + ",";
					$("input[name="+this.name+"_time]").val(qt + t);
				}
				else
				{
					var qt = $("input[name="+this.name+"_time]").val();
					var arr = qt.split(',')
					arr[arr.length-1] = t;
					$("input[name="+this.name+"_time]").val(arr.join(','));
				}
				
				lastQestion = this.name;
			});
		}
		else if(type == "text" || type == "textarea")
		{
			$("input[name=q"+i+"]").focus(function(){
				var t = createDateAsUTC(new Date()).toISOString().slice(0, 19); 
				if(lastQestion != this.name)
				{
					var qt = $("input[name="+this.name+"_time]").val();
					if(qt != "") qt = qt + ",";
					$("input[name="+this.name+"_time]").val(qt + t);
				}
				else
				{
					var qt = $("input[name="+this.name+"_time]").val();
					var arr = qt.split(',')
					arr[arr.length-1] = t;
					$("input[name="+this.name+"_time]").val(arr.join(','));
				}
				
				lastQestion = this.name;
			});
		}
	}
	
	initQuestionnaire();
});
</script>
<body>

<div style="margin:auto; width:70%;">
<form role="form" name="quizform" action="quiztest.asp?qtest=HTML" method="post">

<div class="w3-padding-jumbo w3-light-grey">
	<h2>
	这个调查问卷是你需要观看<a href="/InteractiveVideo/ViewVideo?name=<%=videoName%>&group=<%=group%>" target="_blank"><%=VideoMetadataManager.getInstance().getTitle(videoName) %></a>的这个视频后完成
	</h2>
	<br/><br/>
	<label class="w3-large">
	视频简介：
	</label>
	<br/>
	<label class="intro-text">
	<%=VideoMetadataManager.getInstance().getIntro(videoName) %>
	</label>
	
	<br/><br/>
	<label class="w3-small italic-text">
	如果有问题，请联系鲍凌峰博士：lingfengbao@zju.edu.cn
	</label>
</div>

<div class="w3-padding-jumbo w3-light-grey">
	<h3 style="margin-bottom:10px;">
	<a class="w3-red" href="howto.jsp?group=<%=group %>" target="_blank">请查看问卷和工具介绍</a>
	</h3>
</div>

<div class="w3-padding-jumbo w3-light-grey hidden-text">
	<h2 style="margin-bottom:10px;">
	你当前属于的实验组为：
	<span class="italic-text"> <%=groupName %> </span>
	<input name="group" value="<%=group %> " size="25" type="hidden">
	</h2>
</div>

<div class="w3-padding-jumbo w3-light-grey">
	<label class="w3c-large" style="color:red; font-style: italic;">
	注意：
	<input name="single-choice" id="1" value="1" type="radio" checked disabled> 单选
	<input name="single-choice" id="1" value="1" type="checkbox" checked disabled> 可多选
	</label>
</div>

<div class="w3-padding-jumbo w3-light-grey">
	<input id="controlBtn" name="controlBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 打开视频并开始答题 " type="button" onclick="startAnswer(questionNumber, this.value, '<%=videoName%>', '<%=group%>')">
	<span id="timecounter_span" class="w3-large" style="padding-left:50px; display: none;">持续时间：<label id="timecounter"></label></span>
	<input name="starttime" value="" type="hidden">
</div>

<div class="w3-padding-jumbo w3-light-grey hidden-text overall-warning">
<h3 style="color: red;">所有问题都必须回答！</h3>
</div>

<%if("email".equals(videoName)){ %>
	<%@include file="q_email.jsp"%>
<%}else if("mysql".equals(videoName)){ %>
	<%@include file="q_mysql.jsp"%>
<%}else if("plugin".equals(videoName)){ %>
	<%@include file="q_plugin.jsp"%>
<%} %>

<div class="w3-padding-jumbo w3-light-grey">
	<input id="resetBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 回到顶部 " type="button" onclick="backToTop()">
	<input id="controlBtn" name="controlBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 打开视频并开始答题 " type="button" onclick="startAnswer(questionNumber, this.value, '<%=videoName%>', '<%=group%>')">
	<input id="resetBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 重新开始答题 " type="button" onclick="resetAnswer(questionNumber, '<%=videoName%>', '<%=group%>')">
</div>

</form>
</div>
</body>
</html>