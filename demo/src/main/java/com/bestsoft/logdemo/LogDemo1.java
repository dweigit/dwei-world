package com.bestsoft.logdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogDemo1 {
	private final Logger logger = LoggerFactory.getLogger(LogDemo1.class);

	public void test1() {
		try {
			LogBackConfigLoader.load("logback.xml");
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		logger.trace("======trace");
		logger.debug("======debug");
		logger.info("======info");
		logger.warn("======warn");
		logger.error("======error");
	}

	public static void main(String args[]) {
		new LogDemo1().test1();
	}

}
