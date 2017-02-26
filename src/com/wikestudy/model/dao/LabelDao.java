package com.wikestudy.model.dao;

import java.util.List;

import com.wikestudy.model.pojo.Label;

public interface LabelDao {
	
	public int insertLabel(Label label) throws Exception;
	
	public int deleteLabel(List<Integer> ids) throws Exception;
	
	public int deleteLabel(int labId) throws Exception;
	
	public int updateLabel(int labId, Label label) throws Exception;
	
	/**
	 * 查询所有标签
	 * @return
	 * @throws Exception
	 */
	public List<Label> queryLabelAll();
	
	/**
	 * 根据标签id查询标签
	 * @param labId
	 * @return
	 * @throws Exception
	 */
	public Label queryLabelByLabId(int labId) throws Exception;
	
	public Label queryLabelByName(String name) throws Exception;
	
	public List<Label> queryLabelViewAll();
	
}
