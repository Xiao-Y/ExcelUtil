package com.xiaoy.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DownloadUtils {
	private static Logger logger = LoggerFactory.getLogger(DownloadUtils.class);

	public static void download(HttpServletRequest request, HttpServletResponse response, String filePath)
			throws IOException {
		download(request, response, filePath, "");
	}

	public static void download(HttpServletRequest request, HttpServletResponse response, String filePath,
			String displayName) throws IOException {
		File file = new File(filePath);

		if (StringUtils.isEmpty(displayName)) {
			displayName = file.getName();
		}

		if (!file.exists() || !file.canRead()) {
			response.setContentType("text/html;charset=utf-8");
			response.getWriter().write("您下载的文件不存在！");
			return;
		}

		String userAgent = request.getHeader("User-Agent");
		boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);

		response.reset();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "must-revalidate, no-transform");
		response.setDateHeader("Expires", 0L);
		response.setContentType("application/octet-stream;charset=utf-8");
		response.setContentLength((int) file.length());

		String displayFilename = displayName.substring(displayName.lastIndexOf('_') + 1);
		displayFilename = displayFilename.replace(" ", "_");
		if (isIE) {
			displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
		} else {
			displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
		}
		BufferedInputStream is = null;
		OutputStream os = null;
		try {

			os = response.getOutputStream();
			is = new BufferedInputStream(new FileInputStream(file));
			IOUtils.copy(is, os);

		} catch (Exception e) {
			logger.error("download error", e);
		} finally {
			if (os != null) {
				os.flush();
				os.close();
			}
			IOUtils.closeQuietly(is);
		}
	}

	public static void download(HttpServletRequest request, HttpServletResponse response, String displayName,
			byte[] bytes) throws IOException {

		if (ArrayUtils.isEmpty(bytes)) {
			response.setContentType("text/html;charset=utf-8");
			response.setCharacterEncoding("utf-8");
			response.getWriter().write("您下载的文件不存在！");
			return;
		}

		String userAgent = request.getHeader("User-Agent");
		boolean isIE = (userAgent != null) && (userAgent.toLowerCase().indexOf("msie") != -1);

		response.reset();
		response.setHeader("Pragma", "No-cache");
		response.setHeader("Cache-Control", "must-revalidate, no-transform");
		response.setDateHeader("Expires", 0L);

		response.setContentType("application/x-download");
		response.setContentLength((int) bytes.length);

		String displayFilename = displayName.substring(displayName.lastIndexOf('_') + 1);
		displayFilename = displayFilename.replace(" ", "_");
		if (isIE) {
			displayFilename = URLEncoder.encode(displayFilename, "UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + displayFilename + "\"");
		} else {
			displayFilename = new String(displayFilename.getBytes("UTF-8"), "ISO8859-1");
			response.setHeader("Content-Disposition", "attachment;filename=" + displayFilename);
		}
		BufferedInputStream is = null;
		OutputStream os = null;
		try {

			os = response.getOutputStream();
			is = new BufferedInputStream(new ByteArrayInputStream(bytes));
			IOUtils.copy(is, os);
		} catch (Exception e) {
			logger.error("download error", e);
		} finally {
			IOUtils.closeQuietly(is);
		}
	}
}
