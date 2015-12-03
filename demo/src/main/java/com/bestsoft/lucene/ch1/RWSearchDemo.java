package com.bestsoft.lucene.ch1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Paths;

import org.apache.lucene.analysis.cn.smart.SmartChineseAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.IndexWriterConfig.OpenMode;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.LockObtainFailedException;

public class RWSearchDemo {
	/**
	 * Lucene第五章：中文搜索
	 * 
	 * @author IT学习者-螃蟹
	 * @官网：http://www.itxxz.com
	 * @date 2014-11-10
	 * 
	 */
	public static void main(String[] args) {

		// 实例化IKAnalyzer分词器，使用smart分词模式
		//Analyzer analyzer = new IKAnalyzer();
		SmartChineseAnalyzer analyzer = new SmartChineseAnalyzer();

		Directory directory = null;
		IndexReader ireader = null;
		IndexWriter iwriter = null;
		IndexSearcher isearcher = null;
		final String filePath = "E:/itxxz/data/b.txt";
		final String INDEX = "E:/itxxz/lucene";

		try {
			// 建立内存索引对象
			directory = FSDirectory.open(Paths.get(INDEX));
			// 配置IndexWriterConfig
			IndexWriterConfig iwConfig = new IndexWriterConfig(analyzer);
			iwConfig.setOpenMode(OpenMode.CREATE);
			iwriter = new IndexWriter(directory, iwConfig);
			FileInputStream fis = new FileInputStream(new File(filePath));
			// 写入索引
			Document doc = new Document();
			doc.add(new StringField("id", "10000", Field.Store.YES));
			doc.add(new TextField("title", "IT学习者", Field.Store.YES));
			doc.add(new TextField("url", "http://www.itxxz.com", Field.Store.YES));
			doc.add(new TextField("contents", new BufferedReader(new InputStreamReader(fis))));
			iwriter.addDocument(doc);
			iwriter.close();

			// 搜索过程**********************************
			// 实例化搜索器
			IndexReader reader = DirectoryReader.open(FSDirectory.open(Paths.get(INDEX)));
			isearcher = new IndexSearcher(reader);
			String keyword = "工具";
			// 使用QueryParser查询分析器构造Query对象
			QueryParser qp = new QueryParser("contents", analyzer);
			Query query = qp.parse(keyword);
			System.out.println("Query = " + query);

			// 搜索相似度最高的5条记录
			TopDocs topDocs = isearcher.search(query, 5);
			System.out.println("匹配文件个数：" + topDocs.totalHits);
			// 输出结果
			ScoreDoc[] scoreDocs = topDocs.scoreDocs;
			for (int i = 0; i < topDocs.totalHits; i++) {
				Document targetDoc = isearcher.doc(scoreDocs[i].doc);
				System.out.print("内容：");
				System.out.print("[id:" + targetDoc.get("id"));
				System.out.print("],[title:" + targetDoc.get("title"));
				System.out.print("],[url:" + targetDoc.get("url") + "]");
			}

		} catch (CorruptIndexException e) {
			e.printStackTrace();
		} catch (LockObtainFailedException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		} finally {
			if (ireader != null) {
				try {
					ireader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			if (directory != null) {
				try {
					directory.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
