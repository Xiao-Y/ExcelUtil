package com.xiaoy.controller;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.xiaoy.crud.ZipCRUD;
import com.xiaoy.db.JDBCUtil;
import com.xiaoy.util.BigDataExpUtil;

/**
 * 大数所量导出
 * 
 * @author XiaoY
 * @date: 2016年11月29日 下午9:07:07
 */
@WebServlet("/BigDataExport")
public class BigDataExport extends HttpServlet {

	private static final long serialVersionUID = -821904796803702278L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		Connection connection = JDBCUtil.getConnection();
		ResultSet rs = null;
		try {
			ZipCRUD c = new ZipCRUD();
			rs = c.getZipResultSet(connection);
			String[] param = this.getParam();
			BigDataExpUtil b = new BigDataExpUtil(resp, rs, null);
			b.exportToZip(param, 50000, "");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private String[] getParam() {
		String[] tempM = new String[10];
		tempM[0] = "ID*ID";
		tempM[1] = "城市代码*CITY_CODE";
		tempM[2] = "纬度*LAT";
		tempM[3] = "城市等级*LEVEL_TYPE";
		tempM[4] = "经度*LNG";
		tempM[5] = "全称*NAME";
		tempM[6] = "上级城市ID*PARENT_ID";
		tempM[7] = "城市拼音*PIN_YIN";
		tempM[8] = "城市简称*SHORT_NAME";
		tempM[9] = "行政编码*ZIP_CODE";
		return tempM;
	}

}
