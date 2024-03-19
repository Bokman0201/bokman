package com.hs.drpaper.service;

import java.io.IOException;

import jakarta.mail.MessagingException;

public interface MailService {


	void send(String email, String type, String authenticationNum) throws MessagingException, IOException;

}
