/*
 * Java domain class for entity "CountryMaster" 
 * Created on 2022-09-15 ( Date ISO 2022-09-15 - Time 15:41:33 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-09-15 ( 15:41:33 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.insurance.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.CountryMaster;
import com.maan.insurance.model.entity.CountryMasterId;
/**
 * <h2>CountryMasterRepository</h2>
 *
 * createdAt : 2022-09-15 - Time 15:41:33
 * <p>
 * Description: "CountryMaster" Repository
 */
 
 
 
public interface CountryMasterRepository  extends JpaRepository<CountryMaster,CountryMasterId > , JpaSpecificationExecutor<CountryMaster> {

	List<CountryMaster> findByBranchCodeAndCountryIdIn(String branchCode, List<BigDecimal> countryIncludedList1);

}
