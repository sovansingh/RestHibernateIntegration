package org.emudhra.com.RestWithHibernateIntegration.filter;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.StringTokenizer;

import javax.annotation.security.DenyAll;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.glassfish.jersey.internal.util.Base64;


public class SecurityFilter implements ContainerRequestFilter{

	@Context
	private ResourceInfo info;
	@Context
	private HttpHeaders headers;
	
	@Override
	public void filter(ContainerRequestContext requestContext) throws IOException {
		Method method = info.getResourceMethod();
		if(!method.isAnnotationPresent(PermitAll.class)) {
			if(method.isAnnotationPresent(DenyAll.class)) {
				requestContext.abortWith(Response.ok("No one can access this...").status(Status.FORBIDDEN).build());
				return;
			}
			List<String> authHeader = headers.getRequestHeader("Authorization");
			if(authHeader==null || authHeader.isEmpty()) {
				requestContext.abortWith(Response.ok("Provider access details").status(Status.BAD_REQUEST).build());
				return;
			}
			List<String> userDtls = getUserNameAndPassword(authHeader.get(0));
			if(method.isAnnotationPresent(RolesAllowed.class)) {
				List<String> rolesAllowed = Arrays.asList(method.getAnnotation(RolesAllowed.class).value());
				if (!validUser(userDtls, rolesAllowed)) {
					requestContext.abortWith(Response.ok("Invalid User Details provided").status(Status.UNAUTHORIZED).build());
					return;
				}
			}
		}
	}
	private boolean validUser(List<String> userDtls,List<String> rolesAllowed) {
		if("admin".equals(userDtls.get(0)) && "admin".equals(userDtls.get(1)) && rolesAllowed.contains("ADMIN"))
			return true;
		else if ("sam".equals(userDtls.get(0)) && "sam".equals(userDtls.get(1)) && rolesAllowed.contains("EMPLOYEE"))
			return true;
		return false;
	}
	
	private List<String> getUserNameAndPassword(String auth){
		auth=auth.replace("Basic ", "");
		auth=new String(Base64.decode(auth.getBytes()));
		StringTokenizer stringTokenizer=new StringTokenizer(auth,":");
		return Arrays.asList(stringTokenizer.nextToken(),stringTokenizer.nextToken());
	}
}
