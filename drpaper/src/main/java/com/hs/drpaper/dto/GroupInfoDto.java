package com.hs.drpaper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupInfoDto {

	private int groupInfoId;
	private int groupInfoMaxMember;
	private int gorupsId;
	private String groupInfoDescription;
}
