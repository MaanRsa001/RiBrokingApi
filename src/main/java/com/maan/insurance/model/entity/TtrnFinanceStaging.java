package com.maan.insurance.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Builder;
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
@DynamicInsert
@DynamicUpdate
@Builder
@IdClass(TtrnFinanceStagingId.class)
@Table(name="TTRN_FINANCE_STAGING")
public class TtrnFinanceStaging implements Serializable { 
	 
	private static final long serialVersionUID = 1L;
	 
	    //--- ENTITY PRIMARY KEY 
	    @Id
	    @Column(name="UNIQUE_ID", nullable=false)
	    private String uniqueId ;

	    @Id
	    @Column(name="BRANCH_CODE", nullable=false)
	    private BigDecimal branchCode ;

	    //--- ENTITY DATA FIELDS 
	    @Column(name="JOURNAL_ID")
	    private BigDecimal     journalId ;

	    @Column(name="SERIAL_NO")
	    private BigDecimal     serialNo ;

	    @Column(name="REFERENCE")
	    private String     reference ;

	    @Column(name="VOUCHERSUBTYPE")
	    private String     voucherSubtype ;

	    @Column(name="VOUCHERTYPE")
	    private String voucherType ;

	    @Column(name="LEDGER")
	    private String     ledger ;

	    @Column(name="D_C")
	    private String     dC ;

	    @Column(name="DEBITOC")
	    private BigDecimal     debitoc ;
	    
	    @Column(name="UWY")
	    private BigDecimal     uwy ;

	    @Column(name="CREDITOC")
	    private BigDecimal     creditoc ;

	    @Column(name="DEBITUGX")
	    private BigDecimal     debitugx ;

	    @Column(name="CREDITUGX")
	    private BigDecimal     creditugx ;

	    @Column(name="POSTING_DEBIT")
	    private BigDecimal postingDebit ;

	    @Column(name="POSTING_CREDIT")
	    private BigDecimal     postingCredit ;

	    @Column(name="CURRENCYSYMBOL")
	    private BigDecimal     currencysymbol ;

	    @Column(name="EXCHANGE_RATE")
	    private BigDecimal     exchangeRate ;
	    
	    @Column(name="PRODUCT_ID")
	    private BigDecimal     productId ;

	    @Column(name="SPC")
	    private String     spc ;

	    @Column(name="CURRENCY")
	    private String     currency ;

	    @Column(name="NARRATION")
	    private String narration ;

	    @Column(name="BASE_CURRENCY")
	    private String     baseCurrency ;

	    @Column(name="JV_TYPE")
	    private String     jvType ;

	    @Column(name="JV_ID")
	    private BigDecimal     jvId ;
	    
	    @Column(name="TRANSACTION_NO")
	    private BigDecimal     transactionNo ;
	    
	    @Column(name="POSTED")
	    private String     posted ;

	    @Column(name="REVERSAL_STATUS")
	    private String     reversalStatus ;

	    @Column(name="JV_MONTH")
	    private String jvMonth ;

	    @Column(name="INSTRUMENT_NO")
	    private String     instrumentNo ;

	    @Column(name="POST")
	    private String     post ;

	    @Column(name="TALLY_ID")
	    private String     tallyId ;
	    
	    @Column(name="TRANSFER_TYPE")
	    private String     transferType ;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="STARTDATE")
	    private Date       startDate ;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="END_DATE")
	    private Date       endDate ;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="ENTRY_DATE")
	    private Date       entryDate ;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="JV_DATE")
	    private Date       jvDate ;
	    
	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="LOGIC_DATE")
	    private Date       logicDate ;

	    @Temporal(TemporalType.TIMESTAMP)
	    @Column(name="INSTRUMENT_DATE")
	    private Date       instrumentDate ;


	    //--- ENTITY LINKS ( RELATIONSHIP )


	}



