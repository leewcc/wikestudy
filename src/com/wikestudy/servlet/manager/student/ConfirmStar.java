package com.wikestudy.servlet.manager.student;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.RecordService;
import com.wikestudy.service.manager.StarService;


@WebServlet("/dist/jsp/manager/super/star_manage/star_confirm")
public class ConfirmStar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public ConfirmStar() {
        super();
        
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
		
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection conn = null;
		RecordService rs = null;
		try{
			conn = DBSource.getConnection();
			rs = new RecordService(conn);
			
			if(rs.confirm() <= 0){
				request.setAttribute("message", "确认失败");
			}else{
				request.setAttribute("message", "确认成功");
			}
			
			request.getRequestDispatcher("star_see").forward(request, response);
			
			
		}catch(Exception e) {
			e.printStackTrace();
			throw new ServletException("确认一周之星失败");
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
	}

}
