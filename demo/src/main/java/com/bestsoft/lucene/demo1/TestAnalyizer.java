package com.bestsoft.lucene.demo1;

import org.junit.Before;
import org.junit.Test;

public class TestAnalyizer {
	private AnalyzerDemo demo = null;

	 //private String msg = "我喜欢你，我的祖国！china 中国";
	 //private String msg = "I love you, China!B2C";
	 //private String msg = "伟大的中华人民共和国。列宁、马克思。";
	 private String msg = "使用内存映射的I/O接口进行读操作，这样不需要采取锁机制，并能很好的支持多线程读操作。但由于内存映射的I/O所消耗的地址空间是与索引尺寸相等，所以建议最好只是用64位JRE。";

	@Before
	public void setUp() throws Exception {
		demo = new AnalyzerDemo();
	}

	@Test
	public void testWhitespaceAnalyzer() {
		demo.whitespaceAnalyzer(msg);
	}

	@Test
	public void testSimpleAnalyzer() {
		demo.simpleAnalyzer(msg);
	}

	@Test
	public void testStopAnalyzer() {
		demo.stopAnalyzer(msg);
	}

	@Test
	public void testStandardAnalyzer() {
		demo.standardAnalyzer(msg);
	}
	
	@Test
	public void testIKAnalyzer() {
		demo.iKAnalyzer(msg);
	}
	
	//@Test
	public void testComplexAnalyzer() {
		demo.complexAnalyzer(msg);
	}
}