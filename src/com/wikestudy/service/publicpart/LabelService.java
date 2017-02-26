package com.wikestudy.service.publicpart;

import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.LabelDao;
import com.wikestudy.model.dao.impl.LabelDaoImpl;
import com.wikestudy.model.pojo.Label;

public class LabelService {
	private Connection conn;
	private LabelDao ld;
	
	public LabelService(Connection conn){
		this.conn = conn;
		ld = new LabelDaoImpl(this.conn);
	}
	
	public Label getLabel(int id) throws Exception{
		return ld.queryLabelByLabId(id);
	}
	
	public List<Label> getList() throws Exception{
		return ld.queryLabelAll();
	}
	
	public List<Label>getListV() throws Exception  {
		return ld.queryLabelViewAll();
	}
}
