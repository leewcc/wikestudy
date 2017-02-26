package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.CouChapter;

// 课程章节接口
public interface CouChapterDao {
	/**
	 * 插入课程章节信息
	 * @param couChapter-要新增的课程章节信息
	 * @return true; fasle
	 * @throws Exception
	 */
	public int  insertCouChapter(CouChapter couChapter)
		throws Exception;
	
	/**
	 * 根据课程Id, 删除该课程下所有章节信息
	 * @param couId-要删除的课程Id
	 * @return int[] 数组
	 * @throws Exception
	 */
	public int[] delCouChapter(List<Integer> chaId); 
	

	
	/**
	 * 查询课程下的所有章节信息
	 * @param couId-要查询的课程Id
	 * @return List<CouChapter>-查询的课程章节信息集合
	 * @throws Exception
	 */
	public List<CouChapter> queryCouChapter(int couId);
	
	/**
	 * 根据章节Id更新章节信息
	 * @param chaId-章节Id
	 * @param couChapter-要更新的章节信息
	 * @return true; false
	 * @throws Exception
	 */
	public int updataCouChapter(int chaId, CouChapter couChapter)
		throws Exception;
	
	
	public List<Integer> querySecListByCouId(int couId);
	
	/**
	 * 批量更新课程下所有章节信息
	 * @param ccList
	 * @return
	 */
	public int[] updateCouChapterS(List<CouChapter> ccList) ;
	
	
	/**
	 * 根据couId删除章节
	 * @param couId
	 * @return
	 */
	public int delCouChaByCouId(int couId);

	/**
	 * 批量插入章节
	 * @param ccList
	 * @return
	 */
	public List<Integer> insertCouChaAll(List<CouChapter> ccList);

	/**
	 * 根据chaId查询章节资料
	 * @author CHEN
	 * @param chaId
	 * @return
	 */
	public CouChapter queryCouChapterById(int chaId);
}
