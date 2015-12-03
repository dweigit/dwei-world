package com.bestsoft.htmlunit;

import java.io.IOException;

import com.bestsoft.util.FileUtil;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

public class LoginEmailDemo {

	public static void main(String args[]) throws IOException {
		WebClient webclient = new WebClient(BrowserVersion.CHROME);
		webclient.getOptions().setCssEnabled(false);
		webclient.getOptions().setJavaScriptEnabled(false);
		HtmlPage htmlpage = webclient.getPage("http://mail.126.com/");

		HtmlForm form = htmlpage.getFormByName("login126");
		DomElement button = htmlpage.getElementById("loginBtn");
		// 得到用户名输入框
		HtmlTextInput textFieldName = (HtmlTextInput)htmlpage.getElementById("idInput");
		textFieldName.setValueAttribute("hero99808");
		// 得到用户名输入框
		HtmlPasswordInput textFieldPwd = (HtmlPasswordInput)htmlpage.getElementById("pwdInput");
		textFieldPwd.setValueAttribute("deng3638495");
		// 输入好了，我们点一下这个按钮
		HtmlPage nextPage = button.click();
		// 我把结果转成String
		String result = nextPage.asXml();

		FileUtil.saveFile("F:/test.html", result);
		System.out.println(result);
		webclient.close();
	}
}
