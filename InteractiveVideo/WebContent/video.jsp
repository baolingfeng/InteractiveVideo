<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="cn.zju.blf.video.VideoMetadataManager" %>
<%@ page import="java.util.List" %>
<%
String videoName = request.getParameter("name");
String video = VideoMetadataManager.getInstance().getVideo(videoName);
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" media="all" href="css/style.css" />
<link rel="stylesheet" media="all" href="css/jquery-ui.css" />
<link href="js/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="js/codemirror.css" /> 	
<link rel="stylesheet" href="js/jquery-ui.min.css">

<title>VT-Revolution</title>

<script src="js/jquery-3.0.0.min.js"></script>
<script src="js/jquery.contextMenu.js" type="text/javascript"></script>
<script src="js/jquery.ui.position.min.js" type="text/javascript"></script>
<script src="js/codemirror.js"></script>
<script src="js/clike.js"></script>
<script src="js/jquery-ui.min.js"></script>
<script src="js/jquery.popupoverlay.js"></script>
<script src="charts/loader.js"></script>

</head>


<body>
<div id="main">
	<div id="header">
	<h1>Interactive Programming Video Tutorial</h1>
	</div>
	
	<div id="video_content">
		<video id ="v"  controls>
			<source src=<%=video %> type="video/mp4">
		   	Your browser does not support the video tag.
		</video>
	</div>
	
	<div id="toolbar" >
		<span><input class="searchbox" type="text" name="searchbox" placeholder="Workflow Search" value=""/></span>
		<span class="openbtn" onclick="openNav()">Workflow Timeline</span>
		<span class="openbtn" onclick="openNav2()">File Content View</span>
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
	  <!--  <input class="filterbox" type="text" name="filter" placeholder="filter action" value=""/>-->
	   <div class="multiselect">
        <div class="selectBox" onclick="showCheckboxes()">
            <select>
                <option value="">Filter By Operations or Files</option>
            </select>
            <div class="overSelect"></div>
        </div>
        <div id="checkboxes" style="color:white;">
        	<label>Operations</label>
            <label><input type="checkbox" name="filter" value="fileopen"/>File Open</label>
            <label><input type="checkbox" name="filter" value="fileswtich"/>File Switch</label>
            <label><input type="checkbox" name="filter" value="fileedit"/>File Edit</label>
            <label><input type="checkbox" name="filter" value="APICall"/>Add/Delete API Call</label>
            <label><input type="checkbox" name="filter" value="Import"/>Add/Delete Import</label>
            <label><input type="checkbox" name="filter" value="Variable"/>Add/Delete Variable</label>
            <label><input type="checkbox" name="filter" value="Field"/>Add/Delete Field</label>
            <label><input type="checkbox" name="filter" value="exception"/>Inspect Exception</label>
            <label>Files</label>
        </div>
    </div>
	  
	  <a href="javascript:void(0)" class="closebtn" onclick="closeNav()">&times;</a>
	  <div id="display" style="width: 100%; height: 90%; overflow-y: scroll;">
	  	<table id="tbl_events" style="width: 100%">
	  </table>
	  </div>
</div>

<div id="mySidenav2" class="sidenav">
	  <a href="javascript:void(0)" class="closebtn" onclick="closeNav2()">&times;</a>
	  <div id="filetips" class="w3-yellow">这个区域可以随着视频播放更新文件内容</div>
	  <div id="filetabs" style="display:none; ">
		  <ul id="tabnames"></ul>
	  </div>
	  <div id="timelinebar" style="width: 100%;"></div>
</div>
</body>

<script type="text/javascript">
var videoName = '<%=videoName%>';
</script>

<script src="js/main.js" type="text/javascript"></script>

</html>