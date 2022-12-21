package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPolicyRegisterListResponse {
	@JsonProperty("Result1")
	private List<GetPolicyRegisterListRes1> ifProduct1;
	
	@JsonProperty("Result2")
	private List<GetPolicyRegisterListRes2> ifProduct23;
	
	@JsonProperty("Result3")
	private List<GetPolicyRegisterListRes4> ifProduct45;
}
