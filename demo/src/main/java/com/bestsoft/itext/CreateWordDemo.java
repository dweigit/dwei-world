package com.bestsoft.itext;

import java.awt.Color;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Cell;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.Image;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Table;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.rtf.RtfWriter2;

public class CreateWordDemo {

	public void createDocContext(String file) throws DocumentException, IOException {
		// 设置纸张大小
		Document document = new Document(PageSize.A4);
		// 建立一个书写器(Writer)与document对象关联，通过书写器(Writer)可以将文档写入到磁盘中
		RtfWriter2.getInstance(document, new FileOutputStream(file));
		document.open();

		// 设置中文字体
		BaseFont bfChinese = BaseFont.createFont("/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// 标题字体风格
		Font titleFont = new Font(bfChinese, 12, Font.BOLD);
		// 正文字体风格
		Font contextFont = new Font(bfChinese, 10, Font.NORMAL);

		Paragraph title = new Paragraph("标题");
		// 设置标题格式对齐方式
		title.setAlignment(Element.ALIGN_CENTER);
		title.setFont(titleFont);
		title.setSpacingBefore(15);
		document.add(title);

		String contextString = "iText是一个能够快速产生PDF文件的java类库。"
				+ " \n"// 换行
				+ "iText的java类对于那些要产生包含文本，" + "表格，图形的只读文档是很有用的。它的类库尤其与java Servlet有很好的给合。"
				+ "使用iText与PDF能够使你正确的控制Servlet的输出。";
		Paragraph context = new Paragraph(contextString);
		context.setAlignment(Element.ALIGN_LEFT);
		context.setFont(contextFont);
		// 段间距 离上一段落（标题）空的行数
		context.setSpacingBefore(15);
		// 设置第一行空的列数
		context.setFirstLineIndent(20);
		document.add(context);

		// 利用类FontFactory结合Font和Color可以设置各种各样字体样式
		/**
		 * Font.UNDERLINE 下划线，Font.BOLD 粗体
		 */
		Font font1 = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, Font.UNDERLINE, new Color(0, 0, 255));
		Paragraph underline = new Paragraph("下划线的实现", font1);
		underline.setSpacingBefore(10);
		document.add(underline);

		// 设置Table表格,创建一个三列的表格
		Table table = new Table(3);
		int width[] = { 25, 25, 50 };// 设置每列宽度比例
		table.setWidths(width);
		table.setWidth(90);// 占页面宽度比例
		table.setAlignment(Element.ALIGN_CENTER);// 居中
		table.setAlignment(Element.ALIGN_MIDDLE);// 垂直居中
		table.setAutoFillEmptyCells(true);// 自动填满
		table.setBorderWidth(1);// 边框宽度
		// 设置表头
		Cell haderCell = new Cell("表格表头");
		haderCell.setHeader(true);
		haderCell.setColspan(3);
		table.addCell(haderCell);
		table.endHeaders();

		Font fontChinese = new Font(bfChinese, 12, Font.NORMAL, Color.GREEN);
		Cell cell = new Cell(new Paragraph("这是一个3*3测试表格数据", fontChinese));
		cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
		table.addCell(cell);
		table.addCell(new Cell("#1"));
		table.addCell(new Cell("#2"));
		table.addCell(new Cell("#3"));
		document.add(table);

		// 设置 Table 表格
		Table aTable = new Table(3);
		int widtha[] = { 25, 25, 50 };
		aTable.setWidths(widtha);// 设置每列所占比例
		aTable.setWidth(90); // 占页面宽度 90%

		aTable.setAlignment(Element.ALIGN_CENTER);// 居中显示
		aTable.setAlignment(Element.ALIGN_MIDDLE);// 纵向居中显示
		aTable.setAutoFillEmptyCells(true); // 自动填满
		aTable.setBorderWidth(1); // 边框宽度
		aTable.setBorderColor(new Color(0, 125, 255)); // 边框颜色
		aTable.setPadding(2);// 衬距，看效果就知道什么意思了
		aTable.setSpacing(3);// 即单元格之间的间距
		aTable.setBorder(2);// 边框

		// 设置表头
		/**
		 * cell.setHeader(true);是将该单元格作为表头信息显示； cell.setColspan(3);指定了该单元格占3列；
		 * 为表格添加表头信息时，要注意的是一旦表头信息添加完了之后， 必须调用 endHeaders()方法，否则当表格跨页后，表头信息不会再显示
		 */
		Cell haderCella = new Cell("表格表头");
		haderCella.setHeader(true);
		haderCella.setColspan(3);
		aTable.addCell(haderCella);
		aTable.endHeaders();

		Font fontChinesea = new Font(bfChinese, 12, Font.NORMAL, Color.GREEN);
		Cell cella = new Cell(new Phrase("这是一个测试的 3*3 Table 数据", fontChinesea));
		cella.setVerticalAlignment(Element.ALIGN_TOP);
		cella.setBorderColor(new Color(255, 0, 0));
		cella.setRowspan(2);
		aTable.addCell(cella);

		aTable.addCell(new Cell("#1"));
		aTable.addCell(new Cell("#2"));
		aTable.addCell(new Cell("#3"));
		aTable.addCell(new Cell("#4"));
		Cell cella1 = new Cell(new Phrase("一行三列数据", fontChinese));
		cella1.setColspan(3);
		cella1.setVerticalAlignment(Element.ALIGN_CENTER);
		aTable.addCell(cella1);
		document.add(aTable);
		
		createXlsParagraph(document);

		// //在表格末尾添加图片
		Image img = Image.getInstance("F:/1.png");
		img.setSpacingBefore(10);
		// img.setAbsolutePosition(10, 20);
		img.setAlignment(Image.LEFT);// 设置图片显示位置
		// img.scaleAbsolute(12, 35);// 直接设定显示尺寸
		img.scalePercent(50);// 表示显示的大小为原尺寸的50%
		// img.scalePercent(25, 12);// 图像高宽的显示比例
		img.setRotation(30);// 图像旋转一定角度
		document.add(img);
		document.close();
	}

