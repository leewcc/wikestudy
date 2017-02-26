package com.wikestudy.service.publicpart;

import java.sql.Connection;
import java.util.List;

import javax.print.attribute.standard.RequestingUserName;

import com.wikestudy.model.dao.StudentDao;
import com.wikestudy.model.dao.TeacherDao;
import com.wikestudy.model.dao.TopicDao;
import com.wikestudy.model.dao.impl.StudentDaoImpl;
import com.wikestudy.model.dao.impl.TeacherDaoImpl;
import com.wikestudy.model.dao.impl.TopicDaoImpl;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.pojo.Topic;

public class TopicService {
	private Connection conn;
	private TopicDao td;
	private TeacherDao tead;
	private StudentDao sd;
	private final int shownum = 10;
	
	public TopicService(Connection conn){
		this.conn = conn;
		td = new TopicDaoImpl(this.conn);
		tead = new TeacherDaoImpl(conn);
		sd = new StudentDaoImpl(conn);
	}
	
	public int add(Topic topic) throws Exception{
		return td.insertTopic(topic);
	}
	
	public int delete(int topId)throws Exception{
		return td.deleteTopic(topId);
	}
	
	public PageElem<Topic> getMyTopics(int userId, boolean userType, int currentPage) throws Exception{
		PageElem<Topic> pe = new PageElem<Topic>();
		pe.setCurrentPage(currentPage);
		pe.setPageShow(shownum);
		return td.queryTopicByUser(userId, userType, pe);
	}
	
	public Topic getTopic(int topId) throws Exception{
		td.updataRead(topId, 1);
		return td.queryById(topId);
	}
	
	public PageElem<Topic> queryTopicByType(int flag, int typeId, int currentPage) throws Exception{
		//创建分页对象
		//将当前页数和要展示条数set进去
		PageElem<Topic> pe = new PageElem<Topic>();
		pe.setCurrentPage(currentPage);
		pe.setPageShow(shownum);
		
		//调用根据类型获取话题的方法
		if(typeId <= 0)
			return td.queryAll(flag, pe);
		
		return td.queryByLabel(flag,typeId, pe);
	}
	
	
	public PageElem<Topic> queryByKey(int cp, String key) throws Exception {
		PageElem<Topic> pe = new PageElem<Topic>();
		pe.setCurrentPage(cp);
		pe.setPageShow(shownum);
		return td.queryByKey(key, pe);
	}
	
	
	public boolean isCreater(int topId, int userId, boolean userType) throws Exception{
		//根据话题id获取话题
		Topic t = td.queryById(topId);
		
		if(t != null && t.getTopUserId() == userId && t.isTopUserEnum() == userType)
			return true;
		
		return false;
	}
	
	public PageElem<Topic> queryByTypeM(int label, int currentPage) throws Exception {
		// 创建分页对象
		// 将当前页数和要展示条数set进去
		PageElem<Topic> pe = new PageElem<Topic>();
		pe.setCurrentPage(currentPage);
		pe.setPageShow(shownum);
		pe.setRows(td.getRowsAll());
		
		// 调用根据类型获取话题的方法
		if (label <= 0){
			pe.setRows(td.getRowsAll());
			pe.setPageElem(td.queryAll(pe));
		}else{
			pe.setRows(td.getRowsLabel(label));
			pe.setPageElem(td.queryByLabel(label, pe));
		}
		
		List<Topic> topics = pe.getPageElem();
		for(Topic t : topics) {
			if(t.isTopUserEnum()){
				Teacher tea = tead.queryTeacher(t.getTopUserId());
				if(tea == null || tea.isTeaDelete()) {
					t.setUserName("該用戶不存在");
				}else
					t.setUserName(tea.getTeaName());
			}else{
				Student s = sd.queryById(t.getTopUserId());
				if(s == null){
					t.setUserName("該用戶不存在");
				}else
					t.setUserName(s.getStuName());
			}
		}
		
		return pe;
		
	}
	
}
