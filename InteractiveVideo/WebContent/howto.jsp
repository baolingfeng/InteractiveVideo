<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 
 <%
	String group = request.getParameter("group");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="css/questionnaire.css" />
<link rel="stylesheet" href="css/flexslider.css" />

<script src="js/jquery.1.min.js"></script>
<script src="js/jquery.flexslider-min.js"></script>
<script src="js/modernizr.js"></script>

 <script type="text/javascript" src="js/shCore.js"></script>
  <script type="text/javascript" src="js/shBrushXml.js"></script>
  <script type="text/javascript" src="js/shBrushJScript.js"></script>

  <!-- Optional FlexSlider Additions -->
  <script src="js/jquery.easing.js"></script>
  <script src="js/jquery.mousewheel.js"></script>

<title>说明</title>
</head>
<body>
<div style="margin:auto; width:90%;">
	<div id="header">
		<h1>说明</h1>
		</div>
		
		<div class="w3-padding-jumbo w3-light-grey">
			<h3>实验步骤</h3>
			<ul>
			<li>在问卷页面，点击“打开视频并开始答题”按钮，会弹出另外一个页面（如果浏览器屏蔽弹窗，请允许打开），展示需要看的编程教学视频。</li>
			<li>教学视频有解说，需要打开声音</li>
			<li>开始后，请不要关闭问卷页面和视频页面</li>
			<li>问卷上问题的答案一般都可以通过观看视频找到，可以边看视频边回答问卷</li>
			<li>完成所有问题后点击“提交”按钮，完成问卷</li>
			</ul>
		
		<%if("1".equals(group)){ %>
		
		<span style="background-color: yellow;">图片说明：</span>
		<div class="flexslider" style="width: 1240px;">
		  <ul class="slides">
		    <li>
		      <img src="images/1.png" />
		    </li>
		    <li>
		      <img src="images/2.png" />
		    </li>
		    <li>
		      <img src="images/3.png" />
		    </li>
		    <li>
		      <img src="images/4.png" />
		    </li>
		  </ul>
		</div>
		
		<span style="background-color: yellow;">实验组请先看一下下面一个简短的视频了解下我们的工具； 你们可以利用我们的工具更快更好的完成问卷。</span>
		
		
		<div id="video_content">
			<video id ="v"  controls>
				<source src="data/howto_audio.mp4" type="video/mp4">
			   	Your browser does not support the video tag.
			</video>
		</div>
		<%} %>
		</div>
</div>

<script>
$(window).load(function() {
	  $('.flexslider').flexslider({
	    animation: "slide"
	  });
});
</script>
</body>
</html>