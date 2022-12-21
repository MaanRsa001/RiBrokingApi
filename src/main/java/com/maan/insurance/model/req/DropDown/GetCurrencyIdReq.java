package com.maan.insurance.model.req.DropDown;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetCurrencyIdReq {
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("ClaimNo")
	private String claimNo;
	@JsonProperty("LayerNo")
	private String layerNo;
}
