package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.File;
import java.util.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.*;

import client.*;

/**
 * Servlet implementation class ParseServlet
 */
@WebServlet("/ParseServlet")
public class ParseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    /**
     * Default constructor. 
     */
    public ParseServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		boolean loadFail = false;
		
		//System.out.println("Inside ParseServlet.java");
		String inputFile = request.getParameter("inputFile");
		HttpSession session = request.getSession(true);
		session.setAttribute("inputFile",inputFile);
		
		String filename = (String) session.getAttribute("inputFile");

		Vector<Movie> movieList = new Vector<Movie>();
		Vector<User> userList = new Vector<User>();
		Vector<String> genres = new Vector<String>();
		Vector<String> actions = new Vector<String>();
		boolean invalidInput = false;
		String errorMessage = "";
		try{
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder(); 
			Document doc = db.parse(new File(filename));
			
			doc.getDocumentElement().normalize();
			
			// get list of movie genres
			NodeList nlgenre = doc.getElementsByTagName("genres");
			Element egenre = (Element) nlgenre.item(0);
			for (int i=0;i<egenre.getElementsByTagName("genre").getLength();i++){
				Node node = nlgenre.item(0);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					genres.add(element.getElementsByTagName("genre").item(i).getTextContent());
					//System.out.println(genres.get(i));
				}
			}
			
			// get list of actions
			NodeList nlaction = doc.getElementsByTagName("actions");
			Element eaction = (Element) nlaction.item(0);
			for (int i=0;i<eaction.getElementsByTagName("action").getLength();i++){
				Node node = nlaction.item(0);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					actions.add(element.getElementsByTagName("action").item(i).getTextContent());
					//System.out.println(actions.get(i));
				}
			}
			
			// get list of movies
			NodeList nlmovie = doc.getElementsByTagName("movie");
			NodeList nlmovies = doc.getElementsByTagName("movies");
			Element emovies = (Element) nlmovies.item(0);
			//System.out.println(emovies.getElementsByTagName("movie").getLength());
			// error checking
			if(emovies.getElementsByTagName("movie").getLength()==0){
				errorMessage = "Error: No movies detected.";
				invalidInput = true;
			}
			for (int i=0;i<emovies.getElementsByTagName("movie").getLength();i++){
				//System.out.println(i);
				Node node = nlmovie.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String t = element.getElementsByTagName("title").item(0).getTextContent();
					String dir = element.getElementsByTagName("director").item(0).getTextContent();
					String img = element.getElementsByTagName("image").item(0).getTextContent();
					Vector<String> w = new Vector<String>();
					NodeList nlwriter = doc.getElementsByTagName("writers");
					Element ewriter = (Element) nlwriter.item(i);
					//System.out.println(ewriter.getElementsByTagName("writer").getLength());
					for (int j=0;j<ewriter.getElementsByTagName("writer").getLength();j++){
						Node wnode = nlwriter.item(i);
						if (wnode.getNodeType() == Node.ELEMENT_NODE) {
							Element welement = (Element) wnode;
							w.add(welement.getElementsByTagName("writer").item(j).getTextContent());
							//System.out.println(w.get(j));
						}
					}
					String ystring = element.getElementsByTagName("year").item(0).getTextContent();
					int y = 0;
					if(ystring!=""){
						// error checking
						try{
							y = Integer.parseInt(ystring);
						} catch(NumberFormatException e){
							errorMessage = "Error: Movie has string as year.";
							invalidInput = true;
						}
						//System.out.println(y);
					}
					String g = element.getElementsByTagName("genre").item(0).getTextContent();
					// error checking
					boolean genreExists = false;
					for(int j=0;j<genres.size();j++){
						if(genres.get(j).equals(g)){
							genreExists = true;
						}
					}
					if(!genreExists){
						errorMessage = "Error: Movie has a nonexistent genre.";
						invalidInput = true;
					}
					String des = element.getElementsByTagName("description").item(0).getTextContent();
					String rtstring = element.getElementsByTagName("rating-total").item(0).getTextContent();
					String rcstring = element.getElementsByTagName("rating-count").item(0).getTextContent();
					double rt = 0;
					if(rtstring!=""){
						rt = Double.parseDouble(rtstring);
					}
					int rc = 0;
					if(rcstring!=""){
						rc = Integer.parseInt(rcstring);
					}
					Vector<Actor> a = new Vector<Actor>();
					NodeList nlactor = doc.getElementsByTagName("actors");
					Element eactor = (Element) nlactor.item(i);
					for (int j=0;j<eactor.getElementsByTagName("actor").getLength();j++){
						Node anode = nlactor.item(i);
						if (anode.getNodeType() == Node.ELEMENT_NODE) {
							Element aelement = (Element) anode;
							String f = aelement.getElementsByTagName("fname").item(j).getTextContent();
							String l = aelement.getElementsByTagName("lname").item(j).getTextContent();
							String image = aelement.getElementsByTagName("image").item(j).getTextContent();
							Actor tempActor = new Actor(f,l,image);
							a.add(tempActor);
							//System.out.println(a.get(j));
						}
					}
					//System.out.println("");
					Movie tempMovie = new Movie(t,dir,img,w,y,g,des,rt,rc,a);
					movieList.add(tempMovie);
				}
			}
			
			// get list of users
			NodeList nluser = doc.getElementsByTagName("user");
			NodeList nlusers = doc.getElementsByTagName("users");
			Element eusers = (Element) nlusers.item(0);
			for (int i=0;i<eusers.getElementsByTagName("user").getLength();i++){
				Node node = nluser.item(i);
				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;
					String u = element.getElementsByTagName("username").item(0).getTextContent();
					String p = element.getElementsByTagName("password").item(0).getTextContent();
					String f = element.getElementsByTagName("fname").item(0).getTextContent();
					String l = element.getElementsByTagName("lname").item(0).getTextContent();
					String img = element.getElementsByTagName("image").item(0).getTextContent();
					Vector<String> fo = new Vector<String>();
					NodeList nlfollowing = doc.getElementsByTagName("following");
					Element efollowing = (Element) nlfollowing.item(i);
					for (int j=0;j<efollowing.getElementsByTagName("username").getLength();j++){
						Node fonode = nlfollowing.item(i);
						if (fonode.getNodeType() == Node.ELEMENT_NODE) {
							Element foelement = (Element) fonode;
							fo.add(foelement.getElementsByTagName("username").item(j).getTextContent());
						}
					}
					Vector<Event> fe = new Vector<Event>();
					NodeList nlfeed = doc.getElementsByTagName("feed");
					Element efeed = (Element) nlfeed.item(i);
					for (int j=0;j<efeed.getElementsByTagName("event").getLength();j++){
						Node fenode = nlfeed.item(i);
						if (fenode.getNodeType() == Node.ELEMENT_NODE) {
							Element feelement = (Element) fenode;
							String afeed=feelement.getElementsByTagName("action").item(j).getTextContent();
							String mfeed=feelement.getElementsByTagName("movie").item(j).getTextContent();
							String rstring=element.getElementsByTagName("rating").item(j).getTextContent();
							// error checking
							if(!afeed.equals("Rated")&&!rstring.equals("")){
								//System.out.println(afeed+mfeed+rstring);
								errorMessage = "Error: Event not rated but has rating.";
								invalidInput = true;
							}
							if(afeed.equals("Rated")&&rstring.equals("")){
								System.out.println("Error: Event rated but no rating.");
								invalidInput = true;
							}
							boolean actionExists = false;
							for(int k=0;k<actions.size();k++){
								if(actions.get(k).equals(afeed)){
									actionExists = true;
								}
							}
							if(!actionExists){
								errorMessage = "Error: Event has nonexistent action.";
								invalidInput = true;
							}
							
							double rfeed = 0;
							if(!rstring.equals("")){
								// error checking
								try{
									rfeed = Double.parseDouble(rstring);
								} catch(NumberFormatException e){
									errorMessage = "Error: Event has string as rating.";
									invalidInput = true;
								}
							}
							Event tempEvent = new Event(afeed,mfeed,rfeed);
							fe.add(tempEvent);
						}
					}
					User tempUser = new User(u,p,f,l,img,fo,fe);
					userList.add(tempUser);
				}
			}
			// error checking
			for(int i=0;i<userList.size();i++){
				for(int j=0;j<userList.get(i).getFollowing().size();j++){
					boolean followingExists = false;
					for(int k=0;k<userList.size();k++){
						if(userList.get(i).getFollowing().get(j).equals(userList.get(k).getUsername())){
							followingExists = true;
						}
					}
					if(!followingExists){
						errorMessage = "Error: Following a nonexistent user.";
						invalidInput = true;
					}
				}
			}
			session.setAttribute("movieList",movieList);
			session.setAttribute("userList",userList);
			session.setAttribute("genres",genres);
			session.setAttribute("actions",actions);
			session.setAttribute("errorMessage",errorMessage);
		} catch(Exception e){
			e.printStackTrace();
			loadFail = true;
		}
		
		if(invalidInput||loadFail){
			session.setAttribute("errorMessage","Parsing failed!");
			response.sendRedirect("parser.jsp");
		} else{
			session.setAttribute("errorMessage","");
			response.sendRedirect("login.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
