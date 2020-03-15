package org.emudhra.com.RestWithHibernateIntegration.util;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

	private static SessionFactory sf;
	static {
		try {
			Configuration configuration=new Configuration().configure();
			sf=configuration.buildSessionFactory();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static SessionFactory getSf() {
		return sf;
	}
}