	public void createXlsParagraph(Document document) {
		// 设置合同头
		Paragraph p = new Paragraph("出口合同", new Font(Font.NORMAL, 18, Font.BOLDITALIC, new Color(0, 0, 0)));
		p.setSpacingBefore(20);
		p.setAlignment(1);
		try {
			document.add(p);
			/*
			 * 创建有三列的表格
			 */
			Table table = new Table(3);
			document.add(new Paragraph("生成表格"));
			table.setBorderWidth(1);
			table.setBorderColor(Color.BLACK);
			table.setPadding(0);
			table.setSpacing(0);

			/*
			 * 添加表头的元素
			 */
			Cell cell = new Cell("表头");// 单元格
			cell.setHeader(true);
			cell.setColspan(3);// 设置表格为三列
			cell.setRowspan(3);// 设置表格为三行
			table.addCell(cell);
			table.endHeaders();// 表头结束

			// 表格的主体
			cell = new Cell("Example cell 2");
			cell.setRowspan(2);// 当前单元格占两行,纵向跨度
			table.addCell(cell);
			table.addCell("1,1");
			table.addCell("1,2");
			table.addCell("1,3");
			table.addCell("1,4");
			table.addCell("1,5");
			table.addCell(new Paragraph("用java生成的表格1"));
			table.addCell(new Paragraph("用java生成的表格2"));
			table.addCell(new Paragraph("用java生成的表格3"));
			table.addCell(new Paragraph("用java生成的表格4"));
			document.add(new Paragraph("用java生成word文件"));
			document.add(table);
		} catch (DocumentException e) {
			e.printStackTrace();
		}
	}

	// 图片版
	public void createRTFContext(String path) throws DocumentException, IOException {
		Document document = new Document(PageSize.A4);
		RtfWriter2.getInstance(document, new FileOutputStream(path));
		document.open();
		// 设置中文字体
		BaseFont bfChinese = BaseFont.createFont("/SIMYOU.TTF", BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
		// 标题字体风格
		Font titleFont = new Font(bfChinese, 12, Font.BOLD);
		// 正文字体风格
		Font contextFont = new Font(bfChinese, 10, Font.NORMAL);
		Paragraph title = new Paragraph("标题");
		// 设置标题格式对齐方式
		title.setAlignment(Element.ALIGN_CENTER);
		title.setFont(titleFont);
		document.add(title);
		String contextString = "iText是一个能够快速产生PDF文件的java类库。iText的java类对于那些要产生包含文本，表格，图形的只读文档是很有用的。它的类库尤其与java Servlet有很好的给合。使用iText与PDF能够使你正确的控制Servlet的输出。";
		Paragraph context = new Paragraph(contextString);
		// 正文格式左对齐
		context.setAlignment(Element.ALIGN_LEFT);
		context.setFont(contextFont);
		// 离上一段落（标题）空的行数
		context.setSpacingBefore(20);
		// 设置第一行空的列数
		context.setFirstLineIndent(20);
		document.add(context);
		// //在表格末尾添加图片
		Image img = Image.getInstance("F:/1.png");
		img.setAbsolutePosition(0, 0);
		img.setAlignment(Image.LEFT);// 设置图片显示位置
		// img.scaleAbsolute(12, 35);// 直接设定显示尺寸
		img.scalePercent(50);// 表示显示的大小为原尺寸的50%
		// img.scalePercent(25, 12);// 图像高宽的显示比例
		img.setRotation(30);// 图像旋转一定角度
		document.add(img);
		document.close();
	}

	public static void main(String[] args) {
		CreateWordDemo word = new CreateWordDemo();
		String file = "F:/test.doc";
		try {
			word.createDocContext(file);
		} catch (DocumentException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}