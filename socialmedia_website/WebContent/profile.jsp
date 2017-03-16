<!DOCTYPE html>
<html>
<%@page import="java.util.*,client.*" %>
<%
	String errorMessage = (String) session.getAttribute("errorMessage");
	User user = (User) session.getAttribute("user");
	String name = user.getFname()+" "+user.getLname();
	String username = "@"+user.getUsername();
	String image = user.getImage();
	Vector<String> following = user.getFollowing();
	@SuppressWarnings("unchecked")
	Vector<String> followers = (Vector<String>) session.getAttribute("followers");

	@SuppressWarnings("unchecked")
	Vector<Event> activity = (Vector<Event>) session.getAttribute("activity");
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
		<div id="profileBody">
			<div id="followers">
				<div id="roundedHeader">
					Followers<br/>
				</div>
				<%for(int i=0;i<followers.size();i++){%>
					<%=followers.get(i)%><br/>
				<%}%>
			</div>
			<div id="activity">
				<div id="roundedHeader">
					Activity<br/>
				</div>
				<%for(int i=0;i<activity.size();i++){%>
					<%if(activity.get(i).getAction().equals("Rated")){%>
						<image src="<%=activity.get(i).getActionPicture()%>" class="activityratedpicture" />
					<%}else{%>
						<image src="<%=activity.get(i).getActionPicture()%>" class="activityimage" />
						<%}%>
					<div class="activityaction"><%=activity.get(i).getAction()%></div>
					<div class="activitymovie"><%=activity.get(i).getMovie()%></div>
					<div class="clear"></div>
					<br/>
				<%}%>
			</div>
			<div id="following">
				<div id="roundedHeader">
					Following<br/>
				</div>
				<%for(int i=0;i<following.size();i++){%>
					<%=following.get(i)%><br/>
				<%}%>
			</div>
		</div>
	</div>
</body>
</html>