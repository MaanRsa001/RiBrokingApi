package com.maan.insurance.model.req.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.controller.propPremium.ViewPremiumDetailsRIRes1;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.res.proportionality.CommonSaveRes;

import lombok.Data;
@Data
public class ViewPremiumDetailsRIRes {
	@JsonProperty("Result")
	private List<ViewPremiumDetailsRIRes1> commonResponse;
	
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<ErrorCheck> errors;

	@JsonProperty("ErroCode")
	private int erroCode;
}
