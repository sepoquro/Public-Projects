<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Cinemate</title>
	<link rel="stylesheet" href = "css/main.css">
</head>
<body>
	<div id="container">
		<div id="logo">
			<h1 id="header">Cinemate</h1>
			Logged in! What would you like to do?<br/>
			<br/>
		</div>
		<div id="main">
			<div id="input">
				<div class="left">
					<form name="choice" method="GET" action="ChoiceServlet">
			           	<input type="submit" name="choice" value="1. Search Users" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="2. Search Movies" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="3. View Feed" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="4. View Profile" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="5. Logout" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="6. Exit" class="clickable"/><br/>
			           	<br/>
			           	<br/>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>