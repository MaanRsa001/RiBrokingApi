package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;
@Data
public class InsertMovementReq {
	@JsonProperty("Accper")
	private String accper;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("MovementType")
	private String movementType;
	@JsonProperty("BranchCode")
	private String branchCode;
}
