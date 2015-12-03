/*
 * banger Inc.
 * Copyright (c) 2009-2012 All Rights Reserved.
 * ToDo       :This is Class Description...
 * Author     :liyb
 * Create Date:2012-8-2
 */
package com.bestsoft.pd4ml;

import java.awt.Insets;
import java.io.File;
import java.io.FileOutputStream;
import java.io.StringReader;

import org.zefer.pd4ml.PD4Constants;
import org.zefer.pd4ml.PD4ML;

public class ConverterPdf {
    public static void main(String[] args) throws Exception {
        ConverterPdf converter = new ConverterPdf();
        converter.generatePDF_2(new File("F:/demo_ch_pd4ml_a.pdf"), "F:/pdf.html");
        File pdfFile = new File("F:/demo_ch_pd4mlssss.pdf");
        StringBuffer html = new StringBuffer();
        html.append("<html>")
            .append("<head>")
            .append("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\" />")
            .append("</head>")
            .append("<body>")
            .append("<font face=\"KaiTi_GB2312\">")
            .append("<font color='red' size=22>显示中文</font>")
            .append("</font>")
            .append("</body></html>");
        StringReader strReader = new StringReader(html.toString());
        converter.generatePDF_1(pdfFile, strReader);
    }
    // 手动构造HTML代码
    public void generatePDF_1(File outputPDFFile, StringReader strReader) throws Exception {
        FileOutputStream fos = new FileOutputStream(outputPDFFile);
        PD4ML pd4ml = new PD4ML();
        pd4ml.setPageInsets(new Insets(20, 10, 10, 10));
        pd4ml.setHtmlWidth(950);
        pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
        pd4ml.useTTF("java:fonts", true);
        pd4ml.setDefaultTTFs("SimHei", "Arial", "Courier New");
        pd4ml.enableDebugInfo();
        pd4ml.render(strReader, fos);
    }

    // HTML代码来自于HTML文件
    public void generatePDF_2(File outputPDFFile, String inputHTMLFileName) throws Exception {
        FileOutputStream fos = new FileOutputStream(outputPDFFile);
        PD4ML pd4ml = new PD4ML();
        pd4ml.setPageInsets(new Insets(5, 20, 20, 20));
        pd4ml.setHtmlWidth(1000);
        pd4ml.setPageSize(pd4ml.changePageOrientation(PD4Constants.A4));
        pd4ml.useTTF("java:fonts", true);
        pd4ml.setDefaultTTFs("SimHei", "Arial", "Courier New");
        pd4ml.enableDebugInfo();
        pd4ml.render("file:" + inputHTMLFileName, fos);
    }
}
