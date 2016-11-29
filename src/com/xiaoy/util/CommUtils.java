package com.xiaoy.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.thoughtworks.xstream.XStream;

/**
 * 文件上次下载公共类
 */
public class CommUtils {

	private static Logger logger = LoggerFactory.getLogger(CommUtils.class);

	static final String formatPattern = "yyyy-MM-dd";

	static final String formatPatternM = "yyyy-MM-dd hh:mm:ss";

	static final String formatPattern_Short = "yyyyMMdd";

	static final DecimalFormat df = new DecimalFormat("0.00");

	/**
	 * @param xls
	 *            XlsDto实体类的一个对象
	 * @param tempM
	 *            模板
	 * @param fileName
	 *            下载文件名
	 * @param paramMap
	 *            需要特殊处理的参数
	 * @return
	 * @throws Exception
	 *             在导入Excel的过程中抛出异常
	 */
	public static SXSSFWorkbook xlsDto2ExcelNew(List<Object> xls, String[] tempM, String fileName,
			Map<Object, Map<Object, Object>> paramMap) throws Exception {
		// 文件类型
		String extend = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

		SXSSFWorkbook sXSSFWorkbook = null;

		if ("xlsx".equals(extend)) {

			// 创建Excel文档
			sXSSFWorkbook = new SXSSFWorkbook();
			// 字体
			Font font = (XSSFFont) sXSSFWorkbook.createFont();
			font.setFontName("仿宋_GB2312");
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			font.setFontHeightInPoints((short) 12);

			CellStyle style = (CellStyle) sXSSFWorkbook.createCellStyle();
			// 设置颜色
			style.setAlignment(CellStyle.ALIGN_CENTER);
			style.setFillForegroundColor(IndexedColors.AQUA.getIndex());// 前景颜色
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);// 填充方式，前色填充
			// 边框填充
			style.setBorderBottom(CellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(CellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(CellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(CellStyle.BORDER_THIN);// 右边框
			style.setFont(font);
			style.setWrapText(false);

			style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd"));

			Object xlsDto = null;
			// sheet 对应一个工作页
			Sheet sheet = sXSSFWorkbook.createSheet("1000");
			sXSSFWorkbook.setCompressTempFiles(true);// 生成的临时文件将进行gzip压缩
			Row firstrow = sheet.createRow(0); // 下标为0的行开始
			sheet.setColumnWidth(120, 80);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(1, true);

			CellStyle css = sXSSFWorkbook.createCellStyle();
			DataFormat dataFormat = sXSSFWorkbook.createDataFormat();
			css.setDataFormat(dataFormat.getFormat("@"));

			Cell[] firstcell = new Cell[tempM.length];
			for (int j = 0; j < tempM.length; j++) {
				firstcell[j] = firstrow.createCell(j);
				String[] info = tempM[j].split("_");
				firstcell[j].setCellValue(new XSSFRichTextString(info[0]));
				firstcell[j].setCellStyle(style);

			}
			logger.info("for循环开始：" + new Date());
			for (int i = 0, len = xls.size(); i < len; i++) {

				// 创建一行
				Row row = sheet.createRow(i + 1);
				// 得到要插入的每一条记录
				xlsDto = xls.get(i);

				for (int cellNum = 0; cellNum < tempM.length; cellNum++) {
					String[] info = tempM[cellNum].split("_");
					Field field = xlsDto.getClass().getDeclaredField(info[1]);
					field.setAccessible(true);
					String type = field.getType().toString();// 得到此属性的类型
					Object value = field.get(xlsDto);
					// 在一行内循环
					Cell xh = row.createCell(cellNum);
					// 判断是否有特殊要处理的参数
					if (paramMap.get(info[1]) != null) {
						Map<Object, Object> result = paramMap.get(info[1]);

						if (result.containsKey(value)) {
							value = result.get(value);
						}

					}

					if (value != null) {
						if (type.endsWith("String")) {
							xh.setCellValue(value.toString());
						} else if (type.endsWith("int") || type.endsWith("Integer")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(value.toString());
						} else if (type.endsWith("boolean") || type.endsWith("Boolean")) {
							xh.setCellValue(Boolean.parseBoolean(value.toString()));
						} else if (type.endsWith("Date")) {
							if (value.toString().indexOf(".") > -1) {
								xh.setCellValue(value.toString().substring(0, value.toString().indexOf(".")));
							} else {
								xh.setCellValue((value.toString()));
							}
						} else if (type.endsWith("Double") || type.endsWith("double")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(df.format(value));
						} else if (type.endsWith("float") || type.endsWith("Float")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(df.format(value));
						} else if (type.endsWith("BigDecimal")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(df.format(value));
						} else {
							xh.setCellValue(value.toString());
						}
					} else {
						xh.setCellValue("");
					}
				}
			}
			logger.info("for循环结束：" + new Date());
		}
		return sXSSFWorkbook;
	}

	/**
	 * @param xls
	 *            XlsDto实体类的一个对象
	 * @param tempM
	 *            模板
	 * @param fileName
	 *            下载文件名
	 * @param paramMap
	 *            需要特殊处理的参数
	 * @return
	 * @throws Exception
	 *             在导入Excel的过程中抛出异常
	 */
	@SuppressWarnings("rawtypes")
	public static Workbook xlsDto2Excel(List xls, String[] tempM, String fileName, Map paramMap) throws Exception {
		// 文件类型
		String extend = fileName.substring(fileName.lastIndexOf('.') + 1).toLowerCase();

		Workbook workbook = null;

		if ("xlsx".equals(extend)) {

			// 创建Excel文档
			workbook = new XSSFWorkbook();
			// 字体
			XSSFFont font = (XSSFFont) workbook.createFont();
			font.setFontName("仿宋_GB2312");
			font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);// 粗体显示
			font.setFontHeightInPoints((short) 12);

			XSSFCellStyle style = (XSSFCellStyle) workbook.createCellStyle();
			// 设置颜色
			style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
			style.setFillForegroundColor(HSSFColor.SKY_BLUE.index);// 前景颜色
			style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);// 填充方式，前色填充
			// 边框填充
			style.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 下边框
			style.setBorderLeft(HSSFCellStyle.BORDER_THIN);// 左边框
			style.setBorderTop(HSSFCellStyle.BORDER_THIN);// 上边框
			style.setBorderRight(HSSFCellStyle.BORDER_THIN);// 右边框
			style.setFont(font);
			style.setWrapText(false);

			style.setDataFormat(HSSFDataFormat.getBuiltinFormat("yyyy-MM-dd"));

			Object xlsDto = null;
			// sheet 对应一个工作页
			XSSFSheet sheet = (XSSFSheet) workbook.createSheet("导出excel");
			XSSFRow firstrow = sheet.createRow(0); // 下标为0的行开始
			sheet.setColumnWidth(120, 80);
			sheet.autoSizeColumn(1);
			sheet.autoSizeColumn(1, true);

			CellStyle css = workbook.createCellStyle();
			DataFormat dataFormat = workbook.createDataFormat();
			css.setDataFormat(dataFormat.getFormat("@"));

			XSSFCell[] firstcell = new XSSFCell[tempM.length];
			for (int j = 0; j < tempM.length; j++) {
				firstcell[j] = firstrow.createCell(j);
				String[] info = tempM[j].split("_");
				firstcell[j].setCellValue(new XSSFRichTextString(info[0]));
				firstcell[j].setCellStyle(style);

			}
			for (Object obj : xls) {
				int i = xls.indexOf(obj);
				// 创建一行
				XSSFRow row = sheet.createRow(i + 1);
				// 得到要插入的每一条记录
				xlsDto = xls.get(i);

				for (int cellNum = 0; cellNum < tempM.length; cellNum++) {
					String[] info = tempM[cellNum].split("_");
					Field field = xlsDto.getClass().getDeclaredField(info[1]);
					field.setAccessible(true);
					String type = field.getType().toString();// 得到此属性的类型
					Object value = field.get(xlsDto);
					// 在一行内循环
					XSSFCell xh = row.createCell(cellNum);
					// 判断是否有特殊要处理的参数
					if (paramMap.get(info[1]) != null) {
						Map result = (Map) paramMap.get(info[1]);
						if (result.containsKey(value)) {
							value = result.get(value);
						}
					}

					if (value != null) {
						if (type.endsWith("String")) {
							xh.setCellValue(value.toString());
						} else if (type.endsWith("int") || type.endsWith("Integer")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(value.toString());
						} else if (type.endsWith("boolean") || type.endsWith("Boolean")) {
							xh.setCellValue(Boolean.parseBoolean(value.toString()));
						} else if (type.endsWith("Date")) {
							if (value.toString().indexOf(".") > -1) {
								xh.setCellValue(value.toString().substring(0, value.toString().indexOf(".")));
							} else {
								xh.setCellValue((value.toString()));
							}
						} else if (type.endsWith("Double") || type.endsWith("double")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(df.format(value));
						} else if (type.endsWith("float") || type.endsWith("Float")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(df.format(value));
						} else if (type.endsWith("BigDecimal")) {
							// 设置空白行的单元格格式为“文本”
							sheet.setDefaultColumnStyle(i, css);
							xh.setCellValue(df.format(value));
						} else {
							xh.setCellValue(value.toString());
						}
					} else {
						xh.setCellValue("");
					}
				}
			}
		}
		return workbook;
	}

