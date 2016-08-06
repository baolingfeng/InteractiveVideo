package cn.zju.blf.video.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class ViewVideo
 */
@WebServlet("/ViewVideo")
public class ViewVideo extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ViewVideo() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String videoName = request.getParameter("name");
		String group = request.getParameter("group");
		
		if("1".equals(group))
		{
			response.sendRedirect("/InteractiveVideo/video.jsp?name="+videoName);
		}
		else
		{
			response.sendRedirect("/InteractiveVideo/video2.jsp?name="+videoName);
		}
		
	}

}
