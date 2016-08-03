package cn.zju.blf.video.servlet;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.zju.blf.video.code.MyJavaDoc;
import cn.zju.blf.video.db.JavaDocDBImpl;
import cn.zju.blf.video.db.MySQLDBManager;
import net.sf.json.JSONArray;

/**
 * Servlet implementation class GetJavadoc
 */
@WebServlet("/GetJavadoc")
public class GetJavadoc extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Logger logger = Logger.getLogger(GetJavadoc.class);
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetJavadoc() {
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
		String className = request.getParameter("class");
		String method = request.getParameter("method");
		String flag = request.getParameter("flag");
		
		logger.info("api:" + className + '/' + method + '/' + flag);
		
		JavaDocDBImpl db = new JavaDocDBImpl();
		
		List<MyJavaDoc> list = null;
		if(className != null && !"".equals(className))
		{
			if(method != null && !"".equals(method.trim()))
			{
				list = db.getJavadoc(className, method);
			}
			else if(flag == null || "false".equalsIgnoreCase(flag))
			{
				list = db.getJavadoc(className);
			}
			else{
				list = db.getJavaDocWithQuery(className);
			}
		}
		else if(method != null && !"".equals(method))
		{
			list = db.getJavadocByMethod(method);
		}
		
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		if(list.size() > 5)
		{
			response.getWriter().print(JSONArray.fromObject(list.subList(0, 5)));
		}
		else{
			response.getWriter().print(JSONArray.fromObject(list));
		}
	}
	

}
