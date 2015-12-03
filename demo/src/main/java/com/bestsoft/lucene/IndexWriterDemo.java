package com.bestsoft.lucene;

import java.io.IOException;
import java.util.List;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field.Store;
import org.apache.lucene.document.StringField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TermQuery;
import org.apache.lucene.search.TopDocs;

public class IndexWriterDemo {
	/**
	 * 将即将检索的资源写入索引库
	 * 
	 * @param writer
	 * @throws Exception
	 */
	public void buildDocs(IndexWriter writer) throws Exception {
		writer.deleteAll();// 清空索引库里已存在的文档（document）
		List<User> list = DataUtil.getUsers();// 得到数据资源
		System.out.println("buildDocs()->总人数为 :" + list.size());
		for (User user : list) {
			Document doc = new Document();// 创建索引库的文档
			doc.add(new StringField("id", String.valueOf(user.getId()), Store.YES));
			doc.add(new StringField("name", user.getName(), Store.YES));
			doc.add(new StringField("age", String.valueOf(user.getAge()), Store.YES));
			doc.add(new StringField("sex", user.getSex(), Store.YES));
			doc.add(new StringField("birthday", String.valueOf(user.getBirthday()), Store.YES));
			writer.addDocument(doc);// 将文档写入索引库
		}
		int count = writer.numDocs();
		writer.forceMerge(100);// 合并索引库文件
		writer.close();
		System.out.println("buildDocs()->存入索引库的数量：" + count);
	}

	/**
	 * 从索引库中搜索你要查询的数据
	 * 
	 * @param searcher
	 * @throws IOException
	 */
	public void searcherDocs(IndexSearcher searcher) throws IOException {
		Term term = new Term("sex", "man");// 查询条件，意思是我要查找性别为“man”的人
		TermQuery query = new TermQuery(term);
		TopDocs docs = searcher.search(query, 100);// 查找
		System.out.println("searcherDoc()->男生人数：" + docs.totalHits);
		for (ScoreDoc doc : docs.scoreDocs) {// 获取查找的文档的属性数据
			int docID = doc.doc;
			Document document = searcher.doc(docID);
			String str = "ID:" + document.get("id") + ",姓名：" + document.get("name") + "，性别：" + document.get("sex");
			System.out.println("人员信息:" + str);
		}
	}
}
