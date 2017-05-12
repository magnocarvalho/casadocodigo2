package br.com.casadocodigo.loja.controllers;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.request.async.DeferredResult;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.models.ShoppingCart;
import br.com.casadocodigo.service.IntegrandoComPagamento;

@Controller
@RequestMapping("/pagamento")
public class PaymentController {

	@Autowired
	private ShoppingCart shoppingCart;
	
	@Autowired
	private RestTemplate restTemplate;
	
	//Modo síncrono de integração
//	@RequestMapping(value = "checkout", method = RequestMethod.POST)
//	public ModelAndView checkout() {
//		BigDecimal total = shoppingCart.getTotal();
//		String uriToPay = "http://book-payment.herokuapp.com/payment";
//		try {
//			String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
//			System.out.println(response);
//			return new ModelAndView("redirect:/pagamento/sucesso");
//		} catch (HttpClientErrorException exception) {
//			return new ModelAndView("redirect:/pagamento/erro");
//		}
//	}
	
	//Callable para fazer integração assíncrona (simples) com recurso externo
//	@RequestMapping(value = "checkout", method = RequestMethod.POST)
//	public Callable<ModelAndView> checkout() {
//		return new Callable<ModelAndView>() {
//			@Override
//			public ModelAndView call() throws Exception {
//				BigDecimal total = shoppingCart.getTotal();
//				String uriToPay = "http://book-payment.herokuapp.com/payment";
//				try {
//					String response = restTemplate.postForObject(uriToPay, new PaymentData(total), String.class);
//					System.out.println(response);
//					return new ModelAndView("redirect:/pagamento/sucesso");
//				} catch (HttpClientErrorException exception) {
//					return new ModelAndView("redirect:/pagamento/erro");
//				}
//			}
//		};
//	}
	
	//DeferredResult para fazer integração assíncrona (com mais controles) com recurso externo
	@RequestMapping(value = "checkout", method = RequestMethod.POST)
	public DeferredResult<ModelAndView> checkout() {
		BigDecimal total = shoppingCart.getTotal();
		DeferredResult<ModelAndView> result = new DeferredResult<ModelAndView>();
		IntegrandoComPagamento integrandoComPagamento = new IntegrandoComPagamento(result, total, restTemplate);
		Thread thread = new Thread(integrandoComPagamento);
		thread.start();
		return result;
	}
	
	@RequestMapping(value="/sucesso", method=RequestMethod.GET)
	public ModelAndView sucesso(){
		return new ModelAndView("payment/success");
	}
	
	@RequestMapping(value="/erro", method=RequestMethod.GET)
	public ModelAndView erro(){
		return new ModelAndView("payment/error");
	}
	
}
