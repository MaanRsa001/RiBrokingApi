package com.maan.insurance.service.impl.Dropdown;


import java.io.File;
import java.io.FileOutputStream;
import java.math.BigDecimal;
import java.sql.Connection;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.maan.insurance.controller.Dropdown.updateSubEditModeReq;
import com.maan.insurance.model.entity.PersonalInfo;
import com.maan.insurance.model.entity.PositionMaster;
import com.maan.insurance.model.entity.StatusMaster;
import com.maan.insurance.model.entity.SubStatusMaster;
import com.maan.insurance.model.entity.TtrnBonus;
import com.maan.insurance.model.entity.TtrnRiPlacement;
import com.maan.insurance.model.entity.TtrnRip;
import com.maan.insurance.model.entity.TtrnRiskCommission;
import com.maan.insurance.model.entity.TtrnRiskDetails;
import com.maan.insurance.model.repository.CountryMasterRepository;
import com.maan.insurance.model.repository.PositionMasterRepository;
import com.maan.insurance.model.repository.StatusMasterRepository;
import com.maan.insurance.model.repository.SubStatusMasterRepository;
import com.maan.insurance.model.repository.TmasLedgerMasterRepository;
import com.maan.insurance.model.repository.TtrnBonusRepository;
import com.maan.insurance.model.repository.TtrnInsurerDetailsRepository;
import com.maan.insurance.model.repository.TtrnMndInstallmentsRepository;
import com.maan.insurance.model.repository.TtrnRetroCessionaryRepository;
import com.maan.insurance.model.repository.TtrnRiPlacementRepository;
import com.maan.insurance.model.repository.TtrnRiskCommissionRepository;
import com.maan.insurance.model.req.DropDown.DuplicateCountCheckReq;
import com.maan.insurance.model.req.DropDown.GetClaimDepartmentDropDownReq;
import com.maan.insurance.model.req.DropDown.GetContractLayerNoReq;
import com.maan.insurance.model.req.DropDown.GetCopyQuoteReq;
import com.maan.insurance.model.req.DropDown.GetCurrencyIdReq;
import com.maan.insurance.model.req.DropDown.GetDepartmentDropDownReq;
import com.maan.insurance.model.req.DropDown.GetDepartmentieModuleDropDownReq;
import com.maan.insurance.model.req.DropDown.GetInwardBusinessTypeDropDownReq;
import com.maan.insurance.model.req.DropDown.GetPlacedProposalListReq;
import com.maan.insurance.model.req.DropDown.GetPreDepartmentDropDownReq;
import com.maan.insurance.model.req.DropDown.GetProductieModuleDropDownReq;
import com.maan.insurance.model.req.DropDown.GetProfitCentreieModuleDropDownReq;
import com.maan.insurance.model.req.DropDown.GetProposalNoReq;
import com.maan.insurance.model.req.DropDown.GetSectionListReq;
import com.maan.insurance.model.req.DropDown.GetSubProfitCentreMultiDropDownReq;
import com.maan.insurance.model.req.DropDown.GetSubProfitCentreMultiReq;
import com.maan.insurance.model.req.DropDown.GetTreatyTypeDropDownReq;
import com.maan.insurance.model.req.DropDown.GetYearToListValueReq;
import com.maan.insurance.model.req.proportionality.ContractReq;
import com.maan.insurance.model.req.proportionality.GetRetroContractDetailsListReq;
import com.maan.insurance.model.res.DropDown.CommonResDropDown;
import com.maan.insurance.model.res.DropDown.CommonResponse;
import com.maan.insurance.model.res.DropDown.GetBaseLayerExistingListRes;
import com.maan.insurance.model.res.DropDown.GetBaseLayerExistingListRes1;
import com.maan.insurance.model.res.DropDown.GetBouquetCedentBrokerInfoRes;
import com.maan.insurance.model.res.DropDown.GetBouquetCedentBrokerInfoRes1;
import com.maan.insurance.model.res.DropDown.GetBouquetExistingListRes;
import com.maan.insurance.model.res.DropDown.GetBouquetExistingListRes1;
import com.maan.insurance.model.res.DropDown.GetBouquetListRes;
import com.maan.insurance.model.res.DropDown.GetBouquetListRes1;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
import com.maan.insurance.model.res.DropDown.GetCommonValueRes;
import com.maan.insurance.model.res.DropDown.GetContractValRes;
import com.maan.insurance.model.res.DropDown.GetContractValidationRes;
import com.maan.insurance.model.res.DropDown.GetNewContractInfoRes;
import com.maan.insurance.model.res.DropDown.GetNewContractInfoRes1;
import com.maan.insurance.model.res.DropDown.GetNotPlacedProposalListRes;
import com.maan.insurance.model.res.DropDown.GetNotPlacedProposalListRes1;
import com.maan.insurance.model.res.DropDown.GetOpenPeriodRes;
import com.maan.insurance.model.res.DropDown.GetOpenPeriodRes1;
import com.maan.insurance.model.res.DropDown.GetPlacementInfoListRes;
import com.maan.insurance.model.res.DropDown.GetPlacementInfoListRes1;
import com.maan.insurance.model.res.DropDown.GetYearToListValueRes;
import com.maan.insurance.model.res.DropDown.GetYearToListValueRes1;
import com.maan.insurance.model.res.retro.CommonSaveRes;
import com.maan.insurance.service.Dropdown.DropDownService;
import com.maan.insurance.service.impl.QueryImplemention;
import com.maan.insurance.validation.Formatters;

@Service
public class DropDownServiceImple implements DropDownService{
	private Logger log = LogManager.getLogger(DropDownServiceImple.class);
	private List<Map<String, Object>> department;
	@Autowired
	private QueryImplemention queryImpl;
//	@Autowired
//	private DropDownValidation dropDownValidation;

	@Autowired
	private Formatters fm;
	@Autowired
	private TtrnRiskCommissionRepository rcRepo;
	@Autowired
	private TtrnMndInstallmentsRepository mndRepo;
	@Autowired
	private TtrnInsurerDetailsRepository idRepo;
	@Autowired
	private TtrnRetroCessionaryRepository cessRepo;
	@Autowired
	private CountryMasterRepository cmRepo;
	@Autowired
	private TmasLedgerMasterRepository lmRepo;
	@Autowired
	private PositionMasterRepository pmRepo;
	@Autowired
	private TtrnBonusRepository bonusRepo;
	@Autowired
	private StatusMasterRepository smRepo;
	@Autowired
	private SubStatusMasterRepository ssmRepo;
	@Autowired
	private TtrnRiPlacementRepository riPlaceRepo;
	
	@PersistenceContext
	private EntityManager em;
	

