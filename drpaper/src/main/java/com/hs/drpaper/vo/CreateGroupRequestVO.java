package com.hs.drpaper.vo;

import com.hs.drpaper.dto.GroupTagDto;
import com.hs.drpaper.dto.GroupsDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class CreateGroupRequestVO {
	
	private GroupsDto groupsDto;
	private GroupTagDto groupTagDto;

}
