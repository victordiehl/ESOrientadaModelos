package br.com.gastronomia.imp;

import java.util.List;

public interface GenericDAO<T> {
	public long save(T obj);
	public long remove(T obj);
	public List<T> listAll(Class<?> T);
	public T findId(long id,Class<?> c);
	public String findSingleResultString(String parameter, Object T, String valueParameter, String field);
	public long merge(Object T);
	String findMultipleResultString(String parameter, Object T, String valueParameter, String field);
	Object findSingleObject(String parameter, Class<?> T, Object valueParameter);
}
