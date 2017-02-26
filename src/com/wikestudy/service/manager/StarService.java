package com.wikestudy.service.manager;

import java.sql.Connection;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import com.wikestudy.model.dao.RecordDao;
import com.wikestudy.model.dao.StarDao;
import com.wikestudy.model.dao.StudentDao;
import com.wikestudy.model.dao.impl.RecordDaoImpl;
import com.wikestudy.model.dao.impl.StarDaoImpl;
import com.wikestudy.model.dao.impl.StudentDaoImpl;
import com.wikestudy.model.pojo.PageElem;
import com.wikestudy.model.pojo.Star;
import com.wikestudy.model.pojo.StarView;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.util.TimeToot;

public class StarService {
	private Connection conn;
	private StarDao sd;
	private StudentDao stuD;
	private final int showNum = 50;
	
	public StarService(Connection conn){
		this.conn = conn;
		sd = new StarDaoImpl(this.conn);
		stuD = new StudentDaoImpl(conn);
		
	}
	
	
	
	public int getRows() throws Exception{
		return sd.queryRows(TimeToot.getMonday(0));
	}
	
	public int set(List<Integer> ids) throws Exception{		
		List<Star> sL = new ArrayList<Star>();
		Iterator<Integer> it = ids.iterator();
		Star s = null;
		
		
		//迭代整形集合,每一个学生id创建一个Star
		while(it.hasNext()){
			s = new Star();
			int id = it.next();
			Date d = TimeToot.getMonday(0);
			s.setStarStuId(id);
			s.setStarStuDate(d);
			sL.add(s);
		}
		
		//调用底层设置一周之星方法
		int[] result = sd.addStar(sL);
		if(result.length <= 0)
			return 0;
		
		return 1;
	}
	
	public int delete(List<Integer> ids) throws Exception{
		return sd.deleteStarByIds(ids);
	}
	
	
	public PageElem<StarView> query(int cp, int star) throws Exception{
		PageElem<StarView> pe = new PageElem<StarView>();
		List<StarView> svL = new ArrayList<StarView>();
		StarView sv = null;
		Date d = null;
		List<Student> stus = null;
		Student stu = null;
		Star s = null;
		int i = 0;					//记录找到了多少星期一周之星
		int num = 0;			//记录已有了多少个一周之星
		
		//获取一周之星
		List<Star> list = sd.queryAll(star, showNum);
		
		//迭代一周之星
		Iterator<Star> it = list.iterator(); 
		while(it.hasNext()) {
			s = it.next();
			
			//判断一周之星处于同一个时间内
			//是,则获取学生,添加进集合内
			//不是则新建一个一周之星视图对象
			if(d == null){
				i++;
				sv = new StarView();
				d = s.getStarStuDate();
				stus = new ArrayList<Student>();
				sv.setDate(d);
				sv.setStars(stus);
				svL.add(sv);
				 
			}else if(!s.getStarStuDate().equals(d)){
				i++;
				svL.add(sv);
				sv = new StarView();
				d = s.getStarStuDate();
				stus = new ArrayList<Student>();
				sv.setDate(d);
				sv.setStars(stus);
			}
			
			if(i > 5)
				break;
			
			stu = stuD.queryById(s.getStarStuId());
			stus.add(stu);
			num++;
		}
		
		pe.setCurrentPage(cp);
		pe.setRows(star + num);
		pe.setPageShow(num);
		pe.setPageElem(svL);
		return pe;		
	}

	public List<Star> query() throws Exception{
		return sd.queryByDate(TimeToot.getMonday(0));
	}
	
	
	//获取一周之星放在首页上
	public List<Star> queryStars() throws Exception{
		List<Star> stars = null;
		
		
		//第一步：	循环向上翻时间获取一周之星，直至获取到数据或翻了五个星期停止
		int i = 0;
		while(stars == null || stars.isEmpty()){
			stars = sd.queryByDate(TimeToot.getMonday(i));
			i++;
			if(i == 5)
				return new ArrayList<Star>();
		}
		
		return stars;
	}
}
