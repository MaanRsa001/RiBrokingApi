package com.maan.insurance.validation.placement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.req.placement.EditPlacingDetailsReq;
import com.maan.insurance.model.req.placement.GetExistingAttachListReq;
import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
import com.maan.insurance.model.req.placement.GetPlacementInfoListReq;
import com.maan.insurance.model.req.placement.GetPlacingInfoReq;
import com.maan.insurance.model.req.placement.GetReinsurerInfoReq;
import com.maan.insurance.model.req.placement.ReinsListReq;
import com.maan.insurance.model.req.placement.SavePlacingReq;
import com.maan.insurance.service.impl.Dropdown.DropDownServiceImple;
import com.maan.insurance.service.impl.placement.PlacementServiceImple;
import com.maan.insurance.service.impl.propPremium.PropPremiumServiceImple;
import com.maan.insurance.validation.CommonCalculation;
import com.maan.insurance.validation.Formatters;
import com.maan.insurance.validation.propPremium.PropPremiumValidation;

@Service
public class PlacementValidation {
	private Logger log = LogManager.getLogger(PlacementValidation.class);
	private Properties prop = new Properties();

	@Autowired
	private PlacementServiceImple premiumImpl;
	
	@Autowired
	private Formatters fm;
	
	@Autowired
	private DropDownServiceImple dropDownImple;
	
	@Autowired
	private CommonCalculation calcu;
	
 public PlacementValidation() {
		
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

public List<ErrorCheck> getMailToListVali(GetMailToListReq req) {
	List<ErrorCheck> list = new ArrayList<ErrorCheck>();
	if (StringUtils.isBlank(req.getBrokerCompany())) {
		list.add(new ErrorCheck("Please Enter BrokerCompany", "BrokerCompany", "1"));
	}
	if (StringUtils.isBlank(req.getBrokerId())) {
		list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "2"));
	}
	if (StringUtils.isBlank(req.getCedingCompany())) {
		list.add(new ErrorCheck("Please Enter CedingCompany", "CedingCompany", "3"));
	}
	if (StringUtils.isBlank(req.getCurrentStatus())) {
		list.add(new ErrorCheck("Please Enter CurrentStatus", "CurrentStatus", "4"));
	}
	if (StringUtils.isBlank(req.getNewStatus())) {
		list.add(new ErrorCheck("Please Enter NewStatus", "NewStatus", "5"));
	}
	if (StringUtils.isBlank(req.getReinsurerId())) {
		list.add(new ErrorCheck("Please Enter ReinsurerId", "ReinsurerId", "6"));
	}
	return list;
}

public List<ErrorCheck> getExistingReinsurerListVali(GetExistingReinsurerListReq req) {
	List<ErrorCheck> list = new ArrayList<ErrorCheck>();
	if (StringUtils.isBlank(req.getBaseProposalNo())) {
		list.add(new ErrorCheck("Please Enter BaseProposalNo", "BaseProposalNo", "1"));
	}
	if (StringUtils.isBlank(req.getBouquetNo())) {
		list.add(new ErrorCheck("Please Enter BouquetNo", "BouquetNo", "2"));
	}
	if (StringUtils.isBlank(req.getBranchCode())) {
		list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "3"));
	}
	if (StringUtils.isBlank(req.getEproposalNo())) {
		list.add(new ErrorCheck("Please Enter EproposalNo", "EproposalNo", "4"));
	}
	if (StringUtils.isBlank(req.getProposalNo())) {
		list.add(new ErrorCheck("Please Enter ProposalNo", "ProposalNo", "5"));
	}
	return list;
}

public List<ErrorCheck> getExistingAttachListVali(GetExistingAttachListReq req) {
	List<ErrorCheck> list = new ArrayList<ErrorCheck>();
	if (StringUtils.isBlank(req.getBranchCode())) {
		list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "1"));
	}
	if (StringUtils.isBlank(req.getEproposalNo())) {
		list.add(new ErrorCheck("Please Enter EproposalNo", "EproposalNo", "2"));
	}
	if (StringUtils.isBlank(req.getProposalNo())) {
		list.add(new ErrorCheck("Please Enter ProposalNo", "ProposalNo", "3"));
	}
	if (StringUtils.isBlank(req.getBrokerId())) {
		list.add(new ErrorCheck("Please Enter BrokerId", "BrokerId", "4"));
	}
	if (StringUtils.isBlank(req.getCorresId())) {
		list.add(new ErrorCheck("Please Enter CorresId", "CorresId", "5"));
	}
	if (StringUtils.isBlank(req.getReinsurerId())) {
		list.add(new ErrorCheck("Please Enter ReinsurerId", "ReinsurerId", "6"));
	}
	return list;
}

public List<ErrorCheck> getReinsurerInfoVali(GetReinsurerInfoReq req) {
	List<ErrorCheck> list = new ArrayList<ErrorCheck>();
	if (StringUtils.isBlank(req.getBranchCode())) {
		list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "1"));
	}
	if (StringUtils.isBlank(req.getEproposalNo())) {
		list.add(new ErrorCheck("Please Enter EproposalNo", "EproposalNo", "2"));
	}
	if (StringUtils.isBlank(req.getBouquetNo())) {
		list.add(new ErrorCheck("Please Enter BouquetNo", "BouquetNo", "3"));
	}
	return list;
}

