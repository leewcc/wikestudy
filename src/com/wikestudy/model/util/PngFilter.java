package com.wikestudy.model.util;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

public class PngFilter implements FileFilter{

	@Override
	public boolean accept(File pathname) {
		if(pathname.isFile())
			return true;
		
		return false;
	}



}
