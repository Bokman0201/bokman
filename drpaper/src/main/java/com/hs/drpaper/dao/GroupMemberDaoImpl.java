package com.hs.drpaper.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.drpaper.dto.GroupMemberDto;

@Repository
public class GroupMemberDaoImpl implements GroupMemberDao{

	@Autowired 
	private SqlSession sqlSession;
	@Override
	public void addMember(int nextNum, String clientEmail) {
		Map<String, Object> params = Map.of("nextNum",nextNum,"clientEmail", clientEmail);
		sqlSession.insert("groupMember.addMember",params);
	}
	@Override
	public List<GroupMemberDto> memberList(int groupsId) {
		return sqlSession.selectList("groupMember.memberList",groupsId);
	}
}
