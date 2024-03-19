package com.hs.drpaper.service;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;
import java.util.UUID;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.hs.drpaper.dto.EmailAuthDto;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender sender;

	@Override
	public void send(String email, String type, String authenticationNum) throws MessagingException, IOException {
		MimeMessage messeage = sender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(messeage, false, "UTF-8");

		helper.setTo(email);
		helper.setSubject(type);
		// classpath에 있는 template 폴더의 email.html을 불러오는 코드
		
		//타입에 따라 보내는 메일이 달라짐
		ClassPathResource resource = new ClassPathResource("templates/email2.html");// 자원선택
		File target = resource.getFile();// 파일추출
		Scanner sc = new Scanner(target);// 읽을 수 있는 도구 연결
		StringBuffer buffer = new StringBuffer();// 저장할 수 있는 버퍼 생성

		while (sc.hasNextLine()) {// 더 읽을 줄이 있다면
			buffer.append(sc.nextLine());// 읽어서 버퍼에 추가
		}

		sc.close();

		String text = buffer.toString();// 결과를 1차 저장
		// 해석
		Document doc = Jsoup.parse(text);
		Element who = doc.getElementById("who");
		Element Authentication = doc.getElementById("Authentication");

		who.text("고객님");
	
		Authentication.text(authenticationNum);

		Element link = doc.getElementById("link");
		link.attr("href", "https://www.google.com");// 가져온 태그의 href속성을 변경해라

		helper.setText(doc.toString(), true);

		EmailAuthDto emailAuthDto = new EmailAuthDto();
		emailAuthDto.setEmailAuthCode(authenticationNum);
		emailAuthDto.setEmail(email);
		//emailAuthDao.insertAuth(emailAuthDto);

		sender.send(messeage);
		
	}
}
