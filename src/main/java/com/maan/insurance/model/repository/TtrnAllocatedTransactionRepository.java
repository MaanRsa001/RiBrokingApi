package com.maan.insurance.model.repository;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.maan.insurance.model.entity.TtrnAllocatedTransaction;


@Transactional
public interface TtrnAllocatedTransactionRepository extends JpaRepository<TtrnAllocatedTransaction, Long> {



	List<TtrnAllocatedTransaction> findBySno(BigDecimal bigDecimal);

	//List<TtrnAllocatedTransaction>  findTop1ByOrderBySnoDesc();

	//List<TtrnAllocatedTransaction> findTop1BySnoNotNullOrderBySnoDesc();

	//List<TtrnAllocatedTransaction> findBySnoNotNullOrderBySnoDesc(Pageable paging);

	List<TtrnAllocatedTransaction> findBySnoNotNull(Pageable paging);

	List<TtrnAllocatedTransaction> OrderBySno();

	TtrnAllocatedTransaction findByTransactionNoAndSno(BigDecimal bigDecimal, BigDecimal bigDecimal2);

	TtrnAllocatedTransaction findByReceiptNoAndSno(BigDecimal bigDecimal, BigDecimal bigDecimal2);

	int countByContractNoAndTransactionNoAndLayerNoAndTypeAndStatus(String contractNo,
			BigDecimal bigDecimal, String layerno, String string, String string2);

	int countByTransactionNoAndStatus(BigDecimal bigDecimal, String string);

}
