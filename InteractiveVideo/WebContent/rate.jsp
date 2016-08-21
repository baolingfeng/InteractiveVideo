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
		<p class="w3-large" style="margin-bottom:20px;">请对我们的工具中各个功能的可用性进行打分（1-5分，5分为最好）？
		<span name="rate_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
		</p>
		<table class="ratetable">
		<tr>
		<td><label class="w3-large">与视频同步的代码操作（Code Timeline）</label></td>
		<td>
			<input name="r1" id="1" value="1" type="radio"> 1
			<input name="r1" id="1" value="2" type="radio"> 2
			<input name="r1" id="1" value="3" type="radio"> 3
			<input name="r1" id="1" value="4" type="radio"> 4
			<input name="r1" id="1" value="5" type="radio"> 5
		</td>
		</tr>
		<tr>
		<td><label class="w3-large">代码搜索并与视频同步 （Search Code Element）</label></td>
		<td>
		<input name="r2" id="2" value="1" type="radio"> 1
		<input name="r2" id="2" value="2" type="radio"> 2
		<input name="r2" id="2" value="3" type="radio"> 3
		<input name="r2" id="2" value="4" type="radio"> 4
		<input name="r2" id="2" value="5" type="radio"> 5
		</td>
		</tr>
		<tr>
		<td><label class="w3-large">与视频同步的代码文件 (Code Content)</label></td>
		<td>
		<input name="r3" id="2" value="1" type="radio"> 1
		<input name="r3" id="2" value="2" type="radio"> 2
		<input name="r3" id="2" value="3" type="radio"> 3
		<input name="r3" id="2" value="4" type="radio"> 4
		<input name="r3" id="2" value="5" type="radio"> 5
		</td>
		</tr>
		<tr>
		<td><label class="w3-large">总体评价</label></td>
		<td>
		<input name="r4" id="2" value="1" type="radio"> 1
		<input name="r4" id="2" value="2" type="radio"> 2
		<input name="r4" id="2" value="3" type="radio"> 3
		<input name="r4" id="2" value="4" type="radio"> 4
		<input name="r4" id="2" value="5" type="radio"> 5
		</td>
		</tr>
		</table>
	</div>
	
	<div class="qdiv w3-padding-jumbo w3-light-grey">
		<label class="w3-large">如果你有什么意见或者建议，请告诉我们以便改进这个工具：</label>
		<br/>
		<textarea name="comment" rows="10" cols="70"></textarea>
	</div>
	
	<div class="w3-padding-jumbo w3-light-grey">
		<input id="controlBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 提交 " type="button" onclick="submitRate(recordId, 4)">
	</div>
</div>
</body>
</html>