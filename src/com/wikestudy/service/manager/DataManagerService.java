package com.wikestudy.service.manager;

import java.sql.Connection;

import com.wikestudy.model.dao.DataDao;
import com.wikestudy.model.dao.impl.DataDaoImpl;
import com.wikestudy.model.pojo.Data;
import com.wikestudy.service.publicpart.DataService;

public class DataManagerService {
	private Connection conn;
	private DataDao dd;
	
	public DataManagerService(Connection conn){
		this.conn = conn;
		dd = new DataDaoImpl(this.conn);
	}
	
	public int add(Data data) throws Exception{
		int id =  dd.addData(data);
		if(id <= 0)
			return -1;
		
		data.setDataId(id);
		return 1;
	}
}
