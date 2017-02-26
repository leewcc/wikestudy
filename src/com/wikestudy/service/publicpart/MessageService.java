package com.wikestudy.service.publicpart;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.MessageDao;
import com.wikestudy.model.dao.impl.MessageDaoImpl;
import com.wikestudy.model.pojo.Message;
import com.wikestudy.model.pojo.MessageView;
import com.wikestudy.model.pojo.PageElem;

public class MessageService {
	private Connection conn;
	private MessageDao md;
	private final int shownum = 10;
	
	public MessageService(Connection conn) {
		this.conn = conn;
		md = new MessageDaoImpl(this.conn);
	}
	
	public int addMessage(Message mess)throws Exception{
		int id = md.addMessage(mess);
		if(id <= 0)
			return -1;
		
		mess.setMessId(id);
		return 1;
	}
	
	public int delete(int messId) throws Exception{
		//根据留言id获取留言
		Message m = md.queryMessage(messId);
		
		//判断删除的是留言还是回复
		//是留言的话,则将留言绑定的回复也一起删除
		//如果是回复,直接删掉回复
		if(m.getMessBinding() == 0)
			md.delBinding(m.getMessId());
		
		return md.delMessage(m.getMessId());
	}
	
	
	//根据留言主键id获取留言
	public Message queryById(int messId) throws Exception{
		return md.queryMessage(messId);
	}
	
	
 	public PageElem<MessageView> query(int marsterId, boolean marsterType, int cp) throws Exception{
		PageElem<MessageView> mvL = new PageElem<MessageView>();
		PageElem<Message> mL = new PageElem<Message>();
		
		//第一步：	设置分页对象属性
		mL.setCurrentPage(cp);
		mL.setPageShow(shownum);
		
		
		//第二步：	获取主留言
		mL= md.queryMessages(marsterId, marsterType, mL);
		
		
		//第三步：	初始化需要用到的变量
		List<Message> list = mL.getPageElem();
		Iterator<Message> it = list.iterator();
		MessageView mv = null;
		Message m = null;
		List<Message> replys = new ArrayList<Message>();
		List<MessageView> mvElem = new ArrayList<MessageView>();
		
		
		//第四步：	迭代主留言，获取主留言的回复
		while(it.hasNext()){
			mv = new MessageView();
			m = it.next();
			replys = md.queryBinding(m.getMessId());
			
			
		//第五步：	将查询回来的数据set进留言视图对象张
			mv.setMessage(m);
			mv.setMessReply(replys);
			mvElem.add(mv);
		}
		
		
		//初始化留言视图分页对象
		mvL.setPageElem(mvElem);
		mvL.setCurrentPage(cp);
		mvL.setPageShow(shownum);
		mvL.setRows(mL.getRows());
		
		
		return mvL;
	}
}
