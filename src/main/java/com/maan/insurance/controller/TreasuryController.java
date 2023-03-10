package com.maan.insurance.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.maan.insurance.error.CommonValidationException;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.req.AllocateDetailsReq;
import com.maan.insurance.model.req.AllocateViewReq;
import com.maan.insurance.model.req.AllocatedStatusReq;
import com.maan.insurance.model.req.GetAllRetroTransContractReq;
import com.maan.insurance.model.req.GetAllTransContractReq;
import com.maan.insurance.model.req.GetReceiptAllocateReq;
import com.maan.insurance.model.req.GetReceiptEditReq;
import com.maan.insurance.model.req.GetReceiptGenerationReq;
import com.maan.insurance.model.req.GetReceiptReversalListReq;
import com.maan.insurance.model.req.GetRetroallocateTransactionReq;
import com.maan.insurance.model.req.GetReversalInfoReq;
import com.maan.insurance.model.req.GetShortnameReq;
import com.maan.insurance.model.req.GetTransContractReq;
import com.maan.insurance.model.req.GetTreasuryJournalViewReq;
import com.maan.insurance.model.req.InsertReceiptNoReq;
import com.maan.insurance.model.req.AllocateTransactionReq;
import com.maan.insurance.model.req.CurrecyAmountReq;
import com.maan.insurance.model.req.GenerationReq;
import com.maan.insurance.model.req.PaymentRecieptReq;
import com.maan.insurance.model.req.ReceiptTreasuryReq;
import com.maan.insurance.model.req.ReceiptViewListReq;
import com.maan.insurance.model.req.RetroTransReq;
import com.maan.insurance.model.req.ReciptListReq;
import com.maan.insurance.model.req.ReverseInsertReq;
import com.maan.insurance.model.req.ReverseViewReq;
import com.maan.insurance.model.res.AllocateDetailsRes;
import com.maan.insurance.model.res.AllocateDetailsRes1;
import com.maan.insurance.model.res.AllocateViewCommonRes;
import com.maan.insurance.model.res.AllocateViewCommonRes1;
import com.maan.insurance.model.res.AllocatedStatusRes;
import com.maan.insurance.model.res.AllocatedStatusRes1;
import com.maan.insurance.model.res.GetAllRetroTransContractRes;
import com.maan.insurance.model.res.GetAllTransContractRes;
import com.maan.insurance.model.res.GetAllTransContractRes1;
import com.maan.insurance.model.res.GetDirectCedingReq;
import com.maan.insurance.model.res.GetDirectCedingRes;
import com.maan.insurance.model.res.GetDirectCedingRes1;
import com.maan.insurance.model.res.GetReceiptAllocateRes;
import com.maan.insurance.model.res.GetReceiptAllocateRes1;
import com.maan.insurance.model.res.GetReceiptEditRes;
import com.maan.insurance.model.res.GetReceiptEditRes1;
import com.maan.insurance.model.res.GetReceiptGenerationRes;
import com.maan.insurance.model.res.GetReceiptGenerationRes1;
import com.maan.insurance.model.res.GetReceiptReversalListRes;
import com.maan.insurance.model.res.GetReceiptReversalListRes1;
import com.maan.insurance.model.res.GetRetroallocateTransactionRes;
import com.maan.insurance.model.res.GetReversalInfoRes;
import com.maan.insurance.model.res.GetReversalInfoRes1;
import com.maan.insurance.model.res.GetShortnameRes;
import com.maan.insurance.model.res.GetTransContractRes;
import com.maan.insurance.model.res.GetTransContractRes1;
import com.maan.insurance.model.res.GetTreasuryJournalViewRes;
import com.maan.insurance.model.res.GetTreasuryJournalViewRes1;
import com.maan.insurance.model.res.InsertReceiptNoRes;
import com.maan.insurance.model.req.SecondPageInfoReq;
import com.maan.insurance.model.res.AllocateTransactionListRes;
import com.maan.insurance.model.res.CurrecyAmountListsRes;
import com.maan.insurance.model.res.GenerationListRes;
import com.maan.insurance.model.res.PaymentRecieptRes;
import com.maan.insurance.model.res.PaymentRecieptRes1;
import com.maan.insurance.model.res.ReceiptTreasuryListRes;
import com.maan.insurance.model.res.ReceiptTreasuryRes;
import com.maan.insurance.model.res.ReceiptViewListsRes;
import com.maan.insurance.model.res.ReciptGetLIstRes;
import com.maan.insurance.model.res.RetroTransRes;
import com.maan.insurance.model.res.ReciptListRes;
import com.maan.insurance.model.res.RetroTransListRes;
import com.maan.insurance.model.res.ReverseInsertRes;
import com.maan.insurance.model.res.ReverseViewRes;
import com.maan.insurance.model.res.ReverseViewRes1;
import com.maan.insurance.model.res.SecondPageInfoListsRes;
import com.maan.insurance.model.res.SecondPageInfoRes;
import com.maan.insurance.model.res.retro.CommonResponse;
import com.maan.insurance.model.res.retro.CommonSaveRes;
import com.maan.insurance.service.TreasuryService;
import com.maan.insurance.validation.TreasuryValidation;



