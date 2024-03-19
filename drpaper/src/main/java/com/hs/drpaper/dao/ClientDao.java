package com.hs.drpaper.dao;

import com.hs.drpaper.dto.ClientDto;

public interface ClientDao {

	void createClient(ClientDto clientDto);

	ClientDto find(String clientEmail);
	
	

}
