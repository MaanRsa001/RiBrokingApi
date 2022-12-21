package com.maan.insurance.model.res.DropDown;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.premium.ContractidetifierlistRes;
import com.maan.insurance.model.res.premium.ContractidetifierlistRes1;

import lombok.Data;

@Data
public class GetInstallmentsRes {
	@JsonProperty("Result")
	private  List<GetInstallmentsRes1> commonResponse;
	
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errors;

	@JsonProperty("ErroCode")
	private int erroCode;
}
