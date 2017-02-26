package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;



public interface StudentDao {
	/**
	 * 功能描述:批量增加用户数据到数据库
	 * @param students	学生集合,每个学生内部只有学号和姓名的信息
	 * @return	若返回数与学生集合的元素个数相等,则代表批量添加成功
	 * @throws Exception
	 */
	public List<Integer> addStudent(List<Student> students) throws Exception;
	
	/**
	 * 功能描述:批量删除用户,将指定的用户删除标志置为0
	 * @param ids	学生id集合
	 * @return	若返回数与id集合的元素个数相等,则代表批量删除成功
	 * @throws Exception
	 */
	public int deleteStudent(int id) throws Exception;
	
	/**
	 * 功能描述:修改学生的基本信息(学号 姓名 性别 个性签名 年级 班级等信息)
	 * @param Student		一个封装了学生基本信息的对象
	 * @return	1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateStudent(Student student) throws Exception;
	
	public int updatePassword(int sid, String npassword) throws Exception;
	
	/**
	 * 功能描述:修改学生的密码
	 * @param id 学生的id
	 * @param password	要更改的密码
	 * @return	1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updatePassword(int id, String np, String op) throws Exception;
	
	/**
	 * 功能描述:修改学生的头像
	 * @param id 学生id
	 * @param photoUrl 头像的路径
	 * @return
	 * @throws Exception
	 */
	public int updatePhoto(int id, String photoUrl) throws Exception;
	
	/**
	 * 功能描述:修改个性签名
	 * @param id 学生id
	 * @param signature 要更改的个性签名
	 * @return 1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateSignature(int id, String signature) throws Exception;
	
	/**
	 * 功能描述:修改学生的讨论次数
	 * @param id 学生id
	 * @param disscussNum  讨论次数
 	 * @return 1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateDisscussNum(int id, int disscussNum) throws Exception;
	
	/**
	 * 功能描述:修改学习时间,在原有的基础上添加
	 * @param id 学生id
	 * @param studyHour 学习时间
	 * @return 1-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateStudyHour(int id, long studyHour) throws Exception;
	
	/**
	 * 功能描述:批量修改年级
	 * @param id 学生id集合
	 * @param Grade 学生年级
	 * @return >0-修改成功 0-修改失败
	 * @throws Exception
	 */
	public int updateGrade(List<Integer> ids, String Grade) throws Exception;
	
	public int updateGrade() throws Exception;
	
	public int rollBackGrade() throws Exception;
	
	/**
	 * 功能描述:根据学生id获取学生信息
	 * @param id 学生id
	 * @return 指定id对应的学生,若无则为null
	 * @throws Exception
	 */
	public Student queryById(int id) throws Exception;
	
	/**
	 * 功能描述:根据账号获取学生的信息,一般用来匹配用户登录
	 * @param number 学号
	 * @return 指定学号的学生,若无则为null
	 * @throws Exception
	 */
	public Student queryByNumber(String number) throws Exception;
	
	public PageElem<Student> queryByGradeAndClass(String grade, String stuClass, PageElem<Student> pageElem) throws Exception;
	
	/**
	 * 功能描述:根据年级获取指定页数的学生
	 * @param grade 指定年级
	 * @param pageElem	一个含有页面数据的对象
	 * @return	一个填充了学生实体数据的页面对象
	 * @throws Exception
	 */
	public PageElem<Student> queryByGrade(String grade, PageElem<Student> pageElem)
			throws Exception;
	
	/**
	 * 功能描述:根据学习情况获取指定页数的学生
	 * @param pageElem 一个含有页面数据的对象
	 * @return 一个填充了学生实体数据的页面对象
	 * @throws Exception
	 */
	public PageElem<Student> queryByStudy(PageElem<Student> pageElem) throws Exception;
	
	public PageElem<Student> queryWithout(List<Integer> ids, PageElem<Student> pageElem) throws Exception;
}
