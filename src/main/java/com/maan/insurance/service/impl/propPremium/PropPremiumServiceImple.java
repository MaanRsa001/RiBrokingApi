package com.maan.insurance.service.impl.propPremium;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.maan.insurance.controller.propPremium.ViewPremiumDetailsRIRes1;
import com.maan.insurance.model.entity.RskPremiumDetails;
import com.maan.insurance.model.entity.RskPremiumDetailsRi;
import com.maan.insurance.model.entity.RskPremiumDetailsTemp;
import com.maan.insurance.model.repository.RskPremiumDetailsRIRepository;
import com.maan.insurance.model.repository.RskPremiumDetailsRepository;
import com.maan.insurance.model.repository.RskPremiumDetailsTempRepository;
import com.maan.insurance.model.req.propPremium.ClaimTableListReq;
import com.maan.insurance.model.req.propPremium.ContractDetailsReq;
import com.maan.insurance.model.req.propPremium.GetCashLossCreditReq1;
import com.maan.insurance.model.req.propPremium.GetConstantPeriodDropDownReq;
import com.maan.insurance.model.req.propPremium.GetPreListReq;
import com.maan.insurance.model.req.propPremium.GetPremiumDetailsReq;
import com.maan.insurance.model.req.propPremium.GetPremiumReservedReq;
import com.maan.insurance.model.req.propPremium.GetPremiumedListReq;
import com.maan.insurance.model.req.propPremium.GetSPRetroListReq;
import com.maan.insurance.model.req.propPremium.InsertLossReserved;
import com.maan.insurance.model.req.propPremium.InsertPremiumReq;
import com.maan.insurance.model.req.propPremium.PremiumEditReq;
import com.maan.insurance.model.req.propPremium.SubmitPremiumReservedReq;
import com.maan.insurance.model.req.propPremium.SubmitPremiumReservedReq1;
import com.maan.insurance.model.req.propPremium.ViewPremiumDetailsRIRes;
import com.maan.insurance.model.res.ClaimlistRes;
import com.maan.insurance.model.res.DropDown.GetCommonValueRes;
import com.maan.insurance.model.res.propPremium.ClaimNosListRes;
import com.maan.insurance.model.res.propPremium.ClaimTableListMode1Res;
import com.maan.insurance.model.res.propPremium.ContractDetailsRes;
import com.maan.insurance.model.res.propPremium.ContractDetailsRes1;
import com.maan.insurance.model.res.propPremium.ContractDetailsRes2;
import com.maan.insurance.model.res.propPremium.ContractDetailsResponse;
import com.maan.insurance.model.res.propPremium.CurrencyListRes;
import com.maan.insurance.model.res.propPremium.CurrencyListRes1;
import com.maan.insurance.model.res.propPremium.GetAllocatedCassLossCreditRes;
import com.maan.insurance.model.res.propPremium.GetAllocatedCassLossCreditRes1;
import com.maan.insurance.model.res.propPremium.GetAllocatedListRes;
import com.maan.insurance.model.res.propPremium.GetAllocatedListRes1;
import com.maan.insurance.model.res.propPremium.GetAllocatedListRes2;
import com.maan.insurance.model.res.propPremium.GetAllocatedTransListRes;
import com.maan.insurance.model.res.propPremium.GetAllocatedTransListRes1;
import com.maan.insurance.model.res.propPremium.GetBrokerAndCedingNameRes;
import com.maan.insurance.model.res.propPremium.GetBrokerAndCedingNameRes1;
import com.maan.insurance.model.res.propPremium.GetCashLossCreditRes;
import com.maan.insurance.model.res.propPremium.GetCashLossCreditRes1;
import com.maan.insurance.model.res.propPremium.GetClaimNosDropDownRes;
import com.maan.insurance.model.res.propPremium.GetConstantPeriodDropDownRes;
import com.maan.insurance.model.res.propPremium.GetConstantPeriodDropDownRes1;
import com.maan.insurance.model.res.propPremium.GetContractPremiumRes;
import com.maan.insurance.model.res.propPremium.GetCountCleanCUTRes;
import com.maan.insurance.model.res.propPremium.GetDepartmentNoRes;
import com.maan.insurance.model.res.propPremium.GetDepositReleaseCountRes;
import com.maan.insurance.model.res.propPremium.GetOSBListRes;
import com.maan.insurance.model.res.propPremium.GetPreListRes;
import com.maan.insurance.model.res.propPremium.GetPreListRes1;
import com.maan.insurance.model.res.propPremium.GetPremiumDetailsRes;
import com.maan.insurance.model.res.propPremium.GetPremiumDetailsRes1;
import com.maan.insurance.model.res.propPremium.GetPremiumReservedRes;
import com.maan.insurance.model.res.propPremium.GetPremiumReservedRes1;
import com.maan.insurance.model.res.propPremium.GetPremiumedListRes;
import com.maan.insurance.model.res.propPremium.GetPremiumedListRes1;
import com.maan.insurance.model.res.propPremium.GetPreviousPremiumRes;
import com.maan.insurance.model.res.propPremium.GetRetroContractsRes;
import com.maan.insurance.model.res.propPremium.GetRetroContractsRes1;
import com.maan.insurance.model.res.propPremium.GetSPRetroListRes;
import com.maan.insurance.model.res.propPremium.GetSPRetroListRes1;
import com.maan.insurance.model.res.propPremium.GetSumOfShareSignRes;
import com.maan.insurance.model.res.propPremium.InsertPremiumRes;
import com.maan.insurance.model.res.propPremium.InsertPremiumRes1;
import com.maan.insurance.model.res.propPremium.PremiumEditRes;
import com.maan.insurance.model.res.propPremium.PremiumEditRes1;
import com.maan.insurance.model.res.propPremium.SubmitPremiumReservedRes;
import com.maan.insurance.model.res.propPremium.SubmitPremiumReservedRes1;
import com.maan.insurance.model.res.propPremium.SubmitPremiumReservedResponse;

import com.maan.insurance.model.res.propPremium.premiumUpdateMethodRes;
import com.maan.insurance.model.res.propPremium.premiumUpdateMethodRes1;

import com.maan.insurance.model.res.propPremium.ViewPremiumDetailsRIReq;

import com.maan.insurance.model.res.proportionality.CommonSaveRes;
import com.maan.insurance.service.impl.QueryImplemention;
import com.maan.insurance.service.impl.Dropdown.DropDownServiceImple;
import com.maan.insurance.service.propPremium.PropPremiumService;
import com.maan.insurance.validation.Formatters;
import com.maan.insurance.validation.Claim.ValidationImple;
@Service
public class PropPremiumServiceImple implements PropPremiumService {
	private Logger log = LogManager.getLogger(PropPremiumServiceImple.class);
	
	@Autowired
	private QueryImplemention queryImpl;

	@Autowired
	private Formatters fm;
	
	@Autowired
	private ValidationImple vi;
	
