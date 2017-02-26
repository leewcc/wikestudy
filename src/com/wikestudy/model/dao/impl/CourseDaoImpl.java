package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.wikestudy.model.dao.CourseDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.Course;
import com.wikestudy.model.pojo.PageElem;

public class CourseDaoImpl implements CourseDao {
	
	private PreparedStatement pstmt = null;
	private GenneralDbconn<Course> dbconn = null;
	private Connection conn = null;
	
	public CourseDaoImpl(Connection conn) {
		this.conn = conn;
		dbconn = new GenneralDbconn<Course>();
	}
	
	@Override
	public int insertCourse(Course cou)  {
		
		String sql = "INSERT INTO t_course " + 
		" (teacher_id, label_id, cou_name, cou_brief, cou_grade, cou_pric_url, cou_rele_time) " +
		" VALUES (?, ?, ?, ?, ?, ?, ?)";
		
		try {
			pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			pstmt.setInt(1, cou.getTeacherId());
			pstmt.setInt(2 , cou.getLabelId());
			pstmt.setString(3, cou.getCouName());
			pstmt.setString(4, cou.getCouBrief());
			pstmt.setString(5, cou.getCouGrade());
			pstmt.setString(6, cou.getCouPricUrl());
			pstmt.setTimestamp(7, cou.getCouReleTime());
			System.out.println("insertCourse pstmt:" + pstmt);
			return new GenneralDbconn<Course>().insert(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CourseDaoImpl insertCourse update is wrong");
		}
		
		return -1;
	}

	@Override
	public int updateCourse(int couId, Course cou) {
		String sql = "UPDATE t_course SET label_id=?, cou_name=?, cou_brief=?, " +
	"cou_anno=?,  cou_grade=?, cou_pric_url=?  where cou_id=?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1 , cou.getLabelId());
			pstmt.setString(2, cou.getCouName());
			pstmt.setString(3, cou.getCouBrief());
			pstmt.setString(4, cou.getCouAnno());
			pstmt.setString(5, cou.getCouGrade());
			pstmt.setString(6, cou.getCouPricUrl());
			pstmt.setInt(7, couId);
			
			return new GenneralDbconn<Course>().update(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
			 
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		
		return -1;
	}
	
	@Override
	public int updateCouStudyNum(int couId, boolean flag) throws Exception{
		String sql = null;
		
		//flag为true, 则为自增操作
		if (flag == true)
			sql = "UPDATE t_course SET cou_study_num = cou_study_num+1 WHERE cou_id = ?";
		else
			sql = "UPDATE t_course SET cou_study_num = cou_study_num -1 WHERE cou_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, couId);
		
		return new GenneralDbconn<Course>().update(pstmt);
	}
	
	@Override
	public int updateCouRelease(int couId, boolean flag) {
		String sql = null;
		try {
			if (flag == true) {
				sql = "UPDATE t_course SET cou_rele_time = ?, cou_release = 1 WHERE cou_id = ?";
			
				pstmt = conn.prepareStatement(sql);
				
				SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
				
				pstmt.setString(1, df.format(new java.util.Date()));
				pstmt.setInt(2, couId);
			
			} else {
				sql = "UPDATE t_course SET cou_release = 0 WHERE cou_id = ?";
				
				pstmt = conn.prepareStatement(sql);
				pstmt.setInt(1, couId);
			}
			
			return new GenneralDbconn<Course>().update(pstmt);
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	// 删除课程应该提供
	@Override
	public int delCourse(int couId) {
		
		String sql = "DELETE FROM t_course WHERE cou_id = ?";
		
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
	public Course queryCourseById(int couId) {
		// 你在这里光拿课程: 却没有考虑到课程不存在的情况: get(0) 是错误的
		String sql = "SELECT *, (SELECT r.rec_id FROM t_course AS c1, t_cou_recommend "
				+ "AS r WHERE c1.cou_id = r.cou_id AND c1.cou_id = c.cou_id) AS recomment "
				+ "FROM t_course as c where cou_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			List<Course> couList = new GenneralDbconn<Course>().query(Course.class, pstmt);
			if (couList.size() > 0) {
				return couList.get(0);	
			}
					
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (Exception e ) {
			e.printStackTrace();
		}
		
		System.out.println("当前还未创建课程");
		return null;
		
	}

	@Override
	public PageElem<Course> queryCourseAll(PageElem<Course> pe, boolean flag) throws Exception {
		
		GenneralDbconn<Course> gdb = new GenneralDbconn<Course>();
		
		String sqlF = "SELECT  count(cou_id)  as rows  FROM t_course WHERE cou_release = 1";
		
		pstmt = conn.prepareStatement(sqlF);
		
		pe.setRows(gdb.getRows(pstmt));
		
		String sqlS = null;
		// 如果标志为true, 则默认根据活跃度由高到低拿取课程
		if (flag == true)
			sqlS = "SELECT cou_name, cou_study_num, cou_updated, cou_pric_url "
					+ "FROM t_course where cou_release = 1 ORDER BY cou_study_num DESC limit ?, ?";
		else // 否则根据创建时间从近期开始拿取
			sqlS = "SELECT cou_name, cou_study_num, cou_updated, cou_pric_url "
					+ "FROM t_course where cou_release = 1 ORDER BY cou_rele_time DESC limit ?, ?";
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1,pe.getStartSearch());
		pstmt.setInt(2, pe.getPageShow());
		
		pe.setPageElem(gdb.query(Course.class, pstmt));
		
		return pe;
	}

	@Override
	public PageElem<Course> queryCourseSort(int labelId, PageElem<Course> pe, String type, String couGrade) throws Exception {
		
		GenneralDbconn<Course> gdb = new GenneralDbconn<Course>();
		
		String sqlF = null;
		
		// 查询全部
		if (labelId == 0 && couGrade.equals("0")) {
			sqlF = "SELECT count(cou_id) as rows FROM t_course WHERE cou_release = 1";
			pstmt = conn.prepareStatement(sqlF);
		}
		// 只根据年级查询
		else if (labelId == 0 && !couGrade.equals("0")) {
			sqlF = "SELECT  count(cou_id)  as rows FROM t_course WHERE cou_release = 1 AND cou_grade= ?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setString(1, couGrade);
		}
		// 只根据课程类型查询
		else if (labelId !=0 && couGrade.equals("0")) {
			sqlF = "SELECT  count(cou_id)  as rows FROM t_course WHERE cou_release = 1 AND label_id = ?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1,  labelId);
		}
		// 二者都根据
		else {
			sqlF = "SELECT  count(cou_id)  as rows FROM t_course WHERE cou_release = 1 AND label_id = ? AND cou_grade=?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, labelId);
			pstmt.setString(2, couGrade);
		}
		
		// 得到总行数
		pe.setRows(gdb.getRows(pstmt));
		if (pe.getCurrentPage() > pe.getTotalPage()) {
			// 防止请求页数大于总页数
			pe.setCurrentPage(1);
		}
		
		String sqlS = "SELECT cou_id, cou_name, cou_study_num, cou_updated, cou_pric_url, cou_brief,cou_grade ";
		
		// 查询全部
		if (labelId == 0 && couGrade.equals("0")) {
			sqlS += " FROM t_course WHERE cou_release = 1";
		}
		// 只根据年级查询
		else if (labelId == 0 && !couGrade.equals("0")) {
			sqlS += " FROM t_course WHERE cou_release = 1 AND cou_grade= ?";
		}
		// 只根据课程类型查询
		else if (labelId !=0 && couGrade.equals("0")) {
			sqlS += " FROM t_course WHERE cou_release = 1 AND label_id = ?";
		}
		// 二者都根据
		else {
			sqlS += " FROM t_course WHERE cou_release = 1 AND label_id = ? AND cou_grade=?";
		}
		
		System.out.println(sqlS);
		// flag为true， 则根据活跃度来查询
		if (type.equals("3"))
			sqlS += " ORDER BY cou_study_num DESC limit ?, ?";
		else if (type.equals("2"))// 根据发布最新时间来查询
			sqlS += " ORDER BY cou_rele_time DESC limit ?, ?";
		
		pstmt = conn.prepareStatement(sqlS);
		
		
		if (labelId == 0 && couGrade.equals("0")) {
			pstmt.setInt(1,pe.getStartSearch());
			pstmt.setInt(2, pe.getPageShow());
		}
		// 只根据年级查询
		else if (labelId == 0 && !couGrade.equals("0")) {
			pstmt.setString(1, couGrade);
			pstmt.setInt(2,pe.getStartSearch());
			pstmt.setInt(3, pe.getPageShow());
		}
		// 只根据课程类型查询
		else if (labelId !=0 && couGrade.equals("0")) {
			pstmt.setInt(1, labelId);
			pstmt.setInt(2,pe.getStartSearch());
			pstmt.setInt(3, pe.getPageShow());
		}
		// 二者都根据
		else {
			pstmt.setInt(1, labelId);
			pstmt.setString(2, couGrade);
			pstmt.setInt(3,pe.getStartSearch());
			pstmt.setInt(4, pe.getPageShow());
		}
		System.out.println(sqlS);
		pe.setPageElem(gdb.query(Course.class, pstmt));
		
		return pe;
	}
	
	@Override
	public PageElem<Course> queryCourseTea(int teacherId, int flag, PageElem<Course> pe)  {
		// 管理员课程管理——课程查询页面
		
		GenneralDbconn<Course> gdb = new GenneralDbconn<Course>();
		
		String sqlF = null;
		
		String sql = null;
		
		
		try {
			if (flag == 1) {
				//如果flag 为1, 则为未发布
				sqlF = "SELECT count(cou_id) as rows FROM t_course WHERE teacher_id = ? AND cou_release = 0";
				sql = "SELECT cou_id, cou_name, cou_study_num, cou_pric_url, cou_release, cou_updated FROM t_course " +
			"WHERE teacher_id = ? AND cou_release = 0 limit ? , ? ";
			}
			else if (flag == 2) {
				// flag = 2, 则已发布
				sqlF = "SELECT count(cou_id) as rows FROM t_course WHERE teacher_id = ? AND cou_release = 1";
				sql = "SELECT cou_id,cou_name, cou_study_num, cou_pric_url, cou_release,cou_updated FROM t_course " +
			"WHERE teacher_id = ? AND cou_release = 1 limit ? , ?";
			}
			
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, teacherId);
			
			pe.setRows(gdb.getRows(pstmt));
			
			
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, teacherId);
			pstmt.setInt(2, pe.getStartSearch());
			pstmt.setInt(3, pe.getPageShow());
			System.out.println(pstmt);
			pe.setPageElem(gdb.query(Course.class, pstmt));
		} catch (SQLException e) {
			e.printStackTrace();
			
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("CourseDaoImpl queryCourseTea gdb is wrong");
		}
		
		return pe;
	}

	@Override
	public PageElem<Course> queryCourseStu(List<Integer> couIdList,
			PageElem<Course> pe) throws Exception {
		if (couIdList.size() == 0) {
			pe.setPageElem(new ArrayList<Course>());
			return pe;
			
		}
			
		StringBuffer sql = new StringBuffer("SELECT * FROM t_course WHERE cou_id IN ( ");
		
		for (Integer couId : couIdList) {
			sql.append(couId + ",");
		}
		
		sql.deleteCharAt(sql.length()-1);
		sql.append(")");
		
		pstmt = conn.prepareStatement(sql.toString());
		
		System.out.println(pstmt);
		pe.setPageElem(new GenneralDbconn<Course>().query(Course.class, pstmt));
	
		return pe;
	}

	@Override
	public PageElem<Course> queryCourseSugg(PageElem<Course> pe, int labelId,
			String grade) throws Exception {
		
		String sqlF = null;

		if (grade.equals("0")  && labelId == 0) {
			// 无条件查询
			sqlF = "SELECT count(cou.cou_id) as rows FROM t_cou_recommend AS rec, t_course AS cou WHERE rec.cou_id = cou.cou_id";
			pstmt = conn.prepareStatement(sqlF);
		} 
		else if (!grade.equals("0") && labelId == 0) {
			// 只根据年级查询
			sqlF = "SELECT count(cou.cou_id) as rows FROM t_cou_recommend AS rec, t_course AS cou "
					+ "WHERE rec.cou_id = cou.cou_id AND rec.rec_grade = ?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setString(1, grade);
		}
		else if (grade.equals("0") && labelId != 0) {
			// 只根据科目查询
			sqlF = "SELECT count(cou.cou_id) as rows FROM t_cou_recommend AS rec, t_course AS cou "
					+ "WHERE rec.cou_id = cou.cou_id AND cou.label_id = ?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, labelId);
		}
		else {
			// 既根据科目又根据年级查询
			sqlF = "SELECT count(cou.cou_id) as rows FROM t_cou_recommend AS rec, t_course AS cou "
					+ "WHERE rec.cou_id = cou.cou_id AND cou.label_id = ? AND rec.rec_grade = ?";
			
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, labelId);
			pstmt.setString(2, grade);
		}
		
		GenneralDbconn<Course> gdb = new GenneralDbconn<Course>();
		
		pe.setRows(gdb.getRows(pstmt));
		
		if (grade.equals("0")  && labelId == 0) {
			// 无条件查询
			sqlF = "SELECT cou.cou_id, cou.cou_name, cou.cou_study_num, cou.cou_updated, "
					+ "cou.cou_pric_url, cou.cou_brief FROM t_cou_recommend AS rec, t_course AS cou"
					+ " WHERE rec.cou_id = cou.cou_id limit ?, ?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, pe.getStartSearch());
			pstmt.setInt(2, pe.getPageShow());
		} 
		else if (!grade.equals("0") && labelId == 0) {
			// 只根据年级查询
			sqlF = "SELECT  cou.cou_id, cou.cou_name, cou.cou_study_num, cou.cou_updated, "
					+ "cou.cou_pric_url, cou.cou_brief FROM t_cou_recommend AS rec, t_course AS cou "
					+ "WHERE rec.cou_id = cou.cou_id AND rec.rec_grade = ? limit ?, ?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setString(1, grade);
			pstmt.setInt(2, pe.getStartSearch());
			pstmt.setInt(3, pe.getPageShow());
		}
		else if (grade.equals("0") && labelId != 0) {
			// 只根据科目查询
			sqlF = "SELECT cou.cou_id, cou.cou_name, cou.cou_study_num, cou.cou_updated, "
					+ "cou.cou_pric_url, cou.cou_brief FROM t_cou_recommend AS rec, t_course AS cou "
					+ "WHERE rec.cou_id = cou.cou_id AND cou.label_id = ? limit ?, ?";
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, labelId);
			pstmt.setInt(2, pe.getStartSearch());
			pstmt.setInt(3, pe.getPageShow());
		}
		else {
			// 既根据科目又根据年级查询
			sqlF = "SELECT cou.cou_id, cou.cou_name, cou.cou_study_num, cou.cou_updated, "
					+ "cou.cou_pric_url, cou.cou_brief FROM t_cou_recommend AS rec, t_course AS cou "
					+ "WHERE rec.cou_id = cou.cou_id AND cou.label_id = ? AND rec.rec_grade = ? limit ?, ?";
			
			pstmt = conn.prepareStatement(sqlF);
			pstmt.setInt(1, labelId);
			pstmt.setString(2, grade);
			pstmt.setInt(3, pe.getStartSearch());
			pstmt.setInt(4, pe.getPageShow());
		}
		
		pe.setPageElem(gdb.query(Course.class, pstmt));
		
		return pe;
	}
	
	
	private String installDesc(boolean flag, String sql) {
		//排序标志为1，则按课程学习人数排序
		if(!flag)
			return sql += " ORDER BY cou_study_num DESC, cou_release desc limit ?, ?";
		
		
		//否则，按课程发布时间排序
		else 
			return sql += " ORDER BY cou_rele_time DESC, cou_release desc  limit ?, ?";
	}
	
	
	public PageElem<Course> queryCourseAll(boolean flag, PageElem<Course> pageElem, boolean isRelease) throws Exception{
		String release = "";
		if(isRelease)
		release = "where cou_release = 1 ";
		
		
		//第一步：查询行数
		String sql = "SELECT COUNT(cou_id) AS ROWS FROM t_course " + release;
		pstmt = conn.prepareStatement(sql);
		pageElem.setRows(dbconn.getRows(pstmt));
		
		
		//第二步：编写获取数据的SQL
		sql = "SELECT *, (SELECT r.rec_id FROM t_course AS c1, t_cou_recommend AS r WHERE c1.cou_id = r.cou_id "
				+ "AND c1.cou_id = c.cou_id) AS recomment "
				+ "FROM t_course as c " + release;
		sql = installDesc(flag, sql);		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, pageElem.getStartSearch());
		pstmt.setInt(2, pageElem.getPageShow());
		pageElem.setPageElem(dbconn.query(Course.class, pstmt));
		
		
		return pageElem;
	}
	
	
	public PageElem<Course> queryByGrade(boolean flag, String grade, PageElem<Course> pageElem, boolean isRelease) throws Exception{
		String release = "";
		if(isRelease)
		release = "and cou_release = 1 ";
				
		
		// 第一步：查询行数
		String sql = "SELECT COUNT(cou_id) AS ROWS FROM t_course WHERE cou_grade = ? " + release;
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade);
		pageElem.setRows(dbconn.getRows(pstmt));

		
		// 第二步：编写获取数据的SQL
		sql = "SELECT *, (SELECT r.rec_id FROM t_course AS c1, t_cou_recommend AS r WHERE c1.cou_id = r.cou_id "
				+ "AND c1.cou_id = c.cou_id) AS recomment "
				+ "FROM t_course as c WHERE cou_grade = ? " + release;
		sql = installDesc(flag, sql);	
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, grade);
		pstmt.setInt(2, pageElem.getStartSearch());
		pstmt.setInt(3, pageElem.getPageShow());
		pageElem.setPageElem(dbconn.query(Course.class, pstmt));

		
		return pageElem;
	}
	
	
	public PageElem<Course> queryByLabel(boolean flag, int labId, PageElem<Course> pageElem, boolean isRelease) throws Exception{
		String release = "";
		if(isRelease)
		release = "and cou_release = 1 ";
		
		
		// 第一步：查询行数
		String sql = "SELECT COUNT(cou_id) AS ROWS FROM t_course where label_id = ?   "+ release ;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, labId);
		pageElem.setRows(dbconn.getRows(pstmt));

		
		// 第二步：编写获取数据的SQL
		sql = "SELECT *, (SELECT r.rec_id FROM t_course AS c1, t_cou_recommend AS r WHERE c1.cou_id = r.cou_id "
				+ "AND c1.cou_id = c.cou_id) AS recomment FROM t_course as c where label_id = ? " + release;
		sql = installDesc(flag, sql);	
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, labId);
		pstmt.setInt(2, pageElem.getStartSearch());
		pstmt.setInt(3, pageElem.getPageShow());
		pageElem.setPageElem(dbconn.query(Course.class, pstmt));

		
		return pageElem;
	}
	
	
	public PageElem<Course> queryByGradeAndLabel(boolean flag, int labId, String grade, PageElem<Course> pageElem, boolean isRelease) throws Exception{
		String release = "";
		if(isRelease)
		release = "and cou_release = 1 ";
		
		
		// 第一步：查询行数
		String sql = "SELECT COUNT(cou_id) AS ROWS FROM t_course where label_id = ? and cou_grade = ?  " + release;
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, labId);
		pstmt.setString(2, grade);
		pageElem.setRows(dbconn.getRows(pstmt));
		

		// 第二步：编写获取数据的SQL
		sql = "SELECT *,(SELECT r.rec_id FROM t_course AS c1, t_cou_recommend AS r WHERE c1.cou_id = r.cou_id "
				+ "AND c1.cou_id = c.cou_id) AS recomment  FROM t_course as c where label_id = ? and "
				+ "cou_grade = ? " + release;
		sql = installDesc(flag, sql);	
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1, labId);
		pstmt.setString(2, grade);
		pstmt.setInt(3, pageElem.getStartSearch());
		pstmt.setInt(4, pageElem.getPageShow());
		pageElem.setPageElem(dbconn.query(Course.class, pstmt));
		

		return pageElem;
	}

	@Override
	public PageElem<Course> queryCourseByKey(String key, PageElem<Course> pe) {
		
		String sql = "SELECT cou_id, cou_name, cou_pric_url, cou_study_num, cou_updated FROM t_course WHERE cou_name LIKE ? LIMIT ?,?";
		ResultSet rs = null;
		ResultSet rs2 = null;
		String sqlS = "SELECT COUNT(cou_id) AS rows FROM t_course WHERE cou_name LIKE ?";
		try {
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setString(1, "%"+key+"%");
		pstmt.setInt(2, pe.getStartSearch());
		pstmt.setInt(3, pe.getPageShow());
		
		rs = pstmt.executeQuery();
		
		Course cou = null;
		List<Course> list = new LinkedList<Course>();
		while(rs.next()) {
			cou = new Course();
			cou.setCouId(rs.getInt(1));
			cou.setCouName(rs.getString(2));
			cou.setCouPricUrl(rs.getString(3));
			cou.setCouStudyNum(rs.getInt(4));
			cou.setCouUpdated(rs.getBoolean(5));
			
			list.add(cou);
		}
		
		pe.setPageElem(list);
		
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setString(1, "%"+key+"%");
		rs2 = pstmt.executeQuery();
		
		while(rs.next()) {
			pe.setRows(rs.getInt(1));
		}
		
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) 
				try {
					rs.close();
					rs2.close();
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
		}
		
		return pe;
	}

	@Override
	public int updateStudyNum(int couId) {
		
		String sql = "UPDATE t_course SET cou_study_num = cou_study_num + 1 WHERE cou_id = ?";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			
			return pstmt.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (pstmt != null)
					pstmt.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return -1;
	}
	@Override
	public boolean updateCoursePhoto(int couId, String photoUrl) throws SQLException {
		String sql="UPDATE t_course SET cou_pric_url=? WHERE cou_id =?" ;
	
		PreparedStatement pstmt= conn.prepareStatement(sql);
		
		pstmt.setString(1, photoUrl);
		
		pstmt.setInt(2, couId);
		
		if(pstmt.executeUpdate()>0) {
			pstmt.close();
			return true;
		}
		
		pstmt.close();
		return false;
		
		
	}
	
	
}
