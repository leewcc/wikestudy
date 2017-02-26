package com.wikestudy.model.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;



import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import com.wikestudy.model.dao.TopicDao;
import com.wikestudy.model.dao.dbconn.GenneralDbconn;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Topic;

public class TopicDaoImpl implements TopicDao{
	private PreparedStatement pstmt = null;
	private Connection conn = null;
	private GenneralDbconn<Topic> gdb = null;
	
	public TopicDaoImpl(Connection conn) {
		this.conn = conn;
		gdb = new GenneralDbconn<Topic>();
	}

	@Override
	public int insertTopic(Topic topic) throws Exception {

		String sql = "INSERT INTO t_topic (top_user_id, top_sec_id, lab_id,"
						+ "top_user_enum, top_tit, top_time, top_con, top_is_up) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
		
		pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		pstmt.setInt(1, topic.getTopUserId());
		pstmt.setInt(2, topic.getTopSecId());
		pstmt.setInt(3, topic.getLabId());
		pstmt.setBoolean(4, topic.isTopUserEnum());
		pstmt.setString(5, topic.getTopTit());
		pstmt.setTimestamp(6, topic.getTopTime());
		pstmt.setString(7, topic.getTopCon());
		pstmt.setBoolean(8, topic.isTopIsUp());
		
		return gdb.insert(pstmt);
	}

	@Override
	public int deleteTopic(int topId) throws Exception {
		String sql = "DELETE FROM t_topic WHERE top_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topId);
		
		return gdb.update(pstmt);
	}
	
	public int deleteTopicByUser(int id, boolean type) throws Exception {
		String sql = "DELETE FROM t_topic WHERE top_user_id = ? AND top_user_enum = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		pstmt.setBoolean(2, type);
		
		return gdb.update(pstmt);
	}

	public int updataRead(int topId, int readNum) throws Exception{
		String sql = "update t_topic set top_read_num = top_read_num + ? where top_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, readNum);
		
		pstmt.setInt(2, topId);
		
		return gdb.update(pstmt);
	}
	
	public int updateAnswer(int topId, int answerNum) throws Exception{
		String sql = "update t_topic set top_ans_num = top_ans_num + ? where top_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, answerNum);
		
		pstmt.setInt(2, topId);
		
		return gdb.update(pstmt);
	}
	
	public int setUp(int topId, boolean isUp) throws Exception{
		String sql = "UPDATE t_topic SET top_is_up = ? WHERE top_id = ? ";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setBoolean(1, isUp);
		
		pstmt.setInt(2, topId);
		
		return gdb.update(pstmt);
	}
	
	@Override
	public int updateTopic(int topId, Topic topic) throws Exception {
		String sql = "UPDATE t_topic SET (top_user_id=?, top_sec_id=?,"
				+ " lab_id=?, top_user_enum=?, top_tit=?, top_time=?, top_con=?, top_read_num=?,"
				+ "top_ans_num =?, top_is_up=?)";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topic.getTopUserId());
		pstmt.setInt(2, topic.getTopSecId());
		pstmt.setInt(3, topic.getLabId());
		pstmt.setBoolean(4, topic.isTopUserEnum());
		pstmt.setString(5, topic.getTopTit());
		pstmt.setTimestamp(6, topic.getTopTime());
		pstmt.setString(7, topic.getTopCon());
		pstmt.setInt(8, topic.getTopReadNum());
		pstmt.setInt(9, topic.getTopAnsNum());
		pstmt.setBoolean(10, topic.isTopIsUp());
				
