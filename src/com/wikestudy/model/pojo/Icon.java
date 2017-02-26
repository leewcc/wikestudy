package com.wikestudy.model.pojo;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.mysql.jdbc.interceptors.ServerStatusDiffInterceptor;

public class Icon {
	private String name;
	private BufferedImage image;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BufferedImage getImage() {
		return image;
	}
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	@Override
	public String toString() {
		return name;
	}
	
	@Override
	public boolean equals(Object obj) {
		return obj.toString().contains(name);
	}
	
}
