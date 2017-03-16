<!DOCTYPE html>
<html>
<%@page import="java.util.*,client.*" %>
<%
	String errorMessage = (String) session.getAttribute("errorMessage");
	User user = (User) session.getAttribute("user");
	String name = user.getFname()+" "+user.getLname();
	String username = "@"+user.getUsername();
	String image = user.getImage();
	Vector<Event> feedMessages = (Vector<Event>) session.getAttribute("feedMessages");
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
		<div id="profileHeader">
			<div class="circleImage">
				<img src="<%=image%>" />
			</div>
			<div class="name">
				<%=name%>
			</div><br/>
			<div class="username">
				<%=username%>
			</div>
		</div>
		<div id="feed">
			<div id="roundedHeader">
				Feed
			</div><br/>
			<div class="feedcontainer">
			<%for(int i=0;i<feedMessages.size();i++){%>
				<form name="feeduserbuttonform" method="GET" action="UserPageServlet">
					<div class="feedprofilebuttoncontainer">
						<input type="image" src="<%=feedMessages.get(i).getUser().getImage()%>" class="circleImageFeed" name="userClicked" value="<%=feedMessages.get(i).getUser().getUsername()%>" />
					</div>
				</form>
					<%if(feedMessages.get(i).getAction().equals("Rated")){%>
						<div class="feedactionpicturecontainer">
							<image src="<%=feedMessages.get(i).getActionPicture()%>" class="feedratedpicture" />
						</div>
					<%}else{%>
						<div class="feedactionpicturecontainer">
							<image src="<%=feedMessages.get(i).getActionPicture()%>" class="feedactionpicture" />
						</div>
					<%}%>
				<form name="feedmoviebuttonform" method="GET" action="MoviePageServlet">
					<div class="feedmoviepicturecontainer">
						<input type="image" src="<%=feedMessages.get(i).getMoviePicture()%>" class="feedmoviepicture" name="movieClicked" value="<%=feedMessages.get(i).getMovieObject().getTitle()%>" />
					</div>
					<div class="clear">
					</div>
					<br/><br/>
				</form>
			<%}%>
			</div>
		</div>
	</div>
</body>
</html>