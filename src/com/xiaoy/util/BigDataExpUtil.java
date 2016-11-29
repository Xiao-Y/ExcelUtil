package com.xiaoy.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * 大数据导出excel
 * 
 * @author Liuyongtao
 * @version $Id: BigDataExpUtil.java 2015年12月30日 下午6:55:25 $
 */
public class BigDataExpUtil {

	/**
	 * 压缩后的文件名
	 */
	private String zipFileName;
	private ResultSet rs;
	private HttpServletResponse response;
	private int rowCount = 0;// 总记录数

	/**
	 * 构造方法
	 * 
	 * @param response
	 *            HttpServletResponse
	 * @param rs
	 *            结果集
	 * @param zipFileName
	 *            压缩后的文件名，默认为yyyyMMddHHmmss
	 */
	public BigDataExpUtil(HttpServletResponse response, ResultSet rs, String zipFileName) {
		this.rs = rs;
		if (StringUtils.isEmpty(zipFileName)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
			String string = format.format(new Date());
			zipFileName = string + ".zip";
		}
		this.zipFileName = zipFileName;
		this.response = response;
	}

	/**
	 * 每个文件的最大行数 超过请求按默认算
	 */
	private static final int MAXROWS = 50000;

	/**
	 * excel文件的前缀
	 */
	private static final String PREFIX = "excelTemp";

	/**
	 * excel文件的后缀
	 */
	private static final String SUFFIX = ".xls";

	/**
	 * 设置编码格式
	 */
	private static final String ENCODED = "UTF-8";

	/**
	 * html文件的头
	 */
	StringBuffer headStr = new StringBuffer("<html xmlns:x=\"urn:schemas-microsoft-com:office:excel\">")
			.append("<head>")
			.append("<meta http-equiv=\"content-type\" content=\"application/ms-excel; charset=UTF-8\"/>")
			.append("<!--[if gte mso 9]><xml>").append("<x:ExcelWorkbook>").append("<x:ExcelWorksheets>")
			.append("<x:ExcelWorksheet>").append("<x:Name></x:Name>").append("<x:WorksheetOptions>")
			.append("<x:Print>").append("<x:ValidPrinterInfo />").append("</x:Print>").append("</x:WorksheetOptions>")
			.append("</x:ExcelWorksheet>").append("</x:ExcelWorksheets>").append("</x:ExcelWorkbook>")
			.append("</xml><![endif]-->").append("</head>").append("<body>").append("<table>");

	/**
	 * html文件的尾
	 */
	StringBuffer footStr = new StringBuffer("</table></body></html>");

