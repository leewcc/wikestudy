package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.Power;


public interface PowerDao {
	/**
	 * 获取系统所有的权限
	 * @return 权限集合
	 * @throws Exception
	 */
	public List<Power> queryPower() throws Exception;
}
