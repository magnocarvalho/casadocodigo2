package br.com.casadocodigo.loja.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import br.com.casadocodigo.loja.daos.UserDAO;
import br.com.casadocodigo.loja.models.ShoppingCart;

@Controller
public class AuthController {

	@Autowired
	private UserDAO userDao;
	
	@Autowired
	private ShoppingCart shoppingCart;
	
	@RequestMapping("/login")
	public String loginPage(){
		shoppingCart.removeAll();
		return "auth/login";
	}
	
	@RequestMapping("/logout")
	public void logout(HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		System.out.println("logout");
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){    
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
		
	}
	
	@RequestMapping("/logar")
    public String logar(String username, String password, HttpSession session) {
		System.out.println(BCrypt.hashpw(password, BCrypt.gensalt()));
		
		System.out.println("logar");
		
        UserDetails userDetails = userDao.loadUserByUsername(username);
        
        if(!BCrypt.checkpw(password, userDetails.getPassword())){
        	return "redirect:/login";
        }
        
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(userDetails, password, userDetails.getAuthorities());

        if (usernamePasswordAuthenticationToken.isAuthenticated()) {
            SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
        }
        
        return "redirect:/produtos";
    }
}
