package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetDebtorsAgeingReportRes1 {

	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("NetDueOc")
	private String netDueOc;
	@JsonProperty("AllocatedTillDate")
	private String allocatedTillDate;
	@JsonProperty("PendingAllocation")
	private String pendingAllocation;
	@JsonProperty("M1")
	private String m1;
	@JsonProperty("M2")
	private String m2;
	@JsonProperty("M3")
	private String m3;
	@JsonProperty("M4")
	private String m4;
	@JsonProperty("M5")
	private String m5;
	@JsonProperty("M6")
	private String m6;
	@JsonProperty("NetDueDc")
	private String netDueDc;
	@JsonProperty("AllocatedTillDateDc")
	private String allocatedTillDateDc;
	@JsonProperty("PendingAllocationDc")
	private String pendingAllocationDc;
	@JsonProperty("ClientId")
	private String clientId;

}
