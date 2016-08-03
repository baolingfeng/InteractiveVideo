<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="cn.zju.blf.video.VideoMetadataManager" %>
<%@ page import="java.util.List" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" media="all" href="css/style.css" />
<link href="js/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="js/codemirror.css" /> 	
<link rel="stylesheet" href="js/jquery-ui.min.css">

<title>Interactive Video Demo</title>

<script src="js/jquery-3.0.0.min.js"></script>
<script src="js/jquery.contextMenu.js" type="text/javascript"></script>
<script src="js/jquery.ui.position.min.js" type="text/javascript"></script>
<script src="js/codemirror.js"></script>
<script src="js/clike.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.popupoverlay.js"></script>

</head>
<%
String videoName = request.getParameter("name");
String video = VideoMetadataManager.getInstance().getVideo(videoName);


%>

<body>
<div id="main">
	<div id="header">
	<h1>Interactive Tutorial Video</h1>
	</div>
	
	<div id="video_content">
		<video id ="v"  controls>
			<source src=<%=video %> type="video/mp4">
		   	Your browser does not support the video tag.
		</video>
	</div>
	
	<div id="toolbar" >
		<span><input class="searchbox" type="text" name="searchbox" placeholder="search code element" value=""/></span>
		<span class="openbtn" onclick="openNav()">Show Code Timeline</span>
		<span class="openbtn" onclick="openNav2()">Show Code Content</span>
	</div>
	
	<div id="intro">
		<div id="title"><%=VideoMetadataManager.getInstance().getTitle(videoName) %></div>
		<br/>
		<div id="intro_content"><%=VideoMetadataManager.getInstance().getIntro(videoName) %></div>
	</div>
		
	<div id="search" class="mydialog">
		<div id="search_result"></div>
	</div>
	
	<div id="popup" class="mydialog">
		<div id="APIHelp">loading...
		</div>
		<div style="text-align: right"><button class="popup_close" style="margin-top:8px;">Close</button></div>
	</div>
	
	<div id="dialog" class="mydialog">
	</div>
	
</div>

<div id="mySidenav" class="sidenav">
	  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
	  <div id="display" style="width: 100%; height: 90%; overflow-y: scroll;">
	  	<table id="tbl_events" style="width: 100%">
	  </table>
	  </div>
</div>

<div id="mySidenav2" class="sidenav">
	  <a href="javascript:void(0)" class="closebtn" onclick="closeNav2()">&times;</a>
	  <div id="filetabs" style="display:none;">
		  <ul id="tabnames"></ul>
	  </div>
</div>
</body>

<script type="text/javascript">
var videoName = '<%=videoName%>';
</script>

<script src="js/main.js" type="text/javascript"></script>

</html>