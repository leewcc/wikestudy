package com.wikestudy.model.util;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.awt.image.ImageFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.wikestudy.model.pojo.Photo;

public class PictureTool {
	public static boolean uploadPicture(Photo p) throws IOException {
		Image img;// 重画的图片
		ImageFilter imageFilter;// 图片处理器
		BufferedImage bimg;// 图片的实现类
		int srcWidth;// 原图宽
		int srcHeight;// 原图高
		int x = 0, y = 0, w = 300, h = 300;// 存放真实起点宽高
		if (p.getOldUrl() == null)
			return false;
		else {
			bimg = ImageIO.read(new File(p.getOldUrl()));
			srcWidth = bimg.getWidth();
			srcHeight = bimg.getHeight();
		}
		// 进行切割
		Image tempImg = bimg.getScaledInstance(srcWidth, srcHeight,
				Image.SCALE_DEFAULT);

		// 原图大于切片大小
		
	
	//	if (srcWidth >= p.getW() && srcHeight >= p.getH()) {
			x = p.getX() * srcHeight / p.getWid();
			y = p.getY() * srcHeight / p.getHei();
			w = p.getW() * srcHeight / p.getWid();
			h = p.getH() * srcHeight / p.getHei();

		//}
			if(p.getW()<=1||p.getH()<=1) {
				x=0;y=0;
				w=300;
				h=300;
			}
		imageFilter = new CropImageFilter(x, y, w, h);

		img = Toolkit.getDefaultToolkit().createImage(
				new FilteredImageSource(tempImg.getSource(), imageFilter));
		BufferedImage tag = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics g = tag.getGraphics();
		g.drawImage(img, 0, 0, null); // 绘制缩小后的图
		g.dispose();
		// 重新进行压缩
		File t = new File(p.getSaveUrl());
		ImageIO.write(tag, "JPEG", t);
		zipWidthHeightImageFile(t, t, p.getW(), p.getH(), 100);

		return true;
	}

	public static String zipWidthHeightImageFile(File oldFile, File newFile,
			int width, int height, float quality) {
		if (oldFile == null) {
			return null;
		}
		String newImage = null;
		try {
			/** 对服务器上的临时文件进行处理 */
			Image srcFile = ImageIO.read(oldFile);
			int w = srcFile.getWidth(null);
			// System.out.println(w);
			int h = srcFile.getHeight(null);
			// System.out.println(h);

			/** 宽,高设定 */
			BufferedImage tag = new BufferedImage(width, height,
					BufferedImage.TYPE_INT_RGB);
			tag.getGraphics().drawImage(srcFile, 0, 0, width, height, null);
			// String filePrex = oldFile.substring(0, oldFile.indexOf('.'));
			/** 压缩后的文件名 */
			// newImage = filePrex + smallIcon+
			// oldFile.substring(filePrex.length());

			/** 压缩之后临时存放位置 */
			FileOutputStream out = new FileOutputStream(newFile);

			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(out);
			JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(tag);
			/** 压缩质量 */
			jep.setQuality(quality, true);
			encoder.encode(tag, jep);
			out.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 清空缓存

		return newImage;
	}
}