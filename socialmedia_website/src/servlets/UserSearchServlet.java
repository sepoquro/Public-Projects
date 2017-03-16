package servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.User;

/**
 * Servlet implementation class UserSearchServlet
 */
@WebServlet("/UserSearchServlet")
public class UserSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String userSearch = request.getParameter("userSearch");
		HttpSession session = request.getSession(true);
		session.setAttribute("userSearch",userSearch);
		//System.out.println(userSearch);
		
		@SuppressWarnings("unchecked")
		Vector<User> userList = (Vector<User>) session.getAttribute("userList");
		
		userSearch = userSearch.toLowerCase();
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
