<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>login</title>
</head>
<body onload='document.f.username.focus();'>
	<h1>custome login</h1>
	<h3>Login with Username and Password</h3><form name='f' action='/login' method='POST'>
	<table>
		<tr><td>User:</td><td><input type='text' name='username' value=''></td></tr>
		<tr><td>Password:</td><td><input type='password' name='password'/></td></tr>
		<tr><td><input type='checkbox' name='remember-me'/></td><td>Remember me on this computer.</td></tr>
		<tr><td colspan='2'><input name="submit" type="submit" value="Login"/></td></tr>
	</table>
</body>
</html>