	@Override
	public GetCommonValueRes EditModeStatus(String proposalNo) {
		GetCommonValueRes response = new GetCommonValueRes();
		String result="";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		try{
			String query="POS_MAS_ED_MODE_SELECT";
			String args[] = new String[1];
			
			args[0] = proposalNo;
			list= queryImpl.selectList(query,args);
			if (!CollectionUtils.isEmpty(list)) {
				result = list.get(0).get("EDIT_MODE") == null ? ""
						: list.get(0).get("EDIT_MODE").toString();
			}
			response.setCommonResponse(result);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonValueRes getCoverLimitAmount(String proposalNo, String departmentId, String productId) {
		GetCommonValueRes response = new GetCommonValueRes();
		String coverLimitAmount="0";
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		try{
			if("1".equalsIgnoreCase(productId)){
				String query="GET_CLAIM_COVERLIMIT_FAC";
				
				list=queryImpl.selectList(query, new String[]{proposalNo});
				if (!CollectionUtils.isEmpty(list)) {
					coverLimitAmount = list.get(0).get("RSK_COVERLIMIT_OC") == null ? ""
							: list.get(0).get("RSK_COVERLIMIT_OC").toString();
				}
			}else{
				String query="GET_CLAIM_COVERLIMIT";
				list=queryImpl.selectList(query, new String[]{proposalNo,departmentId});
				if (!CollectionUtils.isEmpty(list)) {
					coverLimitAmount = list.get(0).get("RSK_COVER_LIMT") == null ? ""
							: list.get(0).get("RSK_COVER_LIMT").toString();
				}
			}
			response.setCommonResponse(coverLimitAmount);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetOpenPeriodRes getOpenPeriod(String branchCode) {
		GetOpenPeriodRes response = new GetOpenPeriodRes();
		List<GetOpenPeriodRes1> finalList = new ArrayList<GetOpenPeriodRes1>();
		GetOpenPeriodRes1 res = new GetOpenPeriodRes1();
		String openPeriodDate="";
		try {
		String query="GET_OPEN_PERIOD_DATE";
		String[] args = new String[1];
		args[0] = branchCode;
		
		log.info("Select Query ==> " + query);
		List<Map<String,Object>> list=queryImpl.selectList(query,args);
		
		for(int i=0 ; i<list.size() ; i++) {
			Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
			res.setOpstartDate(tempMap.get("START_DATE")==null?"":tempMap.get("START_DATE").toString());
			res.setOpendDate(tempMap.get("END_DATE")==null?"":tempMap.get("END_DATE").toString());
			openPeriodDate=openPeriodDate+res.getOpstartDate()+" to "+res.getOpendDate()+" ,";
			finalList.add(res);
		}
		if(StringUtils.isNotBlank(openPeriodDate))
		openPeriodDate = openPeriodDate.substring(0, openPeriodDate.length() - 1);
		response.setOpenPeriodDate(openPeriodDate);
		response.setCommonResponse(finalList);
		response.setMessage("Success");
		response.setIsError(false);
		}catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}

	@Override
	public GetCommonDropDownRes getCurrencyMasterDropDown(String branchCode, String countryId) {
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> personalInfo = new ArrayList<CommonResDropDown>();
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		try{
			String query = "common.select.getCurrencyList";
			
			list = queryImpl.selectList(query,new String[]{branchCode,countryId,"Y"});
			for(int i=0 ; i<list.size() ; i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				res.setCode(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
				res.setCodeDescription(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
				personalInfo.add(res);
			}
			response.setCommonResponse(personalInfo);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}

	@Override
	public GetCommonValueRes getDepartmentName(String branchCode, String productCode, String deptCode) {
		GetCommonValueRes response = new GetCommonValueRes();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String deptName="";
		try{
			String query="common.select.getDeptName";
			list = queryImpl.selectList(query,new String[]{branchCode,productCode,deptCode});
			if (!CollectionUtils.isEmpty(list)) {
				deptName = list.get(0).get("TMAS_DEPARTMENT_NAME") == null ? ""	
						: list.get(0).get("TMAS_DEPARTMENT_NAME").toString();
			}
			
			response.setCommonResponse(deptName);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}

		return response;
	}

	@Override
	public GetCommonDropDownRes getClaimDepartmentDropDown(GetClaimDepartmentDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> department = new ArrayList<CommonResDropDown>();
		try{
			String count="";
			String query="";
			if("2".equals(req.getProductId())){
				count=getCombinedClass(req.getBranchCode(),req.getProductId(),req.getDepartmentId());
			}if(StringUtils.isNotBlank(count) && "2".equals(req.getProductId())){

				 query="common.department.combined.premiumclaim";
				
				 list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductId(),count});
				
			}else{
			
				 query="common.select.getDepartmentList.preClaim";
				
				 list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductId(),req.getStatus(),req.getContractNo(),req.getLayerNo()});
			
			if(list.size()==0){
				 query = "DEPARTMENT_VALUE";
				 list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductId(),req.getStatus(),req.getContractNo(),req.getLayerNo()});
				
			}
			}
			 for(int i=0 ; i<list.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					res.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
					res.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
					department.add(res);
				}
			response.setCommonResponse(department);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}
	public String getCombinedClass(String branchCode,String productId,String departId){
		
		String count="";
		try {
			String query="common.select.getCoreCompanyName";
			log.info("Select Query==> " + query);
			List<Map<String,Object>> list = queryImpl.selectList(query, new String[]{branchCode,productId,departId});
			if(list!=null && list.size()>0){
			Map<String,Object>map=list.get(0);
			count=map.get("CORE_COMPANY_CODE")==null?"":map.get("CORE_COMPANY_CODE").toString();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}


	@Override
	public GetCommonDropDownRes getSubProfitCentreMulti(GetSubProfitCentreMultiReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> subProfitCenterList=new ArrayList<CommonResDropDown>();
		String query="";
		try{
			
			if(req.getSubProfitId()!=null && !req.getSubProfitId().equalsIgnoreCase("ALL")){
			query="common.select.getspfcName1";
			
			list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getDepartmentId(),"Y",req.getSubProfitId()});
			
			}
			
			else
			{
				 query="common.select.getspfcName";
				 list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getDepartmentId(),"Y"});
				
			}
			 
			for(int i=0 ; i<list.size() ; i++) 
			{
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					res.setCode(tempMap.get("TMAS_SPFC_ID")==null?"":tempMap.get("TMAS_SPFC_ID").toString());
					res.setCodeDescription(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
					subProfitCenterList.add(res);
				}
			 response.setCommonResponse(subProfitCenterList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		
		return response;
	
	}
	public String getpreReopendDate(String contractNo, String claimNo, String type) {
		String result="";
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			String query="";
			if("Reopen".equalsIgnoreCase(type)){
				query="GET_PRE_REOPEN_DATE";
				list=queryImpl.selectList(query,new String[]{contractNo,claimNo});
				if (!CollectionUtils.isEmpty(list)) {
					result = list.get(0).get("REOPENED_DATE") == null ? ""
							: list.get(0).get("REOPENED_DATE").toString();
				}
			}else if("RetroReopen".equalsIgnoreCase(type)){
				query="GET_PRE_REOPEN_DATE_RETRO";
				list=queryImpl.selectList(query,new String[]{contractNo,claimNo});
				if (!CollectionUtils.isEmpty(list)) {
					result = list.get(0).get("REOPENED_DATE") == null ? ""
							: list.get(0).get("REOPENED_DATE").toString();
				}
			}else if("RetroReputed".equalsIgnoreCase(type)){
				query="GET_PRE_REOPEN_DATE_RETRO";
				list=queryImpl.selectList(query,new String[]{contractNo,claimNo});
				if (!CollectionUtils.isEmpty(list)) {
					result = list.get(0).get("REOPENED_DATE") == null ? ""
							: list.get(0).get("REOPENED_DATE").toString();
				}
			}
			else {
				query="GET_PRE_REPUTED_DATE";
				list=queryImpl.selectList(query,new String[]{contractNo,claimNo});
				if (!CollectionUtils.isEmpty(list)) {
					result = list.get(0).get("REPUDATE_DATE") == null ? ""
							: list.get(0).get("REPUDATE_DATE").toString();
				}
			}
			
			
			System.out.println(result);
		}catch(Exception e){
			e.printStackTrace();
			log.debug("Exception @ {" + e + "}");	
		}

		return result;
	}

	@Override
	public GetCommonValueRes getCurrencyId(GetCurrencyIdReq req) {
		GetCommonValueRes response = new GetCommonValueRes();
		
		String currency="";
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			String query="";
			if("4".equalsIgnoreCase(req.getProductId())||"5".equalsIgnoreCase(req.getProductId())){
				query="GET_CLAIM_CURRENCY_RETRO";
			}else{
			 query="GET_CLAIM_CURRENCY";
			}
			list=queryImpl.selectList(query,new String[]{req.getClaimNo(), req.getContractNo(),req.getLayerNo()});
			if (!CollectionUtils.isEmpty(list)) {
				currency = list.get(0).get("CURRENCY") == null ? ""
						: list.get(0).get("CURRENCY").toString();
			}
			
			response.setCommonResponse(currency);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonValueRes getProposalNo(GetProposalNoReq req) {
		
		GetCommonValueRes response = new GetCommonValueRes();
		String query="";
		String proposalNo="";
		String[] obj=null;
		try {
		if(StringUtils.isNotBlank(req.getProductId()) &&("1".equals(req.getProductId()) || "4".equals(req.getProductId())) ){
			query="GET_FAC_PROPOSAL_NO";
			obj=new String[1];
			obj[0]=req.getContractNo();
		
		}else if("2".equals(req.getProductId())){
			query="GET_PRO_PROPOSAL_NO";
			obj=new String[2];
			obj[0]=req.getContractNo();
			obj[1]=req.getDepartId();
			
		}
		else if("3".equals(req.getProductId()) || "5".equalsIgnoreCase(req.getProductId())){
			query="GET_XOL_PROPOSAL_NO";
			obj=new String[2];
			obj[0]=req.getContractNo();
			obj[1]=req.getLayerNo();
		
		}
		List<Map<String,Object>>list=queryImpl.selectList(query,obj);
		if(list!=null &&list.size()>0){
			Map<String,Object>map=list.get(0);
			proposalNo=map.get("PROPOSAL_NO")==null?"":map.get("PROPOSAL_NO").toString();
		}

		
		response.setCommonResponse(proposalNo);
		response.setMessage("Success");
		response.setIsError(false);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
	return response;
	
	}
	

	@Override
	public GetCommonDropDownRes getSectionList(GetSectionListReq req) {
		String query="";
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> resList=new ArrayList<CommonResDropDown>();
		try{
			query="GET_SECTION_LIST";
			String args[]=new String[3];
			args[0] = req.getContractNo();
			args[1] =req.getDepartId();
			args[2] = req.getBranchCode();
			list=queryImpl.selectList(query,args);
			for(int i=0 ; i<list.size() ; i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				res.setCode(tempMap.get("SECTION_NO")==null?"":tempMap.get("SECTION_NO").toString());
				res.setCodeDescription(tempMap.get("SECTION_NAME")==null?"":tempMap.get("SECTION_NAME").toString());
				resList.add(res);
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonValueRes getContractLayerNo(GetContractLayerNoReq req) {
		GetCommonValueRes response = new GetCommonValueRes();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		String result =" ";
		try{
			if("3".equalsIgnoreCase(req.getProductId()) || "5".equalsIgnoreCase(req.getProductId())){
			String query="GET_CON_DEPT_ID_LAYER";
			String args[] = new String[3];
			args[0] =req.getContractNo();
			args[1] = req.getLayerNo();
			args[2] =req.getBranchCode();
			list=queryImpl.selectList(query,args);
			if (!CollectionUtils.isEmpty(list)) {
				result =(list.get(0).get("LAYER_NO") == null ? ""
						: list.get(0).get("LAYER_NO").toString());
						}
			
			}
			else if("2".equalsIgnoreCase(req.getProductId())){
				String query="GET_CON_DEPT_ID";
				String args[] = new String[3];
				args[0] =req.getContractNo();
				args[1] = req.getLayerNo();
				args[2] =req.getBranchCode();
				list=queryImpl.selectList(query,args);
				if (!CollectionUtils.isEmpty(list)) {
					result =(list.get(0).get("DEPT_ID") == null ? ""
							: list.get(0).get("DEPT_ID").toString());
				
				}
				
				
			}
			else if("1".equalsIgnoreCase(req.getProductId())){
				String query="GET_CON_DETAILS";
				String args[] = new String[2];
				args[0] =req.getContractNo();
				args[1] =req.getBranchCode();
				list=queryImpl.selectList(query,args);
				if (!CollectionUtils.isEmpty(list)) {
					result =(list.get(0).get("GET_CON_DEPT_ID") == null ? ""
							: list.get(0).get("GET_CON_DEPT_ID").toString());
				
				}
				
				
			}
			
			response.setCommonResponse(result);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	
	}

	@Override
	public GetCommonDropDownRes getPreDepartmentDropDown(GetPreDepartmentDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		List<Map<String,Object>> department=new ArrayList<Map<String,Object>>();
		try{
			String count="";
			if("2".equals(req.getProductId())){
				count=getCombinedClass(req.getBranchCode(),req.getProductId(),req.getDepartmentId());
			}if(StringUtils.isNotBlank(count) && "2".equals(req.getProductId())){

				String query="common.department.combined.premiumclaim";
				
				department=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductId(),count});	
			}else{
			
				String query="common.select.getDepartmentList.preClaim";
				
				department=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductId(),req.getStatus(),req.getContractNo(),req.getLayerNo()});	
			if(department.size()==0){
				 query = "DEPARTMENT_VALUE";
				 department=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductId(),req.getStatus(),req.getContractNo(),req.getLayerNo()});	
			}
			}
			for(int i=0 ; i<department.size() ; i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) department.get(i);
				res.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
				res.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				resList.add(res);
			}
			response.setCommonResponse(resList);
			
			
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonDropDownRes getSubProfitCentreMultiDropDown(GetSubProfitCentreMultiDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		String query="";
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try{
			 query="common.select.subProfitCenterList";
			if(req.getSubProfitId()!=null && !((String) req.getSubProfitId()).equalsIgnoreCase("ALL")){
			query="common.select.subProfitCenterList1";
			
			
			list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getDepartmentId(),"Y",(String) req.getSubProfitId()});
			}else{
				query="common.select.subProfitCenterList2";
				list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getDepartmentId(),"Y"});
			}
			
			for(int i=0 ; i<list.size() ; i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				res.setCode(tempMap.get("TMAS_SPFC_ID")==null?"":tempMap.get("TMAS_SPFC_ID").toString());
				res.setCodeDescription(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
				resList.add(res);
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;

	}

	@Override
	public GetCommonValueRes getCashLossCount(String contractNo, String departmentId) {
		GetCommonValueRes response = new GetCommonValueRes();
	
		String query="";
		String code="";
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try{
			query = "GET_CASHLOSS_COUNT";
			list=queryImpl.selectList(query, new String[]{contractNo,departmentId});
			if (!CollectionUtils.isEmpty(list)) {
				code =(list.get(0).get("COUNT") == null ? ""
						: list.get(0).get("COUNT").toString());
			
			}
		
			response.setCommonResponse(code);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	
	}
	public int validatethree(String branchCode, String accountDate) {

		int count=0;
		
		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
		
		try{
			String query="GET_OPEN_PERIOD_DATE";
			String OpstartDate="";
			String OpendDate="";
			String[] args = new String[1];
			args[0]=branchCode;
			
			log.info("Select Query ==> " + query);
			List<Map<String,Object>> list=queryImpl.selectList(query,args);
			for(int i=0 ; i<list.size() ; i++) {
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				OpstartDate=tempMap.get("START_DATE")==null?"":tempMap.get("START_DATE").toString();
				OpendDate=tempMap.get("END_DATE")==null?"":tempMap.get("END_DATE").toString();
				String query1="GET_OPEN_PERIOD_VALIDATE";
				args = new String[3];
				args[0]=accountDate;
				args[1]=OpstartDate;
				args[2]=OpendDate;
		
				result=queryImpl.selectList(query1,args);
				if (!CollectionUtils.isEmpty(result)) {
					count =Integer.valueOf((result.get(0).get("TOTAL") == null ? ""
							: result.get(0).get("TOTAL").toString()));
				
				}
				
				 if(count>0)
					 break;
			}
		}catch(Exception e){
			e.printStackTrace();
			log.debug("Exception @ {" + e + "}");	
		}
		return count;
	
	}
	public  String GetDesginationCountry(final String limitOrigCur,final String ExchangeRate) {
		String valu="0";
		if (StringUtils.isNotBlank(limitOrigCur)&& Double.parseDouble(limitOrigCur) != 0) {
			double originalCountry = Double.parseDouble(limitOrigCur)/ Double.parseDouble(ExchangeRate);
			DecimalFormat myFormatter = new DecimalFormat("###0.00");
			final double dround = Math.round(originalCountry * 100.0) / 100.0;
			valu = myFormatter.format(dround);
		}
		return valu;
	}
	public static String formatter(final String value)
	{
		String output="0.00";
		if(StringUtils.isNotBlank(value))
		{
			double doublevalue=Double.parseDouble(value);
			DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,##0.00");
			output =myFormatter.format(doublevalue);
		}
		return output;
	}
	public static String formattereight(final String value)
	{
		String output="0.00";
		if(StringUtils.isNotBlank(value))
		{
			double doublevalue=Double.parseDouble(value);
			DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,##0.00000000");
			output =myFormatter.format(doublevalue);
		}
		return output;
	}
	public GetCommonValueRes GetExchangeRate(final String location,final String date,final String countryid,final String branchCode) 
	{
		GetCommonValueRes response=new GetCommonValueRes();
		String exRate="";
		String startDtOfMonth="";
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
			String query="common.select.getStartDTOfMonth";
			list=queryImpl.selectList(query,new String[] {date});
			if (!CollectionUtils.isEmpty(list)) {
				 startDtOfMonth=(list.get(0).get("STDATE") == null ? ""
						: list.get(0).get("STDATE").toString());
						}
						{
				query="common.select.getExRate";
			
				list=queryImpl.selectList(query,new String[] {location,countryid,startDtOfMonth,branchCode,startDtOfMonth});
				
				if(list!=null&&list.size()>0)
				{
					Map<String,Object> map=(Map<String,Object>)list.get(0);
					exRate=map.get("EXCHANGE_RATE")==null?"":map.get("EXCHANGE_RATE").toString();
				}
			}

			response.setCommonResponse(exRate);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}
	public static String exchRateFormat(final String value){
		String output="0.00";
		if(StringUtils.isNotBlank(value))
		{
			System.out.println(value);
			double doublevalue=Double.parseDouble(value);
			DecimalFormat myFormatter = new DecimalFormat("#####.##########");
			output = myFormatter.format(doublevalue);
		}
		return output;
	}

	@Override
	public GetCommonDropDownRes getPersonalInfoDropDown(String branchCode, String customerType, String pid) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> personalInfo=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try{
			String arg[]=null;
			String query="common.select.getPersonalInfoList";
			if(customerType.equals("L") && !pid.equalsIgnoreCase("4") &&  !pid.equalsIgnoreCase("5")){
				query="common.select.getPersonalInfoList1";
				arg=new String[7];
				arg[0]=branchCode;
				arg[1]=customerType;
				arg[2]="Y";
				arg[3]=branchCode;
				arg[4]="C";
				arg[5]="Y";
				arg[6]="64";
			}
			else if(customerType.equals("C") && pid.equalsIgnoreCase("4") ){
				query="common.select.getPersonalInfoList1";
				arg=new String[7];
				arg[0]=branchCode;
				arg[1]=customerType;
				arg[2]="Y";
				arg[3]=branchCode;
				arg[4]="C";
				arg[5]="Y";
				arg[6]="64";
			}
			else{
				arg=new String[3];
				arg[0]=branchCode;
				arg[1]=customerType;
				arg[2]="Y";
			}
		
			
			personalInfo=queryImpl.selectList(query,arg);
			 for(int i=0 ; i<personalInfo.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) personalInfo.get(i);
					res.setCode(tempMap.get("CUSTOMER_ID")==null?"":tempMap.get("CUSTOMER_ID").toString());
					res.setCodeDescription(tempMap.get("NAME")==null?"":tempMap.get("NAME").toString());
					resList.add(res);
				}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	
	}

	@Override
	public GetCommonDropDownRes getBankMasterDropDown(String branchCode) {
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> bankMaster=new ArrayList<Map<String,Object>>();
		try{
			String query="common.select.getBankMasterList";
			
			bankMaster=queryImpl.selectList(query, new String[]{branchCode,"Y"});
			 for(int i=0 ; i<bankMaster.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) bankMaster.get(i);
					res.setCode(tempMap.get("BANK_ID")==null?"":tempMap.get("BANK_ID").toString());
					res.setCodeDescription(tempMap.get("BANK_NAME")==null?"":tempMap.get("BANK_NAME").toString());
					resList.add(res);
				}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	
	}

	@Override
	public GetCommonDropDownRes getConstantDropDown(String categoryId) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		List<Map<String,Object>> constantList=new ArrayList<Map<String,Object>>();
		String query ="";
		try{
			if("30".equalsIgnoreCase(categoryId) || "48".equalsIgnoreCase(categoryId)){
			query="COM_SELECT_PERILS";
			}
			else{
			 query="common.select.getConstDet";
			 }
			
			
			constantList=queryImpl.selectList(query, new String[]{categoryId,"Y"});
			 for(int i=0 ; i<constantList.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) constantList.get(i);
					res.setCode(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString());
					res.setCodeDescription(tempMap.get("DETAIL_NAME")==null?"":tempMap.get("DETAIL_NAME").toString());
					resList.add(res);
				}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}



	public GetCommonDropDownRes getCurrencyShortList(String branchCode, String countryId) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		List<Map<String,Object>> constantList=new ArrayList<Map<String,Object>>();
		String query ="";
		try{
			query="GET_CURRENCY_SHORT_LIST";
			constantList=queryImpl.selectList(query, new String[]{branchCode,countryId,"Y"});
			 for(int i=0 ; i<constantList.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) constantList.get(i);
					res.setCode(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					res.setCodeDescription(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());
					resList.add(res);
				}
			response.setCommonResponse(resList);

			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;

	
	}

	@Override
	public GetCommonValueRes getDisableStatus1(String contractNo, String layerNo) {
		GetCommonValueRes response = new GetCommonValueRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String status="Y";
		if(StringUtils.isBlank(contractNo)){
			contractNo="";
		}if(StringUtils.isBlank(layerNo)){
			layerNo="";
		}
		try {
			String query = "GET_DISABLE_STATUS1";
			list=queryImpl.selectList(query, new String[]{contractNo,layerNo,contractNo,layerNo});
			if (!CollectionUtils.isEmpty(list)) {
				status = list.get(0).get("STATUS") == null ? ""
						: list.get(0).get("STATUS").toString();
			}
			response.setCommonResponse(status);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public CommonResponse riskDetailsEndorsement(String proposalNo, String endtStatus) {
		CommonResponse response = new CommonResponse();
		try {
			
			String query = "call COPYQUOTE (?,?,?,?,?)";
			
			queryImpl.updateQuery(query, new String[]{"Endt",endtStatus==null?"":endtStatus,"","",proposalNo});
			
			response.setMessage("Success");
			response.setIsError(false);
	 }	catch(Exception e){
				log.error(e);

	}
		return response;
	}

	@Override
	public GetCommonDropDownRes getCountryDropDown(String branchCode) 
    {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();

		List<Map<String, Object>> countryList=new ArrayList<Map<String,Object>>();
		try
		{
			
		String query="common.select.getCountryList";
			countryList=queryImpl.selectList(query, new String[]{branchCode,"Y"});
			
			for(int i=0 ; i<countryList.size() ; i++)
			 {
				 
				CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) countryList.get(i);
					res.setCode(tempMap.get("COUNTRY_ID")==null?"":tempMap.get("COUNTRY_ID").toString());
					res.setCodeDescription(tempMap.get("COUNTRY_NAME")==null?"":tempMap.get("COUNTRY_NAME").toString());
					resList.add(res);
					
			 }
			
			//countryList=this.mytemplate.queryForList(query,new Object[]{branchCode,"Y"});
			 response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				}
		        
		     catch(Exception e)
		        {
					
		    	log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				
		        }
			return response;

	}

	
	@Override
	public GetCommonDropDownRes getTypeList(String type) 
	{
	GetCommonDropDownRes response = new GetCommonDropDownRes();
	List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();

		List<Map<String, Object>> countryList=new ArrayList<Map<String,Object>>();
		try
		{
			String query="GET_COMMISSION_LIST";
			countryList=queryImpl.selectList(query, new String[]{type,"Y"});
			 
			for(int i=0 ; i<countryList.size() ; i++)
			 {
				
				CommonResDropDown res = new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) countryList.get(i);
				res.setCode(tempMap.get("DETAIL_NAME")==null?"":tempMap.get("DETAIL_NAME").toString());
				res.setCodeDescription(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
				resList.add(res);
				
			}
			     
			    response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
				}
		        
		     catch(Exception e)
		        {
					
		    	log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
				
		        }
			return response;	
		
	}



	@Override
	public GetCommonDropDownRes getReinstatementOptionList() 
	{

		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {

			String query = "GET_REINSTATEMENT_LIST";

			result = queryImpl.selectList(query, new String[] { "33", "Y" });

			for (int i = 0; i < result.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) result.get(i);
				res.setCode(tempMap.get("DETAIL_NAME") == null ? "" : tempMap.get("DETAIL_NAME").toString());
				res.setCodeDescription(tempMap.get("REMARKS") == null ? "" : tempMap.get("REMARKS").toString());
				resList.add(res);
			}

			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}

		catch (Exception e) {

			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);

		}
		return response;
	}
	
   @Override
	public GetCommonDropDownRes getTerritoryRegionList(String branchCode) 
	{
		
		GetCommonDropDownRes red = new GetCommonDropDownRes();
		List<CommonResDropDown> res = new ArrayList<CommonResDropDown>();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try
		{
			String query="GET_Rate_FLOW";
			String[] args=new String[1];
			args[0]=branchCode;
			list=queryImpl.selectList(query,args);
			
			//List=mytemplate.queryForList(query, args);
			
			
			for(int i=0;i<list.size();i++)
			{
				
				CommonResDropDown range=new CommonResDropDown();
				
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				range.setCode(tempMap.get("TERRITORY_ID")==null?"":tempMap.get("TERRITORY_ID").toString());
				range.setCodeDescription(tempMap.get("TERRITORY_NAME")==null?"":tempMap.get("TERRITORY_NAME").toString());
				res.add(range);				
				
				
			}
			 red.setCommonResponse(res);
				red.setMessage("Success");
				red.setIsError(false);
				}
		        
		     catch(Exception e)
		        {
					
		    	log.error(e);
				e.printStackTrace();
				red.setMessage("Failed");
				red.setIsError(true);
				
		        }
			
	return red;	
	}
		
		



	@Override
	public GetCommonDropDownRes getDocType(String branchCode, String productId, String moduleType) 
	{
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();
		
		List<Map<String,Object>> listing=new ArrayList<Map<String,Object>>();
        try
		{
			

		String query="upload.getDocTypeList";
		
		listing=queryImpl.selectList(query,new String[]{branchCode,productId,moduleType,"Y"});
		
		
		for(int i=0;i<listing.size();i++)
		{
			CommonResDropDown range=new CommonResDropDown();
			
			Map<String,Object> tempMap = (Map<String,Object>) listing.get(i);
			range.setCode(tempMap.get("DOC_TYPE")==null?"":tempMap.get("DOC_TYPE").toString());
			range.setCodeDescription(tempMap.get("DOC_NAME")==null?"":tempMap.get("DOC_NAME").toString());
			reslist.add(range);		
			
			
			
			
		}
		response.setCommonResponse(reslist);
		response.setMessage("Success");
		response.setIsError(false);
		
		}
		
		catch(Exception e)
		{
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
		}
		return response;
		}

	
	
	



	@Override
	public GetCommonDropDownRes getPolicyBranchDropDown(String branchCode)
	{
		
	GetCommonDropDownRes response = new GetCommonDropDownRes();
	
	List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();
	
		List<Map<String,Object>> policyBranch=new ArrayList<Map<String,Object>>();
		try{
			String query="common.select.getPolicyBranchList";
			
			policyBranch=queryImpl.selectList(query,new String[]{branchCode,"Y"});
			
			for(int i=0;i<policyBranch.size();i++)
			{
				CommonResDropDown range=new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) policyBranch.get(i);
				range.setCode(tempMap.get("TMAS_POL_BRANCH_ID")==null?"":tempMap.get("TMAS_POL_BRANCH_ID").toString());
				range.setCodeDescription(tempMap.get("TMAS_POL_BRANCH_NAME")==null?"":tempMap.get("TMAS_POL_BRANCH_NAME").toString());
				reslist.add(range);		
				
			}
			response.setCommonResponse(reslist);
			response.setMessage("Success");
			response.setIsError(false);
			
			}
		catch(Exception e)
		{
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
		}
		return response;
		}




	@Override
	public GetCommonDropDownRes getDepartmentDropDown(GetDepartmentDropDownReq req) 
	{
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		try
		{
			List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();
		
		List<Map<String,Object>> policyBranch=new ArrayList<Map<String,Object>>();
		

		if(StringUtils.isBlank(req.getProductCode()))
		{
		
		String query="common.select.departlist.premium";
		policyBranch=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getStatus()});	
		
			for(int i=0;i<policyBranch.size();i++)
		    {
			CommonResDropDown range=new CommonResDropDown();
			Map<String,Object> tempMap = (Map<String,Object>) policyBranch.get(i);
			range.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
			range.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
			reslist.add(range);		
				}
		}
		else if(!StringUtils.isBlank(req.getBaseLayer()) ) 
		{	
			String query="common.select.getDepartmentList3";
			policyBranch=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getStatus(),req.getBaseLayer(),req.getBaseLayer(),req.getProposalNo()});
	
			for(int j=0;j<policyBranch.size();j++)
			{
				CommonResDropDown range=new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) policyBranch.get(j);
				range.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
				range.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				reslist.add(range);		
			}}
		
		else if(StringUtils.isBlank(req.getProposalNo()) && StringUtils.isBlank(req.getContractNo()))
		{
			String query="common.select.getDepartmentList";
			department=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getStatus()});	
			for(int k=0;k<policyBranch.size();k++)
			{
				CommonResDropDown range=new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) policyBranch.get(k);
				range.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
				range.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				reslist.add(range);		
			}
				}
		
		else if(StringUtils.isBlank(req.getContractNo()) && !StringUtils.isBlank(req.getProposalNo()))
		{
			
			String query="common.select.getDepartmentList2";
			
			department=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getStatus(),req.getProposalNo(),req.getBaseLayer(),req.getBaseLayer()});
			
			for(int l=0;l<policyBranch.size();l++)
			{
				CommonResDropDown range=new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) policyBranch.get(l);
				range.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
				range.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				reslist.add(range);		
			}
			
			
		}
		
		else if(!StringUtils.isBlank(req.getContractNo()) && StringUtils.isBlank(req.getProposalNo()))
		{
			String query="common.select.getDepartmentList1";
			department=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getStatus(),req.getContractNo(),req.getContractNo()});

			for(int m=0;m<policyBranch.size();m++)
			{
				CommonResDropDown range=new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) policyBranch.get(m);
				range.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
				range.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				reslist.add(range);	
			}
	
	}
		
		else if(!StringUtils.isBlank(req.getContractNo()))
		{
			String query="common.select.getDepartmentList1";
			
		  department=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProductCode(),req.getStatus(),req.getContractNo(),req.getContractNo()});
		
		
		  for(int n=0;n<policyBranch.size();n++)
			{

				CommonResDropDown range=new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) policyBranch.get(n);
				range.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
				range.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				reslist.add(range);	
			}
		 		}
		 response.setCommonResponse(reslist);
			response.setMessage("Success");
			response.setIsError(false);
		}
	catch(Exception e)
	{
	e.printStackTrace();
	response.setMessage("Success");
	response.setIsError(false);
	}
	return response;
	}

