package com.hs.drpaper.dao;

import java.util.List;

import com.hs.drpaper.dto.GroupMemberDto;

public interface GroupMemberDao {

	void addMember(int nextNum, String clientEmail);

	List<GroupMemberDto> memberList(int groupsId);

}
