// default package
// Generated 26/11/2019 13:42:21 by Hibernate Tools 3.6.0.Final

import java.util.List;
import javax.naming.InitialContext;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.LockMode;
import org.hibernate.SessionFactory;
import static org.hibernate.criterion.Example.create;

/**
 * Home object for domain model class TipoEvento.
 * @see .TipoEvento
 * @author Hibernate Tools
 */
public class TipoEventoHome {

	private static final Log log = LogFactory.getLog(TipoEventoHome.class);

	private final SessionFactory sessionFactory = getSessionFactory();

	protected SessionFactory getSessionFactory() {
		try {
			return (SessionFactory) new InitialContext().lookup("SessionFactory");
		} catch (Exception e) {
			log.error("Could not locate SessionFactory in JNDI", e);
			throw new IllegalStateException("Could not locate SessionFactory in JNDI");
		}
	}

	public void persist(TipoEvento transientInstance) {
		log.debug("persisting TipoEvento instance");
		try {
			sessionFactory.getCurrentSession().persist(transientInstance);
			log.debug("persist successful");
		} catch (RuntimeException re) {
			log.error("persist failed", re);
			throw re;
		}
	}

	public void attachDirty(TipoEvento instance) {
		log.debug("attaching dirty TipoEvento instance");
		try {
			sessionFactory.getCurrentSession().saveOrUpdate(instance);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void attachClean(TipoEvento instance) {
		log.debug("attaching clean TipoEvento instance");
		try {
			sessionFactory.getCurrentSession().lock(instance, LockMode.NONE);
			log.debug("attach successful");
		} catch (RuntimeException re) {
			log.error("attach failed", re);
			throw re;
		}
	}

	public void delete(TipoEvento persistentInstance) {
		log.debug("deleting TipoEvento instance");
		try {
			sessionFactory.getCurrentSession().delete(persistentInstance);
			log.debug("delete successful");
		} catch (RuntimeException re) {
			log.error("delete failed", re);
			throw re;
		}
	}

	public TipoEvento merge(TipoEvento detachedInstance) {
		log.debug("merging TipoEvento instance");
		try {
			TipoEvento result = (TipoEvento) sessionFactory.getCurrentSession().merge(detachedInstance);
			log.debug("merge successful");
			return result;
		} catch (RuntimeException re) {
			log.error("merge failed", re);
			throw re;
		}
	}

	public TipoEvento findById(TipoEventoId id) {
		log.debug("getting TipoEvento instance with id: " + id);
		try {
			TipoEvento instance = (TipoEvento) sessionFactory.getCurrentSession().get("TipoEvento", id);
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

	public List<TipoEvento> findByExample(TipoEvento instance) {
		log.debug("finding TipoEvento instance by example");
		try {
			List<TipoEvento> results = (List<TipoEvento>) sessionFactory.getCurrentSession()
					.createCriteria("TipoEvento").add(create(instance)).list();
			log.debug("find by example successful, result size: " + results.size());
			return results;
		} catch (RuntimeException re) {
			log.error("find by example failed", re);
			throw re;
		}
	}
}
