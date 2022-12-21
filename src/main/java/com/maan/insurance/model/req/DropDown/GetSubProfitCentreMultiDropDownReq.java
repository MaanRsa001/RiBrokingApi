package com.maan.insurance.model.req.DropDown;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.propPremium.ContractDetailsReq;

import lombok.Data;

@Data
public class GetSubProfitCentreMultiDropDownReq {
	@JsonProperty("Status")
	private String status;
	
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("DepartmentId")
	private String departmentId;
	
	@JsonProperty("ProductCode")
	private String productCode;
		
	@JsonProperty("ContNo")
	private String contNo;
	
	@JsonProperty("Endtz")
	private String endtz;
	
	@JsonProperty("ProposalNo")
	private String proposalNo;

	@JsonProperty("Mode")
	private String mode;

	@JsonProperty("BaseLayer")
	private String baseLayer;

	public Object getSubProfitId() {
		// TODO Auto-generated method stub
		return null;
	}

}
