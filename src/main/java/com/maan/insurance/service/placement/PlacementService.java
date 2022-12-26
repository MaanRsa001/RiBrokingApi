package com.maan.insurance.service.placement;

import org.springframework.stereotype.Service;

import com.maan.insurance.model.req.placement.GetExistingAttachListReq;
import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
import com.maan.insurance.model.req.placement.GetPlacementInfoListReq;
import com.maan.insurance.model.req.placement.GetReinsurerInfoReq;
import com.maan.insurance.model.req.placement.SavePlacingReq;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
import com.maan.insurance.model.res.placement.CommonSaveResList;
import com.maan.insurance.model.res.placement.GetExistingAttachListRes;
import com.maan.insurance.model.res.placement.GetPlacementInfoListRes;
import com.maan.insurance.model.res.placement.GetPlacementNoRes;
import com.maan.insurance.model.res.placement.GetReinsurerInfoRes;
import com.maan.insurance.model.res.placement.InsertPlacingRes;
import com.maan.insurance.model.res.placement.ProposalInfoRes;
import com.maan.insurance.model.res.xolPremium.CommonSaveRes;

@Service
public interface PlacementService {

	GetCommonDropDownRes getMailToList(GetMailToListReq req);

	GetCommonDropDownRes getExistingReinsurerList(GetExistingReinsurerListReq req);

	GetCommonDropDownRes getExistingBrokerList(GetExistingReinsurerListReq req);

	GetExistingAttachListRes getExistingAttachList(GetExistingAttachListReq req);

	ProposalInfoRes proposalInfo(String branchCode, String proposalNo, String eProposalNo);

	GetReinsurerInfoRes getReinsurerInfo(GetReinsurerInfoReq req);

	GetPlacementInfoListRes getPlacementInfoList(GetPlacementInfoListReq req);

	CommonSaveResList savePlacing(SavePlacingReq req);

	GetPlacementNoRes getPlacementNo(SavePlacingReq req);

	InsertPlacingRes insertPlacing(SavePlacingReq req);

}
