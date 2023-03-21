package com.spring.backproject;

import com.spring.backproject.Service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

@SpringBootApplication
public class BackprojectApplication {
	@Autowired
	private EmailService emailService;

	public static void main(String[] args) {
		SpringApplication.run(BackprojectApplication.class, args);
	}
/*@EventListener(ApplicationReadyEvent.class)
	public void sendEmail(){
		emailService.sendEmail("masghouniamal84@gmail.com","mail de confir","bonjour");
}*/
}
