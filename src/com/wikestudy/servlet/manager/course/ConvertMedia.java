package com.wikestudy.servlet.manager.course;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.management.ManagementFactory;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import sun.management.ManagementFactoryHelper;
import net.sf.json.JSONObject;

import com.sun.management.OperatingSystemMXBean;
import com.wikestudy.model.dao.CouSectionDao;
import com.wikestudy.model.dao.impl.CouSectionDaoImpl;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.SimpleThreadPool;

@WebServlet("/dist/jsp/teacher/course/media_convert")
public class ConvertMedia extends HttpServlet {

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		boolean doBoolean=true;
		while(doBoolean) {
			//对内存状态进行检查
			OperatingSystemMXBean bean=(OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
			//总内存加上虚拟内存
			long totalMemory =bean.getTotalSwapSpaceSize();
			//剩余的内存
			long freeMemorySize=bean.getFreeSwapSpaceSize();
			Double compare=(Double)(1-freeMemorySize*1.0/totalMemory)*100;
			if(compare.intValue()>80) {
				try {
					Thread.sleep(10000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				};
				doBoolean=true;
			} else {
				doBoolean=false;
			}
		}
	
		
		
		
		// 对视频进行转码
		String basePath = getServletContext().getRealPath("/dist/videos");

		StringBuffer upFilePathBuffer=new StringBuffer(basePath);
		upFilePathBuffer.append("/temp/");
		upFilePathBuffer.append(req.getParameter("fileName"));
		
		String upFilePath =upFilePathBuffer.toString();

		String serialName = String.valueOf(System.currentTimeMillis());

		String flvFilePath = basePath + "/lesson/" + serialName + ".flv"; // 设置转换为flv格式后文件的保存路径

		/**
		 * 这里是保存路径到数据库
		 */
		Connection conn = DBSource.getConnection();
		CouSectionDao csd = new CouSectionDaoImpl(conn);
		// 获得secId
		int secId=0;
		try {
			secId = Integer.parseInt(req.getParameter("secId"));
		} catch (Exception e) {

		}

		csd.updateSecVideo(secId, "lesson/" + serialName + ".flv");

		String cutPicPath = basePath + "/photo/" + serialName + ".jpg"; // 设置从上传的视频中截取的图片的保存路径
		boolean mark = false;
	
			mark = convertVideo(upFilePath, flvFilePath, cutPicPath); // 转换视频格式
			Runnable work=new Runnable() {
				
				@Override
				public void run() {
					String root = getServletContext().getRealPath("/dist/videos/temp");
					File froot = new File(root);
					File[] files = froot.listFiles();
					if(files.length<=0)  {} else {
	 					for (File f : files) {
							long time = f.lastModified();
							if (time + 100000000 < System.currentTimeMillis()) {
								f.delete();
							}
						}
					}
				}
			}; 
			ExecutorService executor =	SimpleThreadPool.executor;
			executor.execute(work);
			

			JSONObject data=new JSONObject();
			data.put("filename", "lesson/" + serialName + ".flv");
					
			PrintWriter out=resp.getWriter();
			out.print(data);
			
			
			
		}

	/**
	 * @功能：①转换上传的视频为FLV格式；②从上传的视频中截图。
	 * @参数：①upFilePath： 用于指定要转换格式的文件路径；以及用来指定要截图的视频。<br>
	 * @参数：②flvFilePath：用于指定转换为FLV格式后的文件的保存路径。<br>
	 * @参数：③cutPicPath： 用于指定截取的图片的保存路径
	 * @返回：boolean型值
	 */
	private boolean convertVideo(String upFilePath, String flvFilePath,
			String cutPicPath) {
		String ffmpegPath;
		if (System.getProperties().getProperty("os.name").toUpperCase()
				.indexOf("WINDOWS") != -1) {
			ffmpegPath = getServletContext().getRealPath("/dist/videos")
					+ "/ffmpeg.exe"; // 获取在web.xml中配置的转换工具（ffmpeg.exe）的存放路径
		} else {
			ffmpegPath = "/usr/local/ffmpeg/bin/ffmpeg";
		}
		System.out.println(ffmpegPath);
		// 创建一个List集合来保存转换视频文件为flv格式的命令
		List<String> convert = new ArrayList<String>();
		convert.add(ffmpegPath); // 添加转换工具路径
		convert.add("-i"); // 添加参数＂-i＂，该参数指定要转换的文件
		convert.add(upFilePath); // 添加要转换格式的视频文件的路径
		convert.add("-qscale");
		convert.add("6");
		convert.add("-ab");
		convert.add("64");
		convert.add("-acodec");
		convert.add("mp3");
		convert.add("-ac");
		convert.add("2");
		convert.add("-ar");
		convert.add("22050");
		convert.add("-r");
		convert.add("24");
		convert.add("-y"); // 添加参数＂-y＂，该参数指定将覆盖已存在的文件
		convert.add(flvFilePath);

		// 创建一个List集合来保存从视频中截取图片的命令
		List<String> cutpic = new ArrayList<String>();
		cutpic.add(ffmpegPath);
		cutpic.add("-i");
		cutpic.add(upFilePath); // 同上（指定的文件即可以是转换为flv格式之前的文件，也可以是转换的flv文件）
		cutpic.add("-y");
		cutpic.add("-f");
		cutpic.add("image2");
		cutpic.add("-ss"); // 添加参数＂-ss＂，该参数指定截取的起始时间
		cutpic.add("2"); // 添加起始时间为第9秒
		cutpic.add("-t"); // 添加参数＂-t＂，该参数指定持续时间
		cutpic.add("0.001"); // 添加持续时间为1毫秒
		cutpic.add("-s"); // 添加参数＂-s＂，该参数指定截取的图片大小
		cutpic.add("350*240"); // 添加截取的图片大小为350*240
		cutpic.add(cutPicPath); // 添加截取的图片的保存路径
		System.out.println(cutPicPath);
		boolean mark = true;
		ProcessBuilder builder = new ProcessBuilder();
		try {

			builder.command(convert);
			builder.start();
			builder.command(cutpic);
			builder.start();

		} catch (Exception e) {
			mark = false;
			System.out.println(e);
			e.printStackTrace();
		}
		return mark;
	}

}
