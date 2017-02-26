package com.wikestudy.service.manager;

import java.io.InputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.wikestudy.model.dao.RecordDao;
import com.wikestudy.model.dao.StudentDao;
import com.wikestudy.model.dao.impl.RecordDaoImpl;
import com.wikestudy.model.dao.impl.StudentDaoImpl;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.MD5;

public class StudentManagerService {
	private Connection conn;
	private StudentDao sd;
	private RecordDao rd;
	private final int showNumber = 10;
	
	public StudentManagerService(Connection conn) {
		this.conn = conn;
		sd = new StudentDaoImpl(this.conn);
		rd = new RecordDaoImpl(this.conn);
	}
	
	/**
	 * 功能描述:将前台传入的学生集合数据插入到数据库中,返回数据库中已经存在的学生集合
	 * @param stus 学生集合
	 * @return	已经存在的学生账户集合
	 * @throws Exception
	 */
	public List<Student> addStudent(List<Student> stus) throws Exception {
		//构建一个List集合,用来存放数据库已经存在的学生
		List<Student> existStu = new ArrayList<Student>();
		
		//遍历前台传过来的集合
		//根据学号去获取用户信息
		//若返回的对象不为null,则存在记录,将该学生从集合中移除,添加到存在学生的集合
		//若不存在,则遍历下一个学生
		Iterator<Student> it = stus.iterator();
		Student s = null;
		Student ts = null;
		
		Set<Student> set = new HashSet<>(); 
		
		while(it.hasNext()){
			s = it.next();
			ts = sd.queryByNumber(s.getStuNumber());
			if(ts != null){
				it.remove();
				existStu.add(s);
			}
			
			if(set.contains(s)){
				it.remove();
				existStu.add(s);
			}else
				set.add(s);
			
		}
		
		//将剩下的学生记录插入到数据库
		//返回存在的学生集合给Servlet
		if(!stus.isEmpty()){
			List<Integer> ids = sd.addStudent(stus);
			if(!ids.isEmpty())
				rd.create(ids);
		}
		return existStu;
	}
	
	public PageElem<Student> queryByGradeAndClass(String grade,String stuClass,  int currentPage) throws Exception{
		//创建分页对象,将每页展示个数和当前页的数据set进去
		PageElem<Student> pe = new PageElem<Student>();
		pe.setPageShow(showNumber);
		pe.setCurrentPage(currentPage);
		
		//调用底层根据年级获取学生的方法
		//返回填充了数据的分页对象
		if(stuClass.equals("0"))
			return sd.queryByGrade(grade, pe);
		else
			return sd.queryByGradeAndClass(grade, stuClass, pe);
	}
	
	public Student queryById(int id) throws Exception{	
		//根据学生id获取学生
		//判断学生是否存在
		//不存在则返回null
		//存在则去获取学生收藏的课程和发布的话题
		Student s = sd.queryById(id);
		
		return s;
	}
	
	public static List<Student> parseExcel(boolean version, InputStream input)throws Exception{
		Workbook workbook = null;
		if(version){
			workbook = new HSSFWorkbook(input);
		}else{
			workbook = new XSSFWorkbook(input);
		}
			
		return parseExcel(workbook);
	}
	
	private static List<Student> parseExcel(Workbook workbook) throws Exception{
		//根据输入流,创建Excel文件,读取文件内容
		Sheet sheet = workbook.getSheetAt(0);
		
		//获取最大行数
		int lastRowNum = sheet.getLastRowNum();
		
		List<Student> stu = new ArrayList<Student>();
		Student s = null;
		//0为标题行,故从第一行开始
		//迭代行,读取每行的内容,根据每行内容创建Student对象，并添加进Student集合
		for(int i = 1; i <= lastRowNum; i++){
			Row row = sheet.getRow(i);
			s = new Student();
			
			//获取学号、姓名、年级单元格
			Cell num = row.getCell(0);
			Cell name = row.getCell(1);
			Cell grade = row.getCell(2);
			Cell cla = row.getCell(3);
			
			//将对应单元格的属性set进student对象中
			s.setStuNumber(getStringCellValue(num));
			s.setStuName(getStringCellValue(name));
			s.setStuGrade(getStringCellValue(grade));
			s.setStuClass(getStringCellValue(cla));
			stu.add(s);
		}
		
		workbook.close();
		return stu;
		
	}
	
	
	private static String getStringCellValue(Cell cell){
		if(cell == null)
			return "";
		
		switch (cell.getCellType()) {
		
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();
			
		case Cell.CELL_TYPE_NUMERIC:
			return String.valueOf((long)cell.getNumericCellValue());
			
		case Cell.CELL_TYPE_BOOLEAN:
			return String.valueOf(cell.getBooleanCellValue());
			
		default:
			return "";
		}
	}
	
	public PageElem<Student> queryWithout(List<Integer> ids, int currentPage)throws Exception{
		PageElem<Student> pe = new PageElem<Student>();
		
		pe.setCurrentPage(currentPage);
		
		pe.setPageShow(showNumber);
		
		return sd.queryWithout(ids, pe);
	}
	
	public int updateGrade() throws Exception{
		return sd.updateGrade();
	}
	
	public int rollbackGrade() throws Exception{
		return sd.rollBackGrade();
	}
	
	public int resetPassword(int stuId) throws Exception{
		return sd.updatePassword(stuId, MD5.getMD5Code("123456"));
	}
}
