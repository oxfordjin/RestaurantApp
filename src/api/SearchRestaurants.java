package api;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;

import db.DBConnection;
import db.MySQLDBConnection;
/**
 * Servlet implementation class SearchRestaurants
 */
@WebServlet("/restaurants")
public class SearchRestaurants extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchRestaurants() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
//	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		JSONArray array = new JSONArray();
//		try {
//			if (request.getParameterMap().containsKey("user_id")
//					&& request.getParameterMap().containsKey("lat")
//					&& request.getParameterMap().containsKey("lon")) {
//				String userId = request.getParameter("user_id");
//				double lat = Double.parseDouble(request.getParameter("lat"));
//				double lon = Double.parseDouble(request.getParameter("lon"));
//				// return some fake restaurants
//				array.put(new JSONObject().put("name", "Panda Express"));
//				array.put(new JSONObject().put("name", "Hong Kong Express"));
//			}
//		} catch (JSONException e) {
//			e.printStackTrace();
//		}
//		RpcParser.writeOutput(response, array);
//
//
//	}
    
    //update on YepAPI class
    protected void doGet(HttpServletRequest request,
      	HttpServletResponse response) throws ServletException, IOException {
    		//updated on 5/2
			// allow access only if session exists
			HttpSession session = request.getSession();
			if (session.getAttribute("user") == null) {
				response.setStatus(403);
				return;
			}
			
      	    JSONArray array = new JSONArray();
	   		DBConnection connection = new MySQLDBConnection();
	   		if (request.getParameterMap().containsKey("lat")
	   				&& request.getParameterMap().containsKey("lon")) {
	   			// term is null or empty by default
	   			String term = request.getParameter("term");
	   			//String userId = (String) session.getAttribute("user");
//	            String userId = "1111";
	            String userId = request.getParameter("user_id");
	   			double lat = Double.parseDouble(request.getParameter("lat"));
	   			double lon = Double.parseDouble(request.getParameter("lon"));
	   			array = connection.searchRestaurants(userId, lat, lon, term);
	   		}
	   		RpcParser.writeOutput(response, array);
       }


	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
		
		// allow access only if session exists
		HttpSession session = request.getSession();
		if (session.getAttribute("user") == null) {
			response.setStatus(403);
			return;
		}
	}

}
