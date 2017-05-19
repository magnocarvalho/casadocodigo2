package br.com.casadocodigo.loja.daos;

import java.math.BigDecimal;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import br.com.casadocodigo.loja.builders.ProductBuilder;
import br.com.casadocodigo.loja.conf.DataSourceConfigurationTest;
import br.com.casadocodigo.loja.conf.JPAConfiguration;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Product;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {DataSourceConfigurationTest.class,ProductDAO.class,JPAConfiguration.class})
@ActiveProfiles("test")
public class ProductDAOTest {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Transactional
	@Test
	public void shouldSumAllPricesOfEachBookPerType(){
		
		System.out.println("Testando ProductDAOTest");
		
		//salva uma lista de livros impressos
		List<Product> printedBooks = ProductBuilder.newProduct(BookType.PRINTED, BigDecimal.TEN).more(4).buildAll();
		
		for(Product product : printedBooks){
			productDAO.save(product);
		}
		
		//salva uma lista de ebooks
		List<Product> ebooks = ProductBuilder.newProduct(BookType.EBOOK, BigDecimal.TEN).more(4).buildAll();
		
		for(Product product : ebooks){
			productDAO.save(product);
		}
		
		BigDecimal value = productDAO.sumPricesPerType(BookType.PRINTED);
		
		System.out.println(value);
		
		String assertionError = null;
        try {
        	Assert.assertEquals(new BigDecimal(50).setScale(2), value);
        }catch (AssertionError ae) {
            assertionError = ae.toString();
        }
        System.out.println(assertionError);
	}

}
