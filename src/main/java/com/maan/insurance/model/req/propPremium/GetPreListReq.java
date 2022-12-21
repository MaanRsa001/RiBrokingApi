package com.maan.insurance.model.req.propPremium;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.res.propPremium.GetPremiumedListRes1;

import lombok.Data;

@Data
public class GetPreListReq {
	@JsonProperty("ContNo")
	private String contNo;

	@JsonProperty("DepartmentId")
	private String departmentId;
	
	
	
}
