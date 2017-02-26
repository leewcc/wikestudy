package com.wikestudy.model.util;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.PixelGrabber;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.stream.Stream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import sun.awt.shell.ShellFolder;



public class IconToot {
	
		//根据文件类型获取图片
	    public static BufferedImage getImageByFileTyle(String filename, String url)  
	            throws FileNotFoundException {  
	        File file = null;  
	             
	        //第一步：	获取文件后缀名
	        String extension = filename.substring(filename.lastIndexOf(".") + 1).toLowerCase();
	        
	        url = url + "WEB-INF" + File.separator + "course"; 
	        
	        File catalog = new File(url);
	        
	        File[] files = catalog.listFiles(new FileFilter(extension));
	        
	        if(files.length > 0){
	        	file = files[0];
	        }else{
	        	//获取空白文件的图标
	        	file = new File(url + File.separator + "psd.ico");
//		        try {  
//		        //第二步：	创建一个空文件，文件名为 icon. + 后缀名
//		            file = File.createTempFile("icon", extension, catalog);  
//		            
//		        } catch (IOException e) {  
//		            e.printStackTrace();  
//		        }  
	        }
	        
	        
	        //第三步：	返回该文件的图片
//	        return toBufferedImage(toImage(toIcon(file)));  
	        return toBufferedImage(file);
	  
	    }  
	    
	    public static BufferedImage toBufferedImage(File file) {
	    	BufferedImage image = null;
	    	InputStream stream = null;
	    	if(file.exists()){
	    		try {
	    			stream = new FileInputStream(file);
					image = ImageIO.read(stream);
				} catch (IOException e) {
					
					e.printStackTrace();
				}finally {
					try {
						stream.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
	    	}
	    	
	    	return image;
	    }
	  
	    //获取文件的图标
	    public static Icon toIcon(File file) throws FileNotFoundException  {  
	    	
	    	ShellFolder shellFolder = ShellFolder.getShellFolder(file);  
	        Icon icon = new ImageIcon(shellFolder.getIcon(true));  
//	        Icon icon = null;
//			try {
//				BufferedImage image = ImageIO.read(file);
//				icon = new ImageIcon(image);
//			} catch (IOException e) {
//				icon = new ImageIcon();
//				e.printStackTrace();
//			}
	        
	        return icon;  
	    }  
	  
	    //将图标转换为图片
	    public static Image toImage(Icon icon) {  
	        if (icon instanceof ImageIcon) {  
	            return ((ImageIcon) icon).getImage();  
	        } else {  
	            int w = icon.getIconWidth();  
	            int h = icon.getIconHeight();  
	            GraphicsEnvironment ge = GraphicsEnvironment  
	                    .getLocalGraphicsEnvironment();  
	            GraphicsDevice gd = ge.getDefaultScreenDevice();  
	            GraphicsConfiguration gc = gd.getDefaultConfiguration();  
	            BufferedImage image = gc.createCompatibleImage(w, h);  
	            Graphics2D g = image.createGraphics();  
	            icon.paintIcon(null, g, 0, 0);  
	            g.dispose();  
	            return image;  
	        }  
	    }  
	  
	    private static boolean hasAlpha(Image image) {  
	        if (image instanceof BufferedImage) {  
	            BufferedImage bimage = (BufferedImage) image;  
	            return bimage.getColorModel().hasAlpha();  
	        }  
	  
	       
	        PixelGrabber pg = new PixelGrabber(image, 0, 0, 1, 1, true);  
	        try {  
	            pg.grabPixels();  
	        } catch (InterruptedException e) {  
	        }  
	  
	        
	        ColorModel cm = pg.getColorModel();  
	        return cm.hasAlpha();  
	    }  
	  
	    
	    public static BufferedImage toBufferedImage(Image image) {  
	        if (image instanceof BufferedImage) {  
	            return (BufferedImage) image;  
	        }  
	   
	        image = new ImageIcon(image).getImage();  
	  
	        // Determine if the image has transparent pixels; for this method's  
	        // implementation, see Determining If an Image Has Transparent Pixels  
	        boolean hasAlpha = hasAlpha(image);  
	  
	        // Create a buffered image with a format that's compatible with the  
	        // screen  
	        BufferedImage bimage = null;  
	        GraphicsEnvironment ge = GraphicsEnvironment  
	                .getLocalGraphicsEnvironment();  
	        try {  
	            // Determine the type of transparency of the new buffered image  
	            int transparency = Transparency.OPAQUE;  
	            if (hasAlpha) {  
	                transparency = Transparency.BITMASK;  
	            }  
	  
	            // Create the buffered image  
	            GraphicsDevice gs = ge.getDefaultScreenDevice();  
	            GraphicsConfiguration gc = gs.getDefaultConfiguration();  
	            bimage = gc.createCompatibleImage(image.getWidth(null), image  
	                    .getHeight(null), transparency);  
	        } catch (HeadlessException e) { 
	            // The system does not have a screen  
	        }  
	  
	        if (bimage == null) {  
	            // Create a buffered image using the default color model  
	            int type = BufferedImage.TYPE_INT_RGB;  
	            if (hasAlpha) {  
	                type = BufferedImage.TYPE_INT_ARGB;  
	            }  
	            bimage = new BufferedImage(image.getWidth(null), image  
	                    .getHeight(null), type);  
	        }  
	  
	        // Copy image to buffered image  
	        Graphics g = bimage.createGraphics();  
	  
	        // Paint the image onto the buffered image  
	        g.drawImage(image, 0, 0, null);  
	        g.dispose();  
	  
	        return bimage;  
	    }  
}
