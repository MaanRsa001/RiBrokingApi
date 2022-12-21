package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.reports.GetJournelPageRes1;

import lombok.Data;

@Data
public class InsertCLMovementReq {
	@JsonProperty("EndDate")
	private String endDate;
	@JsonProperty("MovementType")
	private String movementType;
	@JsonProperty("BranchCode")
	private String branchCode;
}
