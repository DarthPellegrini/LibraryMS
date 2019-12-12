// default package
// Generated 09/12/2019 15:01:47 by Hibernate Tools 3.6.0.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class Prateleira.
 * @see .Prateleira
 * @author Hibernate Tools
 */
public class PrateleiraHome {

	private static final Log log = LogFactory.getLog(PrateleiraHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(Prateleira transientInstance) {
		log.debug("persisting Prateleira instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(Prateleira instance) {
		log.debug("attaching dirty Prateleira instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(Prateleira instance) {
		log.debug("attaching clean Prateleira instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(Prateleira persistentInstance) {
		log.debug("deleting Prateleira instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public Prateleira merge(Prateleira detachedInstance) {
		log.debug("merging Prateleira instance");
		try {
			Prateleira result = (Prateleira) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public Prateleira findById(int id) {
		log.debug("getting Prateleira instance with id: " + id);
		try {
			Prateleira instance = (Prateleira) sessionFactory.getCurrentSession().get("Prateleira", id);
			if (instance == null) {
				log.debug("get successful, no instance found");
			} else {
				log.debug("get successful, instance found");
			}
			return instance;
		} catch (RuntimeException re) {
			log.error("get failed", re);
			throw re;
		}
	}

	public List<Prateleira> findByExample(Prateleira instance) {
		log.debug("finding Prateleira instance by example");
		try {
			List<Prateleira> results = (List<Prateleira>) sessionFactory.getCurrentSession()
					.createCriteria("Prateleira").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
