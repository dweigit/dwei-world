package com.bestsoft.lucene;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DataUtil {
	/**
	 * 检索资源数据的准备； 这里的数据可以来源数据库、文件系统等
	 * 
	 * @return
	 */
	public static List<User> getUsers() {
		List<User> list = new ArrayList<User>();
		User user = new User(1L, "张三1", 20, "man", new Date());
		list.add(user);
		user = new User(2L, "张三2", 20, "man", new Date());
		list.add(user);
		user = new User(3L, "张三3", 20, "woman", new Date());
		list.add(user);
		user = new User(4L, "张三4", 20, "man", new Date());
		list.add(user);
		user = new User(5L, "张三5", 20, "man", new Date());
		list.add(user);
		user = new User(6L, "张三6", 20, "woman", new Date());
		list.add(user);
		return list;
	}
}