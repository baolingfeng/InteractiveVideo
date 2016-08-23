<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="cn.zju.blf.video.VideoMetadataManager" %>
<%@ page import="java.util.List" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" media="all" href="css/style.css" />
<script src="js/jquery-3.0.0.min.js"></script>

<title>VT-Revolution Demo</title>
</head>

<body>
<div id="main">
	<div id="header">
	<h1>VT-Revolution Demo</h1>
	</div>
	
	<div id="video_content">
		<video id ="v"  controls>
			<source src="data/howto_audio.mp4" type="video/mp4">
		   	Your browser does not support the video tag.
		</video>
	</div>
</div>
<script type="text/javascript">
var origin_video_width = 1920;
var origin_video_height = 1080;

var screen_width = screen.width;
var screen_height = screen.height;

var scale = 0.7 * screen_width/origin_video_width;

console.log(origin_video_width + '/' + screen_width + '/' + scale);

$(document).ready(function(){
	
	$('#v').width(origin_video_width * scale);
	$('#v').height(origin_video_height * scale);
	
	$('#video_content').width(origin_video_width * scale);
});

</script>
</body>
</html>