// default package
// Generated 28/11/2019 14:49:10 by Hibernate Tools 3.6.0.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class LivroRetirado.
 * @see .LivroRetirado
 * @author Hibernate Tools
 */
public class LivroRetiradoHome {

	private static final Log log = LogFactory.getLog(LivroRetiradoHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(LivroRetirado transientInstance) {
		log.debug("persisting LivroRetirado instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(LivroRetirado instance) {
		log.debug("attaching dirty LivroRetirado instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(LivroRetirado instance) {
		log.debug("attaching clean LivroRetirado instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(LivroRetirado persistentInstance) {
		log.debug("deleting LivroRetirado instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public LivroRetirado merge(LivroRetirado detachedInstance) {
		log.debug("merging LivroRetirado instance");
		try {
			LivroRetirado result = (LivroRetirado) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public LivroRetirado findById(int id) {
		log.debug("getting LivroRetirado instance with id: " + id);
		try {
			LivroRetirado instance = (LivroRetirado) sessionFactory.getCurrentSession().get("LivroRetirado", id);
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

	public List<LivroRetirado> findByExample(LivroRetirado instance) {
		log.debug("finding LivroRetirado instance by example");
		try {
			List<LivroRetirado> results = (List<LivroRetirado>) sessionFactory.getCurrentSession()
					.createCriteria("LivroRetirado").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
