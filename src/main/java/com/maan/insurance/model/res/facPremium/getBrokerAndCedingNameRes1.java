package com.maan.insurance.model.res.facPremium;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class getBrokerAndCedingNameRes1 {
	
	@JsonProperty("CustomerId")
	private String customerId;

	@JsonProperty("Broker")
	private String broker;
	
	@JsonProperty("Address")
	private String address;

}
