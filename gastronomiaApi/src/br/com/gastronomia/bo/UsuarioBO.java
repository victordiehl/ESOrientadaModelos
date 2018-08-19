package br.com.gastronomia.bo;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.gastronomia.dao.UsuarioDAO;
import br.com.gastronomia.exception.ValidationException;
import br.com.gastronomia.model.Usuario;
import br.com.gastronomia.util.Constantes;
import br.com.gastronomia.util.EncryptUtil;
import br.com.gastronomia.util.MensagemContantes;
import br.com.gastronomia.util.Validator;

public class UsuarioBO {
	private UsuarioDAO usuarioDAO;

	public UsuarioBO() {
		usuarioDAO = new UsuarioDAO();
	}

	public void setUsuarioDAO(UsuarioDAO usuarioDAO) {
		this.usuarioDAO = usuarioDAO;
	}
	
	public boolean validateCPF(Usuario usuario) throws ValidationException {
		if (Validator.validaCpf.fazConta(usuario.getCpf()))
			return true;
		throw new ValidationException("invalido");
		
	}
	
	public boolean createUser(Usuario usuario) throws ValidationException, NoSuchAlgorithmException {
		if (usuario != null) {
			usuario.setTipo(Constantes.USER_ROLE);
			String encryptedPassword = EncryptUtil.encrypt2(usuario.getSenha());
			usuario.setSenha(encryptedPassword);
			usuarioDAO.save(usuario);
			return true;
		}
		throw new ValidationException("invalido");

	}

	public long deactivateUser(long id) {
		return usuarioDAO.alterStatus(id, false);
	}

	public long activateUser(long id) {
		return usuarioDAO.alterStatus(id, true);
	}

	public long updateUser(Usuario usuario) throws ValidationException, NoSuchAlgorithmException {
		if (usuario != null) {
			String encryptedPassword = EncryptUtil.encrypt2(usuario.getSenha());
			usuario.setSenha(encryptedPassword);
			return usuarioDAO.updateUser(usuario);
		}
		throw new ValidationException("invalido");

	}

	public Usuario validate(Usuario newUsuario) {
		return newUsuario;
	}

	public Usuario userExists(Usuario usuarioLogin) throws NoSuchAlgorithmException, ValidationException {
		usuarioLogin.setSenha(EncryptUtil.encrypt2(usuarioLogin.getSenha()));
		Usuario returnedUsuario = null;
		if(usuarioLogin.getEmail() == null){
			returnedUsuario = usuarioDAO.findUserByMatricula(usuarioLogin.getMatricula());
		} else {
			returnedUsuario = usuarioDAO.findUserByEmail(usuarioLogin.getEmail());
		}
		if (!usuarioLogin.getSenha().equals(returnedUsuario.getSenha()))
			throw new ValidationException(MensagemContantes.MSG_ERR_USUARIO_SENHA_INVALIDOS);
		return returnedUsuario;
	}

	public HashMap<String, List<Usuario>> listUser() {
		ArrayList<Usuario> usuarios = null;
		HashMap<String, List<Usuario>> listUsers = new HashMap<String, List<Usuario>>();
		usuarios = (ArrayList<Usuario>) usuarioDAO.listAll(Usuario.class);
		listUsers.put("Usuarios", usuarios);
		return listUsers;
	}

	public Usuario getUserByCpf(Usuario usuarioLogin) throws ValidationException {
		if (usuarioLogin != null) {
			return usuarioDAO.findUserByCPF(usuarioLogin.getCpf());
		}
		throw new ValidationException("CPF Invalido");
	}
	
	public Usuario getUserByEmail(Usuario usuarioLogin) throws ValidationException {
		if (usuarioLogin != null) {
			return usuarioDAO.findUserByEmail(usuarioLogin.getEmail());
		}
		throw new ValidationException("Email Invalido");
	}
	
	public Usuario getUserByMatricula(Usuario usuarioLogin) throws ValidationException {
		if (usuarioLogin != null) {
			return usuarioDAO.findUserByMatricula(usuarioLogin.getMatricula());
		}
		throw new ValidationException("Email Invalido");
	}
	
	public Usuario getUserById(long id) throws ValidationException {
		if (id > 0) {
			return usuarioDAO.findUserByID(id);
		}
		throw new ValidationException("invalido");

	}

}
