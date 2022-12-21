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
@Table(name="TTRN_DEPOSIT_RELEASE")
public class TtrnDepositRelease {

	@Id
	@Column(name="PROPOSAL_NO")
	private String proposalNo;
	
	@Column(name="CONTRACT_NO")
	private String contractNo;
	
	@Column(name="DEPT_ID")
	private String deptId;
	
	@Column(name="RELEASE_TYPE")
	private String releaseType;
	
	@Column(name="RL_NO")
	private String rlNo;
	
	@Column(name="RL_TRANSACTION_NO")
	private String rlTransactionNo;
	
	@Column(name="RL_TRANSACTION_DATE")
	private Date rlTransactionDate;
	
	@Column(name="RT_TRANSACTION_NO")
	private String rtTransactionNo;
	
	@Column(name="RT_TRANSACTION_DATE")
	private Date rtTransactionDate;
	
	@Column(name="RL_CURRENCY_ID")
	private Long rlCurrencyId;
	
	@Column(name="RT_CURRENCY_ID")
	private Long rtCurrencyId;
	
	@Column(name="RL_AMOUNT_IN_RL_CURR")
	private Long rlAmountInRlCurr;
	
	@Column(name="RL_AMOUNT_IN_RT_CURR")
	private Long rlAmountInRtCurr;
	
	@Column(name="EXCHANGE_RATE")
	private Long exchangeRate;
	
	@Column(name="LOGIN_ID")
	private String loginId;
	
	@Column(name="BRANCH_CODE")
	private String branchCode;
	
	@Column(name="SYS_DATE")
	private Date sysDate;
	
	@Column(name="TEMP_REQUEST_NO")
	private BigDecimal tempRequestNo;
	
	@Column(name="TABLE_MOVE_STATUS")
	private String tableMoveStatus;
	
}
