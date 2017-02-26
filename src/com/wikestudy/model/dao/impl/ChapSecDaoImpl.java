package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.ChapSecDao;
import com.wikestudy.model.pojo.ChapSec;

public class ChapSecDaoImpl implements ChapSecDao{

	Connection conn = null;
	
	public ChapSecDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<ChapSec> queryChapSecByCouId(int couId) {
		
		
		return null;
	}
	
}
