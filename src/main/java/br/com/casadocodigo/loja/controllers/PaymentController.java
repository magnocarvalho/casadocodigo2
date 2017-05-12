package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.PaymentData;
import br.com.casadocodigo.loja.models.ShoppingCart;

@RestController
@RequestMapping("/payment")
public class PaymentController {

	@Autowired
	private ShoppingCart shoppingCart;
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public ModelAndView checkout() {
		BigDecimal total = shoppingCart.getTotal();
		String uriToPay = "http://book-payment.herokuapp.com/payment";
		try {
			String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
			System.out.println(response);
			return new ModelAndView("redirect:/payment/success");
		} catch (HttpClientErrorException exception) {
			return new ModelAndView("redirect:/payment/error");
		}
	}
	
	@RequestMapping("/success")
	public ModelAndView success(){
		ModelAndView modelAndView = new ModelAndView("payment/success");
		return modelAndView;
	}
	
	@RequestMapping("/error")
	public ModelAndView error(){
		ModelAndView modelAndView = new ModelAndView("payment/error");
		return modelAndView;
	}
}
