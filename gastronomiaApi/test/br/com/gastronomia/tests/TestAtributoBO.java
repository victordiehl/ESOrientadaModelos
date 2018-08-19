package br.com.gastronomia.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import br.com.gastronomia.bo.AtributoBO;
import br.com.gastronomia.dao.AtributoDAO;
import br.com.gastronomia.exception.ValidationException;
import br.com.gastronomia.model.Atributo;

@RunWith(MockitoJUnitRunner.class)
public class TestAtributoBO {
	
	AtributoBO atributoBO;
	
	@Mock
	AtributoDAO atributoMock;
	
	@Before
	public void init() {
		atributoBO = new AtributoBO();
		atributoBO.setAtributoDAO(atributoMock);
	}
	
	@Test
	public void testCreateAtributo() throws NoSuchAlgorithmException, ValidationException {
		Atributo at = new Atributo();
		Mockito.when(atributoMock.save(at)).thenReturn((long)0);
		
		boolean salvou = atributoBO.createAtributo(at);
		assertTrue(salvou);
	}
	
	@Test
	public void testUpdateAtributo() throws ValidationException {
		Atributo at = new Atributo();
		Mockito.when(atributoMock.updateAtributo(at)).thenReturn((long) 1);
		long codigo = atributoBO.updateAtributo(at);
		
		assertEquals(codigo,1);
	}
	@Test
	public void testGetAtributoByid() throws ValidationException {
		Atributo at = new Atributo();
		Mockito.when(atributoMock.findAtributoByID(1)).thenReturn(at);
		
		Atributo atributo = atributoBO.getAtributoById(1);
		
		assertEquals(at, atributo);
	}
	
	@Test
	public void inactiveAtributo() {
		Atributo at = new Atributo();
		Mockito.when(atributoMock.alterStatus(0, true)).thenReturn((long) 0);
		long codigo = atributoBO.activateUser(0);
		
		assertEquals(codigo, 0);
	}
	@Test
	public void testActiveAtributo() {
		Mockito.when(atributoMock.alterStatus(0, true)).thenReturn((long) 0);
		long codigo = atributoBO.activateUser(0);
		
		assertEquals(codigo, 0);
		
	}
}
