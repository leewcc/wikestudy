package com.wikestudy.servlet.manager.course;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.CourseManageService;



/**
 * Servlet implementation class ChangeCourseServletThi
 * 管理员课程管理——修改课程信息
 */
@WebServlet("/dist/jsp/manager/course/course_thi_change")

public class ChangeCourseThi extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final Log4JLogger l = new Log4JLogger("log4j.properties");


	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ChangeCourseServletThi");
		
		request.setCharacterEncoding("utf-8");
		String jsonS = request.getParameter("json");
		System.out.println(jsonS);
		JSONObject couInforJ;
		jsonS.toString();
		
		List<CouChapter> upChaList = null;  // 新增的章节
		List<CouSection> upSecList = null; 
		
		List<CouChapter> inChaList = null;  // 改变的章节
		List<CouSection> inSecList = null;
		List<Integer> delChaIdList = null;  // 删除的章节
		List<Integer> delSecIdList = null;
		Course cou = null;
		try {
			couInforJ = new JSONObject(jsonS);
			
			JSONArray  chaListJ = couInforJ.getJSONArray("upChaList");
			JSONArray  secListJ = couInforJ.getJSONArray("upSecList");
			JSONArray  delChaListJ = couInforJ.getJSONArray("delChaList");
			JSONArray  delSecListJ = couInforJ.getJSONArray("delSecList");
			
			String couName = couInforJ.getString("couName");
			String couPricUrl = couInforJ.getString("couPricUrl");
			String couBrief = couInforJ.getString("couBrief");
			String couAnno = couInforJ.getString("couAnno");
			int couIdS = couInforJ.getInt("couId");
			System.out.println(couIdS);
			String couGrade = couInforJ.getString("couGrade");
			String labId = couInforJ.getString("labId");
			if (labId == null) {
				labId = "-1";
			}
			
			boolean flag = true; // 标识课程信息是否填写完整
			if (couName == null || couName.equals("")) {
				flag = false;
			}
			
			cou = new Course();
			cou.setCouId(couIdS);
			cou.setCouName(couName);
			cou.setCouPricUrl(couPricUrl);
			cou.setCouBrief(couBrief);
			cou.setCouAnno(couAnno);
			cou.setLabelId(Integer.valueOf(labId));
			cou.setCouGrade(couGrade);
			cou.setCouId(couIdS);
				
			
			upChaList = new LinkedList<CouChapter>();
			upSecList = new LinkedList<CouSection>();
			inChaList = new LinkedList<CouChapter>();
			inSecList = new LinkedList<CouSection>();
		    delChaIdList = new LinkedList<Integer>();
		    delSecIdList = new LinkedList<Integer>();
			
		    // 信息选择
			for (int i = 0; i < chaListJ.length(); i++) {
				JSONObject chaObj = chaListJ.getJSONObject(i);
				Integer chaId = Integer.parseInt(chaObj.getString("chaId"));
				String chaName = chaObj.getString("chaName");
				if (chaName == null || chaName.equals("")) {
					flag = false;
				}
				
				Integer chaNumber = Integer.parseInt(chaObj.getString("chaNumber"));
				
				CouChapter cc = new CouChapter();
				cc.setCouId(couIdS);
				cc.setChaId(chaId);
				cc.setChaName(chaName);
				cc.setChaNumber(chaNumber);
				cc.setChaIntro("");
				if (chaId > 0) {
					upChaList.add(cc);
				} else {
					inChaList.add(cc);
				}
			}
			
			
			for (int i = 0; i < secListJ.length(); i++) {
				JSONObject secObj = secListJ.getJSONObject(i);
				Integer secId = Integer.parseInt(secObj.getString("secId"));
				String secName = secObj.getString("secName");
				if (secName == null || secName.equals("")) {
					flag = false;
				}
				
				String secNumber = secObj.getString("secNumber");
				Integer chaId = Integer.parseInt(secObj.getString("chaId"));
				Integer chaNumber = Integer.parseInt(secObj.getString("chaNumber"));
				
				CouSection cSection = new CouSection();
				cSection.setSecId(secId);
				cSection.setSecName(secName);
				cSection.setSecNumber(secNumber);
				cSection.setChaId(chaId);
				cSection.setChaNumber(chaNumber);
				
				if (secId > 0) {
					upSecList.add(cSection); // 要更新的课时信息
				} else {
					inSecList.add(cSection); // 新插入的课时信息
				}
				
			}
			
			if (!flag) {
				response.setStatus(202);
				response.getWriter().println("请填写课程名和章节信息");
				return ;
			}
			
			
			try {
				for (int i = 0; i < delChaListJ.length(); i++) {
					Integer chaId = Integer.parseInt(delChaListJ.getString(i));
					
					delChaIdList.add(chaId);
				}
				
				for (int i = 0; i < delSecListJ.length(); i++) {
					Integer secId = Integer.parseInt(delSecListJ.getString(i));
					
					delSecIdList.add(secId);
				}
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		}

		
		
		Connection conn = null;
		CourseManageService cms = null;
		
		try {
			conn = DBSource.getConnection();
			conn.setAutoCommit(false);
			
			cms = new CourseManageService(conn);
			List<Integer> inChaId = cms.updateCouAll(cou, upChaList, upSecList, inChaList, inSecList, delChaIdList, delSecIdList);
			
			if (inChaId != null) {
				conn.commit();
				String couPath = this.getServletContext().getRealPath("/") + File.separator + "WEB-INF" + File.separator + "course" + File.separator + cou.getCouId();
				File file = null;
				//创建文件夹
				for (Integer chaId : inChaId) {
					file = new File(couPath + File.separator + chaId);
					if (!file.exists()) {
						file.mkdir();
					} else {
						System.out.println("文件夹已经存在");
						System.exit(0);
					}
				}
				
				// 删除文件夹
				for (Integer delChaId : delChaIdList) {
					file = new File(couPath + File.separator + delChaId);
					if (file.exists()  && file.isDirectory()) {
						if(file.listFiles().length!=0){
					    	File delFile[]=file.listFiles();
					    	for (int i = 0; i < delFile.length; i++) 
					    		delFile[i].delete();
					    }
					}
					file.delete();
				}
			}
			else {
				conn.rollback();
			}
			
			
//			request.setAttribute("url", "");
//			request.setAttribute("message", "课程信息修改成功");
//			request.getRequestDispatcher("../../common/Topic/Success.jsp").forward(request, response);
		
			response.setStatus(200);
			response.getWriter().println("成功保存");
			System.out.println("执行Change Thi完毕");
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("ChangeCourseServletThi 课程内容异常");
			e.printStackTrace();
		} finally {
			try {
				if (conn != null) 
					conn.close();
				else {
					System.out.println("ChangeCourseServletThi conn is null; sql is wrong");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
