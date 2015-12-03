package com.bestsoft.lucene;

import java.nio.file.Paths;

import org.apache.lucene.analysis.core.SimpleAnalyzer;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.SimpleFSDirectory;
import org.junit.Before;
import org.junit.Test;

public class TestIndexWriterFSDirectory {
	private IndexWriter writer=null;
	private Directory directory=null;
	private IndexReader reader = null;
	private IndexSearcher searcher=null;
	private IndexWriterDemo demo =new IndexWriterDemo();
	
	@Before
	public void setUp() throws Exception {
		directory = new SimpleFSDirectory(Paths.get("F:/luc_dir"));
		IndexWriterConfig config = new IndexWriterConfig(new SimpleAnalyzer());
		writer = new IndexWriter(directory,config);
	}

	@Test
	public void testLucene()throws Exception {
		/**生成索引库*/
		demo.buildDocs(writer);
		
		/**查询数据*/
		reader = DirectoryReader.open(directory);
		searcher =new IndexSearcher(reader);
		demo.searcherDocs(searcher);
	}
}
