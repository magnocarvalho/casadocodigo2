package br.com.casadocodigo.loja.controllers;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.com.casadocodigo.loja.daos.ProductDAO;
import br.com.casadocodigo.loja.models.BookType;
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
	public ModelAndView form(Product product){
		ModelAndView modelAndView = new ModelAndView("products/form");
		modelAndView.addObject("types", BookType.values());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.GET)
	public ModelAndView list(){
		ModelAndView modelAndView = new ModelAndView("products/list");
		modelAndView.addObject("products", productDAO.list());
		return modelAndView;
	}
	
	@RequestMapping(method=RequestMethod.POST, name="saveProduct")
	public ModelAndView save(MultipartFile summary, @Valid Product product, BindingResult bindingResult, RedirectAttributes redirectAttributes){
		if(bindingResult.hasErrors()){
			return form(product);
		}
		
		System.out.println(summary.getName() + ";" + summary.getOriginalFilename());
		String webPath = fileSaver.write(summary);
		product.setSummaryPath(webPath);
		
		productDAO.save(product);
		redirectAttributes.addFlashAttribute("sucesso", "Produto cadastrado com sucesso");
		return new ModelAndView("redirect:/produtos");
	}
	
	//"<c:url value='/produtos/download/${product.summaryPath}' />"
	//"/casadocodigo2/produtos/download/123"
	//http://localhost:9444/s3/casadocodigo2/logo-multitec_pequeno.png
	
	@RequestMapping(method=RequestMethod.GET, value="/download")
    public void downloadFile(HttpServletResponse response, String file) throws IOException {
		System.out.println("Teste");
		System.out.println(file);
		
		InputStream inputStream = fileSaver.read(file);
		
//		File file = new File("C:\teste.txt");
//		
//		String mimeType= URLConnection.guessContentTypeFromName(file.getName());
//		
//		response.setContentType(mimeType);
//        
//        response.setHeader("Content-Disposition", String.format("inline; filename=\"" + file.getName() +"\""));
//         
//        response.setContentLength((int)file.length());
// 
//        InputStream inputStream = new BufferedInputStream(new FileInputStream(file));
// 
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", String.format("attachment; filename=\"%s\"", file));
		FileCopyUtils.copy(inputStream, response.getOutputStream());
        
        //return "redirect:/produtos";
	}
	
//	@InitBinder
//	protected void initBinder(WebDataBinder binder) {
//		binder.setValidator(new ProductValidator());
//	}
	
}
