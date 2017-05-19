package br.com.casadocodigo.loja.controllers;

import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.builders.ProductBuilder;
import br.com.casadocodigo.loja.conf.AmazonConfiguration;
import br.com.casadocodigo.loja.conf.AppWebConfiguration;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.conf.SecurityConfiguration;
import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(classes = { AppWebConfiguration.class, JPAConfiguration.class, SecurityConfiguration.class, DataSourceConfigurationTest.class, AmazonConfiguration.class })
@ActiveProfiles("test")
public class ProductsControllerTest {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}
	
	@Test
	@Transactional
	public void shouldListAllBooksInTheHome() throws Exception {
		System.out.println("Testando ProductsControllerTest");
		
		productDAO.save(ProductBuilder.newProduct().buildOne());

		ResultActions action = this.mockMvc.perform(MockMvcRequestBuilders.get("/produtos"));
		ResultMatcher modelAndViewMatcher = new ResultMatcher() {
			@Override
			public void match(MvcResult result) throws Exception {
				ModelAndView mv = result.getModelAndView();
				List<Product> products = (List<Product>) mv.getModel().get("products");
				String assertionError = null;
				try{
					Assert.assertEquals(1, products.size());
				}catch (AssertionError ae) {
		            assertionError = ae.toString();
		        }
		        System.out.println(assertionError);
			}
		};
		action.andExpect(modelAndViewMatcher).andExpect(MockMvcResultMatchers.forwardedUrl("/WEB-INF/views/products/list.jsp"));

	}
	
}