	@Autowired
	private DropDownServiceImple  dropDownImple;
	@Autowired
	private RskPremiumDetailsRepository pdRepo;
	@Autowired
	private RskPremiumDetailsTempRepository pdTempRepo;
	@Autowired
	private RskPremiumDetailsRIRepository pdRIRepo;
	
	
	@PersistenceContext
	private EntityManager em;
	
	
	@Override
	public GetPremiumedListRes getPremiumedList(GetPremiumedListReq req) {
		GetPremiumedListRes response = new GetPremiumedListRes();
		List<GetPremiumedListRes1> finalList = new ArrayList<GetPremiumedListRes1>();
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		String query="";
	    String[] args=null;
//	    int allocationstatus = 0;
//	    int retroPrclStatus = 0;
	    try {
	      	args=new String[4];
	    	args[0]=req.getContNo();
	    	args[1]=req.getBranchCode();
	    	args[2]=req.getContNo();
	    	args[3]=req.getDepartmentId();
	    	if("Main".equalsIgnoreCase(req.getType())){
	    		
	    		query="premium.select.PremiumedList";
	    	
	    	}else{
	    		query = "PTTY_PREMIUM_LIST_TEMP";
	    	}
		
		
	    	list = queryImpl.selectList(query,args);
		for(int i=0 ; i<list.size() ; i++) {
			Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
			GetPremiumedListRes1 tempreq = new GetPremiumedListRes1();
			tempreq.setRequestNo(tempMap.get("REQUEST_NO")==null?"":tempMap.get("REQUEST_NO").toString());
			tempreq.setProposalNo(tempMap.get("RSK_PROPOSAL_NUMBER")==null?"":tempMap.get("RSK_PROPOSAL_NUMBER").toString());
			tempreq.setContNo(tempMap.get("RSK_CONTRACT_NO")==null?"":tempMap.get("RSK_CONTRACT_NO").toString());
			//tempreq.setCedingCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
			//tempreq.setBroker(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
			tempreq.setLayerno(tempMap.get("RSK_LAYER_NO")==null?"":tempMap.get("RSK_LAYER_NO").toString());
			tempreq.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
			tempreq.setAccountPeriod(tempMap.get("ACC_PER")==null?"":tempMap.get("ACC_PER").toString());
			tempreq.setAccountPeriodDate(tempMap.get("ACCOUNTING_PERIOD_DATE")==null?"":tempMap.get("ACCOUNTING_PERIOD_DATE").toString());
			//tempreq.setTransDropDownVal(tempMap.get("REVERSE_TRANSACTION_NO")==null?"":tempMap.get("REVERSE_TRANSACTION_NO").toString());
			
//			if(Double.parseDouble(tempMap.get("ALLOC_AMT").toString())!=0)
//				tempreq.setEndtYN("Yes");
//			else
//				tempreq.setEndtYN("No");
//			tempreq.setProductId("2");
		//	tempreq.setInceptionDate(tempMap.get("INS_DATE")==null?"":tempMap.get("INS_DATE").toString());
			tempreq.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
			//tempreq.setMovementYN(tempMap.get("MOVEMENT_YN")==null?"":tempMap.get("MOVEMENT_YN").toString());
			
			tempreq.setTransDate(tempMap.get("TRANSACTION_DATE")==null?"":tempMap.get("TRANSACTION_DATE").toString());
//			if((StringUtils.isNotBlank(req.getOpstartDate()))&& (StringUtils.isNotBlank(req.getOpendDate()))){
//				if(dropDownImple.Validatethree(req.getBranchCode(), tempreq.getTransDate())==0){
//					tempreq.setTransOpenperiodStatus("N");
//				}else
//				{
//					tempreq.setTransOpenperiodStatus("Y");
//				}
//				}
//			query="premium.select.allocatedYN";
//			list = queryImpl.selectList(query,new String[]{tempreq.getContNo(),tempreq.getTransactionNo(),tempreq.getLayerno()});
//			if (!CollectionUtils.isEmpty(list)) {
//				tempreq.setAllocatedYN(list.get(0).get("ALLOCATEDYN") == null ? ""
//						: list.get(0).get("ALLOCATEDYN").toString());
//			}
			
			
//			int count=dropDownImple.Validatethree(req.getBranchCode(), tempreq.getTransDate());
//				String args2[]=new String[1];
//				args2[0]=tempreq.getTransactionNo();
//				query="allocation.status";
//				list = queryImpl.selectList(query,args2);
//				if (!CollectionUtils.isEmpty(list)) {
//					allocationstatus = Integer.valueOf(list.get(0).get("COUNT") == null ? ""
//							: list.get(0).get("COUNT").toString());
//				}
				
				
//				query="retro.status";
//				list = queryImpl.selectList(query,args2);
//				if (!CollectionUtils.isEmpty(list)) {
//					 retroPrclStatus = Integer.valueOf(list.get(0).get("COUNT") == null ? ""
//							: list.get(0).get("COUNT").toString());
//				}
				
//				int retroPrclStatus1=0;
//				if(retroPrclStatus!=0){
//				query="retro.status1";
//				list = queryImpl.selectList(query,args2);
//				if (!CollectionUtils.isEmpty(list)) {
//					retroPrclStatus1 = Integer.valueOf(list.get(0).get("COUNT") == null ? ""
//							: list.get(0).get("COUNT").toString());
//				}
				
//				}
//				if(count!=0 && allocationstatus ==0 &&  retroPrclStatus1 ==0 ){
//					tempreq.setDeleteStatus("Y");
//				}
			finalList.add(tempreq);
		}
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
	public GetPreListRes getPreList(GetPreListReq req) {
		GetPreListRes response = new GetPreListRes();
		//List<GetPreListRes1> resList = new ArrayList<GetPreListRes1>();
		String query="";
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		String[] args = null;
		try {
		args = new String[2];
		args[0] = req.getContNo();
		args[1] = req.getDepartmentId();
		query="premium.select.facTreatyPreList";
		list = queryImpl.selectList(query,args);
//		for(int i=0 ; i<list.size() ; i++) {
		if(list!=null && list.size()>0) {
				Map<String,Object> tempMap = (Map<String,Object>) list.get(0);
				GetPreListRes1 res = new GetPreListRes1();
				res.setContNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
				res.setDepartmentName(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
				res.setUwYear(tempMap.get("UW_YEAR")==null?"":tempMap.get("UW_YEAR").toString());
				res.setCedingCompanyName(tempMap.get("COMPANY_NAME")==null?"":tempMap.get("COMPANY_NAME").toString());
				res.setBrokerName(tempMap.get("BROKER_NAME")==null?"":tempMap.get("BROKER_NAME").toString());
				res.setLayerno(tempMap.get("LAYER_NO")==null?"":tempMap.get("LAYER_NO").toString());
				res.setProposalNo(tempMap.get("PROPOSAL_NO")==null?"":tempMap.get("PROPOSAL_NO").toString());
//				res.setProductId(tempMap.get("PRODUCT_ID")==null?"":tempMap.get("PRODUCT_ID").toString());
//				if(StringUtils.isNotBlank(res.getProposalNo()) ){
//					query = "premium.select.CEASE_STATUS";
//					list = queryImpl.selectList(query,new String[]{res.getProposalNo()});
//					if (!CollectionUtils.isEmpty(list)) {
//						res.setCeaseStatus(list.get(0).get("CEASE_STATUS") == null ? ""
//								: list.get(0).get("CEASE_STATUS").toString());
//					}
			//	resList.add(res);
		response.setCommonResponse(res);
		response.setMessage("Success");
		response.setIsError(false);
		}
    }catch (Exception e) {
		log.error(e);
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
    return response;
}

	@Override
	public GetConstantPeriodDropDownRes getConstantPeriodDropDown(GetConstantPeriodDropDownReq req) {
		GetConstantPeriodDropDownRes response = new GetConstantPeriodDropDownRes();
		List<GetConstantPeriodDropDownRes1> res1List = new ArrayList<GetConstantPeriodDropDownRes1>();
		List<Map<String,Object>> constantList=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> res=new ArrayList<Map<String,Object>>();
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		boolean val = false;
		boolean preval=false;
		boolean lossval=false;
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		String slide  = "";
		String combine ="";
		String premium ="";
		String preCombine="";
		String loss ="";
		String lossCombine="";
		String DeptNo ="";
		String proposalNo = "";
		String base="";
		String accPeriod="";
		try{
			String query="";
			query ="GET_ACC_PERIOD";
			String args[] = null;
			args=new String[1];
			args[0] = req.getProposalNo();
			list = queryImpl.selectList(query,args);
			if (!CollectionUtils.isEmpty(list)) {
				accPeriod = list.get(0).get("RSK_ACCOUNTING_PERIOD") == null ? ""
						: list.get(0).get("RSK_ACCOUNTING_PERIOD").toString();
			}
			query = "COMMON_SELECT_GETCONSTDET_PTTY";
			args=new String[2];
			args[0] = req.getCategoryId();
			args[1]= "Y";
			if("1".equalsIgnoreCase(accPeriod)){
				query = "COMMON_SELECT_GETCONSTDET_PTTY1";
			}else if("2".equalsIgnoreCase(accPeriod)){
				query = "COMMON_SELECT_GETCONSTDET_PTTY2";
			}else if("3".equalsIgnoreCase(accPeriod)){
				query ="COMMON_SELECT_GETCONSTDET_PTTY3";
			}
			constantList = queryImpl.selectList(query,args);
			query = "GET_BASE_LAYER";
			args=new String[2];
			args[0] = req.getContractNo();
			args[1] = req.getDepartmentId();
            result = queryImpl.selectList(query,args);
            for(int i=0;i<result.size();i++) {
                Map<String, Object> map = result.get(i);
              
                proposalNo = map.get("PROPOSAL_NO")==null?"":map.get("PROPOSAL_NO").toString();
                base = map.get("BASE_LAYER")==null?"":map.get("BASE_LAYER").toString();
            }
			if(base.equalsIgnoreCase("0")){
                args=new String[2];
                args[0] = proposalNo;
                args[1] = req.getDepartmentId();
                
				query = "GET_SLIDE_COMM_VALUE";
				result = queryImpl.selectList(query,args);
				for(int i=0;i<result.size();i++){
					Map<String,Object> map = result.get(i);
					 slide  = map.get("RSK_SLADSCALE_COMM")==null?"":map.get("RSK_SLADSCALE_COMM").toString();
					 combine  = map.get("RSK_SLIDE_COMBIN_SUB_CLASS")==null?"":map.get("RSK_SLIDE_COMBIN_SUB_CLASS").toString();
					 premium = map.get("RSK_PROFIT_COMM")==null?"":map.get("RSK_PROFIT_COMM").toString();
					 preCombine =map.get("RSK_COMBIN_SUB_CLASS")==null?"":map.get("RSK_COMBIN_SUB_CLASS").toString();
					 loss = map.get("RSK_LOSS_PART_CARRIDOR")==null?"":map.get("RSK_LOSS_PART_CARRIDOR").toString();
					 lossCombine =map.get("RSK_LOSS_COMBIN_SUB_CLASS")==null?"":map.get("RSK_LOSS_COMBIN_SUB_CLASS").toString();
					if(slide.equalsIgnoreCase("Y")){
						val = true ;
                        if(combine.equalsIgnoreCase("1")){
                        	response.setSlideScenario("one");
                        }
                        else if(combine.equalsIgnoreCase("2")){
                        	response.setSlideScenario("two");
                        }
					}
					if(premium.equalsIgnoreCase("1")){
						preval=true;
						if(preCombine.equalsIgnoreCase("1")){
							response.setSlideScenario("one");
						}
						else if(preCombine.equalsIgnoreCase("2")){
							response.setSlideScenario("two");
                        }
					}
					if(loss.equalsIgnoreCase("Y")){
						lossval=true;
						if(lossCombine.equalsIgnoreCase("1")){
							response.setSlideScenario("one");
						}
						else if(lossCombine.equalsIgnoreCase("2")){
							response.setSlideScenario("two");
                        }
					}
				}
			}
			else{
				args = new String[1];
				args[0] = base;
				query = "GET_DEPT_ID";
				result = queryImpl.selectList(query,args);
                
                for(int i=0;i<result.size();i++){
                    Map<String,Object> map = result.get(i);
                    DeptNo  = map.get("DEPT_ID")==null?"":map.get("DEPT_ID").toString();
                }
                args=new String[2];
                args[0] = base;
                args[1] = DeptNo;
				query = "GET_SLIDE_COMM_VALUE1";
				
				result = queryImpl.selectList(query,args);
				for(int i=0;i<result.size();i++){
                    Map<String,Object> map = result.get(i);
					 slide  = map.get("RSK_SLADSCALE_COMM")==null?"":map.get("RSK_SLADSCALE_COMM").toString();
					 combine  = map.get("RSK_SLIDE_COMBIN_SUB_CLASS")==null?"":map.get("RSK_SLIDE_COMBIN_SUB_CLASS").toString();
					 premium = map.get("RSK_PROFIT_COMM")==null?"":map.get("RSK_PROFIT_COMM").toString();
					 preCombine =map.get("RSK_COMBIN_SUB_CLASS")==null?"":map.get("RSK_COMBIN_SUB_CLASS").toString();
					 loss = map.get("RSK_LOSS_PART_CARRIDOR")==null?"":map.get("RSK_LOSS_PART_CARRIDOR").toString();
					 lossCombine =map.get("RSK_LOSS_COMBIN_SUB_CLASS")==null?"":map.get("RSK_LOSS_COMBIN_SUB_CLASS").toString();
				}
				if(combine.equalsIgnoreCase("2") || preCombine.equalsIgnoreCase("2")|| lossCombine.equalsIgnoreCase("2")) {
                    args=new String[2];
                    args[0] = proposalNo;
                    args[1] = req.getDepartmentId();
					query = "GET_SLIDE_COMM_VALUE2";
				
					result = queryImpl.selectList(query,args);
					for (int i = 0; i < result.size(); i++) {
                        Map<String,Object> map =result.get(i);
						 slide  = map.get("RSK_SLADSCALE_COMM")==null?"":map.get("RSK_SLADSCALE_COMM").toString();
						 premium = map.get("RSK_PROFIT_COMM")==null?"":map.get("RSK_PROFIT_COMM").toString();
						 loss = map.get("RSK_LOSS_PART_CARRIDOR")==null?"":map.get("RSK_LOSS_PART_CARRIDOR").toString();
						if(slide.equalsIgnoreCase("Y") ){
							val = true ;
							response.setSlideScenario("three");
						}
						if(premium.equalsIgnoreCase("1")){
							preval= true;
							response.setSlideScenario("three");
						}
						if(loss.equalsIgnoreCase("Y") ){
							lossval = true ;
							response.setSlideScenario("three");
						}
					}
				}
			}
			if(!val) {
                for (int i = 0; i < constantList.size(); i++) {
                    Map<String, Object> val1 = constantList.get(i);
                    String type = val1.get("TYPE")==null?"":val1.get("TYPE").toString();
                    if (type.equalsIgnoreCase("8")) {
                        val1.remove(i);
                    } else {
                        res.add(val1);
                    }
                }
                constantList = res;
            }
			if(!preval){
				res=new ArrayList<Map<String,Object>>();
				 for (int i = 0; i < constantList.size(); i++) {
	                    Map<String, Object> val1 = constantList.get(i);
	                    String type = val1.get("TYPE")==null?"":val1.get("TYPE").toString();
	                    if (type.equalsIgnoreCase("7")) {
	                        val1.remove(i);
	                    } else {
	                        res.add(val1);
	                    }
	                }
				 constantList = res;
			}
			if(!lossval){
				res=new ArrayList<Map<String,Object>>();
				 for (int i = 0; i < constantList.size(); i++) {
	                    Map<String, Object> val1 = constantList.get(i);
	                    String type = val1.get("TYPE")==null?"":val1.get("TYPE").toString();
	                    if (type.equalsIgnoreCase("9")) {
	                        val1.remove(i);
	                    } else {
	                        res.add(val1);
	                    }
	                }
				 constantList = res;
			}
			for (int i = 0; i < constantList.size(); i++) {
				Map<String,Object> tempMap = (Map<String,Object>) constantList.get(i);
				GetConstantPeriodDropDownRes1 res2 = new GetConstantPeriodDropDownRes1();
				
				res2.setPremiumType(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString());
				res2.setDetailName(tempMap.get("DETAIL_NAME")==null?"":tempMap.get("DETAIL_NAME").toString());
				res1List.add(res2);
			}
			
			response.setCommonResponse(res1List);
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
	public GetPreviousPremiumRes getPreviousPremium(String contractNo) {
		GetPreviousPremiumRes response = new GetPreviousPremiumRes();
		
		String premium="";
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try{
			String query="select.premium.sum";
			list = queryImpl.selectList(query,new String[] {contractNo});
			if (!CollectionUtils.isEmpty(list)) {
				premium = list.get(0).get("PREMIUM") == null ? ""
						: list.get(0).get("PREMIUM").toString();
			}
		
			
			response.setCommonResponse(premium);
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
	public GetContractPremiumRes getContractPremium(String contractNo, String departmentId, String branchCode) {
		GetContractPremiumRes response = new GetContractPremiumRes();
		
		String contractPremium="";
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try{
			String query="GET_CONT_PREM";
			list = queryImpl.selectList(query,new String[] {contractNo, departmentId, branchCode});
			if (!CollectionUtils.isEmpty(list)) {
				contractPremium = list.get(0).get("RSK_EPI_OSOE_OC") == null ? ""
						: list.get(0).get("RSK_EPI_OSOE_OC").toString();
			}
			response.setCommonResponse(contractPremium);
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
	public GetClaimNosDropDownRes getClaimNosDropDown(String contractNo) {
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		GetClaimNosDropDownRes response = new GetClaimNosDropDownRes();
	 	
	 	List<ClaimNosListRes> resList = new ArrayList<ClaimNosListRes>();
		try{
		 	String query="premium.select.getClaimNoDropdown";
		 	list = queryImpl.selectList(query,new String[] {contractNo});
			for (int i = 0; i < list.size(); i++) {
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				ClaimNosListRes claimRes = new ClaimNosListRes();
				
				
				claimRes.setClaimNo(tempMap.get("CLAIM_NO")==null?"":tempMap.get("CLAIM_NO").toString());
				
				resList.add(claimRes);
			}
			
			response.setCommonResponse(resList);
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
	public ContractDetailsRes contractDetails(ContractDetailsReq req) {
		ContractDetailsRes response = new ContractDetailsRes();
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		ContractDetailsRes1 res = new ContractDetailsRes1();
		List<ContractDetailsRes2> res2List = new ArrayList<ContractDetailsRes2>();
		ContractDetailsResponse response1 =new ContractDetailsResponse();
		 String query="";
		 String[] args=null;
		 
		 try {
			 query="premium.select.treatyContDet";
			 	args =new String[10];
			 	args[0] = req.getProductId();
				args[1] = req.getContNo();
				args[2] = req.getDepartmentId();
				args[3] = req.getBranchCode();
				args[4] = req.getBranchCode();
				
				args[5] = req.getProductId();
				
				args[6] = req.getBranchCode();
				args[7] = req.getBranchCode();
				args[8] = req.getContNo();
				args[9] = req.getBranchCode();
				list = queryImpl.selectList(query,args);
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					
					
					res.setContNo(tempMap.get("RSK_CONTRACT_NO")==null?"":tempMap.get("RSK_CONTRACT_NO").toString());
					res.setAmendId(tempMap.get("RSK_ENDORSEMENT_NO")==null?"":tempMap.get("RSK_ENDORSEMENT_NO").toString());
					res.setProfitCenter(tempMap.get("TMAS_PFC_NAME")==null?"":tempMap.get("TMAS_PFC_NAME").toString());
					res.setSubProfitCenter(tempMap.get("RSK_SPFCID")==null?"":tempMap.get("RSK_SPFCID").toString());
						
						
						
						if(!"ALL".equalsIgnoreCase(res.getSubProfitCenter())){
							res.setSubProfitCenter(tempMap.get("TMAS_SPFC_NAME")==null?"":tempMap.get("TMAS_SPFC_NAME").toString());
							
							}
						
						res.setCedingCo(tempMap.get("COMPANY")==null?"":tempMap.get("COMPANY").toString());
						res.setBroker(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
						res.setTreatyNameType(tempMap.get("RSK_TREATYID")==null?"":tempMap.get("RSK_TREATYID").toString());
						res.setProposalNo(tempMap.get("RSK_PROPOSAL_NUMBER")==null?"":tempMap.get("RSK_PROPOSAL_NUMBER").toString());
						
						res.setUwYear(tempMap.get("RSK_UWYEAR")==null?"":tempMap.get("RSK_UWYEAR").toString());
						res.setLayerNo(tempMap.get("RSK_LAYER_NO")==null?"":tempMap.get("RSK_LAYER_NO").toString());
						res.setInsDate(tempMap.get("INS_DATE")==null?"":tempMap.get("INS_DATE").toString());
						res.setExpDate(tempMap.get("EXP_DATE")==null?"":tempMap.get("EXP_DATE").toString());
						res.setMonth(tempMap.get("MONTH")==null?"":tempMap.get("MONTH").toString());
						res.setBaseCurrencyId(tempMap.get("RSK_ORIGINAL_CURR")==null?"":tempMap.get("RSK_ORIGINAL_CURR").toString());
						res.setBaseCurrencyName(tempMap.get("CURRENCY_NAME")==null?"":tempMap.get("CURRENCY_NAME").toString());
						res.setPolicyBranch(tempMap.get("TMAS_POL_BRANCH_NAME")==null?"":tempMap.get("TMAS_POL_BRANCH_NAME").toString());
						res.setAddress(tempMap.get("ADDRESS")==null?"":tempMap.get("ADDRESS").toString());
						res.setDepartmentId(tempMap.get("RSK_DEPTID")==null?"":tempMap.get("RSK_DEPTID").toString());
				
						String count="";
						if("2".equals(req.getProductId())){
							count = getCombinedClass(req.getBranchCode(),req.getProductId(),req.getDepartmentId());
						}
						if(StringUtils.isBlank(count)){
							res.setPreDepartment(tempMap.get("RSK_DEPTID")==null?"":tempMap.get("RSK_DEPTID").toString());
							res.setConsubProfitId(tempMap.get("RSK_SPFCID")==null?"":tempMap.get("RSK_SPFCID").toString());
						}
						res.setTreatyType(tempMap.get("TREATYTYPE")==null?"":tempMap.get("TREATYTYPE").toString());
						res.setBusinessType(tempMap.get("INWARD_BUS_TYPE")==null?"":tempMap.get("INWARD_BUS_TYPE").toString());
						res.setAcceptenceDate(tempMap.get("RSK_ACCOUNT_DATE")==null?"":tempMap.get("RSK_ACCOUNT_DATE").toString());
				}
				if(list!=null && list.size()>0)
					
					args=new String[2];
					args[0] = req.getProposalNo();
					args[1] = req.getProposalNo();
					query="premium.select.commissionDetails";
					list = queryImpl.selectList(query,args);
					for (int i = 0; i < list.size(); i++) {
						Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
						
						
						res.setCommissionView(fm.formatter(tempMap.get("RSK_COMM_QUOTASHARE")==null?"":tempMap.get("RSK_COMM_QUOTASHARE").toString()));
						res.setPremiumReserveView(fm.formatter(tempMap.get("RSK_PREMIUM_RESERVE")==null?"":tempMap.get("RSK_PREMIUM_RESERVE").toString()));
						res.setLossReserveView(fm.formatter(tempMap.get("RSK_LOSS_RESERVE")==null?"":tempMap.get("RSK_LOSS_RESERVE").toString()));
						res.setProfitCommYN(fm.formatter(tempMap.get("RSK_PROFIT_COMM")==null?"":tempMap.get("RSK_PROFIT_COMM").toString()));
					
						res.setCommissionSurbView(fm.formatter(tempMap.get("RSK_COMM_SURPLUS")==null?"":tempMap.get("RSK_COMM_SURPLUS").toString()));
						res.setOverRiderView(fm.formatter(tempMap.get("RSK_OVERRIDER_PERC")==null?"":tempMap.get("RSK_OVERRIDER_PERC").toString()));
						res.setBrokerageView(fm.formatter(tempMap.get("RSK_BROKERAGE")==null?"":tempMap.get("RSK_BROKERAGE").toString()));
						res.setBrokerageView(tempMap.get("RSK_BROKERAGE")==null?"":tempMap.get("RSK_BROKERAGE").toString());
						res.setTaxView(fm.formatter(tempMap.get("RSK_TAX")==null?"":tempMap.get("RSK_TAX").toString()));
						res.setOtherCostView(fm.formatter(tempMap.get("RSK_OTHER_COST")==null?"":tempMap.get("RSK_OTHER_COST").toString()));
						res.setOurAssessmentOfOrginal(tempMap.get("RSK_OUR_ASS_ACQ_COST")==null?"0.00":tempMap.get("RSK_OUR_ASS_ACQ_COST").toString());
						res.setPremiumReserve(tempMap.get("RSK_PREMIUM_RESERVE")==null?"":tempMap.get("RSK_PREMIUM_RESERVE").toString());
						
						}
					
					args[0] = req.getProposalNo();
					args[1] = req.getProposalNo();
					query="premium.select.treatyProposalDetails";
					list = queryImpl.selectList(query,args);
					for (int i = 0; i < list.size(); i++) {
						Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
						
						
						res.setShareSigned(tempMap.get("RSK_SHARE_SIGNED")==null?"":tempMap.get("RSK_SHARE_SIGNED").toString());
						res.setPremiumQuotaView(fm.formatter(tempMap.get("RSK_PREMIUM_QUOTA_SHARE")==null?"":tempMap.get("RSK_PREMIUM_QUOTA_SHARE").toString()));
						res.setPremiumsurpView(fm.formatter(tempMap.get("RSK_PREMIUM_SURPULS")==null?"":tempMap.get("RSK_PREMIUM_SURPULS").toString()));
						res.setXlCostView(fm.formatter(tempMap.get("RSK_XLCOST_OS_OC")==null?"":tempMap.get("RSK_XLCOST_OS_OC").toString()));
						
						String eps = (tempMap.get("RSK_EPI_OSOE_OC")==null?"":tempMap.get("RSK_EPI_OSOE_OC").toString());
						res.setRdsExchageRate(tempMap.get("RSK_EXCHANGE_RATE")==null?"":tempMap.get("RSK_EXCHANGE_RATE").toString());
					
							
							double val= Double.parseDouble(eps)/Double.parseDouble(res.getRdsExchageRate());
							res.setEpioc(fm.formatter(Double.toString(val)));
							
						}
				
					
					String[] obj=new String[0];
//					
//					if(StringUtils.isEmpty(req.getTransactionNo()))
//					{
//						query="premium.select.getOsClaimLossUpdate";
//						obj=new String[3];
//						obj[0]= req.getBranchCode();
//						obj[1] = req.getContNo();
//						obj[2] = req.getContNo();
//					}
//					else
//					{
//						query = "premium.select.getOsClaimLossUpdate1";
//						obj=new String[4];
//						obj[0] = req.getBranchCode();
//						obj[1] = req.getContNo();
//						obj[2] = req.getContNo();
//						obj[3] = req.getTransactionNo();
//					}
//					
//					
//			
//					
//					List<Map<String,Object>> 	li = queryImpl.selectList(query,obj);
//					
//					
//					if(li!=null && li.size()>0){
//						for (int j = 0; j < li.size(); j++) {
//							final Map<String,Double> osClaimLossMap = new HashMap();
//							final Map tempMap = (Map) li.get(j);
//							osClaimLossMap.put("Previous Outstanding Loss position For "+tempMap.get("CURRENCY_NAME").toString(), Double.parseDouble((tempMap.get("OSCLAIM_LOSSUPDATE_OC")==null?"0":tempMap.get("OSCLAIM_LOSSUPDATE_OC").toString())));
//						}
//						
//					}
//					res.setOsClaimLoss(osClaimLossMap);

					
					if(StringUtils.isNotBlank(req.getTransactionNo())){
						query="premium.select.cashLossCreditUpdate";
						
						obj=new String[2];
						obj[0]=req.getContNo();
						obj[1]=req.getTransactionNo();
						
						List<Map<String,Object>> claimlist = queryImpl.selectList(query,obj);
						if(claimlist!=null){
							
							for(int k=0;k<claimlist.size();k++){
								Map temp=(Map)claimlist.get(k);
								ContractDetailsRes2 res2 = new ContractDetailsRes2();
								res2.setClaimNo(temp.get("CLAIM_NO")==null?"":temp.get("CLAIM_NO").toString());
								res2.setCurrencyId(temp.get("CURRENCY_ID")==null?"":temp.get("CURRENCY_ID").toString());
								res2.setCashoc(temp.get("CASH_LOSS_CREDIT_OC")==null?"":temp.get("CASH_LOSS_CREDIT_OC").toString());
								res2.setCashdc(temp.get("CASH_LOSS_CREDIT_DC")==null?"":temp.get("CASH_LOSS_CREDIT_DC").toString());
								res2List.add(res2);
							}
							
						}
					}
					
					query="premium.select.currecy.name";
					list = queryImpl.selectList(query,new String[]{req.getBranchCode()});
					if (!CollectionUtils.isEmpty(list)) {
						res.setCurrencyName(list.get(0).get("COUNTRY_SHORT_NAME") == null ? ""
								: list.get(0).get("COUNTRY_SHORT_NAME").toString());
					}
				   
				   	
				   	query="premium.select.sumOfPaidPremium";
				   	list = queryImpl.selectList(query,new String[]{req.getContNo()});
					if (!CollectionUtils.isEmpty(list)) {
						res.setSumofPaidPremium(list.get(0).get("PREMIUM_QUOTASHARE_OC") == null ? ""
								: list.get(0).get("PREMIUM_QUOTASHARE_OC").toString());
					}
					
					
					query="GETSETTLEMET_STATUS";
					List<Map<String,Object>> premlist = new ArrayList<Map<String,Object>>();
					premlist = queryImpl.selectList(query,new String[]{req.getContNo()});
					
					if(premlist.size()>0){
						for(int j=0;j<premlist.size();j++){
							Map<String,Object> map = premlist.get(j);
								String allocate = map.get("ALLOCATED_TILL_DATE")==null?"0":map.get("ALLOCATED_TILL_DATE").toString();
								String net = map.get("NETDUE_OC").toString();
								if("0".equalsIgnoreCase(allocate)){
									res.setSettlementStatus("Pending");
								}else if(Double.parseDouble(allocate) == Double.parseDouble(net)){
									res.setSettlementStatus("Allocated");
								}else{
									res.setSettlementStatus("Partially Allocated");
								}
						}
					}
					response1.setCommonResponse2(res2List);
					response1.setCommonResponse1(res);
					response.setCommonResponse(response1);
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
	public String getCombinedClass(String branchCode,String productId,String departId){
	
	String count="";
	try {
		String query="premium.select.CORE_COMPANY_CODE";
		List<Map<String,Object>> list = queryImpl.selectList(query,new String[] {branchCode,productId,departId});
		if (!CollectionUtils.isEmpty(list)) {
			count = list.get(0).get("CORE_COMPANY_CODE") == null ? ""
					: list.get(0).get("CORE_COMPANY_CODE").toString();
		}
		
	} catch (Exception e) {
		e.printStackTrace();
	}
	return count;
}


	@Override
	public ClaimTableListMode1Res claimTableListMode1(ClaimTableListReq req) {

		log.info("CliamBusinessImpl cliamTableList || Enter");
		List<ClaimlistRes> cliamlists = new ArrayList<ClaimlistRes>();
		
		String query = "";
		List<Map<String, Object>> list;
		ClaimTableListMode1Res res = new ClaimTableListMode1Res();
		try {
			/*if (StringUtils.isNotBlank(req.getClaimNo())) {
				query = "claim.select.sumPaidAmt1"; 
				list = queryImpl.selectList(query, new String[] { req.getClaimNo(), req.getPolicyContractNo() });

				if (!CollectionUtils.isEmpty(list)) {
					res.setSumOfPaidAmountOC(fm.formatter(list.get(0).get("PAID_AMOUNT_OC") == null ? ""
							: list.get(0).get("PAID_AMOUNT_OC").toString()));
				}

			} */

			query = "claim.select.claimTableList";

			list = queryImpl.selectList(query,
					new String[] { req.getPolicyContractNo(), req.getLayerNo(), req.getDepartmentId() });
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				
				ClaimlistRes cliam = new ClaimlistRes();
				cliam.setClaimNo(tempMap.get("CLAIM_NO") == null ? "" : tempMap.get("CLAIM_NO").toString());
				cliam.setDateOfLoss(tempMap.get("DATE_OF_LOSS") == null ? "" : tempMap.get("DATE_OF_LOSS").toString());
				cliam.setCreatedDate(tempMap.get("CREATED_DATE") == null ? "" : tempMap.get("CREATED_DATE").toString());
				cliam.setStatusOfClaim(
						tempMap.get("STATUS_OF_CLAIM") == null ? "" : tempMap.get("STATUS_OF_CLAIM").toString());
				cliam.setPolicyContractNo(
						tempMap.get("CONTRACT_NO") == null ? "" : tempMap.get("CONTRACT_NO").toString());
				cliam.setEditMode(tempMap.get("EDITVIEW") == null ? "" : tempMap.get("EDITVIEW").toString());
				int count = Integer.valueOf(dropDownImple.validatethree(req.getBranchCode(), cliam.getCreatedDate()));
				int claimpaymentcount = Integer
						.valueOf(getCliampaymnetCount(cliam.getClaimNo(), cliam.getPolicyContractNo()));
		if (count != 0 && claimpaymentcount == 0) {
				cliam.setDeleteStatus("Y");
				}
				cliamlists.add(cliam);
			}
			res.setCommonResponse(cliamlists);
		/*	if (StringUtils.isNotBlank(req.getProposalNo())) {
				query = "setCeaseStatus";
				list = queryImpl.selectList(query, new String[] { req.getProposalNo() });

				if (!CollectionUtils.isEmpty(list)) {
					res.setCeaseStatus(
							list.get(0).get("CEASE_STATUS") == null ? "" : list.get(0).get("CEASE_STATUS").toString());
				}

			} */
			res.setMessage("Success");
			res.setIsError(false);

		} catch (Exception e) {
			log.error(e);
			e.printStackTrace();
			res.setMessage("Failed");
			res.setIsError(true);
		}
		return res;
	
	}
//	public int Validatethree(String branchCode, String accountDate) {
//
//		int count=0;
//		
//		List<Map<String, Object>> result=new ArrayList<Map<String, Object>>();
//		
//		try{
//			String query="GET_OPEN_PERIOD_DATE";
//			String OpstartDate="";
//			String OpendDate="";
//			String[] args = new String[1];
//			args[0]=branchCode;
//			
//			log.info("Select Query ==> " + query);
//			List<Map<String,Object>> list=queryImpl.selectList(query,args);
//			for(int i=0 ; i<list.size() ; i++) {
//				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
//				OpstartDate=tempMap.get("START_DATE")==null?"":tempMap.get("START_DATE").toString();
//				OpendDate=tempMap.get("END_DATE")==null?"":tempMap.get("END_DATE").toString();
//				String query1="GET_OPEN_PERIOD_VALIDATE";
//				args = new String[3];
//				args[0]=accountDate;
//				args[1]=OpstartDate;
//				args[2]=OpendDate;
//		
//				result=queryImpl.selectList(query1,args);
//				if (!CollectionUtils.isEmpty(result)) {
//					count =Integer.valueOf((result.get(0).get("TOTAL") == null ? ""
//							: result.get(0).get("TOTAL").toString()));
//				
//				}
//				
//				 if(count>0)
//					 break;
//			}
//		}catch(Exception e){
//			e.printStackTrace();
//			log.debug("Exception @ {" + e + "}");	
//		}
//		return count;
//	
//	}
	public String getCliampaymnetCount(String claimNo, String contNo) {
		String result = "";

		try {
			String query1 = "getCliampaymnetCount";
			String[] args = new String[2];
			args[0] = claimNo;
			args[1] = contNo;
			List<Map<String, Object>> list = queryImpl.selectList(query1, args);

			if (!CollectionUtils.isEmpty(list)) {
				result = list.get(0).get("COUNT") == null ? "" : list.get(0).get("COUNT").toString();
			}

		} catch (Exception e) {
			log.debug("Exception @ {" + e + "}");
		}
		return result;
	}


	@Override
	public GetCountCleanCUTRes getCountCleanCUT(String contractNo) {
		GetCountCleanCUTRes response = new GetCountCleanCUTRes();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		int count=0;
		try {
			String query="GET_CLEAN_CUT_CONT_COUNT";
			list=queryImpl.selectList(query,new String[]{contractNo});
						if (!CollectionUtils.isEmpty(list)) {
				count =Integer.valueOf((list.get(0).get("RSK_CONTRACT_NO") == null ? ""
						: list.get(0).get("RSK_CONTRACT_NO").toString()));
			}
			if(count>0){
			query="GET_CLEAN_CUT_COUNT";
			list=queryImpl.selectList(query,new String[]{contractNo});
			if (!CollectionUtils.isEmpty(list)) {
				count =Integer.valueOf((list.get(0).get("COUNT") == null ? ""
						: list.get(0).get("COUNT").toString()));
			}}
		response.setCommonResponse(String.valueOf(count));
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
	public GetSPRetroListRes getSPRetroList(GetSPRetroListReq req) {
		GetSPRetroListRes response = new GetSPRetroListRes();
		GetSPRetroListRes1 res = new GetSPRetroListRes1();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		List<GetSPRetroListRes> reslist = new ArrayList<>();
		try{
			String query="";
			String args[]=null;
			args=new String[1];
			args[0]= req.getContNo();
			query="premium.select.getTreatySPRetro";
			list=queryImpl.selectList(query,args); 
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				
				res.setSpRetro(tempMap.get("RSK_SP_RETRO") == null ? "" : tempMap.get("RSK_SP_RETRO").toString());
				res.setNoOfInsurers(tempMap.get("RSK_NO_OF_INSURERS") == null ? "" : tempMap.get("RSK_NO_OF_INSURERS").toString());
				res.setProposalNo(tempMap.get("RSK_PROPOSAL_NUMBER") == null ? "" : tempMap.get("RSK_PROPOSAL_NUMBER").toString());
				reslist.add(response);			}
			
			response.setCommonResponse(reslist);
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
	public GetRetroContractsRes getRetroContracts(String proposalNo, String noOfRetro) {
		GetRetroContractsRes response = new GetRetroContractsRes();
		List<GetRetroContractsRes1> resList = new ArrayList<GetRetroContractsRes1>();
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
			String query="";
			String args[]=null;
			args=new String[2];
			args[0]=proposalNo;
			args[1]=noOfRetro;
			query="premium.select.insDetails";
			list=queryImpl.selectList(query,args); 
			for (int i = 0; i < list.size(); i++) {
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				GetRetroContractsRes1 res = new GetRetroContractsRes1();
				res.setContractNo(tempMap.get("CONTRACT_NO") == null ? "" : tempMap.get("CONTRACT_NO").toString());
				res.setRetroPercentage(tempMap.get("RETRO_PERCENTAGE") == null ? "" : tempMap.get("RETRO_PERCENTAGE").toString());
				res.setType(tempMap.get("TYPE") == null ? "" : tempMap.get("TYPE").toString());
				res.setUwyear(tempMap.get("UW_YEAR") == null ? "" : tempMap.get("UW_YEAR").toString());
				res.setRetroType(tempMap.get("RETRO_TYPE") == null ? "" : tempMap.get("RETRO_TYPE").toString());
				resList.add(res);
			}
			response.setCommonResponse(resList);
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
	public GetSumOfShareSignRes getSumOfShareSign(String retroContractNo) {
		GetSumOfShareSignRes response = new GetSumOfShareSignRes();
//		GetSumOfShareSignRes1 res = new GetSumOfShareSignRes1();
		String query="";
		String  sumShareSigned="0";
		int noOfRetroCess=0;
		String args[]=null;
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
				query= "premium.select.getNoRetroCess";
				
				args=new String[1];
				args[0]=retroContractNo;
				list=queryImpl.selectList(query,args);
				if (!CollectionUtils.isEmpty(list)) {
					noOfRetroCess =Integer.parseInt((list.get(0).get("RETRO_CESSIONARIES") == null ? ""
							: list.get(0).get("RETRO_CESSIONARIES").toString()))-1;
				
				}
				query="premium.select.getSumOfShareSign";
				args=new String[2];
				args[0]=retroContractNo;
				args[1]= String.valueOf(noOfRetroCess);
				list=queryImpl.selectList(query,args);
				if (!CollectionUtils.isEmpty(list)) {
					sumShareSigned =(list.get(0).get("SHARE_SIGNED") == null ? ""
							: list.get(0).get("SHARE_SIGNED").toString());
				
				}
//				res.setSumShareSigned(sumShareSigned);
				response.setCommonResponse(sumShareSigned);
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
	public GetDepartmentNoRes getDepartmentNo(String contractNo) {
		GetDepartmentNoRes response = new GetDepartmentNoRes();
//		GetDepartmentNoRes1 res = new GetDepartmentNoRes1();
		String deptNo="";
		List<Map<String, Object>> list=new ArrayList<Map<String, Object>>();
		try{
			String query = "GET_DEPARTMENT_NO";
			String args[]=new String[1];
			args[0] = contractNo;
			list=queryImpl.selectList(query,args);
			if (!CollectionUtils.isEmpty(list)) {
				deptNo =(list.get(0).get("RSK_DEPTID") == null ? ""
						: list.get(0).get("RSK_DEPTID").toString());
			
			}
//			res.setDeptNo(deptNo);
			response.setCommonResponse(deptNo);
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
	public GetOSBListRes getOSBList(String transaction, String contractNo, String branchCode) {
		GetOSBListRes response = new GetOSBListRes();
		
		
		List<Map<String,Object>>list=null;
		double sum=0.00;
		try {
			String query="GET_OBS_LIST";
			String [] obj=new String[3];
			obj[0]=transaction;
			obj[1]=contractNo;
			obj[2]=branchCode;
			list=queryImpl.selectList(query,obj);
			if(list!=null && list.size()>0){
				for(int i=0;i<list.size();i++){
					Map<String,Object>tempMap=list.get(i);
					sum+=Double.parseDouble(tempMap.get("OSCLAIM_LOSSUPDATE_OC")==null?"0":tempMap.get("OSCLAIM_LOSSUPDATE_OC").toString());
				}
			}
			response.setCommonResponse(Double.toString(sum));
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
	public InsertPremiumRes insertPremium(InsertPremiumReq req) {
		InsertPremiumRes response = new InsertPremiumRes();
		InsertPremiumRes1 res = new InsertPremiumRes1();
		
		try {
				String query="";
				String[] args = insertArguments(req);
			 	String netDueOc="0";
			 	String RequestNo="";
			 	query="PREMIUM_INSERT_TREATYPREMIUM_TEMP";
		 		netDueOc=args[33];
		 		RequestNo=args[1];
		 		res.setRequestNo(RequestNo);
			 	queryImpl.updateQuery(query, args);
			 	
			 	if("submit".equalsIgnoreCase(req.getButtonStatus())){
			 		res.setTransactionNo(fm.getSequence("Premium",req.getProductId(),req.getDepartmentId(), req.getBranchCode(),"",req.getTransaction()));
					query = "FAC_TEMP_STATUS_UPDATE";
					args = new String[5];
			 		args[0] = "A";
			 		args[1] = req.getLoginId();
			 		args[2] =res.getTransactionNo()==null?"":res.getTransactionNo();
			 		args[3]= res.getRequestNo() ;
			 		args[4]= req.getBranchCode() ;
			 		queryImpl.updateQuery(query, args);
			 		req.setTransactionNo(res.getTransactionNo());
			 		getTempToMainMove(req);
			 		
			 	}
				InsertPremiumReserved(req);
				InsertLossReserved(req);
				response.setCommonResponse(res);
				response.setMessage("Success");
				response.setIsError(false);
		}
		catch (Exception e) {
			e.printStackTrace();
			response.setMessage("Failed");
			response.setIsError(true);
		}
		
		return response;
	

	} 
	private String[] insertArguments(InsertPremiumReq req)
	{
		
		String[] args=null;
			args=new String[94];
			double premiumsurpInsert=0.0;
			double premiumInsert=0.0;
		    args[0]=req.getContNo();
		    args[1] = getRequestNo(req);
			
			args[2]=req.getTransaction();
			args[3]=req.getAccountPeriod();
			args[4]=req.getAccountPeriodyear();
			args[5]=req.getCurrencyId();
			args[6]=req.getExchRate();
			args[7]=req.getBrokerageview();
			args[8]=getModeOfTransaction(req.getBrokerage(),req);
			args[35]=dropDownImple.GetDesginationCountry(args[8], req.getExchRate());
			args[9]=req.getTaxview();
			args[10]=getModeOfTransaction(req.getTax(),req);
			args[36]=dropDownImple.GetDesginationCountry(args[10], req.getExchRate());
			args[67]=getModeOfTransaction(req.getOverrider(),req);
			args[68]=dropDownImple.GetDesginationCountry(args[67],req.getExchRate());
			args[69]=req.getOverRiderview();
	        args[70]=getModeOfTransaction(req.getWithHoldingTaxOC(),req);
	        args[71]=dropDownImple.GetDesginationCountry(args[70], req.getExchRate());
			args[11]=StringUtils.isEmpty(req.getInceptionDate()) ?"" :req.getInceptionDate();
			args[12]=getModeOfTransaction(req.getPremiumQuotaShare(),req);
			args[37]=dropDownImple.GetDesginationCountry(args[12], req.getExchRate());
			args[13]=getModeOfTransaction(req.getCommissionQuotaShare(),req);
			args[38]=dropDownImple.GetDesginationCountry(args[13], req.getExchRate());
			args[14]=getModeOfTransaction(req.getPremiumSurplus(),req);
			args[39]=dropDownImple.GetDesginationCountry(args[14], req.getExchRate());
			args[15]=getModeOfTransaction(req.getCommissionSurplus(),req);
			args[40]=dropDownImple.GetDesginationCountry(args[15], req.getExchRate());
			args[16]=getModeOfTransaction(req.getPremiumportifolioIn(),req);
			args[41]=dropDownImple.GetDesginationCountry(args[16], req.getExchRate());
			args[72]=req.getRiCession();
		
			args[73]= req.getLoginId();
			args[74]=req.getBranchCode();
			args[75]=req.getDepartmentId();
			args[76] = getModeOfTransaction(req.getTaxDedectSource(),req);
			args[77] = dropDownImple.GetDesginationCountry(args[76], req.getExchRate());
			args[78] = getModeOfTransaction(req.getServiceTax(),req);
			args[79] = dropDownImple.GetDesginationCountry(args[78], req.getExchRate());
			args[80] = getModeOfTransaction(req.getSlideScaleCom(),req);
			args[81] = dropDownImple.GetDesginationCountry(args[80], req.getExchRate());
			args[82] = req.getPredepartment();
			args[83] = req.getSubProfitId().replace(" ", "");
			args[84] = req.getAccountPeriodDate();
			args[85] = req.getStatementDate();
			args[86] = req.getOsbYN();
			args[87] = getModeOfTransaction(req.getLossParticipation(),req);
			args[88] = dropDownImple.GetDesginationCountry(args[87], req.getExchRate());
			args[89] = req.getSectionName();
			args[90] = req.getProposalNo();
			args[91] = req.getProductId();
			if("submit".equalsIgnoreCase(req.getButtonStatus())){
				args[92] = "A";
			}else{
				args[92] = "P";
			}
			args[93] = req.getMode();
			

			if(!StringUtils.isEmpty(req.getPremiumQuotaShare())||!StringUtils.isEmpty(req.getPremiumSurplus()))
			{
				

				if(!StringUtils.isEmpty(req.getPremiumQuotaShare()))
				{
					premiumInsert=Double.parseDouble(req.getPremiumQuotaShare());
				}
				if(StringUtils.isEmpty(req.getCommissionQuotaShare()))
				{
					final double commission=premiumInsert*(Double.parseDouble(req.getCommissionview())/100);
					
					args[13]=getModeOfTransaction(commission+" ",req);
					args[38]=dropDownImple.GetDesginationCountry(args[13], req.getExchRate());
				}
				if(!StringUtils.isEmpty(req.getPremiumSurplus()))
				{
					premiumsurpInsert=(Double.parseDouble(req.getPremiumSurplus()));
				}
				if(StringUtils.isEmpty(req.getCommissionSurplus()))
				{
					
					final double comsurp=premiumsurpInsert*(Double.parseDouble(req.getCommssionSurp())/100);
					
					args[15]=getModeOfTransaction(comsurp+" ",req);
					args[40]=dropDownImple.GetDesginationCountry(args[15], req.getExchRate());
				}
				if(StringUtils.isEmpty(req.getBrokerage()))
				{
					final double brokerage=(premiumInsert+premiumsurpInsert)*(Double.parseDouble(req.getBrokerageview())/100);
					args[8]=getModeOfTransaction(brokerage+" ",req);
					args[35]=dropDownImple.GetDesginationCountry(args[8], req.getExchRate());
					
				}
				if(StringUtils.isEmpty(req.getTax()))
				{
					final double tax=(premiumInsert+premiumsurpInsert)*(Double.parseDouble(req.getTaxview())/100);
					args[10]=getModeOfTransaction(tax+" ",req);
					args[36]=dropDownImple.GetDesginationCountry(args[10], req.getExchRate());
					
				}
				if(StringUtils.isEmpty(req.getOverrider()))
				{
					double overrider=(premiumInsert+premiumsurpInsert)*(Double.parseDouble(req.getOverRiderview())/100);
					args[67]=getModeOfTransaction(overrider+" ",req);
					args[68]=dropDownImple.GetDesginationCountry(args[67], req.getExchRate());
					
				}

			}
			
			args[17]=getModeOfTransaction(req.getCliamPortfolioin(),req);
			args[42]=dropDownImple.GetDesginationCountry(args[17], req.getExchRate());
			args[18]=getModeOfTransaction(req.getPremiumportifolioout(),req);
			args[43]=dropDownImple.GetDesginationCountry(args[18], req.getExchRate());
			args[19]=getModeOfTransaction(req.getLossReserveReleased(),req);
			args[44]=dropDownImple.GetDesginationCountry(args[19], req.getExchRate());
			args[20]=getModeOfTransaction(req.getPremiumReserveQuotaShare(),req);
			args[45]=dropDownImple.GetDesginationCountry(args[20], req.getExchRate());
			args[21]=getModeOfTransaction(req.getCashLossCredit(),req);
			args[46]=dropDownImple.GetDesginationCountry(args[21], req.getExchRate());
			args[22]=getModeOfTransaction(req.getLossReserveRetained(),req);
			args[47]=dropDownImple.GetDesginationCountry(args[22], req.getExchRate());
			args[23]=getModeOfTransaction(StringUtils.isBlank(req.getProfitCommission()) ? "0" : req.getProfitCommission(),req);
			args[48]=dropDownImple.GetDesginationCountry(args[23], req.getExchRate());
			args[24]=getModeOfTransaction(req.getCashLossPaid(),req);
			args[49]=dropDownImple.GetDesginationCountry(args[24], req.getExchRate());
			args[25]="Y";
			args[26]="2";
			args[27]=StringUtils.isBlank(req.getReceiptno())?"":req.getReceiptno();
			args[28]=getModeOfTransaction(req.getClaimspaid(),req);
			args[50]=dropDownImple.GetDesginationCountry(args[28], req.getExchRate());
			args[29]=StringUtils.isBlank(req.getSettlementstatus())?"":req.getSettlementstatus();
			args[30]=getModeOfTransaction(req.getXlCost(),req);
			args[51]=dropDownImple.GetDesginationCountry(args[30], req.getExchRate());
			args[31]=getModeOfTransaction(req.getCliamportfolioout(),req);
			args[52]=dropDownImple.GetDesginationCountry(args[31], req.getExchRate());
			args[32]=getModeOfTransaction(req.getPremiumReserveReleased(),req);
			args[53]=dropDownImple.GetDesginationCountry(args[32], req.getExchRate());
			args[34]=getModeOfTransaction(req.getOtherCost(),req);
			args[55]=dropDownImple.GetDesginationCountry(args[34], req.getExchRate());
			
			args[56]=req.getCommissionview();
			args[57]=StringUtils.isBlank(req.getCedentRef())?"":req.getCedentRef();
			args[58]=req.getRemarks();
			args[59]=getModeOfTransaction(req.getTotalCredit(),req);
			args[60]=dropDownImple.GetDesginationCountry(args[59],req.getExchRate());
			args[61]=getModeOfTransaction(req.getTotalDebit(),req);
			args[62]=dropDownImple.GetDesginationCountry(args[61],req.getExchRate());
			args[63]=getModeOfTransaction(req.getInterest(),req);
			args[64]=dropDownImple.GetDesginationCountry(args[63],req.getExchRate());
			args[33]=getNetDueAmount(args,getModeOfTransaction(req.getClaimspaid(),req));
			args[54]=dropDownImple.GetDesginationCountry(args[33], req.getExchRate());
			args[65]=StringUtils.isEmpty(req.getOsClaimsLossUpdateOC())?"0":getModeOfTransaction(req.getOsClaimsLossUpdateOC(),req);
			args[66]=dropDownImple.GetDesginationCountry(args[65], req.getExchRate());

			
			req.setRequestNo(args[1]);
		
		final String[] copiedArray = new String[args.length];
		System.arraycopy(args, 0, copiedArray, 0, args.length);
		
		return copiedArray;
	}
	private  String getRequestNo(InsertPremiumReq req) {
		List<Map<String,Object>>list=null;
		String reqNo = "";
		String name = "";
		try{
			String query="GET_SEQ_NAME";
			list=queryImpl.selectList(query,new String[]{req.getBranchCode()});
			if (!CollectionUtils.isEmpty(list)) {
				name =(list.get(0).get("SEQ_NAME") == null ? ""
						: list.get(0).get("SEQ_NAME").toString());
			
			}
		
			query="SELECT LPAD("+name+".nextval,6,0) REQ_NO FROM DUAL";
			
			list=queryImpl.selectSingle(query,new String[]{});
			if (!CollectionUtils.isEmpty(list)) {
				reqNo =(list.get(0).get("REQ_NO") == null ? ""
						: list.get(0).get("REQ_NO").toString());
			
			}
			
			reqNo ="92"+reqNo;
		}catch(Exception e){
			e.printStackTrace();
		}
		return reqNo;
	}
	private  void getTempToMainMove(InsertPremiumReq req) {
		
		try{
			String query="";
			String[] args = null;
			if(!"Main".equalsIgnoreCase(req.getTableType())){
				
				query ="FAC_PREMIUM_TEMP_TO_MAIN";
		 		args = new String[2];
		 		args[0] = req.getRequestNo();
		 		args[1] = req.getBranchCode();
		 		queryImpl.updateQuery(query, args);
					query="premium.sp.retroSplit";
					
					args = new String[16];
					args[0]=req.getContNo();
					args[1]=StringUtils.isEmpty(req.getLayerno())?"0":req.getLayerno();
					args[2]=req.getProductId();
					args[3]=req.getTransactionNo();
					args[4]=req.getTransaction();
					args[5]=req.getCurrencyId();
					args[6]=req.getExchRate();
					args[7]=req.getBranchCode();
					args[8]="P";
					args[9]=req.getAmendmentDate()==null?"":req.getAmendmentDate();
					args[10]="";
					args[11]="";
					args[12]="";
					args[13]="";
					args[14]="";
					args[15]=req.getRiCession();
					queryImpl.updateQuery(query, args);
					
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
			
		}
	private  void InsertPremiumReserved(InsertPremiumReq req) {
		List<Map<String,Object>>list=null;
		String rLNo="";
		try{
			
			if(req.getInsertPremiumReserved()!=null){
				
		
			if(StringUtils.isNotBlank(req.getPRTransNo())){
//			String[] prTransNo=req.getPRTransNo().split(",");
//			String[] prAmount=req.getPRAmount().split(",");
//			String[] preAmount=req.getPREAmount().split(",");
//			String[] preRate=req.getPRERate().split(",");
			req.setType("PRR");
			
			String sql1="premium.select.MAXRLNo";
			list=queryImpl.selectList(sql1,new String[]{req.getBranchCode()});
			if (!CollectionUtils.isEmpty(list)) {
				rLNo =(list.get(0).get("RL_NO") == null ? ""
						: list.get(0).get("RL_NO").toString());
			
			}
			
			
			for(int i=0;i<req.getInsertPremiumReserved().size();i++) {
				InsertLossReserved request=req.getInsertLossReserved().get(i);
			if(StringUtils.isNotBlank(request.getPRTransNo())){
				GetPremiumReservedReq req1 = new GetPremiumReservedReq();
				req1.setBranchCode(req.getBranchCode());
				req1.setContNo(req.getContNo());
				req1.setCurrencyId(req.getCurrencyId());
				req1.setDepartmentId(req.getDepartmentId());
				req1.setPrTransNo(req.getPRTransNo());
				req1.setTransaction(req.getTransaction());
				req1.setType(req.getType());
				GetPremiumReservedRes1 cashLossList=getPremiumReserved(req1);
				GetPremiumReservedRes form= cashLossList.getCommonResponse().get(0);
				if(request.getPRTransNo().equals(form.getTransactionNo())) {
				String[] obj=new String[18];
				obj[0]="";
				obj[1]=form.getContNo();
				obj[2]=req.getDepartmentId();
				obj[3]="PRR";
				obj[4]=rLNo;
				obj[5]=form.getTransactionNo();
				obj[6]=req.getTransaction();
				obj[7]=request.getPRTransNo();
				obj[8]=form.getPaidDate();
				obj[9]=form.getCurrencyValue();
				obj[10]=req.getCurrency();
				obj[11]=request.getPRAmount();
				obj[12]=request.getPREAmount();
				obj[13]=request.getPRERate();
				obj[14]=req.getLoginId();
				obj[15]=req.getBranchCode();
				if("submit".equalsIgnoreCase(req.getButtonStatus())){
					obj[16] = "A";
				}else{
					obj[16] = "P";
				}
				obj[17] = req.getRequestNo();
			String query="INSERT_PREMIUM_RESERVE";
			queryImpl.updateQuery(query, obj);
			
			
		 
		 	if("submit".equalsIgnoreCase(req.getButtonStatus())){
			 	String sql="UPDATE_PREMIUM_RESERVE";
			 	queryImpl.updateQuery(sql, new String[]{form.getContNo(),form.getRequestNo(),"A",form.getContNo(),form.getTransactionNo()});
			 	
			 	}
			}
				}
			}
			}
			}}catch(Exception e){
			e.printStackTrace();
		}
		
			
		}
	private  void InsertLossReserved(InsertPremiumReq req) {
	
		String prTransNo="";
		String prAmount="";
		String preAmount="";
		String preRate="";
		List<Map<String,Object>>list=null;
		String rLNo="";
		try{	
			if(req.getInsertLossReserved()!=null){
				
//			String[] prTransNo=req.getLRTransNo().split(",");
//			String[] prAmount=req1.getLRAmount().split(",");
//			String[] preAmount=req1.getLREAmount().split(",");
//			String[] preRate=req1.getLRERate().split(",");
			req.setType("LRR");
			
			String sql1="premium.select.RL_NO";
			list=queryImpl.selectList(sql1,new String[]{req.getBranchCode()});
			if (!CollectionUtils.isEmpty(list)) {
				rLNo =(list.get(0).get("RL_NO") == null ? ""
						: list.get(0).get("RL_NO").toString());
			}
			
			for(int i=0;i<req.getInsertLossReserved().size();i++) {
				InsertLossReserved request=req.getInsertLossReserved().get(i);
				
			if(StringUtils.isNotBlank(request.getPRTransNo())){
				GetPremiumReservedReq req1 = new GetPremiumReservedReq();
				req1.setBranchCode(req.getBranchCode());
				req1.setContNo(req.getContNo());
				req1.setCurrencyId(req.getCurrencyId());
				req1.setDepartmentId(req.getDepartmentId());
				req1.setPrTransNo(req.getPRTransNo());
				req1.setTransaction(req.getTransaction());
				req1.setType(req.getType());
				GetPremiumReservedRes1 cashLossList=getPremiumReserved(req1);
			GetPremiumReservedRes form= cashLossList.getCommonResponse().get(0);
			if(request.getPRTransNo().equals(form.getTransactionNo())) {
			String[] obj=new String[18];
			obj[0]="";
			obj[1]=form.getContNo();
			obj[2]=req.getDepartmentId();
			obj[3]="LRR";
			obj[4]=rLNo;
			obj[5]=req.getTransactionNo();
			obj[6]=req.getTransaction();
			obj[7]=request.getPRTransNo();
			obj[8]=form.getPaidDate();
			obj[9]=form.getCurrencyValue();
			obj[10]=req.getCurrency();
			obj[11]=request.getPRAmount();
			obj[12]=request.getPREAmount();
			obj[13]=request.getPRERate();
			obj[14]=req.getLoginId();
			obj[15]=req.getBranchCode();
			if("submit".equalsIgnoreCase(req.getButtonStatus())){
				obj[16] = "A";
			}else{
				obj[16] = "P";
			}
			obj[17] = req.getRequestNo();
			String query="INSERT_PREMIUM_RESERVE";
			queryImpl.updateQuery(query, obj);
		 
		 	if("submit".equalsIgnoreCase(req.getButtonStatus())){
			 	String sql="UPDATE_LOSS_RESERVE";
			 	
				queryImpl.updateQuery(sql, new String[]{form.getContNo(),form.getRequestNo(),"A",form.getContNo(),form.getTransactionNo()});
		 	}
		 	
			}
			}
			}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		
			
		}
	private static String getModeOfTransaction(final String Value,final InsertPremiumReq req) {
		
		String result="0";
		double shareSigned=0.0;
		DecimalFormat twoDForm = new DecimalFormat("#.##");
		if(req.getEnteringMode()!=null)
		{
			if("1".equalsIgnoreCase(req.getEnteringMode()))
			{
				shareSigned=Double.parseDouble(req.getShareSigned());
			}
			else if("2".equalsIgnoreCase(req.getEnteringMode()))
			{
				shareSigned=100;
			}
			
			if(!"".equalsIgnoreCase(Value))
			{
					double finalValue=Double.parseDouble(Value) *shareSigned/100;
					
					result=String.valueOf(Double.valueOf(twoDForm.format(finalValue)));
			}
		}
		
		return result;
		}
	private static String getNetDueAmount(final String[] args,final String CliamPaid) {
		
		double Abt=0;
		double Bbt=0;
		if(StringUtils.isNotEmpty(args[12]))
		{
		Abt+=Double.parseDouble(args[12]);
		}
		if(StringUtils.isNotEmpty(args[14]))
		{
		Abt+=Double.parseDouble(args[14]);
		}
		if(StringUtils.isNotEmpty(args[16]))
		{
		Abt+=Double.parseDouble(args[16]);
		}
		if(StringUtils.isNotEmpty(args[17]))
		{
		Abt+=Double.parseDouble(args[17]);
		}
		if(StringUtils.isNotEmpty(args[19]))
		{
		Abt+=Double.parseDouble(args[19]);
		}
		if(StringUtils.isNotEmpty(args[21]))
		{
		Abt+=Double.parseDouble(args[21]);
		}
		if(StringUtils.isNotEmpty(args[32]))
		{
			Abt+=Double.parseDouble(args[32]);
		}
		if(StringUtils.isNotEmpty(args[63]))
		{
			Abt+=Double.parseDouble(args[63]);
		}
		if(StringUtils.isNotEmpty(args[76]))
		{
		Abt+=Double.parseDouble(args[76]);
		}
		if(StringUtils.isNotEmpty(args[78]))
		{
		Abt+=Double.parseDouble(args[78]);
		}
		
		if(StringUtils.isNotEmpty(args[87]))
		{
		Abt+=Double.parseDouble(args[87]);
		}
		if(StringUtils.isNotEmpty(args[13]))
		{
			Bbt+=Double.parseDouble(args[13]);
		}
		if(StringUtils.isNotEmpty(args[15]))
		{
			Bbt+=Double.parseDouble(args[15]);
		}
		if(StringUtils.isNotEmpty(args[8]))
		{
			Bbt+=Double.parseDouble(args[8]);
		}
		if(StringUtils.isNotEmpty(args[10]))
		{
			Bbt+=Double.parseDouble(args[10]);
		}
		if(StringUtils.isNotEmpty(args[18]))
		{
			Bbt+=Double.parseDouble(args[18]);
		}
		if(StringUtils.isNotEmpty(args[20]))
		{
			Bbt+=Double.parseDouble(args[20]);
		}
		if(StringUtils.isNotEmpty(args[22]))
		{
			Bbt+=Double.parseDouble(args[22]);
		}
		if(StringUtils.isNotEmpty(args[23]))
		{
			Bbt+=Double.parseDouble(args[23]);
		}
		if(StringUtils.isNotEmpty(args[24]))
		{
			Bbt+=Double.parseDouble(args[24]);
		}
		if(StringUtils.isNotEmpty(CliamPaid))
		{
			Bbt+=Double.parseDouble(CliamPaid);
		}
		if(StringUtils.isNotEmpty(args[31]))
		{
			Bbt+=Double.parseDouble(args[31]);
		}
		if(StringUtils.isNotEmpty(args[30]))
		{
			Bbt+=Double.parseDouble(args[30]);
		}
		if(StringUtils.isNotEmpty(args[34]))
		{
			Bbt+=Double.parseDouble(args[34]);
		}
		if(StringUtils.isNotEmpty(args[67]))
		{
			Bbt+=Double.parseDouble(args[67]);
		}
		if(StringUtils.isNotEmpty(args[70]))
		{
			Bbt+=Double.parseDouble(args[70]);
		}
		if(StringUtils.isNotEmpty(args[80]))
		{
			Bbt+=Double.parseDouble(args[80]);
		}
		
	    final double cbt=Abt-Bbt;
	   
		return String.valueOf(cbt);
	}
	
	@Override
	public GetPremiumReservedRes1 getPremiumReserved(GetPremiumReservedReq req) {
		GetPremiumReservedRes1 response = new GetPremiumReservedRes1();
		List<GetPremiumReservedRes> cashLossList = new ArrayList<GetPremiumReservedRes>();
		List<Map<String,Object>> list = null;
		List<Map<String,Object>> list1 = null;
		Double a=0.0;
		Double b=0.0;
		try{
			String selectQry="";
			String[] args = new String[3];
			args[0] = req.getContNo();
			args[1] = req.getDepartmentId();
			args[2] = req.getTransaction();
			if("PRR".equals(req.getType())){
			selectQry = "GET_PREMIUM_RESERVED_DETAILS";
			}
			else if("LRR".equals(req.getType())){
				selectQry = "GET_LOSS_RESERVED_DETAILS";
			}
			if(StringUtils.isNotBlank(req.getPrTransNo())){
				selectQry+=" AND TRANSACTION_NO='"+req.getPrTransNo()+"'";
			}
			
			list = queryImpl.selectList(selectQry,args);
		
			String query="GET_CURRENCY_NAME";
		
			if (list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					GetPremiumReservedRes tempreq = new GetPremiumReservedRes();
					tempreq.setSerialno(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					tempreq.setContNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					tempreq.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
					tempreq.setPaidDate(tempMap.get("TRANSACTION_MONTH_YEAR")==null?"":tempMap.get("TRANSACTION_MONTH_YEAR").toString());
					tempreq.setPayamount(fm.formatter(tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC")==null?"":tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC").toString()));
					tempreq.setCurrencyValue(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					tempreq.setCurrencyId(req.getCurrencyId());
					tempreq.setPrallocatedTillDate(tempMap.get("ALLOCATE_TILLDATE")==null?"":tempMap.get("ALLOCATE_TILLDATE").toString());
					
					list1 = queryImpl.selectList(query,new String[]{req.getBranchCode(), tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString()});
					if (!CollectionUtils.isEmpty(list1)) {
						tempreq.setCurrencyValueName(list1.get(0).get("SHORT_NAME") == null ? ""
								: list1.get(0).get("SHORT_NAME").toString());
					}
					
					
					
					list1 = queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getCurrencyId()});
					if (!CollectionUtils.isEmpty(list1)) {
						tempreq.setCurrencyIdName(list1.get(0).get("SHORT_NAME") == null ? ""
								: list1.get(0).get("SHORT_NAME").toString());
					}
//					if((req.getChkbox()!=null)&& req.getChkbox().equalsIgnoreCase("true")){
//						tempreq.setCheck("true");
//					}
//					MainCreditReq request=req.getMainCredit().get(i);
//					if(StringUtils.isNotBlank(req.getMainclaimPaymentNos()) && req.getMainclaimPaymentNos().contains(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString())){
//						if(tempreq.getCurrencyValue().equalsIgnoreCase(tempreq.getCurrencyId())){
//							tempreq.setStatus("true");
//						}
//						
////						String [] value=req.getMaincreditAmountCLClist().split(",");
////						String [] value1=req.getMaincreditAmountCLDlist().split(",");
////						String [] value2=req.getMainCLCsettlementRatelist().split(",");
////						String [] value3=req.getMainclaimPaymentNos().split(",");
//						
//						tempreq.setCreditAmountCLC(dropDownImple.formatter(request.getMaincreditAmountCLClist()));
//						tempreq.setCreditAmountCLD(dropDownImple.formatter(request.getMaincreditAmountCLDlist()));
//						tempreq.setCLCsettlementRate(request.getMainCLCsettlementRatelist()); 
//						tempreq.setCheck("true");
//					}
//					else if(StringUtils.isNotBlank(req.getMode()) && "error".equals(req.getMode()) ){
//						if(tempreq.getCurrencyValue().equalsIgnoreCase(tempreq.getCurrencyId())){
//							tempreq.setStatus("true");
//						}
//						if(req.getMainCredit()!=null && req.getMainCredit().size()>0 && req.getMainCredit().get(i)!=null){
//						tempreq.setCreditAmountCLC(dropDownImple.formatter(req.getCreditAmountCLClist().get(i).replaceAll(",", "")));
//						}else{
//							tempreq.setCreditAmountCLC("");
//						}
//						if(req.getCreditAmountCLDlist()!=null && req.getCreditAmountCLDlist().size()>0 &&  StringUtils.isNotBlank(req.getCreditAmountCLDlist().get(i))){
//						tempreq.setCreditAmountCLD(dropDownImple.formatter(req.getCreditAmountCLDlist().get(i).replaceAll(",", "")));
//						}else{
//							tempreq.setCreditAmountCLD("");
//						}
//						if(req.getCLCsettlementRatelist()!=null && req.getCLCsettlementRatelist().size()>0 &&  StringUtils.isNotBlank(req.getCLCsettlementRatelist().get(i))){
//						tempreq.setCLCsettlementRate(req.getCLCsettlementRatelist().get(i).replaceAll(",", ""));
//						}else{
//							tempreq.setCLCsettlementRate("");
//						}
//					}
//					else {
//						if(tempreq.getCurrencyValue().equalsIgnoreCase(tempreq.getCurrencyId())){
//							tempreq.setStatus("true");
//							tempreq.setCreditAmountCLC(dropDownImple.formatter(tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC")==null?"":tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC").toString()));
//							tempreq.setCreditAmountCLD(tempreq.getCreditAmountCLC());
//							a=Double.parseDouble(tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC")==null?"0":tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC").toString());
//							b=Double.parseDouble(tempreq.getCreditAmountCLD().replaceAll(",", ""));
//							String c=Double.toString(a/b);
//							tempreq.setCLCsettlementRate(dropDownImple.formattereight(c));
//						}else{
//							tempreq.setCreditAmountCLC(dropDownImple.formatter(tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC")==null?"":tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC").toString()));
//							String RTExchange=dropDownImple.GetExchangeRate(tempreq.getCurrencyValue(),req.getTransaction(),countryId,req.getBranchCode());
//							String RLExchange=dropDownImple.GetExchangeRate(tempreq.getCurrencyId(),req.getTransaction(),countryId,req.getBranchCode());
//							String c=Double.toString(Double.parseDouble(RTExchange)/Double.parseDouble(RLExchange));
//							tempreq.setCLCsettlementRate(dropDownImple.formattereight(c));
//							tempreq.setCreditAmountCLD(dropDownImple.formatter(Double.toString(Double.parseDouble(tempreq.getCreditAmountCLC().replaceAll(",", ""))/Double.parseDouble(c))));
//							//tempreq.setStatus("false");
//						}
//						} 
					
//					}
					cashLossList.add(tempreq);
				}
					
			}

			response.setCommonResponse(cashLossList);
			
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
	public GetPremiumDetailsRes getPremiumDetails(GetPremiumDetailsReq req) {
		List<Map<String,Object>> list = null;
		GetPremiumDetailsRes response = new GetPremiumDetailsRes();
		String query="";
		List<GetPremiumDetailsRes1> resList = new ArrayList<GetPremiumDetailsRes1>();
		try{

			String[] args=new String[3];
	   	  	args[0]=req.getProductId();
		   	args[1]=req.getContractNo();
		   	if("Temp".equalsIgnoreCase(req.getTableType())){
		   		args[2]=req.getRequestNo();
		   		query="PREMIUM_SELECT_TREATYPREMIUMVIEW_TEMP";
		   	}else{
		   		args[2]=req.getTransactionNo();
		   		query="premium.select.treatyPremiumView";
		   	}
		   	list = queryImpl.selectList(query,args);
		   	for (int i = 0; i < list.size(); i++) {
				Map<String, Object> tempMap = (Map<String, Object>) list.get(i);
				GetPremiumDetailsRes1 res = new GetPremiumDetailsRes1();
				res.setContNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
				res.setTransactionNo(tempMap.get("TRANSACTION_NO")==null?"":tempMap.get("TRANSACTION_NO").toString());
				res.setTransaction(tempMap.get("TRANS_DATE")==null?"":tempMap.get("TRANS_DATE").toString());
				res.setBrokerage(tempMap.get("BROKERAGE_AMT_OC")==null?"":dropDownImple.formatter(tempMap.get("BROKERAGE_AMT_OC").toString()));
				res.setTax(tempMap.get("TAX_AMT_OC")==null?"":dropDownImple.formatter(tempMap.get("TAX_AMT_OC").toString()));
				res.setPremiumQuotaShare(tempMap.get("PREMIUM_QUOTASHARE_OC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_QUOTASHARE_OC").toString()));
				res.setCommissionQuotaShare(tempMap.get("COMMISSION_QUOTASHARE_OC")==null?"":dropDownImple.formatter(tempMap.get("COMMISSION_QUOTASHARE_OC").toString()));
				res.setPremiumSurplus(tempMap.get("PREMIUM_SURPLUS_OC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_SURPLUS_OC").toString()));
				res.setCommissionSurplus(tempMap.get("COMMISSION_SURPLUS_OC")==null?"":dropDownImple.formatter(tempMap.get("COMMISSION_SURPLUS_OC").toString()));
				res.setPremiumportifolioIn(tempMap.get("PREMIUM_PORTFOLIOIN_OC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_PORTFOLIOIN_OC").toString()));
				res.setCliamPortfolioin(tempMap.get("CLAIM_PORTFOLIOIN_OC")==null?"":dropDownImple.formatter(tempMap.get("CLAIM_PORTFOLIOIN_OC").toString()));
				res.setPremiumportifolioout(tempMap.get("PREMIUM_PORTFOLIOOUT_OC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_PORTFOLIOOUT_OC").toString()));
					
		
					res.setLossReserveReleased(tempMap.get("LOSS_RESERVE_RELEASED_OC")==null?"":dropDownImple.formatter(tempMap.get("LOSS_RESERVE_RELEASED_OC").toString()));
					res.setPremiumReserveQuotaShare(tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUMRESERVE_QUOTASHARE_OC").toString()));
					res.setCashLossCredit(tempMap.get("CASH_LOSS_CREDIT_OC")==null?"":dropDownImple.formatter(tempMap.get("CASH_LOSS_CREDIT_OC").toString()));
					res.setLossReserveRetained(tempMap.get("LOSS_RESERVERETAINED_OC")==null?"":dropDownImple.formatter(tempMap.get("LOSS_RESERVERETAINED_OC").toString()));
					
					res.setProfitCommission(tempMap.get("PROFIT_COMMISSION_OC")==null?"":dropDownImple.formatter(tempMap.get("PROFIT_COMMISSION_OC").toString()));
					res.setCashLossPaid(tempMap.get("CASH_LOSSPAID_OC")==null?"":dropDownImple.formatter(tempMap.get("CASH_LOSSPAID_OC").toString()));
					res.setNetDue(tempMap.get("NETDUE_OC")==null?"":dropDownImple.formatter(tempMap.get("NETDUE_OC").toString()));
					res.setReceiptno(tempMap.get("RECEIPT_NO")==null?"":tempMap.get("RECEIPT_NO").toString());
				
					res.setClaimsPaid(tempMap.get("CLAIMS_PAID_OC")==null?"":dropDownImple.formatter(tempMap.get("CLAIMS_PAID_OC").toString()));
					res.setInceptionDate(tempMap.get("ENTRY_DATE")==null?"":tempMap.get("ENTRY_DATE").toString());
					res.setXlCost(tempMap.get("XL_COST_OC")==null?"":dropDownImple.formatter(dropDownImple.formatter(tempMap.get("XL_COST_OC").toString())));
					res.setCliamPortfolioOut(tempMap.get("CLAIM_PORTFOLIO_OUT_OC")==null?"":dropDownImple.formatter(tempMap.get("CLAIM_PORTFOLIO_OUT_OC").toString()));
					res.setPremiumReserveReleased(tempMap.get("PREMIUM_RESERVE_REALSED_OC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_RESERVE_REALSED_OC").toString()));
					res.setAccountPeriod(tempMap.get("ACCOUNT_PERIOD_QTR")==null?"":tempMap.get("ACCOUNT_PERIOD_QTR").toString());
					res.setAccountPeriodYear(tempMap.get("ACCOUNT_PERIOD_YEAR")==null?"":tempMap.get("ACCOUNT_PERIOD_YEAR").toString());
					res.setCurrencyId(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					res.setOtherCost(tempMap.get("OTHER_COST_OC")==null?"":dropDownImple.formatter(tempMap.get("OTHER_COST_OC").toString()));
					res.setBrokerageUsd(tempMap.get("BROKERAGE_AMT_DC")==null?"":dropDownImple.formatter(tempMap.get("BROKERAGE_AMT_DC").toString()));
					res.setTaxUsd(tempMap.get("TAX_AMT_DC")==null?"":dropDownImple.formatter(tempMap.get("TAX_AMT_DC").toString()));
					res.setPremiumQuotaShareUsd(tempMap.get("PREMIUM_QUOTASHARE_DC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_QUOTASHARE_DC").toString()));
					res.setCommsissionQuotaShareUsd(tempMap.get("COMMISSION_QUOTASHARE_DC")==null?"":dropDownImple.formatter(tempMap.get("COMMISSION_QUOTASHARE_DC").toString()));
					res.setPremiumSurplusUsd(tempMap.get("PREMIUM_SURPLUS_DC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_SURPLUS_DC").toString()));
					res.setComissionSurplusUsd(tempMap.get("COMMISSION_SURPLUS_DC")==null?"":dropDownImple.formatter(tempMap.get("COMMISSION_SURPLUS_DC").toString()));
					res.setPremiumPortfolioInUsd(tempMap.get("PREMIUM_PORTFOLIOIN_DC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_PORTFOLIOIN_DC").toString()));
					res.setCliamPortfolioUsd(tempMap.get("CLAIM_PORTFOLIOIN_DC")==null?"":dropDownImple.formatter(tempMap.get("CLAIM_PORTFOLIOIN_DC").toString()));
					res.setPremiumPortfolioOutUsd(tempMap.get("PREMIUM_PORTFOLIOOUT_DC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_PORTFOLIOOUT_DC").toString()));
					res.setLossReserveReleasedUsd(tempMap.get("LOSS_RESERVE_RELEASED_DC")==null?"":dropDownImple.formatter(tempMap.get("LOSS_RESERVE_RELEASED_DC").toString()));
					res.setPremiumReserveQuotaShareUsd(tempMap.get("PREMIUMRESERVE_QUOTASHARE_DC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUMRESERVE_QUOTASHARE_DC").toString()));
					res.setCashLossCreditUsd(tempMap.get("CASH_LOSS_CREDIT_DC")==null?"":dropDownImple.formatter(tempMap.get("CASH_LOSS_CREDIT_DC").toString()));
					res.setLossReserveRetainedUsd(tempMap.get("LOSS_RESERVERETAINED_DC")==null?"":dropDownImple.formatter(tempMap.get("LOSS_RESERVERETAINED_DC").toString()));
					res.setProfitCommissionUsd(tempMap.get("PROFIT_COMMISSION_DC")==null?"":dropDownImple.formatter(tempMap.get("PROFIT_COMMISSION_DC").toString()));
					res.setCashLossPaidUsd(tempMap.get("CASH_LOSSPAID_DC")==null?"":dropDownImple.formatter(tempMap.get("CASH_LOSSPAID_DC").toString()));
					res.setClamsPaidUsd(tempMap.get("CLAIMS_PAID_DC")==null?"":dropDownImple.formatter(tempMap.get("CLAIMS_PAID_DC").toString()));
					res.setXlCostUsd(tempMap.get("XL_COST_DC")==null?"":dropDownImple.formatter(tempMap.get("XL_COST_DC").toString()));
					res.setCliamPortfolioOutUsd(tempMap.get("CLAIM_PORTFOLIO_OUT_DC")==null?"":dropDownImple.formatter(tempMap.get("CLAIM_PORTFOLIO_OUT_DC").toString()));
					res.setPremiumReserveReleasedUsd(tempMap.get("PREMIUM_RESERVE_REALSED_DC")==null?"":dropDownImple.formatter(tempMap.get("PREMIUM_RESERVE_REALSED_DC").toString()));
					res.setNetDueUsd(tempMap.get("NETDUE_DC")==null?"":dropDownImple.formatter(tempMap.get("NETDUE_DC").toString()));
					res.setOtherCostUSD(tempMap.get("OTHER_COST_DC")==null?"":dropDownImple.formatter(tempMap.get("OTHER_COST_DC").toString()));
					res.setCedentRef(tempMap.get("CEDANT_REFERENCE")==null?"":tempMap.get("CEDANT_REFERENCE").toString());
					res.setRemarks(tempMap.get("REMARKS")==null?"":tempMap.get("REMARKS").toString());
					res.setTotalCredit(tempMap.get("TOTAL_CR_OC")==null?"":dropDownImple.formatter(tempMap.get("TOTAL_CR_OC").toString()));
					res.setTotalCreditDC(tempMap.get("TOTAL_CR_DC")==null?"":dropDownImple.formatter(tempMap.get("TOTAL_CR_DC").toString()));
					res.setTotalDebit(tempMap.get("TOTAL_DR_OC")==null?"":dropDownImple.formatter(tempMap.get("TOTAL_DR_OC").toString()));
					res.setTotalDebitDC(tempMap.get("TOTAL_DR_DC")==null?"":dropDownImple.formatter(tempMap.get("TOTAL_DR_DC").toString()));
					res.setInterest(tempMap.get("INTEREST_OC")==null?"":dropDownImple.formatter(tempMap.get("INTEREST_OC").toString()));
					res.setInterestDC(tempMap.get("INTEREST_DC")==null?"":dropDownImple.formatter(tempMap.get("INTEREST_DC").toString()));
					res.setOsClaimsLossUpdateOC(tempMap.get("OSCLAIM_LOSSUPDATE_OC")==null?"":dropDownImple.formatter(tempMap.get("OSCLAIM_LOSSUPDATE_OC").toString()));
					res.setOsClaimsLossUpdateDC(tempMap.get("OSCLAIM_LOSSUPDATE_DC")==null?"":dropDownImple.formatter(tempMap.get("OSCLAIM_LOSSUPDATE_DC").toString()));
					res.setOverrider(tempMap.get("OVERRIDER_AMT_OC")==null?"":dropDownImple.formatter(tempMap.get("OVERRIDER_AMT_OC").toString()));
					res.setOverriderUSD(tempMap.get("OVERRIDER_AMT_DC")==null?"":dropDownImple.formatter(tempMap.get("OVERRIDER_AMT_DC").toString()));
					res.setAmendmentDate(tempMap.get("AMENDMENT_DATE")==null?"":tempMap.get("AMENDMENT_DATE").toString());
	                res.setWithHoldingTaxOC(tempMap.get("WITH_HOLDING_TAX_OC")==null?"":dropDownImple.formatter(tempMap.get("WITH_HOLDING_TAX_OC").toString()));
	                res.setWithHoldingTaxDC(tempMap.get("WITH_HOLDING_TAX_DC")==null?"":dropDownImple.formatter(tempMap.get("WITH_HOLDING_TAX_DC").toString()));
	                res.setDueDate(tempMap.get("DUE_DATE")==null?"":tempMap.get("DUE_DATE").toString());
	                res.setCreditsign(tempMap.get("NETDUE_OC")==null?"":tempMap.get("NETDUE_OC").toString());
	                res.setRiCession(tempMap.get("RI_CESSION")==null?"":tempMap.get("RI_CESSION").toString());
	                res.setTaxDedectSource(tempMap.get("TDS_OC")==null?"":dropDownImple.formatter(tempMap.get("TDS_OC").toString()));
					res.setTaxDedectSourceDc(tempMap.get("TDS_DC")==null?"":dropDownImple.formatter(tempMap.get("TDS_DC").toString()));
					res.setServiceTax(tempMap.get("ST_OC")==null?"":dropDownImple.formatter(tempMap.get("ST_OC").toString()));
					res.setServiceTaxDc(tempMap.get("ST_DC")==null?"":dropDownImple.formatter(tempMap.get("ST_DC").toString()));
					res.setLossParticipation(tempMap.get("LPC_OC")==null?"":dropDownImple.formatter(tempMap.get("LPC_OC").toString()));
					res.setLossParticipationDC(tempMap.get("LPC_DC")==null?"":dropDownImple.formatter(tempMap.get("LPC_DC").toString()));
					res.setSlideScaleCom(tempMap.get("SC_COMM_OC")==null?"":dropDownImple.formatter(tempMap.get("SC_COMM_OC").toString()));
					res.setSlideScaleComDC(tempMap.get("SC_COMM_DC")==null?"":dropDownImple.formatter(tempMap.get("SC_COMM_DC").toString()));
					res.setSubProfitId(tempMap.get("SUB")==null?"":tempMap.get("SUB").toString());
					if(!"ALL".equalsIgnoreCase(res.getSubProfitId())){
					res.setSubProfitId(tempMap.get("PREMIUM_SUBCLASS")==null?"":tempMap.get("PREMIUM_SUBCLASS").toString());
					}
					res.setExchRate(dropDownImple.exchRateFormat(tempMap.get("EXCHANGE_RATE")==null?"":dropDownImple.formatter(tempMap.get("EXCHANGE_RATE").toString())));
					res.setStatementDate(tempMap.get("STATEMENT_DATE")==null?"":tempMap.get("STATEMENT_DATE").toString());
					 res.setPremiumClass(tempMap.get("TMAS_DEPARTMENT_NAME")==null?"":tempMap.get("TMAS_DEPARTMENT_NAME").toString());
		                res.setPremiumSubClass(tempMap.get("SUB")==null?"":tempMap.get("SUB").toString());
		                if(!"ALL".equalsIgnoreCase(res.getPremiumSubClass())){
		                	res.setPremiumSubClass(tempMap.get("PREMIUM_SUBCLASS")==null?"":tempMap.get("PREMIUM_SUBCLASS").toString());
		                }
		                res.setOsbYN(tempMap.get("OSBYN")==null?"":tempMap.get("OSBYN").toString());
		                res.setSectionName(tempMap.get("SECTION_NAME")==null?"":tempMap.get("SECTION_NAME").toString());
		                res.setAccDate(tempMap.get("ACCOUNTING_PERIOD_DATE")==null?"":tempMap.get("ACCOUNTING_PERIOD_DATE").toString()) ;
			
		

			 	query="premium.select.sumOfPaidPremium";
			 	list = queryImpl.selectList(query,new String[]{req.getContractNo()});
				if (!CollectionUtils.isEmpty(list)) {
					res.setSumOfPaidPremium(list.get(0).get("PREMIUM_QUOTASHARE_OC") == null ? ""
							: list.get(0).get("PREMIUM_QUOTASHARE_OC").toString());
				}
			
				

		   	if(StringUtils.isNotBlank(res.getCurrencyId())){
				query="premium.select.currency";
				list = queryImpl.selectList(query,new String[]{res.getCurrencyId(),req.getBranchCode()});
				if (!CollectionUtils.isEmpty(list)) {
					res.setCurrency(list.get(0).get("CURRENCY_NAME") == null ? ""
							: list.get(0).get("CURRENCY_NAME").toString());
				}
				
				
				
		   	}
		   	query="premium.select.currecy.name";
		   	list = queryImpl.selectList(query,new String[]{req.getBranchCode()});
			if (!CollectionUtils.isEmpty(list)) {
				res.setCurrencyName(list.get(0).get("COUNTRY_SHORT_NAME") == null ? ""
						: list.get(0).get("COUNTRY_SHORT_NAME").toString());
			}
			resList.add(res);
			}
			response.setCommonResponse(resList);
			response.setMessage("Success");
			response.setIsError(false);
	}
	catch (Exception e) {
		e.printStackTrace();
		response.setMessage("Failed");
		response.setIsError(true);
	}
	
	return response;

	}
	public int getCountAccountPeriod(InsertPremiumReq req) {
		List<Map<String,Object>> list = null;
		int count=0;
		int count1=1;
		try {
			String query="GET_ACCOUNT_PERIOD_COUNT";
			list = queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getAccountPeriodDate(),req.getContNo(),req.getDepartmentId(),req.getContNo(),req.getDepartmentId()});
			if (!CollectionUtils.isEmpty(list)) {
				count= Integer.valueOf(list.get(0).get("COUNT") == null ? ""
						: list.get(0).get("COUNT").toString());
			}
			
			
			if(!(count>=1)){
			query="GET_ACCOUNT_PERIOD_COUNT1";
			list = queryImpl.selectList(query,new String[]{req.getAccountPeriodDate(),req.getContNo(),req.getDepartmentId()});
			if (!CollectionUtils.isEmpty(list)) {
				count1= Integer.valueOf(list.get(0).get("ACCOUNT_PERIOD_COUNT1") == null ? ""
						: list.get(0).get("ACCOUNT_PERIOD_COUNT1").toString());
			}
			
			
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return count1;
	}


//	@Override
//	public PremiumEditRes premiumEdit(PremiumEditReq req) {
//		
//		return null;
//	}


	@Override
	public GetBrokerAndCedingNameRes getBrokerAndCedingName(String contNo, String branchCode) {
		GetBrokerAndCedingNameRes response = new GetBrokerAndCedingNameRes();
		List<GetBrokerAndCedingNameRes1> resList = new ArrayList<GetBrokerAndCedingNameRes1>();
		List<Map<String,Object>> list =new ArrayList<Map<String,Object>>();
		try{
			String query="";
			String args[]=null;
			args=new String[4];
			args[0]=contNo;
			args[1]=branchCode;
			args[2]=contNo;
			args[3]=branchCode;
			query="broker.ceding.name";
			list = queryImpl.selectList(query,args);
			for(int i=0 ; i<list.size() ; i++) {
				Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				GetBrokerAndCedingNameRes1 temp = new GetBrokerAndCedingNameRes1();
				temp.setCutomerId(tempMap.get("CUSTOMER_ID")==null?"":tempMap.get("CUSTOMER_ID").toString());
				temp.setCompanyName(tempMap.get("BROKER")==null?"":tempMap.get("BROKER").toString());
				temp.setAddress(tempMap.get("ADDRESS")==null?"":tempMap.get("ADDRESS").toString());
				resList.add(temp);
				
			}
			response.setCommonResponse(resList);
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
	public GetAllocatedListRes getAllocatedList(String contNo, String transactionNo) {
		GetAllocatedListRes response = new GetAllocatedListRes();
		List<GetAllocatedListRes1> resList = new ArrayList<GetAllocatedListRes1>();
		GetAllocatedListRes2 res2 = new GetAllocatedListRes2();
		
		Double a=0.0;
		try{
			String[] args = new String[4];
			args[0] = contNo;
			args[1] = transactionNo;
			args[2] = contNo;
			args[3] = transactionNo;
			String selectQry = "payment.select.getAlloTransaction";
	
			
			List<Map<String,Object>> list = queryImpl.selectList(selectQry,args);
			
			if (list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					GetAllocatedListRes1 tempreq = new GetAllocatedListRes1();
					tempreq.setSerialNo(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					tempreq.setAllocatedDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
					tempreq.setProductName(tempMap.get("PRODUCT_NAME")==null?"":tempMap.get("PRODUCT_NAME").toString());
					tempreq.setType(tempMap.get("TYPE")==null?"":tempMap.get("TYPE").toString());
					tempreq.setPayAmount(tempMap.get("PAID_AMOUNT")==null?"":tempMap.get("PAID_AMOUNT").toString());
					tempreq.setCurrencyValue(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					tempreq.setAlloccurrencyId(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					tempreq.setAllocateType(tempMap.get("ADJUSTMENT_TYPE")==null?"":tempMap.get("ADJUSTMENT_TYPE").toString());
					tempreq.setStatus((tempMap.get("STATUS")==null?"":tempMap.get("STATUS").toString()));
					tempreq.setPayRecNo(tempMap.get("RECEIPT_NO")==null?"":tempMap.get("RECEIPT_NO").toString());
					tempreq.setSettlementType(tempMap.get("TRANS_TYPE")==null?"":tempMap.get("TRANS_TYPE").toString());
					if(tempreq.getPayRecNo()!=""){
						tempreq.setAllocateType(tempMap.get("ALLOCATE_TYPE")==null?"":tempMap.get("ALLOCATE_TYPE").toString());
				
					}
					a=a+Double.parseDouble(tempMap.get("PAID_AMOUNT")==null?"":tempMap.get("PAID_AMOUNT").toString());
					resList.add(tempreq);
				}
			}
			if(a>0){
				res2.setTotalAmount(fm.formatter(Double.toString(a)));
			}
			else{
				res2.setTotalAmount("");
			}
			res2.setCommonResponse(resList);
			response.setCommonResponse(res2);
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
	public CurrencyListRes currencyList(String branchCode) {
		CurrencyListRes response = new CurrencyListRes();
		
		List<CurrencyListRes1> resList = new ArrayList<CurrencyListRes1>();
		
		List<Map<String,Object>> list=null;
		try{
			String query="";
			String args[]=null;
			args=new String[2];
			args[0]=branchCode;
			args[1]=branchCode;
			query="currency.list";
			list= queryImpl.selectList(query,args);
			if (list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					CurrencyListRes1 res = new CurrencyListRes1();
					res.setCurrencyId(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					res.setShortName(tempMap.get("SHORT_NAME")==null?"":tempMap.get("SHORT_NAME").toString());
					resList.add(res);
				}	
					response.setCommonResponse(resList);
					response.setMessage("Success");
					response.setIsError(false);
			}  }catch (Exception e) {
						log.error(e);
						e.printStackTrace();
						response.setMessage("Failed");
						response.setIsError(true);
					}
				    return response;

	}


	@Override
	public GetCashLossCreditRes getCassLossCredit(InsertPremiumReq req) {
		GetCashLossCreditRes response = new GetCashLossCreditRes();
		List<GetCashLossCreditRes1> cashLossList = new ArrayList<GetCashLossCreditRes1>();
		Double a=0.0;
		Double b=0.0;
		List<Map<String,Object>> list=null;
		List<Map<String,Object>> list1=null;
		String excessRatePercent="";
		
		List<GetCashLossCreditReq1> req1 = req.getGetCashLossCreditReq1();
		try{
			String[] args = new String[3];
			args[0] = req.getContNo();
			args[1] = req.getDepartmentId();
			args[2] = req.getClaimPayNo();
			String selectQry = "GET_CASH_LOSS_CREADIT";
			if(StringUtils.isNotBlank(req.getClaimPayNo())){
				//selectQry+=" AND CLAIM_PAYMENT_NO='"+req.getClaimPayNo()+"'";
				selectQry = "GET_CASH_LOSS_CREADIT";
			}
			list=queryImpl.selectList("GET_EXCESS_RATE_PERCENT",new String[] {});
			if (!CollectionUtils.isEmpty(list)) {
				excessRatePercent = (list.get(0).get("PERCENT") == null ? ""
						: list.get(0).get("PERCENT").toString());
			}
			
			list = queryImpl.selectList(selectQry,args);
			String query="GET_CURRENCY_NAME";
		
			if (list.size()>0) {
				for (int i = 0; i < list.size(); i++) {
					Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
					GetCashLossCreditRes1 tempreq = new GetCashLossCreditRes1();
					tempreq.setSerialNo(tempMap.get("SNO")==null?"":tempMap.get("SNO").toString());
					tempreq.setContNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());
					tempreq.setPaidDate(tempMap.get("INCEPTION_DATE")==null?"":tempMap.get("INCEPTION_DATE").toString());
					tempreq.setClaimNumber(tempMap.get("CLAIM_NO")==null?"":tempMap.get("CLAIM_NO").toString());
					tempreq.setClaimPaymentNo(tempMap.get("CLAIM_PAYMENT_NO")==null?"":tempMap.get("CLAIM_PAYMENT_NO").toString());
					tempreq.setPayAmount(fm.formatter(tempMap.get("PAID_AMOUNT_OC")==null?"":tempMap.get("PAID_AMOUNT_OC").toString()));
					tempreq.setCurrencyValue(tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString());
					tempreq.setExcessRatePercent(excessRatePercent);
					tempreq.setCurrencyId(req.getCurrencyId());
					list1 = queryImpl.selectList(query,new String[]{req.getBranchCode(),tempMap.get("CURRENCY_ID")==null?"":tempMap.get("CURRENCY_ID").toString()});
					if (!CollectionUtils.isEmpty(list1)) {
						tempreq.setCurrencyValueName(list1.get(0).get("SHORT_NAME") == null ? ""
								: list1.get(0).get("SHORT_NAME").toString());
					
					}
					list1 = queryImpl.selectList(query,new String[]{req.getBranchCode(),req.getCurrencyId()});
					if (!CollectionUtils.isEmpty(list1)) {
						tempreq.setCurrencyIdName(list1.get(0).get("SHORT_NAME") == null ? ""
								: list1.get(0).get("SHORT_NAME").toString());
					}
					if(req1.size()>i) {
					if(StringUtils.isNotBlank(req1.get(i).getMainclaimPaymentNos()) && req1.get(i).getMainclaimPaymentNos().contains(tempMap.get("CLAIM_PAYMENT_NO")==null?"":tempMap.get("CLAIM_PAYMENT_NO").toString())){
						if(tempreq.getCurrencyValue().equalsIgnoreCase(tempreq.getCurrencyId())){
							tempreq.setStatus("true");
						}
//						String [] value=req1.getMaincreditAmountCLClist().split(",");
//						String [] value1=req1.getMaincreditAmountCLDlist().split(",");
//						String [] value2=req1.getMainCLCsettlementRatelist().split(",");
//						String [] value3=req1.getMainclaimPaymentNos().split(",");
						tempreq.setCreditAmountCLC(fm.formatter(req1.get(i).getMaincreditAmountCLClist()));
						tempreq.setCreditAmountCLD(fm.formatter(req1.get(i).getMaincreditAmountCLDlist()));
						tempreq.setCLCsettlementRate(req1.get(i).getMainCLCsettlementRatelist());
						tempreq.setCheck("true");
					}
					}
					else if(StringUtils.isNotBlank(req.getMode()) && "error".equals(req.getMode()) ){
						if(tempreq.getCurrencyValue().equalsIgnoreCase(tempreq.getCurrencyId())){
							tempreq.setStatus("true");
						}
						if(req1.get(i).getCreditAmountCLClist()!=null && req1.get(i).getCreditAmountCLClist().length()>0 && StringUtils.isNotBlank(req1.get(i).getCreditAmountCLClist())){
						tempreq.setCreditAmountCLC((req1.get(i).getCreditAmountCLClist()));
						}else{
							tempreq.setCreditAmountCLC("");
						}
						if(req1.get(i).getCreditAmountCLDlist()!=null && req1.get(i).getCreditAmountCLDlist().length()>0 &&  StringUtils.isNotBlank(req1.get(i).getCreditAmountCLDlist())){
						tempreq.setCreditAmountCLD((req1.get(i).getCreditAmountCLDlist()));
						}else{
							tempreq.setCreditAmountCLD("");
						}
						if(req1.get(i).getCLCsettlementRatelist()!=null && req1.get(i).getCLCsettlementRatelist().length()>0 &&  StringUtils.isNotBlank(req1.get(i).getCLCsettlementRatelist())){
						tempreq.setCLCsettlementRate(req1.get(i).getCLCsettlementRatelist());
						}else{
							tempreq.setCLCsettlementRate("");
						}
					}
					else {
						if(tempreq.getCurrencyValue().equalsIgnoreCase(tempreq.getCurrencyId())){
						tempreq.setStatus("true");
						tempreq.setCreditAmountCLCTemp(fm.formatter(tempMap.get("PAID_AMOUNT_OC")==null?"":tempMap.get("PAID_AMOUNT_OC").toString()));
						tempreq.setCreditAmountCLDTemp(tempreq.getCreditAmountCLCTemp());
						a=Double.parseDouble(tempMap.get("PAID_AMOUNT_OC")==null?"0":tempMap.get("PAID_AMOUNT_OC").toString());
						b=Double.parseDouble(tempreq.getCreditAmountCLDTemp().replace(",", ""));
						String c=Double.toString(a/b);
						tempreq.setCLCsettlementRateTemp(c);
							tempreq.setCreditAmountCLC("");
							tempreq.setCreditAmountCLD("");
							tempreq.setCLCsettlementRate("");
						}else{
							tempreq.setCreditAmountCLC("");
							tempreq.setCreditAmountCLD("");
							tempreq.setCLCsettlementRate("");
						}
						} 
					if((req1.get(i).getChkbox()!=null)&& req1.get(i).getChkbox().equalsIgnoreCase("true")){
						tempreq.setCheck("true");
					}
					cashLossList.add(tempreq);
				}
			}
			response.setCommonResponse(cashLossList);
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
	public GetAllocatedTransListRes getAllocatedTransList(String proposalNo) {
		GetAllocatedTransListRes response = new GetAllocatedTransListRes();
	     String query="";
	     List<GetAllocatedTransListRes1>  result=new ArrayList<GetAllocatedTransListRes1>();
	     List<Map<String,Object>>list=new ArrayList<>();
	     try {
			query="GET_ALLOCATED_TRANS_LIST";
			list = queryImpl.selectList(query,new String[]{proposalNo});
			
			 if(list!=null && list.size()>0) {
				 for(int i=0;i<list.size();i++) {
					 GetAllocatedTransListRes1 res=new GetAllocatedTransListRes1();
					 Map<String,Object> tempMap = (Map<String,Object>) list.get(i);
				 
				res.setCreditTrxnNO(tempMap.get("CREDITTRXNNO")==null?"":tempMap.get("CREDITTRXNNO").toString());
				res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());				
				 result.add(res);
				 
				}
			 }
			 response.setCommonResponse(result);
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
	public GetAllocatedCassLossCreditRes getAllocatedCassLossCredit(String proposalNo, String branchCode) {
		GetAllocatedCassLossCreditRes response = new GetAllocatedCassLossCreditRes();
		List<GetAllocatedCassLossCreditRes1>  result=new ArrayList<GetAllocatedCassLossCreditRes1>();
		
	     List<Map<String,Object>>list=new ArrayList<>();
		String query="";
		 List<Map<String,Object>>list1=new ArrayList<>();
			String query1="";
	   
	     try {
			query="GET_ALLOCATED_CASH_LOSS";
			list = queryImpl.selectList(query,new String[]{proposalNo});
			
			 if(list!=null && list.size()>0) {
				 for(int i=0;i<list.size();i++) {
					 GetAllocatedCassLossCreditRes1 res = new GetAllocatedCassLossCreditRes1();
				 Map<String,Object>tempMap=list.get(i);
				 
				 res.setCreditTrxnNo(tempMap.get("CREDITTRXNNO")==null?"":tempMap.get("CREDITTRXNNO").toString());
					res.setContractNo(tempMap.get("CONTRACT_NO")==null?"":tempMap.get("CONTRACT_NO").toString());		
					res.setClaimNo(tempMap.get("CLAIM_NO")==null?"":tempMap.get("CLAIM_NO").toString());
					res.setClaimPaymentNo(tempMap.get("CLAIMPAYMENT_NO")==null?"":tempMap.get("CLAIMPAYMENT_NO").toString());		
					res.setCreditDate(tempMap.get("CREDITDATE")==null?"":tempMap.get("CREDITDATE").toString());
					res.setCldAmount(tempMap.get("CLD_AMOUNT")==null?"":tempMap.get("CLD_AMOUNT").toString());		
					query1 = "GET_CURRENCY_NAME";
					list1 = queryImpl.selectList(query1,new String[]{branchCode,tempMap.get("CLDCURRENCY_ID")==null?"":tempMap.get("CLDCURRENCY_ID").toString()});
					if (!CollectionUtils.isEmpty(list1)) {
						res.setCldCurrencyId(list1.get(0).get("SHORT_NAME") == null ? ""
								: list1.get(0).get("SHORT_NAME").toString());
					
					}
					list1 = queryImpl.selectList(query1,new String[]{branchCode,tempMap.get("CLCCURRENCY_ID")==null?"":tempMap.get("CLCCURRENCY_ID").toString()});
					if (!CollectionUtils.isEmpty(list1)) {
						res.setClcCurrencyId(list1.get(0).get("SHORT_NAME") == null ? ""
								: list1.get(0).get("SHORT_NAME").toString());
					}
					
					res.setCreditAmountClc(tempMap.get("CREDITAMOUNTCLC")==null?"":tempMap.get("CREDITAMOUNTCLC").toString());
					res.setCreditAmountCld(tempMap.get("CREDITAMOUNTCLD")==null?"":tempMap.get("CREDITAMOUNTCLD").toString());		
					res.setExchangeRate(tempMap.get("EXCHANGE_RATE")==null?"":tempMap.get("EXCHANGE_RATE").toString());
					
				 result.add(res);
				}
			 }
			 response.setCommonResponse(result);
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
	public SubmitPremiumReservedRes submitPremiumReserved(SubmitPremiumReservedReq req) {
		SubmitPremiumReservedRes response = new SubmitPremiumReservedRes();
		List<SubmitPremiumReservedRes1> resList = new ArrayList<SubmitPremiumReservedRes1>();
		SubmitPremiumReservedResponse res1 = new SubmitPremiumReservedResponse();
		Map<String,String> cashLossCreditMap = new HashMap<String, String>();
		try{
				double credit=0.00;
				
				if(req.getReqList()!=null && req.getReqList().size()>0) {
				for(int i=0 ; i<req.getReqList().size(); i++) {
					SubmitPremiumReservedReq1 req1 = req.getReqList().get(i);
					if("true".equalsIgnoreCase(req1.getChkbox())) {
						List<SubmitPremiumReservedReq1> filterTrack = req.getReqList().stream().filter( o -> req1.getClaimPaymentNos().equalsIgnoreCase(o.getClaimPaymentNos()) ).collect(Collectors.toList());
						if(!CollectionUtils.isEmpty(filterTrack)) {
							filterTrack.remove(0).getClaimPaymentNos();
							cashLossCreditMap.put(req1.getClaimPaymentNos(), req1.getCreditAmountCLC().replace(",", "")+"~"+req1.getCreditAmountCLD().replace(",", "")+"~"+req1.getCLCsettlementRate().replace(",", ""));
							credit=credit+Double.parseDouble(req1.getCreditAmountCLC().replace(",", ""));
					
						}
						
						if(!CollectionUtils.isEmpty(filterTrack)) {
						//if(cashLossCreditMap.containsKey(req1.getClaimPaymentNos())) {
							cashLossCreditMap.remove(req1.getClaimPaymentNos());
							cashLossCreditMap.put(req1.getClaimPaymentNos(), req1.getCreditAmountCLC().replace(",", "")+"~"+req1.getCreditAmountCLD().replace(",", "")+"~"+req1.getCLCsettlementRate().replace(",", ""));
							credit=credit+Double.parseDouble(req1.getCreditAmountCLC().replace(",", ""));
						}
						else {
							cashLossCreditMap.put(req1.getClaimPaymentNos(), req1.getCreditAmountCLC().replace(",", "")+"~"+req1.getCreditAmountCLD().replace(",", "")+"~"+req1.getCLCsettlementRate().replace(",", ""));
							credit=credit+Double.parseDouble(req1.getCreditAmountCLC().replace(",", ""));
						}
					}
//					else if(cashLossCreditMap!=null && !CollectionUtils.isEmpty(filterTrack)) {
//					else if(cashLossCreditMap!=null && cashLossCreditMap.containsKey(req1.getClaimPaymentNos())) {
//						cashLossCreditMap.remove(req1.getClaimPaymentNos());
//					}
				}
			}
			String value1="";
			String value2="";
			String value3="";
			String value4="";
			GetPremiumReservedReq req1 = new GetPremiumReservedReq();
			GetPremiumReservedReq req2 =req.getPremiumReservedReq();
			req1.setBranchCode(req2.getBranchCode());
			req1.setContNo(req2.getContNo());
			req1.setCurrencyId(req2.getCurrencyId());
			req1.setDepartmentId(req2.getDepartmentId());
			req1.setPrTransNo(req2.getPrTransNo());
			req1.setTransaction(req2.getTransaction());
			req1.setType(req2.getType());
			
			GetPremiumReservedRes1 cashLossList=getPremiumReserved(req1);
		
			
			for(int i=0;i<cashLossList.getCommonResponse().size();i++) {
				GetPremiumReservedRes form= cashLossList.getCommonResponse().get(i);
				SubmitPremiumReservedRes1 res = new SubmitPremiumReservedRes1();
				if(( cashLossCreditMap).containsKey(form.getTransactionNo())) {
					String string3=cashLossCreditMap.get(form.getTransactionNo());
					String[] cashloss=string3.split("~");
					res.setValue1(value1+form.getTransactionNo()+",");
					res.setValue2(value2+cashloss[0]+",");
					res.setValue3(value3+cashloss[1]+",");
					res.setValue4(value4+cashloss[2]+",");
					
				}else{
					res.setValue1(value1+",");
					res.setValue2(value2+",");
					res.setValue3(value3+",");
					res.setValue4(value4+",");
				}
				resList.add(res);
			}
			String value="";
			res1.setCommonResponse1(resList);
			res1.setCreditVal(fm.formatter(Double.toString(credit)));
			
		 response.setCommonResponse(res1);
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
	public GetDepositReleaseCountRes getDepositReleaseCount(String dropDown, String contractNo, String branchCode, String type) {
		GetDepositReleaseCountRes response = new GetDepositReleaseCountRes();
		int res =0;
		  List<Map<String,Object>>list=new ArrayList<>();
		try{
			String sql = "";
			String args[] = new String[4];
			if("cashcountStatus".equalsIgnoreCase(dropDown)){
				args = new String[3];
				args[0] = contractNo;
				args[1] = branchCode;
				args[2] = "P";
				sql = "GET_COUNT_CASHLOSS_PREM";
			}else{
				args[0] = contractNo;
				args[1] = branchCode;
				args[2] = "P";
				args[3] = type;
				sql = "GET_COUNT_DEPOSIT_PREM";
			}
			list = queryImpl.selectList(sql,args);
			if (!CollectionUtils.isEmpty(list)) {
				res = Integer.valueOf(list.get(0).get("COUNT") == null ? ""
						: list.get(0).get("COUNT").toString());
			}
			 response.setCommonResponse(res);
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
	public premiumUpdateMethodRes premiumUpdateMethod(InsertPremiumReq beanObj) {
		premiumUpdateMethodRes response = new premiumUpdateMethodRes();
		premiumUpdateMethodRes1 res = new premiumUpdateMethodRes1();
		String query="";
		try {
			updateAruguments(beanObj);
			String netDueOc="0";
		
			if("Temp".equalsIgnoreCase(beanObj.getTableType())){
				//PREMIUM_UPDATE_TREATYUPDATEPRE_TEMP
				RskPremiumDetailsTemp	 entity = pdTempRepo.findByContractNoAndRequestNo(new BigDecimal(beanObj.getContNo()),new BigDecimal(beanObj.getRequestNo()));	
				netDueOc= entity.getNetdueOc().toString();
			}else{
				//PREMIUM_UPDATE_TREATYUPDATEPRE
				RskPremiumDetails	 entity = pdRepo.findByContractNoAndTransactionNo(new BigDecimal(beanObj.getContNo()),new BigDecimal(beanObj.getTransactionNo()));
				netDueOc= entity.getNetdueOc().toString();
			}
			res.setRequestNo(beanObj.getRequestNo());
			if("Submit".equalsIgnoreCase(beanObj.getButtonStatus()) && "Temp".equalsIgnoreCase(beanObj.getTableType())){
				beanObj.setTransactionNo(fm.getSequence("Premium",beanObj.getProductId(),beanObj.getDepartmentId(), beanObj.getBranchCode(),"",beanObj.getTransaction()));
				//FAC_TEMP_STATUS_UPDATE
				String args[] = new String[5];
				query ="FAC_TEMP_STATUS_UPDATE";
				args = new String[5];
		 		args[0] = "A";
		 		args[1] = beanObj.getLoginId();
		 		args[2] =beanObj.getTransactionNo()==null?"":beanObj.getTransactionNo();
		 		args[3]= beanObj.getRequestNo() ;
		 		args[4]= beanObj.getBranchCode() ;
		 		queryImpl.updateQuery(query, args);
		 		res.setTransactionNo(beanObj.getTransactionNo());
		 		getTempToMainMove(beanObj);

		 		String sql = "UPDATE_TRANSACTION_NO_STATUS";
			 
		 		queryImpl.updateQuery(sql, new String[]{beanObj.getTransactionNo(),"A",beanObj.getContNo(),beanObj.getRequestNo()});
		 	
			 	
				sql="UPDATE_PREMIUM_RESERVE";
			 
				queryImpl.updateQuery(sql, new String[]{beanObj.getContNo(),beanObj.getRequestNo(),"A",beanObj.getContNo(),beanObj.getTransactionNo()});
			 	

			 	sql= "UPDATE_LOSS_RESERVE";
			 
			 	queryImpl.updateQuery(sql, new String[]{beanObj.getContNo(),beanObj.getRequestNo(),"A",beanObj.getContNo(),beanObj.getTransactionNo()});
		 	
			 	sql = "UPDATE_CASHLOSS_STATUS";
			
			 	queryImpl.updateQuery(sql, new String[]{beanObj.getTransactionNo(),"A",beanObj.getContNo(),beanObj.getRequestNo()});
			 	GetCashLossCreditRes cash = getCassLossCredit(beanObj);
			 	 List<GetCashLossCreditRes1> cashLossList=cash.getCommonResponse();
			 	 if(cashLossList!=null) {
			 	 for(int i=0;i<cashLossList.size();i++){
			 		GetCashLossCreditRes1 form= cashLossList.get(0);
			 		 sql= "update.claim.payment";
		           
			 		queryImpl.updateQuery(sql, new String[]{form.getContNo(),beanObj.getBranchCode(),beanObj.getRequestNo(),"A",form.getClaimNumber(),form.getClaimPaymentNo(),form.getContNo(),form.getClaimNumber(),form.getClaimPaymentNo()});
			 	 }
			  }
		
			int spresult= queryImpl.updateQuery("premium.detail.archive",new String[]{beanObj.getContNo(),(StringUtils.isBlank(beanObj.getLayerno())?"0":beanObj.getLayerno()),beanObj.getTransactionNo(),beanObj.getCurrencyId(),beanObj.getExchRate(),netDueOc,beanObj.getDepartmentId(),beanObj.getProductId()});
		
			int update= queryImpl.updateQuery(query,args);
		
			}

		 response.setResponse(res);
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
	@Transactional
	public void updateAruguments(InsertPremiumReq beanObj) throws ParseException {
		
		SimpleDateFormat sdf = new  SimpleDateFormat("dd/MM/yyyy");
		if("Temp".equalsIgnoreCase(beanObj.getTableType())){
			//PREMIUM_UPDATE_TREATYUPDATEPRE_TEMP
			RskPremiumDetailsTemp entity = pdTempRepo.findByContractNoAndRequestNo(new BigDecimal(beanObj.getContNo()),new BigDecimal(beanObj.getRequestNo()));	
			if(entity != null) {
				double premiumsurp=0.0;
				double premium=0.0; 
				entity.setTransactionMonthYear(sdf.parse(beanObj.getTransaction()));		
				entity.setAccountPeriodQtr(beanObj.getAccountPeriod());	
				entity.setAccountPeriodYear(new BigDecimal(beanObj.getAccountPeriodyear()));;
				entity.setCurrencyId(new BigDecimal(beanObj.getCurrencyId()));;
				entity.setExchangeRate(new BigDecimal(beanObj.getExchRate()));;
				entity.setBrokerage(new BigDecimal(beanObj.getBrokerageview()));;
				entity.setBrokerageAmtOc(new BigDecimal(getModeOfTransaction(beanObj.getBrokerage(),beanObj)));;
				entity.setTax(new BigDecimal(beanObj.getTaxview()));;
				entity.setBrokerageAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getBrokerageAmtOc().toString(), beanObj.getExchRate())));	
			
				entity.setTaxAmtOc(new BigDecimal(getModeOfTransaction(beanObj.getTax(),beanObj)));	
				entity.setEntryDateTime(StringUtils.isEmpty(beanObj.getInceptionDate()) ?null :sdf.parse(beanObj.getInceptionDate()));	
				entity.setPremiumQuotashareOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumQuotaShare(),beanObj)));	
				entity.setCommissionQuotashareOc(new BigDecimal(getModeOfTransaction(beanObj.getCommissionQuotaShare(),beanObj)));;
				entity.setPremiumSurplusOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumSurplus(),beanObj)));	
				entity.setCommissionSurplusOc(new BigDecimal(getModeOfTransaction(beanObj.getCommissionSurplus(),beanObj)));;
				entity.setPremiumPortfolioinOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumportifolioIn(),beanObj)));;
				entity.setClaimPortfolioinOc(new BigDecimal(getModeOfTransaction(beanObj.getCliamPortfolioin(),beanObj)));;
				entity.setPremiumPortfoliooutOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumportifolioout(),beanObj)));;
			
				entity.setLossReserveReleasedOc(new BigDecimal(getModeOfTransaction(beanObj.getLossReserveReleased(),beanObj)));;
				entity.setPremiumreserveQuotashareOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumReserveQuotaShare(),beanObj)));	
				entity.setCashLossCreditOc(new BigDecimal(getModeOfTransaction(beanObj.getCashLossCredit(),beanObj)));			
			entity.setLossReserveretainedOc(new BigDecimal(getModeOfTransaction(beanObj.getLossReserveRetained(),beanObj)));;
				entity.setProfitCommissionOc(new BigDecimal(getModeOfTransaction((StringUtils.isEmpty(beanObj.getProfitCommission()) ? "0" : beanObj.getProfitCommission()),beanObj)));;
				entity.setCashLosspaidOc(new BigDecimal(getModeOfTransaction(beanObj.getCashLossPaid(),beanObj)));;
				entity.setEnteringMode("2");
				BigDecimal receiptno = StringUtils.isBlank(beanObj.getReceiptno())? null: new BigDecimal(beanObj.getReceiptno());
				entity.setReceiptNo(receiptno);
				entity.setClaimsPaidOc(new BigDecimal(getModeOfTransaction(beanObj.getClaimspaid(),beanObj)));		
				entity.setPremiumPortfolioinDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumPortfolioinOc().toString(),beanObj.getExchRate())));;
				entity.setClaimPortfolioinDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getClaimPortfolioinOc().toString(), beanObj.getExchRate())));;
				entity.setPremiumPortfoliooutDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumPortfoliooutOc().toString(), beanObj.getExchRate())));
				entity.setLossReserveReleasedDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getLossReserveReleasedOc().toString(), beanObj.getExchRate())));	
				entity.setPremiumreserveQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumreserveQuotashareOc().toString(), beanObj.getExchRate())));	
				entity.setCashLossCreditDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCashLossCreditOc().toString(), beanObj.getExchRate())));	
				if(!StringUtils.isEmpty(beanObj.getPremiumQuotaShare())||!StringUtils.isEmpty(beanObj.getPremiumSurplus()))
				{
					

					if(!StringUtils.isEmpty(beanObj.getPremiumQuotaShare()))
					{
						premium=Double.parseDouble(beanObj.getPremiumQuotaShare());
					}
					if(StringUtils.isEmpty(beanObj.getCommissionQuotaShare()))
					{
						final double commission=premium*(Double.parseDouble(beanObj.getCommissionview())/100);
						entity.setCommissionQuotashareOc(new BigDecimal(getModeOfTransaction(commission+" ",beanObj)));
						entity.setCommissionQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionQuotashareOc().toString(),beanObj.getExchRate())));
						
					}
					if(!StringUtils.isEmpty(beanObj.getPremiumSurplus()))
					{
						premiumsurp=(Double.parseDouble(beanObj.getPremiumSurplus()));
					}
					if(StringUtils.isEmpty(beanObj.getCommissionSurplus()))
					{
					
						double comsurp=premiumsurp*(Double.parseDouble(beanObj.getCommssionSurp())/100);
						entity.setCommissionSurplusOc(new BigDecimal(getModeOfTransaction(comsurp+" ",beanObj)));
						entity.setCommissionSurplusDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionSurplusOc().toString(), beanObj.getExchRate())));
						

					}
					if(StringUtils.isEmpty(beanObj.getBrokerage()))
					{
						double brokerage=(premium+premiumsurp)*(Double.parseDouble(beanObj.getBrokerageview())/100);
						entity.setBrokerageAmtOc(new BigDecimal(getModeOfTransaction(brokerage+" ",beanObj)));
						entity.setBrokerageAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getBrokerageAmtOc().toString(), beanObj.getExchRate())));
					
					}
					if(StringUtils.isEmpty(beanObj.getTax()))
					{
						double tax=(premium+premiumsurp)*(Double.parseDouble(beanObj.getTaxview())/100);
						entity.setTaxAmtOc(new BigDecimal(getModeOfTransaction(tax+" ",beanObj)));	
						entity.setTaxAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTaxAmtOc().toString(), beanObj.getExchRate())))	;
					
					
					}
					if(StringUtils.isEmpty(beanObj.getOverrider()))
					{
						double overrider=(premium+premiumsurp)*(Double.parseDouble(beanObj.getOverRiderview())/100);
						entity.setOverriderAmtOc(new BigDecimal(getModeOfTransaction(overrider+" ",beanObj)));
						entity.setOverriderAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTaxAmtOc().toString(), beanObj.getExchRate())));
						
						
					}
				}
				
				entity.setLossReserveretainedDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getLossReserveretainedOc().toString(), beanObj.getExchRate())));
				entity.setProfitCommissionDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getProfitCommissionOc().toString(), beanObj.getExchRate())));			
				entity.setCashLosspaidDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCashLosspaidOc().toString(), beanObj.getExchRate())));
			entity.setCashLosspaidDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getClaimsPaidOc().toString(), beanObj.getExchRate())));	
			
				
			entity.setSettlementStatus(beanObj.getSettlementstatus());;
			entity.setXlCostOc(new BigDecimal(getModeOfTransaction(beanObj.getXlCost(),beanObj)));	
				entity.setClaimPortfolioOutOc(new BigDecimal(getModeOfTransaction(beanObj.getCliamportfolioout(),beanObj)));
			entity.setPremiumReserveRealsedOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumReserveReleased(),beanObj)));;
			
				entity.setOtherCostOc(new BigDecimal(getModeOfTransaction(beanObj.getOtherCost().toString(),beanObj)));;
				entity.setXlCostDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getXlCostOc().toString(), beanObj.getExchRate())));
				entity.setClaimPortfolioOutDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getClaimPortfolioOutOc().toString(), beanObj.getExchRate())));	
				entity.setPremiumReserveReleaseDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumReserveRealsedOc().toString(), beanObj.getExchRate())));	
				;
				entity.setOtherCostDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getOtherCostOc().toString(), beanObj.getExchRate())));;
				entity.setCedantReference(beanObj.getCedentRef());
				entity.setRemarks(beanObj.getRemarks());	
				entity.setTotalCrOc(new BigDecimal(getModeOfTransaction(beanObj.getTotalCredit(),beanObj)));
				entity.setTotalCrDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTotalCrOc().toString(),beanObj.getExchRate())));;
				entity.setTotalDrOc(new BigDecimal(getModeOfTransaction(beanObj.getTotalDebit(),beanObj)));	
				entity.setTotalDrDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTotalDrOc().toString(),beanObj.getExchRate())));;
				entity.setInterestOc(new BigDecimal(getModeOfTransaction(beanObj.getInterest(),beanObj)));
			entity.setInterestDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getInterestOc().toString(),beanObj.getExchRate())));
				entity.setOsclaimLossupdateOc(StringUtils.isEmpty(beanObj.getOsClaimsLossUpdateOC())?BigDecimal.ZERO:new BigDecimal(getModeOfTransaction(beanObj.getOsClaimsLossUpdateOC().toString(),beanObj)));
				entity.setOsclaimLossupdateDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getOsclaimLossupdateOc().toString(),beanObj.getExchRate())));;
				Date amendmentdate = StringUtils.isBlank(beanObj.getAmendmentDate())? null: (sdf.parse(beanObj.getAmendmentDate()));
				entity.setAmendmentDate(amendmentdate);	
				entity.setWithHoldingTaxOc(StringUtils.isEmpty(beanObj.getWithHoldingTaxOC()) ? BigDecimal.ZERO : new BigDecimal(beanObj.getWithHoldingTaxOC()));
				entity.setWithHoldingTaxDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getWithHoldingTaxOc().toString(), beanObj.getExchRate())));	
				entity.setRiCession(beanObj.getRiCession());		
				entity.setSubClass(new BigDecimal(beanObj.getDepartmentId()));
			entity.setTdsOc(new BigDecimal(getModeOfTransaction(beanObj.getTaxDedectSource(),beanObj)));
				entity.setTdsDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTdsOc().toString(), beanObj.getExchRate())));
				entity.setStOc(new BigDecimal(getModeOfTransaction(beanObj.getServiceTax(),beanObj)));
				entity.setStDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getStOc().toString(), beanObj.getExchRate())));
				entity.setScCommOc(new BigDecimal(getModeOfTransaction(beanObj.getSlideScaleCom(),beanObj)));;
			entity.setScCommDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getScCommOc().toString(), beanObj.getExchRate())));			
				entity.setPremiumClass(beanObj.getPredepartment());	
				entity.setPremiumSubclass(beanObj.getSubProfitId().replace(" ", ""));
				entity.setAccountingPeriodDate(sdf.parse(beanObj.getAccountPeriodDate()));
				entity.setStatementDate(sdf.parse(beanObj.getStatementDate()));
				entity.setOsbyn(beanObj.getOsbYN());	
				entity.setLpcOc(new BigDecimal(getModeOfTransaction(beanObj.getLossParticipation().toString(),beanObj)));
				entity.setLpcDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getLpcOc().toString(), beanObj.getExchRate())));
			entity.setSectionName(beanObj.getSectionName());		
		
		
				
				entity.setTaxAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTaxAmtOc().toString(), beanObj.getExchRate())))	;	
				entity.setOverriderAmtOc(new BigDecimal(getModeOfTransaction(beanObj.getOverrider(),beanObj)));
				entity.setOverriderAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getOverriderAmtOc().toString(),beanObj.getExchRate())));;
				entity.setOverrider(new BigDecimal(beanObj.getOverRiderview()));;
				
			entity.setPremiumQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumQuotashareOc().toString(), beanObj.getExchRate())));;
				entity.setCommissionQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionQuotashareOc().toString(), beanObj.getExchRate())));	
				entity.setPremiumSurplusDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumSurplusOc().toString(), beanObj.getExchRate())));;
				entity.setCommissionSurplusDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionSurplusOc().toString(), beanObj.getExchRate())));;
		
				entity.setNetdueOc(new BigDecimal(updateNetDue(entity,getModeOfTransaction(beanObj.getClaimspaid(),beanObj))));
				entity.setNetdueDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getNetdueOc().toString(), beanObj.getExchRate())));
				 entity.setMovementYn(null);
				 entity.setEntryDate(new Date());
				 pdTempRepo.save(entity);
			} 
			}else{
			//PREMIUM_UPDATE_TREATYUPDATEPRE
			RskPremiumDetails	 entity = pdRepo.findByContractNoAndTransactionNo(new BigDecimal(beanObj.getContNo()),new BigDecimal(beanObj.getTransactionNo()));
			 if(entity != null) {
					double premiumsurp=0.0;
					double premium=0.0; 
					entity.setTransactionMonthYear(sdf.parse(beanObj.getTransaction()));		
					entity.setAccountPeriodQtr(beanObj.getAccountPeriod());	
					entity.setAccountPeriodYear(new BigDecimal(beanObj.getAccountPeriodyear()));;
					entity.setCurrencyId(new BigDecimal(beanObj.getCurrencyId()));;
					entity.setExchangeRate(new BigDecimal(beanObj.getExchRate()));;
					entity.setBrokerage(new BigDecimal(beanObj.getBrokerageview()));;
					entity.setBrokerageAmtOc(new BigDecimal(getModeOfTransaction(beanObj.getBrokerage(),beanObj)));;
					entity.setTax(new BigDecimal(beanObj.getTaxview()));;
					entity.setBrokerageAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getBrokerageAmtOc().toString(), beanObj.getExchRate())));	
				
					entity.setTaxAmtOc(new BigDecimal(getModeOfTransaction(beanObj.getTax(),beanObj)));	
					entity.setEntryDateTime(StringUtils.isEmpty(beanObj.getInceptionDate()) ?null :sdf.parse(beanObj.getInceptionDate()));	
					entity.setPremiumQuotashareOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumQuotaShare(),beanObj)));	
					entity.setCommissionQuotashareOc(new BigDecimal(getModeOfTransaction(beanObj.getCommissionQuotaShare(),beanObj)));;
					entity.setPremiumSurplusOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumSurplus(),beanObj)));	
					entity.setCommissionSurplusOc(new BigDecimal(getModeOfTransaction(beanObj.getCommissionSurplus(),beanObj)));;
					entity.setPremiumPortfolioinOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumportifolioIn(),beanObj)));;
					entity.setClaimPortfolioinOc(new BigDecimal(getModeOfTransaction(beanObj.getCliamPortfolioin(),beanObj)));;
					entity.setPremiumPortfoliooutOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumportifolioout(),beanObj)));;
				
					entity.setLossReserveReleasedOc(new BigDecimal(getModeOfTransaction(beanObj.getLossReserveReleased(),beanObj)));;
					entity.setPremiumreserveQuotashareOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumReserveQuotaShare(),beanObj)));	
					entity.setCashLossCreditOc(new BigDecimal(getModeOfTransaction(beanObj.getCashLossCredit(),beanObj)));			
				entity.setLossReserveretainedOc(new BigDecimal(getModeOfTransaction(beanObj.getLossReserveRetained(),beanObj)));;
					entity.setProfitCommissionOc(new BigDecimal(getModeOfTransaction((StringUtils.isEmpty(beanObj.getProfitCommission()) ? "0" : beanObj.getProfitCommission()),beanObj)));;
					entity.setCashLosspaidOc(new BigDecimal(getModeOfTransaction(beanObj.getCashLossPaid(),beanObj)));;
					entity.setEnteringMode("2");
					entity.setReceiptNo(new BigDecimal(beanObj.getReceiptno()));	
					entity.setClaimsPaidOc(new BigDecimal(getModeOfTransaction(beanObj.getClaimspaid(),beanObj)));		
					entity.setPremiumPortfolioinDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumPortfolioinOc().toString(),beanObj.getExchRate())));;
					entity.setClaimPortfolioinDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getClaimPortfolioinOc().toString(), beanObj.getExchRate())));;
					entity.setPremiumPortfoliooutDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumPortfoliooutOc().toString(), beanObj.getExchRate())));
					entity.setLossReserveReleasedDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getLossReserveReleasedOc().toString(), beanObj.getExchRate())));	
					entity.setPremiumreserveQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumreserveQuotashareOc().toString(), beanObj.getExchRate())));	
					entity.setCashLossCreditDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCashLossCreditOc().toString(), beanObj.getExchRate())));	
					if(!StringUtils.isEmpty(beanObj.getPremiumQuotaShare())||!StringUtils.isEmpty(beanObj.getPremiumSurplus()))
					{
						

						if(!StringUtils.isEmpty(beanObj.getPremiumQuotaShare()))
						{
							premium=Double.parseDouble(beanObj.getPremiumQuotaShare());
						}
						if(StringUtils.isEmpty(beanObj.getCommissionQuotaShare()))
						{
							final double commission=premium*(Double.parseDouble(beanObj.getCommissionview())/100);
							entity.setCommissionQuotashareOc(new BigDecimal(getModeOfTransaction(commission+" ",beanObj)));
							entity.setCommissionQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionQuotashareOc().toString(),beanObj.getExchRate())));
							
						}
						if(!StringUtils.isEmpty(beanObj.getPremiumSurplus()))
						{
							premiumsurp=(Double.parseDouble(beanObj.getPremiumSurplus()));
						}
						if(StringUtils.isEmpty(beanObj.getCommissionSurplus()))
						{
						
							double comsurp=premiumsurp*(Double.parseDouble(beanObj.getCommssionSurp())/100);
							entity.setCommissionSurplusOc(new BigDecimal(getModeOfTransaction(comsurp+" ",beanObj)));
							entity.setCommissionSurplusDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionSurplusOc().toString(), beanObj.getExchRate())));
							

						}
						if(StringUtils.isEmpty(beanObj.getBrokerage()))
						{
							double brokerage=(premium+premiumsurp)*(Double.parseDouble(beanObj.getBrokerageview())/100);
							entity.setBrokerageAmtOc(new BigDecimal(getModeOfTransaction(brokerage+" ",beanObj)));
							entity.setBrokerageAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getBrokerageAmtOc().toString(), beanObj.getExchRate())));
						
						}
						if(StringUtils.isEmpty(beanObj.getTax()))
						{
							double tax=(premium+premiumsurp)*(Double.parseDouble(beanObj.getTaxview())/100);
							entity.setTaxAmtOc(new BigDecimal(getModeOfTransaction(tax+" ",beanObj)));	
							entity.setTaxAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTaxAmtOc().toString(), beanObj.getExchRate())))	;
						
						
						}
						if(StringUtils.isEmpty(beanObj.getOverrider()))
						{
							double overrider=(premium+premiumsurp)*(Double.parseDouble(beanObj.getOverRiderview())/100);
							entity.setOverriderAmtOc(new BigDecimal(getModeOfTransaction(overrider+" ",beanObj)));
							entity.setOverriderAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTaxAmtOc().toString(), beanObj.getExchRate())));
							
							
						}
					}
					
					entity.setLossReserveretainedDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getLossReserveretainedOc().toString(), beanObj.getExchRate())));
					entity.setProfitCommissionDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getProfitCommissionOc().toString(), beanObj.getExchRate())));			
					entity.setCashLosspaidDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCashLosspaidOc().toString(), beanObj.getExchRate())));
				entity.setCashLosspaidDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getClaimsPaidOc().toString(), beanObj.getExchRate())));	
				
					
				entity.setSettlementStatus(beanObj.getSettlementstatus());;
				entity.setXlCostOc(new BigDecimal(getModeOfTransaction(beanObj.getXlCost(),beanObj)));	
					entity.setClaimPortfolioOutOc(new BigDecimal(getModeOfTransaction(beanObj.getCliamportfolioout(),beanObj)));
				entity.setPremiumReserveRealsedOc(new BigDecimal(getModeOfTransaction(beanObj.getPremiumReserveReleased(),beanObj)));;
				
					entity.setOtherCostOc(new BigDecimal(getModeOfTransaction(beanObj.getOtherCost().toString(),beanObj)));;
					entity.setXlCostDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getXlCostOc().toString(), beanObj.getExchRate())));
					entity.setClaimPortfolioOutDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getClaimPortfolioOutOc().toString(), beanObj.getExchRate())));	
					entity.setPremiumReserveReleaseDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumReserveRealsedOc().toString(), beanObj.getExchRate())));	
					;
					entity.setOtherCostDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getOtherCostOc().toString(), beanObj.getExchRate())));;
					entity.setCedantReference(beanObj.getCedentRef());
					entity.setRemarks(beanObj.getRemarks());	
					entity.setTotalCrOc(new BigDecimal(getModeOfTransaction(beanObj.getTotalCredit(),beanObj)));
					entity.setTotalCrDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTotalCrOc().toString(),beanObj.getExchRate())));;
					entity.setTotalDrOc(new BigDecimal(getModeOfTransaction(beanObj.getTotalDebit(),beanObj)));	
					entity.setTotalDrDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTotalDrOc().toString(),beanObj.getExchRate())));;
					entity.setInterestOc(new BigDecimal(getModeOfTransaction(beanObj.getInterest(),beanObj)));
				entity.setInterestDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getInterestOc().toString(),beanObj.getExchRate())));
					entity.setOsclaimLossupdateOc(StringUtils.isEmpty(beanObj.getOsClaimsLossUpdateOC())?BigDecimal.ZERO:new BigDecimal(getModeOfTransaction(beanObj.getOsClaimsLossUpdateOC().toString(),beanObj)));
					entity.setOsclaimLossupdateDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getOsclaimLossupdateOc().toString(),beanObj.getExchRate())));;
					entity.setAmendmentDate(sdf.parse(beanObj.getAmendmentDate()));	
					entity.setWithHoldingTaxOc(StringUtils.isEmpty(beanObj.getWithHoldingTaxOC()) ? BigDecimal.ZERO : new BigDecimal(beanObj.getWithHoldingTaxOC()));
					entity.setWithHoldingTaxDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getWithHoldingTaxOc().toString(), beanObj.getExchRate())));	
					entity.setRiCession(beanObj.getRiCession());		
					entity.setSubClass(new BigDecimal(beanObj.getDepartmentId()));
				entity.setTdsOc(new BigDecimal(getModeOfTransaction(beanObj.getTaxDedectSource(),beanObj)));
					entity.setTdsDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTdsOc().toString(), beanObj.getExchRate())));
					entity.setStOc(new BigDecimal(getModeOfTransaction(beanObj.getServiceTax(),beanObj)));
					entity.setStDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getStOc().toString(), beanObj.getExchRate())));
					entity.setScCommOc(new BigDecimal(getModeOfTransaction(beanObj.getSlideScaleCom(),beanObj)));;
				entity.setScCommDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getScCommOc().toString(), beanObj.getExchRate())));			
					entity.setPremiumClass(beanObj.getPredepartment());	
					entity.setPremiumSubclass(beanObj.getSubProfitId().replace(" ", ""));
					entity.setAccountingPeriodDate(sdf.parse(beanObj.getAccountPeriodDate()));
					entity.setStatementDate(sdf.parse(beanObj.getStatementDate()));
					entity.setOsbyn(beanObj.getOsbYN());	
					entity.setLpcOc(new BigDecimal(getModeOfTransaction(beanObj.getLossParticipation().toString(),beanObj)));
					entity.setLpcDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getLpcOc().toString(), beanObj.getExchRate())));
				entity.setSectionName(beanObj.getSectionName());		
			
			
					
					entity.setTaxAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getTaxAmtOc().toString(), beanObj.getExchRate())))	;	
					entity.setOverriderAmtOc(new BigDecimal(getModeOfTransaction(beanObj.getOverrider(),beanObj)));
					entity.setOverriderAmtDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getOverriderAmtOc().toString(),beanObj.getExchRate())));;
					entity.setOverrider(new BigDecimal(beanObj.getOverRiderview()));;
					
				entity.setPremiumQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumQuotashareOc().toString(), beanObj.getExchRate())));;
					entity.setCommissionQuotashareDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionQuotashareOc().toString(), beanObj.getExchRate())));	
					entity.setPremiumSurplusDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getPremiumSurplusOc().toString(), beanObj.getExchRate())));;
					entity.setCommissionSurplusDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getCommissionSurplusOc().toString(), beanObj.getExchRate())));;
			
					entity.setNetdueOc(new BigDecimal(updateNetDue(entity,getModeOfTransaction(beanObj.getClaimspaid(),beanObj))));
					entity.setNetdueDc(new BigDecimal(dropDownImple.GetDesginationCountry(entity.getNetdueOc().toString(), beanObj.getExchRate())));
					 entity.setMovementYn(null);
					 entity.setEntryDate(new Date());
					 pdRepo.save(entity);
		}
		}
		
	}
	private static String updateNetDue(RskPremiumDetailsTemp entity, String modeOfTransaction) {
		double Aut=0;
		double But=0;
		if(StringUtils.isNotEmpty(entity.getPremiumQuotashareOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getPremiumQuotashareOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumSurplusOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getPremiumSurplusOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumPortfolioinOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getPremiumPortfolioinOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getClaimPortfolioinOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getClaimPortfolioinOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getLossReserveReleasedOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getLossReserveReleasedOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCashLossCreditOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getCashLossCreditOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumReserveRealsedOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getPremiumReserveRealsedOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getInterestOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getInterestOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getTdsOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getTdsOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getStOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getStOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getLpcOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getLpcOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCommissionQuotashareOc().toString()));
		{
			But+=Double.parseDouble(entity.getCommissionQuotashareOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCommissionSurplusOc().toString()));
		{
			But+=Double.parseDouble(entity.getCommissionSurplusOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getBrokerageAmtOc().toString()))
		{
			But+=Double.parseDouble(entity.getBrokerageAmtOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getTaxAmtOc().toString()));
		{
			But+=Double.parseDouble(entity.getTaxAmtOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumPortfoliooutOc().toString()));
		{
			But+=Double.parseDouble(entity.getPremiumPortfoliooutOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumreserveQuotashareOc().toString()));
		{
			But+=Double.parseDouble(entity.getPremiumreserveQuotashareOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getLossReserveretainedOc().toString()));
		{
			But+=Double.parseDouble(entity.getLossReserveretainedOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getProfitCommissionOc().toString()))
		{
			But+=Double.parseDouble(entity.getProfitCommissionOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCashLosspaidOc().toString()));
		{
			But+=Double.parseDouble(entity.getCashLosspaidOc().toString());
		}
		if(StringUtils.isNotEmpty(modeOfTransaction))
		{
			But+=Double.parseDouble(modeOfTransaction);
		}
		if(StringUtils.isNotEmpty(entity.getOtherCostOc().toString()));
		{
			But+=Double.parseDouble(entity.getOtherCostOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getXlCostOc().toString()));
		{
			But+=Double.parseDouble(entity.getXlCostOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getClaimPortfolioOutOc().toString()));
		{
			But+=Double.parseDouble(entity.getClaimPortfolioOutOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getOverriderAmtOc().toString()));
		{
			But+=Double.parseDouble(entity.getOverriderAmtOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getWithHoldingTaxOc().toString()));
		{
			But+=Double.parseDouble(entity.getWithHoldingTaxOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getScCommOc().toString()));
		{
			But+=Double.parseDouble(entity.getScCommOc().toString());
		}
		
	    double cut=Aut-But;
	 
		return String.valueOf(cut);
	}
	private static String updateNetDue(RskPremiumDetails entity,final String claimpaid) {
		double Aut=0;
		double But=0;
		if(StringUtils.isNotEmpty(entity.getPremiumQuotashareOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getPremiumQuotashareOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumSurplusOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getPremiumSurplusOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumPortfolioinOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getPremiumPortfolioinOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getClaimPortfolioinOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getClaimPortfolioinOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getLossReserveReleasedOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getLossReserveReleasedOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCashLossCreditOc().toString()));
		{
		Aut+=Double.parseDouble(entity.getCashLossCreditOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumReserveRealsedOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getPremiumReserveRealsedOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getInterestOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getInterestOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getTdsOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getTdsOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getStOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getStOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getLpcOc().toString()));
		{
			Aut+=Double.parseDouble(entity.getLpcOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCommissionQuotashareOc().toString()));
		{
			But+=Double.parseDouble(entity.getCommissionQuotashareOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCommissionSurplusOc().toString()));
		{
			But+=Double.parseDouble(entity.getCommissionSurplusOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getBrokerageAmtOc().toString()))
		{
			But+=Double.parseDouble(entity.getBrokerageAmtOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getTaxAmtOc().toString()));
		{
			But+=Double.parseDouble(entity.getTaxAmtOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumPortfoliooutOc().toString()));
		{
			But+=Double.parseDouble(entity.getPremiumPortfoliooutOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getPremiumreserveQuotashareOc().toString()));
		{
			But+=Double.parseDouble(entity.getPremiumreserveQuotashareOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getLossReserveretainedOc().toString()));
		{
			But+=Double.parseDouble(entity.getLossReserveretainedOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getProfitCommissionOc().toString()))
		{
			But+=Double.parseDouble(entity.getProfitCommissionOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getCashLosspaidOc().toString()));
		{
			But+=Double.parseDouble(entity.getCashLosspaidOc().toString());
		}
		if(StringUtils.isNotEmpty(claimpaid))
		{
			But+=Double.parseDouble(claimpaid);
		}
		if(StringUtils.isNotEmpty(entity.getOtherCostOc().toString()));
		{
			But+=Double.parseDouble(entity.getOtherCostOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getXlCostOc().toString()));
		{
			But+=Double.parseDouble(entity.getXlCostOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getClaimPortfolioOutOc().toString()));
		{
			But+=Double.parseDouble(entity.getClaimPortfolioOutOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getOverriderAmtOc().toString()));
		{
			But+=Double.parseDouble(entity.getOverriderAmtOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getWithHoldingTaxOc().toString()));
		{
			But+=Double.parseDouble(entity.getWithHoldingTaxOc().toString());
		}
		if(StringUtils.isNotEmpty(entity.getScCommOc().toString()));
		{
			But+=Double.parseDouble(entity.getScCommOc().toString());
		}
		
	    double cut=Aut-But;
	 
		return String.valueOf(cut);
	}
	@Override
	public PremiumEditRes premiumEdit(PremiumEditReq req) {
		PremiumEditRes response = new PremiumEditRes();
		PremiumEditRes1 res = new PremiumEditRes1();
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		 String query="";
		 boolean saveFlag=false;
			 	String[] args = new String[2];
				args[0] = req.getContNo();
		 if("Temp".equalsIgnoreCase(req.getTableType())){
			 query="PTTY_PREMIUM_EDIT_TEMP";
				args[1] = req.getRequestNo();
			}else{
				query="premium.select.treetyXOLPremiumEdit";
				args[1] = req.getTransactionNo();
			}
				try {
					list=queryImpl.selectList(query, args);
					for(int i=0;i<list.size();i++) {
					Map<String,Object> editpremium = (Map<String,Object>)list.get(i);
							res.setTransaction(editpremium.get("TRANS_DATE")==null?"":editpremium.get("TRANS_DATE").toString());
							res.setAccountPeriod(editpremium.get("ACCOUNT_PERIOD_QTR")==null?"":editpremium.get("ACCOUNT_PERIOD_QTR").toString());
							res.setAccountPeriodyear(editpremium.get("ACCOUNT_PERIOD_YEAR")==null?"":editpremium.get("ACCOUNT_PERIOD_YEAR").toString());
							res.setCurrencyId(editpremium.get("CURRENCY_ID")==null?"":editpremium.get("CURRENCY_ID").toString());
							res.setCurrency(editpremium.get("CURRENCY_ID")==null?"":editpremium.get("CURRENCY_ID").toString());
							if(null==editpremium.get("EXCHANGE_RATE")){
								GetCommonValueRes res1 = dropDownImple.GetExchangeRate(req.getCurrencyId(),req.getTransaction(),req.getCountryId(),req.getBranchCode());
							res.setExchRate(res1.getCommonResponse());
							}
							else{
								res.setExchRate(dropDownImple.exchRateFormat(editpremium.get("EXCHANGE_RATE")==null?"":editpremium.get("EXCHANGE_RATE").toString()));
							}
							res.setBrokerage(editpremium.get("BROKERAGE_AMT_OC")==null?"":editpremium.get("BROKERAGE_AMT_OC").toString());
							res.setTax(editpremium.get("TAX_AMT_OC")==null?"":editpremium.get("TAX_AMT_OC").toString());
							res.setPremiumQuotaShare(editpremium.get("PREMIUM_QUOTASHARE_OC")==null?"":editpremium.get("PREMIUM_QUOTASHARE_OC").toString());
							res.setCommissionQuotaShare(editpremium.get("COMMISSION_QUOTASHARE_OC")==null?"":editpremium.get("COMMISSION_QUOTASHARE_OC").toString());
							res.setPremiumSurplus(editpremium.get("PREMIUM_SURPLUS_OC")==null?"":editpremium.get("PREMIUM_SURPLUS_OC").toString());
							res.setCommissionSurplus(editpremium.get("COMMISSION_SURPLUS_OC")==null?"":editpremium.get("COMMISSION_SURPLUS_OC").toString());
							res.setPremiumportifolioIn(editpremium.get("PREMIUM_PORTFOLIOIN_OC")==null?"":editpremium.get("PREMIUM_PORTFOLIOIN_OC").toString());
							res.setCliamPortfolioin(editpremium.get("CLAIM_PORTFOLIOIN_OC")==null?"":editpremium.get("CLAIM_PORTFOLIOIN_OC").toString());
							res.setPremiumportifolioout(editpremium.get("PREMIUM_PORTFOLIOOUT_OC")==null?"":editpremium.get("PREMIUM_PORTFOLIOOUT_OC").toString());
							res.setLossReserveReleased(editpremium.get("LOSS_RESERVE_RELEASED_OC")==null?"":editpremium.get("LOSS_RESERVE_RELEASED_OC").toString());
							res.setPremiumReserveQuotaShare(editpremium.get("PREMIUMRESERVE_QUOTASHARE_OC")==null?"":editpremium.get("PREMIUMRESERVE_QUOTASHARE_OC").toString());
							res.setCashLossCredit(editpremium.get("CASH_LOSS_CREDIT_OC")==null?"":editpremium.get("CASH_LOSS_CREDIT_OC").toString());
							res.setLossReserveRetained(editpremium.get("LOSS_RESERVERETAINED_OC")==null?"":editpremium.get("LOSS_RESERVERETAINED_OC").toString());
							res.setProfitCommission(editpremium.get("PROFIT_COMMISSION_OC")==null?"":editpremium.get("PROFIT_COMMISSION_OC").toString());
							res.setCashLossPaid(editpremium.get("CASH_LOSSPAID_OC")==null?"":editpremium.get("CASH_LOSSPAID_OC").toString());
							res.setStatus(editpremium.get("STATUS")==null?"":editpremium.get("STATUS").toString());
							res.setNetDue(editpremium.get("NETDUE_OC")==null?"":editpremium.get("NETDUE_OC").toString());
							res.setEnteringMode(editpremium.get("ENTERING_MODE")==null?"":editpremium.get("ENTERING_MODE").toString());
							res.setReceiptno(editpremium.get("RECEIPT_NO")==null?"":editpremium.get("RECEIPT_NO").toString());
							res.setClaimspaid(editpremium.get("CLAIMS_PAID_OC")==null?"":editpremium.get("CLAIMS_PAID_OC").toString());
							//bean.setSettlement_status(editPremium.get("SETTLEMENT_STATUS"));
							res.setMdpremium(editpremium.get("M_DPREMIUM_OC")==null?"":editpremium.get("M_DPREMIUM_OC").toString());
							res.setAdjustmentpremium(editpremium.get("ADJUSTMENT_PREMIUM_OC")==null?"":editpremium.get("ADJUSTMENT_PREMIUM_OC").toString());
							res.setRecuirementpremium(editpremium.get("REC_PREMIUM_OC")==null?"":editpremium.get("REC_PREMIUM_OC").toString());
							res.setCommission(editpremium.get("COMMISSION")==null?"":editpremium.get("COMMISSION").toString());
							res.setInstlmentNo(editpremium.get("INSTALMENT_NUMBER")==null?"":editpremium.get("INSTALMENT_NUMBER").toString());
							res.setInceptionDate(editpremium.get("INS_DATE")==null?"":editpremium.get("INS_DATE").toString());
							res.setXlCost(editpremium.get("XL_COST_OC")==null?"":editpremium.get("XL_COST_OC").toString());
							res.setCliamportfolioout(editpremium.get("CLAIM_PORTFOLIO_OUT_OC")==null?"":editpremium.get("CLAIM_PORTFOLIO_OUT_OC").toString());
							res.setPremiumReserveReleased(editpremium.get("PREMIUM_RESERVE_REALSED_OC")==null?"":editpremium.get("PREMIUM_RESERVE_REALSED_OC").toString());
							res.setOtherCost(editpremium.get("OTHER_COST_OC")==null?"":editpremium.get("OTHER_COST_OC").toString());
							res.setCedentRef(editpremium.get("CEDANT_REFERENCE")==null?"":editpremium.get("CEDANT_REFERENCE").toString());
							res.setRemarks(editpremium.get("REMARKS")==null?"":editpremium.get("REMARKS").toString());
							res.setInterest(fm.formatter(editpremium.get("INTEREST_OC")==null?"":editpremium.get("INTEREST_OC").toString()));
							res.setOsClaimsLossUpdateOC(fm.formatter(editpremium.get("OSCLAIM_LOSSUPDATE_OC")==null?"":editpremium.get("OSCLAIM_LOSSUPDATE_OC").toString()));
							res.setOverrider(editpremium.get("OVERRIDER_AMT_OC")==null?"":editpremium.get("OVERRIDER_AMT_OC").toString());
							res.setOverriderUSD(editpremium.get("OVERRIDER_AMT_DC")==null?"":editpremium.get("OVERRIDER_AMT_DC").toString());
							res.setAmendmentDate(editpremium.get("AMENDMENT_DATE")==null?"":editpremium.get("AMENDMENT_DATE").toString());
							res.setWithHoldingTaxOC(fm.formatter(editpremium.get("WITH_HOLDING_TAX_OC")==null?"":editpremium.get("WITH_HOLDING_TAX_OC").toString()));
					        res.setWithHoldingTaxDC(fm.formatter(editpremium.get("WITH_HOLDING_TAX_DC")==null?"":editpremium.get("WITH_HOLDING_TAX_DC").toString()));
					        res.setRicession(editpremium.get("RI_CESSION")==null?"":editpremium.get("RI_CESSION").toString());
					        res.setTaxDedectSource(fm.formatter(editpremium.get("TDS_OC")==null?"":editpremium.get("TDS_OC").toString()));
							res.setTaxDedectSourceDc(fm.formatter(editpremium.get("TDS_DC")==null?"":editpremium.get("TDS_DC").toString()));
							res.setServiceTax(fm.formatter(editpremium.get("ST_OC")==null?"":editpremium.get("ST_OC").toString()));
							res.setServiceTaxDc(fm.formatter(editpremium.get("ST_DC")==null?"":editpremium.get("ST_DC").toString()));
							res.setLossParticipation(fm.formatter(editpremium.get("LPC_OC")==null?"":editpremium.get("LPC_OC").toString()));
							res.setLossParticipationDC(fm.formatter(editpremium.get("LPC_DC")==null?"":editpremium.get("LPC_DC").toString()));
							res.setSlideScaleCom(fm.formatter(editpremium.get("SC_COMM_OC")==null?"":editpremium.get("SC_COMM_OC").toString()));
							res.setSlideScaleComDC(fm.formatter(editpremium.get("SC_COMM_DC")==null?"":editpremium.get("SC_COMM_DC").toString()));
							res.setSubProfitId(editpremium.get("PREMIUM_SUBCLASS")==null?"":editpremium.get("PREMIUM_SUBCLASS").toString());
							res.setPrAllocatedAmount(editpremium.get("PRD_ALLOCATED_TILL_DATE")==null?"":editpremium.get("PRD_ALLOCATED_TILL_DATE").toString());
							res.setLrAllocatedAmount(editpremium.get("LRD_ALLOCATED_TILL_DATE")==null?"":editpremium.get("LRD_ALLOCATED_TILL_DATE").toString());
							res.setStatementDate(editpremium.get("STATEMENT_DATE")==null?"":editpremium.get("STATEMENT_DATE").toString());
							res.setOsbYN(editpremium.get("OSBYN")==null?"":editpremium.get("OSBYN").toString());
							res.setSectionName(editpremium.get("SECTION_NAME")==null?"":editpremium.get("SECTION_NAME").toString());
//							res.setSectionType(editpremium.get("2")==null?"":editpremium.get("TRANS_DATE").toString());
							res.setAccountPeriodDate(editpremium.get("ACCOUNTING_PERIOD_DATE")==null?"":editpremium.get("ACCOUNTING_PERIOD_DATE").toString());
							res.setPredepartment(editpremium.get("PREMIUM_CLASS")==null?"":editpremium.get("PREMIUM_CLASS").toString());
						}
					if(list!=null && list.size()>0)
						saveFlag = true;
					response.setCommonResponse(res);
					response.setMessage("Success");
					response.setIsError(false);
				    }catch (Exception e) {
						e.printStackTrace();
						response.setMessage("Failed");
						response.setIsError(true);
				    }
				return response;
		}
	@Override
	public ViewPremiumDetailsRIRes viewPremiumDetailsRI(ViewPremiumDetailsRIReq req) {
		ViewPremiumDetailsRIRes response = new ViewPremiumDetailsRIRes();
		List<ViewPremiumDetailsRIRes1> resList = new ArrayList<ViewPremiumDetailsRIRes1>();
		try {
			List<RskPremiumDetailsRi> entity =	pdRIRepo.findByTransactionNo(new BigDecimal(req.getTransactionNo()));
			if(entity.size()>0) {
				for(RskPremiumDetailsRi data:entity ) {
					ViewPremiumDetailsRIRes1 res = new ViewPremiumDetailsRIRes1();
					res.setAccountPeriod(data.getAccountPeriodQtr()==null?"":data.getAccountPeriodQtr().toString());	
					res.setAccountPeriodDate(data.getAccountingPeriodDate()==null?"":data.getAccountingPeriodDate().toString());	
					res.setAccountPeriodyear(data.getAccountPeriodYear()==null?"":data.getAccountPeriodYear().toString());	
					res.setAdjustmentpremium(data.getAdjustmentPremiumOc()==null?"":data.getAdjustmentPremiumOc().toString());
					res.setAmendmentDate(data.getAmendmentDate()==null?"":data.getAmendmentDate().toString());
					res.setBrokerage(data.getBrokerage()==null?"":data.getBrokerage().toString());
					res.setCashLossCredit(data.getCashLossCreditOc()==null?"":data.getCashLossCreditOc().toString());
					res.setCashLossPaid(data.getCashLosspaidOc()==null?"":data.getCashLosspaidOc().toString());;
					res.setCedentRef(data.getCedantReference()==null?"":data.getCedantReference ().toString());
					res.setClaimspaid(data.getClaimsPaidOc()==null?"":data.getClaimsPaidOc().toString());
					res.setCliamPortfolioin(data.getClaimPortfolioinOc()==null?"":data.getClaimPortfolioinOc().toString());
					res.setCliamportfolioout(data.getClaimPortfolioOutOc()==null?"":data.getClaimPortfolioOutOc().toString());					
					res.setCommission(data.getCommission()==null?"":data.getCommission().toString());	
					res.setCommissionQuotaShare(data.getCommissionQuotashareOc()==null?"":data.getCommissionQuotashareOc().toString());	
					res.setCommissionSurplus(data.getCommissionSurplusOc()==null?"":data.getCommissionSurplusOc().toString());	
					res.setCurrency(data.getCurrencyId()==null?"":data.getCurrencyId().toString());	
					res.setCurrencyId(data.getCurrencyId()==null?"":data.getCurrencyId().toString());	
					res.setEnteringMode(data.getEnteringMode()==null?"":data.getEnteringMode().toString());
					res.setExchRate(data.getExchangeRate()==null?"":data.getExchangeRate().toString());
					res.setInceptionDate(data.getEntryDate()==null?"":data.getEntryDate().toString());
					res.setInstlmentNo(data.getInstalmentNumber()==null?"":data.getInstalmentNumber().toString());
					res.setInterest(data.getInterestOc()==null?"":data.getInterestOc().toString());
					res.setLossParticipation(data.getLpcOc()==null?"":data.getLpcOc().toString());
					res.setLossParticipationDC(data.getLpcDc()==null?"":data.getLpcDc().toString());
					res.setLossReserveReleased(data.getLossReserveReleasedOc()==null?"":data.getLossReserveReleasedOc().toString());
					res.setLossReserveRetained(data.getLossReserveretainedOc()==null?"":data.getLossReserveretainedOc().toString());
					res.setLrAllocatedAmount(data.getLrdAllocatedTillDate()==null?"":data.getLrdAllocatedTillDate().toString());
					res.setMdpremium(data.getMDpremiumOc()==null?"":data.getMDpremiumOc().toString());
					res.setNetDue(data.getNetdueOc()==null?"":data.getNetdueOc().toString());	
					res.setOsbYN(data.getOsbyn()==null?"":data.getOsbyn().toString());
					res.setOsClaimsLossUpdateOC(data.getOsclaimLossupdateOc()==null?"":data.getOsclaimLossupdateOc().toString());
					res.setOtherCost(data.getOtherCostOc()==null?"":data.getOtherCostOc().toString());
					res.setOverriderUSD(data.getOverriderAmtDc()==null?"":data.getOverriderAmtDc().toString());
					res.setOverrider(data.getOverrider()==null?"":data.getOverrider().toString());
					res.setPrAllocatedAmount(data.getPrdAllocatedTillDate()==null?"":data.getPrdAllocatedTillDate().toString());
					res.setPredepartment(data.getPremiumClass()==null?"":data.getPremiumClass().toString());
					res.setPremiumportifolioIn(data.getPremiumPortfolioinOc()==null?"":data.getPremiumPortfolioinOc().toString());
					res.setPremiumportifolioout(data.getPremiumPortfoliooutOc()==null?"":data.getPremiumPortfoliooutOc().toString());
					res.setPremiumQuotaShare(data.getPremiumQuotashareOc()==null?"":data.getPremiumQuotashareOc().toString());
					res.setPremiumReserveQuotaShare(data.getPremiumreserveQuotashareOc()==null?"":data.getPremiumreserveQuotashareOc().toString());
					res.setPremiumReserveReleased(data.getPremiumReserveRealsedOc()==null?"":data.getPremiumReserveRealsedOc().toString());
					res.setPremiumSurplus(data.getPremiumSubclass()==null?"":data.getPremiumSubclass().toString());
					res.setProfitCommission(data.getProfitCommissionOc()==null?"":data.getProfitCommissionOc().toString());
					res.setReceiptno(data.getReceiptNo()==null?"":data.getReceiptNo().toString());
					res.setRecuirementpremium(data.getRecPremiumOc()==null?"":data.getRecPremiumOc().toString());
					res.setRemarks(data.getRemarks()==null?"":data.getRemarks().toString());
					res.setRicession(data.getRiCession()==null?"":data.getRiCession().toString());
					res.setSectionName(data.getSectionName()==null?"":data.getSectionName().toString());
	//				res.setSectionType(data.gets);
					res.setServiceTax(data.getStOc()==null?"":data.getStOc().toString());
					res.setServiceTaxDc(data.getStDc()==null?"":data.getStDc().toString());
					res.setSlideScaleCom(data.getScCommOc()==null?"":data.getScCommOc().toString());
					res.setSlideScaleComDC(data.getScCommDc()==null?"":data.getScCommDc().toString());
					res.setStatementDate(data.getStatementDate()==null?"":data.getStatementDate().toString());
					res.setStatus(data.getStatus()==null?"":data.getStatus().toString());
					res.setSubProfitId(data.getPremiumSubclass()==null?"":data.getPremiumSubclass().toString());
					res.setTax(data.getTax()==null?"":data.getTax().toString());
					res.setTaxDedectSource(data.getTdsOc()==null?"":data.getTdsOc().toString());
					res.setTaxDedectSourceDc(data.getTdsDc()==null?"":data.getTdsDc().toString());
					res.setTransaction(data.getTransactionNo()==null?"":data.getTransactionNo().toString());
					res.setWithHoldingTaxDC(data.getWithHoldingTaxDc()==null?"":data.getWithHoldingTaxDc().toString());
					res.setWithHoldingTaxOC(data.getWithHoldingTaxOc()==null?"":data.getWithHoldingTaxOc().toString());
					res.setXlCost(data.getXlCostOc()==null?"":data.getXlCostOc().toString());
					resList.add(res);
				}
				
			}
			response.setMessage("Success");
			response.setIsError(false);
		    }catch (Exception e) {
				e.printStackTrace();
				response.setMessage("Failed");
				response.setIsError(true);
		    }
		return response;
	}

	}

	