package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetJournelMoveMentInitRes1 {
	@JsonProperty("AccountPeriod")
	private String accountPeriod;
	@JsonProperty("AccountDate")
	private String accountDate;
	@JsonProperty("Accper")
	private String accper;
	@JsonProperty("MovementType")
	private String movementType;
	@JsonProperty("SNo")
	private String sNo;
}
