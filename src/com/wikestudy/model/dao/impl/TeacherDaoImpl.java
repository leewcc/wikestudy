package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import sun.print.PeekGraphics;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;
import com.wikestudy.model.dao.TeacherDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.ArticleComment;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.MD5;

public class TeacherDaoImpl implements TeacherDao {
	Connection conn = null;

	public TeacherDaoImpl(Connection conn) {
		this.conn = conn;
	}

	private String installId(int count) throws Exception {
		StringBuilder sb = new StringBuilder("(");

		for (int i = 0; i < count; i++) {

			sb.append("?, ");

		}

		return sb.substring(0, sb.lastIndexOf(",")) + ")";

	}

	@Override
	public int[] addTeacher(List<Teacher> teachers) throws Exception {
		String sql = "insert into t_teacher (tea_type,tea_name,tea_number,tea_password,"
				+ "tea_gender,tea_introduction,tea_photo_url,tea_delete) values(?,?,?,?,?,?,?,?)";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		Iterator<Teacher> it = teachers.iterator();
		Teacher t = null;
		while (it.hasNext()) {
			t = it.next();
			pstmt.setBoolean(1, t.isTeaType());
			pstmt.setString(2, t.getTeaName());
			pstmt.setString(3, t.getTeaNumber());
			pstmt.setString(4, MD5.getMD5Code(t.getTeaPassword()));
			pstmt.setString(5, "男".equals(t.getTeaGender())?"male":"female");
			pstmt.setString(6, t.getTeaIntroduction());
			pstmt.setString(7, "");
			pstmt.setBoolean(8, true);
			pstmt.addBatch();
		}

		return new GenneralDbconn<Teacher>().batch(pstmt);
	}

	@Override
	public int delTeacher(List<Integer> teacherid) throws Exception {
		System.out.println("删除");
		if (teacherid.size() <= 0)
			return 0;
		String sql = "update t_teacher set tea_delete =0 where tea_id in "
				+ installId(teacherid.size());
		PreparedStatement pstmt = conn.prepareStatement(sql);
		Iterator<Integer> it = teacherid.iterator();
		for (int i = 1; it.hasNext(); i++) {
			pstmt.setInt(i, it.next());
		}
		return new GenneralDbconn<Teacher>().update(pstmt);
	}

