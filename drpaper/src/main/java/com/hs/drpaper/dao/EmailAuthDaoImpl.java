package com.hs.drpaper.dao;

import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.drpaper.dto.EmailAuthDto;

@Repository
public class EmailAuthDaoImpl implements EmailAuthDao{
	
	@Autowired
	private SqlSession sqlSession;
	@Override
	public EmailAuthDto find(String email) {
		return sqlSession.selectOne("auth.find",email);
	}

	@Override
	public void deleteAuth(String email) {
		sqlSession.delete("auth.deleteAuth",email);
	}

	@Override
	public void createAuth(String email, String authenticationNum) {
		Map<String, Object> params = Map.of("email",email,"emailAuthCode",authenticationNum);
		sqlSession.insert("auth.createAuth", params);
	}
	
}
