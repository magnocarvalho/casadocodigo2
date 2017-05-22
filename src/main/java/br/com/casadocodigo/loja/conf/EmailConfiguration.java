package br.com.casadocodigo.loja.conf;

import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class EmailConfiguration {
	
	@Bean
	public MailSender mailSender() {
		JavaMailSenderImpl javaMailSenderImpl = new JavaMailSenderImpl();
		
		javaMailSenderImpl.setHost("smtp.gmail.com");
		javaMailSenderImpl.setPassword("xxxx");
		javaMailSenderImpl.setPort(587);
		javaMailSenderImpl.setUsername("xxxx@gmail.com");
				
		Properties mailProperties = new Properties();
		mailProperties.put("mail.smtp.auth", true);
		mailProperties.put("mail.smtp.starttls.enable", true);
		javaMailSenderImpl.setJavaMailProperties(mailProperties);
		
		return javaMailSenderImpl;
	}

}
