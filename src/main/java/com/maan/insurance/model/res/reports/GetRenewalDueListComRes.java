package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetRenewalDueListComRes {
	@JsonProperty("Result1")
	private List<GetRenewalDueListRes1> ifProduct1;
	
	@JsonProperty("Result2")
	private List<GetRenewalDueListRes2> ifProductAny;
}
