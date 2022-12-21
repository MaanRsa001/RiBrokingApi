package com.maan.insurance.model.res;

import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetReceiptReversalListRes {

	@JsonProperty("PaymentNoSearch")
	private String paymentNoSearch;
	
	@JsonProperty("BrokerNameSearch")
	private String brokerNameSearch;
	
	@JsonProperty("CompanyNameSearch")
	private String companyNameSearch;
	
	@JsonProperty("RemarksSearch")
	private String remarksSearch;
	
	@JsonProperty("PayRecNo")
	private String payRecNo;
	
	@JsonProperty("CedingCo")
	private String cedingCo;
	
	@JsonProperty("Payamount")
	private String payamount;
	
	@JsonProperty("Broker")
	private String broker;
	
	@JsonProperty("BrokerId")
	private String brokerId;
	
	@JsonProperty("Name")
	private String name;
	
	@JsonProperty("SerialNo")
	private String serialNo;
	
	@JsonProperty("EditShowStatus")
	private String editShowStatus;
	
	@JsonProperty("ReversedShowStatus")
	private String reversedShowStatus;
	
	@JsonProperty("Type")
	private String type;
}
