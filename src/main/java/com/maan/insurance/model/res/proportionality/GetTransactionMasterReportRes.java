package com.maan.insurance.model.res.proportionality;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetTransactionMasterReportRes {
	@JsonProperty("Result")
	private List<GetTransactionMasterReportRes1> commonResponse;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")	
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errorMessage;
	
	@JsonProperty("ErroCode")
	private int erroCode;
}
