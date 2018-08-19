package br.com.gastronomia.tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Matchers.booleanThat;

import java.security.NoSuchAlgorithmException;

import org.junit.Before;
import org.mockito.*;
import org.mockito.runners.MockitoJUnitRunner;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.gastronomia.bo.UsuarioBO;
import br.com.gastronomia.dao.UsuarioDAO;
import br.com.gastronomia.exception.ValidationException;
import br.com.gastronomia.model.Usuario;
import br.com.gastronomia.util.Validator;
import br.com.gastronomia.util.Validator.validaCpf;
import io.jsonwebtoken.lang.Assert;


@RunWith(MockitoJUnitRunner.class)
public class TestUsuarioBO {
	
	
	UsuarioBO usuarioBO;
	
	@Mock
	UsuarioDAO usuarioDAOMock;
	
	@Before
	public void init() {
		usuarioBO = new UsuarioBO();
		usuarioBO.setUsuarioDAO(usuarioDAOMock);
	}
	
	@Test
	public void testCPFTrue() {
		assertEquals(true, Validator.validaCpf.fazConta(("61642096091")));
	}
	@Test
	public void testCPFFalse() {
		assertEquals(false, Validator.validaCpf.fazConta(("85172596021")));
	}
	
	@Test
	public void testGetUserByCPF() throws ValidationException {
		String cpf = "61642096091";
		
		Usuario user = new Usuario(cpf);
		
		Mockito.when(usuarioDAOMock.findUserByCPF(cpf)).thenReturn(user);
		
		Usuario usuario = usuarioBO.getUserByCpf(user);
		
		assertTrue(usuario == user);
	}
	
	@Test
	public void testGetUserByEmail() throws ValidationException {
		String email = "rodrigo.encine@acad.pucrs.br";
		
		Usuario user = new Usuario();
		user.setEmail(email);
		
		Mockito.when(usuarioDAOMock.findUserByEmail(email)).thenReturn(user);
		
		Usuario usuario = usuarioBO.getUserByEmail(user);
		
		assertTrue(usuario == user);
		//assertTrue(usuario.getEmail().equals(user.getEmail()));
	}
	
	
	@Test
	public void testeGetUserByMatricula() throws ValidationException {
		String matricula = "12345678";
		//Usuario user = null;
		Usuario user = new Usuario();
		user.setMatricula(matricula);
		
		Mockito.when(usuarioDAOMock.findUserByMatricula(matricula)).thenReturn(user);
		
		Usuario usuario = usuarioBO.getUserByMatricula(user);
		
		assertTrue(usuario == user);
	}
	@Test
	public void testeGetUserById() throws ValidationException {
		Long id = (long) 1;
		assertNull(usuarioBO.getUserById(id));
		
	}

	@Test
	public void testeCreateUser() throws ValidationException, NoSuchAlgorithmException  {
		String email = "rodrigo.encine@acad.pucrs.br";

		Usuario user = new Usuario();
		user.setEmail(email);
		user.setCpf("61642096091");
		user.setSenha("123456789");
		user.setMatricula("123123123");
		user.setNome("joazinho");

		Mockito.doNothing().doThrow(new IllegalArgumentException()).when(usuarioDAOMock).save(user);

		boolean success = usuarioBO.createUser(user);

		assertTrue(success);
	}
}

