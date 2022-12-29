package com.maan.insurance.model.res.placement;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.res.DropDown.GetBouquetExistingListRes;
import com.maan.insurance.model.res.DropDown.GetBouquetExistingListRes1;

import lombok.Data;

@Data
public class GetPlacementViewListRes {
	@JsonProperty("Result")
	private List<GetPlacementViewListRes1> commonResponse;
	
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<ErrorCheck> errors;

	@JsonProperty("ErroCode")
	private int erroCode;
}
