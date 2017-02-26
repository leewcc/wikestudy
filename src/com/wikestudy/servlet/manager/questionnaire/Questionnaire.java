package com.wikestudy.servlet.manager.questionnaire;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jdk.nashorn.internal.ir.RuntimeNode.Request;
import net.sf.json.JSONObject;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Question;
import com.wikestudy.model.pojo.Student;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.HtmlCreater;
import com.wikestudy.model.util.SaltValue;
import com.wikestudy.service.student.QuestionService;

/**
 * 试卷制作页面
 * 7/25
 * @author CHEN
 *
 */
@WebServlet("/dist/jsp/teacher/course/questionnaire/questionnaire")
public class Questionnaire extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Log4JLogger log = new Log4JLogger("log4j.properties");

	/**
	 * 试卷删除操作
	 *  TODO 删除本地文件
	 */
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
	/*	JSONObject data=new JSONObject();
		String [] id=req.getParameterValues("id");
		List<Integer> idList=new ArrayList<Integer>();
		for(int i=0;i<id.length;i++ ) {
			try {
				idList.add(Integer.parseInt(id[i]));
				data.put("status","success");
				Connection conn=DBSource.getConnection();
				QuestionService questionService=new QuestionService(conn);
				questionService.delQuestion(idList);
			} catch(Exception e) {
				data.put("status", "error");
				log.error(e.getMessage());
			}
		}
		
		PrintWriter out=resp.getWriter();
		out.print(data);
		out.flush();
		out.close();*/
		int secId=0;
		try {
			secId=Integer.parseInt(req.getParameter("secId"));
			Connection conn=null;

			conn= DBSource.getConnection();
			
			QuestionService service=new QuestionService(conn);
			if(service.delQuetionBySecId(secId)>0){
				System.out.println("成功删除");
			}
			
		} catch(Exception e) {
			
		}
		
		//
		String fileName=new StringBuffer(req.getServletContext().getRealPath("/dist/html/questionnaire")
				+"/"+SaltValue.getSaltValue("question"+secId)+"/questionnaire_"+secId+".html").toString();
		File file=new File(fileName);
		if(file.exists()) {
			file.delete();
		}
		
	}

	/**
	 * 当用户请求试卷预览的时候
	 */
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String url;
		
		//获得章或节的Id
		int secId =0;
		try {
			secId=Integer.parseInt(req.getParameter("secId"));
		}catch (Exception e) {
			req.setAttribute("statue", "error");
			req.setAttribute("message", "章节信息错误");
			req.getRequestDispatcher("questionnaire_page.jsp").forward(req, resp);
			return ;
		}
		
		
		/*从数据库读所有的数据*/
		QuestionService qs = null;
		List<Question>  qList = null;
		Connection conn=null;
		try {
			conn= DBSource.getConnection();
			qs = new QuestionService(conn);
			qList=qs.queryQuestion("节",secId);			
		} catch (Exception e) {
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}
		/*将问题进行分类*/

		/*String str[] = list.toArray(new String[]{}); */
		Iterator<Question> qi= qList.iterator();
		List<Question> oList=new ArrayList<Question>();
		List<Question> jList=new ArrayList<Question>();
		List<Question> fList=new ArrayList<Question>();		
		Question q;
		int qid = 0;
		while(qi.hasNext()) {
			q=qi.next();
			qid = q.getQueId();
			if("option".equals(q.getQueType())) oList.add(q);
			else if("judge".equals(q.getQueType())) jList.add(q);
			else if("fill".equals(q.getQueType())) fList.add(q);			
		}
	
	
		try {
			//当前没有试题
			if(oList.size()<=0&&jList.size()<=0&&fList.size()<=0) {
				req.setAttribute("message", "当前没有测试题");
				req.setAttribute("statue", "error");
				req.getRequestDispatcher("questionnaire_page.jsp").forward(req, resp);
				return ;
				
			}
			req.setAttribute("status", "success");
			req.setAttribute("secId", secId);
			req.setAttribute("option", oList);
			req.setAttribute("judge", jList);
			req.setAttribute("fill", fList);
			req.getRequestDispatcher("questionnaire_page.jsp").forward(req, resp);
			
			
		} catch (Exception e) {
	//		log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		}finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
	}

	
	
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		String method=req.getParameter("method");
		if("post".equals(method)) {
			doMyPost(req, resp);
		} else if("put".equals(method)) {
			doMyPut(req, resp);
		}
	}

	/**
	 * 试卷添加操作 通过json交互
	 * 添加试卷而且生成试卷
	 */
	protected void doMyPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		Log4JLogger log = new Log4JLogger("log4j.properties");
		JSONObject data=new JSONObject();
		int checkNum=1;
		// 获得错误集合
		boolean hasError = false;
		Map<String, String> errorMap = new HashMap<String, String>();

		/* 获得节数 */
		int secId=0;
		try {
			secId = Integer.parseInt(req.getParameter("secId"));
		}catch (Exception e) {
			data.put("status", "error");
			data.put("message","章节号出错");
			PrintWriter out=resp.getWriter();
			out.print(data);
			out.close();
 			return;
			
		}

		/* 获得制作者 */
		HttpSession session = req.getSession();
		Teacher t = (Teacher) session.getAttribute("t");
		int authorId = t.getTeaId();

		String[] o = req.getParameterValues("option");// 选择题题目
		String[] j = req.getParameterValues("judge");//  判断题
		String[] f = req.getParameterValues("fill");//  填空题
		if(o==null&&j==null&&f==null) {//没有题目
			data.put("message", "请至少输入一道题的数据");
			data.put("status", "error");
			PrintWriter out=resp.getWriter();
			out.print(data);
			out.close();
 			return;
			
		}
		
		/* 处理选择题 */
		List<Question> optionList = new ArrayList<>();

		// 如果存在选择题，则执行选择题的创建
		if (o != null && o.length > 0) {

			List<String> oList = Arrays.asList(o);
			/* 获得选择题答案 */
			List<String> oaList = new ArrayList<String>();
			{
				String[] on = req.getParameterValues("optionNum");
				for (int i = 0; i < on.length; i++) {
					System.out.println(on[i]);
					String junmingString = req.getParameter(on[i]);
					oaList.add(junmingString);
				}
			}
			/* 选择题的内容 */
			String[] okn = req.getParameterValues("optionKeyNum");
			String[] ocall = req.getParameterValues("optionKey");// 所有的选项内容
			int all = 0;// 用来遍历所有的答案

			List<String> ocList = new ArrayList<String>();// 答案集合
			for (String n : okn) {
				Integer ii = Integer.parseInt(n);
				StringBuilder ss = new StringBuilder();
				for (int i = 0; i < ii; i++) {
					ss.append(ocall[all++]);
					ss.append(" ;next;");
				}
				String ans = ss.toString();
				ocList.add(ans);// 整理好的答案放进集合
			}
			/* 选择题的解释 */
			String[] oe = req.getParameterValues("optionExplain");
			List<String> oeList = Arrays.asList(oe);

			Iterator<String> oIterator = oList.iterator();// 题目
			Iterator<String> oaIterator = oaList.iterator();// 答案
			Iterator<String> ocIterator = ocList.iterator();// 选项
			Iterator<String> oeIterator = oeList.iterator();// 解释
			Question op = null;
			checkNum = 1;
			while (oaIterator.hasNext()) {
				op = new Question();
				op.setChaSecId(secId);
				op.setChaSecType("节");// 暂时只有节
				op.setMakerId(t.getTeaId());

				op.setQueAnswer(oaIterator.next());
				op.setQueContent(oIterator.next());
				op.setQueCorrectNum(0);
				op.setQueExplain(oeIterator.next());
				op.setQueOption(ocIterator.next());
				op.setQuePersonNum(0);
				op.setQueType("option");

				// 检查选择题
				errorMap.putAll(checkOption(op, checkNum++));
				if (errorMap.size() > 0 && !hasError) {// 说明存在错误 以后就不再添加到List中了
					hasError = true;
				}
				if (!hasError) {
					optionList.add(op);// 从来没有错误的时候就添加
				}
			}
		}

		/* 处理判断题 */
		/* 判断题的问题 */
	
		List<Question> judgeList = new ArrayList<>();

		if (j != null && j.length > 0) {
			List<String> jList = Arrays.asList(j);
			/* 判断题的答案 */
			String[] jn = req.getParameterValues("judgeNum");
			List<String> jaList = new ArrayList<String>();
			for (String s : jn) {// 53
				jaList.add(req.getParameter(s));
			}
			/* 判断题的解释 */
			String[] je = req.getParameterValues("judgeExplain");
			List<String> jeList = Arrays.asList(je);

			/* 添加判断题 */
			Iterator<String> jIterator = jList.iterator();
			Iterator<String> jaIterator = jaList.iterator();
			Iterator<String> jeIterator = jeList.iterator();
			Question judge = null;
			checkNum = 1;
			while (jaIterator.hasNext()) {
				judge = new Question();
				judge.setChaSecId(secId);
				judge.setChaSecType("节");
				judge.setMakerId(authorId);
				/* 真值 */
				judge.setQueAnswer(jaIterator.next());
				judge.setQueContent(jIterator.next());
				judge.setQueCorrectNum(0);
				judge.setQueExplain(jeIterator.next());
				judge.setQueOption("");
				judge.setQuePersonNum(0);
				judge.setQueType("judge");

				// 检查选择题
				errorMap.putAll(checkJudge(judge, checkNum++));
				if (errorMap.size() > 0 && !hasError) {// 说明存在错误 以后就不再添加到List中了
					hasError = true;
				}
				if (!hasError) {
					judgeList.add(judge);// 从来没有错误的时候就添加
				}
			}
		}

		/* 填空题 */
		/* 填空题题目 */

		List<Question> fillList = new ArrayList<>();

		if (f != null && f.length > 0) {
			List<String> fList = Arrays.asList(f);
			/* 填空题答案 */
			String[] fk = req.getParameterValues("fillKey");
			List<String> faList = Arrays.asList(fk);
			/* 填空题解释 */
			String[] fe = req.getParameterValues("fillExplain");
			List<String> feList = Arrays.asList(fe);

			Iterator<String> fIterator = fList.iterator();
			Iterator<String> faIterator = faList.iterator();
			Iterator<String> feIterator = feList.iterator();
			Question fill = null;
			checkNum = 1;
			while (faIterator.hasNext()) {
				/* 11参数 */
				/* 假值 */
				fill = new Question();
				fill.setChaSecId(secId);
				fill.setChaSecType("节");
				fill.setMakerId(authorId);
				fill.setQueAnswer(faIterator.next());
				fill.setQueContent(fIterator.next());
				fill.setQueCorrectNum(0);
				fill.setQueExplain(feIterator.next());
				// q.setQueNum(queNum);题号好像不需要
				fill.setQueOption("");
				fill.setQuePersonNum(0);
				fill.setQueType("fill");

				// 检查选择题
				errorMap.putAll(checkFill(fill, checkNum++));
				if (errorMap.size() > 0 && !hasError) {// 说明存在错误 以后就不再添加到List中了
					hasError = true;
				}
				if (!hasError) {
					fillList.add(fill);// 从来没有错误的时候就添加
				}
			}
		}
		
		
		//假如试卷没有错误过 那就添加到数据库，并返回操作成功的信息
		//假如操作失败，则返回错误的集合
		if(hasError) {
			data.put("status", "error");
			data.put("errorMap", errorMap);
		} else {
			data.put("status", "success");
			//存数据进入数据库
			Connection conn = null;
			try {
				/* 整理存入问题集合 */
				List<Question> qList = new ArrayList<Question>();// 题目对象集合
				qList.addAll(optionList);
				qList.addAll(judgeList);
				qList.addAll(fillList);

				/* 存进数据库 */
				QuestionService qs;
				conn = DBSource.getConnection();
				qs = new QuestionService(conn);
				try {
					//删除多余的题目
					qs.delQuetionBySecId(secId);
					
					//存入数据库
					qs.addQuestion(qList);
				} catch (Exception e1) {
					e1.printStackTrace();
					log.error(e1, e1);
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e1) {
					log.error(e1, e1);
				}
			}
		}
		
		PrintWriter out=resp.getWriter();
		out.print(data);
		out.close();
		
		
		new HtmlCreaterThread(new StringBuffer(req.getServletContext().getRealPath("/dist/html/questionnaire")+"/"+SaltValue.getSaltValue("question"+secId)+"/").toString(), secId,optionList,judgeList,fillList).start();
		
	}
	
	protected void doMyPut(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setContentType("text/html;charset=UTF-8");
		Log4JLogger log = new Log4JLogger("log4j.properties");
		JSONObject data=new JSONObject();
		
		// 获得错误集合
		boolean hasError = false;
		Map<String, String> errorMap = new HashMap<String, String>();

		/* 获得节数 */
		int secId=0;
		try {
			secId = Integer.parseInt(req.getParameter("secId"));
		}catch (Exception e) {
			data.put("status", "error");
			data.put("message","章节号出错");
			PrintWriter out=resp.getWriter();
			out.print(data);
			out.close();
 			return;
			
		}

		
		/* 获得制作者 */
		HttpSession session = req.getSession();
		Teacher t = (Teacher) session.getAttribute("t");
		int authorId = t.getTeaId();

		/*如果是更新，需要的问题id*/
		String []idString=req.getParameterValues("id");
		List<String>  iList=null;
		Iterator<String>iterator =null;
		if(idString!=null) {
			iList=Arrays.asList(idString);
			iterator=iList.iterator();
		}
		
		String[] o = req.getParameterValues("option");// 选择题题目
		String[] j = req.getParameterValues("judge");//  判断题
		String[] f = req.getParameterValues("fill");//  填空题
		if(o==null&&j==null&&f==null) {//没有题目
			data.put("message", "请至少输入一道题的数据");
			data.put("status", "error");
			PrintWriter out=resp.getWriter();
			out.print(data);
			out.close();
 			return;
			
		}
		
		/* 处理选择题 */
		List<Question> optionList = new ArrayList<>();

		// 如果存在选择题，则执行选择题的创建
		if (o != null && o.length > 0) {

			List<String> oList = Arrays.asList(o);
			/* 获得选择题答案 */
			List<String> oaList = new ArrayList<String>();
			{
				String[] on = req.getParameterValues("optionNum");
				for (int i = 0; i < on.length; i++) {
					String junmingString = req.getParameter(on[i]);
					oaList.add(junmingString);
				}
			}
			/* 选择题的内容 */
			String[] okn = req.getParameterValues("optionKeyNum");
			String[] ocall = req.getParameterValues("optionKey");// 所有的选项内容
			int all = 0;// 用来遍历所有的答案

			List<String> ocList = new ArrayList<String>();// 答案集合
			for (String n : okn) {
				Integer ii = Integer.parseInt(n);
				StringBuilder ss = new StringBuilder();
				for (int i = 0; i < ii; i++) {
					ss.append(ocall[all++]);
					ss.append(" ;next;");
				}
				String ans = ss.toString();
				ocList.add(ans);// 整理好的答案放进集合
			}
			/* 选择题的解释 */
			String[] oe = req.getParameterValues("optionExplain");
			List<String> oeList = Arrays.asList(oe);

			Iterator<String> oIterator = oList.iterator();// 题目
			Iterator<String> oaIterator = oaList.iterator();// 答案
			Iterator<String> ocIterator = ocList.iterator();// 选项
			Iterator<String> oeIterator = oeList.iterator();// 解释
			Question op = null;
			int i = 1;
			while (oaIterator.hasNext()) {
				op = new Question();
				op.setChaSecId(secId);
				op.setChaSecType("节");// 暂时只有节
				op.setMakerId(t.getTeaId());
				op.setQueId(Integer.parseInt(iterator.next()));
				op.setQueAnswer(oaIterator.next());
				op.setQueContent(oIterator.next());
				op.setQueCorrectNum(0);
				op.setQueExplain(oeIterator.next());
				op.setQueOption(ocIterator.next());
				op.setQuePersonNum(0);
				op.setQueType("option");

				// 检查选择题
				errorMap.putAll(checkOption(op, i));
				i++;
				if (errorMap.size() > 0 && !hasError) {// 说明存在错误 以后就不再添加到List中了
					hasError = true;
				}
				if (!hasError) {
					optionList.add(op);// 从来没有错误的时候就添加
				}
			}
		}

		/* 处理判断题 */
		/* 判断题的问题 */
	
		List<Question> judgeList = new ArrayList<>();

		if (j != null && j.length > 0) {
			List<String> jList = Arrays.asList(j);
			/* 判断题的答案 */
			String[] jn = req.getParameterValues("judgeNum");
			List<String> jaList = new ArrayList<String>();
			for (String s : jn) {// 53
				jaList.add(req.getParameter(s));
			}
			/* 判断题的解释 */
			String[] je = req.getParameterValues("judgeExplain");
			List<String> jeList = Arrays.asList(je);

			/* 添加判断题 */
			Iterator<String> jIterator = jList.iterator();
			Iterator<String> jaIterator = jaList.iterator();
			Iterator<String> jeIterator = jeList.iterator();
			Question judge = null;
			int i = 1;
			while (jaIterator.hasNext()) {
				judge = new Question();
				judge.setChaSecId(secId);
				judge.setChaSecType("节");
				judge.setMakerId(authorId);
				/* 真值 */
				judge.setQueId(Integer.parseInt(iterator.next()));
				judge.setQueAnswer(jaIterator.next());
				judge.setQueContent(jIterator.next());
				judge.setQueCorrectNum(0);
				judge.setQueExplain(jeIterator.next());
				judge.setQueOption("");
				judge.setQuePersonNum(0);
				judge.setQueType("judge");

				// 检查选择题
				errorMap.putAll(checkJudge(judge, i));
				i++;
				if (errorMap.size() > 0 && !hasError) {// 说明存在错误 以后就不再添加到List中了
					hasError = true;
				}
				if (!hasError) {
					judgeList.add(judge);// 从来没有错误的时候就添加
				}
			}
		}

		/* 填空题 */
		/* 填空题题目 */

		List<Question> fillList = new ArrayList<>();

		if (f != null && f.length > 0) {
			List<String> fList = Arrays.asList(f);
			/* 填空题答案 */
			String[] fk = req.getParameterValues("fillKey");
			List<String> faList = Arrays.asList(fk);
			/* 填空题解释 */
			String[] fe = req.getParameterValues("fillExplain");
			List<String> feList = Arrays.asList(fe);

			Iterator<String> fIterator = fList.iterator();
			Iterator<String> faIterator = faList.iterator();
			Iterator<String> feIterator = feList.iterator();
			Question fill = null;
			int i = 1;
			while (faIterator.hasNext()) {
				/* 假值 */
				fill = new Question();
				fill.setQueId(Integer.parseInt(iterator.next()));
				fill.setChaSecId(secId);
				fill.setChaSecType("节");
				fill.setMakerId(authorId);
				fill.setQueAnswer(faIterator.next());
				fill.setQueContent(fIterator.next());
				fill.setQueCorrectNum(0);
				fill.setQueExplain(feIterator.next());
				// q.setQueNum(queNum);题号好像不需要
				fill.setQueOption("");
				fill.setQuePersonNum(0);
				fill.setQueType("fill");

				// 检查选择题
				errorMap.putAll(checkFill(fill, i));
				i++;
				if (errorMap.size() > 0 && !hasError) {// 说明存在错误 以后就不再添加到List中了
					hasError = true;
				}
				if (!hasError) {
					fillList.add(fill);// 从来没有错误的时候就添加
				}
			}
		}
		
		
		//假如试卷没有错误过 那就添加到数据库，并返回操作成功的信息
		//假如操作失败，则返回错误的集合
		if(hasError) {
			data.put("status", "error");
			data.put("errorMap", errorMap);
		} else {
			data.put("status", "success");
			//存数据进入数据库
			Connection conn = null;
			try {
				/* 整理存入问题集合 */
				List<Question> qList = new ArrayList<Question>();// 题目对象集合
				qList.addAll(optionList);
				qList.addAll(judgeList);
				qList.addAll(fillList);

				/* 存进数据库 */
				QuestionService qs;
				conn = DBSource.getConnection();
				qs = new QuestionService(conn);
				try {
					qs.updateQuestion(qList);
				} catch (Exception e1) {
					log.error(e1, e1);
				}
			} finally {
				try {
					conn.close();
				} catch (SQLException e1) {
					log.error(e1, e1);
				}
			}
		}
		
		PrintWriter out=resp.getWriter();
		out.print(data);
		out.close();
		
		//生成试卷
		new HtmlCreaterThread(new StringBuffer(req.getServletContext().getRealPath("/dist/html/questionnaire")+"/"+SaltValue.getSaltValue("question"+secId)+"/").toString(), secId,optionList,judgeList,fillList).start();
		
	}

	/**
	 * 检空操作
	 * 
	 * @param content
	 * @return
	 */
	private boolean checkEmpty(String content) {
		if (content == null || "".equals(content) || "".equals(content.trim()))
			return true;
		return false;
	}

	// 检查选择题是否有错误，有返回true 无返回false
	private Map<String, String> checkOption(Question q, int i) {
		Map<String, String> map = new HashMap<String, String>();
		String content = q.getQueContent();
		String option = q.getQueOption();
		String answer = q.getQueAnswer();
		String explain = q.getQueExplain();

		if (checkEmpty(content)) {
			map.put("option_"+i, "请填写选择题题目，题号为  " + i);
		} else if (content.length() > 400) {
			map.put("option_"+i, "选择题题目内容应小于400，题号为  " + i);
		} else {
			if (checkEmpty(option)) {
				map.put("option_"+i, "请填写选择题答案，题号为  " + i);
			} else {
				String[] options = option.split(";next;");
				for (int j = 0; j < options.length; j++) {
					if (checkEmpty(options[j])) {
						map.put("option_"+i, "请选择选择题答案，题号为  " + i);
					} else if (options[j].length() > 100) {
						map.put("option_"+i, "每个选择题选项不能超过100字，题号为  " + i);
					}  else {
						if (checkEmpty(answer)) {
							map.put("option_"+i, "请选择正确的答案，题号为  " + i);
							
						} else {
							if (checkEmpty(explain)) {
								map.put("option_"+i, "选择题解释不能为  空，题号为  " + i);
							} else if (explain.length() > 400) {
								map.put("option_"+i, "选择题解释长度应小于400，题号为  " + i);
							}
						}
					}
				}
			}
		}

		return map;
	}

	/**
	 * 检查判断题是否有错误，有返回true 无返回false
	 * @param q
	 * @param i
	 * @return
	 */
	private Map<String, String> checkJudge(Question q, int i) {
		Map<String, String> map = new HashMap<String, String>();
		String content = q.getQueContent();
		String option = q.getQueOption();
		String answer = q.getQueAnswer();
		String explain = q.getQueExplain();

		if (checkEmpty(content)) {
			map.put("judge_"+i, "请输入判断题题目，题号为  " + i);
		} else {
			if (checkEmpty(answer)) {
				map.put("judge_"+i, "请填写判断题正确答案，题号为  " + i);
			} else if (answer.length() > 400) {
				map.put("judge_"+i, "判断题答案长度应小于400,题号为  " + i);
			} else {
				if (checkEmpty(explain)) {
					map.put("judge_"+i, "判断题答案解释不能为  空，题号为  " + i);
				} else if (explain.length() > 400) {
					map.put("judge_"+i, "判断题解释长度应小于400，题号为  " + i);
					q.setQueExplain(explain.substring(0, 400));
				}
			}
		}
		return map;
	}

	/**
	 * 检查填空题是否有错误，有返回true 无返回false
	 * @param q
	 * @param i
	 * @return
	 */
	private Map<String, String> checkFill(Question q, int i) {
		Map<String, String> map = new HashMap<String, String>();
		String content = q.getQueContent();
		String answer = q.getQueAnswer();
		String explain = q.getQueExplain();

		if (checkEmpty(content)) {
			map.put("fill_"+i, "请输入填空题题目，题号为  " + i);
		} else if (content.length() > 400) {
			map.put("fill_"+i, "填空题题目长度应小于400 ，题号为  " + i);
		} else {
			if (checkEmpty(answer)) {
				map.put("fill_"+i, "填空题答案不能为  空，题号为  " + i);
			} else if (answer.length() > 400) {
				map.put("fill_"+i, "填空题答案长度应小于400，题号为  " + i);
			} else {
				if (checkEmpty(explain)) {
					map.put("fill_"+i, "填空题解释不能为  空，题号为  " + i);
					q.setQueExplain("");
				} else if (explain.length() > 400) {
					map.put("fill_"+i, "填空题解释长度应小于400，题号为  " + i);
				}
			}
		}
		return map;
	}
	
	class HtmlCreaterThread extends Thread {
		String rootUrl;
		int secId;
		List<Question> oList;
		List<Question> jList;
		List<Question> fList;
		

		public HtmlCreaterThread(String rootUrl, int secId,
				List<Question> qList, List<Question> jList, List<Question> fList) {
			super();
			this.rootUrl = rootUrl;
			this.secId = secId;
			this.oList = qList;
			this.jList = jList;
			this.fList = fList;
		}


		@Override
		public void run() {

			//String rootUrl=req.getServletContext().getRealPath("/dist/html/questionnaire")+"/";
			String fileName=new StringBuffer("questionnaire_"+secId+".html").toString();
			File file=new File(new StringBuffer(rootUrl+fileName).toString());
			String question="";
			if(file.exists()) {
				file.delete();
			}else {
				//文件夹不存在 创建文件夹
				//如果文件不存在 可能文件夹不存在 但是文件存在 文件夹一定存在 所以就少一步判断
				File rootFile=new File(rootUrl);
				if(!file.exists()) {
					rootFile.mkdir();
				}
			}
				//文件不存在
				//从数据库拿数据
				Log4JLogger log = new Log4JLogger("log4j.properties");
				String url;
				
				//获得章或节的Id
				/*从数据库读所有的数据*/
//				QuestionService qs = null;
//				List<Question>  qList = null;
//				Connection conn=null;
//				try {
//					conn= DBSource.getConnection();
//					qs = new QuestionService(conn);
//					qList=qs.queryQuestion("节",secId);			
//				} catch (Exception e) {
//					log.debug(e,e.fillInStackTrace());
//					e.printStackTrace();
//				}
//				将问题进行分类
//				String str[] = list.toArray(new String[]{}); 
//				Iterator<Question> qi= qList.iterator();
//				List<Question> oList=new ArrayList<Question>();
//				List<Question> jList=new ArrayList<Question>();
//				List<Question> fList=new ArrayList<Question>();		
//				Question q;
//				int qid = 0;
//				while(qi.hasNext()) {
//					q=qi.next();
//					qid = q.getQueId();
//					if("option".equals(q.getQueType())) oList.add(q);
//					else if("judge".equals(q.getQueType())) jList.add(q);
//					else if("fill".equals(q.getQueType())) fList.add(q);			
//				}
		
				
					//当前没有试题
					if(oList.size()<=0&&jList.size()<=0&&fList.size()<=0) {
						question= ("当前没有测试题");
					} else {
						int num=1;
						if(oList!=null) {
							question=question+"	<div id='choice_area'>"
										+"<h1>选择题</h1><hr>"
										+ "<input type='hidden' name='optionNum' value='"+oList.size()+"' />";
							for(Question qq:oList) {
								question=question+"<div class='one_choice'>"
										+"<p class='question_content'>第"+num+"题："
										+qq.getQueContent()+"</p>"
										+"<input type='hidden' value='"+qq.getQueId()+"' name='"+num+"optionid'/>";
								String str=qq.getQueOption();
								String[]os =str.split(";next;");
								char A='A';
								for(String s:os) {
									question=question+"<label class='choice_content'>"
											+ "<input type='radio' value='"+A+"' name='"+num+"option'>"
											+"<span>"+A+". "+s.substring(0,s.length() - 1)+"</span></label>";
									A++;
								}
								question+="</div>";
								num++;
							}
							question+="</div>";
						}
						//判断题
						if(jList!=null) {
							num=1;
							question+="	<div id='judge_area'>"
									+ "<h1>判断题</h1>"
									+ "	<input type='hidden' name='judgeNum' value='"+jList.size()+"'/>";
							for(Question qq:jList) {
								question+="<div class='one_judge'"
								+ "	<p class='question_content'>第"+num+"题："
								+ qq.getQueContent()+"</p>"
								+ "<input type='hidden' value='"+qq.getQueId()+"' name='"+num+"judgeid'>"
								+ "<label class='choice_content'>"
								+ "<input type='radio' class='radio' value='Y'  name='"+num + "judge'/><span>正确</span></label>"
								+ "<label class='choice_content'>"
								+ "<input type='radio' class='radio' value='N'  name='"+num + "judge'/><span>错误</span></label>"
								+ "</div>";
								num++;
							}
							question+="</div>";
						}
						//填空题
						if(fList!=null) {
							num=1;
							question+="	<div id='fill_area'>"
									+ "<h1>填空题</h1><hr>"
									+ "<input type='hidden' name='fillNum' value='"+fList.size()+"'>";
							for(Question qq:fList) {
								question+="<div class='one_fill'>"
										+ "<p class='question_content'>第"+num+"题："
										+ qq.getQueContent()+"</p>"
										+ "<input type='hidden' value='"+qq.getQueId()+"' name='"+num+"fillKeyid'>"
										+ "	<label>在此填写答案：<textarea name='"+num+"fillKey'></textarea></label>"
										+ "	</div>";
								num++;
							}
							question+="</div>";
						}

				
				
				String ftlName="questionnaire.ftl";
				Map<String,String> root=new HashMap<String,String>();
				
				root.put("secId", ""+secId);
				root.put("question", question);
				HtmlCreater.create(rootUrl,fileName,ftlName,root);
			} 
		}
		
	}

}
