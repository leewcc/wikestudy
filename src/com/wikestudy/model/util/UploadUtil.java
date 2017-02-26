package com.wikestudy.model.util;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.servlet.ServletRequestContext;
import org.apache.commons.io.filefilter.SuffixFileFilter;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

public class UploadUtil {
	HttpServletRequest request = null;
	HttpSession session = null;
	String tempPath = null;
	String savePath =null;

	public UploadUtil(HttpServletRequest request,HttpSession session) {
		this.request = request;
		this.session = session;
		this.tempPath=request.getServletContext().getRealPath("/dist//temp");
		this.savePath=request.getServletContext().getRealPath("/dist/images");
	}
	
	/**
	 * @param map 瀛樹俊鎭繑鍥炰笂涓�灞�
	 * @param message 杩斿洖鐨勪俊鎭�
	 * @return
	 * @throws Exception
	 */

	public   boolean uploadFile(Map<String, String> map
			) throws Exception {
		
		String fileName =null;
	
	
		// 璁剧疆鏂囦欢涓婁紶鍓嶇殑涓�浜涢厤缃�
		DiskFileItemFactory factory = new DiskFileItemFactory();
		//鏂囦欢澶у皬
		factory.setSizeThreshold(10 * 1024);

		factory.setRepository(new File(tempPath));

		// 寰楀埌涓�涓В鏋愬櫒
		ServletFileUpload upload = new ServletFileUpload(factory);
		// 璁剧疆缂栫爜鏍煎紡
//		upload.setHeaderEncoding("utf-8");

		// 灏嗚姹備紶鍏ュ埌瑙ｆ瀽鍣ㄤ腑锛屽璇锋眰杩涜瑙ｆ瀽
		List<FileItem> list = upload.parseRequest(new ServletRequestContext(
				request));
		// 瀹氫箟闄愬埗涓婁紶鐨勬枃浠剁被鍨嬬殑瀛楃涓叉暟缁�
		String[] suffixs = new String[] {};
		// 鍒涘缓鏂囦欢鍚庣紑杩囨护鍣�
		SuffixFileFilter filter = new SuffixFileFilter(suffixs);

		// 璁剧疆涓婁紶鏂囦欢鐨勬渶澶уぇ灏� 20M
		int maxSize = 1 * 1024 * 1024;
		for (FileItem item : list) {
			if (item.isFormField()) {
				String inputName = item.getFieldName();
				String inputValue = item.getString();
//				inputValue = new String(inputValue.getBytes("iso8859-1"),
//						"UTF-8");
//				map.put(inputName, inputValue);
			} else {
				fileName = item.getName();//鐛茬殑鏂囦欢鍚�
				String newFileName=UUID.randomUUID()+fileName
						.substring(fileName.lastIndexOf("."));

				if (!fileName.equals("")) {
					fileName = fileName
							.substring(fileName.lastIndexOf(File.separator) + 1);

					File file = new File(savePath, fileName);

					if (item.getSize() > maxSize) {
						return false;
					}

					savePath = map.get("savePath");
				}			
				FileOutputStream out = new FileOutputStream(savePath+File.separator+newFileName); // 閲嶆柊鍛藉悕鏂囦欢
				
				map.put("fileName",newFileName);
				InputStream in = item.getInputStream();
				// 寮�濮嬪瓨鍏ユ湇鍔″櫒绔�
				byte[] buf = new byte[1024];
				int len = 0;
				while ((len = in.read(buf)) > 0)
					out.write(buf, 0, len);
				in.close();
				out.close();
				item.delete();// 鍒犻櫎涓存椂鏂囦欢
			} // if 缁撴潫

		} // 鍒ゆ柇鏄惁鏄笅杞芥枃浠剁粨鏉�	
	
		return true;
	}
	  /** 
     * 鎸夊搴﹂珮搴﹀帇缂╁浘鐗囨枃浠�<br> 鍏堜繚瀛樺師鏂囦欢锛屽啀鍘嬬缉銆佷笂浼� 
     * @param oldFile  瑕佽繘琛屽帇缂╃殑鏂囦欢鍏ㄨ矾寰� 
     * @param newFile  鏂版枃浠� 
     * @param width  瀹藉害 
     * @param height 楂樺害 
     * @param quality 璐ㄩ噺 
     * @return 杩斿洖鍘嬬缉鍚庣殑鏂囦欢鐨勫叏璺緞 
     */  
    public static String zipWidthHeightImageFile(File oldFile,File newFile, int width, int height,  
            float quality) {  
        if (oldFile == null) {  
            return null;  
        }  
        String newImage = null;  
        try {  
            /** 瀵规湇鍔″櫒涓婄殑涓存椂鏂囦欢杩涜澶勭悊 */  
            Image srcFile = ImageIO.read(oldFile);  
  
            /** 瀹�,楂樿瀹� */  
            BufferedImage tag = new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);  
            tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);  
            /** 鍘嬬缉鍚庣殑鏂囦欢鍚� */  
  
            /** 鍘嬬缉涔嬪悗涓存椂瀛樻斁浣嶇疆 */  
            FileOutputStream out = new FileOutputStream(newFile);  
  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);  
            /** 鍘嬬缉璐ㄩ噺 */  
            jep.setQuality(quality, true);  
            encoder.encode(tag, jep);  
            out.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return newImage;  
    }  
}
