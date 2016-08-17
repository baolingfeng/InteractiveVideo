package cn.zju.blf.video.servlet;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import cn.zju.blf.video.util.TimeUtil;

/**
 * Servlet implementation class QuestionnaireSession
 */
@WebServlet("/QuestionnaireSession")
public class QuestionnaireSession extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public QuestionnaireSession() {
        super();
        // TODO Auto-generated constructor stub
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
		
		String type = request.getParameter("type");
		String video = request.getParameter("video");
		System.out.println(type + "/" + video);
		HttpSession session = request.getSession();
		
		if("get".equals(type))
		{
			String starttime = (String)session.getAttribute(video + "starttime");
			if(starttime == null)
			{
				starttime = "";
			}
			
			//session.setAttribute("starttime", TimeUtil.timeToStr(new Date(), "yyyy-MM-dd'T'HH:mm:ss"));
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			
			response.getWriter().print(starttime);
		}
		else if("set".equals(type))
		{
			String starttime = request.getParameter("starttime");
			
			session.setAttribute(video + "starttime", starttime);
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("OK");
		}
		else  if("reset".equals(type))
		{
			session.removeAttribute(video+"starttime");
			
			response.setContentType("text/plain");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().print("OK");
		}
		
	}

}
