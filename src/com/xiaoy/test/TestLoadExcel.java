package com.xiaoy.test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.xiaoy.crud.Crud;
import com.xiaoy.model.GuProposalMain;
import com.xiaoy.util.CommUtils;
import com.xiaoy.util.DownloadUtils;

public class TestLoadExcel {

	private static Logger logger = LoggerFactory.getLogger(CommUtils.class);

	public void loadExcel(HttpServletRequest request, HttpServletResponse response) {
		long start = System.currentTimeMillis();
		logger.info("协议导出方法loadExcel start");
		// Locale locale = LocaleContextHolder.getLocale();
		String fileName = "协议查询结果导出.xlsx";
		try {
			// 获取记录限制
			// int start = RequestUtils.getInt(paramsMap, "start", 0);
			// int length = RequestUtils.getInt(paramsMap, "length", 50);

			// 根据传入的orderBy值去排序
			// List<CodeValuePair> orderBy =
			// RequestPageUtils.getOrderByList(request);
			// orderBy.add(new CodeValuePair("agreementNo","desc"));
			// service调用daofindforpage方法
			// ResultPage<GuAgreementMainVo> pages = guAgreementService
			// .findForPage(guAgreementMainVo, orderBy, start, length , isAll);
			String[] tempM = new String[40];
			tempM[0] = "运单号_waybillNo";
			tempM[1] = "运单版本_waybillVersion";
			tempM[2] = "产品大类_riskClass";
			tempM[3] = "产品代码_riskCode";
			tempM[4] = "产品版本_riskVersion";
			tempM[5] = "投保单号_proposalNo";
			tempM[6] = "产品名称_riskName";
			tempM[7] = "协议代码_agreeMentNo";
			tempM[8] = "投保人代码_clientCode";
			tempM[9] = "投保人名称_clientName";
			tempM[10] = "结算方式_settlementTypeCode";
			tempM[11] = "月结账号_accountNo";
			tempM[12] = "付款方式_paymentType";
			tempM[13] = "联系人电话_phoneNo";
			tempM[14] = "投保时间_inputDate";
			tempM[15] = "投保币种_ccy";
			tempM[16] = "声明价值_sumInsured";
			tempM[17] = "承保保费_uwPremium";
			tempM[18] = "应收保费_grossPremium";
			tempM[19] = "核保状态_unwStatus";
			tempM[20] = "业务来源_businessSource";
			tempM[21] = "缴费状态_settlementStatuts";
			tempM[22] = "核保时间_unwDate";
			tempM[23] = "驳回原因_unwRemark";
			tempM[24] = "处理人代码_handleCode";
			tempM[25] = "处理人名称_handleName";
			tempM[26] = "审核处理状态_processState";
			tempM[27] = "承保时间_uwDate";
			tempM[28] = "创建时间_createTime";
			tempM[29] = "保单有效性_flag";
			tempM[30] = "备注_remark";
			tempM[31] = "保单号_policyNo";
			tempM[32] = "是否再保_isReinsurance";
			tempM[33] = "再保公司代码_reinsuranceCompanyCode";
			tempM[34] = "再保公司名称_reinsuranceCompanyName";
			tempM[35] = "投保方式 _insureType";
			tempM[36] = "协议类型_agreementType";
			tempM[37] = "ID_id";
			tempM[38] = "创建时间_createTime";
			tempM[39] = "冲补类型_subtractType";
			// 需要特殊处理的参数(如查询数据字典)
			Map<String, Map<Object, Object>> paramMap = new HashMap<String, Map<Object, Object>>();
			// paramMap.put("damageReason",codeService.findByCodeTypeToList("DamageReason",
			// "1",locale.getCountry()));

			// 数据转excel
			// Workbook workbook = CommUtils.xlsDto2Excel(pages.getData(),
			// tempM,fileName, paramMap);
			Crud c = new Crud();
			List<GuProposalMain> guProposalMainList = c.getGuProposalMainList();
			Workbook workbook = CommUtils.xlsDto2Excel(guProposalMainList, tempM, fileName, paramMap);
			long end = System.currentTimeMillis();
			System.out.println("运行：" + (end - start) + "ms");
			// workbook获取字节数组
			byte[] bytes = CommUtils.getByteArrayByWorkbook(workbook);
			// excle下载
			DownloadUtils.download(request, response, fileName, bytes);
			logger.info("协议导出方法loadExcel end");
		} catch (Exception e) {
			logger.error("协议查询 导出excel", e);
		}
	}
}
