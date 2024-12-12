<%@page import="com.demo.models.AccountModel"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" isELIgnored="false"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<%
		AccountModel accountModel = new AccountModel();
		
	%>
	
	<%= accountModel.findAll() %>
</body>
</html>