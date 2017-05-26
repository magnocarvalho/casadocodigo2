package br.com.casadocodigo.loja.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
import br.com.casadocodigo.loja.models.Price;
import br.com.casadocodigo.loja.models.Product;
import br.com.casadocodigo.loja.utils.FileSaver;

@Controller
@Transactional
@RequestMapping("/produtos")
public class ProductsController {
	
	@Autowired
	private FileSaver fileSaver;
	
	@Autowired
	private ProductDAO productDAO;
	
	@RequestMapping("/form")
	public ModelAndView form(@ModelAttribute Product product){
		ModelAndView modelAndView = new ModelAndView("products/form");
		
		modelAndView.addObject("product", product);
		
		modelAndView.addObject("types", BookType.values());
		
		BigDecimal[] precos = new BigDecimal[3];
		int i = 0;
		for(Price price : product.getPrices()){
			precos[i] = price.getValue();
			i++;
		}
		
		modelAndView.addObject("precos", precos);
		
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	@Cacheable(value="books")
	public ModelAndView list(){
		System.out.println("Exibindo os livros");
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST, name="saveProduct")
	@CacheEvict(value="books", allEntries=true)
	public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if(bindingResult.hasErrors()){
			return form(product);
		}
		
		String arquivo = fileSaver.write(summary);
		if(arquivo != null){
			if(product.getSummaryPath() == null || product.getSummaryPath().length() == 0){
				product.setSummaryPath(arquivo);
			}else if(!product.getSummaryPath().equals(arquivo)){
				fileSaver.deleteObject(product.getSummaryPath());
				product.setSummaryPath(arquivo);
			}
		}
				
		if(product.getId() == null){
			productDAO.save(product);
			redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		}else{
			productDAO.update(product);
			redirectAttributes.addFlashAttribute("sucesso", "Produto alterado com sucesso");
		}
		
		return new ModelAndView("redirect:/produtos");
	}
	
	//"<c:url value='/produtos/download/${product.summaryPath}' />"
	//"/casadocodigo2/produtos/download/123"
	//http://localhost:9444/s3/casadocodigo2/logo-multitec_pequeno.png
	
	@RequestMapping(method=RequestMethod.GET, value="/download")
    public void downloadFile(HttpServletResponse response, String file) throws IOException {
				
		InputStream inputStream = fileSaver.read(file);
		
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());   
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/excluir/{id}")
	public ModelAndView excluir(@PathVariable Integer id, RedirectAttributes redirectAttributes){
		
		Product product = productDAO.find(id);
		
		fileSaver.deleteObject(product.getSummaryPath());
		
		productDAO.remove(product);
		
		redirectAttributes.addFlashAttribute("sucesso", "Produto removido com sucesso");
		return new ModelAndView("redirect:/produtos");
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/alterar/{id}")
	public ModelAndView alterar(@PathVariable Integer id){
		Product product = productDAO.find(id);
		return form(product);
	}
	
	@RequestMapping(method=RequestMethod.GET, value="/apagarImagem/{id}")
	public ModelAndView apagerImagem(@PathVariable Integer id){
		Product product = productDAO.find(id);
		fileSaver.deleteObject(product.getSummaryPath());
		product.setSummaryPath(null);
		productDAO.update(product);
		return form(product);
	}
	
	@RequestMapping(method=RequestMethod.GET,value="/show")
	public ModelAndView show(Integer id){
		ModelAndView modelAndView = new ModelAndView("products/show");
		Product product = productDAO.get(id);
		modelAndView.addObject("product", product);
		return modelAndView;
	}
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.setValidator(new ProductValidator());
//	}
	
	@RequestMapping(method=RequestMethod.GET,value="/listaProdutos")
	public ModelAndView listaProdutos(){
		List<Map<String, Object>> lista = productDAO.listProdutos();
		if(lista != null && lista.size() > 0){
			for(int i = 0; i < lista.size(); i++){
				System.out.println("Linha " + i);
				Map<String, Object> mapLinha = lista.get(i);
				for(String key : mapLinha.keySet()){
					System.out.println(key + " " + mapLinha.get(key));
				}
			}
		}
		
		return new ModelAndView("redirect:/produtos"); 
	}
	
}
