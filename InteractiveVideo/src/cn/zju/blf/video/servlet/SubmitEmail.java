package cn.zju.blf.video.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zju.blf.video.db.QuestionnaireDBImpl;

/**
 * Servlet implementation class SubmitEmail
 */
@WebServlet("/SubmitEmail")
public class SubmitEmail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitEmail() {
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
		String recordId = request.getParameter("recordId");
		String email = request.getParameter("email");
		
		System.out.println("email submit: " + recordId + "/" + email); 
		
		QuestionnaireDBImpl dbimpl = new QuestionnaireDBImpl();
		dbimpl.updateEmail(recordId, email);
		dbimpl.close();
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print("OK");
	}

}
