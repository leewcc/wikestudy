package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.wikestudy.model.dao.LabelDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Label;

public class LabelDaoImpl implements LabelDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private GenneralDbconn<Label> gdb = null;
	
	public LabelDaoImpl(Connection conn) {
		this.conn = conn;
		gdb = new GenneralDbconn<Label>();
	}

	@Override
	public int insertLabel(Label label) throws Exception {
		String sql = "INSERT INTO t_label (lab_name, lab_cib) "
				+ " VALUES (?,?)";
		
		pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setString(1, label.getLabName());
		pstmt.setString(2, label.getLabCib());
		
		return gdb.insert(pstmt);
	}

	private String installId(int count) throws Exception{		
		if(count <= 0)
			return "(0)";
		
		StringBuilder sb = new StringBuilder("(");
		
		for(int i = 0; i < count; i++) {
			
			sb.append("?, ");
			
		}
		
		return sb.substring(0,sb.lastIndexOf(",")) + ")";
		
	}
	
	public int deleteLabel(List<Integer> ids) throws Exception{
		int count = ids.size();
		
		String sql = "delete from t_label where lab_id in " + installId(count);
		
		pstmt = conn.prepareStatement(sql);
		
		for(int i = 0; i < count; i++) {
			pstmt.setInt(i + 1, ids.get(i));
		}
		
		return gdb.update(pstmt);
	}
	
	@Override
	public int deleteLabel(int labId) throws Exception {
		
		String sql = "DELETE FROM t_label WHERE lab_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1,  labId);
		
		return gdb.update(pstmt);
	}

	@Override
	public int updateLabel(int labId, Label label) throws Exception {
		String sql = "UPDATE t_label SET lab_cib=? where lab_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, label.getLabCib());
		pstmt.setInt(2, labId);
		
		return gdb.update(pstmt);
	}

	@Override
	public List<Label> queryLabelAll() {
		String sql = "SELECT *, "
				+ "(SELECT COUNT(*) FROM t_label AS l1, t_topic AS t WHERE l1.lab_id = l2.lab_id AND l1.lab_id = t.lab_id) AS topic_count, "
				+ "(SELECT COUNT(*) FROM t_label AS l1, t_course AS c WHERE l1.lab_id = c.label_id AND l1.lab_id = l2.lab_id) AS course_count "
				+ "FROM  t_label AS l2";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			return gdb.query(Label.class, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	public Label queryLabelByLabId(int labId) throws Exception {
		
		String sql = "SELECT *,"
				+ "(SELECT COUNT(*) FROM t_label AS l1,t_topic AS t WHERE l1.lab_id = l2.lab_id AND l1.lab_id = t.lab_id) AS topic_count, "
				+ "(SELECT COUNT(*) FROM t_label AS l1, t_course AS c WHERE l1.lab_id = c.label_id AND l1.lab_id = l2.lab_id) AS course_count "
				+ "FROM t_label AS l2 WHERE l2.lab_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, labId);
		
		List<Label> list =  gdb.query(Label.class, pstmt);
		
		if(list.isEmpty())
			return null;
		
		return list.get(0);
	}

	@Override
	public Label queryLabelByName(String name) throws Exception {
		String sql = "SELECT * FROM t_label WHERE lab_name = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, name);
		
		List<Label> list =  gdb.query(Label.class, pstmt);
		
		if(list.isEmpty())
			return null;
		
		return list.get(0);
	}
	
	public List<Label> queryLabelViewAll() {
		String sql = "SELECT lab_id, lab_name FROM  t_label AS l2";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			return gdb.query(Label.class, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
}
