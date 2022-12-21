package com.maan.insurance.model.entity;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="TTRN_CASH_LOSS_CREDIT")
public class TtrnCashLossCredit {

	@Id
	@Column(name="CLC_NO")
	private BigDecimal clcNo;
	
	@Column(name="CONTRACT_NO")
	private String contractNo;
	
	@Column(name="CLAIM_NO")
	private Long claimNo;
	
	@Column(name="CLAIMPAYMENT_NO")
	private Long claimpaymentNo;
	
	@Column(name="CLCCURRENCY_ID")
	private Long clccurrencyId;
	
	@Column(name="CREDITAMOUNTCLC")
	private Long creditamountclc;
	
	@Column(name="CLDCURRENCY_ID")
	private Long cldcurrencyId;
	
	@Column(name="EXCHANGE_RATE")
	private Long exchangeRate;
	
	@Column(name="CREDITAMOUNTCLD")
	private Long creditamountcld;
	
	@Column(name="CREDITTRXNNO")
	private String credittrxnno;
	
	@Column(name="CREDITDATE")
	private Date creditdate;
	
	@Column(name="BRANCH_CODE")
	private String branchCode;
	
	@Column(name="TEMP_REQUEST_NO")
	private BigDecimal tempRequestNo;
	
	@Column(name="TABLE_MOVE_STATUS")
	private String tableMoveStatus;
	
	@Column(name="CLD_AMOUNT")
	private Long cldAmount;
	
	@Column(name="PROPOSAL_NO")
	private Long proposalNo;
	
	@Column(name="STATUS")
	private String status;
	
	@Column(name="REVERSAL_CLC")
	private Long reversalClc;
	
	@Column(name="REVERSAL_CLD")
	private Long reversalCld;
	
	@Column(name="REVERSAL_DATE")
	private Date reversalDate;
	
}
