package com.maan.insurance.model.req.facultative;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ShowFirstPageReq {

	@JsonProperty("ProposalNo")
	public String proposalNo;
	
//	@JsonProperty("Branchcode")
//	public String branchcode;
//	
//	@JsonProperty("ProductId")
//	public String productId;
}
