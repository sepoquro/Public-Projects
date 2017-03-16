package client;
import java.util.*;

public class User {
	private String username;
	private String password;
	private String fname;
	private String lname;
	private String image;
	private Vector<String> following;
	private Vector<Event> feed;
	
	public User(String u,String p,String f,String l,String img,Vector<String> fo,Vector<Event> fe){
		username = u;
		password = p;
		fname = f;
		lname = l;
		image = img;
		following = fo;
		feed = fe;
	}
	
	public String getUsername(){
		return username;
	}
	
	public String getPassword(){
		return password;
	}
	
	public String getFname(){
		return fname;
	}
	
	public String getLname(){
		return lname;
	}
	
	public String getImage(){
		return image;
	}
	
	public String getPartialPassword(){
		String partialPassword = "";
		for(int i=0;i<password.length();i++){
			if(i==0||i==password.length()-1){
				partialPassword += password.charAt(i);
			} else{
				partialPassword += '*';
			}
		}
		return partialPassword;
	}
	
	public Vector<String> getFollowing(){
		return following;
	}
	
	public Vector<Event> getFeed(){
		return feed;
	}
}
