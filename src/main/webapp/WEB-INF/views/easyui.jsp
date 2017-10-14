<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ include file="/commons/global.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
 <%@ include file="/commons/basejs.jsp" %>
    <link rel="stylesheet" type="text/css" href="${staticPath }/static/style/css/login.css?v=201612202107" />
    <script type="text/javascript" src="${staticPath }/static/login.js?v=20170115" charset="utf-8"></script>
</head>
<body>
	<div class="easyui-layout" style="width:400px;height:200px;">
	<div region="west" split="true" title="Navigator" style="width:150px;">
		<p style="padding:5px;margin:0;">Select language:</p>
		<ul>
			<li><a href="javascript:void(0)" onclick="showcontent('java')">Java</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('cshape')">C#</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('vb')">VB</a></li>
			<li><a href="javascript:void(0)" onclick="showcontent('erlang')">Erlang</a></li>
		</ul>
	</div>
	<div id="content" region="center" title="Language" style="padding:5px;">
	</div>
</div>
</body>
</html>