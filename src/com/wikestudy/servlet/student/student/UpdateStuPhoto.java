package com.wikestudy.servlet.student.student;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class StuPhotoUpdata
 */
//@WebServlet("/dist/jsp/student/account/photo_update")
public class UpdateStuPhoto extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.getRequestDispatcher("photo_update.jsp").forward(request,response);
	}
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		/*	Log4JLogger log = new Log4JLogger("log4j.properties");
		if(null==request.getParameter("x")||"".equals(request.getParameter("x")))
		{
			request.setAttribute("message", "头像错误");
			request.setAttribute("URL","../../student/account/photo_updata.jsp");
			request.getRequestDispatcher("../../common/error.jsp").forward(request, response);
			return ;
		}
		int x=Integer.valueOf(request.getParameter("x"));
		int y=Integer.valueOf(request.getParameter("y"));
		int w=Integer.valueOf(request.getParameter("w"));
		int h=Integer.valueOf(request.getParameter("h"));
		System.out.println(x+"-"+y+"-"+w+"-"+h);
		String imageUrl=request.getParameter("UploadPhoto");
		if(imageUrl==null) return ;
		System.out.println("我是照片的源地址"+imageUrl);
		
		ImageCut ic=new ImageCut();
		Map<String, String> map=new HashMap<String ,String >();
		ic.cutImage(request, x, y, w, h,map);
		String photo=map.get("imageUrl");
		System.out.println(photo);
		HttpSession session=request.getSession();
		Student s=(Student) session.getAttribute("s");
		StudentService ss=null;
		Connection conn=null;
		try {
			conn=DBSource.getConnection();
			ss=new StudentService(conn);
				if(ss.updatePhotourl(s.getStuId(), photo)!=0){ 
					System.out.println("上传成功");
					session.removeAttribute("photoUrl");
				}
				s.setStuPhotoUrl("/wikestudy/dist/images/portrait/"+photo);		
				session.setAttribute("s", s);
				conn.close();	
		} catch (Exception e) {
			log.info(e,e.fillInStackTrace());
			e.printStackTrace();
		}
//		request.setAttribute("message", message);
		response.sendRedirect("/dist/jsp/student/common/student_home");
//		ic.cutImage(request,"C:\\Users\\CHEN\\Desktop\\20140926181339_TnZTj.thumb.700_0.jpeg", 1, 400,1,400);
*/
	}

}
