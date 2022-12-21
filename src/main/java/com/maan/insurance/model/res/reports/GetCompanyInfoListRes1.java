package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class GetCompanyInfoListRes1 {
	@JsonProperty("CompanyName")
	private String companyName;
	@JsonProperty("Telephone")
	private String telephone;
	@JsonProperty("Mobile")
	private String mobile;
	@JsonProperty("Fax")
	private String fax;
	@JsonProperty("Email")
	private String email;
	@JsonProperty("Pobox")
	private String pobox;
	@JsonProperty("Address1")
	private String address1;
	@JsonProperty("Address2")
	private String address2;
	@JsonProperty("City")
	private String city;
	@JsonProperty("Country")
	private String country;
	@JsonProperty("BaseCurrency")
	private String baseCurrency;
	@JsonProperty("DestCurrency")
	private String destCurrency;
	@JsonProperty("FinancialYear")
	private String financialYear;
	@JsonProperty("BandReport")
	private String bandReport;
	@JsonProperty("CurrencyDecimal")
	private String currencyDecimal;
	@JsonProperty("WebSite")
	private String webSite;
}
