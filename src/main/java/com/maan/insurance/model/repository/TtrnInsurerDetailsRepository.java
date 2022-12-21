/*
 * Java domain class for entity "TtrnInsurerDetails" 
 * Created on 2022-09-15 ( Date ISO 2022-09-15 - Time 15:43:33 )
 * Generated by Telosys Tools Generator ( version 3.3.0 )
 */
 
 /*
 * Created on 2022-09-15 ( 15:43:33 )
 * Generated by Telosys ( http://www.telosys.org/ ) version 3.3.0
 */


package com.maan.insurance.model.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TtrnInsurerDetails;
import com.maan.insurance.model.entity.TtrnInsurerDetailsId;
/**
 * <h2>TtrnInsurerDetailsRepository</h2>
 *
 * createdAt : 2022-09-15 - Time 15:43:33
 * <p>
 * Description: "TtrnInsurerDetails" Repository
 */
 
 
 
public interface TtrnInsurerDetailsRepository  extends JpaRepository<TtrnInsurerDetails,TtrnInsurerDetailsId > , JpaSpecificationExecutor<TtrnInsurerDetails> {

	int countByProposalNoAndInsurerNoAndEndorsementNo(String proposalNo, BigDecimal bigDecimal,
			BigDecimal bigDecimal2);

}