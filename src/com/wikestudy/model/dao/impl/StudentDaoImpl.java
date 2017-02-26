package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.StudentDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;

public class StudentDaoImpl implements StudentDao {
	private Connection conn;
	
	public StudentDaoImpl(Connection conn) {
		this.conn = conn;
	}
	
	@Override
	public List<Integer> addStudent(List<Student> students) throws Exception {
		String sql = "INSERT INTO t_student (stu_number, stu_name, stu_grade) VALUES (?, ?, ?)";
		
		PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		Iterator<Student> it = students.iterator();
		
		Student s = null;
		
		while(it.hasNext()) {
			s = it.next();
			
			pstmt.setString(1, s.getStuNumber());
			
			pstmt.setString(2, s.getStuName());
			
			pstmt.setString(3, s.getStuGrade());
			
			pstmt.addBatch();
		}
		
		return new GenneralDbconn<Student>().insertBatch(pstmt);
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
	
	@Override
	public int deleteStudent(int id) throws Exception {	
		String sql = "UPDATE t_student SET stu_delete = 0  WHERE stu_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		return new GenneralDbconn<Student>().update(pstmt);

	}

	@Override
	public int updateStudent(Student student) throws Exception {
		String sql = "UPDATE t_student SET stu_number = ? ,stu_name = ?, stu_grade = ?, "
				+ "stu_class = ?, stu_gender = ?,stu_signature = ? WHERE stu_id = ?  ";
		
		PreparedStatement  pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, student.getStuNumber());
		pstmt.setString(2, student.getStuName());
		pstmt.setString(3, student.getStuGrade());
		pstmt.setString(4, student.getStuClass());
		pstmt.setString(5, student.getStuGender());
		pstmt.setString(6, student.getStuSignature());
		pstmt.setInt(7, student.getStuId());
		
		return new GenneralDbconn<Student>().update(pstmt);
	}

	@Override
	public int updatePassword(int id, String np, String op) throws Exception {
		String sql  =" UPDATE t_student SET stu_password = ? WHERE stu_id = ? and stu_password = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, np);
		
		pstmt.setInt(2, id);
		
		pstmt.setString(3, op);
		
		return new GenneralDbconn<Student>().update(pstmt);
	}

	public int updatePassword(int sid, String npassword) throws Exception{
		String sql  =" UPDATE t_student SET stu_password = ? WHERE stu_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, npassword);
		
		pstmt.setInt(2, sid);
		
		return new GenneralDbconn<Student>().update(pstmt);
	}
	
	@Override
	public int updatePhoto(int id, String photoUrl) throws Exception {
		String sql = "  UPDATE t_student SET stu_photo_url = ? WHERE stu_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, photoUrl);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Student>().update(pstmt);
	}

	@Override
	public int updateSignature(int id, String signature) throws Exception {
		String sql = "UPDATE t_student SET stu_signature = ? WHERE stu_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, signature);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Student>().update(pstmt);
	}

	@Override
	public int updateDisscussNum(int id, int disscussNum) throws Exception {
		String sql = " UPDATE t_student SET stu_discussion_num = stu_discussion_num + ? WHERE stu_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, disscussNum);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Student>().update(pstmt);
	}

	@Override
	public int updateStudyHour(int id, long studyHour) throws Exception {
		String sql = "UPDATE t_student SET stu_study_hour = stu_study_hour + ? WHERE stu_id = ?";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setLong(1, studyHour);
		
		pstmt.setInt(2, id);
		
		return new GenneralDbconn<Student>().update(pstmt);
	}

	@Override
	public int updateGrade(List<Integer> ids, String Grade) throws Exception {
		if(ids.size() <= 0)
			return 0;
		
		String sql = "UPDATE t_student SET stu_grade = ? WHERE stu_id IN " + installId(ids.size());
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, Grade);
		
