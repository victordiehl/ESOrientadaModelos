package br.com.gastronomia.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.gastronomia.bo.UsuarioBO;
import br.com.gastronomia.dao.UsuarioDAO;
import br.com.gastronomia.dto.StandardResponseDTO;
import br.com.gastronomia.dto.UsuarioFormattedDTO;
import br.com.gastronomia.model.Token;
import br.com.gastronomia.model.Usuario;
import br.com.gastronomia.util.EncryptUtil;
import br.com.gastronomia.util.TokenGenerator;

import static javax.ws.rs.core.Response.Status.UNAUTHORIZED;


@Path("auth")
public class AuthController {
	Logger logger = Logger.getLogger("controller.AuthController");

	private UsuarioBO usuarioBO = new UsuarioBO();
	private UsuarioDAO usuarioDAO = new UsuarioDAO();

	private EncryptUtil encryptUtil = new EncryptUtil();
	@Context
	private HttpServletResponse response;
	@Context
	private HttpServletRequest request;
	private HttpSession session = null;

	@POST
	@Path("/login")
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public Response login(Usuario usuarioLogin) throws IOException {
		Usuario usuario;
		HashMap<String, Object> responsed = new HashMap<>();
		Token token=new Token();
		try {
			ObjectMapper mapper = new ObjectMapper();
			usuario = usuarioBO.userExists(usuarioLogin);
			token.setToken(TokenGenerator.issueToken(mapper.writeValueAsString(usuario)));
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(UNAUTHORIZED).build();
		}
		return Response.ok().entity(token).build();
	}

	/**
	 * Desloga o usu�rio logado.
	 *
	 * @return Objeto com a resposta do
	 *         m�todo.{@link br.com.gastronomia.dto.StandardResponseDTO}
	 **/
	@GET
	@Path("/logout")
	@Produces("application/json; charset=UTF-8")
	public StandardResponseDTO logoutUser() {
		request.getSession().invalidate();
		return new StandardResponseDTO(true, "Deslogado");
	}

	/**
	 * Verifica se existe um usu�rio logado no sistema.
	 *
	 * @return Retorna o usu�rio logado.
	 * @throws IOException
	 **/
	@GET
	@Path("/me")
	@Produces("application/json; charset=UTF-8")
	public UsuarioFormattedDTO getMe() throws IOException {

		logger.debug("Session ME: " + new Date() + " - " + request.getSession().hashCode());

		System.out.println(request.getSession().getAttributeNames().toString());
		request.getSession().getAttribute("usuario");
		Usuario usuario = (Usuario) request.getSession().getAttribute("usuario");

		if (usuario != null) {
			logger.debug("Usuario inserido na session: " + new Date() + " - " + usuario.toString());
			return UsuarioFormattedDTO.getFromUser(usuario);
		}

		logger.debug("Usuario não existe na session");
		response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
		response.flushBuffer();
		return null;
	}

	@POST
	@Path("/recoverPasswordRequest")
	@Consumes("application/json; charset=UTF-8")
	@Produces("application/json; charset=UTF-8")
	public StandardResponseDTO recoverPasswordRequest(Usuario usuarioLogin) throws IOException {

		Usuario usuario;
		StandardResponseDTO responseJson = new StandardResponseDTO();

		try {
			usuario = usuarioBO.getUserByCpf(usuarioLogin);

			if (usuario.isStatus() == false) {
				responseJson.setMessage("inativo");
				responseJson.setSuccess(false);
				return responseJson;
			}

			StringBuffer reqURL = request.getRequestURL();
			String reqURI = request.getRequestURI();
			String baseURL = request.getRequestURL().substring(0, reqURL.length() - reqURI.length()) + "/";

			// passwordChangeBO.createPasswordChangeRequest(usuario, baseURL);

			responseJson.setSuccess(true);
			responseJson.setMessage(
					 usuario.getEmail());
		} catch (Exception e) {
			response.setStatus(Response.Status.BAD_REQUEST.getStatusCode());
			response.flushBuffer();
			responseJson.setSuccess(false);
			responseJson.setMessage(e.getMessage());
		}

		return responseJson;
	}
}
