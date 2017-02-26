package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.Data;


public interface DataDao {
	/**
	 * 功能描述:新增一条资料记录,用于老师上传课程资源时使用
	 * @param data 对应的资料记录
	 * @return 1-添加成功 0-添加失败
	 * @throws Exception
	 */
	public int addData(Data data) throws Exception;
	
	/**
	 * 功能描述:根据资料id删除资料记录
	 * @param id 资料id
	 * @return 1-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteData(int id) throws Exception;
	
	/**
	 * 功能描述:根据绑定的章节id或课时id删除资料记录
	 * @param Binding 章节id或课时id
	 * @param Mark 		0-章节资源 1-课时资源
	 * @return >0-删除成功 0-删除是失败
	 * @throws Exception
	 */
	public int deleteByBinding(int Binding, boolean Mark) throws Exception;
	
	/**
	 * 功能描述:根据课程id删除资料记录
	 * @param courseId 课程id
	 * @return >0-删除成功 0-删除失败
	 * @throws Exception
	 */
	public int deleteByCourseId(int courseId) throws Exception;
	
	/**
	 * 功能描述:修改指定资料的资料名
	 * @param id  资料id
	 * @param name 修改的文件名
	 * @return 1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateName(int id, String name) throws Exception;
	
	/**
	 * 功能描述:修改指定资料的资料描述
	 * @param id  资料id
	 * @param des 修改的资料描述
	 * @return 1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateDes(int id, String des) throws Exception;
	
	/**
	 * 功能描述:修改下载量,在原有基础上加上这个下载量
	 * @param id
	 * @param downLoad
	 * @return
	 * @throws Exception
	 */
	public int updateDownLoad(int id, int downLoad) throws Exception;
	
	/**
	 * 功能描述:根据课程id获取对应课程下的资料
	 * @param courseId 课程id
	 * @return 对应课程下的资料集合
	 * @throws Exception
	 */
	public List<Data> queryByCourseId(int courseId) throws Exception;
	
	/**
	 * 功能描述:根据资料id获取对应的资料
	 * @param id 资料id
	 * @return 对应id的资料对象,无则为null
	 * @throws Exception
	 */
	public Data queryById(int id) throws Exception;
	
	/**
	 * 功能描述:根据绑定的章节id或课时id获取对应的资料
	 * @param Binding 绑定的id
	 * @param Mark 0-章节资源 1-课时资源
	 * @return 对应的资料集合
	 * @throws Exception
	 */
	public List<Data> queryByBinding(int Binding, boolean Mark) throws Exception;

	/**
	 * 根据课程id删除相关全部资料
	 * @param couId
	 * @return
	 */
	public int delCouDataAll(int couId);
}
