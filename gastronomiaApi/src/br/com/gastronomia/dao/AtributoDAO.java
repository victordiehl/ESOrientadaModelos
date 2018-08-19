package br.com.gastronomia.dao;

import java.util.List;

import org.hibernate.Session;

import br.com.gastronomia.db.GenericHibernateDAO;
import br.com.gastronomia.db.HibernateUtil;
import br.com.gastronomia.model.Atributo;


public class AtributoDAO extends GenericHibernateDAO<Atributo> {
	
	public List<Atributo> listForName(Object Atributo, String q) {
		Session session = HibernateUtil.getFactory();
		List<Atributo> atributos = session.getNamedQuery("findAtributoForName").setParameter("atributoNome", q).list();
		return atributos;
	}

	public long removeAtributo(Atributo atributo) {
		return remove(atributo);
	}

	public Atributo findAtributoByID(long id) {
		return (Atributo) findId(id, Atributo.class);
	}

	public long updateAtributo(Atributo atributo) {
		return merge(atributo);
	}

	public long alterStatus(long id, boolean status) {
		Atributo atributo = findAtributoByID(id);
		atributo.setStatus(status);
		return merge(atributo);
	}
}
