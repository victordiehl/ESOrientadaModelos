package br.com.gastronomia.bo;

import br.com.gastronomia.dao.IngredienteDAO;
import br.com.gastronomia.exception.ValidationException;
import br.com.gastronomia.model.Ingrediente;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IngredienteBO {
	private IngredienteDAO ingredienteDAO;

	public IngredienteBO() {
		ingredienteDAO = new IngredienteDAO();
	}
	
	public void setIngredienteDao (IngredienteDAO dao) {
		ingredienteDAO = dao;
	}

	public long inactiveIngrediente(long id) {
		return ingredienteDAO.alterStatus(id, false);
	}

	public long activateIngrediente(long id) {
		return ingredienteDAO.alterStatus(id, true);
	}

	public long updateIngrediente(Ingrediente ingrediente) throws ValidationException {
		if (ingrediente != null) {
			return ingredienteDAO.updateIngrediente(ingrediente);
		}
		throw new ValidationException("invalido");

	}

	public boolean createIngrediente(Ingrediente ingrediente) throws ValidationException, NoSuchAlgorithmException {
		if (ingrediente != null) {
			//System.out.println(ingrediente);
			ingredienteDAO.save(ingrediente);
			return true;
		}
		throw new ValidationException("invalido");

	}

	public HashMap<String, List<Ingrediente>> listIngrediente() {
		ArrayList<Ingrediente> ingredientes = null;
		HashMap<String, List<Ingrediente>> listIngredientes = new HashMap<String, List<Ingrediente>>();
		ingredientes = (ArrayList<Ingrediente>) ingredienteDAO.listAllIngredientes();
		listIngredientes.put("Ingredientes", ingredientes);
		return listIngredientes;
	}

	public Ingrediente getIngredienteById(long id) throws ValidationException {
		if (id > 0) {
			return ingredienteDAO.findIngredienteById(id);
		}
		throw new ValidationException("invalido");

	}

}
