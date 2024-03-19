package com.hs.drpaper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupMemberDto {

	private String clientEmail;
	private int groupsId;
}
