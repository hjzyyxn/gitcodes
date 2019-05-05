<%@ page language="java" contentType="text/html; charset=GB2312"%>
<%@ taglib uri="/mytaglib" prefix="mm" %>
<%@ page errorPage="errorpage.jsp" %>
<%@ page import="java.util.*" %>
<%@ include file="screendefinitions.jsp" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<title>
	<mm:insert definition="bookstore" parameter="title"/>
</title>
</head>
<body>
	<mm:insert definition="bookstore" parameter="banner"/>
	<mm:insert definition="bookstore" parameter="body"/>
</body>
</html>