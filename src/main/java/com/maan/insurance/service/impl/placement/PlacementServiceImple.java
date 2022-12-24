package com.maan.insurance.service.impl.placement;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maan.insurance.model.entity.PersonalInfo;
import com.maan.insurance.model.entity.PersonalInfoContact;
import com.maan.insurance.model.entity.PositionMaster;
import com.maan.insurance.model.entity.RskPremiumDetailsRi;
import com.maan.insurance.model.entity.TtrnRiPlacement;
import com.maan.insurance.model.repository.RskPremiumDetailsRIRepository;
import com.maan.insurance.model.repository.RskPremiumDetailsRepository;
import com.maan.insurance.model.repository.RskPremiumDetailsTempRepository;
import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
import com.maan.insurance.model.res.DropDown.CommonResDropDown;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
import com.maan.insurance.service.impl.QueryImplemention;
import com.maan.insurance.service.impl.Dropdown.DropDownServiceImple;
import com.maan.insurance.service.impl.propPremium.PropPremiumServiceImple;
import com.maan.insurance.service.placement.PlacementService;
import com.maan.insurance.service.propPremium.PropPremiumService;
import com.maan.insurance.validation.Formatters;
import com.maan.insurance.validation.Claim.ValidationImple;

@Service
public class PlacementServiceImple implements PlacementService {
	private Logger log = LogManager.getLogger(PlacementServiceImple.class);
	
	@Autowired
	private QueryImplemention queryImpl;

	@Autowired
	private Formatters fm;
	
	@Autowired
	private DropDownServiceImple  dropDownImple;
	
	@PersistenceContext
	private EntityManager em;

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

}
