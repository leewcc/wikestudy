package com.wikestudy.model.util;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class SimpleThreadPool {
	public final static ExecutorService executor=Executors.newFixedThreadPool(5);//池的大小


}
