package com.wikestudy.model.dao;

import java.util.List;


import com.wikestudy.model.pojo.Record;
import com.wikestudy.model.pojo.Student;

public interface RecordDao {
	public int selectRecordCount(List<Integer> esixts) throws Exception;
	
	public List<Record> selectRecord(List<Integer> esixts, int start, int end) throws Exception;
	
	public int updateStudyTime(int id, long time) throws Exception;
	
	public int updateDidNum(int id, int time) throws Exception;
	
	public int update(Record r) throws Exception;
	
	public int confirm() throws Exception;
	
	public int create(int id) throws Exception;
	
	public int[] create(List<Integer> ids) throws Exception;
	
	public Record find(int id) throws Exception;
	
	public int delete(int id) throws Exception;
}