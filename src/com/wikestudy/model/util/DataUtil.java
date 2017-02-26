package com.wikestudy.model.util;

import java.text.DecimalFormat;

public class DataUtil {
	 private static DecimalFormat formater = new DecimalFormat("####.00");
	 
	public static String getDataSize(long size){  
        if(size<1024){  
            return size+"bytes";  
        }else if(size<1024*1024){  
            float kbsize = size/1024f;    
            return formater.format(kbsize)+"KB";  
        }else if(size<1024*1024*1024){  
            float mbsize = size/1024f/1024f;    
            return formater.format(mbsize)+"MB";  
        }else if(size<1024L*1024L*1024L*1024L){  
            float gbsize = size/1024f/1024f/1024f;    
            return formater.format(gbsize)+"GB";  
        }else{  
            return "size: error";  
        }  
	}
}
