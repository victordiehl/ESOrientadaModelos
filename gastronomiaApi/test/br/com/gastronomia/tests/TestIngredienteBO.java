package br.com.gastronomia.tests;


import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.gastronomia.bo.IngredienteBO;
import br.com.gastronomia.dao.IngredienteDAO;
import br.com.gastronomia.exception.ValidationException;
import br.com.gastronomia.model.Ingrediente;

@RunWith(MockitoJUnitRunner.class)
public class TestIngredienteBO {
	
	IngredienteBO ingredienteBO;
	
	@Mock
	IngredienteDAO ingredienteDao;
	
	@Before
	public void init() {
		ingredienteBO = new IngredienteBO();
		ingredienteBO.setIngredienteDao(ingredienteDao);
	}
	
	@Test
	public void testCreateIngrediente() throws NoSuchAlgorithmException, ValidationException {
		Ingrediente ing = new Ingrediente();
		Mockito.when(ingredienteDao.save(ing)).thenReturn((long) 0);
		boolean retorno = ingredienteBO.createIngrediente(ing);
		assertTrue(retorno);
	}
	
	@Test
	public void testListIngrediente() {
		ArrayList<Ingrediente> ingredientes = new ArrayList<>();
		ingredientes.add(new Ingrediente());
		Mockito.when(ingredienteDao.listAllIngredientes()).thenReturn(ingredientes);
		HashMap<String, List<Ingrediente>> listIngredientes = ingredienteBO.listIngrediente();
		assertEquals(ingredientes.get(0), listIngredientes.get("Ingredientes").get(0));
	}
	
	@Test
	public void testUpdateIngrediente() throws ValidationException {
		Ingrediente ing = new Ingrediente();
		Mockito.when(ingredienteDao.updateIngrediente(ing)).thenReturn((long)1);
		long num = ingredienteBO.updateIngrediente(ing);
		assertEquals(1, num);
	}
	
	@Test
	public void testGetIngredientById() throws ValidationException {
		Ingrediente ing = new Ingrediente();
		Mockito.when(ingredienteDao.findIngredienteById(1)).thenReturn(ing);
		Ingrediente ing2 = ingredienteBO.getIngredienteById(1);
		assertEquals(ing, ing2);
	}
	
	@Test
	public void testInactiveIngrediente() {
		Mockito.when(ingredienteDao.alterStatus(0, false)).thenReturn((long)0);
		long result = ingredienteBO.inactiveIngrediente(0);
		assertEquals(result, 0);
	}
	
	@Test
	public void testActiveIngrediente() {
		Mockito.when(ingredienteDao.alterStatus(0, true)).thenReturn((long)0);
		long result = ingredienteBO.activateIngrediente(0);
		assertEquals(result, 0);
	}

}