//	<!--- bean>
	@Override
	public GetCommonDropDownRes getUnderwriterCountryList(String leaderUnderriter, String branchCode) 
	{
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		try
		{
		
		List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
		
			
			String query="GET_UNDERWRITER_COUNTRY_LIST";
			list=queryImpl.selectList(query,new String[]{branchCode,"Y",leaderUnderriter});
//			result=this.mytemplate.queryForList(query,new Object[]{branchCode,"Y",bean.getLeader_Underwriter()});
			for(int i=0;i<list.size();i++)
			{
				
			CommonResDropDown range=new CommonResDropDown();    
			
			Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
			range.setCode(tempMap.get("COUNTRY_ID")==null?"":tempMap.get("COUNTRY_ID").toString());
			range.setCodeDescription(tempMap.get("COUNTRY_NAME")==null?"":tempMap.get("COUNTRY_NAME").toString());
			reslist.add(range);		   
	              
			}
			response.setCommonResponse(reslist);
			response.setMessage("Success");
			response.setIsError(false);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
		}
		
		return response;
		
	}

	@Override
	public GetCommonDropDownRes getProfitCentreDropDown(String branchCode) 
	{
		
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		try
		{
		
		List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
		
		String query="common.select.getProfitCenterList";
		
		list=queryImpl.selectList(query,new String[]{branchCode,"Y"});
		
		for(int i=0;i<list.size();i++)
		{
			CommonResDropDown range=new CommonResDropDown();  
			
			Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
			range.setCode(tempMap.get("TMAS_PFC_ID")==null?"":tempMap.get("TMAS_PFC_ID").toString());
			range.setCodeDescription(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString());
			reslist.add(range);		
		
        }
		response.setCommonResponse(reslist);
		response.setMessage("Success");
		response.setIsError(false);
		}
		catch(Exception e)
		{
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
		}
		
	return response;
	
	
	}

	
	
	@Override
	public GetCommonDropDownRes RenewalDropDown(String branchCode, String productCode, String status) 
	{


		GetCommonDropDownRes response = new GetCommonDropDownRes();
		try
		{

			List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();

			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	

			String query="common.select.getDepartmentList";


			list=queryImpl.selectList(query,new String[]{branchCode,productCode,status});	


			for(int i=0;i<list.size();i++)
			{
				CommonResDropDown range=new CommonResDropDown();  

				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				range.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
				range.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				reslist.add(range);		
			}

			response.setCommonResponse(reslist);
			response.setMessage("Success");
			response.setIsError(false);
		
		}

		catch(Exception e)
		{
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}

		return response;
	}

	@Override
	public GetCommonDropDownRes getTerritoryDropDown(String branchCode) 
	{
	GetCommonDropDownRes response = new GetCommonDropDownRes();
	try
	{

		List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	

			String query="common.select.getTerritoryList";
			
			list=queryImpl.selectList(query,new String[]{branchCode,"Y"});
			for(int i=0;i<list.size();i++)
			{
				CommonResDropDown range=new CommonResDropDown();  

				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				range.setCode(tempMap.get("TERRITORY_CODE")==null?"":tempMap.get("TERRITORY_CODE").toString());
				range.setCodeDescription(tempMap.get("TERRITORY_DESC")==null?"":tempMap.get("TERRITORY_DESC").toString());
				reslist.add(range);		
			}

			response.setCommonResponse(reslist);
			response.setMessage("Success");
			response.setIsError(false);
			
	}

	
			catch(Exception e)
			{

				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}

		return response;
	}

	@Override
	public CommonResponse updateSubClass(String proposalNo, String type) {
		CommonResponse response = new CommonResponse();
		try {
			String query = "UPDATE_SUBCLASS_QUERY";
			queryImpl.updateQuery(query, new String[]{proposalNo,type});
			response.setMessage("Success");
			response.setIsError(false);
	 }	catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	
	}

	@Override
	public CommonResponse updateRenewalEditMode(updateSubEditModeReq req) {
		CommonResponse response = new CommonResponse();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		String proposal  = "";
		try{
			String query="GET_BASE_PROPOSAL_NO";
			String args[] = new String[1];
			args[0] = req.getProposalNo();
			list=queryImpl.selectList(query, args);
			if (!CollectionUtils.isEmpty(list)) {
				proposal = list.get(0).get("PROPOSAL_NO") == null ? ""
						: list.get(0).get("PROPOSAL_NO").toString();
			}
			
			if(!"0".equalsIgnoreCase(proposal)){
			updateSubEditMode(req);
			updateEditMode(req);
			}
			response.setMessage("Success");
			response.setIsError(false);
	 }	catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}
	public CommonResponse updateSubEditMode(updateSubEditModeReq req) {
		CommonResponse response = new CommonResponse();
		try{
			String query="UPDATE_SUB_ENDT_STATUS";
			String args[] = new String[2];
			if(!"N".equalsIgnoreCase(req.getStatus())){
			args[0] = req.getStatus() +"-"+ req.getUpdateProposalNo();
			}
			else{
				args[0] = req.getStatus();	
			}
			args[1] = req.getProposalNo();
			queryImpl.updateQuery(query, args);
			response.setMessage("Success");
			response.setIsError(false);
	 }	catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}
		public CommonResponse updateEditMode(updateSubEditModeReq req) {
			CommonResponse response = new CommonResponse();
			try{
				String query="POS_MAS_ED_MODE_UPDT";
				String args[] = new String[2];
				if(!"N".equalsIgnoreCase(req.getStatus())){
					args[0] = req.getStatus() +"-"+ req.getUpdateProposalNo();
					}
					else{
						args[0] = req.getStatus();	
					}
				args[1] = req.getProposalNo();
				queryImpl.updateQuery(query, args);
				response.setMessage("Success");
				response.setIsError(false);
		 }	catch(Exception e){
					log.error(e);
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
			return response;
		}

		@Override
		public GetCommonValueRes getBaseProposal(String proposalNo) {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			GetCommonValueRes response = new GetCommonValueRes();
			String result="";
			try{
				String query="GET_BASE_PROPOSAL";
				String args[] = new String[1];
				args[0] = proposalNo;
				list=queryImpl.selectList(query, args);
				if (!CollectionUtils.isEmpty(list)) {
					result = list.get(0).get("BASE_LAYER") == null ? ""
							: list.get(0).get("BASE_LAYER").toString();
				}
				response.setCommonResponse(result);
				response.setMessage("Success");
				response.setIsError(false);
		 }	catch(Exception e){
					log.error(e);
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
			return response;
		
		}
		public GetCommonDropDownRes getSubProfitCentreDropDown(String deptid,String branchCode,String productCode){
			GetCommonDropDownRes response = new GetCommonDropDownRes();
			List<Map<String,Object>> subProfitCenterList=new ArrayList<Map<String,Object>>();
			List<CommonResDropDown>  resList = new ArrayList<CommonResDropDown>();
			try{
				String query="common.select.getSubProfitCenterList";
			
				subProfitCenterList=queryImpl.selectList(query,new String[]{branchCode,productCode,deptid,"Y"});
				 for(int i=0 ; i<subProfitCenterList.size() ; i++) {
					 CommonResDropDown res = new CommonResDropDown();
						Map<String,Object> tempMap = (Map<String,Object>) subProfitCenterList.get(i);
						res.setCode(tempMap.get("TMAS_SPFC_ID")==null?"":tempMap.get("TMAS_SPFC_ID").toString());
						res.setCodeDescription(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
						resList.add(res);
					}
				 response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
		 }	catch(Exception e){
					log.error(e);
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
			return response;
		}	
		public GetCommonDropDownRes getRetroContractDetailsList(GetRetroContractDetailsListReq req,int flag, String UWYear){
			GetCommonDropDownRes response = new GetCommonDropDownRes();
			List<CommonResDropDown>  resList = new ArrayList<CommonResDropDown>();
			String query="";
			List<Map<String, Object>> list= new ArrayList<Map<String, Object>>();
			try{
				if(flag==1)	{
					if("4".equals(req.getProductid())){
						query = "fac.select.uwYear";
					}else{
						query = "risk.select.uwYear";
					}
					list=queryImpl.selectList(query,new String[] {req.getProductid(),req.getIncepDate(),req.getBranchCode(),req.getIncepDate()});
					
					}
				 else if(StringUtils.isNotEmpty(UWYear)&& flag==2){
					query = "fac.select.retroContDet";
					
					list = queryImpl.selectList(query, new String[] {req.getProductid(),(StringUtils.isBlank(req.getRetroType())?"":req.getRetroType()),UWYear,req.getIncepDate(),req.getBranchCode(),(StringUtils.isBlank(req.getRetroType())?"":req.getRetroType()),UWYear,req.getIncepDate()});
				}
				 else if(StringUtils.isNotEmpty(UWYear)&&flag==3){
					query = "FAC_SELECT_RETRO_DUP_CONTRACT";
					
					list = queryImpl.selectList(query, new String[] {"4","TR",UWYear,req.getIncepDate(),req.getBranchCode(),"TR",UWYear,req.getIncepDate()});
				}
				
				if(list!=null && list.size()>0){

					for (int i = 0; i < list.size(); i++) {
						CommonResDropDown res = new CommonResDropDown();
						Map<String, Object> insMap = (Map<String, Object>)list.get(i);
						res.setCode(insMap.get("CONTDET1")==null?"":insMap.get("CONTDET1").toString());
						res.setCodeDescription(insMap.get("CONTDET2")==null?"":insMap.get("CONTDET2").toString());
						resList.add(res);					
						}
				
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
		 }	}catch(Exception e){
					log.error(e);
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
			return response;
		}
		public  String formatterpercentage(final String value)
		{
			String output="0.00";
			if(StringUtils.isNotBlank(value))
			{
				double doublevalue=Double.parseDouble(value);
				DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,##0.0000");
				output =myFormatter.format(doublevalue);
			}
			return output;
		}
			
		public GetCommonValueRes getUnderWriterLimmit(String uwName,String processId,String ProductId,String deptId){
			GetCommonValueRes response=new GetCommonValueRes();
			String uwLimit=null;
			String query="";
			List<Map<String, Object>> uwList= new ArrayList<Map<String, Object>>();
			try{
				query="common.select.getUWLimit1";
				uwList=queryImpl.selectList(query,new String[] {uwName,ProductId,deptId});
				
				
				if(uwList!=null && uwList.size()>0)
				{
					Map<String,Object> uwMap=(Map<String,Object>)uwList.get(0);
					uwLimit=uwMap.get("UWLIMIT")==null?"0":fm.decimalFormat(Double.parseDouble(uwMap.get("UWLIMIT").toString()),0);
				}else
					uwLimit ="0";
				response.setCommonResponse(uwLimit);
				response.setMessage("Success");
				response.setIsError(false);
			}catch(Exception e){
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}

			return response;
		}
		
		public List<GetContractValidationRes> getContractValidation(String productId, String cedingCompany,String inceptionDate, String expiryDate, String year,String originalCurrency, String departmentId, String type,String sumInsured, String ContNo, String profitCenter,String surplus, String coverPer, String dedPer, String layerNo, String branchCode) {
			String query="";
		//	GetContractValidationRes1 response = new GetContractValidationRes1();
			List<GetContractValidationRes> resList = new ArrayList<GetContractValidationRes>();
			List<Map<String,Object>> ContractList=new ArrayList<Map<String,Object>>();
			String args[] = null;
			try{
				if(StringUtils.isBlank(sumInsured)){
					sumInsured="0";
				}
				if(StringUtils.isBlank(surplus)){
					surplus="0";
				}
				if(StringUtils.isBlank(coverPer)){
					coverPer="0";
				}
				if(StringUtils.isBlank(dedPer)){
					dedPer="0";
				}
				if(!cedingCompany.matches("^[0-9]+$")){
					cedingCompany="";
				}
				if("1".equalsIgnoreCase(productId)){
					query="FAC_CONTRACT_LIST";
					 args = new String[10];
						args[0] = cedingCompany;
						args[1] = inceptionDate;
						args[2] = expiryDate;
						args[3] = year;
						args[4] = originalCurrency;
						
						args[5] = departmentId;
						args[6] = type;
						args[7] = sumInsured;
						args[8] = profitCenter;
						args[9] = branchCode;
						if(ContNo!="0"){
						query="FAC_CONTRACT_LIST1";
					}
				}
				else if("2".equalsIgnoreCase(productId)){
					query="PTTY_CONTRACT_LIST";
					args = new String[9];
					args[0] = cedingCompany;
					args[1] = inceptionDate;
					args[2] = expiryDate;
					args[3] = year;
					args[4] = originalCurrency;
					args[5] = departmentId;
					args[6] = type;
					args[7] = profitCenter;
					args[8] = branchCode;
					if("1".equalsIgnoreCase(type) ||"4".equalsIgnoreCase(type)||"5".equalsIgnoreCase(type) ){
						//query+="  and RP.RSK_LIMIT_OC="+req.getSumInsured();
						query="PTTY_CONTRACT_LIST1";
					}
					else if("2".equalsIgnoreCase(type)){
						//query+="  and RP.RSK_TREATY_SURP_LIMIT_OC="+req.getSumInsured();
						query="PTTY_CONTRACT_LIST2";
					}
					else if("3".equalsIgnoreCase(type)){
						//query+="  and RP.RSK_LIMIT_OC="+req.getSumInsured()+" and RP.RSK_TREATY_SURP_LIMIT_OC = "+req.getSurplus();
						query="PTTY_CONTRACT_LIST3";
					}
					if(ContNo!="0"){
						//query+="  and RD.RSK_CONTRACT_NO!="+req.getContno();
						query="PTTY_CONTRACT_LIST4";
					}
				}  if("3".equalsIgnoreCase(productId)){
					if(ContNo!="0"){
						query="NPTTY_CONTRACT_LIST_CON";
						args = new String[11];
						args[0] = cedingCompany;
						args[1] = inceptionDate;
						args[2] = expiryDate;
						args[3] = year;
						args[4] = originalCurrency;
						args[5] = departmentId;
						args[6] = type;
						args[7] = profitCenter;
						args[8] = layerNo;
						args[9] = ContNo;
						args[10] = branchCode;
					}
					else{
						query="NPTTY_CONTRACT_LIST";
						args = new String[10];
						args[0] = cedingCompany;
						args[1] = inceptionDate;
						args[2] = expiryDate;
						args[3] = year;
						args[4] = originalCurrency;
						args[5] = departmentId;
						args[6] = type;
						args[7] = profitCenter;
						args[8] = layerNo;
						args[9] = branchCode;
					}
					
					if("1".equalsIgnoreCase(type) ||"2".equalsIgnoreCase(type)||"3".equalsIgnoreCase(type)||"4".equalsIgnoreCase(type)||"5".equalsIgnoreCase(type) ){
						//query+="  and  E1.cvr_limit="+req.getSumInsured()+" AND E1.DEDU="+req.getSurplus();
						query="NPTTY_CONTRACT_LIST_CON1";
					}
					else if("5".equalsIgnoreCase(type)){
						//query+="  and  E1.cvr_limit="+req.getSumInsured()+" AND E1.DEDU="+req.getSurplus()+" AND E1.COVER_PER = "+req.getCoverPer()+" AND E1.DED_PER = "+req.getDedPer() ;
						query="NPTTY_CONTRACT_LIST1";
					}
				}
		
				
				ContractList =queryImpl.selectList(query,args);
			
		
					 for(int i=0 ; i<ContractList.size() ; i++) {
						 GetContractValidationRes res = new GetContractValidationRes();
							Map<String,Object> tempMap = (Map<String,Object>) ContractList.get(i);
							res.setContractNo(tempMap.get("RSK_CONTRACT_NO")==null?"":tempMap.get("RSK_CONTRACT_NO").toString());
							res.setEndoresmentNo(tempMap.get("RSK_ENDORSEMENT_NO")==null?"":tempMap.get("RSK_ENDORSEMENT_NO").toString());
							res.setProposalNo(tempMap.get("RSK_PROPOSAL_NUMBER")==null?"":tempMap.get("RSK_PROPOSAL_NUMBER").toString());
							res.setBrokerId(tempMap.get("RSK_BROKERID")==null?"":tempMap.get("RSK_BROKERID").toString());
							res.setUnderWritter(tempMap.get("RSK_UNDERWRITTER")==null?"":tempMap.get("RSK_UNDERWRITTER").toString());
							res.setInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
							
							resList.add(res);
						}	
				
				
		 	}catch(Exception e){
					log.error(e);
					e.printStackTrace();
					
				}
			return resList;
		}
		//Productid 3
//		public List<GetContractValidationRes3> getContractValidation3(String productId, String cedingCompany,String inceptionDate, String expiryDate, String year,String originalCurrency, String departmentId, String type,String sumInsured, String ContNo, String profitCenter,String surplus, String coverPer, String dedPer, String layerNo, String branchCode) {
//			String query="";
//		
//			List<GetContractValidationRes3> resList = new ArrayList<GetContractValidationRes3>();
//			List<Map<String,Object>> ContractList=new ArrayList<Map<String,Object>>();
//			String args[] = null;
//			try{
//				if(StringUtils.isBlank(sumInsured)){
//					sumInsured="0";
//				}
//				if(StringUtils.isBlank(surplus)){
//					surplus="0";
//				}
//				if(StringUtils.isBlank(coverPer)){
//					coverPer="0";
//				}
//				if(StringUtils.isBlank(dedPer)){
//					dedPer="0";
//				}
//				if(!cedingCompany.matches("^[0-9]+$")){
//					cedingCompany="";
//				}
//	
//				 if("3".equalsIgnoreCase(productId)){
//					if(ContNo!="0"){
//						query="NPTTY_CONTRACT_LIST_CON";
//						args = new String[11];
//						args[0] = cedingCompany;
//						args[1] = inceptionDate;
//						args[2] = expiryDate;
//						args[3] = year;
//						args[4] = originalCurrency;
//						args[5] = departmentId;
//						args[6] = type;
//						args[7] = profitCenter;
//						args[8] = layerNo;
//						args[9] = ContNo;
//						args[10] = branchCode;
//					}
//					else{
//						query="NPTTY_CONTRACT_LIST";
//						args = new String[10];
//						args[0] = cedingCompany;
//						args[1] = inceptionDate;
//						args[2] = expiryDate;
//						args[3] = year;
//						args[4] = originalCurrency;
//						args[5] = departmentId;
//						args[6] = type;
//						args[7] = profitCenter;
//						args[8] = layerNo;
//						args[9] = branchCode;
//					}
//					
//					if("1".equalsIgnoreCase(type) ||"2".equalsIgnoreCase(type)||"3".equalsIgnoreCase(type)||"4".equalsIgnoreCase(type)||"5".equalsIgnoreCase(type) ){
//						//query+="  and  E1.cvr_limit="+req.getSumInsured()+" AND E1.DEDU="+req.getSurplus();
//						query="NPTTY_CONTRACT_LIST_CON1";
//					}
//					else if("5".equalsIgnoreCase(type)){
//						//query+="  and  E1.cvr_limit="+req.getSumInsured()+" AND E1.DEDU="+req.getSurplus()+" AND E1.COVER_PER = "+req.getCoverPer()+" AND E1.DED_PER = "+req.getDedPer() ;
//						query="NPTTY_CONTRACT_LIST1";
//					}
//				}
//				
//				ContractList =queryImpl.selectList(query,args);
//			
//				
//					 for(int i=0 ; i<ContractList.size() ; i++) {
//						 GetContractValidationRes3 res = new GetContractValidationRes3();
//							Map<String,Object> tempMap = (Map<String,Object>) ContractList.get(i);
////							res.setDedPer(tempMap.get("DED_PER")==null?"":tempMap.get("DED_PER").toString());
////							res.setLayerNo(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
////							res.setCoverPer(tempMap.get("COVER_PER")==null?"":tempMap.get("COVER_PER").toString());
////							res.setDedu(tempMap.get("DEDU")==null?"":tempMap.get("DEDU").toString());
////							res.setCoverLimit(tempMap.get("CVR_LIMIT")==null?"":tempMap.get("CVR_LIMIT").toString());
//							
//							
//							res.setInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
//							res.setProposalNo(tempMap.get("RSK_PROPOSAL_NUMBER")==null?"":tempMap.get("RSK_PROPOSAL_NUMBER").toString());
//							res.setContractNo(tempMap.get("RSK_CONTRACT_NO")==null?"":tempMap.get("RSK_CONTRACT_NO").toString());
//							res.setEndoresmentNo(tempMap.get("RSK_ENDORSEMENT_NO")==null?"":tempMap.get("RSK_ENDORSEMENT_NO").toString());
//							res.setBrokerId(tempMap.get("RSK_BROKERID")==null?"":tempMap.get("RSK_BROKERID").toString());
//							res.setUnderWritter(tempMap.get("RSK_UNDERWRITTER")==null?"":tempMap.get("RSK_UNDERWRITTER").toString());
//							
//							resList.add(res);
//						
//					 }
//				
//		 	}catch(Exception e){
//					log.error(e);
//					e.printStackTrace();
//					
//				}
//			return resList;
//		}
		public String getAcceptanceDate(String proposalNo) {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			String result="";
			try{
				String query="GET_ACCEPTANCE_DATE";
				list=queryImpl.selectList(query, new String[]{proposalNo});
				if (!CollectionUtils.isEmpty(list)) {
					result = list.get(0).get("ACCOUNT_DATE") == null ? ""
							: list.get(0).get("ACCOUNT_DATE").toString();
				}
			}catch(Exception e){
				e.printStackTrace();
			}
			return result;
		}	
		public  boolean GetShareEqualValidation(String productId,String leaderUnderwriterShare, String proposalNo ) {
			List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
			boolean result=false;
			int count=0;
			String query="";
			try {
				if("1".equals(productId)){
				 query="GET_SIGN_SHARE_EQUAL_PRODUCT1";
				}else{
					query="GET_SIGN_SHARE_EQUAL_PRODUCT23";
				}
				list=queryImpl.selectList(query, new String[]{leaderUnderwriterShare,proposalNo});
				if (!CollectionUtils.isEmpty(list)) {
					count = Integer.valueOf(list.get(0).get("COUNT") == null ? ""
							: list.get(0).get("COUNT").toString());
				}
			
				if(count==0){
					result=true;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			return result;

		}

	



//@Override
//	public GetCommonDropDownRes getCeaseaccountStatus(String ContractNo) {
//		// TODO Auto-generated method stub
//		return null;
//	}

@Override
public GetCommonDropDownRes getCrestaIDList(String branchCode, String crestaValue) {
	GetCommonDropDownRes response = new GetCommonDropDownRes();
	try{
		List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
		String query="GET_CRESTAID_LIST";
		list=queryImpl.selectList(query,new String[]{branchCode,StringUtils.isBlank(crestaValue)?"":crestaValue});

	for(int i=0;i<list.size();i++)	{
		CommonResDropDown range=new CommonResDropDown();  
		Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
		range.setCode(tempMap.get("CRESTA_ID")==null?"":tempMap.get("CRESTA_ID").toString());
		range.setCodeDescription(tempMap.get("CRESTA_NAME")==null?"":tempMap.get("CRESTA_NAME").toString());
		reslist.add(range);		
	}
    response.setCommonResponse(reslist);
	response.setMessage("Success");
	response.setIsError(false);
}
	catch(Exception e){
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	return response;
}

@Override
public GetCommonDropDownRes getCrestaNameList(String branchCode, String crestaValue)
{
	
	GetCommonDropDownRes response = new GetCommonDropDownRes();
	try
	{

		List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();

		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	

	String query="GET_CRESTA_NAME_LIST";
		list=queryImpl.selectList(query,new String[]{branchCode,StringUtils.isBlank(crestaValue)?"":crestaValue});
		

		for(int i=0;i<list.size();i++)
		{
			CommonResDropDown range=new CommonResDropDown();  

			Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
			range.setCode(tempMap.get("CRESTA_ID")==null?"":tempMap.get("CRESTA_ID").toString());
			range.setCodeDescription(tempMap.get("CRESTA_NAME")==null?"":tempMap.get("CRESTA_NAME").toString());
			reslist.add(range);		
		}
		   response.setCommonResponse(reslist);
			response.setMessage("Success");
			response.setIsError(false);
			
		}
	
	catch(Exception e)
	{
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}

	return response;
}

@Override
public GetCommonValueRes getCeaseaccountStatus(String ContractNo)
{
	GetCommonValueRes response = new GetCommonValueRes();
	String ceaseStatus="";
	int count=0;
	try
	{
		
	String query="GET_SELECT_ORDER";
	
	List<Map<String,Object>>list=queryImpl.selectList(query, new String[] {ContractNo, ContractNo});
	
	if(list!=null && list.size()>0){
		for(int i=0;i<list.size();i++)
		{
			Map<String,Object> map=list.get(i);
			if(!((map.get("NETDUE_OC")==null?"":map.get("NETDUE_OC").toString()).equalsIgnoreCase((map.get("ALLOCATED_TILL_DATE")==null?"":map.get("ALLOCATED_TILL_DATE").toString())))){
				count=count+1;
			}
					}
	}
	if(count>0){
	ceaseStatus="N";
	}

	 response.setCommonResponse(ceaseStatus);
		response.setMessage("Success");
		response.setIsError(false);
		
	}

catch(Exception e)
{
	e.printStackTrace();
	response.setMessage("Failed");
	response.setIsError(true);
}

return response;
}



@Override
public GetCommonDropDownRes getUnderWritterDropDown(String branchCode, String attachedUW) {
	List<CommonResDropDown> reslist = new ArrayList<CommonResDropDown>();

	List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();	
	GetCommonDropDownRes response = new GetCommonDropDownRes();
	try
	{
     if(StringUtils.isNotBlank(attachedUW) && !"ALL".equalsIgnoreCase(attachedUW)) {
			String query="GET_UNDERWRITER_ATTACHED";
			
			list=queryImpl.selectList(query,new String[]{branchCode,"Y",attachedUW});
			for(int i=0;i<list.size();i++)
			{
				CommonResDropDown range=new CommonResDropDown();  

				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				range.setCode(tempMap.get("UWR_CODE")==null?"":tempMap.get("UWR_CODE").toString());
				range.setCodeDescription(tempMap.get("UNDERWRITTER")==null?"":tempMap.get("UNDERWRITTER").toString());
				reslist.add(range);		
			}}
		
		else 
		{
			
			String query="common.select.getUWList";

			
			list=queryImpl.selectList(query,new String[]{branchCode,"Y"});
			
			
			for(int i=0;i<list.size();i++)
			{
				CommonResDropDown range=new CommonResDropDown();  

				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				range.setCode(tempMap.get("UWR_CODE")==null?"":tempMap.get("UWR_CODE").toString());
				range.setCodeDescription(tempMap.get("UNDERWRITTER")==null?"":tempMap.get("UNDERWRITTER").toString());
				reslist.add(range);		
			}
			
			
		}
	
	
	response.setCommonResponse(reslist);
	response.setMessage("Success");
	response.setIsError(false);
	
}

catch(Exception e)
{
e.printStackTrace();
response.setMessage("Failed");
response.setIsError(true);
}

return response;
}

@Override
public GetCommonValueRes getCrestaName(String branchCode, String crestaValue) {

	String crestaName="";
	GetCommonValueRes response = new GetCommonValueRes();
	try
	{

		
	
		String query="GET_CRESTA_NAME_LIST";
	List<Map<String,Object>>result=queryImpl.selectList(query,new String[]{branchCode,crestaValue});
	if(result!=null && result.size()>0){
		Map<String,Object> res=result.get(0);
		crestaName=(res.get("CRESTA_ID")==null?"":res.get("CRESTA_NAME").toString());
	}
	response.setCommonResponse(crestaName);
	response.setMessage("Success");
	response.setIsError(false);
}
catch(Exception e){
e.printStackTrace();
response.setMessage("Failed");
response.setIsError(true);
}
return response;
}

@Override
public GetCommonValueRes getDisableStatus(String contractNo, String layerNo) {
	GetCommonValueRes response =new GetCommonValueRes();
	String status="Y";
	if(StringUtils.isBlank(contractNo)){
		contractNo="";
	}
	try {
		String query = "GET_DISABLE_STATUS";
		List<Map<String,Object>>list=queryImpl.selectList(query,new String[]{contractNo,layerNo});
		if(list!=null && list.size()>0){
			Map<String,Object> res=list.get(0);
			status=(res.get("STATUS")==null?"":res.get("STATUS").toString());
		}
	response.setCommonResponse(status);
	response.setMessage("Success");
	response.setIsError(false);
}
catch(Exception e){
e.printStackTrace();
response.setMessage("Failed");
response.setIsError(true);
}
return response;
}

@Override
public GetCommonValueRes getCopyQuote(GetCopyQuoteReq req) {
	GetCommonValueRes response = new GetCommonValueRes();
	String newProposalNo="";
	Connection con = null;
	//CallableStatement cstmt = null;
	 StoredProcedureQuery cstmt = null;
	try{
//	con = queryImpl.getDataSource().getConnection();
//	
//	cstmt = con.prepareCall("{CALL copyquote(?,?,?,?,?)}");
//	cstmt.setString(1, type);
//	cstmt.setString(2, "");
//	cstmt.setString(3, productId);
//	cstmt.setString(4, branchCode);
//	cstmt.registerOutParameter(5, java.sql.Types.VARCHAR);
//	cstmt.setString(5, proposalNo);
//	boolean count = cstmt.execute();
//	newProposalNo = cstmt.getString(5);
	response.setCommonResponse(newProposalNo);
	response.setMessage("Success");
	response.setIsError(false);
}
catch(Exception e){
e.printStackTrace();
response.setMessage("Failed");
response.setIsError(true);
}
return response;
}

@Override
public GetCommonValueRes getAllocationDisableStatus(String contractNo, String layerNo) {
	GetCommonValueRes response = new GetCommonValueRes();
	String status = "Y";
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	try {
		if (StringUtils.isBlank(contractNo)) {
			contractNo = "";
		}
		String query ="GET_ALLOCATION_DISABLE_STATUS";
		list=queryImpl.selectList(query, new String[]{contractNo,layerNo});
		if (!CollectionUtils.isEmpty(list)) {
			status = list.get(0).get("Status") == null ? "": list.get(0).get("Status").toString();
		}
	response.setCommonResponse(status);
	response.setMessage("Success");
	response.setIsError(false);
	} catch(Exception e){
		log.error(e);
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
		return response;
	}

	@Override
	public CommonResponse updatepositionMasterEndtStatus(String proposalNo, String endtDate,String ceaseStatus) {
		CommonResponse response = new CommonResponse();
		try {
			String query = "UPDATE_POSITION_MASTER_END_STATUS";
			queryImpl.updateQuery(query, new String[]{endtDate,ceaseStatus,proposalNo});
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}

	@Override
	public GetCommonDropDownRes getYearListValue(String inceptionDate) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> yearsList = new ArrayList<CommonResDropDown>();
		
		String format = "dd/MM/yyyy";
		try {
			if (StringUtils.isNotBlank(inceptionDate)) {
			  
				SimpleDateFormat sdf = new SimpleDateFormat(format);
				Date dateAsObj = null;
				dateAsObj = sdf.parse(inceptionDate);
				
				Calendar cal = Calendar.getInstance();
				cal.setTime(dateAsObj);
				
				// cal.add(Calendar.MONTH, nbMonths);
				int year = cal.get(Calendar.YEAR);
				int month = cal.get(Calendar.MONTH);
				int day = cal.get(Calendar.DATE);
				
					CommonResDropDown res = new CommonResDropDown();

					res.setCode(String.valueOf(year));
					res.setCodeDescription(String.valueOf(year));
					yearsList.add(res);

				if (month == 11) {
					if (day >= 25) {
						cal.add(Calendar.YEAR, 1);
						year = cal.get(Calendar.YEAR);

						res.setCode(String.valueOf(year));
						res.setCodeDescription(String.valueOf(year));
						yearsList.add(res);

					}
				} else if (month == 0) {
					if (7 >= day) {
						cal.add(Calendar.YEAR, -1);
						year = cal.get(Calendar.YEAR);
						res.setCode(String.valueOf(year));
						res.setCodeDescription(String.valueOf(year));
						yearsList.add(res);
					}
				}

			}

			response.setCommonResponse(yearsList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}

	@Override
	public CommonResponse updateInstallmentTransaction(String proposalNo) {
	CommonResponse response = new CommonResponse();
	try {
			String query = "UPDATE_INSTALLMENT_TRANSACTION";
			queryImpl.updateQuery(query, new String[] { proposalNo});
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;

	}

	@Override
	public GetCommonDropDownRes getBonusList() {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();

		List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
		try {

			String query = "GET_BONUS_LIST";

			result = queryImpl.selectList(query, new String[] { "23", "Y" });

			for (int i = 0; i < result.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) result.get(i);
				res.setCode(tempMap.get("DETAIL_NAME") == null ? "" : tempMap.get("DETAIL_NAME").toString());
				res.setCodeDescription(tempMap.get("REMARKS") == null ? "" : tempMap.get("REMARKS").toString());
				resList.add(res);
			}

			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}

		catch (Exception e) {

			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);

		}
		return response;
	}

	@Override
	public GetCommonDropDownRes getConstantDropDownET(String categoryId, String contractNo) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> constantList = new ArrayList<CommonResDropDown>();
		
		String query = "";
		String transNo = "";
		String contNo = "";
		try {
			query = "common.select.getConstDet";
			list = queryImpl.selectList(query, new String[] { categoryId, "Y" });

			query = "GET_CONSTANT_DROPDOWN_ET";
			list = queryImpl.selectList(query, new String[] { contractNo });
			if (list.size() > 0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
					transNo = tempMap.get("TRANSACTION_NO") == null ? "" : tempMap.get("TRANSACTION_NO").toString();
					contNo = tempMap.get("CONTRACT_NO") == null ? "" : tempMap.get("CONTRACT_NO").toString();
				}
			}
			if (transNo.equalsIgnoreCase(contNo) && contractNo != null && contractNo != "") {
				for (int i = 0; i < list.size(); i++) {
					CommonResDropDown res = new CommonResDropDown();
					Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
					res.setCode(tempMap.get("DETAIL_NAME") == null ? "": tempMap.get("DETAIL_NAME").toString());
					res.setCodeDescription(tempMap.get("TYPE") == null ? "": tempMap.get("TYPE").toString());
					constantList.add(res);
				}
			}
			response.setCommonResponse(constantList);
			response.setMessage("Success");
			response.setIsError(false);
		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}

	return response;
	}

	@Override
	public GetCommonDropDownRes getCoverDEpartmentDropDown(String branchCode, String pid, String departId) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> department = new ArrayList<CommonResDropDown>();
		String query="";
		try{
			if("17".equalsIgnoreCase(departId) || "18".equalsIgnoreCase(departId) ||"19".equalsIgnoreCase(departId) ){
				query="GET_COVER_DEPT_LIST";
				list = queryImpl.selectList(query,new String[]{departId,branchCode,pid});
			}else{
				 list=getDepartmentDropDown(branchCode,pid,"Y","","","","","");
			}
	
			 for(int i=0 ; i<list.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					res.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
					res.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
					department.add(res);
				}
			response.setCommonResponse(department);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}
	public 	List<Map<String,Object>> getDepartmentDropDown(String branchCode,String productCode,String status, String contNo, String endt, String proposalNo,String mode, String baseLayer){
		
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> department = new ArrayList<CommonResDropDown>();
		String query="";
		try{

			if (productCode.equalsIgnoreCase("")) {
				query = "common.select.departlist.premium";
				list = queryImpl.selectList(query, new String[] { branchCode, status });
			} else if(!StringUtils.isBlank(baseLayer) ) { 
				query = "common.select.getDepartmentList3";
				list = queryImpl.selectList(query, new String[] { branchCode,productCode,status,baseLayer,baseLayer,proposalNo });
			}else if(StringUtils.isBlank(proposalNo) && StringUtils.isBlank(contNo)){
				 query = "common.select.getDepartmentList";
				 list=queryImpl.selectList(query,new String[]{branchCode,productCode,status});	
			}else if(StringUtils.isBlank(contNo) && !StringUtils.isBlank(proposalNo)){
				query = "common.select.getDepartmentList2";
				list = queryImpl.selectList(query,new String[] { branchCode, productCode, status, proposalNo, baseLayer, baseLayer });
			}else if(!StringUtils.isBlank(contNo) && StringUtils.isBlank(proposalNo)){
				query = "common.select.getDepartmentList1";
				list = queryImpl.selectList(query,new String[] { branchCode,productCode,status,contNo,contNo });
			}else if(!StringUtils.isBlank(contNo)){
				query = "common.select.getDepartmentList1";
				list = queryImpl.selectList(query,new String[] { branchCode,productCode,status,contNo,contNo });
			}
			
			 for(int i=0 ; i<list.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					res.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
					res.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
					department.add(res);
				}
			
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				
		}

		return list;
	}


	@Override
	public GetCommonDropDownRes getProductieModuleDropDown(GetProductieModuleDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> productList = new ArrayList<CommonResDropDown>();
		try {
			String  query="";
			query="common.select.getPRoductListIE";
			
			if(req.getMode().equals("edit")){
				 query="GET_PRODUCTION_MODULE_DROPDOWN";
				 list=queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getProposalNo(),req.getTransactionNo(),req.getType(),req.getTransactionNo()});
				
			}else{
				list=queryImpl.selectList(query,new String[]{req.getBranchCode()});
			}
			for (int i = 0; i < list.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				res.setCode(tempMap.get("TMAS_PRODUCT_ID") == null ? "" : tempMap.get("TMAS_PRODUCT_ID").toString());
				res.setCodeDescription(tempMap.get("TMAS_PRODUCT_NAME") == null ? "": tempMap.get("TMAS_PRODUCT_NAME").toString());
				productList.add(res);
			}
			response.setCommonResponse(productList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}

	@Override
	public GetCommonDropDownRes getInwardBusinessTypeDropDown(GetInwardBusinessTypeDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> constantList = new ArrayList<CommonResDropDown>();
		try {
			String query="";
			query="common.select.getConstDetie";
			
			
			if(req.getMode().equals("edit")){
				 query="GET_INWARD_BUSINESS_TYPE_DROPDOWN";
				 list=queryImpl.selectList(query,new String[]{req.getCategoryId(),"Y",req.getProposalNo(),req.getTransactionNo(),req.getType(),req.getTransactionNo()});
				
			}else{
				list=queryImpl.selectList(query,new String[]{req.getCategoryId(),"Y"});
			}
			for (int i = 0; i < list.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				res.setCode(tempMap.get("TYPE") == null ? "" : tempMap.get("TYPE").toString());
				res.setCodeDescription(tempMap.get("DETAIL_NAME") == null ? "": tempMap.get("DETAIL_NAME").toString());
				constantList.add(res);
			}
			response.setCommonResponse(constantList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}

	@Override
	public GetCommonDropDownRes getTreatyTypeDropDown(GetTreatyTypeDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> constantList = new ArrayList<CommonResDropDown>();
		try {
			String query="";  
			query="common.select.getConstDetie1";
			
			
			if(req.getMode().equals("edit")){
				 query="GET_TREATY_TYPE_DROPDOWN";
				 list=queryImpl.selectList(query,new String[]{req.getCategoryId(),"Y",req.getProposalNo(),req.getTransactionNo(),req.getType(),req.getTransactionNo()});
				
			}else{
				list=queryImpl.selectList(query,new String[]{req.getCategoryId(),"Y",});
			}
			for (int i = 0; i < list.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				res.setCode(tempMap.get("TYPE") == null ? "" : tempMap.get("TYPE").toString());
				res.setCodeDescription(tempMap.get("DETAIL_NAME") == null ? "": tempMap.get("DETAIL_NAME").toString());
				constantList.add(res);
			}
			response.setCommonResponse(constantList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}

	@Override
	public GetCommonDropDownRes getProfitCentreieModuleDropDown(GetProfitCentreieModuleDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> profitCenterList = new ArrayList<CommonResDropDown>();
		try {
			String query=""; 
			query="common.select.getProfitCenterListIE";
			
			
			if(req.getMode().equals("edit")){
				query="GET_PROFIT_CENTREIE_MODULE_DROPDOWN";
				 list=queryImpl.selectList(query,new String[]{req.getBranchCode(),"Y",req.getProposalNo(),req.getTransactionNo(),req.getType(),req.getTransactionNo()});
				
			}else{
				list=queryImpl.selectList(query,new String[]{req.getBranchCode(),"Y"});
			}
			for (int i = 0; i < list.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				res.setCode(tempMap.get("TMAS_PFC_ID") == null ? "" : tempMap.get("TMAS_PFC_ID").toString());
				res.setCodeDescription(tempMap.get("TMAS_PFC_NAME") == null ? "": tempMap.get("TMAS_PFC_NAME").toString());
				profitCenterList.add(res);
			}
			response.setCommonResponse(profitCenterList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}

	@Override
	public GetCommonDropDownRes getDepartmentieModuleDropDown(GetDepartmentieModuleDropDownReq req) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> departmentList = new ArrayList<CommonResDropDown>();
		try {
			String query=""; 
			query="common.select.getDepartListIE";
			
			
			if(req.getMode().equals("edit")){
				query="GET_DEPARTMENTIE_MODULE_DROPDOWN";
				 list=queryImpl.selectList(query,new String[]{req.getBranchCode(),"Y",req.getProposalNo(),req.getTransactionNo(),req.getType(),req.getTransactionNo()});
				
			}else{
				list=queryImpl.selectList(query,new String[]{req.getBranchCode(),"Y"});
			}
			for (int i = 0; i < list.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				res.setCode(tempMap.get("TMAS_DEPARTMENT_ID") == null ? "" : tempMap.get("TMAS_DEPARTMENT_ID").toString());
				res.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME") == null ? "": tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				departmentList.add(res);
			}
			response.setCommonResponse(departmentList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;

	}

	@Override
	public GetCommonDropDownRes getProposalStatus(String excludeProposalNo) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> proposalList = new ArrayList<CommonResDropDown>();
		String query="";
		try{
			excludeProposalNo = excludeProposalNo.trim();
			excludeProposalNo = excludeProposalNo.replaceAll(",", "','");
			query="GET_PROPO_STATUS";
			list=queryImpl.selectList(query,new String[]{excludeProposalNo});
			if(!(list==null)&&list.size()>0) {
			 for(int i=0 ; i<list.size() ; i++) {
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					res.setCode(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
					res.setCodeDescription(tempMap.get("CONTRACT_STATUS")==null?"":tempMap.get("CONTRACT_STATUS").toString());
					proposalList.add(res);
				}
			}
			response.setCommonResponse(proposalList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}
		return response;
	}

	@Override
	public GetCommonValueRes getExcludeProposalNo(String branchCode, String proposalNo, String transactionNo) {
		
		GetCommonValueRes response = new GetCommonValueRes();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		
		String result =" ";
		try {
		String query="GET_EXCLUDE_PROPOSAL";
		String args[] = new String[3];
		args[0] =branchCode;
		args[1] =proposalNo;
		args[2] =transactionNo;
		list=queryImpl.selectList(query,args);
		int j= list.size()-1;
		for(int i=0;i<list.size();i++){
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				if(i==j){
					result += tempMap.get("ITEM_TYPE")==null?"":tempMap.get("ITEM_TYPE").toString();
				
				}else{
					result += tempMap.get("ITEM_TYPE")==null?"":tempMap.get("ITEM_TYPE").toString()+",";
				}
			}
		
		response.setCommonResponse(result);
		response.setMessage("Success");
		response.setIsError(false);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
	return response;
	}

	@Override
	public GetCommonDropDownRes getConstantDropDownBusinessType(String categoryId,  String deptId) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> departmentList = new ArrayList<CommonResDropDown>();
		try {
			String query=""; 
			
			if(!"16".equalsIgnoreCase(deptId)){
				query="common.select.getConstDet";
			}else{
				query="GET_CONSTANT_DROPDOWN_BUSINESS_TYPE1";
			}
		
			list=queryImpl.selectList(query,new String[]{categoryId,"Y"});
			for (int i = 0; i < list.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				res.setCode(tempMap.get("TYPE") == null ? "" : tempMap.get("TYPE").toString());
				res.setCodeDescription(tempMap.get("DETAIL_NAME") == null ? "": tempMap.get("DETAIL_NAME").toString());
				departmentList.add(res);
			}
			response.setCommonResponse(departmentList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}

	@Override
	public GetCommonDropDownRes getConstantDropDownBasis(String categoryId, String deptId) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> constantList = new ArrayList<CommonResDropDown>();
		try {
			String query=""; 
			query="COMMON_SELECT_GETCONSTDET_BASIC";
		
			if(!"16".equalsIgnoreCase(deptId)){
				query="GET_CONSTANT_DROPDOWN_BASIS";
			}
		
			list=queryImpl.selectList(query,new String[]{categoryId,"Y"});
			for (int i = 0; i < list.size(); i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				res.setCode(tempMap.get("TYPE") == null ? "" : tempMap.get("TYPE").toString());
				res.setCodeDescription(tempMap.get("DETAIL_NAME") == null ? "": tempMap.get("DETAIL_NAME").toString());
				constantList.add(res);
			}
			response.setCommonResponse(constantList);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		}

		return response;
	}

	@Override
	public int DuplicateCountCheck(DuplicateCountCheckReq req) {
	int count=0;
	List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
	try {
		String query = "DUPLICATE_COUNT_CHECK";
		String args[] = new String[4];
		args[0]=req.getUwYear();
		args[1]=req.getBranchCode();
		args[2]=req.getPid();
		args[3]=req.getType();
		if (StringUtils.isNotBlank(req.getProposalNo())) {
			args = new String[5];
			args[0]=req.getUwYear();
			args[1]=req.getBranchCode();
			args[2]=req.getPid();
			args[3]=req.getType();
			args[4]=req.getProposalNo();
			query ="DUPLICATE_COUNT_CHECK1";
		}
		list=queryImpl.selectList(query,args);
	
		for (int i = 0; i < list.size(); i++) {
			if (!CollectionUtils.isEmpty(list)) {
				count =Integer.valueOf((list.get(0).get("PROPOSAL_NO") == null ? "": list.get(0).get("PROPOSAL_NO").toString()));
			}
		}
	} catch (Exception e) {
		e.printStackTrace();
		log.debug("Exception @ {" + e + "}");
	}
	return count;
}

	@Override
	public GetCommonDropDownRes getCountryDate(String branchCode, String DEC_FILE) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		List<CommonResDropDown> result = new ArrayList<CommonResDropDown>();
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		try{
			String query = "GET_CRESTA_DATE";
			
			list = queryImpl.selectList(query,new String[]{branchCode,branchCode});
			XSSFWorkbook workbook = new XSSFWorkbook();
		    XSSFSheet sheet = workbook.createSheet("Master");
		    
		    Map<String, Object[]> data = new TreeMap<String, Object[]>();
		    data.put("1", new Object[] {"Territory Code", "Cresta ID", "Cresta Name"});
		    int j=2;
		    for(int i=0;i<list.size();i++){
		    	Map<String,Object> map =list.get(i);
		    	data.put(Integer.toString(j), new Object[]{map.get("COUNTRY_NAME")==null?"":map.get("COUNTRY_NAME"),map.get("CRESTA_ID")==null?"":map.get("CRESTA_ID"),map.get("CRESTA_NAME")==null?"":map.get("CRESTA_NAME")});
		    	j++;
		    }
		    Set<String> keyset = data.keySet();
		    int rownum = 0;
		    for (String key : keyset)
		    {
		        XSSFRow row = sheet.createRow(rownum++);
		        Object [] objArr = data.get(key);
		        int cellnum = 0;
		        for (Object obj : objArr)
		        {
		           XSSFCell cell = row.createCell(cellnum++);
		           if(obj instanceof String)
		                cell.setCellValue((String)obj);
		            else if(obj instanceof Integer)
		                cell.setCellValue((Integer)obj);
		        }
		    }
		    XSSFSheet sheet1 = workbook.createSheet("Import");
		    Map<String, Object[]> data1 = new TreeMap<String, Object[]>();
		    data1.put("1", new Object[] {"Territory Code", "Cresta ID", "Currency ","Accumulation Date","Accumulation of Risk"});
		    Set<String> keyset1 = data1.keySet();
		    int rownum1 = 0;
		    for (String key : keyset1)
		    {
		        XSSFRow row = sheet1.createRow(rownum1++);
		        Object [] objArr = data1.get(key);
		        int cellnum = 0;
		        for (Object obj : objArr)
		        {
		           XSSFCell cell = row.createCell(cellnum++);
		           if(obj instanceof String)
		                cell.setCellValue((String)obj);
		            else if(obj instanceof Integer)
		                cell.setCellValue((Integer)obj);
		        }
		    }
		    try
		    {
		        FileOutputStream out = new FileOutputStream(new File(DEC_FILE));
		        for (int k = 0; k<  5; k++) {
		        	sheet1.autoSizeColumn(k);
                }
		        for (int l = 0; l < 3; l++) {
		        	sheet.autoSizeColumn(l);
                }
		        workbook.write(out);
		        out.close();
		    }
		    catch (Exception e)
		    {
		        e.printStackTrace();
		    }
		
			for(int i=0 ; i<list.size() ; i++) {
				CommonResDropDown res = new CommonResDropDown();
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				res.setCode(tempMap.get("CRESTA_ID")==null?"":tempMap.get("CRESTA_ID").toString());
				res.setCodeDescription(tempMap.get("CRESTA_NAME")==null?"":tempMap.get("CRESTA_NAME").toString());
				
				result.add(res);
			}
			response.setCommonResponse(result);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		return response;
	}
	public int getSumOfInstallmentBooked(String contractNo,String layerNo) {
		int amount=0;
		try {
			String obj[]=new String[2];
			obj[0]=contractNo;
			obj[1]=layerNo;
			String query="GET_SUM_INSTALLMENT_BOOKED";
			List<Map<String,Object>>	list=queryImpl.selectList(query,obj);
				if (!CollectionUtils.isEmpty(list)) {
					amount =Integer.valueOf((list.get(0).get("MND_PREMIUM_OC") == null ? "": list.get(0).get("MND_PREMIUM_OC").toString()));
				}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return amount;
	}
	public int getCountOfInstallmentBooked(String contractNo,String layerNo) {
		int count=0;
		try {
			String obj[]=new String[2];
			obj[0]=contractNo;
			obj[1]=layerNo;
			String query="GET_COUNT_INSTALLMENT_BOOKED";
			List<Map<String,Object>>	list=queryImpl.selectList(query,obj);
			if (!CollectionUtils.isEmpty(list)) {
				count =Integer.valueOf((list.get(0).get("COUNT") == null ? "": list.get(0).get("COUNT").toString()));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return count;
	}
	public boolean getPLCLCountStatus(String contractNo,String layerNo) {
		boolean  status=false;
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		int plclCount=0;
		try{
			String query="common.select.getPRCLCount";
			list= queryImpl.selectList(query,new String[] {contractNo,layerNo,contractNo,layerNo});
			if (!CollectionUtils.isEmpty(list)) {
				plclCount = Integer.valueOf(list.get(0).get("COUNT") == null ? ""
						: list.get(0).get("COUNT").toString());
			}
			
			
			if(plclCount>0)
				status=true;
		}catch(Exception e){
			e.printStackTrace();
		}

		return status;
	}
	public String getRiskComMaxAmendId(final String proposalNo) {
		String result="";
		try{
			List<TtrnRiskCommission> list = rcRepo.findTop1ByRskProposalNumberOrderByRskEndorsementNoDesc(proposalNo);
			if(list.size()>0) {
				result= list.get(0).getRskEndorsementNo()==null?"":list.get(0).getRskEndorsementNo().toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}

		return result;
	}
	public GetContractValRes getContractValidation(ContractReq req) {
		GetContractValRes resp=new GetContractValRes();
		String query="";
		String sumInsured="";
		String surplus="",coverPer="",dedPer="";
		String ContNo=StringUtils.isEmpty(req.getContNo())? "0":req.getContNo();
			List<GetContractValidationRes> resList = new ArrayList<GetContractValidationRes>();
			List<Map<String,Object>> ContractList=new ArrayList<Map<String,Object>>();
			String args[] = null;
			try{
				if(StringUtils.isNotBlank(req.getCedingCo())&&StringUtils.isNotBlank(req.getIncepDate())&&StringUtils.isNotBlank(req.getExpDate())&&StringUtils.isNotBlank(req.getUwYear())&&StringUtils.isNotBlank(req.getOrginalCurrency())&&StringUtils.isNotBlank(req.getDepartId())&&StringUtils.isNotBlank(req.getTreatyType())&&StringUtils.isNotBlank(req.getProfitCenter())){
					if(StringUtils.isNotBlank(req.getTreatyType())&&("4".equalsIgnoreCase(req.getTreatyType()) || "5".equalsIgnoreCase(req.getTreatyType()))){
							sumInsured =StringUtils.isEmpty(req.getFaclimitOrigCur())? "0":req.getFaclimitOrigCur().replaceAll(",", "");
						}
					else if(StringUtils.isNotBlank(req.getTreatyType())&&"3".equalsIgnoreCase(req.getTreatyType())){
							sumInsured =Double.toString(Double.parseDouble(StringUtils.isEmpty(req.getLimitOrigCur())? "0":req.getLimitOrigCur().replaceAll(",", "")));
							surplus = 	Double.toString(Double.parseDouble(StringUtils.isEmpty(req.getTreatyLimitsurplusOC())? "0":req.getTreatyLimitsurplusOC().replaceAll(",", ""))) ;
						}	
					else if(StringUtils.isNotBlank(req.getTreatyType())&&"1".equalsIgnoreCase(req.getTreatyType())){
						sumInsured = StringUtils.isEmpty(req.getLimitOrigCur())? "0":req.getLimitOrigCur().replaceAll(",", "");
					}
					else if(StringUtils.isNotBlank(req.getTreatyType())&&"2".equalsIgnoreCase(req.getTreatyType())){
						surplus = StringUtils.isEmpty(req.getTreatyLimitsurplusOC())? "0":req.getTreatyLimitsurplusOC().replaceAll(",", "");
					}
					
					
				if(StringUtils.isBlank(sumInsured)){
					sumInsured="0";
				}
				if(StringUtils.isBlank(surplus)){
					surplus="0";
				}
				if(StringUtils.isBlank(coverPer)){
					coverPer="0";
				}
				if(StringUtils.isBlank(dedPer)){
					dedPer="0";
				}
				if(!req.getCedingCo().matches("^[0-9]+$")){
					req.setCedingCo("");
				}
				if("1".equalsIgnoreCase(req.getProductId())){
					query="FAC_CONTRACT_LIST";
					 args = new String[10];
						args[0] = req.getCedingCo();
						args[1] = req.getIncepDate();
						args[2] = req.getExpDate();
						args[3] = req.getUwYear();
						args[4] = req.getOrginalCurrency();
						
						args[5] = req.getDepartId();
						args[6] = req.getTreatyType();
						args[7] = sumInsured;
						args[8] = req.getProfitCenter();
						args[9] = req.getBranchCode();
						if(ContNo!="0"){
						query="FAC_CONTRACT_LIST1";
					}
				}
				else if("2".equalsIgnoreCase(req.getProductId())){
					query="PTTY_CONTRACT_LIST";
					args = new String[9];
					args[0] = req.getCedingCo();
					args[1] = req.getIncepDate();
					args[2] = req.getExpDate();
					args[3] = req.getUwYear();
					args[4] = req.getOrginalCurrency();
					args[5] = req.getDepartId();
					args[6] = req.getTreatyType();
					args[7] = req.getProfitCenter();
					args[8] = req.getBranchCode();
					if("1".equalsIgnoreCase(req.getTreatyType()) ||"4".equalsIgnoreCase(req.getTreatyType())||"5".equalsIgnoreCase(req.getTreatyType()) ){
						//query+="  and RP.RSK_LIMIT_OC="+req.getSumInsured();
						query="PTTY_CONTRACT_LIST1";
					}
					else if("2".equalsIgnoreCase(req.getTreatyType())){
						//query+="  and RP.RSK_TREATY_SURP_LIMIT_OC="+req.getSumInsured();
						query="PTTY_CONTRACT_LIST2";
					}
					else if("3".equalsIgnoreCase(req.getTreatyType())){
						//query+="  and RP.RSK_LIMIT_OC="+req.getSumInsured()+" and RP.RSK_TREATY_SURP_LIMIT_OC = "+req.getSurplus();
						query="PTTY_CONTRACT_LIST3";
					}
					if(ContNo!="0"){
						//query+="  and RD.RSK_CONTRACT_NO!="+req.getContno();
						query="PTTY_CONTRACT_LIST4";
					}
				}  if("3".equalsIgnoreCase(req.getProductId())){
					if(!ContNo.equalsIgnoreCase("0")){
						query="NPTTY_CONTRACT_LIST_CON";
						args = new String[11];
						args[0] = req.getCedingCo();
						args[1] = req.getIncepDate();
						args[2] = req.getExpDate();
						args[3] = req.getUwYear();
						args[4] = req.getOrginalCurrency();
						args[5] = req.getDepartId();
						args[6] = req.getTreatyType();
						args[7] = req.getProfitCenter();
						args[8] = req.getLayerNo();
						args[9] = ContNo;
						args[10] = req.getBranchCode();
					}
					else{
						query="NPTTY_CONTRACT_LIST";
						args = new String[10];
						args[0] = req.getCedingCo();
						args[1] = req.getIncepDate();
						args[2] = req.getExpDate();
						args[3] = req.getUwYear();
						args[4] = req.getOrginalCurrency();
						args[5] = req.getDepartId();
						args[6] = req.getTreatyType();
						args[7] = req.getProfitCenter();
						args[8] = req.getLayerNo();
						args[9] = req.getBranchCode();
					}
					
					if("1".equalsIgnoreCase(req.getTreatyType()) ||"2".equalsIgnoreCase(req.getTreatyType())||"3".equalsIgnoreCase(req.getTreatyType())||"4".equalsIgnoreCase(req.getTreatyType())||"5".equalsIgnoreCase(req.getTreatyType()) ){
						args = new String[13];
						args[0] = req.getCedingCo();
						args[1] = req.getIncepDate();
						args[2] = req.getExpDate();
						args[3] = req.getUwYear();
						args[4] = req.getOrginalCurrency();
						args[5] = req.getDepartId();
						args[6] = req.getTreatyType();
						args[7] = req.getProfitCenter();
						args[8] = req.getLayerNo();
						args[9] = ContNo;
						args[10] = req.getBranchCode();
						args[11] = sumInsured;
						args[12] = surplus;
						query="NPTTY_CONTRACT_LIST_CON1";
					}
					else if("5".equalsIgnoreCase(req.getTreatyType())){
						args = new String[14];
						args[0] = req.getCedingCo();
						args[1] = req.getIncepDate();
						args[2] = req.getExpDate();
						args[3] = req.getUwYear();
						args[4] = req.getOrginalCurrency();
						args[5] = req.getDepartId();
						args[6] = req.getTreatyType();
						args[7] = req.getProfitCenter();
						args[8] = req.getLayerNo();
						args[9] = req.getBranchCode();
						args[10] = sumInsured;
						args[11] = surplus;
						args[12] = coverPer;
						args[13] = dedPer;
						
						query="NPTTY_CONTRACT_LIST1";
					}
				}
		
				
				ContractList =queryImpl.selectList(query,args);
			
		
					 for(int i=0 ; i<ContractList.size() ; i++) {
						 GetContractValidationRes res = new GetContractValidationRes();
							Map<String,Object> tempMap = (Map<String,Object>) ContractList.get(i);
							res.setContractNo(tempMap.get("RSK_CONTRACT_NO")==null?"":tempMap.get("RSK_CONTRACT_NO").toString());
							res.setEndoresmentNo(tempMap.get("RSK_ENDORSEMENT_NO")==null?"":tempMap.get("RSK_ENDORSEMENT_NO").toString());
							res.setProposalNo(tempMap.get("RSK_PROPOSAL_NUMBER")==null?"":tempMap.get("RSK_PROPOSAL_NUMBER").toString());
							res.setBrokerId(tempMap.get("RSK_BROKERID")==null?"":tempMap.get("RSK_BROKERID").toString());
							res.setUnderWritter(tempMap.get("RSK_UNDERWRITTER")==null?"":tempMap.get("RSK_UNDERWRITTER").toString());
							res.setInsuredName(tempMap.get("RSK_INSURED_NAME")==null?"":tempMap.get("RSK_INSURED_NAME").toString());
							
							resList.add(res);
						}	
					 resp.setCommonResponse(resList);
					 resp.setMessage("Success");
					 resp.setIsError(false);
				}
		 	}catch(Exception e){
					log.error(e);
					e.printStackTrace();
					resp.setMessage("Failed");
					resp.setIsError(true);
				}
			return resp;
	}
	public boolean getMode(final String proposalNo, int instNo, String endtNo,final int mode) {
		boolean flag=false;
		int count=0;
		try {
			if(mode==1) {
				count = mndRepo.countByProposalNoAndInstallmentNoAndEndorsementNo(proposalNo,new BigDecimal(instNo),new BigDecimal(endtNo));
			}else if(mode==2) {
				count =  idRepo.countByProposalNoAndInsurerNoAndEndorsementNo(proposalNo,new BigDecimal(instNo),new BigDecimal(endtNo));
				}else if(mode==3) {
					count = cessRepo.countByProposalNoAndSnoAndAmendId(proposalNo,new BigDecimal(instNo),new BigDecimal(endtNo));
				}
			if (count==0) {
				flag = true;
			} else {
				flag = false;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	public static String GetACC(final double dround) {
		String valu="0";
			DecimalFormat myFormatter = new DecimalFormat("####.####");
			valu = myFormatter.format(dround);
		return valu;
	}
	public String[] getIncObjectArray(String[] srcObj,String[] descObj){
		final String[] tempObj = new String[srcObj.length];
		System.arraycopy(srcObj, 0, tempObj, 0, srcObj.length);
		srcObj=new String[tempObj.length+descObj.length];
		System.arraycopy(tempObj, 0, srcObj, 0, tempObj.length);
		System.arraycopy(descObj, 0, srcObj, tempObj.length, descObj.length);
		return srcObj;
	}

	public synchronized String getSequencePTRT(String type,String productID,String departmentId,String branchCode, String proposalNo, String trDate){ 
		String seqName="";
		try{
			//select Fn_get_SeqNo(?,?,?,?,?,?) from dual
			String query="select Fn_get_SeqNo(?,?,?,?,?,?) SEQNAME from dual";
			List<Map<String, Object>> list  = queryImpl.selectList(query,new String[]{type,productID,departmentId,branchCode,proposalNo,trDate});
			if (!CollectionUtils.isEmpty(list)) {
				seqName = list.get(0).get("SEQNAME") == null ? ""
						: list.get(0).get("SEQNAME").toString();
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return seqName;
	}

	@Override
	public GetCommonDropDownRes getDeptName(String branchCode) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> relist = new ArrayList<CommonResDropDown>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			String query="common.select.deptName";
			String args [] = new String [1];
			args[0] = branchCode;
			list =queryImpl.selectList(query, args);
			
			for(int i=0 ; i<list.size() ; i++) {
			
				 CommonResDropDown res = new CommonResDropDown();
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					res.setCode(tempMap.get("TMAS_DEPARTMENT_ID")==null?"":tempMap.get("TMAS_DEPARTMENT_ID").toString());
					res.setCodeDescription(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
					relist.add(res);
				}
			response.setCommonResponse(relist);
			response.setMessage("Success");
			response.setIsError(false);
			}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	
	}

	@Override
	public GetYearToListValueRes getYearToListValue(GetYearToListValueReq req) {
		GetYearToListValueRes response = new GetYearToListValueRes();
		List<GetYearToListValueRes1> resList = new ArrayList<GetYearToListValueRes1>();
		try {
		if(StringUtils.isNotBlank(req.getInceptionDate())){
			 String format = "dd/MM/yyyy" ;
		     SimpleDateFormat sdf = new SimpleDateFormat(format) ;
		      Date dateAsObj = null;
		      Date dateAsObj1 = null;
			try {
					dateAsObj = sdf.parse(req.getInceptionDate());
					dateAsObj1 = sdf.parse(req.getExpiryDate());
			} catch (ParseException e) {
					e.printStackTrace();
			}
		    Calendar cal = Calendar.getInstance();
		    cal.setTime(dateAsObj);
		    int year =cal.get(Calendar.YEAR);
		    Calendar cal1 = Calendar.getInstance();
		    cal1.setTime(dateAsObj1);
		    int year1 =cal1.get(Calendar.YEAR);
        
		   for(int j=year;j<=year1;j++){
				GetYearToListValueRes1 res = new GetYearToListValueRes1();
				res.setYear(String.valueOf(j));;
				resList.add(res);
			}
	 }
		 response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public CommonResponse updateBaseLayer(String baseLayer, String proposalNo) {
		CommonResponse response = new CommonResponse();
		try{
			//UPDATE_BASE_LAYER
			PositionMaster list = pmRepo.findByProposalNo(new BigDecimal(proposalNo));
			if(list != null) {
				list.setBaseLayer(baseLayer);
			}
			pmRepo.saveAndFlush(list);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonDropDownRes getPaymentPartnerlist(String branchCode, String cedingId, String brokerId) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try{
			if(StringUtils.isNotBlank(cedingId) && StringUtils.isNotBlank(brokerId)) {
			//GET_PAYMENT_PARTNER_LIST
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
      		CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
      		
      		Root<PersonalInfo> tc = query.from(PersonalInfo.class); 

      		// Select
      		query.multiselect(tc.get("customerId").alias("customerId"),
      				cb.selectCase().when(cb.equal(tc.get("customerType"), "C"), tc.get("companyName")).otherwise(cb.concat(tc.get("firstName"), tc.get("lastName"))).alias("name"))
      				.distinct(true) ;  

      		// MAXAmend ID
      		Subquery<Long> maxAmend = query.subquery(Long.class); 
      		Root<PersonalInfo> pms = maxAmend.from(PersonalInfo.class);
      		maxAmend.select(cb.max(pms.get("amendId")));
      		Predicate a1 = cb.equal(tc.get("branchCode"), pms.get("branchCode"));
      		Predicate a2 = cb.equal(tc.get("customerType"), pms.get("customerType"));
      		Predicate a3 = cb.equal(tc.get("status"), pms.get("status"));
      		Predicate a4 = cb.equal(tc.get("customerId"), pms.get("customerId"));
      		maxAmend.where(a1,a2,a3,a4);
      		
      		//Order By
//			List<Order> orderList = new ArrayList<Order>();
//			orderList.add(cb.asc(cb.selectCase().when(cb.equal(tc.get("customerType"), "C"), tc.get("companyName")).otherwise(cb.concat(tc.get("firstName"), tc.get("lastName")))));
      		
      		// Where
      		Predicate n1 = cb.equal(tc.get("branchCode"), branchCode);
      		Predicate n2 = cb.equal(tc.get("customerType"), "C");
      		Predicate n3 = cb.equal(tc.get("status"), "Y");
      		Predicate n4 = cb.equal(tc.get("customerId"), cedingId);
      		Predicate n5 = cb.equal(tc.get("amendId"), maxAmend);
      		//Order By name (alias name need to check and update)
      		if(!"63".equals(brokerId)){
				//GET_PAYMENT_PARTNER_BR_LIST
      			Predicate n7 = cb.equal(tc.get("customerId"), brokerId);
				Predicate n6 = cb.equal(tc.get("customerType"), "B");
				query.where(n1,n2,n3,n4,n5,n6,n7);
			}else {
      		query.where(n1,n2,n3,n4,n5);
			}
      		
      		// Get Result
      		TypedQuery<Tuple> list = em.createQuery(query);
      		List<Tuple> result = list.getResultList();
			
			//query+="ORDER BY NAME";
      		if(result.size()>0) {
      			for(Tuple data: result) {
      				CommonResDropDown res = new CommonResDropDown();
      				res.setCode(data.get("customerId")==null?"":data.get("customerId").toString());
      				res.setCodeDescription(data.get("name")==null?"": data.get("name").toString());;
      				resList.add(res);
      			}
      			resList.sort(Comparator.comparing(CommonResDropDown :: getCodeDescription));
      		}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}
	@Transactional	
	@Override
	public CommonResponse updateProposalno(String referenceNo, String proposalNo) {
		CommonResponse response = new CommonResponse();
		try{
		//UPDATE_PROPOSAL_SCALE
			CriteriaBuilder cb = this.em.getCriteriaBuilder();
			// create update
			CriteriaUpdate<TtrnBonus> update = cb.createCriteriaUpdate(TtrnBonus.class);
			// set the root class
			Root<TtrnBonus> m = update.from(TtrnBonus.class);
			// set update and where clause
			update.set("proposalNo", new BigDecimal(proposalNo));
			Predicate n1 = cb.equal(m.get("referenceNo"), referenceNo);
			update.where(n1 );
			// perform update
			em.createQuery(update).executeUpdate();
			
			//UPDATE_PROPOSAL_REINS
			CriteriaUpdate<TtrnRip> update1 = cb.createCriteriaUpdate(TtrnRip.class);
			// set the root class
			Root<TtrnRip> tr = update1.from(TtrnRip.class);
			// set update and where clause
			update1.set("proposalNo",  new BigDecimal(proposalNo));
			Predicate m1 = cb.equal(tr.get("referenceNo"), referenceNo);
			update1.where(m1);
			// perform update
			em.createQuery(update1).executeUpdate();
			
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetBouquetListRes getBouquetList(String branchCode) {
		GetBouquetListRes response = new GetBouquetListRes();
		List<GetBouquetListRes1> resList = new ArrayList<GetBouquetListRes1>();
		try{
			//GET_BOUQUET_LIST
			List<PositionMaster> list = pmRepo.findDistinctByBranchCodeAndBouquetNoNotNull(branchCode);
			if(list.size()>0) {
				for(PositionMaster data: list) {
					GetBouquetListRes1 res = new GetBouquetListRes1();
					res.setBouquetNo(data.getBouquetNo()==null?"":data.getBouquetNo().toString());
					resList.add(res);
					}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	public String getSysDate()
	{
	String sysDate="";
	try{
	List<Map<String, Object>> list  = queryImpl.selectSingle("SELECT TO_CHAR(SYSDATE,'DD/MM/YYYY') SYSDT FROM DUAL",new String[]{});
	if (!CollectionUtils.isEmpty(list)) {
	sysDate = list.get(0).get("SYSDT") == null ? ""
	: list.get(0).get("SYSDT").toString();
	}
	}catch(Exception e){
	e.printStackTrace();
	}

	return sysDate;
	}

	@Override
	public GetBouquetExistingListRes getBouquetExistingList(String branchCode, String bouquetNo, String bouquetYN) {
		GetBouquetExistingListRes response = new GetBouquetExistingListRes();
		List<GetBouquetExistingListRes1> resList = new ArrayList<GetBouquetExistingListRes1>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			if(StringUtils.isNotBlank(bouquetNo) && "Y".equals(bouquetYN)) {
			list= queryImpl.selectList("GET_EXISTING_BOUQUET",new String[]{branchCode,bouquetNo});
			if(list.size()>0) {
				for(Map<String,Object> data: list) {
					GetBouquetExistingListRes1 res = new GetBouquetExistingListRes1();
					res.setInsDate(data.get("INS_DATE")==null?"":data.get("INS_DATE").toString()); 
					res.setExpDate(data.get("EXP_DATE")==null?"":data.get("EXP_DATE").toString()); 
					res.setCompanyName(data.get("COMPANY_NAME")==null?"":data.get("COMPANY_NAME").toString()); 
					res.setUwYear(data.get("UW_YEAR")==null?"":data.get("UW_YEAR").toString()); 
					res.setUwYearTo(data.get("UW_YEAR_TO")==null?"":data.get("UW_YEAR_TO").toString()); 
					res.setTreatytype(data.get("TREATYTYPE")==null?"":data.get("TREATYTYPE").toString()); 
					res.setProductId(data.get("PRODUCT_ID")==null?"":data.get("PRODUCT_ID").toString()); 
					res.setBusinessType(data.get("BUSINESS_TYPE")==null?"":data.get("BUSINESS_TYPE").toString()); 
					res.setProposalNo(data.get("PROPOSAL_NO")==null?"":data.get("PROPOSAL_NO").toString()); 
					res.setTreatytype(data.get("TREATY_TYPE")==null?"":data.get("TREATY_TYPE").toString()); 
					res.setRskTreatyid(data.get("RSK_TREATYID")==null?"":data.get("RSK_TREATYID").toString()); 
					res.setPolicyStatus(data.get("POLICY_STATUS")==null?"":data.get("POLICY_STATUS").toString()); 
					res.setExistingShare(data.get("EXISTING_SHARE")==null?"":data.get("EXISTING_SHARE").toString()); 
					res.setBaseLayer(data.get("BASE_LAYER")==null?"":data.get("BASE_LAYER").toString()); 
					res.setSectionNo(data.get("SECTION_NO")==null?"":data.get("SECTION_NO").toString()); 
					res.setLayerNo(data.get("LAYER_NO")==null?"":data.get("LAYER_NO").toString());
					res.setTmasDepartmentName(data.get("TMAS_DEPARTMENT_NAME")==null?"":data.get("TMAS_DEPARTMENT_NAME").toString()); 
					res.setSubClass(data.get("SUB_CLASS")==null?"":data.get("SUB_CLASS").toString());
					res.setOfferNo(data.get("OFFER_NO")==null?"":data.get("OFFER_NO").toString());
					resList.add(res);
					}
			}
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonDropDownRes getStatusDropDown(String branchCode) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try{
			//GET_STATUS_DROP_DOWN
			List<StatusMaster> list = smRepo.findByBranchCodeAndStatus(branchCode,"Y");
			
			if(list.size()>0) {
      			for(StatusMaster data: list) {
      				CommonResDropDown res = new CommonResDropDown();
      				res.setCode(data.getStatusCode()==null?"":data.getStatusCode().toString());
      				res.setCodeDescription(data.getStatusName()==null?"": data.getStatusName().toString());;
      				resList.add(res);
      			}
      		}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonDropDownRes getSubStatusDropDown(String branchCode, String statusCode) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try{
			//GET_SUBSTATUS_DROP_DOWN
			List<SubStatusMaster> list = ssmRepo.findByBranchCodeAndStatusAndStatusCode(branchCode,"Y",statusCode);
			
			if(list.size()>0) {
      			for(SubStatusMaster data: list) {
      				CommonResDropDown res = new CommonResDropDown();
      				res.setCode(data.getSubStatusCode()==null?"":data.getSubStatusCode().toString());
      				res.setCodeDescription(data.getSubStatusName()==null?"": data.getSubStatusName().toString());;
      				resList.add(res);
      			}
      		}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}
    @Transactional
	@Override
	public CommonResponse updateBqEditMode(String proposalNo, String val, String updateProposalNo) {
		CommonResponse response = new CommonResponse();
		try{
			//POS_MAS_BQ_MODE_UPDT
			CriteriaBuilder cb = this.em.getCriteriaBuilder();
			// create update
			CriteriaUpdate<PositionMaster> update = cb.createCriteriaUpdate(PositionMaster.class);
			Root<PositionMaster> m = update.from(PositionMaster.class);
			
			if(!"N".equalsIgnoreCase(val)){
				update.set("editMode", val +"-"+ updateProposalNo);
				}
				else{
					update.set("editMode", val);
				}
			// MAXAmend ID
      		Subquery<Long> maxAmend = update.subquery(Long.class); 
      		Root<PositionMaster> pms = maxAmend.from(PositionMaster.class);
      		maxAmend.select(cb.max(pms.get("amendId")));
      		Predicate a1 = cb.equal(m.get("proposalNo"), pms.get("proposalNo"));
      		maxAmend.where(a1);
			
			Predicate n1 = cb.equal(m.get("bouquetNo"), proposalNo);
			Predicate n2 = cb.equal(m.get("amendId"), maxAmend);
			update.where(n1,n2);
			// perform update
			em.createQuery(update).executeUpdate();
			
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetCommonDropDownRes getPlacedProposalList(GetPlacedProposalListReq bean) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try{
			CriteriaBuilder cb = em.getCriteriaBuilder(); 	
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
      		
      		Root<TtrnRiPlacement> pm = query.from(TtrnRiPlacement.class); 
      		Root<TtrnRiskDetails> rd = query.from(TtrnRiskDetails.class); 
      		
			if(StringUtils.isNotBlank(bean.getBouquetNo())) {
				if("C".equalsIgnoreCase(bean.getPlacementMode())) {
					//GET_PLACED_PROPOSAL_BOUQUET
				
		      		// Select
		      		query.multiselect(pm.get("bouquetNo").alias("CODE")).distinct(true) ;  
		      	//Order By
					List<Order> orderList = new ArrayList<Order>();
					orderList.add(cb.asc(pm.get("bouquetNo")));

		      		// Where
					Predicate n1 = cb.isNotNull(pm.get("bouquetNo"));
		      		Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
		      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
		      		Predicate n4 = cb.equal(pm.get("bouquetNo"), bean.getBouquetNo());
					query.where(n1,n2,n3,n4).orderBy(orderList);
					// Get Result
		      		TypedQuery<Tuple> list = em.createQuery(query);
		      		List<Tuple> result = list.getResultList();
		      		if(result.size()>0) {
		      			for(Tuple data: result) {
		      				CommonResDropDown res = new CommonResDropDown();
		      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
		      				res.setCodeDescription(data.get("CODE")==null?"": data.get("CODE").toString());;
		      				resList.add(res);
		      			}
		      			resList.sort(Comparator.comparing(CommonResDropDown :: getCode));
		      		}
					
				}else {
					//GET_PLACED_PROPOSAL_BOUQUET_SINGLE
					Expression<String> e1 = cb.concat(pm.get("proposalNo"), "(");
					Expression<String> e2 = cb.concat(e1, rd.get("rskTreatyid"));
					Expression<String> e3 = cb.concat(e2, ")");
		      		// Select
		      		query.multiselect(pm.get("proposalNo").alias("CODE"),e3.alias("CODEDESC")).distinct(true) ; 
		      	//Order By
					List<Order> orderList = new ArrayList<Order>();
					orderList.add(cb.asc(pm.get("bouquetNo")));

		      		// Where
					Predicate n1 = cb.isNotNull(pm.get("bouquetNo"));
		      		Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
		      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
		      		Predicate n4 = cb.equal(pm.get("bouquetNo"), bean.getBouquetNo());
					query.where(n1,n2,n3,n4).orderBy(orderList);
					// Get Result
		      		TypedQuery<Tuple> list = em.createQuery(query);
		      		List<Tuple> result = list.getResultList();
		      		if(result.size()>0) {
		      			for(Tuple data: result) {
		      				CommonResDropDown res = new CommonResDropDown();
		      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
		      				res.setCodeDescription(data.get("CODEDESC")==null?"": data.get("CODEDESC").toString());;
		      				resList.add(res);
		      			}
		      			resList.sort(Comparator.comparing(CommonResDropDown :: getCode));
		      		}
				}
				
			
			}else {
				if("C".equalsIgnoreCase(bean.getPlacementMode())) {
					//GET_PLACED_PROPOSAL_BASELAYER
		      		query.multiselect(pm.get("baseProposalNo").alias("CODE")).distinct(true) ; 
		      		//Order By
					List<Order> orderList = new ArrayList<Order>();
					orderList.add(cb.asc(pm.get("baseProposalNo")));
					Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
		      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
		      		Predicate n4 = cb.equal(pm.get("baseProposalNo"), bean.getBaseProposalNo());
		      		Predicate n5 = cb.equal(pm.get("proposalNo"), bean.getBaseProposalNo());
		      		Predicate n6 = cb.or(n4,n5);
		      		query.where(n2,n3,n6).orderBy(orderList);
		      	// Get Result
		      		TypedQuery<Tuple> list = em.createQuery(query);
		      		List<Tuple> result = list.getResultList();
		      		if(result.size()>0) {
		      			for(Tuple data: result) {
		      				CommonResDropDown res = new CommonResDropDown();
		      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
		      				res.setCodeDescription(data.get("CODE")==null?"": data.get("CODE").toString());;
		      				resList.add(res);
		      			}
		      			resList.sort(Comparator.comparing(CommonResDropDown :: getCode));
		      		}
					
				}else {
					//GET_PLACED_PROPOSAL_BASELAYER_SINGLE
					Expression<String> e1 = cb.concat(pm.get("proposalNo"), "(");
					Expression<String> e2 = cb.concat(e1, rd.get("rskTreatyid"));
					Expression<String> e3 = cb.concat(e2, ")");
		      		query.multiselect(pm.get("proposalNo").alias("CODE"),e3.alias("CODEDESC"),
		      				cb.selectCase().when(cb.isNull(pm.get("baseProposalNo")), pm.get("proposalNo")).otherwise(pm.get("baseProposalNo")).alias("baseLayer"))
		      				.distinct(true) ; 
					//Order By
//					List<Order> orderList = new ArrayList<Order>();
//					orderList.add(cb.asc(pm.get("bouquetNo")));
					Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
		      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
		      		Predicate n4 = cb.equal(pm.get("baseProposalNo"), bean.getBaseProposalNo());
		      		Predicate n5 = cb.equal(pm.get("proposalNo"), bean.getBaseProposalNo());
		      		Predicate n6 = cb.or(n4,n5);
		      		query.where(n2,n3,n6);
		      	// Get Result
		      		TypedQuery<Tuple> list = em.createQuery(query);
		      		List<Tuple> result = list.getResultList();
		      		if(result.size()>0) {
		      			for(Tuple data: result) {
		      				CommonResDropDown res = new CommonResDropDown();
		      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
		      				res.setCodeDescription(data.get("CODEDESC")==null?"": data.get("CODEDESC").toString());;
		      				resList.add(res);
		      			}
		      			resList.sort(Comparator.comparing(CommonResDropDown :: getCode));
		      		}
				}
				
			}
			
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public GetNotPlacedProposalListRes getNotPlacedProposalList(GetPlacedProposalListReq bean) {
		GetNotPlacedProposalListRes response = new GetNotPlacedProposalListRes();
		List<GetNotPlacedProposalListRes1> resList = new ArrayList<GetNotPlacedProposalListRes1>();
		try{
			CriteriaBuilder cb = em.getCriteriaBuilder(); 	
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
      		
      		Root<PositionMaster> pm = query.from(PositionMaster.class); 
      		Root<TtrnRiskDetails> rd = query.from(TtrnRiskDetails.class); 
      		
				if(StringUtils.isNotBlank(bean.getBouquetNo())) {
					if("C".equalsIgnoreCase(bean.getPlacementMode())) {
						//GET_NOTPLACED_PROPOSAL_BOUQUET
						
			      		query.multiselect(pm.get("bouquetNo").alias("CODE")).distinct(true) ;  
						
						Subquery<Long> prop = query.subquery(Long.class); 
			      		Root<TtrnRiPlacement> pms = prop.from(TtrnRiPlacement.class);
			      		prop.select((pms.get("proposalNo")));
			      		Predicate a1 = cb.equal(pms.get("bouquetNo"), pm.get("bouquetNo"));
			      		prop.where(a1);
			      	
						Predicate n1 = cb.isNotNull(pm.get("bouquetNo"));
			      		Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
			      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
			      		Predicate n4 = cb.equal(pm.get("bouquetNo"), bean.getBouquetNo());
			      		Predicate n5 = cb.equal(pm.get("contractStatus"), "P");
			    		Expression<String> e0 = pm.get("proposalNo");
			      		Predicate n6 = e0.in(prop).not();
			      		
						query.where(n1,n2,n3,n4,n5,n6);
						
			      		TypedQuery<Tuple> list = em.createQuery(query);
			      		List<Tuple> result = list.getResultList();
			      		if(result.size()>0) {
			      			for(Tuple data: result) {
			      				GetNotPlacedProposalListRes1 res = new GetNotPlacedProposalListRes1();
			      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
			      				res.setCodeDescription(data.get("CODE")==null?"": data.get("CODE").toString());;
			      				resList.add(res);
			      			}
			      			resList.sort(Comparator.comparing(GetNotPlacedProposalListRes1 :: getCode));
			      		}
					}else {
						//GET_NOTPLACED_PROPOSAL_BOUQUET_SINGLE
						Expression<String> e1 = cb.concat(pm.get("proposalNo"), "(");
						Expression<String> e2 = cb.concat(e1, rd.get("rskTreatyid"));
						Expression<String> e3 = cb.concat(e2, ")");
			      		
			      		query.multiselect(pm.get("proposalNo").alias("CODE"),e3.alias("CODEDESC")) ; 
			      	
						List<Order> orderList = new ArrayList<Order>();
						orderList.add(cb.asc(pm.get("productId")));
						orderList.add(cb.asc(pm.get("bouquetNo")));
						orderList.add(cb.asc(pm.get("proposalNo")));
	
						Subquery<Long> prop = query.subquery(Long.class); 
			      		Root<TtrnRiPlacement> pms = prop.from(TtrnRiPlacement.class);
			      		prop.select((pms.get("proposalNo")));
			      		Predicate a1 = cb.equal(pms.get("bouquetNo"), pm.get("bouquetNo"));
			      		prop.where(a1);
			      		
						Predicate n1 = cb.isNotNull(pm.get("bouquetNo"));
			      		Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
			      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
			      		Predicate n4 = cb.equal(pm.get("bouquetNo"), bean.getBouquetNo());
			      		Predicate n5 = cb.equal(pm.get("contractStatus"), "P");
			    		Expression<String> e0 = pm.get("proposalNo");
			      		Predicate n6 = e0.in(prop).not();
						query.where(n1,n2,n3,n4,n5,n6).orderBy(orderList);
					
			      		TypedQuery<Tuple> list = em.createQuery(query);
			      		List<Tuple> result = list.getResultList();
			      		if(result.size()>0) {
			      			for(Tuple data: result) {
			      				GetNotPlacedProposalListRes1 res = new GetNotPlacedProposalListRes1();
			      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
			      				res.setCodeDescription(data.get("CODEDESC")==null?"": data.get("CODEDESC").toString());;
			      				resList.add(res);
			      			}
			      		}
					}
				}else {
					if("C".equalsIgnoreCase(bean.getPlacementMode())) {
						//GET_NOTPLACED_PROPOSAL_BASELAYER
						query.multiselect(pm.get("baseLayer").alias("CODE")).distinct(true) ; 
						
						Subquery<Long> prop = query.subquery(Long.class); 
			      		Root<TtrnRiPlacement> pms = prop.from(TtrnRiPlacement.class);
			      		prop.select((pms.get("proposalNo")));
			      		Predicate a1 = cb.equal(pms.get("baseProposalNo"), pm.get("baseLayer"));
			      		prop.where(a1);
			      		
						Predicate n1 = cb.isNotNull(pm.get("baseLayer"));
						Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
			      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
			      		Predicate n4 = cb.equal(pm.get("baseLayer"), bean.getBaseProposalNo());
			      		Predicate n5 = cb.equal(pm.get("proposalNo"), bean.getBaseProposalNo());
			      		Predicate n6 = cb.or(n4,n5);
			      		Predicate n7 = cb.equal(pm.get("contractStatus"), "P");
			    		Expression<String> e0 = pm.get("proposalNo");
			      		Predicate n8 = e0.in(prop).not();
			      		
			      		query.where(n1,n2,n3,n6,n7,n8);
			    
			      		TypedQuery<Tuple> list = em.createQuery(query);
			      		List<Tuple> result = list.getResultList();
			      		if(result.size()>0) {
			      			for(Tuple data: result) {
			      				GetNotPlacedProposalListRes1 res = new GetNotPlacedProposalListRes1();
			      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
			      				res.setCodeDescription(data.get("CODE")==null?"": data.get("CODE").toString());;
			      				resList.add(res);
			      			}
			      			resList.sort(Comparator.comparing(GetNotPlacedProposalListRes1 :: getCode));
			      		}
					}else {
						//GET_NOTPLACED_PROPOSAL_BASELAYER_SINGLE
						Expression<String> e1 = cb.concat(pm.get("proposalNo"), "(");
						Expression<String> e2 = cb.concat(e1, rd.get("rskTreatyid"));
						Expression<String> e3 = cb.concat(e2, ")");
			      		
			      		query.multiselect(pm.get("proposalNo").alias("CODE"),e3.alias("CODEDESC"),
			      			cb.selectCase().when(cb.isNull(pm.get("baseLayer")), pm.get("proposalNo")).otherwise(pm.get("baseLayer").as(BigDecimal.class)).alias("baseLayer")) ; 
			      	
						List<Order> orderList = new ArrayList<Order>();
						orderList.add(cb.asc(pm.get("productId")));
						orderList.add(cb.asc(pm.get("baseLayer")));
						orderList.add(cb.asc(pm.get("proposalNo")));
	
						Subquery<Long> prop = query.subquery(Long.class); 
			      		Root<TtrnRiPlacement> pms = prop.from(TtrnRiPlacement.class);
			      		prop.select((pms.get("proposalNo")));
			      		Predicate a1 = cb.equal(pms.get("proposalNo"), pm.get("proposalNo"));
			      		prop.where(a1);
			      		
						
			      		Predicate n2 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
			      		Predicate n3 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
			      		Predicate n4 = cb.equal(pm.get("baseLayer"), bean.getBaseProposalNo());
			      		Predicate n5 = cb.equal(pm.get("proposalNo"), bean.getBaseProposalNo());
			      		Predicate n6 = cb.or(n4,n5);
			      		Predicate n7 = cb.equal(pm.get("contractStatus"), "P");
			    		Expression<String> e0 = pm.get("proposalNo");
			      		Predicate n8 = e0.in(prop).not();
			      		
			      		query.where(n2,n3,n6,n7,n8);
					
			      		TypedQuery<Tuple> list = em.createQuery(query);
			      		List<Tuple> result = list.getResultList();
			      		if(result.size()>0) {
			      			for(Tuple data: result) {
			      				GetNotPlacedProposalListRes1 res = new GetNotPlacedProposalListRes1();
			      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
			      				res.setCodeDescription(data.get("CODEDESC")==null?"": data.get("CODEDESC").toString());
			      				res.setBaseLayer(data.get("baseLayer")==null?"": data.get("baseLayer").toString());
			      				resList.add(res);
			      			}
			      		}
					}
					}
				response.setCommonResponse(resList);
				response.setMessage("Success");
				response.setIsError(false);
			}catch(Exception e){
					log.error(e);
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
			return response;
	}

	@Override
	public GetBouquetExistingListRes getBaseLayerExistingList(String branchCode, String baseProposalNo) {
		GetBouquetExistingListRes response = new GetBouquetExistingListRes();
		List<GetBouquetExistingListRes1> resList = new ArrayList<GetBouquetExistingListRes1>();
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		try{
			list= queryImpl.selectList("GET_EXISTING_BASELAYER",new String[]{branchCode,baseProposalNo});
			if(list.size()>0) {
				for(Map<String,Object> data: list) {
					GetBouquetExistingListRes1 res = new GetBouquetExistingListRes1();
					res.setInsDate(data.get("INS_DATE")==null?"":data.get("INS_DATE").toString());  
					res.setExpDate(data.get("EXP_DATE")==null?"":data.get("EXP_DATE").toString());  
					res.setCompanyName(data.get("COMPANY_NAME")==null?"":data.get("COMPANY_NAME").toString());  
					res.setUwYear(data.get("UW_YEAR")==null?"":data.get("UW_YEAR").toString());  
					res.setUwYearTo(data.get("UW_YEAR_TO")==null?"":data.get("UW_YEAR_TO").toString());  
					res.setTreatytype(data.get("TREATYTYPE")==null?"":data.get("TREATYTYPE").toString());  
					res.setProductId(data.get("PRODUCT_ID")==null?"":data.get("PRODUCT_ID").toString());  
					res.setBusinessType(data.get("BUSINESS_TYPE")==null?"":data.get("BUSINESS_TYPE").toString());  
					res.setProposalNo(data.get("PROPOSAL_NO")==null?"":data.get("PROPOSAL_NO").toString());  
					res.setTreatyType1(data.get("TREATY_TYPE")==null?"":data.get("TREATY_TYPE").toString());  
					res.setRskTreatyid(data.get("RSK_TREATYID")==null?"":data.get("RSK_TREATYID").toString());  
					res.setPolicyStatus(data.get("POLICY_STATUS")==null?"":data.get("POLICY_STATUS").toString());  
					res.setExistingShare(data.get("EXISTING_SHARE")==null?"":data.get("EXISTING_SHARE").toString());  
					res.setBaseLayer(data.get("BASE_LAYER")==null?"":data.get("BASE_LAYER").toString());  
					res.setSectionNo(data.get("SECTION_NO")==null?"":data.get("SECTION_NO").toString());  
					res.setLayerNo(data.get("LAYER_NO")==null?"":data.get("LAYER_NO").toString());  
					res.setTmasDepartmentName(data.get("TMAS_DEPARTMENT_NAME")==null?"":data.get("TMAS_DEPARTMENT_NAME").toString());  
					res.setSubClass(data.get("SUB_CLASS")==null?"":data.get("SUB_CLASS").toString());  
					res.setOfferNo(data.get("OFFER_NO")==null?"":data.get("OFFER_NO").toString());  
					resList.add(res);
				}
		}
		response.setCommonResponse(resList);
		response.setMessage("Success");
		response.setIsError(false);
	}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
	return response;
	}

	@Override
	public GetBouquetCedentBrokerInfoRes getBouquetCedentBrokerInfo(String bouquetNo) {
		GetBouquetCedentBrokerInfoRes response = new GetBouquetCedentBrokerInfoRes();
		//doubt, output empty here  , sql dev value 
		try {
			//GET_BOUQUET_CEDENT_BROKER
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<PositionMaster> pm = query.from(PositionMaster.class);
			
			//cedingCompanyName
			Subquery<String> cedingCompanyName = query.subquery(String.class); 
			Root<PersonalInfo> pms = cedingCompanyName.from(PersonalInfo.class);
			cedingCompanyName.select(pms.get("companyName"));
			Predicate a1 = cb.equal( pm.get("cedingCompanyId"), pms.get("customerId"));
			Predicate a2 = cb.equal( pm.get("branchCode"), pms.get("branchCode"));
			Predicate a3 = cb.equal( pms.get("customerType"), "C");
			cedingCompanyName.where(a1,a2,a3);
			
			//brokerName
			Subquery<String> brokerName = query.subquery(String.class); 
			Root<PersonalInfo> pi1 = brokerName.from(PersonalInfo.class);
			
			//select CASE WHEN PI.CUSTOMER_TYPE = 'C' THEN PI.COMPANY_NAME 
			Expression<String> firstName = cb.concat(pi1.get("firstName"), " ");
			brokerName.select(cb.concat(firstName, pi1.get("lastName")));
			
			//maxAmend
			Subquery<Long> maxAmend1 = query.subquery(Long.class); 
			Root<PersonalInfo> pis1 = maxAmend1.from(PersonalInfo.class);
			maxAmend1.select(cb.max(pis1.get("amendId")));
			Predicate c1 = cb.equal( pis1.get("customerId"), pi1.get("customerId"));
			maxAmend1.where(c1);
			
			Predicate d1 = cb.equal( pi1.get("customerType"), "B");
			Predicate d2 = cb.equal( pi1.get("customerId"), pm.get("brokerId"));
			Predicate d3 = cb.equal( pi1.get("branchCode"), pm.get("branchCode"));
			Predicate d4 = cb.equal( pi1.get("amendId"), maxAmend1);
			brokerName.where(d1,d2,d3,d4);

			query.multiselect(pm.get("cedingCompanyId").alias("CEDING_COMPANY_ID"),
					pm.get("brokerId").alias("BROKER_ID"),
					cedingCompanyName.alias("CEDING_COMPANY_NAME"),
					brokerName.alias("BROKER_NAME"),
					pm.get("uwYear").alias("UW_YEAR"),
					pm.get("uwYearTo").alias("UW_YEAR_TO"),
					pm.get("inceptionDate").alias("INCEPTION_DATE"),
					pm.get("expiryDate").alias("EXPIRY_DATE")).distinct(true); 

			//maxamend
			Subquery<Long> amend = query.subquery(Long.class); 
			Root<PositionMaster> s = amend.from(PositionMaster.class);
			amend.select(cb.max(s.get("amendId")));
			Predicate r1 = cb.equal( pm.get("proposalNo"), s.get("proposalNo"));
			Predicate r2 = cb.equal( s.get("contractStatus"), "P");
			amend.where(r1,r2);

			Predicate n1 = cb.equal(pm.get("bouquetNo"), bouquetNo);
			Predicate n2 = cb.equal(pm.get("contractStatus"), "P");
			Predicate n3 = cb.equal(pm.get("amendId"), amend);
			query.where(n1,n2,n3);
			
			TypedQuery<Tuple> res = em.createQuery(query);
			List<Tuple> list = res.getResultList();
			if(list!=null && list.size()>0){
				Tuple data = list.get(0);
				GetBouquetCedentBrokerInfoRes1 bean =new GetBouquetCedentBrokerInfoRes1();
				bean.setCedingCo(data.get("CEDING_COMPANY_ID")==null?"":data.get("CEDING_COMPANY_ID").toString());
				bean.setBroker(data.get("BROKER_ID")==null?"":data.get("BROKER_ID").toString());
				bean.setCedingCompanyName(data.get("CEDING_COMPANY_NAME")==null?"":data.get("CEDING_COMPANY_NAME").toString());
				bean.setBrokerName(data.get("BROKER_NAME")==null?"":data.get("BROKER_NAME").toString());
				bean.setUwYear(data.get("UW_YEAR")==null?"":data.get("UW_YEAR").toString());
				bean.setUwYearTo(data.get("UW_YEAR_TO")==null?"":data.get("UW_YEAR_TO").toString());
				bean.setIncepDate(data.get("INCEPTION_DATE")==null?"":data.get("INCEPTION_DATE").toString());
				bean.setExpDate(data.get("EXPIRY_DATE")==null?"":data.get("EXPIRY_DATE").toString());
				response.setCommonResponse(bean);
			}
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public CommonSaveRes getBouquetCedentBrokercheck(String bouquetNo, String cedingCo, String broker) {
		CommonSaveRes response =new CommonSaveRes();
		boolean result=false;
		//doubt, output empty here  , sql dev value 
		try{
			//GET_BOUQUET_CEDENT_BROKER
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<PositionMaster> pm = query.from(PositionMaster.class);
			
			//cedingCompanyName
			Subquery<String> cedingCompanyName = query.subquery(String.class); 
			Root<PersonalInfo> pms = cedingCompanyName.from(PersonalInfo.class);
			cedingCompanyName.select(pms.get("companyName"));
			Predicate a1 = cb.equal( pm.get("cedingCompanyId"), pms.get("customerId"));
			Predicate a2 = cb.equal( pm.get("branchCode"), pms.get("branchCode"));
			Predicate a3 = cb.equal( pms.get("customerType"), "C");
			cedingCompanyName.where(a1,a2,a3);
			
			//brokerName
			Subquery<String> brokerName = query.subquery(String.class); 
			Root<PersonalInfo> pi1 = brokerName.from(PersonalInfo.class);
			
			//select CASE WHEN PI.CUSTOMER_TYPE = 'C' THEN PI.COMPANY_NAME 
			Expression<String> firstName = cb.concat(pi1.get("firstName"), " ");
			brokerName.select(cb.concat(firstName, pi1.get("lastName")));
			
			//maxAmend
			Subquery<Long> maxAmend1 = query.subquery(Long.class); 
			Root<PersonalInfo> pis1 = maxAmend1.from(PersonalInfo.class);
			maxAmend1.select(cb.max(pis1.get("amendId")));
			Predicate c1 = cb.equal( pis1.get("customerId"), pi1.get("customerId"));
			maxAmend1.where(c1);
			
			Predicate d1 = cb.equal( pi1.get("customerType"), "B");
			Predicate d2 = cb.equal( pi1.get("customerId"), pm.get("brokerId"));
			Predicate d3 = cb.equal( pi1.get("branchCode"), pm.get("branchCode"));
			Predicate d4 = cb.equal( pi1.get("amendId"), maxAmend1);
			brokerName.where(d1,d2,d3,d4);

			query.multiselect(pm.get("cedingCompanyId").alias("CEDING_COMPANY_ID"),
					pm.get("brokerId").alias("BROKER_ID"),
					cedingCompanyName.alias("CEDING_COMPANY_NAME"),
					brokerName.alias("BROKER_NAME"),
					pm.get("uwYear").alias("UW_YEAR"),
					pm.get("uwYearTo").alias("UW_YEAR_TO"),
					pm.get("inceptionDate").alias("INCEPTION_DATE"),
					pm.get("expiryDate").alias("EXPIRY_DATE")).distinct(true); 

			//maxamend
			Subquery<Long> amend = query.subquery(Long.class); 
			Root<PositionMaster> s = amend.from(PositionMaster.class);
			amend.select(cb.max(s.get("amendId")));
			Predicate r1 = cb.equal( pm.get("proposalNo"), s.get("proposalNo"));
			Predicate r2 = cb.equal( s.get("contractStatus"), "P");
			amend.where(r1,r2);

			Predicate n1 = cb.equal(pm.get("bouquetNo"), bouquetNo);
			Predicate n2 = cb.equal(pm.get("contractStatus"), "P");
			Predicate n3 = cb.equal(pm.get("amendId"), amend);
			query.where(n1,n2,n3);
			
			TypedQuery<Tuple> result1 = em.createQuery(query);
			List<Tuple> list = result1.getResultList();
			
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						Tuple map= list.get(i);
						String res=map.get("CEDING_COMPANY_ID")==null?"":map.get("CEDING_COMPANY_ID").toString();
						String res1=map.get("BROKER_ID")==null?"":map.get("BROKER_ID").toString();
						if(!res.equalsIgnoreCase(cedingCo)){
							result=true;
						}else if(!res1.equalsIgnoreCase(broker)){
							result=true;
						}
					}
				}
				response.setResponse(String.valueOf(result));
				response.setMessage("Success");
				response.setIsError(false);
			}catch(Exception e){
					log.error(e);
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
			return response;
	}

	@Override
	public CommonSaveRes gePltDisableStatus(String proposalNo) {
		CommonSaveRes response = new CommonSaveRes();
		String status="N";
		try {
			//GET_PLDISABLE_STATUS
			int count = riPlaceRepo.countByProposalNo(new BigDecimal(proposalNo));
			if(count>0) {
				 status="Y";
			}
			response.setResponse(status);
			response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}

	@Override
	public CommonSaveRes getUWFromTocheck(String bouquetNo, String uwYear, String uwYearTo) {
		CommonSaveRes response = new CommonSaveRes();
		boolean result=false;
		//doubt, output empty here  , sql dev value 
		try{
			//GET_BOUQUET_CEDENT_BROKER
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<PositionMaster> pm = query.from(PositionMaster.class);
			
			//cedingCompanyName
			Subquery<String> cedingCompanyName = query.subquery(String.class); 
			Root<PersonalInfo> pms = cedingCompanyName.from(PersonalInfo.class);
			cedingCompanyName.select(pms.get("companyName"));
			Predicate a1 = cb.equal( pm.get("cedingCompanyId"), pms.get("customerId"));
			Predicate a2 = cb.equal( pm.get("branchCode"), pms.get("branchCode"));
			Predicate a3 = cb.equal( pms.get("customerType"), "C");
			cedingCompanyName.where(a1,a2,a3);
			
			//brokerName
			Subquery<String> brokerName = query.subquery(String.class); 
			Root<PersonalInfo> pi1 = brokerName.from(PersonalInfo.class);
			
			//select CASE WHEN PI.CUSTOMER_TYPE = 'C' THEN PI.COMPANY_NAME 
			Expression<String> firstName = cb.concat(pi1.get("firstName"), " ");
			brokerName.select(cb.concat(firstName, pi1.get("lastName")));
			
			//maxAmend
			Subquery<Long> maxAmend1 = query.subquery(Long.class); 
			Root<PersonalInfo> pis1 = maxAmend1.from(PersonalInfo.class);
			maxAmend1.select(cb.max(pis1.get("amendId")));
			Predicate c1 = cb.equal( pis1.get("customerId"), pi1.get("customerId"));
			maxAmend1.where(c1);
			
			Predicate d1 = cb.equal( pi1.get("customerType"), "B");
			Predicate d2 = cb.equal( pi1.get("customerId"), pm.get("brokerId"));
			Predicate d3 = cb.equal( pi1.get("branchCode"), pm.get("branchCode"));
			Predicate d4 = cb.equal( pi1.get("amendId"), maxAmend1);
			brokerName.where(d1,d2,d3,d4);

			query.multiselect(pm.get("cedingCompanyId").alias("CEDING_COMPANY_ID"),
					pm.get("brokerId").alias("BROKER_ID"),
					cedingCompanyName.alias("CEDING_COMPANY_NAME"),
					brokerName.alias("BROKER_NAME"),
					pm.get("uwYear").alias("UW_YEAR"),
					pm.get("uwYearTo").alias("UW_YEAR_TO"),
					pm.get("inceptionDate").alias("INCEPTION_DATE"),
					pm.get("expiryDate").alias("EXPIRY_DATE")).distinct(true); 

			//maxamend
			Subquery<Long> amend = query.subquery(Long.class); 
			Root<PositionMaster> s = amend.from(PositionMaster.class);
			amend.select(cb.max(s.get("amendId")));
			Predicate r1 = cb.equal( pm.get("proposalNo"), s.get("proposalNo"));
			Predicate r2 = cb.equal( s.get("contractStatus"), "P");
			amend.where(r1,r2);

			Predicate n1 = cb.equal(pm.get("bouquetNo"), bouquetNo);
			Predicate n2 = cb.equal(pm.get("contractStatus"), "P");
			Predicate n3 = cb.equal(pm.get("amendId"), amend);
			query.where(n1,n2,n3);
			
			TypedQuery<Tuple> result1 = em.createQuery(query);
			List<Tuple> list = result1.getResultList();
			
				if(list!=null && list.size()>0){
					for(int i=0;i<list.size();i++){
						Tuple map=list.get(i);
						String res=map.get("UW_YEAR")==null?"":map.get("UW_YEAR").toString();
						String res1=map.get("UW_YEAR_TO")==null?"":map.get("UW_YEAR_TO").toString();
						if(!res.equalsIgnoreCase(uwYear)){
							result=true;
						}else if(!res1.equalsIgnoreCase(uwYearTo)){
							result=true;
						}
					}
				}
				response.setResponse(String.valueOf(result));
				response.setMessage("Success");
				response.setIsError(false);
			}catch(Exception e){
					log.error(e);
					e.printStackTrace();
					response.setMessage("Failed");
					response.setIsError(true);
				}
			return response;
	}
	@Transactional
	@Override
	public GetNewContractInfoRes getNewContractInfo(String branchCode, String proposalNo) {
		List<Map<String,Object>> list=new ArrayList<Map<String,Object>>();
		GetNewContractInfoRes response = new GetNewContractInfoRes();
		List<GetNewContractInfoRes1> resList = new ArrayList<GetNewContractInfoRes1>();
		try{
			list= queryImpl.selectList("NEW_CONTRACT_SUMMARY", new String[]{branchCode,proposalNo});
			if(list != null && list.size()>0) {
				for(Map<String,Object> data: list) {
					GetNewContractInfoRes1 res = new GetNewContractInfoRes1();
					 res.setOfferNo(data.get("OFFER_NO")==null?"":data.get("OFFER_NO").toString()); 
					 res.setBaseProposal(data.get("BASE_PROPOSAL")==null?"":data.get("BASE_PROPOSAL").toString()); 
					 res.setProposalNo(data.get("PROPOSAL_NO")==null?"":data.get("PROPOSAL_NO").toString()); 
					 res.setTreatyName(data.get("TREATY_NAME")==null?"":data.get("TREATY_NAME").toString()); 
					 res.setLayerSection(data.get("LAYER_SECTION")==null?"":data.get("LAYER_SECTION").toString()); 
					 res.setSno(data.get("SNO")==null?"":data.get("SNO").toString()); 
					 res.setReinsurerName(data.get("REINSURER_NAME")==null?"":data.get("REINSURER_NAME").toString()); 
					  res.setBrokerName(data.get("BROKER_NAME")==null?"":data.get("BROKER_NAME").toString()); 
					  res.setCurrency(data.get("CURRENCY")==null?"":data.get("CURRENCY").toString()); 
					  res.setEpi100Oc(data.get("EPI_100_OC")==null?"":data.get("EPI_100_OC").toString()); 
					  res.setEpi100Dc(data.get("EPI_100_DC")==null?"":data.get("EPI_100_DC").toString()); 
					  res.setPlacingStatus(data.get("PLACING_STATUS")==null?"":data.get("PLACING_STATUS").toString()); 
					  res.setShareSigned(data.get("SHARE_SIGNED")==null?"":data.get("SHARE_SIGNED").toString()); 
					  res.setBrokerage(data.get("BROKERAGE")==null?"":data.get("BROKERAGE").toString()); 
					  res.setBrokerageAmt(data.get("BROKERAGE_AMT")==null?"":data.get("BROKERAGE_AMT").toString()); 
					 resList.add(res);
					 }
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
		response.setIsError(false);
	}catch(Exception e){
			log.error(e);
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
	return response;
	}

	@Override
	public GetPlacementInfoListRes getPlacementInfoList(String branchCode, String layerProposalNo) {
		GetPlacementInfoListRes  response = new GetPlacementInfoListRes();
		List<Map<String,Object>>list=null;
		List<GetPlacementInfoListRes1> resList = new ArrayList<GetPlacementInfoListRes1>();
		try {
			list= queryImpl.selectList("GET_REINSURER_INFO", new String[]{branchCode,layerProposalNo});
			if(!CollectionUtils.isEmpty(list)) {
				for(int i=0;i<list.size();i++) {
					Map<String,Object>map=list.get(i);
					GetPlacementInfoListRes1 res = new GetPlacementInfoListRes1();
					res.setBaseproposalNos(map.get("BASE_PROPOSAL_NO")==null?"":map.get("BASE_PROPOSAL_NO").toString());
					res.setBouquetNos(map.get("BOUQUET_NO")==null?"":map.get("BOUQUET_NO").toString());
					res.setBrokerages(map.get("BROKERAGE_PER")==null?"":map.get("BROKERAGE_PER").toString());
					res.setBrokerIds(map.get("BROKER_ID")==null?"":map.get("BROKER_ID").toString());
					res.setCurrentStatus(map.get("CURRENT_STATUS")==null?"":map.get("CURRENT_STATUS").toString());
					res.setNewStatus(map.get("NEW_STATUS")==null?"":map.get("NEW_STATUS").toString());
					res.setProposedSL(map.get("SHARE_PROPOSED_SIGNED")==null?"":formattereight(map.get("SHARE_PROPOSED_SIGNED").toString()));
					res.setProposedWL(map.get("SHARE_PROPOSAL_WRITTEN")==null?"":formattereight(map.get("SHARE_PROPOSAL_WRITTEN").toString()));
					res.setReinsurerIds(map.get("REINSURER_ID")==null?"":map.get("REINSURER_ID").toString());
					res.setShareOffered(map.get("SHARE_OFFERED")==null?"":formattereight(map.get("SHARE_OFFERED").toString()));
					res.setSignedLine(map.get("SHARE_SIGNED")==null?"":formattereight(map.get("SHARE_SIGNED").toString()));
					res.setSnos(map.get("SNO")==null?"":map.get("SNO").toString());
					res.setWrittenLine(map.get("SHARE_WRITTEN")==null?"":formattereight(map.get("SHARE_WRITTEN").toString()));
					res.setStatusNo(map.get("STATUS_NO")==null?"":map.get("STATUS_NO").toString());				
					resList.add(res);
					} }
				response.setCommonResponse(resList);
				response.setMessage("Success");
			response.setIsError(false);
		}catch(Exception e){
				log.error(e);
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
			}
		return response;
	}
}
