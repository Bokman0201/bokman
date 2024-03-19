package com.hs.drpaper.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class GroupJoinRequestVO {
	
	private String clientEmail;
	private int groupsId;
	private String groupsPw;

}
