package com.maan.insurance.validation.reports;

import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.req.reports.CallProBookedPremiumReq;
import com.maan.insurance.model.req.reports.FinanceStagingListReq;
import com.maan.insurance.model.req.reports.GetClaimPaidRegisterListReq;
import com.maan.insurance.model.req.reports.GetClaimRegisterListReq;
import com.maan.insurance.model.req.reports.GetColumnInfoListReq;
import com.maan.insurance.model.req.reports.GetDebtorsAgeingReportReq;
import com.maan.insurance.model.req.reports.GetIEReportReq;
import com.maan.insurance.model.req.reports.GetInstallmentDueReportReq;
import com.maan.insurance.model.req.reports.GetInwardRetroMappingReportReq;
import com.maan.insurance.model.req.reports.GetJVReportsReq;
import com.maan.insurance.model.req.reports.GetJournalListCrntReq;
import com.maan.insurance.model.req.reports.GetMoveMentPageReq;
import com.maan.insurance.model.req.reports.GetMoveMntSummaryReq;
import com.maan.insurance.model.req.reports.GetPayRecRegisterListReq;
import com.maan.insurance.model.req.reports.GetPipelineMovementJvDetailsReq;
import com.maan.insurance.model.req.reports.GetPortfolioOutPendingReportReq;
import com.maan.insurance.model.req.reports.GetRetroInwardMappingReportReq;
import com.maan.insurance.model.req.reports.GetRetroRegisterListReq;
import com.maan.insurance.model.req.reports.GetTrailBalanceReportReq;
import com.maan.insurance.model.req.reports.GetTransactionMasterReportReq;
import com.maan.insurance.model.req.reports.GetTreatyWithdrawRegisterListReq;
import com.maan.insurance.model.req.reports.GetUWStatisticsReportReq;
import com.maan.insurance.model.req.reports.GetViewAllReq;
import com.maan.insurance.model.req.reports.GetViewJurnelAllReq;
import com.maan.insurance.model.req.reports.GetallocationReportListReq;
import com.maan.insurance.model.req.reports.InsertCLMovementReq;
import com.maan.insurance.model.req.reports.InsertMovementReq;
import com.maan.insurance.model.req.reports.ReportsCommonReq;
import com.maan.insurance.service.impl.Dropdown.DropDownServiceImple;
import com.maan.insurance.service.impl.reports.ReportsServiceImple;
import com.maan.insurance.validation.CommonCalculation;
import com.maan.insurance.validation.Formatters;

@Service
public class ReportsValidation {
	final static DecimalFormat twoDigit = new DecimalFormat("###0.00");
	private Logger log = LogManager.getLogger(ReportsValidation.class);
	private Properties prop = new Properties();
	@Autowired
	private ReportsServiceImple impl;
	
	@Autowired
	private DropDownServiceImple dropDowmImpl;
	
	@Autowired
	private Formatters fm;
	
	private static boolean flag;
	
	@Autowired
	private CommonCalculation calcu;
	
	public ReportsValidation() {
		try {
			InputStream inputStream = getClass().getClassLoader()
					.getResourceAsStream("application_field_names.properties");
			if (inputStream != null) {
				prop.load(inputStream);
			}

		} catch (Exception e) {
			log.info(e);
		}
	}

	

