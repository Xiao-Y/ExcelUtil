package com.xiaoy.model;

import java.math.BigDecimal;
import java.util.Date;

public class GuProposalMain implements java.io.Serializable {
	private static final long serialVersionUID = 1L;
	private Long id;

	/**
	 * 运单号
	 */
	private String waybillNo;

	/**
	 * 运单版本
	 */
	private Short waybillVersion;

	/**
	 * 产品大类
	 */
	private String riskClass;

	/**
	 * 产品代码
	 */
	private String riskCode;
	/**
	 * 产品版本
	 */
	private Short riskVersion;

	/**
	 * 投保单号
	 */
	private String proposalNo;

	/**
	 * 保单号
	 */
	private String policyNo;

	/**
	 * 产品代码
	 */
	private String riskName;

	/**
	 * 协议
	 */
	// private GuAgreementMain guAgreementMain;

	/**
	 * 协议代码
	 */
	private Long agreeMentNo;

	/**
	 * 投保人代码
	 */
	private String clientCode;

	/**
	 * 投保人名称
	 */
	private String clientName;

	/**
	 * 结算方式
	 */
	private String settlementTypeCode;

	/**
	 * 月结账号
	 */
	private String accountNo;

	/**
	 * 付款方式
	 */
	private String paymentType;

	/**
	 * 联系人电话
	 */
	private String phoneNo;

	/**
	 * 投保时间
	 */
	private Date inputDate;

	/**
	 * 投保币种
	 */
	private String ccy;

	/**
	 * 声明价值
	 */
	private BigDecimal sumInsured;

	/**
	 * 承保保费
	 */
	private BigDecimal uwPremium;

	/**
	 * 应收保费
	 */
	private BigDecimal grossPremium;

	/**
	 * 核保状态
	 */
	private String unwStatus;

	/**
	 * 业务来源，大网、自动投保、网上投保
	 */
	private String businessSource;

	/**
	 * 缴费状态，0-未出账/待缴费;1-已出账/已出单
	 */
	private String settlementStatuts;

	/**
	 * 核保时间
	 */
	private Date unwDate;

	/**
	 * 驳回原因
	 */
	private String unwRemark;

	/**
	 * 处理人代码
	 */
	private String handleCode;

	/**
	 * 处理人名称
	 */
	private String handleName;

	/**
	 * 审核处理状态
	 */
	private String processState;

	/**
	 * 承保时间
	 */
	private Date uwDate;
	/**
	 * 创建时间
	 */
	private Date createTime;

	/**
	 * 保单有效性
	 */
	private String flag;

	/**
	 * 备注
	 */
	private String remark;

	/**
	 * 冲补类型
	 */
	private String subtractType;

	/**
	 * 是否再保
	 * 
	 */
	private String isReinsurance;
	/**
	 * 再保公司代码
	 * 
	 */
	private String reinsuranceCompanyCode;
	/**
	 * 再保公司名称
	 * 
	 */
	private String reinsuranceCompanyName;

	/**
	 * 投保方式 1-运单勾选；2-在线申请;3-预约统保；4-票票保；5-产品自动投保
	 */
	private String insureType;
	/**
	 * 协议类型:1-费率/票票保协议；2-预约统保
	 */
	private String agreementType;

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getWaybillNo() {
		return this.waybillNo;
	}

	public void setWaybillNo(String waybillNo) {
		this.waybillNo = waybillNo;
	}

	public Short getWaybillVersion() {
		return this.waybillVersion;
	}

	public void setWaybillVersion(Short waybillVersion) {
		this.waybillVersion = waybillVersion;
	}

	public String getRiskClass() {
		return this.riskClass;
	}

	public void setRiskClass(String riskClass) {
		this.riskClass = riskClass;
	}

	public String getProposalNo() {
		return this.proposalNo;
	}

