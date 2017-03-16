package client;

public class Event {
	private String action;
	private String movie;
	private double rating;
	
	private Movie movieObject;
	private User user;
	
	public Event(String a, String m, Double r){
		action = a;
		movie = m;
		rating = r;
	}
	
	public void setUser(User u){
		user = u;
	}
	
	public User getUser(){
		return user;
	}
	
	public void setMovieObject(Movie m){
		movieObject = m;
	}
	
	public Movie getMovieObject(){
		return movieObject;
	}
	
	public String getMoviePicture(){
		return movieObject.getImage();
	}
	
	public String getAction(){
		return action;
	}
	
	public String getMovie(){
		return movie;
	}
	
	public Double getRating(){
		return rating;
	}
	
	public String getActionPicture(){
		if(action.equalsIgnoreCase("Liked")){
			return "images/liked.png";
		} else if(action.equals("Disliked")){
			return "images/disliked.png";
		} else if(action.equals("Watched")){
			return "images/watched.png";
		} else if(action.equals("Rated")){
			if(rating<1.0){
				return "images/rating0.png";
			} else if(rating<3.0){
				return "images/rating1.png";
			} else if(rating<5.0){
				return "images/rating2.png";
			} else if(rating<7.0){
				return "images/rating3.png";
			} else if(rating<9.0){
				return "images/rating4.png";
			} else{
				return "images/rating5.png";
			}
		} else{
			return "";
		}
	}
}
