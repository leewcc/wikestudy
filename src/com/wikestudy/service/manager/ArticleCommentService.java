package com.wikestudy.service.manager;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.xmlbeans.impl.xb.xsdschema.All;

import com.wikestudy.model.dao.ArticleCommentDao;
import com.wikestudy.model.dao.impl.ArticleCommentDaoImpl;
import com.wikestudy.model.pojo.ArticleComment;
import com.wikestudy.model.pojo.PageElem;

public class ArticleCommentService {
	private Connection conn;
	private ArticleCommentDao acs;
	private final int shownum=10;
	
	public ArticleCommentService (Connection conn) {
		acs=new ArticleCommentDaoImpl(conn);
		this.conn=conn;
	}
	
	/**
	 * 功能描述：生成一条评论记录
	 * @param comment 新的评论记录
	 * @return 1-生成成功 0-生成失败
	 * @throws Exception
	 */
	public int addComment(ArticleComment comment) throws Exception {
		return acs.comment_add(comment);
	}

	/**
	 * 功能描述:根据评论id删除记录
	 * @param id 评论id
	 * @return 1-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteById(int id) throws Exception {
		return acs.deleteById(id);
	}

	/**
	 * 功能描述:根据文章id删除评论
	 * @param artId 文章id
	 * @return >0-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteByArtId(int artId) throws Exception {
		return acs.deleteByArtId(artId);
	}

	/**
	 * 功能描述:根据绑定的评论id删除对应的回复
	 * @param binding 绑定的评论
	 * @return >0-删除成功 1-删除失败
	 * @throws Exception
	 */
	public int deleteByBinding(int binding) throws Exception {
		return acs.deleteByBinding(binding);
	}

	/**
	 * 功能描述:根据评论id查询评论
	 * @param id 评论id
	 * @return 对应id的评论
	 * @throws Exception
	 */
	public ArticleComment queryById(int id) throws Exception {
		return acs.queryById(id);
	}

	/**
	 * 功能描述:根据文章id获取文章下的评论
	 * @param artId 文章id
	 * @param pageElem 封装了分页数据信息的对象
	 * @return 填充了评论数据的分页对象
	 * @throws Exception
	 */
	public PageElem<ArticleComment> queryByArtId(int artId,
			PageElem<ArticleComment> pageElem,int cp) throws Exception {
		PageElem<ArticleComment> pac=new PageElem<ArticleComment>();
		pac=acs.queryByArtId(artId, pageElem);
		pac.setPageShow(shownum);
		pac.setCurrentPage(cp);
		return pac;
	}
	
	/**
	 * 功能描述:根据绑定的评论id去获取相应的评论回复
	 * @param Binding 绑定的评论id
	 * @return 对应回复的评论集合
	 * @throws Exception
	 */
	public List<ArticleComment> queryByBinding(int Binding) throws Exception {
		return acs.queryByBinding(Binding);
	}	
	
	/**
	 * 整合评论和回复返回给评论页面
	 */
	public PageElem<ArticleComment> queryAll(int artId,int cp) throws Exception {
		List<ArticleComment> all=new ArrayList<ArticleComment>();
		PageElem<ArticleComment> pageElemAll=new PageElem<ArticleComment>();
		pageElemAll.setCurrentPage(cp);
		pageElemAll.setPageShow(shownum);
		PageElem<ArticleComment> c=acs.queryByArtId(artId,pageElemAll);//评论
		
		List<ArticleComment> comments=c.getPageElem();
		//整合评论和回复
		for(ArticleComment ac:comments) {
			all.add(ac);
			List<ArticleComment> tempList=(ArrayList<ArticleComment>)queryByBinding(ac.getAComId());
			all.addAll(tempList);
		}
		//把留言人放進去
		//把时间格式化一下
		
		
		pageElemAll.setRows(c.getRows());
		pageElemAll.setPageElem(all);
		
		return pageElemAll;
	}

	public int queryAllComment(int i) throws Exception {
		return acs.deleteIdByArt(i);
	}


}
