package cn.zju.blf.video.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import cn.zju.blf.video.EventManager;
import cn.zju.blf.video.VideoMetadataManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * Servlet implementation class GetVideoData
 */
@WebServlet("/GetVideoData")
public class GetVideoData extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	Logger logger = Logger.getLogger(GetVideoData.class.getName());
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetVideoData() {
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
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String videoName = request.getParameter("video");
		logger.info("get video data: " + videoName);
		
		String logFile = VideoMetadataManager.getInstance().getLogFile(videoName);
		
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
        
        //EventManager em = new EventManager("C:/baolingfeng/research/InteractiveVideo/data/example4/log/log.db3");
        //System.out.println(this.getServletContext().getRealPath("/data/example4/log/log.db3"));
		EventManager em = new EventManager(videoName, getServletContext().getRealPath(logFile));
		
        Map<String, Object> data = new HashMap<String, Object>();
        
        //data.put("starttime", em.getStartTime());
        //data.put("events", em.summary());
        response.getWriter().print(JSONArray.fromObject(em.summary()));
        
        logger.info("finish getting video data");
	}

}
