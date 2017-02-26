package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.Message;
import com.wikestudy.model.pojo.PageElem;
/**
 * 对留言的操作
 * @author CHEN
 */
public interface MessageDao {
	/**
	 * 将留言信息插入数据库
	 * @param messages
	 */
	public int addMessage(Message messages)throws Exception;
	/**
	 * 根据留言的id先把回复删除之后再删留言
	 * @param messid
	 */
	public int delMessage(int messid)throws Exception;
	
	public Message queryMessage(int messid)throws Exception;
	/**
	 * 根据留言板主人的id获得所有的留言信息（不包括回复）
	 * @param messmasterid
	 */
	public PageElem<Message> queryMessages(int messmasterid, boolean marsterType, PageElem<Message> pageElem)
			throws Exception;
	/**
	 * 把回复的信息以及留言的信息增到数据库
	 * @param messages
	 */
	public int addBinding (Message messages)throws Exception;
	/**
	 * 根据回复主键（messid）删除信息
	 * @param messbinding
	 */
	public int delBinding (int messBinding)throws Exception;
	/**
	 * 根据留言的id查出所有回复
	 * @param messbinding
	 * 注意：并不需要分页
	 */
	public List<Message> queryBinding(int messbinding) throws Exception;
	
}
