



import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.cfg.Configuration;


public class HibernateUtil {
	private static SessionFactory sessionFactory;
	 public static SessionFactory getSessionFactory()
	 {
		 if (sessionFactory == null)
		 {
			 Configuration conf = new Configuration().configure("hibernate.cfg.xml");
			 StandardServiceRegistryBuilder builder = new
			 StandardServiceRegistryBuilder().applySettings(conf.getProperties());
			 SessionFactory factory = conf.buildSessionFactory(builder.build());

			
					 }
		 return sessionFactory;
	 }
}