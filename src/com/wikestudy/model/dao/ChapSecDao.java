package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.ChapSec;

public interface ChapSecDao {
	/**
	 * 联合查询, 查询章节表
	 * @param couId
	 * @return
	 */
	public List<ChapSec> queryChapSecByCouId(int couId);
}