public List<ErrorCheck> getPlacementInfoListVali(GetPlacementInfoListReq req) {
	List<ErrorCheck> list = new ArrayList<ErrorCheck>();
	if (StringUtils.isBlank(req.getBranchCode())) {
		list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "1"));
	}
	if (StringUtils.isBlank(req.getEproposalNo())) {
		list.add(new ErrorCheck("Please Enter EproposalNo", "EproposalNo", "2"));
	}
	if (StringUtils.isBlank(req.getProposalNo())) {
		list.add(new ErrorCheck("Please Enter ProposalNo", "ProposalNo", "3"));
	}
	if (StringUtils.isBlank(req.getBaseProposalNo())) {
		list.add(new ErrorCheck("Please Enter BaseProposalNo", "BaseProposalNo", "4"));
	}
	if (StringUtils.isBlank(req.getBouquetNo())) {
		list.add(new ErrorCheck("Please Enter BouquetNo", "BouquetNo", "5"));
	}
	if (StringUtils.isBlank(req.getSearchBrokerId())) {
		list.add(new ErrorCheck("Please Enter SearchBrokerId", "SearchBrokerId", "6"));
	}
	if (StringUtils.isBlank(req.getSearchReinsurerId())) {
		list.add(new ErrorCheck("Please Enter SearchReinsurerId", "SearchReinsurerId", "47"));
	}
	if (StringUtils.isBlank(req.getSearchStatus())) {
		list.add(new ErrorCheck("Please Enter SearchStatus", "SearchStatus", "8"));
	}
	if (StringUtils.isBlank(req.getSearchType())) {
		list.add(new ErrorCheck("Please Enter SearchType", "SearchType", "9"));
	}
	return list;
}

public List<ErrorCheck> validatePlacing(SavePlacingReq bean) {
	List<ErrorCheck> list = new ArrayList<ErrorCheck>();
	if(StringUtils.isNotBlank(bean.getBouquetNo()) || StringUtils.isNotBlank(bean.getBaseProposalNo())) {
		if(StringUtils.isBlank(bean.getPlacementMode())) {
			list.add(new ErrorCheck(prop.getProperty("error.placementmode.required"),"placementmode","01"));
		}
		else if("S".equals(bean.getPlacementMode())) {
			if(StringUtils.isBlank(bean.getNotplacedProposal()) && StringUtils.isBlank(bean.getPlacedProposal())) {
				list.add(new ErrorCheck(prop.getProperty("error.placednotpalced.required"),"placednotpalced","01"));
			}
		}
	}
	List<Map<String,Object>> list1 = new ArrayList<Map<String,Object>>();
	if(!CollectionUtils.isEmpty(bean.getReinsListReq())) {
		List<String>rebrlist=new ArrayList<String>();
	for(int i=0;i<bean.getReinsListReq().size();i++) {
		ReinsListReq req =bean.getReinsListReq().get(i);
		if(StringUtils.isBlank(req.getReinsureName())) {
			list.add(new ErrorCheck(prop.getProperty("error.reinsuere.required")+" "+(i+1),"reinsuere","01"));
		}
		if(StringUtils.isBlank(req.getPlacingBroker())) {
			list.add(new ErrorCheck(prop.getProperty("error.placingbroker.required")+" "+(i+1),"placingbroker","01"));
		}
		
		if(StringUtils.isNotBlank(req.getReinsureName()) && StringUtils.isNotBlank(req.getPlacingBroker())) {
			rebrlist.add(req.getReinsureName()+"~"+req.getPlacingBroker());
		}
		Map<String,Object> string = new HashMap<String,Object>();
		string.put("1","1");
		list1.add(string);
		
	}
//	bean.setReinsurerInfoList(list1); doubt
	int count=0;
	for(int i=0;i<bean.getReinsListReq().size();i++) {
		ReinsListReq req =bean.getReinsListReq().get(i);
		String rebr=req.getReinsureName()+"~"+req.getPlacingBroker();
		if (StringUtils.isNotEmpty(rebr)) {
			if (java.util.Collections.frequency(rebrlist, rebr) > 1) {
				if(count==0) {
				list.add(new ErrorCheck(prop.getProperty("error.placingbroker.duplicate"),"placingbroker","01"));
				count=1;
				}
			}
		}
	} 
}
	
	return list;
}

public List<ErrorCheck> getPlacingInfoVali(GetPlacingInfoReq req) {
	List<ErrorCheck> list = new ArrayList<ErrorCheck>();
	if (StringUtils.isBlank(req.getBranchCode())) {
		list.add(new ErrorCheck("Please Enter BranchCode", "BranchCode", "1"));
	}
	if (StringUtils.isBlank(req.getEproposalNo())) {
		list.add(new ErrorCheck("Please Enter EproposalNo", "EproposalNo", "2"));
	}
	if (StringUtils.isBlank(req.getProposalNo())) {
		list.add(new ErrorCheck("Please Enter ProposalNo", "ProposalNo", "3"));
	}
	if (StringUtils.isBlank(req.getBaseProposalNo())) {
		list.add(new ErrorCheck("Please Enter BaseProposalNo", "BaseProposalNo", "4"));
	}
	if (StringUtils.isBlank(req.getBouquetNo())) {
		list.add(new ErrorCheck("Please Enter BouquetNo", "BouquetNo", "5"));
	}
	if (StringUtils.isBlank(req.getSearchBrokerId())) {
		list.add(new ErrorCheck("Please Enter SearchBrokerId", "SearchBrokerId", "6"));
	}
	if (StringUtils.isBlank(req.getSearchReinsurerId())) {
		list.add(new ErrorCheck("Please Enter SearchReinsurerId", "SearchReinsurerId", "47"));
	}
	if (StringUtils.isBlank(req.getSearchStatus())) {
		list.add(new ErrorCheck("Please Enter SearchStatus", "SearchStatus", "8"));
	}
//	if (StringUtils.isBlank(req.getSearchType())) {
//		list.add(new ErrorCheck("Please Enter SearchType", "SearchType", "9"));
//	}
	return list;
}

public List<ErrorCheck> editPlacingDetailsVali(EditPlacingDetailsReq req) {
	// TODO Auto-generated method stub
	return null;
}

}
