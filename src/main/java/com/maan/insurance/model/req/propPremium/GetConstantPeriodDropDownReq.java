package com.maan.insurance.model.req.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.propPremium.GetPremiumedListRes;
import com.maan.insurance.model.res.propPremium.GetPremiumedListRes1;

import lombok.Data;

@Data
public class GetConstantPeriodDropDownReq {
	@JsonProperty("ProposalNo")
	private String proposalNo;
	@JsonProperty("CategoryId")
	private String categoryId;
	@JsonProperty("DepartmentId")
	private String departmentId;
	@JsonProperty("ContractNo")
	private String contractNo;
}
