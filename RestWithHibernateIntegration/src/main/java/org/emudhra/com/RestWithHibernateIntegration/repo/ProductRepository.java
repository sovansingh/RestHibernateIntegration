package org.emudhra.com.RestWithHibernateIntegration.repo;

import java.io.Serializable;
import java.util.List;

import org.emudhra.com.RestWithHibernateIntegration.model.Product;
import org.emudhra.com.RestWithHibernateIntegration.util.HibernateUtil;
import org.hibernate.Session;

public class ProductRepository {

	public static int saveProduct(Product product) {
		Session session = HibernateUtil.getSf().openSession();
		session.beginTransaction();
		Serializable s=session.save(product);
		session.getTransaction().commit();
		return (Integer)s;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Product> getAllProducts(){
		Session session=HibernateUtil.getSf().openSession();
		return session.createQuery("from "+Product.class.getName()).getResultList();
	}
}
