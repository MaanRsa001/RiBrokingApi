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

@Table(name="TMAS_TERRITORY_COUNTRY_MAPPING")  
public class TmasTerritoryCountryMapping implements Serializable {
	
	private static final long serialVersionUID = 1L;
	 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="TERRITORY_ID", nullable=false)
    private BigDecimal territoryId ;
    
  //--- ENTITY DATA FIELDS 
    @Column(name="COUNTRY_ID")
    private String     countryId ;
    
    @Column(name="AMEND_ID")
    private String     amendId ;

    @Column(name="LOGIN_ID")
    private String     loginId ;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="SYS_DATE")
    private Date       sysDate ;

    @Column(name="BRANCH_CODE")
    private String     branchCode ;

    @Column(name="S_NO")
    private String     sNO ;

}
