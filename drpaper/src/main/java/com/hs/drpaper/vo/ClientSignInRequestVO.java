package com.hs.drpaper.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class ClientSignInRequestVO {
	private String clientEmail;
	private String clientPw;

}
