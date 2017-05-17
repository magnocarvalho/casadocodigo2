package br.com.casadocodigo.loja.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.loja.daos.UserDAO;

@Controller
public class AuthController {

	@Autowired
	private UserDAO userDao;
	
	@RequestMapping("/login")
	public ModelAndView loginPage(){
		return new ModelAndView("auth/login");
	}
	
	//o logout é automático pelo spring security, chamar /logout
	
	@RequestMapping("/logar")
    public ModelAndView logar(String username, String password) {
		System.out.println(BCrypt.hashpw(password, BCrypt.gensalt())); //para salvar
		//new BCryptPasswordEncoder().encode(password) para salvar
		
		System.out.println("logar");
		
        UserDetails userDetails = userDao.loadUserByUsername(username);
        
        if(!BCrypt.checkpw(password, userDetails.getPassword())){
        	return new ModelAndView("redirect:/login");
        }
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        
        return new ModelAndView("redirect:/produtos");
    }
}
