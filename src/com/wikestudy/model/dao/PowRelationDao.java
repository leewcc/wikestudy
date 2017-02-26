package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.PowRelation;


public interface PowRelationDao {
	/**
	 * 功能描述:为管理员增加权限的关系  用于超级管理员为管理员分配权限
	 * @param relation 管理员对应的权限关系
	 * @return >0-添加成功 0-添加失败
	 * @throws Exception
	 */
	public int[] addRelation(List<PowRelation> relations) throws Exception;
	
	/**
	 * 功能描述:删除指定老师的权限关系
	 * @param teaId 老师id
	 * @return >0-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteRelationByTea(int teaId) throws Exception;
	
	/**
	 * 功能描述:删除指定权限的所有关系
	 * @param powId 权限id
	 * @return >0-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteRelationByPow(int powId) throws Exception;
	
	/**
	 * 功能描述:根据老师id查找对应老师所有权限关系
	 * @param TeaId 老师id
	 * @return 对应老师的所有权限关系
	 * @throws Exception
	 */
	public List<PowRelation> queryByTea(int TeaId) throws Exception;
	
	/**
	 * 功能描述:根据权限id查找对应的所有权限关系
	 * @param powId 权限id
	 * @return 对应权限的所有权限关系
	 * @throws Exception
	 */
	public List<PowRelation> queryByPow(int powId) throws Exception;
}
