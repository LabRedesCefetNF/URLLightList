package configuracaoHibernate;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Hibernate {

	private static final SessionFactory session = buildSession();

	private static SessionFactory buildSession() {

		Configuration cfg = new Configuration();
		cfg.configure("configuracaoHibernate/hibernate.cfg.xml");
		return cfg.buildSessionFactory();

	}

	public static SessionFactory getSessionFactory() {
		return session;

	}

}
