package com.wikestudy.servlet.manager.article;

import java.io.IOException;
import java.sql.Connection;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.ArticleType;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.service.manager.ArticleTypeService;

/**
 * Servlet implementation class ManagerArtType
 */
@WebServlet("/dist/jsp/manager/article/art_type_manage")
public class ManagerArtType extends HttpServlet {
	private static final long serialVersionUID = 1L;
	Log4JLogger log = new Log4JLogger("log4j.properties");
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ManagerArtType() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		String option=request.getParameter("option");
		System.out.println("进行的操作"+option);
		if("增加".equals(option)) {
			addArtType(request, response);
		} else if("确定修改".equals(option)) {
			updateArtType(request,response);
		}
	}
	private void updateArtType (HttpServletRequest request, HttpServletResponse response) {
		System.out.println("修改标签");
		Connection conn;
		try {
			conn=DBSource.getConnection();
			ArticleTypeService ats=new ArticleTypeService(conn);
			String[] des=request.getParameterValues("aTypeDes");
			String [] name=request.getParameterValues("aTypeName");
			String [] id=request.getParameterValues("aTypeId");
			System.out.println(des+"= =");
			if(id==null||des==null||name==null) return ;
			for(int i=0;i<id.length;i++) {
				ArticleType at=new ArticleType();
				at.setATypeDes(des[i]);
				at.setATypeId(Integer.parseInt(id[i]));
				at.setATypeName(name[i]);			
				ats.updateType(at);
				
			}
		} catch(Exception e) {
			e.printStackTrace();
			log.debug(e,e.fillInStackTrace());
		}
	}
	private void addArtType(HttpServletRequest request, HttpServletResponse response) {
		System.out.println("新增标签");
		Connection conn;
		try {
			conn = DBSource.getConnection();
			ArticleTypeService ats=new ArticleTypeService(conn);
		/*增加想增加的文章类型*/
			String [] aTypeName=request.getParameterValues("aaTypeName");
			String [] aTypeDes=request.getParameterValues("aaTypeDes");
				for(int i=0;i<aTypeName.length;i++) {
					ArticleType at=new ArticleType();
					at.setATypeName(aTypeName[i]);
					at.setATypeDes(aTypeDes[i]);
					ats.AddType(at);
				}
			conn.close();
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
	}
	}
}
