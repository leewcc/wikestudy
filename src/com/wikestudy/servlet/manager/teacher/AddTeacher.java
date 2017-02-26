package com.wikestudy.servlet.manager.teacher;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.TeacherService;

/**
 * Servlet implementation class AddTeacher
 */
@WebServlet("/dist/jsp/manager/super/student_manage/teacher_add")
public class AddTeacher extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("teacher_show.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		String []teaType=request.getParameterValues("teaType");
		String []teaNumber=request.getParameterValues("teaNumber");
		String []teaName=request.getParameterValues("teaName");
		String []teaPassword=request.getParameterValues("teaPassword");
		String []teaGender=request.getParameterValues("teaGender");
		String []teaIntroduction=request.getParameterValues("teaIntroduction");
		Teacher t=null;
		List<Teacher> tList=new ArrayList<Teacher>();
		try {
			for(int i=0;i<teaType.length;i++) {
				t=new Teacher();
				t.setTeaDelete(false);
				t.setTeaGender(teaGender[i]);
				t.setTeaIntroduction(teaIntroduction[i]);
				t.setTeaName(teaName[i]);
				t.setTeaNumber(teaNumber[i]);
				t.setTeaPassword(teaPassword[i]);
				t.setTeaPhotoUrl("");
				if("老师".equals(teaType[i]))
					t.setTeaType(false);
				else if("管理员".equals(teaType[i]))t.setTeaType(true);
				tList.add(t);
			}
			Connection conn= DBSource.getConnection();
			TeacherService ts=new TeacherService(conn);
			ts.addTeacher(tList);
			
			request.setAttribute("message", "导入老师成功");
			request.setAttribute("URL", "/wikestudy/dist/jsp/manager/super/student_manage/teacher_show.jsp");
			request.getRequestDispatcher("/dist/jsp/common/error.jsp").forward(request, response);
			
			conn.close();
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		
		
	}

}