	/**
	 * 导出数据并压缩
	 * 
	 * @param params
	 *            excel头及数据列表头,格式为：tempM[0] = "运单号*waybill_no";
	 * @param maxRow
	 *            最大列，默认为5w,最大为5w
	 * @param fileNamePrefix
	 *            文件的前缀，不能少于8个字符,默认为excelTemp
	 */
	public void exportToZip(final String[] params, final int maxRow, String fileNamePrefix) {
		long start = System.currentTimeMillis();
		System.out.println("导出start");

		int leng = params.length;// 表头长度
		String[] titles = new String[leng];// excel中文表头
		String[] titles_y = new String[leng];// 用于获取结果集的key
		for (int i = 0; i < leng; i++) {
			String[] split = params[i].split("[*]");
			titles[i] = split[0];
			titles_y[i] = split[1];
		}

		final int max = this.getMaxRows(maxRow);// 每个文件最大行数
		List<File> fileList = new ArrayList<File>();// 文件收集器
		int i = 0;// 行数记录器
		File file = null;// 临时文件
		FileOutputStream fos = null;// 文件输出流
		try {
			while (rs.next()) {
				// 达到最大行数 或者 新建的 创建新文件
				if (i == max || i == 0) {
					// 如果不是新文件 为这个文件写入文件尾
					if (file != null) {
						// 写文件尾
						this.writeFooterToOutputStream(fos);
						// 关闭流
						IOUtils.closeQuietly(fos);
					}
					// 创建临时文件
					file = this.createTempFile(fileNamePrefix);
					// 打开流
					fos = FileUtils.openOutputStream(file);
					// 放进收集器里
					fileList.add(file);
					// 写文件头
					this.writeHeaderToOutputStream(fos);
					// 数据区标题栏
					this.writeTitleToOutputStream(titles, fos);
					i = 0;
				}
				i++;
				rowCount++;// 总记录数
				// 写实际一行数据
				this.writeOneRowToOutputStream(titles_y, rs, fos);
			}

			if (file != null) {
				// 写文件尾
				this.writeFooterToOutputStream(fos);
				// 关闭流
				IOUtils.closeQuietly(fos);
			}
			// 打包
			this.doZip(fileList);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fos);
			// 清空临时文件
			this.cleanTempFile(fileList);
			fileList.clear();
			fileList = null;
		}
		long end = System.currentTimeMillis();
		System.out.println("导出end");
		System.out.println("总记录数：" + rowCount);
		System.out.println("运行：" + (end - start) + "ms");
	}

	/**
	 * 打包压缩成zip
	 * 
	 * @param os
	 *            压缩输出流
	 * @param fileList
	 *            被压缩的文件列表
	 * @throws IOException
	 */
	private void doZip(List<File> fileList) throws IOException {
		System.out.println("打包压缩成zip start");

		// 设置输出流格式 start
		response.setCharacterEncoding(ENCODED);
		zipFileName = java.net.URLEncoder.encode(zipFileName, ENCODED);
		response.setHeader("Content-Disposition", "attachment; filename=" + zipFileName);
		response.setContentType("application/zip");// 定义输出类型
		ServletOutputStream os = response.getOutputStream();
		// 设置输出流格式 end
		if (fileList != null && fileList.size() > 0) {
			byte[] buf = new byte[1024];
			ZipOutputStream out = new ZipOutputStream(os);
			for (File file : fileList) {
				FileInputStream in = new FileInputStream(file);
				out.putNextEntry(new ZipEntry(file.getName()));
				int len;
				while ((len = in.read(buf)) > 0) {
					out.write(buf, 0, len);
				}
				out.closeEntry();
				in.close();
			}
			out.close();
		}
		System.out.println("打包压缩成zip end");
	}

	/**
	 * 删除临时文件
	 * 
	 * @param fileList
	 */
	private void cleanTempFile(List<File> fileList) {
		for (File file : fileList) {
			System.out.println("删除文件临时文件：" + file.getPath());
			file.delete();
		}
	}

	/**
	 * 创建临时文件
	 * 
	 * @return
	 * @throws IOException
	 */
	private File createTempFile(String fileNamePrefix) throws IOException {
		fileNamePrefix = StringUtils.isEmpty(fileNamePrefix) ? this.getPrefix() : fileNamePrefix;
		return File.createTempFile(fileNamePrefix, this.getSuffix());
	}

	/**
	 * 文件开头的写入
	 * 
	 * @param fos
	 * @throws IOException
	 */
	private void writeHeaderToOutputStream(FileOutputStream fos) throws IOException {
		this.writeToOutputStream(headStr.toString(), fos);
	}

	/**
	 * 文件结尾的写入
	 * 
	 * @param fos
	 * @throws IOException
	 */
	private void writeFooterToOutputStream(FileOutputStream fos) throws IOException {
		this.writeToOutputStream(footStr.toString(), fos);
	}

	/**
	 * 写入Excel表头
	 * 
	 * @param titles
	 *            表头集
	 * @param fos
	 *            文件输出流
	 * @throws IOException
	 */
	private void writeTitleToOutputStream(String[] titles, FileOutputStream fos) throws IOException {
		System.out.println("写入Excel表头");
		if (titles != null && titles.length > 0) {
			this.writeToOutputStream("<tr>", fos);
			for (String title : titles) {
				this.writeToOutputStream("<td>" + (StringUtils.isNotEmpty(title) ? title : "") + "</td>", fos);
			}
			this.writeToOutputStream("</tr>", fos);
		}
	}

	/**
	 * 一行数据的写入
	 * 
	 * @param rs
	 *            结果集中的一行
	 * @param fos
	 * @throws SQLException
	 * @throws IOException
	 */
	private void writeOneRowToOutputStream(String[] title_y, ResultSet rs, FileOutputStream fos) throws SQLException,
			IOException {
		// 获取结果集的信息
		ResultSetMetaData metaData = rs.getMetaData();
		int leng = title_y.length;
		this.writeToOutputStream("<tr>", fos);
		for (int i = 0; i < leng; i++) {
			// 获取结果集中指定列的数据库类型
			int type = metaData.getColumnType(i + 1);
			// 获取指定列的值
			Object object = this.getTypeObject(rs, type, title_y[i]);
			// 写入一列
			this.writeToOutputStream("<td>" + object + "</td>", fos);
		}
		this.writeToOutputStream("</tr>", fos);
	}

	/**
	 * 数据输出
	 * 
	 * @param data
	 *            写入数据
	 * @param fos
	 *            文件输出流
	 * @throws IOException
	 */
	private void writeToOutputStream(String data, FileOutputStream fos) throws IOException {
		IOUtils.write(data, fos, ENCODED);
	}

	/**
	 * 获取单个文件最大行数
	 * 
	 * @param maxRow
	 *            默认为5w，最大值5w
	 * @return
	 */
	private int getMaxRows(int maxRow) {
		return maxRow < MAXROWS ? maxRow : MAXROWS;
	}

	/**
	 * 临时文件前缀
	 * 
	 * @return
	 */
	private String getPrefix() {
		return PREFIX;
	}

	/**
	 * 临时文件后缀
	 * 
	 * @return
	 */
	private String getSuffix() {
		return SUFFIX;
	}

	/**
	 * 获取数据库中字段的类型
	 * 
	 * @param rs
	 *            单个结果集
	 * @param type
	 *            当前列的类型
	 * @param column
	 *            当前列的列头
	 * @return 列值
	 * @throws SQLException
	 */
	private Object getTypeObject(ResultSet rs, int type, String column) throws SQLException {
		Object o = null;
		switch (type) {
			case Types.INTEGER:
				o = rs.getLong(column);
				break;
			case Types.DATE:
				o = rs.getDate(column);
				break;
			case Types.TINYINT:
			case Types.SMALLINT:
				o = rs.getInt(column);
				break;
			case Types.DECIMAL:
				o = rs.getBigDecimal(column);
				break;
			case Types.VARCHAR:
			case Types.NVARCHAR:
			case Types.OTHER:
			case Types.NUMERIC:
				o = rs.getString(column);
				break;
			default:
				o = rs.getString(column);
				break;
		}
		return o;
	}
}
