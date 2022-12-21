package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPremiumRegisterListRes4 {
	@JsonProperty("RetroContractNo")
	private String retroContractNo; 
	@JsonProperty("RetroLayerNo")
	private String retroLayerNo; 
	@JsonProperty("ContractNo")
	private String contractNo; 
	@JsonProperty("LayerNo")
	private String layerNo; 
	@JsonProperty("TransactionNo")
	private String transactionNo; 
	@JsonProperty("TransactionDate")
	private String transactionDate; 
	@JsonProperty("CurrencyName")
	private String currencyName; 
	@JsonProperty("ExchangeRate")
	private String exchangeRate; 
	@JsonProperty("RetroPercentage")
	private String retroPercentage; 
	@JsonProperty("PremiumAmtDc")
	private String premiumAmtDc; 
	@JsonProperty("RetroPremiumDc")
	private String retroPremiumDc; 
	@JsonProperty("OverRider")
	private String overRider; 
	@JsonProperty("Net")
	private String net; 
	@JsonProperty("Type")
	private String type; 
	@JsonProperty("ClaimPaidDc")
	private String claimPaidDc; 
	@JsonProperty("RetroClaimPaidDc")
	private String retroClaimPaidDc; 
	@JsonProperty("ProductId")
	private String productId; 
	@JsonProperty("BranchCode")
	private String branchCode; 
	@JsonProperty("UwYear")
	private String uwYear;
	
}
