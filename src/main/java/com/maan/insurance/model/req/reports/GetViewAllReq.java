package com.maan.insurance.model.req.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.res.reports.GetallocationReportListRes;
import com.maan.insurance.model.res.reports.GetallocationReportListRes1;

import lombok.Data;

@Data
public class GetViewAllReq {
	@JsonProperty("Display")
	private String display;
	@JsonProperty("Accper")
	private String accper;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("MovId")
	private String movId;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("SNo")
	private String sNo; 
	@JsonProperty("AccountPeriod")
	private String accountPeriod; 
}
