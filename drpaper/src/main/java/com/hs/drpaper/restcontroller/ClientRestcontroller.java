package com.hs.drpaper.restcontroller;

import java.io.IOException;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hs.drpaper.dao.ClientDao;
import com.hs.drpaper.dao.EmailAuthDao;
import com.hs.drpaper.dto.ClientDto;
import com.hs.drpaper.dto.EmailAuthDto;
import com.hs.drpaper.service.MailService;
import com.hs.drpaper.vo.ClientSignInRequestVO;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import lombok.extern.slf4j.Slf4j;

@CrossOrigin
@RequestMapping("/client")
@RestController
@Slf4j
@Tag(name = "client")
public class ClientRestcontroller {
	@Autowired
	private ClientDao clientDao;
	@Autowired
	private BCryptPasswordEncoder encoder;
	@Autowired
	private MailService mailService;
	@Autowired
	private EmailAuthDao emailauthDao;

	@PostMapping("/clientSignup")
	public ResponseEntity<?> clientSignup(@RequestBody ClientDto clientDto) {

		ClientDto findDto = clientDao.find(clientDto.getClientEmail());

		if (findDto != null)
			return ResponseEntity.badRequest().build();
		// 비밀번호 암호화
		String originPw = clientDto.getClientPw();
		String encodedPw = encoder.encode(originPw);
		clientDto.setClientPw(encodedPw);

		clientDao.createClient(clientDto);

		return ResponseEntity.ok(null);
	}

	@PostMapping("/clientSignin")
	public ResponseEntity<?> clientSignin(@RequestBody ClientSignInRequestVO clientSignInRequestVO ) {
		
		ClientDto findDto = clientDao.find(clientSignInRequestVO.getClientEmail());
		log.debug("dbPw={}, inputPw={}result={}",findDto.getClientPw(),clientSignInRequestVO.getClientPw(),encoder.matches(clientSignInRequestVO.getClientPw(), findDto.getClientPw()));
		
		if(encoder.matches(clientSignInRequestVO.getClientPw(), findDto.getClientPw())) {
			findDto.setClientPw(null);	
			return ResponseEntity.ok(findDto);
		}
		else {
			return ResponseEntity.notFound().build();
		}
	}

	// email인증
	@PostMapping("sendEmail/{email}/{type}")
	public ResponseEntity<String> sendEmail(@PathVariable String email, @PathVariable String type)
			throws MessagingException, IOException {

		EmailAuthDto clientDto = emailauthDao.find(email);

		if (clientDto != null)
			return ResponseEntity.badRequest().build();
	
		try {
			log.debug("Start!");
			EmailAuthDto findDto = emailauthDao.find(email);
			log.debug("dto={}", findDto);
			if (findDto != null) {
				// 있으면 지우고
				emailauthDao.deleteAuth(email);
			}

			UUID uuid = UUID.randomUUID();
			String authenticationNum = uuid.toString().replaceAll("-", "").substring(0, 10);

			emailauthDao.createAuth(email, authenticationNum);

			mailService.send(email, type, authenticationNum);
			return ResponseEntity.ok("send success!");
		} catch (Exception e) { // 수정: catch 문 오류 수정
			e.printStackTrace(); // 수정: 예외 상황 로그 출력
			return ResponseEntity.notFound().build(); // 수정: ResponseEntity.not() 대신 실패 응답을 명시적으로 처리
		}

	}

	@DeleteMapping("/clientWithdrawal")
	public ResponseEntity<?> clientWithdrawal() {
		return ResponseEntity.ok(null);
	}

	// 인증하기

	@PostMapping("/isMatchCode/{email}/{code}")
	public ResponseEntity<String> isMatchCode(@PathVariable String email, @PathVariable String code) {
		EmailAuthDto findAuthDto = emailauthDao.find(email);
		log.debug(code, findAuthDto.getEmailAuthCode());

		if (findAuthDto.getEmailAuthCode() == null) {
			return ResponseEntity.badRequest().build();
		}

		if (findAuthDto.getEmailAuthCode().equals(code)) {

			return ResponseEntity.ok("match code!");
		} else {
			return ResponseEntity.notFound().build();
		}

	}

	@DeleteMapping("/deleteAuth/{email}")
	public void deleteAuth(@PathVariable String email) {
		emailauthDao.deleteAuth(email);
	}

}
