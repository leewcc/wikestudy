package com.wikestudy.model.dto;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleService;

@WebServlet("/dist/article_data")
public class ArticleData extends HttpServlet {

	@Override
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");

		String userDataStr = "";
		if (request.getSession().getAttribute("s") != null
				|| request.getSession().getAttribute("t") != null) {
			userDataStr += "<a style='margin-left: 7px;' href='/wikestudy/dist/jsp/common/logoff'>注销</a>";
			if (request.getSession().getAttribute("s") != null) {
				Student user = ((Student) request.getSession()
						.getAttribute("s"));

				userDataStr += "<a id='user_name' href='/wikestudy/dist/jsp/student/common/student_home'>"
						+ user.getStuName() + "</a>";

			} else if (request.getSession().getAttribute("t") != null) {
				Teacher user = ((Teacher) request.getSession()
						.getAttribute("t"));
				if (Teacher.class == user.getClass())
					;
				userDataStr += "<a id='user_name' href='/wikestudy/dist/jsp/teacher/common/teacher_home'>"
						+ user.getTeaName() + "</a>";
			}
		} else {
			userDataStr = "<a href='/wikestudy/dist/jsp/student/student_account/student_login.jsp'>登录</a>	";
		}

		// 获得点击量
		Connection conn = DBSource.getConnection();
		ArticleService articleService = new ArticleService(conn);
		ResultSet rs = null;
		try {
			int artId = Integer.parseInt(request.getParameter("artId"));
			rs=articleService.queryClickNumByArtId(artId);
		} catch (Exception e) {

		}
		JSONObject data = new JSONObject();
		data.put("userData", userDataStr);
		try {
			if(rs.next()) {
				try {
					data.put("clickNum", rs.getInt("art_click"));
					data.put("artType", rs.getString("a_type_name"));
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		PrintWriter out=response.getWriter();
		out.print(data);
	}

}
