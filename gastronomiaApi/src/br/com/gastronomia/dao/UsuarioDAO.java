package br.com.gastronomia.dao;

import java.util.List;

import br.com.gastronomia.model.Usuario;
import org.hibernate.Session;

import br.com.gastronomia.db.GenericHibernateDAO;
import br.com.gastronomia.db.HibernateUtil;

public class UsuarioDAO extends GenericHibernateDAO<Usuario> {
	public List<Usuario> listForName(Object User, String q) {
		Session session = HibernateUtil.getFactory();
		List<Usuario> usuarios = session.getNamedQuery("findUserForName").setParameter("userName", q).list();
		return usuarios;
	}

	public Usuario findUserByCPF(String cpf) {
		return (Usuario) findSingleObject("cpf", Usuario.class, cpf);
	}

	public Usuario findUserByEmail(String email) {
		return (Usuario) findSingleObject("email", Usuario.class, email);
	}
	
	public Usuario findUserByMatricula(String matricula) {
		return (Usuario) findSingleObject("matricula", Usuario.class, matricula);
	}


	public long removeUser(Usuario usuario) {
		return remove(usuario);
	}

	public Usuario findUserByID(long id) {
		return (Usuario) findId(id, Usuario.class);
	}

	public long updateUser(Usuario usuario) {
		return merge(usuario);
	}

	public long alterStatus(long id, boolean status) {
		Usuario usuario = findUserByID(id);
		usuario.setStatus(status);
		return merge(usuario);
	}
}
