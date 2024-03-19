package com.hs.drpaper.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.drpaper.dto.GroupsDto;
import com.hs.drpaper.vo.GroupInfoReqeustVO;
import com.hs.drpaper.vo.GroupResponseVO;

@Repository
public class GroupsDaoImpl implements GroupsDao {

	@Autowired
	private SqlSession sqlSession;

	@Override
	public void createGroup(GroupsDto groupsDto) {
		
		sqlSession.insert("groups.createGroup",groupsDto);
	}
	
	@Override
	public List<GroupResponseVO> groupList() {
		// TODO Auto-generated method stub
		return sqlSession.selectList("groups.groupList");
	}
	
	@Override
	public GroupInfoReqeustVO getGroupInfo(int groupsId) {
		return sqlSession.selectOne("groups.getGroupInfo", groupsId);
	}
	@Override
	public int sequence() {
		return sqlSession.selectOne("groups.sequence");
	}

	@Override
	public GroupsDto findGroup(int groupsId) {
		return sqlSession.selectOne("groups.findGroup",groupsId);
	}
	@Override
	public List<GroupInfoReqeustVO> getMygroup(String clientEmail) {
		return sqlSession.selectList("groups.myGroup",clientEmail);
	}

}
