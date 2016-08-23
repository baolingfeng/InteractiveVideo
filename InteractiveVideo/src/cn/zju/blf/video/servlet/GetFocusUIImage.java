package cn.zju.blf.video.servlet;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Base64;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

/**
 * Servlet implementation class GetFocusUIImage
 */
@WebServlet("/GetFocusUIImage")
public class GetFocusUIImage extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
	private Logger logger = Logger.getLogger(GetFocusUIImage.class);
	
	public static final String IMAGE_FOLDER = "C:/baolingfeng/research/VTRevolution/data/example4/log/screen/";
	
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetFocusUIImage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String timestamp = request.getParameter("timestamp");
		
		String boundLeft = request.getParameter("boundLeft");
		String boundTop = request.getParameter("boundTop");
		String boundRight = request.getParameter("boundRight");
		String boundBottom = request.getParameter("boundBottom");
		
		timestamp = timestamp.replaceAll(" ", "-");
		timestamp = timestamp.replaceAll(":", "-");
		timestamp = timestamp.replaceAll("\\.", "-");
		System.out.println(timestamp + "/" + boundLeft + ", " + boundTop + ", " + boundRight + ", " + boundBottom + ", "); 
		
		String imagepath = this.getServletContext().getRealPath("/data/example4/log/screen/" + timestamp + ".png");
		System.out.println(imagepath);
		
		BufferedImage image = null;
		try
		{
			image = ImageIO.read(new File(imagepath));
			
			ByteArrayOutputStream out = new ByteArrayOutputStream();
			ImageIO.write( image, "png", out );
			out.flush();
			byte[] bytes = out.toByteArray();
			out.close();
			//String b64 = javax.xml.bind.DatatypeConverter.printBase64Binary(imageInByteArray);
			String base64bytes = Base64.getEncoder().encodeToString(bytes);
			
			//System.out.println(base64bytes);
			
			response.setContentType("text/plain");
			response.getOutputStream().print("data:image/png;base64," + base64bytes);
		}catch(Exception e)
		{
			logger.info(e.getMessage());
		}
		
		//ImageIO.write(image, "png", response.getOutputStream() );
	}

}
