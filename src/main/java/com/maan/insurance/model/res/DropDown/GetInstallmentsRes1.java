package com.maan.insurance.model.res.DropDown;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetInstallmentsRes1 {
	@JsonProperty("TransactionList")
	private String transactionList;
	
	@JsonProperty("InstalmentDateList")
	private String instalmentDateList;
	
	@JsonProperty("InstallmentPremium")
	private String installmentPremium;
	
	@JsonProperty("PaymentDueDays")
	private String paymentDueDays;
	
	@JsonProperty("Status")
	private Boolean status;
	
}
