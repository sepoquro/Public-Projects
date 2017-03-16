package servlets;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MovieSearchMenuServlet
 */
@WebServlet("/MovieSearchMenuServlet")
public class MovieSearchMenuServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieSearchMenuServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String choice = request.getParameter("choice");
		HttpSession session = request.getSession(true);
		session.setAttribute("errorMessage","");
		session.setAttribute("movieSearchType","");
		if(choice==null){
			response.sendRedirect("movieSearchMenu.jsp");
		} else if(choice.equals("")){
			response.sendRedirect("movieSearchMenu.jsp");
		} else if(choice.equals("1. Search by Actor")){
			session.setAttribute("movieSearchType","actor");
			response.sendRedirect("movieSearch.jsp");
		} else if(choice.equals("2. Search by Title")){
			session.setAttribute("movieSearchType","title");
			response.sendRedirect("movieSearch.jsp");
		} else if(choice.equals("3. Search by Genre")){
			session.setAttribute("movieSearchType","genre");
			response.sendRedirect("movieSearch.jsp");
		} else if(choice.equals("4. Back to Login Menu")){
			response.sendRedirect("loggedin.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
