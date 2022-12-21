package com.maan.insurance.model.res;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data
public class ReciptViewRes {
	@JsonProperty("Pay_res")
	private String pay_res;
	@JsonProperty("Serial_no")
	private String serial_no;
	@JsonProperty("Payamount")
	private String payamount;
	@JsonProperty("Exrate")
	private String exrate;
	@JsonProperty("Inceptiobdate")
	private String inceptiobdate;
	@JsonProperty("Currency")
	private String currency;
	@JsonProperty("TotAmount")
	private String totAmount;
	@JsonProperty("SetExcRate")
	private String setExcRate;
	@JsonProperty("ConRecCur")
	private String conRecCur;
	@JsonProperty("HideRowCnt")
	private String hideRowCnt;

}
