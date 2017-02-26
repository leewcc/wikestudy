package com.wikestudy.service.manager;

import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.ArticleTypeDao;
import com.wikestudy.model.dao.impl.ArticleTypeDaoImpl;
import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.ArticleType;
import com.wikestudy.model.pojo.PageElem;

public class ArticleTypeService {
	private Connection conn;
	private ArticleTypeDao atd;
	public ArticleTypeService (Connection conn) {
		this.conn=conn;
		atd=new ArticleTypeDaoImpl(conn);
	}

	/**
	 * 功能描述:新建一个类型
	 * @param type 类型
	 * @return 1-添加成功 0-添加失败
	 * @throws Exception
	 */
	public int AddType(ArticleType type) throws Exception {
		return atd.AddType(type);
	}
	
	/**
	 * 功能描述:根据id删除类型
	 * @param id 类型id
	 * @return 1-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteById(int id) throws Exception {
		return atd.deleteById(id);
	}
	
	/**
	 * 功能描述:修改类型
	 * @param type 要修改的类型
	 * @return 1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateType(ArticleType type) throws Exception {
		return atd.updateType(type);
	}
	
	/**
	 * 功能描述:查找所有类型
	 * @return 一个封装所有类型的集合
	 * @throws Exception
	 */
	public List<ArticleType> queryType() throws Exception {
		return atd.queryType();
	}
	/**
	 * 功能描述:根据id查找某个类型
	 * @param id 类型id
	 * @return 某个具体的类型
	 * @throws Exception
	 */
	public ArticleType queryById(int id) throws Exception {
		return atd.queryById(id);
	}

	public String queryTypeByTypeId(int artTypeId) throws Exception {
		
		return atd.queryTypeByTypeId(artTypeId) ;
	}
}
