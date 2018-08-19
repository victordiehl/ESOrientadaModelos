package br.com.gastronomia.db;

import java.util.Properties;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import br.com.gastronomia.util.ReadProperties;

public class HibernateUtil {

	private static SessionFactory factory;
	private static Session session;

	public static Session getFactory() {
		try {
			Properties prop = ReadProperties.read();
			Configuration config = new Configuration().configure("hibernate.cfg.xml");
			config.setProperty("hibernate.connection.url", prop.getProperty("conexao.url"));
			config.setProperty("hibernate.connection.password", prop.getProperty("conexao.password"));
			config.setProperty("hibernate.connection.username", prop.getProperty("conexao.user"));
			factory = config.buildSessionFactory();
			session = factory.openSession();
		} catch (Throwable ex) {
			ex.printStackTrace();
			System.out.println("Erro ao iniciar sessao no HibernateUtil: " + ex.getMessage());
			System.err.println("Failed to create sessionFactory object." + ex);
			throw new ExceptionInInitializerError(ex);
		}
		return session;
	}

}
