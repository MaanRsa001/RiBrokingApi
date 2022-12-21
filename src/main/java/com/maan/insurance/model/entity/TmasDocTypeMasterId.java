/*
 * Created on 2022-09-15 ( 15:42:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */
package com.maan.insurance.model.entity;

import lombok.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


import java.math.BigDecimal;

/**
 * Composite primary key for entity "TmasDocTypeMaster" ( stored in table "TMAS_DOC_TYPE_MASTER" )
 *
 * @author Telosys
 *
 */

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TmasDocTypeMasterId implements Serializable {

    private static final long serialVersionUID = 1L;

    //--- ENTITY KEY ATTRIBUTES 
    private BigDecimal docType ;
    
    private String     moduleType ;
    
    private BigDecimal amendId ;
    
    private BigDecimal productId ;
    
    private String     branchCode ;
    
     
}