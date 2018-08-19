package br.com.gastronomia.controllers;

import java.io.IOException;
import java.security.Key;

import javax.annotation.Priority;

import javax.inject.Inject;
import javax.ws.rs.Priorities;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.Provider;

import br.com.gastronomia.util.SimpleKeyGenerator;
import io.jsonwebtoken.Jwts;

@Provider
@JWTTokenNeeded
@Priority(Priorities.AUTHENTICATION)
public class JWTTokenNeededFilter implements ContainerRequestFilter {
 
 
    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
 
        // Get the HTTP Authorization header from the request
    	try {
        String authorizationHeader = requestContext.getHeaderString(HttpHeaders.AUTHORIZATION);
 
        // Extract the token from the HTTP Authorization header
        String token = authorizationHeader.substring("Bearer".length()).trim();
        System.out.println(token);
 
 
            // Validate the token
        	 Key key = SimpleKeyGenerator.generateKey();
             Jwts.parser().setSigningKey(key).parseClaimsJws(token);
           
 
        } catch (Exception e) {
            requestContext.abortWith(Response.status(Response.Status.UNAUTHORIZED).build());
        }
    }
}