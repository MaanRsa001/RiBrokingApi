package com.maan.insurance.model.req.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.res.reports.GetJVReportsRes;
import com.maan.insurance.model.res.reports.GetJVReportsRes1;

import lombok.Data;

@Data
public class GetPipelineMovementJvDetailsReq {
	@JsonProperty("ProductId")
	private String productId;
	@JsonProperty("MovementId")
	private String movementId;
	@JsonProperty("Spc")
	private String spc;
	@JsonProperty("CurrencyId")
	private String currencyId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("UwYear")
	private String uwYear;
}
