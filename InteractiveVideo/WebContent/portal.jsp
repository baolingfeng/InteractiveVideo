<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="cn.zju.blf.video.VideoMetadataManager" %>
<%@ page import="java.util.Set" %>

<%
	String group = request.getParameter("group");
	String v = request.getParameter("v");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Interactive Video Demo</title>
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
	width: 600px;
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
<h1>Interactive Tutorial Video</h1>
</div>

<div id="videolist">
	<div>
		<h1>Video List</h1>
		
	</div>
	<table class="vtable">
	<tr>
		<th>Video Name</th>
		<th>Description</th>
		<th>Open</th>
	</tr>
	<%
		Set<String> videoList = VideoMetadataManager.getInstance().getVideoList();
		for(String video: videoList)
		{
			if("1".equals(v) && "plugin".equals(video))
			{
				 continue;
			}
			else if("2".equals(v) && ("email".equals(video) || "mysql".equals(video)))
			{
				continue;
			}
	%>
	<tr>
		<td><%=video%></td>
		<td><%=VideoMetadataManager.getInstance().getTitle(video)%></td>
		<td>
			<a href="/VTRevolution/PortalServlet?name=<%=video%>&group=<%=group%>" target="_blank">open</a>
		</td>
	</tr>
	<%}%>
	</table>
	<br/>
	<span><a class="w3-red" href="howto.jsp?group=<%=group%>" target="_blank">请查看实验介绍</a></span>
</div>

<div id="footer">Copyright @ lingfengbao@zju.edu.cn</div>

</body>
</html>