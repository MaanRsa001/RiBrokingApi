package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.req.reports.GetTransactionMasterReportReq;

import lombok.Data;

@Data
public class GetJVReportsRes {
	@JsonProperty("Result")
	private List<GetJVReportsRes1> commonResponse;
	
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<ErrorCheck> errors;

	@JsonProperty("ErroCode")
	private int erroCode;
}
