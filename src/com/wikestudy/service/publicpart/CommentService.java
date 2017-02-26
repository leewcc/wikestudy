package com.wikestudy.service.publicpart;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.naming.ldap.Rdn;

import com.wikestudy.model.dao.CommentDao;
import com.wikestudy.model.dao.RecordDao;
import com.wikestudy.model.dao.StudentDao;
import com.wikestudy.model.dao.TopicDao;
import com.wikestudy.model.dao.impl.CommentDaoImpl;
import com.wikestudy.model.dao.impl.RecordDaoImpl;
import com.wikestudy.model.dao.impl.StudentDaoImpl;
import com.wikestudy.model.dao.impl.TopicDaoImpl;
import com.wikestudy.model.pojo.Comment;
import com.wikestudy.model.pojo.CommentView;
import com.wikestudy.model.pojo.PageElem;

public class CommentService {
	private Connection conn;
	private CommentDao cd;
	private StudentDao sd;
	private TopicDao td;
	private RecordDao rd;
	private final int shownum = 10;
	
	public CommentService(Connection conn){
		this.conn = conn;
		cd = new CommentDaoImpl(this.conn); 
		sd = new StudentDaoImpl(this.conn);
		td = new TopicDaoImpl(this.conn);
		rd = new RecordDaoImpl(this.conn);
	}
	
	public int add(Comment comment)throws Exception{
		int id = cd.comment_add(comment);
		 if(id > 0){
			 comment.setComId(id);
			 if(!comment.isComUserEnum()){
				 td.updateAnswer(comment.getComTopicId(), 1);
				 sd.updateDisscussNum(comment.getComUserId(),1);
				 rd.updateDidNum(comment.getComUserId(), 1);
			 }
				 return 1;
			 
		 }else {
			return 0;
		}
	}
	
	public int setBestAnswer(int topId, int comId, boolean isUp) throws Exception{
		//判断是设置最佳回复还是取消最佳回复
		//如果是最佳回复，则调用取消以前设置的最佳回复，调用设置新的回复为最佳回复
		if(isUp){
			cd.cancelBest(topId);			
		}
		
		return cd.setBest(comId, topId, isUp);
	}
	
	//获取我的回答
	public PageElem<Comment> getMyAnswers(int userId, boolean userType, int currentPage) throws Exception{
		PageElem<Comment> Answers = new PageElem<Comment>();
		Answers.setCurrentPage(currentPage);
		Answers.setPageShow(shownum);
		
		return cd.queryBySender(userId, userType, Answers);
		
		
	}
	
	public PageElem<Comment> getMyReplys(int userId, boolean userType, int currentPage) throws Exception{
		PageElem<Comment> replys = new PageElem<Comment>();
		replys.setCurrentPage(currentPage);
		replys.setPageShow(shownum);
		
		//去数据库获取数据
		return cd.queryByReceiver(userId, userType, replys);
	}
	
	public PageElem<CommentView> comment_gets(int topId, int currentPage) throws Exception{
		PageElem<CommentView> cvL = new PageElem<CommentView>();
		PageElem<Comment> cL = new PageElem<Comment>();
		
		//设置分页对象的分页属性
		cL.setCurrentPage(currentPage);
		cL.setPageShow(shownum);
		
		//调用底层获取数据
		cL = cd.queryByTopic(topId, cL);
		
		//将获取回来的分页数据存放进评论回复视图中
		cvL.setCurrentPage(currentPage);
		cvL.setPageShow(shownum);
		cvL.setRows(cL.getRows());
		
		//获取查询回来的评论集合,迭代评论,获取每个评论的对应回复,并与评论组装成一个评论视图对象
		List<Comment> list = cL.getPageElem();
		Iterator<Comment> it = list.iterator();
		CommentView cv = null;
		Comment c = null;
		List<Comment> comReply = null;
		List<CommentView> cvElem = new ArrayList<CommentView>();
		
		while(it.hasNext()){
			cv = new CommentView();
			c = it.next();
			comReply = cd.queryByBinding(c.getComId());
			cv.setComment(c);
			cv.setComReply(comReply);
			cvElem.add(cv);			
		}
		
		cvL.setPageElem(cvElem);
		return cvL;
	}
	
	public Comment comment_get(int id) throws Exception{
		return cd.queryById(id);
	}
	
	public int deleteComment(int id) throws Exception{
		cd.deleteById(id);
		return cd.deleteByBinding(id);
	}
}
