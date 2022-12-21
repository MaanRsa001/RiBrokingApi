package com.maan.insurance.service.reports;

import org.springframework.stereotype.Service;

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
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
import com.maan.insurance.model.res.proportionality.GetTransactionMasterReportRes;
import com.maan.insurance.model.res.reports.FinanceStagingListRes;
import com.maan.insurance.model.res.reports.GetBookedPremiumDetailsRes;
import com.maan.insurance.model.res.reports.GetBookedPremiumInitRes;
import com.maan.insurance.model.res.reports.GetBookedUprDetailsRes;
import com.maan.insurance.model.res.reports.GetBookedUprInitRes;
import com.maan.insurance.model.res.reports.GetClaimJournelInitRes;
import com.maan.insurance.model.res.reports.GetClaimMoveMentInitRes;
import com.maan.insurance.model.res.reports.GetClaimMoveMentPageRes;
import com.maan.insurance.model.res.reports.GetClaimPaidRegisterListRes;
import com.maan.insurance.model.res.reports.GetClaimRegisterListRes;
import com.maan.insurance.model.res.reports.GetColumnInfoListRes;
import com.maan.insurance.model.res.reports.GetCompanyInfoListRes;
import com.maan.insurance.model.res.reports.GetDebtorsAgeingReportRes;
import com.maan.insurance.model.res.reports.GetIEReportRes;
import com.maan.insurance.model.res.reports.GetInstallmentDueReportRes;
import com.maan.insurance.model.res.reports.GetInwardRetroMappingReportRes;
import com.maan.insurance.model.res.reports.GetInwardRetroMappingReportRes1;
import com.maan.insurance.model.res.reports.GetJVReportsRes;
import com.maan.insurance.model.res.reports.GetJournalListCrntRes;
import com.maan.insurance.model.res.reports.GetJournelMoveMentInitRes;
import com.maan.insurance.model.res.reports.GetJournelPageRes;
import com.maan.insurance.model.res.reports.GetMoveMentInitRes;
import com.maan.insurance.model.res.reports.GetMoveMentPageRes;
import com.maan.insurance.model.res.reports.GetMoveMntSummaryRes;
import com.maan.insurance.model.res.reports.GetPayRecRegisterListRes;
import com.maan.insurance.model.res.reports.GetPendingOffersListRes;
import com.maan.insurance.model.res.reports.GetPipelineMovementJvDetailsRes;
import com.maan.insurance.model.res.reports.GetPipelineWrittenInitRes;
import com.maan.insurance.model.res.reports.GetPipelinedWrtnDetailsRes;
import com.maan.insurance.model.res.reports.GetPolicyRegisterListRes;
import com.maan.insurance.model.res.reports.GetPortfolioOutPendingReportRes;
import com.maan.insurance.model.res.reports.GetPptyOSLRClaimReportRes;
import com.maan.insurance.model.res.reports.GetPptySOAPendingReportRes;
import com.maan.insurance.model.res.reports.GetPremiumRegisterListRes;
import com.maan.insurance.model.res.reports.GetRenewalDueListRes;
import com.maan.insurance.model.res.reports.GetRetroInwardMappingReportRes;
import com.maan.insurance.model.res.reports.GetRetroQuarterlyReport;
import com.maan.insurance.model.res.reports.GetRetroRegisterListRes;
import com.maan.insurance.model.res.reports.GetTrailBalanceReportRes;
import com.maan.insurance.model.res.reports.GetTreatyWithdrawRegisterListRes;
import com.maan.insurance.model.res.reports.GetUWStatisticsReportRes;
import com.maan.insurance.model.res.reports.GetViewAllRes;
import com.maan.insurance.model.res.reports.GetViewJurnelAllRes;
import com.maan.insurance.model.res.reports.GetallocationReportListRes;
import com.maan.insurance.model.res.reports.ReportsCommonRes;
import com.maan.insurance.model.res.retro.CommonSaveRes;

@Service
public interface ReportsService {

	GetMoveMentInitRes getMoveMentInit(String branchCode);

	GetClaimMoveMentInitRes getClaimMoveMentInit(String branchCode);

	GetClaimJournelInitRes getClaimJournelInit(String branchCode);

	GetBookedUprInitRes getBookedUprInit(String branchCode);

	GetBookedPremiumInitRes getBookedPremiumInit(String branchCode);

	GetPipelineWrittenInitRes getPipelineWrittenInit(String branchCode);

	GetCommonDropDownRes getProductDropDown(String branchCode, String typeId);

