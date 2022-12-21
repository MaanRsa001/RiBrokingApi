package com.maan.insurance.model.req.DropDown;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.DropDown.GetInstallmentsRes;
import com.maan.insurance.model.res.DropDown.GetInstallmentsRes1;

import lombok.Data;

@Data
public class GetYearToListValueReq {
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	
	@JsonProperty("ExpiryDate")
	private String expiryDate;
}
