package com.maan.insurance.service.impl.placement;

import java.io.InputStream;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.Tuple;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Expression;
import javax.persistence.criteria.Order;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maan.insurance.model.entity.ConstantDetail;
import com.maan.insurance.model.entity.NotificationAttachmentDetail;
import com.maan.insurance.model.entity.PersonalInfo;
import com.maan.insurance.model.entity.PersonalInfoContact;
import com.maan.insurance.model.entity.PositionMaster;
import com.maan.insurance.model.entity.TmasDocTypeMaster;
import com.maan.insurance.model.entity.TtrnRiPlacement;
import com.maan.insurance.model.entity.TtrnRiskDetails;
import com.maan.insurance.model.repository.TtrnRiPlacementRepository;
import com.maan.insurance.model.req.placement.GetExistingAttachListReq;
import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
import com.maan.insurance.model.req.placement.GetPlacementInfoListReq;
import com.maan.insurance.model.req.placement.GetReinsurerInfoReq;
import com.maan.insurance.model.req.placement.ReinsListReq;
import com.maan.insurance.model.req.placement.SavePlacingReq;
import com.maan.insurance.model.res.DropDown.CommonResDropDown;
import com.maan.insurance.model.res.DropDown.GetBouquetExistingListRes1;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
import com.maan.insurance.model.res.placement.CommonSaveResList;
import com.maan.insurance.model.res.placement.GetExistingAttachListRes;
import com.maan.insurance.model.res.placement.GetExistingAttachListRes1;
import com.maan.insurance.model.res.placement.GetPlacementInfoListRes;
import com.maan.insurance.model.res.placement.GetPlacementInfoListRes1;
import com.maan.insurance.model.res.placement.GetPlacementNoRes;
import com.maan.insurance.model.res.placement.GetPlacementNoRes1;
import com.maan.insurance.model.res.placement.GetReinsurerInfoRes;
import com.maan.insurance.model.res.placement.GetReinsurerInfoRes1;
import com.maan.insurance.model.res.placement.GetReinsurerInfoResponse;
import com.maan.insurance.model.res.placement.InsertPlacingRes;
import com.maan.insurance.model.res.placement.ProposalInfoRes;
import com.maan.insurance.model.res.placement.ProposalInfoRes1;
import com.maan.insurance.model.res.placement.SavePlacingRes;
import com.maan.insurance.service.impl.QueryImplemention;
import com.maan.insurance.service.impl.Dropdown.DropDownServiceImple;
import com.maan.insurance.service.placement.PlacementService;
import com.maan.insurance.validation.Formatters;

@Service
public class PlacementServiceImple implements PlacementService {
	private Logger log = LogManager.getLogger(PlacementServiceImple.class);
	
	@Autowired
	private QueryImplemention queryImpl;

	@Autowired
	private Formatters fm;
	
	@Autowired
	private DropDownServiceImple  dropDownImple;
	
	@Autowired
	private TtrnRiPlacementRepository ripRepo;
	
	@PersistenceContext
	private EntityManager em;

	private Properties prop = new Properties();
	private Query query1=null;
	
	  public PlacementServiceImple() {
	        try {
	        	InputStream  inputStream = getClass().getClassLoader().getResourceAsStream("OracleQuery.properties");
	        	if (inputStream != null) {
					prop.load(inputStream);
				}
	        	
	        } catch (Exception e) {
	            log.info(e);
	        }
	    }
	  
