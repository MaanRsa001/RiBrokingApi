package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPremiumRegisterListComRes {
	@JsonProperty("Result1")
	private List<GetPremiumRegisterListRes1> ifProduct1;
	
	@JsonProperty("Result2")
	private List<GetPremiumRegisterListRes2> ifProduct2;
	
	@JsonProperty("Result3")
	private List<GetPremiumRegisterListRes3> ifProduct3;
	
	@JsonProperty("Result4")
	private List<GetPremiumRegisterListRes4> ifProduct4;
}
