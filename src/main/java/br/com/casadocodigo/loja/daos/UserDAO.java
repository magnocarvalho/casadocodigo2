package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.User;

@Repository
public class UserDAO implements UserDetailsService{
	
	@PersistenceContext
	private EntityManager manager;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("load user");
		
//		TypedQuery<User> query = manager.createQuery("select u from User u where u.login = :login", User.class)
//								.setParameter("login", username);
//		
//		return query.getSingleResult();
		
		String jpql = "select u from User u where u.login = :login";
		List<User> users = manager.createQuery(jpql,User.class).setParameter("login", username).getResultList();
		if(users.isEmpty()){
			throw new UsernameNotFoundException("O usuario "+username+" não existe");
		}
		return users.get(0);
	}
}
