package cn.zju.blf.video.servlet;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.zju.blf.video.db.QuestionnaireDBImpl;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class SubmitQuestionnaire
 */
@WebServlet("/SubmitQuestionnaire")
public class SubmitQuestionnaire extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SubmitQuestionnaire() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("enter into SubmitQuestionnaire");
		
		String answers = request.getParameter("answers");
		
		String host = request.getRemoteHost();
		System.out.println(host);
		
		JSONObject jsonObj = JSONObject.fromObject(answers);
		jsonObj.put("host", host);
		
		QuestionnaireDBImpl dbimpl = new QuestionnaireDBImpl();
		int recordId = dbimpl.insertRecord(jsonObj);
		dbimpl.close();
		
		response.setContentType("text/plain");
		response.setCharacterEncoding("UTF-8");
		response.getWriter().print(recordId);
	}

}
