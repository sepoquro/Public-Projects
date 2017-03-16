package client;
import java.util.*;

public class Movie {
	private String title;
	private String genre;
	private String director;
	private String image;
	private String description;
	private Vector<Actor> actors;
	private Vector<String> writers;
	private HashSet<String> actorNames;
	private HashSet<String> writerNames;
	private int year;
	private double ratingtotal;
	private int ratingcount;
	
	public Movie(String t,String dir,String img,Vector<String> w,int y,String g,String des,double rt,int rc,Vector<Actor> a){
		title = t;
		director = dir;
		image = img;
		year = y;
		genre = g;
		description = des;
		ratingtotal = rt;
		ratingcount = rc;
		
		actors = a;
		writers = w;
		
		// change vectors to sets
		actorNames = new HashSet<String>();
		writerNames = new HashSet<String>();
		for(int i=0;i<a.size();i++){
			String aname = a.get(i).getFname().toLowerCase()+" "+a.get(i).getLname().toLowerCase();
			actorNames.add(aname);
		}
		for(int i=0;i<w.size();i++){
			writerNames.add(w.get(i).toLowerCase());
		}
	}
	
	public String getTitle(){
		return title;
	}
	
	public String getGenre(){
		return genre;
	}
	
	public String getDirector(){
		return director;
	}
	
	public String getImage(){
		return image;
	}
	
	public String getDescription(){
		return description;
	}
	
	public Vector<Actor> getActors(){
		return actors;
	}
	
	public Vector<String> getWriters(){
		return writers;
	}
	
	public int getYear(){
		return year;
	}
	
	public double getRating(){
		double rating = ratingtotal/ratingcount;
		return rating;
	}
	
	public boolean hasActor(String s){
		if(actorNames.contains(s)){
			return true;
		}
		return false;
	}
	
	public boolean hasWriter(String s){
		if(writerNames.contains(s)){
			return true;
		}
		return false;
	}
	
	public void rateMovie(Double r){
		ratingtotal += r;
		ratingcount++;
	}
}
