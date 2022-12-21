package com.maan.insurance.model.repository;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.maan.insurance.model.entity.TmasProductMaster;
import com.maan.insurance.model.entity.TmasProductMasterKey;
import com.maan.insurance.model.entity.TtrnFinanceStaging;
import com.maan.insurance.model.entity.TtrnFinanceStagingId;
@Repository
public interface TtrnFinanceStagingRepository extends JpaRepository<TtrnFinanceStaging,TtrnFinanceStagingId> , JpaSpecificationExecutor<TtrnFinanceStagingId> {

	List<TtrnFinanceStaging> findByStartDateAndEndDateAndBranchCodeAndPost(Date parse, Date parse2,
			BigDecimal bigDecimal, String post);

}