		return new GenneralDbconn<Student>().update(pstmt);
	}

	
	public int updateGrade() throws Exception{
		String sql = "update t_student set stu_delete = 0 , stu_grade = '5' where stu_grade = '4'";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.addBatch(sql);
		
		sql = "update t_student set stu_delete = 0 , stu_grade = '4' where stu_grade = '3'";
		
		pstmt.addBatch(sql);
		
		sql = "update t_student set stu_grade = '3' where stu_grade = '2'";
		
		pstmt.addBatch(sql);
		
		sql = "update t_student set stu_grade = '2' where stu_grade = '1'";
		
		pstmt.addBatch(sql);
		
		return new GenneralDbconn<Student>().batch(pstmt).length;
		
	}
	
	public int rollBackGrade() throws Exception {
		String sql = "update t_student set stu_grade = '1' where stu_grade = '2'";
		
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.addBatch(sql);
		
		sql = "update t_student set stu_grade = '2' where stu_grade = '3'";
		
		
		
		pstmt.addBatch(sql);
		
		sql = "update t_student set stu_delete = 1 , stu_grade = '3' where stu_grade = '4'";
		
		pstmt.addBatch(sql);
			
		return new GenneralDbconn<Student>().batch(pstmt).length;
	}
	
	@Override
	public Student queryById(int id) throws Exception {
		String sql = "SELECT * FROM t_student WHERE stu_id = ? AND stu_delete = 1";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		List<Student> list = new GenneralDbconn<Student>().query(Student.class, pstmt);
		
		if(list.size() <= 0)
			return null;
		
		return list.get(0);
	}

	@Override
	public Student queryByNumber(String number) throws Exception {
		String sql = "SELECT * FROM t_student WHERE stu_number = ? AND stu_delete = 1 ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, number);
		
		List<Student> list = new GenneralDbconn<Student>().query(Student.class, pstmt);
		
		if(list.size() <= 0)
			return null;
		
		return list.get(0);
	}

	public PageElem<Student> queryByGradeAndClass(String grade, String stuClass, PageElem<Student> pageElem) throws Exception{
		// 获取总记录数
		String sql = "SELECT COUNT(*) AS ROWS FROM t_student WHERE stu_grade = ? AND stu_class = ? AND stu_delete = 1";

		PreparedStatement pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, grade);
		
		pstmt.setString(2, stuClass);

		GenneralDbconn<Student> dbconn = new GenneralDbconn<Student>();

		int rows = dbconn.getRows(pstmt);

		pageElem.setRows(rows);

		// 获取对应页数的数据记录
		sql = "SELECT * FROM t_student WHERE stu_grade = ? AND stu_class = ? AND stu_delete = 1 LIMIT ?, ?";

		pstmt = conn.prepareStatement(sql);

		pstmt.setString(1, grade);
		
		pstmt.setString(2, stuClass);

		pstmt.setInt(3, pageElem.getStartSearch());

		pstmt.setInt(4, pageElem.getPageShow());

		List<Student> list = dbconn.query(Student.class, pstmt);

		pageElem.setPageElem(list);

		return pageElem;
	}
	
	@Override
	public PageElem<Student> queryByGrade(String grade, PageElem<Student> pageElem)
			throws Exception {
		//获取总记录数
		String sql = "SELECT COUNT(*) AS ROWS FROM t_student WHERE stu_grade = ? AND stu_delete = 1";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, grade);
		
		GenneralDbconn<Student> dbconn = new GenneralDbconn<Student>();
		
		int rows = dbconn.getRows(pstmt);
		
		pageElem.setRows(rows);
		
		//获取对应页数的数据记录
		sql = "SELECT * FROM t_student WHERE stu_grade = ? AND stu_delete = 1 LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, grade);
		
		pstmt.setInt(2, pageElem.getStartSearch());
		
		pstmt.setInt(3, pageElem.getPageShow());
		
		List<Student> list = dbconn.query(Student.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}

	@Override
	public PageElem<Student> queryByStudy(PageElem<Student> pageElem)
			throws Exception {
		//获取总记录数
		String sql = "SELECT COUNT(*) AS ROWS FROM t_student WHERE stu_delete = 1";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		GenneralDbconn<Student> dbconn = new GenneralDbconn<Student>();
		
		int rows = dbconn.getRows(pstmt);
		
		pageElem.setRows(rows);
		
		//获取对应页数的数据记录
		sql = "SELECT * ROWS FROM t_student WHERE stu_delete = 1 ORDER BY stu_study_hour "
				+ "AND stu_discussion_num LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, pageElem.getStartSearch());
		
		pstmt.setInt(2, pageElem.getPageShow());
		
		List<Student> list = dbconn.query(Student.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}

	@Override
	public PageElem<Student> queryWithout(List<Integer> ids, PageElem<Student> pageElem) throws Exception {
		int count = ids.size();
		
		String idS =  installId(count);
		
		String sql = "SELECT COUNT(*) AS ROWS FROM t_student WHERE stu_delete = 1 and stu_id not in "+ idS ;
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		
		
		for(int i = 1; i <= count; i++)
			pstmt.setInt(i, ids.get(i - 1));
		
		GenneralDbconn<Student> dbconn = new GenneralDbconn<Student>();
		
		int rows = dbconn.getRows(pstmt);
		
		pageElem.setRows(rows);
		
		//获取对应页数的数据记录
		sql = "SELECT * FROM t_student WHERE stu_delete = 1 and stu_id not in "+ idS + "ORDER BY stu_study_hour "
				+ "AND stu_discussion_num LIMIT ?, ?";
		
		pstmt = conn.prepareStatement(sql);
		
		for(int i = 1; i <= count; i++)
			pstmt.setInt(i, ids.get(i - 1));
		
		pstmt.setInt(count + 1, pageElem.getStartSearch());
		
		pstmt.setInt(count + 2, pageElem.getPageShow());
		
		List<Student> list = dbconn.query(Student.class, pstmt);
		
		pageElem.setPageElem(list);
		
		return pageElem;
	}

}
