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
 * Servlet implementation class MoviePageServlet
 */
@WebServlet("/MoviePageServlet")
public class MoviePageServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MoviePageServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String movieClicked = request.getParameter("movieClicked");
		HttpSession session = request.getSession(true);
		User user = (User) session.getAttribute("user");
		
		@SuppressWarnings("unchecked")
		Vector<Movie> movieList = (Vector<Movie>) session.getAttribute("movieList");
		
		Movie movieClickedObject = null;
		for(int i=0;i<movieList.size();i++){
			if(movieList.get(i).getTitle().equals(movieClicked)){
				movieClickedObject = movieList.get(i);
			}
		}
		session.setAttribute("movieClickedObject",movieClickedObject);
		
		response.sendRedirect("MoviePage.jsp");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
