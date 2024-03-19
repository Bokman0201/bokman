package com.hs.drpaper.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientDto {

	private String clientEmail;
	private String clientPw;
	private String clientName;
	private String clientNick;
	private String cleintJoinDate;
	private String clientBirth;
	private long companyId;
}