	GetCedingCompanyRes getCedingCompany(String branchCode, String productId);

	CommonSaveRes getReportName(String typeId, String productId);

	GetPendingOffersListRes getPendingOffersList(ReportsCommonReq req);

	GetPolicyRegisterListRes getPolicyRegisterList(ReportsCommonReq req);

	GetPremiumRegisterListRes getPremiumRegisterList(ReportsCommonReq req);

	GetClaimRegisterListRes getClaimRegisterList(GetClaimRegisterListReq req);

	GetRenewalDueListRes getRenewalDueList(ReportsCommonReq req);

	GetRetroQuarterlyReport getRetroQuarterlyReport(ReportsCommonReq req);

	GetInwardRetroMappingReportRes getInwardRetroMappingReport(GetInwardRetroMappingReportReq req);

	GetRetroInwardMappingReportRes getRetroInwardMappingReport(GetRetroInwardMappingReportReq req);

	GetTransactionMasterReportRes getTransactionMasterReport(GetTransactionMasterReportReq req);

	GetDebtorsAgeingReportRes getDebtorsAgeingReport(GetDebtorsAgeingReportReq req);

	GetMoveMntSummaryRes getMoveMntSummary(GetMoveMntSummaryReq req);

	GetallocationReportListRes getallocationReportList(GetallocationReportListReq req);

	GetPayRecRegisterListRes getPayRecRegisterList(GetPayRecRegisterListReq req);

	GetClaimPaidRegisterListRes getClaimPaidRegisterList(GetClaimPaidRegisterListReq req);

	GetRetroRegisterListRes getRetroRegisterList(GetRetroRegisterListReq req);

	GetTreatyWithdrawRegisterListRes getTreatyWithdrawRegisterList(GetTreatyWithdrawRegisterListReq req);

	GetJVReportsRes getJVReports(GetJVReportsReq req);

	GetInstallmentDueReportRes getInstallmentDueReport(GetInstallmentDueReportReq req);

	GetPortfolioOutPendingReportRes getPortfolioOutPendingReport(GetPortfolioOutPendingReportReq req);

	GetTrailBalanceReportRes getTrailBalanceReport(GetTrailBalanceReportReq req);

	GetPptySOAPendingReportRes getPptySOAPendingReport(GetTrailBalanceReportReq req);

	GetUWStatisticsReportRes getUWStatisticsReport(GetUWStatisticsReportReq req);

	GetIEReportRes getIEReport(GetIEReportReq req);

	GetPptyOSLRClaimReportRes getPptyOSLRClaimReport(GetTrailBalanceReportReq req);

	GetCompanyInfoListRes getCompanyInfoList();

	GetColumnInfoListRes getColumnInfoList(GetColumnInfoListReq req);

	GetMoveMentPageRes getMoveMentPage(GetMoveMentPageReq req);

	GetJournalListCrntRes getJournalListCrnt(GetJournalListCrntReq req);

	CommonSaveRes callProBookedPremium(CallProBookedPremiumReq req);

	CommonSaveRes callProBookedUpr(CallProBookedPremiumReq req);

	CommonSaveRes callPipelineMoveJv(CallProBookedPremiumReq req);

	CommonSaveRes callProPipelineWritten(CallProBookedPremiumReq req);

	GetPipelineMovementJvDetailsRes getPipelineMovementJvDetails(GetPipelineMovementJvDetailsReq req);

	GetBookedPremiumDetailsRes getBookedPremiumDetails(GetPipelineMovementJvDetailsReq req);

	GetBookedUprDetailsRes getBookedUprDetails(GetPipelineMovementJvDetailsReq req);

	GetPipelinedWrtnDetailsRes getPipelinedWrtnDetails(GetPipelineMovementJvDetailsReq req);

	GetViewAllRes getViewAll(GetViewAllReq req);

	GetClaimMoveMentPageRes getClaimMoveMentPage(String branchCode, String movId);

	GetJournelPageRes getJournelPage(String branchCode, String movId);

	CommonSaveRes insertCLMovement(InsertCLMovementReq req);

	CommonSaveRes insertMovement(InsertMovementReq req);

	GetViewJurnelAllRes getViewJurnelAll(GetViewJurnelAllReq req);

	CommonSaveRes journelInsertMovement(InsertMovementReq req);

	GetJournelMoveMentInitRes getJournelMoveMentInit(String branchCode);

	FinanceStagingListRes financeStagingList(FinanceStagingListReq req);

}
