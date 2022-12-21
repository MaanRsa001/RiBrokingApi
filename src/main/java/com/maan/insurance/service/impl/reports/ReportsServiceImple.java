package com.maan.insurance.service.impl.reports;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maan.insurance.model.entity.TtrnFinanceStaging;
import com.maan.insurance.model.repository.TtrnFinanceStagingRepository;
import com.maan.insurance.model.repository.TtrnRiskDetailsRepository;
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
import com.maan.insurance.model.res.DropDown.CommonResDropDown;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes1;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
import com.maan.insurance.model.res.proportionality.GetRetroInwardMappingReportRes1;
import com.maan.insurance.model.res.proportionality.GetTransactionMasterReportRes;
import com.maan.insurance.model.res.proportionality.GetTransactionMasterReportRes1;
import com.maan.insurance.model.res.reports.FinanceStagingListRes;
import com.maan.insurance.model.res.reports.FinanceStagingListRes1;
import com.maan.insurance.model.res.reports.GetBookedPremiumDetailsRes;
import com.maan.insurance.model.res.reports.GetBookedPremiumDetailsRes1;
import com.maan.insurance.model.res.reports.GetBookedPremiumInitRes;
import com.maan.insurance.model.res.reports.GetBookedPremiumInitRes1;
import com.maan.insurance.model.res.reports.GetBookedUprDetailsRes;
import com.maan.insurance.model.res.reports.GetBookedUprDetailsRes1;
import com.maan.insurance.model.res.reports.GetBookedUprInitRes;
import com.maan.insurance.model.res.reports.GetBookedUprInitRes1;
import com.maan.insurance.model.res.reports.GetClaimJournelInitRes;
import com.maan.insurance.model.res.reports.GetClaimJournelInitRes1;
import com.maan.insurance.model.res.reports.GetClaimMoveMentInitRes;
import com.maan.insurance.model.res.reports.GetClaimMoveMentInitRes1;
import com.maan.insurance.model.res.reports.GetClaimMoveMentPageRes;
import com.maan.insurance.model.res.reports.GetClaimMoveMentPageRes1;
import com.maan.insurance.model.res.reports.GetClaimPaidRegisterListRes;
import com.maan.insurance.model.res.reports.GetClaimPaidRegisterListRes1;
import com.maan.insurance.model.res.reports.GetClaimRegisterListRes;
import com.maan.insurance.model.res.reports.GetClaimRegisterListRes1;
import com.maan.insurance.model.res.reports.GetColumnInfoListRes;
import com.maan.insurance.model.res.reports.GetColumnInfoListRes1;
import com.maan.insurance.model.res.reports.GetCompanyInfoListRes;
import com.maan.insurance.model.res.reports.GetCompanyInfoListRes1;
import com.maan.insurance.model.res.reports.GetDebtorsAgeingReportRes;
import com.maan.insurance.model.res.reports.GetDebtorsAgeingReportRes1;
import com.maan.insurance.model.res.reports.GetIEReportRes;
import com.maan.insurance.model.res.reports.GetIEReportRes1;
import com.maan.insurance.model.res.reports.GetInstallmentDueReportRes;
import com.maan.insurance.model.res.reports.GetInstallmentDueReportRes1;
import com.maan.insurance.model.res.reports.GetInwardRetroMappingReportRes;
import com.maan.insurance.model.res.reports.GetInwardRetroMappingReportRes1;
import com.maan.insurance.model.res.reports.GetJVReportsRes;
import com.maan.insurance.model.res.reports.GetJVReportsRes1;
import com.maan.insurance.model.res.reports.GetJournalListCrntRes;
import com.maan.insurance.model.res.reports.GetJournalListCrntRes1;
import com.maan.insurance.model.res.reports.GetJournelMoveMentInitRes;
import com.maan.insurance.model.res.reports.GetJournelMoveMentInitRes1;
import com.maan.insurance.model.res.reports.GetJournelPageRes;
import com.maan.insurance.model.res.reports.GetJournelPageRes1;
import com.maan.insurance.model.res.reports.GetMoveMentInitRes;
import com.maan.insurance.model.res.reports.GetMoveMentInitRes1;
import com.maan.insurance.model.res.reports.GetMoveMentPageRes;
import com.maan.insurance.model.res.reports.GetMoveMentPageRes1;
import com.maan.insurance.model.res.reports.GetMoveMntSummaryRes;
import com.maan.insurance.model.res.reports.GetMoveMntSummaryRes1;
import com.maan.insurance.model.res.reports.GetPayRecRegisterListRes;
import com.maan.insurance.model.res.reports.GetPayRecRegisterListRes1;
import com.maan.insurance.model.res.reports.GetPendingOffersListRes;
import com.maan.insurance.model.res.reports.GetPendingOffersListRes1;
import com.maan.insurance.model.res.reports.GetPipelineMovementJvDetailsRes;
import com.maan.insurance.model.res.reports.GetPipelineMovementJvDetailsRes1;
import com.maan.insurance.model.res.reports.GetPipelineWrittenInitRes;
import com.maan.insurance.model.res.reports.GetPipelineWrittenInitRes1;
import com.maan.insurance.model.res.reports.GetPipelinedWrtnDetailsRes;
import com.maan.insurance.model.res.reports.GetPipelinedWrtnDetailsRes1;
import com.maan.insurance.model.res.reports.GetPolicyRegisterListRes;
import com.maan.insurance.model.res.reports.GetPolicyRegisterListRes1;
import com.maan.insurance.model.res.reports.GetPolicyRegisterListRes2;
import com.maan.insurance.model.res.reports.GetPolicyRegisterListRes4;
import com.maan.insurance.model.res.reports.GetPolicyRegisterListResponse;
import com.maan.insurance.model.res.reports.GetPortfolioOutPendingReportRes;
import com.maan.insurance.model.res.reports.GetPortfolioOutPendingReportRes1;
import com.maan.insurance.model.res.reports.GetPptyOSLRClaimReportRes;
import com.maan.insurance.model.res.reports.GetPptyOSLRClaimReportRes1;
import com.maan.insurance.model.res.reports.GetPptySOAPendingReportRes;
import com.maan.insurance.model.res.reports.GetPptySOAPendingReportRes1;
import com.maan.insurance.model.res.reports.GetPremiumRegisterListComRes;
import com.maan.insurance.model.res.reports.GetPremiumRegisterListRes;
import com.maan.insurance.model.res.reports.GetPremiumRegisterListRes1;
import com.maan.insurance.model.res.reports.GetPremiumRegisterListRes2;
import com.maan.insurance.model.res.reports.GetPremiumRegisterListRes3;
import com.maan.insurance.model.res.reports.GetPremiumRegisterListRes4;
import com.maan.insurance.model.res.reports.GetRenewalDueListComRes;
import com.maan.insurance.model.res.reports.GetRenewalDueListRes;
import com.maan.insurance.model.res.reports.GetRenewalDueListRes1;
import com.maan.insurance.model.res.reports.GetRenewalDueListRes2;
import com.maan.insurance.model.res.reports.GetRetroInwardMappingReportRes;
import com.maan.insurance.model.res.reports.GetRetroQuarterlyReport;
import com.maan.insurance.model.res.reports.GetRetroQuarterlyReport1;
import com.maan.insurance.model.res.reports.GetRetroRegisterListRes;
import com.maan.insurance.model.res.reports.GetRetroRegisterListRes1;
import com.maan.insurance.model.res.reports.GetTrailBalanceReportRes;
import com.maan.insurance.model.res.reports.GetTrailBalanceReportRes1;
import com.maan.insurance.model.res.reports.GetTreatyWithdrawRegisterListRes;
import com.maan.insurance.model.res.reports.GetTreatyWithdrawRegisterListRes1;
import com.maan.insurance.model.res.reports.GetUWStatisticsReportRes;
import com.maan.insurance.model.res.reports.GetUWStatisticsReportRes1;
import com.maan.insurance.model.res.reports.GetViewAllRes;
import com.maan.insurance.model.res.reports.GetViewAllRes1;
import com.maan.insurance.model.res.reports.GetViewJurnelAllRes;
import com.maan.insurance.model.res.reports.GetViewJurnelAllRes1;
import com.maan.insurance.model.res.reports.GetallocationReportListRes;
import com.maan.insurance.model.res.reports.GetallocationReportListRes1;
import com.maan.insurance.model.res.reports.ReportsCommonRes;
import com.maan.insurance.model.res.retro.CommonSaveRes;
import com.maan.insurance.service.impl.QueryImplemention;
import com.maan.insurance.service.impl.Dropdown.DropDownServiceImple;
import com.maan.insurance.service.impl.claim.ClaimServiceImple;
import com.maan.insurance.service.impl.jasper.JasperConfiguration;
import com.maan.insurance.service.reports.ReportsService;
import com.maan.insurance.validation.Formatters;
import com.maan.insurance.validation.Claim.ValidationImple;

import oracle.jdbc.OracleTypes;


@Service
public class ReportsServiceImple implements ReportsService {
	private Logger logger = LogManager.getLogger(ReportsServiceImple.class);
	@Autowired
	private QueryImplemention queryImpl;
	
	@Autowired
	private DropDownServiceImple dropDowmImpl;

	@Autowired
	private Formatters fm;
	
	@Autowired
	private ValidationImple vi;
	
	@Autowired
	private TtrnRiskDetailsRepository rdRepo;
	
	@PersistenceContext
	private EntityManager em;
	@Autowired
	private JasperConfiguration config;
	@Autowired
	private TtrnFinanceStagingRepository finRepo;
	
	
	private Properties prop = new Properties();
	private Query query1=null;

	  public ReportsServiceImple() {
	        try {
	        	InputStream  inputStream = getClass().getClassLoader().getResourceAsStream("OracleQuery.properties");
	        	if (inputStream != null) {
					prop.load(inputStream);
				}
	        	
	        } catch (Exception e) {
	            logger.info(e);
	        }
	    }


	@Override
	public GetMoveMentInitRes getMoveMentInit(String branchCode) {
		GetMoveMentInitRes response = new GetMoveMentInitRes();
		List<GetMoveMentInitRes1> resList = new ArrayList<GetMoveMentInitRes1>();
		try{
			List<Map<String, Object>> list = queryImpl.selectList("report.select.moveMentInit", new String[] {branchCode,branchCode,branchCode});
			if(list.size()>0) {
				for(Map<String, Object> data : list) {
					GetMoveMentInitRes1 res = new GetMoveMentInitRes1();
					res.setAccountPeriod(data.get("ACCOUNT_PERIOD_DATE")==null?"":data.get("ACCOUNT_PERIOD_DATE").toString());
					res.setAccountPeriodDate(data.get("ACCOUNT_PERIOD_DATE")==null?"":data.get("ACCOUNT_PERIOD_DATE").toString());	
					res.setAccountPeriodQtr(data.get("ACCOUNT_PERIOD_QTR")==null?"":data.get("ACCOUNT_PERIOD_QTR").toString());
					res.setDeptName(data.get("DEPT_NAME")==null?"":data.get("DEPT_NAME").toString());
					res.setDetailName(data.get("DETAIL_NAME")==null?"":data.get("DETAIL_NAME").toString());
					res.setMovmentTranid(data.get("MOVMENT_TRANID")==null?"":data.get("MOVMENT_TRANID").toString());
					res.setProductName(data.get("PRODUCT_NAME")==null?"":data.get("PRODUCT_NAME").toString());
					res.setReportType(data.get("REPORT_TYPE")==null?"":data.get("REPORT_TYPE").toString());
					resList.add(res);
					}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
		logger.debug("Exception @ {" + e + "}");
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
	}

	@Override
	public GetClaimMoveMentInitRes getClaimMoveMentInit(String branchCode) {
		GetClaimMoveMentInitRes response = new GetClaimMoveMentInitRes();
		List<GetClaimMoveMentInitRes1> resList = new ArrayList<GetClaimMoveMentInitRes1>();
		try{
			List<Map<String, Object>> list = queryImpl.selectList("report.select.clMoveMentInit", new String[] {branchCode});
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
				GetClaimMoveMentInitRes1 bean = new GetClaimMoveMentInitRes1();
				bean.setSno(tempMap.get("MOVEMENT_ID")==null?"":tempMap.get("MOVEMENT_ID").toString());
				bean.setAccountDate(tempMap.get("MOVEMENT_DT")==null?"":tempMap.get("MOVEMENT_DT").toString());
				bean.setMovementType(tempMap.get("REPORT_TYPE")==null?"Interim":tempMap.get("REPORT_TYPE").toString().equalsIgnoreCase("F")?"Final":"Intreim");
				resList.add(bean);
				}
			}	
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
		logger.debug("Exception @ {" + e + "}");
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
	}

	@Override
	public GetClaimJournelInitRes getClaimJournelInit(String branchCode) {
		GetClaimJournelInitRes response = new GetClaimJournelInitRes();
		List<GetClaimJournelInitRes1> resList = new ArrayList<GetClaimJournelInitRes1>();
		try{
			List<Map<String, Object>> list = queryImpl.selectList("report.select.clJournalInit", new String[] {branchCode});
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
				GetClaimJournelInitRes1 bean = new GetClaimJournelInitRes1();
				bean.setSno(tempMap.get("MOVMENT_TRANID")==null?"":tempMap.get("MOVMENT_TRANID").toString());
				bean.setAccountPeriod(tempMap.get("DETAIL_NAME")==null?"":tempMap.get("DETAIL_NAME").toString());
				bean.setAccper(tempMap.get("ACCOUNT_PERIOD_QTR")==null?"":tempMap.get("ACCOUNT_PERIOD_QTR").toString());
				bean.setAccountDate(tempMap.get("ACCOUNT_PERIOD")==null?"":tempMap.get("ACCOUNT_PERIOD").toString());
				bean.setMovementType(tempMap.get("REPORT_TYPE")==null?"Interim":tempMap.get("REPORT_TYPE").toString().equalsIgnoreCase("F")?"Final":"Intreim");
				resList.add(bean);
			}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
		logger.debug("Exception @ {" + e + "}");
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
	}

	@Override
	public GetBookedUprInitRes getBookedUprInit(String branchCode) {
		GetBookedUprInitRes response = new GetBookedUprInitRes();
		List<GetBookedUprInitRes1> resList = new ArrayList<GetBookedUprInitRes1>();
		try{
			List<Map<String, Object>> list = queryImpl.selectList("BOOKED_UPR_INIT", new String[] {branchCode});
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetBookedUprInitRes1 bean = new GetBookedUprInitRes1();		
				bean.setSno(tempMap.get("MOVEMENT_ID")==null?"":tempMap.get("MOVEMENT_ID").toString());
				bean.setUWYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setReportDate(tempMap.get("REPORT_DATE")==null?"":tempMap.get("REPORT_DATE").toString());
				bean.setMovementType(tempMap.get("MOVEMENT_TYPE")==null?"Interim":tempMap.get("MOVEMENT_TYPE").toString().equalsIgnoreCase("F")?"Final":"Intreim");
				resList.add(bean);
			}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
		logger.debug("Exception @ {" + e + "}");
		e.printStackTrace();	
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
	}

	@Override
	public GetBookedPremiumInitRes getBookedPremiumInit(String branchCode) { 
		GetBookedPremiumInitRes response = new GetBookedPremiumInitRes();
		List<GetBookedPremiumInitRes1> resList = new ArrayList<GetBookedPremiumInitRes1>();
		try{
			List<Map<String, Object>> list = queryImpl.selectList("BOOKED_PREMIUM_INIT", new String[] {branchCode});
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetBookedPremiumInitRes1 bean = new GetBookedPremiumInitRes1();		
				bean.setSno(tempMap.get("MOVEMENT_ID")==null?"":tempMap.get("MOVEMENT_ID").toString());
				bean.setUWYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setReportDate(tempMap.get("REPORT_DATE")==null?"":tempMap.get("REPORT_DATE").toString());
				bean.setMovementType(tempMap.get("MOVEMENT_TYPE")==null?"Interim":tempMap.get("MOVEMENT_TYPE").toString().equalsIgnoreCase("F")?"Final":"Intreim");
				resList.add(bean);
			}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
		logger.debug("Exception @ {" + e + "}");
		e.printStackTrace();	
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
	}

	@Override
	public GetPipelineWrittenInitRes getPipelineWrittenInit(String branchCode) { 
		GetPipelineWrittenInitRes response = new GetPipelineWrittenInitRes();
		List<GetPipelineWrittenInitRes1> resList = new ArrayList<GetPipelineWrittenInitRes1>();
		try{
			List<Map<String, Object>> list = queryImpl.selectList("PIPELIEN_WRITTEN_INIT", new String[] {branchCode});
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetPipelineWrittenInitRes1 bean = new GetPipelineWrittenInitRes1();		
				bean.setSno(tempMap.get("MOVEMENT_ID")==null?"":tempMap.get("MOVEMENT_ID").toString());
				bean.setUWYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setReportDate(tempMap.get("REPORT_DATE")==null?"":tempMap.get("REPORT_DATE").toString());
				bean.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
				bean.setMovementType(tempMap.get("MOVEMENT_TYPE")==null?"Interim":tempMap.get("MOVEMENT_TYPE").toString().equalsIgnoreCase("F")?"Final":"Intreim");
				resList.add(bean);
			}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
		logger.debug("Exception @ {" + e + "}");
		e.printStackTrace();	
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
	}

	@Override
	public GetCommonDropDownRes getProductDropDown(String branchCode,String typeId) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		try{
			list = queryImpl.selectList("reportdao.select.getReportProductList",new String[]{typeId,branchCode});
			for(int i=0 ; i<list.size() ; i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				res.setCode(tempMap.get("TMAS_PRODUCT_ID")==null?"":tempMap.get("TMAS_PRODUCT_ID").toString());
				res.setCodeDescription(tempMap.get("TMAS_PRODUCT_NAME")==null?"":tempMap.get("TMAS_PRODUCT_NAME").toString());
				resList.add(res);
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}

	@Override
	public GetCedingCompanyRes getCedingCompany(String branchCode, String productId) {
		GetCedingCompanyRes response = new GetCedingCompanyRes();
		List<GetCedingCompanyRes1> resList = new ArrayList<GetCedingCompanyRes1>();
		String  cedingco="";
		try{
			List<Map<String,Object>> list = queryImpl.selectList("report.select.getCedingCompanyList",new String[]{branchCode,"C","Y",branchCode,productId});
			if(list!=null && list.size()>0){
				logger.info("List Size=>"+list.size());
				Map<String,Object> resMap;
				for(int i=0;i<list.size();i++){
					GetCedingCompanyRes1 res = new GetCedingCompanyRes1();
					resMap = (Map<String,Object>)list.get(i); 
					if(i==(list.size()-1)){
						cedingco+=resMap.get("CUSTOMER_ID").toString()+"~"+resMap.get("NAME").toString();
					}else{
						cedingco+=resMap.get("CUSTOMER_ID").toString()+"~"+resMap.get("NAME").toString()+"~";	
					}
					res.setCustomerId(resMap.get("CUSTOMER_ID")==null?"":resMap.get("CUSTOMER_ID").toString());
					res.setName(resMap.get("NAME")==null?"":resMap.get("NAME").toString());
					res.setCedingCo(cedingco);
					resList.add(res);
				} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
			}catch(Exception e){
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}

	@Override
	public CommonSaveRes getReportName(String typeId, String productId) {
		CommonSaveRes response = new CommonSaveRes();
		String reportName=null;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try{
			if("19".equals(typeId) || "21".equals(typeId)|| "11".equals(typeId) || "54".equals(typeId)){
				list=  queryImpl.selectList("report.select.reportname",new String[]{typeId,"Y"});
			}else{
				list=  queryImpl.selectList("report.select.getReportName",new String[]{productId,typeId,"Y"});
			}
			if (!CollectionUtils.isEmpty(list)) {
				reportName =list.get(0).get("TYPE_NAME") == null ? "": list.get(0).get("TYPE_NAME").toString();
			}
			response.setResponse(reportName);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}

	@Override
	public GetPendingOffersListRes getPendingOffersList(ReportsCommonReq bean) {
		GetPendingOffersListRes response = new GetPendingOffersListRes();
		List<GetPendingOffersListRes1> resList = new ArrayList<GetPendingOffersListRes1>();
		String query="";
		String[] obj= new String[0];
		String qutext = "";
		try {
		if("1".equalsIgnoreCase(bean.getProductId())){
			query = "reportdao.select.facgetQuoteRegisterList";
			  qutext = prop.getProperty(query);
			obj=new String[4];
			obj[0]=bean.getBranchCode();
			obj[1]=bean.getProductId();
			obj[2]=bean.getStartDate();
			obj[3]=bean.getEndDate();
			if(!"-1".equals(bean.getLoginId())){
				obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
			}
			if(!"-1".equals(bean.getBrokerId())){
				obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
			}
			if(!"-1".equals(bean.getCedingId())){
				obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
			}
			if(!"-1".equals(bean.getUwYear())){
				obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
				qutext += " " +prop.getProperty("report.select.reportUWYear");
			}
			qutext += " " +prop.getProperty("reportdao.select.facOrder");
		}else if("2".equalsIgnoreCase(bean.getProductId()) || "3".equalsIgnoreCase(bean.getProductId())){
			query= "reportdao.select.xolOrTreatygetQuoteRegisterList";
			  qutext = prop.getProperty(query);
			obj=new String[4];
			obj[0]=bean.getBranchCode();
			obj[1]=bean.getProductId();
			obj[2]=bean.getStartDate();
			obj[3]=bean.getEndDate();
			if(!"-1".equals(bean.getLoginId())){
				obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
			}
			if(!"-1".equals(bean.getBrokerId())){
				obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
			}
			if(!"-1".equals(bean.getCedingId())){
				obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
			}
			if(!"-1".equals(bean.getUwYear())){
				obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
				qutext += " " +prop.getProperty("report.select.reportUWYear");
			}
			qutext += " " +prop.getProperty("reportdao.select.facOrder");
		}
		else if("4".equalsIgnoreCase(bean.getProductId()) || "5".equalsIgnoreCase(bean.getProductId())){
			query="reportdao.select.deptNameRetroRetroxol";
			qutext = prop.getProperty(query);
			obj=new String[6];
			obj[0]=bean.getBranchCode();
			obj[1]=bean.getBranchCode();
			obj[2]=bean.getProductId();
			obj[3]=bean.getDept();
			obj[4]=bean.getStartDate();
			obj[5]=bean.getEndDate();
			logger.info("DeptName==>"+bean.getDept());
			if(!"-1".equals(bean.getLoginId())){
				obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
			}
			if(!"-1".equals(bean.getBrokerId())){
				obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
			}
			if(!"-1".equals(bean.getCedingId())){
				obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
			}
			if(!"-1".equals(bean.getUwYear())){
				obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
				qutext += " " +prop.getProperty("report.select.reportUWYear");
			}
			qutext += " " +prop.getProperty("reportdao.select.facOrder");
		}else{
			query= "reportdao.select.xolOrTreatygetQuoteRegisterList";
			qutext = prop.getProperty(query);
			obj=new String[4];
			obj[0]=bean.getBranchCode();
			obj[1]=bean.getProductId();
			obj[2]=bean.getStartDate();
			obj[3]=bean.getEndDate();
			if(!"-1".equals(bean.getLoginId())){
				obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
			}
			if(!"-1".equals(bean.getBrokerId())){
				obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
			}
			if(!"-1".equals(bean.getCedingId())){
				obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
				qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
			}
			if(!"-1".equals(bean.getUwYear())){
				obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
				qutext += " " +prop.getProperty("report.select.reportUWYear");
			}
			qutext += " " +prop.getProperty("reportdao.select.facOrder");
		}
		List<Map<String, Object>> list = new ArrayList<>();
    	query1 =queryImpl.setQueryProp(qutext, obj);
		query1.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		try {
			 list = query1.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} 
		if(list!=null && list.size()>0){
			for(Map<String, Object> tempMap : list) {
				GetPendingOffersListRes1 res = new GetPendingOffersListRes1();
				res.setAccDate(tempMap.get("ACC_DATE")==null?"":tempMap.get("ACC_DATE").toString());
				res.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
				res.setBrokerName(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
				res.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
				res.setExpiryDate(tempMap.get("EXP_DATE")==null?"":tempMap.get("EXP_DATE").toString());
				res.setInceptionDate(tempMap.get("INC_DATE")==null?"":tempMap.get("INC_DATE").toString());
				res.setInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
				res.setProposalNo(tempMap.get("RSK_PROPOSAL_NUMBER")==null?"":tempMap.get("RSK_PROPOSAL_NUMBER").toString());
				res.setProposalStatus(tempMap.get("PROPOSAL_STATUS")==null?"":tempMap.get("PROPOSAL_STATUS").toString());
				res.setShareSigned(tempMap.get("SHARE_SIGNED")==null?"":tempMap.get("SHARE_SIGNED").toString());
				res.setShareWritten(tempMap.get("SHARE_WRITTEN")==null?"":tempMap.get("SHARE_WRITTEN").toString());
				res.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
				res.setUnderWritter(tempMap.get("RSK_UNDERWRITTER")==null?"":tempMap.get("RSK_UNDERWRITTER").toString());
				res.setUWYear(tempMap.get("RSK_UWYEAR")==null?"":tempMap.get("RSK_UWYEAR").toString());
				resList.add(res);
		} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
			} catch (Exception e) {
			logger.debug("Exception @ {" + e + "}");
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}
	private String[] getIncObjectArray(String[] srcObj,String[] descObj){
		final String[] tempObj = new String[srcObj.length];
		System.arraycopy(srcObj, 0, tempObj, 0, srcObj.length);
		srcObj=new String[tempObj.length+descObj.length];
		System.arraycopy(tempObj, 0, srcObj, 0, tempObj.length);
		System.arraycopy(descObj, 0, srcObj, tempObj.length, descObj.length);
		return srcObj;
	}

	@Override
	public GetPolicyRegisterListRes getPolicyRegisterList(ReportsCommonReq bean) {
		GetPolicyRegisterListRes response = new GetPolicyRegisterListRes();
		GetPolicyRegisterListResponse comRes = new GetPolicyRegisterListResponse();
		ResultSet resultSet=null;
		List<GetPolicyRegisterListRes1> res1List = new ArrayList<GetPolicyRegisterListRes1>();
		List<GetPolicyRegisterListRes2> res2List = new ArrayList<GetPolicyRegisterListRes2>();
		List<GetPolicyRegisterListRes4> res4List = new ArrayList<GetPolicyRegisterListRes4>();
		String query="";
		 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 String[] obj= new String[0];
		 String qutext = "";
		 try {
		if("1".equalsIgnoreCase(bean.getProductId())){
		Connection connection=null;
		try {
			connection = config.getDataSourceForJasper().getConnection();
		} 
		catch (Exception e1) {
			e1.printStackTrace();
		} 
		try {
			// call PRC_FAC_POL_REG(?,?,?,?,?,?,?,?,?,?) not in DB (identifier 'PRC_FAC_POL_REG' must be declared) 140
			
			String runSP = "{call PRC_FAC_POL_REG(?,?,?,?,?,?,?,?,?,?)}";
			CallableStatement cs = connection.prepareCall(runSP);
			cs.setString(1, bean.getStartDate());
			cs.setString(2, bean.getEndDate());
			cs.setString(3, bean.getBranchCode());
			cs.setString(4, bean.getLoginId());
			cs.setString(5, bean.getBrokerId());
			cs.setString(6, bean.getCedingId());
			cs.setString(7, bean.getUwYear());
			cs.setString(8, "10");
			cs.setString(9, "1");

			cs.registerOutParameter(10, OracleTypes.CURSOR);

			cs.execute();

			resultSet = (ResultSet) cs.getObject(10);
			list = resultSetToArrayList(resultSet);
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetPolicyRegisterListRes1 res1 = new GetPolicyRegisterListRes1();
					   res1.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
			           res1.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
			           res1.setTmasPfcName(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString());
			           res1.setUnderwritter(tempMap.get("UNDERWRITTER")==null?"":tempMap.get("UNDERWRITTER").toString());
			           res1.setTmasDepartmentName(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
			           res1.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
			           res1.setRskUwyear(tempMap.get("RSK_UWYEAR")==null?"":tempMap.get("RSK_UWYEAR").toString());
			           res1.setMaximumLimitOc(tempMap.get("MAXIMUM_LIMIT_OC")==null?"":tempMap.get("MAXIMUM_LIMIT_OC").toString());
			           res1.setUwCapacityUgx(tempMap.get("UW_CAPACITY_UGX")==null?"":tempMap.get("UW_CAPACITY_UGX").toString());
			           res1.setTmasPolBranchName(tempMap.get("TMAS_POL_BRANCH_NAME")==null?"":tempMap.get("TMAS_POL_BRANCH_NAME").toString());
			           res1.setTerritoryDesc(tempMap.get("TERRITORY_DESC")==null?"":tempMap.get("TERRITORY_DESC").toString());
			          res1.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
			           res1.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
			           res1.setIncDate(tempMap.get("Inc_Date")==null?"":tempMap.get("Inc_Date").toString());
			           res1.setExpDate(tempMap.get("Exp_Date")==null?"":tempMap.get("Exp_Date").toString());
			           res1.setAccDate(tempMap.get("Acc_Date")==null?"":tempMap.get("Acc_Date").toString());
			           res1.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());
			           res1.setRskExchangeRate(tempMap.get("RSK_EXCHANGE_RATE")==null?"":tempMap.get("RSK_EXCHANGE_RATE").toString());
			           res1.setRskInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
			           res1.setRskCity(tempMap.get("RSK_CITY")==null?"":tempMap.get("RSK_CITY").toString());
			           res1.setRskLocation(tempMap.get("RSK_LOCATION")==null?"":tempMap.get("RSK_LOCATION").toString());
			           res1.setCoverLimitOc(tempMap.get("COVER_LIMIT_OC")==null?"":tempMap.get("COVER_LIMIT_OC").toString());
			           res1.setCoverLimitDc(tempMap.get("COVER_LIMIT_DC")==null?"":tempMap.get("COVER_LIMIT_DC").toString());
			           res1.setDeductibleOc(tempMap.get("DEDUCTIBLE_OC")==null?"":tempMap.get("DEDUCTIBLE_OC").toString());
			           res1.setDeductibleDc(tempMap.get("DEDUCTIBLE_DC")==null?"":tempMap.get("DEDUCTIBLE_DC").toString());
			           res1.setMLop(tempMap.get("M_LOP")==null?"":tempMap.get("M_LOP").toString());
			           res1.setALop(tempMap.get("A_LOP")==null?"":tempMap.get("A_LOP").toString());
			           res1.setRskCedretType(tempMap.get("RSK_CEDRET_TYPE")==null?"":tempMap.get("RSK_CEDRET_TYPE").toString());
			           res1.setRskCedantRetention(tempMap.get("RSK_CEDANT_RETENTION")==null?"":tempMap.get("RSK_CEDANT_RETENTION").toString());
			           res1.setRskPremiumRate(tempMap.get("RSK_PREMIUM_RATE")==null?"":tempMap.get("RSK_PREMIUM_RATE").toString());
			           res1.setSpRetro(tempMap.get("SP_RETRO")==null?"":tempMap.get("SP_RETRO").toString());
			           res1.setSpecficRetro(tempMap.get("SPECFIC_RETRO")==null?"":tempMap.get("SPECFIC_RETRO").toString());
			           res1.setSpecficRetroPerc(tempMap.get("SPECFIC_RETRO_PERC")==null?"":tempMap.get("SPECFIC_RETRO_PERC").toString());
			           res1.setTreatyRetro(tempMap.get("TREATY_RETRO")==null?"":tempMap.get("TREATY_RETRO").toString());
			           res1.setTreatyRetroPerc(tempMap.get("TREATY_RETRO_PERC")==null?"":tempMap.get("TREATY_RETRO_PERC").toString());
			           res1.setNoOfInstallment(tempMap.get("NO_OF_INSTALLMENT")==null?"":tempMap.get("NO_OF_INSTALLMENT").toString());
			           res1.setNoOfInsurers(tempMap.get("NO_OF_INSURERS")==null?"":tempMap.get("NO_OF_INSURERS").toString());
			           res1.setSiPml(tempMap.get("SI_PML")==null?"":tempMap.get("SI_PML").toString());
			           res1.setSiPmlDc(tempMap.get("SI_PML_DC")==null?"":tempMap.get("SI_PML_DC").toString());
			           res1.setPml(tempMap.get("PML")==null?"":tempMap.get("PML").toString());
			           res1.setSumInsuredOc(tempMap.get("SUM_INSURED_OC")==null?"":tempMap.get("SUM_INSURED_OC").toString());
			           res1.setPmlOc(tempMap.get("PML_OC")==null?"":tempMap.get("PML_OC").toString());
			           res1.setGwpiOc(tempMap.get("GWPI_OC")==null?"":tempMap.get("GWPI_OC").toString());
			           res1.setTplOc(tempMap.get("TPL_OC")==null?"":tempMap.get("TPL_OC").toString());
			           res1.setProposalStatus(tempMap.get("PROPOSAL_STATUS")==null?"":tempMap.get("PROPOSAL_STATUS").toString());
			           res1.setRskInterest(tempMap.get("RSK_INTEREST")==null?"":tempMap.get("RSK_INTEREST").toString());
			           res1.setShareWritten(tempMap.get("SHARE_WRITTEN")==null?"":tempMap.get("SHARE_WRITTEN").toString());
			           res1.setShareSigned(tempMap.get("SHARE_SIGNED")==null?"":tempMap.get("SHARE_SIGNED").toString());
			           res1.setSumInsuredOurShareOc(tempMap.get("SUM_INSURED_OUR_SHARE_OC")==null?"":tempMap.get("SUM_INSURED_OUR_SHARE_OC").toString());
			           res1.setSumInsuredOurShareDc(tempMap.get("SUM_INSURED_OUR_SHARE_DC")==null?"":tempMap.get("SUM_INSURED_OUR_SHARE_DC").toString());
			           res1.setPmlOurShareOc(tempMap.get("PML_OUR_SHARE_OC")==null?"":tempMap.get("PML_OUR_SHARE_OC").toString());
			           res1.setPmlOurShareDc(tempMap.get("PML_OUR_SHARE_DC")==null?"":tempMap.get("PML_OUR_SHARE_DC").toString());
			           res1.setGwpiOurShareOc(tempMap.get("GWPI_OUR_SHARE_OC")==null?"":tempMap.get("GWPI_OUR_SHARE_OC").toString());
			           res1.setGwpiOurShareDc(tempMap.get("GWPI_OUR_SHARE_DC")==null?"":tempMap.get("GWPI_OUR_SHARE_DC").toString());
			           res1.setTplOurShareOc(tempMap.get("TPL_OUR_SHARE_OC")==null?"":tempMap.get("TPL_OUR_SHARE_OC").toString());
			           res1.setTplOurShareDc(tempMap.get("TPL_OUR_SHARE_DC")==null?"":tempMap.get("TPL_OUR_SHARE_DC").toString());
			           res1.setGradeDesc(tempMap.get("GRADE_DESC")==null?"":tempMap.get("GRADE_DESC").toString());
			           res1.setOccupationCode(tempMap.get("OCCUPATION_CODE")==null?"":tempMap.get("OCCUPATION_CODE").toString());
			           res1.setRiskDetails(tempMap.get("RISK_DETAILS")==null?"":tempMap.get("RISK_DETAILS").toString());
			           res1.setFirePort(tempMap.get("FIRE_PORT")==null?"":tempMap.get("FIRE_PORT").toString());
			           res1.setScope(tempMap.get("SCOPE")==null?"":tempMap.get("SCOPE").toString());
			            res1.setMbInd(tempMap.get("MB_IND")==null?"":tempMap.get("MB_IND").toString());
			            res1.setCategoryZone(tempMap.get("CATEGORY_ZONE")==null?"":tempMap.get("CATEGORY_ZONE").toString());
			           res1.setEarthquakeWsInd(tempMap.get("EARTHQUAKE_WS_IND")==null?"":tempMap.get("EARTHQUAKE_WS_IND").toString());
			           res1.setEqThreat(tempMap.get("EQ_THREAT")==null?"":tempMap.get("EQ_THREAT").toString());
			           res1.setRskComm(tempMap.get("RSK_COMM")==null?"":tempMap.get("RSK_COMM").toString());
			           res1.setRskBrokerage(tempMap.get("RSK_BROKERAGE")==null?"":tempMap.get("RSK_BROKERAGE").toString());
			           res1.setRskTax(tempMap.get("RSK_TAX")==null?"":tempMap.get("RSK_TAX").toString());
			           res1.setRskOtherCost(tempMap.get("RSK_OTHER_COST")==null?"":tempMap.get("RSK_OTHER_COST").toString());
			           res1.setAccPercentage(tempMap.get("ACC_PERCENTAGE")==null?"":tempMap.get("ACC_PERCENTAGE").toString());
			           res1.setRskAcquistionCostOc(tempMap.get("RSK_ACQUISTION_COST_OC")==null?"":tempMap.get("RSK_ACQUISTION_COST_OC").toString());
			           res1.setCu(tempMap.get("CU")==null?"":tempMap.get("CU").toString());
			           res1.setCuRsn(tempMap.get("CU_RSN")==null?"":tempMap.get("CU_RSN").toString());
			           res1.setRskLossRecord(tempMap.get("RSK_LOSS_RECORD")==null?"":tempMap.get("RSK_LOSS_RECORD").toString());
			           res1.setRskRefToHo(tempMap.get("RSK_REF_TO_HO")==null?"":tempMap.get("RSK_REF_TO_HO").toString());
			           res1.setRskUwRecommendation(tempMap.get("RSK_UW_RECOMMENDATION")==null?"":tempMap.get("RSK_UW_RECOMMENDATION").toString());
			           res1.setRskRemarks(tempMap.get("RSK_REMARKS")==null?"":tempMap.get("RSK_REMARKS").toString());
			           res1.setRskOthAccep(tempMap.get("RSK_OTH_ACCEP")==null?"":tempMap.get("RSK_OTH_ACCEP").toString());
			           res1.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
			           res1.setBrokerId(tempMap.get("BROKER_ID")==null?"":tempMap.get("BROKER_ID").toString());
			           res1.setCedingCompanyId(tempMap.get("CEDING_COMPANY_ID")==null?"":tempMap.get("CEDING_COMPANY_ID").toString());
			           res1.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
			           res1.setDeptId(tempMap.get("DEPT_ID")==null?"":tempMap.get("DEPT_ID").toString());
			           res1.setSumInsuredDc(tempMap.get("SUM_INSURED_DC")==null?"":tempMap.get("SUM_INSURED_DC").toString());
			            res1.setTplDc(tempMap.get("TPL_DC")==null?"":tempMap.get("TPL_DC").toString());
			           res1.setGwpiDc(tempMap.get("GWPI_DC")==null?"":tempMap.get("GWPI_DC").toString());
			            res1.setPmlDc(tempMap.get("PML_DC")==null?"":tempMap.get("PML_DC").toString());
			           res1.setRskAcquistionCostDc(tempMap.get("RSK_ACQUISTION_COST_DC")==null?"":tempMap.get("RSK_ACQUISTION_COST_DC").toString());
			           res1.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
			           res1.setLimitPerVesselOc(tempMap.get("LIMIT_PER_VESSEL_OC")==null?"":tempMap.get("LIMIT_PER_VESSEL_OC").toString());
			           res1.setLimitPerVesselDc(tempMap.get("LIMIT_PER_VESSEL_DC")==null?"":tempMap.get("LIMIT_PER_VESSEL_DC").toString());
			           res1.setLimitPerLocationOc(tempMap.get("LIMIT_PER_LOCATION_OC")==null?"":tempMap.get("LIMIT_PER_LOCATION_OC").toString());
			           res1.setLimitPerLocationDc(tempMap.get("LIMIT_PER_LOCATION_DC")==null?"":tempMap.get("LIMIT_PER_LOCATION_DC").toString());
			           res1.setModeOfTransport(tempMap.get("MODE_OF_TRANSPORT")==null?"":tempMap.get("MODE_OF_TRANSPORT").toString());
			           res1.setVesselName(tempMap.get("VESSEL_NAME")==null?"":tempMap.get("VESSEL_NAME").toString());
			           res1.setVesselAge(tempMap.get("VESSEL_AGE")==null?"":tempMap.get("VESSEL_AGE").toString());
			           res1.setType(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString());
			           res1.setDeductibleOurshareOc(tempMap.get("DEDUCTIBLE_OURSHARE_OC")==null?"":tempMap.get("DEDUCTIBLE_OURSHARE_OC").toString());
			           res1.setDeductibleOurshareUgx(tempMap.get("DEDUCTIBLE_OURSHARE_UGX")==null?"":tempMap.get("DEDUCTIBLE_OURSHARE_UGX").toString());
			           res1.setCoverlimitOurshareOc(tempMap.get("COVERLIMIT_OURSHARE_OC")==null?"":tempMap.get("COVERLIMIT_OURSHARE_OC").toString());
			           res1.setCoverlimitOurshareUgx(tempMap.get("COVERLIMIT_OURSHARE_UGX")==null?"":tempMap.get("COVERLIMIT_OURSHARE_UGX").toString());
			           res1.setEndorsementDate(tempMap.get("ENDORSEMENT_DATE")==null?"":tempMap.get("ENDORSEMENT_DATE").toString());
			           res1.setRskBonusType(tempMap.get("RSK_BONUS_TYPE")==null?"":tempMap.get("RSK_BONUS_TYPE").toString());
			            res1.setRskBonusPer(tempMap.get("RSK_BONUS_PER")==null?"":tempMap.get("RSK_BONUS_PER").toString());
			           res1.setInwardBusType(tempMap.get("INWARD_BUS_TYPE")==null?"":tempMap.get("INWARD_BUS_TYPE").toString());
			           res1.setRskLatitude(tempMap.get("RSK_LATITUDE")==null?"":tempMap.get("RSK_LATITUDE").toString());
			           res1.setRskLongitude(tempMap.get("RSK_LONGITUDE")==null?"":tempMap.get("RSK_LONGITUDE").toString());
			           res1.setRskLocIssued(tempMap.get("RSK_LOC_ISSUED")==null?"":tempMap.get("RSK_LOC_ISSUED").toString());
			            res1.setRskVessalTonnage(tempMap.get("RSK_VESSAL_TONNAGE")==null?"":tempMap.get("RSK_VESSAL_TONNAGE").toString());
			            res1.setCoverLimitPmlOc(tempMap.get("COVER_LIMIT_PML_OC")==null?"":tempMap.get("COVER_LIMIT_PML_OC").toString());
			           res1.setRskReceiptPayement(tempMap.get("RSK_RECEIPT_PAYEMENT")==null?"":tempMap.get("RSK_RECEIPT_PAYEMENT").toString());
			           res1.setPslOc(tempMap.get("PSL_OC")==null?"":tempMap.get("PSL_OC").toString());
			           res1.setPllOc(tempMap.get("PLL_OC")==null?"":tempMap.get("PLL_OC").toString());
			           res1.setPblOc(tempMap.get("PBL_OC")==null?"":tempMap.get("PBL_OC").toString());
			           res1.setPslOsOc(tempMap.get("PSL_OS_OC")==null?"":tempMap.get("PSL_OS_OC").toString());
			           res1.setPllOsOc(tempMap.get("PLL_OS_OC")==null?"":tempMap.get("PLL_OS_OC").toString());
			           res1.setPblOsOc(tempMap.get("PBL_OS_OC")==null?"":tempMap.get("PBL_OS_OC").toString());
			           res1.setPslDc(tempMap.get("PSL_DC")==null?"":tempMap.get("PSL_DC").toString());
			           res1.setPllDc(tempMap.get("PLL_DC")==null?"":tempMap.get("PLL_DC").toString());
			           res1.setPblDc(tempMap.get("PBL_DC")==null?"":tempMap.get("PBL_DC").toString());
			           res1.setRskCreastaStatus(tempMap.get("RSK_CREASTA_STATUS")==null?"":tempMap.get("RSK_CREASTA_STATUS").toString());
			           res1.setRskExclusion(tempMap.get("RSK_EXCLUSION")==null?"":tempMap.get("RSK_EXCLUSION").toString());
			           res1.setRskLeadUw(tempMap.get("RSK_LEAD_UW")==null?"":tempMap.get("RSK_LEAD_UW").toString());
			           res1.setRskLeadUnderwriterCountry(tempMap.get("RSK_LEAD_UNDERWRITER_COUNTRY")==null?"":tempMap.get("RSK_LEAD_UNDERWRITER_COUNTRY").toString());
			           res1.setRskLeadUwShare(tempMap.get("RSK_LEAD_UW_SHARE")==null?"":tempMap.get("RSK_LEAD_UW_SHARE").toString());
			           res1.setPslOsDc(tempMap.get("PSL_OS_DC")==null?"":tempMap.get("PSL_OS_DC").toString());
			           res1.setPllOsDc(tempMap.get("PLL_OS_DC")==null?"":tempMap.get("PLL_OS_DC").toString());
			           res1.setCoverLimitPmlOsOc(tempMap.get("COVER_LIMIT_PML_OS_OC")==null?"":tempMap.get("COVER_LIMIT_PML_OS_OC").toString());
			           res1.setCoverLimitPmlDc(tempMap.get("COVER_LIMIT_PML_DC")==null?"":tempMap.get("COVER_LIMIT_PML_DC").toString());
			           res1.setCoverLimitPmlOsDc(tempMap.get("COVER_LIMIT_PML_OS_DC")==null?"":tempMap.get("COVER_LIMIT_PML_OS_DC").toString());
			           res1.setRetenPer(tempMap.get("RETEN_PER")==null?"":tempMap.get("RETEN_PER").toString());
			           res1.setRetroPer(tempMap.get("RETRO_PER")==null?"":tempMap.get("RETRO_PER").toString());
			           res1.setRskTerritory(tempMap.get("RSK_TERRITORY")==null?"":tempMap.get("RSK_TERRITORY").toString());
			           res1.setCountriesInclude(tempMap.get("COUNTRIES_INCLUDE")==null?"":tempMap.get("COUNTRIES_INCLUDE").toString());
			           res1.setCountriesExclude(tempMap.get("COUNTRIES_EXCLUDE")==null?"":tempMap.get("COUNTRIES_EXCLUDE").toString());
			           res1.setRskLocBnkName(tempMap.get("RSK_LOC_BNK_NAME")==null?"":tempMap.get("RSK_LOC_BNK_NAME").toString());
			           res1.setRskLocCrdtPrd(tempMap.get("RSK_LOC_CRDT_PRD")==null?"":tempMap.get("RSK_LOC_CRDT_PRD").toString());
			           res1.setRskLocCrdtAmt(tempMap.get("RSK_LOC_CRDT_AMT")==null?"":tempMap.get("RSK_LOC_CRDT_AMT").toString());
			           res1.setRskLocBenfcreName(tempMap.get("RSK_LOC_BENFCRE_NAME")==null?"":tempMap.get("RSK_LOC_BENFCRE_NAME").toString());
			           res1.setPblOsDc(tempMap.get("PBL_OS_DC")==null?"":tempMap.get("PBL_OS_DC").toString());
					res1List.add(res1);
				} 
				comRes.setIfProduct1(res1List);
			}
			response.setCommonResponse(comRes); //140 resp
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
        
		}else if("2".equalsIgnoreCase(bean.getProductId()) || "3".equalsIgnoreCase(bean.getProductId())){
			query = "report.select.policyRegPropotionalTreaty"; //207 response fields
			qutext = prop.getProperty(query);
			obj=new String[4];
			obj[0]=bean.getProductId();
			obj[1]=bean.getStartDate();
			obj[2]=bean.getEndDate();
			obj[3]=bean.getBranchCode();
			if(!"-1".equals(bean.getLoginId())){
				obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacLoginId");
			}
			if(!"-1".equals(bean.getBrokerId())){
				obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacBrokerId");
			}
			if(!"-1".equals(bean.getCedingId())){
				obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacCedingId");
			}
			if(!"-1".equals(bean.getUwYear())){
				obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
				qutext += " " +prop.getProperty("report.select.reportUWYear");
			}
			qutext += " " +prop.getProperty("report.select.policyRegFacOrderBy");
		}else if("4".equalsIgnoreCase(bean.getProductId())||"5".equalsIgnoreCase(bean.getProductId())){
			query = "report.select.retroPolicyReport"; //73 resp
			qutext = prop.getProperty(query);
			obj=new String[6];
			obj[0]=bean.getBranchCode();
			obj[1]=bean.getProductId();
			obj[2]=bean.getDept();
			obj[3]=bean.getStartDate();
			obj[4]=bean.getEndDate();
			obj[5]=bean.getBranchCode();
			if(!"-1".equals(bean.getLoginId())){
				obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacLoginId");
			}
			if(!"-1".equals(bean.getBrokerId())){
				obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacBrokerId");
			}
			if(!"-1".equals(bean.getCedingId())){
				obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacCedingId");
			}
			if(!"-1".equals(bean.getUwYear())){
				obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
				qutext += " " +prop.getProperty("report.select.reportUWYear");
			}
			qutext += " " +prop.getProperty("report.select.policyRegFacOrderBy");
		}
		query1 =queryImpl.setQueryProp(qutext, obj);
		query1.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
		try {
			 list = query1.getResultList();
		} catch(Exception e) {
			e.printStackTrace();
		} 
		if("2".equalsIgnoreCase(bean.getProductId()) || "3".equalsIgnoreCase(bean.getProductId())){
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetPolicyRegisterListRes2 res2 = new GetPolicyRegisterListRes2();
					 res2.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString()); 
					 res2.setCreaseStatus(tempMap.get("CREASE_STATUS")==null?"":tempMap.get("CREASE_STATUS").toString()); 
					 res2.setRskReinstateNo(tempMap.get("RSK_REINSTATE_NO")==null?"":tempMap.get("RSK_REINSTATE_NO").toString()); 
					 res2.setRskSladscaleComm(tempMap.get("RSK_SLADSCALE_COMM")==null?"":tempMap.get("RSK_SLADSCALE_COMM").toString()); 
					 res2.setRskLossPartCarridor(tempMap.get("RSK_LOSS_PART_CARRIDOR")==null?"":tempMap.get("RSK_LOSS_PART_CARRIDOR").toString()); 
					 res2.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString()); 
					 res2.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString()); 
					 res2.setTmasPfcName(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString()); 
					 res2.setUnderwritter(tempMap.get("UNDERWRITTER")==null?"":tempMap.get("UNDERWRITTER").toString()); 
					 res2.setTmasDepartmentName(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString()); 
					 res2.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString()); 
					 res2.setRskUwyear(tempMap.get("RSK_UWYEAR")==null?"":tempMap.get("RSK_UWYEAR").toString()); 
					 res2.setRskMaxLmtCover(tempMap.get("RSK_MAX_LMT_COVER")==null?"":tempMap.get("RSK_MAX_LMT_COVER").toString()); 
					 res2.setTmasPolBranchName(tempMap.get("TMAS_POL_BRANCH_NAME")==null?"":tempMap.get("TMAS_POL_BRANCH_NAME").toString()); 
					 res2.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString()); 
					 res2.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString()); 
					 res2.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString()); 
					 res2.setProposalType(tempMap.get("PROPOSAL_TYPE")==null?"":tempMap.get("PROPOSAL_TYPE").toString()); 
					 res2.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString()); 
					 res2.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString()); 
					 res2.setAccountDate(tempMap.get("ACCOUNT_DATE")==null?"":tempMap.get("ACCOUNT_DATE").toString()); 
					 res2.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString()); 
					 res2.setRskExchangeRate(tempMap.get("RSK_EXCHANGE_RATE")==null?"":tempMap.get("RSK_EXCHANGE_RATE").toString()); 
					 res2.setRskPeriodOfNotice(tempMap.get("RSK_PERIOD_OF_NOTICE")==null?"":tempMap.get("RSK_PERIOD_OF_NOTICE").toString()); 
					 res2.setRskRiskCovered(tempMap.get("RSK_RISK_COVERED")==null?"":tempMap.get("RSK_RISK_COVERED").toString()); 
					 res2.setRskTerritoryScope(tempMap.get("RSK_TERRITORY_SCOPE")==null?"":tempMap.get("RSK_TERRITORY_SCOPE").toString()); 
					 res2.setTerritoryDesc(tempMap.get("TERRITORY_DESC")==null?"":tempMap.get("TERRITORY_DESC").toString()); 
					 res2.setAccountingPeriod(tempMap.get("ACCOUNTING_PERIOD")==null?"":tempMap.get("ACCOUNTING_PERIOD").toString()); 
					 res2.setRskReceiptStatement(tempMap.get("RSK_RECEIPT_STATEMENT")==null?"":tempMap.get("RSK_RECEIPT_STATEMENT").toString()); 
					 res2.setRskReceiptPayement(tempMap.get("RSK_RECEIPT_PAYEMENT")==null?"":tempMap.get("RSK_RECEIPT_PAYEMENT").toString()); 
					 res2.setRskSpRetro(tempMap.get("RSK_SP_RETRO")==null?"":tempMap.get("RSK_SP_RETRO").toString()); 
					 res2.setRskNoOfInsurers(tempMap.get("RSK_NO_OF_INSURERS")==null?"":tempMap.get("RSK_NO_OF_INSURERS").toString()); 
					 res2.setRskLimitOc(tempMap.get("RSK_LIMIT_OC")==null?"":tempMap.get("RSK_LIMIT_OC").toString()); 
					 res2.setRskEpiOfferOc(tempMap.get("RSK_EPI_OFFER_OC")==null?"":tempMap.get("RSK_EPI_OFFER_OC").toString()); 
					 res2.setRskEpiEstimate(tempMap.get("RSK_EPI_ESTIMATE")==null?"":tempMap.get("RSK_EPI_ESTIMATE").toString()); 
					 res2.setRskEpiEstOc(tempMap.get("RSK_EPI_EST_OC")==null?"":tempMap.get("RSK_EPI_EST_OC").toString()); 
					 res2.setRskXlcostOc(tempMap.get("RSK_XLCOST_OC")==null?"":tempMap.get("RSK_XLCOST_OC").toString()); 
					 res2.setRskCedretType(tempMap.get("RSK_CEDRET_TYPE")==null?"":tempMap.get("RSK_CEDRET_TYPE").toString()); 
					 res2.setRskCedantRetention(tempMap.get("RSK_CEDANT_RETENTION")==null?"":tempMap.get("RSK_CEDANT_RETENTION").toString()); 
					 res2.setProposalStatus(tempMap.get("PROPOSAL_STATUS")==null?"":tempMap.get("PROPOSAL_STATUS").toString()); 
					 res2.setRskShareWritten(tempMap.get("RSK_SHARE_WRITTEN")==null?"":tempMap.get("RSK_SHARE_WRITTEN").toString()); 
					 res2.setRskShareSigned(tempMap.get("RSK_SHARE_SIGNED")==null?"":tempMap.get("RSK_SHARE_SIGNED").toString()); 
					 res2.setRskLimitOsOc(tempMap.get("RSK_LIMIT_OS_OC")==null?"":tempMap.get("RSK_LIMIT_OS_OC").toString()); 
					 res2.setRskLimitOsDc(tempMap.get("RSK_LIMIT_OS_DC")==null?"":tempMap.get("RSK_LIMIT_OS_DC").toString()); 
					 res2.setRskEpiOsofOc(tempMap.get("RSK_EPI_OSOF_OC")==null?"":tempMap.get("RSK_EPI_OSOF_OC").toString()); 
					 res2.setRskEpiOsofDc(tempMap.get("RSK_EPI_OSOF_DC")==null?"":tempMap.get("RSK_EPI_OSOF_DC").toString()); 
					 res2.setRskEpiOsoeOc(tempMap.get("RSK_EPI_OSOE_OC")==null?"":tempMap.get("RSK_EPI_OSOE_OC").toString()); 
					 res2.setRskEpiOsoeDc(tempMap.get("RSK_EPI_OSOE_DC")==null?"":tempMap.get("RSK_EPI_OSOE_DC").toString()); 
					 res2.setRskXlcostOsOc(tempMap.get("RSK_XLCOST_OS_OC")==null?"":tempMap.get("RSK_XLCOST_OS_OC").toString()); 
					 res2.setRskXlcostOsDc(tempMap.get("RSK_XLCOST_OS_DC")==null?"":tempMap.get("RSK_XLCOST_OS_DC").toString()); 
					 res2.setRskPremiumQuotaShare(tempMap.get("RSK_PREMIUM_QUOTA_SHARE")==null?"":tempMap.get("RSK_PREMIUM_QUOTA_SHARE").toString()); 
					 res2.setRskPremiumQuotaShareDc(tempMap.get("RSK_PREMIUM_QUOTA_SHARE_DC")==null?"":tempMap.get("RSK_PREMIUM_QUOTA_SHARE_DC").toString()); 
					 res2.setRskPremiumSurpuls(tempMap.get("RSK_PREMIUM_SURPULS")==null?"":tempMap.get("RSK_PREMIUM_SURPULS").toString()); 
					 res2.setRskPremiumSurpulsDc(tempMap.get("RSK_PREMIUM_SURPULS_DC")==null?"":tempMap.get("RSK_PREMIUM_SURPULS_DC").toString()); 
					 res2.setRskCommQuotashare(tempMap.get("RSK_COMM_QUOTASHARE")==null?"":tempMap.get("RSK_COMM_QUOTASHARE").toString()); 
					 res2.setRskCommSurplus(tempMap.get("RSK_COMM_SURPLUS")==null?"":tempMap.get("RSK_COMM_SURPLUS").toString()); 
					 res2.setCommissionQsAmount(tempMap.get("COMMISSION_QS_AMOUNT")==null?"":tempMap.get("COMMISSION_QS_AMOUNT").toString()); 
					 res2.setCommissionSurpAmount(tempMap.get("COMMISSION_SURP_AMOUNT")==null?"":tempMap.get("COMMISSION_SURP_AMOUNT").toString()); 
					 res2.setRskOverriderPerc(tempMap.get("RSK_OVERRIDER_PERC")==null?"":tempMap.get("RSK_OVERRIDER_PERC").toString()); 
					 res2.setRskBrokerage(tempMap.get("RSK_BROKERAGE")==null?"":tempMap.get("RSK_BROKERAGE").toString()); 
					 res2.setRskTax(tempMap.get("RSK_TAX")==null?"":tempMap.get("RSK_TAX").toString()); 
					 res2.setRskOtherCost(tempMap.get("RSK_OTHER_COST")==null?"":tempMap.get("RSK_OTHER_COST").toString()); 
					 res2.setOtherAcqCostAmount(tempMap.get("OTHER_ACQ_COST_AMOUNT")==null?"":tempMap.get("OTHER_ACQ_COST_AMOUNT").toString()); 
					 res2.setAcqCostAmount(tempMap.get("ACQ_COST_AMOUNT")==null?"":tempMap.get("ACQ_COST_AMOUNT").toString()); 
					 res2.setRskProfitComm(tempMap.get("RSK_PROFIT_COMM")==null?"":tempMap.get("RSK_PROFIT_COMM").toString()); 
					 res2.setRskPremiumReserve(tempMap.get("RSK_PREMIUM_RESERVE")==null?"":tempMap.get("RSK_PREMIUM_RESERVE").toString()); 
					 res2.setRskLossReserve(tempMap.get("RSK_LOSS_RESERVE")==null?"":tempMap.get("RSK_LOSS_RESERVE").toString()); 
					 res2.setRskInterest(tempMap.get("RSK_INTEREST")==null?"":tempMap.get("RSK_INTEREST").toString()); 
					 res2.setRskPfInoutLoss(tempMap.get("RSK_PF_INOUT_LOSS")==null?"":tempMap.get("RSK_PF_INOUT_LOSS").toString()); 
					 res2.setRskPfInoutPrem(tempMap.get("RSK_PF_INOUT_PREM")==null?"":tempMap.get("RSK_PF_INOUT_PREM").toString()); 
					 res2.setRskLossadvice(tempMap.get("RSK_LOSSADVICE")==null?"":tempMap.get("RSK_LOSSADVICE").toString()); 
					 res2.setRskCashlossLmtOc(tempMap.get("RSK_CASHLOSS_LMT_OC")==null?"":tempMap.get("RSK_CASHLOSS_LMT_OC").toString()); 
					 res2.setRskLeadUw(tempMap.get("RSK_LEAD_UW")==null?"":tempMap.get("RSK_LEAD_UW").toString()); 
					 res2.setRskLeadUwShare(tempMap.get("RSK_LEAD_UW_SHARE")==null?"":tempMap.get("RSK_LEAD_UW_SHARE").toString()); 
					 res2.setRskExclusion(tempMap.get("RSK_EXCLUSION")==null?"":tempMap.get("RSK_EXCLUSION").toString()); 
					 res2.setRskRemarks(tempMap.get("RSK_REMARKS")==null?"":tempMap.get("RSK_REMARKS").toString()); 
					 res2.setRskUwRecomm(tempMap.get("RSK_UW_RECOMM")==null?"":tempMap.get("RSK_UW_RECOMM").toString()); 
					 res2.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString()); 
					 res2.setBrokerId(tempMap.get("BROKER_ID")==null?"":tempMap.get("BROKER_ID").toString()); 
					 res2.setCedingCompanyId(tempMap.get("CEDING_COMPANY_ID")==null?"":tempMap.get("CEDING_COMPANY_ID").toString()); 
					 res2.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString()); 
					 res2.setRskBasis(tempMap.get("RSK_BASIS")==null?"":tempMap.get("RSK_BASIS").toString()); 
					 res2.setRskDeducOc(tempMap.get("RSK_DEDUC_OC")==null?"":tempMap.get("RSK_DEDUC_OC").toString()); 
					 res2.setRskSubjPremiumOc(tempMap.get("RSK_SUBJ_PREMIUM_OC")==null?"":tempMap.get("RSK_SUBJ_PREMIUM_OC").toString()); 
					 res2.setRskAdjrate(tempMap.get("RSK_ADJRATE")==null?"":tempMap.get("RSK_ADJRATE").toString()); 
					 res2.setRskMdPremOc(tempMap.get("RSK_MD_PREM_OC")==null?"":tempMap.get("RSK_MD_PREM_OC").toString()); 
					 res2.setMndInstallments(tempMap.get("MND_INSTALLMENTS")==null?"":tempMap.get("MND_INSTALLMENTS").toString()); 
					 res2.setRskMdPremOsOc(tempMap.get("RSK_MD_PREM_OS_OC")==null?"":tempMap.get("RSK_MD_PREM_OS_OC").toString()); 
					 res2.setRskMdPremOsDc(tempMap.get("RSK_MD_PREM_OS_DC")==null?"":tempMap.get("RSK_MD_PREM_OS_DC").toString()); 
					 res2.setRskAggregateLiabOc(tempMap.get("RSK_AGGREGATE_LIAB_OC")==null?"":tempMap.get("RSK_AGGREGATE_LIAB_OC").toString()); 
					 res2.setRskReinstateAddlPremPct(tempMap.get("RSK_REINSTATE_ADDL_PREM_PCT")==null?"":tempMap.get("RSK_REINSTATE_ADDL_PREM_PCT").toString()); 
					 res2.setRskReinstateAddlPremOc(tempMap.get("RSK_REINSTATE_ADDL_PREM_OC")==null?"":tempMap.get("RSK_REINSTATE_ADDL_PREM_OC").toString()); 
					 res2.setRskBurningCostPct(tempMap.get("RSK_BURNING_COST_PCT")==null?"":tempMap.get("RSK_BURNING_COST_PCT").toString()); 
					 res2.setRskAggregateLiabDc(tempMap.get("RSK_AGGREGATE_LIAB_DC")==null?"":tempMap.get("RSK_AGGREGATE_LIAB_DC").toString()); 
					 res2.setRskReinstateAddlPremDc(tempMap.get("RSK_REINSTATE_ADDL_PREM_DC")==null?"":tempMap.get("RSK_REINSTATE_ADDL_PREM_DC").toString()); 
					 res2.setRskAcquistionCostOc(tempMap.get("RSK_ACQUISTION_COST_OC")==null?"":tempMap.get("RSK_ACQUISTION_COST_OC").toString()); 
					 res2.setRskAcquistionCostDc(tempMap.get("RSK_ACQUISTION_COST_DC")==null?"":tempMap.get("RSK_ACQUISTION_COST_DC").toString()); 
					 res2.setRskSubjPremiumDc(tempMap.get("RSK_SUBJ_PREMIUM_DC")==null?"":tempMap.get("RSK_SUBJ_PREMIUM_DC").toString()); 
					 res2.setRskLimitDc(tempMap.get("RSK_LIMIT_DC")==null?"":tempMap.get("RSK_LIMIT_DC").toString()); 
					 res2.setRskDeducDc(tempMap.get("RSK_DEDUC_DC")==null?"":tempMap.get("RSK_DEDUC_DC").toString()); 
					 res2.setRskEpiEstDc(tempMap.get("RSK_EPI_EST_DC")==null?"":tempMap.get("RSK_EPI_EST_DC").toString()); 
					 res2.setRskXlpremDc(tempMap.get("RSK_XLPREM_DC")==null?"":tempMap.get("RSK_XLPREM_DC").toString()); 
					 res2.setRskMdPremDc(tempMap.get("RSK_MD_PREM_DC")==null?"":tempMap.get("RSK_MD_PREM_DC").toString()); 
					 res2.setRskPfCovered(tempMap.get("RSK_PF_COVERED")==null?"":tempMap.get("RSK_PF_COVERED").toString()); 
					 res2.setRskEpiOfferDc(tempMap.get("RSK_EPI_OFFER_DC")==null?"":tempMap.get("RSK_EPI_OFFER_DC").toString()); 
					 res2.setRskXlcostDc(tempMap.get("RSK_XLCOST_DC")==null?"":tempMap.get("RSK_XLCOST_DC").toString()); 
					 res2.setRskCashlossLmtDc(tempMap.get("RSK_CASHLOSS_LMT_DC")==null?"":tempMap.get("RSK_CASHLOSS_LMT_DC").toString()); 
					 res2.setLimitPerVesselOc(tempMap.get("LIMIT_PER_VESSEL_OC")==null?"":tempMap.get("LIMIT_PER_VESSEL_OC").toString()); 
					 res2.setLimitPerVesselDc(tempMap.get("LIMIT_PER_VESSEL_DC")==null?"":tempMap.get("LIMIT_PER_VESSEL_DC").toString()); 
					 res2.setLimitPerLocationOc(tempMap.get("LIMIT_PER_LOCATION_OC")==null?"":tempMap.get("LIMIT_PER_LOCATION_OC").toString()); 
					 res2.setLimitPerLocationDc(tempMap.get("LIMIT_PER_LOCATION_DC")==null?"":tempMap.get("LIMIT_PER_LOCATION_DC").toString()); 
					 res2.setTreatyRetro(tempMap.get("TREATY_RETRO")==null?"":tempMap.get("TREATY_RETRO").toString()); 
					 res2.setTreatyRetroPerc(tempMap.get("TREATY_RETRO_PERC")==null?"":tempMap.get("TREATY_RETRO_PERC").toString()); 
					 res2.setCommQsAmt(tempMap.get("COMM_QS_AMT")==null?"":tempMap.get("COMM_QS_AMT").toString()); 
					 res2.setCommSurplusAmt(tempMap.get("COMM_SURPLUS_AMT")==null?"":tempMap.get("COMM_SURPLUS_AMT").toString()); 
					 res2.setCommQsAmtDc(tempMap.get("COMM_QS_AMT_DC")==null?"":tempMap.get("COMM_QS_AMT_DC").toString()); 
					 res2.setCommSurplusAmtDc(tempMap.get("COMM_SURPLUS_AMT_DC")==null?"":tempMap.get("COMM_SURPLUS_AMT_DC").toString()); 
					 res2.setRskLossadviceDc(tempMap.get("RSK_LOSSADVICE_DC")==null?"":tempMap.get("RSK_LOSSADVICE_DC").toString()); 
					 res2.setRskCommQuotashareDc(tempMap.get("RSK_COMM_QUOTASHARE_DC")==null?"":tempMap.get("RSK_COMM_QUOTASHARE_DC").toString()); 
					 res2.setCommissionSurpAmountDc(tempMap.get("COMMISSION_SURP_AMOUNT_DC")==null?"":tempMap.get("COMMISSION_SURP_AMOUNT_DC").toString()); 
					 res2.setOtherAcqCostAmountDc(tempMap.get("OTHER_ACQ_COST_AMOUNT_DC")==null?"":tempMap.get("OTHER_ACQ_COST_AMOUNT_DC").toString()); 
					 res2.setEndorsementDate(tempMap.get("ENDORSEMENT_DATE")==null?"":tempMap.get("ENDORSEMENT_DATE").toString()); 
					 res2.setEgpniAsOffer(tempMap.get("EGPNI_AS_OFFER")==null?"":tempMap.get("EGPNI_AS_OFFER").toString()); 
					 res2.setOurassessment(tempMap.get("OURASSESSMENT")==null?"":tempMap.get("OURASSESSMENT").toString()); 
					 res2.setInwardBusType(tempMap.get("INWARD_BUS_TYPE")==null?"":tempMap.get("INWARD_BUS_TYPE").toString()); 
					 res2.setTreatytype(tempMap.get("TREATYTYPE")==null?"":tempMap.get("TREATYTYPE").toString()); 
					 res2.setRskEventLimitOc(tempMap.get("RSK_EVENT_LIMIT_OC")==null?"":tempMap.get("RSK_EVENT_LIMIT_OC").toString()); 
					 res2.setRskEventLimitDc(tempMap.get("RSK_EVENT_LIMIT_DC")==null?"":tempMap.get("RSK_EVENT_LIMIT_DC").toString()); 
					 res2.setRskAggregateLimitOc(tempMap.get("RSK_AGGREGATE_LIMIT_OC")==null?"":tempMap.get("RSK_AGGREGATE_LIMIT_OC").toString()); 
					 res2.setRskAggregateLimitDc(tempMap.get("RSK_AGGREGATE_LIMIT_DC")==null?"":tempMap.get("RSK_AGGREGATE_LIMIT_DC").toString()); 
					 res2.setRskOccurrentLimitOc(tempMap.get("RSK_OCCURRENT_LIMIT_OC")==null?"":tempMap.get("RSK_OCCURRENT_LIMIT_OC").toString()); 
					 res2.setRskOccurrentLimitDc(tempMap.get("RSK_OCCURRENT_LIMIT_DC")==null?"":tempMap.get("RSK_OCCURRENT_LIMIT_DC").toString()); 
					 res2.setRskTreatySurpLimitOc(tempMap.get("RSK_TREATY_SURP_LIMIT_OC")==null?"":tempMap.get("RSK_TREATY_SURP_LIMIT_OC").toString()); 
					 res2.setRskTreatySurpLimitDc(tempMap.get("RSK_TREATY_SURP_LIMIT_DC")==null?"":tempMap.get("RSK_TREATY_SURP_LIMIT_DC").toString()); 
					 res2.setRskTreatySurpLimitOsOc(tempMap.get("RSK_TREATY_SURP_LIMIT_OS_OC")==null?"":tempMap.get("RSK_TREATY_SURP_LIMIT_OS_OC").toString()); 
					 res2.setRskTreatySurpLimitOsDc(tempMap.get("RSK_TREATY_SURP_LIMIT_OS_DC")==null?"":tempMap.get("RSK_TREATY_SURP_LIMIT_OS_DC").toString()); 
					 res2.setRskPml(tempMap.get("RSK_PML")==null?"":tempMap.get("RSK_PML").toString()); 
					 res2.setRskPmlPercent(tempMap.get("RSK_PML_PERCENT")==null?"":tempMap.get("RSK_PML_PERCENT").toString()); 
					 res2.setRskCreastaStatus(tempMap.get("RSK_CREASTA_STATUS")==null?"":tempMap.get("RSK_CREASTA_STATUS").toString()); 
					 res2.setRskProNotes(tempMap.get("RSK_PRO_NOTES")==null?"":tempMap.get("RSK_PRO_NOTES").toString()); 
					 res2.setRskLocIssued(tempMap.get("RSK_LOC_ISSUED")==null?"":tempMap.get("RSK_LOC_ISSUED").toString()); 
					 res2.setRskPerilsCovered(tempMap.get("RSK_PERILS_COVERED")==null?"":tempMap.get("RSK_PERILS_COVERED").toString()); 
					 res2.setRskLeadUnderwriterCountry(tempMap.get("RSK_LEAD_UNDERWRITER_COUNTRY")==null?"":tempMap.get("RSK_LEAD_UNDERWRITER_COUNTRY").toString()); 
					 res2.setRetenPer(tempMap.get("RETEN_PER")==null?"":tempMap.get("RETEN_PER").toString()); 
					 res2.setRetroPer(tempMap.get("RETRO_PER")==null?"":tempMap.get("RETRO_PER").toString()); 
					 res2.setRskTerritory(tempMap.get("RSK_TERRITORY")==null?"":tempMap.get("RSK_TERRITORY").toString()); 
					 res2.setCountriesInclude(tempMap.get("COUNTRIES_INCLUDE")==null?"":tempMap.get("COUNTRIES_INCLUDE").toString()); 
					 res2.setCountriesExclude(tempMap.get("COUNTRIES_EXCLUDE")==null?"":tempMap.get("COUNTRIES_EXCLUDE").toString()); 
					 res2.setRskIncludeAcqCost(tempMap.get("RSK_INCLUDE_ACQ_COST")==null?"":tempMap.get("RSK_INCLUDE_ACQ_COST").toString()); 
					 res2.setRskOurAssAcqCost(tempMap.get("RSK_OUR_ASS_ACQ_COST")==null?"":tempMap.get("RSK_OUR_ASS_ACQ_COST").toString()); 
					 res2.setRskOurAcqOurShareOc(tempMap.get("RSK_OUR_ACQ_OUR_SHARE_OC")==null?"":tempMap.get("RSK_OUR_ACQ_OUR_SHARE_OC").toString()); 
					 res2.setRskNoOfLine(tempMap.get("RSK_NO_OF_LINE")==null?"":tempMap.get("RSK_NO_OF_LINE").toString()); 
					 res2.setRskRate(tempMap.get("RSK_RATE")==null?"":tempMap.get("RSK_RATE").toString()); 
					 res2.setRskProCommType(tempMap.get("RSK_PRO_COMM_TYPE")==null?"":tempMap.get("RSK_PRO_COMM_TYPE").toString()); 
					 res2.setRskProSupProCom(tempMap.get("RSK_PRO_SUP_PRO_COM")==null?"":tempMap.get("RSK_PRO_SUP_PRO_COM").toString()); 
					 res2.setRskProCommPer(tempMap.get("RSK_PRO_COMM_PER")==null?"":tempMap.get("RSK_PRO_COMM_PER").toString()); 
					 res2.setRskProSetUp(tempMap.get("RSK_PRO_SET_UP")==null?"":tempMap.get("RSK_PRO_SET_UP").toString()); 
					 res2.setRskProLossCaryType(tempMap.get("RSK_PRO_LOSS_CARY_TYPE")==null?"":tempMap.get("RSK_PRO_LOSS_CARY_TYPE").toString()); 
					 res2.setRskProLossCaryYear(tempMap.get("SK_PRO_LOSS_CARY_YEAR")==null?"":tempMap.get("RSK_PRO_LOSS_CARY_YEAR").toString()); 
					 res2.setRskProProfitCaryType(tempMap.get("RSK_PRO_PROFIT_CARY_TYPE")==null?"":tempMap.get("RSK_PRO_PROFIT_CARY_TYPE").toString()); 
					 res2.setRskProProfitCaryYear(tempMap.get("RSK_PRO_PROFIT_CARY_YEAR")==null?"":tempMap.get("RSK_PRO_PROFIT_CARY_YEAR").toString()); 
					 res2.setRskProFirstPfoCom(tempMap.get("RSK_PRO_FIRST_PFO_COM")==null?"":tempMap.get("RSK_PRO_FIRST_PFO_COM").toString()); 
					 res2.setRskProFirstPfoComPrd(tempMap.get("RSK_PRO_FIRST_PFO_COM_PRD")==null?"":tempMap.get("RSK_PRO_FIRST_PFO_COM_PRD").toString()); 
					 res2.setRskProSubPfoComPrd(tempMap.get("RSK_PRO_SUB_PFO_COM_PRD")==null?"":tempMap.get("RSK_PRO_SUB_PFO_COM_PRD").toString()); 
					 res2.setRskProSubPfoCom(tempMap.get("RSK_PRO_SUB_PFO_COM")==null?"":tempMap.get("RSK_PRO_SUB_PFO_COM").toString()); 
					 res2.setRskProSubSeqCal(tempMap.get("RSK_PRO_SUB_SEQ_CAL")==null?"":tempMap.get("RSK_PRO_SUB_SEQ_CAL").toString()); 
					 res2.setRskRunOffYear(tempMap.get("RSK_RUN_OFF_YEAR")==null?"":tempMap.get("RSK_RUN_OFF_YEAR").toString()); 
					 res2.setRskTrtyLmtPmlOc(tempMap.get("RSK_TRTY_LMT_PML_OC")==null?"":tempMap.get("RSK_TRTY_LMT_PML_OC").toString()); 
					 res2.setRskTrtyLmtPmlOsOc(tempMap.get("RSK_TRTY_LMT_PML_OS_OC")==null?"":tempMap.get("RSK_TRTY_LMT_PML_OS_OC").toString()); 
					 res2.setRskTrtyLmtPmlDc(tempMap.get("RSK_TRTY_LMT_PML_DC")==null?"":tempMap.get("RSK_TRTY_LMT_PML_DC").toString()); 
					 res2.setRskTrtyLmtPmlOsDc(tempMap.get("RSK_TRTY_LMT_PML_OS_DC")==null?"":tempMap.get("RSK_TRTY_LMT_PML_OS_DC").toString()); 
					 res2.setRskTrtyLmtSurPmlOc(tempMap.get("RSK_TRTY_LMT_SUR_PML_OC")==null?"":tempMap.get("RSK_TRTY_LMT_SUR_PML_OC").toString()); 
					 res2.setRskTrtyLmtSurPmlOsOc(tempMap.get("RSK_TRTY_LMT_SUR_PML_OS_OC")==null?"":tempMap.get("RSK_TRTY_LMT_SUR_PML_OS_OC").toString()); 
					 res2.setRskTrtyLmtSurPmlDc(tempMap.get("RSK_TRTY_LMT_SUR_PML_DC")==null?"":tempMap.get("RSK_TRTY_LMT_SUR_PML_DC").toString()); 
					 res2.setRskTrtyLmtSurPmlOsDc(tempMap.get("RSK_TRTY_LMT_SUR_PML_OS_DC")==null?"":tempMap.get("RSK_TRTY_LMT_SUR_PML_OS_DC").toString()); 
					 res2.setRskLocBnkName(tempMap.get("RSK_LOC_BNK_NAME")==null?"":tempMap.get("RSK_LOC_BNK_NAME").toString()); 
					 res2.setRskLocCrdtPrd(tempMap.get("RSK_LOC_CRDT_PRD")==null?"":tempMap.get("RSK_LOC_CRDT_PRD").toString()); 
					 res2.setRskLocCrdtAmt(tempMap.get("RSK_LOC_CRDT_AMT")==null?"":tempMap.get("RSK_LOC_CRDT_AMT").toString()); 
					 res2.setRskLocBenfcreName(tempMap.get("RSK_LOC_BENFCRE_NAME")==null?"":tempMap.get("RSK_LOC_BENFCRE_NAME").toString()); 
					 res2.setType(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString()); 
					 res2.setRskCoverLimitUxlOc(tempMap.get("RSK_COVER_LIMIT_UXL_OC")==null?"":tempMap.get("RSK_COVER_LIMIT_UXL_OC").toString()); 
					 res2.setRskCoverLimitUxlOsOc(tempMap.get("RSK_COVER_LIMIT_UXL_OS_OC")==null?"":tempMap.get("RSK_COVER_LIMIT_UXL_OS_OC").toString()); 
					 res2.setRskCoverLimitUxlDc(tempMap.get("RSK_COVER_LIMIT_UXL_DC")==null?"":tempMap.get("RSK_COVER_LIMIT_UXL_DC").toString()); 
					 res2.setRskCoverLimitUxlOsDc(tempMap.get("RSK_COVER_LIMIT_UXL_OS_DC")==null?"":tempMap.get("RSK_COVER_LIMIT_UXL_OS_DC").toString()); 
					 res2.setRskDeductableUxlOc(tempMap.get("RSK_DEDUCTABLE_UXL_OC")==null?"":tempMap.get("RSK_DEDUCTABLE_UXL_OC").toString()); 
					 res2.setRskDeductableUxlOsOc(tempMap.get("RSK_DEDUCTABLE_UXL_OS_OC")==null?"":tempMap.get("RSK_DEDUCTABLE_UXL_OS_OC").toString()); 
					 res2.setRskDeductableUxlDc(tempMap.get("RSK_DEDUCTABLE_UXL_DC")==null?"":tempMap.get("RSK_DEDUCTABLE_UXL_DC").toString()); 
					 res2.setRskDeductableUxlOsDc(tempMap.get("RSK_DEDUCTABLE_UXL_OS_DC")==null?"":tempMap.get("RSK_DEDUCTABLE_UXL_OS_DC").toString()); 
					 res2.setRskMinimumPremiumOc(tempMap.get("RSK_MINIMUM_PREMIUM_OC")==null?"":tempMap.get("RSK_MINIMUM_PREMIUM_OC").toString()); 
					 res2.setRskMinimumPremiumOsOc(tempMap.get("RSK_MINIMUM_PREMIUM_OS_OC")==null?"":tempMap.get("RSK_MINIMUM_PREMIUM_OS_OC").toString()); 
					 res2.setRskMinimumPremiumDc(tempMap.get("RSK_MINIMUM_PREMIUM_DC")==null?"":tempMap.get("RSK_MINIMUM_PREMIUM_DC").toString()); 
					 res2.setRskMinimumPremiumOsDc(tempMap.get("RSK_MINIMUM_PREMIUM_OS_DC")==null?"":tempMap.get("RSK_MINIMUM_PREMIUM_OS_DC").toString()); 
					 res2.setEgpniAsOfferDc(tempMap.get("EGPNI_AS_OFFER_DC")==null?"":tempMap.get("EGPNI_AS_OFFER_DC").toString()); 
					 res2.setRskAggregateDeductOc(tempMap.get("RSK_AGGREGATE_DEDUCT_OC")==null?"":tempMap.get("RSK_AGGREGATE_DEDUCT_OC").toString()); 
					 res2.setRskAggregateDeductDc(tempMap.get("RSK_AGGREGATE_DEDUCT_DC")==null?"":tempMap.get("RSK_AGGREGATE_DEDUCT_DC").toString()); 
					 res2.setRskPremiumBasis(tempMap.get("RSK_PREMIUM_BASIS")==null?"":tempMap.get("RSK_PREMIUM_BASIS").toString()); 
					 res2.setRskMinimumRate(tempMap.get("RSK_MINIMUM_RATE")==null?"":tempMap.get("RSK_MINIMUM_RATE").toString()); 
					 res2.setRskMaxiimumRate(tempMap.get("RSK_MAXIIMUM_RATE")==null?"":tempMap.get("RSK_MAXIIMUM_RATE").toString()); 
					 res2.setRskBurningCostLf(tempMap.get("RSK_BURNING_COST_LF")==null?"":tempMap.get("RSK_BURNING_COST_LF").toString()); 
					 res2.setRskReinstatementPremium(tempMap.get("RSK_REINSTATEMENT_PREMIUM")==null?"":tempMap.get("RSK_REINSTATEMENT_PREMIUM").toString()); 
					 res2.setRskBonusId(tempMap.get("RSK_BONUS_ID")==null?"":tempMap.get("RSK_BONUS_ID").toString()); 
					 res2.setRskNoclaimbonusPrcent(tempMap.get("RSK_NOCLAIMBONUS_PRCENT")==null?"":tempMap.get("RSK_NOCLAIMBONUS_PRCENT").toString()); 
					 res2.setRskUmbrellaXl(tempMap.get("RSK_UMBRELLA_XL")==null?"":tempMap.get("RSK_UMBRELLA_XL").toString());  
					   res2List.add(res2);
				} 
				comRes.setIfProduct23(res2List);
			}
			
		}else if("4".equalsIgnoreCase(bean.getProductId())||"5".equalsIgnoreCase(bean.getProductId())){
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetPolicyRegisterListRes4 res4 = new GetPolicyRegisterListRes4();
					res4.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());   
					res4.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());   
					res4.setTmasPfcName(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString());   
					res4.setUnderwritter(tempMap.get("UNDERWRITTER")==null?"":tempMap.get("UNDERWRITTER").toString());   
					res4.setTmasDepartmentName(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());   
					res4.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());   
					res4.setRskUwyear(tempMap.get("RSK_UWYEAR")==null?"":tempMap.get("RSK_UWYEAR").toString());   
					res4.setRskMaxLmtCover(tempMap.get("RSK_MAX_LMT_COVER")==null?"":tempMap.get("RSK_MAX_LMT_COVER").toString());   
					res4.setTmasPolBranchName(tempMap.get("TMAS_POL_BRANCH_NAME")==null?"":tempMap.get("TMAS_POL_BRANCH_NAME").toString());   
					res4.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());   
					res4.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());   
					res4.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());   
					res4.setProposalType(tempMap.get("PROPOSAL_TYPE")==null?"":tempMap.get("PROPOSAL_TYPE").toString());   
					res4.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());   
					res4.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());   
					res4.setAccountDate(tempMap.get("ACCOUNT_DATE")==null?"":tempMap.get("ACCOUNT_DATE").toString());   
					res4.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());   
					res4.setRskExchangeRate(tempMap.get("RSK_EXCHANGE_RATE")==null?"":tempMap.get("RSK_EXCHANGE_RATE").toString());   
					res4.setRskPeriodOfNotice(tempMap.get("RSK_PERIOD_OF_NOTICE")==null?"":tempMap.get("RSK_PERIOD_OF_NOTICE").toString());   
					res4.setRskRiskCovered(tempMap.get("RSK_RISK_COVERED")==null?"":tempMap.get("RSK_RISK_COVERED").toString());   
					res4.setRskTerritoryScope(tempMap.get("RSK_TERRITORY_SCOPE")==null?"":tempMap.get("RSK_TERRITORY_SCOPE").toString());   
					res4.setAccountingPeriod(tempMap.get("ACCOUNTING_PERIOD")==null?"":tempMap.get("ACCOUNTING_PERIOD").toString());   
					res4.setRskReceiptStatement(tempMap.get("RSK_RECEIPT_STATEMENT")==null?"":tempMap.get("RSK_RECEIPT_STATEMENT").toString());   
					res4.setRskReceiptPayement(tempMap.get("RSK_RECEIPT_PAYEMENT")==null?"":tempMap.get("RSK_RECEIPT_PAYEMENT").toString());   
					res4.setRskSpRetro(tempMap.get("RSK_SP_RETRO")==null?"":tempMap.get("RSK_SP_RETRO").toString());   
					res4.setRskNoOfInsurers(tempMap.get("RSK_NO_OF_INSURERS")==null?"":tempMap.get("RSK_NO_OF_INSURERS").toString());   
					res4.setRskLimitOc(tempMap.get("RSK_LIMIT_OC")==null?"":tempMap.get("RSK_LIMIT_OC").toString());   
					res4.setRskEpiOfferOc(tempMap.get("RSK_EPI_OFFER_OC")==null?"":tempMap.get("RSK_EPI_OFFER_OC").toString());   
					res4.setRskEpiEstimate(tempMap.get("RSK_EPI_ESTIMATE")==null?"":tempMap.get("RSK_EPI_ESTIMATE").toString());   
					res4.setRskEpiEstOc(tempMap.get("RSK_EPI_EST_OC")==null?"":tempMap.get("RSK_EPI_EST_OC").toString());   
					res4.setRskXlcostOc(tempMap.get("RSK_XLCOST_OC")==null?"":tempMap.get("RSK_XLCOST_OC").toString());   
					res4.setRskCedretType(tempMap.get("RSK_CEDRET_TYPE")==null?"":tempMap.get("RSK_CEDRET_TYPE").toString());   
					res4.setRskCedantRetention(tempMap.get("RSK_CEDANT_RETENTION")==null?"":tempMap.get("RSK_CEDANT_RETENTION").toString());   
					res4.setProposalStatu(tempMap.get("PROPOSAL_STATU")==null?"":tempMap.get("PROPOSAL_STATU").toString());   
					res4.setRskShareWritten(tempMap.get("RSK_SHARE_WRITTEN")==null?"":tempMap.get("RSK_SHARE_WRITTEN").toString());   
					res4.setRskShareSigned(tempMap.get("RSK_SHARE_SIGNED	  RSK_SHARE_SIGNED").toString());   
					res4.setRskLimitOsOc(tempMap.get("RSK_LIMIT_OS_OC")==null?"":tempMap.get("RSK_LIMIT_OS_OC").toString());   
					res4.setRskLimitOsDc(tempMap.get("RSK_LIMIT_OS_DC")==null?"":tempMap.get("RSK_LIMIT_OS_DC").toString());   
					res4.setRskEpiOsofOc(tempMap.get("RSK_EPI_OSOF_OC")==null?"":tempMap.get("RSK_EPI_OSOF_OC").toString());   
					res4.setRskEpiOsofDc(tempMap.get("RSK_EPI_OSOF_DC")==null?"":tempMap.get("RSK_EPI_OSOF_DC").toString());   
					res4.setRskEpiOsoeOc(tempMap.get("RSK_EPI_OSOE_OC")==null?"":tempMap.get("RSK_EPI_OSOE_OC").toString());   
					res4.setRskEpiOsoeDc(tempMap.get("RSK_EPI_OSOE_DC")==null?"":tempMap.get("RSK_EPI_OSOE_DC").toString());   
					res4.setRskXlcostOsOc(tempMap.get("RSK_XLCOST_OS_OC")==null?"":tempMap.get("RSK_XLCOST_OS_OC").toString());   
					res4.setRskXlcostOsDc(tempMap.get("RSK_XLCOST_OS_DC")==null?"":tempMap.get("RSK_XLCOST_OS_DC").toString());   
					res4.setRskPremiumQuotaShare(tempMap.get("RSK_PREMIUM_QUOTA_SHARE")==null?"":tempMap.get("RSK_PREMIUM_QUOTA_SHARE").toString());   
					res4.setRskPremiumSurpuls(tempMap.get("RSK_PREMIUM_SURPULS")==null?"":tempMap.get("RSK_PREMIUM_SURPULS").toString());   
					res4.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());   
					res4.setBrokerId(tempMap.get("BROKER_ID")==null?"":tempMap.get("BROKER_ID").toString());   
					res4.setCedingCompanyId(tempMap.get("CEDING_COMPANY_ID")==null?"":tempMap.get("CEDING_COMPANY_ID").toString());   
					res4.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());   
					res4.setRskLayerNo(tempMap.get("RSK_LAYER_NO")==null?"":tempMap.get("RSK_LAYER_NO").toString());   
					res4.setRskBasis(tempMap.get("RSK_BASIS")==null?"":tempMap.get("RSK_BASIS").toString());   
					res4.setRskDeducOc(tempMap.get("RSK_DEDUC_OC")==null?"":tempMap.get("RSK_DEDUC_OC").toString());   
					res4.setRskSubjPremiumOc(tempMap.get("RSK_SUBJ_PREMIUM_OC")==null?"":tempMap.get("RSK_SUBJ_PREMIUM_OC").toString());   
					res4.setRskAdjrate(tempMap.get("RSK_ADJRATE")==null?"":tempMap.get("RSK_ADJRATE").toString());   
					res4.setRskMdPremOc(tempMap.get("RSK_MD_PREM_OC")==null?"":tempMap.get("RSK_MD_PREM_OC").toString());   
					res4.setMndInstallments(tempMap.get("MND_INSTALLMENTS")==null?"":tempMap.get("MND_INSTALLMENTS").toString());   
					res4.setRskMdPremOsOc(tempMap.get("RSK_MD_PREM_OS_OC")==null?"":tempMap.get("RSK_MD_PREM_OS_OC").toString());   
					res4.setRskMdPremOsDc(tempMap.get("RSK_MD_PREM_OS_DC")==null?"":tempMap.get("RSK_MD_PREM_OS_DC").toString());   
					res4.setProductId(tempMap.get("PRODUCT_ID")==null?"":tempMap.get("PRODUCT_ID").toString());   
					res4.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());   
					res4.setRskSubjPremiumDc(tempMap.get("RSK_SUBJ_PREMIUM_DC")==null?"":tempMap.get("RSK_SUBJ_PREMIUM_DC").toString());   
					res4.setRskLimitDc(tempMap.get("RSK_LIMIT_DC")==null?"":tempMap.get("RSK_LIMIT_DC").toString());   
					res4.setRskDeducDc(tempMap.get("RSK_DEDUC_DC")==null?"":tempMap.get("RSK_DEDUC_DC").toString());   
					res4.setRskEpiEstDc(tempMap.get("RSK_EPI_EST_DC")==null?"":tempMap.get("RSK_EPI_EST_DC").toString());   
					res4.setRskXlpremDc(tempMap.get("RSK_XLPREM_DC")==null?"":tempMap.get("RSK_XLPREM_DC").toString());   
					res4.setRskMdPremDc(tempMap.get("vRSK_MD_PREM_DC")==null?"":tempMap.get("RSK_MD_PREM_DC").toString());   
					res4.setRskPfCovered(tempMap.get("RSK_PF_COVERED")==null?"":tempMap.get("RSK_PF_COVERED").toString());   
					res4.setRskEpiOfferDc(tempMap.get("RSK_EPI_OFFER_DC")==null?"":tempMap.get("RSK_EPI_OFFER_DC").toString());   
					res4.setRskXlcostDc(tempMap.get("RSK_XLCOST_DC")==null?"":tempMap.get("RSK_XLCOST_DC").toString());   
					res4.setRskRetroType(tempMap.get("RSK_RETRO_TYPE")==null?"":tempMap.get("RSK_RETRO_TYPE").toString());   
					res4.setRetroCessionaries(tempMap.get("RETRO_CESSIONARIES")==null?"":tempMap.get("RETRO_CESSIONARIES").toString());   
					res4.setRskRemarks(tempMap.get("RSK_REMARKS")==null?"":tempMap.get("RSK_REMARKS").toString());   
					res4List.add(res4);
				} 
				comRes.setIfProduct45(res4List);
			}
		}
		
				response.setCommonResponse(comRes);
				response.setMessage("Success");
				response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}
		 public List<Map<String,Object>> resultSetToArrayList(ResultSet rs) {
			 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
				try {
					ResultSetMetaData md = rs.getMetaData();
					int columns = md.getColumnCount();
					
					while (rs.next()){
						HashMap row = new HashMap(columns);
						for(int i=1; i<=columns; ++i){
							row.put(md.getColumnName(i),rs.getObject(i));
						}
						list.add(row);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				return list;
			}


		@Override
		public GetPremiumRegisterListRes getPremiumRegisterList(ReportsCommonReq bean) {
			GetPremiumRegisterListRes response = new GetPremiumRegisterListRes();
			GetPremiumRegisterListComRes comRes = new GetPremiumRegisterListComRes();
		
			List<GetPremiumRegisterListRes1> res1List = new ArrayList<GetPremiumRegisterListRes1>();
			List<GetPremiumRegisterListRes2> res2List = new ArrayList<GetPremiumRegisterListRes2>();
			List<GetPremiumRegisterListRes3> res3List = new ArrayList<GetPremiumRegisterListRes3>();
			List<GetPremiumRegisterListRes4> res4List = new ArrayList<GetPremiumRegisterListRes4>();
			 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			String[] obj= new String[0];
			String query="";
			String qutext = "";
			try {
			if("1".equalsIgnoreCase(bean.getProductId())){
				obj= new String[7];
				obj[0]=bean.getStartDate();
				obj[1]=bean.getEndDate();
				obj[2]=bean.getBranchCode();
				obj[3]=bean.getLoginId();
				obj[4] =bean.getBrokerId();
				obj[5] =bean.getCedingId();
				obj[6] = bean.getUwYear();
				//3fn calls, 4 qry: 54,93,55,19 (diff response)
				query = "report.select.premiumReport";
				qutext = prop.getProperty(query);
			}else if("2".equalsIgnoreCase(bean.getProductId())){
				obj= new String[7];
				obj[0]=bean.getStartDate();
				obj[1]=bean.getEndDate();
				obj[2]=bean.getBranchCode();
				obj[3]=bean.getLoginId();
				obj[4] =bean.getBrokerId();
				obj[5] =bean.getCedingId();
				obj[6] = bean.getUwYear();
				query="REPORT_SELECT_PTTYPREMIUMREPORT";
				qutext = prop.getProperty(query);
			}else if("3".equalsIgnoreCase(bean.getProductId())){
				obj= new String[7];
				obj[0]=bean.getStartDate();
				obj[1]=bean.getEndDate();
				obj[2]=bean.getBranchCode();
				obj[3]=bean.getLoginId();
				obj[4] =bean.getBrokerId();
				obj[5] =bean.getCedingId();
				obj[6] = bean.getUwYear();
				query="REPORT_SELECT_NPTTYPREMIUMREPORT";
				qutext = prop.getProperty(query);
			}
			else if("4".equalsIgnoreCase(bean.getProductId()) || "5".equalsIgnoreCase(bean.getProductId())){
				obj=new String[6];
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getProductId();
				obj[2]=bean.getDept();
				obj[3]=bean.getStartDate();
				obj[4]=bean.getEndDate();
				obj[5]=bean.getBranchCode();
				query="report.select.retroPermiumClaim";
				qutext = prop.getProperty(query);
			}
			if("4".equalsIgnoreCase(bean.getProductId()) || "5".equalsIgnoreCase(bean.getProductId())){
				if(!"-1".equals(bean.getLoginId())){
					obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacLoginId");
				}
				if(!"-1".equals(bean.getBrokerId())){
					obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacBrokerId");
				}
				if(!"-1".equals(bean.getCedingId())){
					obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacCedingId");
				}
				if(!"-1".equals(bean.getUwYear())){
					obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
					qutext += " " +prop.getProperty("report.select.policyRegFacUWYear");
				}
			}
			qutext += " " +prop.getProperty("report.select.premiumReportOrderBy");
			query1 =queryImpl.setQueryProp(qutext, obj);
			query1.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			try {
				 list = query1.getResultList();
			} catch(Exception e) {
				e.printStackTrace();
			} 
			
			if("1".equalsIgnoreCase(bean.getProductId())){
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPremiumRegisterListRes1 res1 = new GetPremiumRegisterListRes1();
					    res1.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
				        res1.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
				        res1.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
				        res1.setTmasPfcName(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString());
				        res1.setRskInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
				        res1.setRskDeptid(tempMap.get("RSK_DEPTID")==null?"":tempMap.get("RSK_DEPTID").toString());
				        res1.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
				        res1.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				        res1.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
				        res1.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
				        res1.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
				        res1.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE  ").toString());
				        res1.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());
				        res1.setCedantReference(tempMap.get("CEDANT_REFERENCE")==null?"":tempMap.get("CEDANT_REFERENCE").toString());
				        res1.setPremiumClass(tempMap.get("PREMIUM_CLASS")==null?"":tempMap.get("PREMIUM_CLASS").toString());
				        res1.setPremiumSubclass(tempMap.get("PREMIUM_SUBCLASS")==null?"":tempMap.get("PREMIUM_SUBCLASS").toString());
				        res1.setRiCession(tempMap.get("RI_CESSION")==null?"":tempMap.get("RI_CESSION").toString());
				        res1.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
				        res1.setStatmentRecdDate(tempMap.get("STATMENT_RECD_DATE")==null?"":tempMap.get("STATMENT_RECD_DATE").toString());
				        res1.setPremiumQuotashareOc(tempMap.get("PREMIUM_QUOTASHARE_OC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_OC").toString());
				        res1.setTdsOc(tempMap.get("TDS_OC")==null?"":tempMap.get("TDS_OC").toString());
				        res1.setStOc(tempMap.get("ST_OC")==null?"":tempMap.get("ST_OC").toString());
				        res1.setTotalCrOc(tempMap.get("TOTAL_CR_OC")==null?"":tempMap.get("TOTAL_CR_OC").toString());
				        res1.setCommissionQuotashareOc(tempMap.get("COMMISSION_QUOTASHARE_OC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_OC").toString());
				        res1.setBrokerageAmtOc(tempMap.get("BROKERAGE_AMT_OC")==null?"":tempMap.get("BROKERAGE_AMT_OC").toString());
				        res1.setTaxAmtOc(tempMap.get("TAX_AMT_OC")==null?"":tempMap.get("TAX_AMT_OC").toString());
				        res1.setOtherCostOc(tempMap.get("OTHER_COST_OC")==null?"":tempMap.get("OTHER_COST_OC").toString());
				        res1.setWithHoldingTaxOc(tempMap.get("WITH_HOLDING_TAX_OC")==null?"":tempMap.get("WITH_HOLDING_TAX_OC").toString());
				        res1.setBonusOc(tempMap.get("BONUS_OC")==null?"":tempMap.get("BONUS_OC").toString());
				        res1.setTotalDrOc(tempMap.get("TOTAL_DR_OC")==null?"":tempMap.get("TOTAL_DR_OC").toString());
				        res1.setNetdueOc(tempMap.get("NETDUE_OC")==null?"":tempMap.get("NETDUE_OC").toString());
				        res1.setAllocatedTillDateOc(tempMap.get("ALLOCATED_TILL_DATE_OC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_OC").toString());
				        res1.setAllcationPendingOc(tempMap.get("ALLCATION_PENDING_OC")==null?"":tempMap.get("ALLCATION_PENDING_OC").toString());
				        res1.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
				        res1.setPremiumQuotashareDc(tempMap.get("PREMIUM_QUOTASHARE_DC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_DC").toString());
				        res1.setTdsDc(tempMap.get("TDS_DC")==null?"":tempMap.get("TDS_DC").toString());
				        res1.setStDc(tempMap.get("ST_DC")==null?"":tempMap.get("ST_DC").toString());
				        res1.setTotalCrDc(tempMap.get("TOTAL_CR_DC")==null?"":tempMap.get("TOTAL_CR_DC").toString());
				        res1.setCommissionQuotashareDc(tempMap.get("COMMISSION_QUOTASHARE_DC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_DC").toString());
				        res1.setBrokerageAmtDc(tempMap.get("BROKERAGE_AMT_DC")==null?"":tempMap.get("BROKERAGE_AMT_DC").toString());
				        res1.setTaxAmtDc(tempMap.get("TAX_AMT_DC")==null?"":tempMap.get("TAX_AMT_DC").toString());
				        res1.setOtherCostDc(tempMap.get("OTHER_COST_DC")==null?"":tempMap.get("OTHER_COST_DC").toString());
				        res1.setWithHoldingTaxDc(tempMap.get("WITH_HOLDING_TAX_DC")==null?"":tempMap.get("WITH_HOLDING_TAX_DC").toString());
				        res1.setBonusDc(tempMap.get("BONUS_DC")==null?"":tempMap.get("BONUS_DC").toString());
				        res1.setTotalDrDc(tempMap.get("TOTAL_DR_DC")==null?"":tempMap.get("TOTAL_DR_DC").toString());
				        res1.setNetdueDc(tempMap.get("NETDUE_DC")==null?"":tempMap.get("NETDUE_DC").toString());
				        res1.setAllocatedTillDateDc(tempMap.get("ALLOCATED_TILL_DATE_DC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_DC").toString());
				        res1.setAllcationPendingDc(tempMap.get("ALLCATION_PENDING_DC")==null?"":tempMap.get("ALLCATION_PENDING_DC").toString());
				        res1.setRemarks(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
				        res1.setSettlementStatus(tempMap.get("SETTLEMENT_STATUS")==null?"":tempMap.get("SETTLEMENT_STATUS").toString());
				        res1.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
				        res1.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
				        res1.setInstallmentDate(tempMap.get("INSTALLMENT_DATE")==null?"":tempMap.get("INSTALLMENT_DATE").toString());
				        res1.setEndorsementDate(tempMap.get("ENDORSEMENT_DATE")==null?"":tempMap.get("ENDORSEMENT_DATE").toString());
						res1List.add(res1);
			} 
					comRes.setIfProduct1(res1List);		
					
				}
			}else if("2".equalsIgnoreCase(bean.getProductId())){
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPremiumRegisterListRes2 res2 = new GetPremiumRegisterListRes2();
						res2.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
				        res2.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
				        res2.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
				        res2.setTmasPfcName(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString());
				        res2.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());
				        res2.setRskDeptid(tempMap.get("RSK_DEPTID")==null?"":tempMap.get("RSK_DEPTID").toString());
				        res2.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
				        res2.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				        res2.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
				        res2.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
				        res2.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
				        res2.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE  ").toString());
				        res2.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());
				        res2.setCedantReference(tempMap.get("CEDANT_REFERENCE")==null?"":tempMap.get("CEDANT_REFERENCE").toString());
				        res2.setPremiumClass(tempMap.get("PREMIUM_CLASS")==null?"":tempMap.get("PREMIUM_CLASS").toString());
				        res2.setPremiumSubclass(tempMap.get("PREMIUM_SUBCLASS")==null?"":tempMap.get("PREMIUM_SUBCLASS").toString());
				        res2.setRiCession(tempMap.get("RI_CESSION")==null?"":tempMap.get("RI_CESSION").toString());
				        res2.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
				        res2.setStatmentRecdDate(tempMap.get("STATMENT_RECD_DATE")==null?"":tempMap.get("STATMENT_RECD_DATE").toString());
				        res2.setPremiumQuotashareOc(tempMap.get("PREMIUM_QUOTASHARE_OC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_OC").toString());
				        res2.setPremiumSurplusOc(tempMap.get("PREMIUM_SURPLUS_OC")==null?"":tempMap.get("PREMIUM_SURPLUS_OC").toString());
				        res2.setPremiumPortfolioinOc(tempMap.get("PREMIUM_PORTFOLIOIN_OC")==null?"":tempMap.get("PREMIUM_PORTFOLIOIN_OC").toString());
				        res2.setClaimPortfolioinOc(tempMap.get("CLAIM_PORTFOLIOIN_OC")==null?"":tempMap.get("CLAIM_PORTFOLIOIN_OC").toString());
				        res2.setPremiumReserveRealsedOc(tempMap.get("PREMIUM_RESERVE_REALSED_OC")==null?"":tempMap.get("PREMIUM_RESERVE_REALSED_OC").toString());
				        res2.setLossReserveReleasedOc(tempMap.get("LOSS_RESERVE_RELEASED_OC")==null?"":tempMap.get("LOSS_RESERVE_RELEASED_OC").toString());
				        res2.setInterestOc(tempMap.get("INTEREST_OC")==null?"":tempMap.get("INTEREST_OC").toString());
				        res2.setCashLossCreditOc(tempMap.get("CASH_LOSS_CREDIT_OC")==null?"":tempMap.get("CASH_LOSS_CREDIT_OC").toString());
				        res2.setTdsOc(tempMap.get("TDS_OC")==null?"":tempMap.get("TDS_OC").toString());
				        res2.setStOc(tempMap.get("ST_OC")==null?"":tempMap.get("ST_OC").toString());
				        res2.setLpcOc(tempMap.get("LPC_OC")==null?"":tempMap.get("LPC_OC").toString());
				        res2.setTotalCrOc(tempMap.get("TOTAL_CR_OC")==null?"":tempMap.get("TOTAL_CR_OC").toString());
				        res2.setCommissionQuotashareOc(tempMap.get("COMMISSION_QUOTASHARE_OC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_OC").toString());
				        res2.setCommissionSurplusOc(tempMap.get("COMMISSION_SURPLUS_OC")==null?"":tempMap.get("COMMISSION_SURPLUS_OC").toString());
				        res2.setBrokerageAmtOc(tempMap.get("BROKERAGE_AMT_OC")==null?"":tempMap.get("BROKERAGE_AMT_OC").toString());
				        res2.setTaxAmtOc(tempMap.get("TAX_AMT_OC")==null?"":tempMap.get("TAX_AMT_OC").toString());
				        res2.setOtherCostOc(tempMap.get("OTHER_COST_OC")==null?"":tempMap.get("OTHER_COST_OC").toString());
				        res2.setOverriderAmtOc(tempMap.get("OVERRIDER_AMT_OC")==null?"":tempMap.get("OVERRIDER_AMT_OC").toString());
				        res2.setXlCostOc(tempMap.get("XL_COST_OC")==null?"":tempMap.get("XL_COST_OC").toString());
				        res2.setPremiumReserveRetainedOc(tempMap.get("PREMIUM_RESERVE_RETAINED_OC")==null?"":tempMap.get("PREMIUM_RESERVE_RETAINED_OC").toString());
				        res2.setLossReserveretainedOc(tempMap.get("LOSS_RESERVERETAINED_OC")==null?"":tempMap.get("LOSS_RESERVERETAINED_OC").toString());
				        res2.setPremiumPortfoliooutOc(tempMap.get("PREMIUM_PORTFOLIOOUT_OC")==null?"":tempMap.get("PREMIUM_PORTFOLIOOUT_OC").toString());
				        res2.setClaimPortfolioOutOc(tempMap.get("CLAIM_PORTFOLIO_OUT_OC")==null?"":tempMap.get("CLAIM_PORTFOLIO_OUT_OC").toString());
				        res2.setWithHoldingTaxOc(tempMap.get("WITH_HOLDING_TAX_OC")==null?"":tempMap.get("WITH_HOLDING_TAX_OC").toString());
				        res2.setScCommOc(tempMap.get("SC_COMM_OC")==null?"":tempMap.get("SC_COMM_OC").toString());
				        res2.setProfitCommissionOc(tempMap.get("PROFIT_COMMISSION_OC")==null?"":tempMap.get("PROFIT_COMMISSION_OC").toString());
				        res2.setClaimsPaidOc(tempMap.get("CLAIMS_PAID_OC")==null?"":tempMap.get("CLAIMS_PAID_OC").toString());
				        res2.setCashLosspaidOc(tempMap.get("CASH_LOSSPAID_OC")==null?"":tempMap.get("CASH_LOSSPAID_OC").toString());
				        res2.setTotalDrOc(tempMap.get("TOTAL_DR_OC")==null?"":tempMap.get("TOTAL_DR_OC").toString());
				        res2.setNetdueOc(tempMap.get("NETDUE_OC")==null?"":tempMap.get("NETDUE_OC").toString());
				        res2.setAllocatedTillDateOc(tempMap.get("ALLOCATED_TILL_DATE_OC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_OC").toString());
				        res2.setAllcationPendingOc(tempMap.get("ALLCATION_PENDING_OC")==null?"":tempMap.get("ALLCATION_PENDING_OC").toString());
				        res2.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
				        res2.setPremiumQuotashareDc(tempMap.get("PREMIUM_QUOTASHARE_DC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_DC").toString());
				        res2.setPremiumSurplusDc(tempMap.get("PREMIUM_SURPLUS_DC")==null?"":tempMap.get("PREMIUM_SURPLUS_DC").toString());
				        res2.setPremiumPortfolioinDc(tempMap.get("PREMIUM_PORTFOLIOIN_DC")==null?"":tempMap.get("PREMIUM_PORTFOLIOIN_DC").toString());
				        res2.setClaimPortfolioinDc(tempMap.get("CLAIM_PORTFOLIOIN_DC")==null?"":tempMap.get("CLAIM_PORTFOLIOIN_DC").toString());
				        res2.setPremiumReserveRealsedDc(tempMap.get("PREMIUM_RESERVE_REALSED_DC")==null?"":tempMap.get("PREMIUM_RESERVE_REALSED_DC").toString());
				        res2.setLossReserveReleasedDc(tempMap.get("LOSS_RESERVE_RELEASED_DC")==null?"":tempMap.get("LOSS_RESERVE_RELEASED_DC").toString());
				        res2.setInterestDc(tempMap.get("INTEREST_DC")==null?"":tempMap.get("INTEREST_DC").toString());
				        res2.setCashLossCreditDc(tempMap.get("CASH_LOSS_CREDIT_DC")==null?"":tempMap.get("CASH_LOSS_CREDIT_DC").toString());
				        res2.setTdsDc(tempMap.get("TDS_DC")==null?"":tempMap.get("TDS_DC").toString());
				        res2.setStDc(tempMap.get("ST_DC")==null?"":tempMap.get("ST_DC").toString());
				        res2.setLpcDc(tempMap.get("LPC_DC")==null?"":tempMap.get("LPC_DC").toString());
				        res2.setTotalCrDc(tempMap.get("TOTAL_CR_DC")==null?"":tempMap.get("TOTAL_CR_DC").toString());
				        res2.setCommissionQuotashareDc(tempMap.get("COMMISSION_QUOTASHARE_DC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_DC").toString());
				        res2.setCommissionSurplusDc(tempMap.get("COMMISSION_SURPLUS_DC")==null?"":tempMap.get("COMMISSION_SURPLUS_DC").toString());
				        res2.setBrokerageAmtDc(tempMap.get("BROKERAGE_AMT_DC")==null?"":tempMap.get("BROKERAGE_AMT_DC").toString());
				        res2.setTaxAmtDc(tempMap.get("TAX_AMT_DC")==null?"":tempMap.get("TAX_AMT_DC").toString());
				        res2.setOtherCostDc(tempMap.get("OTHER_COST_DC")==null?"":tempMap.get("OTHER_COST_DC").toString());
				        res2.setOverriderAmtDc(tempMap.get("OVERRIDER_AMT_DC")==null?"":tempMap.get("OVERRIDER_AMT_DC").toString());
				        res2.setXlCostDc(tempMap.get("XL_COST_DC")==null?"":tempMap.get("XL_COST_DC").toString());
				        res2.setPremiumReserveRetainedDc(tempMap.get("PREMIUM_RESERVE_RETAINED_DC")==null?"":tempMap.get("PREMIUM_RESERVE_RETAINED_DC").toString());
				        res2.setLossReserveretainedDc(tempMap.get("LOSS_RESERVERETAINED_DC")==null?"":tempMap.get("LOSS_RESERVERETAINED_DC").toString());
				        res2.setPremiumPortfoliooutDc(tempMap.get("PREMIUM_PORTFOLIOOUT_DC")==null?"":tempMap.get("PREMIUM_PORTFOLIOOUT_DC").toString());
				        res2.setClaimPortfolioOutDc(tempMap.get("CLAIM_PORTFOLIO_OUT_DC")==null?"":tempMap.get("CLAIM_PORTFOLIO_OUT_DC").toString());
				        res2.setWithHoldingTaxDc(tempMap.get("WITH_HOLDING_TAX_DC")==null?"":tempMap.get("WITH_HOLDING_TAX_DC").toString());
				        res2.setScCommDc(tempMap.get("SC_COMM_DC")==null?"":tempMap.get("SC_COMM_DC").toString());
				        res2.setProfitCommissionDc(tempMap.get("PROFIT_COMMISSION_DC")==null?"":tempMap.get("PROFIT_COMMISSION_DC").toString());
				        res2.setClaimsPaidDc(tempMap.get("CLAIMS_PAID_DC")==null?"":tempMap.get("CLAIMS_PAID_DC").toString());
				        res2.setCashLosspaidDc(tempMap.get("CASH_LOSSPAID_DC")==null?"":tempMap.get("CASH_LOSSPAID_DC").toString());
				        res2.setTotalDrDc(tempMap.get("TOTAL_DR_DC")==null?"":tempMap.get("TOTAL_DR_DC").toString());
				        res2.setNetdueDc(tempMap.get("NETDUE_DC")==null?"":tempMap.get("NETDUE_DC").toString());
				        res2.setAllocatedTillDateDc(tempMap.get("ALLOCATED_TILL_DATE_DC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_DC").toString());
				        res2.setAllcationPendingDc(tempMap.get("ALLCATION_PENDING_DC")==null?"":tempMap.get("ALLCATION_PENDING_DC").toString());
				        res2.setRemarks(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
				        res2.setSettlementStatus(tempMap.get("SETTLEMENT_STATUS")==null?"":tempMap.get("SETTLEMENT_STATUS").toString());
				        res2.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
				        res2.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
				        res2.setInstallmentDate(tempMap.get("INSTALLMENT_DATE")==null?"":tempMap.get("INSTALLMENT_DATE").toString());
				        res2.setOsbyn(tempMap.get("OSBYN")==null?"":tempMap.get("OSBYN").toString());
				        res2.setOsclaimLossupdateOc(tempMap.get("OSCLAIM_LOSSUPDATE_OC")==null?"":tempMap.get("OSCLAIM_LOSSUPDATE_OC").toString());
				        res2.setOsclaimLossupdateDc(tempMap.get("OSCLAIM_LOSSUPDATE_DC")==null?"":tempMap.get("OSCLAIM_LOSSUPDATE_DC").toString());
				        res2.setEndorsementDate(tempMap.get("ENDORSEMENT_DATE")==null?"":tempMap.get("ENDORSEMENT_DATE").toString());
						   
						   res2List.add(res2);
			}
					comRes.setIfProduct2(res2List);
					}
			}else if("3".equalsIgnoreCase(bean.getProductId())){
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPremiumRegisterListRes3 res3 = new GetPremiumRegisterListRes3();
						 res3.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
				         res3.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
				         res3.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
				         res3.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
				         res3.setTmasPfcName(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString());
				         res3.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());
				         res3.setRskDeptid(tempMap.get("RSK_DEPTID")==null?"":tempMap.get("RSK_DEPTID").toString());
				         res3.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				         res3.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
				         res3.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
				         res3.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
				         res3.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE  ").toString());
				         res3.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());
				         res3.setCedantReference(tempMap.get("CEDANT_REFERENCE")==null?"":tempMap.get("CEDANT_REFERENCE").toString());
				         res3.setPremiumClass(tempMap.get("PREMIUM_CLASS")==null?"":tempMap.get("PREMIUM_CLASS").toString());
				         res3.setRiCession(tempMap.get("RI_CESSION")==null?"":tempMap.get("RI_CESSION").toString());
				         res3.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
				         res3.setStatmentRecdDate(tempMap.get("STATMENT_RECD_DATE")==null?"":tempMap.get("STATMENT_RECD_DATE").toString());
				         res3.setMDpremiumOc(tempMap.get("M_DPREMIUM_OC")==null?"":tempMap.get("M_DPREMIUM_OC").toString());
				         res3.setAdjustmentPremiumOc(tempMap.get("ADJUSTMENT_PREMIUM_OC")==null?"":tempMap.get("ADJUSTMENT_PREMIUM_OC").toString());
				         res3.setRecPremiumOc(tempMap.get("REC_PREMIUM_OC")==null?"":tempMap.get("REC_PREMIUM_OC").toString());
				         res3.setTdsOc(tempMap.get("TDS_OC")==null?"":tempMap.get("TDS_OC").toString());
				         res3.setStOc(tempMap.get("ST_OC")==null?"":tempMap.get("ST_OC").toString());
				         res3.setTotalCrOc(tempMap.get("TOTAL_CR_OC")==null?"":tempMap.get("TOTAL_CR_OC").toString());
				         res3.setBrokerageAmtOc(tempMap.get("BROKERAGE_AMT_OC")==null?"":tempMap.get("BROKERAGE_AMT_OC").toString());
				         res3.setTaxAmtOc(tempMap.get("TAX_AMT_OC")==null?"":tempMap.get("TAX_AMT_OC").toString());
				         res3.setOtherCostOc(tempMap.get("OTHER_COST_OC")==null?"":tempMap.get("OTHER_COST_OC").toString());
				         res3.setBonusOc(tempMap.get("BONUS_OC")==null?"":tempMap.get("BONUS_OC").toString());
				         res3.setWithHoldingTaxOc(tempMap.get("WITH_HOLDING_TAX_OC")==null?"":tempMap.get("WITH_HOLDING_TAX_OC").toString());
				         res3.setTotalDrOc(tempMap.get("TOTAL_DR_OC")==null?"":tempMap.get("TOTAL_DR_OC").toString());
				         res3.setNetdueOc(tempMap.get("NETDUE_OC")==null?"":tempMap.get("NETDUE_OC").toString());
				         res3.setAllocatedTillDateOc(tempMap.get("ALLOCATED_TILL_DATE_OC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_OC").toString());
				         res3.setAllcationPendingOc(tempMap.get("ALLCATION_PENDING_OC")==null?"":tempMap.get("ALLCATION_PENDING_OC").toString());
				         res3.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
				         res3.setMDpremiumDc(tempMap.get("M_DPREMIUM_DC")==null?"":tempMap.get("M_DPREMIUM_DC").toString());
				         res3.setAdjustmentPremiumDc(tempMap.get("ADJUSTMENT_PREMIUM_DC")==null?"":tempMap.get("ADJUSTMENT_PREMIUM_DC").toString());
				         res3.setRecPremiumDc(tempMap.get("REC_PREMIUM_DC")==null?"":tempMap.get("REC_PREMIUM_DC").toString());
				         res3.setTdsDc(tempMap.get("TDS_DC")==null?"":tempMap.get("TDS_DC").toString());
				         res3.setStDc(tempMap.get("ST_DC")==null?"":tempMap.get("ST_DC").toString());
				         res3.setTotalCrDc(tempMap.get("TOTAL_CR_DC")==null?"":tempMap.get("TOTAL_CR_DC").toString());
				         res3.setBrokerageAmtDc(tempMap.get("BROKERAGE_AMT_DC")==null?"":tempMap.get("BROKERAGE_AMT_DC").toString());
				         res3.setTaxAmtDc(tempMap.get("TAX_AMT_DC")==null?"":tempMap.get("TAX_AMT_DC").toString());
				         res3.setOtherCostDc(tempMap.get("OTHER_COST_DC")==null?"":tempMap.get("OTHER_COST_DC").toString());
				         res3.setWithHoldingTaxDc(tempMap.get("WITH_HOLDING_TAX_DC")==null?"":tempMap.get("WITH_HOLDING_TAX_DC").toString());
				         res3.setBonusDc(tempMap.get("BONUS_DC")==null?"":tempMap.get("BONUS_DC").toString());
				         res3.setTotalDrDc(tempMap.get("TOTAL_DR_DC")==null?"":tempMap.get("TOTAL_DR_DC").toString());
				         res3.setNetdueDc(tempMap.get("NETDUE_DC")==null?"":tempMap.get("NETDUE_DC").toString());
				         res3.setAllocatedTillDateDc(tempMap.get("ALLOCATED_TILL_DATE_DC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_DC").toString());
				         res3.setAllcationPendingDc(tempMap.get("ALLCATION_PENDING_DC")==null?"":tempMap.get("ALLCATION_PENDING_DC").toString());
				         res3.setRemarks(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
				         res3.setSettlementStatus(tempMap.get("SETTLEMENT_STATUS")==null?"":tempMap.get("SETTLEMENT_STATUS").toString());
				         res3.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
				         res3.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
				         res3.setInstallmentDate(tempMap.get("INSTALLMENT_DATE")==null?"":tempMap.get("INSTALLMENT_DATE").toString());
				         res3.setEndorsementDate(tempMap.get("ENDORSEMENT_DATE")==null?"":tempMap.get("ENDORSEMENT_DATE").toString());
			
						   res3List.add(res3);
					
					}
					comRes.setIfProduct3(res3List);		
					}
				
			}else if("4".equalsIgnoreCase(bean.getProductId()) || "5".equalsIgnoreCase(bean.getProductId())){
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPremiumRegisterListRes4 res4 = new GetPremiumRegisterListRes4();
						res4.setRetroContractNo(tempMap.get("RETRO_CONTRACT_NO")==null?"":tempMap.get("RETRO_CONTRACT_NO").toString()); 
						res4.setRetroLayerNo(tempMap.get("RETRO_LAYER_NO")==null?"":tempMap.get("RETRO_LAYER_NO").toString()); 
						res4.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString()); 
						res4.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString()); 
						res4.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString()); 
						res4.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString()); 
						res4.setCurrencyName(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString()); 
						res4.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString()); 
						res4.setRetroPercentage(tempMap.get("RETRO_PERCENTAGE")==null?"":tempMap.get("RETRO_PERCENTAGE").toString()); 
						res4.setPremiumAmtDc(tempMap.get("PREMIUM_AMT_DC")==null?"":tempMap.get("PREMIUM_AMT_DC").toString()); 
						res4.setRetroPremiumDc(tempMap.get("RETRO_PREMIUM_DC")==null?"":tempMap.get("RETRO_PREMIUM_DC").toString()); 
						res4.setOverRider(tempMap.get("OVER_RIDER")==null?"":tempMap.get("OVER_RIDER").toString()); 
						res4.setNet(tempMap.get("NET")==null?"":tempMap.get("NET").toString()); 
						res4.setType(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString()); 
						res4.setClaimPaidDc(tempMap.get("CLAIM_PAID_DC")==null?"":tempMap.get("CLAIM_PAID_DC").toString()); 
						res4.setRetroClaimPaidDc(tempMap.get("RETRO_CLAIM_PAID_DC")==null?"":tempMap.get("RETRO_CLAIM_PAID_DC").toString()); 
						res4.setProductId(tempMap.get("PRODUCT_ID")==null?"":tempMap.get("PRODUCT_ID").toString()); 
						res4.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString()); 
						res4.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
						
						 res4List.add(res4);
					}
					comRes.setIfProduct4(res4List);		
					}
				
			}
			response.setCommonResponse(comRes);
			response.setMessage("Success");
			response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}
		@Override
		public GetClaimRegisterListRes getClaimRegisterList(GetClaimRegisterListReq bean) {
			GetClaimRegisterListRes response = new GetClaimRegisterListRes();
			 List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			 List<GetClaimRegisterListRes1> resList = new ArrayList<GetClaimRegisterListRes1>();
			try{
			String[] obj= new String[0];
			String query="";
			query  = "GET_CLAIM_REG_REPORT";
			obj= new String[7];
			obj[0] = bean.getProductId();
			obj[1]=bean.getEndDate();
			obj[2]=bean.getBranchCode();
			obj[3]=bean.getLoginId();
			obj[4] =bean.getBrokerId();
			obj[5] =bean.getCedingId();
			obj[6] = bean.getUwYear();
			list = queryImpl.selectList(query,obj);
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetClaimRegisterListRes1 res = new GetClaimRegisterListRes1();
					res.setClaimNo(tempMap.get("CLAIM_NO")==null?"":tempMap.get("CLAIM_NO").toString());
					res.setSlNo(tempMap.get("SL_NO")==null?"":tempMap.get("SL_NO").toString());
					res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
					res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
					res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					res.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
					res.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
					res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
					res.setStatusOfClaim(tempMap.get("STATUS_OF_CLAIM")==null?"":tempMap.get("STATUS_OF_CLAIM").toString());
					res.setContractClass(tempMap.get("CONTRACT_CLASS")==null?"":tempMap.get("CONTRACT_CLASS").toString());
					res.setClaimClass(tempMap.get("CLAIM_CLASS")==null?"":tempMap.get("CLAIM_CLASS").toString());
					res.setClaimSubclass(tempMap.get("CLAIM_SUBCLASS")==null?"":tempMap.get("CLAIM_SUBCLASS").toString());
					res.setDateOfLoss(tempMap.get("DATE_OF_LOSS")==null?"":tempMap.get("DATE_OF_LOSS").toString());
					res.setReportDate(tempMap.get("REPORT_DATE")==null?"":tempMap.get("REPORT_DATE").toString());
					res.setCreatedDate(tempMap.get("CREATED_DATE")==null?"":tempMap.get("CREATED_DATE").toString());
					res.setClaimClosedDate(tempMap.get("CLAIM_CLOSED_DATE")==null?"":tempMap.get("CLAIM_CLOSED_DATE").toString());
					res.setReopenedDate(tempMap.get("REOPENED_DATE")==null?"":tempMap.get("REOPENED_DATE").toString());
					res.setLossDetails(tempMap.get("LOSS_DETAILS")==null?"":tempMap.get("LOSS_DETAILS").toString());
					res.setCauseOfLoss(tempMap.get("CAUSE_OF_LOSS")==null?"":tempMap.get("CAUSE_OF_LOSS").toString());
					res.setLocation(tempMap.get("LOCATION")==null?"":tempMap.get("LOCATION").toString());
					res.setCedentClaimNo(tempMap.get("CEDENT_CLAIM_NO")==null?"":tempMap.get("CEDENT_CLAIM_NO").toString());
					res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
					res.setInsuredName(tempMap.get("INSURED_NAME")==null?"":tempMap.get("INSURED_NAME").toString());
					res.setGrosslossFguOc(tempMap.get("GROSSLOSS_FGU_OC")==null?"":tempMap.get("GROSSLOSS_FGU_OC").toString());
					res.setLossEstimateOc(tempMap.get("LOSS_ESTIMATE_OC")==null?"":tempMap.get("LOSS_ESTIMATE_OC").toString());
					res.setRecordFeesCreReserve(tempMap.get("RECORD_FEES_CRE_RESERVE")==null?"":tempMap.get("RECORD_FEES_CRE_RESERVE").toString());
					res.setSaf100Oc(tempMap.get("SAF_100_OC")==null?"":tempMap.get("SAF_100_OC").toString());
					res.setOthFee100Oc(tempMap.get("OTH_FEE_100_OC")==null?"":tempMap.get("OTH_FEE_100_OC").toString());
					res.setRecordIbnr(tempMap.get("RECORD_IBNR")==null?"":tempMap.get("RECORD_IBNR").toString());
					res.setCIbnr100Oc(tempMap.get("C_IBNR_100_OC")==null?"":tempMap.get("C_IBNR_100_OC").toString());
					res.setShareSigned(tempMap.get("SHARE_SIGNED")==null?"":tempMap.get("SHARE_SIGNED").toString());
					res.setLossEstimateOsOc(tempMap.get("LOSS_ESTIMATE_OS_OC")==null?"":tempMap.get("LOSS_ESTIMATE_OS_OC").toString());
					res.setSafOsOc(tempMap.get("SAF_OS_OC")==null?"":tempMap.get("SAF_OS_OC").toString());
					res.setOthFeeOsOc(tempMap.get("OTH_FEE_OS_OC")==null?"":tempMap.get("OTH_FEE_OS_OC").toString());
					res.setCIbnrOsOc(tempMap.get("C_IBNR_OS_OC")==null?"":tempMap.get("C_IBNR_OS_OC").toString());
					res.setRegExchangeRate(tempMap.get("REG_EXCHANGE_RATE")==null?"":tempMap.get("REG_EXCHANGE_RATE").toString());
					res.setLossEstimateDc(tempMap.get("LOSS_ESTIMATE_DC")==null?"":tempMap.get("LOSS_ESTIMATE_DC").toString());
					res.setSaf100Dc(tempMap.get("SAF_100_DC")==null?"":tempMap.get("SAF_100_DC").toString());
					res.setOthFee100Dc(tempMap.get("OTH_FEE_100_DC")==null?"":tempMap.get("OTH_FEE_100_DC").toString());
					res.setCIbnr100Dc(tempMap.get("C_IBNR_100_DC")==null?"":tempMap.get("C_IBNR_100_DC").toString());
					res.setLossEstimateOsDc(tempMap.get("LOSS_ESTIMATE_OS_DC")==null?"":tempMap.get("LOSS_ESTIMATE_OS_DC").toString());
					res.setSafOsDc(tempMap.get("SAF_OS_DC")==null?"":tempMap.get("SAF_OS_DC").toString());
					res.setOthFeeOsDc(tempMap.get("OTH_FEE_OS_DC")==null?"":tempMap.get("OTH_FEE_OS_DC").toString());
					res.setCIbnrOsDc(tempMap.get("C_IBNR_OS_DC")==null?"":tempMap.get("C_IBNR_OS_DC").toString());
					res.setRemarks(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
					res.setRiRecovery(tempMap.get("RI_RECOVERY")==null?"":tempMap.get("RI_RECOVERY").toString());
					res.setRiskCode(tempMap.get("RISK_CODE")==null?"":tempMap.get("RISK_CODE").toString());
					res.setAccumulationCode(tempMap.get("ACCUMULATION_CODE")==null?"":tempMap.get("ACCUMULATION_CODE").toString());
					res.setEventCode(tempMap.get("EVENT_CODE")==null?"":tempMap.get("EVENT_CODE").toString());
					res.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
					res.setProductId(tempMap.get("PRODUCT_ID")==null?"":tempMap.get("PRODUCT_ID").toString());
					res.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
					res.setPtdClaimOsOc(tempMap.get("PTD_CLAIM_OS_OC")==null?"":tempMap.get("PTD_CLAIM_OS_OC").toString());
					res.setPtdSafOsOc(tempMap.get("PTD_SAF_OS_OC")==null?"":tempMap.get("PTD_SAF_OS_OC").toString());
					res.setPtdOthOsOc(tempMap.get("PTD_OTH_OS_OC")==null?"":tempMap.get("PTD_OTH_OS_OC").toString());
					res.setPtdTotClaimOsOc(tempMap.get("PTD_TOT_CLAIM_OS_OC")==null?"":tempMap.get("PTD_TOT_CLAIM_OS_OC").toString());
					res.setResClaimOc(tempMap.get("RES_CLAIM_OC")==null?"":tempMap.get("RES_CLAIM_OC").toString());
					res.setResSafOc(tempMap.get("RES_SAF_OC")==null?"":tempMap.get("RES_SAF_OC").toString());
					res.setResOthOc(tempMap.get("RES_OTH_OC")==null?"":tempMap.get("RES_OTH_OC").toString());
					res.setResPosOc(tempMap.get("RES_POS_OC")==null?"":tempMap.get("RES_POS_OC").toString());
					res.setBkdExchangeRate(tempMap.get("BKD_EXCHANGE_RATE")==null?"":tempMap.get("BKD_EXCHANGE_RATE").toString());
					res.setPtdClaimOsDc(tempMap.get("PTD_CLAIM_OS_DC")==null?"":tempMap.get("PTD_CLAIM_OS_DC").toString());
					res.setPtdSafOsDc(tempMap.get("PTD_SAF_OS_DC")==null?"":tempMap.get("PTD_SAF_OS_DC").toString());
					res.setPtdOthOsDc(tempMap.get("PTD_OTH_OS_DC")==null?"":tempMap.get("PTD_OTH_OS_DC").toString());
					res.setPtdTotClaimOsDc(tempMap.get("PTD_TOT_CLAIM_OS_DC")==null?"":tempMap.get("PTD_TOT_CLAIM_OS_DC").toString());
					res.setResClaimDc(tempMap.get("RES_CLAIM_DC")==null?"":tempMap.get("RES_CLAIM_DC").toString());
					res.setResSafDc(tempMap.get("RES_SAF_DC")==null?"":tempMap.get("RES_SAF_DC").toString());
					res.setResOthDc(tempMap.get("RES_OTH_DC")==null?"":tempMap.get("RES_OTH_DC").toString());
					res.setResPosDc(tempMap.get("RES_POS_DC")==null?"":tempMap.get("RES_POS_DC").toString());
					res.setRestExchangeRate(tempMap.get("REST_EXCHANGE_RATE")==null?"":tempMap.get("REST_EXCHANGE_RATE").toString());
					res.setRestClaimDc(tempMap.get("REST_CLAIM_DC")==null?"":tempMap.get("REST_CLAIM_DC").toString());
					res.setRestSafDc(tempMap.get("REST_SAF_DC")==null?"":tempMap.get("REST_SAF_DC").toString());
					res.setRestOthDc(tempMap.get("REST_OTH_DC")==null?"":tempMap.get("REST_OTH_DC").toString());
					res.setRestPosDc(tempMap.get("REST_POS_DC")==null?"":tempMap.get("REST_POS_DC").toString());
					res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
					resList.add(res);
				}}
			response.setCommonResponse(resList);
			response.setMessage("Success");
					response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}

		@Override
		public GetRenewalDueListRes getRenewalDueList(ReportsCommonReq bean) {
			GetRenewalDueListRes response = new GetRenewalDueListRes();
			GetRenewalDueListComRes comRes = new GetRenewalDueListComRes();
		
			List<GetRenewalDueListRes1> res1List = new ArrayList<GetRenewalDueListRes1>();
			List<GetRenewalDueListRes2> res2List = new ArrayList<GetRenewalDueListRes2>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			String query="";
			String[] obj= new String[0];
			String qutext ="";
			try {
			if("1".equalsIgnoreCase(bean.getProductId())){
				//3diff queries
				query= "reportdao.select.renewalDueFac";
				qutext = prop.getProperty(query);
				obj=new String[6];
				obj[0]=bean.getProductId();
				obj[1]=bean.getBranchCode();
				obj[2]=bean.getBranchCode();
				obj[3]=bean.getBranchCode();
				obj[4]=bean.getStartDate();
				obj[5]=bean.getEndDate();
				if(!"-1".equals(bean.getLoginId())){
					obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
				}
				if(!"-1".equals(bean.getBrokerId())){
					obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
				}
				if(!"-1".equals(bean.getCedingId())){
					obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
				}
				if(!"-1".equals(bean.getUwYear())){
					obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
					qutext += " " +prop.getProperty("report.select.reportUWYear");
				}
				qutext += " " +prop.getProperty("reportdao.select.renewalOrder");
//			}else if("2".equalsIgnoreCase(bean.getProductId()) || "3".equalsIgnoreCase(bean.getProductId())){
//				query="reportda.select.xolRenewalDueList";
//				qutext = prop.getProperty(query);
//				obj=new String[4];
//				obj[0]=bean.getBranchCode();
//				obj[1]=bean.getProductId();
//				obj[2]=bean.getStartDate();
//				obj[3]=bean.getEndDate();
//				if(!"-1".equals(bean.getLoginId())){
//					obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
//					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
//				}
//				if(!"-1".equals(bean.getBrokerId())){
//					obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
//					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
//				}
//				if(!"-1".equals(bean.getCedingId())){
//					obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
//					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
//				}
//				if(!"-1".equals(bean.getUwYear())){
//					obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
//					qutext += " " +prop.getProperty("report.select.reportUWYear");
//				}
			}else if("4".equalsIgnoreCase(bean.getProductId()) || "5".equalsIgnoreCase(bean.getProductId())){
				query = "reportda.select.retroRetroxol";
				qutext = prop.getProperty(query);
				obj=new String[6];
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getBranchCode();
				obj[2]=bean.getProductId();
				obj[3]=bean.getDept();
				obj[4]=bean.getStartDate();
				obj[5]=bean.getEndDate();
				if(!"-1".equals(bean.getLoginId())){
					obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
				}
				if(!"-1".equals(bean.getBrokerId())){
					obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
				}
				if(!"-1".equals(bean.getCedingId())){
					obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
				}
				if(!"-1".equals(bean.getUwYear())){
					obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
					qutext += " " +prop.getProperty("report.select.reportUWYear");
				}
			}else{
				query= "reportda.select.xolRenewalDueList";
				qutext = prop.getProperty(query);
				obj=new String[4];
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getProductId();
				obj[2]=bean.getStartDate();
				obj[3]=bean.getEndDate();
				if(!"-1".equals(bean.getLoginId())){
					obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterLoginId");
				}
				if(!"-1".equals(bean.getBrokerId())){
					obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterBrokerId");
				}
				if(!"-1".equals(bean.getCedingId())){
					obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
					qutext += " " +prop.getProperty("reportdao.select.facgetQuoteRegisterCedingId");
				}
				if(!"-1".equals(bean.getUwYear())){
					obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
					qutext += " " +prop.getProperty("report.select.reportUWYear");
				}
			}
			query1 =queryImpl.setQueryProp(qutext, obj);
			query1.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			try {
				 list = query1.getResultList();
			} catch(Exception e) {
				e.printStackTrace();
			} 
			if("1".equalsIgnoreCase(bean.getProductId())){
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetRenewalDueListRes1 res1 = new GetRenewalDueListRes1();
						res1.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
						res1.setRskUwyear(tempMap.get("RSK_UWYEAR")==null?"":tempMap.get("RSK_UWYEAR").toString());
						res1.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res1.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
						res1.setTerritoryDesc(tempMap.get("TERRITORY_DESC")==null?"":tempMap.get("TERRITORY_DESC").toString());
						res1.setBrokerName(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
						res1.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
						res1.setRskInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
						res1.setIncDate(tempMap.get("INC_DATE")==null?"":tempMap.get("INC_DATE").toString());
						res1.setExpDate(tempMap.get("EXP_DATE")==null?"":tempMap.get("EXP_DATE").toString());
						res1.setShareSigned(tempMap.get("SHARE_SIGNED")==null?"":tempMap.get("SHARE_SIGNED").toString());
						
						res1List.add(res1);
						}
					comRes.setIfProduct1(res1List);		
					}
				
			}else {
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetRenewalDueListRes2 res2 = new GetRenewalDueListRes2();
						res2.setRskProposalNumber(tempMap.get("RSK_PROPOSAL_NUMBER")==null?"":tempMap.get("RSK_PROPOSAL_NUMBER").toString());
						res2.setRskUwyear(tempMap.get("RSK_UWYEAR	RSK_UWYEAR").toString());
						res2.setRskContractNo(tempMap.get("RSK_CONTRACT_NO")==null?"":tempMap.get("RSK_CONTRACT_NO ").toString());
						res2.setRskLayerNo(tempMap.get("RSK_LAYER_NO")==null?"":tempMap.get("RSK_LAYER_NO").toString());
						res2.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
						res2.setTerritoryDesc(tempMap.get("TERRITORY_DESC")==null?"":tempMap.get("TERRITORY_DESC").toString());
						res2.setBrokerName(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
						res2.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME ").toString());
						res2.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());
						res2.setIncDate(tempMap.get("INC_DATE")==null?"":tempMap.get("INC_DATE").toString());
						res2.setExpDate(tempMap.get("EXP_DATE")==null?"":tempMap.get("EXP_DATE").toString());
						res2.setShareSigned(tempMap.get("SHARE_SIGNED")==null?"":tempMap.get("SHARE_SIGNED ").toString());
						res2.setRskEpiOsofOc(tempMap.get("RSK_EPI_OSOF_OC")==null?"":tempMap.get("RSK_EPI_OSOF_OC").toString());
						res2.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
						
						res2List.add(res2);
						}
					comRes.setIfProductAny(res2List);
					}
				
			}
			response.setCommonResponse(comRes);
			response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}


		@Override
		public GetRetroQuarterlyReport getRetroQuarterlyReport(ReportsCommonReq bean) {
			GetRetroQuarterlyReport response = new GetRetroQuarterlyReport();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			List<GetRetroQuarterlyReport1> resList = new ArrayList<GetRetroQuarterlyReport1>();
			String query="";
			String qutext = "";
			String[] obj= new String[0];
			try {
			query="report.select.retroQuarRDS";
			qutext = prop.getProperty(query);
			obj=new String[6];
			obj[0]=bean.getBranchCode();
			obj[1]=bean.getProductId();
			obj[2]=bean.getDept();
			obj[3]=bean.getStartDate();
			obj[4]=bean.getEndDate();
			obj[5]=bean.getBranchCode();
			if(!"-1".equals(bean.getLoginId())){
				obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacLoginId");
			}
			if(!"-1".equals(bean.getBrokerId())){
				obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacBrokerId");
			}
			if(!"-1".equals(bean.getCedingId())){
				obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
				qutext += " " +prop.getProperty("report.select.policyRegFacCedingId");
			}
			query1 =queryImpl.setQueryProp(qutext, obj);
			query1.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			try {
				list = query1.getResultList();
			} catch(Exception e) {
				e.printStackTrace();
			} 
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetRetroQuarterlyReport1 res = new GetRetroQuarterlyReport1();
					res.setAdjustmentPremiumDc(tempMap.get("ADJUSTMENT_PREMIUM_DC")==null?"":tempMap.get("ADJUSTMENT_PREMIUM_DC").toString());
					res.setAdjustmentPremiumOc(tempMap.get("ADJUSTMENT_PREMIUM_OC")==null?"":tempMap.get("ADJUSTMENT_PREMIUM_OC").toString());
					res.setBrokerageAmtDc(tempMap.get("BROKERAGE_AMT_DC")==null?"":tempMap.get("BROKERAGE_AMT_DC").toString());
					res.setBrokerageAmtOc(tempMap.get("BROKERAGE_AMT_OC")==null?"":tempMap.get("BROKERAGE_AMT_OC").toString());					
					res.setBrokerName(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
					res.setCashLossCreditDc(tempMap.get("CASH_LOSS_CREDIT_DC")==null?"":tempMap.get("CASH_LOSS_CREDIT_DC").toString());
					res.setCashLossCreditOc(tempMap.get("CASH_LOSS_CREDIT_OC")==null?"":tempMap.get("CASH_LOSS_CREDIT_OC").toString());					
					res.setCashLosspaidDc(tempMap.get("CASH_LOSSPAID_DC")==null?"":tempMap.get("CASH_LOSSPAID_DC").toString());
					res.setCashLosspaidOc(tempMap.get("CASH_LOSSPAID_OC")==null?"":tempMap.get("CASH_LOSSPAID_OC").toString());
					res.setClaimPortfolioinDc(tempMap.get("CLAIM_PORTFOLIOIN_DC")==null?"":tempMap.get("CLAIM_PORTFOLIOIN_DC").toString());
					res.setClaimPortfolioinOc(tempMap.get("CLAIM_PORTFOLIOIN_OC")==null?"":tempMap.get("CLAIM_PORTFOLIOIN_OC").toString());			
					res.setClaimPortfolioOutDc(tempMap.get("CLAIM_PORTFOLIO_OUT_DC")==null?"":tempMap.get("CLAIM_PORTFOLIO_OUT_DC").toString());
					res.setClaimPortfolioOutOc(tempMap.get("CLAIM_PORTFOLIO_OUT_OC")==null?"":tempMap.get("CLAIM_PORTFOLIO_OUT_OC").toString());
					res.setClaimsPaidDc(tempMap.get("CLAIMS_PAID_DC")==null?"":tempMap.get("CLAIMS_PAID_DC").toString());
					res.setClaimsPaidOc(tempMap.get("CLAIMS_PAID_OC")==null?"":tempMap.get("CLAIMS_PAID_OC").toString());
					res.setCommissionOc(tempMap.get("COMMISSIONOC")==null?"":tempMap.get("COMMISSIONOC").toString());
					res.setCommissionQuotashareDc(tempMap.get("COMMISSION_QUOTASHARE_DC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_DC").toString());
					res.setCommissionQuotashareOc(tempMap.get("COMMISSION_QUOTASHARE_OC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_OC").toString());
					res.setCommissionSurplusDc(tempMap.get("COMMISSION_SURPLUS_DC")==null?"":tempMap.get("COMMISSION_SURPLUS_DC").toString());
					res.setCommissionSurplusOc(tempMap.get("COMMISSION_SURPLUS_OC")==null?"":tempMap.get("COMMISSION_SURPLUS_OC").toString());	
					res.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());		
					res.setContractNo(tempMap.get("ACC_DATE")==null?"":tempMap.get("ACC_DATE").toString());
					res.setDocType(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					res.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());		
					res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
					res.setInterestDc(tempMap.get("INTEREST_DC")==null?"":tempMap.get("INTEREST_DC").toString());
					res.setInterestOc(tempMap.get("INTEREST_OC")==null?"":tempMap.get("INTEREST_OC").toString());
					res.setInwardtype(tempMap.get("INWARDTYPE")==null?"":tempMap.get("INWARDTYPE").toString());
					res.setInwuwYear(tempMap.get("INWUW_YEAR")==null?"":tempMap.get("INWUW_YEAR").toString());
					res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
					res.setLossReserveReleasedDc(tempMap.get("LOSS_RESERVE_RELEASED_DC")==null?"":tempMap.get("LOSS_RESERVE_RELEASED_DC").toString());
					res.setLossReserveReleasedOc(tempMap.get("LOSS_RESERVE_RELEASED_OC")==null?"":tempMap.get("LOSS_RESERVE_RELEASED_OC").toString());
					res.setLossReserveRetainedDc(tempMap.get("LOSS_RESERVERETAINED_DC")==null?"":tempMap.get("LOSS_RESERVERETAINED_DC").toString());
					res.setLossReserveretainedOc(tempMap.get("LOSS_RESERVERETAINED_OC")==null?"":tempMap.get("LOSS_RESERVERETAINED_OC").toString());
					res.setMdPremiumDc(tempMap.get("M_DPREMIUM_DC")==null?"":tempMap.get("M_DPREMIUM_DC").toString());
					res.setMdPremiumOc(tempMap.get("M_DPREMIUM_OC")==null?"":tempMap.get("M_DPREMIUM_OC").toString());
					res.setNetdueDc(tempMap.get("NETDUE_DC")==null?"":tempMap.get("NETDUE_DC").toString());	
					res.setNetdueOc(tempMap.get("NETDUEOC")==null?"":tempMap.get("NETDUEOC").toString());	
					res.setNetdueUgx(tempMap.get("NETDUE_UGX")==null?"":tempMap.get("NETDUE_UGX").toString());
					res.setOtherCostDc(tempMap.get("OTHER_COST_DC")==null?"":tempMap.get("OTHER_COST_DC").toString());
					res.setOtherCostOc(tempMap.get("OTHER_COST_OC")==null?"":tempMap.get("OTHER_COST_OC").toString());
					res.setOutwardoverriderOc(tempMap.get("OUTWARDOVERRIDER_OC")==null?"":tempMap.get("OUTWARDOVERRIDER_OC").toString());
					res.setOutwaroverriderDc(tempMap.get("OUTWAROVERRIDER_DC")==null?"":tempMap.get("OUTWAROVERRIDER_DC").toString());
					res.setOverriderAmtDc(tempMap.get("OVERRIDER_AMT_DC")==null?"":tempMap.get("OVERRIDER_AMT_DC").toString());
					res.setOverriderAmtOc(tempMap.get("OVERRIDER_AMT_OC")==null?"":tempMap.get("OVERRIDER_AMT_OC").toString());
					res.setPremiumOc(tempMap.get("PREMIUM_OC")==null?"":tempMap.get("PREMIUM_OC").toString());	
					res.setPremiumPortfolioinDc(tempMap.get("PREMIUM_PORTFOLIOIN_DC")==null?"":tempMap.get("PREMIUM_PORTFOLIOIN_DC").toString());
					res.setPremiumPortfolioinOc(tempMap.get("PREMIUM_PORTFOLIOIN_OC")==null?"":tempMap.get("PREMIUM_PORTFOLIOIN_OC").toString());
					res.setPremiumPortfoliooutDc(tempMap.get("PREMIUM_PORTFOLIOOUT_DC")==null?"":tempMap.get("PREMIUM_PORTFOLIOOUT_DC").toString());
					res.setPremiumPortfoliooutOc(tempMap.get("PREMIUM_PORTFOLIOOUT_OC")==null?"":tempMap.get("PREMIUM_PORTFOLIOOUT_OC").toString());
					res.setPremiumQuotashareDc(tempMap.get("PREMIUM_QUOTASHARE_DC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_DC").toString());
					res.setPremiumQuotashareOc(tempMap.get("PREMIUM_QUOTASHARE_OC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_OC").toString());		
					res.setPremiumReserveRealsedDc(tempMap.get("PREMIUM_RESERVE_REALSED_DC")==null?"":tempMap.get("PREMIUM_RESERVE_REALSED_DC").toString());
					res.setPremiumReserveRealsedOc(tempMap.get("PREMIUM_RESERVE_REALSED_OC")==null?"":tempMap.get("PREMIUM_RESERVE_REALSED_OC").toString());
					res.setPremiumReserveRetainedDc(tempMap.get("PREMIUM_RESERVE_RETAINED_DC")==null?"":tempMap.get("PREMIUM_RESERVE_RETAINED_DC").toString());
					res.setPremiumReserveRetainedOc(tempMap.get("PREMIUM_RESERVE_RETAINED_OC")==null?"":tempMap.get("PREMIUM_RESERVE_RETAINED_OC").toString());
					res.setPremiumSurplusDc(tempMap.get("PREMIUM_SURPLUS_DC")==null?"":tempMap.get("PREMIUM_SURPLUS_DC").toString());
					res.setPremiumSurplusOc(tempMap.get("PREMIUM_SURPLUS_OC")==null?"":tempMap.get("PREMIUM_SURPLUS_OC").toString());
					res.setPremiumTranNo(tempMap.get("PREMIUM_TRAN_NO")==null?"":tempMap.get("PREMIUM_TRAN_NO").toString());
					res.setPremiumUgx(tempMap.get("PREMIUMUGX")==null?"":tempMap.get("PREMIUMUGX").toString());
					res.setProfitCommissionDc(tempMap.get("PROFIT_COMMISSION_DC")==null?"":tempMap.get("PROFIT_COMMISSION_DC").toString());
					res.setProfitcommissionoc(tempMap.get("PROFITCOMMISSIONOC")==null?"":tempMap.get("PROFITCOMMISSIONOC").toString());
					res.setRecPremiumDc(tempMap.get("REC_PREMIUM_DC")==null?"":tempMap.get("REC_PREMIUM_DC").toString());
					res.setRecPremiumOc(tempMap.get("REC_PREMIUM_OC")==null?"":tempMap.get("REC_PREMIUM_OC").toString());
					res.setRetrobroker(tempMap.get("RETROBROKER")==null?"":tempMap.get("RETROBROKER").toString());
					res.setRetroCeding(tempMap.get("RETRO_CEDING")==null?"":tempMap.get("RETRO_CEDING").toString());
					res.setRetroContractNo(tempMap.get("RETRO_CONTRACT_NO")==null?"":tempMap.get("RETRO_CONTRACT_NO").toString());
					res.setRetroExpiry(tempMap.get("RETROEXPIRY")==null?"":tempMap.get("RETROEXPIRY").toString());
					res.setRetroInception(tempMap.get("RETROINCEPTION")==null?"":tempMap.get("RETROINCEPTION").toString());
					res.setRetroLayerNo(tempMap.get("RETRO_LAYER_NO")==null?"":tempMap.get("RETRO_LAYER_NO").toString());
					res.setRetroPercentage(tempMap.get("RETRO_PERCENTAGE")==null?"":tempMap.get("RETRO_PERCENTAGE").toString());
					res.setRetroUwy(tempMap.get("RETROUWY")==null?"":tempMap.get("RETROUWY").toString());
					res.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());
					res.setSubclass(tempMap.get("SUBCLASS")==null?"":tempMap.get("SUBCLASS").toString());
					res.setTaxAmtDc(tempMap.get("TAX_AMT_DC")==null?"":tempMap.get("TAX_AMT_DC").toString());
					res.setTaxAmtOc(tempMap.get("TAX_AMT_OC")==null?"":tempMap.get("TAX_AMT_OC").toString());
					res.setTotalCrDc(tempMap.get("TOTAL_CR_DC")==null?"":tempMap.get("TOTAL_CR_DC").toString());
					res.setTotalCrOc(tempMap.get("TOTAL_CR_OC")==null?"":tempMap.get("TOTAL_CR_OC").toString());
					res.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
					res.setTransactiontype(tempMap.get("TRANSACTIONTYPE")==null?"":tempMap.get("TRANSACTIONTYPE").toString());
					res.setXlCostDc(tempMap.get("XL_COST_DC")==null?"":tempMap.get("XL_COST_DC").toString());					
					res.setXlCostOc(tempMap.get("XL_COST_OC")==null?"":tempMap.get("XL_COST_OC").toString());
					resList.add(res);
				}}
			response.setCommonResponse(resList);
			response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}
		@Override
		public GetInwardRetroMappingReportRes getInwardRetroMappingReport(GetInwardRetroMappingReportReq bean) {
			GetInwardRetroMappingReportRes response = new GetInwardRetroMappingReportRes();
			List<GetInwardRetroMappingReportRes1> resList = new ArrayList<GetInwardRetroMappingReportRes1>();
			List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
			String query="";
			String qutext = "";
			String[] obj= new String[0];
			try {
			if("1".equalsIgnoreCase(bean.getProductId())){
				query= "reportdao.select.getFacRetroList";
				qutext = prop.getProperty(query);
				obj=new String[3];
				obj[0]=bean.getStartDate();
				obj[1]=bean.getEndDate();
				obj[2]=bean.getBranchCode();
				if(!"-1".equals(bean.getLoginId())){
					obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacLoginId");
				}
				if(!"-1".equals(bean.getBrokerId())){
					obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacBrokerId");
				}
				if(!"-1".equals(bean.getCedingId())){
					obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacCedingId");
				}
				if(!"-1".equals(bean.getUwYear())){
					obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
					qutext += " " +prop.getProperty("report.select.policyRegFacUWYear");
				}
				qutext += " " +prop.getProperty("reportdao.select.getFacRetroOrderBy");
			}else {
				query= "reportdao.select.getTreatyXOLRetroList";
				qutext = prop.getProperty(query);
				obj=new String[4];
				obj[0]=bean.getProductId();
				obj[1]=bean.getStartDate();
				obj[2]=bean.getEndDate();
				obj[3]=bean.getBranchCode();
				if(!"-1".equals(bean.getLoginId())){
					obj=getIncObjectArray(obj,new String[]{bean.getLoginId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacLoginId");
				}
				if(!"-1".equals(bean.getBrokerId())){
					obj=getIncObjectArray(obj,new String[]{bean.getBrokerId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacBrokerId");
				}
				if(!"-1".equals(bean.getCedingId())){
					obj=getIncObjectArray(obj,new String[]{bean.getCedingId()});
					qutext += " " +prop.getProperty("report.select.policyRegFacCedingId");

				}
				if(!"-1".equals(bean.getUwYear())){
					obj=getIncObjectArray(obj,new String[]{bean.getUwYear()});
					qutext += " " +prop.getProperty("report.select.policyRegFacUWYear");
				}
				qutext += " " +prop.getProperty("reportdao.select.getFacRetroOrderBy");
			}
			query1 =queryImpl.setQueryProp(qutext, obj);
			query1.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
			try {
				list = query1.getResultList();
			} catch(Exception e) {
				e.printStackTrace();
			} 
			if(list!=null && list.size()>0){
				if("1".equalsIgnoreCase(bean.getProductId())){
				for(Map<String, Object> tempMap : list) {
					GetInwardRetroMappingReportRes1 res = new GetInwardRetroMappingReportRes1();
					res.setAccountDate(tempMap.get("ACCOUNT_DATE")==null?"":tempMap.get("ACCOUNT_DATE").toString());
					res.setBrokerId(tempMap.get("BROKER_ID")==null?"":tempMap.get("BROKER_ID").toString());
					res.setCedingCompanyId(tempMap.get("CEDING_COMPANY_ID")==null?"":tempMap.get("CEDING_COMPANY_ID").toString());
					res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					res.setDeptId(tempMap.get("DEPT_ID")==null?"":tempMap.get("DEPT_ID").toString());
					res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
					res.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
					res.setNet(tempMap.get("NET")==null?"":tempMap.get("NET").toString());
					res.setOverRider(tempMap.get("OVER_RIDER")==null?"":tempMap.get("OVER_RIDER").toString());
					res.setOverRiderPer(tempMap.get("OVER_RIDER_PER")==null?"":tempMap.get("OVER_RIDER_PER").toString());
					res.setOwiCommission(tempMap.get("OWI_COMMISSION")==null?"":tempMap.get("OWI_COMMISSION").toString());
					res.setOwiOtherCharges(tempMap.get("OWI_OTHER_CHARGES")==null?"":tempMap.get("OWI_OTHER_CHARGES").toString());
					res.setOwiPremiumUsd(tempMap.get("OWI_PREMIUM_USD")==null?"":tempMap.get("OWI_PREMIUM_USD").toString());
					res.setRetroContractNo(tempMap.get("RETRO_CONTRACT_NO")==null?"":tempMap.get("RETRO_CONTRACT_NO").toString());
					res.setRetroLayerNo(tempMap.get("RETRO_LAYER_NO")==null?"":tempMap.get("RETRO_LAYER_NO").toString());
					res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					resList.add(res);
					}
				}else {
					for(Map<String, Object> tempMap : list) {
						GetInwardRetroMappingReportRes1 res = new GetInwardRetroMappingReportRes1();
						res.setAccountDate(tempMap.get("ACCOUNT_DATE")==null?"":tempMap.get("ACCOUNT_DATE").toString());
						res.setBrokerId(tempMap.get("BROKER_ID")==null?"":tempMap.get("BROKER_ID").toString());
						res.setCedingCompanyId(tempMap.get("CEDING_COMPANY_ID")==null?"":tempMap.get("CEDING_COMPANY_ID").toString());
						res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
						res.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
						res.setNet(tempMap.get("NET")==null?"":tempMap.get("NET").toString());
						res.setOverRider(tempMap.get("OVER_RIDER")==null?"":tempMap.get("OVER_RIDER").toString());
						res.setOverRiderPer(tempMap.get("OVER_RIDER_PER")==null?"":tempMap.get("OVER_RIDER_PER").toString());
						res.setOwiCommissionQs(tempMap.get("OWI_COMMISSION_QS")==null?"":tempMap.get("OWI_COMMISSION_QS").toString());
						res.setOwiCommissionSurplus(tempMap.get("OWI_COMMISSION_SURPLUS")==null?"":tempMap.get("OWI_COMMISSION_SURPLUS").toString());
						res.setOwiOtherCharges(tempMap.get("OWI_OTHER_CHARGES")==null?"":tempMap.get("OWI_OTHER_CHARGES").toString());
						res.setOwiPremiumUsd(tempMap.get("OWI_PREMIUM_USD")==null?"":tempMap.get("OWI_PREMIUM_USD").toString());
						res.setProductId(tempMap.get("PRODUCT_ID")==null?"":tempMap.get("PRODUCT_ID").toString());
						res.setRetroContractNo(tempMap.get("RETRO_CONTRACT_NO")==null?"":tempMap.get("RETRO_CONTRACT_NO").toString());
						res.setRetroLayerNo(tempMap.get("RETRO_LAYER_NO")==null?"":tempMap.get("RETRO_LAYER_NO").toString());
						res.setRetroPercentage(tempMap.get("RETRO_PERCENTAGE")==null?"":tempMap.get("RETRO_PERCENTAGE").toString());
						res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
						resList.add(res);
				} 
				}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}

		@Override
		public GetRetroInwardMappingReportRes getRetroInwardMappingReport(GetRetroInwardMappingReportReq bean) {
			GetRetroInwardMappingReportRes response = new GetRetroInwardMappingReportRes();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<GetRetroInwardMappingReportRes1> resList = new ArrayList<GetRetroInwardMappingReportRes1>();
			try {
				String[] obj = new String[3];
				String query = "";
				obj[0] = bean.getStartDate();
				obj[1] = bean.getEndDate();
				obj[2] = bean.getBranchCode();
				query = "GET_RETRO_RDS_REPORT";
				list = queryImpl.selectList(query, obj);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetRetroInwardMappingReportRes1 res = new GetRetroInwardMappingReportRes1();
						res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
					    res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					    res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
					    res.setProductName(tempMap.get("PRODUCT_NAME")==null?"":tempMap.get("PRODUCT_NAME").toString());
					    res.setDepartmentName(tempMap.get("DEPARTMENT_NAME")==null?"":tempMap.get("DEPARTMENT_NAME").toString());
					    res.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
					    res.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
					    res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					    res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
					    res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
					    res.setAccountDate(tempMap.get("ACCOUNT_DATE")==null?"":tempMap.get("ACCOUNT_DATE").toString());
					    res.setEndorsementDate(tempMap.get("ENDORSEMENT_DATE")==null?"":tempMap.get("ENDORSEMENT_DATE").toString());
					    res.setRetroContractNo(tempMap.get("RETRO_CONTRACT_NO")==null?"":tempMap.get("RETRO_CONTRACT_NO").toString());
					    res.setRetroContractPercentage(tempMap.get("RETRO_CONTRACT_PERCENTAGE")==null?"":tempMap.get("RETRO_CONTRACT_PERCENTAGE").toString());
					    res.setSno(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					    res.setRetroCessionaire(tempMap.get("RETRO_CESSIONAIRE")==null?"":tempMap.get("RETRO_CESSIONAIRE").toString());
					    res.setRetroBroker(tempMap.get("RETRO_BROKER")==null?"":tempMap.get("RETRO_BROKER").toString());
					    res.setRetCes(tempMap.get("RET_CES")==null?"":tempMap.get("RET_CES").toString());
					    res.setRetroCessionairePercentage(tempMap.get("RETRO_CESSIONAIRE_PERCENTAGE")==null?"":tempMap.get("RETRO_CESSIONAIRE_PERCENTAGE").toString());
					    res.setRetrocessionPercentage(tempMap.get("RETROCESSION_PERCENTAGE")==null?"":tempMap.get("RETROCESSION_PERCENTAGE").toString());
					    res.setRskRetroType(tempMap.get("RSK_RETRO_TYPE")==null?"":tempMap.get("RSK_RETRO_TYPE").toString());
					    res.setInwardCurrency(tempMap.get("INWARD_CURRENCY")==null?"":tempMap.get("INWARD_CURRENCY").toString());
					    res.setOutwardCurrency(tempMap.get("OUTWARD_CURRENCY")==null?"":tempMap.get("OUTWARD_CURRENCY").toString());
					    res.setShareSigned(tempMap.get("SHARE_SIGNED")==null?"":tempMap.get("SHARE_SIGNED").toString());
					    resList.add(res);
					}}
				response.setCommonResponse(resList);
				response.setMessage("Success");
					response.setIsError(false);
					} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
				return response;
		}

		@Override
		public GetTransactionMasterReportRes getTransactionMasterReport(GetTransactionMasterReportReq bean) {
			GetTransactionMasterReportRes response = new GetTransactionMasterReportRes();
			List<GetTransactionMasterReportRes1> resList = new ArrayList<GetTransactionMasterReportRes1>();
			String query="";
			String[] obj= new String[0];
			try {
			//Package or function FN_TRANSACTION_REPORT is in an invalid state
			query= "reportdao.select.getTransMasterList";
			obj=new String[8];
			obj[0]=bean.getStartDate();
			obj[1]=bean.getEndDate();
			obj[2]=bean.getBranchCode();
			obj[3]=bean.getDocType().replaceAll("\\s+","");
			obj[4]=bean.getBrokerId();
			obj[5]=bean.getCedingId();
			obj[6]=bean.getUwYear();
			obj[7]=bean.getContractNo();
			List<Map<String,Object>> list= queryImpl.selectList(query, obj);
			if(list!=null && list.size()>0){
				for(Map<String, Object> tempMap : list) {
					GetTransactionMasterReportRes1 res = new GetTransactionMasterReportRes1();
					res.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
					res.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
					res.setAmendmentDate(tempMap.get("AMENDMENT_DATE")==null?"":tempMap.get("AMENDMENT_DATE").toString());
					res.setTransactionMonth(tempMap.get("TRANSACTION_MONTH")==null?"":tempMap.get("TRANSACTION_MONTH").toString());
					res.setTransactionYear(tempMap.get("TRANSACTION_YEAR")==null?"":tempMap.get("TRANSACTION_YEAR").toString());
					res.setDocType(tempMap.get("DOC_TYPE")==null?"":tempMap.get("DOC_TYPE").toString());
					res.setCedantReference(tempMap.get("CEDANT_REFERENCE")==null?"":tempMap.get("CEDANT_REFERENCE").toString());
					res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					res.setRskSpfcid(tempMap.get("RSK_SPFCID")==null?"":tempMap.get("RSK_SPFCID").toString());
					res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
					res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
					res.setCompanyCode(tempMap.get("COMPANY_CODE")==null?"":tempMap.get("COMPANY_CODE").toString());
					res.setBrokerCode(tempMap.get("BROKER_CODE")==null?"":tempMap.get("BROKER_CODE").toString());
					res.setRskInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
					res.setLossDate(tempMap.get("LOSS_DATE")==null?"":tempMap.get("LOSS_DATE").toString());
					res.setClaimNo(tempMap.get("CLAIM_NO")==null?"":tempMap.get("CLAIM_NO").toString());
					res.setAccountPeriodQtr(tempMap.get("ACCOUNT_PERIOD_QTR")==null?"":tempMap.get("ACCOUNT_PERIOD_QTR").toString());
					res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
					res.setPremiumQuotashareOc(tempMap.get("PREMIUM_QUOTASHARE_OC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_OC").toString());
					res.setPremiumSurplusOc(tempMap.get("PREMIUM_SURPLUS_OC")==null?"":tempMap.get("PREMIUM_SURPLUS_OC").toString());
					res.setPremiumTotal(tempMap.get("PREMIUM_TOTAL")==null?"":tempMap.get("PREMIUM_TOTAL").toString());
					res.setXlCostOc(tempMap.get("XL_COST_OC")==null?"":tempMap.get("XL_COST_OC").toString());
					res.setPremiumPortfolioinOc(tempMap.get("PREMIUM_PORTFOLIOIN_OC")==null?"":tempMap.get("PREMIUM_PORTFOLIOIN_OC").toString());
					res.setPremiumPortfoliooutOc(tempMap.get("PREMIUM_PORTFOLIOOUT_OC")==null?"":tempMap.get("PREMIUM_PORTFOLIOOUT_OC").toString());
					res.setNetPremium(tempMap.get("NET_PREMIUM")==null?"":tempMap.get("NET_PREMIUM").toString());
					res.setCommissionQuotashareOc(tempMap.get("COMMISSION_QUOTASHARE_OC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_OC").toString());
					res.setCommissionSurplusOc(tempMap.get("COMMISSION_SURPLUS_OC")==null?"":tempMap.get("COMMISSION_SURPLUS_OC").toString());
					res.setCommissionTotal(tempMap.get("COMMISSION_TOTAL")==null?"":tempMap.get("COMMISSION_TOTAL").toString());
					res.setBrokerageAmtOc(tempMap.get("BROKERAGE_AMT_OC")==null?"":tempMap.get("BROKERAGE_AMT_OC").toString());
					res.setTaxAmtOc(tempMap.get("TAX_AMT_OC")==null?"":tempMap.get("TAX_AMT_OC").toString());
					res.setOverriderAmtOc(tempMap.get("OVERRIDER_AMT_OC")==null?"":tempMap.get("OVERRIDER_AMT_OC").toString());
					res.setOtherCostOc(tempMap.get("OTHER_COST_OC")==null?"":tempMap.get("OTHER_COST_OC").toString());
					res.setProfitCommissionOc(tempMap.get("PROFIT_COMMISSION_OC")==null?"":tempMap.get("PROFIT_COMMISSION_OC").toString());
					res.setTotalCost(tempMap.get("TOTAL_COST")==null?"":tempMap.get("TOTAL_COST").toString());
					res.setWithHoldingTaxOc(tempMap.get("WITH_HOLDING_TAX_OC")==null?"":tempMap.get("WITH_HOLDING_TAX_OC").toString());
					res.setPremiumReserveRetainedOc(tempMap.get("PREMIUM_RESERVE_RETAINED_OC")==null?"":tempMap.get("PREMIUM_RESERVE_RETAINED_OC").toString());
					res.setPremiumReserveRealsedOc(tempMap.get("PREMIUM_RESERVE_REALSED_OC")==null?"":tempMap.get("PREMIUM_RESERVE_REALSED_OC").toString());
					res.setLossReserveretainedOc(tempMap.get("LOSS_RESERVERETAINED_OC")==null?"":tempMap.get("LOSS_RESERVERETAINED_OC").toString());
					res.setLossReserveReleasedOc(tempMap.get("LOSS_RESERVE_RELEASED_OC")==null?"":tempMap.get("LOSS_RESERVE_RELEASED_OC").toString());
					res.setInterestOc(tempMap.get("INTEREST_OC")==null?"":tempMap.get("INTEREST_OC").toString());
					res.setClaimsPaidOc(tempMap.get("CLAIMS_PAID_OC")==null?"":tempMap.get("CLAIMS_PAID_OC").toString());
					res.setClaimRecoveryOc(tempMap.get("CLAIM_RECOVERY_OC")==null?"":tempMap.get("CLAIM_RECOVERY_OC").toString());
					res.setClaimPortfolioinOc(tempMap.get("CLAIM_PORTFOLIOIN_OC")==null?"":tempMap.get("CLAIM_PORTFOLIOIN_OC").toString());
					res.setClaimPortfolioOutOc(tempMap.get("CLAIM_PORTFOLIO_OUT_OC")==null?"":tempMap.get("CLAIM_PORTFOLIO_OUT_OC").toString());
					res.setCashLosspaidOc(tempMap.get("CASH_LOSSPAID_OC")==null?"":tempMap.get("CASH_LOSSPAID_OC").toString());
					res.setCashLossCreditOc(tempMap.get("CASH_LOSS_CREDIT_OC")==null?"":tempMap.get("CASH_LOSS_CREDIT_OC").toString());
					res.setNetClaims(tempMap.get("NET_CLAIMS")==null?"":tempMap.get("NET_CLAIMS").toString());
					res.setReceiptAmount(tempMap.get("RECEIPT_AMOUNT")==null?"":tempMap.get("RECEIPT_AMOUNT").toString());
					res.setPaymentAmount(tempMap.get("PAYMENT_AMOUNT")==null?"":tempMap.get("PAYMENT_AMOUNT").toString());
					res.setNetDueOc(tempMap.get("NET_DUE_OC")==null?"":tempMap.get("NET_DUE_OC").toString());
					res.setAllocatedTillDate(tempMap.get("ALLOCATED_TILL_DATE")==null?"":tempMap.get("ALLOCATED_TILL_DATE").toString());
					res.setPendingAllocation(tempMap.get("PENDING_ALLOCATION")==null?"":tempMap.get("PENDING_ALLOCATION").toString());
					res.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
					res.setPremiumQuotashareDc(tempMap.get("PREMIUM_QUOTASHARE_DC")==null?"":tempMap.get("PREMIUM_QUOTASHARE_DC").toString());
					res.setPremiumSurplusDc(tempMap.get("PREMIUM_SURPLUS_DC")==null?"":tempMap.get("PREMIUM_SURPLUS_DC").toString());
					res.setPremiumTotalDc(tempMap.get("PREMIUM_TOTAL_DC")==null?"":tempMap.get("PREMIUM_TOTAL_DC").toString());
					res.setXlCostDc(tempMap.get("XL_COST_DC")==null?"":tempMap.get("XL_COST_DC").toString());
					res.setPremiumPortfolioinDc(tempMap.get("PREMIUM_PORTFOLIOIN_DC")==null?"":tempMap.get("PREMIUM_PORTFOLIOIN_DC").toString());
					res.setPremiumPortfoliooutDc(tempMap.get("PREMIUM_PORTFOLIOOUT_DC")==null?"":tempMap.get("PREMIUM_PORTFOLIOOUT_DC").toString());
					res.setNetPremiumDc(tempMap.get("NET_PREMIUM_DC")==null?"":tempMap.get("NET_PREMIUM_DC").toString());
					res.setCommissionQuotashareDc(tempMap.get("COMMISSION_QUOTASHARE_DC")==null?"":tempMap.get("COMMISSION_QUOTASHARE_DC").toString());
					res.setCommissionSurplusDc(tempMap.get("COMMISSION_SURPLUS_DC")==null?"":tempMap.get("COMMISSION_SURPLUS_DC").toString());
					res.setCommissionTotalDc(tempMap.get("COMMISSION_TOTAL_DC")==null?"":tempMap.get("COMMISSION_TOTAL_DC").toString());
					res.setBrokerageAmtDc(tempMap.get("BROKERAGE_AMT_DC")==null?"":tempMap.get("BROKERAGE_AMT_DC").toString());
					res.setTaxAmtDc(tempMap.get("TAX_AMT_DC")==null?"":tempMap.get("TAX_AMT_DC").toString());
					res.setOverriderAmtDc(tempMap.get("OVERRIDER_AMT_DC")==null?"":tempMap.get("OVERRIDER_AMT_DC").toString());
					res.setOtherCostDc(tempMap.get("OTHER_COST_DC")==null?"":tempMap.get("OTHER_COST_DC").toString());
					res.setXlCostDc(tempMap.get("XL_COST_DC")==null?"":tempMap.get("XL_COST_DC").toString());
					res.setProfitCommissionDc(tempMap.get("PROFIT_COMMISSION_DC")==null?"":tempMap.get("PROFIT_COMMISSION_DC").toString());
					res.setTotalCostDc(tempMap.get("TOTAL_COST_DC")==null?"":tempMap.get("TOTAL_COST_DC").toString());
					res.setWithHoldingTaxDc(tempMap.get("WITH_HOLDING_TAX_DC")==null?"":tempMap.get("WITH_HOLDING_TAX_DC").toString());
					res.setPremiumReserveRetainedDc(tempMap.get("PREMIUM_RESERVE_RETAINED_DC")==null?"":tempMap.get("PREMIUM_RESERVE_RETAINED_DC").toString());
					res.setPremiumReserveRealsedDc(tempMap.get("PREMIUM_RESERVE_REALSED_DC")==null?"":tempMap.get("PREMIUM_RESERVE_REALSED_DC").toString());
					res.setLossReserveretainedDc(tempMap.get("LOSS_RESERVERETAINED_DC")==null?"":tempMap.get("LOSS_RESERVERETAINED_DC").toString());
					res.setLossReserveReleasedDc(tempMap.get("LOSS_RESERVE_RELEASED_DC")==null?"":tempMap.get("LOSS_RESERVE_RELEASED_DC").toString());
					res.setInterestDc(tempMap.get("INTEREST_DC")==null?"":tempMap.get("INTEREST_DC").toString());
					res.setClaimsPaidDc(tempMap.get("CLAIMS_PAID_DC")==null?"":tempMap.get("CLAIMS_PAID_DC").toString());
					res.setClaimRecoveryDc(tempMap.get("CLAIM_RECOVERY_DC")==null?"":tempMap.get("CLAIM_RECOVERY_DC").toString());
					res.setClaimPortfolioinDc(tempMap.get("CLAIM_PORTFOLIOIN_DC")==null?"":tempMap.get("CLAIM_PORTFOLIOIN_DC").toString());
					res.setClaimPortfolioOutDc(tempMap.get("CLAIM_PORTFOLIO_OUT_DC")==null?"":tempMap.get("CLAIM_PORTFOLIO_OUT_DC").toString());
					res.setCashLosspaidDc(tempMap.get("CASH_LOSSPAID_DC")==null?"":tempMap.get("CASH_LOSSPAID_DC").toString());
					res.setCashLossCreditDc(tempMap.get("CASH_LOSS_CREDIT_DC")==null?"":tempMap.get("CASH_LOSS_CREDIT_DC").toString());
					res.setNetClaimsDc(tempMap.get("NET_CLAIMS_DC")==null?"":tempMap.get("NET_CLAIMS_DC").toString());
					res.setReceiptAmountDc(tempMap.get("RECEIPT_AMOUNT_DC")==null?"":tempMap.get("RECEIPT_AMOUNT_DC").toString());
					res.setPaymentAmountDc(tempMap.get("PAYMENT_AMOUNT_DC")==null?"":tempMap.get("PAYMENT_AMOUNT_DC").toString());
					res.setNetDueDc(tempMap.get("NET_DUE_DC")==null?"":tempMap.get("NET_DUE_DC").toString());
					res.setAllocatedTillDateDc(tempMap.get("ALLOCATED_TILL_DATE_DC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_DC").toString());
					res.setPendingAllocationDc(tempMap.get("PENDING_ALLOCATION_DC")==null?"":tempMap.get("PENDING_ALLOCATION_DC").toString());
					res.setAllocationNumberDc(tempMap.get("ALLOCATION_NUMBER_DC")==null?"":tempMap.get("ALLOCATION_NUMBER_DC").toString());
					res.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
					res.setReinstatementpremiumOc(tempMap.get("REINSTATEMENTPREMIUM_OC")==null?"":tempMap.get("REINSTATEMENTPREMIUM_OC").toString());
					res.setReinstatementpremiumDc(tempMap.get("REINSTATEMENTPREMIUM_DC")==null?"":tempMap.get("REINSTATEMENTPREMIUM_DC").toString());
					res.setCustomerId(tempMap.get("CUSTOMER_ID")==null?"":tempMap.get("CUSTOMER_ID").toString());
					res.setBrokerId(tempMap.get("BROKER_ID")==null?"":tempMap.get("BROKER_ID").toString());
					res.setSno(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					res.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
					res.setTdsOc(tempMap.get("TDS_OC")==null?"":tempMap.get("TDS_OC").toString());
					res.setTdsDc(tempMap.get("TDS_DC")==null?"":tempMap.get("TDS_DC").toString());
					res.setStOc(tempMap.get("ST_OC")==null?"":tempMap.get("ST_OC").toString());
					res.setStDc(tempMap.get("ST_DC")==null?"":tempMap.get("ST_DC").toString());
					res.setScCommOc(tempMap.get("SC_COMM_OC")==null?"":tempMap.get("SC_COMM_OC").toString());
					res.setScCommDc(tempMap.get("SC_COMM_DC")==null?"":tempMap.get("SC_COMM_DC").toString());
					res.setBonusOc(tempMap.get("BONUS_OC")==null?"":tempMap.get("BONUS_OC").toString());
					res.setBonusDc(tempMap.get("BONUS_DC")==null?"":tempMap.get("BONUS_DC").toString());
					res.setLpcOc(tempMap.get("LPC_OC")==null?"":tempMap.get("LPC_OC").toString());
					res.setLpcDc(tempMap.get("LPC_DC")==null?"":tempMap.get("LPC_DC").toString());
					res.setPrdAllocatedTillDate(tempMap.get("PRD_ALLOCATED_TILL_DATE")==null?"":tempMap.get("PRD_ALLOCATED_TILL_DATE").toString());
					res.setLrdAllocatedTillDate(tempMap.get("LRD_ALLOCATED_TILL_DATE")==null?"":tempMap.get("LRD_ALLOCATED_TILL_DATE").toString());
					res.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
					res.setOsbyn(tempMap.get("OSBYN")==null?"":tempMap.get("OSBYN").toString());
					res.setDepartmentName(tempMap.get("DEPARTMENT_NAME")==null?"":tempMap.get("DEPARTMENT_NAME").toString());
					res.setOsclaimLossupdateOc(tempMap.get("OSCLAIM_LOSSUPDATE_OC")==null?"":tempMap.get("OSCLAIM_LOSSUPDATE_OC").toString());
					res.setOsclaimLossupdateDc(tempMap.get("OSCLAIM_LOSSUPDATE_DC")==null?"":tempMap.get("OSCLAIM_LOSSUPDATE_DC").toString());
					res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
					res.setSafOsOc(tempMap.get("SAF_OS_OC")==null?"":tempMap.get("SAF_OS_OC").toString());
					res.setSafOsDc(tempMap.get("SAF_OS_DC")==null?"":tempMap.get("SAF_OS_DC").toString());
					res.setOthFeeOsDc(tempMap.get("OTH_FEE_OS_DC")==null?"":tempMap.get("OTH_FEE_OS_DC").toString());
					resList.add(res);
				}}
			response.setCommonResponse(resList);
			response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}

		@Override
		public GetDebtorsAgeingReportRes getDebtorsAgeingReport(GetDebtorsAgeingReportReq bean) {
			GetDebtorsAgeingReportRes response = new GetDebtorsAgeingReportRes();
			String query = "";
			List<GetDebtorsAgeingReportRes1> resList = new ArrayList<GetDebtorsAgeingReportRes1>();
			String[] obj = new String[19];
			try {
				//Package or function FN_DR_CR_AGEING_REPORT is in an invalid state
				query = "reportdao.select.getAgeingList";
				if("1".equalsIgnoreCase(bean.getType())){
					query+=" ORDER BY CURRENCY, BROKER_CODE,COMPANY_CODE,TRANSACTION_DATE,TRANSACTION_NO";
				}else if("2".equalsIgnoreCase(bean.getType())){
					query+=" ORDER BY UPPER(CLIENT_ID), CURRENCY";
				}
				obj[0] = bean.getType();
				obj[1] = bean.getStartDate();
				obj[2] = bean.getDebFrom();
				obj[3] = bean.getDebTo();
				obj[4] = bean.getDebFrom1();
				obj[5] = bean.getDebTo1();
				obj[6] = bean.getDebFrom2();
				obj[7] = bean.getDebTo2();
				obj[8] = bean.getDebFrom3();
				obj[9] = bean.getDebTo3();
				obj[10] = bean.getDebFrom4();
				obj[11] = bean.getDebTo4();
				obj[12] = bean.getDebFrom5();
				obj[13] = bean.getDebTo5();
				obj[14] = bean.getBranchCode();
				obj[15] = bean.getProductId();
				obj[16] = bean.getBrokerId();
				obj[17] = bean.getCedingId();
				obj[18] = bean.getDocType();
				List<Map<String,Object>> list= queryImpl.selectList(query, obj);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetDebtorsAgeingReportRes1 res = new GetDebtorsAgeingReportRes1();
						res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());    
						   res.setNetDueOc(tempMap.get("NET_DUE_OC")==null?"":tempMap.get("NET_DUE_OC").toString());   
						   res.setAllocatedTillDate(tempMap.get("ALLOCATED_TILL_DATE")==null?"":tempMap.get("ALLOCATED_TILL_DATE").toString());    
						   res.setPendingAllocation(tempMap.get("PENDING_ALLOCATION")==null?"":tempMap.get("PENDING_ALLOCATION").toString());   
						  res.setM1(tempMap.get("M1")==null?"":tempMap.get("M1").toString());  
						   res.setM2(tempMap.get("M2")==null?"":tempMap.get("M2").toString()); 
						   res.setM3(tempMap.get("M3")==null?"":tempMap.get("M3").toString()); 
						   res.setM4(tempMap.get("M4")==null?"":tempMap.get("M4").toString()); 
						   res.setM5(tempMap.get("M5")==null?"":tempMap.get("M5").toString()); 
						   res.setM6(tempMap.get("M6")==null?"":tempMap.get("M6").toString());    
						   res.setNetDueDc(tempMap.get("NET_DUE_DC")==null?"":tempMap.get("NET_DUE_DC").toString());
						   res.setAllocatedTillDateDc(tempMap.get("ALLOCATED_TILL_DATE_DC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_DC").toString()); 
						   res.setPendingAllocationDc(tempMap.get("PENDING_ALLOCATION_DC")==null?"":tempMap.get("PENDING_ALLOCATION_DC").toString());
						   res.setClientId(tempMap.get("CLIENT_ID")==null?"":tempMap.get("CLIENT_ID").toString());
						resList.add(res);
					}}
				response.setCommonResponse(resList);
				response.setMessage("Success");
					response.setIsError(false);
					} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
				return response;
		}

		@Override
		public GetMoveMntSummaryRes getMoveMntSummary(GetMoveMntSummaryReq bean) {
			GetMoveMntSummaryRes response = new GetMoveMntSummaryRes();
			List<GetMoveMntSummaryRes1> resList = new ArrayList<GetMoveMntSummaryRes1>();
			String query="";
			String[] obj= new String[3];
			try{
				query= "reportdao.select.getMovMntSummaryList";
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getStartDate();
				obj[2]=bean.getEndDate();
				List<Map<String,Object>> list= queryImpl.selectList(query, obj);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetMoveMntSummaryRes1 res = new GetMoveMntSummaryRes1();
						res.setRownum(tempMap.get("ROWNUM")==null?"":tempMap.get("ROWNUM").toString());
						res.setRskUwyear(tempMap.get("RSK_UWYEAR")==null?"":tempMap.get("RSK_UWYEAR").toString());
						res.setSumoftotprDc(tempMap.get("SUMOFTOTPR_DC")==null?"":tempMap.get("SUMOFTOTPR_DC").toString());
						res.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());	
						resList.add(res);
					}
					}
				response.setCommonResponse(resList);
				response.setMessage("Success");
					response.setIsError(false);
					} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
				return response;
		}


		@Override
		public GetallocationReportListRes getallocationReportList(GetallocationReportListReq bean) {
			GetallocationReportListRes response = new GetallocationReportListRes();
			List<GetallocationReportListRes1> resList = new ArrayList<GetallocationReportListRes1>();
			try{
				String[] args=new String[7];
				args[0]=bean.getStartDate();
				args[1]=bean.getEndDate();
				args[2]=bean.getSettlementType();
				args[3]=bean.getBrokerId();
				args[4]=bean.getCedingId();
				args[5]=bean.getAllocateStatus();
				args[6]=bean.getBranchCode();
				if("-1".equals(bean.getBrokerId())){
					args[3]="ALL";
				}
				if("-1".equals(bean.getCedingId())){
					args[4]="ALL";
				}
				List<Map<String,Object>> list = queryImpl.selectList("allocation.report.list", args);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetallocationReportListRes1 res = new GetallocationReportListRes1();
						res.setAllocationNo(tempMap.get("ALLOCATION_NO")==null?"":tempMap.get("ALLOCATION_NO").toString());
					    res.setAllocationDate(tempMap.get("ALLOCATION_DATE")==null?"":tempMap.get("ALLOCATION_DATE").toString());
					    res.setStlType(tempMap.get("STL_TYPE")==null?"":tempMap.get("STL_TYPE").toString());
					    res.setStlNo(tempMap.get("STL_NO")==null?"":tempMap.get("STL_NO").toString());
					    res.setStlDate(tempMap.get("STL_DATE")==null?"":tempMap.get("STL_DATE").toString());
					    res.setProcessType(tempMap.get("PROCESS_TYPE")==null?"":tempMap.get("PROCESS_TYPE").toString());
					    res.setStatus(tempMap.get("STATUS")==null?"":tempMap.get("STATUS").toString());
					    res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
					    res.setType(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString());
					    res.setBookTransNo(tempMap.get("BOOK_TRANS_NO")==null?"":tempMap.get("BOOK_TRANS_NO").toString());
					    res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
					    res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					    res.setProductName(tempMap.get("PRODUCT_NAME")==null?"":tempMap.get("PRODUCT_NAME").toString());
					    res.setCedingCompany(tempMap.get("CEDING_COMPANY")==null?"":tempMap.get("CEDING_COMPANY").toString());
					    res.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
					    res.setAllocatedAmt(tempMap.get("ALLOCATED_AMT")==null?"":tempMap.get("ALLOCATED_AMT").toString());
					    res.setSettledRate(tempMap.get("SETTLED_RATE")==null?"":tempMap.get("SETTLED_RATE").toString());
					    res.setAllocatedAmtDc(tempMap.get("ALLOCATED_AMT_DC")==null?"":tempMap.get("ALLOCATED_AMT_DC").toString());
					    res.setBkdExchangeRate(tempMap.get("BKD_EXCHANGE_RATE")==null?"":tempMap.get("BKD_EXCHANGE_RATE").toString());
					    res.setBookedAmtDc(tempMap.get("BOOKED_AMT_DC")==null?"":tempMap.get("BOOKED_AMT_DC").toString());
					    res.setRealisedExDiff(tempMap.get("REALISED_EX_DIFF")==null?"":tempMap.get("REALISED_EX_DIFF").toString());
					resList.add(res);
					}
					}
				response.setCommonResponse(resList);
				response.setMessage("Success");
					response.setIsError(false);
					} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
				return response;
		}


		@Override
		public GetPayRecRegisterListRes getPayRecRegisterList(GetPayRecRegisterListReq bean) {
			GetPayRecRegisterListRes response = new GetPayRecRegisterListRes();
			List<GetPayRecRegisterListRes1> resList = new ArrayList<GetPayRecRegisterListRes1>();
			try{
				String[] args=new String[8];
				args[0]=bean.getBranchCode();
				args[1]=bean.getStartDate();
				args[2]=bean.getEndDate();
				if("-1".equals(bean.getCedingId())){
					args[3]="ALL";
				}
				if("-1".equals(bean.getBrokerId())){
					args[4]="ALL";
				}
				args[5]=bean.getPayrecType();
				args[6]=bean.getTransactionType();
				args[7]=bean.getShowAllFields();
				List<Map<String,Object>> list= queryImpl.selectList("payrec.register.list", args);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPayRecRegisterListRes1 res = new GetPayRecRegisterListRes1();
						  res.setPaymentReceiptNo(tempMap.get("PAYMENT_RECEIPT_NO")==null?"":tempMap.get("PAYMENT_RECEIPT_NO").toString());
					      res.setTransDate(tempMap.get("TRANS_DATE")==null?"":tempMap.get("TRANS_DATE").toString());
					      res.setAmendmentDate(tempMap.get("AMENDMENT_DATE")==null?"":tempMap.get("AMENDMENT_DATE").toString());
					      res.setTransType(tempMap.get("TRANS_TYPE")==null?"":tempMap.get("TRANS_TYPE").toString());
					      res.setBrokerName(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
					      res.setCedingId(tempMap.get("CEDING_ID")==null?"":tempMap.get("CEDING_ID").toString());
					      res.setReceiptBank(tempMap.get("RECEIPT_BANK")==null?"":tempMap.get("RECEIPT_BANK").toString());
					      res.setCurrencyName(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
					      res.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
					      res.setGrossAmt(tempMap.get("GROSS_AMT")==null?"":tempMap.get("GROSS_AMT").toString());
					      res.setBankCharges(tempMap.get("BANK_CHARGES")==null?"":tempMap.get("BANK_CHARGES").toString());
					      res.setHoldtaxrecoverOc(tempMap.get("HOLDTAXRECOVER_OC")==null?"":tempMap.get("HOLDTAXRECOVER_OC").toString());
					      res.setNetAmt(tempMap.get("NET_AMT")==null?"":tempMap.get("NET_AMT").toString());
					      res.setGrossAmtDc(tempMap.get("GROSS_AMT_DC")==null?"":tempMap.get("GROSS_AMT_DC").toString());
					      res.setBankChargesDc(tempMap.get("BANK_CHARGES_DC")==null?"":tempMap.get("BANK_CHARGES_DC").toString());
					      res.setHoldtaxrecoverUgx(tempMap.get("HOLDTAXRECOVER_UGX")==null?"":tempMap.get("HOLDTAXRECOVER_UGX").toString());
					      res.setNetAmtDc(tempMap.get("NET_AMT_DC")==null?"":tempMap.get("NET_AMT_DC").toString());
					      res.setDiffType(tempMap.get("DIFF_TYPE")==null?"":tempMap.get("DIFF_TYPE").toString());
					      res.setExchangedifferenceamount(tempMap.get("EXCHANGEDIFFERENCEAMOUNT")==null?"":tempMap.get("EXCHANGEDIFFERENCEAMOUNT").toString());
					      res.setExchageDiffPerc(tempMap.get("EXCHAGE_DIFF_PERC")==null?"":tempMap.get("EXCHAGE_DIFF_PERC").toString());
					      res.setRoundOffOc(tempMap.get("ROUND_OFF_OC")==null?"":tempMap.get("ROUND_OFF_OC").toString());
					      res.setRoundOffDc(tempMap.get("ROUND_OFF_DC")==null?"":tempMap.get("ROUND_OFF_DC").toString());
					      res.setTranscationtype(tempMap.get("TRANSCATIONTYPE")==null?"":tempMap.get("TRANSCATIONTYPE").toString());
					      res.setReversaltransno(tempMap.get("REVERSALTRANSNO")==null?"":tempMap.get("REVERSALTRANSNO").toString());
					      res.setRemarks(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
					      res.setSno(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					      res.setCurrencyId(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					      res.setAmountOc(tempMap.get("AMOUNT_OC")==null?"":tempMap.get("AMOUNT_OC").toString());
					      res.setClientexchangerate(tempMap.get("CLIENTEXCHANGERATE")==null?"":tempMap.get("CLIENTEXCHANGERATE").toString());
					      res.setConvertedPaidAmt(tempMap.get("CONVERTED_PAID_AMT")==null?"":tempMap.get("CONVERTED_PAID_AMT").toString());
					      res.setExchangeRate(tempMap.get("EXCHANGERATE")==null?"":tempMap.get("EXCHANGERATE").toString());
					      res.setTotalAmountDc(tempMap.get("TOTAL_AMOUNT_DC")==null?"":tempMap.get("TOTAL_AMOUNT_DC").toString());
					      res.setAllocatedTillDate(tempMap.get("ALLOCATED_TILL_DATE")==null?"":tempMap.get("ALLOCATED_TILL_DATE").toString());
					      res.setPendingAmount(tempMap.get("PENDING_AMOUNT")==null?"":tempMap.get("PENDING_AMOUNT").toString());
					      res.setAllocatedAmountUgx(tempMap.get("ALLOCATED_AMOUNT_UGX")==null?"":tempMap.get("ALLOCATED_AMOUNT_UGX").toString());
					      res.setPendingAmountUgx(tempMap.get("PENDING_AMOUNT_UGX")==null?"":tempMap.get("PENDING_AMOUNT_UGX").toString());
						resList.add(res);
					}
					}
				response.setCommonResponse(resList);
				response.setMessage("Success");
					response.setIsError(false);
					} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
				return response;
		}

		@Override
		public GetClaimPaidRegisterListRes getClaimPaidRegisterList(GetClaimPaidRegisterListReq bean) {
			GetClaimPaidRegisterListRes response = new GetClaimPaidRegisterListRes();
			List<GetClaimPaidRegisterListRes1> resList = new ArrayList<GetClaimPaidRegisterListRes1>();
			try{
				String[] obj=new String[8];
				obj[0]=bean.getProductId();
				obj[1]=bean.getBranchCode();
				if("-1".equals(bean.getLoginId())){
					obj[2]="ALL";
				}else{
					obj[2]=	bean.getLoginId();
				}
				if("-1".equals(bean.getBrokerId())){
					obj[3]="ALL";
				}else{
					obj[3]=	bean.getBrokerId();
				}
				if("-1".equals(bean.getCedingId())){
					obj[4]="ALL";
				}else{
					obj[4]=	bean.getCedingId();
				}
				if("-1".equals(bean.getUwYear())){
					obj[5]="ALL";
				}else{
					obj[5]=	bean.getUwYear();
				}
				obj[6]=bean.getStartDate();
				obj[7]=bean.getEndDate();
				List<Map<String,Object>> list = queryImpl.selectList("claim.paid.report", obj);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetClaimPaidRegisterListRes1 res = new GetClaimPaidRegisterListRes1();
						  res.setClaimNo(tempMap.get("CLAIM_NO")==null?"":tempMap.get("CLAIM_NO").toString());
					      res.setClaimPaymentNo(tempMap.get("CLAIM_PAYMENT_NO")==null?"":tempMap.get("CLAIM_PAYMENT_NO").toString());
					      res.setClaimpaiddate(tempMap.get("CLAIMPAIDDATE")==null?"":tempMap.get("CLAIMPAIDDATE").toString());
					      res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					      res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					      res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
					      res.setCedingCompany(tempMap.get("CEDING_COMPANY")==null?"":tempMap.get("CEDING_COMPANY").toString());
					      res.setBrokerId(tempMap.get("BROKER_ID")==null?"":tempMap.get("BROKER_ID").toString());
					      res.setContractClass(tempMap.get("CONTRACT_CLASS")==null?"":tempMap.get("CONTRACT_CLASS").toString());
					      res.setClaimClass(tempMap.get("CLAIM_CLASS")==null?"":tempMap.get("CLAIM_CLASS").toString());
					      res.setClaimSubclass(tempMap.get("CLAIM_SUBCLASS")==null?"":tempMap.get("CLAIM_SUBCLASS").toString());
					      res.setInsuredName(tempMap.get("INSURED_NAME")==null?"":tempMap.get("INSURED_NAME").toString());
					      res.setCedentClaimNo(tempMap.get("CEDENT_CLAIM_NO")==null?"":tempMap.get("CEDENT_CLAIM_NO").toString());
					      res.setTreatyName(tempMap.get("TREATY_NAME")==null?"":tempMap.get("TREATY_NAME").toString());
					      res.setReserveId(tempMap.get("RESERVE_ID")==null?"":tempMap.get("RESERVE_ID").toString());
					      res.setReserveDate(tempMap.get("RESERVE_DATE")==null?"":tempMap.get("RESERVE_DATE").toString());
					      res.setDateOfLoss(tempMap.get("DATE_OF_LOSS")==null?"":tempMap.get("DATE_OF_LOSS").toString());
					      res.setLossDetails(tempMap.get("LOSS_DETAILS")==null?"":tempMap.get("LOSS_DETAILS").toString());
					      res.setCauseOfLoss(tempMap.get("CAUSE_OF_LOSS")==null?"":tempMap.get("CAUSE_OF_LOSS").toString());
					      res.setLocation(tempMap.get("LOCATION")==null?"":tempMap.get("LOCATION").toString());
					      res.setRiRecovery(tempMap.get("RI_RECOVERY")==null?"":tempMap.get("RI_RECOVERY").toString());
					      res.setRiskCode(tempMap.get("RISK_CODE")==null?"":tempMap.get("RISK_CODE").toString());
					      res.setAccumulationCode(tempMap.get("ACCUMULATION_CODE")==null?"":tempMap.get("ACCUMULATION_CODE").toString());
					      res.setEventCode(tempMap.get("EVENT_CODE")==null?"":tempMap.get("EVENT_CODE").toString());
					      res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
					      res.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
					      res.setPaymentRequestNo(tempMap.get("PAYMENT_REQUEST_NO")==null?"":tempMap.get("PAYMENT_REQUEST_NO").toString());
					      res.setPaidClaimOsOc(tempMap.get("PAID_CLAIM_OS_OC")==null?"":tempMap.get("PAID_CLAIM_OS_OC").toString());
					      res.setSafOsOc(tempMap.get("SAF_OS_OC")==null?"":tempMap.get("SAF_OS_OC").toString());
					      res.setOthFeeOsOc(tempMap.get("OTH_FEE_OS_OC")==null?"":tempMap.get("OTH_FEE_OS_OC").toString());
					      res.setTotClaimAmountOc(tempMap.get("TOT_CLAIM_AMOUNT_OC")==null?"":tempMap.get("TOT_CLAIM_AMOUNT_OC").toString());
					      res.setReinstatementType(tempMap.get("REINSTATEMENT_TYPE")==null?"":tempMap.get("REINSTATEMENT_TYPE").toString());
					      res.setReinspremiumOurshareOc(tempMap.get("REINSPREMIUM_OURSHARE_OC")==null?"":tempMap.get("REINSPREMIUM_OURSHARE_OC").toString());
					      res.setPaidAmountOc(tempMap.get("PAID_AMOUNT_OC")==null?"":tempMap.get("PAID_AMOUNT_OC").toString());
					      res.setPaidClaimOsDc(tempMap.get("PAID_CLAIM_OS_DC")==null?"":tempMap.get("PAID_CLAIM_OS_DC").toString());
					      res.setSafOsDc(tempMap.get("SAF_OS_DC")==null?"":tempMap.get("SAF_OS_DC").toString());
					      res.setOthFeeOsDc(tempMap.get("OTH_FEE_OS_DC")==null?"":tempMap.get("OTH_FEE_OS_DC").toString());
					      res.setTotClaimAmountDc(tempMap.get("TOT_CLAIM_AMOUNT_DC")==null?"":tempMap.get("TOT_CLAIM_AMOUNT_DC").toString());
					      res.setReinspremiumOurshareDc(tempMap.get("REINSPREMIUM_OURSHARE_DC")==null?"":tempMap.get("REINSPREMIUM_OURSHARE_DC").toString());
					      res.setPaidAmountDc(tempMap.get("PAID_AMOUNT_DC")==null?"":tempMap.get("PAID_AMOUNT_DC").toString());
					      res.setAllocationstatus(tempMap.get("ALLOCATIONSTATUS")==null?"":tempMap.get("ALLOCATIONSTATUS").toString());
					      res.setAllocatedTillDateOc(tempMap.get("ALLOCATED_TILL_DATE_OC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_OC").toString());
					      res.setAllocatedTillDateDc(tempMap.get("ALLOCATED_TILL_DATE_DC")==null?"":tempMap.get("ALLOCATED_TILL_DATE_DC").toString());
					      res.setClaimNoteRecomm(tempMap.get("CLAIM_NOTE_RECOMM")==null?"":tempMap.get("CLAIM_NOTE_RECOMM").toString());
					      res.setPaymentReference(tempMap.get("PAYMENT_REFERENCE")==null?"":tempMap.get("PAYMENT_REFERENCE").toString());
					      res.setAdviceTreasury(tempMap.get("ADVICE_TREASURY")==null?"":tempMap.get("ADVICE_TREASURY").toString());
					      res.setRemarks(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
					      res.setLoginId(tempMap.get("LOGIN_ID")==null?"":tempMap.get("LOGIN_ID").toString());
						  resList.add(res);
					}
					}
				response.setCommonResponse(resList);
				response.setMessage("Success");
					response.setIsError(false);
					} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
				return response;
		}

		@Override
		public GetRetroRegisterListRes getRetroRegisterList(GetRetroRegisterListReq bean) {
			GetRetroRegisterListRes response = new GetRetroRegisterListRes();
			List<GetRetroRegisterListRes1> resList = new ArrayList<GetRetroRegisterListRes1>();
			try{
				String[] obj=new String[12];
				obj[0]=bean.getStartDate();
				obj[1]=bean.getEndDate();
				obj[2]=bean.getBranchCode();
				if("-1".equals(bean.getBrokerId())){
					obj[3]="ALL";
					obj[4]="ALL";
					obj[5]="ALL";
				}else{
					obj[3]=	bean.getBrokerId();
					obj[4]=	bean.getBrokerId();
					obj[5]=	bean.getBrokerId();
				}
				if("-1".equals(bean.getCedingId())){
					obj[6]="ALL";
					obj[7]="ALL";
					obj[8]="ALL";
				}else{
					obj[6]=	bean.getCedingId();
					obj[7]=	bean.getCedingId();
					obj[8]=	bean.getCedingId();
				}
				if("-1".equals(bean.getUwYear())){
					obj[9]="ALL";
					obj[10]="ALL";
					obj[11]="ALL";
				}else{
					obj[9]=	bean.getUwYear();
					obj[10]=bean.getUwYear();
					obj[11]=bean.getUwYear();
				}
				List<Map<String,Object>> list = queryImpl.selectList("retro.report.list", obj);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetRetroRegisterListRes1 res = new GetRetroRegisterListRes1();
						res.setAmendId(tempMap.get("AMEND_ID")==null?"":tempMap.get("AMEND_ID").toString());	
						res.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
						res.setBrokerageIc(tempMap.get("BROKERAGE_IC")==null?"":tempMap.get("BROKERAGE_IC").toString());
						res.setBrokerageInr(tempMap.get("BROKERAGE_INR")==null?"":tempMap.get("BROKERAGE_INR").toString());
						res.setBrokerageRc(tempMap.get("BROKERAGE_RC")==null?"":tempMap.get("BROKERAGE_RC").toString());
						res.setClamspaidIc(tempMap.get("CLAMSPAID_IC")==null?"":tempMap.get("CLAMSPAID_IC").toString());
						res.setClamspaidInr(tempMap.get("CLAMSPAID_IC")==null?"CLAMSPAID_INR":tempMap.get("CLAMSPAID_INR").toString());
						res.setClamspaidRc(tempMap.get("CLAMSPAID_RC")==null?"":tempMap.get("CLAMSPAID_RC").toString());
						res.setCommissionIc(tempMap.get("COMMISSION_IC")==null?"":tempMap.get("COMMISSION_IC").toString());
						res.setCommissionInr(tempMap.get("COMMISSION_INR")==null?"":tempMap.get("COMMISSION_INR").toString());
						res.setCommissionRc(tempMap.get("COMMISSION_RC")==null?"":tempMap.get("COMMISSION_RC").toString());
						res.setInterestIc(tempMap.get("INTEREST_IC")==null?"":tempMap.get("INTEREST_IC").toString());
						res.setInterestInr(tempMap.get("INTEREST_INR")==null?"":tempMap.get("INTEREST_INR").toString());
						res.setInterestRc(tempMap.get("INTEREST_RC")==null?"":tempMap.get("INTEREST_RC").toString());
						res.setInwardbusiness(tempMap.get("INWARDBUSINESS")==null?"":tempMap.get("INWARDBUSINESS").toString());
						res.setInwardcontractno(tempMap.get("INWARDCONTRACTNO")==null?"":tempMap.get("INWARDCONTRACTNO").toString());
						res.setInwardcurrencyname(tempMap.get("INWARDCURRENCYNAME")==null?"":tempMap.get("INWARDCURRENCYNAME").toString());
						res.setInwardexchangerate(tempMap.get("INWARDEXCHANGERATE")==null?"":tempMap.get("INWARDEXCHANGERATE").toString());	
						res.setInwardoverriderIc(tempMap.get("INWARDOVERRIDER_IC")==null?"":tempMap.get("INWARDOVERRIDER_IC").toString());
						res.setInwardoverriderInr(tempMap.get("INWARDOVERRIDER_INR")==null?"":tempMap.get("INWARDOVERRIDER_INR").toString());
						res.setInwardoverriderRc(tempMap.get("INWARDOVERRIDER_RC")==null?"":tempMap.get("INWARDOVERRIDER_RC").toString());
						res.setLayerno(tempMap.get("LAYERNO")==null?"":tempMap.get("LAYERNO").toString());
						res.setLossreservereleasedIc(tempMap.get("LOSSRESERVERELEASED_IC")==null?"":tempMap.get("LOSSRESERVERELEASED_IC").toString());
						res.setLossreservereleasedInr(tempMap.get("LOSSRESERVERELEASED_INR")==null?"":tempMap.get("LOSSRESERVERELEASED_INR").toString());
						res.setLossreservereleasedRc(tempMap.get("LOSSRESERVERELEASED_RC")==null?"":tempMap.get("LOSSRESERVERELEASED_RC").toString());
						res.setLossreserveretainedIc(tempMap.get("LOSSRESERVERETAINED_IC")==null?"":tempMap.get("LOSSRESERVERETAINED_IC").toString());
						res.setLossreserveretainedInr(tempMap.get("LOSSRESERVERETAINED_INR")==null?"":tempMap.get("LOSSRESERVERETAINED_INR").toString());
						res.setLossreserveretainedRc(tempMap.get("LOSSRESERVERETAINED_RC")==null?"":tempMap.get("LOSSRESERVERETAINED_RC").toString());		
						res.setNetdueIc(tempMap.get("NETDUE_IC")==null?"":tempMap.get("NETDUE_IC").toString());
						res.setNetdueInr(tempMap.get("NETDUE_INR")==null?"":tempMap.get("NETDUE_INR").toString());
						res.setNetdueRc(tempMap.get("NETDUE_RC")==null?"":tempMap.get("NETDUE_RC").toString());
						res.setOsclaimLossIc(tempMap.get("OSCLAIM_LOSS_IC")==null?"":tempMap.get("OSCLAIM_LOSS_IC").toString());
						res.setOsclaimLossInr(tempMap.get("OSCLAIM_LOSS_INR")==null?"":tempMap.get("OSCLAIM_LOSS_INR").toString());
						res.setOsclaimLossRc(tempMap.get("OSCLAIM_LOSS_RC")==null?"":tempMap.get("OSCLAIM_LOSS_RC").toString());
						res.setOthercostIc(tempMap.get("OTHERCOST_IC")==null?"":tempMap.get("OTHERCOST_IC").toString());
						res.setOthercostInr(tempMap.get("OTHERCOST_INR")==null?"":tempMap.get("OTHERCOST_INR").toString());
						res.setOthercostRc(tempMap.get("OTHERCOST_RC")==null?"":tempMap.get("OTHERCOST_RC").toString());
						res.setOutwardoverriderIc(tempMap.get("OUTWARDOVERRIDER_IC")==null?"":tempMap.get("OUTWARDOVERRIDER_IC").toString());
						res.setOutwardoverriderInr(tempMap.get("OUTWARDOVERRIDER_INR")==null?"":tempMap.get("OUTWARDOVERRIDER_INR").toString());
						res.setOutwardoverriderRc(tempMap.get("OUTWARDOVERRIDER_RC")==null?"":tempMap.get("OUTWARDOVERRIDER_RC").toString());
						res.setPremiumIc(tempMap.get("PREMIUM_IC")==null?"":tempMap.get("PREMIUM_IC").toString());
						res.setPremiumInr(tempMap.get("PREMIUM_INR")==null?"":tempMap.get("PREMIUM_INR").toString());
						res.setPremiumRc(tempMap.get("PREMIUM_RC")==null?"":tempMap.get("PREMIUM_RC").toString());
						res.setPremiumreservereleasedIc(tempMap.get("PREMIUMRESERVERELEASED_IC")==null?"":tempMap.get("PREMIUMRESERVERELEASED_IC").toString());
						res.setPremiumreservereleasedInr(tempMap.get("PREMIUMRESERVERELEASED_INR")==null?"":tempMap.get("PREMIUMRESERVERELEASED_INR").toString());
						res.setPremiumreservereleasedRc(tempMap.get("PREMIUMRESERVERELEASED_RC")==null?"":tempMap.get("PREMIUMRESERVERELEASED_RC").toString());
						res.setPremiumreserveretainedIc(tempMap.get("PREMIUMRESERVERETAINED_IC")==null?"":tempMap.get("PREMIUMRESERVERETAINED_IC").toString());
						res.setPremiumreserveretainedInr(tempMap.get("PREMIUMRESERVERETAINED_INR")==null?"":tempMap.get("PREMIUMRESERVERETAINED_INR").toString());
						res.setPremiumreserveretainedRc(tempMap.get("PREMIUMRESERVERETAINED_RC")==null?"":tempMap.get("PREMIUMRESERVERETAINED_RC").toString());
						res.setRetroBroker(tempMap.get("RETRO_BROKER")==null?"":tempMap.get("RETRO_BROKER").toString());
						res.setRetrobrokername(tempMap.get("RETROBROKERNAME")==null?"":tempMap.get("RETROBROKERNAME").toString());
						res.setRetrocessionaire(tempMap.get("RETROCESSIONAIRE")==null?"":tempMap.get("RETROCESSIONAIRE").toString());
						res.setRetrocessionairename(tempMap.get("RETROCESSIONAIRENAME")==null?"":tempMap.get("RETROCESSIONAIRENAME").toString());
						res.setRetrocontractnumber(tempMap.get("RETROCONTRACTNUMBER")==null?"":tempMap.get("RETROCONTRACTNUMBER").toString());
						res.setRetrocurrencyname(tempMap.get("RETROCURRENCYNAME")==null?"":tempMap.get("RETROCURRENCYNAME").toString());
						res.setRetrocessionPercentage(tempMap.get("RETROCESSION_PERCENTAGE")==null?"":tempMap.get("RETROCESSION_PERCENTAGE").toString());
						res.setRetroexchangerate(tempMap.get("RETROEXCHANGERATE")==null?"":tempMap.get("RETROEXCHANGERATE").toString());
						res.setRetrocessionPercentage(tempMap.get("")==null?"":tempMap.get("").toString());
						res.setSoafrequency(tempMap.get("SOAFREQUENCY")==null?"":tempMap.get("SOAFREQUENCY").toString());
						res.setSubclass(tempMap.get("SUBCLASS")==null?"":tempMap.get("SUBCLASS").toString());
						res.setTaxIc(tempMap.get("TAX_IC")==null?"":tempMap.get("TAX_IC").toString());
						res.setTaxInr(tempMap.get("TAX_INR")==null?"":tempMap.get("TAX_INR").toString());
						res.setTaxRc(tempMap.get("TAX_RC")==null?"":tempMap.get("TAX_RC").toString());
						res.setTransactiondate(tempMap.get("TRANSACTIONDATE")==null?"":tempMap.get("TRANSACTIONDATE").toString());
						res.setTransactionMonthYear(tempMap.get("TRANSACTION_MONTH_YEAR")==null?"":tempMap.get("TRANSACTION_MONTH_YEAR").toString());
						res.setTransactionno(tempMap.get("TRANSACTIONNO")==null?"":tempMap.get("TRANSACTIONNO").toString());
						res.setTransactiontype(tempMap.get("TRANSACTIONTYPE")==null?"":tempMap.get("TRANSACTIONTYPE").toString());
						res.setTreatyInsuredName(tempMap.get("TREATY_INSURED_NAME")==null?"":tempMap.get("TREATY_INSURED_NAME").toString());
						res.setUwy(tempMap.get("UWY")==null?"":tempMap.get("UWY").toString());
						resList.add(res);						
					}}
					response.setCommonResponse(resList);
					response.setMessage("Success");
					response.setIsError(false);
					} catch (Exception e) {
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
				return response;
		}


		@Override
		public GetTreatyWithdrawRegisterListRes getTreatyWithdrawRegisterList(GetTreatyWithdrawRegisterListReq bean) {
			GetTreatyWithdrawRegisterListRes response = new GetTreatyWithdrawRegisterListRes();
			List<GetTreatyWithdrawRegisterListRes1> resList = new ArrayList<GetTreatyWithdrawRegisterListRes1>();
			try {
				String[] obj = new String[6];
				if ("-1".equals(bean.getCedingId())) {
					obj[0] = "ALL";
				} else {
					obj[0] = bean.getCedingId();
				}
				if ("-1".equals(bean.getBrokerId())) {
					obj[1] = "ALL";
				} else {
					obj[1] = bean.getBrokerId();
				}
				if ("-1".equals(bean.getUwYear())) {
					obj[2] = "ALL";
				} else {
					obj[2] = bean.getUwYear();
				}
				if ("-1".equals(bean.getTreatyMainClass())) {
					obj[3] = "ALL";
				} else {
					obj[3] = bean.getTreatyMainClass();
				}
				if ("-1".equals(bean.getTreatyType())) {
					obj[4] = "ALL";
				} else {
					obj[5] = bean.getTreatyType();
				}
				obj[5] = bean.getEndDate();
				
				List<Map<String,Object>> list = queryImpl.selectList("treaty.withdraw.report.list", obj);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetTreatyWithdrawRegisterListRes1 res = new GetTreatyWithdrawRegisterListRes1();
						res.setAcceptanceDate(tempMap.get("ACCEPTANCE_DATE")==null?"":tempMap.get("ACCEPTANCE_DATE").toString());
						res.setAccountingPeriod(tempMap.get("ACCOUNTING_PERIOD")==null?"":tempMap.get("ACCOUNTING_PERIOD").toString());
						res.setBrokerName(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
						res.setCedingCompany(tempMap.get("CEDING_COMPANY")==null?"":tempMap.get("CEDING_COMPANY").toString());
						res.setClas(tempMap.get("CLASS")==null?"":tempMap.get("CLASS").toString());
						res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res.setEpiEstimateDc(tempMap.get("EPI_ESTIMATE_DC")==null?"":tempMap.get("EPI_ESTIMATE_DC").toString());
						res.setEpiEstimateOc(tempMap.get("EPI_ESTIMATE_OC")==null?"":tempMap.get("EPI_ESTIMATE_OC").toString());
						res.setEpiEstimateShareDc(tempMap.get("EPI_ESTIMATE_SHARE_DC")==null?"":tempMap.get("EPI_ESTIMATE_SHARE_DC").toString());
						res.setEpiEstimateShareOc(tempMap.get("EPI_ESTIMATE_SHARE_OC")==null?"":tempMap.get("EPI_ESTIMATE_SHARE_OC").toString());
						res.setEpiOfferDc(tempMap.get("EPI_OFFER_dc")==null?"":tempMap.get("EPI_OFFER_dc").toString());
						res.setEpiOfferOc(tempMap.get("EPI_OFFER_OC")==null?"":tempMap.get("EPI_OFFER_OC").toString());
						res.setEpiOfferShareDc(tempMap.get("EPI_OFFER_SHARE_dc")==null?"":tempMap.get("EPI_OFFER_SHARE_dc").toString());
						res.setEpiOfferShareOc(tempMap.get("")==null?"":tempMap.get("").toString());
						res.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
						res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
						res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
						res.setMainClass(tempMap.get("MAIN_CLASS")==null?"":tempMap.get("MAIN_CLASS").toString());
						res.setOriginalCurrency(tempMap.get("ORIGINAL_CURRENCY")==null?"":tempMap.get("ORIGINAL_CURRENCY").toString());
						res.setOurAssessment(tempMap.get("OUR_ASSESSMENT")==null?"":tempMap.get("OUR_ASSESSMENT").toString());
						res.setOverdueDays(tempMap.get("OVERDUE_DAYS")==null?"":tempMap.get("OVERDUE_DAYS").toString());
						res.setPofolioInoutLoss(tempMap.get("POFOLIO_INOUT_LOSS")==null?"":tempMap.get("POFOLIO_INOUT_LOSS").toString());
						res.setPofolioInoutPremium(tempMap.get("POFOLIO_INOUT_PREMIUM")==null?"":tempMap.get("POFOLIO_INOUT_PREMIUM").toString());
						res.setPofolioWithdrawDate(tempMap.get("POFOLIO_WITHDRAW_DATE")==null?"":tempMap.get("POFOLIO_WITHDRAW_DATE").toString());
						res.setPofolioWithdrawYrs(tempMap.get("POFOLIO_WITHDRAW_YRS")==null?"":tempMap.get("POFOLIO_WITHDRAW_YRS").toString());
						res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
						res.setRunoffClean(tempMap.get("RUNOFF_CLEAN")==null?"":tempMap.get("RUNOFF_CLEAN").toString());
						res.setShareSigned(tempMap.get("SHARE_SIGNED")==null?"":tempMap.get("SHARE_SIGNED").toString());
						res.setShareWritten(tempMap.get("SHARE_WRITTEN")==null?"":tempMap.get("SHARE_WRITTEN").toString());
						res.setStatementDueDays(tempMap.get("STATEMENT_DUE_DAYS")==null?"":tempMap.get("STATEMENT_DUE_DAYS").toString());
						res.setSubClass(tempMap.get("SUB_CLASS")==null?"":tempMap.get("SUB_CLASS").toString());
						res.setTreatyLimitDc(tempMap.get("TREATY_LIMIT_dc")==null?"":tempMap.get("TREATY_LIMIT_dc").toString());
						res.setTreatyLimitOc(tempMap.get("TREATY_LIMIT_OC")==null?"":tempMap.get("TREATY_LIMIT_OC").toString());
						res.setTreatyLimitShareDc(tempMap.get("TREATY_LIMIT_SHARE_dc")==null?"":tempMap.get("TREATY_LIMIT_SHARE_dc").toString());
						res.setTreatyLimitShareOc(tempMap.get("TREATY_LIMIT_SHARE_OC")==null?"":tempMap.get("TREATY_LIMIT_SHARE_OC").toString());
						res.setTreatyName(tempMap.get("TREATY_NAME")==null?"":tempMap.get("TREATY_NAME").toString());
						res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
						resList.add(res);
				}
					}
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}

		@Override
		public GetJVReportsRes getJVReports(GetJVReportsReq bean) {
			GetJVReportsRes response = new GetJVReportsRes();
			List<GetJVReportsRes1> resList = new ArrayList<GetJVReportsRes1>();
			try {
				String[] obj = new String[5];
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getStartDate();
				obj[2]=bean.getEndDate();
				obj[3]=bean.getJournalType();
				obj[4]=bean.getStatus();
				List<Map<String,Object>> list = queryImpl.selectList("GET_JV_REPORT", obj); 
				//JOURNAL_VIEW_REPORT is in an invalid state
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetJVReportsRes1 res = new GetJVReportsRes1();
						res.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
				        res.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
				        res.setSpc(tempMap.get("SPC")==null?"":tempMap.get("SPC").toString());
				        res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
				        res.setLedger(tempMap.get("LEDGER")==null?"":tempMap.get("LEDGER").toString());
				        res.setDebitOc(tempMap.get("DEBIT_OC")==null?"":tempMap.get("DEBIT_OC").toString());
				        res.setCreditOc(tempMap.get("CREDIT_OC")==null?"":tempMap.get("CREDIT_OC").toString());
				        res.setDebitDc(tempMap.get("DEBIT_DC")==null?"":tempMap.get("DEBIT_DC").toString());
				        res.setCreditDc(tempMap.get("CREDIT_DC")==null?"":tempMap.get("CREDIT_DC").toString());
				        res.setNarration(tempMap.get("NARRATION")==null?"":tempMap.get("NARRATION").toString());
				        res.setReversalTrxnNo(tempMap.get("REVERSAL_TRXN_NO")==null?"":tempMap.get("REVERSAL_TRXN_NO").toString());
				        res.setJournalId(tempMap.get("JOURNAL_ID")==null?"":tempMap.get("JOURNAL_ID").toString());
						resList.add(res);
					}
						}
					response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}


		@Override
		public GetInstallmentDueReportRes getInstallmentDueReport(GetInstallmentDueReportReq bean) {
			GetInstallmentDueReportRes response = new GetInstallmentDueReportRes();
			List<GetInstallmentDueReportRes1> resList = new ArrayList<GetInstallmentDueReportRes1>();
			try {
				String[] obj = new String[6];
				obj[0] = bean.getEndDate();
				obj[1] = bean.getBranchCode();
				obj[2] = bean.getProductId();
				obj[3] = bean.getCedingId();
				obj[4] = bean.getBrokerId();
				obj[5] = bean.getAllocationYN();
				// query arg count mismatch doubt
				List<Map<String,Object>> list = queryImpl.selectList("GET_INSTALLMENT_DUE_REPORT", obj); 
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetInstallmentDueReportRes1 res = new GetInstallmentDueReportRes1();
						res.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
						res.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
						res.setCedingCompany(tempMap.get("CEDING_COMPANY")==null?"":tempMap.get("CEDING_COMPANY").toString());
						res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
						res.setDepartmentName(tempMap.get("DEPARTMENT_NAME")==null?"":tempMap.get("DEPARTMENT_NAME").toString());
						res.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
						res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
						res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
						res.setInstallmentDate(tempMap.get("INSTALLMENT_DATE")==null?"":tempMap.get("INSTALLMENT_DATE").toString());
						res.setInstallmentNumber(tempMap.get("INSTALLMENT_NUMBER")==null?"":tempMap.get("INSTALLMENT_NUMBER").toString());
						res.setInsuredName(tempMap.get("INSURED_NAME")==null?"":tempMap.get("INSURED_NAME").toString());
						res.setLayer(tempMap.get("LAYER")==null?"":tempMap.get("LAYER").toString());
						res.setMndPremiumDc(tempMap.get("MND_PREMIUM_DC")==null?"":tempMap.get("MND_PREMIUM_DC").toString());
						res.setMndPremiumOc(tempMap.get("MND_PREMIUM_OC")==null?"":tempMap.get("MND_PREMIUM_OC").toString());
						res.setOverdueDays(tempMap.get("OVERDUE_DAYS")==null?"":tempMap.get("OVERDUE_DAYS").toString());
						res.setPpwDate(tempMap.get("PPW_DATE")==null?"":tempMap.get("PPW_DATE").toString());
						res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
						res.setRskProductid(tempMap.get("RSK_PRODUCTID")==null?"":tempMap.get("RSK_PRODUCTID").toString());
						res.setShareSignedPercent(tempMap.get("SHARE_SIGNED_PERCENT")==null?"":tempMap.get("SHARE_SIGNED_PERCENT").toString());
						res.setShareWrittenPercent(tempMap.get("SHARE_WRITTEN_PERCENT")==null?"":tempMap.get("SHARE_WRITTEN_PERCENT").toString());
						res.setTreatyName(tempMap.get("TREATY_NAME")==null?"":tempMap.get("TREATY_NAME").toString());;
						res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());;
						resList.add(res);						
					} 
					}
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}

		@Override
		public GetPortfolioOutPendingReportRes getPortfolioOutPendingReport(GetPortfolioOutPendingReportReq bean) {
			GetPortfolioOutPendingReportRes response = new GetPortfolioOutPendingReportRes();
			List<GetPortfolioOutPendingReportRes1> resList = new ArrayList<GetPortfolioOutPendingReportRes1>();
			try {
				String[] obj = new String[2];
				obj[0] = bean.getStartDate();
				obj[1] = bean.getBranchCode();
				List<Map<String,Object>> list = queryImpl.selectList("PORTFOLIO_OUT_PENDING_REPORT", obj); 
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPortfolioOutPendingReportRes1 res = new GetPortfolioOutPendingReportRes1();
						res.setAccountDate(tempMap.get("ACCOUNT_DATE")==null?"":tempMap.get("ACCOUNT_DATE").toString());
						res.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
						res.setBrokerCode(tempMap.get("BROKER_CODE")==null?"":tempMap.get("BROKER_CODE").toString());
						res.setCleancutRunoff(tempMap.get("CLEANCUT_RUNOFF")==null?"":tempMap.get("CLEANCUT_RUNOFF").toString());
						res.setCompanyCode(tempMap.get("COMPANY_CODE")==null?"":tempMap.get("COMPANY_CODE").toString());
						res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res.setDepartmentName(tempMap.get("DEPARTMENT_NAME")==null?"":tempMap.get("DEPARTMENT_NAME").toString());
						res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
						res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
						res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
						res.setRskRiskCovered(tempMap.get("RSK_RISK_COVERED")==null?"":tempMap.get("RSK_RISK_COVERED").toString());
						res.setRskRunOffYear(tempMap.get("RSK_RUN_OFF_YEAR")==null?"":tempMap.get("RSK_RUN_OFF_YEAR").toString());
						res.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());
						res.setStatementDueDays(tempMap.get("STATEMENT_DUE_DAYS")==null?"":tempMap.get("STATEMENT_DUE_DAYS").toString());
						res.setTreatyType(tempMap.get("TREATYTYPE")==null?"":tempMap.get("TREATYTYPE").toString());
						res.setTrfDate(tempMap.get("TRF_DATE")==null?"":tempMap.get("TRF_DATE").toString());
						res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());;
						resList.add(res);	
					} 
				}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
		}

		@Override
		public GetTrailBalanceReportRes getTrailBalanceReport(GetTrailBalanceReportReq bean) {
			GetTrailBalanceReportRes response = new GetTrailBalanceReportRes();
			List<GetTrailBalanceReportRes1> resList = new ArrayList<GetTrailBalanceReportRes1>();
			try {
				String[] obj = new String[2];
				obj[0] = bean.getStartDate();
				obj[1] = bean.getBranchCode();
			
				List<Map<String,Object>> list = queryImpl.selectList("GET_TRAIL_BALANCE_REPORT", obj); 
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetTrailBalanceReportRes1 res = new GetTrailBalanceReportRes1();
						res.setCreditdc(tempMap.get("CREDITDC")==null?"":tempMap.get("CREDITDC").toString());
						res.setCreditoc(tempMap.get("CREDITOC")==null?"":tempMap.get("CREDITOC").toString());
						res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
						res.setDebitdc(tempMap.get("DEBITDC")==null?"":tempMap.get("DEBITDC").toString());
						res.setDebitoc(tempMap.get("DEBITOC")==null?"":tempMap.get("DEBITOC").toString());
						res.setGrouped(tempMap.get("GROUPED")==null?"":tempMap.get("GROUPED").toString());	
						res.setLedger(tempMap.get("LEDGER")==null?"":tempMap.get("LEDGER").toString());
						res.setMainGroup(tempMap.get("MAIN_GROUP")==null?"":tempMap.get("MAIN_GROUP").toString());
						res.setNetdc(tempMap.get("NETDC")==null?"":tempMap.get("NETDC").toString());
						res.setNetoc(tempMap.get("NETOC")==null?"":tempMap.get("NETOC").toString());
						res.setPostingCredit(tempMap.get("POSTING_CREDIT")==null?"":tempMap.get("POSTING_CREDIT").toString());
						res.setPostingDebit(tempMap.get("POSTING_DEBIT")==null?"":tempMap.get("POSTING_DEBIT").toString());
						res.setPostingNet(tempMap.get("POSTING_NET")==null?"":tempMap.get("POSTING_NET").toString());
						resList.add(res);						
					} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}


		@Override
		public GetPptySOAPendingReportRes getPptySOAPendingReport(GetTrailBalanceReportReq bean) {
			GetPptySOAPendingReportRes response = new GetPptySOAPendingReportRes();
			List<GetPptySOAPendingReportRes1> resList = new ArrayList<GetPptySOAPendingReportRes1>();
			try {
				String[] obj = new String[2];
				obj[0] = bean.getStartDate();
				obj[1] = bean.getBranchCode();
				
				List<Map<String,Object>> list = queryImpl.selectList("GET_PPTY_SOA_PENDING_REPORT", obj); 
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPptySOAPendingReportRes1 res = new GetPptySOAPendingReportRes1();
						res.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
						res.setCleancutRunoff(tempMap.get("CLEANCUT_RUNOFF")==null?"":tempMap.get("CLEANCUT_RUNOFF").toString());
						res.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
						res.setContractClass(tempMap.get("CONTRACT_CLASS")==null?"":tempMap.get("CONTRACT_CLASS").toString());
						res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
						res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
						res.setInwardBusType(tempMap.get("INWARD_BUS_TYPE")==null?"":tempMap.get("INWARD_BUS_TYPE").toString());
						res.setOverDueDays(tempMap.get("OVER_DUE_DAYS")==null?"":tempMap.get("OVER_DUE_DAYS").toString());
						res.setPeriodicity(tempMap.get("PERIODICITY")==null?"":tempMap.get("PERIODICITY").toString());	
						res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
						res.setRskPfcId(tempMap.get("RSK_PFC_ID")==null?"":tempMap.get("RSK_PFC_ID").toString());
						res.setRskReceiptStatement(tempMap.get("RSK_RECEIPT_STATEMENT")==null?"":tempMap.get("RSK_RECEIPT_STATEMENT").toString());
						res.setRskRunOffYear(tempMap.get("RSK_RUN_OFF_YEAR")==null?"":tempMap.get("RSK_RUN_OFF_YEAR").toString());
						res.setRskShareSigned(tempMap.get("RSK_SHARE_SIGNED")==null?"":tempMap.get("RSK_SHARE_SIGNED").toString());
						res.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());
						res.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
						res.setStatementDueDate(tempMap.get("STATEMENT_DUE_DATE")==null?"":tempMap.get("STATEMENT_DUE_DATE").toString());
						res.setTreatyType(tempMap.get("TREATY_TYPE")==null?"":tempMap.get("TREATY_TYPE").toString());;
						resList.add(res);						
			} }
		response.setCommonResponse(resList);
		response.setMessage("Success");
		response.setIsError(false);
		} catch (Exception e) {
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
		}

		@Override
		public GetUWStatisticsReportRes getUWStatisticsReport(GetUWStatisticsReportReq bean) {
			GetUWStatisticsReportRes response = new GetUWStatisticsReportRes();
			List<GetUWStatisticsReportRes1> resList = new ArrayList<GetUWStatisticsReportRes1>();
			String args[]=null;
			try {
				args=new String[7];
				args[0] = bean.getPeriodFrom();
				args[1] = bean.getPeriodTo();
				args[2] = bean.getUwFrom();
				args[3] = bean.getUwTo();
				args[4] = bean.getContractNo();
				args[5] = bean.getBranchCode();
				args[6] = bean.getType();
				List<Map<String,Object>> list = queryImpl.selectList("GET_RENEWAL_STATISTICS_REPORT", args); 
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetUWStatisticsReportRes1 res = new GetUWStatisticsReportRes1();
						res.setAccountingPeriodDate(tempMap.get("ACCOUNTING_PERIOD_DATE")==null?"":tempMap.get("ACCOUNTING_PERIOD_DATE").toString());
						res.setAccountPeriodQtr(tempMap.get("ACCOUNT_PERIOD_QTR")==null?"":tempMap.get("ACCOUNT_PERIOD_QTR").toString());
						res.setAmountDc(tempMap.get("AMOUNT_DC")==null?"":tempMap.get("AMOUNT_DC").toString());
						res.setAmountOc(tempMap.get("AMOUNT_OC")==null?"":tempMap.get("AMOUNT_OC").toString());
						res.setBrokerCode(tempMap.get("BROKER_CODE")==null?"":tempMap.get("BROKER_CODE").toString());
						res.setCedantReference(tempMap.get("CEDANT_REFERENCE")==null?"":tempMap.get("CEDANT_REFERENCE").toString());
						res.setClaimNo(tempMap.get("CLAIM_NO")==null?"":tempMap.get("CLAIM_NO").toString());
						res.setCnt(tempMap.get("CNT")==null?"":tempMap.get("CNT").toString());
						res.setCompanyCode(tempMap.get("COMPANY_CODE")==null?"":tempMap.get("COMPANY_CODE").toString());;
						res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
						res.setDepartmentName(tempMap.get("DEPARTMENT_NAME")==null?"":tempMap.get("DEPARTMENT_NAME").toString());
						res.setDocType(tempMap.get("DOC_TYPE")==null?"":tempMap.get("DOC_TYPE").toString());
						res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
						res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
						res.setItem(tempMap.get("ITEM")==null?"":tempMap.get("ITEM").toString());
						res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
						res.setM1(tempMap.get("M1")==null?"":tempMap.get("M1").toString());
						res.setM2(tempMap.get("M2")==null?"":tempMap.get("M2").toString());
						res.setM3(tempMap.get("M3")==null?"":tempMap.get("M3").toString());
						res.setM4(tempMap.get("M4")==null?"":tempMap.get("M4").toString());
						res.setM5(tempMap.get("M5")==null?"":tempMap.get("M5").toString());
						res.setM6(tempMap.get("M6")==null?"":tempMap.get("M6").toString());
						res.setM7(tempMap.get("M7")==null?"":tempMap.get("M7").toString());
						res.setM8(tempMap.get("M8")==null?"":tempMap.get("M8").toString());
						res.setM9(tempMap.get("M9")==null?"":tempMap.get("M9").toString());
						res.setM10(tempMap.get("M10")==null?"":tempMap.get("M10").toString());
						res.setM11(tempMap.get("M11")==null?"":tempMap.get("M11").toString());
						res.setM12(tempMap.get("M12")==null?"":tempMap.get("M12").toString());
						res.setM13(tempMap.get("M13")==null?"":tempMap.get("M13").toString());
						res.setM14(tempMap.get("M14")==null?"":tempMap.get("M14").toString());
						res.setM15(tempMap.get("M15")==null?"":tempMap.get("M15").toString());
						res.setM16(tempMap.get("M16")==null?"":tempMap.get("M16").toString());
						res.setM17(tempMap.get("M17")==null?"":tempMap.get("M17").toString());
						res.setM18(tempMap.get("M18")==null?"":tempMap.get("M18").toString());
						res.setM19(tempMap.get("M19")==null?"":tempMap.get("M19").toString());
						res.setM20(tempMap.get("M20")==null?"":tempMap.get("M20").toString());
						res.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());;
						res.setRskInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
						res.setRskSpfcid(tempMap.get("RSK_SPFCID")==null?"":tempMap.get("RSK_SPFCID").toString());
						res.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
						res.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
						res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());						;
						resList.add(res);						
			} }
		response.setCommonResponse(resList);
		response.setMessage("Success");
		response.setIsError(false);
		} catch (Exception e) {
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
		}


		@Override
		public GetIEReportRes getIEReport(GetIEReportReq bean) {
			GetIEReportRes response = new GetIEReportRes();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			List<GetIEReportRes1> resList = new ArrayList<GetIEReportRes1>();
			String args[]=null;
			String uwYear ="";
			try {
				args = new String[3];
				args[0]=bean.getProposalNo();
				args[1] = bean.getContractNo();
				args[2] = bean.getLayerNo();
				list = queryImpl.selectList("GET_UW_YEAR", args); 
				if (!CollectionUtils.isEmpty(list)) {
					uwYear = list.get(0).get("UW_YEAR") == null ? ""
							: list.get(0).get("UW_YEAR").toString();
				}
				args=new String[6];
				args[0] = bean.getStartDate();
				args[1] = bean.getEndDate();
				args[2] = bean.getContractNo();
				args[3] = bean.getLayerNo();
				args[4] = bean.getBranchCode();
				args[5] = uwYear;
				list = queryImpl.selectList("GET_IE_REPORT", args); 
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetIEReportRes1 res = new GetIEReportRes1();
						res.setRetrobrokername(tempMap.get("RETROBROKERNAME")==null?"":tempMap.get("RETROBROKERNAME").toString());
						res.setRetroBroker(tempMap.get("RETRO_BROKER")==null?"":tempMap.get("RETRO_BROKER").toString());
						res.setRetrocessionairename(tempMap.get("RETROCESSIONAIRENAME")==null?"":tempMap.get("RETROCESSIONAIRENAME").toString());
						res.setRetrocessionaire(tempMap.get("RETROCESSIONAIRE")==null?"":tempMap.get("RETROCESSIONAIRE").toString());
						res.setInwardproposalno(tempMap.get("INWARDPROPOSALNO")==null?"":tempMap.get("INWARDPROPOSALNO").toString());
						res.setInwardcontractno(tempMap.get("INWARDCONTRACTNO")==null?"":tempMap.get("INWARDCONTRACTNO").toString());
						res.setRskPfcid(tempMap.get("")==null?"RSK_PFCID":tempMap.get("RSK_PFCID").toString());
						res.setInwardBusType(tempMap.get("INWARD_BUS_TYPE")==null?"":tempMap.get("INWARD_BUS_TYPE").toString());
						res.setTreatytype(tempMap.get("TREATYTYPE")==null?"":tempMap.get("TREATYTYPE").toString());
						res.setProductId(tempMap.get("PRODUCT_ID")==null?"":tempMap.get("PRODUCT_ID").toString());
						res.setLayerno(tempMap.get("LAYERNO")==null?"":tempMap.get("LAYERNO").toString());
						res.setUwy(tempMap.get("UWY")==null?"":tempMap.get("UWY").toString());
						res.setInwardbusiness(tempMap.get("INWARDBUSINESS")==null?"":tempMap.get("INWARDBUSINESS").toString());
						res.setSubclass(tempMap.get("SUBCLASS")==null?"":tempMap.get("SUBCLASS").toString());
						res.setTransactionno(tempMap.get("TRANSACTIONNO")==null?"":tempMap.get("TRANSACTIONNO").toString());
						res.setTransactiondate(tempMap.get("TRANSACTIONDATE")==null?"":tempMap.get("TRANSACTIONDATE").toString());
						res.setTransactiontype(tempMap.get("TRANSACTIONTYPE")==null?"":tempMap.get("TRANSACTIONTYPE").toString());
						res.setInwardcurrencyname(tempMap.get("INWARDCURRENCYNAME")==null?"":tempMap.get("INWARDCURRENCYNAME").toString());
						res.setInwardexchangerate(tempMap.get("INWARDEXCHANGERATE")==null?"":tempMap.get("INWARDEXCHANGERATE").toString());
						res.setPremiumIc(tempMap.get("PREMIUM_IC")==null?"":tempMap.get("PREMIUM_IC").toString());
						res.setCommissionIc(tempMap.get("COMMISSION_IC")==null?"":tempMap.get("COMMISSION_IC").toString());
						res.setBrokerageIc(tempMap.get("BROKERAGE_IC")==null?"":tempMap.get("BROKERAGE_IC").toString());
						res.setInterestIc(tempMap.get("INTEREST_IC")==null?"":tempMap.get("INTEREST_IC").toString());
						res.setTaxIc(tempMap.get("TAX_IC")==null?"":tempMap.get("TAX_IC").toString());
						res.setInwardoverriderIc(tempMap.get("INWARDOVERRIDER_IC")==null?"":tempMap.get("INWARDOVERRIDER_IC").toString());
						res.setOthercostIc(tempMap.get("OTHERCOST_IC")==null?"":tempMap.get("OTHERCOST_IC").toString());
						res.setClamspaidIc(tempMap.get("CLAMSPAID_IC")==null?"":tempMap.get("CLAMSPAID_IC").toString());
						res.setPremiumreserveretainedIc(tempMap.get("PREMIUMRESERVERETAINED_IC")==null?"":tempMap.get("PREMIUMRESERVERETAINED_IC").toString());
						res.setPremiumreservereleasedIc(tempMap.get("PREMIUMRESERVERELEASED_IC")==null?"":tempMap.get("PREMIUMRESERVERELEASED_IC").toString());
						res.setLossreserveretainedIc(tempMap.get("LOSSRESERVERETAINED_IC")==null?"":tempMap.get("LOSSRESERVERETAINED_IC").toString());
						res.setLossreservereleasedIc(tempMap.get("LOSSRESERVERELEASED_IC")==null?"":tempMap.get("LOSSRESERVERELEASED_IC").toString());
						res.setOutwardoverriderIc(tempMap.get("OUTWARDOVERRIDER_IC")==null?"":tempMap.get("OUTWARDOVERRIDER_IC").toString());
						res.setNetdueIc(tempMap.get("NETDUE_IC")==null?"":tempMap.get("NETDUE_IC").toString());
						res.setPremiumInr(tempMap.get("PREMIUM_INR")==null?"":tempMap.get("PREMIUM_INR").toString());
						res.setCommissionInr(tempMap.get("COMMISSION_INR")==null?"":tempMap.get("COMMISSION_INR").toString());
						res.setBrokerageInr(tempMap.get("BROKERAGE_INR")==null?"":tempMap.get("BROKERAGE_INR").toString());
						res.setInterestInr(tempMap.get("INTEREST_INR")==null?"":tempMap.get("INTEREST_INR").toString());
						res.setTaxInr(tempMap.get("TAX_INR")==null?"":tempMap.get("TAX_INR").toString());
						res.setInwardoverriderInr(tempMap.get("INWARDOVERRIDER_INR")==null?"":tempMap.get("INWARDOVERRIDER_INR").toString());
						res.setOthercostInr(tempMap.get("OTHERCOST_INR")==null?"":tempMap.get("OTHERCOST_INR").toString());
						res.setClamspaidInr(tempMap.get("CLAMSPAID_INR")==null?"":tempMap.get("CLAMSPAID_INR").toString());
						res.setPremiumreserveretainedInr(tempMap.get("PREMIUMRESERVERETAINED_INR")==null?"":tempMap.get("PREMIUMRESERVERETAINED_INR").toString());
						res.setPremiumreservereleasedInr(tempMap.get("PREMIUMRESERVERELEASED_INR")==null?"":tempMap.get("PREMIUMRESERVERELEASED_INR").toString());
						res.setLossreserveretainedInr(tempMap.get("LOSSRESERVERETAINED_INR")==null?"":tempMap.get("LOSSRESERVERETAINED_INR").toString());
						res.setLossreservereleasedInr(tempMap.get("LOSSRESERVERELEASED_INR")==null?"":tempMap.get("LOSSRESERVERELEASED_INR").toString());
						res.setOutwardoverriderInr(tempMap.get("OUTWARDOVERRIDER_INR")==null?"":tempMap.get("OUTWARDOVERRIDER_INR").toString());
						res.setNetdueInr(tempMap.get("NETDUE_INR")==null?"":tempMap.get("NETDUE_INR").toString());
						res.setAmendId(tempMap.get("AMEND_ID")==null?"":tempMap.get("AMEND_ID").toString());
						res.setBranchCode(tempMap.get("BRANCH_CODE")==null?"":tempMap.get("BRANCH_CODE").toString());
						res.setPremiumClass(tempMap.get("PREMIUM_CLASS")==null?"":tempMap.get("PREMIUM_CLASS").toString());
						res.setProposalNo1(tempMap.get("PROPOSAL_NO1")==null?"":tempMap.get("PROPOSAL_NO1").toString());
						res.setItemNo1(tempMap.get("ITEM_NO1")==null?"":tempMap.get("ITEM_NO1").toString());
						res.setItemType1(tempMap.get("ITEM_TYPE1")==null?"":tempMap.get("ITEM_TYPE1").toString());
						res.setItemInclusionExclusion1(tempMap.get("ITEM_INCLUSION_EXCLUSION1")==null?"":tempMap.get("ITEM_INCLUSION_EXCLUSION1").toString());
						res.setEffectiveDate1(tempMap.get("EFFECTIVE_DATE1")==null?"":tempMap.get("EFFECTIVE_DATE1").toString());
						res.setBranchCode1(tempMap.get("BRANCH_CODE1")==null?"":tempMap.get("BRANCH_CODE1").toString());
						res.setProposalNo2(tempMap.get("PROPOSAL_NO2")==null?"":tempMap.get("PROPOSAL_NO2").toString());
						res.setItemNo2(tempMap.get("ITEM_NO2")==null?"":tempMap.get("ITEM_NO2").toString());
						res.setItemType2(tempMap.get("ITEM_TYPE2")==null?"":tempMap.get("ITEM_TYPE2").toString());
						res.setItemInclusionExclusion2(tempMap.get("ITEM_INCLUSION_EXCLUSION2")==null?"":tempMap.get("ITEM_INCLUSION_EXCLUSION2").toString());
						res.setEffectiveDate2(tempMap.get("EFFECTIVE_DATE2")==null?"":tempMap.get("EFFECTIVE_DATE2").toString());
						res.setBranchCode2(tempMap.get("BRANCH_CODE2")==null?"":tempMap.get("BRANCH_CODE2").toString());
						res.setProposalNo3(tempMap.get("PROPOSAL_NO3")==null?"":tempMap.get("PROPOSAL_NO3").toString());
						res.setItemNo3(tempMap.get("ITEM_NO3")==null?"":tempMap.get("ITEM_NO3").toString());
						res.setItemType3(tempMap.get("ITEM_TYPE3")==null?"":tempMap.get("ITEM_TYPE3").toString());
						res.setItemInclusionExclusion3(tempMap.get("ITEM_INCLUSION_EXCLUSION3")==null?"":tempMap.get("ITEM_INCLUSION_EXCLUSION3").toString());
						res.setEffectiveDate3(tempMap.get("EFFECTIVE_DATE3")==null?"":tempMap.get("EFFECTIVE_DATE3").toString());
						res.setBranchCode3(tempMap.get("BRANCH_CODE3")==null?"":tempMap.get("BRANCH_CODE3").toString());
						res.setProposalNo4(tempMap.get("PROPOSAL_NO4")==null?"":tempMap.get("PROPOSAL_NO4").toString());
						res.setItemNo4(tempMap.get("ITEM_NO4")==null?"":tempMap.get("ITEM_NO4").toString());
						res.setItemType4(tempMap.get("ITEM_TYPE4")==null?"":tempMap.get("ITEM_TYPE4").toString());
						res.setItemInclusionExclusion4(tempMap.get("ITEM_INCLUSION_EXCLUSION4")==null?"":tempMap.get("ITEM_INCLUSION_EXCLUSION4").toString());
						res.setEffectiveDate4(tempMap.get("EFFECTIVE_DATE4")==null?"":tempMap.get("EFFECTIVE_DATE4").toString());
						res.setBranchCode4(tempMap.get("BRANCH_CODE4")==null?"":tempMap.get("BRANCH_CODE4").toString());
						resList.add(res);						
					} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
			return response;
		}


		@Override
		public GetPptyOSLRClaimReportRes getPptyOSLRClaimReport(GetTrailBalanceReportReq bean) {
			GetPptyOSLRClaimReportRes response = new GetPptyOSLRClaimReportRes();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<GetPptyOSLRClaimReportRes1> resList = new ArrayList<GetPptyOSLRClaimReportRes1>();
			try {
				String[] obj = new String[2];
				obj[0] = bean.getStartDate();
				obj[1] = bean.getBranchCode();
				list = queryImpl.selectList("GET_PPTY_OSLR_CLAIM_REPORT", obj); 
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetPptyOSLRClaimReportRes1 res = new GetPptyOSLRClaimReportRes1();
						res.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
						res.setTransactionDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
						res.setDocType(tempMap.get("DOC_TYPE")==null?"":tempMap.get("DOC_TYPE").toString());
						res.setCedantReference(tempMap.get("CEDANT_REFERENCE")==null?"":tempMap.get("CEDANT_REFERENCE").toString());
						res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
						res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
						res.setContractClass(tempMap.get("CONTRACT_CLASS")==null?"":tempMap.get("CONTRACT_CLASS").toString());
						res.setPremiumClass(tempMap.get("PREMIUM_CLASS")==null?"":tempMap.get("PREMIUM_CLASS").toString());
						res.setRskSpfcid(tempMap.get("RSK_SPFCID")==null?"":tempMap.get("RSK_SPFCID").toString());
						res.setInceptionDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
						res.setExpiryDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
						res.setCompanyCode(tempMap.get("COMPANY_CODE")==null?"":tempMap.get("COMPANY_CODE").toString());
						res.setBrokerCode(tempMap.get("BROKER_CODE")==null?"":tempMap.get("BROKER_CODE").toString());
						res.setRskTreatyid(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());
						res.setAccountPeriodQtr(tempMap.get("ACCOUNT_PERIOD_QTR")==null?"":tempMap.get("ACCOUNT_PERIOD_QTR").toString());
						res.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
						res.setStatementRecdDate(tempMap.get("STATEMENT_RECD_DATE")==null?"":tempMap.get("STATEMENT_RECD_DATE").toString());
						res.setCurrency(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
						res.setAmountOc(tempMap.get("AMOUNT_OC")==null?"":tempMap.get("AMOUNT_OC").toString());
						res.setAmountBkdDc(tempMap.get("AMOUNT_BKD_DC")==null?"":tempMap.get("AMOUNT_BKD_DC").toString());
						res.setAmountRstDc(tempMap.get("AMOUNT_RST_DC")==null?"":tempMap.get("AMOUNT_RST_DC").toString());
						res.setSectionName(tempMap.get("SECTION_NAME")==null?"":tempMap.get("SECTION_NAME").toString());
						res.setAccountingPeriodDate(tempMap.get("ACCOUNTING_PERIOD_DATE")==null?"":tempMap.get("ACCOUNTING_PERIOD_DATE").toString());
						resList.add(res);						
					} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}

		@Override
		public GetCompanyInfoListRes getCompanyInfoList() {
			GetCompanyInfoListRes response = new GetCompanyInfoListRes();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<GetCompanyInfoListRes1> resList = new ArrayList<GetCompanyInfoListRes1>();
			try{
				list = queryImpl.selectList("report.select.getCompanyInfoList", new String[] {"RI01","Y"});	
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
						GetCompanyInfoListRes1 res = new GetCompanyInfoListRes1();
						res.setCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
						  res.setTelephone(tempMap.get("TELEPHONE")==null?"":tempMap.get("TELEPHONE").toString());
						  res.setMobile(tempMap.get("MOBILE")==null?"":tempMap.get("MOBILE").toString());
						  res.setFax(tempMap.get("FAX")==null?"":tempMap.get("FAX").toString());
						  res.setEmail(tempMap.get("EMAIL")==null?"":tempMap.get("EMAIL").toString());
						  res.setPobox(tempMap.get("POBOX")==null?"":tempMap.get("POBOX").toString());
						  res.setAddress1(tempMap.get("ADDRESS1")==null?"":tempMap.get("ADDRESS1").toString());
						  res.setAddress2(tempMap.get("ADDRESS2")==null?"":tempMap.get("ADDRESS2").toString());
						  res.setCity(tempMap.get("CITY")==null?"":tempMap.get("CITY").toString());
						  res.setCountry(tempMap.get("COUNTRY")==null?"":tempMap.get("COUNTRY").toString());
						  res.setBaseCurrency(tempMap.get("BASE_CURRENCY")==null?"":tempMap.get("BASE_CURRENCY").toString());
						  res.setDestCurrency(tempMap.get("DEST_CURRENCY")==null?"":tempMap.get("DEST_CURRENCY").toString());
						  res.setFinancialYear(tempMap.get("FINANCIAL_YEAR")==null?"":tempMap.get("FINANCIAL_YEAR").toString());
						  res.setBandReport(tempMap.get("BAND_REPORT")==null?"":tempMap.get("BAND_REPORT").toString());
						  res.setCurrencyDecimal(tempMap.get("CURRENCY_DECIMAL")==null?"":tempMap.get("CURRENCY_DECIMAL").toString());
						  res.setWebSite(tempMap.get("WEB_SITE")==null?"":tempMap.get("WEB_SITE").toString());
						resList.add(res);						
					} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}

		@Override
		public GetColumnInfoListRes getColumnInfoList(GetColumnInfoListReq bean) {
			GetColumnInfoListRes response = new GetColumnInfoListRes();
			List<Map<String,Object>> columnInfo=null;
			List<GetColumnInfoListRes1> resList = new ArrayList<GetColumnInfoListRes1>();
			try{
				String query = "report.select.getColumnInfoList";
				String obj[]=new String[5];
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getProductId();
				if("1".equals(bean.getProductId()))
					obj[2]=(StringUtils.isBlank(bean.getDept())||"-1".equals(bean.getDept())?"2":bean.getDept());
				else
					obj[2]=(StringUtils.isBlank(bean.getDept())||"-1".equals(bean.getDept())?"0":bean.getDept());
				if("26".equalsIgnoreCase(bean.getTypeId())){
					obj[3]="9";	
				}
				else{
				obj[3]=bean.getTypeId();
				}
				obj[4]="Y";
				
				 columnInfo =	queryImpl.selectList(query, obj);
			
				if("N".equals(bean.getShowAllFields()) && "9".equals(bean.getTypeId())){
					query = "SELECT EXCEL_HEADER_NAME,FIELD_NAME,DATA_TYPE,SUMATIONYN,FORMULA FROM XL_DOWNLOAD_CONFIG_MASTER WHERE  TYPE_ID=20 AND STATUS='Y' ORDER BY SNO";
					
					columnInfo =	queryImpl.selectSingle(query, new String[] {});
				}else if("N".equals(bean.getShowAllFields()) && "21".equals(bean.getTypeId())){
					query = "SELECT EXCEL_HEADER_NAME,FIELD_NAME,DATA_TYPE,SUMATIONYN,FORMULA FROM XL_DOWNLOAD_CONFIG_MASTER WHERE  TYPE_ID=22 AND STATUS='Y' ORDER BY SNO";
					
					columnInfo =	queryImpl.selectSingle(query, new String[] {});
				}else if("36".equals(bean.getTypeId()) || "11".equals(bean.getTypeId()) || "54".equals(bean.getTypeId())){
					query = "SELECT EXCEL_HEADER_NAME,FIELD_NAME,DATA_TYPE,SUMATIONYN,FORMULA FROM XL_DOWNLOAD_CONFIG_MASTER WHERE  TYPE_ID=? AND STATUS='Y' ORDER BY SNO";
					String obj1[]=new String[1];
					obj1[0]=bean.getTypeId();
					columnInfo =	queryImpl.selectList(query, obj1);
				}
				if(columnInfo!=null && columnInfo.size()>0){
					for(Map<String, Object> tempMap : columnInfo) {
						GetColumnInfoListRes1 res = new GetColumnInfoListRes1();
						res.setExcelHeaderName(tempMap.get("EXCEL_HEADER_NAME")==null?"":tempMap.get("EXCEL_HEADER_NAME").toString()); 
						res.setFieldName(tempMap.get("FIELD_NAME")==null?"":tempMap.get("FIELD_NAME").toString());
						res.setDataType(tempMap.get("DATA_TYPE")==null?"":tempMap.get("DATA_TYPE").toString());
						res.setSumationyn(tempMap.get("SUMATIONYN")==null?"":tempMap.get("SUMATIONYN").toString());
						res.setFormula(tempMap.get("FORMULA")==null?"":tempMap.get("FORMULA").toString());
						resList.add(res);						
					} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}


		@Override
		public GetMoveMentPageRes getMoveMentPage(GetMoveMentPageReq req) {
			GetMoveMentPageRes response = new GetMoveMentPageRes();
			GetMoveMentPageRes1 bean  = new GetMoveMentPageRes1();
			String[] args;
			String query="";
			args=new String[5];
			try {
			args[0]=req.getAccper();
			args[1]=req.getAccountDate();
			args[2]=req.getBranchCode();
			args[3]=req.getMovementType();
			args[4]=req.getMovId();
			if("MovDisPage".equalsIgnoreCase(req.getDisplay())){
				query= "report.select.discrepancies";
			}else{
				query= "report.select.moveMent";
			} 
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
			list =queryImpl.selectList(query,args);
			if(list!=null && list.size()>0){
				Map<String,Object> tempMap = list.get(0);
				
				if("MovDisPage".equalsIgnoreCase(req.getDisplay())){
					bean.setAccper(tempMap.get("ACCOUNT_QTR")==null?"":tempMap.get("ACCOUNT_QTR").toString());
					bean.setAccountDate(tempMap.get("ACCOUNT_YEAR")==null?"":tempMap.get("ACCOUNT_YEAR").toString());
					bean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					bean.setSpc(tempMap.get("SPC_NAME")==null?"":tempMap.get("SPC_NAME").toString());
					bean.setCurrencyname(tempMap.get("CURRENCY")==null?"":tempMap.get("CURRENCY").toString());
					bean.setPreviousPremium(tempMap.get("PREV_AMT_DC")==null?"":tempMap.get("PREV_AMT_DC").toString());
					bean.setCurrentPremium(tempMap.get("AMOUNT_DC")==null?"":tempMap.get("AMOUNT_DC").toString());
					bean.setDiffPremium(tempMap.get("PRM_DIFF")==null?"":tempMap.get("PRM_DIFF").toString());
					bean.setCurrentGrossPre((tempMap.get("GROSS_PREMIUM_DC"))==null?"":tempMap.get("GROSS_PREMIUM_DC").toString());
					bean.setPreGrossPre((tempMap.get("GROSS_PREMIUM_DC_PREV"))==null?"":tempMap.get("GROSS_PREMIUM_DC_PREV").toString());
					bean.setDiffGrossPre((tempMap.get("GROSS_PREMIUM_DC_DIFF"))==null?"":tempMap.get("GROSS_PREMIUM_DC_DIFF").toString());
					bean.setCurrentGrossAcq((tempMap.get("GROSS_ACQUISTION_COST_DC"))==null?"":tempMap.get("GROSS_ACQUISTION_COST_DC").toString());
					bean.setPreviousGrossAcq((tempMap.get("GROSS_ACQUISTION_COST_DC_PREV"))==null?"":tempMap.get("GROSS_ACQUISTION_COST_DC_PREV").toString());
					bean.setDiffGrossAcq((tempMap.get("GROSS_ACQUISTION_COST_DC_DIFF"))==null?"":tempMap.get("GROSS_ACQUISTION_COST_DC_DIFF").toString());
					bean.setCurrentPremiumDepositRet(((tempMap.get("PREMIUM_DEPOSIT_RETAINED_DC")))==null?"":tempMap.get("PREMIUM_DEPOSIT_RETAINED_DC").toString());
					bean.setPreviousPremiumDepositRet(((tempMap.get("PREM_DEP_RETAINED_DC_PREV")))==null?"":tempMap.get("PREM_DEP_RETAINED_DC_PREV").toString());
					bean.setDiffPremiumDepositRet(((tempMap.get("PREM_DEP_RETAINED_DC_DIFF")))==null?"":tempMap.get("PREM_DEP_RETAINED_DC_DIFF").toString());
					bean.setCurrentPremiumDepositRel(((tempMap.get("PREMIUM_DEPOSIT_RELEASED_DC")))==null?"":tempMap.get("PREMIUM_DEPOSIT_RELEASED_DC").toString());
					bean.setPreviousPremiumDepositRel(((tempMap.get("PREM_DEP_RELEASED_DC_PREV")))==null?"":tempMap.get("PREM_DEP_RELEASED_DC_PREV").toString());
					bean.setDiffPremiumDepositRel(((tempMap.get("PREM_DEP_RELEASED_DC_DIFF")))==null?"":tempMap.get("PREM_DEP_RELEASED_DC_DIFF").toString());
					bean.setCurrentClaimDepositRet(((tempMap.get("CLAIM_DEPOSIT_RETAINED_DC")))==null?"":tempMap.get("CLAIM_DEPOSIT_RETAINED_DC").toString());
					bean.setPreviousClaimDepositRet(((tempMap.get("CLAIM_DEPOSIT_RETAINED_DC_PREV")))==null?"":tempMap.get("CLAIM_DEPOSIT_RETAINED_DC_PREV").toString());
					bean.setDiffClaimDepositRet(((tempMap.get("CLAIM_DEPOSIT_RETAINED_DC_DIFF")))==null?"":tempMap.get("CLAIM_DEPOSIT_RETAINED_DC_DIFF").toString());
					bean.setCurrentClaimDepositRel(((tempMap.get("CLAIM_DEPOSIT_RELEASED_DC")))==null?"":tempMap.get("CLAIM_DEPOSIT_RELEASED_DC").toString());
					bean.setPreviousClaimDepositRel(((tempMap.get("CLAIM_DEPOSIT_RELEASED_DC_PREV")))==null?"":tempMap.get("CLAIM_DEPOSIT_RELEASED_DC_PREV").toString());
					bean.setDiffClaimDepositRel(((tempMap.get("CLAIM_DEPOSIT_RELEASED_DC_DIFF")))==null?"":tempMap.get("CLAIM_DEPOSIT_RELEASED_DC_DIFF").toString());
					bean.setCurrentInterestDeposit(((tempMap.get("INTEREST_ON_DEPOSIT_DC")))==null?"":tempMap.get("INTEREST_ON_DEPOSIT_DC").toString());
					bean.setPreviousInterestDeposit(((tempMap.get("INTEREST_ON_DEPOSIT_DC_PREV")))==null?"":tempMap.get("INTEREST_ON_DEPOSIT_DC_PREV").toString());
					bean.setDiffInterestDeposit(((tempMap.get("INTEREST_ON_DEPOSIT_DC_DIFF")))==null?"":tempMap.get("INTEREST_ON_DEPOSIT_DC_DIFF").toString());
					bean.setCurrentGrossClaimPaid(((tempMap.get("GROSS_CLAIMS_PAID_DC")))==null?"":tempMap.get("GROSS_CLAIMS_PAID_DC").toString());
					bean.setPreviousGrossClaimPaid(((tempMap.get("GROSS_CLAIMS_PAID_DC_PREV")))==null?"":tempMap.get("GROSS_CLAIMS_PAID_DC_PREV").toString());
					bean.setDiffGrossClaimPaid(((tempMap.get("GROSS_CLAIMS_PAID_DC_DIFF")))==null?"":tempMap.get("GROSS_CLAIMS_PAID_DC_DIFF").toString());
				}else{
					bean.setSNo(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					bean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					bean.setSpc(tempMap.get("SPC_NAME")==null?"":tempMap.get("SPC_NAME").toString());
					bean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
					bean.setSumofPrm(fm.formatter(tempMap.get("SUMOFTOTPR")==null?"0":tempMap.get("SUMOFTOTPR").toString()));
					bean.setSumofPrmDC(fm.formatter(tempMap.get("SUMOFTOTPR_DC")==null?"0":tempMap.get("SUMOFTOTPR_DC").toString()));
				} 
				}
			response.setCommonResponse(bean);
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}


		@Override
		public GetJournalListCrntRes getJournalListCrnt(GetJournalListCrntReq bean) {
			GetJournalListCrntRes response = new GetJournalListCrntRes();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			List<GetJournalListCrntRes1> resList = new ArrayList<GetJournalListCrntRes1>();
			try{
				String query="";
				String[] args = null;
				if ("15".equalsIgnoreCase(bean.getTypeId())) {
					args=new String[6];			
					args[0]=bean.getBranchCode();
					args[1]=bean.getUwYear();
					args[2]=bean.getMovementId();
					args[3]=bean.getAccountDate();
					args[4]=bean.getBranchCode();
					args[5]=bean.getBranchCode();
					query="BOOKED_UPR_CRNT";
				}
				else if ("16".equalsIgnoreCase(bean.getTypeId())) {
					args=new String[6];			
					args[0]=bean.getBranchCode();
					args[1]=bean.getUwYear();
					args[2]=bean.getMovementId();
					args[3]=bean.getAccountDate();
					args[4]=bean.getBranchCode();
					args[5]=bean.getBranchCode();
					query="BOOKED_PREMIUM_CRNT";
				}				
				else if ("17".equalsIgnoreCase(bean.getTypeId())) {
					args=new String[6];			
					args[0]=bean.getBranchCode();
					args[1]=bean.getUwYear();
					args[2]=bean.getMovementId();
					args[3]=bean.getAccountDate();
					args[4]=bean.getBranchCode();
					args[5]=bean.getBranchCode();
					query="BOOKED_UPR_CRNT";
				}				
				else if ("18".equalsIgnoreCase(bean.getTypeId())) {
					args=new String[6];			
					args[0]=bean.getBranchCode();				
					args[1]=bean.getUwYear();
					args[2]=bean.getMovementId();
					args[3]=bean.getAccountDate();
					args[4]=bean.getBranchCode();
					args[5]=bean.getBranchCode();
					query="PIPELINE_WRITTEN_CRNT";
				}
				list =queryImpl.selectList(query,args);
				if(list!=null && list.size()>0){
					for(Map<String, Object> tempMap : list) {
					GetJournalListCrntRes1 res  = new GetJournalListCrntRes1();
					  res.setSpfcid(tempMap.get("SPFCID")==null?"":tempMap.get("SPFCID").toString()); 
					  res.setTmasSpfcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString()); 
					  res.setProductId(tempMap.get("PRODUCT_ID")==null?"":tempMap.get("PRODUCT_ID").toString()); 
					  res.setTmasProductName(tempMap.get("TMAS_PRODUCT_NAME")==null?"":tempMap.get("TMAS_PRODUCT_NAME").toString()); 
					  res.setCurrencyName(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString()); 
					  res.setCurrencyId(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString()); 
					  res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString()); 
					  resList.add(res);
					} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
			
		}
		@Override
		public CommonSaveRes callProBookedPremium(CallProBookedPremiumReq bean) {
			CommonSaveRes response = new CommonSaveRes();
				try {
					//prc cal not wrking
					  StoredProcedureQuery integration = null;
					  integration = em.createStoredProcedureQuery("UPR_PIPELINED")
					  .registerStoredProcedureParameter("pvBranch", String.class, ParameterMode.IN)
					  .registerStoredProcedureParameter("PvReport", String.class, ParameterMode.IN)
					  .registerStoredProcedureParameter("Pvtype", String.class, ParameterMode.IN)
					  .registerStoredProcedureParameter("Pvtrimtype", String.class, ParameterMode.IN)
					  .registerStoredProcedureParameter("Pvproduct", String.class, ParameterMode.IN)
					  .registerStoredProcedureParameter("PVERROR", String.class, ParameterMode.OUT)
					  .setParameter("pvBranch", bean.getBranchCode())
					  .setParameter("PvReport", bean.getReportDate())
					  .setParameter("Pvtype", bean.getType())
					  .setParameter("Pvtrimtype", bean.getMovementType())
					  .setParameter("Pvproduct", bean.getProduct());
					  integration.execute();
					  response.setResponse((String) integration.getOutputParameterValue("PVERROR"));
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}


		@Override
		public CommonSaveRes callProBookedUpr(CallProBookedPremiumReq bean) {
			CommonSaveRes response = new CommonSaveRes();
			try {
				//prc cal not wrking
				  StoredProcedureQuery integration = null;
				  integration = em.createStoredProcedureQuery("UPR_PIPELINED")
				  .registerStoredProcedureParameter("pvBranch", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("PvReport", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("Pvtype", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("Pvtrimtype", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("Pvproduct", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("PVERROR", String.class, ParameterMode.OUT)
				  .setParameter("pvBranch", bean.getBranchCode())
				  .setParameter("PvReport", bean.getReportDate())
				  .setParameter("Pvtype", bean.getType())
				  .setParameter("Pvtrimtype", bean.getMovementType())
				  .setParameter("Pvproduct", bean.getProduct());
				  integration.execute();
				  response.setResponse((String) integration.getOutputParameterValue("PVERROR"));
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}


		@Override
		public CommonSaveRes callPipelineMoveJv(CallProBookedPremiumReq bean) {
			CommonSaveRes response = new CommonSaveRes();
			try {
				//prc cal not wrking
				  StoredProcedureQuery integration = null;
				  integration = em.createStoredProcedureQuery("UPR_PIPELINED")
				  .registerStoredProcedureParameter("pvBranch", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("PvReport", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("Pvtype", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("Pvtrimtype", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("Pvproduct", String.class, ParameterMode.IN)
				  .registerStoredProcedureParameter("PVERROR", String.class, ParameterMode.OUT)
				  .setParameter("pvBranch", bean.getBranchCode())
				  .setParameter("PvReport", bean.getReportDate())
				  .setParameter("Pvtype", bean.getType())
				  .setParameter("Pvtrimtype", bean.getMovementType())
				  .setParameter("Pvproduct", bean.getProduct());
				  integration.execute();
				  response.setResponse((String) integration.getOutputParameterValue("PVERROR"));
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}

		@Override
		public CommonSaveRes callProPipelineWritten(CallProBookedPremiumReq bean) {
			CommonSaveRes response = new CommonSaveRes();
			try {
			//prc cal not wrking
			  StoredProcedureQuery integration = null;
			  integration = em.createStoredProcedureQuery("PIPELINED_WRITTEN")
			  .registerStoredProcedureParameter("pvBranch", String.class, ParameterMode.IN)
			  .registerStoredProcedureParameter("PvReport", String.class, ParameterMode.IN)
			  .registerStoredProcedureParameter("Pvtype", String.class, ParameterMode.IN)
			  .registerStoredProcedureParameter("Pvtrimtype", String.class, ParameterMode.IN)
			  .registerStoredProcedureParameter("Pvproduct", String.class, ParameterMode.IN)
			  .registerStoredProcedureParameter("PVERROR", String.class, ParameterMode.OUT)
			  .setParameter("pvBranch", bean.getBranchCode())
			  .setParameter("PvReport", bean.getReportDate())
			  .setParameter("Pvtype", bean.getType())
			  .setParameter("Pvtrimtype", bean.getMovementType())
			  .setParameter("Pvproduct", bean.getProduct());
			  integration.execute();
			  response.setResponse((String) integration.getOutputParameterValue("PVERROR"));
		response.setMessage("Success");
		response.setIsError(false);
		} catch (Exception e) {
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
		}
	return response;
		}

		@Override
		public GetPipelineMovementJvDetailsRes getPipelineMovementJvDetails(GetPipelineMovementJvDetailsReq req) {
			GetPipelineMovementJvDetailsRes response = new GetPipelineMovementJvDetailsRes();
			GetPipelineMovementJvDetailsRes1 bean = new GetPipelineMovementJvDetailsRes1();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
			try {
			String[] args;
			String query="";
			args=new String[8];
			args[0]=req.getMovementId();
			args[1]=req.getBranchCode();
			args[2]=req.getProductId();
			args[3]=req.getSpc();
			args[4]=req.getCurrencyId();
			args[5]=req.getUwYear();
			args[6]=req.getBranchCode();
			args[7]=req.getBranchCode();
			// "MOVEMENT_START_DATE": invalid identifier
			query = "PINELINE_MOVEMENT_JV_CRNT_DETAILS";
			list= queryImpl.selectList(query, args);
			if(list!=null && list.size()>0){
				Map<String,Object> tempMap = list.get(0);
				bean.setAccountDate(tempMap.get("REPORT_DATE")==null?"":tempMap.get("REPORT_DATE").toString());
				bean.setSpcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
				bean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
				bean.setProductName(tempMap.get("TMAS_PRODUCT_NAME")==null?"":tempMap.get("TMAS_PRODUCT_NAME").toString());
				bean.setStartDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
				bean.setEndDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
				bean.setInwrdPiplinUnearnPremiumD(tempMap.get("INWARDPIPEUNEARN_DEBIT")==null?"":tempMap.get("INWARDPIPEUNEARN_DEBIT").toString());
				bean.setInwrdPiplinDefAcqCostBsD(tempMap.get("INWARDPIPEUPRB_S_DEBIT")==null?"":tempMap.get("INWARDPIPEUPRB_S_DEBIT").toString());
				bean.setInwrdPiplinDefAcqCostC(tempMap.get("INWARDPIPEUNEARNACQCOST_CREDIT")==null?"":tempMap.get("INWARDPIPEUNEARNACQCOST_CREDIT").toString());
				bean.setInwrdPiplinUprBsC(tempMap.get("INWARDPIPEUPRB_S_CREDIT")==null?"":tempMap.get("INWARDPIPEUPRB_S_CREDIT").toString());
				bean.setDebitTotal(String.valueOf(Double.parseDouble(bean.getInwrdPiplinDefAcqCostBsD()) + 
						Double.parseDouble(bean.getInwrdPiplinUnearnPremiumD())));
				bean.setCreditTotal(String.valueOf(Double.parseDouble(bean.getInwrdPiplinDefAcqCostC()) + 
						Double.parseDouble(bean.getInwrdPiplinUprBsC())));
				response.setCommonResponse(bean);
			}
			
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}


		@Override
		public GetBookedPremiumDetailsRes getBookedPremiumDetails(GetPipelineMovementJvDetailsReq req) {
			GetBookedPremiumDetailsRes response = new GetBookedPremiumDetailsRes();
			GetBookedPremiumDetailsRes1 bean = new GetBookedPremiumDetailsRes1();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
			String[] args;
			String query="";
			try {
			args=new String[7];
			args[0]=req.getMovementId();
			args[1]=req.getProductId();
			args[2]=req.getSpc();
			args[3]=req.getCurrencyId();
			args[4]=req.getUwYear();
			args[5]=req.getBranchCode();
			args[6]=req.getBranchCode();
			//"MOVEMENT_START_DATE": invalid identifier
			query="BOOKED_PREMIUM_DETAILS";
			list= queryImpl.selectList(query, args);
			if(list!=null && list.size()>0){
				Map<String,Object> tempMap = list.get(0);
				bean.setAccountDate(tempMap.get("REPORT_DATE")==null?"0":tempMap.get("REPORT_DATE").toString());
				bean.setSpcName(tempMap.get("TMAS_SPFC_NAME")==null?"0":tempMap.get("TMAS_SPFC_NAME").toString());
				bean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"0":tempMap.get("CURRENCY_NAME").toString());
				bean.setProductName(tempMap.get("TMAS_PRODUCT_NAME")==null?"":tempMap.get("TMAS_PRODUCT_NAME").toString());
				bean.setStartDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
				bean.setEndDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
				
				bean.setGrsPremiumC(tempMap.get("GWPI_DC")==null?"":tempMap.get("GWPI_DC").toString());
				
				bean.setGrsPiplinPremiumD(tempMap.get("PREMIUM_DC")==null?"0":tempMap.get("PREMIUM_DC").toString());
				
				bean.setGrsAcqCostD(tempMap.get("GROSS_ACQUISTION_COST_DC")==null?"0":tempMap.get("GROSS_ACQUISTION_COST_DC").toString());
				
				bean.setGrsPiplinAcqCostC(tempMap.get("GROSS_PIPELINE_ACQUTION_COST")==null?"0":tempMap.get("GROSS_PIPELINE_ACQUTION_COST").toString());
				
				bean.setPreResRetainD(tempMap.get("PREMIUM_DEPOSIT_RETAINED_DC")==null?"0":tempMap.get("PREMIUM_DEPOSIT_RETAINED_DC").toString());
			
				bean.setPreResReleaseC(tempMap.get("PREMIUM_DEPOSIT_RELEASED_DC")==null?"0":tempMap.get("PREMIUM_DEPOSIT_RELEASED_DC").toString());
				
				bean.setLossResRetainD(tempMap.get("CLAIM_DEPOSIT_RETAINED_DC")==null?"0":tempMap.get("CLAIM_DEPOSIT_RETAINED_DC").toString());
				
				bean.setLossResReleaseC(tempMap.get("CLAIM_DEPOSIT_RELEASED_DC")==null?"0":tempMap.get("CLAIM_DEPOSIT_RELEASED_DC").toString());
				
				bean.setBkdPreInterestC(tempMap.get("INTEREST_ON_DEPOSIT_DC")==null?"0":tempMap.get("INTEREST_ON_DEPOSIT_DC").toString());
				
				bean.setGrsClaimsPaidD(tempMap.get("GROSS_CLAIMS_PAID_DC")==null?"0":tempMap.get("GROSS_CLAIMS_PAID_DC").toString());
				
				
				bean.setPiplinNetAcC(tempMap.get("PIPELINED_NET_AC")==null?"0":tempMap.get("PIPELINED_NET_AC").toString());
				
				bean.setCreditTotal(String.valueOf(Double.parseDouble(bean.getGrsPremiumC()) + Double.parseDouble(bean.getGrsPiplinAcqCostC()) +
						Double.parseDouble(bean.getPreResReleaseC()) + Double.parseDouble(bean.getLossResReleaseC()) + 
						Double.parseDouble(bean.getBkdPreInterestC())+ Double.parseDouble(bean.getPiplinNetAcC())));
				
				Double businessPratInward =  (Double.parseDouble(bean.getGrsPremiumC()) + Double.parseDouble(bean.getGrsPiplinAcqCostC()) +
												Double.parseDouble(bean.getPreResReleaseC()) + Double.parseDouble(bean.getLossResReleaseC()) + 
												Double.parseDouble(bean.getBkdPreInterestC())+ Double.parseDouble(bean.getPiplinNetAcC()))
												- 
											(Double.parseDouble(bean.getGrsPiplinPremiumD()) + Double.parseDouble(bean.getGrsAcqCostD()) +
													Double.parseDouble(bean.getPreResRetainD()) + Double.parseDouble(bean.getLossResRetainD()) + 
													Double.parseDouble(bean.getGrsClaimsPaidD()));
				
				bean.setBusiPartInwrdNetAcD( businessPratInward.toString());
				
				bean.setDebitTotal(String.valueOf(Double.parseDouble(bean.getGrsPiplinPremiumD()) + Double.parseDouble(bean.getGrsAcqCostD()) +
						Double.parseDouble(bean.getPreResRetainD()) + Double.parseDouble(bean.getLossResRetainD()) + 
						Double.parseDouble(bean.getGrsClaimsPaidD()) + Double.parseDouble(bean.getBusiPartInwrdNetAcD())));
				response.setCommonResponse(bean);
			}
				
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}


		@Override
		public GetBookedUprDetailsRes getBookedUprDetails(GetPipelineMovementJvDetailsReq req) {
			GetBookedUprDetailsRes response = new GetBookedUprDetailsRes();
			GetBookedUprDetailsRes1 bean = new GetBookedUprDetailsRes1();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
			String[] args;
			String query="";
			try {
			args=new String[8];
			args[0]=req.getMovementId();
			args[1]=req.getBranchCode();
			args[2]=req.getProductId();
			args[3]=req.getSpc();
			args[4]=req.getCurrencyId();
			args[5]=req.getUwYear();
			args[6]=req.getBranchCode();
			args[7]=req.getBranchCode();
			query="BOOKED_UPR_DETAILS";
			list= queryImpl.selectList(query, args);
			if(list!=null && list.size()>0){
				Map<String,Object> tempMap = list.get(0);
				bean.setAccountDate(tempMap.get("REPORT_DATE")==null?"":tempMap.get("REPORT_DATE").toString());
				bean.setSpcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
				bean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
				bean.setProductName(tempMap.get("TMAS_PRODUCT_NAME")==null?"":tempMap.get("TMAS_PRODUCT_NAME").toString());
				bean.setStartDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
				bean.setEndDate(tempMap.get("EXPIRY_DATE")==null?"":tempMap.get("EXPIRY_DATE").toString());
				bean.setBkdUprD(tempMap.get("BOOKED_UPR_DEBIT")==null?"":tempMap.get("BOOKED_UPR_DEBIT").toString());
				bean.setInwrdBkdDefAcqCostBsD(tempMap.get("INWARD_BOOK_DACQ_BS_DEBIT")==null?"":tempMap.get("INWARD_BOOK_DACQ_BS_DEBIT").toString());
				bean.setPiplinAcqCostD(tempMap.get("PIPELINED_ACQ_COST")==null?"":tempMap.get("PIPELINED_ACQ_COST").toString());
				bean.setInwrdPiplinUprBSD(tempMap.get("INWARD_PIPELINE_UPR_BS_DEBIT")==null?"":tempMap.get("INWARD_PIPELINE_UPR_BS_DEBIT").toString());
				bean.setBkdDefAcqCostC(tempMap.get("BOOK_DEFFE_ACQUI_COST_CREDIT")==null?"":tempMap.get("BOOK_DEFFE_ACQUI_COST_CREDIT").toString());
				bean.setInwrdBkdUprBsC(tempMap.get("INWARD_BOOKED_UPR_BS_CREDIT")==null?"":tempMap.get("INWARD_BOOKED_UPR_BS_CREDIT").toString());			
				bean.setPiplinUprC(tempMap.get("PIPELINED_UPR_CREDIT")==null?"":tempMap.get("PIPELINED_UPR_CREDIT").toString());			
				bean.setInwrdPiplinDefAcqCostBsC(tempMap.get("INWARD_PIPE_DACQ_COST_CREDIT")==null?"":tempMap.get("INWARD_PIPE_DACQ_COST_CREDIT").toString());
				
				bean.setDebitTotal(String.valueOf(Double.parseDouble(bean.getBkdUprD()) + Double.parseDouble(bean.getInwrdBkdDefAcqCostBsD()) + 
						Double.parseDouble(bean.getPiplinAcqCostD()) + Double.parseDouble(bean.getInwrdPiplinUprBSD())));
				
				bean.setCreditTotal(String.valueOf(Double.parseDouble(bean.getBkdDefAcqCostC()) + Double.parseDouble(bean.getInwrdBkdUprBsC()) +
						Double.parseDouble(bean.getPiplinUprC()) + Double.parseDouble(bean.getInwrdPiplinDefAcqCostBsC())));
				response.setCommonResponse(bean);
			}
				
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}

		@Override
		public GetPipelinedWrtnDetailsRes getPipelinedWrtnDetails(GetPipelineMovementJvDetailsReq req) {
			GetPipelinedWrtnDetailsRes response = new GetPipelinedWrtnDetailsRes();
			GetPipelinedWrtnDetailsRes1 bean = new GetPipelinedWrtnDetailsRes1();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
			String[] args;
			String query="";
			try {
			args=new String[7];
			args[0]=req.getMovementId();
			args[1]=req.getBranchCode();
			args[2]=req.getBranchCode();
			args[3]=req.getProductId();
			args[4]=req.getCurrencyId();
			args[5]=req.getUwYear();
			args[6]=req.getSpc();		
			query="PIPELINE_WRITTEN_DETAILS";
			list= queryImpl.selectList(query, args);
			if(list!=null && list.size()>0){
				Map<String,Object> tempMap = list.get(0);
				bean.setAccountDate(tempMap.get("REPORT_DATE")==null?"":tempMap.get("REPORT_DATE").toString());
				bean.setSpcName(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
				bean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
				bean.setProductName(tempMap.get("TMAS_PRODUCT_NAME")==null?"":tempMap.get("TMAS_PRODUCT_NAME").toString());
				bean.setPiplinPremiumC(tempMap.get("PIPELINED_GWPI_DC")==null?"":tempMap.get("PIPELINED_GWPI_DC").toString());
				bean.setPiplinAcqCostD(tempMap.get("PIPELINED_ACQUISTION_DC")==null?"":tempMap.get("PIPELINED_ACQUISTION_DC").toString());
				bean.setPiplinNetAcD(tempMap.get("PIPELINED_NETPREMIUM")==null?"":tempMap.get("PIPELINED_NETPREMIUM").toString());
				bean.setDebitTotal(String.valueOf(Double.parseDouble(bean.getPiplinAcqCostD()) + Double.parseDouble(bean.getPiplinNetAcD())));
				bean.setCreditTotal(String.valueOf(Double.parseDouble(bean.getPiplinPremiumC())));
				response.setCommonResponse(bean);	
			}
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}

		@Override
		public GetViewAllRes getViewAll(GetViewAllReq bean) {
			GetViewAllRes response = new GetViewAllRes();
			String query="";
			List<GetViewAllRes1> finalList=new ArrayList<GetViewAllRes1>();	
			try{
				String args[]=null;
				if("viewJurnalAll".equalsIgnoreCase(bean.getDisplay())){
					args=new String[4];
					args[0]=bean.getAccper();
					args[1]=bean.getAccountDate();
					args[2]=bean.getBranchCode();
					args[3]=bean.getMovId();
					query="report.select.viewJurnal1";
					
				}else{
					args=new String[5];
					args[0]=bean.getAccper();
					args[1]=bean.getAccountDate();
					args[2]=bean.getBranchCode();
					args[3]=bean.getSNo();
					args[4]=bean.getMovId();
					query= "report.select.viewJurnal";
				}
				List<Map<String,Object>>list = queryImpl.selectList(query, args);
				for(int i=0 ; i<list.size() ; i++) {
					Map<String,Object> tempMap = list.get(i);
					double gpOC,gpDC,gAOC,gADC,pdRelOC,pdRelDC,pdRetOC,pdRetDC,cdRetOC,cdRetDC,cdRelOC,cdRelDC,intOC,intDC,gcpOC,gcpDC,blctlOC=0,blctlDC=0,totalCROC=0,totalCRDC=0,totalDROC=0,totalDRDC=0;
					GetViewAllRes1 tempBean = new GetViewAllRes1();
					tempBean.setAccountPeriod(bean.getAccountPeriod());
					tempBean.setAccountDate(bean.getAccountDate());
					tempBean.setSNo(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					tempBean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					tempBean.setSpc(tempMap.get("SPC_NAME")==null?"":tempMap.get("SPC_NAME").toString());
					tempBean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());

					tempBean.setGrossPremiumOC(fm.formatter(tempMap.get("GROSS_PREMIUM_OC")==null?"":tempMap.get("GROSS_PREMIUM_OC").toString())); //Gross Premium oc
					tempBean.setGrossPremiumDC(fm.formatter(tempMap.get("GROSS_PREMIUM_DC")==null?"":tempMap.get("GROSS_PREMIUM_DC").toString())); //Gross Premium dc

					tempBean.setGrossAcqCostOC(fm.formatter(tempMap.get("GROSS_ACQUISTION_COST_OC")==null?"":tempMap.get("GROSS_ACQUISTION_COST_OC").toString())); //
					tempBean.setGrossAcqCostDC(fm.formatter(tempMap.get("GROSS_ACQUISTION_COST_DC")==null?"":tempMap.get("GROSS_ACQUISTION_COST_DC").toString())); //

					tempBean.setPremiumDepositRetainedDC(fm.formatter(tempMap.get("PREMIUM_DEPOSIT_RETAINED_DC")==null?"":tempMap.get("PREMIUM_DEPOSIT_RETAINED_DC").toString())); //Premium Deposit Retained dc
					tempBean.setPremiumDepositRetainedOC(fm.formatter(tempMap.get("PREMIUM_DEPOSIT_RETAINED_OC")==null?"":tempMap.get("PREMIUM_DEPOSIT_RETAINED_OC").toString())); //Premium Deposit Retained oc

					tempBean.setPremiumDepositReleasedOC(fm.formatter(tempMap.get("PREMIUM_DEPOSIT_RELEASED_OC")==null?"":tempMap.get("PREMIUM_DEPOSIT_RELEASED_OC").toString())); //Premium Deposit Released oc
					tempBean.setPremiumDepositReleasedDC(fm.formatter(tempMap.get("PREMIUM_DEPOSIT_RELEASED_DC")==null?"":tempMap.get("PREMIUM_DEPOSIT_RELEASED_DC").toString())); //Premium Deposit Released dc

					tempBean.setClaimDepositRetainedOC(fm.formatter(tempMap.get("CLAIM_DEPOSIT_RETAINED_OC")==null?"":tempMap.get("CLAIM_DEPOSIT_RETAINED_OC").toString()));  //Claim Deposit Retained oc
					tempBean.setClaimDepositRetainedDC(fm.formatter(tempMap.get("CLAIM_DEPOSIT_RETAINED_DC")==null?"":tempMap.get("CLAIM_DEPOSIT_RETAINED_DC").toString())); //Claim Deposit Retained dc

					tempBean.setClaimDepositReleasedOC(fm.formatter(tempMap.get("CLAIM_DEPOSIT_RELEASED_OC")==null?"":tempMap.get("CLAIM_DEPOSIT_RELEASED_OC").toString()));  //Claim Deposit Released oc
					tempBean.setClaimDepositReleasedDC(fm.formatter(tempMap.get("CLAIM_DEPOSIT_RELEASED_DC")==null?"":tempMap.get("CLAIM_DEPOSIT_RELEASED_DC").toString())); //Claim Deposit Released dc

					tempBean.setInterestOnDepositOC(fm.formatter(tempMap.get("INTEREST_ON_DEPOSIT_OC")==null?"":tempMap.get("INTEREST_ON_DEPOSIT_OC").toString())); //Interest On Deposit oc
					tempBean.setInterestOnDepositDC(fm.formatter(tempMap.get("INTEREST_ON_DEPOSIT_DC")==null?"":tempMap.get("INTEREST_ON_DEPOSIT_DC").toString())); //Interest On Deposit dc

					tempBean.setGrossClaimPaidOC(fm.formatter(tempMap.get("GROSS_CLAIMS_PAID_OC")==null?"":tempMap.get("GROSS_CLAIMS_PAID_OC").toString())); //Gross Claims Paid oc
					tempBean.setGrossClaimPaidDC(fm.formatter(tempMap.get("GROSS_CLAIMS_PAID_DC")==null?"":tempMap.get("GROSS_CLAIMS_PAID_DC").toString())); //Gross Claims Paid dc

					tempBean.setGpOC(tempBean.getGrossPremiumOC().replaceAll(",", ""));
					tempBean.setGAOC(tempBean.getGrossAcqCostOC().replaceAll(",", ""));
					tempBean.setPdRetOC(tempBean.getPremiumDepositRetainedOC().replaceAll(",", ""));
					tempBean.setPdRelOC(tempBean.getPremiumDepositReleasedOC().replaceAll(",", ""));
					tempBean.setCdRelOC(tempBean.getClaimDepositReleasedOC().replaceAll(",", ""));
					tempBean.setCdRetOC(tempBean.getClaimDepositRetainedOC().replaceAll(",", ""));
					tempBean.setIntOC(tempBean.getInterestOnDepositOC().replaceAll(",", ""));
					tempBean.setGcpOC(tempBean.getGrossClaimPaidOC().replaceAll(",", ""));

					gpOC=Double.valueOf(tempBean.getGrossPremiumOC().replaceAll(",", ""));
					gpDC=Double.valueOf(tempBean.getGrossPremiumDC().replaceAll(",", ""));

					gAOC=Double.valueOf(tempBean.getGrossAcqCostOC().replaceAll(",", ""));
					gADC=Double.valueOf(tempBean.getGrossAcqCostDC().replaceAll(",", ""));

					pdRetOC=Double.valueOf(tempBean.getPremiumDepositRetainedOC().replaceAll(",", ""));
					pdRetDC=Double.valueOf(tempBean.getPremiumDepositRetainedDC().replaceAll(",", ""));

					pdRelOC=Double.valueOf(tempBean.getPremiumDepositReleasedOC().replaceAll(",", ""));
					pdRelDC=Double.valueOf(tempBean.getPremiumDepositReleasedDC().replaceAll(",", ""));

					cdRelOC=Double.valueOf(tempBean.getClaimDepositReleasedOC().replaceAll(",", ""));
					cdRelDC=Double.valueOf(tempBean.getClaimDepositReleasedDC().replaceAll(",", ""));

					cdRetOC=Double.valueOf(tempBean.getClaimDepositRetainedOC().replaceAll(",", ""));
					cdRetDC=Double.valueOf(tempBean.getClaimDepositRetainedDC().replaceAll(",", ""));

					intOC=Double.valueOf(tempBean.getInterestOnDepositOC().replaceAll(",", ""));
					intDC=Double.valueOf(tempBean.getInterestOnDepositDC().replaceAll(",", ""));

					gcpOC=Double.valueOf(tempBean.getGrossClaimPaidOC().replaceAll(",", ""));
					gcpDC=Double.valueOf(tempBean.getGrossClaimPaidDC().replaceAll(",", ""));

					//Credit Total
					if(gpOC>=0){
						totalCROC=totalCROC+gpOC;
						totalCRDC=totalCRDC+gpDC;
					}else{
						totalDROC=totalDROC+Math.abs(gpOC);
						totalDRDC=totalDRDC+Math.abs(gpDC);
					}
					if(pdRelOC>=0){
						totalCROC=totalCROC+pdRelOC;
						totalCRDC=totalCRDC+pdRelDC;
					}else{
						totalDROC=totalDROC+Math.abs(pdRelOC);
						totalDRDC=totalDRDC+Math.abs(pdRelDC);
					}
					if(cdRelOC>=0){
						totalCROC=totalCROC+cdRelOC;
						totalCRDC=totalCRDC+cdRelDC;
					}else{
						totalDROC=totalDROC+Math.abs(cdRelOC);
						totalDRDC=totalDRDC+Math.abs(cdRelDC);
					}
					if(intOC>=0){
						totalCROC=totalCROC+intOC;
						totalCRDC=totalCRDC+intDC;
					}else{
						totalDROC=totalDROC+Math.abs(intOC);
						totalDRDC=totalDRDC+Math.abs(intDC);
					}
					if(gcpOC>=0){
						totalCROC=totalCROC+gcpOC;
						totalCRDC=totalCRDC+gcpDC;
					}else{
						totalDROC=totalDROC+Math.abs(gcpOC);
						totalDRDC=totalDRDC+Math.abs(gcpDC);
					}

					//Debit Total

					if(gAOC>=0){
						totalDROC=totalDROC+gAOC;
						totalDRDC=totalDRDC+gADC;
					}else{
						totalCROC=totalCROC+Math.abs(gAOC);
						totalCRDC=totalCRDC+Math.abs(gADC);
					}
					if(pdRetOC>=0){
						totalDROC=totalDROC+pdRetOC;
						totalDRDC=totalDRDC+pdRetDC;
					}else{
						totalCROC=totalCROC+Math.abs(pdRetOC);
						totalCRDC=totalCRDC+Math.abs(pdRetDC);
					}
					if(cdRetOC>=0){
						totalDROC=totalDROC+cdRetOC;
						totalDRDC=totalDRDC+cdRetDC;
					}else{
						totalCROC=totalCROC+Math.abs(cdRetOC);
						totalCRDC=totalCRDC+Math.abs(cdRetDC);
					}
					blctlOC=totalCROC-totalDROC;
					blctlDC=totalCRDC-totalDRDC;
					if(blctlOC>0){
						totalDROC=totalDROC+blctlOC;
						totalDRDC=totalDRDC+blctlDC;
					}else{
						totalCROC=totalCROC+Math.abs(blctlOC);
						totalCRDC=totalCRDC+Math.abs(blctlDC);	
					}
					tempBean.setBrokerLedCtlOC(fm.formatter(String.valueOf(blctlOC)));
					tempBean.setBrokerLedCtlDC(fm.formatter(String.valueOf(blctlDC)));
					tempBean.setBlOC(String.valueOf(blctlOC));
					tempBean.setTotalCROC(fm.formatter(String.valueOf(totalCROC)));
					tempBean.setTotalDROC(fm.formatter(String.valueOf(totalDROC)));
					tempBean.setTotalCRDC(fm.formatter(String.valueOf(totalCRDC)));
					tempBean.setTotalDRDC(fm.formatter(String.valueOf(totalDRDC)));

					finalList.add(tempBean);
				}
				response.setCommonResponse(finalList);	
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}


		@Override
		public GetClaimMoveMentPageRes getClaimMoveMentPage(String branchCode, String movId) {
			GetClaimMoveMentPageRes response = new GetClaimMoveMentPageRes();
			GetClaimMoveMentPageRes1 bean = new GetClaimMoveMentPageRes1();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
			String[] args;
			String query="";
			try {
			args=new String[4];
			args[0]=movId;
			args[1]=branchCode;
			args[2]=movId;
			args[3]=branchCode;
			query="report.select.clMoveMentDtls";
			list= queryImpl.selectList(query, args);
			if(list!=null && list.size()>0){
				Map<String,Object> tempMap = list.get(0);
				bean.setUwYear(tempMap.get("UWYEAR")==null?"":tempMap.get("UWYEAR").toString());
				bean.setSpcName(tempMap.get("SPC_NAME")==null?"":tempMap.get("SPC_NAME").toString());
				bean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
				bean.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
				bean.setProductName(tempMap.get("PRODUCT")==null?"":tempMap.get("PRODUCT").toString());
				bean.setTranNo(tempMap.get("TRAN_NO")==null?"":tempMap.get("TRAN_NO").toString());
				bean.setType(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString());
				bean.setOsLossupdateOC(tempMap.get("OUTSTANDING_AMOUNT_OC")==null?"":tempMap.get("OUTSTANDING_AMOUNT_OC").toString());
				bean.setOsLossupdateDC(tempMap.get("OUTSTANDING_AMOUNT_DC")==null?"":tempMap.get("OUTSTANDING_AMOUNT_DC").toString());
				response.setCommonResponse(bean);	
			}
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}

		@Override
		public GetJournelPageRes getJournelPage(String branchCode, String movId) {
			GetJournelPageRes response = new GetJournelPageRes();
			GetJournelPageRes1 bean = new GetJournelPageRes1();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
			String[] args;
			String query="";
			try {
			args=new String[6];
			args[0]=branchCode;
			args[1]=branchCode;
			args[2]=movId;
			args[3]=branchCode;
			args[4]=branchCode;
			args[5]=branchCode;
			query="report.select.journel";
			list= queryImpl.selectList(query, args);
			if(list!=null && list.size()>0){
				Map<String,Object> tempMap = list.get(0);
				bean.setSNo(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
				bean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				bean.setSpcName(tempMap.get("SPC_NAME")==null?"":tempMap.get("SPC_NAME").toString());
				bean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
				bean.setSumofPrm(fm.formatter(tempMap.get("LOSS_ESTIMATE_REVISED_OC")==null?"0":tempMap.get("LOSS_ESTIMATE_REVISED_OC").toString()));
				bean.setSumofPrmDC(fm.formatter(tempMap.get("LOSS_ESTIMATE_REVISED_DC")==null?"0":tempMap.get("LOSS_ESTIMATE_REVISED_DC").toString()));
				response.setCommonResponse(bean);	
			}
			response.setMessage("Success");
			response.setIsError(false);
			} catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
			}
		return response;
		}

		@Override
		public CommonSaveRes insertCLMovement(InsertCLMovementReq bean) {
			CommonSaveRes response = new CommonSaveRes();
			try{
				String query= "report.sp.clMovementRep";
				String[] args=new String[] {bean.getEndDate(),bean.getBranchCode(),bean.getMovementType()};
				int result=queryImpl.updateQuery(query, args); 
				response.setResponse("");	
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}


		@Override
		public CommonSaveRes insertMovement(InsertMovementReq bean) {
			CommonSaveRes response = new CommonSaveRes();
			try{
				String query="journelreport.sp.clMovementRep";
				String[] args=new String[] {bean.getAccper(),bean.getAccountDate(),bean.getBranchCode(),bean.getMovementType()};
				int result=queryImpl.updateQuery(query, args); 
				response.setResponse("");	
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}	
		public int getCountMovementRecord(String branchCode, String accQtr,	String accYear) {
			int count=0;
			String val = "";
			try{
				String query="report.get.movementCnt";
				String[] args=new String[] {branchCode,accQtr,accYear};
			
				List<Map<String, Object>> list  = queryImpl.selectList(query,args);
				if (!CollectionUtils.isEmpty(list)) {
					 val = list.get(0).get("COUNT") == null ? ""
							: list.get(0).get("COUNT").toString();
				}
				count = Integer.valueOf(val);
			}catch(Exception e){
				logger.debug("Exception @ {" + e + "}");
				e.printStackTrace();
			}
			return count;
		}

		@Override
		public GetViewJurnelAllRes getViewJurnelAll(GetViewJurnelAllReq bean) {
			GetViewJurnelAllRes response = new GetViewJurnelAllRes();
			String query="";
			List<GetViewJurnelAllRes1> finalList=new ArrayList<GetViewJurnelAllRes1>();	
			try{
				String args[]=null;
				if("viewJurnalAll".equalsIgnoreCase(bean.getDisplay())){
					args=new String[6];
					args[0]=bean.getBranchCode();
					args[1]=bean.getBranchCode();
					args[2]=bean.getMovId();
					args[3]=bean.getBranchCode();
					args[4]=bean.getBranchCode();
					args[5]=bean.getBranchCode();
					query="report.select.viewJurnalall1";
					
				}else{
					args=new String[7];
					args[0]=bean.getBranchCode();
					args[1]=bean.getBranchCode();
					args[2]=bean.getMovId();
					args[3]=bean.getBranchCode();
					args[4]=bean.getBranchCode();
					args[5]=bean.getBranchCode();
					args[6]=bean.getSNo();
					query="report.select.viewJurnalall";
					
				}

				List<Map<String,Object>>list = queryImpl.selectList(query,args);
				//if(list!=null && list.size()>0){
				for(int i=0 ; i<list.size() ; i++) {
					Map<String,Object> tempMap = list.get(i);
					double gpOC,gpDC,gAOC,gADC,pdRelOC,pdRelDC,pdRetOC,pdRetDC,cdRetOC,cdRetDC,cdRelOC,cdRelDC,blctlOC=0,blctlDC=0,totalCROC=0,totalCRDC=0,totalDROC=0,totalDRDC=0;
					GetViewJurnelAllRes1 tempBean = new GetViewJurnelAllRes1();
					tempBean.setAccountPeriod(bean.getAccountPeriod());
					tempBean.setAccountDate(bean.getAccountDate());
					tempBean.setSNo(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					tempBean.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
					tempBean.setSpc(tempMap.get("SPC_NAME")==null?"":tempMap.get("SPC_NAME").toString());
					tempBean.setCurrencyname(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());

					tempBean.setOsLossMovement(fm.formatter(tempMap.get("OS_LOSS_MOVEMENT_PL")==null?"":tempMap.get("OS_LOSS_MOVEMENT_PL").toString())); 
					tempBean.setOsLossMovementUSD(fm.formatter(tempMap.get("OS_LOSS_MOVEMENT_PL_USD")==null?"":tempMap.get("OS_LOSS_MOVEMENT_PL_USD").toString())); 

					tempBean.setBsMovement(fm.formatter(tempMap.get("BS_MOVEMENT")==null?"":tempMap.get("BS_MOVEMENT").toString())); 
					tempBean.setBsMovementUSD(fm.formatter(tempMap.get("BS_MOVEMENT_USD")==null?"":tempMap.get("BS_MOVEMENT_USD").toString())); 

					tempBean.setJournelClaimPaid(fm.formatter(tempMap.get("CLAIMS_PAID")==null?"":tempMap.get("CLAIMS_PAID").toString())); 
					tempBean.setJournelClaimPaidUSD(fm.formatter(tempMap.get("CLAIMS_PAID_USD")==null?"":tempMap.get("CLAIMS_PAID_USD").toString()));

					tempBean.setDebtorsCreditors(fm.formatter(tempMap.get("DEBTORS_CREDIT_CONTROL_AC")==null?"":tempMap.get("DEBTORS_CREDIT_CONTROL_AC").toString()));
					tempBean.setDebtorsCreditorsUSD(fm.formatter(tempMap.get("DEBTORS_CREDIT_CONTROL_AC_USD")==null?"":tempMap.get("DEBTORS_CREDIT_CONTROL_AC_USD").toString()));

					tempBean.setOsLossMovementBs(fm.formatter(tempMap.get("OS_LOSS_MOVEMENT_BS")==null?"":tempMap.get("OS_LOSS_MOVEMENT_BS").toString()));
					tempBean.setOsLossMovementBsUSD(fm.formatter(tempMap.get("OS_LOSS_MOVEMENT_BS_USD")==null?"":tempMap.get("OS_LOSS_MOVEMENT_BS_USD").toString()));

					tempBean.setOsLossPl(fm.formatter(tempMap.get("OS_LOSS_PL_CR")==null?"":tempMap.get("OS_LOSS_PL_CR").toString()));
					tempBean.setOsLossPlUSD(fm.formatter(tempMap.get("OS_LOSS_PL_CR_USD")==null?"":tempMap.get("OS_LOSS_PL_CR_USD").toString()));

					tempBean.setOsLM(tempBean.getOsLossMovement().replaceAll(",", ""));
					tempBean.setBsM(tempBean.getBsMovement().replaceAll(",", ""));
					tempBean.setJCP(tempBean.getJournelClaimPaid().replaceAll(",", ""));
					tempBean.setDC(tempBean.getDebtorsCreditors().replaceAll(",", ""));
					tempBean.setOsLMB(tempBean.getOsLossMovementBs().replaceAll(",", ""));
					tempBean.setOsLP(tempBean.getOsLossPl().replaceAll(",", ""));


					gpOC=Double.valueOf(tempBean.getOsLossMovement().replaceAll(",", ""));
					gpDC=Double.valueOf(tempBean.getOsLossMovementUSD().replaceAll(",", ""));

					gAOC=Double.valueOf(tempBean.getBsMovement().replaceAll(",", ""));
					gADC=Double.valueOf(tempBean.getBsMovementUSD().replaceAll(",", ""));

					pdRetOC=Double.valueOf(tempBean.getJournelClaimPaid().replaceAll(",", ""));
					pdRetDC=Double.valueOf(tempBean.getJournelClaimPaidUSD().replaceAll(",", ""));

					pdRelOC=Double.valueOf(tempBean.getDebtorsCreditors().replaceAll(",", ""));
					pdRelDC=Double.valueOf(tempBean.getDebtorsCreditorsUSD().replaceAll(",", ""));

					cdRelOC=Double.valueOf(tempBean.getOsLossMovementBs().replaceAll(",", ""));
					cdRelDC=Double.valueOf(tempBean.getOsLossMovementBsUSD().replaceAll(",", ""));

					cdRetOC=Double.valueOf(tempBean.getOsLossPl().replaceAll(",", ""));
					cdRetDC=Double.valueOf(tempBean.getOsLossPlUSD().replaceAll(",", ""));



					//Credit Total
					if(gpOC>=0){
						totalCROC=totalCROC+gpOC;
						totalCRDC=totalCRDC+gpDC;
					}else{
						totalDROC=totalDROC+Math.abs(gpOC);
						totalDRDC=totalDRDC+Math.abs(gpDC);
					}
					if(pdRelOC>=0){
						totalCROC=totalCROC+pdRelOC;
						totalCRDC=totalCRDC+pdRelDC;
					}else{
						totalDROC=totalDROC+Math.abs(pdRelOC);
						totalDRDC=totalDRDC+Math.abs(pdRelDC);
					}
					if(cdRelOC>=0){
						totalCROC=totalCROC+cdRelOC;
						totalCRDC=totalCRDC+cdRelDC;
					}else{
						totalDROC=totalDROC+Math.abs(cdRelOC);
						totalDRDC=totalDRDC+Math.abs(cdRelDC);
					}


					//Debit Total

					if(gAOC>=0){
						totalDROC=totalDROC+gAOC;
						totalDRDC=totalDRDC+gADC;
					}else{
						totalCROC=totalCROC+Math.abs(gAOC);
						totalCRDC=totalCRDC+Math.abs(gADC);
					}
					if(pdRetOC>=0){
						totalDROC=totalDROC+pdRetOC;
						totalDRDC=totalDRDC+pdRetDC;
					}else{
						totalCROC=totalCROC+Math.abs(pdRetOC);
						totalCRDC=totalCRDC+Math.abs(pdRetDC);
					}
					if(cdRetOC>=0){
						totalDROC=totalDROC+cdRetOC;
						totalDRDC=totalDRDC+cdRetDC;
					}else{
						totalCROC=totalCROC+Math.abs(cdRetOC);
						totalCRDC=totalCRDC+Math.abs(cdRetDC);
					}
					blctlOC=totalCROC-totalDROC;
					blctlDC=totalCRDC-totalDRDC;
					if(blctlOC>0){
						totalDROC=totalDROC+blctlOC;
						totalDRDC=totalDRDC+blctlDC;
					}else{
						totalCROC=totalCROC+Math.abs(blctlOC);
						totalCRDC=totalCRDC+Math.abs(blctlDC);	
					}
					tempBean.setBrokerLedCtlOC(fm.formatter(String.valueOf(blctlOC)));
					tempBean.setBrokerLedCtlDC(fm.formatter(String.valueOf(blctlDC)));

					tempBean.setTotalCROC(fm.formatter(String.valueOf(totalCROC)));
					tempBean.setTotalDROC(fm.formatter(String.valueOf(totalDROC)));
					tempBean.setTotalCRDC(fm.formatter(String.valueOf(totalCRDC)));
					tempBean.setTotalDRDC(fm.formatter(String.valueOf(totalDRDC)));

				
					finalList.add(tempBean);
				}
				response.setCommonResponse(finalList);	
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}


		@Override
		public CommonSaveRes journelInsertMovement(InsertMovementReq bean) {
			CommonSaveRes response = new CommonSaveRes();
			try{
				bean.setMovementType(StringUtils.isBlank(bean.getMovementType())?"I":bean.getMovementType());
				String query="journelreport.sp.clMovementRep";
				String[] args=new String[] {bean.getAccper(),bean.getAccountDate(),bean.getBranchCode(),bean.getMovementType()};
				int result=queryImpl.updateQuery(query, args); 
				response.setResponse("");	
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}


		@Override
		public GetJournelMoveMentInitRes getJournelMoveMentInit(String branchCode) {
			GetJournelMoveMentInitRes response = new GetJournelMoveMentInitRes();
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			GetJournelMoveMentInitRes1 bean = new GetJournelMoveMentInitRes1();
			try{
				String[] args=new String[3];
				String query="";
				args[0]=branchCode;
				args[1]=branchCode;
				args[2]=branchCode;
				query="journelreport.select.moveMentInit";
				list = queryImpl.selectList(query,args);
				if(list!=null && list.size()>0){
					Map<String,Object> tempMap = list.get(0);
					bean.setSNo(tempMap.get("MOVMENT_TRANID")==null?"":tempMap.get("MOVMENT_TRANID").toString());
					bean.setAccountPeriod(tempMap.get("DETAIL_NAME")==null?"":tempMap.get("DETAIL_NAME").toString());
					bean.setAccper(tempMap.get("ACCOUNT_PERIOD_QTR")==null?"":tempMap.get("ACCOUNT_PERIOD_QTR").toString());
					bean.setAccountDate(tempMap.get("ACCOUNT_PERIOD_DATE")==null?"":tempMap.get("ACCOUNT_PERIOD_DATE").toString());
					bean.setMovementType(tempMap.get("REPORT_TYPE")==null?"Interim":tempMap.get("REPORT_TYPE").toString().equalsIgnoreCase("F")?"Final":"Intreim");
					response.setCommonResponse(bean);
				}
				response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}

		@Override
		public FinanceStagingListRes financeStagingList(FinanceStagingListReq req) {
			FinanceStagingListRes response = new FinanceStagingListRes();
			List<FinanceStagingListRes1> resList = new ArrayList<FinanceStagingListRes1>();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			try { 
				List<TtrnFinanceStaging> list = finRepo.findByStartDateAndEndDateAndBranchCodeAndPost
						(sdf.parse(req.getStartDate()),sdf.parse(req.getEndDate()),new BigDecimal(req.getBranchCode()),req.getPost());
						
						if(list.size()>0) {
							for(TtrnFinanceStaging data : list) {
								FinanceStagingListRes1 res = new FinanceStagingListRes1();
								res.setBaseCurrency(data.getBaseCurrency()==null?"":data.getBaseCurrency().toString());										
								res.setBranchCode(data.getBranchCode()==null?"":data.getBranchCode().toString());
								res.setCreditoc(data.getCreditoc()==null?"":data.getCreditoc().toString());
								res.setCurrency(data.getCurrency()==null?"":data.getCurrency().toString());
								res.setDC(data.getDC()==null?"":data.getDC().toString());
								res.setDebitoc(data.getDebitoc()==null?"":data.getDebitoc().toString());
								res.setExchangeRate(data.getExchangeRate()==null?"":data.getExchangeRate().toString());
								res.setInstrumentDate(data.getInstrumentDate()==null?"":data.getInstrumentDate().toString());
								res.setInstrumentNo(data.getInstrumentNo()==null?"":data.getInstrumentNo().toString());
								res.setJvDate(data.getJvDate()==null?"":data.getJvDate().toString());
								res.setLedger(data.getLedger()==null?"":data.getLedger().toString());
								res.setLogicDate(data.getLogicDate()==null?"":data.getLogicDate().toString());
								res.setNarration(data.getNarration()==null?"":data.getNarration().toString());
								res.setPostingCredit(data.getPostingCredit()==null?"":data.getPostingCredit().toString());
								res.setPostingDebit(data.getPostingDebit()==null?"":data.getPostingDebit().toString());
								res.setReference(data.getReference()==null?"":data.getReference().toString());
								res.setTransferType(data.getTransferType()==null?"":data.getTransferType().toString());
								res.setVoucherSubtype(data.getVoucherSubtype()==null?"":data.getVoucherSubtype().toString());
								res.setVouchertype(data.getVoucherType()==null?"":data.getVoucherType().toString());
								resList.add(res);							
							} }
							response.setCommonResponse(resList);
							response.setMessage("Success");
				response.setIsError(false);
				} catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				}
			return response;
		}
}
