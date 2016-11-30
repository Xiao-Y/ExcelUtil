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
@WebServlet("/BigDataExport2")
public class BigDataExport2 extends HttpServlet {

	private static final long serialVersionUID = -821904796803702278L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		toResultSet(resp);
	}

	/**
	 * 结果集形式
	 * 
	 * <br>
	 * added by liuyongtao<br>
	 * 
	 * @param resp
	 * 
	 * @date 2016年11月30日 上午9:54:43
	 */
	private void toResultSet(HttpServletResponse resp) {
		Connection connection = JDBCUtil.getConnection();
		ResultSet rs = null;
		try {
			ZipCRUD c = new ZipCRUD();
			rs = c.getGwWfLogResultSet(connection);
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
		String[] tempM = new String[45];
		tempM[0] = "nodeStatus*nodeStatus";
		tempM[1] = "flowStatus*flowStatus";
		tempM[2] = "packageId*packageId";
		tempM[3] = "businessType*businessType";
		tempM[4] = "businessNo*businessNo";
		tempM[5] = "contractNo*contractNo";
		tempM[6] = "planCode*planCode";
		tempM[7] = "riskClass*riskClass";
		tempM[8] = "riskCode*riskCode";
		tempM[9] = "issueCompany*issueCompany";
		tempM[10] = "companyCode*companyCode";
		tempM[11] = "handlerCode*handlerCode";
		tempM[12] = "salesmanCode*salesmanCode";
		tempM[13] = "relateFlowId*relateFlowId";
		tempM[14] = "relateLogNo*relateLogNo";
		tempM[15] = "posX*posX";
		tempM[16] = "posY*posY";
		tempM[17] = "licenseNo*licenseNo";
		tempM[18] = "relateContractNo*relateContractNo";
		tempM[19] = "insuredCode*insuredCode";
		tempM[20] = "insuredName*insuredName";
		tempM[21] = "identifyType*identifyType";
		tempM[22] = "identifyNumber*identifyNumber";
		tempM[23] = "reinsStatus*reinsStatus";
		tempM[24] = "policyNo*policyNo";
		tempM[25] = "claimNo*claimNo";
		tempM[26] = "flag*flag";
		tempM[27] = "firstTrial*firstTrial";
		tempM[28] = "appliCode*appliCode";
		tempM[29] = "appliName*appliName";
		tempM[30] = "priorityInd*priorityInd";
		tempM[31] = "registNo*registNo";
		tempM[32] = "channelDetailCode*channelDetailCode";
		tempM[33] = "adjustmentType*adjustmentType";
		tempM[34] = "relateClaimNo*relateClaimNo";
		tempM[35] = "taskNatureInd*taskNatureInd";
		tempM[36] = "oldNodeNo*oldNodeNo";
		tempM[37] = "imageInd*imageInd";
		tempM[38] = "endorType*endorType";
		tempM[39] = "oano*oano";
		tempM[40] = "conveyanceCodeInd*conveyanceCodeInd";
		tempM[41] = "vipInd*vipInd";
		tempM[42] = "nodeSource*nodeSource";
		tempM[43] = "clientAddr*clientAddr";
		tempM[44] = "taskType*taskType";
		return tempM;
	}

}
