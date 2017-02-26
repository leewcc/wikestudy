package com.wikestudy.model.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FFmpegUtil {
	// 运行命令
	public void runCmd(String command) {
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(command);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}

	// 视频转码，infile输入文件(包括完整路径)，outfile输出文件
	public boolean transfer(String infile, String outfile) {
		String avitoflv = "ffmpeg -i " + infile
				+ " -ar 22050 -ab 56 -f flv -y -s 320x240 " + outfile;
		String flvto3gp = "ffmpeg -i "
				+ infile
				+ " -ar 8000 -ac 1 -acodec amr_nb -vcodec h263 -s 176x144 -r 12 -b 30 -ab 12 "
				+ outfile;
		String avito3gp = "ffmpeg -i "
				+ infile
				+ " -ar 8000 -ac 1 -acodec amr_nb -vcodec h263 -s 176x144 -r 12 -b 30 -ab 12 "
				+ outfile;
		// avi -> jpg
		String avitojpg = "ffmpeg -i " + infile
				+ " -y -f image2 -ss 00:00:10 -t 00:00:01 -s 350x240 "
				+ outfile;
		try {
			Runtime rt = Runtime.getRuntime();
			Process proc = rt.exec(avitoflv);
			InputStream stderr = proc.getErrorStream();
			InputStreamReader isr = new InputStreamReader(stderr);
			BufferedReader br = new BufferedReader(isr);
			String line = null;
			System.out.println("<ERROR>");
			while ((line = br.readLine()) != null)
				System.out.println(line);
			System.out.println("</ERROR>");
			int exitVal = proc.waitFor();
			System.out.println("Process exitValue: " + exitVal);
		} catch (Throwable t) {
			t.printStackTrace();
			return false;
		}
		return true;
	}

	// 读取文件,fileName输入文件，id行数
	public static String readFile(String fileName, int id) {
		String dataStr = "";
		FileInputStream fis = null;
		try {
			FileReader file = new FileReader(fileName);// 建立FileReader对象，并实例化为fr
			BufferedReader br = new BufferedReader(file);// 建立BufferedReader对象，并实例化为br
			int i = 1;
			String Line = br.readLine();// 从文件读取一行字符串
			// System.out.println("Line1="+Line+"="+Line);
			// 判断读取到的字符串是否不为空
			// while(Line!=null){
			// System.out.println(Line + "<br>");//输出从文件中读取的数据
			// if(i==line){dataStr=Line;break;}else{i=i+1;}
			// Line=br.readLine();//从文件中继续读取一行数据
			// }
			dataStr = Line;
			br.close();// 关闭BufferedReader对象
		} catch (Exception e) {
		} finally {
			try {
				if (fis != null)
					fis.close();
			} catch (Exception e) {
			}
		}
		return dataStr;
	}

	// 读时长信息
	public String readtime(String file) {
		String str = "/opt/cgogo/test/info.txt";
		String timelen = "";
		String cmd = "timelen " + file;
		runCmd(cmd);
		timelen = readFile(str, 1);
		return timelen;
	}

	public static void main(String args[]) {
		FFmpegUtil me = new FFmpegUtil();
		String infile = "/opt/cgogo/test/02.avi";
		String outfile = "01.flv";

		// 时长
		String timelen = me.readtime(infile);
		System.out.println("02.avi timelen is :" + timelen);
		// 转码
		if (me.transfer(infile, outfile)) {
			System.out.println("the transfer is ok!");
		} else {
			System.out.println("the transfer is error!");
		}
	}
}
