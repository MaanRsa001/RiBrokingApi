package com.maan.insurance.validation.placement;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
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
	// TODO Auto-generated method stub
	return null;
}

}