	public String getRiskCode() {
		return riskCode;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setRiskCode(String riskCode) {
		this.riskCode = riskCode;
	}

	public Short getRiskVersion() {
		return riskVersion;
	}

	public void setRiskVersion(Short riskVersion) {
		this.riskVersion = riskVersion;
	}

	public void setProposalNo(String proposalNo) {
		this.proposalNo = proposalNo;
	}

	public String getPolicyNo() {
		return this.policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getRiskName() {
		return this.riskName;
	}

	public void setRiskName(String riskName) {
		this.riskName = riskName;
	}

	public String getClientCode() {
		return this.clientCode;
	}

	public void setClientCode(String clientCode) {
		this.clientCode = clientCode;
	}

	public String getClientName() {
		return this.clientName;
	}

	public void setClientName(String clientName) {
		this.clientName = clientName;
	}

	public String getSettlementTypeCode() {
		return this.settlementTypeCode;
	}

	public void setSettlementTypeCode(String settlementTypeCode) {
		this.settlementTypeCode = settlementTypeCode;
	}

	public String getAccountNo() {
		return this.accountNo;
	}

	public void setAccountNo(String accountNo) {
		this.accountNo = accountNo;
	}

	public String getPaymentType() {
		return this.paymentType;
	}

	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}

	public String getPhoneNo() {
		return this.phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public Date getInputDate() {
		return this.inputDate;
	}

	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	public String getCcy() {
		return this.ccy;
	}

	public void setCcy(String ccy) {
		this.ccy = ccy;
	}

	public BigDecimal getSumInsured() {
		return this.sumInsured;
	}

	public void setSumInsured(BigDecimal sumInsured) {
		this.sumInsured = sumInsured;
	}

	public BigDecimal getUwPremium() {
		return this.uwPremium;
	}

	public void setUwPremium(BigDecimal uwPremium) {
		this.uwPremium = uwPremium;
	}

	public BigDecimal getGrossPremium() {
		return this.grossPremium;
	}

	public void setGrossPremium(BigDecimal grossPremium) {
		this.grossPremium = grossPremium;
	}

	public String getUnwStatus() {
		return this.unwStatus;
	}

	public void setUnwStatus(String unwStatus) {
		this.unwStatus = unwStatus;
	}

	public String getBusinessSource() {
		return this.businessSource;
	}

	public void setBusinessSource(String businessSource) {
		this.businessSource = businessSource;
	}

	public String getSettlementStatuts() {
		return this.settlementStatuts;
	}

	public void setSettlementStatuts(String settlementStatuts) {
		this.settlementStatuts = settlementStatuts;
	}

	public Date getUnwDate() {
		return this.unwDate;
	}

	public void setUnwDate(Date unwDate) {
		this.unwDate = unwDate;
	}

	public String getUnwRemark() {
		return this.unwRemark;
	}

	public void setUnwRemark(String unwRemark) {
		this.unwRemark = unwRemark;
	}

	public String getHandleCode() {
		return this.handleCode;
	}

	public void setHandleCode(String handleCode) {
		this.handleCode = handleCode;
	}

	public String getHandleName() {
		return this.handleName;
	}

	public void setHandleName(String handleName) {
		this.handleName = handleName;
	}

	public String getProcessState() {
		return this.processState;
	}

	public void setProcessState(String processState) {
		this.processState = processState;
	}

	public Date getUwDate() {
		return this.uwDate;
	}

	public void setUwDate(Date uwDate) {
		this.uwDate = uwDate;
	}

	public String getFlag() {
		return this.flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSubtractType() {
		return subtractType;
	}

	public void setSubtractType(String subtractType) {
		this.subtractType = subtractType;
	}

	public Long getAgreeMentNo() {
		return agreeMentNo;
	}

	public void setAgreeMentNo(Long agreeMentNo) {
		this.agreeMentNo = agreeMentNo;
	}

	public String getIsReinsurance() {
		return isReinsurance;
	}

	public void setIsReinsurance(String isReinsurance) {
		this.isReinsurance = isReinsurance;
	}

	public String getReinsuranceCompanyCode() {
		return reinsuranceCompanyCode;
	}

	public void setReinsuranceCompanyCode(String reinsuranceCompanyCode) {
		this.reinsuranceCompanyCode = reinsuranceCompanyCode;
	}

	public String getReinsuranceCompanyName() {
		return reinsuranceCompanyName;
	}

	public void setReinsuranceCompanyName(String reinsuranceCompanyName) {
		this.reinsuranceCompanyName = reinsuranceCompanyName;
	}

	public String getInsureType() {
		return insureType;
	}

	public void setInsureType(String insureType) {
		this.insureType = insureType;
	}

	public String getAgreementType() {
		return agreementType;
	}

	public void setAgreementType(String agreementType) {
		this.agreementType = agreementType;
	}
}
