package com.wikestudy.servlet.publicpart;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Icon;
import com.wikestudy.model.util.IconToot;


@WebServlet("/system_icon_get")
public class GetSystemIcon extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
    public GetSystemIcon() {
        super();
       
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//第一步：	获取文件名
		String filename = request.getParameter("filename");
		
		
		//第二步：	设置响应类型，获取输出流
		response.setContentType("image/png");
		OutputStream output = response.getOutputStream();
		try{
			
		BufferedImage image = null;
		//第三步：	获取文件图片，并输出
			ServletContext context = this.getServletContext();
			List<Icon> icons = (List<Icon>)context.getAttribute("icons");
			Icon icon = new Icon();
			icon.setName(filename);
			int index = icons.indexOf(icon);
			if(index < 0){
				icon.setName("blank");
				icon = icons.get(icons.indexOf(icon));
				image = icon.getImage();
			}else{
				icon = icons.get(index);
				image = icon.getImage();
			}
				
//			BufferedImage image = IconToot.getImageByFileTyle(filename,this.getServletContext().getRealPath("/"));
			ImageIO.write(image, "png", output);
			output.flush();
			output.close();
			
		}catch(Exception e){
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
	}

}
