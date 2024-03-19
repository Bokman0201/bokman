package com.hs.drpaper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder 
public class GroupsDto {

	private long groupsId;
	private String groupsPw;
	private String groupsName;
	private String groupsHost;
	private String groupsInviteCode;
	private String groupsCreateDate;
}
