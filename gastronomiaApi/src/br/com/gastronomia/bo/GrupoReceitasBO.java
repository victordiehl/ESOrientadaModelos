package br.com.gastronomia.bo;

import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import br.com.gastronomia.dao.GrupoReceitasDAO;
import br.com.gastronomia.exception.ValidationException;
import br.com.gastronomia.model.GrupoReceitas;
import br.com.gastronomia.util.Constantes;
import br.com.gastronomia.util.EncryptUtil;
import br.com.gastronomia.util.MensagemContantes;
import br.com.gastronomia.util.Validator;

public class GrupoReceitasBO {
	private GrupoReceitasDAO grupoReceitasDAO;

	public GrupoReceitasBO() {
		grupoReceitasDAO = new GrupoReceitasDAO();
	}

	public void setGrupoReceitasDAO(GrupoReceitasDAO grupoReceitasDAO) {
		this.grupoReceitasDAO = grupoReceitasDAO;
	}

	public boolean createGroup(GrupoReceitas grupo) throws ValidationException, NoSuchAlgorithmException {
		if (grupo != null) {
			grupoReceitasDAO.save(grupo);
			return true;
		}

		throw new ValidationException("invalido");

	}

	public long deactivateGroup(long id) {
		return grupoReceitasDAO.alterStatus(id, false);
	}

	public long activateGroup(long id) {
		return grupoReceitasDAO.alterStatus(id, true);
	}

	public long updateGroup(GrupoReceitas grupoReceitas) throws ValidationException {
		if (grupoReceitas != null) {
			return grupoReceitasDAO.updateGroup(grupoReceitas);
		}
		throw new ValidationException("invalido");

	}

	public GrupoReceitas validate(GrupoReceitas grupoReceitas) {
		return grupoReceitas;
	}

	public HashMap<String, List<GrupoReceitas>> listGroups() {
		ArrayList<GrupoReceitas> grupoReceitas = null;
		HashMap<String, List<GrupoReceitas>> listGrupoReceitas = new HashMap<String, List<GrupoReceitas>>();
		grupoReceitas = (ArrayList<GrupoReceitas>) grupoReceitasDAO.listAll(GrupoReceitas.class);
		listGrupoReceitas.put("Grupos", grupoReceitas);
		return listGrupoReceitas;
	}

	public GrupoReceitas getGroupByCod(long id) throws ValidationException {
		if (id != 0) {
			return grupoReceitasDAO.findGroupByID(id);
		}
		throw new ValidationException("invalido");

	}


}
