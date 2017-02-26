package com.wikestudy.service.student;

import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.StudentDao;
import com.wikestudy.model.dao.TopicDao;
import com.wikestudy.model.dao.impl.StudentDaoImpl;
import com.wikestudy.model.dao.impl.TopicDaoImpl;
import com.wikestudy.model.pojo.Student;

public class StudentService {
	private Connection conn;
	private StudentDao sd;
	private TopicDao td;
	
	public StudentService(Connection conn) {
		this.conn = conn;
		sd = new StudentDaoImpl(this.conn);
		td = new TopicDaoImpl(conn);
	}
	
	public Student Login(String number, String password) throws Exception{
		//根据学号获取学生
		Student s = sd.queryByNumber(number);
		
		//判断学生是否存在
		//若不存在,则返回null
		if(s == null)
			return null;
		
		//若存在,则比较密码是否正确
		//若正确,返回学生
		//若不正确,则返回null
		if(password.equals(s.getStuPassword()))
			return s;
				
		return null;
	}
	
	public int updateBasic(Student stu) throws Exception{
		return sd.updateStudent(stu);
	}
	
	public int updateSign(int id, String sign) throws Exception{
		return sd.updateSignature(id, sign);
	}

	public int updatePassword(int id, String op, String np) throws Exception{
		return sd.updatePassword(id, np, op);
		
}

	public int delete(int id) throws Exception{
		int result = sd.deleteStudent(id);
		if(result > 0){
			td.deleteTopicByUser(id, false);
		}
		return result;
			
	}
	
	public Student getStudent(int stuId) throws Exception{
		return sd.queryById(stuId);
	}

	public Student getStudent(String num) throws Exception {
		return sd.queryByNumber(num);
	}
	
	public int updatePhotourl(int stuId, String photo) throws Exception {
		return sd.updatePhoto(stuId, photo);
	}
	
	public int updateStudy(int id, long time) throws Exception {
		return sd.updateStudyHour(id, time);
	}

}
