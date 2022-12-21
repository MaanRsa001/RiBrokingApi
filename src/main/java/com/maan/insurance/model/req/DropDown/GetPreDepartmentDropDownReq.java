package com.maan.insurance.model.req.DropDown;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.propPremium.ContractDetailsRes1;

import lombok.Data;

@Data
public class GetPreDepartmentDropDownReq {
	@JsonProperty("ProductId")
	private String productId;
	
	@JsonProperty("BranchCode")
	private String branchCode;
	
	@JsonProperty("DepartmentId")
	private String departmentId;
	@JsonProperty("Status")
	private String status;
	@JsonProperty("ContractNo")
	private String contractNo;
	@JsonProperty("LayerNo")
	private String layerNo;
}
