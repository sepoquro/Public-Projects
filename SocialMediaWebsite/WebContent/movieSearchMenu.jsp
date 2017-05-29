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
			<br/>
		</div>
		<div id="main">
			<div id="input">
				<div class="left">
					<form name="movieSearchMenu" method="GET" action="MovieSearchMenuServlet">
			           	<input type="submit" name="choice" value="1. Search by Actor" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="2. Search by Title" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="3. Search by Genre" class="clickable"/><br/>
			           	<input type="submit" name="choice" value="4. Back to Login Menu" class="clickable"/><br/>
			           	<br/>
			           	<br/>
					</form>
				</div>
			</div>
		</div>
	</div>
</body>
</html>