@RestController
@RequestMapping("/Insurance/Treasury")
public class TreasuryController {
	Gson gson = new Gson();
	private Logger log = LogManager.getLogger(TreasuryController.class);
	
	@Autowired
	private TreasuryValidation treasuryValidation;
	@Autowired
	private TreasuryService treasuryservice;
	
	@PostMapping("/save")
	public CommonResponse savepaymentReciept(@RequestBody PaymentRecieptReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.PaymentRecieptvalidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.savepaymentReciept(req);
	}
	
	@PostMapping("/save/reverse")
	public ReverseInsertRes savereverseInsert(@RequestBody ReverseInsertReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.reverseInsertvalidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.savereverseInsert(req);
	}
	@PostMapping("/get/status")
	public AllocatedStatusRes1 getAllocatedStatus(@RequestBody AllocatedStatusReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.getAllocatedStatusVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getAllocatedStatus(req);
	
	}
	
	@PostMapping("/allocateview")
	public AllocateViewCommonRes1 allocateView(@RequestBody AllocateViewReq req) throws CommonValidationException {
		/*List<ErrorCheck> error=treasuryValidation.allocateViewValidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.allocateView(req);*/
		List<ErrorCheck> error=treasuryValidation.allocateViewVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.allocateView(req);
		
			}
	
	@GetMapping("/get/receiptedit/{paymentReceiptNo}/{branchCode}")
	public GetReceiptEditRes1 getReceiptEdit( @PathVariable ("paymentReceiptNo") String paymentReceiptNo, @PathVariable("branchCode") String branchCode) throws CommonValidationException {
		return treasuryservice.getReceiptEdit(paymentReceiptNo,branchCode);
	}
	
	@PostMapping("/get/receiptGeneration")
	public GetReceiptGenerationRes1 getReceiptGeneration(@RequestBody GetReceiptGenerationReq req) throws CommonValidationException {
		
		List<ErrorCheck> error=treasuryValidation.getReceiptGenerationVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getReceiptGeneration(req);
		
	}
	
	@PostMapping("/get/reversalinfo")
	public GetReversalInfoRes1 getReversalInfo(@RequestBody GetReversalInfoReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.getReversalInfoVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getReversalInfo(req);
		
	}
	
	@PostMapping("/reverseview")
	public ReverseViewRes1 reverseView(@RequestBody ReverseViewReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.reverseViewallocateViewValidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.reverseView(req);
		}
	
	@PostMapping("/get/ReceiptReversalList")
	public GetReceiptReversalListRes1 getReceiptReversalList(@RequestBody GetReceiptReversalListReq req) throws CommonValidationException {
		
		List<ErrorCheck> error=treasuryValidation.getReceiptReversalListVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getReceiptReversalList(req);
		
		
	}
	
	@PostMapping("/get/receiptallocate")
	public GetReceiptAllocateRes1 getReceiptAllocate(@RequestBody GetReceiptAllocateReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.getReceiptAllocateVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getReceiptAllocate(req);
		}
	
	@PostMapping("/allocate/details")
	public AllocateDetailsRes1 allocateDetails(@RequestBody AllocateDetailsReq req) throws CommonValidationException {
	
	List<ErrorCheck> error=treasuryValidation.allocateDetailsVali(req);
	if(error!=null && error.size()>0) {
		throw new CommonValidationException("error",error);
	}
	return treasuryservice.allocateDetails(req);
	}
	
