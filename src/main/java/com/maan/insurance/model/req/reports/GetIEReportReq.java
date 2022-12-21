package com.maan.insurance.model.req.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.reports.GetUWStatisticsReportRes1;

import lombok.Data;

@Data
public class GetIEReportReq {
	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("StartDate")
	private String startDate;
	@JsonProperty("EndDate")
	private String endDate;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("LayerNo")
	private String layerNo;

}
