package com.maan.insurance.model.res.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.propPremium.SubmitPremiumReservedReq1;

import lombok.Data;

@Data
public class SubmitPremiumReservedResponse {
	
	@JsonProperty("Result1")
	private List<SubmitPremiumReservedRes1> commonResponse1;
	@JsonProperty("CreditVal")
	private String creditVal;
}