	/**
	 * workbook获取字节数组
	 * 
	 * @param workbook
	 * @return
	 * @throws IOException
	 */
	public static byte[] getByteArrayByWorkbook(Workbook workbook) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		workbook.write(baos);
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}

	/**
	 * sXSSFWorkbook获取字节数组
	 * 
	 * @param workbook
	 * @return
	 * @throws IOException
	 */
	public static byte[] getByteArrayByWorkbookNew(SXSSFWorkbook sXSSFWorkbook) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		sXSSFWorkbook.write(baos);
		byte[] bytes = baos.toByteArray();
		baos.close();
		return bytes;
	}

	/**
	 * obj -> BigDecimal
	 * 
	 * @param value
	 * @return
	 */
	public static BigDecimal getBigDecimal(Object value) {
		BigDecimal ret = new BigDecimal(0.00);
		if (value != null && !"".equals(value)) {
			if (value instanceof BigDecimal) {
				ret = (BigDecimal) value;
			} else if (value instanceof String) {
				ret = new BigDecimal((String) value);
			} else if (value instanceof BigInteger) {
				ret = new BigDecimal((BigInteger) value);
			} else if (value instanceof Number) {
				ret = new BigDecimal(Double.parseDouble(df.format(((Number) value))));
			} else {
				throw new ClassCastException("Not possible to coerce [" + value + "] from class " + value.getClass()
						+ " into a BigDecimal.");
			}
		}
		return ret;
	}

	/**
	 * 获取日期转换
	 * 
	 * @return
	 */
	public static Date getFormatPattern(String dateStr) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		Date date = null;

		try {
			if (dateStr != null && !"".equals(dateStr)) {
				date = format.parse(dateStr);
			}
		} catch (ParseException e) {
			logger.error("日期格式化错误", e);
		} catch (Exception e) {
			logger.error("日期格式化错误", e);
		}
		return date;

	}

	/**
	 * 获取日期转换
	 * 
	 * @return
	 */
	public static Date getFormatPattern(String dateStr, String formatPattern) {
		SimpleDateFormat format = new SimpleDateFormat(formatPattern);
		Date date = null;
		try {
			if (dateStr != null && !"".equals(dateStr)) {
				date = format.parse(dateStr);
			}
		} catch (ParseException e) {
			logger.error("日期格式化错误", e);
		} catch (Exception e) {
			logger.error("日期格式化错误", e);
		}
		return date;

	}

	/**
	 * 转换xml
	 * 
	 * @param object
	 * @return
	 */
	public static String toXML(Object object) {
		if (object != null) {
			XStream xStream = new XStream();
			String xml = xStream.toXML(object);
			return xml != null && xml.length() > 2500 ? xml.substring(0, 2500) : xml;
		} else {
			return "";
		}

	}

}
