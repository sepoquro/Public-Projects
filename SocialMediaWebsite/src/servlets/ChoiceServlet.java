package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.*;

import client.*;

/**
 * Servlet implementation class ChoiceServlet
 */
@WebServlet("/ChoiceServlet")
public class ChoiceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChoiceServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choice = request.getParameter("choice");
		String searchInput = request.getParameter("searchInput");
		searchInput = searchInput.toLowerCase();
		HttpSession session = request.getSession(true);
		session.setAttribute("errorMessage","");
		Vector<String> searchResults = new Vector<String>();
		session.setAttribute("searchResults",searchResults);
		@SuppressWarnings("unchecked")
		Vector<User> userList = (Vector<User>) session.getAttribute("userList");
		@SuppressWarnings("unchecked")
		Vector<Movie> movieList = (Vector<Movie>) session.getAttribute("movieList");
		User user = (User) session.getAttribute("user");
		if(user==null){
			response.sendRedirect("login.jsp");
		}
		if(choice==null){
			response.sendRedirect("loggedin.jsp");
		} else if(choice.equals("")){
			response.sendRedirect("loggedin.jsp");
		} else if(choice.equals("1. Search Users")){
			String userSearch = searchInput;
			Vector<String> nameMatches = new Vector<String>();
			for(int i=0;i<userList.size();i++){ // search usernames
				String tempUsername = userList.get(i).getUsername();
				tempUsername = tempUsername.toLowerCase();
				if(userSearch.equals(tempUsername)){
					nameMatches.add(userList.get(i).getUsername());
				}
			}
			for(int i=0;i<userList.size();i++){ // search firstnames
				String tempFname = userList.get(i).getFname();
				tempFname = tempFname.toLowerCase();
				if(userSearch.equals(tempFname)){
					nameMatches.add(userList.get(i).getUsername());
				}
			}
			for(int i=0;i<userList.size();i++){ // search lastnames
				String tempLname = userList.get(i).getLname();
				tempLname = tempLname.toLowerCase();
				if(userSearch.equals(tempLname)){
					nameMatches.add(userList.get(i).getUsername());
				}
			}
			session.setAttribute("searchResults",nameMatches);
			response.sendRedirect("userSearch.jsp");
		} else if(choice.equals("2. Search Movies")){
			String movieSearchType = searchInput.split(":", 2)[0];
			if(movieSearchType.equals("actor")) {
				session.setAttribute("movieSearchType","actor");
			} else if(movieSearchType.equals("title")){
				session.setAttribute("movieSearchType","title");
			} else if(movieSearchType.equals("genre")){
				session.setAttribute("movieSearchType","genre");
			} else {
				session.setAttribute("movieSearchType","title"); // if user enters invalid search category assume they are searching for movie title
			}
			String search = "";
			try{
				 search = searchInput.split(":", 2)[1];
				if(search.charAt(0)==' '){
					search = searchInput.split(" ", 2)[1];
				}
			} catch(Exception e) {
				
			}
			search = search.toLowerCase();
			if(movieSearchType.equals("actor")){
				for(int i=0;i<movieList.size();i++){
					if(movieList.get(i).hasActor(search)){
						searchResults.add(movieList.get(i).getTitle());
					}
				}
				
				session.setAttribute("searchResults",searchResults);
			} else if(movieSearchType.equals("title")){
				for(int i=0;i<movieList.size();i++){
					String tempTitle = movieList.get(i).getTitle();
					tempTitle = tempTitle.toLowerCase();
					if(search.equals(tempTitle)){
						searchResults.add(movieList.get(i).getTitle());
					}
				}
				
				session.setAttribute("searchResults",searchResults);
			} else if(movieSearchType.equals("genre")){
				for(int i=0;i<movieList.size();i++){
					String tempGenre = movieList.get(i).getGenre();
					tempGenre = tempGenre.toLowerCase();
					if(search.equals(tempGenre)){
						searchResults.add(movieList.get(i).getTitle());
					}
				}
				
				session.setAttribute("searchResults",searchResults);
			}
			response.sendRedirect("movieSearch.jsp");
		} else if(choice.equals("3. View Feed")){
			Vector<Event> feedMessages = new Vector<Event>();
			for(int i=0;i<user.getFollowing().size();i++){
				User tempUser = null;
				for(int j=0;j<userList.size();j++){
					if(userList.get(j).getUsername().equals(user.getFollowing().get(i))){
						tempUser = userList.get(j);
					}
				}
				if(tempUser!=null){
					for(int j=0;j<tempUser.getFeed().size();j++){
						String action = tempUser.getFeed().get(j).getAction();
						String movie = tempUser.getFeed().get(j).getMovie();
						Double rating = tempUser.getFeed().get(j).getRating();
						Event e = new Event(action,movie,rating);
						for(int k=0;k<movieList.size();k++){
							if(movie.equals(movieList.get(k).getTitle())){
								e.setMovieObject(movieList.get(k));
							}
						}
						e.setUser(tempUser);
						feedMessages.add(e);
					}
				}
			}
			session.setAttribute("feedMessages",feedMessages);
			
			response.sendRedirect("feed.jsp");
		} else if(choice.equals("4. View Profile")){
			Vector<Event> activity = new Vector<Event>();
			for(int i=0;i<user.getFeed().size();i++){
				String action = user.getFeed().get(i).getAction();
				String movie = user.getFeed().get(i).getMovie();
				Double rating = user.getFeed().get(i).getRating();
				Event e = new Event(action,movie,rating);
				activity.add(e);
			}
			session.setAttribute("activity",activity);
			
			Vector<String> followers = new Vector<String>();
			for(int i=0;i<userList.size();i++){
				for(int j=0;j<userList.get(i).getFollowing().size();j++){
					if(user.getUsername().equals(userList.get(i).getFollowing().get(j))){
						followers.add(userList.get(i).getUsername());
					}
				}
			}
			session.setAttribute("followers",followers);
			
			response.sendRedirect("profile.jsp");
		} else if(choice.equals("5. Logout")){
			session.setAttribute("user", null);
			response.sendRedirect("login.jsp");
		} else if(choice.equals("6. Exit")){
			response.sendRedirect("parser.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
