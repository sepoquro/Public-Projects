<!DOCTYPE html>
<html>
<%@page import="java.util.*,client.*" %>
<%
	String errorMessage = (String) session.getAttribute("errorMessage");
	Movie movie = (Movie) session.getAttribute("movieClickedObject");

	User user = (User) session.getAttribute("user");
	String name = user.getFname()+" "+user.getLname();
	String username = "@"+user.getUsername();
	String image = user.getImage();
	
	Vector<Actor> actors = movie.getActors();
	
	String movieImage = movie.getImage();
	String movieName = movie.getTitle()+" ("+movie.getYear()+")";
	String movieGenre = "Genre: " + movie.getGenre();
	String movieDirector = "Director: " + movie.getDirector();
	
	String movieActors = "Actors: ";
	for(int i=0;i<movie.getActors().size();i++){
		movieActors += movie.getActors().get(i).getName();
		if(i!=movie.getActors().size()-1){
			movieActors += ", ";
		}
	}
	
	String movieWriters = "Writers: ";
	for(int i=0;i<movie.getWriters().size();i++){
		movieWriters += movie.getWriters().get(i);
		if(i!=movie.getWriters().size()-1){
			movieWriters += ", ";
		}
	}
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
			<div class="movieimage">
				<img src="<%=movieImage%>" class="movieimage" />
			</div>
			<div class="moviename">
				<%=movieName%>
			</div><br/>
			<div class="movieinfo">
				<%=movieGenre%><br/>
				<%=movieDirector%><br/>
				<%=movieActors%><br/>
				<%=movieWriters%><br/>
			</div>
			<div class="movierating">
				**********<br/>
			</div>
			<div class="moviedescription">
				<%=movie.getDescription()%><br/>
			</div>
			<div class="clear">
			</div>
		</div>
		<div id="profileBody">
			<div class="cast">
				<div id="roundedHeader">
					Cast<br/>
				</div><br/>
				<%for(int i=0;i<actors.size();i++){%>
					<image src="<%=actors.get(i).getImage()%>" class="actorimage" />
					<div class="actorname"><%=actors.get(i).getName()%></div>
					<div class="clear"></div>
					<br/>
				<%}%>
			</div>
		</div>
	</div>
</body>
</html>