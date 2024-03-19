package com.hs.drpaper.dao;

import java.util.List;

import com.hs.drpaper.dto.GroupsDto;
import com.hs.drpaper.vo.GroupInfoReqeustVO;
import com.hs.drpaper.vo.GroupResponseVO;

public interface GroupsDao {

	void createGroup(GroupsDto groupsDto);

	List<GroupResponseVO> groupList();

	GroupInfoReqeustVO getGroupInfo(int groupsId);

	int sequence();

	GroupsDto findGroup(int groupsId);

	List<GroupInfoReqeustVO> getMygroup(String clientEmail);

}
