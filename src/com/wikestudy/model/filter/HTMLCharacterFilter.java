package com.wikestudy.model.filter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;


@WebFilter(
		//urlPatterns={"/*"},//配置成过滤用户就可以了
		initParams={ 
		@WebInitParam(name="WORD",value="/WEB-INF/word.txt")
		})


public class HTMLCharacterFilter implements Filter {
	public void destroy() {
		// TODO Auto-generated method stub

	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		response.setContentType("text/html;charset=UTF-8");
		request.setCharacterEncoding("UTF-8");
		// 非法字符过滤
		HttpServletRequest myRequest = (HttpServletRequest) request;
	/*	String url=myRequest.getRequestURI();
		if("/wikestudy/dist/jsp/Manager/article/PublishArticle".equals(url)) 
		{
			chain.doFilter(request, response);
			return ;
		}*///禁止用户输入敏感词
		Map map = myRequest.getParameterMap();
		Collection coll = map.values();
		// 取出过滤的字
		Set<String> keys=hm.keySet();
		for (Iterator iterator = coll.iterator(); iterator.hasNext();) {
			String[] s = (String[]) iterator.next();
			for (int i = 0; i < s.length; i++) {
				// 需要过滤的字符
				s[i]=s[i].replaceAll("<", "&lt;").replaceAll(">", "&gt;");
/*						.replaceAll("\n", "<br>").replaceAll("\'", "&apos;")
						.replaceAll("\"", "&quot;");*/
				// 过滤不雅字
				Iterator it=keys.iterator();
				while(it.hasNext()) {
					String key=(String)it.next();
					String value=(String) hm.get(key);
					s[i]=s[i].replace(key,value);
				}
			}
		}
		// 放行
		chain.doFilter(request, response);
	}

	public HashMap<String, String> hm = new HashMap<String, String>();

	public void init(FilterConfig filterConfig) throws ServletException {

		String configPath = filterConfig.getInitParameter("WORD");

		ServletContext sc = filterConfig.getServletContext();
		String filePath = sc.getRealPath(configPath);
		try {
			FileReader fr = new FileReader(filePath);
			BufferedReader br = new BufferedReader(fr);

			String line;
			while (null != (line = br.readLine())) {
				String[] strTemp = line.split("=");
				//分别是要替换的字和替换的字
				hm.put(strTemp[0], strTemp[1]);
			}
		} catch (IOException ie) {
			throw new ServletException("读取过滤文件信息出错！");
		}

	}

}