package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes;
import com.maan.insurance.model.res.DropDown.GetCedingCompanyRes1;

import lombok.Data;

@Data
public class GetPptySOAPendingReportRes1 {

	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("ContractClass")
	private String contractClass;
	@JsonProperty("RskPfcId")
	private String rskPfcId;
	@JsonProperty("InwardBusType")
	private String inwardBusType;
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("Broker")
	private String broker;
	@JsonProperty("RskTreatyid")
	private String rskTreatyid;
	@JsonProperty("TreatyType")
	private String treatyType;
	@JsonProperty("InceptionDate")
	private String inceptionDate;
	@JsonProperty("ExpiryDate")
	private String expiryDate;
	@JsonProperty("CleancutRunoff")
	private String cleancutRunoff;
	@JsonProperty("RskRunOffYear")
	private String rskRunOffYear;
	@JsonProperty("Periodicity")
	private String periodicity;
	@JsonProperty("StatementDate")
	private String statementDate;
	@JsonProperty("RskReceiptStatement")
	private String rskReceiptStatement;
	@JsonProperty("RskShareSigned")
	private String rskShareSigned;
	@JsonProperty("StatementDueDate")
	private String statementDueDate;
	@JsonProperty("OverDueDays")
	private String overDueDays;

}
