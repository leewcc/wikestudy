package com.wikestudy.service.manager;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.storage.ListManagedBlock;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.mysql.fabric.Response;
import com.wikestudy.model.dao.StudentDao;
import com.wikestudy.model.dao.impl.StudentDaoImpl;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.StudentView;

import java.io.InputStream;
import java.sql.Connection;
import java.util.List;

import com.wikestudy.model.dao.TeacherDao;
import com.wikestudy.model.dao.impl.TeacherDaoImpl;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Teacher;

public class TeacherService {
	private Connection conn;
	private TeacherDao td;

	public TeacherService(Connection conn) throws Exception {
		this.conn = conn;
		td = new TeacherDaoImpl(conn);
	}

	/**
	 * 批量添加老师账号
	 * 如果已经存在某一工号则直接跳过，并在页面显示出来
	 */
	public int[] addTeacher(List<Teacher> tea) throws Exception {
		List <String> number=td.queAllTeacherNum();//肯定是没有重复的
		List<Teacher> teacher=new ArrayList<Teacher>();//用来装老师的
		List<String> temp=new ArrayList<String>();
		for(Teacher t:tea) {
			int flat=0;//标识有没有这个人
			for(String s:number) {
				if(t.getTeaNumber().equals(s)) {
					flat=1; break;
				} 
			}
			if(flat==0) {
					teacher.add(t);
					number.add(t.getTeaNumber());
			}
				
		}
		
		return td.addTeacher(teacher);
	}
	public int ResetPassword(int teaId) throws Exception {
		return td.ResetPassword(teaId);
	}
	
	public int[] upudateTeacher(List<Teacher> teachers) throws Exception {
		int[] flag = td.updateTeacher(teachers);
		conn.close();
		return flag;
	}
	
	public int updateOneTeacher(Teacher teacher) throws Exception {
		return td.updateOneTeacher(teacher);
	}
	
	public int updateIntroduction(int teaid, String teaintroduction)throws Exception {
		int flag=td.updateIntroduction(teaid, teaintroduction);
		return flag;
	}
	
	public int updatePhotourl(int teaid, String photourl) throws Exception {
		int flag=td.updatePhotourl(teaid,photourl);
		return flag;
	}
	
	public PageElem<Teacher> queryAllTeacher(int cp)
			throws Exception {
		int showNum=10;//展示的数据条数
		PageElem<Teacher> pe=new PageElem<Teacher>();
		pe.setCurrentPage(cp);
		pe.setPageShow(showNum);
		pe=td.queryAllTeacher(pe);
		return  pe;
	}
	
	public Teacher queryOneTeacher(int teaid) throws Exception {
		return td.queryTeacher(teaid);
	}
	public Teacher queryTeacherByNum(String teanum,String teapassword)throws Exception{
		return td.queryTeacherByNum(teanum, teapassword);
	}

	public List<Teacher> parseExcel(String string, InputStream inputStream) throws IOException {
		Workbook workbook=null;
		if(".xls".equals(inputStream)) {
			workbook=new HSSFWorkbook(inputStream);
		} else {
			workbook =new XSSFWorkbook(inputStream);
		}
		
		return parseExcel(workbook);
	
	}

	private List<Teacher> parseExcel(Workbook workbook) {
		Sheet sheet=workbook.getSheetAt(0);
		
		int lastRowNum=sheet.getLastRowNum();
		List<Teacher> tea=new ArrayList<Teacher>();
		Teacher t=null;
		//从第二行开始
		for(int i=1;i<=lastRowNum;i++) {
			Row row=sheet.getRow(i);
			t=new Teacher();

			Cell teaType=row.getCell(0);
			Cell teaNumber=row.getCell(1);
			Cell teaName=row.getCell(2);
			Cell teaPassword=row.getCell(3);
			Cell teaGender=row.getCell(4);
			Cell teaIntroduction=row.getCell(5);	
			
			t.setTeaGender(getStringCellValue(teaGender));
			t.setTeaIntroduction(getStringCellValue(teaIntroduction));
			t.setTeaName(getStringCellValue(teaName));
			t.setTeaNumber(getStringCellValue(teaNumber));
			t.setTeaPassword(getStringCellValue(teaPassword));
			if("管理员".equals(getStringCellValue(teaType))) {//管理员
				t.setTeaType(true);
			} else if("老师".equals(getStringCellValue(teaType))) {
				t.setTeaType(false);
			}
			tea.add(t);
		}
		return tea;
	}
	private  String getStringCellValue(Cell cell){
		if(cell == null)
			return "";
		
		switch (cell.getCellType()) {
		
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
			
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf((int)cell.getNumericCellValue());
			
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
			
		default:
			return "";
		}
	}

	public int updatePassword(int id, String op, String np) throws Exception {
		
		return td.updatePassword(id, op,np);
	}

	public PageElem<Teacher> queryAllTeacher(int cp, int i) throws Exception {
		PageElem <Teacher> t=new PageElem<Teacher>();
		t.setCurrentPage(cp);
		t.setPageShow(10);//设置每一页中显示的数目
		t=td.queryAllTeacher(t);	
		
		return t;
	}

	public void deleteTeacher(List<Integer> id) throws Exception {
		td.delTeacher(id);
		
	}
	
	public  boolean queryTeacherByName(String search,PageElem<Teacher> pe) throws Exception{
		return td.queryTeacherByName(search,pe);
	} 

}
