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

import com.xiaoy.crud.Crud;
import com.xiaoy.db.JDBCUtil;
import com.xiaoy.util.BigDataExpUtil;

@WebServlet("/HtmlLoadExecl")
public class HtmlLoadExecl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public HtmlLoadExecl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		Crud c = new Crud();
		ResultSet rs;
		Connection connection = JDBCUtil.getConnection();
		try {
			rs = c.getGuProposalMainResultSet(connection);
			String[] param = this.getParam();
			BigDataExpUtil b = new BigDataExpUtil(response, rs, null);
			b.exportToZip(param, 50000, "");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if (connection != null) {
				JDBCUtil.closeConnection(connection);
			}
		}
	}

	private String[] getParam() {
		String[] tempM = new String[39];
		tempM[0] = "运单号*waybill_no";
		tempM[1] = "运单版本*waybill_version";
		tempM[2] = "产品大类*risk_class";
		tempM[3] = "产品代码*risk_code";
		tempM[4] = "产品版本*risk_version";
		tempM[5] = "投保单号*proposal_no";
		tempM[6] = "产品名称*risk_name";
		tempM[7] = "协议代码*agreement_no";
		tempM[8] = "投保人代码*client_code";
		tempM[9] = "投保人名称*client_name";
		tempM[10] = "结算方式*settlement_type_code";
		tempM[11] = "月结账号*account_no";
		tempM[12] = "付款方式*payment_type";
		tempM[13] = "联系人电话*phone_no";
		tempM[14] = "投保时间*input_date";
		tempM[15] = "投保币种*ccy";
		tempM[16] = "声明价值*sum_insured";
		tempM[17] = "承保保费*uw_premium";
		tempM[18] = "应收保费*gross_premium";
		tempM[19] = "核保状态*unw_status";
		tempM[20] = "业务来源*business_source";
		tempM[21] = "缴费状态*settlement_statuts";
		tempM[22] = "核保时间*unw_date";
		tempM[23] = "驳回原因*unw_remark";
		tempM[24] = "处理人代码*handle_code";
		tempM[25] = "处理人名称*handle_name";
		tempM[26] = "审核处理状态*process_state";
		tempM[27] = "承保时间*uw_date";
		tempM[28] = "创建时间*create_time";
		tempM[29] = "保单有效性*flag";
		tempM[30] = "备注*remark";
		tempM[31] = "保单号*policy_no";
		tempM[32] = "是否再保*is_reinsurance";
		tempM[33] = "再保公司代码*reinsurance_company_code";
		tempM[34] = "再保公司名称*reinsurance_company_name";
		tempM[35] = "投保方式 *insure_type";
		tempM[36] = "协议类型*agreement_type";
		tempM[37] = "ID*id";
		tempM[38] = "冲补类型*subtract_type";

		return tempM;
	}
}
