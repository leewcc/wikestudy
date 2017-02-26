package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.enums.Grade;
import com.wikestudy.model.pojo.CouRecommend;

// 课程推荐表接口
public interface CouRecommendDao {
	
	/**
	 * 插入被推荐课程的信息
	 * @param couRecomment-推荐课程的信息
	 * @return true;  false
	 * @throws Exception
	 */
	public int insertCouRecommend(CouRecommend couRecomment)
		throws Exception;
	
	/**
	 * 根据课程Id删除推荐课程
	 * @param couId-要删除的课程Id
	 * @return true; false
	 * @throws Exception
	 */
	public int delCouRecommend (int couId);
	
	/**
	 * 根据年级查询推荐课程集合
	 * @param grade-查询年级
	 * @return List<CouRecommend>该年级推荐的课程
	 * @throws Exception
	 */
	public List<CouRecommend> queryCouRecommendByGrade(String grade)
		throws Exception;
	
	/**
	 *  根据课程Id，更新推荐课程信息
	 * @param couId-要更新的课程Id
	 * @param recGrade-要更新的推荐年级
	 * @return true; false;
	 * @throws Exception
	 */
	public int updataCouRecommend(int couId, String recGrade )
		throws Exception;
	
	
}
