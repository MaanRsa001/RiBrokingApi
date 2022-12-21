package com.maan.insurance.model.res;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AllocateViewCommonRes {
	
	private List<AllocateViewRes> alllist;
	private List<AllocateViewRes> allocatedList;
	private List<AllocateViewRes> revertedList;

}
