package com.maan.insurance.model.res.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.propPremium.GetCashLossCreditReq;

import lombok.Data;

@Data
public class GetAllocatedTransListRes {
	@JsonProperty("Result")
	private List<GetAllocatedTransListRes1> commonResponse;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")	
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errorMessage;
	
	@JsonProperty("ErroCode")
	private int erroCode;
}