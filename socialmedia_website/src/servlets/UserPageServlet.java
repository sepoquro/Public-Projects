package servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.*;

/**
 * Servlet implementation class UserPageServlet
 */
@WebServlet("/UserPageServlet")
public class UserPageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserPageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userClicked = request.getParameter("userClicked");
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");

		@SuppressWarnings("unchecked")
		Vector<User> userList = (Vector<User>) session.getAttribute("userList");
		
		User userClickedObject = null;
		for(int i=0;i<userList.size();i++){
			if(userList.get(i).getUsername().equals(userClicked)){
				userClickedObject = userList.get(i);
			}
		}
		session.setAttribute("userClickedObject",userClickedObject);
		
		Vector<Event> activity = new Vector<Event>();
		for(int i=0;i<userClickedObject.getFeed().size();i++){
			String action = userClickedObject.getFeed().get(i).getAction();
			String movie = userClickedObject.getFeed().get(i).getMovie();
			Double rating = userClickedObject.getFeed().get(i).getRating();
			Event e = new Event(action,movie,rating);
			activity.add(e);
		}
		session.setAttribute("activity",activity);
		
		Vector<String> followers = new Vector<String>();
		for(int i=0;i<userList.size();i++){
			for(int j=0;j<userList.get(i).getFollowing().size();j++){
				if(userClickedObject.getUsername().equals(userList.get(i).getFollowing().get(j))){
					followers.add(userList.get(i).getUsername());
				}
			}
		}
		session.setAttribute("followers",followers);
		
		response.sendRedirect("UserPage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