		return gdb.update(pstmt);
	}

	public Topic queryById(int topId)throws Exception{
		String sql = "SELECT t1.*,s.stu_name AS user_name,s.stu_photo_url AS photo,l.lab_name AS label_name FROM t_topic AS t1 "
				+ "INNER JOIN t_student AS s ON stu_delete = 1 AND t1.top_user_id = s.stu_id AND t1.top_user_enum = 0  "
				+ "INNER JOIN t_label AS l USING(lab_id) "
				+ "WHERE  t1.top_id = ?  "
				+ "UNION all "
				+ "SELECT t1.*,tea.tea_name AS user_name,tea.tea_photo_url AS photo,l.lab_name AS label_name FROM t_topic AS t1 "
				+ "INNER JOIN t_teacher AS tea ON tea_delete = 1 AND t1.top_user_id = tea.tea_id  AND t1.top_user_enum = 1  "
				+ "INNER JOIN t_label AS l USING(lab_id) "
				+ "WHERE t1.top_id = ? ";
		
		PreparedStatement pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topId);
		pstmt.setInt(2, topId);	
		
		List<Topic> list = new GenneralDbconn<Topic>().query(Topic.class, pstmt);
		
		if(list.isEmpty())
			return null;
		
		return list.get(0);
	}
	
	@Override
	public PageElem<Topic> queryTopicByUser(int topUserId, boolean topUserEnum, PageElem<Topic> pe)
			throws Exception {
		String sql = "SELECT COUNT(*) AS ROWS FROM t_topic AS t "
				+ "INNER JOIN t_student AS s ON t.top_user_id = s.stu_id "
				+ "INNER JOIN t_label AS l ON t.lab_id = l.lab_id "
				+ "WHERE top_user_id=? AND top_user_enum = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, topUserId);
		
		pstmt.setBoolean(2, topUserEnum);
		
		pe.setRows(gdb.getRows(pstmt));
		
		if(!topUserEnum)
			sql = "SELECT t.*,stu_name AS user_name,stu_photo_url AS photo,lab_name AS label_name FROM t_topic AS t "
					+ "INNER JOIN t_student AS s ON t.top_user_id = s.stu_id "
					+ "INNER JOIN t_label AS l ON t.lab_id = l.lab_id "
					+ "WHERE top_user_enum = ? AND top_user_id = ? "
					+ "ORDER BY top_time DESC LIMIT ?,? ";
		
		else
			sql =  "SELECT t.*,tea.tea_name AS user_name,tea.tea_photo_url AS photo,l.lab_name AS label_name FROM t_topic AS t "
					+ "INNER JOIN t_teacher AS tea ON t.top_user_id = tea.tea_id "
					+ "INNER JOIN t_label AS l ON t.lab_id = l.lab_id "
					+ "WHERE top_user_enum = ? AND top_user_id = ? "
					+ "ORDER BY top_time DESC LIMIT ?,?";
			
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setBoolean(1, topUserEnum);	
		pstmt.setInt(2, topUserId);
		pstmt.setInt(3, pe.getStartSearch());
		pstmt.setInt(4, pe.getPageShow());
		
		pe.setPageElem(gdb.query(Topic.class, pstmt));
		
		return pe;
	}

	@Override
	public PageElem<Topic> queryTopicByLabel(int labId, int flag, PageElem<Topic>pe)
			throws Exception {
		String sqlF = "SELECT COUNT(top_id) as rows  FROM t_topic WHERE lab_id = ?";
		
		pstmt = conn.prepareStatement(sqlF);
		
		pstmt.setInt(1, labId);
		
		pe.setRows(gdb.getRows(pstmt));
		
		String sqlS = null;
		if (flag == 1) {
			sqlS = "SELECT * FROM t_topic WHERE lab_id =? "
					+ "ORDER BY top_is_up DESC, top_time DESC LIMIT ?, ?";
		} else if (flag == 2) {
			sqlS = "SELECT * FROM t_topic WHERE lab_id =? "
					+ "ORDER BY top_is_up DESC, top_time DESC LIMIT ?, ?";
		} else if (flag == 3) {
			sqlS = "SELECT * FROM t_topic WHERE lab_id =? AND top_ans_number = 0"
					+ "ORDER BY top_is_up DESC, top_time DESC LIMIT ?, ?";
		}
		
		pstmt = conn.prepareStatement(sqlS);
		pstmt.setInt(1,  labId);
		pstmt.setInt(2, pe.getStartSearch());
		pstmt.setInt(3, pe.getPageShow());
		
	    pe.setPageElem(gdb.query(Topic.class, pstmt));
	    
	    return pe;
	}
	
	@Override
	public PageElem<Topic> queryTopicAll(int flag, PageElem<Topic>pe) throws Exception {
		String sqlF = "SELECT COUNT(top_id) as rows FROM t_topic";
		
		pstmt = conn.prepareStatement(sqlF);
		
		pe.setRows(gdb.getRows(pstmt)); 
		
		String sqlS = null;
		if (flag == 1) {
			sqlS = "SELECT * FROM t_topic "
					+ "ORDER BY top_is_up DESC, top_time DESC LIMIT ?, ?";
		} else if (flag == 2) {
			sqlS = "SELECT * FROM t_topic "
					+ "ORDER BY top_is_up DESC, top_time DESC LIMIT ?, ?";
		} else if (flag == 3) {
			sqlS = "SELECT * FROM t_topic WHERE  top_ans_number = 0"
					+ "ORDER BY top_is_up DESC, top_time DESC LIMIT ?, ?";
		}
		
		pstmt = conn.prepareStatement(sqlS);
		
		pstmt.setInt(1, pe.getStartSearch());
		pstmt.setInt(2, pe.getPageShow());
		
	    pe.setPageElem(gdb.query(Topic.class, pstmt));
	    
	    return pe;
	}
	
	private int getAllRows() throws Exception {
		int row = 0;
		
		String sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic, t_student WHERE top_user_enum = 0 "
				+ "and top_user_id = stu_id  AND stu_delete = 1";
		
//		String sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic, t_student WHERE top_user_id = stu_id AND "
//				+ "top_user_enum = 0 AND stu_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		row += gdb.getRows(pstmt);
		
		sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic, t_teacher WHERE top_user_enum = 1 "
				+ "and top_user_id = tea_id AND  tea_delete = 1";
		
//		sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic, t_teacher WHERE top_user_id = tea_id AND "
//				+ "top_user_enum = 1 AND tea_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		row += gdb.getRows(pstmt);
		
		return row;
	}
	
	public PageElem<Topic> queryAll(int flag, PageElem<Topic> pageElem) throws Exception{
		pageElem.setRows(getAllRows()); 
		
		String sql = "(SELECT t1.*,s.stu_name AS user_name,s.stu_photo_url AS photo,l.lab_name AS label_name "
				+ "FROM t_topic AS t1 "
				+ "INNER JOIN t_student AS s ON t1.top_user_id = s.stu_id AND stu_delete = 1 "
				+ "INNER JOIN t_label AS l USING(lab_id) "
				+ "WHERE t1.top_user_enum = 0 limit ?,?) "
				+ "UNION ALL "
				+ "(SELECT t1.*,tea.tea_name AS user_name,tea.tea_photo_url AS photo,l.lab_name AS label_name "
				+ "FROM t_topic AS t1 "
				+ "INNER JOIN t_teacher AS tea ON t1.top_user_id = tea.tea_id  AND tea_delete = 1 "
				+ "INNER JOIN t_label AS l USING(lab_id) "
				+ "where t1.top_user_enum = 1 limit ?,?)";
		
		if(flag == 1)
			sql += "ORDER BY top_is_up DESC, top_read_num DESC, top_ans_num DESC";		
		else
			sql+= "ORDER BY top_is_up DESC,top_time DESC";
		
		sql += " LIMIT ?";
		
		pstmt = conn.prepareStatement(sql);		
		pstmt.setInt(1, pageElem.getStartSearch());
		pstmt.setInt(2, pageElem.getPageShow());	
		pstmt.setInt(3, pageElem.getStartSearch());
		pstmt.setInt(4, pageElem.getPageShow());
		pstmt.setInt(5, pageElem.getPageShow());
		pageElem.setPageElem(gdb.query(Topic.class, pstmt));	    
	    return pageElem;
	}
	
	private int getRowsByLabel(int id) throws Exception {
		int row = 0;
		
		String sql = "SELECT COUNT(top_id) as rows  FROM t_topic, t_student WHERE lab_id = ? and  "
				+ "top_user_id = stu_id AND top_user_enum = 0 AND stu_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		row += gdb.getRows(pstmt);
		
		sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic, t_teacher WHERE lab_id = ? and "
				+ "top_user_id = tea_id AND top_user_enum = 1 AND tea_delete = 1";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, id);
		
		row += gdb.getRows(pstmt);
		
		return row;
	}
	
	public PageElem<Topic> queryByLabel(int flag, int labId, PageElem<Topic> pageElem) throws Exception{
		pageElem.setRows(getRowsByLabel(labId));
		
		String sql = "(SELECT t1.*,s.stu_name AS user_name,s.stu_photo_url AS photo,l.lab_name AS label_name "
				+ "FROM t_topic AS t1 "
				+ "INNER JOIN t_student AS s ON t1.top_user_id = s.stu_id  AND stu_delete = 1  "
				+ "INNER JOIN t_label AS l ON t1.lab_id = l.lab_id "
				+ "WHERE t1.lab_id = ? AND t1.top_user_enum = 0 LIMIT ?,?) "
				+ "UNION ALL "
				+ "(SELECT t1.*,tea.tea_name AS user_name,tea.tea_photo_url AS photo,l.lab_name AS label_name "
				+ "FROM t_topic AS t1 "
				+ "INNER JOIN t_teacher AS tea ON t1.top_user_id = tea.tea_id  AND tea_delete = 1  "
				+ "INNER JOIN t_label AS l ON t1.lab_id = l.lab_id "
				+ "WHERE t1.lab_id = ? AND t1.top_user_enum = 1  LIMIT ?,?) ";
		
		if(flag == 1)
			sql += "ORDER BY top_is_up DESC, top_read_num DESC, top_ans_num DESC ";
		else
			sql+= "ORDER BY top_is_up DESC,top_time DESC ";
		
		sql += "LIMIT ?";
		
		pstmt = conn.prepareStatement(sql);
		pstmt.setInt(1,  labId);
		pstmt.setInt(2, pageElem.getStartSearch());
		pstmt.setInt(3, pageElem.getPageShow());
		pstmt.setInt(4,  labId);
		pstmt.setInt(5, pageElem.getStartSearch());
		pstmt.setInt(6, pageElem.getPageShow());
		pstmt.setInt(7, pageElem.getPageShow());
		pageElem.setPageElem(gdb.query(Topic.class, pstmt));
	    return pageElem;
	}
	
	@Override
	public int changeTopicByCouId(int couId) {
		// 删除话题
		String sql = "UPDATE t_topic "
				+ " SET (t_topic.top_sec_id = ?)"
				+ " WHERE t_cou_chapter.cou_id = ? AND t_cou_chapter.cha_id = t_cou_section.cha_id "
				+ " AND t_topic.top_sec_id = t_cou_section.sec_id";
		
		try {
			pstmt = conn.prepareStatement(sql);
			
			pstmt.setInt(1, couId);
			pstmt.setInt(2, 0);
			System.out.println(pstmt);
			return pstmt.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}
	
	public PageElem<Topic> queryTopicByCouId(int couId, PageElem<Topic> pe) {
		String sqlF = "SELECT COUNT(t.top_id) AS rows " 
				+ " FROM t_topic AS t, t_cou_chapter AS cc, t_cou_section AS cs "
				+ " WHERE cc.cou_id = ? AND cc.cha_id = cs.cha_id AND cs.sec_id = t.top_sec_id";
		
		
		
		
		String sqlS = "SELECT t.*,s.stu_name AS user_name, s.stu_photo_url AS photo "
				+ " FROM t_topic AS t, t_student AS s, t_cou_chapter AS cc, t_cou_section AS cs "
				+ " WHERE t.top_user_enum = 0 AND t.top_user_id = s.stu_id AND cc.cou_id = ? AND cc.cha_id = cs.cha_id AND cs.sec_id = t.top_sec_id "
				+ " UNION all "
				+ " SELECT t.*,tea.tea_name AS user_name,tea.tea_photo_url AS photo "
				+ " FROM t_topic AS t, t_teacher AS tea, t_cou_chapter AS cc, t_cou_section AS cs "
				+ " WHERE t.top_user_enum = 1 AND t.top_user_id = tea.tea_id AND cc.cou_id = ? AND cc.cha_id = cs.cha_id AND cs.sec_id = t.top_sec_id "
				+ " ORDER BY top_is_up DESC,top_time DESC LIMIT ?,?";
	
		PreparedStatement pstmt;
		
		try {
			pstmt = conn.prepareStatement(sqlF);
		
			pstmt.setInt(1, couId);
			System.out.println(pstmt);
			pe.setRows(gdb.getRows(pstmt));
			
			pstmt = conn.prepareStatement(sqlS);
			pstmt.setInt(1, couId);
			pstmt.setInt(2, couId);
			pstmt.setInt(3, pe.getStartSearch());
			pstmt.setInt(4, pe.getPageShow());
			System.out.println(pstmt);
			pe.setPageElem(gdb.query(Topic.class, pstmt));
		} catch (SQLException e) {
			
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		} 
		
		return pe;
	}
	
	private int getRowsByKey(String key) throws Exception {
		int rows = 0;
		String sql = "SELECT COUNT(*) AS ROWS FROM t_topic inner join t_student on top_user_id = stu_id "
				+ "and stu_delete = 1 WHERE top_user_enum = 0 and (top_tit LIKE ? OR top_con LIKE ?) ";
//		String sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic,t_student WHERE (top_tit LIKE ? OR top_con LIKE ?) "
//				+ "and top_user_id = stu_id and top_user_enum = 0 and stu_delete = 1 ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + key + "%");
		pstmt.setString(2, "%" + key + "%");
		rows += gdb.getRows(pstmt);
		
		 sql = "SELECT COUNT(*) AS ROWS FROM t_topic,t_teacher WHERE (top_tit LIKE ? OR top_con LIKE ?) "
					+ "and top_user_id = tea_id and top_user_enum = 1 and tea_delete = 1 ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + key + "%");
		pstmt.setString(2, "%" + key + "%");
		rows += gdb.getRows(pstmt);
		
		return rows;
	}
	
	public PageElem<Topic> queryByKey(String key, PageElem<Topic> pageElem) throws Exception{
		//第一步：	编写按标题和内容模糊搜索的SQL的个数语句,，执行获取个数
		pageElem.setRows(getRowsByKey(key));
		
		
		//第二步：	编写获取数据SQL，执行获取数据
		String sql = "(SELECT t1.*,s.stu_name AS user_name,s.stu_photo_url AS photo,l.lab_name AS label_name "
				+ "FROM t_topic AS t1 "
				+ "INNER JOIN t_student AS s ON t1.top_user_id = s.stu_id  AND stu_delete = 1 "
				+ "INNER JOIN t_label AS l ON t1.lab_id = l.lab_id "
				+ "WHERE t1.top_user_enum = 0 AND (t1.top_tit LIKE ? OR t1.top_con LIKE ?) LIMIT ?,?) "
				+ "UNION ALL "
				+ "(SELECT t1.*,tea.tea_name AS user_name,tea.tea_photo_url AS photo,l.lab_name AS label_name "
				+ "FROM t_topic AS t1 "
				+ "INNER JOIN t_teacher AS tea ON t1.top_user_id = tea.tea_id  AND tea_delete = 1 "
				+ "INNER JOIN t_label AS l ON t1.lab_id = l.lab_id "
				+ "WHERE t1.top_user_enum = 1 AND (t1.top_tit LIKE ? OR t1.top_con LIKE ?) LIMIT ?,?) "
				+ "ORDER BY top_is_up DESC,top_read_num DESC,top_ans_num DESC LIMIT ? ";
		pstmt = conn.prepareStatement(sql);
		pstmt.setString(1, "%" + key + "%");
		pstmt.setString(2, "%" + key + "%");
		pstmt.setInt(3, pageElem.getStartSearch());
		pstmt.setInt(4, pageElem.getPageShow());
		pstmt.setString(5, "%" + key + "%");
		pstmt.setString(6, "%" + key + "%");
		pstmt.setInt(7, pageElem.getStartSearch());
		pstmt.setInt(8, pageElem.getPageShow());
		pstmt.setInt(9, pageElem.getPageShow());
		pageElem.setPageElem(gdb.query(Topic.class, pstmt));
		
		
		return pageElem;
	}
	
	public int getRowsAll() throws Exception {
		String sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic ";
		
		pstmt = conn.prepareStatement(sql);
		
		return gdb.getRows(pstmt);
	}
	
	public List<Topic> queryAll(PageElem<Topic> pe) throws Exception {
		String sql = "select * from t_topic limit ?,?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, pe.getStartSearch());
		
		pstmt.setInt(2, pe.getPageShow());
		
		return gdb.query(Topic.class, pstmt);
	}
	
	
	public int getRowsLabel(int label) throws Exception {
		String sql = "SELECT COUNT(top_id) AS ROWS FROM t_topic where lab_id = ?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, label);
		
		return gdb.getRows(pstmt);
	}
	
	public List<Topic> queryByLabel(int label, PageElem<Topic> pe) throws Exception {
		String sql = "select * from t_topic where lab_id = ? limit ?,?";
		
		pstmt = conn.prepareStatement(sql);
		
		pstmt.setInt(1, label);
		
		pstmt.setInt(2, pe.getStartSearch());
		
		pstmt.setInt(3, pe.getPageShow());
		
		return gdb.query(Topic.class, pstmt);
	}
}
