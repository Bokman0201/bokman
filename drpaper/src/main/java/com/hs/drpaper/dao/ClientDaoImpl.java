package com.hs.drpaper.dao;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.hs.drpaper.dto.ClientDto;

@Repository
public class ClientDaoImpl implements ClientDao{

	@Autowired
	private SqlSession sqlSession;
	@Override
	public void createClient(ClientDto clientDto) {
		sqlSession.insert("client.createClient",clientDto);
		
	}
	@Override
	public ClientDto find(String clientEmail) {
		return sqlSession.selectOne("client.find",clientEmail);
	}
}
