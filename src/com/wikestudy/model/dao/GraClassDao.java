package com.wikestudy.model.dao;

import com.wikestudy.model.pojo.GraClass;


public interface GraClassDao {
	/**
	 * 功能描述:修改年级的班级个数
	 * @param classes 要修改的年级班级个数
	 * @return 1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateNum(GraClass classes) throws Exception;
	
	/**
	 * 功能描述:获取指定年级的班级情况
	 * @param grade 1-初一 2-初二 3-初三
	 * @return	指定年级的班级对象
	 * @throws Exception
	 */
	public  GraClass queryClass(int claId) throws Exception;
}
