package servlets;

import java.io.IOException;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import client.Movie;

/**
 * Servlet implementation class MovieSearchServlet
 */
@WebServlet("/MovieSearchServlet")
public class MovieSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MovieSearchServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String movieSearch = (String) request.getParameter("movieSearch");
		HttpSession session = request.getSession(true);
		String movieSearchType = (String) session.getAttribute("movieSearchType");
		session.setAttribute("movieSearch",movieSearch);
		//System.out.println(movieSearch);
		//System.out.println(movieSearchType);
		
		@SuppressWarnings("unchecked")
		Vector<Movie> movieList = (Vector<Movie>) session.getAttribute("movieList");
		
		String search = movieSearch;
		search = search.toLowerCase();
		Vector<String> searchResults = new Vector<String>();
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
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
