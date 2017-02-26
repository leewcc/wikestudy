package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.wikestudy.model.dao.CouChapterDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.CouChapter;

public class CouChapterDaoImpl implements CouChapterDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	
	public CouChapterDaoImpl(Connection conn) {
		this.conn = conn;
	}

	@Override // 全插入
	public int insertCouChapter(CouChapter couChapter) throws Exception {
		String sql = "INSERT INTO t_cou_chapter (cou_id, cha_number, cha_name,cha_intro)"
				+ " VALUES (?,?,?,?)";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, couChapter.getCouId());
		pstmt.setInt(2, couChapter.getChaNumber());
		pstmt.setString(2, couChapter.getChaName());
		pstmt.setString(3, couChapter.getChaIntro());
		
		return new GenneralDbconn<CouChapter>().update(pstmt);
	}

	@Override
	public int[] delCouChapter(List<Integer> chaId)  {
		String sql = "DELETE FROM t_cou_chapter WHERE cha_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			for (Integer id: chaId) {
				pstmt.setInt(1, id);
				pstmt.addBatch();
			}
			
			return pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public List<CouChapter> queryCouChapter(int couId) {
		// 查询课程下全部章节
		
		String sql = "SELECT * FROM t_cou_chapter WHERE cou_id=? ORDER BY cha_number";
		
		try {
			pstmt = conn.prepareStatement(sql);
				
			
			pstmt.setInt(1, couId);
			
			return  new GenneralDbconn<CouChapter>().query(CouChapter.class, pstmt);
			
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * @author CHEN 
	 */
	@Override 
	public  CouChapter  queryCouChapterById(int chaId) {
		String sql="select *from t_cou_chapter where cha_id=?";
		List<CouChapter> l=null;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt .setInt(1, chaId);
			l= new GenneralDbconn<CouChapter>().query(CouChapter.class, pstmt);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(l.size()>0||l==null) return l.get(0);
		return null;
	}
	
	@Override // 全更新
	public int updataCouChapter(int chaId, CouChapter couChapter)
			throws Exception {
		String sql = "UPDATE t_cou_chapter SET cou_id=?, cha_number=?, cha_name=?, cha_intro=? WHERE cha_id=?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, couChapter.getCouId());
		pstmt.setInt(2, couChapter.getChaNumber());
		pstmt.setString(3, couChapter.getChaName());
		pstmt.setString(4, couChapter.getChaIntro());
		
		return new GenneralDbconn<CouChapter>().update(pstmt);
	}

	@Override
	public List<Integer> querySecListByCouId(int couId) {
		String sql = "SELECT cs.sec_id FROM t_cou_section AS cs, t_cou_chapter AS cc WHERE cc.cou_id = 1 AND "
				+ " cc.cha_id = cs.cha_id";
		
		List<Integer> secIdList = new LinkedList<Integer>();
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			ResultSet rs = null;
			
			rs = pstmt.executeQuery();
			
			
			while (rs.next()) {
				secIdList.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
			
		return secIdList;
	}
	
	@Override
	public int[] updateCouChapterS(List<CouChapter> ccList) {
		// 批量更新章节信息
		String sqlString = "UPDATE t_cou_chapter SET cha_number = ?, cha_name = ?, cha_intro = ? WHERE cha_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sqlString);
			
			
			for (int i = 0; i < ccList.size(); i++) {
				CouChapter cc = ccList.get(i);
				pstmt.setInt(1, cc.getChaNumber());
				pstmt.setString(2, cc.getChaName());
				pstmt.setString(3, cc.getChaIntro());
				pstmt.setInt(4, cc.getChaId());
				System.out.println(pstmt);
				pstmt.addBatch();
			}
			
			return new GenneralDbconn<CouChapter>().batch(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
		return null;
	}
	
	@Override
	public int delCouChaByCouId(int couId) {
		String sql = "DELETE FROM t_cou_chapter WHERE cou_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			System.out.println(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	@Override
	public List<Integer> insertCouChaAll(List<CouChapter> ccList) {
		List<Integer> list = new ArrayList<Integer>();
		if (ccList.size() == 0)
			return list;
		
		StringBuffer stringBuffer = new StringBuffer("INSERT INTO t_cou_chapter (cou_id, cha_number, cha_name, cha_intro) VALUES ");
		
		String sql = "INSERT INTO t_cou_chapter (cou_id, cha_number, cha_name, cha_intro) VALUES (?,?,?,?)";
	/*	for (CouChapter cc: ccList) {
			stringBuffer.append("(" +cc.getCouId() + "," + cc.getChaNumber() + ",'" + cc.getChaName() + "','" + cc.getChaIntro()  +"') ,");
		}*/
		
		//stringBuffer.deleteCharAt(stringBuffer.length() - 1);
		
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			for (CouChapter cc: ccList) {
				pstmt.setInt(1, cc.getCouId());
				pstmt.setInt(2, cc.getChaNumber());
				pstmt.setString(3, cc.getChaName());
				pstmt.setString(4, cc.getChaIntro());
				pstmt.addBatch();
				System.out.println(pstmt);
			}
			
			pstmt.executeBatch();
			ResultSet rs = pstmt.getGeneratedKeys(); // 获取结果
			
			while (rs.next()) {
				list.add(rs.getInt(1));
				System.out.println("刚插入的chaId :" + rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return list;
		
		
	}
}
