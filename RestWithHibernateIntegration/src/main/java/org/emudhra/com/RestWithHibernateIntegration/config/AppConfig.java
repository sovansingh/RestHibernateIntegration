package org.emudhra.com.RestWithHibernateIntegration.config;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.server.ResourceConfig;

@ApplicationPath("/api")
public class AppConfig extends ResourceConfig{

	public AppConfig() {
		packages("org.emudhra.com.RestWithHibernateIntegration.resources","org.emudhra.com.RestWithHibernateIntegration.filter");
	}
}
