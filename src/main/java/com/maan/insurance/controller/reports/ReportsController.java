package com.maan.insurance.controller.reports;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.maan.insurance.error.CommonValidationException;
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
import com.maan.insurance.service.reports.ReportsService;
import com.maan.insurance.validation.reports.ReportsValidation;

@RestController
@RequestMapping("/Insurance/reports")
public class ReportsController {
	Gson gson = new Gson();
	
	@Autowired
	private ReportsService serv;
	@Autowired
	private ReportsValidation val;
	
	@PostMapping("/getPendingOffersList")
	public GetPendingOffersListRes getPendingOffersList(@RequestBody ReportsCommonReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getReportsCommonVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPendingOffersList(req);
	}
	
	@GetMapping("/getMoveMentInit/{branchCode}")
	public GetMoveMentInitRes getMoveMentInit(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
		return serv.getMoveMentInit(branchCode);
		} 
	@GetMapping("/getClaimMoveMentInit/{branchCode}")
	public GetClaimMoveMentInitRes getClaimMoveMentInit(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
		return serv.getClaimMoveMentInit(branchCode);
		} 
	@GetMapping("/getClaimJournelInit/{branchCode}")
	public GetClaimJournelInitRes getClaimJournelInit(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
		return serv.getClaimJournelInit(branchCode);
		} 
	@GetMapping("/getBookedUprInit/{branchCode}")
	public GetBookedUprInitRes getBookedUprInit(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
		return serv.getBookedUprInit(branchCode);
		} 
	@GetMapping("/getBookedPremiumInit/{branchCode}")
	public GetBookedPremiumInitRes getBookedPremiumInit(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
		return serv.getBookedPremiumInit(branchCode);
		} 	
	@GetMapping("/getPipelineWrittenInit/{branchCode}")
	public GetPipelineWrittenInitRes getPipelineWrittenInit(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
		return serv.getPipelineWrittenInit(branchCode);
		} 
	@GetMapping("/getProductDropDown/{branchCode}/{typeId}")
	public GetCommonDropDownRes getProductDropDown(@PathVariable ("branchCode") String branchCode,@PathVariable ("typeId") String typeId) throws CommonValidationException {
		return serv.getProductDropDown(branchCode,typeId);
		} 			
	@GetMapping("/getCedingCompany/{branchCode}/{productId}")
	public GetCedingCompanyRes getCedingCompany(@PathVariable ("branchCode") String branchCode,@PathVariable ("productId") String productId) throws CommonValidationException {
		return serv.getCedingCompany(branchCode,productId);
		}
	@GetMapping("/getReportName/{typeId}/{productId}")
	public CommonSaveRes getReportName(@PathVariable ("typeId") String typeId,@PathVariable ("productId") String productId) throws CommonValidationException {
		return serv.getReportName(typeId,productId);
		}
	@PostMapping("/getPolicyRegisterList")
	public GetPolicyRegisterListRes getPolicyRegisterList(@RequestBody ReportsCommonReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getReportsCommonVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPolicyRegisterList(req);
	} 
	@PostMapping("/getPremiumRegisterList")
	public GetPremiumRegisterListRes getPremiumRegisterList(@RequestBody ReportsCommonReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getReportsCommonVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPremiumRegisterList(req);
	} 
	@PostMapping("/getClaimRegisterList")
	public GetClaimRegisterListRes getClaimRegisterList(@RequestBody GetClaimRegisterListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getClaimRegisterListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getClaimRegisterList(req);
	}
	@PostMapping("/getRenewalDueList")
	public GetRenewalDueListRes getRenewalDueList(@RequestBody ReportsCommonReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getReportsCommonVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getRenewalDueList(req);
	} 
	@PostMapping("/getRetroQuarterlyReport")
	public GetRetroQuarterlyReport getRetroQuarterlyReport(@RequestBody ReportsCommonReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getReportsCommonVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getRetroQuarterlyReport(req);
	}
	@PostMapping("/getInwardRetroMappingReport")
	public GetInwardRetroMappingReportRes getInwardRetroMappingReport(@RequestBody GetInwardRetroMappingReportReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getInwardRetroMappingReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getInwardRetroMappingReport(req);
	} 
	@PostMapping("/getRetroInwardMappingReport")
	public GetRetroInwardMappingReportRes getRetroInwardMappingReport(@RequestBody GetRetroInwardMappingReportReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getRetroInwardMappingReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getRetroInwardMappingReport(req);
	}  
	@PostMapping("/getTransactionMasterReport")
	public GetTransactionMasterReportRes getTransactionMasterReport(@RequestBody GetTransactionMasterReportReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getTransactionMasterReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getTransactionMasterReport(req);
	}  
	@PostMapping("/getDebtorsAgeingReport")
	public GetDebtorsAgeingReportRes getDebtorsAgeingReport(@RequestBody GetDebtorsAgeingReportReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getDebtorsAgeingReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getDebtorsAgeingReport(req);
	}
	@PostMapping("/getMoveMntSummary")
	public GetMoveMntSummaryRes getMoveMntSummary(@RequestBody GetMoveMntSummaryReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getMoveMntSummaryVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getMoveMntSummary(req);
	} 
	@PostMapping("/getallocationReportList")
	public GetallocationReportListRes getallocationReportList(@RequestBody GetallocationReportListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getallocationReportListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getallocationReportList(req);
	}  
	@PostMapping("/getPayRecRegisterList")
	public GetPayRecRegisterListRes getPayRecRegisterList(@RequestBody GetPayRecRegisterListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getPayRecRegisterListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPayRecRegisterList(req);
	} 
	@PostMapping("/getClaimPaidRegisterList")
	public GetClaimPaidRegisterListRes getClaimPaidRegisterList(@RequestBody GetClaimPaidRegisterListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getClaimPaidRegisterListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getClaimPaidRegisterList(req);
	}  
	@PostMapping("/getRetroRegisterList")
	public GetRetroRegisterListRes getRetroRegisterList(@RequestBody GetRetroRegisterListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getRetroRegisterListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getRetroRegisterList(req);
	} 
	@PostMapping("/getTreatyWithdrawRegisterList")
	public GetTreatyWithdrawRegisterListRes getTreatyWithdrawRegisterList(@RequestBody GetTreatyWithdrawRegisterListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getTreatyWithdrawRegisterListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getTreatyWithdrawRegisterList(req);
	} 
	@PostMapping("/getJVReports")
	public GetJVReportsRes getJVReports(@RequestBody GetJVReportsReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getJVReportsVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getJVReports(req);
	}  
	@PostMapping("/getInstallmentDueReport")
	public GetInstallmentDueReportRes getInstallmentDueReport(@RequestBody GetInstallmentDueReportReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getInstallmentDueReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getInstallmentDueReport(req);
	}   
	@PostMapping("/getPortfolioOutPendingReport")
	public GetPortfolioOutPendingReportRes getPortfolioOutPendingReport(@RequestBody GetPortfolioOutPendingReportReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getPortfolioOutPendingReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPortfolioOutPendingReport(req);
	} 
	@PostMapping("/getTrailBalanceReport")
	public GetTrailBalanceReportRes getTrailBalanceReport(@RequestBody GetTrailBalanceReportReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getTrailBalanceReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getTrailBalanceReport(req);
	} 
	@PostMapping("/getPptySOAPendingReport")
	public GetPptySOAPendingReportRes getPptySOAPendingReport(@RequestBody GetTrailBalanceReportReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getTrailBalanceReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPptySOAPendingReport(req);
	} 
	@PostMapping("/getUWStatisticsReport")
	public GetUWStatisticsReportRes getUWStatisticsReport(@RequestBody GetUWStatisticsReportReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getUWStatisticsReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getUWStatisticsReport(req);
	} 
	@PostMapping("/getIEReport")
	public GetIEReportRes getIEReport(@RequestBody GetIEReportReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getIEReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getIEReport(req);
	}
	@PostMapping("/getPptyOSLRClaimReport")
	public GetPptyOSLRClaimReportRes getPptyOSLRClaimReport(@RequestBody GetTrailBalanceReportReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getTrailBalanceReportVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPptyOSLRClaimReport(req);
	}   
	@GetMapping("/getCompanyInfoList")
	public GetCompanyInfoListRes getCompanyInfoList() throws CommonValidationException {
		return serv.getCompanyInfoList();
		} 
	@PostMapping("/getColumnInfoList")
	public GetColumnInfoListRes getColumnInfoList(@RequestBody GetColumnInfoListReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getColumnInfoListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getColumnInfoList(req);
	}
	@PostMapping("/getMoveMentPage")
	public GetMoveMentPageRes getMoveMentPage(@RequestBody GetMoveMentPageReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getMoveMentPageVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getMoveMentPage(req);
	} 
	@PostMapping("/getJournalListCrnt")
	public GetJournalListCrntRes getJournalListCrnt(@RequestBody GetJournalListCrntReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getJournalListCrntVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getJournalListCrnt(req);
	} 
	@PostMapping("/callProBookedPremium")
	public CommonSaveRes callProBookedPremium(@RequestBody CallProBookedPremiumReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.validateProBookedPremium(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.callProBookedPremium(req);
	}  
	@PostMapping("/callProBookedUpr")
	public CommonSaveRes callProBookedUpr(@RequestBody CallProBookedPremiumReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.validateProBookedPremium(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.callProBookedUpr(req);
	} 
	@PostMapping("/callPipelineMoveJv")
	public CommonSaveRes callPipelineMoveJv(@RequestBody CallProBookedPremiumReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.validateProBookedPremium(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.callPipelineMoveJv(req);
	}
	@PostMapping("/callProPipelineWritten")
	public CommonSaveRes callProPipelineWritten(@RequestBody CallProBookedPremiumReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.validateProBookedPremium(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.callProPipelineWritten(req);
	}
	@PostMapping("/getPipelineMovementJvDetails")
	public GetPipelineMovementJvDetailsRes getPipelineMovementJvDetails(@RequestBody GetPipelineMovementJvDetailsReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getPipelineMovementJvDetailsVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPipelineMovementJvDetails(req);
	} 
	@PostMapping("/getBookedPremiumDetails")
	public GetBookedPremiumDetailsRes getBookedPremiumDetails(@RequestBody GetPipelineMovementJvDetailsReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getPipelineMovementJvDetailsVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getBookedPremiumDetails(req);
	}  
	@PostMapping("/getBookedUprDetails")
	public GetBookedUprDetailsRes getBookedUprDetails(@RequestBody GetPipelineMovementJvDetailsReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getPipelineMovementJvDetailsVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getBookedUprDetails(req);
	} 
	@PostMapping("/getPipelinedWrtnDetails")
	public GetPipelinedWrtnDetailsRes getPipelinedWrtnDetails(@RequestBody GetPipelineMovementJvDetailsReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getPipelineMovementJvDetailsVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPipelinedWrtnDetails(req);
	} 
	@PostMapping("/getViewAll")
	public GetViewAllRes getViewAll(@RequestBody GetViewAllReq req) throws CommonValidationException {
		List<ErrorCheck> error = val.getViewAllVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getViewAll(req);
	}
	@GetMapping("/getClaimMoveMentPage/{branchCode}/{movId}")
	public GetClaimMoveMentPageRes getClaimMoveMentPage(@PathVariable ("branchCode") String branchCode,@PathVariable ("movId") String movId) throws CommonValidationException {
		return serv.getClaimMoveMentPage(branchCode,movId);
		}  
	@GetMapping("/getJournelPage/{branchCode}/{movId}")
	public GetJournelPageRes getJournelPage(@PathVariable ("branchCode") String branchCode,@PathVariable ("movId") String movId) throws CommonValidationException {
		return serv.getJournelPage(branchCode,movId);
		}
		@PostMapping("/insertCLMovement")
		public CommonSaveRes insertCLMovement(@RequestBody InsertCLMovementReq req) throws CommonValidationException {
			List<ErrorCheck> error = val.getCLInsertMovement(req);
			if(error!=null && error.size()>0) {
				throw new CommonValidationException("error",error);
			}
			return serv.insertCLMovement(req);
		}		
		@PostMapping("/insertMovement")
		public CommonSaveRes insertMovement(@RequestBody InsertMovementReq req) throws CommonValidationException {
			List<ErrorCheck> error = val.getInsertMovement(req);
			if(error!=null && error.size()>0) {
				throw new CommonValidationException("error",error);
			}
			return serv.insertMovement(req);
		} 
		@PostMapping("/getViewJurnelAll")
		public GetViewJurnelAllRes getViewJurnelAll(@RequestBody GetViewJurnelAllReq req) throws CommonValidationException {
			List<ErrorCheck> error = val.getViewJurnelAllVali(req);
			if(error!=null && error.size()>0) {
				throw new CommonValidationException("error",error);
			}
			return serv.getViewJurnelAll(req);
		}  
		@PostMapping("/journelInsertMovement")
		public CommonSaveRes journelInsertMovement(@RequestBody InsertMovementReq req) throws CommonValidationException {
			List<ErrorCheck> error = val.getInsertMovement(req);
			if(error!=null && error.size()>0) {
				throw new CommonValidationException("error",error);
			}
			return serv.journelInsertMovement(req);
		} 
		@GetMapping("/getJournelMoveMentInit/{branchCode}")
		public GetJournelMoveMentInitRes getJournelMoveMentInit(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
			return serv.getJournelMoveMentInit(branchCode);
			} 
		@PostMapping("/financeStagingList")
		public FinanceStagingListRes financeStagingList(@RequestBody FinanceStagingListReq req) throws CommonValidationException {
			List<ErrorCheck> error = val.financeStagingListVali(req);
			if(error!=null && error.size()>0) {
				throw new CommonValidationException("error",error);
			}
			return serv.financeStagingList(req);
		} 
} 
