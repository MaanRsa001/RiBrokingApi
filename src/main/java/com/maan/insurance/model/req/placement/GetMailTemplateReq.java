package com.maan.insurance.model.req.placement;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.maan.insurance.error.ErrorCheck;
import com.maan.insurance.model.res.placement.PlacementSummaryRes;
import com.maan.insurance.model.res.placement.PlacementSummaryRes1;

import lombok.Data;

@Data
public class GetMailTemplateReq {
	@JsonProperty("MailType")
	private String mailType; 
	@JsonProperty("MailTo")
	private String mailTo;
	@JsonProperty("MailCC")
	private String mailCC; 
}
