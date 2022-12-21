package com.maan.insurance.model.repository;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.TmasTerritory;
import com.maan.insurance.model.entity.TmasTerritoryCountryMapping;

public interface TmasTerritoryCountryMappingRepository extends JpaRepository<TmasTerritoryCountryMapping,BigDecimal> , JpaSpecificationExecutor<TmasTerritory> {

}
