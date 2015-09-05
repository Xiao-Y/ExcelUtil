package com.xiaoy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import jxl.Cell;
import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;

/**
 * Excel数据导入数据库
 * 
 * @author XiaoY
 * @date: 2015年9月5日 下午10:11:16
 */
public class ExcelFileImportToDB {

	/**
	 * 解析Excel中的数据
	 * 
	 * @param formFile
	 * @return list ArrayList
	 * @throws Exception
	 */
	public static ArrayList<String[]> parseExcelData(File formFile) throws Exception {
		InputStream in = null;
		Workbook wb = null;
		ArrayList<String[]> list = new ArrayList<String[]>();
		try {
			if (formFile == null) {
				throw new Exception("文件为空！");
			}

			in = new FileInputStream(formFile);

			wb = Workbook.getWorkbook(in);

			Sheet sheet[] = wb.getSheets();
			if (sheet != null) {
				for (int i = 0; i < sheet.length; i++) {
					int intCount = 0;
					for (int j = 1; j < sheet[i].getRows(); j++) {
						String[] valStr = new String[11];
						if (i == 0 && j == 0) {
							continue;
						}

						for (int k = 0; k < sheet[i].getColumns(); k++) {
							Cell cell = sheet[i].getCell(k, j);
							String content = "";
							if (cell.getType() == CellType.DATE) {
								DateCell dateCell = (DateCell) cell;
								java.util.Date importdate = dateCell.getDate();
								/** 如果excel是日期格式的话需要减去8小时 */
								long eighthour = 8 * 60 * 60 * 1000;
								SimpleDateFormat simpledate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
								/** 在当前日期上减8小时 */
								long time = importdate.getTime() - eighthour;
								java.util.Date date = new java.util.Date();
								date.setTime(time);
								content = simpledate.format(date);
							} else {
								String tempcontent = (cell.getContents() == null ? "" : cell.getContents());
								content = tempcontent.trim().replace('\'', ' ');
							}
							valStr[k] = content;

						}
						list.add(j - 1 - intCount, valStr);
					}
				}
			}
			System.out.println("成功获取Excel中的数据...");
			return list;
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (wb != null) {
				wb.close();
			}
			if (in != null) {
				try {
					in.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
	}
}
