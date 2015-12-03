package com.bestsoft.jxl;

import java.io.File;
import java.io.IOException;

import jxl.Range;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

public class SimpleExcelRead {
	public void readExcel(File file) throws BiffException, IOException {
		Workbook wb = Workbook.getWorkbook(file);// 从文件流中取得Excel工作区对象
		Sheet sheet = wb.getSheet(0);

		System.out.println(file.getName());
		System.out.println("第一个sheet的名称为：" + sheet.getName());
		System.out.println("第一个sheet共有：" + sheet.getRows() + "行" + sheet.getColumns() + "列");
		System.out.println("具体内容如下：");
		Range[] rangeCell = sheet.getMergedCells();

		for (int i = 0; i < sheet.getRows(); i++) {
			for (int j = 0; j < sheet.getColumns(); j++) {
				String str = null;
				str = sheet.getCell(j, i).getContents();
				for (Range r : rangeCell) {
					if (i > r.getTopLeft().getRow() && i <= r.getBottomRight().getRow() && j >= r.getTopLeft().getColumn()
							&& j <= r.getBottomRight().getColumn()) {
						str = sheet.getCell(r.getTopLeft().getColumn(), r.getTopLeft().getRow()).getContents();
					}
				}
				System.out.print(str + "\t");
			}
			System.out.println();
		}
		wb.close();
	}

	public static void main(String[] args) throws BiffException, IOException {
		SimpleExcelRead sr = new SimpleExcelRead();
		File file = new File("D://test.xlsx");
		sr.readExcel(file);
	}

}
