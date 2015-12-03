package com.bestsoft.thread;

public class ThreadMain {
	public static void main(String argv[]) {
//		// 产生两个同样的线程
//		ThreadDemo th1 = new ThreadDemo();
//		ThreadDemo th2 = new ThreadDemo();
//
//		// 我们的目的是先运行第一个线程，再运行第二个线程
//		th1.start();
//		th2.start();
		
		ThreadMain tm = new ThreadMain();
		tm.method2();
	}

	// 第一种方法：不断查询第一个线程是否已经终止，如果没有，则让主线程睡眠一直到它终止为止 即：while/isAlive/sleep
	public void method1() {
		ThreadDemo th1 = new ThreadDemo();
		ThreadDemo th2 = new ThreadDemo();
		// 执行第一个线程
		th1.start();
		// 不断查询第一个线程的状态
		while (th1.isAlive()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
			}
		}
		// 第一个线程终止，运行第二个线程
		th2.start();
	}

	// 第二种方法：join()
	public void method2() {
		ThreadDemo th1 = new ThreadDemo();
		ThreadDemo th2 = new ThreadDemo();
		// 执行第一个线程
		th1.start();
		try {
			th1.join();
		} catch (InterruptedException e) {
		}
		// 执行第二个线程
		th2.start();
	}
}