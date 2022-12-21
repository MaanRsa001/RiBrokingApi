package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetColumnInfoListReq {
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("Dept")
	private String dept;
	@JsonProperty("TypeId")
	private String typeId;
	@JsonProperty("ShowAllFields")
	private String showAllFields;
	@JsonProperty("BranchCode")
	private String branchCode;

}
