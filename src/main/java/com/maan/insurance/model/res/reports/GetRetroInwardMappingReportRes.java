package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.proportionality.GetRetroInwardMappingReportRes1;

import lombok.Data;

@Data
public class GetRetroInwardMappingReportRes {
	@JsonProperty("Result")
	private List<GetRetroInwardMappingReportRes1> commonResponse;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")	
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errorMessage;
	
	@JsonProperty("ErroCode")
	private int erroCode;
}
