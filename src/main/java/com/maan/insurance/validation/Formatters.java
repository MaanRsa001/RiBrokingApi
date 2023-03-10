package com.maan.insurance.validation;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;



import com.maan.insurance.model.req.GetRetroallocateTransactionReq;
import com.maan.insurance.service.impl.QueryImplemention;
import com.maan.insurance.service.impl.TreasuryServiceImpl;
import com.maan.insurance.service.impl.Dropdown.DropDownServiceImple;
@Service
public class Formatters {
	
	
	@Autowired
	private QueryImplemention queryImpl;


	public  String formatter(final String value)
	{
		String output="0.00";
		if(StringUtils.isNotBlank(value))
		{
			double doublevalue=Double.parseDouble(value);
			DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,##0.00");
			output =myFormatter.format(doublevalue);
		}
		return output;
	}


	public synchronized String getSequence(String type,String productID,String departmentId,String branchCode, String proposalNo,String date){ 
		Logger logger = LogUtil.getLogger(Formatters.class);
		String seqName="";
		try{
			
			//String query=getQuery(DBConstants.COMMON_SELECT_SEQNAME);
			//String query="select fn_get_seqno('Proposal',1,2,'06') from dual";
			String query="dropdowm.getseqno";
			logger.info("Query==> " + query);
			logger.info("Type==> " + type + ", Product ID==> " + productID + ", dept ID==> " + departmentId + ", Branch Code==> " + branchCode);
			List<Map<String, Object>> list = queryImpl.selectList(query,new String[]{type,productID,departmentId,branchCode,proposalNo,date});
			if (!CollectionUtils.isEmpty(list)) {
				seqName = ((list.get(0).get("SEQNO") == null ? ""
						: list.get(0).get("SEQNO").toString()));
			}
			
			
			logger.info("Result==> " + seqName);
			
		}catch(Exception e){
			logger.debug("Exception @ {" + e + "}");
		}

		return seqName;
	}
	public  String formattereight(final String value)
	{
		String output="0.00";
		if(StringUtils.isNotBlank(value))
		{
			double doublevalue=Double.parseDouble(value);
			DecimalFormat myFormatter = new DecimalFormat("###,###,###,###,##0.00000000");
			output =myFormatter.format(doublevalue);
		}
		return output;

	}


	public Object exchRateFormat(String string) {
		
		return null;
	}
	public String decimalFormat(final double number, final int nos) {

		String noFmt="0";
		try{
			String digit = "0.00";
			noFmt= String.valueOf(number);
			switch (nos) {
			case 0:
				digit = "###,###";
				break;
			case 1:
				digit = "##,##0.0";
				break;
			case 2:
				digit = "##,##0.00";
				break;
			case 3:
				digit = "##,##0.000";
				break;
			case 4:
				digit = "##,##0.0000";
				break;
			case 5:
				digit = "##,##0.00000";
				break;
			case 6:
				digit = "##,##0.000000";
				break;
			default:
				digit = "0.00";
			}
			java.text.NumberFormat fmtNo;
			fmtNo = new java.text.DecimalFormat(digit);
			noFmt = fmtNo.format(number);
		}catch(Exception e){
			e.printStackTrace();
		}

		return noFmt;
	}

}
