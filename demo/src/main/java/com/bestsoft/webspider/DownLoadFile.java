package com.bestsoft.webspider;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

public class DownLoadFile {

	/* 下载 url 指向的网页 */
	public String downloadFile(String url) {
		String filePath = null;
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpGet httpget = new HttpGet(url);
		System.out.println("---------:【"+LinkQueue.getUnVisitedUrlNum()+"】"+url);
		// 设置请求和传输超时时间
		CloseableHttpResponse response = null;
		try {
			response = httpclient.execute(httpget);
			if (response.getStatusLine().getStatusCode() == 200) {
				// 根据网页 url 生成保存时的文件名
				filePath = "F:\\spider\\" + getFileNameByUrl(url, response.getLastHeader("Content-Type").getValue());
				FileOutputStream fos = new FileOutputStream(filePath);
				response.getEntity().writeTo(fos);
				fos.flush();
				fos.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				response.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return filePath;
	}

	/**
	 * 根据 url 和网页类型生成需要保存的网页的文件名 去除掉 url 中非文件名字符
	 */
	public String getFileNameByUrl(String url, String contentType) {
		// remove http://
		url = url.substring(7);
		// text/html类型
		if (contentType.indexOf("html") != -1) {
			url = url.replaceAll("[\\?/:*|<>\"]", "_") + ".html";
			return url;
		}
		// 如application/pdf类型
		else {
			return url.replaceAll("[\\?/:*|<>\"]", "_") + "." + contentType.substring(contentType.lastIndexOf("/") + 1);
		}
	}

	public static void main(String args[]){
		new DownLoadFile().downloadFile("http://ant.apache.org/antlibs/bindownlo ad.cgi#");
	}
}