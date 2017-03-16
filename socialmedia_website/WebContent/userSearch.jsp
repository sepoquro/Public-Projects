<!DOCTYPE html>
<html>
<%@page import="java.util.*,client.*" %>
<%
	String errorMessage = (String) session.getAttribute("errorMessage");
	@SuppressWarnings("unchecked")
	Vector<String> searchResults = (Vector<String>) session.getAttribute("searchResults");
	
	User user = (User) session.getAttribute("user");
	String image = user.getImage();
%>
<head>
	<meta charset="ISO-8859-1">
	<title>Cinemate</title>
	<link rel="stylesheet" href = "css/main.css">
	<script src="js/searchChangeButton.js"></script>
</head>
<body>
	<div id="container">
		<div id="logo">
			<div class="searchheader">
				<form name="feedprofilebuttonform" method="GET" action="ChoiceServlet">
					<div id="searchbarcontainer">
						<input type="text" name="searchInput" value="Search movies" class="searchbar" />
					</div>
					<div id="searchbuttoncontainer">
						<input type="image" src="images/search_icon.png" class="searchbuttonimage" name="choice" value="2. Search Movies" />
					</div>
					<div id="searchchangebuttoncontainer">
						<button class="searchchangebutton" onclick="searchChangeUser()">
							<image src="images/clapperboard_icon.png" class="searchchangebuttonimage" />
						</button>
					</div>
					<div class="feedbuttoncontainer">
						<input type="image" src="images/feed_icon.png" class="feedbuttonimage" name="choice" value="3. View Feed" />
					</div>
					<div class="profilebuttoncontainer">
						<input type="image" src="<%=image%>" class="smallcircleImage" name="choice" value="4. View Profile" />
					</div>
					<div class="feedheadertext">Cinemate</div>
					<div class="logoutbuttoncontainer">
						<input type="image" src="images/logout_icon.png" class="logoutbuttonimage" name="choice" value="5. Logout" />
					</div>
					<div class="exitbuttoncontainer">
						<input type="image" src="images/exit_icon.jpg" class="exitbuttonimage" name="choice" value="6. Exit" />
					</div>
				</form>
			</div>
			<br/>
		</div>
		<div id="main">
			<div id="input">
				<div class="searchBody">
		           	<br/>
		           	<div id="roundedHeader2">
		           		Search Results
		           	</div>
		           	<div class="results">
		           		<form name="searchuserbuttonform" method="GET" action="UserPageServlet">
				           	<%for(int i=0;i<searchResults.size();i++){%>
								<p><input type="submit" name="userClicked" value="<%=searchResults.get(i)%>" class="noborderbutton" /></p>
							<%}%>
						</form>
					</div>
		           	<%=errorMessage%>
				</div>
			</div>
		</div>
	</div>
</body>
</html>