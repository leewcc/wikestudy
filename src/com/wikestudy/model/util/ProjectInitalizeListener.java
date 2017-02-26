package com.wikestudy.model.util;

import java.io.File;
import java.io.FileInputStream;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.GraClass;
import com.wikestudy.model.pojo.Icon;
import com.wikestudy.service.manager.ClassService;

@WebListener
public class ProjectInitalizeListener implements ServletContextListener {

 
    public ProjectInitalizeListener() {
        
    }

	@Override
	public void contextDestroyed(ServletContextEvent arg0) {
	
		try {
			DBSource.destroy();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		System.out.println("数据源已关闭");
		
	}

	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		//初始化数据库信息
		try{
			
    		Properties p = new Properties();
    		
    		//获取配置文件的路径
    		String url = arg0.getServletContext().getRealPath("/") + File.separator + "WEB-INF" + File.separator+"dbcpconfig.properties" ;//this.getClass().getResource("/").getPath();
    	//	url = url.substring(1, url.indexOf("classes"));
    		System.out.println(url);
    		   		
    		//根据路径获取文件流
    		FileInputStream file = new FileInputStream(url);
    		
    		p.load(file);
    		new DBSource(p);
    		
    	}catch(Exception e){
    		e.printStackTrace();
    		System.out.println("项目初始化出错,配置数据源信息失败");
    	}
		
		//获取年级班级信息
		Connection conn = null;
		ClassService cs = null;
		try{
			conn = DBSource.getConnection();
			cs = new ClassService(conn);
			
			GraClass one = cs.query(1);
			GraClass two = cs.query(2);
			GraClass three = cs.query(3);
			
			ServletContext context = arg0.getServletContext();
			context.setAttribute("one", one);
			context.setAttribute("two", two);
			context.setAttribute("three", three);
			
			String root=context.getRealPath("/");
			
			String courseroot = root + "WEB-INF" + File.separator +  "course";
			File f = new File(courseroot);
			if(!f.exists())
				f.mkdir();
			
			//加载图标
			List<Icon> icons = installIcon(f);
			log.debug("图片的缓存数量 ： " + icons.size());
			context.setAttribute("icons", icons);
			
			
//			System.out.println("创建必要文件夹");
//			//获得根路径
//			//图片
//			File file=new File(root+"/images");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        }
//	        file=new File(root+"/images/article");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/images/Common");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/images/images");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/images/Manager");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/dist/images/portrait");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/images/portrait/temp");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/images/lesson");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/images/lesson/temp");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        //视频
//	        file=new File(root+"/videos");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/videos/temp");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//	        file=new File(root+"/videos/lesson");
//	        if (!file.exists()) { // 如果路径不存在，创建  
//	            file.mkdirs();  
//	        } 
//			
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			try {
				if(conn!=null)
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
				log.debug(e,e.fillInStackTrace());
			}
		}
		
	}
	
	private List<Icon> installIcon(File course) {
		List<Icon> iconsL = new ArrayList<>(18);
		File[] iconsF = course.listFiles(new PngFilter());
		for(int i = 0; i < iconsF.length; i++) {
			try{
				File f = iconsF[i];
				Icon icon = new Icon();
				icon.setName(f.getName().substring(0, f.getName().lastIndexOf(".")));
				icon.setImage(IconToot.toBufferedImage(f));
				iconsL.add(icon);
			}catch(Exception e){
				continue;
			}
		}
		
		return iconsL;
	}
	
}
