package com.wikestudy.model.dao;

import java.sql.SQLException;
import java.util.List;
















import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;
/**
 * 老师账户的操作
 * @author CHEN
 */
public interface TeacherDao {
	/**
	 * 增加集合中老师对象
	 * @param teachers
	 */
	public int[] addTeacher (List<Teacher> teachers)throws Exception;
	/**
	 * 根据集合中的老师id删除老师
	 * @param teacherid
	 */
	public int delTeacher(List<Integer> teacherid) throws Exception;
	/**
	 * 根据老师id更新老师的所有字段信息
	 * @param teachers
	 */
	public int[] updateTeacher(List<Teacher> teachers) throws Exception;
	/**
	 * 根据老师的id更新老师的密码
	 * @param teaid,teapassword
	 */
	public 	int updatePassword(int teaid, String op, String np) throws Exception;
	/**
	 * 根据老师的id更新老师的简介
	 * @param teaid,teaintroduction
	 */
	public int updateIntroduction(int teaid,String teaintroduction)throws Exception;
	/**
	 * 根据老师的id更新老师的头像地址
	 * @param teaid,photourl
	 */
	public int updatePhotourl(int teaid,String photourl) throws Exception;
	/**
	 * 根据老师id集合返回某个的老师信息
	 * @param teaid
	 */
	public Teacher queryTeacher(int teaid) throws Exception;
	/**
	 * 根据老师id集合返回所有老师信息
	 * @param teaid
	 */
	public PageElem<Teacher> queryAllTeacher(PageElem<Teacher> pageElem)
			throws Exception;
	/**
	 * 查询老师teaNumber是否存在
	 * @return true 存在
	 * @param teanum
	 */
	public Boolean queryTeacherByNum(String teanum) throws Exception;
	/**
	 * @param teanum
	 * @param teapassword
	 * 查询是否允许该用户登陆
	 */
	public Teacher queryTeacherByNum(String teanum, String teapassword)
			throws Exception;
	public int updateOneTeacher(Teacher teacher) throws Exception;
	/**
	 * 
	 * @param teaid
	 * @return
	 * @throws Exception
	 * 更新密码
	 */
	int ResetPassword(int teaid) throws Exception;
	/**
	 * 查询老师工号用的
	 * @return
	 */
	public List<String> queAllTeacherNum()  throws Exception ;
	/**
	 * 根据老师名字查询
	 * @param search
	 * @return
	 * @throws SQLException 
	 * @throws Exception 
	 */
	public boolean queryTeacherByName(String search, PageElem<Teacher> pageElem)
			throws Exception;

}
