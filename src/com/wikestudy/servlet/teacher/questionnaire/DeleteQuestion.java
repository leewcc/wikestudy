package com.wikestudy.servlet.teacher.questionnaire;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.QuestionService;
import com.wikestudy.service.publicpart.CouCharService;
import com.wikestudy.service.publicpart.CouSectionService;
import com.wikestudy.service.publicpart.CourseService;


@WebServlet("/dist/jsp/teacher/course/question_delete")
public class DeleteQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteQuestion() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		//获取课时id
		int sid = Integer.parseInt(request.getParameter("secId"));
				
		//判断是否有权限
		if(!checkPower(sid, request)){
			request.getRequestDispatcher("/dist/jsp/teacher/common/my_courses_query").forward(request, response);
			return;
		}
		
		//执行删除
		delete(sid, request);
		response.sendRedirect("question_make_by_tea?secId=" + sid);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	public boolean checkPower(int sid, HttpServletRequest request) {
		HttpSession session = request.getSession();
		Teacher t = (Teacher)session.getAttribute("t");
		
		Connection conn = null;
		try{
			conn = DBSource.getConnection();
			CouSectionService css = new CouSectionService(conn);
			CouCharService ccs = new CouCharService(conn);
			CourseService cs = new CourseService(conn);
			
			//获取课时
			CouSection s = css.getSection(sid);
			if(s == null){
				request.setAttribute("message", "该课时不存在");
				return false;
			}
			
			CouChapter cc = ccs.getChapter(s.getChaId());
			if(cc == null){
				request.setAttribute("message", "该课时不存在");
				return false;
			}
			
			Course c = cs.queryById(cc.getCouId());
			if(c == null){
				request.setAttribute("message", "该课时不存在");
				return false;
			}else{
				if(c.getTeacherId() != t.getTeaId()){
					request.setAttribute("message", "你不能删除该测试");
					return false;
				}
			}
			
			
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
		
	}
	
	private void delete(int sid, HttpServletRequest request){
		Connection conn = null;
		try{
			conn = DBSource.getConnection();
			QuestionService qs = new QuestionService(conn);
			qs.deleteBySec(sid);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
}
