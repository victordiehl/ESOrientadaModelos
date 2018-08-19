package br.com.gastronomia.controllers;

import br.com.gastronomia.model.Usuario;
import br.com.gastronomia.util.Util;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;

@Path("/")
public class APIController {

	@GET
	@Path("/")
	@Produces("application/json; charset=UTF-8")
	public Response index() {
		
		Usuario usuario =new Usuario();
		usuario.setNome("Luis Santana");
		return Response.ok().entity(usuario).build();
	}

	@GET
	@Path("/version")
	@Produces("application/text; charset=UTF-8")
	public String getVersion() {
		return Util.getVersion();
	}
}
