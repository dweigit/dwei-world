package com.bestsoft.htmlunit;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

public class TEst {

	public static void main(String args[]) throws IOException {
		// 得到浏览器对象，直接New一个就能得到，现在就好比说你得到了一个浏览器了
		WebClient webclient = new WebClient();

		// 这里是配置一下不加载css和javaScript,配置起来很简单，是不是
		webclient.getOptions().setCssEnabled(false);
		webclient.getOptions().setJavaScriptEnabled(false);

		// 做的第一件事，去拿到这个网页，只需要调用getPage这个方法即可
		HtmlPage htmlpage = webclient.getPage("http://www.xicidaili.com/nt/");
		System.out.println(htmlpage.asText());

//		// 根据名字得到一个表单，查看上面这个网页的源代码可以发现表单的名字叫"f"
//		final HtmlForm form = htmlpage.getFormByName("f");
//		// 同样道理，获取"百度一下"这个按钮
//		final HtmlSubmitInput button = form.getInputByValue("百度一下");
//		// 得到搜索框
//		final HtmlTextInput textField = form.getInputByName("q1");
//		// 最近周星驰比较火呀，我这里设置一下在搜索框内填入"周星驰"
//		textField.setValueAttribute("周星驰");
//		// 输入好了，我们点一下这个按钮
//		final HtmlPage nextPage = button.click();
//		// 我把结果转成String
//		String result = nextPage.asXml();
//
//		FileUtil.saveFile("F:/test.txt",result);
//		System.out.println(result);
		webclient.close();
	}
}
