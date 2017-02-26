package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import jdk.nashorn.internal.objects.annotations.Where;

import com.sun.xml.internal.ws.util.xml.CDATA;
import com.wikestudy.model.dao.CouSectionDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.CourseInfor;

public class CouSectionDaoImpl implements CouSectionDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private GenneralDbconn<CouSection> gdb = null;
	
	public CouSectionDaoImpl(Connection conn) {
		this.conn = conn;
		gdb = new GenneralDbconn<CouSection>();
	}

	@Override // 全插入
	public int insertCouSeciton(CouSection couSection) throws Exception {
		String sql  = "INSERT INTO t_cou_section (cha_id, sec_number, sec_video_url, sec_name) "
				+ " VALUES (?,?,?,?)";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, couSection.getChaId());
		pstmt.setString(2, couSection.getSecNumber());
		pstmt.setString(3, couSection.getSecVideoUrl());
		pstmt.setString(4, couSection.getSecName());
		
		return gdb.update(pstmt);
	}

	@Override
	public int delCouSection(int secId) throws Exception {
		String sql = "DELETE FROM t_cou_section WHERE sec_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, secId);
		
		return gdb.update(pstmt);
	}
	
	@Override 
	public int delCouSecByCouId(int couId) {
		String sql = "DELETE FROM t_cou_section " +
				" USING t_cou_chapter, t_cou_section " +
				" WHERE t_cou_chapter.cou_id = ? AND t_cou_chapter.cha_id = t_cou_section.cha_id";
		
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

	/* (non-Javadoc)
	 * @see com.wikestudy.model.dao.CouSectionDao#queryCouSection(int)
	 */
	@Override
	public List<CouSection> queryCouSectionUrl(int couId) {
		
		String sql = "SELECT cs.sec_id, cs.sec_name, cs.sec_number, cs.cha_id FROM "
				+ "t_cou_chapter AS cc, t_cou_section AS cs WHERE cc.cou_id = ? AND cs.cha_id = cc.cha_id ORDER BY cs.sec_number";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, couId);
			
			return gdb.query(CouSection.class, pstmt); 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public List<CouSection> queryCouSection(int couId) {
		
		String sql = "SELECT cs.sec_id, cs.sec_name, cs.sec_number, cs.cha_id , cs.sec_video_url FROM "
				+ "t_cou_chapter AS cc, t_cou_section AS cs WHERE cc.cou_id = ? AND cs.cha_id = cc.cha_id ORDER BY cs.sec_number";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, couId);
			
			return gdb.query(CouSection.class, pstmt); 
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@Override
	public CouSection queryCouSectionBySec(int secId) throws Exception{
		String sql = "SELECT * FROM t_cou_section WHERE sec_id=?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, secId);
		
		List<CouSection> list =  gdb.query(CouSection.class, pstmt);
		
		if(list.isEmpty())
			return null;
		
		return list.get(0);
	}

	@Override // 全
	public int updataCouSection(int secId, CouSection couSection)
			throws Exception {
		String sql = "UPDATE t_cou_section SET cha_id=?, sec_number=?, sec_video_url=?, sec_name=?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, couSection.getChaId());
		pstmt.setString(2, couSection.getSecNumber());
		pstmt.setString(3, couSection.getSecVideoUrl());
		pstmt.setString(4, couSection.getSecName());
		
		return gdb.update(pstmt);
	}
	
	@Override
	public List<CouSection> queryCouSectionByCha(int chaId) {
		
		String sql = "SELECT * FROM t_cou_section WHERE cha_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			pstmt.setInt(1, chaId);
			
			return gdb.query(CouSection.class, pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int[] updateCouSectionS(List<CouSection> csList) {
		// 管理员课程管理——信息修改
		String sqlString = "UPDATE t_cou_section SET sec_number=?, sec_name=? WHERE sec_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sqlString);
			
			for (int i = 0 ; i < csList.size(); i++) {
				CouSection cSection = csList.get(i);
				
				pstmt.setString(1, cSection.getSecNumber());
				pstmt.setString(2, cSection.getSecName());
				pstmt.setInt(3, cSection.getSecId());
				
				pstmt.addBatch();
			}
			
			return pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public int updateSecVideo(int secId, String url) {
		
		String sqlString = "UPDATE t_cou_section SET sec_video_url = ? WHERE sec_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sqlString);
			
			pstmt.setString(1, url);
			pstmt.setInt(2, secId);
			
			return gdb.update(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}
	
	@Override
	public int insertCouSecList(List<CouSection> csList) {
		if (csList.size() == 0)
			return 0;
		
		StringBuffer sB = new StringBuffer("INSERT INTO t_cou_section (cha_id, sec_number, sec_video_url, sec_name) VALUES ");
		
		for (CouSection cs : csList) {
			
			sB.append("(" + cs.getChaId() + ",'" + cs.getSecNumber() + "','" + cs.getSecVideoUrl() + "','" + cs.getSecName() + "') ,");
		}
		
		
		sB.deleteCharAt(sB.length() - 1);
		
		try {
			pstmt = conn.prepareStatement(sB.toString());
			
			System.out.println(pstmt);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public int[] delCouSecBySecList(List<Integer> csIdlist) {
		
		
		String sqlString = "DELETE FROM t_cou_section WHERE sec_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sqlString);
			
			for (Integer i : csIdlist) {
				pstmt.setInt(1, i);
				
				pstmt.addBatch();
			}
					
			return pstmt.executeBatch();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return null;
		
	}

	@Override
	public void queryCouMedia(int chaId, int secId,
			CouChapter cc, CouSection cs) throws Exception {
		
		String sql="select cc.* ,cs.* from  "
				+ " t_cou_chapter as cc, t_cou_section as cs"
				+ " where cc.cha_id=? and cs.sec_id=?";
		
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setInt(1, chaId);
		
		pstmt.setInt(2, secId);
		
		ResultSet rs=pstmt.executeQuery();
		
	/*	ccl= new GenneralDbconn<CouChapter>().query(CouChapter.class, pstmt);
		csl= new GenneralDbconn<CouSection>().query(CouSection.class, pstmt);*/
	
		while(rs.next()) {
			cc.setChaId(rs.getInt("cha_id"));
			cc.setChaIntro(rs.getString("cha_intro"));
			cc.setChaName(rs.getString("cha_name"));
			cc.setChaNumber(rs.getInt("cha_number"));

			cs.setChaId(rs.getInt("cha_id"));
			cs.setSecId(rs.getInt("sec_id"));
			cs.setSecName(rs.getString("sec_name"));
			cs.setSecNumber(rs.getString("sec_number"));
			cs.setSecVideoUrl(rs.getString("sec_video_url"));
			
		}
	
	}


	@Override
	public String getMediaUrl(int secId) throws Exception {
		String sql="select sec_video_url from t_cou_section where sec_id=?" ;
		PreparedStatement pstmt=conn.prepareStatement(sql);
		
		pstmt.setInt(1, secId);
		
		ResultSet rs=pstmt.executeQuery();
		if(rs.next()) {
			return rs.getString("sec_video_url");
		} else {
			return "no video";
		}
	}
}
