/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-09-15 ( Date ISO 2022-09-15 - Time 15:41:51 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-09-15 ( 15:41:51 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


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




/**
* Domain class for entity "PersonalInfo"
*
* @author Telosys Tools Generator
*
*/
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@DynamicInsert
@DynamicUpdate
@Builder
@IdClass(PersonalInfoId.class)
@Table(name="PERSONAL_INFO")


public class PersonalInfo implements Serializable {
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="CUSTOMER_ID", nullable=false)
    private BigDecimal customerId ;

    @Id
    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    @Id
    @Column(name="BRANCH_CODE", nullable=false, length=8)
    private String     branchCode ;

    //--- ENTITY DATA FIELDS 
    @Column(name="APPLICATION_ID", length=25)
    private String     applicationId ;

    @Column(name="TITLE", length=10)
    private String     title ;

    @Column(name="FIRST_NAME", length=200)
    private String     firstName ;

    @Column(name="LAST_NAME", length=25)
    private String     lastName ;

    @Column(name="NATIONALITY", length=25)
    private String     nationality ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="DOB")
    private Date       dob ;

    @Column(name="GENDER", length=1)
    private String     gender ;

    @Column(name="TELEPHONE", length=500)
    private String     telephone ;

    @Column(name="MOBILE", length=20)
    private String     mobile ;

    @Column(name="FAX", length=500)
    private String     fax ;

    @Column(name="EMAIL", length=500)
    private String     email ;

    @Column(name="ADDRESS1", length=500)
    private String     address1 ;

    @Column(name="ADDRESS2", length=150)
    private String     address2 ;

    @Column(name="OCCUPATION", length=50)
    private String     occupation ;

    @Column(name="POBOX", length=25)
    private String     pobox ;

    @Column(name="COUNTRY", length=25)
    private String     country ;

    @Column(name="EMIRATE", length=25)
    private String     emirate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="INCEPTION_DATE")
    private Date       inceptionDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EXPIRY_DATE")
    private Date       expiryDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="EFFECTIVE_DATE")
    private Date       effectiveDate ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="ENTRY_DATE")
    private Date       entryDate ;

    @Column(name="REMARKS", length=100)
    private String     remarks ;

    @Column(name="STATUS", length=1)
    private String     status ;

    @Column(name="LOGIN_ID", length=15)
    private String     loginId ;

    @Column(name="COMPANY_NAME", length=200)
    private String     companyName ;

    @Column(name="MISSIPPI_CUSTOMER_CODE")
    private BigDecimal missippiCustomerCode ;

    @Column(name="CUSTOMER_TYPE", length=1)
    private String     customerType ;

    @Column(name="CED_COMPANY_PHONE", length=100)
    private String     cedCompanyPhone ;

    @Column(name="CITY", length=200)
    private String     city ;

    @Column(name="COMPANY_CODE", length=30)
    private String     companyCode ;

    @Column(name="ORIGINAL_CURRENCY", length=20)
    private String     originalCurrency ;

    @Column(name="PAN_NUMBER", length=15)
    private String     panNumber ;

    @Column(name="INDIVIDUALYN", length=5)
    private String     individualyn ;

    @Column(name="NON_RESIDENTYN", length=5)
    private String     nonResidentyn ;

    @Column(name="SPECIAL_RATE", length=15)
    private String     specialRate ;

    @Column(name="CON_DEPARTMENT", length=300)
    private String     conDepartment ;

    @Column(name="BANK_CURRENCY", length=300)
    private String     bankCurrency ;

    @Column(name="BANK_ACC_NO", length=300)
    private String     bankAccNo ;

    @Column(name="BANK_NAME", length=500)
    private String     bankName ;

    @Column(name="BANK_ACC_NAME", length=500)
    private String     bankAccName ;

    @Column(name="SWIFT_CODE", length=200)
    private String     swiftCode ;

    @Column(name="IFSC_CODE", length=200)
    private String     ifscCode ;

    @Column(name="BANK_REMARKS", length=1000)
    private String     bankRemarks ;

    @Column(name="BROKER_GROUP", length=100)
    private String     brokerGroup ;

    @Column(name="ESTB_YEAR")
    private BigDecimal estbYear ;

    @Column(name="REG_NO", length=100)
    private String     regNo ;

    @Column(name="CIN_NO", length=100)
    private String     cinNo ;

    @Column(name="RATING", length=100)
    private String     rating ;

    @Column(name="RATING_AGENCY", length=100)
    private String     ratingAgency ;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="LAST_RATING")
    private Date       lastRating ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}


