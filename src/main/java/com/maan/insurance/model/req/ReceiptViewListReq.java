package com.maan.insurance.model.req;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

@Data

public class ReceiptViewListReq {
	@JsonProperty("BranchCode")
	private String branchCode;
	@JsonProperty("Serial_no")
	private String serial_no;

}
