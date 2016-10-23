<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="cn.zju.blf.video.VideoMetadataManager" %>
<%@ page import="java.util.Set" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>VT-Revolution - Interactive Programming Video Tutorial Authoring and Watching System</title>
<link rel="stylesheet" media="all" href="css/style.css" />
<link rel="stylesheet" media="all" href="css/questionnaire.css" />

<link href="js/jquery.contextMenu.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" href="js/codemirror.css" /> 	
<link rel="stylesheet" href="js/jquery-ui.min.css">

<style>

html,body{ height: 100%;}

body{position: relative; min-height: 550px;}

#header {
    background-color:black;
    color:white;
    text-align:center;
    padding:5px;
}

#videolist {
	height: 75%;
	width: 800px;
    margin: 0 auto;
    padding-top: 100px;
}

#footer {
    background-color:black;
    color:white;
    text-align:center;
    position: fix; 
    bottom: 0; 
    left: 0;
    width: 100%;
    margin-bottom:0px;
}

table.vtable{
	font-family: arial, sans-serif;
    border-collapse: collapse;
    width: 100%;
}

table.vtable td, th {
    border: 1px solid #dddddd;
    text-align: left;
    padding: 8px;
}

</style> 

</head>
<body>
<div id="header">
<h1>VT-Revolution</h1>
</div>

<div id="videolist">
	<div>
		<span style="font-size: 26px; font-weight: bold;">Video List</span>
		<span>(<a style="color: red;" href="demo.jsp" target="_blank">Demonstration</a>)</span>
	</div>
	<br/>
	<table class="vtable">
	<tr>
		<th>Video Name</th>
		<th>Description</th>
		<th style="text-align: center;">View</th>
		<th style="text-align: center;">Questionnaire</th>
	</tr>
	<%
		Set<String> videoList = VideoMetadataManager.getInstance().getVideoList();
		for(String v: videoList)
		{
	%>
	<tr>
		<td><%=v%></td>
		<td><%=VideoMetadataManager.getInstance().getTitle(v)%></td>
		<td style="text-align: center;">
			<a href="video.jsp?name=<%=v%>" target="_blank">
			<img src="images/play.png" alt="Play with VT-Revolution" title="Play with VT-Revolution"/>
			</a>
		</td>
		<td style="text-align: center;">
			<a href="questionnaire.jsp?name=<%=v%>&group=1" target="_blank">
			<!--<img src="images/questionnaire.png" alt="View Questionnaire" title="View Questionniare (Chinese Version)"/>-->
			(Chinese Version)
			</a>
			<br/>
			<a href="questionnaire_en.jsp?name=<%=v%>&group=1" target="_blank">
			(English Version)
			</a>
		</td>
	</tr>
	<%}%>
	</table>
	<br/>
	
</div>


<div id="footer">Copyright @ lingfengbao@zju.edu.cn</div>

</body>
</html>