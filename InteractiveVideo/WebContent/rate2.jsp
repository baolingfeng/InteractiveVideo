<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/questionnaire.css" />
<script src="js/jquery-3.0.0.min.js"></script>
<script src="js/questionnaire.js"></script>

<style>
table.ratetable td {
  vertical-align:middle;
  font-weight:300;
  font-size:18px;
  border: 1px solid #ddd;
  padding: 10px;
}

</style>

<title>评价 - Interactive Video</title>

<%
	int recordId = -1;
	try{
		recordId = Integer.parseInt(request.getParameter("record"));
	}catch(Exception e){}
%>
<script>
var recordId = <%=recordId %>;
</script>
</head>
<body>

<div style="margin:auto; width:70%;">
	<div class="qdiv w3-padding-jumbo w3-light-grey">
		<p class="w3-large" style="margin-bottom:20px;">你认为当前的视频播放器对观看编程教学视频是否能够有效的支持（1-5分，5分为最好）？
		<span name="rate_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
		</p>
		<input name="r1" id="1" value="1" type="radio"> 1
		<input name="r1" id="1" value="2" type="radio"> 2
		<input name="r1" id="1" value="3" type="radio"> 3
		<input name="r1" id="1" value="4" type="radio"> 4
		<input name="r1" id="1" value="5" type="radio"> 5
	</div>
	
	<div class="qdiv w3-padding-jumbo w3-light-grey">
		<label class="w3-large">你认为观看编程教学视频，如果有额外的工具支持，你最想要的功能是什么：</label>
		<br/>
		<textarea name="comment" rows="10" cols="70"></textarea>
	</div>
	
	<div class="w3-padding-jumbo w3-light-grey">
		<input id="controlBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 提交 " type="button" onclick="submitRate(recordId, 1)">
	</div>
</div>
</body>
</html>