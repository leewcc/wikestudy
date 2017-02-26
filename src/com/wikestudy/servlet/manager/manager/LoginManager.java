package com.wikestudy.servlet.manager.manager;

import java.io.IOException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.impl.Log4JLogger;
import org.apache.log4j.Logger;

import com.wikestudy.model.dao.TeacherDao;
import com.wikestudy.model.dao.impl.TeacherDaoImpl;
import com.wikestudy.model.enums.Role;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.MD5;

/**
 * Servlet implementation class ManagerLogin
 */
@WebServlet("/dist/jsp/manager/page/manager_login")
public class LoginManager extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getRequestDispatcher("login.jsp").forward(req,resp);
	}
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		Connection conn = null;
		TeacherDao td = null;
		HttpSession session = request.getSession();
		Teacher t = new Teacher();
		Logger logger = Logger.getLogger(this.getClass().getName());

		try {
			conn = DBSource.getConnection();
			td = new TeacherDaoImpl(conn);
			String n = request.getParameter("number");
			String p = request.getParameter("password");
			String c = request.getParameter("code");

			if (check(n, p, c, request)) {
				jump(request, response);
				return;
			}

			p = MD5.getMD5Code(p);

			if ((t = td.queryTeacherByNum(n, p)) != null) {
				if (!t.isTeaDelete() || !t.isTeaType()) {
					request.setAttribute("error", "请输入正确的用户名和密码，请注意大小写");
					jump(request, response);
					return;
				}

				if (!"".equals(t.getTeaPortraitUrl())
						&& null != t.getTeaPortraitUrl()) {// 设置图片位置
					// t.setTeaPhotoUrl( t.getTeaPortraitUrl());
				} else {
					t.setTeaPhotoUrl("/wikestudy/dist/images/portrait/default.jpg");
				}
				session.setAttribute("t", t);// 把整个老师放进去
				session.setAttribute("userType", true);
				session.removeAttribute("s");
				session.setMaxInactiveInterval(60 * 60);// 单位：s

				session.setAttribute("role", Role.teacher);
				Cookie c1 = new Cookie("mid", t.getTeaId() + "");
				response.addCookie(c1);
				Cookie c2 = new Cookie("type", "0");
				response.addCookie(c2);
				response.sendRedirect("/wikestudy/dist/jsp/manager/course/course_query_by_laybel_type?label=0&currentPage=1&type=0");
			} else {// 错误提醒页面
				request.setAttribute("error", "账号或密码错误");
				jump(request, response);
			}
		} catch (Exception e) {
			// log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e, e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

	// 检查账号吗密码是否为空
	private boolean check(String n, String p, String c, HttpServletRequest req) {
		if ("".equals(n) || "".equals(p)) {
			req.setAttribute("error", "请输入账号或密码");
			return true;
		}

		String pE = Student.passwordCheck(p);
		if (!"".equals(pE)) {
			req.setAttribute("error", pE);
			return true;
		}

		if (c == null || "".equals(c)) {
			req.setAttribute("codeE", "请输入验证码");
			return true;
		} else {
			HttpSession ses = req.getSession();
			String rand = (String) ses.getAttribute("rand");
			c = c.toUpperCase();
			if (!c.equals(rand)) {
				req.setAttribute("codeE", "验证码不正确");
				return true;
			}
		}

		return false;

	}

	// 执行跳转
	private void jump(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("login.jsp").forward(request, response);
	}

}
