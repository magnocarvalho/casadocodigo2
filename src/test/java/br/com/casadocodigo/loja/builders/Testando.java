package br.com.casadocodigo.loja.builders;

import br.com.casadocodigo.loja.daos.ProductDAOTest;


public class Testando {
	public static void main(String[] args) {
		System.out.println("Teste");
		org.junit.runner.JUnitCore.runClasses(ProductDAOTest.class);
	}
}
