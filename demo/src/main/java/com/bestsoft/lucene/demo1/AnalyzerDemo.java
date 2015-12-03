package com.bestsoft.lucene.demo1;

import java.io.IOException;
import java.io.StringReader;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.analysis.core.WhitespaceAnalyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.chenlb.mmseg4j.analysis.ComplexAnalyzer;

public class AnalyzerDemo {

	/** WhitespaceAnalyzer分析器 */
	public void whitespaceAnalyzer(String msg) {
		WhitespaceAnalyzer analyzer = new WhitespaceAnalyzer();
		this.getTokens(analyzer, msg);
	}

	/** SimpleAnalyzer分析器 */
	public void simpleAnalyzer(String msg) {
		SimpleAnalyzer analyzer = new SimpleAnalyzer();
		this.getTokens(analyzer, msg);
	}

	/** StopAnalyzer分析器 */
	public void stopAnalyzer(String msg) {
		StopAnalyzer analyzer = new StopAnalyzer();
		this.getTokens(analyzer, msg);
	}

	/** StandardAnalyzer分析器 */
	public void standardAnalyzer(String msg) {
		StandardAnalyzer analyzer = new StandardAnalyzer();
		this.getTokens(analyzer, msg);
	}
	
	/** SmartChineseAnalyzer分析器 */
	public void smartChineseAnalyzer(String msg) {
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();
		this.getTokens(analyzer, msg);
	}
	
	/** IKAnalyzer分析器 */
	public void iKAnalyzer(String msg) {
		Analyzer analyzer = new IKAnalyzer();
		this.getTokens(analyzer, msg);
	}
	
	/** ComplexAnalyzer分析器 */
	public void complexAnalyzer(String msg) {
		Analyzer analyzer = new ComplexAnalyzer();
		this.getTokens(analyzer, msg);
	}

	private void getTokens(Analyzer analyzer, String msg) {
		TokenStream tokenStream;
		try {
			tokenStream = analyzer.tokenStream("content", new StringReader(msg));
			 tokenStream.reset();
			this.printTokens(analyzer.getClass().getSimpleName(), tokenStream);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printTokens(String analyzerType, TokenStream tokenStream) {
		CharTermAttribute ta = tokenStream.addAttribute(CharTermAttribute.class);
		StringBuffer result = new StringBuffer();
		try {
			while (tokenStream.incrementToken()) {
				if (result.length() > 0) {
					result.append(",");
				}
				result.append("[" + ta.toString() + "]");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(analyzerType + "->" + result.toString());
	}
}
