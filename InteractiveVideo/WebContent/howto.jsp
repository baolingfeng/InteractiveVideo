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
			<li>在问卷页面，点击“打开视频并开始答题”按钮，会弹出另外一个页面（<span class="w3-red">如果浏览器屏蔽弹窗，请允许打开</span>），展示需要看的编程教学视频。</li>
			<li><span style="color:red;">教学视频有解说，观看时最好打开声音，如果没有声音，可以从视频中的文字标注理解当前视频播放的内容</span></li>
			<li>开始后，请不要关闭问卷页面和视频页面</li>
			<li>开始实验后，可以根据问题去视频中寻找答案，实验组记得利用我们的工具。</li>
			<li>完成所有问题后点击“提交”按钮，完成问卷</li>
			</ul>
		
		<%if("1".equals(group)){ %>
		
		<span style="background-color: yellow;">InteractiveVideo功能说明（实验组需要了解）：</span><br/>
		<span style="color:red;">tips：实现组可以根据问题，用我们的工具快速定位到视频相关的位置，还可以查看某些API的文档。</span>
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
		
		<span style="background-color: yellow;">（实验组需要了解）下面一个简短的视频说明了我们工具的功能； 你们可以利用我们的工具更快更好的完成问卷。</span>
		
		
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