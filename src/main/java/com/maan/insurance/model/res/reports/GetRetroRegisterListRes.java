package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes1;

import lombok.Data;

@Data
public class GetRetroRegisterListRes {
	@JsonProperty("Result")
	private List<GetRetroRegisterListRes1> commonResponse;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")	
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errorMessage;
	
	@JsonProperty("ErroCode")
	private int erroCode;
}
