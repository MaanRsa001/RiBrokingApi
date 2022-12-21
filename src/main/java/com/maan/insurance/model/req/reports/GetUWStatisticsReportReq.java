package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.reports.GetPptySOAPendingReportRes1;

import lombok.Data;

@Data
public class GetUWStatisticsReportReq {
	@JsonProperty("PeriodFrom")
	private String periodFrom;
	@JsonProperty("PeriodTo")
	private String periodTo;
	@JsonProperty("UwFrom")
	private String uwFrom;
	@JsonProperty("UwTo")
	private String uwTo;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("Type")
	private String type;
}
