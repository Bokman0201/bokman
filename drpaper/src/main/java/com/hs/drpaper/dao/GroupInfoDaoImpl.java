package com.hs.drpaper.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.drpaper.dto.GroupInfoDto;

@Repository
public class GroupInfoDaoImpl implements GroupInfoDao {
	
	@Autowired private SqlSession sqlSession;

	@Override
	public void addInfo(int nextNum) {
		sqlSession.insert("groupInfo.addInfo",nextNum);
		
	}

}
