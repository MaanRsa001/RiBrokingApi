/*
 * Java domain class for entity "TmasProductMaster" 
 * Created on 2022-09-15 ( Date ISO 2022-09-15 - Time 15:42:40 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-09-15 ( 15:42:40 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.insurance.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TmasProductMaster;
import com.maan.insurance.model.entity.TmasProductMasterKey;
/**
 * <h2>TmasProductMasterRepository</h2>
 *
 * createdAt : 2022-09-15 - Time 15:42:40
 * <p>
 * Description: "TmasProductMaster" Repository
 */
 
 
 
public interface TmasProductMasterRepository  extends JpaRepository<TmasProductMaster,TmasProductMasterKey > , JpaSpecificationExecutor<TmasProductMasterKey> {

	TmasProductMaster findByTmasProductIdAndBranchCodeAndTmasStatus(BigDecimal bigDecimal, String branchCode,
			String string);

	List<TmasProductMaster> findByBranchCodeAndTmasProductIdIn(String branchCode, List<BigDecimal> bigDecimalList);

}
