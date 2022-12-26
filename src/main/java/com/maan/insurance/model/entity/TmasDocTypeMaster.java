/* 
*  Copyright (c) 2019. All right reserved
 * Created on 2022-09-15 ( Date ISO 2022-09-15 - Time 15:42:27 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */

/*
 * Created on 2022-09-15 ( 15:42:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.insurance.model.entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Table;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

import java.math.BigDecimal;
import javax.persistence.*;




/**
* Domain class for entity "TmasDocTypeMaster"
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
@IdClass(TmasDocTypeMasterId.class)
@Table(name="TMAS_DOC_TYPE_MASTER")


public class TmasDocTypeMaster implements Serializable { 
 
private static final long serialVersionUID = 1L;
 
    //--- ENTITY PRIMARY KEY 
    @Id
    @Column(name="DOC_TYPE", nullable=false)
    private BigDecimal docType ;

    @Id
    @Column(name="MODULE_TYPE", nullable=false, length=5)
    private String     moduleType ;

    @Id
    @Column(name="AMEND_ID", nullable=false)
    private BigDecimal amendId ;

    @Id
    @Column(name="PRODUCT_ID", nullable=false)
    private BigDecimal productId ;

    @Id
    @Column(name="BRANCH_CODE", nullable=false, length=8)
    private String     branchCode ;

    //--- ENTITY DATA FIELDS 
    @Column(name="SNO")
    private BigDecimal sno ;

    @Column(name="DOC_NAME", length=100)
    private String     docName ;

    @Column(name="STATUS", nullable=false, length=1)
    private String     status ;

    @Column(name="REMARKS", length=500)
    private String     remarks ;


    //--- ENTITY LINKS ( RELATIONSHIP )


}



