package com.maan.insurance.model.res.propPremium;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import lombok.Data;

@Data
public class GetSPRetroListRes1 {
	
	
	@JsonProperty("SpRetro")
	private String spRetro;
	@JsonProperty("NoOfInsurers")
	private String noOfInsurers;
	@JsonProperty("ProposalNo")
	private String proposalNo;
}
