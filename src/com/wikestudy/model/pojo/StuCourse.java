package com.wikestudy.model.pojo;

// 学生个人课程表
public class StuCourse {
	private int    stuCouId;    // 主键
	private int    stuId;           // 学生表Id
	private int    couId;          // 课程表Id
	private boolean couFinish;  //  标志该课程是否完成 
	                                              //   0: 未完成; 1: 已完成 
	public void setStuCouId(int stuCouId) {
		this.stuCouId = stuCouId;
	}

	public void setStuId(int stuId) {
		this.stuId = stuId;
	}
	public void setCouId(int couId) {
		this.couId = couId;
	}
	public void setCouFinish(boolean couFinish) {
		this.couFinish = couFinish;
	}
	public int getStuCouId() {
		return stuCouId;
	}
	public int getStuId() {
		return stuId;
	}
	public int getCouId() {
		return couId;
	}
	
	public boolean isCouFinish() {
		return couFinish;
	}
	
	
	
}
