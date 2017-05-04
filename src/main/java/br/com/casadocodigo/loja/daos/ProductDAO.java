package br.com.casadocodigo.loja.daos;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;

import br.com.casadocodigo.loja.models.Product;

@Repository
public class ProductDAO {
	
	@PersistenceContext
	private EntityManager manager;
	
	public void save(Product product){
		manager.persist(product);
	}
	
	public void update(Product product){
		manager.merge(product);
	}
	
	public List<Product> list() {
		return manager.createQuery("select distinct(p) from Product p join fetch p.prices",Product.class).getResultList();
	}
	
	public void remove(Product product){
		manager.remove(product);
	}
	
	public Product find(Integer id){
		return manager.find(Product.class, id);
	}
	
}
