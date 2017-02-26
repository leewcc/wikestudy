package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.SecToCou;

// 天信功能特殊接口, 根据课时id查询三张表拿到课程名字
public interface SecToCouDao {
	public List<SecToCou> queryCouBySecId(List<Integer> secIdList
			) throws Exception ;
}
