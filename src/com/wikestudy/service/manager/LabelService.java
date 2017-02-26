package com.wikestudy.service.manager;

import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.LabelDao;
import com.wikestudy.model.dao.impl.LabelDaoImpl;
import com.wikestudy.model.pojo.Label;

public class LabelService {
	private Connection conn;
	private LabelDao ld;
	
	public LabelService(Connection conn) {
		this.conn = conn;
		ld = new LabelDaoImpl(this.conn);
	}
	
	public int add(Label label) throws Exception{
		int id = ld.insertLabel(label);
		if(id <= 0)
			return -1;
		
		label.setLabId(id);
		return 1;
	}
	
	public int delete(int id) throws Exception{		
			return ld.deleteLabel(id);
	}
	
	public int update(Label l) throws Exception{
		return ld.updateLabel(l.getLabId(), l);
	}
	
	
	public Label getLabel(int id) throws Exception{
		return ld.queryLabelByLabId(id);
	}
	
	public Label getLabel(String name) throws Exception {
		return ld.queryLabelByName(name);
	}
	
	public List<Label> query() throws Exception{
		return ld.queryLabelAll();
	}
}
