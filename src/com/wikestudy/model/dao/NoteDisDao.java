package com.wikestudy.model.dao;

import java.sql.Timestamp;
import java.util.List;

import com.wikestudy.model.pojo.DiscussView;
import com.wikestudy.model.pojo.NoteCourse;
import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.NoteView;
import com.wikestudy.model.pojo.PageElem;

// 课程笔记 评论接口
public interface NoteDisDao {
	
	/**
	 * 新增笔记 评论
	 * @param noteDis-新增笔记,评论的信息
	 * @return true; false
	 * @throws Exception
	 */
	public int insertNoteDis(NoteDis noteDis) throws Exception;
	
	/**
	 * 根据Id删除笔记or评论
	 * @param nDId-要删除的笔记/评论Id
	 * @return true; false
	 * @throws Exception
	 */
	public int delNoteDisBynDId(int nDId) throws Exception;
	
	/**
	 * 删除该课时下的所有笔记
	 * @param secId
	 * @return
	 * @throws Exception
	 */
	public int delNoteDisBysecId(int secId) throws Exception;
	
	
	
	/**
	 * 根据笔记/评论Id更新笔记/评论信息
	 * @param nDId-要更新的Id
	 * @param noteDis-要更新的内容
	 * @return true; false
	 * @throws Exception
	 */
	public int updataNoteDis(int nDId, NoteDis noteDis)
		throws Exception;
	
	/**
	 * 更新笔记内容
	 * @param nDId
	 * @param content
	 * @return
	 */
	public int updataNoteContent(int nDId, String content, Timestamp data);
	// 根据课时
	
	public NoteDis queryNote(int id) throws Exception;
	
	/**
	 * 根据课时Id, 查询该课时下的笔记或评论(实现要传递参数)
	 * @param secId
	 * @param page
	 * @return List<NoteDis>-查询的笔记/评论集合
	 * @throws Exception
	 */
	public PageElem<NoteDis> queryNoteDis(int secId, PageElem<NoteDis> pe, 
			boolean flag)
		throws Exception;
	
	
	/**
	 * 分页查询用户的全部笔记
	 * @param stuId-用户Id
	 * @param page-当前页数
	 * @return List<NoteDis>-该页数的笔记集合
	 * @throws Exception
	 */
	public PageElem<NoteView> queryNoteAll(int stuId, PageElem<NoteView> pe)
			throws Exception;

	// 根据用户的课程
	/**
	 *  根据用户课程查询该课程下全部笔记——分页
	 * @param stuId
	 * @param secIdList
	 * @param page
	 * @return
	 * @throws Exception
	 */
	PageElem<NoteView> queryNoteCou(int stuId, int couId,
			PageElem<NoteView> pe, String sortType);
	
	// 视图
	/**
	 * 查询笔记目录By 课程为单位
	 * @param stuId
	 * @return
	 * @throws Exception
	 */
	public List<NoteCourse> queryNoteDia(int stuId)
		throws Exception;
	
	public List<NoteView> queryNoteByCou(int stuId, int couId, String sort, 
			PageElem<NoteView> pe) throws Exception;
	
	/**
	 * 根据课程找出评论
	 * @param couId
	 * @return
	 */
	public PageElem<DiscussView> queryDisByCouId(int couId,PageElem<DiscussView> pe);

	
	/**
	 * 课程id来删除
	 * @param couId
	 * @return
	 * @throws Exception
	 */
	public int delNoteByCouId(int couId);

	
	/**
	 * 返回课时评论的详情
	 * @param secId
	 * @param pe
	 * @return
	 * @throws Exception
	 */
	public PageElem<NoteDis> querySecComment(int secId, PageElem<NoteDis> pe)
			throws Exception;

	public PageElem<NoteDis> querySecComment(int secId, PageElem<NoteDis> pe,boolean isNote) throws Exception ;
}
