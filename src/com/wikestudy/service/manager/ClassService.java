package com.wikestudy.service.manager;

import java.sql.Connection;

import com.wikestudy.model.dao.GraClassDao;
import com.wikestudy.model.dao.impl.GraClassDaoImpl;
import com.wikestudy.model.pojo.GraClass;

public class ClassService {
	private Connection conn = null;
	private GraClassDao cd = null;
	
	public ClassService(Connection conn){
		this.conn = conn;
		cd = new GraClassDaoImpl(this.conn);
	}
	
	public int update(GraClass cla) throws Exception{
		return cd.updateNum(cla);
	}
	
	public GraClass query(int claId) throws Exception{
		return cd.queryClass(claId);
	}
}
