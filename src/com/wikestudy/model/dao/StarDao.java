package com.wikestudy.model.dao;


import java.sql.Date;
import java.util.List;

import com.wikestudy.model.pojo.Star;

public interface StarDao {
	/**
	 * 功能描述:添加一周之星记录
	 * @param stars 一周之星集合,封装了哪些学生被选为一周之星
	 * @return 1-添加成功 0-添加失败
	 * @throws Exception
	 */
	public int[] addStar(List<Star> stars) throws Exception;
	
	/**
	 * 功能描述:通过一周之星id删除记录
	 * @param id 一周之星id
	 * @return 1-
	 * @throws Exception
	 */
	public int deleteStarById(int id) throws Exception;
	
	public int deleteStarByIds(List<Integer> ids) throws Exception;
	
	/**
	 * 功能描述:根据时间删除一周之星
	 * @param date 时间
	 * @return >0-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteStarByTime(Date date) throws Exception;
	
	/**
	 * 功能描述:根据指定时间获取所有一周之星
	 * @param date 时间
	 * @return	对应时间的一周之星
	 * @throws Exception
	 */
	public List<Star> queryByDate(Date date) throws Exception;
	
	/**
	 * 功能描述:获取对应位置指定数量的一周之星
	 * @return 所有一周之星的集合
	 * @throws Exception
	 */
	public List<Star> queryAll(int star, int num) throws Exception;
	
	public int queryRows(Date date) throws Exception;
}
