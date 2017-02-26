package com.wikestudy.service.manager;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.wikestudy.model.dao.ArticleDao;
import com.wikestudy.model.dao.ArticleTypeDao;
import com.wikestudy.model.dao.impl.ArticleDaoImpl;
import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.ArticleComment;
import com.wikestudy.model.pojo.PageElem;

public class ArticleService {
	private Connection conn;
	private ArticleDao ad;
	public ArticleService (Connection conn) {
		this.conn=conn;
		ad=new ArticleDaoImpl(conn);
	}
	/**
	 * 功能描述:新增一篇文章
	 * @param article 一个文章对象
	 * @return 1-新增成功 0-新增失败
	 * @throws Exception
	 */
	public int addArticle(Article article) throws Exception {
		return ad.addArticle(article);
	}
	
	/**
	 * 功能描述:根据id删除文章
	 * @param id 指定文章的id
	 * @return 1-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteById(int id) throws Exception {
		ArticleCommentService ac=new ArticleCommentService(conn);
		ac.deleteByArtId(id);
		return ad.deleteById(id);
	}
	
	/**
	 * 更新文章的标志
	 * @param id
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public int updateType(int id, int typeId) throws Exception {
		return ad.updateType(id, typeId);
	}
	
	/**
	 * 功能描述:根据文章id获取文章
	 * @param id 文章id
	 * @return 若有-返回对应的文章信息,否则则返回null
	 * @throws Exception
	 */
	public Article queryById(int id) throws Exception {
		ad.updateClick(id);
		return ad.queryById(id);
	}
	
	/**
	 * 根据文章类型分页获取文章数据
	 * @param typeId 文章类型id
	 * @param articles 封装有分页数据的分页对象
	 * @return 填充了文章数据的分页对象
	 * @throws Exception
	 */
	public PageElem<Article> queryByType(int typeId, PageElem<Article> articles) throws Exception {
		int showNum=10;//显示的条数
		articles.setPageShow(showNum);
		return ad.queryByType(typeId, articles);
	}
	
	/**
	 * 功能描述:根据作者id分页获取文章数据
	 * @param authorId 作者id
	 * @param articles 封装有分页数据的分页对象
	 * @return 填充了文章数据的分页对象
	 * @throws Exception
	 */
	public PageElem<Article> queryByAuthor(int authorId, PageElem<Article> articles) throws Exception {
		return ad.queryByAuthor(authorId, articles);
	}
	
	
	public PageElem<Article> queryByAuthor(int authorId,int cp) throws Exception {
		PageElem<Article> pe = new PageElem<Article>();
		pe.setCurrentPage(cp);
		pe.setPageShow(10);
		return ad.queryByAuthor(authorId, pe);
	}
	
	/**
	 * 功能描述:根据标题模糊搜索文章
	 * @param title 标题
	 * @param articles 封装有分页数据的分页对象
	 * @return 填充了文章数据的分页对象
	 * @throws Exception
	 */
	public PageElem<Article> queryByTitle(String title,int cp) throws Exception {
		int shownum=10;//显示的条数
		PageElem<Article> pe=new PageElem<Article>();
		pe.setPageShow(shownum);
		pe.setCurrentPage(cp);
		pe=ad.queryByTitle(title,pe);
		return pe;
	}
	/**
	 * 功能描述：返回文章类型下的所有文章标题
	 * @param aTypeId
	 * @param cp
	 * @return
	 * @throws Exception 
	 */
	public PageElem<Article> queryAriById(int aTypeId, int cp) throws Exception {
		int shownum=10;
		PageElem<Article> pe=new PageElem<Article>();
		pe.setCurrentPage(cp);
		pe.setPageShow(shownum);
		PageElem<Article> pea=ad.queryByType(aTypeId, pe);
		List<Article>  aList =pea.getPageElem();
		pe.setPageElem(aList);
		pe.setRows(pea.getRows());
		return pe;
	}
	/**
	 * 功能描述：删除某一种类的所有文章
	 * @param typeId
	 * @throws Exception 
	 */
	public int  deleteByTypeId(int typeId) throws Exception {
		ArticleCommentService acs=new ArticleCommentService(conn);
		List<Integer> artList=ad.queryIdByType(typeId);//文章的id集合
		for(int i:artList) {//删除文章的评论
			acs.queryAllComment(i);
		}
		return (ad.deleteByTypeId(typeId)); 
		
	}
	public int insertArticle(Article article) throws SQLException {
		return ad.insertArticle(article);
	}
	public ResultSet queryClickNumByArtId(int artId) throws SQLException{
		return ad.queryClickNumByArtId(artId);
		
	}

	
}
