package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.ArticleComment;
import com.wikestudy.model.pojo.PageElem;



public interface ArticleCommentDao {
	/**
	 * 功能描述：生成一条评论记录
	 * @param comment 新的评论记录
	 * @return 1-生成成功 0-生成失败
	 * @throws Exception
	 */
	public int comment_add(ArticleComment comment) throws Exception;

	/**
	 * 功能描述:根据评论id删除记录
	 * @param id 评论id
	 * @return 1-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteById(int id) throws Exception;

	/**
	 * 功能描述:根据文章id删除评论
	 * @param artId 文章id
	 * @return >0-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteByArtId(int artId) throws Exception;

	/**
	 * 功能描述:根据绑定的评论id删除对应的回复
	 * @param binding 绑定的评论
	 * @return >0-删除成功 1-删除失败
	 * @throws Exception
	 */
	public int deleteByBinding(int binding) throws Exception;

	/**
	 * 功能描述:根据评论id查询评论
	 * @param id 评论id
	 * @return 对应id的评论
	 * @throws Exception
	 */
	public ArticleComment queryById(int id) throws Exception;

	/**
	 * 功能描述:根据文章id获取文章下的评论
	 * @param artId 文章id
	 * @param pageElem 封装了分页数据信息的对象
	 * @return 填充了评论数据的分页对象
	 * @throws Exception
	 */
	public PageElem<ArticleComment> queryByArtId(int artId,
			PageElem<ArticleComment> pageElem) throws Exception;
	
	/**
	 * 功能描述:根据绑定的评论id去获取相应的评论回复
	 * @param Binding 绑定的评论id
	 * @return 对应回复的评论集合
	 * @throws Exception
	 */
	public List<ArticleComment> queryByBinding(int Binding) throws Exception;

	public int deleteIdByArt(int i) throws Exception ;

}
