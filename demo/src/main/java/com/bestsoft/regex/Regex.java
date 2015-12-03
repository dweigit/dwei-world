package com.bestsoft.regex;

public class Regex {
	public static void main(String args[]) {
		String newPasswd = "Fde3";
		String regCn = "[^\\x00-\\xff]";// 否存在中文和全角字符
		String regPassWd = "(?![0-9a-z]+$)(?![0-9A-Z]+$)(?![0-9\\W]+$)(?![a-z\\W]+$)(?![a-zA-Z]+$)(?![A-Z\\W]+$)[a-zA-Z0-9\\W_]+";
		// 解密
		if (newPasswd.matches(regPassWd)) {
			System.out.println("1234123".length());
		}
	}
}
