
package com.maan.insurance.model.res.DropDown;


import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.propPremium.GetAllocatedListRes2;

import lombok.Data;


@Data
public class GetCommonDropDownRes {
	@JsonProperty("Result")
	private List<CommonResDropDown> commonResponse;

	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")	
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<Error> errorMessage;
	
	@JsonProperty("ErroCode")
	private int erroCode;
}
