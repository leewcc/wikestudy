package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.wikestudy.model.dao.DataDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Data;

public class DataDaoImpl implements DataDao{
	private Connection conn;
	
	public DataDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public int addData(Data data) throws Exception {
		String sql = "INSERT INTO t_data (data_binding, data_mark, "
				+ "data_name, data_size, data_root,data_des) VALUES (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setInt(1, data.getDataBinding());
		
		pstmt.setBoolean(2, data.isDataMark());
		
		pstmt.setString(3, data.getDataName());
		
		pstmt.setLong(4, data.getDataSize());
		
		pstmt.setString(5, data.getDataRoot());
		
		pstmt.setString(6, data.getDataDes());
		
		return new GenneralDbconn<Data>().insert(pstmt);
	}

	@Override
	public int deleteData(int id) throws Exception {
		String sql = "DELETE FROM t_data WHERE data_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return new GenneralDbconn<Data>().update(pstmt);
	}

	@Override
	public int deleteByBinding(int Binding, boolean Mark) throws Exception {
		String sql = "DELETE FROM t_data WHERE data_binding = ? AND data_mark = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Binding);
		
		pstmt.setBoolean(2, Mark);
		
		return new GenneralDbconn<Data>().update(pstmt);
	}

	@Override
	public int deleteByCourseId(int courseId) throws Exception {
		String sql = "DELETE FROM t_data WHERE data_course_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, courseId);
		
		return new GenneralDbconn<Data>().update(pstmt);
	}

	@Override
	public int updateName(int id, String name) throws Exception {
		String sql = "UPDATE t_data SET data_name = ? WHERE data_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Data>().update(pstmt);
	}

	@Override
	public int updateDes(int id, String des) throws Exception {
		String sql = "UPDATE t_data SET data_des = ? WHERE data_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, des);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Data>().update(pstmt);
	}

	@Override
	public int updateDownLoad(int id, int downLoad) throws Exception {
		String sql = "UPDATE t_data SET data_download = data_download + ? WHERE data_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, downLoad);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Data>().update(pstmt);
	}

	@Override
	public List<Data> queryByCourseId(int courseId) throws Exception {
		String sql = "SELECT * FROM t_data WHERE data_course_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, courseId);
		
		return new GenneralDbconn<Data>().query(Data.class, pstmt);
	}

	@Override
	public Data queryById(int id) throws Exception {
		String sql = "SELECT * FROM t_data WHERE data_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		List<Data> list = new GenneralDbconn<Data>().query(Data.class, pstmt);
		
		if(list.size() <= 0)
			return null;
		
		return list.get(0);
	}

	@Override
	public List<Data> queryByBinding(int Binding, boolean Mark)
			throws Exception {
		String sql = "SELECT * FROM t_data WHERE data_binding = ? AND data_mark = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, Binding);
		
		pstmt.setBoolean(2, Mark);
		
		return new GenneralDbconn<Data>().query(Data.class, pstmt);
	}
	
	@Override
	public int delCouDataAll(int couId) {
		String sql = "DELETE FROM t_data "
				+ " USING t_data, t_cou_chapter, t_cou_section "
				+ " WHERE t_cou_chapter.cou_id = ? AND t_cou_chapter.cha_id = t_cou_section.cha_id "
				+ " AND( (t_data.data_mark = 0 AND t_data.data_binding = t_cou_chapter.cha_id)  "
				+ " OR (t_data.data_mark = 1 AND t_data.data_binding = t_cou_section.sec_id) )";
	
		
		try {
			PreparedStatement pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return -1;
	}

}
