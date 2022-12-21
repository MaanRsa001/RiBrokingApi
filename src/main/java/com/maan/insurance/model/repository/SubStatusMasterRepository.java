package com.maan.insurance.model.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.maan.insurance.model.entity.SubStatusMaster;
import com.maan.insurance.model.entity.SubStatusMasterId;

public interface SubStatusMasterRepository extends JpaRepository<SubStatusMaster,SubStatusMasterId > , JpaSpecificationExecutor<SubStatusMasterId> {

}
