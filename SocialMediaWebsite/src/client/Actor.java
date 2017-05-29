package client;

public class Actor {
	private String fname;
	private String lname;
	private String image;
	
	public Actor(String f, String l, String img){
		fname = f;
		lname = l;
		image = img;
	}
	
	public String getFname(){
		return fname;
	}
	
	public String getLname(){
		return lname;
	}
	
	public String getName(){
		return fname + " " + lname;
	}
	
	public String getImage(){
		return image;
	}
}
