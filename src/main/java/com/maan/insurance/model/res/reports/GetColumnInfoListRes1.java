package com.maan.insurance.model.res.reports;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.model.req.reports.GetIEReportReq;

import lombok.Data;

@Data
public class GetColumnInfoListRes1 {

	@JsonProperty("ExcelHeaderName")
	private String excelHeaderName; 
	@JsonProperty("FieldName")
	private String fieldName;
	@JsonProperty("DataType")
	private String dataType;
	@JsonProperty("Sumationyn")
	private String sumationyn;
	@JsonProperty("Formula")
	private String formula;
	
}