	@PostMapping("/get/transcontract")
	public GetTransContractRes1 getTransContract(@RequestBody GetTransContractReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.getTransContractVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getTransContract(req);
		}
	
	@PostMapping("/get/allTransContract")
	public GetAllTransContractRes1 insertReceiptNo(@RequestBody GetAllTransContractReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.getAllTransContractVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getAllTransContract(req);
		}
		
	
	@GetMapping("/get/directceding/{branchId}")
	public GetDirectCedingRes1 getDirectCeding(@PathVariable ("branchId") String branchId ) throws CommonValidationException {
		
		
			return treasuryservice.getDirectCeding( branchId);
		
	}
	
	@GetMapping("/get/shortname/{branchcode}")
	public GetShortnameRes getShortname(@PathVariable ("branchcode") String branchcode ) throws CommonValidationException {
		
	
			return treasuryservice.getShortname(branchcode);
		
	}
	
	@PostMapping("/get/treasury/journalview")
	public GetTreasuryJournalViewRes1 getTreasuryJournalView(@RequestBody GetTreasuryJournalViewReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.getTreasuryJournalViewVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getTreasuryJournalView(req);
	}
	
	@PostMapping("/get/RetroallocateTransaction")
	public  CommonResponse getRetroallocateTransaction(@RequestBody GetRetroallocateTransactionReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.getRetroallocateTransactionVali(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getRetroallocateTransaction(req);
		
	}
	
	/*@PostMapping("/getAll/RetroTransContract")
	public  List<GetAllRetroTransContractRes> getAllRetroTransContract(@RequestBody GetRetroallocateTransactionReq req) throws CommonValidationException {
		
		 List<GetAllRetroTransContractRes> data = treasuryservice.getAllRetroTransContract(req);
		if (data != null) {
			return treasuryservice.getAllRetroTransContract(req);
		} else {
			return null;
		}

	}*/

	@PostMapping("/getlist")
	public ReciptGetLIstRes getRecipt(@RequestBody ReciptListReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.Reciptgetvalidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
	   return treasuryservice.getReciptList(req);
	}
	@PostMapping("/getretrotrans")
	public RetroTransListRes getRetroTransContract(@RequestBody RetroTransReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.RetroTransvalidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
	   return treasuryservice.getRetroTransContract(req);
	}
	@PostMapping("/getreceipttreasury")
	public ReceiptTreasuryListRes getReceiptTreasuryGeneration(@RequestBody ReceiptTreasuryReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.ReciptTreasuryvalidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
	   return treasuryservice.getReceiptTreasuryGeneration(req);
	}
	@PostMapping("/getreceiptviewlist")
	public ReceiptViewListsRes getReceiptViewList(@RequestBody ReceiptViewListReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.ReciptviewListvalidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
	   return treasuryservice.getReceiptViewList(req);
	}
	@PostMapping("/getcurrecyamount")
	public CommonSaveRes getCurrecyAmount(@RequestBody CurrecyAmountReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.validateCurrencyAmount(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
	   return treasuryservice.getCurrecyAmount(req);
	}
	@PostMapping("/getsecondpageinfo")
	public SecondPageInfoRes getSecondPageInfo(@RequestBody SecondPageInfoReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.validateSecondPageInfo(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
	   return treasuryservice.getSecondPageInfo(req);
	}
	@PostMapping("/get/transcont")
	public GetTransContractRes1 getTraContract(@RequestBody GetTransContractReq req) throws CommonValidationException {
		
		List<ErrorCheck> error=treasuryValidation.validateTransCont(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
			return treasuryservice.getTransContract(req);
		
	} 
	@PostMapping("/getAllocateTransaction")
	public CommonResponse getAllocateTransaction(@RequestBody GetTransContractReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.validateTransCont(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.getAllocateTransaction(req);
	}

	@PostMapping("/receipt/detail")
	public PaymentRecieptRes1 receiptdetail(@RequestBody PaymentRecieptReq req) throws CommonValidationException {
		List<ErrorCheck> error=treasuryValidation.PaymentRecieptvalidate(req);
		if(error!=null && error.size()>0) {
			throw new CommonValidationException("error",error);
		}
		return treasuryservice.receiptdetail(req);
	}
	
}
