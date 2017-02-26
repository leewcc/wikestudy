package com.wikestudy.service.student;

import java.sql.Connection;
import java.sql.Timestamp;
import java.util.List;

import com.wikestudy.model.dao.CouChapterDao;
import com.wikestudy.model.dao.NoteDisDao;
import com.wikestudy.model.dao.impl.NoteDisDaoImpl;
import com.wikestudy.model.pojo.CouChapter;
import com.wikestudy.model.pojo.NoteCourse;
import com.wikestudy.model.pojo.NoteDis;
import com.wikestudy.model.pojo.NoteView;
import com.wikestudy.model.pojo.PageElem;


public class NoteManagerService {
	private Connection conn = null;
	private NoteDisDao ndDao = null;
	private CouChapterDao ccDao = null;
	private int notePageShows = 12;
	
	public NoteManagerService(Connection conn) {
		this.conn = conn;
		ndDao = new NoteDisDaoImpl(this.conn);
	}
	
	//根据笔记id去获取笔记
	public NoteDis queryById(int id) throws Exception {
		return ndDao.queryNote(id);
	}
	
	// 查询笔记课程列表 flag
	public List<NoteCourse> querycourse_note_sort(int stuId)
		throws Exception {
		
		return ndDao.queryNoteDia(stuId);
	}
	
	//  根据课程查询笔记 sortType-1: 章节顺序; 2-时间顺序
	public PageElem<NoteView> queryNoteByCou(int stuId, int couId, int currentPage, String sortType) {
		PageElem<NoteView> peND = new PageElem<NoteView>();
		
		peND.setCurrentPage(currentPage);
		
		peND.setPageShow(notePageShows);
		
		
		return ndDao.queryNoteCou(stuId, couId, peND, sortType);
	}
	
	// 查询全部笔记
	public PageElem<NoteView> queryNoteAll(int stuId, int page) throws Exception{
		PageElem<NoteView> pe = new PageElem<NoteView>();
		
		pe.setCurrentPage(page);
		pe.setPageShow(notePageShows);
		
		pe = ndDao.queryNoteAll(stuId, pe);
		
		return pe;
	}
	
	public int saveNote(int nDId, String content, Timestamp data) throws Exception {
		return ndDao.updataNoteContent(nDId, content, data);
	}
	
	public int deleteNote(int nDId) throws Exception {
		return ndDao.delNoteDisBynDId(nDId);
	}
	
	public PageElem<NoteDis> queryBySec(int secId) throws Exception{
		PageElem<NoteDis> pe = new PageElem<>();
		pe.setCurrentPage(1);
		pe.setPageShow(20);
		return (ndDao.querySecComment(secId, pe,false));
	}
	
	
	
}