	public List<ErrorCheck> getReportsCommonVali(ReportsCommonReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		if (StringUtils.isBlank(req.getDept())) {
			list.add(new ErrorCheck("Please Enter Dept", "Dept", "5"));
		}
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getLoginId())) {
			list.add(new ErrorCheck("Please Enter LoginId", "LoginId", "8"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "10"));
		}
		return list;
	}



	public List<ErrorCheck> getClaimRegisterListVali(GetClaimRegisterListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getLoginId())) {
			list.add(new ErrorCheck("Please Enter LoginId", "LoginId", "8"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "10"));
		}
		return list;
	}



	public List<ErrorCheck> getInwardRetroMappingReportVali(GetInwardRetroMappingReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getLoginId())) {
			list.add(new ErrorCheck("Please Enter LoginId", "LoginId", "8"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "10"));
		}
		return list;
	}


	public List<ErrorCheck> getRetroInwardMappingReportVali(GetRetroInwardMappingReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "1"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "2"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "3"));
		}
		return list;
	}



	public List<ErrorCheck> getTransactionMasterReportVali(GetTransactionMasterReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		if (StringUtils.isBlank(req.getDocType())) {
			list.add(new ErrorCheck("Please Enter DocType", "DocType", "5"));
		}
		if (StringUtils.isBlank(req.getContractNo())) {
			list.add(new ErrorCheck("Please Enter ContractNo", "ContractNo", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "10"));
		}
		return list;
	}



	public List<ErrorCheck> getDebtorsAgeingReportVali(GetDebtorsAgeingReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		if (StringUtils.isBlank(req.getDebFrom())) {
			list.add(new ErrorCheck("Please Enter DebFrom", "DebFrom", "5"));
		}
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getDebFrom1())) {
			list.add(new ErrorCheck("Please Enter DebFrom1", "DebFrom1", "8"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		if (StringUtils.isBlank(req.getDebFrom2())) {
			list.add(new ErrorCheck("Please Enter DebFrom2", "DebFrom2", "10"));
		}
		if (StringUtils.isBlank(req.getDebFrom4())) {
			list.add(new ErrorCheck("Please Enter DebFrom4", "DebFrom4", "11"));
		}
		if (StringUtils.isBlank(req.getDebTo())) {
			list.add(new ErrorCheck("Please Enter DebTo", "DebTo", "12"));
		}
		if (StringUtils.isBlank(req.getDebFrom5())) {
			list.add(new ErrorCheck("Please Enter DebFrom5", "DebFrom5", "13"));
		}
		if (StringUtils.isBlank(req.getDebTo1())) {
			list.add(new ErrorCheck("Please Enter DebTo1", "DebTo1", "14"));
		}
		if (StringUtils.isBlank(req.getDebTo2())) {
			list.add(new ErrorCheck("Please Enter DebTo2", "DebTo2", "15"));
		}
		if (StringUtils.isBlank(req.getDebTo3())) {
			list.add(new ErrorCheck("Please Enter DebTo3", "DebTo3", "16"));
		}
		if (StringUtils.isBlank(req.getDebTo4())) {
			list.add(new ErrorCheck("Please Enter DebTo4", "DebTo4", "17"));
		}
		if (StringUtils.isBlank(req.getDebTo5())) {
			list.add(new ErrorCheck("Please Enter DebTo5", "DebTo5", "18"));
		}
		if (StringUtils.isBlank(req.getType())) {
			list.add(new ErrorCheck("Please Enter Type", "Type", "19"));
		}
		if (StringUtils.isBlank(req.getDocType())) {
			list.add(new ErrorCheck("Please Enter DocType", "DocType", "20"));
		}
		return list;
	}



	public List<ErrorCheck> getMoveMntSummaryVali(GetMoveMntSummaryReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "1"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "2"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "3"));
		}
		return list;
	}



	public List<ErrorCheck> getallocationReportListVali(GetallocationReportListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		if (StringUtils.isBlank(req.getSettlementType())) {
			list.add(new ErrorCheck("Please Enter SettlementType", "SettlementType", "5"));
		}
		if (StringUtils.isBlank(req.getAllocateStatus())) {
			list.add(new ErrorCheck("Please Enter AllocateStatus", "AllocateStatus", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		
		return list;
	}



	public List<ErrorCheck> getPayRecRegisterListVali(GetPayRecRegisterListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		if (StringUtils.isBlank(req.getShowAllFields())) {
			list.add(new ErrorCheck("Please Enter ShowAllFields", "ShowAllFields", "5"));
		}
		if (StringUtils.isBlank(req.getPayrecType())) {
			list.add(new ErrorCheck("Please Enter PayrecType", "PayrecType", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getTransactionType())) {
			list.add(new ErrorCheck("Please Enter TransactionType", "TransactionType", "8"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		return list;
	}

	public List<ErrorCheck> getClaimPaidRegisterListVali(GetClaimPaidRegisterListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "6"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getLoginId())) {
			list.add(new ErrorCheck("Please Enter LoginId", "LoginId", "8"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "10"));
		}
		return list;
	}



	public List<ErrorCheck> getRetroRegisterListVali(GetRetroRegisterListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
	
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "10"));
		}
		return list;
	}

	public List<ErrorCheck> getTreatyWithdrawRegisterListVali(GetTreatyWithdrawRegisterListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getTreatyMainClass())) {
			list.add(new ErrorCheck("Please Enter TreatyMainClass", "TreatyMainClass", "2"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "3"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "4"));
		}
	
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		
		if (StringUtils.isBlank(req.getTreatyType())) {
			list.add(new ErrorCheck("Please Enter TreatyType", "TreatyType", "9"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "10"));
		}
		return list;
	}
	public List<ErrorCheck> getJVReportsVali(GetJVReportsReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getJournalType())) {
			list.add(new ErrorCheck("Please Enter JournalType", "JournalType", "3"));
		}
		if (StringUtils.isBlank(req.getStatus())) {
			list.add(new ErrorCheck("Please Enter Status", "Status", "4"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		return list;
	}



	public List<ErrorCheck> getInstallmentDueReportVali(GetInstallmentDueReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getAllocationYN())) {
			list.add(new ErrorCheck("Please Enter AllocationYN", "AllocationYN", "3"));
		}
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "4"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "7"));
		}
		if (StringUtils.isBlank(req.getBrokerId())) {
			list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "9"));
		}
		if (StringUtils.isBlank(req.getCedingId())) {
			list.add(new ErrorCheck("Please Enter CedingId", "CedingId", "8"));
		}
		return list;
	}

	public List<ErrorCheck> getPortfolioOutPendingReportVali(GetPortfolioOutPendingReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}	
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		return list;
	}



	public List<ErrorCheck> getTrailBalanceReportVali(GetTrailBalanceReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}	
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "9"));
		}
		return list;
	}



	public List<ErrorCheck> getUWStatisticsReportVali(GetUWStatisticsReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getContractNo())) {
			list.add(new ErrorCheck("Please Enter ContractNo", "ContractNo", "3"));
		}
		if (StringUtils.isBlank(req.getPeriodFrom())) {
			list.add(new ErrorCheck("Please Enter PeriodFrom", "PeriodFrom", "4"));
		}
		if (StringUtils.isBlank(req.getPeriodTo())) {
			list.add(new ErrorCheck("Please Enter PeriodTo", "PeriodTo", "5"));
		}
		if (StringUtils.isBlank(req.getType())) {
			list.add(new ErrorCheck("Please Enter Type", "Type", "6"));
		}
		if (StringUtils.isBlank(req.getUwFrom())) {
			list.add(new ErrorCheck("Please Enter UwFrom", "UwFrom", "7"));
		}
		if (StringUtils.isBlank(req.getUwTo())) {
			list.add(new ErrorCheck("Please Enter UwTo", "UwTo", "8"));
		}
		return list;
	}



	public List<ErrorCheck> getIEReportVali(GetIEReportReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getContractNo())) {
			list.add(new ErrorCheck("Please Enter ContractNo", "ContractNo", "3"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "4"));
		}
		if (StringUtils.isBlank(req.getLayerNo())) {
			list.add(new ErrorCheck("Please Enter LayerNo", "LayerNo", "5"));
		}
		if (StringUtils.isBlank(req.getProposalNo())) {
			list.add(new ErrorCheck("Please Enter ProposalNo", "ProposalNo", "6"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "7"));
		}
		return list;
	}



	public List<ErrorCheck> getColumnInfoListVali(GetColumnInfoListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getDept())) {
			list.add(new ErrorCheck("Please Enter Dept", "Dept", "3"));
		}
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "4"));
		}
		if (StringUtils.isBlank(req.getShowAllFields())) {
			list.add(new ErrorCheck("Please Enter ShowAllFields", "ShowAllFields", "5"));
		}
		if (StringUtils.isBlank(req.getTypeId())) {
			list.add(new ErrorCheck("Please Enter TypeId", "TypeId", "6"));
		}
		return list;
	}



	public List<ErrorCheck> getMoveMentPageVali(GetMoveMentPageReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getAccountDate())) {
			list.add(new ErrorCheck("Please Enter AccountDate", "AccountDate", "3"));
		}
		if (StringUtils.isBlank(req.getAccper())) {
			list.add(new ErrorCheck("Please Enter Accper", "Accper", "4"));
		}
		if (StringUtils.isBlank(req.getDisplay())) {
			list.add(new ErrorCheck("Please Enter Display", "Display", "5"));
		}
		if (StringUtils.isBlank(req.getMovementType())) {
			list.add(new ErrorCheck("Please Enter MovementType", "MovementType", "6"));
		}
		if (StringUtils.isBlank(req.getMovId())) {
			list.add(new ErrorCheck("Please Enter MovId", "MovId", "7"));
		}
		return list;
	}

	public List<ErrorCheck> getJournalListCrntVali(GetJournalListCrntReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getAccountDate())) {
			list.add(new ErrorCheck("Please Enter AccountDate", "AccountDate", "3"));
		}
		if (StringUtils.isBlank(req.getMovementId())) {
			list.add(new ErrorCheck("Please Enter MovementId", "MovementId", "4"));
		}
		if (StringUtils.isBlank(req.getTypeId())) {
			list.add(new ErrorCheck("Please Enter TypeId", "TypeId", "5"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "6"));
		}
		return list;
	}
	public List<ErrorCheck> validateProBookedPremium(CallProBookedPremiumReq bean) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if(StringUtils.isBlank(bean.getReportDate()))
			list.add(new ErrorCheck(prop.getProperty("Please select Report Date"), "Report Date","01"));		
		if(StringUtils.isBlank(bean.getProduct()))
			list.add(new ErrorCheck(prop.getProperty("Please select Product"), "Product","01"));
		if(StringUtils.isBlank(bean.getMovementType()))
			list.add(new ErrorCheck(prop.getProperty("Please select Movement Type"), "Movement","01"));
		return list;
	}
	public List<ErrorCheck> getPipelineMovementJvDetailsVali(GetPipelineMovementJvDetailsReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getCurrencyId())) {
			list.add(new ErrorCheck("Please Enter CurrencyId", "CurrencyId", "3"));
		}
		if (StringUtils.isBlank(req.getMovementId())) {
			list.add(new ErrorCheck("Please Enter MovementId", "MovementId", "4"));
		}
		if (StringUtils.isBlank(req.getProductId())) {
			list.add(new ErrorCheck("Please Enter ProductId", "ProductId", "5"));
		}
		if (StringUtils.isBlank(req.getSpc())) {
			list.add(new ErrorCheck("Please Enter Spc", "Spc", "6"));
		}
		if (StringUtils.isBlank(req.getUwYear())) {
			list.add(new ErrorCheck("Please Enter UwYear", "UwYear", "7"));
		}
		return list;
	}



	public List<ErrorCheck> getViewAllVali(GetViewAllReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getAccountDate())) {
			list.add(new ErrorCheck("Please Enter AccountDate", "AccountDate", "3"));
		}
		if (StringUtils.isBlank(req.getAccountPeriod())) {
			list.add(new ErrorCheck("Please Enter AccountPeriod", "AccountPeriod", "4"));
		}
		if (StringUtils.isBlank(req.getAccper())) {
			list.add(new ErrorCheck("Please Enter Accper", "Accper", "5"));
		}
		if (StringUtils.isBlank(req.getDisplay())) {
			list.add(new ErrorCheck("Please Enter Display", "Display", "6"));
		}
		if (StringUtils.isBlank(req.getMovId())) {
			list.add(new ErrorCheck("Please Enter MovId", "MovId", "7"));
		}
		if (StringUtils.isBlank(req.getSNo())) {
			list.add(new ErrorCheck("Please Enter SNo", "SNo", "8"));
		}
		return list;
	}

	public List<ErrorCheck> getCLInsertMovement(InsertCLMovementReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		final Validation val=new Validation();
		if(val.checkDate(req.getEndDate()).equalsIgnoreCase("INVALID")){
			list.add(new ErrorCheck(prop.getProperty("errors.report.movRepAsOn"),"movRepAsOn","01"));
		}
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "3"));
		}
		if (StringUtils.isBlank(req.getMovementType())) {
			list.add(new ErrorCheck("Please Enter MovementType", "MovementType", "4"));
		}
		return list;
	}



	public List<ErrorCheck> getInsertMovement(InsertMovementReq bean) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if(Integer.parseInt(bean.getAccper())==0){
			flag=true;
			list.add(new ErrorCheck(prop.getProperty("error.accountingQtr"),"accountingQtr","01"));	
		}
		if(Integer.parseInt(bean.getAccountDate())==0){
			flag=true;
			list.add(new ErrorCheck(prop.getProperty("error.accountingYr"),"accountingYr","01"));		
		}
		String date="";
		if("1".equals(bean.getAccper())) {
			date="31/03";
		}else if("2".equals(bean.getAccper())){
			date="30/06";
		}else if("3".equals(bean.getAccper())){
			date="30/09";
		}else if("4".equals(bean.getAccper())){
			date="31/12";
		}
		if(date.length()>0){
			date=date+"/"+bean.getAccountDate();
			if(Validation.ValidateTwo(dropDowmImpl.getSysDate(),date).equalsIgnoreCase("")){
				flag=true;
				list.add(new ErrorCheck(prop.getProperty("errors.movmentDateInvalid"),"","01"));	
			}
		}
		if((!flag)&&"F".equalsIgnoreCase(bean.getMovementType())&&((impl.getCountMovementRecord(bean.getBranchCode(),bean.getAccper(),bean.getAccountDate()))>0)){
			list.add(new ErrorCheck(prop.getProperty("error.movmentExists"),"","01"));	
		}
		return list;
	}



	public List<ErrorCheck> getViewJurnelAllVali(GetViewJurnelAllReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getAccountDate())) {
			list.add(new ErrorCheck("Please Enter AccountDate", "AccountDate", "3"));
		}
		if (StringUtils.isBlank(req.getAccountPeriod())) {
			list.add(new ErrorCheck("Please Enter AccountPeriod", "AccountPeriod", "4"));
		}
		if (StringUtils.isBlank(req.getDisplay())) {
			list.add(new ErrorCheck("Please Enter Display", "Display", "6"));
		}
		if (StringUtils.isBlank(req.getMovId())) {
			list.add(new ErrorCheck("Please Enter MovId", "MovId", "7"));
		}
		if (StringUtils.isBlank(req.getSNo())) {
			list.add(new ErrorCheck("Please Enter SNo", "SNo", "8"));
		}
		return list;
	}


	public List<ErrorCheck> financeStagingListVali(FinanceStagingListReq req) {
		List<ErrorCheck> list = new ArrayList<ErrorCheck>();
		if (StringUtils.isBlank(req.getBranchCode())) {
			list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "2"));
		}
		if (StringUtils.isBlank(req.getEndDate())) {
			list.add(new ErrorCheck("Please Enter EndDate", "EndDate", "3"));
		}
		if (StringUtils.isBlank(req.getPost())) {
			list.add(new ErrorCheck("Please Enter Post", "Post", "4"));
		}
		if (StringUtils.isBlank(req.getStartDate())) {
			list.add(new ErrorCheck("Please Enter StartDate", "StartDate", "6"));
		}
		return null;
	}
}
