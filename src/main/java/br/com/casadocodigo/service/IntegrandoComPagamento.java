package br.com.casadocodigo.service;

import java.math.BigDecimal;

import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.User;

public class IntegrandoComPagamento implements Runnable {
	
	private DeferredResult<ModelAndView> result;
	private BigDecimal value;
	private RestTemplate restTemplate;
	private User user;
	private MailSender mailer;
	
	public IntegrandoComPagamento(DeferredResult<ModelAndView> result, BigDecimal value, RestTemplate restTemplate, User user, MailSender mailer) {
		super();
		this.result = result;
		this.value = value;
		this.restTemplate = restTemplate;
		this.user = user;
		this.mailer = mailer;
	}
	
	@Override
	public void run() {
		String uriToPay = "http://book-payment.herokuapp.com/payment";
		try {
			String response = restTemplate.postForObject(uriToPay, new PaymentData(value), String.class);
			System.out.println(response);
			sendNewPurchaseMail();			
			result.setResult(new ModelAndView("redirect:/pagamento/sucesso"));
		} catch (HttpClientErrorException exception) {
			result.setResult(new ModelAndView("redirect:/pagamento/erro"));
		}
	}
	
	private void sendNewPurchaseMail() {
		SimpleMailMessage email = new SimpleMailMessage();
		//email.setFrom("compras@casadocodigo.com.br");
		email.setFrom("williammian@gmail.com");
		//email.setTo(user.getLogin());
		email.setTo("william_mian@yahoo.com.br");
		email.setSubject("Nova compra");
		email.setText("corpo do email");
		mailer.send(email);
	}
}
