package com.hs.drpaper.dao;

import com.hs.drpaper.dto.EmailAuthDto;

public interface EmailAuthDao {

	EmailAuthDto find(String email);

	void deleteAuth(String email);

	void createAuth(String email, String authenticationNum);

}
