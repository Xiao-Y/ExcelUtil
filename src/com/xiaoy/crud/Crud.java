package com.xiaoy.crud;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.xiaoy.db.JDBCUtil;
import com.xiaoy.model.GuProposalMain;

public class Crud {

	public List<GuProposalMain> getGuProposalMainList() throws SQLException {
		String sql = "select r.id,r.waybill_no, r.waybill_version,r.risk_class,"
				+ "r.proposal_no,r.policy_no, r.risk_code, r.risk_version, r.risk_name,"
				+ "r.agreement_no, r.client_code, r.client_name,r.settlement_type_code,"
				+ "r.account_no,r.payment_type,r.phone_no,r.input_date,r.ccy,r.sum_insured,"
				+ "r.uw_premium,r.gross_premium,r.unw_status,r.business_source,"
				+ "r.settlement_statuts,r.unw_date,r.unw_remark,r.handle_code,r.handle_name,"
				+ "r.process_state,r.uw_date,r.subtract_type,r.flag,r.remark,r.is_reinsurance,"
				+ "r.reinsurance_company_code,r.reinsurance_company_name,r.create_time,"
				+ "r.agreement_type,r.insure_type,r.subtract_type,rownum from gu_proposal_main r where rownum < 2";
		Connection connection = JDBCUtil.getConnection();
		try {

			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			List<GuProposalMain> list = new ArrayList<GuProposalMain>();
			while (rs.next()) {
				GuProposalMain g = new GuProposalMain();
				g.setId(rs.getLong("id"));
				g.setWaybillNo(rs.getString("waybill_no"));
				g.setWaybillVersion(rs.getShort("waybill_version"));
				g.setRiskClass(rs.getString("risk_class"));
				g.setProposalNo(rs.getString("proposal_no"));
				g.setPolicyNo(rs.getString("policy_no"));
				g.setRiskCode(rs.getString("risk_code"));
				g.setRiskVersion(rs.getShort("risk_version"));
				g.setRiskName(rs.getString("risk_name"));
				g.setAgreeMentNo(rs.getLong("agreement_no"));
				g.setClientCode(rs.getString("client_code"));
				g.setClientName(rs.getString("client_name"));
				g.setSettlementTypeCode(rs.getString("settlement_type_code"));
				g.setAccountNo(rs.getString("account_no"));
				g.setPaymentType(rs.getString("payment_type"));
				g.setPhoneNo(rs.getString("phone_no"));
				g.setInputDate(rs.getDate("input_date"));
				g.setCcy(rs.getString("ccy"));
				g.setSumInsured(rs.getBigDecimal("sum_insured"));
				g.setUwPremium(rs.getBigDecimal("uw_premium"));
				g.setGrossPremium(rs.getBigDecimal("gross_premium"));
				g.setUnwStatus(rs.getString("unw_status"));
				g.setBusinessSource(rs.getString("business_source"));
				g.setSettlementStatuts(rs.getString("settlement_statuts"));
				g.setUwDate(rs.getDate("unw_date"));
				g.setUnwRemark(rs.getString("unw_remark"));
				g.setHandleCode(rs.getString("handle_code"));
				g.setHandleName(rs.getString("handle_name"));
				g.setProcessState(rs.getString("process_state"));
				g.setSubtractType(rs.getString("subtract_type"));
				g.setFlag(rs.getString("flag"));
				g.setRemark(rs.getString("remark"));
				g.setIsReinsurance(rs.getString("is_reinsurance"));
				g.setReinsuranceCompanyCode(rs.getString("reinsurance_company_code"));
				g.setReinsuranceCompanyName(rs.getString("reinsurance_company_name"));
				g.setCreateTime(rs.getDate("create_time"));
				g.setAgreementType(rs.getString("agreement_type"));
				g.setInsureType(rs.getString("insure_type"));
				list.add(g);
			}
			return list;
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JDBCUtil.closeConnection(connection);
		}
		return null;
	}

	public ResultSet getGuProposalMainResultSet(Connection connection) throws SQLException {
		String sql = "select r.id,r.waybill_no, r.waybill_version,r.risk_class,"
				+ "r.proposal_no,r.policy_no, r.risk_code, r.risk_version, r.risk_name,"
				+ "r.agreement_no, r.client_code, r.client_name,r.settlement_type_code,"
				+ "r.account_no,r.payment_type,r.phone_no,r.input_date,r.ccy,r.sum_insured,"
				+ "r.uw_premium,r.gross_premium,r.unw_status,r.business_source,"
				+ "r.settlement_statuts,r.unw_date,r.unw_remark,r.handle_code,r.handle_name,"
				+ "r.process_state,r.uw_date,r.subtract_type,r.flag,r.remark,r.is_reinsurance,"
				+ "r.reinsurance_company_code,r.reinsurance_company_name,r.create_time,"
				+ "r.agreement_type,r.insure_type,r.subtract_type from gu_proposal_main r ";
		try {
			PreparedStatement ps = connection.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			return rs;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
