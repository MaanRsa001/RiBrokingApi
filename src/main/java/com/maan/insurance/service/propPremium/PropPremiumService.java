package com.maan.insurance.service.propPremium;

import org.springframework.stereotype.Service;

import com.maan.insurance.model.req.propPremium.ClaimTableListReq;
import com.maan.insurance.model.req.propPremium.ContractDetailsReq;
import com.maan.insurance.model.req.propPremium.GetConstantPeriodDropDownReq;
import com.maan.insurance.model.req.propPremium.GetPreListReq;
import com.maan.insurance.model.req.propPremium.GetPremiumDetailsReq;
import com.maan.insurance.model.req.propPremium.GetPremiumReservedReq;
import com.maan.insurance.model.req.propPremium.GetPremiumedListReq;
import com.maan.insurance.model.req.propPremium.GetSPRetroListReq;
import com.maan.insurance.model.req.propPremium.InsertPremiumReq;
import com.maan.insurance.model.req.propPremium.PremiumEditReq;
import com.maan.insurance.model.req.propPremium.SubmitPremiumReservedReq;
import com.maan.insurance.model.req.propPremium.ViewPremiumDetailsRIRes;
import com.maan.insurance.model.res.propPremium.ClaimTableListMode1Res;
import com.maan.insurance.model.res.propPremium.ContractDetailsRes;
import com.maan.insurance.model.res.propPremium.CurrencyListRes;
import com.maan.insurance.model.res.propPremium.GetAllocatedCassLossCreditRes;
import com.maan.insurance.model.res.propPremium.GetAllocatedListRes;
import com.maan.insurance.model.res.propPremium.GetAllocatedTransListRes;
import com.maan.insurance.model.res.propPremium.GetBrokerAndCedingNameRes;
import com.maan.insurance.model.res.propPremium.GetCashLossCreditRes;
import com.maan.insurance.model.res.propPremium.GetClaimNosDropDownRes;
import com.maan.insurance.model.res.propPremium.GetConstantPeriodDropDownRes;
import com.maan.insurance.model.res.propPremium.GetContractPremiumRes;
import com.maan.insurance.model.res.propPremium.GetCountCleanCUTRes;
import com.maan.insurance.model.res.propPremium.GetDepartmentNoRes;
import com.maan.insurance.model.res.propPremium.GetDepositReleaseCountRes;
import com.maan.insurance.model.res.propPremium.GetOSBListRes;
import com.maan.insurance.model.res.propPremium.GetPreListRes;
import com.maan.insurance.model.res.propPremium.GetPremiumDetailsRes;
import com.maan.insurance.model.res.propPremium.GetPremiumReservedRes1;
import com.maan.insurance.model.res.propPremium.GetPremiumedListRes;
import com.maan.insurance.model.res.propPremium.GetPreviousPremiumRes;
import com.maan.insurance.model.res.propPremium.GetRetroContractsRes;
import com.maan.insurance.model.res.propPremium.GetSPRetroListRes;
import com.maan.insurance.model.res.propPremium.GetSumOfShareSignRes;
import com.maan.insurance.model.res.propPremium.InsertPremiumRes;
import com.maan.insurance.model.res.propPremium.PremiumEditRes;
import com.maan.insurance.model.res.propPremium.SubmitPremiumReservedRes;
import com.maan.insurance.model.res.propPremium.ViewPremiumDetailsRIReq;
import com.maan.insurance.model.res.propPremium.premiumUpdateMethodRes;

@Service
public interface PropPremiumService {

	GetPremiumedListRes getPremiumedList(GetPremiumedListReq req);

	GetPreListRes getPreList(GetPreListReq req);

	GetConstantPeriodDropDownRes getConstantPeriodDropDown(GetConstantPeriodDropDownReq req);

	GetPreviousPremiumRes getPreviousPremium(String contractNo);

	GetContractPremiumRes getContractPremium(String contractNo, String departmentId, String branchCode);

	GetClaimNosDropDownRes getClaimNosDropDown(String contractNo);

	ContractDetailsRes contractDetails(ContractDetailsReq req);

	ClaimTableListMode1Res claimTableListMode1(ClaimTableListReq req);

	GetCountCleanCUTRes getCountCleanCUT(String contractNo);

	GetSPRetroListRes getSPRetroList(GetSPRetroListReq req);

	GetRetroContractsRes getRetroContracts(String proposalNo, String noOfRetro);

	GetSumOfShareSignRes getSumOfShareSign(String retroContractNo);

	GetDepartmentNoRes getDepartmentNo(String contractNo);

	GetOSBListRes getOSBList(String transaction, String contractNo, String branchCode);

	InsertPremiumRes insertPremium(InsertPremiumReq req);

	GetPremiumDetailsRes getPremiumDetails(GetPremiumDetailsReq req);

	PremiumEditRes premiumEdit(PremiumEditReq req);

	GetBrokerAndCedingNameRes getBrokerAndCedingName(String contNo, String branchCode);

	GetAllocatedListRes getAllocatedList(String contNo, String transactionNo);

	CurrencyListRes currencyList(String branchCode);



	GetPremiumReservedRes1 getPremiumReserved(GetPremiumReservedReq req);

	GetCashLossCreditRes getCassLossCredit(InsertPremiumReq req);

	GetAllocatedTransListRes getAllocatedTransList(String proposalNo);

	GetAllocatedCassLossCreditRes getAllocatedCassLossCredit(String proposalNo, String branchCode);

	SubmitPremiumReservedRes submitPremiumReserved(SubmitPremiumReservedReq req);

	GetDepositReleaseCountRes getDepositReleaseCount(String dropDown, String contractNo, String branchCode, String type);

	premiumUpdateMethodRes premiumUpdateMethod(InsertPremiumReq req);

	ViewPremiumDetailsRIRes viewPremiumDetailsRI(ViewPremiumDetailsRIReq req);

}
