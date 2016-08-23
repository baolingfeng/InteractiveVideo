package cn.zju.blf.video.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zju.blf.video.GlobalSetting;

/**
 * Servlet implementation class PortalServlet
 */
@WebServlet("/PortalServlet")
public class PortalServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PortalServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String video = request.getParameter("name");
		String group = request.getParameter("group");
		
		boolean toQustionnaire = GlobalSetting.getInstance().IS_QUESTIONNAIRE;
		
		if(toQustionnaire)
		{
			response.sendRedirect("/VTRevolution/questionnaire.jsp?name=" + video + "&group=" + group);
		}
		else
		{
			if("1".equals(group))
			{
				response.sendRedirect("/VTRevolution/video.jsp?name=" + video);
			}
			else
			{
				response.sendRedirect("/VTRevolution/video2.jsp?name=" + video);
			}
			
		}
	}

}
