/*
 * Java domain class for entity "TerritoryMaster" 
 * Created on 2022-09-15 ( Date ISO 2022-09-15 - Time 15:42:21 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-09-15 ( 15:42:21 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.insurance.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TerritoryMaster;
import com.maan.insurance.model.entity.TerritoryMasterId;
/**
 * <h2>TerritoryMasterRepository</h2>
 *
 * createdAt : 2022-09-15 - Time 15:42:21
 * <p>
 * Description: "TerritoryMaster" Repository
 */
 
 
 
public interface TerritoryMasterRepository  extends JpaRepository<TerritoryMaster,TerritoryMasterId > , JpaSpecificationExecutor<TerritoryMaster> {

}