	@Override
	public GetCommonDropDownRes getMailToList(GetMailToListReq bean) { 
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		String cedeingId="";
		try{
			if("A".equals(bean.getCurrentStatus())  && "PWL".equals(bean.getNewStatus())) {
			//	proposalInfo(bean);
				cedeingId="63".equals(bean.getBrokerCompany())?bean.getCedingCompany():bean.getBrokerCompany();
				
			}else {
				cedeingId="63".equals(bean.getBrokerId())?bean.getReinsurerId():bean.getBrokerId();
			}
			//GET_MAIL_CC_LIST
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<PersonalInfoContact> pm = query.from(PersonalInfoContact.class);

			query.multiselect(pm.get("email").alias("CODE")); 

			Subquery<Long> amend = query.subquery(Long.class); 
			Root<PersonalInfoContact> pms = amend.from(PersonalInfoContact.class);
			amend.select(cb.max(pms.get("amendId")));
			Predicate a1 = cb.equal( pm.get("customerId"), pms.get("customerId"));
			amend.where(a1);

			// Where
			Predicate n1 = cb.equal(pm.get("customerId"), cedeingId);
			Predicate n2 = cb.equal(pm.get("amendId"), amend);
			query.where(n1,n2);
			
			TypedQuery<Tuple> result = em.createQuery(query);
			List<Tuple> list = result.getResultList();
			if(list.size()>0) {
      			for(Tuple data: list) {
      				CommonResDropDown res = new CommonResDropDown();
      				res.setCode(data.get("CODE")==null?"":data.get("CODE").toString());
      				res.setCodeDescription(data.get("CODE")==null?"": data.get("CODE").toString());;
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
	public GetCommonDropDownRes getExistingReinsurerList(GetExistingReinsurerListReq bean) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<TtrnRiPlacement> pm = query.from(TtrnRiPlacement.class);
						
			//reinsurerName
			Subquery<String> reInsurerName = query.subquery(String.class); 
			Root<PersonalInfo> pi = reInsurerName.from(PersonalInfo.class);
		
			Expression<String> firstName1 = cb.concat(pi.get("firstName"), " ");
			reInsurerName.select(cb.concat(firstName1, pi.get("lastName")));
			
			//maxAmend
			Subquery<Long> maxAmend = query.subquery(Long.class); 
			Root<PersonalInfo> pis = maxAmend.from(PersonalInfo.class);
			maxAmend.select(cb.max(pis.get("amendId")));
			Predicate b1 = cb.equal( pis.get("customerId"), pi.get("customerId"));
			maxAmend.where(b1);
			
			Predicate a1 = cb.equal( pi.get("customerType"), "R");
			Predicate a2 = cb.equal( pi.get("customerId"), pm.get("reinsurerId"));
			Predicate a3 = cb.equal( pi.get("branchCode"), pm.get("branchCode"));
			Predicate a4 = cb.equal( pi.get("amendId"), maxAmend);
			reInsurerName.where(a1,a2,a3,a4);
			
			query.multiselect(pm.get("reinsurerId").alias("REINSURER_ID"),reInsurerName.alias("REINSURER_NAME")).distinct(true); 
			
			
			Predicate n1 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
			if(StringUtils.isNotBlank(bean.getBouquetNo())) {
				//GET_EX_REINSURER_BOUQUET_LIST
				Predicate n2 = cb.equal(pm.get("bouquetNo"), bean.getBouquetNo());
				query.where(n1,n2);
			}else if(StringUtils.isNotBlank(bean.getBaseProposalNo())) {
				//GET_EX_REINSURER_BASE_LIST
				Predicate n2 = cb.equal(pm.get("baseProposalNo"), bean.getBaseProposalNo());
				query.where(n1,n2);
			}else {
				//GET_EX_REINSURER_PRO_LIST
				Predicate n2 = cb.equal(pm.get("proposalNo"), StringUtils.isBlank(bean.getEproposalNo())?bean.getProposalNo():bean.getEproposalNo());
				query.where(n1,n2);
			}
			TypedQuery<Tuple> result = em.createQuery(query);
			List<Tuple> list = result.getResultList();
			
			if(list.size()>0) {
				for(Tuple data: list) {
      				CommonResDropDown res = new CommonResDropDown();
      				res.setCode(data.get("REINSURER_ID")==null?"":data.get("REINSURER_ID").toString());
      				res.setCodeDescription(data.get("REINSURER_NAME")==null?"": data.get("REINSURER_NAME").toString());;
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
	public GetCommonDropDownRes getExistingBrokerList(GetExistingReinsurerListReq bean) {
		GetCommonDropDownRes response = new GetCommonDropDownRes();
		List<CommonResDropDown> resList = new ArrayList<CommonResDropDown>();
		try {
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<TtrnRiPlacement> pm = query.from(TtrnRiPlacement.class);
						
			//brokerName
			Subquery<String> brokerName = query.subquery(String.class); 
			Root<PersonalInfo> pi = brokerName.from(PersonalInfo.class);
		
			Expression<String> firstName1 = cb.concat(pi.get("firstName"), " ");
			brokerName.select(cb.concat(firstName1, pi.get("lastName")));
			
			//maxAmend
			Subquery<Long> maxAmend = query.subquery(Long.class); 
			Root<PersonalInfo> pis = maxAmend.from(PersonalInfo.class);
			maxAmend.select(cb.max(pis.get("amendId")));
			Predicate b1 = cb.equal( pis.get("customerId"), pi.get("customerId"));
			maxAmend.where(b1);
			
			Predicate a1 = cb.equal( pi.get("customerType"), "B");
			Predicate a2 = cb.equal( pi.get("customerId"), pm.get("brokerId"));
			Predicate a3 = cb.equal( pi.get("branchCode"), pm.get("branchCode"));
			Predicate a4 = cb.equal( pi.get("amendId"), maxAmend);
			brokerName.where(a1,a2,a3,a4);
			
			query.multiselect(pm.get("brokerId").alias("BROKER_ID"),brokerName.alias("BROKER_NAME")).distinct(true); 
			
			Predicate n1 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
			if(StringUtils.isNotBlank(bean.getBouquetNo())) {
				//GET_EX_BROKER_BOUQUET_LIST
				Predicate n2 = cb.equal(pm.get("bouquetNo"), bean.getBouquetNo());
				query.where(n1,n2);
			}else if(StringUtils.isNotBlank(bean.getBaseProposalNo())) {
				//GET_EX_BROKER_BASE_LIST
				Predicate n2 = cb.equal(pm.get("baseProposalNo"), bean.getBaseProposalNo());
				query.where(n1,n2);
			}else {
				//GET_EX_BROKER_PRO_LIST
				Predicate n2 = cb.equal(pm.get("proposalNo"), StringUtils.isBlank(bean.getEproposalNo())?bean.getProposalNo():bean.getEproposalNo());
				query.where(n1,n2);
			}
			TypedQuery<Tuple> result = em.createQuery(query);
			List<Tuple> list = result.getResultList();
			
			if(list.size()>0) {
				for(Tuple data: list) {
      				CommonResDropDown res = new CommonResDropDown();
      				res.setCode(data.get("BROKER_ID")==null?"":data.get("BROKER_ID").toString());
      				res.setCodeDescription(data.get("BROKER_NAME")==null?"": data.get("BROKER_NAME").toString());;
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
	public GetExistingAttachListRes getExistingAttachList(GetExistingAttachListReq bean) {
		GetExistingAttachListRes response = new GetExistingAttachListRes();
		List<GetExistingAttachListRes1> resList = new ArrayList<GetExistingAttachListRes1>();
		try {
				//GET_EX_DOC_PRO_LIST
				CriteriaBuilder cb = em.getCriteriaBuilder(); 
				CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
				Root<NotificationAttachmentDetail> pm = query.from(NotificationAttachmentDetail.class);
							
				//DOC_TYPE
				Subquery<String> docName = query.subquery(String.class); 
				Root<TmasDocTypeMaster> pi = docName.from(TmasDocTypeMaster.class);
			
				docName.select( pi.get("docName"));
				
				//maxAmend
				Subquery<Long> maxAmend = query.subquery(Long.class); 
				Root<TmasDocTypeMaster> pis = maxAmend.from(TmasDocTypeMaster.class);
				maxAmend.select(cb.max(pis.get("amendId")));
				Predicate b1 = cb.equal( pis.get("docType"), pi.get("docType"));
				Predicate b2 = cb.equal( pis.get("branchCode"), pi.get("branchCode"));
				Predicate b3 = cb.equal( pis.get("moduleType"), pi.get("moduleType"));
				Predicate b4 = cb.equal( pis.get("productId"), pi.get("productId"));
				maxAmend.where(b1,b2,b3,b4);
				
				Predicate a1 = cb.equal( pi.get("productId"), "12");
				Predicate a2 = cb.equal( pi.get("moduleType"), "PL");
				Predicate a3 = cb.equal( pi.get("branchCode"), pm.get("branchCode"));
				Predicate a4 = cb.equal( pi.get("status"), "Y");
				Predicate a5 = cb.equal( pi.get("amendId"), maxAmend);
				Predicate a6 = cb.equal( pi.get("docType"), pm.get("docType"));
				docName.where(a1,a2,a3,a4,a5,a6);
				
				query.multiselect(pm.get("docId").alias("DOC_ID"),docName.alias("DOC_TYPE"),
						pm.get("docDescription").alias("DOC_DESCRIPTION"),pm.get("orgFileName").alias("ORG_FILE_NAME")
						,pm.get("ourFileName").alias("OUR_FILE_NAME")).distinct(true); 
				
				Predicate n1 = cb.equal(pm.get("branchCode"), bean.getBranchCode());
				
				Expression<String> e0 = pm.get("docType");
				Predicate n2 = e0.in("MA","MA2").not();

				Predicate n3 = cb.equal(pm.get("proposalNo"), StringUtils.isBlank(bean.getEproposalNo())?bean.getProposalNo():bean.getEproposalNo());
				Predicate n4 = cb.equal(pm.get("reinsurerId"), bean.getReinsurerId());
				Predicate n5 = cb.equal(pm.get("brokerId"), bean.getBrokerId());
				Predicate n6 = cb.equal(pm.get("correspondentId"), bean.getCorresId());
				query.where(n1,n2,n3,n4,n5,n6);
				
				TypedQuery<Tuple> result = em.createQuery(query);
				List<Tuple> list = result.getResultList();
		
				if(list.size()>0) {
					for(Tuple data: list) {
						GetExistingAttachListRes1 res = new GetExistingAttachListRes1();
						res.setDocDescription(data.get("DOC_DESCRIPTION")==null?"":data.get("DOC_DESCRIPTION").toString());
						res.setDocId(data.get("DOC_ID")==null?"":data.get("DOC_ID").toString());
						res.setDocType(data.get("DOC_TYPE")==null?"":data.get("DOC_TYPE").toString());
						res.setOrgFileName(data.get("ORG_FILE_NAME")==null?"":data.get("ORG_FILE_NAME").toString());
						res.setOurFileName(data.get("OUR_FILE_NAME")==null?"":data.get("OUR_FILE_NAME").toString());						
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
	public ProposalInfoRes proposalInfo(String branchCode, String proposalNo, String eProposalNo) {
		ProposalInfoRes response = new ProposalInfoRes();
		ProposalInfoRes1 bean = new ProposalInfoRes1();
		try {
			String proposal=StringUtils.isBlank(eProposalNo)?proposalNo:eProposalNo;
			//GET_EXISTING_PROPOSAL
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<PositionMaster> pm = query.from(PositionMaster.class);
			Root<TtrnRiskDetails> rd = query.from(TtrnRiskDetails.class); //'New' POLICY_STATUS,''EXISTING_SHARE
			
			// MAXAmend ID
			Subquery<String> companyName = query.subquery(String.class); 
			Root<PersonalInfo> pms = companyName.from(PersonalInfo.class);
			companyName.select(pms.get("companyName"));
			Predicate a1 = cb.equal( rd.get("rskCedingid"), pms.get("customerId"));
			Predicate a2 = cb.equal( pms.get("customerType"), "C");
			Predicate a3 = cb.equal( pm.get("branchCode"), pms.get("branchCode"));
			companyName.where(a1,a2,a3);
			
			//baseLayer
			Subquery<String> baseLayer = query.subquery(String.class); 
			Root<PositionMaster> bs = baseLayer.from(PositionMaster.class);
			baseLayer.select(bs.get("baseLayer")).distinct(true);
			Predicate b1 = cb.equal( bs.get("baseLayer"),  pm.get("baseLayer")==null?pm.get("proposalNo"):pm.get("baseLayer"));
			baseLayer.where(b1);
			
			
			//treatyType
			Subquery<String> treatyType = query.subquery(String.class); 
			Root<ConstantDetail> cd = treatyType.from(ConstantDetail.class);
			treatyType.select(cd.get("detailName"));
			Predicate c1 =  cb.equal(cb.selectCase().when(cb.equal(pm.get("productId") ,"2") ,new BigDecimal(43) ).otherwise(new BigDecimal(29)) ,
					  cd.get("categoryId"));
			Predicate c2 = cb.equal(cb.selectCase().when(cb.equal(pm.get("productId") ,"2") ,rd.get("treatytype") ).otherwise(rd.get("rskBusinessType")) ,
					  cd.get("type"));	
			treatyType.where(c1,c2);

			query.multiselect(rd.get("rskInceptionDate").alias("INS_DATE"),rd.get("rskExpiryDate").alias("EXP_DATE"),
					companyName.alias("COMPANY_NAME"),pm.get("uwYear").alias("UW_YEAR"),
					pm.get("uwYearTo").alias("UW_YEAR_TO"),pm.get("contractNo").alias("CONTRACT_NO"),
					baseLayer.alias("BASE_LAYER"),pm.get("layerNo").alias("LAYER_NO"),pm.get("sectionNo").alias("SECTION_NO"),
					pm.get("proposalNo").alias("PROPOSAL_NO"),
					rd.get("rskTreatyid").alias("RSK_TREATYID"),treatyType.alias("TREATY_TYPE"),
					pm.get("bouquetNo").alias("BOUQUET_NO"),pm.get("bouquetModeYn").alias("Bouquet_Mode_YN"),
					pm.get("offerNo").alias("OFFER_NO"),rd.get("rskCedingid").alias("RSK_CEDINGID"),
					rd.get("rskBrokerid").alias("RSK_BROKERID"),pm.get("amendId").alias("AMEND_ID")); 

			//Order By
			List<Order> orderList = new ArrayList<Order>();
			orderList.add(cb.asc(pm.get("offerNo")));
			orderList.add(cb.asc(pm.get("proposalNo")));

			// Where
			Predicate n1 = cb.equal(pm.get("branchCode"), branchCode);
			Predicate n2 = cb.equal(pm.get("proposalNo"), rd.get("rskProposalNumber"));
			Predicate n3 = cb.equal(pm.get("proposalNo"), proposal);
			Predicate n4 = cb.equal(pm.get("contractStatus"), "P");
			query.where(n1,n2,n3,n4).orderBy(orderList);
			
			// Get Result
			TypedQuery<Tuple> res = em.createQuery(query);
			List<Tuple> list = res.getResultList();
		
			if(list.size()>0) {
				Tuple map=list.get(0);
				bean.setCedingCompanyName(map.get("COMPANY_NAME")==null?"":map.get("COMPANY_NAME").toString());
				bean.setCedingCompany(map.get("RSK_CEDINGID")==null?"":map.get("RSK_CEDINGID").toString());
				bean.setBrokerCompany(map.get("RSK_BROKERID")==null?"":map.get("RSK_BROKERID").toString());
				bean.setTreatyName(map.get("TREATY_TYPE")==null?"":map.get("TREATY_TYPE").toString());
				bean.setInceptionDate(map.get("INS_DATE")==null?"":map.get("INS_DATE").toString());
				bean.setExpiryDate(map.get("EXP_DATE")==null?"":map.get("EXP_DATE").toString());
				bean.setUwYear(map.get("UW_YEAR")==null?"":map.get("UW_YEAR").toString());
				bean.setUwYearTo(map.get("UW_YEAR_TO")==null?"":map.get("UW_YEAR_TO").toString());
				bean.setBouquetModeYN(map.get("BOUQUET_MODE_YN")==null?"":map.get("BOUQUET_MODE_YN").toString());
				bean.setBouquetNo(map.get("BOUQUET_NO")==null?"":map.get("BOUQUET_NO").toString());
				bean.setBaseProposalNo(map.get("BASE_LAYER")==null?"":map.get("BASE_LAYER").toString());
				bean.setContractNo(map.get("CONTRACT_NO")==null?"":map.get("CONTRACT_NO").toString());
				bean.setLayerNo(map.get("LAYER_NO")==null?"0":map.get("LAYER_NO").toString());
				bean.setSectionNo(map.get("SECTION_NO")==null?"":map.get("SECTION_NO").toString());
				bean.setOfferNo(map.get("OFFER_NO")==null?"":map.get("OFFER_NO").toString());
				bean.setAmendId(map.get("AMEND_ID")==null?"":map.get("AMEND_ID").toString());
				if(StringUtils.isBlank(eProposalNo))
				bean.setEproposalNo(proposalNo);
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
	public GetReinsurerInfoRes getReinsurerInfo(GetReinsurerInfoReq bean) {
		GetReinsurerInfoRes response = new GetReinsurerInfoRes();
		List<GetReinsurerInfoRes1> resList = new ArrayList<GetReinsurerInfoRes1>();
		GetReinsurerInfoResponse res1 = new GetReinsurerInfoResponse();
		List<Map<String,Object>>list=null;
		int mailcount=0;
		
		try {
			list = queryImpl.selectList("GET_REINSURER_INFO_NOTIN",new String[] {bean.getBranchCode(),bean.getEproposalNo()});
			if(!CollectionUtils.isEmpty(list)) {
				res1.setPlacementMode(list.get(0).get("PLACEMENT_MODE")==null?"":list.get(0).get("PLACEMENT_MODE").toString());
				res1.setPlacementDisabled("Y");
			}
			//GET_PROPOSAL_MAIL_COUNT
			mailcount = ripRepo.countByBranchCodeAndProposalNoAndStatusNot(bean.getBranchCode(),new BigDecimal(bean.getEproposalNo()),"0");
			if(StringUtils.isNotBlank(bean.getBouquetNo()) && "C".equals(res1.getPlacementMode())) {
				list=queryImpl.selectList("GET_REINSURER_INFO_BOUQUET_NOTIN",new String[] {bean.getBranchCode(),bean.getBouquetNo()});
				//GET_BOUQUET_MAIL_COUNT
				mailcount=ripRepo.countByBranchCodeAndBouquetNoAndStatusNot(bean.getBranchCode(),new BigDecimal(bean.getBouquetNo()),"0");
			}
			if(!CollectionUtils.isEmpty(list)) {
				for(int i=0;i<list.size();i++) {
					Map<String,Object>map=list.get(i);
					GetReinsurerInfoRes1 res = new GetReinsurerInfoRes1();
					res.setMailStatus(map.get("MAIL_STATUS")==null?"":map.get("MAIL_STATUS").toString());
					res.setPlacingBroker(map.get("BROKER_ID")==null?"":map.get("BROKER_ID").toString());
					res.setProposalNos(map.get("PROPOSAL_NO")==null?"":map.get("PROPOSAL_NO").toString());
					res.setReinsSNo(map.get("SNO")==null?"":map.get("SNO").toString());
					res.setReinsureName(map.get("REINSURER_ID")==null?"":map.get("REINSURER_ID").toString());
					res.setShareOffer(map.get("SHARE_OFFERED")==null?"":dropDownImple.formattereight(map.get("SHARE_OFFERED").toString()));;
		
					if(mailcount>0) {
						res.setDeleteStatus("N");
					}else {
						res.setDeleteStatus("N");
					}
					res.setChangeStatus("N");
					resList.add(res);
					}
				res1.setReinsurerInfoList(resList);;
			}
			response.setCommonResponse(res1);	
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
	public GetPlacementInfoListRes getPlacementInfoList(GetPlacementInfoListReq bean) {
		GetPlacementInfoListRes response = new GetPlacementInfoListRes();
		List<Map<String,Object>>list=null;
		String query="";
		String qutext ="";
		List<GetPlacementInfoListRes1> resList = new ArrayList<GetPlacementInfoListRes1>();
		try {
			String[] obj=new String[2];
			if(StringUtils.isNotBlank(bean.getBouquetNo())) {
				query="GET_PLACEMENT_BOUQUET_LIST";
				  qutext = prop.getProperty(query);
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getBouquetNo();
			}else if(StringUtils.isNotBlank(bean.getBaseProposalNo())) {
				query="GET_PLACEMENT_BASE_LIST";
				 qutext = prop.getProperty(query);
				obj[0]=bean.getBranchCode();
				obj[1]=bean.getBaseProposalNo();
			}else {
				query="GET_PLACEMENT_LIST";
				 qutext = prop.getProperty(query);
				obj[0]=bean.getBranchCode();
				obj[1]=StringUtils.isBlank(bean.getEproposalNo())?bean.getProposalNo():bean.getEproposalNo();
			}
			if(StringUtils.isNotBlank(bean.getSearchType())) {
				qutext=qutext+" AND P.REINSURER_ID='"+bean.getSearchReinsurerId()+"' AND P.BROKER_ID='"+bean.getSearchBrokerId()+"' AND P.STATUS='"+bean.getSearchStatus()+"'";
			}
			qutext=qutext+" ORDER BY OFFER_NO,P.BASE_PROPOSAL_NO,P.PROPOSAL_NO,P.SNO";
			query1 =queryImpl.setQueryProp(qutext, obj);
    		query1.unwrap(NativeQueryImpl.class).setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);
    		try {
    			 list = query1.getResultList();
    		} catch(Exception e) {
    			e.printStackTrace();
    		} 
			
			if(list.size()>0) {
				for(int i=0;i<list.size();i++) {
					Map<String,Object>map=list.get(i);
					GetPlacementInfoListRes1 res = new GetPlacementInfoListRes1();
					res.setSno(map.get("SNO")==null?"":map.get("SNO").toString()); 
					res.setBouquetNo(map.get("BOUQUET_NO")==null?"":map.get("BOUQUET_NO").toString()); 
					res.setBaseProposalNo(map.get("BASE_PROPOSAL_NO")==null?"":map.get("BASE_PROPOSAL_NO").toString()); 
					res.setProposalNo(map.get("ROPOSAL_NO")==null?"":map.get("PROPOSAL_NO").toString()); 
					res.setCedingCompanyId(map.get("CEDING_COMPANY_ID")==null?"":map.get("CEDING_COMPANY_ID").toString()); 
					res.setReinsurerId(map.get("REINSURER_ID")==null?"":map.get("REINSURER_ID").toString()); 
					res.setBrokerId(map.get("BROKER_ID")==null?"":map.get("BROKER_ID").toString()); 
					res.setReinsurerName(map.get("REINSURER_NAME")==null?"":map.get("REINSURER_NAME").toString()); 
					res.setBrokerName(map.get("BROKER_NAME")==null?"":map.get("BROKER_NAME").toString()); 
					res.setShareOffered(map.get("SHARE_OFFERED")==null?"":map.get("SHARE_OFFERED").toString()); 
					res.setShareWritten(map.get("SHARE_WRITTEN")==null?"":map.get("SHARE_WRITTEN").toString()); 
					res.setShareProposalWritten(map.get("SHARE_PROPOSAL_WRITTEN")==null?"":map.get("SHARE_PROPOSAL_WRITTEN").toString()); 
					res.setShareSigned(map.get("SHARE_SIGNED")==null?"":map.get("SHARE_SIGNED").toString()); 
					res.setBrokeragePer(map.get("BROKERAGE_PER")==null?"":map.get("BROKERAGE_PER").toString()); 
					res.setStatus(map.get("STATUS")==null?"":map.get("STATUS").toString()); 
					res.setWrittenLineValidity(map.get("WRITTEN_LINE_VALIDITY")==null?"":map.get("WRITTEN_LINE_VALIDITY").toString()); 
					res.setWrittenLineRemarks(map.get("WRITTEN_LINE_REMARKS")==null?"":map.get("WRITTEN_LINE_REMARKS").toString()); 
					res.setShareLineValidity(map.get("SHARE_LINE_VALIDITY")==null?"":map.get("SHARE_LINE_VALIDITY").toString()); 
					res.setShareLineRemarks(map.get("SHARE_LINE_REMARKS")==null?"":map.get("SHARE_LINE_REMARKS").toString()); 
					res.setShareProposedSigned(map.get("SHARE_PROPOSED_SIGNED")==null?"":map.get("SHARE_PROPOSED_SIGNED").toString()); 
					res.setMailStatus(map.get("MAIL_STATUS")==null?"":map.get("MAIL_STATUS").toString()); 
					res.setOfferNo(map.get("OFFER_NO")==null?"":map.get("OFFER_NO").toString());
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
	public CommonSaveResList savePlacing(SavePlacingReq bean) {
		CommonSaveResList response = new CommonSaveResList();
		List<SavePlacingRes> resList = new ArrayList<SavePlacingRes>();
		List<GetBouquetExistingListRes1> list = null;
		try {
			getPlacementNo(bean);
			if("C".equalsIgnoreCase(bean.getPlacementMode())) {
				if(StringUtils.isNotBlank(bean.getBouquetNo())) {
				list =	dropDownImple.getBouquetExistingList(bean.getBranchCode(),bean.getBouquetNo(),bean.getBouquetModeYN()).getCommonResponse();
				}else {
					list = dropDownImple.getBaseLayerExistingList(bean.getBranchCode(),bean.getBaseProposalNo()).getCommonResponse();
				}
				for(int i=0;i<list.size();i++) {
					SavePlacingRes res = new SavePlacingRes();
					res.setEproposalNo(list.get(i).getProposalNo()==null?"":list.get(i).getProposalNo().toString());
					resList.add(res);
					insertPlacing(bean);
				}
			}else {
				insertPlacing(bean);
			}
			response.setResponse(resList);
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
	public GetPlacementNoRes getPlacementNo(SavePlacingReq bean) {
		GetPlacementNoRes response = new GetPlacementNoRes();
		GetPlacementNoRes1 res = new GetPlacementNoRes1();
		String placementNo="",statusNo;
		TtrnRiPlacement list=null;
		try {
			if("C".equalsIgnoreCase(bean.getPlacementMode())) {
				if(StringUtils.isNotBlank(bean.getBouquetNo())) {
					//GET_PLACEMENT_NO_BOUQUET
					list = ripRepo.findDistinctByBouquetNo(new BigDecimal(bean.getBouquetNo()));
				}else {
					//GET_PLACEMENT_NO_BASELAYER
					list = ripRepo.findDistinctByBaseProposalNo(new BigDecimal(bean.getBaseProposalNo()));
				}
			}else {
				//GET_PLACEMENT_NO_PROPOSAL
				list = ripRepo.findDistinctByProposalNo(new BigDecimal(bean.getEproposalNo()));
			}
			if(list != null) {
				placementNo=list.getPlacementNo()==null?"":list.getPlacementNo().toString();
			}
			if(StringUtils.isBlank(placementNo)) {
			 	placementNo= fm.getSequence("PlacementNo","0","0", bean.getBranchCode(),"","");
			}
			statusNo= fm.getSequence("StatusNo","0","0", bean.getBranchCode(),"","");
			res.setStatusNo(statusNo);
			res.setPlacementNo(placementNo);
			res.setStatusNo(statusNo);
			response.setCommonResponse(res);	
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
	public InsertPlacingRes insertPlacing(SavePlacingReq bean) {
		InsertPlacingRes response = new InsertPlacingRes();
		String plamendId="0",currentStatus="O";
		Tuple result=null;
		TtrnRiPlacement entity = new TtrnRiPlacement();
		try {
			proposalInfo(bean.getBranchCode(),bean.getProposalNo(),bean.getEproposalNo());
			//DeletePlacement(bean);
			//INSERT_PLACEMENT_INFO
			for(int i=0;i<bean.getReinsListReq().size();i++) {
				ReinsListReq req =	bean.getReinsListReq().get(i);
				bean.setReinsurerId(req.getReinsureName());
				bean.setBrokerId(req.getPlacingBroker());
				plamendId=getMaxAmendId(bean);
				bean.setPlacementamendId(StringUtils.isBlank(plamendId)?"0":plamendId);
				result=getPlacementDetails(bean.getEproposalNo(),bean.getReinsurerId(),bean.getBrokerId(),bean.getBranchCode());
				if(result!=null) {
					currentStatus=result.get("STATUS")==null?"O":result.get("STATUS").toString();
				}
//				entity.setPlacementNo(new BigDecimal(bean.getPlacementNo()));
//				entity.setSno(req.getReinsSNo());
//				entity.setBouquetNo(bean.getBouquetNo())
//				entity.setBaseProposalNo(bean.getBaseProposalNo())
//				entity.setProposalNo(bean.getEproposalNo())
//				entity.setContractNo(null)
//				entity.setLayerNo(null)
//				entity.setSectionNo(null)
//				entity.setAmendId(null)
//				entity.setReinsurerId(currentStatus)
//				entity.setBrokerId(currentStatus);
//				entity.setShareOffered(null)
//				entity.setBranchCode(currentStatus)
//				entity.setSysDate(null)
//				entity.setCedingCompanyId(currentStatus)
//				entity.setPlacementMode(currentStatus);
//				entity.setStatus(currentStatus)
//				entity.setPlacementAmendId(null)
//				entity.setStatusNo(null)
//				entity.setApproveStatus(currentStatus)
//				entity.setUserId(currentStatus);
//				
//				
//					obj[0]=;
//					obj[1]=;
//					obj[2]=;
//					obj[3]=;
//					obj[4]=;
//					obj[5]=bean.getContractNo();
//					obj[6]=bean.getLayerNo();
//					obj[7]=bean.getSectionNo();
//					obj[8]=bean.getAmendId();
//					obj[9]=req.getReinsureName();
//					obj[10]=req.getPlacingBroker();
//					obj[11]=req.getShareOffer();
//					obj[12]=bean.getBranchCode();
//					obj[13]=bean.getCedingCompany();
//					obj[14]=bean.getPlacementMode();
//					obj[15]=currentStatus;
//					obj[16]=bean.getPlacementamendId();
//					obj[17]=bean.getStatusNo();
//					obj[18]="Y";
//					obj[19]=bean.getUserId();
//					this.mytemplate.update(query,obj);
//					
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return response;
	}
	private String getMaxAmendId(SavePlacingReq bean) {
		String plamendId="0";
		try {
			//GET_PLACEMENT_MAX_AMENDID
			TtrnRiPlacement  list = ripRepo.findTop1ByBranchCodeAndProposalNoAndReinsurerIdAndBrokerIdOrderByPlacementAmendIdDesc(
					bean.getBranchCode(),new BigDecimal(bean.getEproposalNo()),bean.getReinsurerId(),bean.getBrokerId());
			plamendId= String.valueOf(list.getPlacementAmendId().intValue()+1);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return plamendId;
	}
	private Tuple getPlacementDetails(String proposalNo, String reinsuerId, String brokerId,String branchCode) {
		Tuple map=null;
		try {
			//GET_PLACEMENT_DETAIL
			CriteriaBuilder cb = em.getCriteriaBuilder(); 
			CriteriaQuery<Tuple> query = cb.createQuery(Tuple.class); 
			
			Root<TtrnRiPlacement> pm = query.from(TtrnRiPlacement.class);

			query.multiselect(pm.get("placementNo").alias("PLACEMENT_NO"),pm.get("sno").alias("SNO"),
					pm.get("bouquetNo").alias("BOUQUET_NO"),pm.get("proposalNo").alias("PROPOSAL_NO"),
					pm.get("contractNo").alias("CONTRACT_NO"),pm.get("layerNo").alias("LAYER_NO"),
					pm.get("sectionNo").alias("SECTION_NO"),pm.get("amendId").alias("AMEND_ID"),
					pm.get("cedingCompanyId").alias("CEDING_COMPANY_ID"),pm.get("reinsurerId").alias("REINSURER_ID"),
					pm.get("brokerId").alias("BROKER_ID"),pm.get("shareOffered").alias("SHARE_OFFERED"),
					pm.get("placementMode").alias("PLACEMENT_MODE"),pm.get("status").alias("STATUS")); 

			// MAXAmend ID
			Subquery<Long> amend = query.subquery(Long.class); 
			Root<TtrnRiPlacement> pms = amend.from(TtrnRiPlacement.class);
			amend.select(cb.max(pms.get("placementAmendId")));
			Predicate a1 = cb.equal( pm.get("branchCode"), pms.get("branchCode"));
			Predicate a2 = cb.equal( pm.get("proposalNo"), pms.get("proposalNo"));
			Predicate a3 = cb.equal( pm.get("reinsuerId"), pms.get("reinsuerId"));
			Predicate a4 = cb.equal( pm.get("brokerId"), pms.get("brokerId"));
			amend.where(a1,a2,a3,a4);

			Predicate n1 = cb.equal(pm.get("branchCode"), branchCode);
			Predicate n2 = cb.equal(pm.get("proposalNo"), proposalNo);
			Predicate n3 = cb.equal(pm.get("reinsuerId"), reinsuerId);
			Predicate n4 = cb.equal(pm.get("brokerId"), brokerId);
			Predicate n5 = cb.equal(pm.get("placementAmendId"), amend);
			query.where(n1,n2,n3,n4,n5);
			
			TypedQuery<Tuple> res = em.createQuery(query);
			List<Tuple> list = res.getResultList();

			if(!CollectionUtils.isEmpty(list)) {
					map=list.get(0);
			}
		}
		catch (Exception e) {
				e.printStackTrace();
		}	
		return map;
	}
}
