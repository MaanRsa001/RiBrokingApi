package com.maan.insurance.model.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="TMAS_OPEN_PERIOD")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class TmasOpenPeriod implements Serializable{  
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SNO")
	private BigDecimal sno;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "START_DATE")
	private Date startDate;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "END_DATE")
	private Date endDate;
	
	@Column(name = "YEAR")
	private BigDecimal year;
	
	@Column(name = "STATUS")
	private String status;
	
	@Column(name = "REMARKS")
	private String remarks;
	
	@Column(name = "BRANCH_CODE")
	private String branchCode;
}
