package com.maan.insurance.controller.placement;

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
import com.maan.insurance.model.req.placement.EditPlacingDetailsReq;
import com.maan.insurance.model.req.placement.GetExistingAttachListReq;
import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
import com.maan.insurance.model.req.placement.GetPlacementInfoListReq;
import com.maan.insurance.model.req.placement.GetPlacingInfoReq;
import com.maan.insurance.model.req.placement.GetReinsurerInfoReq;
import com.maan.insurance.model.req.placement.SavePlacingReq;
import com.maan.insurance.model.req.retro.FirstInsertReq;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
import com.maan.insurance.model.res.placement.CommonSaveResList;
import com.maan.insurance.model.res.placement.EditPlacingDetailsRes;
import com.maan.insurance.model.res.placement.GetExistingAttachListRes;
import com.maan.insurance.model.res.placement.GetPlacementInfoListRes;
import com.maan.insurance.model.res.placement.GetPlacementNoRes;
import com.maan.insurance.model.res.placement.GetPlacingInfoRes;
import com.maan.insurance.model.res.placement.GetReinsurerInfoRes;
import com.maan.insurance.model.res.placement.InsertPlacingRes;
import com.maan.insurance.model.res.placement.ProposalInfoRes;
import com.maan.insurance.model.res.retro.FirstInsertRes;
import com.maan.insurance.model.res.xolPremium.CommonSaveRes;
import com.maan.insurance.service.placement.PlacementService;
import com.maan.insurance.service.retro.RetroService;
import com.maan.insurance.validation.placement.PlacementValidation;
import com.maan.insurance.validation.retro.RetroValidation;

@RestController
@RequestMapping("/Insurance/placement")
public class PlacementController {
Gson gson = new Gson();
	
	@Autowired
	private PlacementService serv;
	@Autowired
	private PlacementValidation val;
	
	@PostMapping("/getMailToList")
	public GetCommonDropDownRes getMailToList(@RequestBody GetMailToListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getMailToListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getMailToList(req);
	} 

	@PostMapping("/getExistingReinsurerList")
	public GetCommonDropDownRes getExistingReinsurerList(@RequestBody GetExistingReinsurerListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getExistingReinsurerListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getExistingReinsurerList(req);
	} 
	@PostMapping("/getExistingBrokerList")
	public GetCommonDropDownRes getExistingBrokerList(@RequestBody GetExistingReinsurerListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getExistingReinsurerListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getExistingBrokerList(req);
	} 
	@PostMapping("/getExistingAttachList")
	public GetExistingAttachListRes getExistingAttachList(@RequestBody GetExistingAttachListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getExistingAttachListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getExistingAttachList(req);
	}  
	@GetMapping("/proposalInfo/{branchCode}/{proposalNo}/{eProposalNo}")
	public ProposalInfoRes proposalInfo(@PathVariable ("branchCode") String branchCode,@PathVariable ("proposalNo") String proposalNo,@PathVariable ("eProposalNo") String eProposalNo) throws CommonValidationException {
		return serv.proposalInfo(branchCode,proposalNo,eProposalNo);
		}  
	@PostMapping("/getReinsurerInfo")
	public GetReinsurerInfoRes getReinsurerInfo(@RequestBody GetReinsurerInfoReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getReinsurerInfoVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getReinsurerInfo(req);
		}   
	@PostMapping("/getPlacementInfoList")
	public GetPlacementInfoListRes getPlacementInfoList(@RequestBody GetPlacementInfoListReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getPlacementInfoListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return serv.getPlacementInfoList(req);
		} 
	@PostMapping("/savePlacing")
	public CommonSaveResList savePlacing(@RequestBody SavePlacingReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.validatePlacing(req);
		if(error!=null && error.size()>0) {
			 throw new CommonValidationException("error",error);
		}
			return serv.savePlacing(req);
		}  
	@PostMapping("/getPlacementNo")
		public GetPlacementNoRes getPlacementNo(@RequestBody SavePlacingReq req) throws CommonValidationException {
			List<ErrorCheck> error= val.validatePlacing(req);
			if(error!=null && error.size()>0) {
				 throw new CommonValidationException("error",error);
		}
			return serv.getPlacementNo(req);
	} 
	@PostMapping("/insertPlacing")
	public InsertPlacingRes insertPlacing(@RequestBody SavePlacingReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.validatePlacing(req);
		if(error!=null && error.size()>0) {
			 throw new CommonValidationException("error",error);
	}
		return serv.insertPlacing(req);
	} 	
	@PostMapping("/getPlacingInfo")
	public GetPlacingInfoRes getPlacingInfo(@RequestBody GetPlacingInfoReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.getPlacingInfoVali(req);
		if(error!=null && error.size()>0) {
			 throw new CommonValidationException("error",error);
	}
		return serv.getPlacingInfo(req);
	} 
	@PostMapping("/editPlacingDetails")
	public EditPlacingDetailsRes editPlacingDetails(@RequestBody EditPlacingDetailsReq req) throws CommonValidationException {
		List<ErrorCheck> error= val.editPlacingDetailsVali(req);
		if(error!=null && error.size()>0) {
			 throw new CommonValidationException("error",error);
	}
		return serv.editPlacingDetails(req);
	}
}
