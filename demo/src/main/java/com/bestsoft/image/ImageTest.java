package com.bestsoft.image;

import java.io.File;


public class ImageTest {
public static void main(String args[]){
	try {
		ImageHelper.cutCenterImage(new File("E:/11.jpg"));
		//ImageHelper.zoomImage("E:/11.gif", "E:/22.gif", 120, 380);
	} catch (Exception e) {
		e.printStackTrace();
	}
}
}
