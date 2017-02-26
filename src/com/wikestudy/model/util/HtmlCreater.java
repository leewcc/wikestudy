package com.wikestudy.model.util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;

/**
 * 
 * @author CHEN
 * <pre>html页面生成器</pre>
 *
 */
public class HtmlCreater {

	/**
	 * 
	 * @param rootUrl  html文件根路径 
	 * @param fileName  文件名
	 * @param ftlName  jtl的文件名
	 * @param map  映射属性
	 * @return  操作结果
	 */
	public static boolean create(String rootUrl,String fileName,String ftlName,Map<String, String> root){
		boolean flag=true;
		try {
			Class.forName("com.wikestudy.model.util.HtmlCreater");
		} catch (ClassNotFoundException e2) {
			e2.printStackTrace();
		}
		Configuration config=new Configuration();
		config.setClassForTemplateLoading(HtmlCreater.class, "/com/wikestudy/templates");////从class 目录开始
		config.setObjectWrapper(new DefaultObjectWrapper());
		config.setEncoding(Locale.getDefault(), "utf-8");
		
		
		//输出文件
		FileOutputStream fileOutputStream;
		try {
			Template template=config.getTemplate(ftlName,"utf-8");//查找ftl模板
			fileOutputStream = new FileOutputStream(rootUrl+fileName);
			Writer out=new OutputStreamWriter(fileOutputStream,"utf-8");
			try {
				template.process(root, out);
				out.flush();
				out.close();
			} catch (TemplateException e) {
				e.printStackTrace();
				flag=false;
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
			flag=false;
			
		} catch (IOException e1) {
			e1.printStackTrace();
			flag=false;
		}
		return flag;
	}
}
