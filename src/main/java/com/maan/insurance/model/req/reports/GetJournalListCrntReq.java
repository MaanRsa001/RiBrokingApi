package com.maan.insurance.model.req.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.res.reports.GetallocationReportListRes;
import com.maan.insurance.model.res.reports.GetallocationReportListRes1;

import lombok.Data;

@Data
public class GetJournalListCrntReq {
	@JsonProperty("TypeId")
	private String typeId;

	@JsonProperty("MovementId")
	private String movementId;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("UwYear")
	private String uwYear;
}
