package com.wikestudy.model.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;



import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.StringMap;
import com.qiniu.util.UrlSafeBase64;

import net.sf.json.JSONObject;
import net.sf.json.JSONSerializer;


public class QiNiuUtil {
	private static String DEFAULT_PROPERTIES = "video.properties";
	private static Properties properties = new Properties();
	static {
		String path = QiNiuUtil.class.getResource("/").toString();
		path = path.substring(6, path.length() - 8) + DEFAULT_PROPERTIES;
		System.out.println(path);
		try {
			FileInputStream fileInputStream = new FileInputStream(path);
			properties.load(fileInputStream);
			System.out.println(properties.toString());
		} catch (IOException e) {
			System.out.println("配置文件不存在，加载配置文件失败");
		}
		
	}
	
	public static String domian = properties.getProperty("domain");
	
	private static String ACCESS_KEY = properties.getProperty("access_key");
	private static String SECRET_KEY = properties.getProperty("secert_key");
	
	// 要上传的空间
	private static String bucketname = properties.getProperty("bucketname");
	
	// 设置切片操作参数
	private static String fops =properties.getProperty("fops");
		
	// 设置转码的队列
	private static String pipeline = properties.getProperty("pipeline");
		



  //密钥配置
  private static Auth auth = Auth.create(ACCESS_KEY, SECRET_KEY);
  //创建上传对象
  private static UploadManager uploadManager = new UploadManager();

  //上传策略中设置persistentOps字段和persistentPipeline字段
  public static String getUpToken(String pfops){
      return auth.uploadToken(bucketname,null,3600,new StringMap()
          .putNotEmpty("persistentOps", pfops)
          .putNotEmpty("persistentPipeline", pipeline), true);
  }

  public static String upload(byte[] data, String key) throws IOException{
	 
	  Response res = null;
	  String id = "error";
	  try {
		  // 调用put方法上传
		  // 指定文件以 m3u8 格式另存
		  String urlbase64 = UrlSafeBase64.encodeToString(bucketname + ":" + key + ".m3u8");
		  res = uploadManager.put(data, key, getUpToken(fops + "|saveas/"+ urlbase64));
      
		  
	      //打印返回的信息
	      System.out.println(res.bodyString());
	      String json = res.bodyString();
	        
	      try {
	    	JSONObject jsonObject = (JSONObject) JSONSerializer.toJSON(json); 
			id = jsonObject.getString("persistentId");
		} catch (Exception e) {
			
			e.printStackTrace();
		}
      
      } catch (QiniuException e) {
          Response r = e.response;
          // 请求失败时打印的异常的信息
          System.out.println(r.toString());
          
          try {
              //响应的文本信息
            System.out.println(r.bodyString());
          } catch (QiniuException e1) {
              //ignore
          }
      } 
    return res.isOK() ? id : "error" ;
  }

}
