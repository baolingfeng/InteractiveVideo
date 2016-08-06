<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" href="css/questionnaire.css" />
<script src="js/jquery-3.0.0.min.js"></script>
<script src="js/questionnaire.js"></script>

<title>评价 - Interactive Video</title>
</head>
<body>

<div style="margin:auto; width:70%;">
	<div class="qdiv w3-padding-jumbo">
		<p class="w3-large" style="margin-bottom:20px;">请对我们的工具进行评价？
		<span name="q5_warning" class="hidden-text warning-text" style="padding-left:20px">此题不能为空</span>
		</p>
		<input name="q5_time" value="" size="25" type="hidden">
		<div class="radio">
			<label class="w3-large"><input name="rate" id="1" value="1" type="radio"> excellent</label>
		</div>
		<div class="radio">
			<label class="w3-large"><input name="rate" id="2" value="2" type="radio"> very good</label>
		</div>
		<div class="radio">
			<label class="w3-large"><input name="rate" id="3" value="3" type="radio"> good</label>
		</div>
		<div class="radio">
			<label class="w3-large"><input name="rate" id="4" value="4" type="radio"> fair</label>
		</div>
		<div class="radio">
			<label class="w3-large"><input name="rate" id="5" value="5" type="radio"> poor</label>
		</div>
		<div class="radio">
			<label class="w3-large"><input name="rate" id="6" value="6" type="radio"> very bad</label>
		</div>
	</div>
	
	<div class="w3-padding-jumbo w3-light-grey">
		<input id="controlBtn" class="w3-btn w3-orange w3-large w3-text-white" value=" 提交 " type="button" onclick="submitRate()">
	</div>
</div>
</body>
</html>