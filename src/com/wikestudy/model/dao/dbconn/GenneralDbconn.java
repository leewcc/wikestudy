package com.wikestudy.model.dao.dbconn;

import java.lang.reflect.Method;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Timestamp;
import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.stream.events.EndDocument;

import com.sun.org.apache.bcel.internal.generic.RETURN;

public class GenneralDbconn<T> {
	//　执行插入方法
	public int insert(PreparedStatement pstmt) throws Exception{
		ResultSet rs = null;
		
		try{
			pstmt.executeUpdate();
			rs = pstmt.getGeneratedKeys();
			if(rs.next())
				return rs.getInt(1);
			
			return -1;
			
		}catch(Exception e){
			e.printStackTrace();
			return -1;
		}
		finally{
			rs.close();
			pstmt.close();
		}
		
	}
	
	
	// 更新方法
	public int update(PreparedStatement pstmt) throws Exception {
		try {

			return pstmt.executeUpdate();

		} finally {
			pstmt.close();
		}

	}

	// 批量操作方法
	public int[] batch(PreparedStatement pstmt) throws Exception {
		try {

			return pstmt.executeBatch();

		} finally {
			System.out.println(pstmt.toString());
			pstmt.close();
		}

	}
	
	public List<Integer> insertBatch(PreparedStatement pstmt) throws Exception{
		ResultSet rs = null;
		
		try {
			pstmt.executeBatch();
			rs = pstmt.getGeneratedKeys();
			List<Integer> id = new ArrayList<>();
			
			while(rs.next()) {
				id.add(rs.getInt(1));
			}
			
			return id;

		} finally {
			pstmt.close();
		}
	}

	// 获取总记录数的方法
	public int getRows(PreparedStatement pstmt) throws Exception {
		long begin = System.currentTimeMillis();
		ResultSet rs = null;

		try {

			rs = pstmt.executeQuery();

			if (rs.next())
				return rs.getInt("rows");

			return 0;

		} finally {
			System.out.println(pstmt.toString());
			rs.close();
			pstmt.close();
			
			long end = System.currentTimeMillis();
			System.out.println("此次查询共用了  " + (end - begin));
		}

	}

	
	/**
	 * 查询语句执行
	 * @param clazz 映射的实体类
	 * @param pstmt	要执行的查询
	 * @return
	 * @throws Exception
	 */
	public List<T> query(Class<T> clazz, PreparedStatement pstmt)
			throws Exception {
		//　定义存放结果集对象
		ResultSet rs = null;

		try {
			//　执行查询方法，获取查询结果
			rs = pstmt.executeQuery();
			
			// 获取投影的属性字段集
			Map<String, Class> colNames = getColName(rs.getMetaData());
			
			// 根据搜索的结果组装对象集合,并返回给上一层对象
			return installObject(clazz, colNames, rs);

		} finally {
			rs.close();
			pstmt.close();
		}
		
	}
	
	
	//　获取数据库字段的类型与 java 实体属性类型映射
	private Class getType(int type) {
		switch (type) {
		case Types.INTEGER:
			return int.class;

		case Types.BIT:
			return boolean.class;

		case Types.BIGINT:
			return long.class;

		case Types.DATE:
			return Date.class;

		case Types.TIMESTAMP:
			return Timestamp.class;

		default:
			return String.class;
		}
	}
		
	// 获取搜索的列名字符串数组
	private Map<String, Class> getColName(ResultSetMetaData data) throws Exception {
		int count = data.getColumnCount();

		Map<String, Class> colMess = new LinkedHashMap<String, Class>();
		String colName = "";
		Class type = null;

		for (int i = 1; i <= count; i++) {
			colName = TransformName(data.getColumnLabel(i));
			type = getType(data.getColumnType(i));
			colMess.put(colName, type);
		}

		return colMess;
	}

	// 列名转换,将数据库的列名转化成java对应的属性名
	private String TransformName(String colName) {
		String[] session = colName.split("_");
		StringBuilder sb = new StringBuilder();

		for (int i = 0; i < session.length; i++) {
			char[] cs = session[i].toCharArray();
			cs[0] -= 32;
			sb.append(String.valueOf(cs));
		}

		return sb.toString();
	}

	// 根据属性名和结果集构照对应的类
	private List<T> installObject(Class<T> clazz, Map<String, Class> colMess, ResultSet rs) throws Exception {
		List<T> list = new ArrayList<T>();
		Set<String> colNames = colMess.keySet();

		// 迭代结果集,讲每行结果构照出一个映射类
		while (rs.next()) {
			T t = clazz.newInstance();
			int i = 0;
			for (String colName : colNames) {
				// 获取set方法的字符串
				String methodName = "set" + colName;

				Method m = clazz.getMethod(methodName, colMess.get(colName));

				try {
					m.invoke(t, rs.getObject(i + 1));
				} catch (IllegalArgumentException e) {
					m.invoke(t, 0);
				}

				i++;
			}

			list.add(t);
		}

		return list;

	}
	
	
	
	

//	// 获取搜索的列名字符串数组
//	private String[] getColName(ResultSetMetaData data) throws Exception {
//		int count = data.getColumnCount();
//
//		String[] colNames = new String[count];
//
//		for (int i = 1; i <= count; i++) {
//			colNames[i - 1] = TransformName(data.getColumnLabel(i));
//			
//		}
//		return colNames;
//	}
//	
//	// 列名转换,将数据库的列名转化成java对应的属性名
//	private String TransformName(String colName) {
//		String[] session = colName.split("_");
//		StringBuilder sb = new StringBuilder();
//
//		for (int i = 0; i < session.length; i++) {
//			char[] cs = session[i].toCharArray();
//			cs[0] -= 32;
//			sb.append(String.valueOf(cs));
//		}
//
//		return sb.toString();
//	}
//
//	// 根据属性名和结果集构照对应的类
//	private List<T> installObject(Class<T> clazz, String[] colNames,
//			ResultSet rs) throws Exception {
//		List<T> list = new ArrayList<T>();
//
//		// 获取对应类的所有方法
//		Method[] ms = clazz.getMethods();
//
//		// 迭代结果集,讲每行结果构照出一个映射类
//		while (rs.next()) {
//			T t = clazz.newInstance();
//
//			for (int i = 0; i < colNames.length; i++) {
//				// 获取set方法的字符串
//				String colName = colNames[i];
//				String methodName = "set" + colName;
//
//				// 迭代方法列表,找到对应的方法并调用
//				for (Method m : ms) {
//					if (methodName.equals(m.getName())) {
//
//						m.invoke(t, rs.getObject(i + 1));
//						break;
//					}
//				}
//
//			}
//
//			list.add(t);
//		}
//
//		return list;
//	}
}
