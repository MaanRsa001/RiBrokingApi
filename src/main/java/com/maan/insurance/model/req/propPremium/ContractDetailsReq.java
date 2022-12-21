package com.maan.insurance.model.req.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.propPremium.ContractDetailsRes;
import com.maan.insurance.model.res.propPremium.ContractDetailsRes1;

import lombok.Data;

@Data
public class ContractDetailsReq {
	@JsonProperty("ContNo")
	private String contNo;
	
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("DepartmentId")
	private String departmentId;
	
	@JsonProperty("ProductId")
	private String productId; 
	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("TransactionNo")
	private String transactionNo;
}