	@Override
	public int[] updateTeacher(List<Teacher> teachers) throws Exception {
		String sql = "update t_teacher set tea_type=?, tea_number=?, tea_name=?,"
				+ " tea_password=?,"
				+ "tea_gender=?, tea_introduction=?, tea_photo_url=?, tea_delete=? "
				+ " where tea_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		for (Teacher t : teachers) {
			pstmt.setBoolean(1, t.isTeaType());
			pstmt.setString(2, t.getTeaNumber());
			pstmt.setString(3, t.getTeaName());
			pstmt.setString(4, t.getTeaPassword());
			pstmt.setString(5, t.getTeaGender());
			pstmt.setString(6, t.getTeaIntroduction());
			pstmt.setString(7, t.getTeaPortraitUrl());
			pstmt.setBoolean(8, t.isTeaDelete());
			pstmt.setInt(9, t.getTeaId());
			pstmt.addBatch();
		}
		return new GenneralDbconn<Teacher>().batch(pstmt);
	}

	@Override
	public int updateOneTeacher(Teacher t) throws Exception {
		String sql = "update t_teacher set tea_name=?, tea_gender=?, tea_introduction=? "
				+ " where tea_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, t.getTeaName());
			pstmt.setString(2, t.getTeaGender());
			pstmt.setString(3, t.getTeaIntroduction());
			pstmt.setInt(4, t.getTeaId());
		return new GenneralDbconn<Teacher>().update(pstmt);
	}

	
	@Override
	public int updatePassword(int teaid, String op,String np) throws Exception {
		String sql = "update t_teacher set tea_password=? where tea_id=? and tea_password=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, np);
		pstmt.setInt(2, teaid);
		pstmt.setString(3, op);

		return new GenneralDbconn<Teacher>().update(pstmt);
	}

	@Override
	public int updateIntroduction(int teaid, String teaintroduction)throws Exception {
		String sql = "update t_teacher set tea_Introduction=? where tea_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, teaintroduction);
		pstmt.setInt(2, teaid);

		return new GenneralDbconn<Teacher>().update(pstmt);
	}

	@Override
	public int updatePhotourl(int teaid, String photourl) throws Exception {
		String sql = "update t_teacher set tea_photo_url=? where tea_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, photourl);
		pstmt.setInt(2, teaid);

		return new GenneralDbconn<Teacher>().update(pstmt);
	}

	@Override
	public PageElem<Teacher> queryAllTeacher(PageElem<Teacher> pageElem)
			throws Exception {
		String sql = "SELECT COUNT(*) as rows FROM t_teacher WHERE tea_delete = 1";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		GenneralDbconn<Teacher> dbconn = new GenneralDbconn<Teacher>();
		
		int rows = dbconn.getRows(pstmt);

		pageElem.setRows(rows);

		sql = "select *  from t_teacher where tea_delete=1  limit ?,?";
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageElem.getStartSearch());
		pstmt.setInt(2, pageElem.getPageShow());
		List<Teacher> list = dbconn.query(Teacher.class, pstmt);
		pageElem.setPageElem(list);

		return pageElem;

	}

	@Override
	public boolean queryTeacherByName(String search,PageElem<Teacher> pageElem) throws Exception {
		String sql="select count(*) as rows from t_teacher where tea_delete=1";
		PreparedStatement pstmt=conn.prepareStatement(sql);
		GenneralDbconn<Teacher> dbconn = new GenneralDbconn<Teacher>();
		int rows=dbconn.getRows(pstmt);
		pageElem.setRows(rows);
		sql="select * from t_teacher where tea_delete=0 and tea_name like ? limit ?,? ";
		pstmt=conn.prepareStatement(sql);
		pstmt.setString(1, "%"+search+"%");
		pstmt.setInt(2, pageElem.getStartSearch());
		pstmt.setInt(3, pageElem.getPageShow());
		List<Teacher> list=dbconn.query(Teacher.class,pstmt);
		pageElem.setPageElem(list);
		return true;
	}
	
	@Override
	public Teacher queryTeacher(int teaid) throws Exception {
		String sql = "select * from t_teacher where tea_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teaid);
		List<Teacher> l=new GenneralDbconn<Teacher>().query(Teacher.class, pstmt);
		if(l.size()>0)
			return l.get(0);
			else return null;
	}
	@Override
	public Boolean queryTeacherByNum(String teanum) throws Exception {
		String sql="select * from t_teacher where tea_number=?"; 
		PreparedStatement pstmt =conn.prepareStatement(sql);
		pstmt.setString(1, teanum);
		List<Teacher> l=new GenneralDbconn<Teacher>().query(Teacher.class, pstmt);
		if(l.size()<0) 
			return true;
		else return false;
	}

	
	@Override
	public Teacher queryTeacherByNum(String teanum,String teapassword) throws Exception {
		String sql="select * from t_teacher where tea_number=? and tea_password=?"; 
		PreparedStatement pstmt =conn.prepareStatement(sql);
		pstmt.setString(1, teanum);
		pstmt.setString(2, teapassword);
		List<Teacher> t=new GenneralDbconn<Teacher>().query(Teacher.class, pstmt);
//		System.out.println(t.size());
//		if(t.size()!=0) return true;
//		else return false;
		if(t.size()>0)
		return t.get(0);
		else return null;
	}

	@Override
	public int ResetPassword(int teaid) throws Exception {
		String sql = "update t_teacher set tea_password=cf79ae6addba60ad018347359bd144d2 where tea_id=?";
		PreparedStatement pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, teaid);

		return new GenneralDbconn<Teacher>().update(pstmt);
	}

	@Override
	public List<String> queAllTeacherNum() throws Exception {
		String sql="select tea_number from t_teacher where tea_delete=1"; 
			PreparedStatement pstmt =conn.prepareStatement(sql);
			ResultSet rs=pstmt.executeQuery();
			List<String> l=new ArrayList<String>();
			while(rs.next()) {
				l.add(rs.getString("tea_number"));
			}
			return l;
	}

}
