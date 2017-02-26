package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;

import com.wikestudy.model.dao.NoteDisDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.DiscussView;
import com.wikestudy.model.pojo.NoteCourse;
import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.NoteView;
import com.wikestudy.model.pojo.PageElem;

public class NoteDisDaoImpl implements NoteDisDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private GenneralDbconn<NoteDis> gdb = null;
	
	public NoteDisDaoImpl(Connection conn) {
		this.conn = conn;
		gdb = new GenneralDbconn<NoteDis>();
	}

	@Override // 全插入
	public int insertNoteDis(NoteDis noteDis) throws Exception {
		String sql = "INSERT INTO t_note_dis (stu_id, sec_id, n_d_content,"
				+ "n_d_rele_time, n_d_mark) VALUES (?,?,?,?,?)";
		
		pstmt = conn.prepareStatement(sql);
				
		pstmt.setInt(1, noteDis.getStuId());
		pstmt.setInt(2, noteDis.getSecId());
		pstmt.setString(3, noteDis.getNDContent());
		pstmt.setTimestamp(4,Timestamp.valueOf( noteDis.getNDReleTime()));
		System.out.println(noteDis.getNDReleTime());
		pstmt.setBoolean(5, noteDis.isNDMark());
		
		return gdb.update(pstmt);
	}

	@Override
	public int delNoteDisBynDId(int nDId) throws Exception {
		String sql = "DELETE FROM t_note_dis WHERE n_d_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, nDId);
		
		return gdb.update(pstmt);
	}

	@Override
	public int delNoteDisBysecId(int secId) throws Exception {
		String sql = "DELETE FROM t_note_dis WHERE sec_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, secId);
		
		return gdb.update(pstmt);
	}

	@Override
	public int delNoteByCouId(int couId) {
		// 删除笔记
		String sql = "DELETE FROM t_note_dis "
				+ " USING t_note_dis, t_cou_chapter, t_cou_section "
				+ " WHERE t_cou_chapter.cou_id = ? AND t_cou_chapter.cha_id = t_cou_section.cha_id "
				+ " AND t_note_dis.sec_id = t_cou_section.sec_id AND n_d_mark = 0";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	@Override // 全
	public int updataNoteDis(int nDId, NoteDis noteDis) throws Exception {
		String sql = "UPDATE t_note_dis SET stu_id=?, sec_id=?, n_d_content=?, n_d_rele_time=?, n_d_mark=? "
				+ "WHERE n_d_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, noteDis.getStuId());
		pstmt.setInt(2, noteDis.getSecId());
		pstmt.setString(3, noteDis.getNDContent());
		pstmt.setTimestamp(4,Timestamp.valueOf( noteDis.getNDReleTime()));
		pstmt.setBoolean(5, noteDis.isNDMark());
		
		return gdb.update(pstmt);
	}

	@Override
	public NoteDis queryNote(int id) throws Exception {
		String sql = "select * from t_note_dis where n_d_id = ?";
				
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		List<NoteDis> notes =  gdb.query(NoteDis.class, pstmt);
		
		if(notes.isEmpty())
			return null;
		
		return notes.get(0);
	}
	
	@Override // 按课时查询
	public PageElem<NoteDis> queryNoteDis(int secId, PageElem<NoteDis> pe,
			boolean flag) throws Exception {
		
		String sqlF = null;
		String sqlS = null;
		
		// flag 为true, 则查询评论
		if (flag == true) {
			sqlS = "SELECT * FROM t_note_dis WHERE sec_id = ? AND n_d_mark=1 ORDER BY n_d_rele_time DESC LIMIT ?, ?";
			sqlF = "SELECT COUNT(n_d_id) as rows FROM t_note_dis WHERE sec_id = ? AND n_d_mark =1";
		}
		else {
			sqlF = "SELECT COUNT(n_d_id) as rows FROM t_note_dis WHERE sec_id = ? AND n_d_mark =0";
			sqlS = "SELECT * FROM t_note_dis WHERE sec_id = ? AND n_d_mark=0 ORDER BY n_d_rele_time DESC LIMIT ?, ?";
		}
		
		pstmt = conn.prepareStatement(sqlF);
		
		pstmt.setInt(1, secId);
		
		pe.setRows(gdb.getRows(pstmt));
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1, secId);
		pstmt.setInt(2, pe.getStartSearch());
		pstmt.setInt(3, pe.getPageShow());
		
		pe.setPageElem(gdb.query(NoteDis.class, pstmt));
		
		return pe;
	}

	@Override // 返回评论的所有内容
	public PageElem<NoteDis> querySecComment(int secId, PageElem<NoteDis> pe) throws Exception {
		
		String sqlF = null;
		String sqlS = null;
		
		sqlS = "SELECT t_note_dis.* ,t_student.stu_name as stuName ,t_student.stu_photo_url as stuPhoto FROM t_note_dis , t_student WHERE  t_note_dis.stu_id=t_student.stu_id AND t_note_dis.sec_id = ? AND t_note_dis.n_d_mark=1  ORDER BY t_note_dis.n_d_rele_time DESC LIMIT ?, ?";
		sqlF = "SELECT COUNT(n_d_id) as rows FROM t_note_dis WHERE sec_id = ? AND n_d_mark =1";		
		pstmt = conn.prepareStatement(sqlF);
		
		pstmt.setInt(1, secId);
		
		pe.setRows(gdb.getRows(pstmt));
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1, secId);
		pstmt.setInt(2, 0);//从第一条开始
		pstmt.setInt(3, (pe.getPageShow()*pe.getCurrentPage()));
		
		pe.setPageElem(gdb.query(NoteDis.class, pstmt));
		
		return pe;
	}
	
	@Override // 根据发布时间
	public PageElem<NoteView> queryNoteAll(int stuId, PageElem<NoteView> pe)
			throws Exception {
		GenneralDbconn<NoteView> gdbNV = new GenneralDbconn<NoteView>();
		
		String sqlF = "SELECT COUNT(n_d_id) AS rows FROM t_note_dis  WHERE stu_id = ? AND n_d_mark = 0";
		
		String sqlS = "SELECT nd.n_d_id, nd.n_d_content, nd.n_d_rele_time,cs.sec_name, cs.sec_number"
				+ " FROM t_note_dis AS nd, t_cou_section AS cs "
				+ " WHERE stu_id = ? AND n_d_mark = 0 AND nd.sec_id = cs.sec_id "
				+ " ORDER BY n_d_rele_time DESC LIMIT ?,?";
		
		pstmt = conn.prepareStatement(sqlF);
		
		pstmt.setInt(1, stuId);
		System.out.println(pstmt);
		pe.setRows(gdbNV.getRows(pstmt));
		if (pe.getCurrentPage() > pe.getTotalPage()) 
			pe.setCurrentPage(pe.getTotalPage());
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1, stuId);
		pstmt.setInt(2, pe.getStartSearch());
		pstmt.setInt(3, pe.getPageShow());
		System.out.println(pstmt);
		pe.setPageElem(gdbNV.query(NoteView.class, pstmt));
		
		return pe;
	}

	@Override // 根据发布时间 且以课程为单位
	public PageElem<NoteView> queryNoteCou(int stuId, int couId,
			PageElem<NoteView> pe, String sortType){
		GenneralDbconn<NoteView> gdbNoteView = new GenneralDbconn<NoteView>();
		
		String sqlF = "SELECT COUNT(nd.n_d_id) AS rows "
				+ " FROM t_note_dis AS nd, t_cou_chapter AS cc, t_cou_section AS cs "
				+ " WHERE nd.stu_id=? AND nd.n_d_mark = 0"
				+ " AND cc.cou_id = ? AND cc.cha_id = cs.cha_id AND cs.sec_id = nd.sec_id ";
		
		try {
			pstmt = conn.prepareStatement(sqlF);
			
			pstmt.setInt(1, stuId);
			pstmt.setInt(2, couId);
			
			
			pe.setRows(gdbNoteView.getRows(pstmt));
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 做一个页面检测, 防止页数查询超出范围
		if (pe.getCurrentPage() > pe.getTotalPage())
			pe.setCurrentPage(pe.getTotalPage());
		
		String sqlS = "SELECT nd.n_d_id, nd.n_d_content, nd.n_d_rele_time, cs.sec_name, cs.sec_number "
				+ "FROM t_note_dis AS nd, t_cou_section as cs, t_cou_chapter AS cc "
				+ " WHERE nd.n_d_mark = 0 AND nd.stu_id = ? AND cc.cou_id = ? AND cc.cha_id = cs.cha_id AND cs.sec_id = nd.sec_id ";
		if (sortType.equals("1")) // 章节顺序排序
			sqlS += " ORDER BY cs.sec_number DESC LIMIT ?, ?";
		else // 根据发布时间最新发布
			sqlS += " ORDER BY nd.n_d_rele_time DESC LIMIT ?, ?";
		
		try {
			pstmt = conn.prepareStatement(sqlS);
			
			pstmt.setInt(1, stuId);
			pstmt.setInt(2, couId);
			pstmt.setInt(3, pe.getStartSearch());
			pstmt.setInt(4, pe.getPageShow());

			pe.setPageElem(gdbNoteView.query(NoteView.class, pstmt));
			
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pe;
	}

	@Override //查询学生课程信息
	public List<NoteCourse> queryNoteDia(int stuId)
			{
		
		List<NoteCourse> ncList = new LinkedList<NoteCourse>();
		ResultSet rs = null;
		
		String sqlF = "SELECT c.cou_id, c.cou_name,c.cou_pric_url FROM "
				+ " t_course AS c, t_stu_course AS sc"
				+ " WHERE sc.cou_id = c.cou_id  AND sc.stu_id=?";
		try {
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, stuId);
			rs = pstmt.executeQuery();
			while(rs.next()) {
				NoteCourse nc = new NoteCourse();
				nc.setCouId(rs.getInt("c.cou_id"));
				nc.setCouName(rs.getString("c.cou_name"));
				nc.setCouPricUrl(rs.getString("c.cou_pric_url"));
				ncList.add(nc);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		String sqlS = "SELECT COUNT(nd.n_d_id) AS rows"
				+ " FROM t_note_dis AS nd, t_cou_chapter AS cc, t_cou_section AS cs "
				+ " WHERE cc.cou_id = ? AND cc.cha_id = cs.cha_id "
				+ " AND cs.sec_id = nd.sec_id AND nd.stu_id = ? AND nd.n_d_mark = 0";
		try {
			pstmt = conn.prepareStatement(sqlS);
			for (NoteCourse nc : ncList) {
				pstmt.setInt(1, nc.getCouId());
				pstmt.setInt(2, stuId);
				rs = pstmt.executeQuery();
				while(rs.next()) {
					nc.setNoteNumber(rs.getInt("rows"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}finally {
			try {
				rs.close();
				pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
		return ncList;
	}

	@Override
	public List<NoteView> queryNoteByCou(int stuId, int couId, String sort,
			PageElem<NoteView> pe) throws Exception {
		
		return null;
	}

	@Override
	public PageElem<DiscussView> queryDisByCouId(int couId, PageElem<DiscussView> pe) {
		String sqlF = "SELECT COUNT(nd.n_d_id) AS rows FROM t_note_dis as nd, "
				+ "t_cou_chapter as cc, t_cou_section as cs "
				+ "WHERE cc.cou_id = ? AND cc.cha_id = cs.cha_id AND cs.sec_id = nd.sec_id AND nd.n_d_mark = 1";
		try {
		pstmt = conn.prepareStatement(sqlF);
		
		pstmt.setInt(1, couId);
		System.out.println(pstmt);
		GenneralDbconn<DiscussView> gdbView = new GenneralDbconn<DiscussView>();
		
		pe.setRows(gdbView.getRows(pstmt));
		
		String sqlS = " SELECT nd.n_d_id, nd.stu_id, nd.sec_id, nd.n_d_content, nd.n_d_rele_time, nd.n_d_mark,"
				+ " stu.stu_name, stu.stu_photo_url, cs.sec_number, cs.sec_name "
				+ " FROM t_note_dis as nd, t_student as stu, t_cou_section as cs, t_cou_chapter as cc"
				+ " WHERE cc.cou_id = ? AND cc.cha_id = cs.cha_id AND cs.sec_id = nd.sec_id AND nd.n_d_mark = 1"
				+ "  ORDER BY n_d_rele_time DESC limit ?, ?";
		
		pstmt = conn.prepareStatement(sqlS);
		pstmt.setInt(1, couId);
		pstmt.setInt(2, pe.getStartSearch());
		pstmt.setInt(3, pe.getPageShow());
		System.out.println(pstmt);
		
		pe.setPageElem(gdbView.query(DiscussView.class, pstmt));
		
		}catch(SQLException e) {
			e.printStackTrace();
		}catch(Exception e) {
			e.printStackTrace();
		}
		return pe;
	}

	@Override
	public int updataNoteContent(int nDId, String content, Timestamp data) {
		
		String sql = "UPDATE  t_note_dis SET n_d_content = ?, n_d_rele_time = ?"
				+ " WHERE n_d_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setString(1, content);
			pstmt.setTimestamp(2, data);
			pstmt.setInt(3, nDId);
			
			return gdb.update(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return 0;
	}

	@Override
	public PageElem<NoteDis> querySecComment(int secId, PageElem<NoteDis> pe, boolean isNote) throws Exception {

		String sqlF = null;
		String sqlS = null;
		
		sqlS = "SELECT t_note_dis.* ,t_student.stu_name as stuName ,t_student.stu_photo_url as stuPhoto FROM t_note_dis , t_student WHERE  t_note_dis.stu_id=t_student.stu_id AND t_note_dis.sec_id = ? AND t_note_dis.n_d_mark=?  ORDER BY t_note_dis.n_d_rele_time DESC LIMIT ?, ?";
		sqlF = "SELECT COUNT(n_d_id) as rows FROM t_note_dis WHERE sec_id = ? AND n_d_mark =?";		
		pstmt = conn.prepareStatement(sqlF);
		
		pstmt.setInt(1, secId);
		pstmt.setBoolean(2, isNote);
		pe.setRows(gdb.getRows(pstmt));
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1, secId);
		pstmt.setBoolean(2, isNote);
		pstmt.setInt(3, 0);//从第一条开始
		pstmt.setInt(4, (pe.getPageShow()*pe.getCurrentPage()));
		
		
		pe.setPageElem(gdb.query(NoteDis.class, pstmt));
		
		return pe;
	}
	
	
}
