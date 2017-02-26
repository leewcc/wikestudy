package com.wikestudy.model.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import com.wikestudy.model.pojo.Article;
import com.wikestudy.model.pojo.PageElem;


public interface ArticleDao {
	/**
	 * 功能描述:新增一篇文章
	 * @param article 一个文章对象
	 * @return 1-新增成功 0-新增失败
	 * @throws Exception
	 */
	public int addArticle(Article article) throws Exception;
	
	/**
	 * 功能描述:根据id删除文章
	 * @param id 指定文章的id
	 * @return 1-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteById(int id) throws Exception;
	
	/**
	 * 
	 * @param id
	 * @param typeId
	 * @return
	 * @throws Exception
	 */
	public int updateType(int id, int typeId) throws Exception;
	
	/**
	 * 功能描述:根据文章id获取文章
	 * @param id 文章id
	 * @return 若有-返回对应的文章信息,否则则返回null
	 * @throws Exception
	 */
	public Article queryById(int id) throws Exception;
	
	/**
	 * 根据文章类型分页获取文章数据
	 * @param typeId 文章类型id
	 * @param articles 封装有分页数据的分页对象
	 * @return 填充了文章数据的分页对象
	 * @throws Exception
	 */
	public PageElem<Article> queryByType(int typeId, PageElem<Article> articles) throws Exception;
	
	/**
	 * 功能描述:根据作者id分页获取文章数据
	 * @param authorId 作者id
	 * @param articles 封装有分页数据的分页对象
	 * @return 填充了文章数据的分页对象
	 * @throws Exception
	 */
	public PageElem<Article> queryByAuthor(int authorId, PageElem<Article> articles) throws Exception;
	
	/**
	 * 功能描述:根据标题模糊搜索文章
	 * @param title 标题
	 * @param articles 封装有分页数据的分页对象
	 * @return 填充了文章数据的分页对象
	 * @throws Exception
	 */
	public PageElem<Article> queryByTitle(String title, PageElem<Article> articles) throws Exception;
/**
 * 功能：删除某一种类的所有内容
 * @param typeId
 * @throws Exception 
 */
	public int  deleteByTypeId(int typeId) throws Exception;
/**
 * 功能：查询该类型的文章
 * @param typeId
 * @return
 */
public List<Integer> queryIdByType(int typeId) throws Exception;

int updateClick(int id) throws Exception;

public int insertArticle(Article article) throws SQLException;

public ResultSet queryClickNumByArtId(int artId) throws SQLException;

}
