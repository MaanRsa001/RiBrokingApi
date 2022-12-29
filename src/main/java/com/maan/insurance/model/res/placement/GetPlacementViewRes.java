package com.maan.insurance.model.res.placement;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.req.placement.GetPlacementViewListReq;
import com.maan.insurance.model.res.DropDown.GetBouquetExistingListRes1;

import lombok.Data;

@Data
public class GetPlacementViewRes {
	@JsonProperty("Result")
	private GetPlacementViewRes1 commonResponse;
	
	@JsonProperty("Message")
	private String message;

	@JsonProperty("IsError")
	private Boolean isError;

	@JsonProperty("ErrorMessage")
	private List<ErrorCheck> errors;

	@JsonProperty("ErroCode")
	private int erroCode;
}
