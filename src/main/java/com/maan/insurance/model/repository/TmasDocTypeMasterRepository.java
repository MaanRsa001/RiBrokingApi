/*
 * Java domain class for entity "TmasDocTypeMaster" 
 * Created on 2022-09-15 ( Date ISO 2022-09-15 - Time 15:42:27 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-09-15 ( 15:42:27 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.insurance.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TmasDocTypeMaster;
import com.maan.insurance.model.entity.TmasDocTypeMasterId;
/**
 * <h2>TmasDocTypeMasterRepository</h2>
 *
 * createdAt : 2022-09-15 - Time 15:42:27
 * <p>
 * Description: "TmasDocTypeMaster" Repository
 */
 
 
 
public interface TmasDocTypeMasterRepository  extends JpaRepository<TmasDocTypeMaster,TmasDocTypeMasterId > , JpaSpecificationExecutor<TmasDocTypeMaster> {

}