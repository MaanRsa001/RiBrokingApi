package com.maan.insurance.service.placement;

import org.springframework.stereotype.Service;

import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;

@Service
public interface PlacementService {

	GetCommonDropDownRes getMailToList(GetMailToListReq req);

	GetCommonDropDownRes getExistingReinsurerList(GetExistingReinsurerListReq req);

	GetCommonDropDownRes getExistingBrokerList(GetExistingReinsurerListReq req);

}
