package com.bestsoft.poi;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.POIXMLException;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class SimpleExcelRead {
	/**
	 * Read the Excel 2010
	 * 
	 * @param path
	 *            the path of the excel file
	 * @return
	 * @throws IOException
	 */
	public void readXlsx(File file) throws POIXMLException, IOException {
		System.out.println(file.getAbsolutePath());
		InputStream is = new FileInputStream(file);
		XSSFWorkbook xssfWorkbook = new XSSFWorkbook(is);
		// Read the Sheet
		for (int numSheet = 0; numSheet < xssfWorkbook.getNumberOfSheets(); numSheet++) {
			XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(numSheet);
			if (xssfSheet == null) {
				continue;
			}
			System.out.println("第一个sheet的名称为：" + xssfSheet.getSheetName());
			System.out.println("第一个sheet共有：" + xssfSheet.getLastRowNum() + "行" );
			System.out.println("具体内容如下：");
			// Read the Row
			for (int rowNum = 1; rowNum <= xssfSheet.getLastRowNum(); rowNum++) {
				XSSFRow xssfRow = xssfSheet.getRow(rowNum);
				if (xssfRow != null && xssfRow.getLastCellNum() > 0) {
					for (int k = 1; k < xssfRow.getLastCellNum(); k++) {
						XSSFCell cell = xssfRow.getCell(k);
						System.out.print(cell+"\t");
					}
				}
				System.out.println("");
			}
		}
		xssfWorkbook.close();
	}

	/**
	 * Read the Excel 2003-2007
	 * 
	 * @param path
	 *            the path of the Excel
	 * @return
	 * @throws IOException
	 */
	public void readXls(File file) throws IOException {
		InputStream is = new FileInputStream(file);
		HSSFWorkbook hssfWorkbook = new HSSFWorkbook(is);
		// Read the Sheet
		for (int numSheet = 0; numSheet < hssfWorkbook.getNumberOfSheets(); numSheet++) {
			HSSFSheet hssfSheet = hssfWorkbook.getSheetAt(numSheet);
			if (hssfSheet == null) {
				continue;
			}
			System.out.println("第一个sheet的名称为：" + hssfSheet.getSheetName());
			System.out.println("第一个sheet共有：" + hssfSheet.getLastRowNum() + "行" );
			System.out.println("具体内容如下：");
			// Read the Row
			for (int rowNum = 1; rowNum <= hssfSheet.getLastRowNum(); rowNum++) {
				HSSFRow hssfRow = hssfSheet.getRow(rowNum);
				if (hssfRow != null) {
					for (int k = 1; k < hssfRow.getLastCellNum(); k++) {
						HSSFCell cell = hssfRow.getCell(k);
						System.out.println(cell+"\t");
					}
				}
			}
		}
		hssfWorkbook.close();
	}

	public static void main(String[] args) {
		SimpleExcelRead sr = new SimpleExcelRead();
		File file = new File("D://test.xlsx");
		try {
			sr.readXlsx(file);
		} catch (POIXMLException e) {
			try {
				sr.readXls(file);
			} catch (IOException e1) {
				System.out.println("解析失败");
			}
		} catch (IOException e) {
			System.out.println("解析失败");
		}
	}
}
