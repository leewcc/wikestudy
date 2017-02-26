package com.wikestudy.service.publicpart;

import java.sql.Connection;

import com.wikestudy.model.dao.CouSectionDao;
import com.wikestudy.model.dao.impl.CouSectionDaoImpl;
import com.wikestudy.model.pojo.CouSection;

public class CouSectionService {
	private Connection conn;
	private CouSectionDao sd;
	
	public CouSectionService(Connection conn){
		this.conn = conn;
		sd = new CouSectionDaoImpl(this.conn);
	}
	
	public CouSection getSection(int secId) throws Exception{
		return sd.queryCouSectionBySec(secId);
	}

	public String getMediaUrl(int secId) throws Exception{
		return sd.getMediaUrl(secId);
	}
}
