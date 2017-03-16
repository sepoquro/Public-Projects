/**
 * 
 */
function searchChangeMovie(){
	document.getElementById("searchchangebuttoncontainer").innerHTML = '<button class="searchchangebutton" onclick="searchChangeUser()"><image src="images/clapperboard_icon.png" class="searchchangebuttonimage" /></button>';
	document.getElementById("searchbuttoncontainer").innerHTML = '<input type="image" src="images/search_icon.png" class="searchbuttonimage" name="choice" value="2. Search Movies" />';
	document.getElementById("searchbarcontainer").innerHTML = '<input type="text" name="searchInput" value="Search movies" class="searchbar" />';
}

function searchChangeUser(){
	document.getElementById("searchchangebuttoncontainer").innerHTML = '<button class="searchchangebutton" onclick="searchChangeMovie()"><image src="images/user_icon.png" class="searchchangebuttonimage" /></button>';
	document.getElementById("searchbuttoncontainer").innerHTML = '<input type="image" src="images/search_icon.png" class="searchbuttonimage" name="choice" value="1. Search Users" />';
	document.getElementById("searchbarcontainer").innerHTML = '<input type="text" name="searchInput" value="Search users" class="searchbar" />';
}