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
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
import com.wikestudy.model.pojo.Photo;

public class ImageCut {

	/**
	 * 图片切割
	 * 
	 * @param imagePath
	 *            原图地址
	 * @param x
	 *            目标切片坐标 X轴起点
	 * @param y
	 *            目标切片坐标 Y轴起点
	 * @param w
	 *            目标切片 宽度
	 * @param h
	 *            目标切片 高度
	 */
	public boolean cutImage(Photo p) {
		/*
		 * try { Image img; ImageFilter cropFilter; //原图的路径 String
		 * imagePath=map.get("imageUrl"); //存放的路径 String
		 * savePath=map.get("savePath"); // 读取源图像 if(imagePath==null) return
		 * false; BufferedImage bi = ImageIO.read(new File(imagePath)); int
		 * srcWidth = bi.getWidth(); // 源图宽度 int srcHeight = bi.getHeight(); //
		 * 源图高度
		 * 
		 * //若原图大小大于切片大小，则进行切割 // if (srcWidth >= w && srcHeight >= h) { Image
		 * image = bi.getScaledInstance(srcWidth,
		 * srcHeight,Image.SCALE_DEFAULT); int x1,y1,w1,h1; if(w<=1||h<=1) {
		 * x1=0; y1=0; w1=300; h1=300;
		 * 
		 * } else { x1 = x*srcHeight/300; y1 = y*srcHeight/300; w1 =
		 * w*srcHeight/300; h1 = h*srcHeight/300; } cropFilter = new
		 * CropImageFilter(x1, y1, w1, h1); img =
		 * Toolkit.getDefaultToolkit().createImage(new
		 * FilteredImageSource(image.getSource(), cropFilter)); BufferedImage
		 * tag = new BufferedImage(w1, h1,BufferedImage.TYPE_INT_RGB); Graphics
		 * g = tag.getGraphics(); g.drawImage(img, 0, 0, null); // 绘制缩小后的图
		 * g.dispose(); // 重新进行压缩 File t=new File(savePath); ImageIO.write(tag,
		 * "JPEG", t); zipWidthHeightImageFile(t, t, 400, 400, 100); //} return
		 * true; } catch (IOException e) { e.printStackTrace(); return false; }
		 */

		try {
			Image img;
			ImageFilter cropFilter;
			// 原图的路径
			String imagePath = p.getOldUrl();
			// 存放的路径
			String savePath = p.getSaveUrl();
			// 读取源图像
			if (imagePath == null)
				return false;
			BufferedImage bi = ImageIO.read(new File(imagePath));
			int srcWidth = bi.getWidth(); // 源图宽度
			int srcHeight = bi.getHeight(); // 源图高度
			// 若原图大小大于切片大小，则进行切割
			// if (srcWidth >= w && srcHeight >= h) {
			Image image = bi.getScaledInstance(srcWidth, srcHeight,
					Image.SCALE_DEFAULT);
			int x1, y1, w1, h1;
			if (p.getW() <=1 || p.getH() <= 1) {
				x1 = 0;
				y1 = 0;
				w1 = 300;
				h1 = 300;

			} else {
				x1 = p.getX() * srcHeight / 300;
				y1 = p.getY() * srcHeight / 300;
				w1 = p.getW() * srcHeight / 300;
				h1 = p.getH() * srcHeight / 300;
			}
			cropFilter = new CropImageFilter(x1, y1, w1, h1);
			img = Toolkit.getDefaultToolkit().createImage(
					new FilteredImageSource(image.getSource(), cropFilter));
			BufferedImage tag = new BufferedImage(w1, h1,
					BufferedImage.TYPE_INT_RGB);
			Graphics g = tag.getGraphics();
			g.drawImage(img, 0, 0, null); // 绘制缩小后的图
			g.dispose();
			// 重新进行压缩
			File t = new File(savePath);
			ImageIO.write(tag, "JPEG", t);
			zipWidthHeightImageFile(t, t, 400, 400, 100);
			// }
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}

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