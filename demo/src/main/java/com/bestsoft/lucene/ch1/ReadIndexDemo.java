package com.bestsoft.lucene.ch1;

import java.io.IOException;
import java.nio.file.Paths;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

public class ReadIndexDemo {

	/**
	 * 查看所有索引文件
	 * 
	 * @author IT学习者-螃蟹
	 * @官网：http://www.itxxz.com
	 * @date 2014-11-17
	 * @param writer
	 * @param file
	 * @throws IOException
	 */
	public static void readAllIndexDocs() throws IOException {
		try {
			Directory dir = FSDirectory.open(Paths.get("E:/itxxz/lucene"));
			IndexReader reader = DirectoryReader.open(dir);
			IndexSearcher searcher = new IndexSearcher(reader);
			Document doc = null;
			for (int i = 0; i < reader.maxDoc(); i++) {
				doc = searcher.doc(i);
				System.out.println("Doc [" + i + "] : filename: " + doc.get("filename") + ", Path: " + doc.get("path"));
			}
		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String args[]) {
		try {
			ReadIndexDemo.readAllIndexDocs();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
