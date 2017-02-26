package com.wikestudy.model.pojo;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class Label {
	private int labId;
	private String labName;
	private String labCib;
	private int topicCount;
	private int courseCount;
	
	public void setLabId(int labId) {
		this.labId = labId;
	}
	public void setLabName(String labName) {
		this.labName = labName;
	}
	public void setLabCib(String labCib) {
		this.labCib = labCib;
	}
	public void setTopicCount(long count) {
		this.topicCount = (int)count;
	}

	public int getLabId() {
		return labId;
	}
	public String getLabName() {
		return labName;
	}
	public String getLabCib() {
		return labCib;
	}
	public int getTopicCount() {
		return topicCount;
	}
	public int getCourseCount() {
		return courseCount;
	}
	public void setCourseCount(long courseCount) {
		this.courseCount = (int)courseCount;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Label){
			if(equals(((Label)obj).getLabId() == labId))
					return true;
		}
		
		return false;
		
	}
}
