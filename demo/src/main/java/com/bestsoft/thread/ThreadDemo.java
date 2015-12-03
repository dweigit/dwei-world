package com.bestsoft.thread;

/**
 * =====================================================================
 * 文件：ThreadDemo_02.java 描述：等待一个线程的结束
 * ======================================================================
 */
public class ThreadDemo extends Thread {
	ThreadDemo() {
	}

	ThreadDemo(String szName) {
		super(szName);
	}

	// 重载run函数
	public void run() {
		for (int count = 1, row = 1; row < 20; row++, count++) {
			for (int i = 0; i < count; i++) {
				System.out.print('*');
			}
			System.out.println();
		}
	}
}
