package com.maan.insurance.model.req.nonproportionality;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class coverLimitOC {
	
	@JsonProperty("CoverdepartId")
	private String coverdepartId;
	
	@JsonProperty("CoverLimitOC")
	private String coverLimitOC;
	
	@JsonProperty("DeductableLimitOC")
	private String deductableLimitOC;
	
	@JsonProperty("EgnpiAsPerOff")
	private String egnpiAsPerOff;
	
	@JsonProperty("GnpiAsPO")
	private String gnpiAsPO;
}
