package com.maan.insurance.model.res.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.controller.propPremium.ViewPremiumDetailsRIRes1;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.req.propPremium.GetVatInfoReq;

import lombok.Data;

@Data
public class GetRIPremiumListRes {
	@JsonProperty("Result")
	private List<GetRIPremiumListRes1> commonResponse;
	
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<ErrorCheck> errors;

	@JsonProperty("ErroCode")
	private int erroCode;
}
