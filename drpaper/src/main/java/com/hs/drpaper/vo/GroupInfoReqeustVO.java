package com.hs.drpaper.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupInfoReqeustVO {
	
	private int groupsId;
	private String groupsName;
	private String groupsHost;
	private String groupsCreateDate;
	private String groupInfoDescription;
	private int groupInfoMaxMember;
	private String groupStatus;
	private int memberCount;
	
//	g.groups_id, 
//	g.groups_name,
//	g.groups_host,
//	g.groups_create_date,
//	gi.group_info_description,
//	gi.group_info_max_member
}
