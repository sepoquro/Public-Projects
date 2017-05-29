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
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		HttpSession session = request.getSession(true);
		String errorMessage = "";
		@SuppressWarnings("unchecked")
		Vector<User> userList = (Vector<User>) session.getAttribute("userList");
		@SuppressWarnings("unchecked")
		Vector<Movie> movieList = (Vector<Movie>) session.getAttribute("movieList");
		boolean validName = false;
		boolean correctPassword = false;
		User user = null;
		int userId = -1;
		for(int i=0;i<userList.size();i++){
			if(username.equals(userList.get(i).getUsername())){
				validName = true;
				userId = i;
			}
		}
		if(userId!=-1){
			if(password.equals(userList.get(userId).getPassword())){
				correctPassword = true;
				user = userList.get(userId);
			} else{
				errorMessage = "Error: password is incorrect!";
			}
		} else{
			errorMessage = "Error: username does not exist!";
		}
		session.setAttribute("errorMessage",errorMessage);
		if(validName&&correctPassword){
			session.setAttribute("user", user);
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
		} else{
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
