<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<%
	String errorMessage = (String) session.getAttribute("errorMessage");
%>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Cinemate</title>
	<link rel="stylesheet" href = "css/main.css">
<title>Cinemate</title>
</head>
<body>
	<div id="container">
		<div id="logo">
			<h1 id="header">Cinemate</h1>
			Please login.<br/>
			<br/>
		</div>
		<div id="main">
			<div id="input">
				<div class="left">
					<form name="login" method="GET" action="LoginServlet">
						Username<br/>
			           	<input type="text" name="username" class="bar"/><br/>
			           	<br/>
			           	Password<br/>
			           	<input type="password" name="password" class="bar"/><br/>
			           	<br/>
			           	<input type="submit" name="submit" value="Login" class="button floatleft"/>
					</form>
					<form name="signup" method="GET" action="SignupServlet">
			           	<input type="submit" name="submit" value="Sign Up" class="button floatright"/><br/>
			        </form>
			        <div class="clear">
			        </div>
		           	<br/>
		           	<%=errorMessage%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>