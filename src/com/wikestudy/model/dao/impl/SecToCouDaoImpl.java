package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.List;

import com.wikestudy.model.dao.SecToCouDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.SecToCou;


public class SecToCouDaoImpl implements SecToCouDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private GenneralDbconn<SecToCou> gdb = null;
	
	public SecToCouDaoImpl(Connection conn) {
		this.conn = conn;
		gdb = new GenneralDbconn<SecToCou>();
	}

	@Override
	public List<SecToCou> queryCouBySecId(List<Integer> secIdList) throws Exception {
		StringBuffer sql = new StringBuffer();
		
		sql.append("SELECT * FROM t_sec_to_cou WHERE sec_id IN ( ");
		
		for (int i = 0; i < secIdList.size(); i++)  {
			sql.append("?,");
		}
		
		sql.deleteCharAt(sql.length() - 1);
		sql.append(")");
		
		
		pstmt = conn.prepareStatement(sql.toString());
		
		int i = 1;
		for (Integer secId: secIdList) {
			pstmt.setInt(i, secId);
			i++;
		}
		
		return gdb.query(SecToCou.class, pstmt);
	}
	
	
}
