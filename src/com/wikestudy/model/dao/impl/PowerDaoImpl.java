package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.wikestudy.model.dao.PowerDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Power;

public class PowerDaoImpl implements PowerDao{
	private Connection conn;
	
	public PowerDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Power> queryPower() throws Exception {
		String sql = "SELECT * FROM t_power ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		return new GenneralDbconn<Power>().query(Power.class, pstmt);
	}

}
