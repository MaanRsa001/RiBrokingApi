package com.maan.insurance.model.res.premium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.premium.ContractidetifierlistRes1;

import lombok.Data;
@Data
public class ContractidetifierlistRes {
	@JsonProperty("Result")
	private  List<ContractidetifierlistRes1> commonResponse;
	
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errors;

	@JsonProperty("ErroCode")
	private int erroCode;
}
