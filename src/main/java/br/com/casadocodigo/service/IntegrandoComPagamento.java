package br.com.casadocodigo.service;

import java.math.BigDecimal;

import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.PaymentData;

public class IntegrandoComPagamento implements Runnable {
	
	private DeferredResult<ModelAndView> result;
	private BigDecimal value;
	private RestTemplate restTemplate;
	
	public IntegrandoComPagamento(DeferredResult<ModelAndView> result, BigDecimal value, RestTemplate restTemplate) {
		super();
		this.result = result;
		this.value = value;
		this.restTemplate = restTemplate;
	}
	
	@Override
	public void run() {
		String uriToPay = "http://book-payment.herokuapp.com/payment";
		try {
			String response = restTemplate.postForObject(uriToPay, new PaymentData(value), String.class);
			System.out.println(response);
			//linha de notificação
			result.setResult(new ModelAndView("redirect:/pagamento/sucesso"));
		} catch (HttpClientErrorException exception) {
			result.setResult(new ModelAndView("redirect:/pagamento/erro"));
		}
	}
}
