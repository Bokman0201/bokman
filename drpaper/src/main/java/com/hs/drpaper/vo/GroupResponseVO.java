package com.hs.drpaper.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupResponseVO {
	
	private String groupsId;
	private String groupsName;
	private String groupsHost;
	private String groupsCreateDate;
	private String groupStatus;

}
