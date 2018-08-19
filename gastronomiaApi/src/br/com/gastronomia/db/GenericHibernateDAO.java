package br.com.gastronomia.db;

import br.com.gastronomia.imp.GenericDAO;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

@SuppressWarnings("unchecked")

public class GenericHibernateDAO<T> implements GenericDAO<T> {

	@Override
	public long save(T obj) {
		try {
			Session session = HibernateUtil.getFactory();
			Transaction tx = null;
			long id = 0;
			try {
				tx = session.beginTransaction();
				id = (long) session.save(obj);
				tx.commit();
			} catch (HibernateException e) {
				if (tx != null)
					tx.rollback();
				e.printStackTrace();
				System.out.println("Erro de HibernateException ao salvar no GenericHibernateDAO: " + e.getMessage());
			} finally {
				session.close();
			}
			System.out.println("ID: " + id);
			return id;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Erro de Exception no salvar do GenericHibernateDAO: " + e.getMessage());
		}
		return 0;
	}

	@Override
	public long remove(T obj) {
		Session session = HibernateUtil.getFactory();
		Transaction tx = null;
		long sucess = 0;
		try {
			tx = session.beginTransaction();
			session.remove(obj);
			tx.commit();
			return 1;
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			System.out.println("Erro de HibernateException ao excluir no GenericHibernateDAO: " + e.getMessage());
			return sucess;
		} finally {
			session.close();
		}
	}




	@Override
	public List<T> listAll(Class<?> T) {
		Session session = HibernateUtil.getFactory();
		String queryAll = "Select t from " + T.getSimpleName() + " t ";
		List<T> objects = session.createQuery(queryAll).list();
		return objects;
	}

	@Override
	public T findId(long id,Class<?> c) {

		Session session = HibernateUtil.getFactory();
		Transaction tx = null;
		
		try {
			tx = session.beginTransaction();
			T t = (T) session.find(c,id);
			return t;
			
		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
			System.out.println("Erro de HibernateException ao excluir no GenericHibernateDAO: " + e.getMessage());
		
		} finally {
			session.close();
		}
		return null;

	}
	@SuppressWarnings("deprecation")
	@Override
	public String findSingleResultString(String parameter, Object T, String valueParameter, String field) {
		Session session = HibernateUtil.getFactory();
		// Deus me perdoe por tudo que tive que fazer para isto vir a funcionar
		// <2
		String hql = "Select T." + field + " FROM " + T.getClass().getSimpleName() + " T  where T." + parameter
				+ " = ?";
		return session.createQuery(hql).setString(0, valueParameter).getSingleResult().toString().replace("[", "")
				.replace("]", "");
		// return session.createQuery(hql).getSingleResult().toString();
	}

	@Override
	public String findMultipleResultString(String parameter, Object T, String valueParameter, String field) {
		Session session = HibernateUtil.getFactory();
		// Select T.password from FROM Usuario T WHERE T.cpf = 10
		String sql = "Select T." + field + " FROM " + T.getClass().getSimpleName() + " T  where T." + parameter + " ="
				+ valueParameter;
		String results = session.createQuery(sql).list().toString();

		return results;
	}

	@Override
	public Object findSingleObject(String parameter, Class<?> T, Object valueParameter) {
		Session session = HibernateUtil.getFactory();
		
		String hql = "Select T FROM " + T.getSimpleName() + " T  where T." + parameter + " = :" +parameter ;
		return session.createQuery(hql).setParameter(parameter, valueParameter).getSingleResult();

		
	}

	@Override
	public long merge(Object T) {
		Session session = HibernateUtil.getFactory();
		Transaction tx = null;
		long id = 0;
		try {
			tx = session.beginTransaction();
			session.merge(T);
			tx.commit();
			id = 1;

		} catch (HibernateException e) {
			if (tx != null)
				tx.rollback();
			e.printStackTrace();
		} finally {
			session.close();
		}
		return id;

	}

}
