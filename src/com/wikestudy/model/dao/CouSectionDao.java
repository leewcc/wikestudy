package com.wikestudy.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.CouSection;
import com.wikestudy.model.pojo.CourseInfor;

// 课程课时接口
public interface CouSectionDao {
	
	/**
	 * 插入课时信息
	 * @param couSection-新增的课时信息
	 * @return true;false
	 * @throws Exception
	 */
	public int insertCouSeciton(CouSection couSection)
		throws Exception;
	
	/**
	 * 根据课时Id删除课时信息
	 * @param secId-要删除课时的Id
	 * @return true;false
	 * @throws Exception
	 */
	public int delCouSection(int secId) throws Exception;
	
	
	
	/**
	 * 查询章节下的所有课时信息
	 * @param chaIdList-章节信息集合
	 * @return List<CouSection>-含有该课程所有章节全部课时信息对象
	 * @throws Exception
	 */
	public List<CouSection> queryCouSection(int couId);
	
	/**
	 *  根据课时id查询课时
	 * @param secId
	 * @return
	 * @throws Exception
	 */
	public CouSection queryCouSectionBySec(int secId)
			throws Exception;
	
	/**
	 * 根据章节id查询课时
	 * @param chaId
	 * @return 课时集合
	 */
	public List<CouSection> queryCouSectionByCha(int chaId);

	/**
	 * 根据课时Id，更新课时信息
	 * @param secId-要更新的课时Id
	 * @param couSection-要更新的课时内容
	 * @return true; false
	 * @throws Exception
	 */
	public int updataCouSection(int secId, CouSection couSection)
		throws Exception;
	
	/**
	 * 更新整个课程下的所有课时信息
	 * @param csList
	 * @return
	 */
	public int[] updateCouSectionS(List<CouSection> csList);
	
	/**
	 * 俊铭更新视频
	 * @param secId-课程id
	 * @param url-视频url
	 * @return 0-更新未成功; 1-更新成功
	 */
	public int updateSecVideo(int secId, String url);
	
	/**
	 * 根据couId删除课时
	 * @param couId
	 * @return
	 */
	public int delCouSecByCouId(int couId);

	/**
	 * 批量增加课时
	 * @param csList
	 * @return
	 */
	public int insertCouSecList(List<CouSection> csList);
	
	
	/**
	 * 批量删除课时
	 * @param csIdlist
	 * @return
	 */
	public int[] delCouSecBySecList(List<Integer> csIdlist);



	void queryCouMedia(int chaId, int secId, CouChapter cc, CouSection cs)
			throws Exception;

	public String getMediaUrl(int secId) throws Exception;

	List<CouSection> queryCouSectionUrl(int couId);
}
