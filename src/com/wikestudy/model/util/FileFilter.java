package com.wikestudy.model.util;

import java.io.File;
import java.io.FilenameFilter;


public class FileFilter implements FilenameFilter{
	private String suffix;
	
	public FileFilter(String suffix) {
		this.suffix = suffix;
	}
	
	@Override
	public boolean accept(File dir, String name) {
		return name.contains(suffix.toLowerCase());
	}

}

