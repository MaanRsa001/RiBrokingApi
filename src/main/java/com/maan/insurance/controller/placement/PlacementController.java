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
import com.maan.insurance.model.req.placement.GetExistingReinsurerListReq;
import com.maan.insurance.model.req.placement.GetMailToListReq;
import com.maan.insurance.model.req.retro.FirstInsertReq;
import com.maan.insurance.model.res.DropDown.GetCommonDropDownRes;
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
//	@GetMapping("/getShortname/{branchCode}")
//	public CommonSaveRes getShortname(@PathVariable ("branchCode") String branchCode) throws CommonValidationException {
//		return retroServ.getShortname(branchCode);
//		} 
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

}
