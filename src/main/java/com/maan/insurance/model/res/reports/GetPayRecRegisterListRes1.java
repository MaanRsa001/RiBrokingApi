package com.maan.insurance.model.res.reports;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;

import lombok.Data;

@Data
public class GetPayRecRegisterListRes1 {

	@JsonProperty("PaymentReceiptNo")
	private String paymentReceiptNo ;
    @JsonProperty("TransDate")
	private String transDate ;
    @JsonProperty("AmendmentDate")
	private String amendmentDate ;
    @JsonProperty("TransType")
	private String transType ;
    @JsonProperty("BrokerName")
	private String brokerName ;
    @JsonProperty("CedingId")
	private String cedingId ;
    @JsonProperty("ReceiptBank")
	private String receiptBank ;
    @JsonProperty("CurrencyName")
	private String currencyName ;
    @JsonProperty("ExchangeRate")
	private String exchangeRate ;
    @JsonProperty("GrossAmt")
	private String grossAmt ;
    @JsonProperty("BankCharges")
	private String bankCharges ;
    @JsonProperty("HoldtaxrecoverOc")
	private String holdtaxrecoverOc ;
    @JsonProperty("NetAmt")
	private String netAmt ;
    @JsonProperty("GrossAmtDc")
	private String grossAmtDc ;
    @JsonProperty("BankChargesDc")
	private String bankChargesDc ;
    @JsonProperty("HoldtaxrecoverUgx")
	private String holdtaxrecoverUgx ;
    @JsonProperty("NetAmtDc")
	private String netAmtDc ;
    @JsonProperty("DiffType")
	private String diffType ;
    @JsonProperty("Exchangedifferenceamount")
	private String exchangedifferenceamount ;
    @JsonProperty("ExchageDiffPerc")
	private String exchageDiffPerc ;
    @JsonProperty("RoundOffOc")
	private String roundOffOc ;
    @JsonProperty("RoundOffDc")
	private String roundOffDc ;
    @JsonProperty("Transcationtype")
	private String transcationtype ;
    @JsonProperty("Reversaltransno")
	private String reversaltransno ;
    @JsonProperty("Remarks")
	private String remarks ;
    @JsonProperty("Sno")
	private String sno ;
    @JsonProperty("CurrencyId")
	private String currencyId ;
    @JsonProperty("AmountOc")
	private String amountOc ;
    @JsonProperty("Clientexchangerate")
	private String clientexchangerate ;
    @JsonProperty("ConvertedPaidAmt")
	private String convertedPaidAmt ;
    @JsonProperty("Exchangerate")
	private String exchangerate ;
    @JsonProperty("TotalAmountDc")
	private String totalAmountDc ;
    @JsonProperty("AllocatedTillDate")
	private String allocatedTillDate ;
    @JsonProperty("PendingAmount")
	private String pendingAmount ;
    @JsonProperty("AllocatedAmountUgx")
	private String allocatedAmountUgx ;
    @JsonProperty("PendingAmountUgx")
	private String pendingAmountUgx ;
	
}
