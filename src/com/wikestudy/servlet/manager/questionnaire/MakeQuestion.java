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

import net.sf.json.JSONObject;

import org.apache.commons.logging.impl.Log4JLogger;

import com.wikestudy.model.pojo.Question;
import com.wikestudy.model.pojo.Teacher;
import com.wikestudy.model.util.DBSource;
import com.wikestudy.model.util.HtmlCreater;
import com.wikestudy.model.util.SaltValue;
import com.wikestudy.service.student.QuestionService;

/**
 * Servlet implementation class MakeQuestion
 */
@WebServlet("/dist/jsp/teacher/course/question_make")
public class MakeQuestion extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MakeQuestion() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Log4JLogger log = new Log4JLogger("log4j.properties");
		JSONObject json=new JSONObject();
		
		/*获得节数*/
		String secIdString=request.getParameter("secId");
		int secId=Integer.parseInt(secIdString);
		
		/*获得制作者*/
		HttpSession session=request.getSession();
		Teacher t=(Teacher)session.getAttribute("t");
		int authorId=t.getTeaId();
		
		
		/*如果是更新，需要的问题id*/
		String []idString=request.getParameterValues("id");
		List<String>  iList=null;
		Iterator<String>iterator =null;
		if(idString!=null) {
			iList=Arrays.asList(idString);
			iterator=iList.iterator();
		}
		
		int flag=0;
		if(iterator==null) flag=1;
		
		boolean hasError = false; 	//用于记录题目是否有内容上的错误
		
		/*处理选择题*/
		String[] o=request.getParameterValues("option");//题目
		List<Question> optionList = new ArrayList<>();
		
		//如果存在选择题，则执行选择题的创建
		if(o != null && o.length > 0){
		
			List<String> oList=Arrays.asList(o);
			/*获得选择题答案*/
			List<String> oaList=new ArrayList<String>();
			{		
				String []on=request.getParameterValues("optionNum");
				for(int i=0;i<on.length;i++) {
				System.out.println(on[i]);
				String junmingString=request.getParameter(on[i]);
					oaList.add(junmingString);
				}			
			}
			/*选择题的内容*/
			String[] okn =request.getParameterValues("optionKeyNum");
			String[] ocall =request.getParameterValues("optionKey");//所有的选项内容
			int all = 0;//用来遍历所有的答案
			
			List<String> ocList = new ArrayList<String>();//答案集合
			for(String n:okn)
			{
				Integer ii=Integer.parseInt(n);
				StringBuilder ss=new StringBuilder();
				for(int i=0;i<ii;i++) {
					ss.append(ocall[all++]);
					ss.append(" ;next;");
				}
				String ans = ss.toString();
				ocList.add(ans);//整理好的答案放进集合			
			}		
			/*选择题的解释*/
			String [] oe =request.getParameterValues("optionExplain");
			List<String> oeList = Arrays.asList(oe);
			
			Iterator <String> oIterator=oList.iterator();//题目
			Iterator <String> oaIterator=oaList.iterator();//答案
			Iterator <String> ocIterator=ocList.iterator();//选项
			Iterator <String> oeIterator=oeList.iterator();//解释
			Question op = null;
			int i = 0;
			while(oaIterator.hasNext()&&!hasError) {
				op=new Question();
				op.setChaSecId(secId);
				op.setChaSecType("节");//暂时只有章
				op.setMakerId(t.getTeaId());
				/*真值*/
				if(flag==0) {
					op.setQueId(Integer.parseInt(iterator.next()));
				}
				op.setQueAnswer(oaIterator.next());
				op.setQueContent(oIterator.next());
				op.setQueCorrectNum(0);
				op.setQueExplain(oeIterator.next());
//				q.setQueNum(queNum);题号好像不需要
				op.setQueOption(ocIterator.next());
				op.setQuePersonNum(0);
				op.setQueType("option");
				
				if(checkOption(op, i++, json))
						hasError = true;
				
				optionList.add(op);
			}
		}
		
		
		/*处理判断题*/
		/*判断题的问题*/
		String []j=request.getParameterValues("judge");
		List<Question> judgeList = new ArrayList<>();
		
		if(!hasError) {
			if(j!= null && j.length > 0) {
				List<String> jList=Arrays.asList(j);
				/*判断题的答案*/
				String []jn=request.getParameterValues("judgeNum");
				List <String>jaList=new ArrayList<String>();
				for(String s:jn) {//53
					jaList.add(request.getParameter(s));
				}
				/*判断题的解释*/
				String[] je=request.getParameterValues("judgeExplain");
				List<String> jeList=Arrays.asList(je);
				
				/*添加判断题*/
				Iterator<String> jIterator=jList.iterator();
				Iterator<String> jaIterator=jaList.iterator();
				Iterator<String> jeIterator=jeList.iterator();
				Question judge = null;
				int i = 0;
				while(jaIterator.hasNext()) {
					judge=new Question();
					judge.setChaSecId(secId);
					judge.setChaSecType("节");
					judge.setMakerId(authorId);
					/*真值*/
					if(flag==0)
						judge.setQueId(Integer.parseInt(iterator.next()));
					judge.setQueAnswer(jaIterator.next());
					judge.setQueContent(jIterator.next());
					judge.setQueCorrectNum(0);
					judge.setQueExplain(jeIterator.next());
//					q.setQueNum(queNum);题号好像不需要
					judge.setQueOption("");
					judge.setQuePersonNum(0);
					judge.setQueType("judge");
					
					if(checkJudge(judge, i++, json))
						hasError = true;
					
					judgeList.add(judge);
				}
			}
		} 
		
		
		/*填空题*/
		/*填空题题目*/
				String []f=request.getParameterValues("fill");
				List<Question> fillList = new ArrayList<>();
				
				if(!hasError) {
				if(f != null && f.length > 0 ) {
				List<String> fList=Arrays.asList(f);
				/*填空题答案*/
				String []fk=request.getParameterValues("fillKey");
				List<String> faList=Arrays.asList(fk);
				/*填空题解释*/
				String []fe=request.getParameterValues("fillExplain");
				List<String> feList=Arrays.asList(fe);
			
				Iterator<String> fIterator=fList.iterator();
				Iterator<String> faIterator=faList.iterator();
				Iterator<String> feIterator=feList.iterator();
				Question fill = null;
				int i = 0;
				while(faIterator.hasNext()) {
					/*11参数*/
					/*假值*/
					fill=new Question();
					fill.setChaSecId(secId);
					fill.setChaSecType("节");
					fill.setMakerId(authorId);
					/*真值*/
					if(flag==0)
						fill.setQueId(Integer.parseInt(iterator.next()));
					fill.setQueAnswer(faIterator.next());
					fill.setQueContent(fIterator.next());
					fill.setQueCorrectNum(0);
					fill.setQueExplain(feIterator.next());
//					q.setQueNum(queNum);题号好像不需要
					fill.setQueOption("");
					fill.setQuePersonNum(0);
					fill.setQueType("fill");
					
					if(checkFill(fill, i++, json))
						hasError = true;
					
					fillList.add(fill);
				}	
			}
			}
		
			
		//判断是否存在错误
		if(hasError && flag == 1){
			request.setAttribute("hasError", true);
			request.setAttribute("options", optionList);
			request.setAttribute("judges", judgeList);
			request.setAttribute("fills", fillList);
			request.setAttribute("secId", secId);
	
			json.put("status", "error");
			PrintWriter writer= response.getWriter();
			writer.print(json);
			writer.flush();
			writer.close();
			//request.getRequestDispatcher("question_make.jsp").forward(request, response);
			return;
		}else if(hasError && flag == 0) {
			request.setAttribute("hasError", true);
			request.setAttribute("options", optionList);
			request.setAttribute("judges", judgeList);
			request.setAttribute("fills", fillList);
			request.setAttribute("secId", secId);

			json.put("status", "error");
			PrintWriter writer= response.getWriter();
			writer.print(json);
			writer.flush();
			writer.close();
	
			//request.getRequestDispatcher("question_page.jsp").forward(request, response);
			return;
		}
			
		
		
		
			/*整理存入问题集合*/
		List<Question> qList = new ArrayList<Question>();// 题目对象集合
		qList.addAll(optionList);
		qList.addAll(judgeList);
		qList.addAll(fillList);
		
		/*存进数据库*/
		QuestionService qs;
		Connection conn = null;
		try {
			conn= DBSource.getConnection();
			qs = new QuestionService(conn);
			String submit=request.getParameter("submit");
			if("commit".equals(submit))
				qs.addQuestion(qList);
			else if("update".equals(submit)) 
				qs.updateQuestion(qList);
			
		} catch (Exception e) {
			System.out.println("存进数据库失败");
			log.debug(e,e.fillInStackTrace());
			e.printStackTrace();
		} finally {
			try {
				conn.close();
			} catch (SQLException e) {
				log.debug(e,e.fillInStackTrace());
				e.printStackTrace();
			}
		}
		new HtmlCreaterThread(new StringBuffer(request.getServletContext().getRealPath("/dist/html/questionnaire")+"/"+SaltValue.getSaltValue("question"+secId)+"/").toString(), secId,optionList,judgeList,fillList).start();
		//返回json

		
		//request.getRequestDispatcher("question_page?secId="+secId).forward(request, response);
	}
	
	private boolean checkEmpty(String content) {
		if(content == null || "".equals(content) || "".equals(content.trim()))
			return true;
		
		return false;
	}
	
	//检查选择题是否有错误，有返回true 无返回false
	private boolean checkOption(Question q, int i, JSONObject json) {
		String message="";
		boolean hasError = false;
		String content = q.getQueContent();
		String option = q.getQueOption();
		String answer = q.getQueAnswer();
		String explain = q.getQueExplain();
		
		if(checkEmpty(content)){
			message="选择题" +( i+1)+ "：请输入选择题题目";
			hasError = true;
		}else if(content.length() > 400){
			message="选择题" +( i+1)+ "：选择题题目长度不超过400";
			hasError = true;
		}
		
		if(checkEmpty(option)&&!hasError){
			message="选择题" +( i+1)+ "：请输入选择题答案";
			hasError = true;
		}else{
			String[] options = option.split(";next;");
			for(int j = 0; j < options.length&&!hasError; j++){
				if(checkEmpty(options[j])){
					message="选择题" +( i+1)+ "：请输入选择题答案";
					hasError = true;
				}else if(options[j].length() > 100){
					message="选择题" +( i+1)+"：每个选项不能超过100字";
					hasError = true;
				}
			}
		}
		
		if(checkEmpty(answer)){
			message="选择题" +( i+1)+ "：请选择正确答案";
			hasError = true;
		}else{
			q.setQueAnswer(answer.substring(0, 1));
		}
		
		if(checkEmpty(explain)){
			q.setQueExplain("");
		}else if(explain.length() > 400)
			q.setQueExplain(explain.substring(0, 400));
		
		json.put("message", message);
		return hasError;
	}
	
	//检查判断题是否有错误，有返回true 无返回false
	private boolean checkJudge(Question q, int i,JSONObject json) {
		String message="";
		boolean hasError = false;
		String content = q.getQueContent();
		String option = q.getQueOption();
		String answer = q.getQueAnswer();
		String explain = q.getQueExplain();
		
		if(checkEmpty(content)){
			message="判断题" +( i+1)+ "：请输入判断题题目";
			hasError = true;
		}else if(content.length() > 400){
			message="判断题" +( i+1)+ "：判断题题目长度不超过400";
			hasError = true;
		}
		
	
		if(checkEmpty(answer)){
			message="判断题" +( i+1)+ "：请选择正确答案";
			hasError = true;
		}else{
			q.setQueAnswer(answer.substring(0, 1));
		}
		
		if(checkEmpty(explain)){
			q.setQueExplain("");
		}else if(explain.length() > 400)
			q.setQueExplain(explain.substring(0, 400));
		json.put("message", message);
		return hasError;
	}
	
	//检查填空题是否有错误，有返回true 无返回false
	private boolean checkFill(Question q, int i, JSONObject json) {
		String message="";
		boolean hasError = false;
		String content = q.getQueContent();
		String answer = q.getQueAnswer();
		String explain = q.getQueExplain();
		
		if(checkEmpty(content)){
			message="填空题" +( i+1)+ "：请输入填空题题目";
			hasError = true;
		}else if(content.length() > 400){
			message="填空题" +( i+1)+ "：填空题题目长度不超过400";
			hasError = true;
		}
		
	
		if(checkEmpty(answer)){
			message="填空题" +( i+1)+ "：请选择正确答案";
			hasError = true;
		}else if(answer.length() > 400){
			message="填空题" +( i+1)+"：填空题答案长度不超过400";
			hasError = true;
		}
		
		if(checkEmpty(explain)){
			q.setQueExplain("");
		}else if(explain.length() > 400)
			q.setQueExplain(explain.substring(0, 400));
		json.put("message", message);
		return hasError;
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
