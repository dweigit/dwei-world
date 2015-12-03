package com.bestsoft.jvm;

public class TestClassLoaderDemo {
	 public static void main(String[] args) throws InstantiationException, IllegalAccessException {  
	        Class<?> thisCls = TestClassLoaderDemo.class;  
	        MyClassLoader myClassLoader = new MyClassLoader();
	        System.out.println(thisCls.getClassLoader());
	        System.out.println(myClassLoader.getParent());  
	        try {  
	            //用自定义的类装载器来装载类,这是动态扩展的一种途径  
	            Class<?> cls2 = myClassLoader.loadClass("com.bestsoft.jvm.TestBeLoader1");  
	            System.out.println(cls2.getClassLoader());  
	            TestBeLoader test=(TestBeLoader)cls2.newInstance(); 
	        } catch (ClassNotFoundException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
